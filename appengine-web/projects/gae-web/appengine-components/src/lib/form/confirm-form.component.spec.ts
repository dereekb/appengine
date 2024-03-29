import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Component, ViewChild, Input } from '@angular/core';
import { By } from '@angular/platform-browser';
import { GaeConfirmModelFormComponent } from './confirm-form.component';
import { GaeFormComponentsModule } from './form.module';
import { Observable, of } from 'rxjs';
import { UniqueModel } from '@gae-web/appengine-utility';
import { GaeCheckboxFormControlComponent } from './input/checkbox.component';

describe('GaeConfirmModelFormComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeFormComponentsModule
      ],
      declarations: [TestConfirmFormViewComponent]
    }).compileComponents();
  }));

  let component: TestConfirmFormViewComponent;
  let formComponent: GaeConfirmModelFormComponent<UniqueModel>;
  let checkboxComponent: GaeCheckboxFormControlComponent;
  let fixture: ComponentFixture<TestConfirmFormViewComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestConfirmFormViewComponent);
    component = fixture.componentInstance;
    formComponent = component.formComponent;

    component.input = of({
      key: 0,
      modelKey: 0
    });

    checkboxComponent = fixture.debugElement.query(By.directive(GaeCheckboxFormControlComponent)).componentInstance;
    fixture.detectChanges();
  }));

  it('should have the model.', () => {
    expect(formComponent.hasModel).toBeTrue();
  });

  describe('before checked', () => {

    it('it should not be marked complete.', () => {
      expect(formComponent.isComplete).toBeFalse();
    });

  });

  describe('after checked', () => {

    beforeEach(() => {
      checkboxComponent.form.controls.confirm.setValue(true);
      fixture.detectChanges();
      formComponent.forceUpdateForChange();
    });

    it('it should be marked complete.', () => {
      expect(formComponent.isComplete).toBeTrue();
    });

  });

});

const HINT_TEXT = 'Confirmation is required.';

@Component({
  template: `
    <gae-confirm-model-form [hint]="hint" [input]="input"></gae-confirm-model-form>
  `
})
class TestConfirmFormViewComponent {

  @Input()
  public hint = HINT_TEXT;

  @Input()
  public input: Observable<UniqueModel>;

  @ViewChild(GaeConfirmModelFormComponent, {static: true})
  public formComponent: GaeConfirmModelFormComponent<UniqueModel>;

}
