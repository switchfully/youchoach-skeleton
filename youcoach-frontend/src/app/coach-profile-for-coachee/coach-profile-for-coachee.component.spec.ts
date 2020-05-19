import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CoachProfileForCoacheeComponent } from './coach-profile-for-coachee.component';

describe('CoachProfileForCoacheeComponent', () => {
  let component: CoachProfileForCoacheeComponent;
  let fixture: ComponentFixture<CoachProfileForCoacheeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CoachProfileForCoacheeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoachProfileForCoacheeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
