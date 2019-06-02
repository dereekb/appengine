import { Observable, Subscription } from 'rxjs';
import { SelectionModel, SelectionChange } from '@angular/cdk/collections';
import { ModelKey, UniqueModel, NamedUniqueModel, ArrayDelta, ValueUtility, ModelUtility } from '@gae-web/appengine-utility';
import { Directive, Component, ViewEncapsulation, forwardRef, Input, AfterViewInit, OnDestroy, ViewChild, QueryList, ChangeDetectorRef } from '@angular/core';
import { ListViewSource, ConversionListViewSource } from './source';
import { ProvideListViewComponent, AbstractListViewComponent } from './list-view.component';
import { AbstractListContentComponent } from './list-content.component';
import { MatListOption, MatSelectionList } from '@angular/material/list';
import { delay } from 'rxjs/operators';

export interface KeyedSelectionListItem<K, T> {
  readonly key: K;
  readonly value: T;
  readonly name: string;
  readonly icon: string;
}

export abstract class KeySelection<K> {

  constructor(protected readonly _selection = new SelectionModel<K>(true), private _initialKeys: K[] = []) {
    if (this._initialKeys.length) {
      this._updateForInitialKeys();
    }
  }

  // MARK: KeySelection
  public get selected(): K[] {
    return this._selection.selected;
  }

  public get onSelectionChange(): Observable<SelectionChange<K>> {
    return this._selection.onChange.asObservable() as Observable<SelectionChange<K>>;
  }

  public selectKey(key: K) {
    this._selection.select(key);
  }

  public deselectKey(key: K) {
    this._selection.deselect(key);
  }

  public hasKeySelected(key: K) {
    return this._selection.isSelected(key);
  }

  // MARK: Keys
  public clearSelectedKeys() {
    this._selection.clear();
  }

  public setSelectedKeys(keys: K[]) {
    this.clearSelectedKeys();
    keys.forEach((key) => this.selectKey(key));
  }

  public setInitialKeys(keys: K[]) {
    this._initialKeys = keys;
    this._updateForInitialKeys();
  }

  public getDelta(): ArrayDelta<K> {
    if (this._initialKeys.length) {
      return ValueUtility.arrayDelta(this._initialKeys, this.selected);
    } else {
      return {
        added: this._selection.selected,
        removed: [],
        kept: []
      };
    }
  }

  private _updateForInitialKeys() {
    // console.log('Updating for initial keys: ' + this._initialKeys);
    this.setSelectedKeys(this._initialKeys);
  }

}

@Directive({
  selector: '[gaeSelectionListController]',
  exportAs: 'gaeSelectionListController'
})
export class GaeSelectionListControllerDirective extends KeySelection<string> {

  constructor() {
    super();
  }

  public setInitialModelKeys(keys: ModelKey[]) {
    this.setInitialKeys(ModelUtility.modelKeysAsStrings(keys));
  }

}

export type SelectionListItem<T> = KeyedSelectionListItem<string, T>;

@Component({
  selector: 'gae-selection-list-view',
  templateUrl: './selection-list.component.html',
  providers: [ProvideListViewComponent(GaeSelectionListViewComponent)]
})
export class GaeSelectionListViewComponent<T> extends AbstractListViewComponent<SelectionListItem<T>> {

  constructor(public readonly listController: GaeSelectionListControllerDirective, cdRef: ChangeDetectorRef) {
    super(cdRef);
  }

}

export abstract class SelectionListViewSourceDirectiveConversionDelegate<T extends UniqueModel> {
  readonly selectionListConvertFn: (i: T[]) => SelectionListItem<T>[];
}

@Directive({
  selector: '[gaeSelectionListViewNamedConversion]',
  providers: [{ provide: SelectionListViewSourceDirectiveConversionDelegate, useExisting: forwardRef(() => GaeSelectionListViewNamedConversionDirective) }]
})
export class GaeSelectionListViewNamedConversionDirective<T extends NamedUniqueModel> extends SelectionListViewSourceDirectiveConversionDelegate<T> {

  @Input()
  public namedItemIcons: string;

  public readonly selectionListConvertFn = ((i) => this._convert(i));

  private _convert(i: T[]): SelectionListItem<T>[] {
    return i.map((x) => {
      return {
        key: String(x.key),
        name: x.uniqueModelName,
        value: x,
        icon: this.namedItemIcons
      };
    });
  }

}

/**
 * Special source for GaeSelectionListViewComponent that wraps another source and converts the input models to SelectionListItem values.
 */
