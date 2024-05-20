package gay.asoji.goveelightsdiscordbot.commands

import gay.asoji.goveelightsdiscordbot.client
import gay.asoji.goveelightsdiscordbot.config
import gay.asoji.goveelightsdiscordbot.goveeApiUri
import gay.asoji.goveelightsdiscordbot.logger
import gay.asoji.goveelightsdiscordbot.utils.hexToRgb
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import okio.ByteString.Companion.decodeHex
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.command.slash.annotations.SlashSubCommand
import xyz.artrinix.aviation.command.slash.annotations.SubCommandHolder
import xyz.artrinix.aviation.entities.Scaffold
import java.awt.Color

@SlashCommand("changelightcolor", "Change the fucking dipshit colors of the fucking gay lights that is on the moron's wall")
class GoveeChangeLightColors : Scaffold {
    @SlashSubCommand("Change lights using RGB values")
    suspend fun rgb(ctx: SlashContext, @Description("[R]GB") r: Int, @Description("R[G]B") g: Int, @Description("RG[B]") b: Int) {
        val rInput = r.coerceIn(0..255)
        val gInput = g.coerceIn(0..255)
        val bInput = b.coerceIn(0..255)

        val goveeLightsColorChangeRGB: HttpResponse = client.request("$goveeApiUri/devices/control") {
            method = HttpMethod.Put
            ContentType.parse("application/json")
            setBody("{\"model\":\"${config.govee.deviceModel}\",\"cmd\":{\"value\":{\"name\":\"Color\",\"r\":$rInput,\"g\":$gInput,\"b\":$bInput},\"name\":\"color\"},\"device\":\"${config.govee.macId}\"}")
            headers {
                append(HttpHeaders.Accept, "application/json")
                append(HttpHeaders.ContentType, "application/json")
                append("Govee-API-Key", config.govee.apiKey)
            }
        }

        ctx.sendEmbed {
            setColor(Color(rInput, gInput, bInput))
            setTitle("Gay Lights Color Changed!")
            setDescription("${ctx.member!!.asMention} has changed the gay lights to $rInput, $gInput, $bInput!")
            if (r > 255 || g > 255 || b > 255) {
                setFooter("One of your RGB inputs has a number over 255! Remember that you cannot use numbers higher than 255 in RGB, so we just round it back down to 255.")
            }
        }
    }

    @SlashSubCommand("Change lights using HEX values")
    suspend fun hex(ctx: SlashContext, @Description("HEX code") hex: String) {
        // currently for some godforsaken reason, this returns back 255, 255, 255 [white] 100% of the time and ive been trying to figure out how to fucking fix that and i have given up please send help
        // OKAY NOW FOR SOME FUCKING REASON WITHOUT THE `#` IT JUST WHITES, IOHRGPORE:LKGBJ EHRPUIB
        val (r, g, b) = hexToRgb(hex) ?: Triple(255, 255, 255)

        val goveeLightsColorChangeHex: HttpResponse = client.request("$goveeApiUri/devices/control") {
            method = HttpMethod.Put
            ContentType.parse("application/json")
            setBody("{\"model\":\"${config.govee.deviceModel}\",\"cmd\":{\"value\":{\"name\":\"Color\",\"r\":$r,\"g\":$g,\"b\":$b},\"name\":\"color\"},\"device\":\"${config.govee.macId}\"}")
            headers {
                append(HttpHeaders.Accept, "application/json")
                append(HttpHeaders.ContentType, "application/json")
                append("Govee-API-Key", config.govee.apiKey)
            }
        }

        ctx.sendEmbed {
            setColor(Color(r, g, b))
            setTitle("Gay Lights Color Changed!")
            setDescription("${ctx.member!!.asMention} has changed the gay lights to $hex!")
        }
    }
}