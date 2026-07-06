package xyz.codimc.healthindicator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
    private long startTime;

    @Override
    public void onEnable() {
        startTime = System.currentTimeMillis();

        saveDefaultConfig();

        serverType = detectServerType();

        if (serverType == ServerType.FOLIA) {
            getLogger().info("Folia Detected!");
            getLogger().info("Enabling Folia (Multi-thread)");
        } else {
            getLogger().info("Paper Detected!");
            getLogger().info("Enabling Paper (Single-thread)");
        }

        // Check if PlaceholderAPI is installed
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new HealthPlaceholder(this).register();
            getLogger().info("PlaceHolderAPI found - hooking it with placeholder expansions has been initiated...");
        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin requires PlaceholderAPI to work.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        long elapsed = System.currentTimeMillis() - startTime;
        getLogger().info("Successfully Enabled. (took " + elapsed + "ms)");

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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("codihi")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("codihi.reload")) {
                    sender.sendMessage("You don't have permission to do that.");
                    return true;
                }
                reloadConfig();
                sender.sendMessage("CodiHI has successfully reloaded.");
                return true;
            }
            sender.sendMessage("Usage: /codihi reload");
            return true;
        }
        return false;
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
