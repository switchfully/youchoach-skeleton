import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestSessionComponent } from './request-session.component';

describe('RequestSessionComponent', () => {
  let component: RequestSessionComponent;
  let fixture: ComponentFixture<RequestSessionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RequestSessionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RequestSessionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should only be allowed to request a session if all fields are filled in', () => {
    expect(component.sessionForm.valid).toBeFalsy();
  });
});
