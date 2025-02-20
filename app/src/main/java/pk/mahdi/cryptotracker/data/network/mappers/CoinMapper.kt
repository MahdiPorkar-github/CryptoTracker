package pk.mahdi.cryptotracker.data.network.mappers

import pk.mahdi.cryptotracker.data.network.dto.CoinDto
import pk.mahdi.cryptotracker.data.network.dto.CoinPriceDto
import pk.mahdi.cryptotracker.domain.coin.model.Coin
import pk.mahdi.cryptotracker.domain.coin.model.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
    )
}

fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        dateTime = Instant
            .ofEpochMilli(time)
            .atZone(ZoneId.systemDefault())
    )
}