import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { PasswordChangeFailureComponent } from './password-change-failure.component';

describe('PasswordChangeFailureComponent', () => {
  let component: PasswordChangeFailureComponent;
  let fixture: ComponentFixture<PasswordChangeFailureComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ PasswordChangeFailureComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PasswordChangeFailureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
