import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input, DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';
import { ModelKey, SingleElementReadSource, SourceState } from '@gae-web/appengine-utility';
import { GaeModelComponentsModule } from '../model.module';
import { TestFooReadSourceComponent } from '../resource/read.component.spec';
import { GaeModelLoadingViewComponent } from './loading.component';
import { TestFoo } from '@gae-web/appengine-api';
import { ModelLoader } from './model-loader.component';
import { of } from 'rxjs';
import { filter, delay } from 'rxjs/operators';

describe('GaeModelLoadingViewComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [GaeModelComponentsModule],
      declarations: [TestViewComponent, TestFooReadSourceComponent]
    }).compileComponents();
  }));

  let component: GaeModelLoadingViewComponent;
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

  function assertShowsContent() {
    const testContent: HTMLElement = fixture.debugElement.query(By.css(`#${TEST_CONTENT_ID}`)).nativeElement;
    expect(testContent).not.toBeNull();
    expect(testContent.innerText).toBe(TEST_CONTENT);
  }

  function assertDoesNotShowContent() {
    const testContent: DebugElement = fixture.debugElement.query(By.css(`#${TEST_CONTENT_ID}`));
    expect(testContent).toBeNull();
  }

  function assertShowsLoadingContent() {
    const testContent: HTMLElement = fixture.debugElement.query(By.css(`#${TEST_LOADING_CONTENT_ID}`)).nativeElement;
    expect(testContent).not.toBeNull();
    expect(testContent.innerText).toBe(TEST_LOADING_CONTENT);
  }

  function assertDoesNotShowLoadingContent() {
    const testContent: DebugElement = fixture.debugElement.query(By.css(`#${TEST_LOADING_CONTENT_ID}`));
    expect(testContent).toBeNull();
  }

  function assertShowsErrorContent() {
    const testContent: HTMLElement = fixture.debugElement.query(By.css(`#${TEST_ERROR_CONTENT_ID}`)).nativeElement;
    expect(testContent).not.toBeNull();
    expect(testContent.innerText).toBe(TEST_ERROR_CONTENT);
  }

  describe('with source', () => {

    let source: TestFooReadSourceComponent;

    beforeEach(() => {
      source = new TestFooReadSourceComponent();
      testComponent.source = source;
      fixture.detectChanges();
    });

    describe('while loading', () => {

      beforeEach(() => {
        source.testFooReadService.loadingTime = 5000;
        source.readSourceKeys = of([1]).pipe(delay(10000));
      });

      it('should show the loading', (done) => {
        expect(source.isLoading).toBeTrue();
        expect(component.context.isLoading).toBe(true);

        source.stream.pipe(
          filter((x) => {
            return x.state === SourceState.Loading;
          })
        ).subscribe(() => {
          assertShowsLoadingContent();
          done();
        });
      });

    });

    describe('with elements', () => {

      beforeEach(() => {
        source.readSourceKeys = of([1]);
      });

      it('should show the content', (done) => {
        assertShowsContent();
        done();
      });

    });

    describe('with error', () => {

    });

  });

  describe('with model loader', () => {

    it('', () => {

    });

  });

  // TODO: Test custom content.

  // TODO: Test loading states.

});

const TEST_CONTENT_ID = 'test-content';
const TEST_CONTENT = 'CONTENT';

const TEST_LOADING_CONTENT_ID = 'test-loading';
const TEST_LOADING_CONTENT = 'LOADING';

const TEST_ERROR_CONTENT_ID = 'test-error';
const TEST_ERROR_CONTENT = 'ERROR';

@Component({
  template: `
  <div>
    <gae-model-loading-view [loader]="loader" [source]="source">
      <div content>
        <p id="${ TEST_CONTENT_ID}">${TEST_CONTENT}</p>
      </div>
      <div loading>
        <p id="${ TEST_LOADING_CONTENT_ID}">${TEST_LOADING_CONTENT}</p>
      </div>
      <div error>
        <p id="${ TEST_ERROR_CONTENT_ID}">${TEST_ERROR_CONTENT}</p>
      </div>
    </gae-model-loading-view>
  </div>
  `
})
class TestViewComponent {

  @Input()
  public source: SingleElementReadSource<TestFoo>;

  @Input()
  public loader: ModelLoader<TestFoo>;

  @ViewChild(GaeModelLoadingViewComponent, { static: true })
  public component: GaeModelLoadingViewComponent;

}
