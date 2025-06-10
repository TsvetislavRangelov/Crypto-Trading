import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { User } from './types/user';

@Injectable({
  providedIn: 'root',
})
export class GlobalStateService {
  private userSubject = new BehaviorSubject<User>({id: 0, username: null, cash: 0});
  public user$ = this.userSubject.asObservable();
  private usernameKey = 'username';

  updateUser(newUser: User){
    this.userSubject.next(newUser);
    sessionStorage.setItem(this.usernameKey, newUser.username!);
  }

  getUser(): User {
    return this.userSubject.getValue();
  }

  getUsername(): string | null {
    return sessionStorage.getItem(this.usernameKey);
  }
  hasUserName(): boolean {
    return this.getUsername() !== null;
  }

  getCashBalance(): number {
    return +this.getUser().cash.toPrecision(8);
  }
}
