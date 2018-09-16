package scratchpad.undertow

import io.undertow.Undertow
import io.undertow.server.handlers.resource.ClassPathResourceManager
import io.undertow.server.handlers.resource.ResourceHandler


object Manager {
    val manager = ClassPathResourceManager(this.javaClass.classLoader)
}

fun main(args: Array<String>) {
    val server = Undertow.builder()
            .addHttpListener(8080, "localhost")


            .setHandler {// exchange ->
//                exchange.responseHeaders.put(Headers.CONTENT_TYPE, "text/plain")
//                exchange.responseSender.send("Hello World")
                ResourceHandler(Manager.manager)

            }


            .build()
    server.start()
}