import { Component, inject } from '@angular/core';
import { GlobalStateService } from '../global-state-service';

@Component({
  selector: 'app-transactions',
  imports: [],
  templateUrl: './transactions.html',
  styleUrl: './transactions.scss'
})
export class Transactions {

  private globalStateService = inject(GlobalStateService);

}
