import { AbstractIterableSourceComponent } from './source.component';
import { UniqueModel, ModelKey, IterableSource, WrappedIterableSource } from '@gae-web/appengine-utility';
import { Observable } from 'rxjs';
import { OnDestroy, Input } from '@angular/core';
import { QueryService, TypedModelSearchService } from '@gae-web/appengine-api';
import {
  KeyQuerySource, KeySearchSource, QuerySourceConfiguration,
  TypedModelSearchSourceConfiguration, SearchSourceConfiguration, KeyTypedModelSearchSource
} from '@gae-web/appengine-client';

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
