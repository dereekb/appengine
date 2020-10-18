import { Directive, Input, Output, Type, Provider, Host, Optional, AfterViewInit } from '@angular/core';
import { SourceComponent, ProvideSourceComponent, AbstractConversionSourceComponent } from './source.component';
import { Observable, of } from 'rxjs';
import { ModelKey, UniqueModel, SingleElementConversionSource, SingleElementSource } from '@gae-web/appengine-utility';
import { ReadSourceFactory, ReadSource } from '@gae-web/appengine-client';
import { map, share, shareReplay } from 'rxjs/operators';

export abstract class ReadSourceComponent<T extends UniqueModel> extends SourceComponent<T> {

  public readonly readSource: ReadSource<T>;
  public abstract set readSourceKeys(keysObs: Observable<ModelKey | ModelKey[]> | undefined);

}

export function ProvideReadSourceComponent<S extends ReadSourceComponent<any>>(sourceType: Type<S>): Provider[] {
  return [...ProvideSourceComponent(sourceType), { provide: ReadSourceComponent, useExisting: sourceType }];
}

// TODO: Change to be a Directive instead of a Component?
/**
 * Abstract read component that takes in an observable list of keys and retrieves models for those keys.
 *
 * Subscribes to the input keys, and outputs the loaded elements using a BehaviorSubject.
 *
 * Wraps a unique ReadSource.
 */
@Directive()
export abstract class AbstractReadSourceComponent<T extends UniqueModel> extends AbstractConversionSourceComponent<ModelKey, T>
  implements ReadSourceComponent<T>, SingleElementConversionSource<ModelKey, T> {

  constructor(factory: ReadSourceFactory<T>) {
    super(factory.makeSource());
  }

  get readSource(): ReadSource<T> {
    return this.source;
  }

  protected get source() {
    return this._source as ReadSource<T>;
  }

  protected makeFirstObs() {
    return this.source.first;
  }

  protected makeElementsObs() {
    return this.source.elements;
  }

  // MARK: Accessors
  get atomic() {
    return this.source.config.atomic;
  }

  @Input()
  set atomic(atomic) {
    this.source.config = {
      atomic
    };
  }

  // TODO: Move to AbstractConversionSourceComponent or remove entirely.
  get failed(): Observable<ModelKey[]> {
    return this.source.failedStream;
  }

  get readSourceKeys(): Observable<ModelKey | ModelKey[]> {
    return this.source.input;
  }

  @Input()
  set readSourceKeys(input: Observable<ModelKey | ModelKey[]> | undefined) {
    this.setSourceInput(input);
  }

}

/**
 * Directive for a read source that sets a single key as the source input.
 */
@Directive({
  selector: '[gaeReadSourceKey]'
})
export class GaeReadSourceKeyDirective<T extends UniqueModel> {

  private _source: ReadSourceComponent<T>;
  private _key?: ModelKey;

  constructor(@Optional() @Host() source: ReadSourceComponent<T>) {
    this._source = source;
  }

  get source() {
    return this._source;
  }

  @Input()
  set source(source) {
    this._source = source;
    this._update();
  }

  @Input()
  set gaeReadSourceKey(key: ModelKey) {
    this.key = key;
  }

  @Input()
  get key() {
    return this._key;
  }

  set key(key: ModelKey) {
    this._key = key;
    this._update();
  }

  private _update() {
    if (!this._source) {
      return;
    }

    const keys: ModelKey[] = [];

    if (this._key) {
      keys.push(this._key);
    }

    this._source.readSourceKeys = of(keys);
  }

}

/**
 * Component that takes in a SingleElementSource and retrieves the keys from that source and applies them to another read source.
 */
export abstract class AbstractSingleElementSourceKeysProvider<T extends UniqueModel> {

  constructor(private _source: AbstractReadSourceComponent<UniqueModel>) { }

  protected setSingleElementSource(source: SingleElementSource<T>) {
    if (source) {
      this._source.readSourceKeys = source.first.pipe(
        map((x) => ((x) ? this.mapToKeys(x) || [] : [])),
        shareReplay(1)
      );
    } else {
      this._source.readSourceKeys = undefined;
    }
  }

  protected abstract mapToKeys(x: T): ModelKey[];

}
