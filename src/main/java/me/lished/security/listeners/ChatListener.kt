package me.lished.security.listeners

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ChatListener : Listener {
    @EventHandler
    fun onChat(e: AsyncChatEvent) {
        val url = URL("https://www.purgomalum.com/service/json?text=${e.originalMessage()}")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = reader.readText()
            e.message(Component.text(response.substring(11, response.length - 1)))
        }
    }
}
