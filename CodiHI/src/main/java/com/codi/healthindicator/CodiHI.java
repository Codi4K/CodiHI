package xyz.codimc.healthindicator;

import org.bukkit.plugin.java.JavaPlugin;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;

public class CodiHI extends JavaPlugin {

    private static final int BSTATS_PLUGIN_ID = 29452;

    public enum ServerType {
        FOLIA("Folia"),
        PAPER("Paper");

        private final String name;

        ServerType(String name) { this.name = name; }

        @Override
        public String toString() { return name; }
    }

    private ServerType serverType;

    @Override
    public void onEnable() {
        // Detect server type
        serverType = detectServerType();
        getLogger().info("Detected server type: " + serverType);

        if (serverType == ServerType.FOLIA) {
            getLogger().info("Folia features enabled!");
        } else {
            getLogger().info("Running in standard Paper mode.");
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
        metrics.addCustomChart(new SimplePie("server_type", () -> serverType.toString()));
    }

    @Override
    public void onDisable() {
        getLogger().info("CodiHI has been disabled!");
    }

    public ServerType getServerType() {
        return serverType;
    }

    private ServerType detectServerType() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return ServerType.FOLIA;
        } catch (ClassNotFoundException e) {
            return ServerType.PAPER;
        }
    }
}
