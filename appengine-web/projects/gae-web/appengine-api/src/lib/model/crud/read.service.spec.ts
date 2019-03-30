import 'jasmine-expect';
import { ClientReadService, ReadRequest } from './read.service';
import { FOO_MODEL_TYPE, Foo, FooData, FooSerializer } from '../../../test/foo.model';
import { TestUtility } from '../../../test/test';
import { ValueUtility, ModelKey } from '@gae-web/appengine-utility';
import { LargeAtomicRequestError, MISSING_REQUIRED_RESOURCE_ERROR_CODE } from './error';
import { ApiResponse, ApiResponseJson } from '../../api';
import { of } from 'rxjs';

describe('ClientReadService', () => {

  describe('#read()', () => {

    const routeConfig = TestUtility.testApiRouteConfig();

    let clientReadService: ClientReadService<Foo, FooData>;

    function setClientResult(data, success = true, status = 200, errors?, included?) {
      const httpClientSpy: { get: jasmine.Spy } = jasmine.createSpyObj('HttpClient', ['get']);

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

      httpClientSpy.get.and.returnValue(of(mockHttpResponse));

      const httpClient = httpClientSpy as any;

      clientReadService = new ClientReadService<Foo, FooData>({
        httpClient,
        routeConfig,
        type: FOO_MODEL_TYPE,
        serializer: new FooSerializer()
      });

    }


    function setClientResultResultsModelsForKeys(keys: ModelKey[], failedKeys?: ModelKey[], atomic = false) {
      let data;

      const errors = [];
      let status = 200;

      if (failedKeys && failedKeys.length > 0) {
        const missingKeysError = {
          code: MISSING_REQUIRED_RESOURCE_ERROR_CODE,
          title: 'Missing Resource',
          data: {
            resources: failedKeys.map((x) => String(x))
          }
        };

        errors.push(missingKeysError);

        if (atomic) {
          status = 410;
        }
      }

      const success = (status === 200);

      if (success) {
        data = {
          type: 'Foo',
          data: keys.map((x) => new FooData(x).toJSON())
        } as any;
      }

      setClientResult(data, success, status, errors);
    }

    it('should return all requested models.', async () => {
      const modelKeys = [1, 2, 3];

      const readRequest: ReadRequest = {
        atomic: false,
        modelKeys
      };

      setClientResultResultsModelsForKeys(modelKeys);

      const response = await clientReadService.read(readRequest).toPromise();
      expect(response.models).toBeArrayOfObjects();
    });

    it('should return all failed models if not atomic.', async () => {
      const modelKeys = [1, 2, 3];

      const readRequest: ReadRequest = {
        atomic: false,
        modelKeys
      };

      setClientResultResultsModelsForKeys(modelKeys, [3]);

      const response = await clientReadService.read(readRequest).toPromise();
      expect(response.models).toBeArrayOfObjects();
      expect(response.failed).toBeArray();
      expect(response.failed).toContain(String(3));
    });

    it('should fail if atomic and one or more item cannot be returned.', async () => {
      const modelKeys = [1, 2, 3];

      const readRequest: ReadRequest = {
        atomic: true,
        modelKeys
      };

      setClientResultResultsModelsForKeys(modelKeys, [3], true);

      const response = await clientReadService.read(readRequest).toPromise();
      expect(response.models).toBeEmptyArray();
      expect(response.failed).toBeArray();
    });

    it(`should fail if an atomic read request of over ${ClientReadService.MAX_KEYS_ALLOWED_PER_REQUEST} keys throws an error.`, async () => {
      const readRequest: ReadRequest = {
        atomic: true,
        modelKeys: ValueUtility.range(0, (ClientReadService.MAX_KEYS_ALLOWED_PER_REQUEST + 2))
      };

      expect(() => clientReadService.read(readRequest))
        .toThrowError(LargeAtomicRequestError);
    });

    it(`should not fail if a non-atomic read request of over ${ClientReadService.MAX_KEYS_ALLOWED_PER_REQUEST} keys throws an error.`, (done) => {
      const modelKeys = ValueUtility.range(0, (ClientReadService.MAX_KEYS_ALLOWED_PER_REQUEST + 2));

      const readRequest: ReadRequest = {
        atomic: false,
        modelKeys
      };

      setClientResultResultsModelsForKeys(modelKeys);

      clientReadService.read(readRequest).subscribe((result) => {
        expect(result.models).toBeArray();
        done();
      });
    });

  });

});
