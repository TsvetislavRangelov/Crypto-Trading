import { Component, inject, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Tickers } from "./tickers/tickers";
import { NamePromptComponent } from "./name-prompt/name-prompt";
import { CommonModule } from '@angular/common';
import { GlobalStateService } from './global-state-service';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Tickers, NamePromptComponent, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit{
  constructor(public globalState: GlobalStateService){}
  protected title = 'crypto-client';

  ngOnInit(): void {
      if(this.globalState.hasUserName()){
        console.log("no username found. Prompting");
      }
  }
}