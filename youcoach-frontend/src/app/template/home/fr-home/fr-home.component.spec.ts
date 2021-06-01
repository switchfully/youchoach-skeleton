import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { FrHomeComponent } from './fr-home.component';

describe('FrHomeComponent', () => {
  let component: FrHomeComponent;
  let fixture: ComponentFixture<FrHomeComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ FrHomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FrHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
