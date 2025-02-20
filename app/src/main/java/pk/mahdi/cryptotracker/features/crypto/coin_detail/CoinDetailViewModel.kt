package pk.mahdi.cryptotracker.features.crypto.coin_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pk.mahdi.cryptotracker.domain.util.onError
import pk.mahdi.cryptotracker.domain.util.onSuccess
import pk.mahdi.cryptotracker.domain.coin.usecases.GetCoinDetailUseCase
import pk.mahdi.cryptotracker.domain.coin.usecases.GetCoinHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pk.mahdi.cryptotracker.features.crypto.models.toCoinUi
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinDetailsUseCase: GetCoinDetailUseCase,
    private val getCoinHistoryUseCase: GetCoinHistoryUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(CoinDetailState())
    val state = _state
        .onStart { fetchCoinDetails() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinDetailState()
        )

    private val _events = Channel<CoinDetailEvent>()
    val events = _events.receiveAsFlow()

    private val coinId: String = savedStateHandle["coinId"] ?: ""

    private fun fetchCoinDetails() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val coinDetailsDeferred = async {
                getCoinDetailsUseCase.getCoinDetail(coinId)
            }
            val coinHistoryDeferred = async {
                getCoinHistoryUseCase.getCoinHistory(
                    coinId = coinId,
                    start = ZonedDateTime.now().minusDays(5),
                    end = ZonedDateTime.now()
                )
            }

            val coinDetailsResult = coinDetailsDeferred.await()
            val coinHistoryResult = coinHistoryDeferred.await()

            coinDetailsResult.onSuccess { coin ->
                coinHistoryResult.onSuccess { history ->
                    val dataPoints = history
                        .sortedBy { it.dateTime }
                        .map {
                            DataPoint(
                                x = it.dateTime.hour.toFloat(),
                                y = it.priceUsd.toFloat(),
                                xLabel = DateTimeFormatter
                                    .ofPattern("ha\nM/d")
                                    .format(it.dateTime)
                            )
                        }

                    val coinUi = coin.toCoinUi().copy(coinPriceHistory = dataPoints)

                    _state.update {
                        it.copy(
                            coin = coinUi,
                            isLoading = false,
                        )
                    }
                }.onError { error ->
                    _events.send(CoinDetailEvent.Error(error))
                }
            }.onError { error ->
                _events.send(CoinDetailEvent.Error(error))
            }
        }
    }

}
