package domain.entity

data class Planet(
    val id: String,
    val name: String,
    val shortDescription: String,
    val distanceFromTheSun: Double,
    val imageUrl: String
)
