import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  api: string = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  getUserList(): Observable<string[]> {
    return this.http.get<string[]>(`${this.api}/users`, { withCredentials: true }).pipe(
      catchError(this.handleError)
    );
  }

  getUserById(id: number): Observable<any> {
    return this.http.get<any>(`${this.api}/users/${id}`, { withCredentials: true })
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 403) {
      return throwError(() => new Error('Access Denied.'));
    } else {
      return throwError(() => new Error('An unexpected error occurred.'));
    }
  }
}
