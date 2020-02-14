import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { GaeModelComponentsModule } from '../model.module';
import { GaeTestFooModelFormComponent } from '../../form/model.component.spec';
import { GaeFormComponentsModule } from '../../form/form.module';
import { TestFooTestKeyQuerySource, QuerySourceConfiguration } from '@gae-web/appengine-client';
import { TestFooKeyQuerySourceComponent } from '../resource/search.component.spec';
import { GaeKeyQuerySourceFormFilterDirective, GaeKeyQuerySourceFormFilterDirectiveFunction } from './search.component';
import { SourceState } from '@gae-web/appengine-utility';
import { GaeKeyQuerySourceFilterDirective } from '../resource/search.component';
import { filter } from 'rxjs/operators';

describe('GaeKeyQuerySourceFormFilterDirective', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeFormComponentsModule,
        GaeModelComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [
        TestFooKeyQuerySourceComponent,
        GaeTestFooModelFormComponent,
        TestViewComponent
      ]
    }).compileComponents();
  }));

  let component: TestViewComponent;

  let form: GaeTestFooModelFormComponent;
  let filterDirective: GaeKeyQuerySourceFilterDirective<any>;
  let formFilterDirective: GaeKeyQuerySourceFormFilterDirective;

  let querySourceComponent: TestFooKeyQuerySourceComponent;
  let testQuerySource: TestFooTestKeyQuerySource;

  let fixture: ComponentFixture<TestViewComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestViewComponent);
    component = fixture.componentInstance;

    form = component.form;
    querySourceComponent = component.querySourceComponent;

    testQuerySource = querySourceComponent.testFooTestKeyQuerySource;
    testQuerySource.testQueryService.keyResults = [1, 2, 3, 4, 5];

    filterDirective = component.querySourceFilterDirective;
    formFilterDirective = component.querySourceFormFilterDirective;

    fixture.detectChanges();
  }));

  it('should be created', () => {
    expect(formFilterDirective).toBeDefined();
  });

  describe('with custom makeFilter function', () => {

    let madeFilter = false;
    let customMakeFilter: GaeKeyQuerySourceFormFilterDirectiveFunction;

    beforeEach(() => {
      component.makeFilter = customMakeFilter = (x) => {
        madeFilter = true;
        return x;
      };

      fixture.detectChanges();
    });

    it('should have hit the custom filter', () => {
      expect(madeFilter).toBe(true);
    });

    it('should not trigger an automatic refresh', (done) => {
      expect(form.isComplete).toBe(true);

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
        filterDirective.resetOnNewConfig = true;
      });

      it('form changes should trigger new results', (done) => {
        form.model = form.model;

        expect(form.isComplete).toBe(true);

        querySourceComponent.stream.pipe(
          filter((x) => x.state === SourceState.Done)
        ).subscribe({
          next: (x) => {
            expect(x.state).toBe(SourceState.Done);
            done();
          }
        });
      });

    });

  });

});

@Component({
  template: `
    <gae-test-model-model-form #form></gae-test-model-model-form>
    <gae-test-model-key-query-source gaeKeyQuerySourceFilter [baseConfig]="baseConfig" [gaeKeyQuerySourceFormFilter]="form" [makeFilter]="makeFilter"></gae-test-model-key-query-source>
  `
})
class TestViewComponent {

  @Input()
  makeFilter: GaeKeyQuerySourceFormFilterDirectiveFunction;

  @Input()
  baseConfig: QuerySourceConfiguration = undefined;

  @ViewChild(GaeTestFooModelFormComponent, { static: true })
  public form: GaeTestFooModelFormComponent;

  @ViewChild(TestFooKeyQuerySourceComponent, { static: true })
  public querySourceComponent: TestFooKeyQuerySourceComponent;

  @ViewChild(GaeKeyQuerySourceFilterDirective, { static: true })
  public querySourceFilterDirective: GaeKeyQuerySourceFilterDirective<any>;

  @ViewChild(GaeKeyQuerySourceFormFilterDirective, { static: true })
  public querySourceFormFilterDirective: GaeKeyQuerySourceFormFilterDirective;

}
