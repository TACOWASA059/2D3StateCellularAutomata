package com.github.tacowasa059.multistatecellularautomata2d.commands;

import com.github.tacowasa059.multistatecellularautomata2d.MultiStateCellularAutomata2d;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class randomize implements CommandExecutor {
    private final MultiStateCellularAutomata2d plugin;
    public randomize(MultiStateCellularAutomata2d plugin){
        this.plugin=plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(MultiStateCellularAutomata2d.run){
                player.sendMessage(ChatColor.RED+"描画中のため処理できません。");
                return true;
            }
            if(plugin.updateState!=null){
                plugin.updateState.randomize();
                player.sendMessage(ChatColor.GREEN+"Randomized.");
            }
            else{
                player.sendMessage(ChatColor.RED+"先に/initializeをしてください。");
            }
        }

        return true;
    }
}
