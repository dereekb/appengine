import {
  UniqueModel, ModelKey, ModelUtility, AsyncModelCacheWrap, SourceFactory,
  KeySafeAsyncModelCacheWrap, TimedKeySafeAsyncModelCacheWrap, NonCacheAsyncModelCacheWrap, ModelsOrKeys, ModelOrKey, ValueUtility
} from '@gae-web/appengine-utility';
import { ModelReadService, AppEngineReadSourceFactory, ModelUpdateService, ModelDeleteService } from './crud.service';
import { WrapperEventType, ModelWrapperEvent, ModelServiceAnonymousWrapperEventSystem, AnonymousWrapperEvent, WrapperEvent, WrapperEventFilter } from './wrapper';
import { Subject, Observable } from 'rxjs';
import { filter, tap } from 'rxjs/operators';
import { ReadService, CreateService, UpdateService, DeleteService, QueryService, CreateRequest, CreateResponse, TypedModelSearchService } from '@gae-web/appengine-api';
import { ModelQueryService, KeyedPredictiveOrderedQueryDelegate, ModelFilteredKeyedPredictiveOrderedQueryDelegate, ModelSearchService } from './search.service';
import { BaseError } from 'make-error';

/**
 * Used for announcing key-only events.
 */
export abstract class ModelServiceEventAnnouncer {

  /**
   * Manually announces the creation of models of this type.
   *
   * Useful when models are created indirectly.
   */
  public announceCreated(keys: ModelKey[]) {
    return this.announceEvent(WrapperEventType.ModelCreate, keys);
  }

  /**
   * Manually announces the update of models of this type.
   *
   * Useful when models are updated indirectly.
   */
  public announceUpdated(keys: ModelKey[]) {
    return this.announceEvent(WrapperEventType.ModelUpdate, keys);
  }

  public announceDeleted(keys: ModelKey[]) {
    return this.announceEvent(WrapperEventType.ModelDelete, keys);
  }

  public abstract announceEvent(eventType: WrapperEventType, keys: ModelKey[]);

}

export class KeyOnlyModelServiceEventAnnouncer extends ModelServiceEventAnnouncer {

  constructor(protected readonly _wrapper: ModelServiceWrapper<UniqueModel>) {
    super();
  }

  // MARK: Announcements
  public announceEvent(eventType: WrapperEventType, keys: ModelKey[]) {
    this._wrapper.next({
      modelType: this._wrapper.type,
      eventType,
      // Make sure the keys are unique, always.
      keys: ModelUtility.filterUniqueModelKeys(keys)
    } as ModelWrapperEvent);
  }

}

export class FullModelServiceEventAnnouncer<T extends UniqueModel> extends KeyOnlyModelServiceEventAnnouncer {

  constructor(wrapper: ModelServiceWrapper<T>, private _reader: ModelReadService<T>) {
    super(wrapper);
  }

  // MARK: Announcements
  public announceEvent(eventType: WrapperEventType, keys: ModelKey[]) {
    keys = ModelUtility.filterUniqueModelKeys(keys);

    switch (eventType) {
      case WrapperEventType.ModelDelete:
        super.announceEvent(eventType, keys);
        break;
      default:
        this._tryReadAndAnnounceEvent(eventType, keys);
        break;
    }
  }

  private _tryReadAndAnnounceEvent(eventType: WrapperEventType, keys: ModelKey[]) {
    this._reader.read({
      modelKeys: keys
    }, false).subscribe((result) => {

      // TODO: Should we enforce the keys to match models?

      this._wrapper.next({
        modelType: this._wrapper.type,
        eventType,
        models: result.models,
        keys
      } as ModelWrapperEvent);
    }, (e) => {
      console.error('Error: ' + e);
    });
  }

}

// MARK: Wrapper
export abstract class ModelServiceWrapperConfig<T extends UniqueModel> {
  /**
   * Model type
   */
  readonly type: string;

  /**
   * Whether or not to cache values at all.
   */
  readonly cache?: boolean;

  /**
   * Time to live of items in the cache.
   */
  readonly cacheTimeToLive?: number;

