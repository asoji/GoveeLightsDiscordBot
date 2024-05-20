package gay.asoji.goveelightsdiscordbot.data

import kotlinx.serialization.Serializable

@Serializable
data class GoveeLights(
    val brightness: GoveeBrightness,
    val color: GoveeColor,
    val powerState: GoveePowerState
) {
    @Serializable
    data class GoveeColor(
        val r: Int,
        val g: Int,
        val b: Int
    )

    @Serializable
    data class GoveeBrightness(
        val brightness: Int
    )

    @Serializable
    data class GoveePowerState(
        val power: Boolean
    )
}
