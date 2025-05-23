import { Component, OnInit } from '@angular/core';
import { EventService } from '../../core/services/event.service';

@Component({
  selector: 'app-two-column',
  templateUrl: './two-column.component.html',
  styleUrls: ['./two-column.component.scss']
})

/**
 * TwoColumnComponent
 */
export class TwoColumnComponent implements OnInit {

  constructor(private eventService: EventService) { }
  isCondensed = false;

  ngOnInit(): void {
    if (document.documentElement.clientWidth <= 767) {      
      document.documentElement.setAttribute('data-layout', 'vertical');
    }
    else{
      document.documentElement.setAttribute('data-layout', 'twocolumn');
      document.documentElement.setAttribute('data-topbar', 'light');
      document.documentElement.setAttribute('data-sidebar', 'dark');
      document.documentElement.setAttribute('data-sidebar-size', 'lg');
      document.documentElement.setAttribute('data-layout-style', 'default');
      document.documentElement.setAttribute('data-layout-mode', 'light');
      document.documentElement.setAttribute('data-layout-width', 'fluid');
      document.documentElement.setAttribute('data-layout-position', 'fixed');
      document.documentElement.setAttribute('data-preloader', 'disable');
    }
  }

  /**
   * On mobile toggle button clicked
   */
   onToggleMobileMenu() {
    document.body.classList.toggle('twocolumn-panel');
  }

  /**
   * on settings button clicked from topbar
   */
   onSettingsButtonClicked() {
    document.body.classList.toggle('right-bar-enabled');
    const rightBar = document.getElementById('theme-settings-offcanvas');
    if(rightBar != null){
      rightBar.classList.toggle('show');
      rightBar.setAttribute('style',"visibility: visible;");
    }
  }

  onResize(event:any) {    
    if(document.body.getAttribute('layout') == "twocolumn") {
      if (event.target.innerWidth <= 767) {
        this.eventService.broadcast('changeLayout', 'vertical');      
      }else{
        this.eventService.broadcast('changeLayout', 'twocolumn');  
        document.body.classList.remove('twocolumn-panel'); 
        document.body.classList.remove('vertical-sidebar-enable'); 
      }
    }
  }
    
  isTwoColumnLayoutRequested() {
    return 'twocolumn' === document.documentElement.getAttribute('data-layout');
    
  }
  
  issemiboxLayoutRequested() {
    return 'semibox' === document.documentElement.getAttribute('data-layout');
  }

}
