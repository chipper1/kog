package com.danneu.kog

sealed class Header(val key: String) {
    // Keys are canonicalized on creation.
    class Custom(key: String) : Header(key.toLowerCase()) {
        override fun equals(other: Any?): Boolean {
            return other is Custom && key == other.key
        }

        override fun hashCode() = key.hashCode()
    }

    // AUTHENTICATION

    object WwwAuthenticate : Header(WWW_AUTHENTICATE)
    object Authorization : Header(AUTHORIZATION)
    object ProxyAuthenticate : Header(PROXY_AUTHENTICATE)
    object ProxyAuthorization : Header(PROXY_AUTHORIZATION)

    // CACHING

    object Age : Header(AGE)
    object CacheControl : Header(CACHE_CONTROL)
    object Expires : Header(EXPIRES)
    object Pragma : Header(PRAGMA)
    object Warning : Header(WARNING)

    // CLIENT HINTS

    object AcceptCh : Header(ACCEPT_CH)
    object ContentDpr : Header(CONTENT_DPR)
    object Dpr : Header(DPR)
    object Downlink : Header(DOWNLINK)
    object SaveData : Header(SAVE_DATA)
    object ViewportWidth : Header(VIEWPORT_WIDTH)
    object Width : Header(WIDTH)

    // CONDITIONALS

    object LastModified : Header(LAST_MODIFIED)
    object Etag : Header(ETAG)
    object IfMatch : Header(IF_MATCH)
    object IfNoneMatch : Header(IF_NONE_MATCH)
    object IfModifiedSince : Header(IF_MODIFIED_SINCE)
    object IfUnmodifiedSince : Header(IF_UNMODIFIED_SINCE)

    // CONNECTION MANAGEMENT

    object Connection : Header(CONNECTION)
    object KeepAlive : Header(KEEP_ALIVE)

    // CONTENT NEGOTIATION

    object Accept : Header(ACCEPT)
    object AcceptCharset : Header(ACCEPT_CHARSET)
    object AcceptEncoding : Header(ACCEPT_ENCODING)
    object AcceptLanguage : Header(ACCEPT_LANGUAGE)

    // CONTROLS

    object Expect : Header(EXPECT)
    object MaxForwards : Header(MAX_FORWARDS)

    // COOKIES

    object Cookie : Header(COOKIE)
    object SetCookie : Header(SET_COOKIE)
    object Cookie2 : Header(COOKIE2)
    object SetCookie2 : Header(SET_COOKIE2)

    // CORS

    object AccessControlAllowOrigin : Header(ACCESS_CONTROL_ALLOW_ORIGIN)
    object AccessControlAllowCredentials : Header(ACCESS_CONTROL_ALLOW_CREDENTIALS)
    object AccessControlAllowHeaders : Header(ACCESS_CONTROL_ALLOW_HEADERS)
    object AccessControlAllowMethods : Header(ACCESS_CONTROL_ALLOW_METHODS)
    object AccessControlExposeHeaders : Header(ACCESS_CONTROL_EXPOSE_HEADERS)
    object AccessControlMaxAge : Header(ACCESS_CONTROL_MAX_AGE)
    object AccessControlRequestHeaders : Header(ACCESS_CONTROL_REQUEST_HEADERS)
    object AccessControlRequestMethod : Header(ACCESS_CONTROL_REQUEST_METHOD)
    object Origin : Header(ORIGIN)

    // DO NOT TRACK

    object Dnt : Header(DNT)
    object Tk : Header(TK)

    // DOWNLOADS

    object ContentDisposition : Header(CONTENT_DISPOSITION)

    // MESSAGE BODY INFO

    object ContentLength : Header(CONTENT_LENGTH)
    object ContentType : Header(CONTENT_TYPE)
    object ContentEncoding : Header(CONTENT_ENCODING)
    object ContentLanguage : Header(CONTENT_LANGUAGE)
    object ContentLocation : Header(CONTENT_LOCATION)


    // MESSAGE ROUTING

    object Via : Header(VIA)

    // REDIRECTS

    object Location : Header(LOCATION)

    // REQUEST CONTEXT

    object From : Header(FROM)
    object Host : Header(HOST)
    object Referer : Header(REFERER) // sic
    object ReferrerPolicy : Header(REFERRER_POLICY)
    object UserAgent : Header(USER_AGENT)

    // RESPONSE CONTEXT

    object Allow : Header(ALLOW)
    object Server : Header(SERVER)

    // RANGE REQUESTS

