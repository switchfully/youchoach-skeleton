import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BecomeCoachComponent } from './become-coach.component';

describe('BecomeCoachComponent', () => {
  let component: BecomeCoachComponent;
  let fixture: ComponentFixture<BecomeCoachComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BecomeCoachComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BecomeCoachComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
