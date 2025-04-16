import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddChecklistTypeComponent } from './add-checklist-type.component';

describe('AddChecklistTypeComponent', () => {
  let component: AddChecklistTypeComponent;
  let fixture: ComponentFixture<AddChecklistTypeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddChecklistTypeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddChecklistTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
