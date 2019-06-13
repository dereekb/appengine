import { ReadSourceFactory, ReadSource, KeyQuerySource, QuerySourceConfiguration, ReadServiceReadSourceFactory } from '../lib/service/source';
import { TestFoo, TestFooReadService } from '@gae-web/appengine-api';
import { TestQueryService } from '@gae-web/appengine-api';

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
