package pk.mahdi.cryptotracker.features.crypto.coin_list

import androidx.compose.runtime.Immutable
import pk.mahdi.cryptotracker.features.crypto.models.CoinUi

@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinUi> = emptyList(),
    val selectedCoin: CoinUi? = null
)
