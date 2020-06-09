package domain.interactor

import data.PlatformDispatcher
import data.service.PlanetService
import domain.entity.Planet

class GetPlanets {

    private val platformDispatcher = PlatformDispatcher()
    private val planetService by lazy { PlanetService(platformDispatcher) }
    operator fun invoke(callback: (List<Planet>) -> Unit) {
        planetService.getPlanets(callback)
    }
}