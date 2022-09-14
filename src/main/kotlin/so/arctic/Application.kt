package so.arctic

import io.ktor.server.application.*
import io.ktor.server.netty.*
import so.arctic.plugins.configureHTTP
import so.arctic.plugins.configureRouting
import so.arctic.plugins.configureSerialization
import so.arctic.plugins.configureSockets

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    configureHTTP()
    configureSerialization()
    configureSockets()
    configureRouting()
}