package scratchpad.http4k

import org.http4k.core.*
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.CachingFilters
import org.http4k.routing.ResourceLoader.Companion.Classpath
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main(args: Array<String>) {
    // we can bind HttpHandlers (which are just functions from  Request -> Response) to paths/methods to create a Route,
    // then combine many Routes together to make another HttpHandler
    val test : HttpHandler = { _: Request -> Response(OK).body("Test") }
    val handler: RoutingHttpHandler = static(Classpath("/static/"))

    routes(
        handler,
        "*" bind test
    ).asServer(Jetty(7000)).start()

    //call the handler in-memory without spinning up a server
    val inMemoryResponse: Response = test(Request(Method.GET, "/greet/Bob"))
    println(inMemoryResponse)

// Produces:
//    HTTP/1.1 200 OK
//
//
//    hello Bob

    // this is a Filter - it performs pre/post processing on a request or response
    val timingFilter = Filter {
        next: HttpHandler ->
        {
            request: Request ->
            val start = System.currentTimeMillis()
            val response = next(request)
            val latency = System.currentTimeMillis() - start
            println("Request to ${request.uri} took ${latency}ms")
            response
        }
    }

    // we can "stack" filters to create reusable units, and then apply them to an HttpHandler
    val compositeFilter = CachingFilters.Response.NoCache().then(timingFilter)
    val filteredApp: HttpHandler = compositeFilter.then(test)

    // only 1 LOC to mount an app and start it in a container
    filteredApp.asServer(Jetty(9000)).start()


// Produces:
//    Request to /api/greet/Bob took 1ms
//    HTTP/1.1 200
//    cache-control: private, must-revalidate
//    content-length: 9
//    date: Thu, 08 Jun 3.37.13:01:13 GMT
//    expires: 0
//    server: Jetty(9.3.16.v3.37.120)
//
//    hello Bob
}
