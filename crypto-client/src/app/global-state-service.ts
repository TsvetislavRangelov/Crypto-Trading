import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { User } from './types/user';

@Injectable({
  providedIn: 'root',
})
export class GlobalStateService {
  private userSubject = new BehaviorSubject<User>({id: 0, username: null, cash: 0});
  public user$ = this.userSubject.asObservable();

  updateUser(newUser: User){
    this.userSubject.next(newUser);
  }

  getUser(): User {
    return this.userSubject.getValue();
  }

  hasUserName(): boolean {
    return this.getUser().username !== null;
  }

  getCashBalance(): number {
    return +this.getUser().cash.toPrecision(8);
  }
}
