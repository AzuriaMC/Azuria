package fr.choukas.azuria.api.game;

import java.util.Date;
import java.util.UUID;

public class AzuriaPlayer implements Cloneable {

    private UUID uuid;
    private int coins;
    private Date registrationDate;
    private Date lastConnectionDate;

    private Group group;

    public AzuriaPlayer(UUID uuid, int coins, Date registrationDate, Date lastConnectionDate, Group group) {
        this.uuid = uuid;
        this.coins = coins;
        this.registrationDate = registrationDate;
        this.lastConnectionDate = lastConnectionDate;

        this.group = group;
    }

    public boolean hasPermission(String permission) {
        return this.group.getPermissions().contains(permission);
    }

    public UUID getUniqueId() {
        return this.uuid;
    }

    public int getCoins() {
        return this.coins;
    }

    public Date getRegistrationDate() {
        return this.registrationDate;
    }

    public Date getLastConnectionDate() {
        return this.lastConnectionDate;
    }

    public Group getGroup() {
        return this.group;
    }

    public AzuriaPlayer clone() {
        try {
            return (AzuriaPlayer) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
