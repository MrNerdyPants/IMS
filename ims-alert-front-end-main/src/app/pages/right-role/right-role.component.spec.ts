import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RightRoleComponent } from './right-role.component';

describe('RightRoleComponent', () => {
  let component: RightRoleComponent;
  let fixture: ComponentFixture<RightRoleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RightRoleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RightRoleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
