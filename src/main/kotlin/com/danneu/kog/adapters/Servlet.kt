package com.danneu.kog.adapters

import com.danneu.kog.ContentType
import javax.servlet.http.HttpServlet
import com.danneu.kog.Handler
import com.danneu.kog.Header
import com.danneu.kog.HeaderPair
import com.danneu.kog.Method
import com.danneu.kog.Protocol
import com.danneu.kog.Request
import com.danneu.kog.Response
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


// Create a Servlet from a kog handler
class Servlet(val handler: Handler) : HttpServlet() {
    override fun service (servletRequest: HttpServletRequest, servletResponse: HttpServletResponse) {
        val request = intoKogRequest(servletRequest)
        val response = handler(request).finalize()
        updateServletResponse(servletResponse, response)
    }

    companion object {
        fun updateServletResponse(servletResponse: HttpServletResponse, response: Response) {
            servletResponse.status = response.status.code

            // Set headers
            for ((k, v) in response.headers.iterator()) {
                servletResponse.addHeader(k.toString(), v)
            }

            // Some headers have special setters
            response.getHeader(Header.ContentType)?.let { type ->
                servletResponse.contentType = type
            }

            //contentType property overwrites the header if it is set
            response.contentType?.let { type ->
                servletResponse.contentType = type.toString()
            }

            response.body.pipe(servletResponse.outputStream)
        }

        fun intoKogRequest(r: HttpServletRequest): Request {
            return Request(
              serverPort = r.serverPort,
              serverName = r.serverName,
              remoteAddr = r.remoteAddr,
              href = r.requestURL.toString() + if (r.queryString != null) "?" + r.queryString else "",
              queryString = r.queryString,
              scheme = r.scheme,
              method = Method.fromString(r.method),
              protocol = Protocol.fromString(r.protocol),
              headers = expandHeaders(r),
              contentType = r.contentType?.let { ContentType.parse(it) },
              length = if (r.contentLength >= 0) r.contentLength else null,
              body = r.inputStream,
              path = r.pathInfo ?: "/"
            )
        }
    }
}


private fun expandHeaders(r: HttpServletRequest): MutableList<HeaderPair> {
    return r.headerNames.asSequence().flatMap { name ->
        r.getHeaders(name).asSequence().map { value ->
            Header.fromString(name) to value
        }
    }.toMutableList()
}
