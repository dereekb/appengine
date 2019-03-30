import 'jasmine-expect';
import { TestReadService } from './model';
import { FooReadService, Foo } from './foo.model';

describe('TestReadService', () => {

  const testReadService: TestReadService<Foo> = new FooReadService();

  describe('#filteredKeysSet', () => {

    it('should result in the filtered keys being returned as excluded.', async () => {
      testReadService.filteredKeysSet.add(1);

      const result = await testReadService.read({
        modelKeys: [1, 2]
      }).toPromise();

      expect(result.failed).toContain(1);
      expect(result.models).not.toBeEmptyArray();
    });

  });

});

