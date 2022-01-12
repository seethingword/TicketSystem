package com.github.henriquemb.ticketsystem;

import com.github.henriquemb.ticketsystem.commands.CommandRegister;
import com.github.henriquemb.ticketsystem.database.factory.CreateDatabase;
import com.github.henriquemb.ticketsystem.events.ListenerRegister;
import com.github.henriquemb.ticketsystem.util.CustomConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public final class TicketSystem extends JavaPlugin {
    @Getter @Setter
    private static TicketSystem main;
    @Getter @Setter
    private static Model model;
    @Getter @Setter
    private static FileConfiguration messages;

    @SneakyThrows
    @Override
    public void onEnable() {
        Locale.setDefault(Locale.US);

        setMain(this);

        setMessages(CustomConfig.createCustomConfig("messages"));

        setModel(new Model());

        getMain().getConfig().options().copyDefaults(true);
        getMain().saveConfig();

        new CreateDatabase();

        new CommandRegister(this);
        new ListenerRegister(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
