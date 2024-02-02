package me.lished.security.listeners

import me.lished.security.managers.PlayerDataManager
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class AuthListener : Listener {
    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val player = e.player

        if (!player.hasPlayedBefore()) {
            PlayerDataManager.storeIP(player)
        } else {
            if (!PlayerDataManager.checkIP(player)) {
                e.joinMessage = null
                player.kick(Component.text("This account is already used"))
                Bukkit.broadcast(Component.text("$player was kicked because he joined with the wrong IP address (${player.address.address.hostAddress})"), "security.kickmessages")
            }
        }
    }
}
