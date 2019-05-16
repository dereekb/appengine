import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Component, ViewChild, Input, Inject, forwardRef } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Observable, of, throwError } from 'rxjs';
import { UniqueModel, ModelKey } from '@gae-web/appengine-utility';
import { TestModel } from '../model/resource/read.component.spec';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AbstractListViewComponent, ProvideListViewComponent } from './list-view.component';
import { AbstractListContentComponent } from './list-content.component';
import { GaeListComponentsModule } from './list.module';
import { ListViewSource, AbstractListViewSource, ListViewSourceState } from './source';
import { GaeListViewWrapperComponent } from './list-view-wrapper.component';
import { ReadSource } from '@gae-web/appengine-client';
import { ReadService, ReadRequest, ReadResponse, TestReadService } from '@gae-web/appengine-api';
import { delay, map } from 'rxjs/operators';

describe('ListViewSource', () => {

  let testListViewSource: TestListViewSource;

  describe('while loading', () => {

    beforeEach(() => {
      testListViewSource = TestListViewSourceFactory.makeLoadingSource();
    });

    it('should be loading in the loading state', (done) => {
      testListViewSource.stream.subscribe((x) => {
        expect(x.state === ListViewSourceState.Loading);
        done();
      });
    });

  });

  describe('with items', () => {

    const keys = [1, 2, 3, 4];

    beforeEach(() => {
      testListViewSource = TestListViewSourceFactory.makeSource(keys);
    });

    it('should have elements', (done) => {
      testListViewSource.stream.subscribe((x) => {
        expect(x.elements).toBeDefined();
        expect(x.elements).toBeArrayOfObjects();
        expect(x.elements.length).toBe(keys.length);
        expect(x.state === ListViewSourceState.Done);
        done();
      });
    });

  });

  describe('with no items', () => {

    beforeEach(() => {
      testListViewSource = TestListViewSourceFactory.makeEmptySource();
    });

    it('should have no elements', (done) => {
      testListViewSource.stream.subscribe((x) => {
        expect(x.elements).toBeDefined();
        expect(x.state === ListViewSourceState.Done);
        done();
      });
    });

  });

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

  static makeEmptySource(): TestListViewSource {
    return this.makeSource([]);
  }

  static makeLoadingSource(loadingTime: number = 1000): TestListViewSource {
    const readService: ReadService<TestModel> = {
      type: 'testModel',
      read(request: ReadRequest): Observable<ReadResponse<TestModel>> {
        return of({
          models: []
        }).pipe(
          delay(loadingTime),
          map(() => {
            return throwError(undefined);
          })
        ) as any;
      }
    };

    const readSource = new ReadSource<TestModel>(readService);
    readSource.input = of(1);

    const listReadSource = new TestListViewSource();
    listReadSource.readSource = readSource;

    return listReadSource;
  }

  static makeSource(keys: ModelKey[]): TestListViewSource {
    const readService: ReadService<TestModel> = new TestReadService<TestModel>('testModel', (x) => {
      return new TestModel(x);
    });

    const readSource = new ReadSource<TestModel>(readService);
    readSource.input = of(keys);

    const listReadSource = new TestListViewSource();
    listReadSource.readSource = readSource;

    return listReadSource;
  }

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
