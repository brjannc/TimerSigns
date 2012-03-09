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

import org.bukkit.block.Block;

public class TimerSign {

    private Block signBlock;
    private long onTicks;
    private long offTicks;
    private SignTask task;

    public TimerSign(TimerSigns plugin, Block signBlock, long onTicks, long offTicks) {
        this.signBlock = signBlock;
        this.onTicks = onTicks;
        this.offTicks = offTicks;
        this.task = new SignTask(plugin, BlockUtil.getAttachedBlock(signBlock), onTicks, offTicks);
    }

    public void cancelTask() {
        task.cancel();
    }

    public Block getSignBlock() {
        return signBlock;
    }

    public long getOnTicks() {
        return onTicks;
    }

    public long getOffTicks() {
        return offTicks;
    }

    @Override
    public String toString() {
        return signBlock.getWorld().getName() + "," + signBlock.getX() + "," + signBlock.getY() + "," + signBlock.getZ() + "," + onTicks + "," + offTicks;
    }
}
