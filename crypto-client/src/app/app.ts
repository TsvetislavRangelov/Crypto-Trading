import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Tickers } from "./tickers/tickers";
import { NamePromptComponent } from "./name-prompt/name-prompt";
import { CommonModule } from '@angular/common';
import { GlobalStateService } from './global-state-service';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Tickers, NamePromptComponent, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  constructor(public globalState: GlobalStateService){}
  protected title = 'crypto-client';
}