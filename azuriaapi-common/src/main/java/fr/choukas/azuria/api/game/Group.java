package fr.choukas.azuria.api.game;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public enum Group {

    PLAYER("Joueur", null),
    VIP("VIP", "VIP"),
    VIP_PLUS("VIP+", "VIP+"),
    FRIEND("Ami", "Ami"),
    BUILDER("Builder", "Builder"),
    DEVELOPER("Développeur", "Dev"),
    MODERATOR("Modérateur", "Modo"),
    ADMINISTRATOR("Administrateur", "Admin");

    private String name;
    private String prefix;

    private List<String> permissions;

    Group(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;

        this.permissions = new ArrayList<>();

        URL url = this.getClass().getClassLoader().getResource("permissions.yml");

        if (url != null) {
            try {
                URLConnection connection = url.openConnection();

                InputStream inputStream = connection.getInputStream();

                Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(inputStream);

                this.permissions.addAll(config.getStringList(this.toString().toLowerCase()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getPermissions() {
        return this.permissions;
    }
}
