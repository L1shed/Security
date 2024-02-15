package me.lished.security.listeners

import io.papermc.paper.event.player.AsyncChatEvent
import me.lished.security.managers.ChatManager
import me.lished.security.managers.Suspicious
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class ChatListener : Listener {

    private val suspiciousPlayers = Suspicious().suspiciousPlayers

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

        if(suspiciousPlayers.contains(player)) {
            e.isCancelled = true
            if (!player.hasPlayedBefore()) {
                player.kick(Component.text("§cPlease move before chatting"))
                return
            }
            player.sendMessage("§cPlease move before chatting")
        }
        e.message(Component.text(ChatManager.getCensored(e.message().toString())))
    }
}
