package pk.mahdi.cryptotracker.features.crypto.coin_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pk.mahdi.cryptotracker.domain.util.onError
import pk.mahdi.cryptotracker.domain.util.onSuccess
import pk.mahdi.cryptotracker.domain.coin.usecases.GetCoinDetailUseCase
import pk.mahdi.cryptotracker.domain.coin.usecases.GetCoinHistoryUseCase
import pk.mahdi.cryptotracker.domain.coin.usecases.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pk.mahdi.cryptotracker.features.crypto.coin_detail.DataPoint
import pk.mahdi.cryptotracker.features.crypto.models.CoinUi
import pk.mahdi.cryptotracker.features.crypto.models.toCoinUi
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase,
    private val getCoinHistoryUseCase: GetCoinHistoryUseCase,
    private val getCoinDetailUseCase: GetCoinDetailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state = _state
        .onStart { loadCoins() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinListState()
        )

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {
                selectCoin(action.coinUi)
            }
        }
    }

    private fun selectCoin(coinUi: CoinUi) {
        _state.update { it.copy(selectedCoin = coinUi) }

        viewModelScope.launch {
            getCoinHistoryUseCase
                .getCoinHistory(
                    coinId = coinUi.id,
                    start = ZonedDateTime.now().minusDays(5),
                    end = ZonedDateTime.now()
                )
                .onSuccess { history ->
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

                    _state.update {
                        it.copy(
                            selectedCoin = it.selectedCoin?.copy(
                                coinPriceHistory = dataPoints
                            )
                        )
                    }
                }
                .onError { error ->
                    _events.send(CoinListEvent.Error(error))
                }

            getCoinDetailUseCase.getCoinDetail(coinUi.id).onSuccess {
                Log.e("getCoinDetailUseCase", it.toString() )
            }

        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            getCoinsUseCase
                .getCoins()
                .onSuccess { coins ->
                    _state.update { coinListState ->
                        coinListState.copy(
                            isLoading = false,
                            coins = coins.map { it.toCoinUi() }
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }
}