import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordChangeSuccessComponent } from './password-change-success.component';

describe('PasswordChangeSuccessComponent', () => {
  let component: PasswordChangeSuccessComponent;
  let fixture: ComponentFixture<PasswordChangeSuccessComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PasswordChangeSuccessComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PasswordChangeSuccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
