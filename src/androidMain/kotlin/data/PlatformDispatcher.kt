package data

import kotlinx.coroutines.Dispatchers

actual class PlatformDispatcher {
    actual fun getDispatcher() = Dispatchers.IO
}