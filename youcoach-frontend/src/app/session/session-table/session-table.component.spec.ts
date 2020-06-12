import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SessionTableComponent } from './session-table.component';

describe('SessionTableComponent', () => {
  let component: SessionTableComponent;
  let fixture: ComponentFixture<SessionTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SessionTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SessionTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
