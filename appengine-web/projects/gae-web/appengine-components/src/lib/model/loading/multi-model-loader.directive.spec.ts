import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild } from '@angular/core';
import { By } from '@angular/platform-browser';
import { ErrorInput, SingleElementConversionSource, ModelKey } from '@gae-web/appengine-utility';
import { GaeModelComponentsModule } from '../model.module';
import { GaeMultiModelLoaderDirective } from './multi-model-loader.directive';
import { TestFooReadSourceComponent } from '../resource/read.component.spec';
import { ModelLoaderState, GaeModelLoaderComponent, ModelLoaderFailedLoadingError } from './model-loader.component';
import { TestFoo } from '@gae-web/appengine-api';
import { filter } from 'rxjs/operators';

const TEST_MODEL_KEY = 1;

describe('GaeMultiModelLoaderDirective', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [GaeModelComponentsModule],
      declarations: [TestMultiModelLoaderComponent, TestFooReadSourceComponent]
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

  it('should be in a complete state when both values are loaded', (done) => {
    directive.stream.pipe(
      filter(x => x.state === ModelLoaderState.Data)
    ).subscribe(() => {
      expect(directive.state).toBe(ModelLoaderState.Data);
      expect(testComponent.aLoader.state).toBe(ModelLoaderState.Data);
      expect(testComponent.bLoader.state).toBe(ModelLoaderState.Data);
      done();
    });
  });

  describe('where one non-optional loader fails', () => {

    it('should be in a failed state', (done) => {
      expect(testComponent.aLoader.optional).toBe(false);

      testComponent.aSource.testFooReadService.filteredKeysSet.add(TEST_MODEL_KEY);
      testComponent.aSource.refresh();

      directive.stream.pipe(
        filter(x => x.state === ModelLoaderState.Failed)
      ).subscribe((x) => {
        expect(directive.state).toBe(ModelLoaderState.Failed);
        expect(testComponent.aLoader.state).toBe(ModelLoaderState.Failed);
        expect(testComponent.bLoader.state).toBe(ModelLoaderState.Data);
        done();
      });
    });

  });

});

@Component({
  template: `
  <div [gaeMultiModelLoader]="[a, b]">
    <gae-test-model-read-source #aSource [gaeReadSourceKey]="keyA"></gae-test-model-read-source>
    <gae-test-model-read-source #bSource [gaeReadSourceKey]="keyB"></gae-test-model-read-source>
    <gae-model-loader #a [optional]="false" [source]="aSource"></gae-model-loader>
    <gae-model-loader #b [source]="bSource"></gae-model-loader>
  </div>
  `
})
class TestMultiModelLoaderComponent {

  keyA: ModelKey = TEST_MODEL_KEY;
  keyB: ModelKey = TEST_MODEL_KEY;

  @ViewChild('aSource', { static: true })
  public aSource: TestFooReadSourceComponent;

  @ViewChild('bSource', { static: true })
  public bSource: TestFooReadSourceComponent;

  @ViewChild('a', { static: true })
  public aLoader: GaeModelLoaderComponent<TestFoo>;

  @ViewChild('b', { static: true })
  public bLoader: GaeModelLoaderComponent<TestFoo>;

  @ViewChild(GaeMultiModelLoaderDirective, { static: true })
  public directive: GaeMultiModelLoaderDirective;

}
