import { Component, OnInit, EventEmitter, Output, Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { EventService } from '../../core/services/event.service';

//Logout
import { environment } from '../../../environments/environment';
import { AuthenticationService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/services/token-storage.service';

// Language
import { CookieService } from 'ngx-cookie-service';
import { LanguageService } from '../../core/services/language.service';
import { TranslateService } from '@ngx-translate/core';

import { CartModel } from './topbar.model';
import { cartData } from './data';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/pages/toast.service';

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.scss']
})
export class TopbarComponent implements OnInit {

  element: any;
  mode: string | undefined;
  @Output() mobileMenuButtonClicked = new EventEmitter();

  flagvalue: any;
  valueset: any;
  countryName: any;
  cookieValue: any;
  userData: any;
  cartData!: CartModel[];
  total = 0;
  cart_length: any = 0;
  allNewNotes: any = [];
  allNotes: any = [];
  allAlertNotes: any = [];
  currentUser: any;
  intervalId: any;
  // constructor(@Inject(DOCUMENT) private document: any, private eventService: EventService, public languageService: LanguageService,
  //   public _cookiesService: CookieService, public translate: TranslateService, private authService: AuthenticationService, private authFackservice: AuthfakeauthenticationService,
  //   private router: Router, private TokenStorageService: TokenStorageService) { }

  constructor(@Inject(DOCUMENT) private document: any, private eventService: EventService, public languageService: LanguageService,
    public _cookiesService: CookieService, public translate: TranslateService, private authService: AuthenticationService,
    private router: Router, private TokenStorageService: TokenStorageService,
    public genericService: GenericService, private toaster: ToastService) { }

  ngOnInit(): void {
    this.userData = this.TokenStorageService.getUser();
    this.element = document.documentElement;

    this.currentUser = this.authService.currentUserValue;
    this.getNotification(this.currentUser.id);
    this.getUnReadNotificationCounter();

    // this.intervalId = setInterval(() => {
    //   this.getUnReadNotificationCounter();
    // }, 10000);

    // Cookies wise Language set
    // this.cookieValue = this._cookiesService.get('lang');
    // const val = this.listLang.filter(x => x.lang === this.cookieValue);
    // this.countryName = val.map(element => element.text);
    // if (val.length === 0) {
    //   if (this.flagvalue === undefined) { this.valueset = 'assets/images/flags/us.svg'; }
    // } else {
    //   this.flagvalue = val.map(element => element.flag);
    // }

    // Fetch Data
    // this.cartData = cartData;
    // this.cart_length = this.cartData.length;
    // this.cartData.forEach((item) => {
    //   var item_price = item.quantity * item.price
    //   this.total += item_price
    // });
  }

  markUnread(): any {
    let data = [];
    for (let noti of this.allNewNotes?.response) {
      if (noti.status === "1") {
        data.push(noti.id);
      }
    }
    if (data.length > 0) {
      this.genericService.saveData("maintenance/update-notification", data)
        .subscribe(
          (respData: any) => {
          },
          (error: any) => {
            console.error(error);
          });
    }
  }

  getNotification(id: any): any {
    this.genericService.getData('maintenance/get-notification/' + id, false)
      .subscribe(
        (data: any) => {
          this.allNewNotes = data;
          this.allAlertNotes = [];
          for (let obj of data?.response) {
            if (obj?.status === "1") {
              this.allAlertNotes.push(obj);
            }
          }
          // console.log(this.allNewNotes);
        },
        (error: any) => {
          console.error(error);
        });
  }

  getUnReadNotificationCounter(): any {
    this.genericService.getData('maintenance/get-notification-count/' + this.currentUser.id, false)
      .subscribe(
        (data: any) => {
          if (data?.response) {
            this.allNotes = data?.response.length;
            let markList = [];
            for (let obj of data?.response) {
              if (obj?.status === "0") {
                markList.push(obj.id);
              }
            }
            if (markList.length > 0) {
              this.genericService.saveData("maintenance/update-notification", markList)
                .subscribe(
                  (respData: any) => {
                    this.getNotification(this.currentUser.id);
                    this.toaster.info('New notification received.');
                  },
                  (error: any) => {
                    console.error(error);
                  });
            }
          } else {
            this.allNotes = 0;
          }
        }, (error: any) => {
          console.error(error);
        });
  }

