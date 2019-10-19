import { TEST_FOO_MODEL_TYPE, ApiModuleRouteConfiguration } from '@gae-web/appengine-api';
import { HttpClient } from '@angular/common/http';
import {
  FooClientCreateService, FooClientReadService, FooClientUpdateService, FooClientDeleteService,
  FooClientQueryService, FooServiceWrapper, FooReadService, FooQueryService, FooDefaultQueryConfiguration,
  FooCachedKeySourceCache, FooCreateService, FooUpdateService, FooDeleteService, FooReadSourceFactory
} from './foo.service';
import { ModuleWithProviders, NgModule, Optional, SkipSelf } from '@angular/core';
import { UniqueModel, SourceFactory } from '@gae-web/appengine-utility';
import {
  GaeClientModule, ModelServiceWrapper, ModelServiceWrapperSet,
  ModelCreateService, ModelReadService, ModelDeleteService, ModelUpdateService, ModelQueryService
} from '@gae-web/appengine-client';
import { FooSerializer } from './foo.data';
import { Foo } from './foo';
import { TEST_API_ROUTE_CONFIGURATION_TOKEN, TestApiModule } from '../test.api';

export function fooServiceFactory(make: (config: any) => any) {
  return (s: FooSerializer, h: HttpClient, r: ApiModuleRouteConfiguration) => {
    return make({
      type: TEST_FOO_MODEL_TYPE,
      serializer: s,
      httpClient: h,
      routeConfig: r
    });
  };
}

export function fooServiceWrapperFactory(set: ModelServiceWrapperSet): ModelServiceWrapper<UniqueModel> {
  return set.initWrapper({
    type: TEST_FOO_MODEL_TYPE,
    init: (fooWrapper, wrapperSet) => {
      // NOTE: Can bind here for watching specific events.
    }
  });
}

export function fooClientCreateServiceFactory(s: FooSerializer, h: HttpClient, r: ApiModuleRouteConfiguration) {
  return fooServiceFactory((c) => new FooClientCreateService(c)).apply(null, arguments);
}

export function fooClientReadServiceFactory(s: FooSerializer, h: HttpClient, r: ApiModuleRouteConfiguration) {
  return fooServiceFactory((c) => new FooClientReadService(c)).apply(null, arguments);
}

export function fooClientUpdateServiceFactory(s: FooSerializer, h: HttpClient, r: ApiModuleRouteConfiguration) {
  return fooServiceFactory((c) => new FooClientUpdateService(c)).apply(null, arguments);
}

export function fooClientDeleteServiceFactory(s: FooSerializer, h: HttpClient, r: ApiModuleRouteConfiguration) {
  return fooServiceFactory((c) => new FooClientDeleteService(c)).apply(null, arguments);
}

export function fooClientQueryServiceFactory(s: FooSerializer, h: HttpClient, r: ApiModuleRouteConfiguration) {
  return fooServiceFactory((c) => new FooClientQueryService(c)).apply(null, arguments);
}

export function fooCreateServiceFactory(wrapper: FooServiceWrapper, service: FooClientCreateService): ModelCreateService<Foo> {
  return wrapper.wrapCreateService(service);
}

export function fooReadServiceFactory(wrapper: FooServiceWrapper, service: FooClientReadService): ModelReadService<Foo> {
  return wrapper.wrapReadService(service);
}

export function fooUpdateServiceFactory(wrapper: FooServiceWrapper, service: FooClientUpdateService): ModelUpdateService<Foo> {
  return wrapper.wrapUpdateService(service);
}

export function fooDeleteServiceFactory(wrapper: FooServiceWrapper, service: FooClientDeleteService): ModelDeleteService<Foo> {
  return wrapper.wrapDeleteService(service);
}

export function fooQueryServiceFactory(wrapper: FooServiceWrapper, readService: FooReadService, queryService: FooClientQueryService): ModelQueryService<Foo> {
  return wrapper.wrapQueryService(readService, queryService);
}

export function fooReadSourceFactoryFn(wrapper: FooServiceWrapper, service: FooReadService): SourceFactory<Foo> {
  return wrapper.makeReadSourceFactory(service);
}

export function fooCachedKeySourceCacheFactoryFn(wrapper: FooServiceWrapper, queryService: FooQueryService, config: FooDefaultQueryConfiguration) {
  return new FooCachedKeySourceCache(queryService, wrapper.makeKeyedPredictiveOrderedQueryDelegate(), config);
}

@NgModule({
  imports: [GaeClientModule, TestApiModule]
})
export class FooClientModule {

  constructor(@Optional() @SkipSelf() parentModule: FooClientModule) {
    if (parentModule) {
      throw new Error('FooClientModule is already loaded.');
    }
  }

  static forApp(): ModuleWithProviders<any> {
    return {
      ngModule: FooClientModule,
      providers: [

        // MARK: Foo
        FooSerializer,
        {
          provide: FooClientCreateService,
          useFactory: fooClientCreateServiceFactory,
          deps: [FooSerializer, HttpClient, TEST_API_ROUTE_CONFIGURATION_TOKEN]
        },
        {
          provide: FooClientReadService,
          useFactory: fooClientReadServiceFactory,
          deps: [FooSerializer, HttpClient, TEST_API_ROUTE_CONFIGURATION_TOKEN]
        },
        {
          provide: FooClientUpdateService,
          useFactory: fooClientUpdateServiceFactory,
          deps: [FooSerializer, HttpClient, TEST_API_ROUTE_CONFIGURATION_TOKEN]
        },
        {
          provide: FooClientDeleteService,
          useFactory: fooClientDeleteServiceFactory,
          deps: [FooSerializer, HttpClient, TEST_API_ROUTE_CONFIGURATION_TOKEN]
        },
        {
          provide: FooClientQueryService,
          useFactory: fooClientQueryServiceFactory,
          deps: [FooSerializer, HttpClient, TEST_API_ROUTE_CONFIGURATION_TOKEN]
        },
        {
          provide: FooReadSourceFactory,
          useFactory: fooReadSourceFactoryFn,
          deps: [FooClientReadService]
        },

        // MARK: Wrapped
        {
          provide: FooServiceWrapper,
          useFactory: fooServiceWrapperFactory,
          deps: [ModelServiceWrapperSet]
        },
        {
          provide: FooCreateService,
          useFactory: fooCreateServiceFactory,
          deps: [FooServiceWrapper, FooClientCreateService]
        },
        {
          provide: FooReadService,
          useFactory: fooReadServiceFactory,
          deps: [FooServiceWrapper, FooClientReadService]
        },
        {
          provide: FooUpdateService,
          useFactory: fooUpdateServiceFactory,
          deps: [FooServiceWrapper, FooClientUpdateService]
        },
        {
          provide: FooDeleteService,
          useFactory: fooDeleteServiceFactory,
          deps: [FooServiceWrapper, FooClientDeleteService]
        },
        {
          provide: FooQueryService,
          useFactory: fooQueryServiceFactory,
          deps: [FooServiceWrapper, FooReadService, FooClientQueryService]
        },
        {
          provide: FooReadSourceFactory,
          useFactory: fooReadSourceFactoryFn,
          deps: [FooServiceWrapper, FooReadService]
        },

        FooDefaultQueryConfiguration,
        {
          provide: FooCachedKeySourceCache,
          useFactory: fooCachedKeySourceCacheFactoryFn,
          deps: [FooServiceWrapper, FooQueryService, FooDefaultQueryConfiguration]
        }
      ]
    };
  }

}
