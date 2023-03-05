package com.github.tacowasa059.multistatecellularautomata2d.commands;

import com.github.tacowasa059.multistatecellularautomata2d.MultiStateCellularAutomata2d;
import com.github.tacowasa059.multistatecellularautomata2d.UpdateState;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class initialize implements CommandExecutor {
    private final MultiStateCellularAutomata2d plugin;
    public initialize(MultiStateCellularAutomata2d plugin){
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
            Location location1 = plugin.getConfig().getLocation("position1");
            Location location2 = plugin.getConfig().getLocation("position2");
            //設定されてないときにNUllを返す
            if (location1 != null && location2 != null) {
                Location location_min = new Location(player.getWorld(), Math.min(location1.getX(), location2.getX()), Math.min(location1.getY(), location2.getY()), Math.min(location1.getZ(), location2.getZ()));
                Location location_max = new Location(player.getWorld(), Math.max(location1.getX(), location2.getX()), Math.max(location1.getY(), location2.getY()), Math.max(location1.getZ(), location2.getZ()));
                int x = Math.abs((int) (location1.getX() - location2.getX()));
                int y = Math.abs((int) (location1.getZ() - location2.getZ()));
                plugin.updateState=new UpdateState(x,y,location_min);
                player.sendMessage(ChatColor.GREEN+"Initialized.");
            }
            else{
                player.sendMessage(ChatColor.RED+"位置を設定してください。");
            }
        }

        return true;
    }
}
