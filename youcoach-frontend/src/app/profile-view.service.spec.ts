import { TestBed } from '@angular/core/testing';

import { ProfileViewService } from './profile-view.service';

describe('ProfileViewService', () => {
  let service: ProfileViewService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProfileViewService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
