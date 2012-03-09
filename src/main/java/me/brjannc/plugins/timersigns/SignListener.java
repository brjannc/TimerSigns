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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener implements Listener {

    private TimerSigns plugin;

    public SignListener(TimerSigns plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onSignChange(SignChangeEvent event) {
        String[] lines = event.getLines();
        if (!lines[0].toLowerCase().equals("[timer]")) {
            return;
        }

        long onTicks = Math.max(10, Long.parseLong(lines[1]));
        long offTicks = Math.max(10, Long.parseLong(lines[2]));

        plugin.createTimerSign(event.getBlock(), onTicks, offTicks);
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (plugin.isTimerSign(block)) {
            plugin.destroyTimerSign(block);
        }
    }
}
