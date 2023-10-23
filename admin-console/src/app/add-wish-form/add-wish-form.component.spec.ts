import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddWishFormComponent } from './add-wish-form.component';

describe('AddWishFormComponent', () => {
  let component: AddWishFormComponent;
  let fixture: ComponentFixture<AddWishFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddWishFormComponent]
    });
    fixture = TestBed.createComponent(AddWishFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
