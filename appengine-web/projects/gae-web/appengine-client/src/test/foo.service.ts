import { Foo, FooData, ClientQueryService, ClientCreateService, ClientReadService, ClientUpdateService, ClientDeleteService } from '@gae-web/appengine-api';
import { ModelServiceWrapper, ModelCreateService } from '../lib/service/model.service';
import { ModelQueryService, KeyedPredictiveOrderedQueryDelegate } from '../lib/service/query.service';
import { ModelReadService, ModelUpdateService, ModelDeleteService } from '../lib/service/crud.service';
import { ReadSourceFactory, QuerySourceConfiguration, CachedKeySourceCache, KeyQuerySource } from '../lib/service/source';

export class FooClientQueryService extends ClientQueryService<Foo, FooData> { }

export class FooClientCreateService extends ClientCreateService<Foo, FooData> { }

export class FooClientReadService extends ClientReadService<Foo, FooData> { }

export class FooClientUpdateService extends ClientUpdateService<Foo, FooData> { }

export class FooClientDeleteService extends ClientDeleteService<Foo, FooData> { }

export class FooServiceWrapper extends ModelServiceWrapper<Foo> { }

export class FooQueryService extends ModelQueryService<Foo> { }

export class FooCreateService extends ModelCreateService<Foo> { }

export class FooReadService extends ModelReadService<Foo> { }

export class FooUpdateService extends ModelUpdateService<Foo> { }

export class FooDeleteService extends ModelDeleteService<Foo> { }

export class FooReadSourceFactory extends ReadSourceFactory<Foo> { }

// MARK: Query Cache
export class FooDefaultQueryConfiguration implements QuerySourceConfiguration {

  readonly filters = {
    date: '_,,DESC' // Sort by newest first
  };

}

export class FooCachedKeySourceCache extends CachedKeySourceCache<Foo> {

  constructor(queryService: FooQueryService, delegate: KeyedPredictiveOrderedQueryDelegate<Foo>, config?: QuerySourceConfiguration) {
    super(new KeyQuerySource<Foo>(queryService, config || new FooDefaultQueryConfiguration()), delegate);
  }

}
