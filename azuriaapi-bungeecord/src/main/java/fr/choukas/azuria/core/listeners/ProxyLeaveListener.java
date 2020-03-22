package fr.choukas.azuria.core.listeners;

import fr.choukas.azuria.api.AzuriaAPI;
import fr.choukas.azuria.api.game.AzuriaPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class ProxyLeaveListener implements Listener {

    private AzuriaAPI api;

    public ProxyLeaveListener(AzuriaAPI api) {
        this.api = api;
    }

    @EventHandler
    public void onProxyLeave(PlayerDisconnectEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        AzuriaPlayer player = this.api.getPlayerList().get(uuid);

        try (Connection connection = this.api.getDatabaseAccess().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE players SET coins = ?, `group` = ?, last_connection_date = ? WHERE uuid = ?");

            statement.setInt(1, player.getCoins());
            statement.setString(2, player.getGroup().toString());
            statement.setDate(3, new Date(new java.util.Date().getTime()));

            statement.setString(4, player.getUniqueId().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.api.getPlayerList().remove(player.getUniqueId());
    }
}
