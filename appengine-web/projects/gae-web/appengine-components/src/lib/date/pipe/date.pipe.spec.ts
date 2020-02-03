import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Component, ViewChild, Input, Inject, forwardRef } from '@angular/core';
import { By } from '@angular/platform-browser';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { DateTime } from 'luxon';
import { GaeDatePipesModule } from './date.pipe.module';

fdescribe('Date Pipe Test Component', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeDatePipesModule,
        NoopAnimationsModule
      ],
      declarations: [DatePipesTestComponent]
    }).compileComponents();
  }));

  let testComponent: DatePipesTestComponent;
  let fixture: ComponentFixture<DatePipesTestComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(DatePipesTestComponent);
    testComponent = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should load', () => {});

});

@Component({
  template: `
    <div>
      <p>{{ dateTime | dateFromPlusTo:'h:mm a':10 }}</p>
      <p>{{ 500 | minutesString }}</p>
      <p>{{ dateTime | timeDistance }}</p>
      <p>{{ dateTime | toDateTime }}</p>
      <p>{{ dateTime | toJsDate }}</p>
      <p>{{ dateTime | toMinutes }}</p>
    </div>
  `
})
class DatePipesTestComponent {

  public dateTime: DateTime = DateTime.local();

}
