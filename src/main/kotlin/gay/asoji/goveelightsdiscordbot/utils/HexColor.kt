package gay.asoji.goveelightsdiscordbot.utils

fun hexToRgb(hex: String): Triple<Int, Int, Int>? {
    // Check if the hex string is valid
    if (!hex.matches(Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$"))) {
        println("Invalid hex color code")
        return null
    }

    // Remove '#' symbol if present
    val cleanHex = if (hex[0] == '#') hex.substring(1) else hex

    // Expand short hex format to full format if necessary
    val fullHex = if (cleanHex.length == 3) {
        cleanHex.map { it.toString() + it }.joinToString("")
    } else {
        cleanHex
    }

    // Convert hex to RGB
    val red = fullHex.substring(0, 2).toInt(16)
    val green = fullHex.substring(2, 4).toInt(16)
    val blue = fullHex.substring(4, 6).toInt(16)

    return Triple(red, green, blue)
}