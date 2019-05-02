import { Directive } from '@angular/core';
import { AbstractTemplateModelFormControllerDirective } from './template.directive';
import { UniqueModel } from '@gae-web/appengine-utility';
import { CreateResponse } from '@gae-web/appengine-api';
import { AbstractFormActionObject } from './form-submit.controller';

/**
 * Directive for facilitating a create action between a form and an action.
 */
@Directive({
    selector: '[gaeCreateModelFormController]',
    exportAs: 'gaeCreateModelFormController'
})
export class GaeCreateModelFormControllerDirective<T extends UniqueModel> extends AbstractTemplateModelFormControllerDirective<CreateResponse<T>, T> {}

/**
 * Abstract form component.
 */
export abstract class AbstractCreateModelFormComponent<T extends UniqueModel> extends AbstractFormActionObject<T> {

    public get controller(): GaeCreateModelFormControllerDirective<T> {
        return this._controller as GaeCreateModelFormControllerDirective<T>;
    }

}
