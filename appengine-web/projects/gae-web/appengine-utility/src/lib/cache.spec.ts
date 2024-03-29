import 'jasmine-expect';
import { UniqueModel } from '@gae-web/appengine-utility';
import { ModelKey } from './model';
import { AsyncModelCacheWrap, KeySafeAsyncModelCacheWrap, KeyedCacheChange, MapTimeKeyedCache, MapTimeKeyedCacheItem, TimedObservableCacheWrap } from './cache';
import { takeUntil, delay, filter } from 'rxjs/operators';
import { timer, Observable, of } from 'rxjs';
import { trigger } from '@angular/animations';

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

  fdescribe('model with number key', () => {

    describe('#get()', () => {

      beforeEach(() => {
        cache.putModel(testModel);
      });

      it('should return the requested model if the number key is passed as a string.', () => {
        const result = cache.get(testModelKey.toString());
        expect(result).toBe(testModel);
      });

      it('should return the requested model if the number key is passed as a number.', () => {
        const result = cache.get(testModelKey);
        expect(result).toBe(testModel);
      });

    });

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

const makeWaitForExpiration = (expirationTime: number) => {
  return (testFn: (doneFn) => void) => {
    return (done) => {
      // Delay the execution past the expiration time, then execute the function.
      of(0).pipe(delay(expirationTime)).subscribe({
        next: () => {
          testFn(done);
        }
      });
    };
  };
};

describe('MapTimeKeyedCache', () => {

  let cache: MapTimeKeyedCache<number, number>;
  let onExpiredFn: (x: MapTimeKeyedCacheItem<number, number>) => void;

  const expirationTime = 600;  // .6 second expiration.

  beforeEach(() => {
    cache = new MapTimeKeyedCache(expirationTime, (x) => {
      if (onExpiredFn) {
        onExpiredFn(x);
      }
    });
  });

  describe('item in cache', () => {

    const TEST_KEY = 1;
    const TEST_VALUE = 100;

    beforeEach(() => {
      cache.put(TEST_KEY, TEST_VALUE);
    });

    it('should exist in the cache', () => {
      const cachedValue = cache.get(TEST_KEY);
      expect(cachedValue).toBe(TEST_VALUE);
    });

    describe('on item expiration', () => {

      const waitForExpiration = makeWaitForExpiration(expirationTime);

      describe('get()', () => {

        it('should return undefined', waitForExpiration((done) => {
          expect(cache.get(TEST_KEY)).toBeUndefined();
          done();
        }));

      });

      describe('has()', () => {

        it('should return undefined', waitForExpiration((done) => {
          expect(cache.has(TEST_KEY)).toBeFalse();
          done();
        }));

      });

      it('should call the expired function', waitForExpiration((done) => {
        let hasExpired = false;

        onExpiredFn = (item) => {
          hasExpired = true;

          expect(item.key).toBe(TEST_KEY);
          expect(item.item).toBe(TEST_VALUE);
          expect(item.hasExpired).toBeTrue();
        };

        // Expire item on check.
        expect(cache.has(TEST_KEY)).toBeFalse();

        expect(hasExpired).toBeTrue();
        done();
      }));

    });

  });

});

describe('TimedObservableCacheWrap', () => {

  let cache: TimedObservableCacheWrap<number, number>;

  const expirationTime = 600;  // .6 second expiration.

  beforeEach(() => {
    cache = new TimedObservableCacheWrap(expirationTime);
  });

  describe('item in cache', () => {

    const TEST_KEY = 1;
    const TEST_VALUE = 100;

    beforeEach(() => {
      cache.put(TEST_KEY, TEST_VALUE);
    });

    describe('on item expiration', () => {

      const waitForExpiration = makeWaitForExpiration(expirationTime);

      const setupSendRemovedTest = (triggerFn: () => void) => {

        it('should send a removed from cache event when the item expires', waitForExpiration((done) => {

          // Subscribe to events
          cache.events.pipe(
            filter(x => x.change === KeyedCacheChange.Remove)
          ).subscribe({
            next: (x) => {
              done();
            }
          });

          // Trigger with has() or get()
          triggerFn();
        }));
      };

      describe('get()', () => {

        setupSendRemovedTest(() => {
          expect(cache.get(TEST_KEY)).toBeUndefined();
        });

      });

      describe('has()', () => {

        setupSendRemovedTest(() => {
          expect(cache.has(TEST_KEY)).toBeFalse();
        });

      });

      describe('load()', () => {

        setupSendRemovedTest(() => {
          const result = cache.load([TEST_KEY]);
          expect(result.misses).not.toBeEmptyArray();
          expect(result.misses[0]).toBe(TEST_KEY);
        });

      });

    });

  });

});
