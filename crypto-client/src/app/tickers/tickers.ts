import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { Ticker } from '../types/ticker';
import { CryptoTickerService } from '../services/crypto-ticker-service';
import { HttpClient } from '@angular/common/http';
import { GlobalStateService } from '../global-state-service';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-tickers',
  imports: [CommonModule],
  templateUrl: './tickers.html',
  styleUrl: './tickers.scss'
})
export class Tickers implements OnInit {

  public cashBalance$ = new BehaviorSubject<number>(0);
  private httpClient = inject(HttpClient);
  private globalStateService = inject(GlobalStateService);
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
      this.loadCashBalance();
    }

    public getTickerQuoteCurrency = (ticker: Ticker) => {
      const symbol = ticker.symbol;
      return " " + symbol.substring(symbol.length, symbol.indexOf('/') + 1); // + 1 to remove the / before the quote.
    }

    public resetCashBalance = () => {
      const username = this.globalStateService.getUserName();
      if(username !== null){
        this.httpClient.post("http://localhost:8080/reset", username).subscribe((data) => {console.log(data)});
      }
    }

    private loadCashBalance(): void {
    const username = this.globalStateService.getUserName();
    if (username !== null) {
      this.httpClient
        .get<number>('http://localhost:8080/cash/' + username)
        .subscribe((data) => this.cashBalance$.next(data));
    }
  }
  
}
