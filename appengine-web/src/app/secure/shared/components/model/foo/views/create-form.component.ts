import { Directive, Component, OnInit, OnDestroy, ViewEncapsulation, Input, ViewChild } from '@angular/core';
import { GaeCreateModelFormControllerDirective, GaeSubmitViewComponent } from '@gae-web/appengine-components';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';
import { SubscriptionObject } from '@gae-web/appengine-utility';
import { AppSegueService } from 'src/app/secure/segue.service';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
    selector: 'app-foo-create-view',
    templateUrl: './create-form.component.html'
})
export class FooCreateFormComponent {}

@Directive({
    selector: '[appFooSegueToCreatedView]'
})
export class FooSegueToCreatedViewDirective implements OnInit, OnDestroy {

    private _sub = new SubscriptionObject();

    constructor(private controller: GaeCreateModelFormControllerDirective<Foo>, private _segueService: AppSegueService) { }

    ngOnInit() {
        this._sub.subscription = this.controller.responseStream.subscribe((x) => {
            const foo = this.controller.firstResult;

            if (foo) {
                // TODO: Segue to created object.
            }
        });
    }

    ngOnDestroy() {
        this._sub.destroy();
    }

}
