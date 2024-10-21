import { Component } from '@angular/core';
import {UserService} from '../user.service';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatOption, MatSelect} from '@angular/material/select';
import {NgForOf, NgIf} from '@angular/common';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-userinfo',
  standalone: true,
  imports: [
    MatLabel,
    MatFormField,
    MatSelect,
    MatOption,
    NgForOf,
    NgIf
  ],
  templateUrl: './userinfo.component.html',
  styleUrl: './userinfo.component.css'
})
export class UserinfoComponent {
  users: string[] = [];
  selectedUser: any = null;
  errorMessage: string | null = null;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.loadUsers();

    const userId = this.route.snapshot.paramMap.get('id');
    if (userId) {
      this.loadUser(+userId);
    }
  }

  loadUsers(): void {
    this.userService.getUserList().subscribe({
      next: (data) => this.users = data,
      error: (err) => this.handleError(err)
    });
  }

  loadUser(id: number): void {
    this.userService.getUserById(id).subscribe({
      next: (data) => {
        this.selectedUser = data;
        this.errorMessage = null;
      },
      error: (err) => this.handleError(err)
    });
  }

  onUserSelected(index: number): void {
    this.loadUser(index);
  }

  handleError(error: any): void {
    this.errorMessage = error.message;
    this.selectedUser = null;
  }
}
