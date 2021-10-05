import { Component, OnInit } from '@angular/core';
import PerfectScrollbar from 'perfect-scrollbar';
import { JwtService } from 'src/app/services/jwt.service';
import { AuthService } from 'src/app/services/auth.service';

declare const $: any;

// Metadata
export interface RouteInfo {
  path: string;
  title: string;
  type: string;
  icontype: string;
  collapse?: string;
  children?: ChildrenItems[];
}

export interface ChildrenItems {
  path: string;
  title: string;
  ab: string;
  type?: string;
  active: boolean;
}

// Menu Items
export const ROUTES: RouteInfo[] = [
  {
    path: '/product',
    title: 'Products',
    type: 'sub',
    icontype: 'devices_other',
    collapse: 'product',
    children: [
      { path: 'view', title: 'View product', ab: 'VP', active: true },
      { path: 'create', title: 'Create product', ab: 'CP', active: true },
      { path: 'edit', title: 'Edit product', ab: 'EP', active: false },
    ],
  },
  {
    path: '/user',
    title: 'Users',
    type: 'sub',
    icontype: 'person',
    collapse: 'user',
    children: [
      { path: 'view', title: 'View Users', ab: 'VU', active: true },
      { path: 'create', title: 'Create Users', ab: 'CU', active: true },
      { path: 'edit', title: 'Edit Users', ab: 'EU', active: false },
    ],
  },

  {
    path: '/quotation',
    title: 'Orders & Quotations',
    type: 'sub',
    icontype: 'description',
    collapse: 'user',
    children: [
      { path: 'summary', title: 'View All Orders', ab: 'VAO', active: true },
      {
        path: 'get-all-quotations',
        title: 'View All Quotations',
        ab: 'VAQ',
        active: true,
      },
    ],
  },
  {
    path: '/invoice',
    title: 'Invoice',
    type: 'sub',
    icontype: 'receipt',
    collapse: 'user',
    children: [
      { path: 'summary', title: 'View All Invoices', ab: 'VAO', active: true },
      {
        path: 'get-all-payments',
        title: 'View All Payments',
        ab: 'VAP',
        active: true,
      },
    ],
  },
];

@Component({
  selector: 'app-sidebar-cmp',
  templateUrl: 'sidebar.component.html',
})
export class SidebarComponent implements OnInit {
  public menuItems: any[];
  ps: any;
  userName: any;

  constructor() {}

  isMobileMenu() {
    if ($(window).width() > 991) {
      return false;
    }
    return true;
  }

  ngOnInit() {
    this.menuItems = ROUTES.filter((menuItem) => menuItem);
    if (window.matchMedia(`(min-width: 960px)`).matches && !this.isMac()) {
      const elemSidebar = <HTMLElement>(
        document.querySelector('.sidebar .sidebar-wrapper')
      );
      this.ps = new PerfectScrollbar(elemSidebar);
    }
  }

  updatePS(): void {
    if (window.matchMedia(`(min-width: 960px)`).matches && !this.isMac()) {
      this.ps.update();
    }
  }

  isMac(): boolean {
    let bool = false;
    if (
      navigator.platform.toUpperCase().indexOf('MAC') >= 0 ||
      navigator.platform.toUpperCase().indexOf('IPAD') >= 0
    ) {
      bool = true;
    }
    return bool;
  }
}
