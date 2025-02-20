package pk.mahdi.cryptotracker.data.network

import pk.mahdi.cryptotracker.core.data.networking.HttpClientFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun provideHttpClient(): HttpClient {
    return HttpClientFactory.create(CIO.create())
  }


}
