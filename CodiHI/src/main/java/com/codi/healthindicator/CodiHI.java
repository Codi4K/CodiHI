package com.codi.healthindicator;

import org.bukkit.plugin.java.JavaPlugin;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;

public class CodiHI extends JavaPlugin {

    private static final int BSTATS_PLUGIN_ID = 29452;

    private ServerTypeDetector serverType;

    @Override
    public void onEnable() {
        // Detect server type
        serverType = new ServerTypeDetector();
        getLogger().info("Detected server type: " + serverType.getServerType());

        if (serverType.isFolia()) {
            getLogger().info("Folia features enabled!");
        } else {
            getLogger().info("Running in standard Paper/Spigot mode.");
        }

        // Check if PlaceholderAPI is installed
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new HealthPlaceholder(this).register();
            getLogger().info("CodiHI has been enabled! PlaceholderAPI hooked successfully.");
        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin requires PlaceholderAPI to work.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Initialize bStats metrics
        Metrics metrics = new Metrics(this, BSTATS_PLUGIN_ID);

        // Custom chart: track which server type is being used
        metrics.addCustomChart(new SimplePie("server_type", () -> serverType.getServerType().toString()));
    }

    @Override
    public void onDisable() {
        getLogger().info("CodiHI has been disabled!");
    }

    public ServerTypeDetector getServerType() {
        return serverType;
    }
}