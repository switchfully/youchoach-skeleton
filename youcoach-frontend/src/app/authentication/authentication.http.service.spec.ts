import { TestBed } from '@angular/core/testing';

import { AuthenticationHttpService } from './authentication.http.service';

describe('LoginService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AuthenticationHttpService = TestBed.get(AuthenticationHttpService);
    expect(service).toBeTruthy();
  });
});
