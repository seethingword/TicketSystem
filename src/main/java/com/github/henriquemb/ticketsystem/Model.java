package com.github.henriquemb.ticketsystem;

import de.themoep.minedown.MineDown;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
public class Model {
    private final FileConfiguration messages = TicketSystem.getMessages();

    private final String ticketPrefix = messages.getString("prefix.ticket");
    private final String reportPrefix = messages.getString("prefix.report");
    private final String suggestionPrefix = messages.getString("prefix.suggestion");

    private final Map<Player, Timestamp> supportCommandDelay = new HashMap<>();

    public void sendMessage(Player player, String message) {
        player.spigot().sendMessage(MineDown.parse(message));
    }

    public void sendMessage(Player p, String message, String prefix) {
        sendMessage(p, Objects.requireNonNull(messages.getString(String.format("prefix.%s", prefix))).concat(message));
    }

    public void broadcastMessage(String message) {
        Bukkit.getOnlinePlayers().forEach(player ->
                player.spigot().sendMessage(MineDown.parse(message)));
    }
}
