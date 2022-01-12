package com.github.henriquemb.ticketsystem.util;

import com.github.henriquemb.ticketsystem.TicketSystem;
import com.github.henriquemb.ticketsystem.database.model.ReportModel;
import com.github.henriquemb.ticketsystem.database.model.SuggestionModel;
import com.github.henriquemb.ticketsystem.database.model.TicketModel;
import com.github.henriquemb.ticketsystem.enums.ReportStatusEnum;
import com.github.henriquemb.ticketsystem.enums.TicketRatingEnum;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;

public class PrepareMessages {
    private final FileConfiguration messages = TicketSystem.getMessages();

    public String reportMessage(String msg, ReportModel report) {
        ReportStatusEnum status = null;
        for (ReportStatusEnum r : ReportStatusEnum.values()) {
            if (r.getId() == report.getStatus()) {
                status = r;
                System.out.println(r.getName());
                break;
            }
        }

        String reason = report.getReason();
        if (reason == null) reason = messages.getString("report.no_reason");
        else if (reason.split(" ").length > 3) reason = String.join(" ", Arrays.copyOfRange(reason.split(" "), 0, 3)) + "..";

        return msg.replace("<id>", report.getId().toString())
                .replace("<player>", report.getPlayer())
                .replace("<reported>", report.getReported())
                .replace("<reason>", report.getReason() != null ? report.getReason() : reason)
                .replace("<reason-shot>", reason)
                .replace("<evidence>", verifyNull(report.getEvidence(), messages.getString("report.no_reason")))
                .replace("<status>", status.format())
                .replace("<button-teleport>",
                        String.format("[%s](/report teleport %d hover=%s)",
                                messages.getString("report.buttons.teleport.label"),
                                report.getId(),
                                messages.getString("report.buttons.teleport.hover")))
                .replace("<button-view>",
                        String.format("[%s](/report view %d hover=%s)",
                                messages.getString("report.buttons.view.label"),
                                report.getId(),
                                messages.getString("report.buttons.view.hover")))
                .replace("<button-list>",
                        String.format("[%s](/reports hover=%s)",
                                messages.getString("report.buttons.list.label"),
                                messages.getString("report.buttons.list.hover")));
    }

    public String suggestionMessage(String msg, SuggestionModel suggestion) {
        String suggestionShot = suggestion.getSuggestion();
        if (suggestionShot.split(" ").length > 3) suggestionShot = String.join(" ", Arrays.copyOfRange(suggestionShot.split(" "), 0, 3)) + "..";

        return msg.replace("<id>", suggestion.getId().toString())
                .replace("<player>", suggestion.getPlayer())
                .replace("<suggestion>", suggestion.getSuggestion())
                .replace("<suggestion-shot>", suggestionShot)
                .replace("<respondedBy>", verifyNull(suggestion.getRespondedBy(), ""))
                .replace("<response>", verifyNull(suggestion.getResponse(), ""))
                .replace("<staffer>", verifyNull(suggestion.getRespondedBy(), ""))
                .replace("<button-response>",
                        String.format("[%s](suggest_command=/suggestion response %d hover=%s)",
                                messages.getString("suggestion.buttons.response.label"),
                                suggestion.getId(),
                                messages.getString("suggestion.buttons.response.hover")))
                .replace("<button-view>",
                        String.format("[%s](/suggestion view %d hover=%s)",
                                messages.getString("suggestion.buttons.view.label"),
                                suggestion.getId(),
                                messages.getString("suggestion.buttons.view.hover")))
                .replace("<button-list>",
                        String.format("[%s](/suggestions hover=%s)",
                                messages.getString("suggestion.buttons.list.label"),
                                messages.getString("suggestion.buttons.list.hover")));
    }

    public String ticketMessage(String msg, TicketModel ticket) {
        TicketRatingEnum rating = null;
        for (TicketRatingEnum tre : TicketRatingEnum.values()) {
            if (tre.getRate() == ticket.getRating()) {
                rating = tre;
                break;
            }
        }

        String request = ticket.getRequest();
        if (request.split(" ").length > 3) request = String.join(" ", Arrays.copyOfRange(request.split(" "), 0, 3)) + "..";

        return msg.replace("<id>", ticket.getId().toString())
                .replace("<player>", ticket.getPlayer())
                .replace("<ticket>", ticket.getRequest())
                .replace("<ticket-shot>", request)
                .replace("<response>", verifyNull(ticket.getResponse(), ""))
                .replace("<respondedBy>", verifyNull(ticket.getRespondedBy(), ""))
                .replace("<rating>", rating != null ? rating.format() : "null")
                .replace("<button-teleport>",
                        String.format("[%s](/ticket teleport %d hover=%s)",
                                messages.getString("ticket.buttons.teleport.label"),
                                ticket.getId(),
                                messages.getString("ticket.buttons.teleport.hover")))
                .replace("<button-view>",
                        String.format("[%s](/ticket view %d hover=%s)",
                                messages.getString("ticket.buttons.view.label"),
                                ticket.getId(),
                                messages.getString("ticket.buttons.view.hover")))
                .replace("<button-list>",
                        String.format("[%s](/tickets hover=%s)",
                                messages.getString("ticket.buttons.list.label"),
                                messages.getString("ticket.buttons.list.hover")))
                .replace("<button-response>",
                        String.format("[%s](suggest_command=/ticket response %d  hover=%s)",
                                messages.getString("ticket.buttons.response.label"),
                                ticket.getId(),
                                messages.getString("ticket.buttons.response.hover")));
    }

    private static String verifyNull(String msg, String replace) {
        if (msg != null) return msg;
        return replace;
    }
}
