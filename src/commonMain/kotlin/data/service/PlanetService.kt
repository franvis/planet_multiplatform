package data.service

import data.PlatformDispatcher
import domain.entity.Planet
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable

class PlanetService(private val platformDispatcher: PlatformDispatcher) {
    fun getPlanets(callback: (List<Planet>) -> Unit) {
        GlobalScope.apply {
            launch(platformDispatcher.getDispatcher()) {
                val result = HttpClient() {
                    install(JsonFeature) {
                        serializer = KotlinxSerializer().apply {
                            register(NextDayDeliveryResponse.serializer())
                        }
                    }
                }.get<NextDayDeliveryResponse>("https://y3fsc8hysh.execute-api.us-east-2.amazonaws.com/training/planets")

                callback(result.planets.map{it.mapToDomain()})
            }
        }
    }
}

@Serializable
data class NextDayDeliveryResponse(val planets: List<PlanetDto>)

@Serializable
data class PlanetDto(
    val id: String,
    val name: String,
    val shortDescription: String,
    val imageUrl: String,
    val distanceFromSun: Double
) {
    fun mapToDomain(): Planet {
        return Planet(id, name, shortDescription, distanceFromSun, imageUrl)
    }
}
