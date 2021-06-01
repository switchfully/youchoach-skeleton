import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { EnHomeComponent } from './en-home.component';

describe('EnHomeComponent', () => {
  let component: EnHomeComponent;
  let fixture: ComponentFixture<EnHomeComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ EnHomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EnHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
