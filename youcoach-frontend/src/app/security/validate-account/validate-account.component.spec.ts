import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ValidateAccountComponent } from './validate-account.component';

describe('ValidateAccountComponent', () => {
  let component: ValidateAccountComponent;
  let fixture: ComponentFixture<ValidateAccountComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ValidateAccountComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ValidateAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
