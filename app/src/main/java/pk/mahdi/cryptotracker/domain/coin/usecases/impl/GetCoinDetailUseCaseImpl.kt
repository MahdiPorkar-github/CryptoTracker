package pk.mahdi.cryptotracker.domain.coin.usecases.impl

import pk.mahdi.cryptotracker.domain.util.NetworkError
import pk.mahdi.cryptotracker.domain.util.Result
import pk.mahdi.cryptotracker.data.repositories.CoinsRepository
import pk.mahdi.cryptotracker.domain.coin.model.Coin
import pk.mahdi.cryptotracker.domain.coin.usecases.GetCoinDetailUseCase
import javax.inject.Inject

class GetCoinDetailUseCaseImpl @Inject constructor(
    private val coinsRepository: CoinsRepository
) : GetCoinDetailUseCase {
    override suspend fun getCoinDetail(coinId: String): Result<Coin, NetworkError> {
        return coinsRepository.getCoinDetail(coinId)
    }
}