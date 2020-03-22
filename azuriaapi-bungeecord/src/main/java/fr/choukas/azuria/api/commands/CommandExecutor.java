package fr.choukas.azuria.api.commands;

import net.md_5.bungee.api.CommandSender;

public interface CommandExecutor {

    /**
     * Call when all check of the command have been made.
     * @param sender Command's sender
     * @param args Command's arguments
     */
    void execute(CommandSender sender, String[] args);
}
