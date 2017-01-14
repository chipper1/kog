package com.danneu.kog.batteries

import com.danneu.kog.ByteLength
import com.danneu.kog.Handler
import com.danneu.kog.Header
import com.danneu.kog.Method
import com.danneu.kog.Middleware
import com.danneu.kog.Request
import com.danneu.kog.Response
import com.danneu.kog.ResponseBody
import com.danneu.kog.SafeRouter
import com.danneu.kog.Server
import java.io.InputStream
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.util.zip.GZIPOutputStream


// TODO: This is just stubbed out
// TODO: Impl real negotiation parsing / matching
// TODO: Consider a Vary tool for appending it instead of assoc'ing it

// https://www.fastly.com/blog/best-practices-for-using-the-vary-header


fun compress(threshold: ByteLength = ByteLength.ofBytes(1024)): Middleware = { handler ->
    fun(request: Request): Response {
        val response = handler(request)

        response.setHeader(Header.Vary, "Accept-Encoding")

        // SHORT CIRCUITS

        if (request.method == Method.Head) return response
        // Body is empty
        if (response.body is ResponseBody.None) return response
        if (response.status.empty) return response
        // Body already encoded
        if (response.getHeader(Header.ContentEncoding) != null) return response
        // Body length is not-null and it doesn't meet threshold
        if (response.body.length ?: Long.MAX_VALUE < threshold.byteLength) return response

        // COMPRESS

        return response.apply {
            setHeader(Header.ContentEncoding, "gzip")
            removeHeader(Header.ContentLength)
            setBody(ResponseBody.InputStream(compressBody(response.body)))
        }
    }
}


// TODO: Don't wait on the compression.
private fun compressBody(body: ResponseBody): InputStream {
    val pipeIn = PipedInputStream()
    val pipeOut = PipedOutputStream(pipeIn)
    GZIPOutputStream(pipeOut).use { gzipOut ->
        body.pipe(gzipOut)
    }
    return pipeIn
}


fun main(args: Array<String>) {
    val router = SafeRouter {
        group(compress()) {
            get("/a", fun(): Handler = { Response().text("hello") })
            get("/b", fun(): Handler = { Response().text("test") })
        }
        group(compress(threshold = ByteLength.zero)) {
            get("/c", fun(): Handler = { Response().text("test") })
        }
    }

    Server(router.handler()).listen(3000)
}
