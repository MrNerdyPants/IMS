export interface MenuItem {
  id?: number;
  name?: any;
  icon?: string;
  url?: string;
  label?:string,
  children?: any;
  isTitle?: boolean;
  activeInd?: boolean;
  sort?: number;
  parentId?: number;
  isLayout?: boolean;
  badge?: any;
}