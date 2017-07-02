package com.akolov.curriusers

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@SpringBootApplication
class CurriUsersApplication

fun main(args: Array<String>) {
    SpringApplication.run(CurriUsersApplication::class.java, *args)
}

annotation class Secure(val roles: String)

@Component
class SecurityInterceptor : HandlerInterceptorAdapter() {

    val log : Logger = LoggerFactory.getLogger(SecurityInterceptor::class.java)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse?, handler: Any?): Boolean {
        if (handler is HandlerMethod) {
            val ann = handler.getMethodAnnotation(Secure::class.java)
            if( ann != null) {
                val secRoles = ann.roles.split(",")
                if( secRoles.size > 0) {
                    val requestRoles = getRoles(request)
                    val ok = secRoles.find {  requestRoles.contains( it) } != null
                    if( !ok) {
                        log.warn("Illegal access attempt for method ${handler.method.name}")
                        response?.sendError(401)
                        return false
                    }
                }
            }

        }
        return super.preHandle(request, response, handler)
    }

    private fun getRoles(request: HttpServletRequest): List<String> {
        val roleAny = listOf("any")
        val headerValue = request.getHeader("x-curri-roles")
        if( headerValue != null) {
            return roleAny + headerValue.split(",").map { it.trim() }
        }
        return roleAny
    }

    override fun afterConcurrentHandlingStarted(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?) {
        super.afterConcurrentHandlingStarted(request, response, handler)
    }

    override fun postHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, modelAndView: ModelAndView?) {
        super.postHandle(request, response, handler, modelAndView)
    }

    override fun afterCompletion(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, ex: Exception?) {
        super.afterCompletion(request, response, handler, ex)
    }
}

@Configuration
class webConfig : WebMvcConfigurerAdapter() {

    @Autowired
    var securityInterceptor: SecurityInterceptor? = null

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(securityInterceptor)
    }
}