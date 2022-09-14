package so.arctic.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import so.arctic.domain.Connection
import so.arctic.domain.WebSocketPipeline
import java.util.*

fun Application.configureRouting() {
    val pipe = WebSocketPipeline()
    routing {
        val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())

        get("/") { call.respondText("Hello World!") }

        webSocket("/chat", handler = pipe.chatSocket(connections))
    }
}

