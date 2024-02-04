package me.lished.security.listeners

import io.papermc.paper.event.player.AsyncChatEvent
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@Serializable
data class Result(val result: String)

class ChatListener : Listener {
    @EventHandler
    fun onChat(e: AsyncChatEvent) {
        val url = URL("https://www.purgomalum.com/service/json?text=${e.originalMessage()}")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = reader.readText()

            val result = Json.decodeFromString<Result>(response).result

            e.message(Component.text(result))
        }
    }
}
