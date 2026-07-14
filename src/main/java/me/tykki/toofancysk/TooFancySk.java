package me.tykki.toofancysk;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class TooFancySk extends JavaPlugin {

    public static TooFancySk instance;
    SkriptAddon addon;

    @Override
    public void onEnable() {
        instance = this;
        addon = Skript.registerAddon(this);
        try {
            // Load Types first so the 'hologram' type is registered before effects
            // try to compile patterns containing %hologram%
            Class.forName("me.tykki.toofancysk.elements.Types");
            addon.loadClasses("me.tykki.toofancysk.elements", "effects", "expressions", "sections");
        } catch (IOException | ClassNotFoundException e) {
            getLogger().severe("Failed to load classes: " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("TooFancySk has been enabled!");

        // check for mineskin api key in FancyNpcs config
        File fancyNpcsConfig = new File(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("FancyNpcs")).getDataFolder(), "config.yml");
        if (!fancyNpcsConfig.exists()) {
            getLogger().warning("FancyNpcs config.yml not found! Please make sure FancyNpcs is installed correctly. (Or the server has started up for the first time)");
        } else {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(fancyNpcsConfig);
            if (!config.isSet("mineskin_api_key") || Objects.requireNonNull(config.getString("mineskin_api_key")).isEmpty()) {
                getLogger().warning("It is suggested to set your server's MineSkin API Key in the FancyNpcs config.yml to avoid rate limits.");
            } else {
                getLogger().info("FancyNpcs config.yml loaded successfully. MineSkin API Key is set.");
            }
        }
    }

    public TooFancySk getInstance() {
        return instance;
    }

    public SkriptAddon getAddon() {
        return addon;
    }

}
