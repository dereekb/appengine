import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { GaeModelComponentsModule } from '../model.module';
import { GaeTestFooModelFormComponent } from '../../form/model.component.spec';
import { GaeFormComponentsModule } from '../../form/form.module';
import { TestFoo } from '@gae-web/appengine-api';
import { MatProgressButtonsModule } from 'mat-progress-buttons';
import { GaeSubmitButtonComponent } from './submit.component';

describe('GaeSubmitComponents', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeFormComponentsModule,
        GaeModelComponentsModule,
        MatProgressButtonsModule.forRoot(),
        NoopAnimationsModule
      ],
      declarations: [
        GaeTestFooModelFormComponent,
        TestViewComponent
      ]
    }).compileComponents();
  }));

  let component: GaeSubmitButtonComponent;
  let testComponent: TestViewComponent;
  let fixture: ComponentFixture<TestViewComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestViewComponent);
    testComponent = fixture.componentInstance;
    component = testComponent.submitButton;
    fixture.detectChanges();
  }));

  it('should be created', () => {
    expect(component).toBeDefined();
  });

});

@Component({
  template: `
    <gae-submit-button #submit></gae-submit-button>
  `
})
class TestViewComponent {

  @ViewChild(GaeSubmitButtonComponent, {static: true})
  public submitButton: GaeSubmitButtonComponent;

}
