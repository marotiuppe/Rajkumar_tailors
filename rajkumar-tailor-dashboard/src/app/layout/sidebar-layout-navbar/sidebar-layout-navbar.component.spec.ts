import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarLayoutNavbarComponent } from './sidebar-layout-navbar.component';

describe('SidebarLayoutNavbarComponent', () => {
  let component: SidebarLayoutNavbarComponent;
  let fixture: ComponentFixture<SidebarLayoutNavbarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SidebarLayoutNavbarComponent]
    });
    fixture = TestBed.createComponent(SidebarLayoutNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
