package pk.mahdi.cryptotracker.data.api

import pk.mahdi.cryptotracker.domain.util.NetworkError
import pk.mahdi.cryptotracker.domain.util.Result
import pk.mahdi.cryptotracker.domain.coin.model.Coin
import pk.mahdi.cryptotracker.domain.coin.model.CoinPrice
import pk.mahdi.cryptotracker.data.network.CoinsService
import java.time.ZonedDateTime
import javax.inject.Inject

class CoinsRemoteSourceImpl @Inject constructor(
    private val coinsService: CoinsService
) : CoinsRemoteSource {

    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return coinsService.getCoins()
    }

    override suspend fun getCoinDetail(coinId: String): Result<Coin, NetworkError> {
        return coinsService.getCoinDetail(coinId)
    }

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        return coinsService.getCoinHistory(coinId,start,end)
    }

}