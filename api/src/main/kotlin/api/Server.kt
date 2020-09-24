package api

import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.jackson.JacksonConverter
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    val server = embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            register(ContentType.Application.Json, JacksonConverter())
        }
        routing {
            api()
        }
    }
    server.start(wait = true)
}



