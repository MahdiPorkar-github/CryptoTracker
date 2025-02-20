package pk.mahdi.cryptotracker.domain.coin.di

import pk.mahdi.cryptotracker.domain.coin.usecases.GetCoinDetailUseCase
import pk.mahdi.cryptotracker.domain.coin.usecases.GetCoinHistoryUseCase
import pk.mahdi.cryptotracker.domain.coin.usecases.GetCoinsUseCase
import pk.mahdi.cryptotracker.domain.coin.usecases.impl.GetCoinDetailUseCaseImpl
import pk.mahdi.cryptotracker.domain.coin.usecases.impl.GetCoinHistoryUseCaseImpl
import pk.mahdi.cryptotracker.domain.coin.usecases.impl.GetCoinsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoinsUseCaseModule {

    @Binds
    abstract fun bindGetCoinsUseCase(
        impl: GetCoinsUseCaseImpl
    ): GetCoinsUseCase

    @Binds
    abstract fun bindGetCoinHistoryUseCase(
        impl: GetCoinHistoryUseCaseImpl
    ): GetCoinHistoryUseCase

    @Binds
    abstract fun bindGetCoinDetailUseCase(
        impl: GetCoinDetailUseCaseImpl
    ): GetCoinDetailUseCase


}