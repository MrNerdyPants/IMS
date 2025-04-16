import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddChecklistCategoryComponent } from './add-checklist-category.component';

describe('AddChecklistCategoryComponent', () => {
  let component: AddChecklistCategoryComponent;
  let fixture: ComponentFixture<AddChecklistCategoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddChecklistCategoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddChecklistCategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
