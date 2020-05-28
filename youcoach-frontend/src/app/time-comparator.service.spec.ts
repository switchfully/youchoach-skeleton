import { TestBed } from '@angular/core/testing';

import { TimeComparatorService } from './time-comparator.service';

describe('TimeComparatorService', () => {
  let service: TimeComparatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TimeComparatorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
