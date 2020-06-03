import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FindACoachComponent } from './find-a-coach.component';

describe('FindACoachComponent', () => {
  let component: FindACoachComponent;
  let fixture: ComponentFixture<FindACoachComponent>;

  beforeEach(async(() => {
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
