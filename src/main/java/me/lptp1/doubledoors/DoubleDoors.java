package me.lptp1.doubledoors;

import me.lptp1.doubledoors.events.InteractWithDoorEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class DoubleDoors extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("DoubleDoors is starting up...");
        getServer().getPluginManager().registerEvents(new InteractWithDoorEvent(), this);
        System.out.println("DoubleDoors has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("DoubleDoors has been disabled!");
    }
}
