package me.lished.security.listeners

import io.papermc.paper.event.player.AsyncChatEvent
import me.lished.security.managers.ChatManager
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class ChatListener : Listener {

    @EventHandler
    fun onChat(e: AsyncChatEvent) {
        val player = e.player

        if(player.hasPermission("security.bypass.filter")) {return}

        if(System.currentTimeMillis() - player.lastLogin < 1000) {
            e.isCancelled = true
            player.kick(Component.text("Please wait before chatting"))
            Bukkit.broadcast(Component.text("${e.player}"), "security.alerts")
            return
        }

        e.message(Component.text(ChatManager.getCensored(e.message().toString())))
    }
}
