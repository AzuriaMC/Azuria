package fr.choukas.azuria.core.commands;

import fr.choukas.azuria.api.AzuriaAPI;
import fr.choukas.azuria.api.commands.CommandExecutor;
import net.md_5.bungee.api.CommandSender;

public class PartyCommand implements CommandExecutor {

    private final AzuriaAPI api;

    public PartyCommand(AzuriaAPI api) {
        this.api = api;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        System.out.println("Party command");
    }
}
