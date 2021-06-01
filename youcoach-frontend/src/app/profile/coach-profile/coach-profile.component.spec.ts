import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CoachProfileComponent } from './coach-profile.component';

describe('CoachProfileComponent', () => {
  let component: CoachProfileComponent;
  let fixture: ComponentFixture<CoachProfileComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CoachProfileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoachProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
