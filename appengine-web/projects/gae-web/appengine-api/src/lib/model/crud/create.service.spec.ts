import 'jasmine-expect';
import { ClientCreateService, CreateRequest } from './create.service';
import { TEST_FOO_MODEL_TYPE, TestFoo, TestFooData, TestFooSerializer } from '../../../test/foo.model';
import { TestUtility } from '../../../test/test';
import { ValueUtility, ModelKey } from '@gae-web/appengine-utility';
import { LargeAtomicRequestError } from './error';
import { ApiResponse, ApiResponseJson } from '../../api';
import { of } from 'rxjs';

describe('ClientCreateService', () => {

  describe('#create()', () => {

    const routeConfig = TestUtility.testApiRouteConfig();

    let clientCreateService: ClientCreateService<TestFoo, TestFooData>;

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

      clientCreateService = new ClientCreateService<TestFoo, TestFooData>({
        httpClient,
        routeConfig,
        type: TEST_FOO_MODEL_TYPE,
        serializer: new TestFooSerializer()
      });
    }

    it(`should fail if more than ${ClientCreateService.MAX_TEMPLATES_ALLOWED_PER_REQUEST} models requested to be created.`, async () => {
      const modelKeys = ValueUtility.range(0, (ClientCreateService.MAX_TEMPLATES_ALLOWED_PER_REQUEST + 2));

      const createRequest: CreateRequest<TestFoo> = {
        templates: modelKeys.map((x) => new TestFoo(x))
      };

      setClientResult({});

      expect(() => clientCreateService.create(createRequest))
        .toThrowError(LargeAtomicRequestError);
    });

  });

});
