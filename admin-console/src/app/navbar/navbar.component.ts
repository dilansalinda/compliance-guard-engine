import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  constructor(private router: Router) {}

  logout() {
    // Implement your logout logic here, e.g., clear user session.
    // Then, navigate to the login page.
    this.router.navigate(['/login']);
  }
}