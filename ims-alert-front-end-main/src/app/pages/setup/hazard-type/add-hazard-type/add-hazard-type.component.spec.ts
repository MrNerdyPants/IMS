import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddHazardTypeComponent } from './add-hazard-type.component';

describe('AddHazardTypeComponent', () => {
  let component: AddHazardTypeComponent;
  let fixture: ComponentFixture<AddHazardTypeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddHazardTypeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddHazardTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
