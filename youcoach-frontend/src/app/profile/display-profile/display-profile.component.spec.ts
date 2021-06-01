import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { DisplayProfileComponent } from './display-profile.component';

describe('DisplayProfileComponent', () => {
  let component: DisplayProfileComponent;
  let fixture: ComponentFixture<DisplayProfileComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ DisplayProfileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DisplayProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
