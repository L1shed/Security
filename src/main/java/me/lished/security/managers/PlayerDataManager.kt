package me.lished.security.managers

import java.io.File
import org.bukkit.entity.Player
import org.yaml.snakeyaml.Yaml


object PlayerDataManager {

    private val dataFile = File("plugins/Security/ips.yml")

    init {
        // Create data file if not exists
        if (!dataFile.exists()) {
            dataFile.createNewFile()
        }
    }

    fun hasJoinedBefore(player: Player): Boolean {
        val yaml = Yaml()
        val data = yaml.load(dataFile.reader()) as? Map<*, *>

        return data?.containsKey(player.uniqueId.toString()) == true
    }

    fun storeIP(player: Player) {
        val yaml = Yaml()
        val data = yaml.load(dataFile.reader()) as? MutableMap<*, *> ?: mutableMapOf()

        data[player.name] = player.address?.address?.hostAddress

        dataFile.writeText(yaml.dumpAsMap(data))
    }

    fun checkIP(player: Player): Boolean {
        val yaml = Yaml()
        val data = yaml.load(dataFile.reader()) as? Map<*, *>

        return data?.get(player.uniqueId.toString()) == player.address?.address?.hostAddress
    }
}
