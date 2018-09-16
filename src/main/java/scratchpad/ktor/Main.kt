
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.request.location
import io.ktor.request.path
import io.ktor.response.respondText
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    val server = embeddedServer(Netty, port = 8080) {

        this.routing {
            static("static") {
                resources("static")
            }
            route("{...}") {
                this.handle {
                    val location = this.context.request.location()
                    val path = this.context.request.path()
                    this.context.respondText("location - $location -- path $path")
                    this.context.response.status(HttpStatusCode.OK)
                }
            }
        }

//        routing {
//
//            route("{path...}") {
//
//
//
//                handle {
//                    this.context.respondText("Test ${this.context.url()} -- ${this.context.parameters.getAll("path")}")
//
//                }
//            }

//            get("/") {
//                call.respondText("Hello World!", ContentType.Text.Plain)
//            }
//            get("/demo") {
//                call.respondText("HELLO WORLD!")
//            }
//        }
    }
    server.start(wait = true)
}
