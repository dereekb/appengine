import 'jasmine-expect';
import { of } from 'rxjs';
import { ApiResponseJson } from '../../../../api';
import { MISSING_REQUIRED_RESOURCE_ERROR_CODE, LargeAtomicRequestError } from '../../../crud/error';
import { ModelKey, ValueUtility } from '@gae-web/appengine-utility';
import { ClientQueryService, QueryRequest } from './query.service';
import { TestFoo, TestFooData, TEST_FOO_MODEL_TYPE, TestFooSerializer } from '../../../../../test/foo.model';
import { TestUtility } from '../../../../../test/test';

describe('ClientQueryService', () => {

  describe('#query()', () => {

    const queryCursor = 'CgA=';
    const routeConfig = TestUtility.testApiRouteConfig();

    let clientQueryService: ClientQueryService<TestFoo, TestFooData>;

    function setClientResult(body, status = 200) {
      const httpClientSpy: { get: jasmine.Spy } = jasmine.createSpyObj('HttpClient', ['get']);

      const mockHttpResponse = {
        status,
        body
      };

      httpClientSpy.get.and.returnValue(of(mockHttpResponse));

      const httpClient = httpClientSpy as any;

      clientQueryService = new ClientQueryService<TestFoo, TestFooData>({
        httpClient,
        routeConfig,
        type: TEST_FOO_MODEL_TYPE,
        serializer: new TestFooSerializer()
      });

    }

    function setClientResultResultsModelsForKeys(keys: ModelKey[]) {
      const body = JSON.parse(`{"data":{"type":"search_results","data":{"modelType":"${ TEST_FOO_MODEL_TYPE }","data":[${ keys }],"cursor":"${queryCursor}"}}}`);
      setClientResult(body);
    }

    it(`should properly parse keys from the response.`, (done) => {
      const modelKeys = ValueUtility.range(0, 100);

      const queryRequest: QueryRequest = {
        isKeysOnly: true
      };

      setClientResultResultsModelsForKeys(modelKeys);

      clientQueryService.query(queryRequest).subscribe((result) => {
        expect(result.keyResults).toBeArray();
        expect(result.keyResults[0]).toBeNumber();
        done();
      });
    });

    it(`should properly parse the cursor response.`, (done) => {
      const modelKeys = ValueUtility.range(0, 100);

      const queryRequest: QueryRequest = {
        isKeysOnly: true
      };

      setClientResultResultsModelsForKeys(modelKeys);

      clientQueryService.query(queryRequest).subscribe((result) => {
        expect(result.cursor).toBe(queryCursor);
        done();
      });
    });

  });

});
