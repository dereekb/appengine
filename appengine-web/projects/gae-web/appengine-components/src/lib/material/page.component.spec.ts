import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild } from '@angular/core';
import { GaeMaterialComponentsModule } from './material.module';
import { filter, first } from 'rxjs/operators';
import { By } from '@angular/platform-browser';
import { GaeAppPageComponent } from './page.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { UIRouterModule } from '@uirouter/angular';

describe('GaeAppPageComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [GaeMaterialComponentsModule.forApp(), UIRouterModule.forRoot(), NoopAnimationsModule],
      declarations: [TestViewComponent]
    }).compileComponents();
  }));

  let fixture: ComponentFixture<TestViewComponent>;
  let component: TestViewComponent;
  let pageComponent: GaeAppPageComponent;

  beforeEach(() => {
    fixture = TestBed.createComponent(TestViewComponent);
    component = fixture.componentInstance;
    pageComponent = component.pageComponent;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
    expect(pageComponent).toBeTruthy();
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
