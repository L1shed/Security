package me.lished.security

import me.lished.security.listeners.AuthListener
import org.bukkit.plugin.java.JavaPlugin

class Security : JavaPlugin() {
    override fun onEnable() {
        server.pluginManager.registerEvents(AuthListener(), this)
    }
}
