import { TEST_FOO_MODEL_TYPE, ApiRouteConfiguration, TestFooSerializer, TestFoo, QueryService } from '@gae-web/appengine-api';
import { HttpClient } from '@angular/common/http';
import {
  TestFooClientCreateService, TestFooClientReadService, TestFooClientUpdateService, TestFooClientDeleteService,
  TestFooClientQueryService, TestFooServiceWrapper, TestFooReadService, TestFooQueryService, TestFooDefaultQueryConfiguration,
  TestFooCachedKeySourceCache, TestFooCreateService, TestFooUpdateService, TestFooDeleteService, TestFooReadSourceFactory
} from './foo.service';
import { ModuleWithProviders, NgModule } from '@angular/core';
import { GaeClientModule } from '../lib/client.module';
import { ModelServiceWrapper, ModelServiceWrapperSet, ModelCreateService } from '../lib/service/model.service';
import { UniqueModel, SourceFactory } from '@gae-web/appengine-utility';
import { ModelReadService, ModelDeleteService, ModelUpdateService } from '../lib/service/crud.service';
import { ModelQueryService } from '../lib/service/query.service';

export function fooServiceFactory(make: (config: any) => any) {
  return (s: TestFooSerializer, h: HttpClient, r: ApiRouteConfiguration) => {
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

export function fooClientCreateServiceFactory(s: TestFooSerializer, h: HttpClient, r: ApiRouteConfiguration) {
  return fooServiceFactory((c) => new TestFooClientCreateService(c)).apply(null, arguments);
}

export function fooClientReadServiceFactory(s: TestFooSerializer, h: HttpClient, r: ApiRouteConfiguration) {
  return fooServiceFactory((c) => new TestFooClientReadService(c)).apply(null, arguments);
}

export function fooClientUpdateServiceFactory(s: TestFooSerializer, h: HttpClient, r: ApiRouteConfiguration) {
  return fooServiceFactory((c) => new TestFooClientUpdateService(c)).apply(null, arguments);
}

export function fooClientDeleteServiceFactory(s: TestFooSerializer, h: HttpClient, r: ApiRouteConfiguration) {
  return fooServiceFactory((c) => new TestFooClientDeleteService(c)).apply(null, arguments);
}

export function fooClientQueryServiceFactory(s: TestFooSerializer, h: HttpClient, r: ApiRouteConfiguration) {
  return fooServiceFactory((c) => new TestFooClientQueryService(c)).apply(null, arguments);
}

export function fooCreateServiceFactory(wrapper: TestFooServiceWrapper, service: TestFooClientCreateService): ModelCreateService<TestFoo> {
  return wrapper.wrapCreateService(service);
}

export function fooReadServiceFactory(wrapper: TestFooServiceWrapper, service: TestFooClientReadService): ModelReadService<TestFoo> {
  return wrapper.wrapReadService(service);
}

export function fooUpdateServiceFactory(wrapper: TestFooServiceWrapper, service: TestFooClientUpdateService): ModelUpdateService<TestFoo> {
  return wrapper.wrapUpdateService(service);
}

export function fooDeleteServiceFactory(wrapper: TestFooServiceWrapper, service: TestFooClientDeleteService): ModelDeleteService<TestFoo> {
  return wrapper.wrapDeleteService(service);
}

export function fooQueryServiceFactory(wrapper: TestFooServiceWrapper, readService: TestFooReadService, queryService: TestFooClientQueryService): ModelQueryService<TestFoo> {
  return wrapper.wrapQueryService(readService, queryService);
}

export function fooReadSourceFactoryFn(wrapper: TestFooServiceWrapper, service: TestFooReadService): SourceFactory<TestFoo> {
  return wrapper.makeReadSourceFactory(service);
}

export function fooCachedKeySourceCacheFactoryFn(wrapper: TestFooServiceWrapper, queryService: TestFooQueryService, config: TestFooDefaultQueryConfiguration) {
  return new TestFooCachedKeySourceCache(queryService, wrapper.makeKeyedPredictiveOrderedQueryDelegate(), config);
}

@NgModule({
  imports: [GaeClientModule]
})
export class TestFooClientModule {

  static forApp(): ModuleWithProviders {
    const moduleWithProviders = {
      ngModule: TestFooClientModule,
      providers: [

        // MARK: TestFoo
        TestFooSerializer,
        {
          provide: TestFooClientCreateService,
          useFactory: fooClientCreateServiceFactory,
          deps: [TestFooSerializer, HttpClient, ApiRouteConfiguration]
        },
        {
          provide: TestFooClientReadService,
          useFactory: fooClientReadServiceFactory,
          deps: [TestFooSerializer, HttpClient, ApiRouteConfiguration]
        },
        {
          provide: TestFooClientUpdateService,
          useFactory: fooClientUpdateServiceFactory,
          deps: [TestFooSerializer, HttpClient, ApiRouteConfiguration]
        },
        {
          provide: TestFooClientDeleteService,
          useFactory: fooClientDeleteServiceFactory,
          deps: [TestFooSerializer, HttpClient, ApiRouteConfiguration]
        },
        {
          provide: TestFooClientQueryService,
          useFactory: fooClientQueryServiceFactory,
          deps: [TestFooSerializer, HttpClient, ApiRouteConfiguration]
        },
        {
          provide: TestFooReadSourceFactory,
          useFactory: fooReadSourceFactoryFn,
          deps: [TestFooClientReadService]
        },

        // MARK: Wrapped
        {
          provide: TestFooServiceWrapper,
          useFactory: fooServiceWrapperFactory,
          deps: [ModelServiceWrapperSet]
        },
        {
          provide: TestFooCreateService,
          useFactory: fooCreateServiceFactory,
          deps: [TestFooServiceWrapper, TestFooClientCreateService]
        },
        {
          provide: TestFooReadService,
          useFactory: fooReadServiceFactory,
          deps: [TestFooServiceWrapper, TestFooClientReadService]
        },
        {
          provide: TestFooUpdateService,
          useFactory: fooUpdateServiceFactory,
          deps: [TestFooServiceWrapper, TestFooClientUpdateService]
        },
        {
          provide: TestFooDeleteService,
          useFactory: fooDeleteServiceFactory,
          deps: [TestFooServiceWrapper, TestFooClientDeleteService]
        },
        {
          provide: TestFooQueryService,
          useFactory: fooQueryServiceFactory,
          deps: [TestFooServiceWrapper, TestFooReadService, TestFooClientQueryService]
        },
        {
          provide: TestFooReadSourceFactory,
          useFactory: fooReadSourceFactoryFn,
          deps: [TestFooServiceWrapper, TestFooReadService]
        },

        TestFooDefaultQueryConfiguration,
        {
          provide: TestFooCachedKeySourceCache,
          useFactory: fooCachedKeySourceCacheFactoryFn,
          deps: [TestFooServiceWrapper, TestFooQueryService, TestFooDefaultQueryConfiguration]
        }
      ]
    };
    return moduleWithProviders;
  }
}
