import { Observable, BehaviorSubject, Subscription } from 'rxjs';
import { map } from 'rxjs/operators';
import { OnDestroy, Component, Input, Directive, Inject, AfterViewInit, Host, Optional } from '@angular/core';
import { Source, SourceState, ControllableSource, UniqueModel } from '@gae-web/appengine-utility';
import { ReadSource, KeyQuerySource, MergedReadQuerySource } from '@gae-web/appengine-client';
import { ListViewSourceEvent, ListViewSource, ListViewSourceState, AbstractListViewSource } from './source';
import { ListViewComponent } from './list-view.component';

/**
 * Abstract source directive that auto-binds to a ListViewComponent.
 */
export abstract class AbstractListViewSourceDirective<T> extends AbstractListViewSource<T> implements AfterViewInit {

  constructor(@Host() @Optional() @Inject(ListViewComponent) private readonly listViewComponent: ListViewComponent<T>) {
    super();
  }

  ngAfterViewInit() {
    if (this.listViewComponent) {
      this.listViewComponent.source = this;
    }
  }

}

/**
 * Implementation for a read source.
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
 * Implementation for a query source.
 */
@Directive({
  selector: '[gaeListViewKeyQuerySource]',
  exportAs: 'gaeListViewKeyQuerySource'
})
export class GaeListViewKeyQuerySourceDirective<T extends UniqueModel> extends AbstractListViewSourceDirective<T> implements ListViewSource<T> {

  private _source: ControllableSource<T>;

  private _readSource?: ReadSource<T>;
  private _querySource?: KeyQuerySource<T>;

  @Input()
  public set readSource(source: ReadSource<T> | undefined) {
    this._readSource = source;
    this._update();
  }

  @Input()
  public set querySource(source: KeyQuerySource<T> | undefined) {
    this._querySource = source;
    this._update();
  }

  // MARK: Update
  private _update() {
    if (this._readSource && this._querySource) {
      this._source = new MergedReadQuerySource<T>(this._readSource, this._querySource);
      super.setSource(this._source);
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
