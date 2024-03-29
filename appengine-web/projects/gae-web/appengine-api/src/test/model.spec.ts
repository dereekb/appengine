import 'jasmine-expect';
import { TestReadService } from './model';
import { TestFooReadService, TestFoo } from './foo.model';
import { first } from 'rxjs/operators';

describe('TestReadService', () => {

  const testReadService: TestReadService<TestFoo> = new TestFooReadService();

  describe('#filteredKeysSet', () => {

    it('should result in the filtered keys being returned as excluded.', async () => {
      testReadService.filteredKeysSet.add(1);

      const result = await testReadService.read({
        modelKeys: [1, 2]
      }).pipe(
        first()
      ).toPromise();

      expect(result.failed).toContain(1);
      expect(result.models).not.toBeEmptyArray();
    });

  });

});

