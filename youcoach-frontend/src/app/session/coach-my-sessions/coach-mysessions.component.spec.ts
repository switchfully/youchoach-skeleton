import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CoachMysessionsComponent } from './coach-mysessions.component';

describe('CoachMysessionsComponent', () => {
  let component: CoachMysessionsComponent;
  let fixture: ComponentFixture<CoachMysessionsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CoachMysessionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoachMysessionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
