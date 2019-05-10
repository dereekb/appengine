import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Component, ViewChild, Input, Inject, forwardRef } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Observable, of, throwError } from 'rxjs';
import { UniqueModel } from '@gae-web/appengine-utility';
import { TestModel } from '../model/resource/read.component.spec';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AbstractListViewComponent, ProvideListViewComponent } from './list-view.component';
import { AbstractListContentComponent } from './list-content.component';
import { GaeListComponentsModule } from './list.module';
import { ListViewSource, AbstractListViewSource, ListViewSourceState } from './source';
import { GaeListViewWrapperComponent } from './list-view-wrapper.component';
import { ReadSource } from '@gae-web/appengine-client';
import { ReadService, ReadRequest, ReadResponse } from '@gae-web/appengine-api';

fdescribe('ListViewSource', () => {

  let testListViewSource: TestListViewSource;

  describe('with error', () => {

    beforeEach(() => {
      testListViewSource = TestListViewSourceFactory.makeErrorSource();
    });

    it('should have an error.', (done) => {
      testListViewSource.stream.subscribe((x) => {
        expect(x.error).toBeDefined();
        done();
      });
    });

  });

});

export class TestListViewSourceFactory {

  /**
   * Creates a source that only returns errors.
   */
  static makeErrorSource(error: any = new Error('Test')): TestListViewSource {
    const readService: ReadService<TestModel> = {
      type: 'testModel',
      read(request: ReadRequest): Observable<ReadResponse<TestModel>> {
        return throwError(error);
      }
    };

    const readSource = new ReadSource<TestModel>(readService);
    readSource.input = of(1);

    const listReadSource = new TestListViewSource();
    listReadSource.readSource = readSource;

    return listReadSource;
  }

}

class TestListViewSource extends AbstractListViewSource<TestModel> {

  public set readSource(source: ReadSource<TestModel> | undefined) {
    super.setSource(source);
  }

  // MARK: ListViewSource
  public more(): void {
    // Do nothing.
  }

  public refresh(): void {
    // Do nothing.
  }

}
