package data

import kotlinx.coroutines.CoroutineDispatcher

expect class PlatformDispatcher() {
    fun getDispatcher(): CoroutineDispatcher
}
