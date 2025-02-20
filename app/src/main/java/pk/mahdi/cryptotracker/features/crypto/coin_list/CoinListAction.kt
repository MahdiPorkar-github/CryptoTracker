package pk.mahdi.cryptotracker.features.crypto.coin_list

import pk.mahdi.cryptotracker.features.crypto.models.CoinUi


sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi): CoinListAction
}