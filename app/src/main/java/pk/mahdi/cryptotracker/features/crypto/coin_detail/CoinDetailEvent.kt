package pk.mahdi.cryptotracker.features.crypto.coin_detail

import pk.mahdi.cryptotracker.domain.util.NetworkError

sealed interface CoinDetailEvent {
    data class Error(val error: NetworkError): CoinDetailEvent
}