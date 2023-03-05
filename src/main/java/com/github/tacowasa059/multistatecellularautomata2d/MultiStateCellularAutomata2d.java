package com.github.tacowasa059.multistatecellularautomata2d;

import com.github.tacowasa059.multistatecellularautomata2d.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class MultiStateCellularAutomata2d extends JavaPlugin {
    public UpdateState updateState;
    public static MultiStateCellularAutomata2d plugin;
    public static Boolean run=false;
    @Override
    public void onEnable() {
        this.plugin=this;
        // Plugin startup logic
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getCommand("position1").setExecutor(new position1(this));
        getCommand("position2").setExecutor(new position2(this));
        getCommand("randomize").setExecutor(new randomize(this));
        getCommand("initialize").setExecutor(new initialize(this));
        getCommand("TimeEvolution").setExecutor(new TimeEvolution(this));
        getCommand("setThreshold").setExecutor(new changeThreshold());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
