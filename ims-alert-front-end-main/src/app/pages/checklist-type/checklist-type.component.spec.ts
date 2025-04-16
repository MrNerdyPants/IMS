import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChecklistTypeComponent } from './checklist-type.component';

describe('ChecklistTypeComponent', () => {
  let component: ChecklistTypeComponent;
  let fixture: ComponentFixture<ChecklistTypeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChecklistTypeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChecklistTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
