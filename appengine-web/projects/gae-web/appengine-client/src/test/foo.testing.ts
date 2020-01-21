import {
  ReadSourceFactory, ReadSource, KeyQuerySource, QuerySourceConfiguration,
  ReadServiceReadSourceFactory, KeyTypedModelSearchSource, TypedModelSearchSourceConfiguration
} from '../lib/service/source';
import { TestQueryService, TestFoo, TestFooReadService, TestTypedModelSearchService } from '@gae-web/appengine-api';

export class TestFooTestReadSourceFactory extends ReadServiceReadSourceFactory<TestFoo> {

  constructor(public readonly testReadService = new TestFooReadService()) {
    super(testReadService);
  }

  public static makeReadSource(): ReadSource<TestFoo> {
    return new TestFooTestReadSourceFactory().makeSource();
  }

}

export class TestFooTestKeyQuerySource extends KeyQuerySource<TestFoo> {

  constructor(public readonly testQueryService = new TestQueryService<TestFoo>(), config?: QuerySourceConfiguration) {
    super(testQueryService, config);
  }

}

export class TestFooTestKeyTypedModelSearchSource extends KeyTypedModelSearchSource<TestFoo> {

  constructor(public readonly testSearchService = new TestTypedModelSearchService<TestFoo>(), config?: TypedModelSearchSourceConfiguration) {
    super(testSearchService, config);
  }

}
