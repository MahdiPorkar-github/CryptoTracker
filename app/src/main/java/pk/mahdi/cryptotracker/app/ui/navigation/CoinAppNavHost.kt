package pk.mahdi.cryptotracker.app.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import pk.mahdi.cryptotracker.features.crypto.coin_detail.coinDetailScreen
import pk.mahdi.cryptotracker.features.crypto.coin_detail.navigateToCoinDetail
import pk.mahdi.cryptotracker.features.crypto.coin_list.GRAPH_HOME_ROUTE
import pk.mahdi.cryptotracker.features.crypto.coin_list.homeGraph


@ExperimentalAnimationApi
@Composable
internal fun CoinAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = GRAPH_HOME_ROUTE.route,
        modifier = modifier,
    ) {

        homeGraph(
            onCoinClicked = { rootRoute, coinId ->
                navController.navigateToCoinDetail(rootRoute, coinId)
            },
            nestedGraphs = { rootRoute ->
                addCoinDetailScreen(rootRoute, navController)
            }
        )
    }
}

private fun NavGraphBuilder.addCoinDetailScreen(
    rootRoute: DestinationRoute,
    navController: NavHostController,
) {
    coinDetailScreen(
        rootRoute = rootRoute,
        onBack = navController::popBackStack,
    )
}