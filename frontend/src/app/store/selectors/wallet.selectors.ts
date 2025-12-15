import { createSelector, createFeatureSelector } from '@ngrx/store';
import { WalletState } from '../reducers/wallet.reducer';

export const selectWalletState = createFeatureSelector<WalletState>('wallet');

export const selectAssets = createSelector(
    selectWalletState,
    (state) => state.assets
);

export const selectTotalBalance = createSelector(
    selectWalletState,
    (state) => {
        let total = state.balanceUsd;
        state.assets.forEach(asset => {
            const currentPrice = state.marketPrices[asset.symbol.toLowerCase()]?.usd || 0;
            total += asset.amount * currentPrice;
        });
        return total;
    }
);
