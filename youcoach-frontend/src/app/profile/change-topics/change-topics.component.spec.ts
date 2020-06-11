import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeTopicsComponent } from './change-topics.component';

describe('ChangeTopicsComponent', () => {
  let component: ChangeTopicsComponent;
  let fixture: ComponentFixture<ChangeTopicsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangeTopicsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeTopicsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
