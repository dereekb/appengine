import { Directive, Input, Output, AfterViewInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { SelectionListControllerDirective } from './selection-list.component';
import { ModelKey } from '@gae-web/appengine-utility';
import { SelectionChange } from '@angular/cdk/collections';
import { SingleElementSource } from '@gae-web/appengine-utility';

/**
 * Abstract action delegate that wraps a controller.
 */
export abstract class AbstractSelectionListControllerActionController implements AfterViewInit, OnDestroy {

    private _hasKeyChange: boolean;
    private _keySub: Subscription;

    constructor(protected readonly controller: SelectionListControllerDirective) { }

    ngAfterViewInit() {
        this.watchKeyChange();
    }

    ngOnDestroy() {
        this._clearKeysSub();
    }

    public get hasKeyChange() {
        return this._hasKeyChange;
    }

    // MARK: Internal Functions
    protected setInitialKeys(keys: ModelKey[]) {
        this._hasKeyChange = false;
        this.controller.setInitialModelKeys(keys);
    }

    protected watchKeyChange() {
        if (!this._keySub) {
            this._keySub = this.controller.onSelectionChange.subscribe((x) => this.updateForKeysChange(x));
        }
    }

    protected updateForKeysChange(change: SelectionChange<string>) {
        this._hasKeyChange = true;
        // Override to do something.
    }

    protected _clearKeysSub() {
        if (this._keySub) {
            this._keySub.unsubscribe();
            delete this._keySub;
        }
    }

}

/**
 * AbstractSelectionListControllerActionDelegate extension that watches an element source.
 */
export abstract class AbstractObjectSelectionListControllerActionController<T> extends AbstractSelectionListControllerActionController implements OnDestroy {

    private _element?: T;

    private _elementSource: SingleElementSource<T>;
    private _elementSourceSub: Subscription;

    constructor(controller: SelectionListControllerDirective) {
        super(controller);
    }

    ngOnDestroy() {
        super.ngOnDestroy();
        this._clearElementSource();
    }

    protected get element() {
        return this._element;
    }

    protected get hasElement() {
        return Boolean(this._element);
    }

    protected setElementSource(source: SingleElementSource<T> | undefined) {
        this._clearElementSource();

        if (source) {
            this._elementSource = source;
            this._elementSourceSub = this._elementSource.first.subscribe((element) => {
                if (element) {
                    this._updateForElement(element);
                } else {
                    this._updateForNoElement();
                }
            });
        } else {
            this._updateForNoElement();
        }
    }

    protected _updateForElement(element: T) {
        this._element = element;
    }

    protected _updateForNoElement() {
        // Set initial keys to unselected by default.
        this.setInitialKeys([]);
    }

    protected _clearElementSource() {
        if (this._elementSourceSub) {
            this._elementSourceSub.unsubscribe();
            delete this._elementSourceSub;
        }
    }

}
