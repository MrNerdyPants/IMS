import { Injectable, TemplateRef } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  toasts: any[] = [];

  show(textOrTpl: string | TemplateRef<any>, options: any = {}) {
    this.toasts.push({ textOrTpl, ...options });
  }

  success(textOrTpl: string | TemplateRef<any>) {
    this.toasts.push({ textOrTpl, classname: 'bg-success text-light', delay: 15000 });
  }

  info(textOrTpl: string | TemplateRef<any>) {
    this.toasts.push({ textOrTpl, classname: 'bg-info text-light toast-bottom-left', delay: 15000 });
  }

  error(textOrTpl: string | TemplateRef<any>) {
    this.toasts.push({ textOrTpl, classname: 'bg-danger text-light', delay: 15000 });
  }


  warning(textOrTpl: string | TemplateRef<any>) {
    this.toasts.push({ textOrTpl, classname: 'bg-warning text-light', delay: 15000 });
  }


  remove(toast: any) {
    this.toasts = this.toasts.filter(t => t !== toast);
  }
}
