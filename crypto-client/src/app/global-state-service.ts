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
  private userIdKey = 'userId';
  private cashKey = "cash";  

  updateUser(newUser: User){
    this.userSubject.next(newUser);
    sessionStorage.setItem(this.usernameKey, newUser.username!);
    sessionStorage.setItem(this.userIdKey, newUser.id.toString());
    sessionStorage.setItem(this.cashKey, newUser.cash.toString());
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

  getUserId(): number {
    const userId = sessionStorage.getItem(this.userIdKey);
    if(userId !== null){
      return parseInt(userId);
    }
    return 0;
  }

  getCash(): number {
    const cash = sessionStorage.getItem(this.cashKey);
    if(cash !== null){
      return parseInt(cash);
    }
    return 0;
  }
}
