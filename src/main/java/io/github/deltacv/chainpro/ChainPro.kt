package io.github.deltacv.chainpro

import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask

class ChainPro : JavaPlugin() {

    companion object {
        const val MAX_LINKS_PER_PLAYER = 20
        const val CHAIN_LENGTH = 4
    }

    var chainCentersPerWorld = mutableMapOf<World, Location>()

    private val chainTasks = mutableMapOf<World, BukkitTask>()

    override fun onEnable() {
        getCommand("chainpro")!!.setExecutor(ChainProCommand(this))
    }

    override fun onDisable() {
    }

    private fun makeLinks(player: Player): List<ChainProLink> {
        return mutableListOf()
    }

    fun startChain(world: World) {
        if(chainCentersPerWorld[world] == null) return
        val chainedPlayers = world.players.map {
            it.gameMode = GameMode.ADVENTURE
            ChainedPlayer(it, makeLinks(it))
        }

        val task = ChainTask(this, chainCentersPerWorld[world]!!, chainedPlayers)
        chainTasks[world] = task.runTaskTimer(this, 0, 0)
    }

    fun stopChain(world: World) {
        chainTasks[world]?.cancel()
        chainTasks.remove(world)
    }

    data class ChainedPlayer(val player: Player, val links: List<ChainProLink>)
}
