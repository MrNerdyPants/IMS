import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {


  constructor(private routes: Router) {

  }

  ngOnInit(): void {
    this.getLocateions();
  }



  getLocateions() {
    let url = this.routes.url;
    console.log(url);
  }

}
