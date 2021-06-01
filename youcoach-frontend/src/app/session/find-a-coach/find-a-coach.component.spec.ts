import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { FindACoachComponent } from './find-a-coach.component';

describe('FindACoachComponent', () => {
  let component: FindACoachComponent;
  let fixture: ComponentFixture<FindACoachComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ FindACoachComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FindACoachComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
