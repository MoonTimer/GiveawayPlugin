package me.moontimer.giveaway.manager;

import me.moontimer.giveaway.Giveaway;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GiveawayManager {

    private boolean runningGiveaway = false;
    private final List<Player> joinedPlayers = new ArrayList<>();
    public GiveawayManager() {

    }

    public void createGiveaway() {
        if (!isRunningGiveaway()) {
            TextComponent message = new TextComponent("§7[§d§lGewinnspiel§7] §aEin neues Gewinnspiel §7Drücke §f§lHier§7 um teilzunehmen");
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/giveaway join"));
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.spigot().sendMessage(message);
            }
            setRunningGiveaway(true);

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Giveaway.getInstance(), new Runnable(){
                public void run(){
                    getWinner();
                }
            }, 300*20);
        }
    }

    public void addPlayer(Player player) {
        if (isRunningGiveaway()) {
            joinedPlayers.add(player);
            player.sendMessage(Giveaway.getPrefix() + "§7Du hast §aerfolgreich §7im Gewinnspiel teilgenommen");
        } else {
            player.sendMessage(Giveaway.getPrefix() + "§cZurzeit leuft kein Gewinnspiel");
        }
    }

    public void getWinner() {
        if (joinedPlayers.isEmpty()) {
            Bukkit.broadcastMessage(Giveaway.getPrefix() + "§cEs hat keiner im Gewinnspiel teilgenommen, es gibt keinen Gewinner");
            return;
        }

        //Get Winner
        Random random = new Random();
        int winnerNumber = random.nextInt(joinedPlayers.size());
        Player player = joinedPlayers.get(winnerNumber);
        player.sendMessage(Giveaway.getPrefix() + "§aHerzlichen Glückwunsch, Du hast gewonnen");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "loot give " + player.getName() + " LOOT-ID 1");

        //Clear and Stop Giveaway
        joinedPlayers.clear();
        setRunningGiveaway(false);
    }



    public boolean isRunningGiveaway() {
        return runningGiveaway;
    }

    public void setRunningGiveaway(boolean runningGiveaway) {
        this.runningGiveaway = runningGiveaway;
    }

    public List<Player> getJoinedPlayers() {
        return joinedPlayers;
    }
}
