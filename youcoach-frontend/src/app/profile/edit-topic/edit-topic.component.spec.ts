import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { EditTopicComponent } from './edit-topic.component';

describe('EditTopicComponent', () => {
  let component: EditTopicComponent;
  let fixture: ComponentFixture<EditTopicComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ EditTopicComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditTopicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
