import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Component, ViewChild, Input, Inject, forwardRef } from '@angular/core';
import { TestFooReadSourceComponent } from '../model/resource/read.component.spec';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AbstractListViewComponent, ProvideListViewComponent } from './list-view.component';
import { AbstractListContentComponent } from './list-content.component';
import { GaeListComponentsModule } from './list.module';
import { GaeSelectionListViewComponent } from './selection-list.component';
import { GaeSelectionListComponentsModule } from './selection.module';
import { GaeModelComponentsModule } from '../model/model.module';
import { GaeMaterialComponentsModule } from '../material/material.module';
import { TestFoo } from '@gae-web/appengine-api';

describe('GaeSelectionListViewComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeModelComponentsModule,
        GaeSelectionListComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [TestFooReadSourceComponent, TestViewComponent]
    }).compileComponents();
  }));

  let component: GaeSelectionListViewComponent<TestFoo>;
  let testComponent: TestViewComponent;
  let fixture: ComponentFixture<TestViewComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestViewComponent);
    testComponent = fixture.componentInstance;
    component = testComponent.component;
    fixture.detectChanges();
  }));

  it('should be created', () => {
    expect(component).toBeDefined();
  });

});

@Component({
  template: `
  <ng-container gaeSelectionListController>
    <gae-test-model-read-source></gae-test-model-read-source>
    <gae-selection-list-view gaeSelectionListViewNamedConversion></gae-selection-list-view>
  </ng-container>
  `
})
class TestViewComponent {

  @ViewChild(GaeSelectionListViewComponent, {static: false})
  public component: GaeSelectionListViewComponent<TestFoo>;

}
