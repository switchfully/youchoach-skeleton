import { AuthImagePipe } from './auth-image.pipe';

describe('AuthImagePipe', () => {
  it('create an instance', () => {
    const pipe = new AuthImagePipe(null);
    expect(pipe).toBeTruthy();
  });
});
