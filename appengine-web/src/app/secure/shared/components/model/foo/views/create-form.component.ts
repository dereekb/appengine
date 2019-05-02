import { Directive, Component, OnInit, OnDestroy, ViewEncapsulation } from '@angular/core';

@Component({
    selector: 'app-foo-create-view',
    templateUrl: './create-form.component.html'
})
export class FooCreateFormComponent {}

@Directive({
    selector: '[appFooSegueToCreatedView]'
})
export class FooSegueToCreatedViewDirective implements OnInit, OnDestroy {

    private _sub: Subscription;

    constructor(private controller: GaeCreateModelFormControllerDirective<Foo>, private _stateService: TallyNoteStateService) { }

    ngOnInit() {
        this._sub = this.controller.responseStream.subscribe((x) => {
            const tallyType = this.controller.firstResult;

            if (tallyType) {
                this._stateService.goToFooCreatedView(tallyType);
            }
        });
    }

    ngOnDestroy() {
        if (this._sub) {
            this._sub.unsubscribe();
            delete this._sub;
        }
    }

}
