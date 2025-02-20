package pk.mahdi.cryptotracker.domain.coin.usecases

import pk.mahdi.cryptotracker.domain.util.NetworkError
import pk.mahdi.cryptotracker.domain.util.Result
import pk.mahdi.cryptotracker.domain.coin.model.Coin

interface GetCoinDetailUseCase {
    suspend fun getCoinDetail(coinId: String): Result<Coin, NetworkError>
}