import 'jasmine-expect';
import { ClientCreateService, CreateRequest } from './create.service';
import { FOO_MODEL_TYPE, Foo, FooData, FooSerializer } from '../../../test/foo.model';
import { TestUtility } from '../../../test/test';
import { ValueUtility, ModelKey } from '@gae-web/appengine-utility';
import { LargeAtomicRequestError } from './error';
import { ApiResponse, ApiResponseJson } from '../../api';
import { of } from 'rxjs';

describe('ClientCreateService', () => {

  describe('#create()', () => {

    const routeConfig = TestUtility.testApiRouteConfig();

    let clientCreateService: ClientCreateService<Foo, FooData>;

    function setClientResult(data, success = true, status = 200, errors?, included?) {
      const httpClientSpy: { create: jasmine.Spy } = jasmine.createSpyObj('HttpClient', ['create']);

      const apiResponseData: ApiResponseJson = {
        success,
        data,
        included,
        errors
      };

      const mockHttpResponse = {
        status,
        body: apiResponseData
      };

      httpClientSpy.create.and.returnValue(of(mockHttpResponse));

      const httpClient = httpClientSpy as any;

      clientCreateService = new ClientCreateService<Foo, FooData>({
        httpClient,
        routeConfig,
        type: FOO_MODEL_TYPE,
        serializer: new FooSerializer()
      });
    }

    it(`should fail if more than ${ClientCreateService.MAX_TEMPLATES_ALLOWED_PER_REQUEST} models requested to be created.`, async () => {
      const modelKeys = ValueUtility.range(0, (ClientCreateService.MAX_TEMPLATES_ALLOWED_PER_REQUEST + 2));

      const createRequest: CreateRequest<Foo> = {
        templates: modelKeys.map((x) => new Foo(x))
      };

      setClientResult({});

      expect(() => clientCreateService.create(createRequest))
        .toThrowError(LargeAtomicRequestError);
    });

  });

});
