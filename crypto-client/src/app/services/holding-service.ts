import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Holding } from '../types/holding';

@Injectable({
  providedIn: 'root',
})
export class HoldingService {
  private holdingsSubject = new BehaviorSubject<Holding[]>([]);
  holdings$ = this.holdingsSubject.asObservable();

  constructor(private http: HttpClient) {}

  public getHoldingsForUserId(userId: number): void {
    const url = `http://localhost:8080/holdings/user/${userId}`;
    this.http.get<Holding[]>(url).subscribe({
      next: (holdings) => {
        this.holdingsSubject.next(holdings);
        this.holdings$.subscribe({
            
        })
      },
      error: (err) => {
        console.error('Failed to fetch holdings:', err);
      },
    });
  }
}
