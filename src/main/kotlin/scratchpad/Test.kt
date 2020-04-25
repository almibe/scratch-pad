
package scratchpad

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


fun createFlow(): Flow<Int> {
    return flow<Int> {
        try {
            emit(1)
            emit(2)
            //TODO()
        } finally {
            println("fuck")
        }
    }
}


fun main() {
    runBlocking {

            createFlow().take(1).onEach { println(it) }.toList()

    }
    Thread.sleep(1000)
}
