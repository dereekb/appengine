import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild } from '@angular/core';
import { By } from '@angular/platform-browser';
import { ModelKey } from '@gae-web/appengine-utility';
import { GaeModelComponentsModule } from '../model.module';
import { TestFooReadSourceComponent } from '../resource/read.component.spec';
import { GaeModelLoadingViewComponent } from './loading.component';

describe('GaeModelLoadingViewComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [GaeModelComponentsModule],
      declarations: [TestGaeModelLoadingViewComponent, TestFooReadSourceComponent]
    }).compileComponents();
  }));

  let component: GaeModelLoadingViewComponent;
  let testComponent: TestGaeModelLoadingViewComponent;
  let fixture: ComponentFixture<TestGaeModelLoadingViewComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestGaeModelLoadingViewComponent);
    testComponent = fixture.componentInstance;
    component = testComponent.component;
    fixture.detectChanges();
  }));

  it('should be created', () => {
    expect(component).toBeDefined();
  });

  // TODO: Test custom content.

  // TODO: Test loading states.

});

@Component({
  template: `
  <div>
    <gae-test-model-read-source #aSource [gaeReadSourceKey]="keyA"></gae-test-model-read-source>
    <gae-model-loader #a [source]="aSource"></gae-model-loader>
    <gae-model-loading-view [loader]="a">
      <div content>
        <p>Loaded!</p>
      </div>
    </gae-model-loading-view>
  </div>
  `
})
class TestGaeModelLoadingViewComponent {

  keyA: ModelKey = 1;

  @ViewChild(GaeModelLoadingViewComponent, {static: false})
  public component: GaeModelLoadingViewComponent;

}
