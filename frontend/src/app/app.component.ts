import { Component } from '@angular/core';
import {Router, RouterLink, RouterOutlet} from '@angular/router';
import {MatToolbar} from '@angular/material/toolbar';
import {MatButton} from '@angular/material/button';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    MatToolbar,
    MatButton,
    RouterLink,
    NgIf
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
  currentpath = '';

  //sub to router as pub -> listen to changes in route
  constructor(private router: Router) {
    this.router.events.subscribe(() => {
      this.currentpath = this.router.url;
    });
  }

  isUserInfoRoute(): boolean {
    return this.currentpath.startsWith('/userinfo/');
  }

  isNotUserInfoRoute(): boolean {
    return !(this.currentpath.startsWith('/userinfo/'));
  }
}
