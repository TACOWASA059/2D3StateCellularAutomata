package com.github.tacowasa059.multistatecellularautomata2d.commands;

import com.github.tacowasa059.multistatecellularautomata2d.MultiStateCellularAutomata2d;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class TimeEvolution implements CommandExecutor {
    private final MultiStateCellularAutomata2d plugin;
    private BukkitTask task;//繰り返し処理タスク
    private int step;



    public TimeEvolution(MultiStateCellularAutomata2d plugin) {
        this.plugin = plugin;
        this.step=0;
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
                if (args.length != 1) {
                    player.sendMessage(ChatColor.RED + "引数が不足しています。繰り返し回数を指定してください。");
                } else {
                    int tmax = Integer.parseInt(args[0]);
                    step=0;
                    plugin.run=true;
                    task=plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
                        plugin.updateState.update();
                        if(step==tmax){
                            step=0;
                            task.cancel();
                            plugin.run=false;
                            player.sendMessage(ChatColor.GREEN + "Success!");
                        }
                        step++;
                    }, 0L, 2L);


                }
            }
            else{
                player.sendMessage(ChatColor.RED+"まず初期化してください。/initialize");
            }

        }

        return true;
    }
}
