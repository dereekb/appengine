import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component } from '@angular/core';
import { GaeLoadingComponentsModule } from './loading.module';
import { By } from '@angular/platform-browser';
import { GaeLoadingProgressComponent } from './loading-progress.component';
import { ErrorInput } from '@gae-web/appengine-utility';
import { GaeErrorComponent } from './error.component';

describe('GaeErrorComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [GaeLoadingComponentsModule],
      declarations: [ErrorComponent]
    }).compileComponents();
  }));

});

@Component({
  template: `
    <gae-error [error]="error"></gae-error>
  `
})
class ErrorComponent {

  public error: ErrorInput;

}
