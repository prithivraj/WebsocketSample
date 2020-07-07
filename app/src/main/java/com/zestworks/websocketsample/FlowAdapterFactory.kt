import com.tinder.scarlet.Stream
import com.tinder.scarlet.StreamAdapter
import com.tinder.scarlet.utils.getRawType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import java.lang.reflect.Type

/**
 * A [stream adapter factory][StreamAdapter.Factory] that uses Kotlin Flow.
 */
@ExperimentalCoroutinesApi
class FlowStreamAdapterFactory : StreamAdapter.Factory {
    override fun create(type: Type): StreamAdapter<Any, Any> {
        return when (type.getRawType()) {
            Flow::class.java -> FlowStreamAdapter()
            else -> throw IllegalArgumentException()
        }
    }
}

/**
 *  A [stream adapter][StreamAdapter] that adapts the given stream
 *  and emit as kotlin Flow
 */
internal class FlowStreamAdapter<T> : StreamAdapter<T, Flow<T>> where T : Any {
    override fun adapt(stream: Stream<T>) = stream.asFlow()
}