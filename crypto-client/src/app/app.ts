import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Tickers } from "./tickers/tickers";
import { Messages } from "./messages/messages";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Tickers, Messages],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected title = 'crypto-client';
}
