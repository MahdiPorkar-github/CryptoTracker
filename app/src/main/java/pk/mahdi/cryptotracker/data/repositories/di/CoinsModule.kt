package pk.mahdi.cryptotracker.data.repositories.di

import pk.mahdi.cryptotracker.data.repositories.CoinsRepository
import pk.mahdi.cryptotracker.data.repositories.CoinsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoinsModule {

  @Binds
  abstract fun bindCoinsRemoteSource(
    impl: CoinsRepositoryImpl,
  ): CoinsRepository
}