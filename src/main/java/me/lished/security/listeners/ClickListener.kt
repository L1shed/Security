package me.lished.security.listeners

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import java.util.concurrent.ConcurrentHashMap

class ClickListener : Listener {

    private val clickTimes: MutableMap<String, MutableList<Long>> = ConcurrentHashMap()

    @EventHandler
    fun onClick(event: PlayerInteractEvent) {
        val playerName = event.player.name
        val currentTime = System.currentTimeMillis()

        if (!clickTimes.containsKey(playerName)) {
            clickTimes[playerName] = mutableListOf(currentTime)
            return
        }

        val clickList = clickTimes[playerName]!!
        clickList.add(currentTime)

        if (clickList.size >= 10) {
            if (isConsistent(clickList)) {
                Bukkit.broadcast(Component.text("$playerName failed AutoClicker"), "security.alerts")
            }
            clickList.clear()
        }
    }

    private fun isConsistent(delayList: List<Long>): Boolean {
        val differences = mutableListOf<Long>()
        for (i in 1 until delayList.size) {
            val difference = delayList[i] - delayList[i - 1]
            differences.add(difference)
        }
        differences.sort()

        return differences[differences.size -1] - differences[0] < differences[differences.size/2]/5
    }
}
