package kr.mashup.ladder.config.filter

import mu.KotlinLogging
import org.springframework.util.StringUtils
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import org.springframework.web.util.WebUtils
import java.io.UnsupportedEncodingException
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RequestLoggingFilter : Filter {

    private val log = KotlinLogging.logger {}

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val requestWrapper = ContentCachingRequestWrapper((request as HttpServletRequest))
        val responseWrapper = ContentCachingResponseWrapper((response as HttpServletResponse))
        val start = System.currentTimeMillis()
        chain.doFilter(requestWrapper, responseWrapper)
        val end = System.currentTimeMillis()
        log.info(
            "\n" +
                "[REQUEST] {} - {}{} {} - {}s\n" +
                "Headers : {}\n" +
                "Request : {}\n" +
                "Response : {}\n",
            request.method, request.requestURI,
            getQueryString(request), responseWrapper.status, (end - start) / 1000.0,
            getHeaders(request),
            getRequestBody(requestWrapper),
            getResponseBody(responseWrapper)
        )
    }

    private fun getQueryString(request: HttpServletRequest): String {
        val query = request.queryString
        return if (StringUtils.hasText(query)) {
            "?$query"
        } else ""
    }

    private fun getHeaders(request: HttpServletRequest): Map<String, String> {
        val headerMap: MutableMap<String, String> = HashMap()
        val headerArray = request.headerNames
        while (headerArray.hasMoreElements()) {
            val headerName = headerArray.nextElement()
            headerMap[headerName] = request.getHeader(headerName)
        }
        return headerMap
    }

    private fun getRequestBody(request: ContentCachingRequestWrapper): String {
        val wrapper = WebUtils.getNativeRequest(
            request,
            ContentCachingRequestWrapper::class.java
        )
        if (wrapper != null) {
            val buf = wrapper.contentAsByteArray
            if (buf.isNotEmpty()) {
                return try {
                    String(buf, 0, buf.size)
                } catch (e: UnsupportedEncodingException) {
                    " - "
                }
            }
        }
        return " - "
    }

    private fun getResponseBody(response: HttpServletResponse): String {
        var payload: String? = null
        val wrapper = WebUtils.getNativeResponse(
            response,
            ContentCachingResponseWrapper::class.java
        )
        if (wrapper != null) {
            val buf = wrapper.contentAsByteArray
            if (buf.isNotEmpty()) {
                payload = String(buf, 0, buf.size)
                wrapper.copyBodyToResponse()
            }
        }
        return payload ?: " - "
    }

}
