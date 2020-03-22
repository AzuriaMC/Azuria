package fr.choukas.azuria.core.listeners;

import fr.choukas.azuria.api.game.AzuriaPlayer;
import fr.choukas.azuria.api.game.Group;
import fr.choukas.azuria.api.AzuriaAPI;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;


public class ProxyJoinListener implements Listener {

    private AzuriaAPI api;

    public ProxyJoinListener(AzuriaAPI api) {
        this.api = api;
    }

    @EventHandler
    public void onProxyJoin(PostLoginEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        try (Connection connection = this.api.getDatabaseAccess().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM players WHERE uuid = ?");

            statement.setString(1, uuid.toString());

            ResultSet resultSet = statement.executeQuery();

            AzuriaPlayer player;

            if (resultSet.next()) {
                // Account was found
                int coins = resultSet.getInt("coins");
                Date registrationDate = resultSet.getDate("registration_date");
                Date lastConnectionDate = resultSet.getDate("last_connection_date");
                Group group = Group.valueOf(resultSet.getString("group"));

                player = new AzuriaPlayer(uuid, coins, registrationDate, lastConnectionDate, group);
            } else {
                // Account was not found, creation of a new account
                player = this.createAccount(uuid);
            }

            this.api.getPlayerList().put(player.getUniqueId(), player);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private AzuriaPlayer createAccount(UUID uuid) throws SQLException {
        final AzuriaPlayer DEFAULT_PLAYER = new AzuriaPlayer(uuid, 100, new Date(), new Date(), Group.PLAYER);

        try (Connection connection = this.api.getDatabaseAccess().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO players(uuid, coins, group, registration_date, last_connection_date) VALUES (?, ?, ?, ?, ?)");

            statement.setString(1, DEFAULT_PLAYER.getUniqueId().toString());
            statement.setInt(2, DEFAULT_PLAYER.getCoins());
            statement.setString(3, DEFAULT_PLAYER.getGroup().toString());
            statement.setDate(4, new java.sql.Date(DEFAULT_PLAYER.getRegistrationDate().getTime()));
            statement.setDate(5, new java.sql.Date(DEFAULT_PLAYER.getLastConnectionDate().getTime()));

            statement.executeUpdate();
        }

        return DEFAULT_PLAYER;
    }
}
