package so.arctic.domain

import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.slf4j.LoggerFactory

class WebSocketPipeline {
    private val log = LoggerFactory.getLogger(this::class.java)
    fun chatSocket(connections: MutableSet<Connection>): suspend DefaultWebSocketServerSession.() -> Unit = {
        log.info("Adding user!")
        val thisConnection = Connection(this)
        connections += thisConnection
        try {
            send("You are connected! There are ${connections.count()} users here.")
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val receivedText = frame.readText()
                val textWithUsername = "[${thisConnection.name}]: $receivedText"
                connections.forEach {
                    it.session.send(textWithUsername)
                }
                if (receivedText.equals("bye", ignoreCase = true)) {
                    close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                }
            }
        } catch (e: Exception) {
            log.error(e.localizedMessage)
        } finally {
            log.info("Removing $thisConnection!")
            connections -= thisConnection
        }
    }

}