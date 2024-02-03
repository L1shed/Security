package me.lished.security.managers

import java.io.File
import org.bukkit.entity.Player
import org.bukkit.configuration.file.YamlConfiguration


object AuthManager {

    private val dataFile = YamlConfiguration.loadConfiguration(File("plugins/Security/ips.yml"))

    fun hasJoinedBefore(player: Player): Boolean {
        return dataFile.getString(player.name) != null
    }

    fun storeIP(player: Player) {
        dataFile.set(player.name, player.address.hostString)

        dataFile.save(File("plugins/Security/ips.yml"))
    }

    fun hasSameIP(player: Player): Boolean {
        return dataFile.getString(player.name) == player.address.hostString
    }
}