  init?: (wrapper: ModelServiceWrapper<T>, wrapperSet: ModelServiceWrapperSet) => void;
}

export class ModelServiceWrapper<T extends UniqueModel> implements ModelServiceEventAnnouncer, ModelServiceAnonymousWrapperEventSystem {

  private _cache: AsyncModelCacheWrap<T>;
  private _events = new Subject<ModelWrapperEvent>();

  private _config: ModelServiceWrapperConfig<T>;

  private _announcer: ModelServiceEventAnnouncer = new KeyOnlyModelServiceEventAnnouncer(this);

  constructor(private _type: string, private _parent: ModelServiceWrapperSet) { }

  public init(config: ModelServiceWrapperConfig<T>) {
    if (this._config) {
      throw new ModelWrapperInitializedError(this._type);
    }

    this._config = config;

    // Init Cache
    if (config.cache !== false) {
      if (config.cacheTimeToLive) {
        this._cache = new TimedKeySafeAsyncModelCacheWrap<T>(config.cacheTimeToLive);
      } else {
        this._cache = new KeySafeAsyncModelCacheWrap<T>();
      }
    } else {
      this._cache = new NonCacheAsyncModelCacheWrap<T>();
    }

    // Call Init Function
    if (config.init) {
      config.init(this, this._parent);
    }
  }

  public get type(): string {
    return this._type;
  }

  public get parent(): ModelServiceWrapperSet {
    return this._parent;
  }

  public get cache(): AsyncModelCacheWrap<T> {
    return this._cache;
  }

  public get events(): Observable<ModelWrapperEvent> {
    return this._events.asObservable();
  }

  public watchEventType(eventType: WrapperEventType): Observable<ModelWrapperEvent> {
    return this.events.pipe(
      filter((e) => e.eventType === eventType)
    );
  }

  // MARK: Events
  public nextEvent(event: AnonymousWrapperEvent): void {
    this.next(event);
  }

  public next(input: AnonymousWrapperEvent): void {
    const event = WrapperEvent.make({ ...input, modelType: this.type });
    this._events.next(event);
  }

  // MARK: Factory
  public makeReadSourceFactory(service: ReadService<T>): SourceFactory<T> {
    return new AppEngineReadSourceFactory<T>(this, service);
  }

  public wrapCreateService(service: CreateService<T>): ModelCreateService<T> {
    return new ModelCreateService<T>(this, service);
  }

  public wrapReadService(service: ReadService<T>): ModelReadService<T> {
    return new ModelReadService<T>(this, service);
  }

  public wrapUpdateService<S extends ModelUpdateService<T>>(service: UpdateService<T>, constructor?: (parent: ModelServiceWrapper<T>, updateService: UpdateService<T>) => S): S {
    if (constructor) {
      return constructor(this, service);
    } else {
      return new ModelUpdateService<T>(this, service) as S;
    }
  }

  public wrapDeleteService(service: DeleteService<T>): ModelDeleteService<T> {
    return new ModelDeleteService<T>(this, service);
  }

  public wrapQueryService(readService: ModelReadService<T>, queryService: QueryService<T>): ModelQueryService<T> {
    return new ModelQueryService<T>(this, readService, queryService);
  }

  public wrapSearchService(readService: ModelReadService<T>, searchService: TypedModelSearchService<T>): ModelSearchService<T> {
    return new ModelSearchService<T>(this, readService, searchService);
  }

  public makeKeyedPredictiveOrderedQueryDelegate(eventFilter?: WrapperEventFilter): KeyedPredictiveOrderedQueryDelegate<T> {
    return new KeyedPredictiveOrderedQueryDelegate<T>(this, eventFilter);
  }

  public makeModelFilteredKeyedPredictiveOrderedQueryDelegate(modelsFilter: (model: T) => boolean,
    strictFilter?: boolean,
    eventFilter?: WrapperEventFilter): ModelFilteredKeyedPredictiveOrderedQueryDelegate<T> {
    return new ModelFilteredKeyedPredictiveOrderedQueryDelegate<T>(this, modelsFilter, strictFilter, eventFilter);
  }

