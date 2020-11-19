import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FrFaqComponent } from './fr-faq.component';

describe('FrFaqComponent', () => {
  let component: FrFaqComponent;
  let fixture: ComponentFixture<FrFaqComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FrFaqComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FrFaqComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
