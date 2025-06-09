import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { GlobalStateService } from '../global-state-service';
import { HttpClient } from '@angular/common/http';
import { User } from '../types/user';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-name-prompt',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './name-prompt.html',
  styleUrls: ['./name-prompt.scss']
})
export class NamePromptComponent {
  name: string = '';
  private httpClient = inject(HttpClient);
  constructor(private globalState: GlobalStateService) {}

  saveName(): void {
    if (this.name.trim()) {
      this.onLogin(this.name.trim()).subscribe((user) => {
        this.globalState.updateUser(user);
      })
    }
  }

  public onLogin = (username: string): Observable<User> => {
     return this.httpClient.post<User>("http://localhost:8080/login", username);
  }


  
}
