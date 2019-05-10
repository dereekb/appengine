import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Component, ViewChild, Input, Inject, forwardRef } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';
import { UniqueModel } from '@gae-web/appengine-utility';
import { TestModel } from '../model/resource/read.component.spec';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AbstractListViewComponent, ProvideListViewComponent } from './list-view.component';
import { AbstractListContentComponent } from './list-content.component';
import { GaeListComponentsModule } from './list.module';
import { ListViewSource } from './source';
import { GaeListViewWrapperComponent } from './list-view-wrapper.component';

describe('GaeListViewReadSourceDirectives', () => {

  // TODO: Test GaeListViewReadSourceDirective, etc. and show their use.

});
