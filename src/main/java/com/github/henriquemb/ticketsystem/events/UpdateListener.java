package com.github.henriquemb.ticketsystem.events;

import com.github.henriquemb.ticketsystem.Model;
import com.github.henriquemb.ticketsystem.TicketSystem;
import com.github.henriquemb.ticketsystem.util.UpdateChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateListener implements Listener {
    private final Model m = TicketSystem.getModel();

    @EventHandler
    public void onVerifyResponse(PlayerJoinEvent e) {
        if (!e.getPlayer().isOp()) return;

        new UpdateChecker(TicketSystem.getMain(), 99324).getVersion(version -> {
            if (!TicketSystem.getMain().getDescription().getVersion().equals(version)) {
                m.sendMessage(e.getPlayer(), "&7[&bTicketSystem&7] &fNova versão disponível [&b[Clique para baixar]](https://www.spigotmc.org/resources/ticketsystem.99324/ hover=&fBaixar)");
            }
        });
    }
}
