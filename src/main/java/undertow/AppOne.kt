package undertow

import io.undertow.Undertow
import io.undertow.io.IoCallback
import io.undertow.security.api.AuthenticationMechanism
import io.undertow.security.api.AuthenticationMode
import io.undertow.security.handlers.AuthenticationCallHandler
import io.undertow.security.handlers.AuthenticationConstraintHandler
import io.undertow.security.handlers.AuthenticationMechanismsHandler
import io.undertow.security.handlers.SecurityInitialHandler
import io.undertow.security.idm.IdentityManager
import io.undertow.security.impl.BasicAuthenticationMechanism
import io.undertow.security.impl.FormAuthenticationMechanism
import io.undertow.server.HttpHandler
import java.util.*

/**
 * Example of HTTP Basic auth
 *
 *
 * TODO: this needs to be cleaned up
 *
 * @author Stuart Douglas
 */
fun main(args: Array<String>) {

    println("You can login with the following credentials:")
    println("User: userOne Password: passwordOne")
    println("User: userTwo Password: passwordTwo")

    val users = HashMap<String, CharArray>(2)
    users["userOne"] = "passwordOne".toCharArray()
    users["userTwo"] = "passwordTwo".toCharArray()

    val identityManager = MapIdentityManager(users)

    val server = Undertow.builder()
            .addHttpListener(8080, "localhost")
            .setHandler(addSecurity(HttpHandler { exchange ->
                val context = exchange.securityContext
                exchange.responseSender.send("Hello " + context.authenticatedAccount.principal.name, IoCallback.END_EXCHANGE)
            }, identityManager))
            .build()
    server.start()
}

private fun addSecurity(toWrap: HttpHandler, identityManager: IdentityManager): HttpHandler {
    var handler = toWrap
    handler = AuthenticationCallHandler(handler)
    handler = AuthenticationConstraintHandler(handler)
    val mechanisms = listOf<AuthenticationMechanism>(
            BasicAuthenticationMechanism("My Realm"),
            FormAuthenticationMechanism()
    )
    handler = AuthenticationMechanismsHandler(handler, mechanisms)
    handler = SecurityInitialHandler(AuthenticationMode.PRO_ACTIVE, identityManager, handler)
    return handler
}

