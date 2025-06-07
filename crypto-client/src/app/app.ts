import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Tickers } from "./tickers/tickers";
import { AllCommunityModule, ModuleRegistry } from 'ag-grid-community';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Tickers],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected title = 'crypto-client';
}
ModuleRegistry.registerModules([AllCommunityModule]);