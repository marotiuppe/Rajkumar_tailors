import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopLayoutNavbarComponent } from './top-layout-navbar.component';

describe('TopLayoutNavbarComponent', () => {
  let component: TopLayoutNavbarComponent;
  let fixture: ComponentFixture<TopLayoutNavbarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TopLayoutNavbarComponent]
    });
    fixture = TestBed.createComponent(TopLayoutNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
