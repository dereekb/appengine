import { TestFoo, TestFooData, ClientQueryService, ClientCreateService, ClientReadService, ClientUpdateService, ClientDeleteService } from '@gae-web/appengine-api';
import { ModelServiceWrapper, ModelCreateService } from '../lib/service/model.service';
import { ModelQueryService, KeyedPredictiveOrderedQueryDelegate } from '../lib/service/query.service';
import { ModelReadService, ModelUpdateService, ModelDeleteService } from '../lib/service/crud.service';
import { ReadSourceFactory, QuerySourceConfiguration, CachedKeySourceCache, KeyQuerySource } from '../lib/service/source';

export class TestFooClientQueryService extends ClientQueryService<TestFoo, TestFooData> { }

export class TestFooClientCreateService extends ClientCreateService<TestFoo, TestFooData> { }

export class TestFooClientReadService extends ClientReadService<TestFoo, TestFooData> { }

export class TestFooClientUpdateService extends ClientUpdateService<TestFoo, TestFooData> { }

export class TestFooClientDeleteService extends ClientDeleteService<TestFoo, TestFooData> { }

export class TestFooServiceWrapper extends ModelServiceWrapper<TestFoo> { }

export class TestFooQueryService extends ModelQueryService<TestFoo> { }

export class TestFooCreateService extends ModelCreateService<TestFoo> { }

export class TestFooReadService extends ModelReadService<TestFoo> { }

export class TestFooUpdateService extends ModelUpdateService<TestFoo> { }

export class TestFooDeleteService extends ModelDeleteService<TestFoo> { }

export class TestFooReadSourceFactory extends ReadSourceFactory<TestFoo> { }

// MARK: Query Cache
export class TestFooDefaultQueryConfiguration implements QuerySourceConfiguration {

  readonly filters = {
    parameters: {
      date: '_,,DESC' // Sort by newest first
    }
  };

}

export class TestFooCachedKeySourceCache extends CachedKeySourceCache<TestFoo> {

  constructor(queryService: TestFooQueryService, delegate: KeyedPredictiveOrderedQueryDelegate<TestFoo>, config?: QuerySourceConfiguration) {
    super(new KeyQuerySource<TestFoo>(queryService, config || new TestFooDefaultQueryConfiguration()), delegate);
  }

}
