package pk.mahdi.cryptotracker.features.crypto.coin_list

import pk.mahdi.cryptotracker.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError): CoinListEvent
}