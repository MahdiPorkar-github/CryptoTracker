package pk.mahdi.cryptotracker.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinDetailsResponse(
    val data: CoinDto
)