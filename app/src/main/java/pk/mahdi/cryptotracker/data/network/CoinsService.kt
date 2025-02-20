package pk.mahdi.cryptotracker.data.network

import pk.mahdi.cryptotracker.core.data.networking.constructUrl
import pk.mahdi.cryptotracker.core.data.networking.safeCall
import pk.mahdi.cryptotracker.domain.util.NetworkError
import pk.mahdi.cryptotracker.domain.util.Result
import pk.mahdi.cryptotracker.domain.util.map
import pk.mahdi.cryptotracker.data.network.dto.CoinDetailsResponse
import pk.mahdi.cryptotracker.data.network.mappers.toCoin
import pk.mahdi.cryptotracker.data.network.mappers.toCoinPrice
import pk.mahdi.cryptotracker.data.network.dto.CoinHistoryDto
import pk.mahdi.cryptotracker.data.network.dto.CoinsResponseDto
import pk.mahdi.cryptotracker.domain.coin.model.Coin
import pk.mahdi.cryptotracker.domain.coin.model.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

class CoinsService @Inject constructor(
    private val httpClient: HttpClient
) {

     suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }

    suspend fun getCoinDetail(coinId: String): Result<Coin, NetworkError> {
        return safeCall<CoinDetailsResponse> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId")
            ) {

            }
        }.map { response ->
            response.data.toCoin()
        }
    }

     suspend fun getCoinHistory(
         coinId: String,
         start: ZonedDateTime,
         end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
        val endMillis = end
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()

        return safeCall<CoinHistoryDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map { it.toCoinPrice() }
        }
    }
}