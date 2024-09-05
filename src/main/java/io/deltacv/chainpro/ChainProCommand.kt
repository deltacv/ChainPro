package io.deltacv.chainpro

import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

internal class ChainProCommand(val plugin: ChainPro) : CommandExecutor {
    override fun onCommand(
        commandSender: CommandSender,
        command: org.bukkit.command.Command,
        s: String,
        args: Array<String>
    ): Boolean {
        if(commandSender !is Player) {
            commandSender.sendMessage("Este comando solo lo pueden usar jugadores.")
            return true
        }
        if(!commandSender.isOp) {
            commandSender.sendMessage("No tienes permisos.")
            return true
        }

        if(args.size != 1) return false

        return when(args[0]) {
            "center" -> {
                plugin.chainCentersPerWorld[commandSender.world] = commandSender.location
                commandSender.sendMessage("Se definió el centro con éxito")
                true
            }
            "start" -> {
                plugin.startChain(commandSender.world)
                true
            }
            "stop" -> {
                plugin.stopChain(commandSender.world)
                true
            }
            else -> false
        }
    }
}