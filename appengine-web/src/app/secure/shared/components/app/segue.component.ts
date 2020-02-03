import { Directive, Component } from '@angular/core';
import { AppSegueService } from '../../../segue.service';
import { ModelKey, ModelOrKey } from '@gae-web/appengine-utility';
import { Foo } from '../../api/model/foo/foo';

/**
 * Directive that provides functions for performing segues in the demo app.
 */
@Directive({
    selector: '[appSegue]',
    exportAs: 'appSegue'
})
export class AppSegueDirective {

    constructor(private _service: AppSegueService) {
        this._service = _service;
    }

    public segueToFoo(target: ModelOrKey<Foo>) {
        return this._service.segueToFoo(target);
    }

}
