import 'jasmine-expect';
import { UniqueModel } from '@gae-web/appengine-utility/lib/model';
import { ModelKey } from './model';
import { AsyncModelCacheWrap, KeySafeAsyncModelCacheWrap, KeyedCacheChange } from './cache';
import { takeUntil } from 'rxjs/operators';
import { timer } from 'rxjs';

class TestModel implements UniqueModel {

  constructor(public readonly key: ModelKey) { }

  get modelKey() {
    return this.key;
  }

}

describe('SimpleCache', () => {

  // TODO

});

describe('AsyncModelCacheWrap', () => {

  let cache: AsyncModelCacheWrap<TestModel>;
  const testModelKey = 1;
  const testModel = new TestModel(testModelKey);

  beforeEach(() => {
    cache = new KeySafeAsyncModelCacheWrap<TestModel>();
  });

  describe('#asyncRead()', () => {

    // TODO: Test ignoredChanges is used properly.

    it('should return a hit for a model that exists in the cache.', (done) => {
      cache.putModel(testModel);
      cache.asyncRead([testModelKey], {}).subscribe((result) => {
        expect(result.hits).toContain(testModel);
        expect(result.misses).toBeEmptyArray();
        done();
      });
    });

    it('should return a miss for a models that does not exist in the cache.', (done) => {
      cache.asyncRead([testModelKey], {}).subscribe((result) => {
        expect(result.hits).toBeEmptyArray();
        expect(result.misses).toContain(testModelKey);
        done();
      });
    });

    it('should read when models are put into the cache.', (done) => {

      cache.asyncRead([1], {}).subscribe((result) => {
        expect(result.hits).toContain(testModel);
        done();
      });

      cache.putModel(testModel);

    });

    it('should read when models are removed from the cache.', (done) => {

      cache.putModel(testModel);
      cache.asyncRead([1], {}).subscribe((result) => {
        expect(result.misses).toContain(testModelKey);
        done();
      });

      cache.removeModel(testModel);

    });

    it('should read a second time when requested models are put into the cache a second time.', (done) => {
      let updates = 0;

      cache.asyncRead([1], {})
        .pipe(
          takeUntil(timer(100))
        )
        .subscribe((result) => {
          updates += 1;

          switch (updates) {
            case 1:
              expect(result.hits).toContain(testModel);
              cache.putModel(testModel); // Put Again
              break;
          }
        }).add(() => {
          expect(updates).toBe(2);  // Second put should have been ignored.
          done();
        });

      cache.putModel(testModel);
    });

    it('should not read a second time when put is ignored and models are put into the cache.', (done) => {
      let updates = 0;

      cache.asyncRead([1], { ignoredChanges: [KeyedCacheChange.Put] })
        .pipe(
          takeUntil(timer(100))
        )
        .subscribe((result) => {
          updates += 1;

          switch (updates) {
            case 1:
              expect(result.hits).toContain(testModel);
              cache.putModel(testModel); // Put Again
              break;
          }
        }).add(() => {
          expect(updates).toBe(1);  // Second put should have been ignored.
          done();
        });

      cache.putModel(testModel);
    });

  });

});
