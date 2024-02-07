package me.lished.security.listeners

import io.papermc.paper.event.player.AsyncChatEvent
import me.lished.security.managers.ChatManager
import me.lished.security.managers.Suspicious
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class ChatListener : Listener {

    private val suspiciousPlayers = Suspicious().suspiciousPlayers

    @EventHandler
    fun onChat(e: AsyncChatEvent) {
        if(e.player.hasPermission("security.bypass.filter")) {return}
        if(suspiciousPlayers.contains(e.player)) {
            e.isCancelled = true
            if (!e.player.hasPlayedBefore()) {
                e.player.kick(Component.text("§cPlease move before chatting"))
                return
            }
            e.player.sendMessage("§cPlease move before chatting")
        }
        e.message(Component.text(ChatManager.getCensored(e.message().toString())))
    }
}
