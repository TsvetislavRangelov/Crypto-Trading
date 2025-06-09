import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { Ticker } from '../types/ticker';
import { CryptoTickerService } from '../services/crypto-ticker-service';
import { HttpClient } from '@angular/common/http';
import { GlobalStateService } from '../global-state-service';
import { FormsModule } from '@angular/forms';
import { TransactionType } from '../types/transactionType';
import { Transaction } from '../types/transaction';

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
        this.httpClient.post("http://localhost:8080/reset", username).subscribe((data) => {console.log(data)});
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
      var userId = this.globalStateService.getUser().id;
      const transaction: Transaction =  {
        id: 0,
        userId: userId,
        dateProduced: new Date(),
        symbol: symbol,
        pricePerShare: price,
        amountOfShares: quantity,
        action: action
      };
      const isRegistered = this.httpClient.post<boolean>("http://localhost:8080/register", transaction).subscribe((x) => {});
      if(isRegistered){
        console.log("Transaction went through fine! ")
      }
}
  
}
