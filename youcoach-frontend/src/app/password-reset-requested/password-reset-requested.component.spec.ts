import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordResetRequestedComponent } from './password-reset-requested.component';

describe('PasswordResetRequestedComponent', () => {
  let component: PasswordResetRequestedComponent;
  let fixture: ComponentFixture<PasswordResetRequestedComponent>;

  beforeEach(async(() => {
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
