package pk.mahdi.cryptotracker.features.crypto.coin_detail

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import pk.mahdi.cryptotracker.app.ui.navigation.DestinationRoute
import pk.mahdi.cryptotracker.core.designsystem.util.ObserveAsEvents
import pk.mahdi.cryptotracker.core.designsystem.util.toString

fun NavGraphBuilder.coinDetailScreen(
    rootRoute: DestinationRoute,
    onBack: () -> Unit,
) {
    composable(
        route = "${rootRoute.route}/coinDetail/{coinId}",
        arguments = listOf(navArgument("coinId") { type = NavType.StringType })
    ) {
        val viewModel = hiltViewModel<CoinDetailViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val context = LocalContext.current
        ObserveAsEvents(events = viewModel.events) { event ->
            when(event) {
                is CoinDetailEvent.Error -> {
                    Toast.makeText(
                        context,
                        event.error.toString(context),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        CoinDetailScreen(
            state = state,
        )
    }
}


fun NavController.navigateToCoinDetail(rootRoute: DestinationRoute, coinId: String) {
    navigate("${rootRoute.route}/coinDetail/$coinId")
}