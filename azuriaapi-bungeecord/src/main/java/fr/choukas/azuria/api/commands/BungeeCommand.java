package fr.choukas.azuria.api.commands;

import fr.choukas.azuria.api.AzuriaAPI;
import fr.choukas.azuria.api.game.AzuriaPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Arrays;
import java.util.UUID;

public class BungeeCommand extends net.md_5.bungee.api.plugin.Command {

    private Command command;

    // TODO : Change format
    private static final String USAGE_HELP_FORMAT = "/%command% %usage% : %description%";

    public BungeeCommand(Command command) {
        super(command.getName(), "", command.getAliases());

        this.command = command;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            UUID senderUUID = ((ProxiedPlayer) sender).getUniqueId();
            AzuriaPlayer player = AzuriaAPI.getInstance().getPlayerList().get(senderUUID);

            if (player.hasPermission(this.command.getPermission())) {
                if (args.length != 0) {
                    if (command.getUsages().containsKey(args[0])) {
                        // Usage exists
                        Command.CommandUsage usage = command.getUsages().get(args[0]);

                        if (player.hasPermission(usage.getPermission())) {
                            String[] usageArgs = usage.getUsage().split(" ");

                            final long requiredArgs = Arrays.stream(usageArgs).filter((s) -> s.startsWith("<")).count();
                            final long optionalArgs = usageArgs.length - requiredArgs - 1; // Optional args = args_length - required_args - 1 (sub command name)

                            if (args.length >= requiredArgs && args.length <= optionalArgs) {
                                // Everything is ok -> run command
                                ((CommandExecutor) this).execute((ProxiedPlayer) player, args);
                            } else {
                                // Advanced Help
                                String help = this.getHelpFormat(usage);
                                sender.sendMessage(new TextComponent(help));
                            }
                        } else {
                            sender.sendMessage(new TextComponent(usage.getNotPermMessage()));
                        }
                    } else {
                        // Full help
                        sender.sendMessage(new TextComponent(this.command.getName() + ":" + this.command.getDescription()));

                        for (Command.CommandUsage usage : this.command.getUsages().values()) {
                            String help = this.getHelpFormat(usage);
                            sender.sendMessage(new TextComponent(help));
                        }
                    }
                } else {
                    this.command.getExecutor().execute(sender, args);
                }
            } else {
                sender.sendMessage(new TextComponent(this.command.getNotPermMessage()));
            }
        } else {
            sender.sendMessage(new TextComponent("Only players can execute a command"));
        }
    }

    private String getHelpFormat(Command.CommandUsage usage) {
        return USAGE_HELP_FORMAT
                .replace("%command%", this.getName())
                .replace("%usage%", usage.getUsage())
                .replace("%description%", usage.getDescription());
    }
}
