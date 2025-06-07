import { Component, OnDestroy, OnInit } from '@angular/core';
import { RxStompService } from '../rx-stomp.service';
import { Message } from '@stomp/stompjs';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-messages',
  imports: [CommonModule],
  templateUrl: './messages.html',
  styleUrl: './messages.scss'
})
export class Messages implements OnInit, OnDestroy {
  //@ts-ignore
  private channelSubscription: Subscription;
  public receivedMessages: string[] = [];
  constructor(private rxStompService: RxStompService){

  }

  ngOnInit(): void {
    this.rxStompService.publish({
          destination: '/app/ticker'
        });
    this.channelSubscription = this.rxStompService.watch('/channel/ticker').subscribe((message: Message) => {
        this.receivedMessages.push(message.body);
    })
  }

  ngOnDestroy(){
    this.channelSubscription.unsubscribe();
  }
}
