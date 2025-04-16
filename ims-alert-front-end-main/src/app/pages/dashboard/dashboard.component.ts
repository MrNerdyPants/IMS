import { Component, OnInit } from '@angular/core';
import { ChartType } from './dashboard.model';
import { GenericService } from 'src/app/services/generic.service';
import { AuthenticationService } from 'src/app/services/auth.service';
import { statData } from './data';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})

/**
 * Ecommerce Component
 */
export class DashboardComponent implements OnInit {

  // bread crumb items
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Dashboard';
  analyticsChart!: ChartType;
  BestSelling: any;
  TopSelling: any;
  RecentSelling: any;
  SalesCategoryChart!: ChartType;
  statData!: any;
  activities: any;
  // Current Date
  intervalId: any;
  currentDate: any;
  allNewNotes: any = [];

  constructor(public genericService: GenericService, public auth: AuthenticationService) {
    var date = new Date();
    var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
    var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
    this.currentDate = { from: firstDay, to: lastDay }
  }

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: 'Dashboard', active: true }
    ];

    if (localStorage.getItem('toast')) {
      // this.toastService.show('Logged in Successfull.', { classname: 'bg-success text-center text-white', delay: 5000 });
      localStorage.removeItem('toast');
    }
    this.getActivity();
    this.getNotification();
    this.getHeadCountOfRecords();
    // this.intervalId = setInterval(() => {
    //   this.getUnReadNotificationCounter();
    // }, 10000);
  }


  errorHandler(event: any) {
    event.target.src = "../../assets/logo.png";
  }

  getActivity(): any {
    this.genericService.getData('settings/get-activity', true)
      .subscribe(
        data => {
          this.activities = data;
        }, error => {
          console.error(error);
        });
  }

  getNotification(): any {
    this.genericService.getData('maintenance/get-notification/' + this.auth.currentUserValue.id, false)
      .subscribe(
        data => {
          this.allNewNotes = data;
        }, error => {
          console.error(error);
        });
  }


  getUnReadNotificationCounter(): any {
    this.genericService.getData('maintenance/get-notification-count/' + this.auth.currentUserValue.id, false)
      .subscribe(
        (data: any) => {
          if (data?.response) {
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
                    this.getNotification();
                  }, (error: any) => {
                    console.error(error);
                  });
            }
          }
        }, (error: any) => {
          console.error(error);
        });
  }

  getHeadCountOfRecords(): void {
    this.statData = [{
      title: 'COMPLAINTS',
      value: 10,
      icon: 'ri-space-ship-line',
      profit: 'up'
    }, {
      title: 'ASSIGNED',
      value: 10,
      icon: 'ri-space-ship-line',
      profit: 'up'
    }, {
      title: 'RESOLVED',
      value: 10,
      icon: 'ri-exchange-dollar-line',
      profit: 'up'
    }, {
      title: 'CUSTOMERS',
      value: 10,
      icon: 'ri-trophy-line',
      profit: 'up'
    }, {
      title: 'PRODUCTS',
      value: 10,
      icon: 'ri-pulse-line',
      profit: 'down'
    },];
  }

}