    object AcceptRanges : Header(ACCEPT_RANGES)
    object Range : Header(RANGE)
    object IfRange : Header(IF_RANGE)
    object ContentRange : Header(CONTENT_RANGE)

    // SECURITY

    object ContentSecurityPolicy : Header(CONTENT_SECURITY_POLICY)
    object ContentSecurityPolicyReportOnly : Header(CONTENT_SECURITY_POLICY_REPORT_ONLY)
    object PublicKeyPins : Header(PUBLIC_KEY_PINS)
    object PublicKeyPinsReportOnly : Header(PUBLIC_KEY_PINS_REPORT_ONLY)
    object StrictTransportSecurity : Header(STRICT_TRANSPORT_SECURITY)
    object UpgradeInsecureRequests : Header(UPGRADE_INSECURE_REQUESTS)
    object XContentTypeOptions : Header(X_CONTENT_TYPE_OPTIONS)
    object XFrameOptions : Header(X_FRAME_OPTIONS)
    object XXssProtection : Header(X_XSS_PROTECTION)

    // SERVER-SENT EVENTS

    object PingFrom : Header(PING_FROM)
    object PingTo : Header(PING_TO)
    object LastEventId : Header(LAST_EVENT_ID)

    // TRANSFER ENCODING

    object TransferEncoding : Header(TRANSFER_ENCODING)
    object Te : Header(TE)
    object Trailer : Header(TRAILER)

    // WEBSOCKETS

    object SecWebSocketKey : Header(SEC_WEBSOCKET_KEY)
    object SecWebSocketExtensions : Header(SEC_WEBSOCKET_EXTENSIONS)
    object SecWebSocketAccept : Header(SEC_WEBSOCKET_ACCEPT)
    object SecWebSocketProtocol : Header(SEC_WEBSOCKET_PROTOCOL)
    object SecWebSocketVersion : Header(SEC_WEBSOCKET_VERSION)

    // OTHER

    object Date : Header(DATE)
    object Link : Header(LINK)
    object RetryAfter : Header(RETRY_AFTER)
    object Upgrade : Header(UPGRADE)
    object Vary : Header(VARY)
    object XContentDuration : Header(X_CONTENT_DURATION)
    object XDnsPrefetchControl : Header(X_DNS_PREFETCH_CONTROL)
    object XRequestedWith : Header(X_REQUESTED_WITH)
    object XUaCompatible : Header(X_UA_COMPATIBLE)

    // Hyphenates capitalize-letter boundaries.
    //
    // Example: "XForwardedFor" -> "X-Forwarded-For"
    override fun toString() = key

