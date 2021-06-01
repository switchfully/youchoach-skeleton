import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { PasswordResetRequestedComponent } from './password-reset-requested.component';

describe('PasswordResetRequestedComponent', () => {
  let component: PasswordResetRequestedComponent;
  let fixture: ComponentFixture<PasswordResetRequestedComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ PasswordResetRequestedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PasswordResetRequestedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
