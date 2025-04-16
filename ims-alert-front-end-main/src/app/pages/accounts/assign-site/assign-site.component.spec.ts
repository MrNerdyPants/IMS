import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignSiteComponent } from './assign-site.component';

describe('AssignSiteComponent', () => {
  let component: AssignSiteComponent;
  let fixture: ComponentFixture<AssignSiteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssignSiteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AssignSiteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
