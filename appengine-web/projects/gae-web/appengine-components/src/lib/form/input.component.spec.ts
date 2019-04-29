import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Component, ViewChild } from '@angular/core';
import { By } from '@angular/platform-browser';
import { GaeConfiguredConfirmModelFormComponent, GaeConfirmModelFormComponent } from './confirm-form.component';
import { GaeFormComponentsModule } from './form.module';

describe('Input Components', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeFormComponentsModule
      ],
      declarations: []
    }).compileComponents();
  }));

  describe('SelectFieldFormControlComponent', () => {

    // TODO

  });

});
