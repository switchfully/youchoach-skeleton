import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordChangeFailureComponent } from './password-change-failure.component';

describe('PasswordChangeFailureComponent', () => {
  let component: PasswordChangeFailureComponent;
  let fixture: ComponentFixture<PasswordChangeFailureComponent>;

  beforeEach(async(() => {
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
