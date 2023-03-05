package com.github.tacowasa059.multistatecellularautomata2d.commands;

import com.github.tacowasa059.multistatecellularautomata2d.MultiStateCellularAutomata2d;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class changeThreshold implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player=(Player) sender;
            if(args.length!=1){
                player.sendMessage(ChatColor.RED+"引数が間違っています.");
                player.sendMessage(ChatColor.RED+"/<command> <value>");
                return true;
            }
            int value=Integer.parseInt(args[0]);
            MultiStateCellularAutomata2d.plugin.getConfig().set("threshold",value);
            MultiStateCellularAutomata2d.plugin.saveConfig();
            player.sendMessage(ChatColor.GREEN+"閾値を "+ChatColor.AQUA+MultiStateCellularAutomata2d.plugin.getConfig().get("threshold")+ChatColor.GREEN+" に変更しました。");
        }
        return true;
    }
}
