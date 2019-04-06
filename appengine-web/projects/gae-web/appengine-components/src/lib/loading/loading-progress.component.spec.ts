import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component } from '@angular/core';
import { GaeLoadingModule } from './loading.module';


describe('GaeLoadingProgress', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [GaeLoadingModule],
      declarations: [LoadingProgressSpinnerComponent, LoadingProgressLinearComponent]
    }).compileComponents();
  }));

  describe('with spinner', () => {

    let fixture: ComponentFixture<LoadingProgressSpinnerComponent>;
    let component: LoadingProgressSpinnerComponent;

    beforeEach(() => {
      fixture = TestBed.createComponent(LoadingProgressSpinnerComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    it('should create', () => {
      expect(component).toBeTruthy();
    });

  });

  describe('with linear', () => {

    let fixture: ComponentFixture<LoadingProgressLinearComponent>;
    let component: LoadingProgressLinearComponent;

    beforeEach(() => {
      fixture = TestBed.createComponent(LoadingProgressLinearComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    it('should create', () => {
      expect(component).toBeTruthy();
    });

  });

});

@Component({
  template: `
    <gae-loading-progress [text]="text"></gae-loading-progress>
  `
})
class LoadingProgressSpinnerComponent {

  text: string;

}

@Component({
  template: `
    <gae-loading-progress [linear]="true" [text]="text"></gae-loading-progress>
  `
})
class LoadingProgressLinearComponent {

  text: string;

}
