import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input, ContentChild, ContentChildren, ViewChildren } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Observable } from 'rxjs';
import { UniqueModel } from '@gae-web/appengine-utility';
import { FormBuilder, Validators } from '@angular/forms';
import { TestFoo } from '@gae-web/appengine-api';
import { AbstractActionDirective } from './action.directive';

describe('AbstractActionDirective', () => {

  let directive: TestActionDirective;

  beforeEach(async(() => {
    directive = new TestActionDirective();
  }));

  it('should not be working', () => {
    expect(directive.isWorking).toBeFalse();
  });

});

export class TestActionDirective extends AbstractActionDirective<any> {

}
