import { AbstractIterableSourceComponent, ProvideIterableSourceComponent } from './source.component';
import { UniqueModel, ModelKey, IterableSource, WrappedIterableSource } from '@gae-web/appengine-utility';
import { Observable, BehaviorSubject, combineLatest } from 'rxjs';
import { OnDestroy, Input, Type, Directive, AfterContentInit, Inject, Host } from '@angular/core';
import { QueryService, TypedModelSearchService } from '@gae-web/appengine-api';
import {
  KeyQuerySource, KeySearchSource, QuerySourceConfiguration,
  TypedModelSearchSourceConfiguration, SearchSourceConfiguration, KeyTypedModelSearchSource
} from '@gae-web/appengine-client';
import { AbstractSubscriptionComponent } from '../../shared/subscription';
import { map } from 'rxjs/operators';

/**
 * Basic component used to wrap a KeyQuerySource or similar iterable source.
 *
 * Automatically calls next when initialized.
 *
 * Use AbstractConfigurableKeyQuerySourceComponent for queries that must be configured.
 */
export abstract class AbstractIterableKeySourceComponent extends AbstractIterableSourceComponent<ModelKey> {

  constructor(iterableSource: IterableSource<ModelKey>, callNextOnReset = true) {
    super(iterableSource, callNextOnReset);
  }

  get keys(): Observable<ModelKey[]> {
    return this.elements;
  }

}

/**
 * AbstractIterableKeySourceComponent that creates a WrappedIterableSource initially.
 */
export abstract class AbstractWrappedIterableKeySourceComponent<T extends UniqueModel> extends AbstractIterableKeySourceComponent {

  constructor() {
    super(new WrappedIterableSource<ModelKey>());
  }

  protected setWrapperSource(realSource: IterableSource<ModelKey>) {
    const source: WrappedIterableSource<ModelKey> = (this.source as WrappedIterableSource<ModelKey>);
    source.setSource(realSource);
  }

  get isInitialized() {
    return (this.source as WrappedIterableSource<ModelKey>).hasSource();
  }

}

export function ProvideConfigurableKeySearchSourceComponent<S extends AbstractConfigurableKeySearchSourceComponent<any, any>>(sourceType: Type<S>) {
  return [...ProvideIterableSourceComponent(sourceType), { provide: AbstractConfigurableKeySearchSourceComponent, useExisting: sourceType }];
}

/**
 * Abstract key query source component that can be configured to wait for query configuration.
 *
 * If you don't need to configure the query through this type, extend AbstractIterableKeySourceComponent.
 */
export abstract class AbstractConfigurableKeySearchSourceComponent<T extends UniqueModel, C extends SearchSourceConfiguration> extends AbstractIterableKeySourceComponent implements OnDestroy {

  private _waitForConfiguration = true;

  private _stopWaiting: () => void;
  private _waiting: Promise<{}>;

  constructor(input: KeySearchSource<T, C>, callNextOnReset?: boolean) {
    super(input, callNextOnReset);
    this._waiting = new Promise((stopWaiting) => {
      this._stopWaiting = stopWaiting;
    });
  }

  protected get source(): KeyQuerySource<T> {
    return this._source as KeyQuerySource<T>;
  }

  public ngOnDestroy() {
    super.ngOnDestroy();
    this._stopWaiting();
  }

  // MARK: Config
  public get waitForConfig() {
    return this._waitForConfiguration;
  }

  @Input()
  public set waitForConfig(wait: boolean) {
    this._waitForConfiguration = wait;

    if (!wait) {
      this.stopWaiting();
    }
  }

  public get config(): C {
    return this.source.config as C;
  }

  @Input()
  public set config(config: C) {
    this.source.config = config;
    this.stopWaiting();
  }

  /**
   * Loads the next value.
   */
  public next(): Promise<ModelKey[]> {
    return this._waiting.then(() => {
      return this.source.next().catch(() => undefined);
    });
  }

  protected stopWaiting() {
    this._stopWaiting();
  }

}

export type GaeKeyQuerySourceFilterDirectiveMakeConfigFunction<C> = (baseConfig: any, filter: any) => C;

