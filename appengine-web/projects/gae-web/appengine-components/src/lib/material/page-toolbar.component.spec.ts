import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild } from '@angular/core';
import { GaeMaterialComponentsModule } from './material.module';
import { GaePageToolbarComponent, GaePageToolbarConfigurationComponent } from './page-toolbar.component';


fdescribe('GaePageToolbarComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [GaeMaterialComponentsModule.forApp()],
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
  });

});

@Component({
  template: `
  <gae-page-toolbar>
    <ng-container content>
      <gae-page-toolbar-configuration></gae-page-toolbar-configuration>
    </ng-container>
  </gae-page-toolbar>
  `
})
class TestViewComponent {

  @ViewChild(GaePageToolbarComponent)
  public toolbarComponent: GaePageToolbarComponent;


  @ViewChild(GaePageToolbarConfigurationComponent)
  public toolbarConfigurationComponent: GaePageToolbarConfigurationComponent;

}
