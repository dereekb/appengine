import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild } from '@angular/core';
import { By } from '@angular/platform-browser';
import { ErrorInput, SingleElementConversionSource, ModelKey } from '@gae-web/appengine-utility';
import { GaeModelComponentsModule } from '../model.module';
import { TestModelReadSourceComponent } from '../resource/read.component.spec';
import { GaeModelLoaderComponent } from './model-loader.component';

describe('GaeModelLoaderComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [GaeModelComponentsModule],
      declarations: [TestGaeModelLoaderComponent, TestModelReadSourceComponent]
    }).compileComponents();
  }));

  let component: GaeModelLoaderComponent<any>;
  let testComponent: TestGaeModelLoaderComponent;
  let fixture: ComponentFixture<TestGaeModelLoaderComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestGaeModelLoaderComponent);
    testComponent = fixture.componentInstance;
    component = testComponent.component;
    fixture.detectChanges();
  }));

  it('should be created', () => {
    expect(component).toBeDefined();
  });

  // TODO: Test loading states.

});

@Component({
  template: `
  <div>
    <gae-test-model-read-source #aSource [gaeReadSourceKey]="keyA"></gae-test-model-read-source>
    <gae-model-loader #a [source]="aSource"></gae-model-loader>
  </div>
  `
})
class TestGaeModelLoaderComponent {

  keyA: ModelKey = 1;

  @ViewChild(GaeModelLoaderComponent)
  public component: GaeModelLoaderComponent<any>;

}
