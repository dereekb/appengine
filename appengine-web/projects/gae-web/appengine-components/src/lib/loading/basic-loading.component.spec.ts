import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component } from '@angular/core';
import { GaeLoadingModule } from './loading.module';
import { By } from '@angular/platform-browser';
import { GaeLoadingProgressComponent } from './loading-progress.component';
import { ErrorInput } from 'projects/gae-web/appengine-utility/src/lib/error';
import { GaeErrorComponent } from './error.component';

describe('BasicLoadingComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [GaeLoadingModule],
      declarations: [BasicLoadingWithContentComponent, BasicLoadingWithCustomErrorComponent, BasicLoadingWithCustomLoadingComponent]
    }).compileComponents();
  }));

  describe('with content', () => {
    let fixture: ComponentFixture<BasicLoadingWithContentComponent>;
    let component: BasicLoadingWithContentComponent;

    beforeEach(() => {
      fixture = TestBed.createComponent(BasicLoadingWithContentComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    it('should display the content while not loading.', () => {
      component.loading = false;
      fixture.detectChanges();

      const testContent: HTMLElement = fixture.debugElement.query(By.css('#test-content')).nativeElement;
      expect(testContent).not.toBeNull();
      expect(testContent.innerText).toBe(TEST_CONTENT);
    });

    it('should not display the content while loading.', () => {
      component.loading = true;
      fixture.detectChanges();

      const testContentQueryResult = fixture.debugElement.query(By.css('#test-content'));
      expect(testContentQueryResult).toBeNull();
    });

    it('should not display the content if there is an error.', () => {
      component.error = {
        code: 'Test',
        message: 'Test'
      };

      fixture.detectChanges();

      const testContentQueryResult = fixture.debugElement.query(By.css('#test-content'));
      expect(testContentQueryResult).toBeNull();
    });

    it('should display the loading progress view while loading.', () => {
      component.loading = true;
      fixture.detectChanges();

      const loadingProgressQueryResult = fixture.debugElement.query(By.directive(GaeLoadingProgressComponent));
      expect(loadingProgressQueryResult).not.toBeNull();
    });

    it('should display the error view when there is an error.', () => {
      component.error = {
        code: 'Test',
        message: 'Test'
      };

      fixture.detectChanges();

      const errorComponentQueryResult = fixture.debugElement.query(By.directive(GaeErrorComponent));
      expect(errorComponentQueryResult).not.toBeNull();
    });

  });

  describe('with custom error', () => {
    let fixture: ComponentFixture<BasicLoadingWithCustomErrorComponent>;
    let component: BasicLoadingWithCustomErrorComponent;

    beforeEach(() => {
      fixture = TestBed.createComponent(BasicLoadingWithCustomErrorComponent);
      component = fixture.componentInstance;

      component.error = {
        code: 'Test',
        message: 'Test'
      };

      fixture.detectChanges();
    });

    it('should display the custom error content on error.', () => {
      const customError: HTMLElement = fixture.debugElement.query(By.css('#test-error')).nativeElement;
      expect(customError).not.toBeNull();
      expect(customError.innerText).toBe(CUSTOM_ERROR_CONTENT);
    });

  });

  describe('with custom loading', () => {
    let fixture: ComponentFixture<BasicLoadingWithCustomLoadingComponent>;
    let component: BasicLoadingWithCustomLoadingComponent;

    beforeEach(() => {
      fixture = TestBed.createComponent(BasicLoadingWithCustomLoadingComponent);
      component = fixture.componentInstance;
      component.loading = true;
      fixture.detectChanges();
    });

    it('should display the custom loading content while loading.', () => {
      fixture.detectChanges();

      const customLoading: HTMLElement = fixture.debugElement.query(By.css('#custom-loading')).nativeElement;
      expect(customLoading).not.toBeNull();
      expect(customLoading.innerText).toBe(CUSTOM_LOADING_CONTENT);
    });

  });

});

const TEST_CONTENT = 'Content';
const CUSTOM_LOADING_CONTENT = 'Custom Loading...';
const CUSTOM_ERROR_CONTENT = 'Error.';

@Component({
  template: `
    <gae-basic-loading [waitFor]="loading" [error]="error">
      <div content>
        <p id="test-content">${TEST_CONTENT}</p>
      </div>
    </gae-basic-loading>
  `
})
class BasicLoadingWithContentComponent {

  public loading = false;

  public error: ErrorInput;

}

@Component({
  template: `
    <gae-basic-loading [error]="error">
      <div error>
        <p id="test-error">${CUSTOM_ERROR_CONTENT}</p>
      </div>
    </gae-basic-loading>
  `
})
class BasicLoadingWithCustomErrorComponent {

  public error: ErrorInput;

}

@Component({
  template: `
    <gae-basic-loading [waitFor]="loading">
      <div content>
      </div>
      <div loading>
        <p id="custom-loading">${CUSTOM_LOADING_CONTENT}</p>
      </div>
    </gae-basic-loading>
  `
})
class BasicLoadingWithCustomLoadingComponent {

  public loading = false;

}
