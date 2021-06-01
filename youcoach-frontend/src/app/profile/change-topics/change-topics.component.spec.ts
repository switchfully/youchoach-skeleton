import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ChangeTopicsComponent } from './change-topics.component';

describe('ChangeTopicsComponent', () => {
  let component: ChangeTopicsComponent;
  let fixture: ComponentFixture<ChangeTopicsComponent>;

  beforeEach(waitForAsync(() => {
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
