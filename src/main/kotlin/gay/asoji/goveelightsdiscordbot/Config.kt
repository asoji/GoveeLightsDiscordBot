package gay.asoji.goveelightsdiscordbot

import com.akuleshov7.ktoml.file.TomlFileReader
import gay.asoji.goveelightsdiscordbot.data.BotConfig
import kotlinx.serialization.serializer
import kotlin.system.exitProcess

object Config {
    private val configPath = System.getProperty("govee_config", "config.toml")

    fun loadConfig(): BotConfig {
        return try {
            logger.info("Attemping to load config")
            TomlFileReader.decodeFromFile(serializer(), configPath)
        } catch (e: Exception) {
            logger.error(
                """
                    
                    
                    #######################################################
                    #                                                     #
                    #    oopsies woopsies we did a wittle fwucky wucky    #
                    #                                                     #
                    #######################################################
                    
                """.trimIndent(), e)
            exitProcess(1)
        }
    }
}