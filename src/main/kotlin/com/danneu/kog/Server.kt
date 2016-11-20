
package com.danneu.kog

import com.danneu.kog.json.Encoder as JE
import com.danneu.kog.json.Decoder as JD
import org.eclipse.jetty.server.Handler as JettyHandler
import org.eclipse.jetty.server.HttpConfiguration
import org.eclipse.jetty.server.HttpConnectionFactory
import org.eclipse.jetty.server.Request as JettyRequest
import org.eclipse.jetty.server.Server as JettyServer
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.server.handler.AbstractHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.eclipse.jetty.util.thread.QueuedThreadPool
import org.eclipse.jetty.io.EofException
import com.danneu.kog.adapters.Servlet
import org.eclipse.jetty.server.handler.ContextHandler
import org.eclipse.jetty.server.handler.HandlerCollection
import org.eclipse.jetty.server.handler.HandlerList
import org.eclipse.jetty.websocket.server.WebSocketServerFactory
import org.eclipse.jetty.websocket.server.WebSocketHandler as JettyWebSocketHandler
import org.eclipse.jetty.server.Request as JettyServerRequest


// Lift a kog handler into a jetty handler
class JettyHandler(val handler: Handler, val insertContextHandler: (String, WebSocketAcceptor) -> Unit) : AbstractHandler() {
    val installedSocketHandlers: MutableSet<String> = mutableSetOf()

    override fun handle(target: String, baseRequest: JettyServerRequest, servletRequest: HttpServletRequest, servletResponse: HttpServletResponse) {
        val request = Servlet.intoKogRequest(servletRequest)
        val response = handler(request)
        if (response.status == Status.switchingProtocols && response.webSocket != null && WebSocketServerFactory().isUpgradeRequest(servletRequest, servletResponse)) {
            val (key, accept) = response.webSocket!!
            if (!installedSocketHandlers.contains(key)) {
                installedSocketHandlers.add(key)
                insertContextHandler(key, accept)
            }
            // HACK: Store info in this Any bucket
            servletRequest.setAttribute("kog-request", request)
        } else {
            Servlet.updateServletResponse(servletResponse, response)
            baseRequest.isHandled = true
        }
    }
}


class Server(val handler: Handler = { Response(Status.notFound) }, val websockets: Map<String, WebSocketAcceptor> = emptyMap()) {
    val jettyServer: org.eclipse.jetty.server.Server

    init {
        val threadPool = QueuedThreadPool(50)
        val server = org.eclipse.jetty.server.Server(threadPool)
        val httpConfig = HttpConfiguration()
        httpConfig.sendServerVersion = false
        val httpFactory = HttpConnectionFactory(httpConfig)
        val serverConnector = ServerConnector(server, httpFactory)
        serverConnector.idleTimeout = 200000
        server.addConnector(serverConnector)
        jettyServer = server
    }

    fun listen(port: Int): Server {
        (jettyServer.connectors.first() as ServerConnector).port = port

        val handlers = HandlerCollection(true)

        val insertContextHandler: (key: String, accept: WebSocketAcceptor) -> Unit = { key, accept ->
            val context = ContextHandler(key)
            context.handler = WebSocket.handler(accept)
            context.allowNullPathInfo = true  // don't redirect /foo to /foo/
            context.server = jettyServer
            context.start()
            handlers.addHandler(context)
        }

        handlers.addHandler(JettyHandler(Server.middleware()(handler), insertContextHandler))

        jettyServer.handler = handlers

        try {
            jettyServer.start()
            jettyServer.join()
            return this
        } catch (ex: Exception) {
            jettyServer.stop()
            throw ex
        }
    }

    companion object
}


// MIDDLEWARE


// The server's stack of top-level middleware. This should always
// wrap the user's final handler.
fun Server.Companion.middleware(): Middleware = composeMiddleware(
  ::finalizer,
  ::errorHandler
)


// Must be last middleware to touch the response
private fun finalizer(handler: Handler): Handler {
    return { req -> handler(req).finalize() }
}


// Catches uncaught errors and lifts them into 500 responses
private fun errorHandler(handler: Handler): Handler {
    return { req ->
        try {
            handler(req)
        } catch (ex: EofException) {
            // We can't do anything about early client hangup
            Response(Status.internalError)
        } catch (ex: Exception) {
            System.err.print("Unhandled error: ")
            ex.printStackTrace(System.err)
            Response(Status.internalError)
        }
    }
}
