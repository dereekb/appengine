import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input, DebugElement } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { GaeModelComponentsModule } from '../model.module';
import { GaeFormComponentsModule } from '../../form/form.module';
import { MatProgressButtonsModule } from 'mat-progress-buttons';
import { GaeActionViewComponent, GaeActionResetViewComponent } from './action.component';
import { By } from '@angular/platform-browser';
import { ActionObject, ActionState } from '../../shared/action';
import { AbstractActionDirective } from '../../shared/action.directive';

describe('GaeActionViewComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeFormComponentsModule,
        GaeModelComponentsModule,
        MatProgressButtonsModule.forRoot(),
        NoopAnimationsModule
      ],
      declarations: [
        TestViewComponent,
        TestDoneViewComponent,
        TestResetViewComponent
      ]
    }).compileComponents();
  }));

  describe('GaeActionDoneViewComponent', () => {

    let testComponent: TestDoneViewComponent;
    let fixture: ComponentFixture<TestDoneViewComponent>;

    beforeEach(async(() => {
      fixture = TestBed.createComponent(TestDoneViewComponent);
      testComponent = fixture.componentInstance;
      fixture.detectChanges();
    }));

    it('should be created', () => {
      expect(testComponent).toBeDefined();
    });

  });

  describe('GaeActionResetViewComponent', () => {

    let testComponent: TestResetViewComponent;
    let fixture: ComponentFixture<TestResetViewComponent>;

    beforeEach(async(() => {
      fixture = TestBed.createComponent(TestResetViewComponent);
      testComponent = fixture.componentInstance;
      fixture.detectChanges();
    }));

    it('should be created', () => {
      expect(testComponent).toBeDefined();
    });

  });

  describe('GaeActionViewComponent', () => {

    let action: TestActionObject;
    let component: GaeActionViewComponent;
    let testComponent: TestViewComponent;
    let fixture: ComponentFixture<TestViewComponent>;

    beforeEach(async(() => {
      action = new TestActionObject();
      fixture = TestBed.createComponent(TestViewComponent);
      testComponent = fixture.componentInstance;
      component = testComponent.component;
      component.action = action;
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

    function assertShowsDoneContent() {
      const testContent: HTMLElement = fixture.debugElement.query(By.css(`#${TEST_DONE_CONTENT_ID}`)).nativeElement;
      expect(testContent).not.toBeNull();
      expect(testContent.innerText).toBe(TEST_DONE_CONTENT);
    }

    function assertDoesNotShowDoneContent() {
      const testContent: DebugElement = fixture.debugElement.query(By.css(`#${TEST_DONE_CONTENT_ID}`));
      expect(testContent).toBeNull();
    }

    function assertShowsErrorContent() {
      const testContent: HTMLElement = fixture.debugElement.query(By.css(`#${TEST_ERROR_CONTENT_ID}`)).nativeElement;
      expect(testContent).not.toBeNull();
      expect(testContent.innerText).toBe(TEST_ERROR_CONTENT);
    }

    describe('while not done', () => {

      it('should show the content', () => {
        assertShowsContent();
      });

      it('should not show the done content', () => {
        assertDoesNotShowDoneContent();
      });

    });

    describe('with an error', () => {

      beforeEach(async(() => {
        action.setTestError();
      }));

      it('should show the error', () => {
        assertShowsErrorContent();
      });

    });

    describe('while the action is working', () => {

      beforeEach(async(() => {
        action.setTestWorking();
      }));

      it('should show the content', () => {
        assertShowsContent();
      });

    });

    describe('when done', () => {

      beforeEach(async(() => {
        action.setTestDone();
      }));

      it('should show the done content', () => {
        assertShowsDoneContent();
      });

      it('should not show content', () => {
        assertDoesNotShowContent();
      });

    });

  });

});

const TEST_CONTENT_ID = 'test-content';
const TEST_CONTENT = 'CONTENT';

const TEST_DONE_CONTENT_ID = 'test-done';
const TEST_DONE_CONTENT = 'DONE';

const TEST_ERROR_CONTENT_ID = 'test-error';
const TEST_ERROR_CONTENT = 'ERROR';

class TestActionObject extends AbstractActionDirective<any> {

  setTestError() {
    this.next({
      state: ActionState.Error,
      result: [],
      error: new Error()
    });
  }

  setTestWorking() {
    this.next({
      state: ActionState.Working,
      result: null,
      error: null
    });
  }

  setTestDone() {
    this.next({
      state: ActionState.Complete,
      result: [],
      error: null
    });
  }

}

@Component({
  template: `
    <gae-action-view [action]="action">
      <div content>
        <p id="${ TEST_CONTENT_ID}">${TEST_CONTENT}</p>
      </div>
      <div done>
        <p id="${ TEST_DONE_CONTENT_ID}">${TEST_DONE_CONTENT}</p>
      </div>
      <div error>
        <p id="${ TEST_ERROR_CONTENT_ID}">${TEST_ERROR_CONTENT}</p>
      </div>
    </gae-action-view>
  `
})
class TestViewComponent {

  @Input()
  public action: ActionObject;

  @ViewChild(GaeActionViewComponent, { static: true })
  public component: GaeActionViewComponent;

}

@Component({
  template: `
    <gae-action-done-view></gae-action-done-view>
  `
})
class TestDoneViewComponent {

}

@Component({
  template: `
  <gae-action-view [action]="action">
    <div content>
      <gae-action-reset-view></gae-action-reset-view>
    </div>
  </gae-action-view>
  `
})
class TestResetViewComponent {

  @ViewChild(GaeActionResetViewComponent, { static: true })
  public component: GaeActionResetViewComponent;

}
