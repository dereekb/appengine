import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild } from '@angular/core';
import { By } from '@angular/platform-browser';
import { ErrorInput, SingleElementConversionSource, ModelKey } from '@gae-web/appengine-utility';
import { GaeModelComponentsModule } from '../model.module';
import { GaeMultiModelLoaderDirective } from './multi-model-loader.directive';
import { TestReadSourceComponent } from '../resource/read.component.spec';

describe('GaeMultiModelLoaderDirective', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [GaeModelComponentsModule],
      declarations: [TestMultiModelLoaderComponent, TestReadSourceComponent]
    }).compileComponents();
  }));

  let directive: GaeMultiModelLoaderDirective;
  let testComponent: TestMultiModelLoaderComponent;
  let fixture: ComponentFixture<TestMultiModelLoaderComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestMultiModelLoaderComponent);
    testComponent = fixture.componentInstance;
    directive = testComponent.directive;
    fixture.detectChanges();
  }));

  it('should be created', () => {
    expect(directive).toBeDefined();
  });

  // TODO: Test loading states.

});

@Component({
  template: `
  <div [gaeMultiModelLoader]="[a, b]">
    <gae-test-read-source #aSource [gaeReadSourceKey]="keyA"></gae-test-read-source>
    <gae-test-read-source #bSource [gaeReadSourceKey]="keyB"></gae-test-read-source>
    <gae-model-loader #a [source]="aSource"></gae-model-loader>
    <gae-model-loader #b [source]="bSource"></gae-model-loader>
  </div>
  `
})
class TestMultiModelLoaderComponent {

  keyA: ModelKey = 1;
  keyB: ModelKey = 1;

  @ViewChild(GaeMultiModelLoaderDirective)
  public directive: GaeMultiModelLoaderDirective;

}
