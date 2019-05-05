import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Component, ViewChild, Input, Inject, forwardRef } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';
import { UniqueModel } from '@gae-web/appengine-utility';
import { TestModel } from '../model/resource/read.component.spec';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AbstractListViewComponent, ProvideListViewComponent } from './list-view.component';
import { AbstractListContentComponent } from './list-content.component';
import { GaeListComponentsModule } from './list.module';

describe('ListViewComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeListComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [GaeTestModelListComponent, GaeTestModelListContentComponent, TestViewComponent]
    }).compileComponents();
  }));

  let component: GaeTestModelListComponent;
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
  selector: 'gae-test-model-list-content',
  template: `
    <div>TODO</div>
  `
})
export class GaeTestModelListContentComponent extends AbstractListContentComponent<TestModel> {

  constructor(@Inject(forwardRef(() => GaeTestModelListComponent)) listView: GaeTestModelListComponent) {
    super(listView);
  }

}

@Component({
  selector: 'gae-test-model-list',
  template: `
  <gae-list-view-wrapper>
    <gae-test-model-list-content></gae-test-model-list-content>
  </gae-list-view-wrapper>
  `,
  providers: [ProvideListViewComponent(GaeTestModelListComponent)]
})
export class GaeTestModelListComponent extends AbstractListViewComponent<TestModel> {

  @ViewChild(GaeTestModelListContentComponent)
  public component: GaeTestModelListContentComponent;

}

@Component({
  template: `
    <gae-test-model-list></gae-test-model-list>
  `
})
class TestViewComponent {

  @ViewChild(GaeTestModelListComponent)
  public component: GaeTestModelListComponent;

}
