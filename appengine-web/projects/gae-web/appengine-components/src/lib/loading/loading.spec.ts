import 'jasmine-expect';
import { LoadingContext } from './loading';

describe('LoadingContext', () => {

  it('should start in a loading state if nothing is specified', () => {
    const context: LoadingContext = new LoadingContext();
    expect(context.isLoading).toBeTrue();
  });

  it('should not start in a loading state if loading not specified.', () => {
    const context: LoadingContext = new LoadingContext({ isLoading: false });
    expect(context.isLoading).toBeFalse();
  });

});
