package gay.asoji.goveelightsdiscordbot.data

import kotlinx.serialization.Serializable

@Serializable
data class BotConfig(
    val discord: DiscordConfig,
    val govee: GoveeConfig
) {
    @Serializable
    data class DiscordConfig(
        val serverSnowflake: String,
        val token: String,
        val owner: String
    )

    @Serializable
    data class GoveeConfig(
        val apiKey: String,
        val deviceModel: String,
        val macId: String,
    )
}
