import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerCheckListComponent } from './customer-check-list.component';

describe('CustomerCheckListComponent', () => {
  let component: CustomerCheckListComponent;
  let fixture: ComponentFixture<CustomerCheckListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CustomerCheckListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomerCheckListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
