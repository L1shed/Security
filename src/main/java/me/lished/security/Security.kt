package me.lished.security

import me.lished.security.listeners.AuthListener
import me.lished.security.listeners.ChatListener
import me.lished.security.listeners.ClickListener
import org.bukkit.plugin.java.JavaPlugin

class Security : JavaPlugin() {
    override fun onEnable() {
        server.pluginManager.registerEvents(AuthListener(), this)
//        server.pluginManager.registerEvents(ClickListener(), this)
//        server.pluginManager.registerEvents(ChatListener(), this)
    }
}