export function DEFAULT_MAKE_CONFIG(baseConfig: any, filter: any) {
  return {
    ...baseConfig,
    filter
  };
}

@Directive({
  selector: '[gaeKeyQuerySourceFilter]',
  exportAs: 'gaeKeyQuerySourceFilter'
})
export class GaeKeyQuerySourceFilterDirective<C extends SearchSourceConfiguration> extends AbstractSubscriptionComponent implements AfterContentInit {

  @Input()
  public refreshOnNewConfig = true;

  private _makeConfig = new BehaviorSubject<GaeKeyQuerySourceFilterDirectiveMakeConfigFunction<C>>(undefined);
  private _baseConfig = new BehaviorSubject<C>(undefined);
  private _filter = new BehaviorSubject<any>(undefined);

  constructor(@Inject(AbstractConfigurableKeySearchSourceComponent) @Host() public readonly component: AbstractConfigurableKeySearchSourceComponent<any, C>) {
    super();
  }

  ngAfterContentInit() {
    this.sub = combineLatest([this._baseConfig, this._filter, this._makeConfig]).pipe(
      map(([config, filter, makeFn]) => {
        if (!makeFn) {
          makeFn = DEFAULT_MAKE_CONFIG;
        }

        return makeFn(config, filter);
      })
    ).subscribe({
      next: (newConfig) => {
        this._updateConfig(newConfig);
      }
    });
  }

  public get makeConfig() {
    return this._makeConfig.value;
  }

  @Input()
  public set makeConfig(makeConfig: GaeKeyQuerySourceFilterDirectiveMakeConfigFunction<C>) {
    this._makeConfig.next(makeConfig);
  }

  public get baseConfig() {
    return this._baseConfig.value;
  }

  @Input()
  public set baseConfig(config: C) {
    this._baseConfig.next(config);
  }

  public get filter() {
    return this._filter.value;
  }

  @Input('gaeKeyQuerySourceFilter')
  public set filter(filter: any) {
    this._filter.next(filter);
  }

  // MARK: Internal
  protected _updateConfig(config: C) {
    this.component.config = config;

    if (this.refreshOnNewConfig) {
      this.component.reset();
    }
  }

}

/**
 * Abstract key query source component that can be configured to wait for query configuration.
 *
 * If you don't need to configure the query through this type, extend AbstractIterableKeySourceComponent.
 */
export abstract class AbstractConfigurableKeyQuerySourceComponent<T extends UniqueModel> extends AbstractConfigurableKeySearchSourceComponent<T, QuerySourceConfiguration> {

  static makeKeyQuerySource<T extends UniqueModel>(input: QueryService<T> | KeyQuerySource<T>): KeyQuerySource<T> {
    if (input instanceof KeyQuerySource) {
      return input;
    } else {
      return new KeyQuerySource<T>(input);
    }
  }

  constructor(input: QueryService<T> | KeyQuerySource<T>, callNextOnReset?: boolean) {
    super(AbstractConfigurableKeyQuerySourceComponent.makeKeyQuerySource(input), callNextOnReset);
  }

}

/**
 * Abstract key search source component that can be configured to wait for query configuration.
 *
 * If you don't need to configure the search through this type, extend AbstractIterableKeySourceComponent.
 */
export abstract class AbstractConfigurableKeyTypedModelSearchSourceComponent<T extends UniqueModel> extends AbstractConfigurableKeySearchSourceComponent<T, TypedModelSearchSourceConfiguration> {

  static makeKeySearchSource<T extends UniqueModel>(input: TypedModelSearchService<T> | KeyTypedModelSearchSource<T>): KeyTypedModelSearchSource<T> {
    if (input instanceof KeyTypedModelSearchSource) {
      return input;
    } else {
      return new KeyTypedModelSearchSource<T>(input);
    }
  }

  constructor(input: TypedModelSearchService<T> | KeyTypedModelSearchSource<T>, callNextOnReset?: boolean) {
    super(AbstractConfigurableKeyTypedModelSearchSourceComponent.makeKeySearchSource(input), callNextOnReset);
  }

}
