import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CoachMysessionsComponent } from './coach-mysessions.component';

describe('CoachMysessionsComponent', () => {
  let component: CoachMysessionsComponent;
  let fixture: ComponentFixture<CoachMysessionsComponent>;

  beforeEach(async(() => {
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