  // MARK: Announcements
  public announceCreated(keys: ModelKey[]) {
    return this._announcer.announceCreated(keys);
  }

  public announceUpdated(keys: ModelKey[]) {
    return this._announcer.announceUpdated(keys);
  }

  public announceDeleted(keys: ModelKey[]) {
    return this._announcer.announceDeleted(keys);
  }

  public announceEvent(eventType: WrapperEventType, keys: ModelKey[]) {
    return this._announcer.announceEvent(eventType, keys);
  }

  public setFullAnnouncerWithReadService(readService: ModelReadService<T>) {
    this._announcer = new FullModelServiceEventAnnouncer(this, readService);
  }

  // MARK: Cache
  public clearFromCache(keys: ModelsOrKeys<T>): T[] {
    return this._cache.removeAll(ModelUtility.readModelKeysFromModelsOrKeys(keys));
  }

  // MARK: Utility
  /**
   * Attempts to load and update the specified model if it exists in the cache.
   *
   * @param key Key of model to load.
   * @param updateFn Update function. If a model is returned, the value will be replaced in the cache.
   * If false is returned, no change occurs. If undefined is returned, the original model is notified to be updated.
   */
  public tryUpdateCachedModel(key: ModelOrKey<T>, updateFn: (model: T) => T | false | true | undefined | void) {
    const keyValue = ModelUtility.readModelKey(key);

    const cachedModel = this.cache.get(keyValue);

    if (cachedModel) {
      const updateFnResult = updateFn(cachedModel);

      if (updateFnResult !== false) {
        const updateValue = ((updateFnResult === true) ? cachedModel : updateFnResult) || cachedModel;
        this._cache.putModel(updateValue);
        this.announceUpdated([keyValue]);
      }
    }
  }

}

// MARK: Create
export class ModelCreateService<T extends UniqueModel> implements CreateService<T> {

  constructor(protected readonly _parent: ModelServiceWrapper<T>, protected readonly _createService: CreateService<T>) { }

  // MARK: Create Service
  public create(request: CreateRequest<T>): Observable<CreateResponse<T>> {

    // Add all created models to the cache.
    return this._createService.create(request).pipe(
      tap((x) => this.updateForCreateResponse(request, x))
    );
  }

  protected updateForCreateResponse(request: CreateRequest<T>, response: CreateResponse<T>) {
    this._parent.next({
      eventType: WrapperEventType.ModelCreate,
      request,
      response,
      models: response.models
    });

    this._parent.cache.putModels(response.models);
  }

}

// MARK: Services Wrapper
export class ModelServiceWrapperSet {

  private _map = new Map<string, ModelServiceWrapper<UniqueModel>>();
  private _events = new Subject<ModelWrapperEvent>();

  constructor() { }

  // MARK: Accessors
  public initWrapper<T extends UniqueModel>(config: ModelServiceWrapperConfig<T>): ModelServiceWrapper<T> {
    const type = config.type;
    const wrapper = this.getWrapper<T>(type);
    wrapper.init(config);

    wrapper.events.subscribe((event) => {
      this._events.next(event);
    });

    return wrapper;
  }

  public getWrapper<T extends UniqueModel>(type: string): ModelServiceWrapper<T> {
    type = type.toLowerCase();

    if (!this._map.has(type)) {
      this._map.set(type, new ModelServiceWrapper<T>(type, this));
    }

    return this._map.get(type) as ModelServiceWrapper<T>;
  }

  // MARK: Events
  get events(): Observable<ModelWrapperEvent> {
    return this._events.asObservable();
  }

  public watchEventsForModelType(type: string, eventType?: WrapperEventType): Observable<ModelWrapperEvent> {
    let obs;

    if (eventType !== undefined) {
      obs = this.getWrapper(type).watchEventType(eventType);
    } else {
      obs = this.getWrapper(type).events;
    }

    return obs;
  }

}

// MARK: Error
export class ModelWrapperInitializedError extends BaseError {

  constructor(public readonly type: string) {
    super(`ServiceWrapper of type "${type}" has already been initialized.`);
  }

}
