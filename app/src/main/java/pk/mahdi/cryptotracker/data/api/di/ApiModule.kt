package pk.mahdi.cryptotracker.data.api.di

import pk.mahdi.cryptotracker.data.api.CoinsRemoteSource
import pk.mahdi.cryptotracker.data.api.CoinsRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule {

  @Binds
  abstract fun bindCoinsService(impl: CoinsRemoteSourceImpl): CoinsRemoteSource

}