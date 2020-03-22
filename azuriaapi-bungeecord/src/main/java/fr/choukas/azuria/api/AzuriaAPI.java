package fr.choukas.azuria.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import fr.choukas.azuria.api.commands.CommandManager;
import fr.choukas.azuria.api.data.sql.DatabaseAccess;
import fr.choukas.azuria.api.data.sql.DatabaseCredentials;
import fr.choukas.azuria.api.game.AzuriaPlayer;
import fr.choukas.azuria.core.commands.PartyCommand;
import fr.choukas.azuria.core.listeners.ProxyJoinListener;
import fr.choukas.azuria.core.listeners.ProxyLeaveListener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public final class AzuriaAPI extends Plugin {

    private static AzuriaAPI INSTANCE;

    private DatabaseAccess databaseAccess;
    private CommandManager commandManager;

    private Map<UUID, AzuriaPlayer> players;

    @Override
    public void onEnable() {
        this.log("========= [AzuriaProxy] =========");
        this.log("= AzuriaProxy is loading...     =");
        this.log("=================================");

        INSTANCE = this;

        URL url = this.getClass().getClassLoader().getResource("config.yml");

        if (url != null) {
            try {
                ObjectMapper om = new ObjectMapper(new YAMLFactory());
                DatabaseCredentials credentials = om.readValue(url, DatabaseCredentials.class);

                this.databaseAccess = new DatabaseAccess(credentials);
                this.databaseAccess.initPool();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.commandManager = new CommandManager(this);

        this.players = new HashMap<>();

        this.registerListeners();
        this.registerCommands();
    }

    @Override
    public void onDisable() {
        this.log("========= [AzuriaProxy] =========");
        this.log("= AzuriaProxy is disabling ...  =");
        this.log("=================================");

        this.databaseAccess.closePool();
    }

    public void log(Level level, String message) {
        this.getLogger().log(level, message);
    }

    public void log(String message) {
        this.getLogger().info(message);
    }

    private void registerListeners() {
        PluginManager pm = this.getProxy().getPluginManager();

        pm.registerListener(this, new ProxyJoinListener(this));
        pm.registerListener(this, new ProxyLeaveListener(this));
    }

    private void registerCommands() {
        CommandManager cm = this.commandManager;

        cm.registerCommand("party", new PartyCommand(this));
    }

    public static AzuriaAPI getInstance() {
        return INSTANCE;
    }

    public Map<UUID, AzuriaPlayer> getPlayerList() {
        return this.players;
    }

    public DatabaseAccess getDatabaseAccess() {
        return this.databaseAccess;
    }
}
