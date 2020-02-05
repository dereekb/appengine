import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Component, Input } from '@angular/core';
import { By } from '@angular/platform-browser';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ClickableAnchor } from './anchor.component';
import { GaeAnchorModule } from './anchor.module';
import { UIRouterModule } from '@uirouter/angular';

fdescribe('GaeAnchorComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        NoopAnimationsModule,
        GaeAnchorModule,
        UIRouterModule.forRoot()
      ],
      declarations: [TestViewComponent]
    }).compileComponents();
  }));

  let testComponent: TestViewComponent;
  let fixture: ComponentFixture<TestViewComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestViewComponent);
    testComponent = fixture.componentInstance;
    fixture.detectChanges();
  }));

  function testContentWasShown() {

    it('should show content', () => {
      const anchorElement: HTMLElement = fixture.debugElement.query(By.css(`#${CUSTOM_CONTENT_ID}`)).nativeElement;
      expect(anchorElement).not.toBeNull();
      expect(anchorElement.innerText).toBe(CUSTOM_CONTENT);
    });

  }

  function testDisabledTests() {

    describe('when disabled', () => {

      it('should display the disabled version.', () => {
        testComponent.disabled = true;
        fixture.detectChanges();

        const anchorElement: HTMLElement = fixture.debugElement.query(By.css(`.gae-anchor-disabled`)).nativeElement;
        expect(anchorElement).not.toBeNull();
      });

    });

  }

  describe('with click config', () => {

    let clicked: boolean;

    beforeEach(async () => {
      clicked = false;
      testComponent.anchor = {
        onClick: () => {
          clicked = true;
        }
      };
      fixture.detectChanges();
    });

    testContentWasShown();
    testDisabledTests();

    it('should display the click version.', () => {
      const anchorElement: HTMLElement = fixture.debugElement.query(By.css(`.gae-anchor-click`)).nativeElement;
      expect(anchorElement).not.toBeNull();
    });

    it('should respond to clicks.', (done) => {
      const anchorElement = fixture.debugElement.query(By.css(`.gae-anchor-click`));
      anchorElement.triggerEventHandler('click', {} as MouseEvent);
      fixture.whenStable().then(() => {
        expect(clicked).toBeTrue();
        done();
      });
    });

  });

  describe('with sref config', () => {

    beforeEach(async () => {
      testComponent.anchor = {
        ref: 'test'
      };
      fixture.detectChanges();
    });

    testContentWasShown();
    testDisabledTests();

    it('should display the sref version.', () => {
      const anchorElement: HTMLElement = fixture.debugElement.query(By.css(`.gae-anchor-sref`)).nativeElement;
      expect(anchorElement).not.toBeNull();
    });

  });

  describe('with href config', () => {

    beforeEach(async () => {
      testComponent.anchor = {
        url: 'https://google.com'
      };
      fixture.detectChanges();
    });

    testContentWasShown();
    testDisabledTests();

    it('should display the href version.', () => {
      const anchorElement: HTMLElement = fixture.debugElement.query(By.css(`.gae-anchor-href`)).nativeElement;
      expect(anchorElement).not.toBeNull();
    });

  });

});

const CUSTOM_CONTENT_ID = 'custom-content';
const CUSTOM_CONTENT = 'Custom Content';

@Component({
  template: `
    <gae-anchor [anchor]="anchor" [disabled]="disabled">
      <span id="${CUSTOM_CONTENT_ID}">${CUSTOM_CONTENT}</span>
    </gae-anchor>
  `
})
class TestViewComponent {

  @Input()
  public disabled: boolean;

  @Input()
  public anchor: ClickableAnchor;

}
