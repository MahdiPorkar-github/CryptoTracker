package pk.mahdi.cryptotracker.data.repositories

import pk.mahdi.cryptotracker.domain.util.NetworkError
import pk.mahdi.cryptotracker.domain.util.Result
import pk.mahdi.cryptotracker.domain.coin.model.Coin
import pk.mahdi.cryptotracker.domain.coin.model.CoinPrice
import pk.mahdi.cryptotracker.data.api.CoinsRemoteSource
import java.time.ZonedDateTime
import javax.inject.Inject


class CoinsRepositoryImpl @Inject constructor(
    private val coinsRemoteSource: CoinsRemoteSource
): CoinsRepository {

    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return coinsRemoteSource.getCoins()
    }

    override suspend fun getCoinDetail(coinId: String): Result<Coin, NetworkError> {
        return coinsRemoteSource.getCoinDetail(coinId)
    }

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        return coinsRemoteSource.getCoinHistory(
            coinId, start, end
        )
    }
}