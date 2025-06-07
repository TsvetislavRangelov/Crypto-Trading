import { Injectable } from '@angular/core';
import { Ticker } from '../types/ticker';
import { Message } from '@stomp/stompjs';
import { BehaviorSubject } from 'rxjs';
import { RxStompService } from '../rx-stomp.service';

@Injectable({
  providedIn: 'root'
})
export class CryptoTickerService {

  // synced with the server.
  private tickerCache: Map<string, Ticker> = new Map();

  private tickersSubject = new BehaviorSubject<Ticker[]>([]);
  tickers$ = this.tickersSubject.asObservable();

  constructor(private rxStompService: RxStompService) { 
    this.subscribeToTickerChannel();
  }

    private subscribeToTickerChannel() {
      // gets the initial top 20 from the ws endpoint.
    this.rxStompService.publish({
      destination: '/app/ticker',
    });

    this.rxStompService.watch('/channel/ticker').subscribe((message: Message) => {
      const data = JSON.parse(message.body);
      if (Array.isArray(data)) {
        this.tickerCache.clear();
        data.forEach((ticker: Ticker) => this.tickerCache.set(ticker.symbol, ticker));
      } else if (data.symbol) {
        this.tickerCache.set(data.symbol, {
          ...this.tickerCache.get(data.symbol),
          ...data,
        });
      }
      this.tickersSubject.next(Array.from(this.tickerCache.values()));
    });
  }
}
