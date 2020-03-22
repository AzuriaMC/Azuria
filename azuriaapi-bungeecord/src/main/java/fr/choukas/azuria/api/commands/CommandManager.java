package fr.choukas.azuria.api.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;
import fr.choukas.azuria.api.AzuriaAPI;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Map;
import java.util.SortedMap;

public class CommandManager {

    private AzuriaAPI api;

    private Map<String, Command> commands;

    public CommandManager(AzuriaAPI api) {
        this.api = api;

        this.loadCommands();
    }

    private void loadCommands() {
        URL url = this.getClass().getClassLoader().getResource("commands.json");

        if (url != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();

                Type hashType = new TypeToken<SortedMap<String, Command>>() {
                }.getType();

                this.commands = mapper.readValue(url, mapper.getTypeFactory().constructType(hashType));

                for (Map.Entry<String, Command> entry : commands.entrySet()) {
                    String name = entry.getKey();
                    Command command = entry.getValue();
                    command.setName(name);

                    this.commands.put(name, command);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void registerCommand(String name, CommandExecutor executor) {
        Command command = this.commands.get(name);
        command.setExecutor(executor);

        this.api.getProxy().getPluginManager().registerCommand(this.api, new BungeeCommand(command));
    }
}
