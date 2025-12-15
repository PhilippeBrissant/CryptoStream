import { createAction, props } from '@ngrx/store';
import { Asset } from '../../models/asset.model';

export const loadPortfolio = createAction('[Wallet] Load Portfolio', props<{ userId: string }>());
export const loadPortfolioSuccess = createAction('[Wallet] Load Portfolio Success', props<{ assets: Asset[] }>());
export const loadPortfolioFailure = createAction('[Wallet] Load Portfolio Failure', props<{ error: any }>());

export const buyAsset = createAction('[Wallet] Buy Asset', props<{ userId: string, symbol: string, amount: number, price: number }>());
export const buyAssetSuccess = createAction('[Wallet] Buy Asset Success', props<{ transaction: any }>());

export const updateRealTimePrice = createAction('[Market] Update Price', props<{ prices: any }>());
