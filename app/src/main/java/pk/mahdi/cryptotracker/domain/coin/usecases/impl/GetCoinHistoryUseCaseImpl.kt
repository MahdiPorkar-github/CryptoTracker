package pk.mahdi.cryptotracker.domain.coin.usecases.impl

import pk.mahdi.cryptotracker.domain.util.NetworkError
import pk.mahdi.cryptotracker.domain.util.Result
import pk.mahdi.cryptotracker.domain.coin.model.CoinPrice
import pk.mahdi.cryptotracker.data.repositories.CoinsRepository
import pk.mahdi.cryptotracker.domain.coin.usecases.GetCoinHistoryUseCase
import java.time.ZonedDateTime
import javax.inject.Inject


class GetCoinHistoryUseCaseImpl @Inject constructor(
    private val coinsRepository: CoinsRepository
) : GetCoinHistoryUseCase {
    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        return coinsRepository.getCoinHistory(coinId, start, end)
    }
}