  openUrl(url: string | null, additional: string): any {
    // if (url !== null && url !== 'member-evaluation') {
    //   this.router.navigate([url]);
    // }
    // if (url !== null && url === 'member-evaluation') {
    //   let object = JSON.parse(additional);
    //   console.log(object);
    //   const obj = {
    //     EDIT_IND: false,
    //     AC_GRADE_NME: object.AC_GRADE_NME,
    //     DEPARTMENT_NME: object.DEPARTMENT_NME,
    //     EMPLOYEE_ID: object.EMPLOYEE_ID,
    //     EMPLOYEE_NME: object.EMPLOYEE_NME,
    //     EMP_PERCENTAGE: object.EMP_PERCENTAGE,
    //     GRADE_NME: object.GRADE_NME,
    //     LEAD_NME: object.LEAD_NME,
    //     MANAGER_NME: object.MANAGER_NME,
    //     PERCENTAGE: object.PERCENTAGE,
    //     PERIOD: object.PERIOD,
    //     PUBLISH_ON_QUATER: object.PUBLISH_ON_QUATER,
    //     PUBLISH_YEAR: object.PUBLISH_YEAR,
    //     STATUS: object.STATUS,
    //     TEAM_CODE: object.TEAM_CODE,
    //     TEAM_ID: object.TEAM_ID,
    //     TEAM_NAME: object.TEAM_NAME
    //   };
    //   if (object.PUBLISH_ON_QUATER !== null && object.PUBLISH_ON_QUATER !== '') {
    //     const passObj = JSON.stringify(object);
    //     const encrypt = this.edt.set(environment.key, passObj);
    //     const key = this.replaceAll(encrypt, '/', '\'~\'');
    //     this.router.navigate([`/` + url + `/${passObj}`]);
    //   }
    // }

    // if (url !== null && url === 'manager-grievance-form') {
    //   let object = JSON.parse(additional);
    //   const passObj = JSON.stringify(object);
    //   this.router.navigate([`/` + url + `/${passObj}`]);
    // }
  }

































































  /**
   * Toggle the menu bar when having mobile screen
   */
  toggleMobileMenu(event: any) {
    document.querySelector('.hamburger-icon')?.classList.toggle('open')
    event.preventDefault();
    this.mobileMenuButtonClicked.emit();
    if (document.documentElement.clientWidth <= 1024) {
      if (document.documentElement.getAttribute('data-layout') == 'vertical') {
        (document.documentElement.getAttribute('data-sidebar-size') == "sm") ? document.documentElement.setAttribute('data-sidebar-size', 'lg') : document.documentElement.setAttribute('data-sidebar-size', 'sm')
      }
      if (document.documentElement.getAttribute('data-layout') == 'horizontal')
        document.body.classList.toggle('menu');
    }
    if (document.documentElement.clientWidth <= 767) {
      document.body.classList.toggle('vertical-sidebar-enable');
      document.documentElement.setAttribute('data-sidebar-size', 'lg')
    }
  }

  /**
   * Fullscreen method
   */
  fullscreen() {
    document.body.classList.toggle('fullscreen-enable');
    if (
      !document.fullscreenElement && !this.element.mozFullScreenElement &&
      !this.element.webkitFullscreenElement) {
      if (this.element.requestFullscreen) {
        this.element.requestFullscreen();
      } else if (this.element.mozRequestFullScreen) {
        /* Firefox */
        this.element.mozRequestFullScreen();
      } else if (this.element.webkitRequestFullscreen) {
        /* Chrome, Safari and Opera */
        this.element.webkitRequestFullscreen();
      } else if (this.element.msRequestFullscreen) {
        /* IE/Edge */
        this.element.msRequestFullscreen();
      }
    } else {
      if (this.document.exitFullscreen) {
        this.document.exitFullscreen();
      } else if (this.document.mozCancelFullScreen) {
        /* Firefox */
        this.document.mozCancelFullScreen();
      } else if (this.document.webkitExitFullscreen) {
        /* Chrome, Safari and Opera */
        this.document.webkitExitFullscreen();
      } else if (this.document.msExitFullscreen) {
        /* IE/Edge */
        this.document.msExitFullscreen();
      }
    }
  }

  /**
  * Topbar Light-Dark Mode Change
  */
  changeMode(mode: string) {
    this.mode = mode;
    this.eventService.broadcast('changeMode', mode);

    switch (mode) {
      case 'light':
        document.body.setAttribute('data-layout-mode', "light");
        document.body.setAttribute('data-sidebar', "light");
        break;
      case 'dark':
        document.body.setAttribute('data-layout-mode', "dark");
        document.body.setAttribute('data-sidebar', "dark");
        break;
      default:
        document.body.setAttribute('data-layout-mode', "light");
        break;
    }
  }

