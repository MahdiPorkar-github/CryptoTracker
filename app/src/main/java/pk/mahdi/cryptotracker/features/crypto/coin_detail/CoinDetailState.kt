package pk.mahdi.cryptotracker.features.crypto.coin_detail

import pk.mahdi.cryptotracker.features.crypto.models.CoinUi


data class CoinDetailState(
    val isLoading: Boolean = false,
    val coin: CoinUi? = null,
)
