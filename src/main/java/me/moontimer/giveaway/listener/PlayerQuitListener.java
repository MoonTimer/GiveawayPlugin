package me.moontimer.giveaway.listener;

import me.moontimer.giveaway.Giveaway;
import me.moontimer.giveaway.manager.GiveawayManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (Giveaway.getInstance().getGiveawayManager().isRunningGiveaway()) {
            Giveaway.getInstance().getGiveawayManager().getJoinedPlayers().remove(event.getPlayer());
        }
    }
}
