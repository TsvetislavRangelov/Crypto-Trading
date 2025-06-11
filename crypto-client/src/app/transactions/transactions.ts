import { Component, effect, inject, OnInit } from '@angular/core';
import { GlobalStateService } from '../global-state-service';
import { TransactionService } from '../services/transaction-service';
import { Transaction } from '../types/transaction';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-transactions',
  imports: [CommonModule],
  templateUrl: './transactions.html',
  styleUrl: './transactions.scss'
})
export class Transactions implements OnInit {


  private globalStateService = inject(GlobalStateService);
  private transactionService = inject(TransactionService);
  transactions$ = this.transactionService.transactions$;

  ngOnInit(): void {
    this.transactionService.getTransactionsForUserId(this.globalStateService.getUserId());
  }
}
