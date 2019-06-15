import { Input, Directive, Inject, AfterViewInit, Host, Optional } from '@angular/core';
import { ControllableSource, UniqueModel, SourceEvent, SourceState, ModelKey, IterableSource } from '@gae-web/appengine-utility';
import { ReadSource, KeyQuerySource, MergedReadQuerySource, QueryIterableSource } from '@gae-web/appengine-client';
import { ListViewSourceEvent, ListViewSource, ListViewSourceState, AbstractListViewSource } from './source';
import { ListViewComponent } from './list-view.component';
import { Observable, combineLatest } from 'rxjs';
import { map } from 'rxjs/operators';
import { ReadSourceComponent } from '../model/resource/read.component';
import { IterableSourceComponent } from '../model/resource/source.component';

/**
 * Abstract source directive that auto-binds to a ListViewComponent.
 */
export abstract class AbstractListViewSourceDirective<T> extends AbstractListViewSource<T> implements AfterViewInit {

  constructor(@Host() @Optional() @Inject(ListViewComponent) private readonly listViewComponent?: ListViewComponent<T>) {
    super();
  }

  ngAfterViewInit() {
    if (this.listViewComponent) {
      this.listViewComponent.source = this;
    }
  }

}

/**
 * ListViewSource implementation that uses a ReadSource.
 */
@Directive({
  selector: '[gaeListViewReadSource]',
  exportAs: 'gaeListViewReadSource'
})
export class GaeListViewReadSourceDirective<T extends UniqueModel> extends AbstractListViewSourceDirective<T> implements ListViewSource<T> {

  private _source?: ReadSource<T>;

  @Input()
  public set readSource(source: ReadSource<T> | undefined) {
    this._source = source;
    super.setSource(this._source);
  }

  // MARK: ListViewSource
  public more(): void {
    // Do nothing.
  }

  public refresh(): void {
    // Do nothing.
  }

}

/**
 * ListViewSource implementation that uses a MergedReadQuerySource
 * to automatically bind the query and read source together, and act as the ListViewSource.
 */
@Directive({
  selector: '[gaeListViewKeyQuerySource]',
  exportAs: 'gaeListViewKeyQuerySource'
})
export class GaeListViewKeyQuerySourceDirective<T extends UniqueModel> extends AbstractListViewSourceDirective<T> implements ListViewSource<T>, AfterViewInit {

  private _initialized = false;

  private _source: ControllableSource<T>;

  private _readSource?: ReadSource<T>;
  private _querySource?: IterableSource<ModelKey>;

  @Input()
  public autoStartQuery = true;

  @Input()
  public set readComponent(component: ReadSourceComponent<T> | undefined) {
    if (component) {
      this.readSource = component.readSource;
    }
  }

  @Input()
  public set queryComponent(component: IterableSourceComponent<ModelKey> | undefined) {
    this.querySource = component;
  }

  @Input()
  public set readSource(source: ReadSource<T> | undefined) {
    this._readSource = source;
    this._update();
  }

  @Input()
  public set querySource(source: IterableSource<ModelKey> | undefined) {
    this._querySource = source;
    this._update();
  }

  ngAfterViewInit() {
    super.ngAfterViewInit();
    this._initialized = true;
    this._resetForUpdate();
  }

  // MARK: Update
  private _update() {
    if (this._readSource && this._querySource) {
      this._source = new MergedReadQuerySource<T>(this._readSource, this._querySource);
      super.setSource(this._source);
      this._resetForUpdate();
    }
  }

  private _resetForUpdate() {
    if (this._initialized && this.autoStartQuery && this._source && this._querySource.state === SourceState.Reset) {
      // Automatically reset to pull first results.
      this.more();
    }
  }

  // MARK: ListViewSource
  public more(): void {
    if (this._source) {
      this._source.next();
    }
  }

  public refresh(): void {
    if (this._source) {
      this._source.reset();
    }
  }

}
