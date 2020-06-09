package data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.darwin.dispatch_async
import platform.darwin.dispatch_queue_global_t
import platform.darwin.dispatch_queue_t
import kotlin.coroutines.CoroutineContext

actual class PlatformDispatcher {
    actual fun getDispatcher(): CoroutineDispatcher =
        NsQueueDispatcher(dispatch_queue_global_t())

    internal class NsQueueDispatcher(
        private val dispatchQueue: dispatch_queue_t
    ) : CoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) {
            dispatch_async(dispatchQueue) {
                block.run()
            }
        }
    }
}
