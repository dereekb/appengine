import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild } from '@angular/core';
import { GaeMaterialComponentsModule } from './material.module';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { GaeSidenavControllerDirective } from './sidenav.component';
import { MatSidenavModule } from '@angular/material';
import { UIRouterModule } from '@uirouter/angular';

fdescribe('GaeSideNavComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [MatSidenavModule, UIRouterModule.forRoot(), GaeMaterialComponentsModule.forApp(), NoopAnimationsModule],
      declarations: [TestViewComponent]
    }).compileComponents();
  }));

  let fixture: ComponentFixture<TestViewComponent>;
  let component: TestViewComponent;

  beforeEach(() => {
    fixture = TestBed.createComponent(TestViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
    expect(component.sidenavController).toBeTruthy();
  });

  it('should be linked to the sidenav', () => {
    expect(component.sidenavController.sidenav).toBeTruthy();
  });

});

@Component({
  template: `
    <mat-sidenav-container gaeSidenavController [sidenav]="sidenav">
      <mat-sidenav #sidenav></mat-sidenav>
    </mat-sidenav-container>
  `
})
class TestViewComponent {

  @ViewChild(GaeSidenavControllerDirective)
  public sidenavController: GaeSidenavControllerDirective;

}
