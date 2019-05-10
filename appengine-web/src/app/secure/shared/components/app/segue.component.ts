import { Directive, Component } from '@angular/core';
import { AppSegueService } from '../../../segue.service';
import { ModelKey } from '@gae-web/appengine-utility/lib/model';
import { Foo } from '../../api/model/foo/foo';

/**
 * Directive that provides functions for performing segues in TallyNote.
 */
@Directive({
    selector: '[appSegue]',
    exportAs: 'appSegue'
})
export class AppSegueDirective {

    constructor(private _service: AppSegueService) {
        this._service = _service;
    }

    public segueToFoo(target: Foo | ModelKey) {
        return this._service.segueToFoo(target);
    }

}
