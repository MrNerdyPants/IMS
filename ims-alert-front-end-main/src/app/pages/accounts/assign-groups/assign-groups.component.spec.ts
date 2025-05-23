import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignGroupsComponent } from './assign-groups.component';

describe('AssignGroupsComponent', () => {
  let component: AssignGroupsComponent;
  let fixture: ComponentFixture<AssignGroupsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssignGroupsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AssignGroupsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
