package me.lptp1.doubledoors;

import me.lptp1.doubledoors.events.InteractWithDoorEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class DoubleDoors extends JavaPlugin {
    
    private Logger log;
    
    @Override
    public void onEnable() {
        log = getLogger();
        // Plugin startup logic
        log.info("DoubleDoors is starting up...");
        getServer().getPluginManager().registerEvents(new InteractWithDoorEvent(), this);
        log.info("DoubleDoors has been enabled!");
    }
    
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        log.info("DoubleDoors has been disabled!");
    }
}
