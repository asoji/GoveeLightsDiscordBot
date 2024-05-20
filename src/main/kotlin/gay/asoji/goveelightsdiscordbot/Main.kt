package gay.asoji.goveelightsdiscordbot

import dev.minn.jda.ktx.events.listener
import dev.minn.jda.ktx.jdabuilder.default
import dev.minn.jda.ktx.jdabuilder.intents
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.requests.GatewayIntent
import xyz.artrinix.aviation.Aviation
import xyz.artrinix.aviation.AviationBuilder
import xyz.artrinix.aviation.events.AviationExceptionEvent
import xyz.artrinix.aviation.events.CommandFailedEvent
import xyz.artrinix.aviation.internal.utils.on
import xyz.artrinix.aviation.ratelimit.DefaultRateLimitStrategy

val logger = KotlinLogging.logger {}
val config = Config.loadConfig()
lateinit var jda: JDA
lateinit var aviation: Aviation
val client = HttpClient(CIO) { install(DefaultRequest) }
val goveeApiUri = "https://developer-api.govee.com/v1"

suspend fun main(): Unit = runBlocking {
    logger.info { "Starting up the bot that controls the damn gay lights" }

    logger.info { "Govee API Key: ${config.govee.apiKey}" }
    logger.info { "Govee Device Model: ${config.govee.deviceModel}" }
    logger.info { "Govee Device MAC ID: ${config.govee.macId}" }

    jda = default(config.discord.token, enableCoroutines = true) {
        intents += listOf(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES)
    }

    aviation = AviationBuilder()
        .apply {
            ratelimitProvider = DefaultRateLimitStrategy()
            doTyping = true
            developers.add(config.discord.owner.toLong())
            registerDefaultParsers()
        }
        .build()
        .apply {
            slashCommands.register("gay.asoji.goveelightsdiscordbot.commands")
        }


    listenAviationEvents()

    jda.addEventListener(aviation)

    jda.listener<ReadyEvent> {
        aviation.syncCommands(jda)
        logger.info { "Ready to control the gay lights" }
    }

    jda.listener<MessageReceivedEvent> { event ->
        if (event.author.isBot || !event.isFromGuild) return@listener

    }
}

private fun listenAviationEvents() {
    aviation.on<AviationExceptionEvent> {
        logger.error {
            "Aviation threw an exception: ${this.error}"
        }
    }

    aviation.on<CommandFailedEvent> {
        logger.error { "[Command Execution] A command has failed. ${this.error}" }
    }
}