package me.lished.security

import org.bukkit.plugin.java.JavaPlugin

class Security : JavaPlugin() {
    override fun onEnable() {
        server.pluginManager.registerEvents(ClickListener(), this)
    }
}
