import { FOO_MODEL_TYPE, ApiRouteConfiguration, FooSerializer } from '@gae-web/appengine-api';
import { HttpClient } from '@angular/common/http';
import {
  FooClientCreateService, FooClientReadService, FooClientUpdateService, FooClientDeleteService,
  FooClientQueryService, FooServiceWrapper, FooReadService, FooQueryService, FooDefaultQueryConfiguration,
  FooCachedKeySourceCache, FooCreateService, FooUpdateService, FooDeleteService, FooReadSourceFactory
} from './foo.service';
import { ModuleWithProviders, NgModule } from '@angular/core';
import { GaeClientModule } from '../lib/client.module';
import { ModelServiceWrapperSet } from '../lib/service/model.service';

export function fooServiceFactory(make: (config: any) => any) {
  return (s: FooSerializer, h: HttpClient, r: ApiRouteConfiguration) => {
    return make({
      type: FOO_MODEL_TYPE,
      serializer: s,
      httpClient: h,
      routeConfig: r
    });
  };
}

export function fooServiceWrapperFactory(set: ModelServiceWrapperSet) {
  return set.initWrapper({
    type: FOO_MODEL_TYPE,
    init: (fooWrapper, wrapperSet) => {
      // NOTE: Can bind here for watching specific events.
    }
  });
}

export function fooClientCreateServiceFactory(s: FooSerializer, h: HttpClient, r: ApiRouteConfiguration) {
  return fooServiceFactory((c) => new FooClientCreateService(c)).apply(null, arguments);
}

export function fooClientReadServiceFactory(s: FooSerializer, h: HttpClient, r: ApiRouteConfiguration) {
  return fooServiceFactory((c) => new FooClientReadService(c)).apply(null, arguments);
}

export function fooClientUpdateServiceFactory(s: FooSerializer, h: HttpClient, r: ApiRouteConfiguration) {
  return fooServiceFactory((c) => new FooClientUpdateService(c)).apply(null, arguments);
}

export function fooClientDeleteServiceFactory(s: FooSerializer, h: HttpClient, r: ApiRouteConfiguration) {
  return fooServiceFactory((c) => new FooClientDeleteService(c)).apply(null, arguments);
}

export function fooClientQueryServiceFactory(s: FooSerializer, h: HttpClient, r: ApiRouteConfiguration) {
  return fooServiceFactory((c) => new FooClientQueryService(c)).apply(null, arguments);
}

export function fooCreateServiceFactory(wrapper: FooServiceWrapper, service: FooClientCreateService) {
  return wrapper.wrapCreateService(service);
}

export function fooReadServiceFactory(wrapper: FooServiceWrapper, service: FooClientReadService) {
  return wrapper.wrapReadService(service);
}

export function fooUpdateServiceFactory(wrapper: FooServiceWrapper, service: FooClientUpdateService) {
  return wrapper.wrapUpdateService(service);
}

export function fooDeleteServiceFactory(wrapper: FooServiceWrapper, service: FooClientDeleteService) {
  return wrapper.wrapDeleteService(service);
}

export function fooQueryServiceFactory(wrapper: FooServiceWrapper, readService: FooReadService, queryService: FooClientQueryService) {
  return wrapper.wrapQueryService(readService, queryService);
}

export function fooReadSourceFactoryFn(wrapper: FooServiceWrapper, service: FooReadService) {
  return wrapper.makeReadSourceFactory(service);
}

export function fooCachedKeySourceCacheFactoryFn(wrapper: FooServiceWrapper, queryService: FooQueryService, config: FooDefaultQueryConfiguration) {
  return new FooCachedKeySourceCache(queryService, wrapper.makeKeyedPredictiveOrderedQueryDelegate(), config);
}

@NgModule({
  imports: [GaeClientModule]
})
export class FooClientModule {

  static forApp(): ModuleWithProviders {
    const moduleWithProviders = {
      ngModule: FooClientModule,
      providers: [

        // MARK: Foo
        FooSerializer,
        {
          provide: FooClientCreateService,
          useFactory: fooClientCreateServiceFactory,
          deps: [FooSerializer, HttpClient, ApiRouteConfiguration]
        },
        {
          provide: FooClientReadService,
          useFactory: fooClientReadServiceFactory,
          deps: [FooSerializer, HttpClient, ApiRouteConfiguration]
        },
        {
          provide: FooClientUpdateService,
          useFactory: fooClientUpdateServiceFactory,
          deps: [FooSerializer, HttpClient, ApiRouteConfiguration]
        },
        {
          provide: FooClientDeleteService,
          useFactory: fooClientDeleteServiceFactory,
          deps: [FooSerializer, HttpClient, ApiRouteConfiguration]
        },
        {
          provide: FooClientQueryService,
          useFactory: fooClientQueryServiceFactory,
          deps: [FooSerializer, HttpClient, ApiRouteConfiguration]
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
    return moduleWithProviders;
  }
}
