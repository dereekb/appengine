import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild } from '@angular/core';
import { GaeMaterialComponentsModule } from './material.module';
import { GaeAppPageComponent } from './page.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { UIRouterModule, UIRouter, Ng2StateDeclaration, UIView } from '@uirouter/angular';
import { By } from '@angular/platform-browser';

describe('GaeAppPageComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [GaeMaterialComponentsModule.forApp(), UIRouterModule.forRoot({ useHash: true, states: TEST_STATES }), NoopAnimationsModule],
      declarations: [TestViewComponent, TestViewSidenavComponent, TestViewContentComponent]
    }).compileComponents();
  }));

  let fixture: ComponentFixture<TestViewComponent>;
  let component: TestViewComponent;
  let pageComponent: GaeAppPageComponent;
  let contentComponent: TestViewContentComponent;
  let sidenavComponent: TestViewSidenavComponent;
  let router: UIRouter;

  beforeEach((done) => {
    fixture = TestBed.createComponent(TestViewComponent);
    fixture.detectChanges();

    router = fixture.debugElement.injector.get(UIRouter);

    router.stateService.go(TEST_STATE_NAME).then(() => {
      component = fixture.componentInstance;
      pageComponent = component.pageComponent;
      contentComponent = fixture.debugElement.query(By.directive(TestViewContentComponent)).componentInstance;
      sidenavComponent = fixture.debugElement.query(By.directive(TestViewSidenavComponent)).componentInstance;
      done();
    });
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
    expect(pageComponent).toBeTruthy();
    expect(contentComponent).toBeTruthy();
    expect(sidenavComponent).toBeTruthy();
  });

});

@Component({
  template: `
    <gae-app-page></gae-app-page>
  `
})
class TestViewComponent {

  @ViewChild(GaeAppPageComponent)
  public pageComponent: GaeAppPageComponent;

}

const TEST_SIDENAV_CONTENT = 'SIDENAV!';
const TEST_SIDENAV_CONTENT_ID = 'test-sidenav-content';

@Component({
  template: `<p id="${TEST_SIDENAV_CONTENT_ID}">${TEST_SIDENAV_CONTENT}</p>`
})
class TestViewSidenavComponent {

}

const TEST_VIEW_CONTENT = 'VIEW';
const TEST_VIEW_CONTENT_ID = 'test-view-content';

@Component({
  template: `<p id="${TEST_VIEW_CONTENT_ID}">${TEST_VIEW_CONTENT}</p>`
})
class TestViewContentComponent {

}

const TEST_STATE_NAME = 'test';

const TEST_STATES: Ng2StateDeclaration[] = [
  {
    name: TEST_STATE_NAME,
    views: {
      content: {
        component: TestViewContentComponent
      },
      sidenav: {
        component: TestViewSidenavComponent
      }
    }
  }
];
