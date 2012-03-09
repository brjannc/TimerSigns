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

public class SignTask implements Runnable {

    private TimerSigns plugin;
    private Block controlledBlock;
    private long onTicks;
    private long offTicks;
    private boolean powered;
    private int taskId;

    public SignTask(TimerSigns plugin, Block controlledBlock, long onTicks, long offTicks) {
        this.plugin = plugin;
        this.controlledBlock = controlledBlock;
        this.onTicks = onTicks;
        this.offTicks = offTicks;
        this.powered = false;

        run();
    }

    public void run() {
        powered = !powered;
        BlockUtil.setAttachedLevers(controlledBlock, powered);
        BlockUtil.applyPhysics(controlledBlock);

        this.taskId = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, powered ? onTicks : offTicks);
    }

    public void cancel() {
        plugin.getServer().getScheduler().cancelTask(taskId);
    }
}
