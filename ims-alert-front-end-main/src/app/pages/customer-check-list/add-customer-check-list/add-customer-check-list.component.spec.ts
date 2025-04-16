import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCustomerCheckListComponent } from './add-customer-check-list.component';

describe('AddCustomerCheckListComponent', () => {
  let component: AddCustomerCheckListComponent;
  let fixture: ComponentFixture<AddCustomerCheckListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddCustomerCheckListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddCustomerCheckListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
