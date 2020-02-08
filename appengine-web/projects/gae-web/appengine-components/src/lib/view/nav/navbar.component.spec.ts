import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Component, Input } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { UIRouterModule } from '@uirouter/angular';
import { PlugNavModule } from './nav.module';
import { ClickableAnchorLink } from './navbar.component';

fdescribe('GaeNavbarComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        NoopAnimationsModule,
        PlugNavModule,
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

  describe('with links', () => {

    beforeEach(async(() => {
      testComponent.links = [{
        title: 'Test'
      }];

      fixture.detectChanges();
    }));

    it('should render', () => undefined);

  });

});

@Component({
  template: `
    <gae-nav-bar [links]="links"></gae-nav-bar>
  `
})
class TestViewComponent {

  @Input()
  public links: ClickableAnchorLink[];

}
