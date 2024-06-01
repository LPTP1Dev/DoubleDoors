package me.lptp1.doubledoors.events;

import me.lptp1.doubledoors.utils.DoorUtils;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.block.BlockFace;

import static org.bukkit.Bukkit.getServer;

public class InteractWithDoorEvent implements Listener {
    @EventHandler
    public void onInteractWithDoor(PlayerInteractEvent event) { // This method is called when a player interacts with a block

        // Check if the block the player interacted with is a door, but not a trapdoor
        // Also check if the event was actually a right click
        if (!DoorUtils.isDoorBlock(event.getClickedBlock()) || !event.getAction().isRightClick()) {
            return;
        }

        Block doorBlock = event.getClickedBlock();
        BlockState doorState = doorBlock.getState();
        Door door = (Door) doorState.getBlockData();

        boolean newDoorState = !door.isOpen();

        switch(door.getFacing()) {
            case NORTH: {
                switch(door.getHinge()) {
                    case LEFT:
                        Block eastBlock = doorBlock.getRelative(BlockFace.EAST, 1);
                        updateNextDoor(doorBlock, eastBlock, newDoorState);
                        break;
                    case RIGHT:
                        Block westBlock = doorBlock.getRelative(BlockFace.WEST, 1);
                        updateNextDoor(doorBlock, westBlock, newDoorState);
                        break;
                }
                break;
            }
            case SOUTH: {
                switch (door.getHinge()) {
                    case LEFT:
                        Block westBlock = doorBlock.getRelative(BlockFace.WEST, 1);
                        updateNextDoor(doorBlock, westBlock, newDoorState);
                        break;
                    case RIGHT:
                        Block eastBlock = doorBlock.getRelative(BlockFace.EAST, 1);
                        updateNextDoor(doorBlock, eastBlock, newDoorState);
                        break;
                }
                break;
            }
            case EAST: {
                switch (door.getHinge()) {
                    case LEFT:
                        Block southBlock = doorBlock.getRelative(BlockFace.SOUTH, 1);
                        updateNextDoor(doorBlock, southBlock, newDoorState);
                        break;
                    case RIGHT:
                        Block northBlock = doorBlock.getRelative(BlockFace.NORTH, 1);
                        updateNextDoor(doorBlock, northBlock, newDoorState);
                        break;
                }
                break;
            }
            case WEST: {
                switch (door.getHinge()) {
                    case LEFT:
                        Block northBlock = doorBlock.getRelative(BlockFace.NORTH, 1);
                        updateNextDoor(doorBlock, northBlock, newDoorState);
                        break;
                    case RIGHT:
                        Block southBlock = doorBlock.getRelative(BlockFace.SOUTH, 1);
                        updateNextDoor(doorBlock, southBlock, newDoorState);
                        break;
                }
                break;
            }
        }
    }

    private void updateNextDoor(Block doorBlock, Block block, boolean open) {
        Block blockBelowDoor = doorBlock.getWorld().getBlockAt(doorBlock.getLocation().add(0, -1, 0));
        Block blockBelowNext = block.getWorld().getBlockAt(block.getLocation().add(0, -1, 0));

        if (DoorUtils.isDoorBlock(blockBelowDoor) && !DoorUtils.isDoorBlock(blockBelowNext)) {
            return;
        }
        if (!DoorUtils.isDoorBlock(blockBelowDoor) && DoorUtils.isDoorBlock(blockBelowNext)) {
            return;
        }

        BlockState doorState = doorBlock.getState();
        Door originalDoor = (Door) doorState.getBlockData();
        if (DoorUtils.isDoorBlock(block)) {
            BlockState nextDoorState = block.getState();
            Door otherDoor = (Door) nextDoorState.getBlockData();
            if (!originalDoor.getHinge().equals(otherDoor.getHinge())) {
                DoorUtils.setDoorOpen(block, open);
            }
        }
    }
}
