import { Injectable } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { Store } from '@ngrx/store';
import { updateRealTimePrice } from '../store/actions/wallet.actions';
import { retry, tap, catchError } from 'rxjs/operators';
import { EMPTY } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class MarketDataService {

    private socket$: WebSocketSubject<any>;

    constructor(private store: Store) {
        this.socket$ = webSocket('ws://localhost:8081/ws/prices');
    }

    connect() {
        this.socket$.pipe(
            retry({ count: 5, delay: 1000 }), // Reconnect logic
            tap(prices => this.store.dispatch(updateRealTimePrice({ prices }))),
            catchError(err => {
                console.error(err);
                return EMPTY;
            })
        ).subscribe();
    }
}
