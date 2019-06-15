import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, Input, DebugElement, ViewChild, Optional } from '@angular/core';
import { GaeModelComponentsModule } from '../model.module';
import { ProvideReadSourceComponent, AbstractReadSourceComponent, GaeReadSourceKeyDirective, ReadSourceComponent } from './read.component';
import { ReadSourceFactory, TestFooTestReadSourceFactory, ReadSource } from '@gae-web/appengine-client';
import { AbstractDatabaseModel, ReadRequest, ReadResponse, ReadService, TestFoo, TestFooReadService } from '@gae-web/appengine-api';
import { Observable, of, BehaviorSubject, Subject } from 'rxjs';
import { ModelUtility, ModelKey, ValueUtility, SourceState, NamedUniqueModel } from '@gae-web/appengine-utility';
import { By } from '@angular/platform-browser';
import { filter } from 'rxjs/operators';

describe('Read Components', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [GaeModelComponentsModule],
      declarations: [TestViewComponent, TestFooReadSourceComponent]
    }).compileComponents();
  }));

  describe('GaeReadSourceKeyDirective', () => {
    let fixture: ComponentFixture<TestViewComponent>;
    let component: TestViewComponent;

    let testReadSourceComponentComponent: TestFooReadSourceComponent;
    let readSourceDirectiveComponent: GaeReadSourceKeyDirective<TestFoo>;

    beforeEach(async(() => {
      fixture = TestBed.createComponent(TestViewComponent);
      component = fixture.componentInstance;

      const testReadSourceComponentElement: DebugElement = fixture.debugElement.query(By.directive(TestFooReadSourceComponent));
      testReadSourceComponentComponent = testReadSourceComponentElement.componentInstance;

      readSourceDirectiveComponent = component.gaeReadSource;

      fixture.detectChanges();
    }));

    it('should have the key value.', () => {
      expect(readSourceDirectiveComponent.key).toBe(component.key);
    });

    it('should have bound to the host ReadSourceComponent', () => {
      expect(readSourceDirectiveComponent.source).toBeDefined();
      expect(readSourceDirectiveComponent.source).toBe(testReadSourceComponentComponent);
    });

    it('should have set the readSourceKeys value on the ReadSourceComponent', () => {
      expect(testReadSourceComponentComponent.readSourceKeys).toBeDefined();
    });

  });

  describe('AbstractReadSourceComponent', () => {

    let readSourceComponent: TestFooReadSourceComponent;
    let testReadService: TestFooReadService;

    beforeEach(() => {
      readSourceComponent = new TestFooReadSourceComponent();
      testReadService = readSourceComponent.testFooReadService;
    });

    describe('with no read source keys set', () => {

      it('should be in a reset state.', () => {
        expect(readSourceComponent.state).toBe(SourceState.Reset);
      });

    });

    const TEST_KEY = 1;
    const SECOND_TEST_KEY = 2;

    describe('with simple input observable', () => {

      beforeEach(() => {
        readSourceComponent.readSourceKeys = of([TEST_KEY]);
        testReadService.loadingTime = 100;
      });

      it('should be in the done state when the input source completes and elements are finished loading.', (done) => {
        readSourceComponent.stream.pipe(
          filter((x) => {
            return x.elements.length > 0;
          })
        ).subscribe((x) => {
          expect(x.state).toBe(SourceState.Done);
          done();
        });
      });

      describe('in the done state', () => {

        beforeEach((done) => {
          readSourceComponent.stream.pipe(
            filter((x) => {
              return x.state === SourceState.Done;
            })
          ).subscribe((x) => {
            done();
          });
        });

        it('should update when the input is changed.', (done) => {
          readSourceComponent.readSourceKeys = of([SECOND_TEST_KEY]);
          expect(readSourceComponent.state).toBe(SourceState.Loading);

          readSourceComponent.stream.pipe(
            filter((x) => {
              return x.state === SourceState.Done;
            })
          ).subscribe((x) => {
            expect(x.elements.length).toBe(1);
            expect(x.elements[0].key).toBe(SECOND_TEST_KEY);
            done();
          });
        });

      });

    });

    // TODO: Can test the inputs/outputs, as the component is mainly a pass through for the ReadSourceFactory.

  });

});

@Component({
  template: '',
  selector: 'gae-test-model-read-source',
  providers: [ProvideReadSourceComponent(TestFooReadSourceComponent)]
})
export class TestFooReadSourceComponent extends AbstractReadSourceComponent<TestFoo> {

  constructor(@Optional() factory?: ReadSourceFactory<TestFoo>) {
    super(factory || new TestFooTestReadSourceFactory());
  }

  get testFooReadService() {
    return (this._source as any | ReadSource<TestFoo>)._service as TestFooReadService;
  }

}

@Component({
  template: `
    <gae-test-model-read-source [gaeReadSourceKey]="key"></gae-test-model-read-source>
  `
})
class TestViewComponent {

  key: ModelKey = 1;

  @ViewChild(GaeReadSourceKeyDirective, { static: true })
  public gaeReadSource: GaeReadSourceKeyDirective<TestFoo>;

}
