package pk.mahdi.cryptotracker.core.data.networking

import android.util.Log
import pk.mahdi.cryptotracker.domain.util.NetworkError
import pk.mahdi.cryptotracker.domain.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

const val SAFE_CALL_TAG = "Network_safeCall("
suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        Log.e(SAFE_CALL_TAG, e.message.toString())
        return Result.Error(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        Log.e(SAFE_CALL_TAG, e.message.toString())
        return Result.Error(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        Log.e(SAFE_CALL_TAG, e.message.toString())
        coroutineContext.ensureActive()
        return Result.Error(NetworkError.UNKNOWN)
    }

    return responseToResult(response)
}