import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CoacheeMySessionsComponent } from './coachee-my-sessions.component';

describe('CoacheeMySessionsComponent', () => {
  let component: CoacheeMySessionsComponent;
  let fixture: ComponentFixture<CoacheeMySessionsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CoacheeMySessionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoacheeMySessionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
