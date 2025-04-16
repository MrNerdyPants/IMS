import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HazardComponent } from './hazard.component';

describe('HazardComponent', () => {
  let component: HazardComponent;
  let fixture: ComponentFixture<HazardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HazardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HazardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