  /***
   * Language Listing
   */
  listLang = [
    { text: 'English', flag: 'assets/images/flags/us.svg', lang: 'en' },
    { text: 'Española', flag: 'assets/images/flags/spain.svg', lang: 'es' },
    { text: 'Deutsche', flag: 'assets/images/flags/germany.svg', lang: 'de' },
    { text: 'Italiana', flag: 'assets/images/flags/italy.svg', lang: 'it' },
    { text: 'русский', flag: 'assets/images/flags/russia.svg', lang: 'ru' },
    { text: '中国人', flag: 'assets/images/flags/china.svg', lang: 'ch' },
    { text: 'français', flag: 'assets/images/flags/french.svg', lang: 'fr' },
    { text: 'Arabic', flag: 'assets/images/flags/ae.svg', lang: 'ar' },
  ];

  /***
   * Language Value Set
   */
  setLanguage(text: string, lang: string, flag: string) {
    this.countryName = text;
    this.flagvalue = flag;
    this.cookieValue = lang;
    this.languageService.setLanguage(lang);
  }

  /**
   * Logout the user
   */
  logout() {
    // if (environment.defaultauth === 'firebase') {
    //   this.authService.logout();
    // } else {
    //   this.authFackservice.logout();
    // }
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }

  windowScroll() {
    // if (document.body.scrollTop > 100 || document.documentElement.scrollTop > 100) {
    //   (document.getElementById("back-to-top") as HTMLElement).style.display = "block";
    //   document.getElementById('page-topbar')?.classList.add('topbar-shadow')
    // } else {
    //   (document.getElementById("back-to-top") as HTMLElement).style.display = "none";
    //   document.getElementById('page-topbar')?.classList.remove('topbar-shadow')
    // }
  }

  // Delete Item
  deleteItem(event: any, id: any) {
    var price = event.target.closest('.dropdown-item').querySelector('.item_price').innerHTML;
    var Total_price = this.total - price;
    this.total = Total_price;
    this.cart_length = this.cart_length - 1;
    this.total > 1 ? (document.getElementById("empty-cart") as HTMLElement).style.display = "none" : (document.getElementById("empty-cart") as HTMLElement).style.display = "block";
    document.getElementById('item_' + id)?.remove();
  }

  // Search Topbar
  Search() {
    var searchOptions = document.getElementById("search-close-options") as HTMLAreaElement;
    var dropdown = document.getElementById("search-dropdown") as HTMLAreaElement;
    var input: any, filter: any, ul: any, li: any, a: any | undefined, i: any, txtValue: any;
    input = document.getElementById("search-options") as HTMLAreaElement;
    filter = input.value.toUpperCase();
    var inputLength = filter.length;

    if (inputLength > 0) {
      dropdown.classList.add("show");
      searchOptions.classList.remove("d-none");
      var inputVal = input.value.toUpperCase();
      var notifyItem = document.getElementsByClassName("notify-item");

      Array.from(notifyItem).forEach(function (element: any) {
        var notifiTxt = ''
        if (element.querySelector("h6")) {
          var spantext = element.getElementsByTagName("span")[0].innerText.toLowerCase()
          var name = element.querySelector("h6").innerText.toLowerCase()
          if (name.includes(inputVal)) {
            notifiTxt = name
          } else {
            notifiTxt = spantext
          }
        } else if (element.getElementsByTagName("span")) {
          notifiTxt = element.getElementsByTagName("span")[0].innerText.toLowerCase()
        }
        if (notifiTxt)
          element.style.display = notifiTxt.includes(inputVal) ? "block" : "none";

      });
    } else {
      dropdown.classList.remove("show");
      searchOptions.classList.add("d-none");
    }
  }

  /**
   * Search Close Btn
   */
  closeBtn() {
    var searchOptions = document.getElementById("search-close-options") as HTMLAreaElement;
    var dropdown = document.getElementById("search-dropdown") as HTMLAreaElement;
    var searchInputReponsive = document.getElementById("search-options") as HTMLInputElement;
    dropdown.classList.remove("show");
    searchOptions.classList.add("d-none");
    searchInputReponsive.value = "";
  }

}
