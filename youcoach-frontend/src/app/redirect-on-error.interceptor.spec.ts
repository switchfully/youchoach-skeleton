import { TestBed } from '@angular/core/testing';

import { RedirectOnErrorInterceptor } from './redirect-on-error.interceptor';

describe('RedirectOnErrorInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      RedirectOnErrorInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: RedirectOnErrorInterceptor = TestBed.inject(RedirectOnErrorInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
