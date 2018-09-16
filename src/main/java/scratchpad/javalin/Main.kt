package scratchpad.javalin

import io.javalin.Javalin


fun main(args: Array<String>) {
    val app = Javalin.create().start(7000)

    app.get("/test/") { ctx ->
        ctx.status(200)
        ctx.result("Test")
    }

    app.get("/test/*") { ctx ->
        ctx.status(200)
        ctx.result("Test Splat")
    }

    app.after("hello/*") { ctx ->
        ctx.status(200)
        ctx.result("Hello World")

    }
}
