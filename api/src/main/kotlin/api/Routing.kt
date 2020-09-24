package api

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get

data class Beer(
    val name: String
)

val beers = listOf(
    Beer("Heineken"),
    Beer("Harp")
)

data class Bar(
    val name: String,
    val beers: List<Beer>
)

val bars = listOf(
    Bar("Lavery's", beers),
    Bar("Thompson's", listOf(beers[0])),
    Bar("Shit bar", emptyList())
)

// used to return a list of HTTP assets in a consistent way
data class MultiResponse<T>(
    val elements: List<T>
)


fun Routing.api() {
    get("/beers") {
        fetchAllBeers(call)
    }

    get("/serves/{beerName}") {
        doesBarServe(call)
    }
}

suspend fun fetchAllBeers(call: ApplicationCall) {
    call.respond(MultiResponse(beers))
}

suspend fun doesBarServe(call: ApplicationCall) {
    val searchName = call.parameters["beerName"] ?: ""
    val matchingBeer = beers.find { it.name.contains(searchName) }

    if (matchingBeer == null) {
        call.respond(HttpStatusCode.BadRequest, "Beer not found")
    } else {
        val restaurantsServingMatchingBeer = bars.filter { it.beers.contains(matchingBeer) }
        call.respond(MultiResponse(restaurantsServingMatchingBeer))
    }
}
