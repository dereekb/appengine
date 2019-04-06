import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component } from '@angular/core';
import { GaeLoadingModule } from './loading.module';
import { By } from '@angular/platform-browser';
import { GaeLoadingProgressComponent } from './loading-progress.component';

describe('BasicLoadingComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [GaeLoadingModule],
      declarations: [BasicLoadingWithContentComponent]
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

    it('should display the loading progress view while loading.', () => {
      component.loading = true;
      fixture.detectChanges();

      const loadingProgressQueryResult = fixture.debugElement.query(By.directive(GaeLoadingProgressComponent));
      expect(loadingProgressQueryResult).not.toBeNull();
    });

  });

});

const TEST_CONTENT = 'Content';

@Component({
  template: `
    <gae-basic-loading [waitFor]="loading">
      <div content>
        <p id="test-content">${TEST_CONTENT}</p>
      </div>
    </gae-basic-loading>
  `
})
class BasicLoadingWithContentComponent {

  public loading = false;

}

@Component({
  template: `
    <gae-basic-loading>
      <error>
      </error>
    </gae-basic-loading>
  `
})
class BasicLoadingWithCustomErrorComponent {

}

@Component({
  template: `
    <gae-basic-loading>
      <loading>
        <p id="loading-text">Loading...</p>
      </loading>
    </gae-basic-loading>
  `
})
class BasicLoadingWithCustomLoadingComponent {

}
