import 'jasmine-expect';
import { ClientDeleteService, DeleteRequest } from './delete.service';
import { FOO_MODEL_TYPE, Foo, FooData, FooSerializer } from '../../../test/foo.model';
import { TestUtility } from '../../../test/test';
import { ValueUtility, ModelKey } from '@gae-web/appengine-utility';
import { LargeAtomicRequestError } from './error';
import { ApiResponse, ApiResponseJson } from '../../api';
import { of } from 'rxjs';

describe('ClientDeleteService', () => {

  describe('#delete()', () => {

    const routeConfig = TestUtility.testApiRouteConfig();

    let clientDeleteService: ClientDeleteService<Foo, FooData>;

    function setClientResult(data, success = true, status = 200, errors?, included?) {
      const httpClientSpy: { delete: jasmine.Spy } = jasmine.createSpyObj('HttpClient', ['delete']);

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

      httpClientSpy.delete.and.returnValue(of(mockHttpResponse));

      const httpClient = httpClientSpy as any;

      clientDeleteService = new ClientDeleteService<Foo, FooData>({
        httpClient,
        routeConfig,
        type: FOO_MODEL_TYPE,
        serializer: new FooSerializer()
      });
    }

    it(`should fail if more than ${ClientDeleteService.MAX_KEYS_ALLOWED_PER_REQUEST} models requested to be deleted.`, async () => {
      const modelKeys = ValueUtility.range(0, (ClientDeleteService.MAX_KEYS_ALLOWED_PER_REQUEST + 2));

      const deleteRequest: DeleteRequest = {
        modelKeys
      };

      setClientResult({});

      expect(() => clientDeleteService.delete(deleteRequest))
        .toThrowError(LargeAtomicRequestError);
    });

  });

});
