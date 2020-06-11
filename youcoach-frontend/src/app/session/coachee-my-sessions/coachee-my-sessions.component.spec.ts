import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CoacheeMySessionsComponent } from './coachee-my-sessions.component';

describe('CoacheeMySessionsComponent', () => {
  let component: CoacheeMySessionsComponent;
  let fixture: ComponentFixture<CoacheeMySessionsComponent>;

  beforeEach(async(() => {
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
