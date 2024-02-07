package me.lished.security.managers

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bukkit.entity.Player
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@Serializable
data class Result(val result: String)

data class Suspicious(val suspiciousPlayers: MutableList<Player> = mutableListOf())

object ChatManager {
    fun getCensored(sentence: String): String {
        val url = URL("https://www.purgomalum.com/service/json?text=$sentence")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = reader.readText()

            return Json.decodeFromString<Result>(response).result
        }
        return sentence
    }
}