package scratchpad.undertow

import io.undertow.Handlers
import io.undertow.Undertow
import io.undertow.server.HttpHandler

object BasicServer {

    @JvmStatic
    fun main(args: Array<String>) {
        val handler = Handlers.pathTemplate()

        val server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(handler)
                .build()
        server.start()

        handler.add("/test", HttpHandler { exchange ->
            exchange.responseSender.send("Hello ")
        })

        handler.add("/test", HttpHandler { exchange ->
            exchange.responseSender.send("Hello 2")
        })
    }
}
