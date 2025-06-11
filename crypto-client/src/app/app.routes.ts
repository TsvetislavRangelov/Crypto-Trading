import { Routes } from '@angular/router';
import { Transactions } from './transactions/transactions';
import { Tickers } from './tickers/tickers';
import { Holdings } from './holdings/holdings';

export const routes: Routes = [{path: 'transactions', component: Transactions}, {path: '', component: Tickers}, {path: 'holdings', component: Holdings}];
