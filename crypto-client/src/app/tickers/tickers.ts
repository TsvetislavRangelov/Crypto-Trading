import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { Ticker } from '../types/ticker';
import { CryptoTickerService } from '../services/crypto-ticker-service';
import { HttpClient } from '@angular/common/http';
import { GlobalStateService } from '../global-state-service';
import { FormsModule } from '@angular/forms';
import { TransactionType } from '../types/transactionType';
import { Transaction } from '../types/transaction';
import { User } from '../types/user';

@Component({
  selector: 'app-tickers',
  imports: [CommonModule, FormsModule],
  templateUrl: './tickers.html',
  styleUrl: './tickers.scss'
})
export class Tickers implements OnInit {

  // unwrap the enum since imports are private in angular
  protected action = TransactionType;

  public quantities: number[] = [];
  private httpClient = inject(HttpClient);
  public globalStateService = inject(GlobalStateService);
    tickers: Ticker[] = [];


    constructor(private tickerService: CryptoTickerService, private changeDetector: ChangeDetectorRef) {
    }

    ngOnInit(): void {
        this.tickerService.tickers$.subscribe({
          next: (tickerUpdates) => {
            this.tickers = tickerUpdates;
            this.changeDetector.detectChanges();
          }
        });
      this.quantities = Array(this.tickers.length).fill(0);
      this.loadCashBalance();

    }

    public getTickerQuoteCurrency = (ticker: Ticker) => {
      const symbol = ticker.symbol;
      return " " + symbol.substring(symbol.length, symbol.indexOf('/') + 1); // + 1 to remove the / before the quote.
    }

    public resetCashBalance = () => {
      const username = this.globalStateService.getUser().username;
      if(username !== null){
        this.httpClient.post("http://localhost:8080/cash/update", {username: username, newAmount: 10000}).subscribe((data) => {});
      }
    }

    private loadCashBalance(): void {
    const user = this.globalStateService.getUser();
    if (user.username !== null) {
      this.httpClient
        .get<number>('http://localhost:8080/cash/' + user.username)
        .subscribe((data) => this.globalStateService.updateUser({id: user.id, username: user.username, cash: data}));
    }
  }

    public executeTransaction(symbol: string, price: number, quantity: number, action: TransactionType) {
      const user = this.globalStateService.getUser();
      const transaction: Transaction =  {
        id: 0,
        userId: user.id,
        dateProduced: new Date(),
        symbol: symbol,
        pricePerShare: price,
        amountOfShares: quantity,
        action: action
      };
      const newBalance = user.cash - transaction.pricePerShare * transaction.amountOfShares;
      if(newBalance > 0){
        const updateCashRequestBody = {
          username: user.username,
          newAmount: user.cash - transaction.pricePerShare * transaction.amountOfShares
        }
        const updatedUserData: User = {
          id: user.id,
          username: user.username,
          cash: newBalance
        }
        this.httpClient.post("http://localhost:8080/register", transaction).subscribe((x) => {});
        this.httpClient.post("http://localhost:8080/cash/update", updateCashRequestBody).subscribe((x) => {});
        this.globalStateService.updateUser(updatedUserData);
      }
    }
  }
