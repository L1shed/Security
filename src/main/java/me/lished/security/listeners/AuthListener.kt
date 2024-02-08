package me.lished.security.listeners

import DiscordWebhook
import me.lished.security.managers.AuthManager
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class AuthListener : Listener {
    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val player = e.player

        if (!AuthManager.hasJoinedBefore(player)) {
            if (!AuthManager.hasVPN(player)) AuthManager.storeIP(player)
        } else {
            if (!AuthManager.hasSameIP(player)) {
                e.joinMessage(Component.text(""))
                player.kick(Component.text("This account is already used"))
                Bukkit.broadcast(Component.text("$player was kgicked because he joined with the wrong IP address (${player.address.address.hostAddress})"), "security.kickmessages")
            }
        }

        val webhook = DiscordWebhook("not4ggu")
        webhook.username = ("aaaafffffg")
        webhook.execute()
    }
}
