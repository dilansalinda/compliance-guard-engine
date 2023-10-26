import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  email: string = '';
  password: string = '';

  login() {
    // Implement login logic here.
  }

  forgotPassword() {
    // Implement the logic for handling forgotten passwords, e.g., open a dialog with a form.
  }
}