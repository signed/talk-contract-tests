import { SubtractionClient } from '../src/SubtractionClient';

describe('SubtractionClient', () => {
  it('should run unit tests', () => {
    expect(new SubtractionClient('localhost')).not.toBeNull();
  });
});