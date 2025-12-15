import { createReducer, on } from '@ngrx/store';
import * as WalletActions from '../actions/wallet.actions';
import { Asset } from '../../models/asset.model';

export interface WalletState {
    assets: Asset[];
    balanceUsd: number;
    loading: boolean;
    error: any;
    marketPrices: any;
}

export const initialState: WalletState = {
    assets: [],
    balanceUsd: 10000, // Initial fictitious balance
    loading: false,
    error: null,
    marketPrices: {}
};

export const walletReducer = createReducer(
    initialState,
    on(WalletActions.loadPortfolio, state => ({ ...state, loading: true })),
    on(WalletActions.loadPortfolioSuccess, (state, { assets }) => ({ ...state, loading: false, assets })),
    on(WalletActions.updateRealTimePrice, (state, { prices }) => ({ ...state, marketPrices: prices }))
);
