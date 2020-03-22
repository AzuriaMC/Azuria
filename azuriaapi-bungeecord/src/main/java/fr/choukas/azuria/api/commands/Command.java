package fr.choukas.azuria.api.commands;

import java.util.SortedMap;

public class Command {

    private String name;
    private String description = "Pas de description";
    private String[] aliases = {};
    private String permission = "";
    private String notPermMessage = "Vous n'avez pas la permission d'exécuter cette commande.";
    private SortedMap<String, CommandUsage> usages;

    private CommandExecutor executor;

    public Command(String name, String description, String[] aliases, String permission, String notPermMessage, SortedMap<String, CommandUsage> usages) {
        this.name = name;
        this.description = description;
        this.aliases = aliases;
        this.permission = permission;
        this.notPermMessage = notPermMessage;
        this.usages = usages;
    }

    public Command() {}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getPermission() {
        return permission;
    }

    public String getNotPermMessage() {
        return notPermMessage;
    }

    public SortedMap<String, CommandUsage> getUsages() {
        return usages;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    public static class CommandUsage {

        private String name;
        private String usage;
        private String description;
        private String permission;
        private String notPermMessage;

        public CommandUsage(String name, String usage, String description, String permission, String notPermMessage) {
            this.name = name;
            this.usage = usage;
            this.description = description;
            this.permission = permission;
            this.notPermMessage = notPermMessage;
        }

        public String getUsage() {
            return usage;
        }

        public String getDescription() {
            return description;
        }

        public String getPermission() {
            return permission;
        }

        public String getNotPermMessage() {
            return notPermMessage;
        }
    }
}
