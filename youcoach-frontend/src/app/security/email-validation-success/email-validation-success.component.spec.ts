import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { EmailValidationSuccessComponent } from './email-validation-success.component';

describe('EmailValidationSuccessComponent', () => {
  let component: EmailValidationSuccessComponent;
  let fixture: ComponentFixture<EmailValidationSuccessComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ EmailValidationSuccessComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailValidationSuccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
