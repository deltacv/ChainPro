package io.deltacv.chainpro

import io.deltacv.chainpro.ChainPro.ChainedPlayer
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import kotlin.math.floor

class ChainTask(val plugin: ChainPro, val center: Location, val chainedPlayers: List<ChainedPlayer>) : BukkitRunnable() {
    override fun run() {
        for(p in chainedPlayers) {
            val dist = center.toVector().distance(p.player.location.toVector())

            val almostChainLength = ChainPro.CHAIN_LENGTH - 0.5

            if(dist >= ChainPro.CHAIN_LENGTH) {
                val yVelocity = p.player.velocity.y
                val rejectionVector = center.toVector().subtract(p.player.location.toVector()).multiply(0.05)

                p.player.velocity = Vector(rejectionVector.x, yVelocity, rejectionVector.z)
            } else if(dist >= almostChainLength) {
                val interp = ((dist - almostChainLength) / (ChainPro.CHAIN_LENGTH - almostChainLength))
                p.player.sendMessage(interp.toString())

                val yVelocity = p.player.velocity.y
                val rejectionVector = center.toVector().subtract(p.player.location.toVector()).multiply(0.04 * interp)

                p.player.velocity = Vector(rejectionVector.x, yVelocity, rejectionVector.z)
            }
        }
    }
}