    companion object {
        private const val ACCEPT = "accept"
        private const val ACCEPT_CH = "accept-ch"
        private const val ACCEPT_CHARSET = "accept-charset"
        private const val ACCEPT_ENCODING = "accept-encoding"
        private const val ACCEPT_LANGUAGE = "accept-language"
        private const val ACCEPT_RANGES = "accept-ranges"
        private const val ACCESS_CONTROL_ALLOW_CREDENTIALS = "access-control-allow-credentials"
        private const val ACCESS_CONTROL_ALLOW_HEADERS = "access-control-allow-headers"
        private const val ACCESS_CONTROL_ALLOW_METHODS = "access-control-allow-methods"
        private const val ACCESS_CONTROL_ALLOW_ORIGIN = "access-control-allow-origin"
        private const val ACCESS_CONTROL_EXPOSE_HEADERS = "access-control-expose-headers"
        private const val ACCESS_CONTROL_MAX_AGE = "access-control-max-age"
        private const val ACCESS_CONTROL_REQUEST_HEADERS = "access-control-request-headers"
        private const val ACCESS_CONTROL_REQUEST_METHOD = "access-control-request-method"
        private const val AGE = "age"
        private const val ALLOW = "allow"
        private const val AUTHORIZATION = "authorization"
        private const val CACHE_CONTROL = "cache-control"
        private const val CONNECTION = "connection"
        private const val CONTENT_DISPOSITION = "content-disposition"
        private const val CONTENT_DPR = "content-dpr"
        private const val CONTENT_ENCODING = "content-encoding"
        private const val CONTENT_LANGUAGE = "content-language"
        private const val CONTENT_LENGTH = "content-length"
        private const val CONTENT_LOCATION = "content-location"
        private const val CONTENT_RANGE = "content-range"
        private const val CONTENT_SECURITY_POLICY = "content-security-policy"
        private const val CONTENT_SECURITY_POLICY_REPORT_ONLY = "content-security-policy-report-only"
        private const val CONTENT_TYPE = "content-type"
        private const val COOKIE = "cookie"
        private const val COOKIE2 = "cookie2"
        private const val DATE = "date"
        private const val DNT = "dnt"
        private const val DOWNLINK = "downlink"
        private const val DPR = "dpr"
        private const val ETAG = "etag"
        private const val EXPECT = "expect"
        private const val EXPIRES = "expires"
        private const val FROM = "from"
        private const val HOST = "host"
        private const val IF_MATCH = "if-match"
        private const val IF_MODIFIED_SINCE = "if-modified-since"
        private const val IF_NONE_MATCH = "if-none-match"
        private const val IF_RANGE = "if-range"
        private const val IF_UNMODIFIED_SINCE = "if-unmodified-since"
        private const val KEEP_ALIVE = "keep-alive"
        private const val LAST_EVENT_ID = "last-event-id"
        private const val LAST_MODIFIED = "last-modified"
        private const val LINK = "link"
        private const val LOCATION = "location"
        private const val MAX_FORWARDS = "max-forwards"
        private const val ORIGIN = "origin"
        private const val PING_FROM = "ping-from"
        private const val PING_TO = "ping-to"
        private const val PRAGMA = "pragma"
        private const val PROXY_AUTHENTICATE = "proxy-authenticate"
        private const val PROXY_AUTHORIZATION = "proxy-authorization"
        private const val PUBLIC_KEY_PINS = "public-key-pins"
        private const val PUBLIC_KEY_PINS_REPORT_ONLY = "public-key-pins-report-only"
        private const val RANGE = "range"
        private const val REFERER = "referer"
        private const val REFERRER_POLICY = "referrer-policy"
        private const val RETRY_AFTER = "retry-after"
        private const val SAVE_DATA = "save-data"
        private const val SEC_WEBSOCKET_ACCEPT = "sec-websocket-accept"
        private const val SEC_WEBSOCKET_EXTENSIONS = "sec-websocket-extensions"
        private const val SEC_WEBSOCKET_KEY = "sec-websocket-key"
        private const val SEC_WEBSOCKET_PROTOCOL = "sec-websocket-protocol"
        private const val SEC_WEBSOCKET_VERSION = "sec-websocket-version"
        private const val SERVER = "server"
        private const val SET_COOKIE = "set-cookie"
        private const val SET_COOKIE2 = "set-cookie2"
        private const val STRICT_TRANSPORT_SECURITY = "strict-transport-security"
        private const val TE = "te"
        private const val TK = "tk"
        private const val TRAILER = "trailer"
        private const val TRANSFER_ENCODING = "transfer-encoding"
        private const val UPGRADE = "upgrade"
        private const val UPGRADE_INSECURE_REQUESTS = "upgrade-insecure-requests"
        private const val USER_AGENT = "user-agent"
        private const val VARY = "vary"
        private const val VIA = "via"
        private const val VIEWPORT_WIDTH = "viewport-width"
        private const val WARNING = "warning"
        private const val WIDTH = "width"
        private const val WWW_AUTHENTICATE = "www-authenticate"
        private const val X_CONTENT_DURATION = "x-content-duration"
        private const val X_CONTENT_TYPE_OPTIONS = "x-content-type-options"
        private const val X_DNS_PREFETCH_CONTROL = "x-dns-prefetch-control"
        private const val X_FRAME_OPTIONS = "x-frame-options"
        private const val X_REQUESTED_WITH = "x-requested-with"
        private const val X_UA_COMPATIBLE = "x-ua-compatible"
        private const val X_XSS_PROTECTION = "x-xss-protection"

        fun fromString(string: String): Header = when (string.toLowerCase()) {
            ACCEPT -> Accept
            ACCEPT_CH -> AcceptCh
            ACCEPT_CHARSET -> AcceptCharset
            ACCEPT_ENCODING -> AcceptEncoding
            ACCEPT_LANGUAGE -> AcceptLanguage
            ACCEPT_RANGES -> AcceptRanges
            ACCESS_CONTROL_ALLOW_CREDENTIALS -> AccessControlAllowCredentials
            ACCESS_CONTROL_ALLOW_HEADERS -> AccessControlAllowHeaders
            ACCESS_CONTROL_ALLOW_METHODS -> AccessControlAllowMethods
            ACCESS_CONTROL_ALLOW_ORIGIN -> AccessControlAllowOrigin
            ACCESS_CONTROL_EXPOSE_HEADERS -> AccessControlExposeHeaders
            ACCESS_CONTROL_MAX_AGE -> AccessControlMaxAge
            ACCESS_CONTROL_REQUEST_HEADERS -> AccessControlRequestHeaders
            ACCESS_CONTROL_REQUEST_METHOD -> AccessControlRequestMethod
            AGE -> Age
            ALLOW -> Allow
            AUTHORIZATION -> Authorization
            CACHE_CONTROL -> CacheControl
            CONNECTION -> Connection
            CONTENT_DISPOSITION -> ContentDisposition
            CONTENT_DPR -> ContentDpr
            CONTENT_ENCODING -> ContentEncoding
            CONTENT_LANGUAGE -> ContentLanguage
            CONTENT_LENGTH -> ContentLength
            CONTENT_LOCATION -> ContentLocation
            CONTENT_RANGE -> ContentRange
            CONTENT_SECURITY_POLICY -> ContentSecurityPolicy
            CONTENT_SECURITY_POLICY_REPORT_ONLY -> ContentSecurityPolicyReportOnly
            CONTENT_TYPE -> ContentType
            COOKIE -> Cookie
            COOKIE2 -> Cookie2
            DATE -> Date
            DNT -> Dnt
            DOWNLINK -> Downlink
            DPR -> Dpr
            ETAG -> Etag
            EXPECT -> Expect
            EXPIRES -> Expires
            FROM -> From
            HOST -> Host
            IF_MATCH -> IfMatch
            IF_MODIFIED_SINCE -> IfModifiedSince
            IF_NONE_MATCH -> IfNoneMatch
            IF_RANGE -> IfRange
            IF_UNMODIFIED_SINCE -> IfUnmodifiedSince
            KEEP_ALIVE -> KeepAlive
            LAST_EVENT_ID -> LastEventId
            LAST_MODIFIED -> LastModified
            LINK -> Link
            LOCATION -> Location
            MAX_FORWARDS -> MaxForwards
            ORIGIN -> Origin
            PING_FROM -> PingFrom
            PING_TO -> PingTo
            PRAGMA -> Pragma
            PROXY_AUTHENTICATE -> ProxyAuthenticate
            PROXY_AUTHORIZATION -> ProxyAuthorization
            PUBLIC_KEY_PINS -> PublicKeyPins
            PUBLIC_KEY_PINS_REPORT_ONLY -> PublicKeyPinsReportOnly
            RANGE -> Range
            REFERER -> Referer
            REFERRER_POLICY -> ReferrerPolicy
            RETRY_AFTER -> RetryAfter
            SAVE_DATA -> SaveData
            SEC_WEBSOCKET_ACCEPT -> SecWebSocketAccept
            SEC_WEBSOCKET_EXTENSIONS -> SecWebSocketExtensions
            SEC_WEBSOCKET_KEY -> SecWebSocketKey
            SEC_WEBSOCKET_PROTOCOL -> SecWebSocketProtocol
            SEC_WEBSOCKET_VERSION -> SecWebSocketVersion
            SERVER -> Server
            SET_COOKIE -> SetCookie
            SET_COOKIE2 -> SetCookie2
            STRICT_TRANSPORT_SECURITY -> StrictTransportSecurity
            TE -> Te
            TK -> Tk
            TRAILER -> Trailer
            TRANSFER_ENCODING -> TransferEncoding
            UPGRADE -> Upgrade
            UPGRADE_INSECURE_REQUESTS -> UpgradeInsecureRequests
            USER_AGENT -> UserAgent
            VARY -> Vary
            VIA -> Via
            VIEWPORT_WIDTH -> ViewportWidth
            WARNING -> Warning
            WIDTH -> Width
            WWW_AUTHENTICATE -> WwwAuthenticate
            X_CONTENT_DURATION -> XContentDuration
            X_CONTENT_TYPE_OPTIONS -> XContentTypeOptions
            X_DNS_PREFETCH_CONTROL -> XDnsPrefetchControl
            X_FRAME_OPTIONS -> XFrameOptions
            X_REQUESTED_WITH -> XRequestedWith
            X_UA_COMPATIBLE -> XUaCompatible
            X_XSS_PROTECTION -> XXssProtection
            else -> Custom(string.toLowerCase())
        }
    }

}



// "foo-bar-qux" -> "Foo-Bar-Qux"
fun String.toCookieCase(): String {
    return this.toLowerCase()
        // Capitalize the first letter after each boundary
        .replace(Regex("""(?<=\b)([a-z])"""), { it.value.toUpperCase() })
}
