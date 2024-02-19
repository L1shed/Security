package me.lished.security.managers

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import org.bukkit.entity.Player
import org.bukkit.configuration.file.YamlConfiguration
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@Serializable
data class IpAPIResponse(
    val country: String,
    val countryCode: String, // can be parsed
    val mobile: Boolean,
    val proxy: Boolean,
    val hosting: Boolean
)

@Serializable
data class VPNBlockerResponse(
    val hostip: Boolean
)

object AuthManager {

    private val dataFile = YamlConfiguration.loadConfiguration(File("plugins/Security/ips.yml"))

    fun hasJoinedBefore(player: Player): Boolean {
        return dataFile.getString(player.name) != null
    }

    fun storeIP(player: Player) {
        dataFile.set(player.name, player.address.hostString)

        dataFile.save(File("plugins/Security/ips.yml"))
    }

    fun hasSameIP(player: Player): Boolean {
        return dataFile.getString(player.name) == player.address.hostString
    }

    fun hasVPN(player: Player): Boolean {
        var url = URL("http://ip-api.com/json/${player.address.hostString}?fields=country,countryCode,proxy")
        var connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = reader.readText()

            val vpnInfo = Json.decodeFromString<IpAPIResponse>(response)

            if (vpnInfo.mobile || vpnInfo.proxy || vpnInfo.hosting) return true
        }

        url = URL("http://api.vpnblocker.net/v2/json/${player.address.hostString}")
        connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = reader.readText()

            val vpnInfo = Json.decodeFromString<VPNBlockerResponse>(response)

            if (vpnInfo.hostip) return true
        }

        return false
    }
}
