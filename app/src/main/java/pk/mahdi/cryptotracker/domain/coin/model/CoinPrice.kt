package pk.mahdi.cryptotracker.domain.coin.model

import java.time.ZonedDateTime

data class CoinPrice(
    val priceUsd: Double,
    val dateTime: ZonedDateTime
)
