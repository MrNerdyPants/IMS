import { MenuItem } from './menu.model';

export const MENU: MenuItem[] = [ {
  id: 3,
  name: 'Administrator',
  label:'Administrator',
  icon: 'bx bxs-first-aid',
  url: '/drugs',
}, {
  id: 2,
  name: 'Dashboard',
  icon: 'bx bx-home',
  url: '/',
}, {
  id: 4,
  name: 'Business Setup',
  icon: 'ri-shopping-cart-2-fill',
  badge:{
    variant:"bg-success",
    text:"Badge"
  },
  url: '/purchases',
  children: [
    {
      id: 7,
      parentId: 4,
      name: 'Product',
      url: '/reports',
    }
  ]
}, {
  id: 5,
  name: 'Sales',
  icon: 'bx bxs-shopping-bags',
  url: '/sales',
}, {
  id: 6,
  name: 'Reports',
  icon: 'ri-git-repository-line',  
}, {
  id: 8,
  name: 'Tabs',
  icon: 'bx bx-tab',
  url: '/tabs',
}];