@Directive({
  selector: '[gaeSelectionListViewSource]'
})
export class GaeSelectionListViewSourceDirective<T extends UniqueModel> {

  constructor(private _listView: GaeSelectionListViewComponent<T>, private _delegate: SelectionListViewSourceDirectiveConversionDelegate<T>) { }

  @Input()
  public set gaeSelectionListViewSource(source: ListViewSource<T>) {
    this._listView.source = new ConversionListViewSource(source, this._delegate.selectionListConvertFn);
  }

}

@Component({
  selector: 'gae-selection-list-content',
  templateUrl: './selection-content.component.html'
})
export class GaeSelectionListContentComponent<T> extends AbstractListContentComponent<SelectionListItem<T>> implements AfterViewInit, OnDestroy {

  private _optionsMap = new Map<string, MatListOption>();

  private _optionsSub: Subscription;
  private _changeSub: Subscription;

  private _selection: KeySelection<string>;
  private _selectionSub: Subscription;

  @ViewChild(MatSelectionList, {static: false})
  public readonly selectionList: MatSelectionList;

  constructor(listView: GaeSelectionListViewComponent<T>) {
    super(listView);
  }

  ngAfterViewInit() {

    // Delay here to wait for view to update first.
    this._optionsSub = this.selectionList.options.changes.pipe(
      delay(0)
    ).subscribe((queryList: QueryList<MatListOption>) => {
      this._updateOptionsMap(queryList);
    });

    this._changeSub = this.selectionList.selectedOptions.onChange.subscribe((x) => {
      this._selectOptions(x.added);
      this._deselectOptions(x.removed);
    });

    this.keySelection = this.listView.listController;
  }

  ngOnDestroy() {
    this._optionsSub.unsubscribe();
    this._changeSub.unsubscribe();
  }

  // MARK: Accessors
  protected get listView() {
    return this._listView as GaeSelectionListViewComponent<T>;
  }

  public get keySelection() {
    return this._selection;
  }

  public set keySelection(keySelection: KeySelection<string>) {
    this._selection = keySelection;

    // TODO: Consider watching and consolidating the selection changes into a single buffered result.
    this._selectionSub = keySelection.onSelectionChange.subscribe((change) => {
      this._updateForSelectedKeys(change.added);
      this._updateForDeselectedKeys(change.removed);
    });
  }

  private _clearSelectionSub() {
    if (this._selectionSub) {
      this._selectionSub.unsubscribe();
      delete this._selectionSub;
    }
  }

  // MARK: Selections
  public destroyedOption(option: MatListOption) {
    this._deleteOption(option);
  }

  // MARK: Options Map
  private _updateOptionsMap(queryList: QueryList<MatListOption>) {
    // console.log('Updating options map.');
    queryList.forEach((option: MatListOption) => {
      this._initOption(option);
    });
  }

  private _initOption(option: MatListOption) {
    const value = option.value as SelectionListItem<T>;
    const key = value.key;

    if (this.keySelection) {
      const keySelected = this.keySelection.hasKeySelected(key);
      this._setOptionSelected(option, keySelected);
    }

    this._optionsMap.set(key, option);
  }

  private _selectOptions(options: MatListOption[]) {
    // console.log('Selected options: ' + options);
    options.forEach((x) => this.keySelection.selectKey(x.value.key));
  }

  private _deselectOptions(options: MatListOption[]) {
    // console.log('Deselected options: ' + options);
    options.forEach((x) => this.keySelection.deselectKey(x.value.key));
  }

  public _updateForSelectedKeys(keys: string[]) {
    // console.log('Saw selected keys: ' + keys);
    keys.forEach((key) => {
      const option = this._optionsMap.get(key);

      if (option) {
        this._setOptionSelected(option, true);
      }
    });
  }

  public _updateForDeselectedKeys(keys: string[]) {
    // console.log('Saw deselected keys: ' + keys);
    keys.forEach((key) => {
      const option = this._optionsMap.get(key);
      // console.log('Tried getting option: ' + option);

      if (option) {
        this._setOptionSelected(option, false);
      }
    });
  }

  private _setOptionSelected(option: MatListOption, selected: boolean) {
    if (option.selected !== selected) {
      option.toggle();
    }
  }

  private _deleteOption(option: MatListOption) {
    // TODO: Ignore this for now and implement later if it needed.
    // As the options are destroyed before this, this will be called during destruction.
  }

}
