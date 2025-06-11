import { ChangeDetectorRef, Component, effect, inject, OnInit } from '@angular/core';
import { GlobalStateService } from '../global-state-service';
import { CommonModule } from '@angular/common';
import { HoldingService } from '../services/holding-service';
import { HttpClient } from '@angular/common/http';
import { Ticker } from '../types/ticker';
import { CryptoTickerService } from '../services/crypto-ticker-service';
import { Holding } from '../types/holding';
import { map, switchMap } from 'rxjs';

@Component({
  selector: 'app-holdings',
  imports: [CommonModule],
  templateUrl: './holdings.html',
  styleUrl: './holdings.scss'
})
export class Holdings implements OnInit {


  private tickerService = inject(CryptoTickerService)
  private httpClient = inject(HttpClient);
  private globalStateService = inject(GlobalStateService);
  private holdingService = inject(HoldingService);
  private changeDetector = inject(ChangeDetectorRef);
  holdings: Holding[] = [];
  tickers: Ticker[] = [];

  ngOnInit(): void {
    this.holdingService.getHoldingsForUserId(this.globalStateService.getUserId());
         this.holdingService.holdings$.subscribe({
    next: (holdings) => {
      this.holdings = holdings;

      const symbols = this.holdings.map((x) => x.symbol);

      this.httpClient.post("http://localhost:8080/unsubscribe/tickers", symbols).subscribe({
        next: () => {
          this.httpClient.post("http://localhost:8080/subscribe/tickers", symbols).subscribe();
        },
        error: (error) => console.error('Error unsubscribing from tickers', error),
      });
    },
    error: (error) => console.error('Error fetching holdings', error),
  });
    this.tickerService.tickers$.subscribe({
          next: (tickerUpdates) => {
            tickerUpdates.forEach((t) => {
              if(this.holdings.find(x => x.symbol === t.symbol)){
                this.tickers.push(t);
              }
            })
            this.changeDetector.detectChanges();
          }
        });
  }

  public getTickerPriceForSymbol(symbol: string): number {
    const t = this.tickers.find(x => x.symbol === symbol);
    if(t !== undefined){
      return t.price;
    }
    return 0;
  }

  public getPlForHolding(symbol: string, avg: number, shares: number) : number {
    const t = this.tickers.find(x => x.symbol === symbol);
    if(t !== undefined){
      return (t.price - avg) * shares;
    }
    return 0;
  }
}
