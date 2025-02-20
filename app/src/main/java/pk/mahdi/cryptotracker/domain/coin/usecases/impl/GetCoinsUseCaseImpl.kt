package pk.mahdi.cryptotracker.domain.coin.usecases.impl

import pk.mahdi.cryptotracker.domain.util.NetworkError
import pk.mahdi.cryptotracker.domain.util.Result
import pk.mahdi.cryptotracker.domain.coin.model.Coin
import pk.mahdi.cryptotracker.data.repositories.CoinsRepository
import pk.mahdi.cryptotracker.domain.coin.usecases.GetCoinsUseCase
import javax.inject.Inject

class GetCoinsUseCaseImpl @Inject constructor(
    private val coinsRepository: CoinsRepository
) : GetCoinsUseCase {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return coinsRepository.getCoins()
    }
}