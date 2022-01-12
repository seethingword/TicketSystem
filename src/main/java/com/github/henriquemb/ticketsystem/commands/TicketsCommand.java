package com.github.henriquemb.ticketsystem.commands;

import com.github.henriquemb.ticketsystem.Model;
import com.github.henriquemb.ticketsystem.TicketSystem;
import com.github.henriquemb.ticketsystem.database.controller.TicketController;
import com.github.henriquemb.ticketsystem.database.model.TicketModel;
import com.github.henriquemb.ticketsystem.exceptions.PaginationException;
import com.github.henriquemb.ticketsystem.util.Pagination;
import com.github.henriquemb.ticketsystem.util.PrepareMessages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class TicketsCommand implements CommandExecutor {
    private final Model m = TicketSystem.getModel();
    private final TicketController controller = new TicketController();
    private final FileConfiguration messages = TicketSystem.getMessages();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messages.getString("prefix.report") + messages.getString("warnings.only_players"));
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("ticketsystem.ticket.staff")) {
            m.sendMessage(p, messages.getString("permission.no_permission"), "ticket");
            return true;
        }

        int pag = 1;
        if (args.length > 0) {
            try {
                 pag = Integer.parseInt(args[0]);
            }
            catch (Exception e) {
                m.sendMessage(p, messages.getString("pagination.invalid"));
            }
        }

        List<TicketModel> tickets = controller.fetchNotAnswered();

        if (tickets == null || tickets.isEmpty()) {
            m.sendMessage(p, messages.getString("ticket.all_verified"), "ticket");
            return true;
        }


        Pagination<TicketModel> pagination = new Pagination<>(tickets, 10);

        try {
            StringBuilder str = new StringBuilder();
            str.append(messages.getString("pagination.header").replace("<self>", String.valueOf(pag)).replace("<total>", String.valueOf(pagination.length())));

            for (TicketModel t : pagination.getPag(pag)) {
                str.append("\n");
                str.append(new PrepareMessages().ticketMessage(messages.getString("ticket.list"), t));
            }

            String footer = String.format("%s", messages.getString("pagination.footer"))
                    .replace("<button-back>", pag > 1 && pagination.length() > 1
                            ? String.format("[%s](/tickets %d hover=%s)",
                            messages.getString("pagination.buttons.back.label"),
                            pag - 1,
                            messages.getString("pagination.buttons.back.hover")) : "")
                    .replace("<button-next>", pagination.length() > pag
                            ? String.format("[%s](/tickets %d hover=%s)",
                            messages.getString("pagination.buttons.next.label"),
                            pag + 1,
                            messages.getString("pagination.buttons.next.hover")) : "");

            str.append(pagination.length() == 1 ? "\n" : footer);

            m.sendMessage(p, str.toString());
        }
        catch (PaginationException e) {
            m.sendMessage(p, messages.getString("pagination.error.not_found"), "report");
        }

        return true;
    }
}
