import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { EnFaqComponent } from './en-faq.component';

describe('EnFaqComponent', () => {
  let component: EnFaqComponent;
  let fixture: ComponentFixture<EnFaqComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ EnFaqComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EnFaqComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
