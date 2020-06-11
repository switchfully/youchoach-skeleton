import { TestBed } from '@angular/core/testing';

import { CoacheeService } from './coachee.service';

describe('CoacheeServiceService', () => {
  let service: CoacheeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CoacheeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
