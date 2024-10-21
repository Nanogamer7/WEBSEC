import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatCard, MatCardContent, MatCardTitle} from '@angular/material/card';
import {MatFormField} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatButton} from '@angular/material/button';

import {HttpClient, HttpHeaders} from '@angular/common/http';
import {HttpClientModule} from '@angular/common/http';

import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    MatCardTitle,
    MatCard,
    MatFormField,
    MatInput,
    MatButton,
    MatCardContent,
    MatFormFieldModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {
  constructor(private http: HttpClient, private router: Router) {
  }

  username: string = '';
  password: string = '';

  onSubmit() {
    if (this.username && this.password) {

      //define url for http call
      let url = 'http://localhost:8080/login';

      //define body
      const body = {
        password: this.password,
        username: this.username
      }

      //make http post call
      //on non-error producing response: navigate forumpage
      this.http.post(url, body).subscribe({
        next: (response) => {
          console.log('Response: ', response);
          this.router.navigate(["/userinfo", response]);
        },
        error: (error) => {
          console.error('Error: ', error);
        }
      });

    } else {
      console.log('Please enter a username and password.');
    }
  }
}
