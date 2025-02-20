package pk.mahdi.cryptotracker.data.repositories

import pk.mahdi.cryptotracker.domain.util.NetworkError
import pk.mahdi.cryptotracker.domain.util.Result
import pk.mahdi.cryptotracker.domain.coin.model.Coin
import pk.mahdi.cryptotracker.domain.coin.model.CoinPrice
import java.time.ZonedDateTime

interface CoinsRepository {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>

    suspend fun getCoinDetail(coinId: String): Result<Coin, NetworkError>

    suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError>
}