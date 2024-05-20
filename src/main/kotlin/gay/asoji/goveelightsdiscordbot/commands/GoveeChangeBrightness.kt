package gay.asoji.goveelightsdiscordbot.commands

import gay.asoji.goveelightsdiscordbot.client
import gay.asoji.goveelightsdiscordbot.config
import gay.asoji.goveelightsdiscordbot.goveeApiUri
import gay.asoji.goveelightsdiscordbot.utils.hexToRgb
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.command.slash.annotations.SlashSubCommand
import xyz.artrinix.aviation.entities.Scaffold
import java.awt.Color

@SlashCommand("changebrightness", "Make it dark or make a flashbang")
class GoveeChangeBrightness : Scaffold {
    @SlashSubCommand("Change lights using 0-100")
    suspend fun brightness(ctx: SlashContext, @Description("Brightness from 0-100") brightness: Int) {
        val brightnessInput = brightness.coerceIn(0..100)

        val goveeLightsBrightnessChange: HttpResponse = client.request("$goveeApiUri/devices/control") {
            method = HttpMethod.Put
            ContentType.parse("application/json")
            setBody("{\"model\":\"${config.govee.deviceModel}\",\"cmd\":{\"name\":\"brightness\",\"value\":$brightnessInput},\"device\":\"${config.govee.macId}\"}")
            headers {
                append(HttpHeaders.Accept, "application/json")
                append(HttpHeaders.ContentType, "application/json")
                append("Govee-API-Key", config.govee.apiKey)
            }
        }

        ctx.sendEmbed {
            // should find a way to scale this in whiteness
            setColor(Color(brightnessInput))
            setTitle("Gay Lights Brightness Changed!")
            setDescription("${ctx.member!!.asMention} has changed the gay lights to $brightnessInput%!")
            if (brightnessInput > 100) {
                setFooter("Are you trying to kill the lights?")
            }
        }
    }
}