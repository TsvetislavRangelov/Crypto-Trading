import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Transaction } from '../types/transaction';

@Injectable({
  providedIn: 'root',
})
export class TransactionService {
  private transactionsSubject = new BehaviorSubject<Transaction[]>([]);
  transactions$ = this.transactionsSubject.asObservable();

  constructor(private http: HttpClient) {}

  public getTransactionsForUserId(userId: number): void {
    const url = `http://localhost:8080/transactions/${userId}`;
    this.http.get<Transaction[]>(url).subscribe({
      next: (transactions) => {
        this.transactionsSubject.next(transactions);
      },
      error: (err) => {
        console.error('Failed to fetch transactions:', err);
      },
    });
  }
}
