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
data class Security(
    val vpn: Boolean,
    val proxy: Boolean,
    val tor: Boolean,
    val relay: Boolean
)

@Serializable
data class IPAddressInfo(
    val ip: String,
    val security: Security,
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
        val url = URL("https://vpnapi.io/api/8.8.8.8?key=")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = reader.readText()

            val vpnInfo = Json.decodeFromString<IPAddressInfo>(response).security

            if (vpnInfo.vpn || vpnInfo.proxy || vpnInfo.tor || vpnInfo.relay) return true
        }

        return false
    }
}
