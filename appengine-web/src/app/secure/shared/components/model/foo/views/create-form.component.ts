import { Directive, Component, OnInit, OnDestroy, ViewEncapsulation } from '@angular/core';
import { Subscription } from 'rxjs';
import { GaeCreateModelFormControllerDirective } from '@gae-web/appengine-components';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';
import { SubscriptionObject } from '@gae-web/appengine-utility';

@Component({
    selector: 'app-foo-create-view',
    templateUrl: './create-form.component.html'
})
export class FooCreateFormComponent { }

@Directive({
    selector: '[appFooSegueToCreatedView]'
})
export class FooSegueToCreatedViewDirective implements OnInit, OnDestroy {

    private _sub = new SubscriptionObject();

    constructor(private controller: GaeCreateModelFormControllerDirective<Foo>, private _stateService: TallyNoteStateService) { }

    ngOnInit() {
        this._sub.subscription = this.controller.responseStream.subscribe((x) => {
            const tallyType = this.controller.firstResult;

            if (tallyType) {
                this._stateService.goToFooCreatedView(tallyType);
            }
        });
    }

    ngOnDestroy() {
        this._sub.destroy();
    }

}
