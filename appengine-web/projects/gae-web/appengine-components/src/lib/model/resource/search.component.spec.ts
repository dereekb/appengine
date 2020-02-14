import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, Input, DebugElement, ViewChild, Optional } from '@angular/core';
import { GaeModelComponentsModule } from '../model.module';
import { KeyQuerySource, TestFooTestKeyQuerySource, QuerySourceConfiguration } from '@gae-web/appengine-client';
import { TestFoo, TestFooReadService, SearchParameters } from '@gae-web/appengine-api';
import { Observable, of, BehaviorSubject, Subject } from 'rxjs';
import { ModelUtility, ModelKey, ValueUtility, SourceState, NamedUniqueModel } from '@gae-web/appengine-utility';
import { By } from '@angular/platform-browser';
import { AbstractConfigurableKeyQuerySourceComponent, ProvideConfigurableKeySearchSourceComponent, GaeKeyQuerySourceFilterDirective } from './search.component';
import { ProvideIterableSourceComponent } from './source.component';
import { filter } from 'rxjs/operators';

describe('Search Components', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [GaeModelComponentsModule],
      declarations: [TestViewComponent, TestFooKeyQuerySourceComponent]
    }).compileComponents();
  }));

  describe('GaeReadSourceKeyDirective', () => {
    let fixture: ComponentFixture<TestViewComponent>;
    let component: TestViewComponent;

    let testReadSourceComponentComponent: TestFooKeyQuerySourceComponent;
    let querySourceComponent: TestFooKeyQuerySourceComponent;
    let querySourceFilterDirective: GaeKeyQuerySourceFilterDirective<any>;

    let testQuerySource: TestFooTestKeyQuerySource;

    beforeEach(async(() => {
      fixture = TestBed.createComponent(TestViewComponent);
      component = fixture.componentInstance;

      const testReadSourceComponentElement: DebugElement = fixture.debugElement.query(By.directive(TestFooKeyQuerySourceComponent));
      testReadSourceComponentComponent = testReadSourceComponentElement.componentInstance;

      querySourceComponent = component.querySourceComponent;
      querySourceFilterDirective = component.querySourceFilterDirective;

      testQuerySource = querySourceComponent.testFooTestKeyQuerySource;
      testQuerySource.testQueryService.keyResults = [1, 2, 3, 4, 5];

      fixture.detectChanges();
    }));

    describe('gaeKeyQuerySourceFilter', () => {

      describe('with base configuration', () => {
        let baseConfig;

        beforeEach(() => {
          component.baseConfig = baseConfig = {};
          fixture.detectChanges();
        });

        it('should have the base configuration set', () => {
          expect(querySourceFilterDirective.baseConfig).toBe(baseConfig);
        });

        describe('with filter set', () => {

          let sourceFilter: SearchParameters;

          beforeEach(() => {
            component.filters = sourceFilter = { parameters: {} };
            fixture.detectChanges();
          });

          it('should have the filter set', () => {
            expect(querySourceFilterDirective.filters).toBe(sourceFilter);
          });

          it('should not trigger an automatic refresh', (done) => {
            let sub;

            sub = querySourceComponent.stream.subscribe({
              next: (x) => {
                expect(x.state).toBe(SourceState.Reset);

                setTimeout(() => {
                  sub.unsubscribe();
                  done();
                });
              }
            });
          });

          describe('with auto refresh enabled', () => {

            beforeEach(() => {
              querySourceFilterDirective.resetOnNewConfig = true;
            });

            it('should load new results when the filter changes', (done) => {

              // Set new results for the test.
              const newResults = [10, 11];
              testQuerySource.testQueryService.keyResults = newResults;

              component.filters = {
                test: 'x'
              };

              fixture.detectChanges();

              querySourceComponent.stream.pipe(
                filter((x) => x.state === SourceState.Done)
              ).subscribe({
                next: (x) => {
                  expect(x.state).toBe(SourceState.Done);
                  expect(x.elements.length).toBe(newResults.length);
                  done();
                }
              });

            });

          });

        });

      });

    });

  });

  describe('AbstractConfigurableKeyQuerySourceComponent', () => {

    let readSourceComponent: TestFooKeyQuerySourceComponent;
    let testKeyQuerySource: TestFooTestKeyQuerySource;

    beforeEach(() => {
      readSourceComponent = new TestFooKeyQuerySourceComponent();
      testKeyQuerySource = readSourceComponent.testFooTestKeyQuerySource;
    });

  });

});

@Component({
  template: '',
  selector: 'gae-test-model-key-query-source',
  providers: ProvideConfigurableKeySearchSourceComponent(TestFooKeyQuerySourceComponent)
})
export class TestFooKeyQuerySourceComponent extends AbstractConfigurableKeyQuerySourceComponent<TestFoo> {

  constructor(@Optional() source?: KeyQuerySource<TestFoo>) {
    super(source || new TestFooTestKeyQuerySource());
  }

  get testFooTestKeyQuerySource() {
    return (this._source as any | KeyQuerySource<TestFoo>) as TestFooTestKeyQuerySource;
  }

}

@Component({
  template: `
    <gae-test-model-key-query-source [gaeKeyQuerySourceFilter]="filters" [baseConfig]="baseConfig"></gae-test-model-key-query-source>
  `
})
class TestViewComponent {

  @Input()
  filters: any;

  @Input()
  baseConfig: QuerySourceConfiguration = undefined;

  @ViewChild(TestFooKeyQuerySourceComponent, { static: true })
  public querySourceComponent: TestFooKeyQuerySourceComponent;

  @ViewChild(GaeKeyQuerySourceFilterDirective, { static: true })
  public querySourceFilterDirective: GaeKeyQuerySourceFilterDirective<any>;

}
