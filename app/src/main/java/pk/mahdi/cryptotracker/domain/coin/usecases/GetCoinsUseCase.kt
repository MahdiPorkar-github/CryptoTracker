package pk.mahdi.cryptotracker.domain.coin.usecases

import pk.mahdi.cryptotracker.domain.util.NetworkError
import pk.mahdi.cryptotracker.domain.util.Result
import pk.mahdi.cryptotracker.domain.coin.model.Coin

interface GetCoinsUseCase {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}