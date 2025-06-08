import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Ticker } from '../types/ticker';
import { CryptoTickerService } from '../services/crypto-ticker-service';

@Component({
  selector: 'app-tickers',
  imports: [CommonModule],
  templateUrl: './tickers.html',
  styleUrl: './tickers.scss'
})
export class Tickers implements OnInit {

    tickers: Ticker[] = [];

    constructor(private tickerService: CryptoTickerService) {}

    ngOnInit(): void {
        this.tickerService.tickers$.subscribe({
          next: (tickerUpdates) => {
            this.tickers = tickerUpdates;
          }
        })
    }

    public getTickerQuoteCurrency = (ticker: Ticker) => {
      const symbol = ticker.symbol;
      return " " + symbol.substring(symbol.length, symbol.indexOf('/') + 1); // + 1 to remove the / before the quote.
    }
  
}
