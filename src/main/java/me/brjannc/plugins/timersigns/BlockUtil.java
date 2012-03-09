/*
 * Copyright (C) 2012 brjannc <brjannc at gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.brjannc.plugins.timersigns;

import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.Attachable;
import org.bukkit.material.MaterialData;

public class BlockUtil {

    private static final Logger log = Logger.getLogger("Minecraft.BlockUtil");
    public static final BlockFace attachableFaces[] = {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST, BlockFace.UP};

    public static boolean isAttached(Block lhs, Block rhs) {
        MaterialData data = lhs.getState().getData();
        return (data instanceof Attachable && getAttachedBlock(lhs).equals(rhs));
    }

    public static Block getAttachedBlock(Block block) {
        MaterialData data = block.getState().getData();
        return block.getRelative(((Attachable) data).getAttachedFace());
    }

    public static void setLever(Block block, boolean powered) {
        BlockState state = block.getState();
        MaterialData data = state.getData();

        byte flags = data.getData();

        int newFlags;
        if (powered) {
            newFlags = flags | 0x8;
        } else {
            newFlags = flags & 0x7;
        }

        if (newFlags != flags) {
            state.getData().setData((byte) newFlags);
            state.update();
        }
    }

    public static void setAttachedLevers(Block block, boolean powered) {
        for (BlockFace face : BlockUtil.attachableFaces) {
            Block neighbor = block.getRelative(face);
            if (neighbor.getType() == Material.LEVER && BlockUtil.isAttached(neighbor, block)) {
                BlockUtil.setLever(neighbor, powered);
            }
        }
    }

    public static void applyPhysics(Block block) {
        // from https://github.com/Bukkit/Bukkit/pull/473

        byte oldData = block.getData();
        byte notData;
        if (oldData > 1) {
            notData = (byte) (oldData - 1);
        } else if (oldData < 15) {
            notData = (byte) (oldData + 1);
        } else {
            notData = 0;
        }
        block.setData(notData, true);
        block.setData(oldData, true);
    }
}
