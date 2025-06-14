import { RxStompConfig } from '@stomp/rx-stomp';

export const myRxStompConfig: RxStompConfig = {
  brokerURL: 'ws://localhost:8080/crypto-trading',

  heartbeatIncoming: 0, 
  heartbeatOutgoing: 20000, 

  reconnectDelay: 2000
};