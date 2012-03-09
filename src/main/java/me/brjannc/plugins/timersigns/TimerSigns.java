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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TimerSigns extends JavaPlugin implements Listener {

    private static final Logger log = Logger.getLogger("Minecraft.TimerSigns");
    private Map<Location, TimerSign> timerSigns;

    @Override
    public void onEnable() {
        timerSigns = new HashMap<Location, TimerSign>();

        startup();
        getServer().getPluginManager().registerEvents(new SignListener(this), this);

        log.info(this + " is now enabled");
    }

    @Override
    public void onDisable() {
        shutdown();

        log.info(this + " is now disabled");
    }

    private void startup() {
        Configuration config = getConfig();
        if (config.getKeys(true).isEmpty()) {
            config.options().copyDefaults(true);
        }
    }

    private void shutdown() {
        saveConfig();
    }

    public void createTimerSign(Block signBlock, long onTicks, long offTicks) {
        timerSigns.put(signBlock.getLocation(), new TimerSign(this, signBlock, onTicks, offTicks));
    }

    public boolean isTimerSign(Block block) {
        return timerSigns.containsKey(block.getLocation());
    }

    public void destroyTimerSign(Block block) {
        TimerSign timerSign = getTimerSign(block);
        timerSign.cancelTask();

        timerSigns.remove(block.getLocation());
    }

    public TimerSign getTimerSign(Block block) {
        return timerSigns.get(block.getLocation());
    }
}
