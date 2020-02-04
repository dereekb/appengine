import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Observable, of, throwError } from 'rxjs';
import { UniqueModel, ModelKey, NumberModelKey } from '@gae-web/appengine-utility';
import { ListViewSource, AbstractListViewSource, ListViewSourceState } from './source';
import { ReadSource } from '@gae-web/appengine-client';
import { ReadService, ReadRequest, ReadResponse, TestReadService, TestFoo } from '@gae-web/appengine-api';
import { delay, map, filter, first } from 'rxjs/operators';

describe('ListViewSource', () => {

  let testListViewSource: TestListViewSource;

  describe('while loading', () => {

    beforeEach(() => {
      testListViewSource = TestListViewSourceFactory.makeLoadingSource();
    });

    it('should be loading in the loading state', (done) => {
      testListViewSource.stream.pipe(
        filter((x) => x.state === ListViewSourceState.Loading),
        first()
      ).subscribe((x) => {
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
      testListViewSource.stream.pipe(
        filter((x) => x.state === ListViewSourceState.Done),
        first()
      ).subscribe((x) => {
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
      testListViewSource.stream.pipe(
        filter((x) => x.state === ListViewSourceState.Done),
        first()
      ).subscribe((x) => {
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
      testListViewSource.stream.pipe(
        filter((x) => x.state === ListViewSourceState.Error),
        first()
      ).subscribe((x) => {
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
    const readService: ReadService<TestFoo> = {
      type: 'testModel',
      read(request: ReadRequest): Observable<ReadResponse<TestFoo>> {
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

    const readSource = new ReadSource<TestFoo>(readService);
    readSource.input = of(1);

    const listReadSource = new TestListViewSource();
    listReadSource.readSource = readSource;

    return listReadSource;
  }

  static makeSource(keys: ModelKey[]): TestListViewSource {
    const readService: ReadService<TestFoo> = new TestReadService<TestFoo>('testModel', (x) => {
      return new TestFoo(x as NumberModelKey);
    });

    const readSource = new ReadSource<TestFoo>(readService);
    readSource.input = of(keys);

    const listReadSource = new TestListViewSource();
    listReadSource.readSource = readSource;

    return listReadSource;
  }

  /**
   * Creates a source that only returns errors.
   */
  static makeErrorSource(error: any = new Error('Test')): TestListViewSource {
    const readService: ReadService<TestFoo> = {
      type: 'testModel',
      read(request: ReadRequest): Observable<ReadResponse<TestFoo>> {
        return throwError(error);
      }
    };

    const readSource = new ReadSource<TestFoo>(readService);
    readSource.input = of(1);

    const listReadSource = new TestListViewSource();
    listReadSource.readSource = readSource;

    return listReadSource;
  }

}

class TestListViewSource extends AbstractListViewSource<TestFoo> {

  public set readSource(source: ReadSource<TestFoo> | undefined) {
    super.setSource(source);
  }

  // MARK: ListViewSource
  public canLoadMore(): boolean {
    return false;
  }

  public more(): void {
    // Do nothing.
  }

  public refresh(): void {
    // Do nothing.
  }

}
