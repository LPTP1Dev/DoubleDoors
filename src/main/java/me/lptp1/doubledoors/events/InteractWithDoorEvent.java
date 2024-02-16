package me.lptp1.doubledoors.events;

import me.lptp1.doubledoors.utils.DoorUtils;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.block.BlockFace;

public class InteractWithDoorEvent implements Listener {
    @EventHandler
    public void onInteractWithDoor(PlayerInteractEvent event) { // This method is called when a player interacts with a block

        // Check if the block the player interacted with is a door, but not a trapdoor
        if (!DoorUtils.isDoorBlock(event.getClickedBlock())) {
            return;
        }

        Block doorBlock = event.getClickedBlock();
        BlockState doorState = doorBlock.getState();
        Door door = (Door) doorState.getBlockData();

        boolean newDoorState = !door.isOpen();

        if (door.getFacing() == BlockFace.NORTH || door.getFacing() == BlockFace.SOUTH) {
            Block eastBlock = doorBlock.getRelative(BlockFace.EAST, 1);
            Block westBlock = doorBlock.getRelative(BlockFace.WEST, 1);
            updateNextDoors(door, eastBlock, westBlock, newDoorState);
        }

        if (door.getFacing() == BlockFace.EAST || door.getFacing() == BlockFace.WEST) {
            Block northBlock = doorBlock.getRelative(BlockFace.NORTH, 1);
            Block southBlock = doorBlock.getRelative(BlockFace.SOUTH, 1);
            updateNextDoors(door, northBlock, southBlock, newDoorState);
        }
    }

    private void updateNextDoors(Door originalDoor, Block block1, Block block2, boolean open) {
        if (DoorUtils.isDoorBlock(block1)) {
            BlockState doorState1 = block1.getState();
            Door otherDoor = (Door) doorState1.getBlockData();
            if (!originalDoor.getHinge().equals(otherDoor.getHinge())) {
                DoorUtils.setDoorOpen(block1, open);
            }
        }
        if (DoorUtils.isDoorBlock(block2)) {
            BlockState doorState2 = block2.getState();
            Door otherDoor = (Door) doorState2.getBlockData();
            if (!originalDoor.getHinge().equals(otherDoor.getHinge())) {
                DoorUtils.setDoorOpen(block2, open);
            }
        }
    }
}
