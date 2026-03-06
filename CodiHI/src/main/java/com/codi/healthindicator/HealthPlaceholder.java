package com.codi.healthindicator;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.attribute.Attribute;
import org.jetbrains.annotations.NotNull;

public class HealthPlaceholder extends PlaceholderExpansion {

    private final CodiHI plugin;
    // Using MiniMessage format for color
    private static final String HEART = "<color:#DB1010>❤</color>";
    private static final String ABSORPTION_HEART = "<color:#E5CD00>❤</color>";

    public HealthPlaceholder(CodiHI plugin) {
        this.plugin = plugin;
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return "codihi";
    }

    @Override
    @NotNull
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    @NotNull
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        }

        // Get health values safely (Folia-compatible)
        int currentHealth = getHealthSafely(player);
        int maxHealth = getMaxHealthSafely(player);
        int absorption = getAbsorptionSafely(player);

        // %codihi_health% - Shows current health with heart
        if (params.equalsIgnoreCase("health")) {
            if (absorption > 0) {
                // When player has absorption, show total health + absorption with yellow heart after
                return (currentHealth + absorption) + " " + ABSORPTION_HEART;
            }
            // Normal health, show health with red heart after
            return currentHealth + " " + HEART;
        }

        // %codihi_health_current% - Just current health number
        if (params.equalsIgnoreCase("health_current")) {
            return String.valueOf(currentHealth);
        }

        // %codihi_health_max% - Just max health number
        if (params.equalsIgnoreCase("health_max")) {
            return String.valueOf(maxHealth);
        }

        // %codihi_absorption% - Just absorption amount
        if (params.equalsIgnoreCase("absorption")) {
            return String.valueOf(absorption);
        }

        // %codihi_health_hearts% - Visual hearts representation
        if (params.equalsIgnoreCase("health_hearts")) {
            int hearts = (int) Math.ceil(currentHealth / 2.0);
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < hearts; i++) {
                result.append(HEART);
            }
            return result.toString();
        }

        // %codihi_absorption_hearts% - Visual absorption hearts
        if (params.equalsIgnoreCase("absorption_hearts")) {
            if (absorption > 0) {
                int absHearts = (int) Math.ceil(absorption / 2.0);
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < absHearts; i++) {
                    result.append(ABSORPTION_HEART);
                }
                return result.toString();
            }
            return "";
        }

        // %codihi_health_bar% - Full health bar with both types
        if (params.equalsIgnoreCase("health_bar")) {
            int hearts = (int) Math.ceil(currentHealth / 2.0);
            StringBuilder bar = new StringBuilder();
            for (int i = 0; i < hearts; i++) {
                bar.append(HEART);
            }

            if (absorption > 0) {
                int absHearts = (int) Math.ceil(absorption / 2.0);
                bar.append(" ");
                for (int i = 0; i < absHearts; i++) {
                    bar.append(ABSORPTION_HEART);
                }
            }
            return bar.toString();
        }

        return null;
    }

    // Folia-safe method to get health
    private int getHealthSafely(Player player) {
        if (plugin.getServerType().isFolia()) {
            // On Folia, ensure we're on the right thread
            return (int) Math.round(player.getHealth());
        } else {
            // On Paper/Spigot, direct access is fine
            return (int) Math.round(player.getHealth());
        }
    }

    // Folia-safe method to get max health
    private int getMaxHealthSafely(Player player) {
        if (plugin.getServerType().isFolia()) {
            // On Folia, ensure we're on the right thread
            return (int) Math.round(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        } else {
            // On Paper/Spigot, direct access is fine
            return (int) Math.round(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        }
    }

    // Folia-safe method to get absorption
    private int getAbsorptionSafely(Player player) {
        if (plugin.getServerType().isFolia()) {
            // On Folia, ensure we're on the right thread
            return (int) Math.round(player.getAbsorptionAmount());
        } else {
            // On Paper/Spigot, direct access is fine
            return (int) Math.round(player.getAbsorptionAmount());
        }
    }
}