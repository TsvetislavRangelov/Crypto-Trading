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
import { Holding } from '../types/holding';

@Component({
  selector: 'app-tickers',
  imports: [CommonModule, FormsModule],
  templateUrl: './tickers.html',
  styleUrl: './tickers.scss'
})
export class Tickers implements OnInit {
  errorMsg: boolean = false;
  errorString: string = '';

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

    public resetAccount = () => {
      const user = this.globalStateService.getUser();
      if(user.username !== null){
        this.httpClient.get(`http://localhost:8080/reset/${user.username}`).subscribe((data) => {});
        this.globalStateService.updateUser({id: user.id, username: user.username, cash: 10000});
      }
    }

    private loadCashBalance(): void {
    const user = this.globalStateService.getUsername();
    if (user !== null) {
      this.httpClient
        .get<number>('http://localhost:8080/cash/' + user)
        .subscribe((data) => this.globalStateService.updateUser({id: this.globalStateService.getUserId(), username: user, cash: data}));
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
      if(action === TransactionType.BUY){
        this.buy(symbol, price, quantity, transaction, user);
      }
      else{
        this.sell(transaction, user);
      }
    
    }

    private registerHolding(id: number, userId: number, symbol: string, invested: number, amount: number, avgPrice: number){
        const holding: Holding = {
              id: id,
              userId: userId,
              invested: invested,
              symbol: symbol,
              shareAmount: amount,
              avg: avgPrice
          };
        this.httpClient.post("http://localhost:8080/holdings", holding).subscribe((x) => {});    
    }

    private sell(transaction: Transaction, user: User){
      let newBalance: number;
        this.httpClient.post("http://localhost:8080/registerTransaction", transaction).subscribe((x) => {});
        this.httpClient.get<Holding>(`http://localhost:8080/holdings/${user.id}?symbol=${transaction.symbol}`).subscribe((x) => {
          // sell partial.
          if(x !== null){
            newBalance = user.cash + (transaction.pricePerShare - x.avg) * x.shareAmount + x.invested;
            if(x.shareAmount > transaction.amountOfShares){
              const newAvg = (x.invested + (transaction.pricePerShare * transaction.amountOfShares)) / (x.shareAmount + transaction.amountOfShares);
              this.registerHolding(x.id, user.id, x.symbol, x.invested - (transaction.pricePerShare * transaction.amountOfShares), x.shareAmount - transaction.amountOfShares, newAvg);
              const updateCashRequestBody = {
                  username: user.username,
                  newAmount: newBalance
                };
                const updatedUserData: User = {
                  id: user.id,
                  username: user.username,
                  cash: newBalance
                };
              this.httpClient.post("http://localhost:8080/cash/update", updateCashRequestBody).subscribe((x) => {});
              this.globalStateService.updateUser(updatedUserData);
              
            }
            else if (x.shareAmount == transaction.amountOfShares){
            // sell everything.
            this.httpClient.delete(`http://localhost:8080/holdings/delete/${user.id}?symbol=${transaction.symbol}`).subscribe((x) =>{
              if(x){
                
              // code duplication but its 22:40 and I have to study for finals afterwards so dont care
              const updateCashRequestBody = {
                username: user.username,
                newAmount: newBalance
              };
              const updatedUserData: User = {
                id: user.id,
                username: user.username,
                cash: newBalance
              };
                this.httpClient.post("http://localhost:8080/cash/update", updateCashRequestBody).subscribe((x) => {});
                this.globalStateService.updateUser(updatedUserData);
              }
            })
          }
          else{
            this.errorMsg = true;
            this.errorString = "You can't sell more shares than the amount you own."
          }
          }
        });
    }

    private buy(symbol: string, price: number, quantity: number, transaction: Transaction, user: User){
      const newBalance = user.cash - transaction.pricePerShare * transaction.amountOfShares;
      if(newBalance > 0){
        const updateCashRequestBody = {
          username: user.username,
          newAmount: newBalance
        }
        const updatedUserData: User = {
          id: user.id,
          username: user.username,
          cash: newBalance
        }
        this.httpClient.post("http://localhost:8080/registerTransaction", transaction).subscribe((x) => {});
        this.httpClient.post("http://localhost:8080/cash/update", updateCashRequestBody).subscribe((x) => {});
        this.httpClient.get<Holding>(`http://localhost:8080/holdings/${user.id}?symbol=${symbol}`).subscribe((x) => {
          if(x !== null){
            const newAvg = (x.invested + (price * quantity)) / (x.shareAmount + quantity);
            this.registerHolding(x.id, user.id, x.symbol, x.invested + (price * quantity), x.shareAmount + quantity, newAvg);
            this.globalStateService.updateUser(updatedUserData);
          }
          else{
            // initially, the avg price is the current price of the asset and the invested amount is the price
            // per share times the amount of shares to buy.
            this.registerHolding(0 ,user.id, symbol, price * quantity, quantity, price);
            this.globalStateService.updateUser(updatedUserData);
          }
        });
      }
      else{
        this.errorMsg = true;
        this.errorString = "You do not have enough funds to buy this pair."
      }
    }
  }
