import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { GlobalStateService } from '../global-state-service';
import { HttpClient } from '@angular/common/http';

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
      this.globalState.setUserName(this.name.trim());
      const name = this.globalState.getUserName();
      if(name !== null){
        this.onLogin(name);
      }
    }
  }

  public onLogin = (username: string) => {
    this.httpClient.post("http://localhost:8080/login", username).subscribe((data) => console.log(data));
  }


  
}
