package me.lptp1.doubledoors.utils;

import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;

public class DoorUtils {
    public static boolean isDoorBlock(Block block) {
        if (block == null) return false;
        return block.getType().toString().contains("DOOR") && !block.getType().toString().contains("TRAP");
    }

    public static void setDoorOpen(Block block, boolean open) {
        Door door = (Door) block.getBlockData();
        door.setOpen(open);
        block.setBlockData(door);
    }
}
