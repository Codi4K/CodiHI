package xyz.codimc.healthindicator;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.attribute.Attribute;
import org.jetbrains.annotations.NotNull;

public class HealthPlaceholder extends PlaceholderExpansion {

    private final CodiHI plugin;

    private static final String HEART     = "<color:#DB1010><shadow:#370404:1>❤</shadow></color>";
    private static final String ABS_HEART = "<color:#E5CD00><shadow:#393300:1>❤</shadow></color>";

    public HealthPlaceholder(CodiHI plugin) {
        this.plugin = plugin;
    }

    @Override @NotNull public String getIdentifier() { return "codihi"; }
    @Override @NotNull public String getAuthor()     { return plugin.getDescription().getAuthors().toString(); }
    @Override @NotNull public String getVersion()    { return plugin.getDescription().getVersion(); }
    @Override public boolean persist()               { return true; }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) return "";

        double rawHealth     = getHealthSafely(player);
        double rawMax        = getMaxHealthSafely(player);
        double rawAbsorption = getAbsorptionSafely(player);

        // %codihi_health%
        // Whole numbers: "20 ❤"  |  Fractions: "16.23 ❤"
        // With absorption: "20 ❤ | 4 ❤"
        if (params.equalsIgnoreCase("health")) {
            String healthStr = formatHealth(rawHealth) + " " + HEART;
            if (rawAbsorption > 0) {
                return healthStr + " <color:#AAAAAA>|</color> " + formatHealth(rawAbsorption) + " " + ABS_HEART;
            }
            return healthStr;
        }

        // %codihi_health_current%
        // Whole part of health + absorption (floor, so 23.50 -> "23" not "24")
        if (params.equalsIgnoreCase("health_current")) {
            return String.valueOf((int) (rawHealth + rawAbsorption));
        }

        // %codihi_health_max%
        if (params.equalsIgnoreCase("health_max")) {
            return formatHealth(rawMax);
        }

        // %codihi_absorption%
        if (params.equalsIgnoreCase("absorption")) {
            return formatHealth(rawAbsorption);
        }

        // %codihi_old_health%
        // Combined health + absorption as a single number: "24 ❤"
        // Uses yellow heart when absorption is active, red heart otherwise
        if (params.equalsIgnoreCase("old_health")) {
            double total = rawHealth + rawAbsorption;
            String heart = rawAbsorption > 0 ? ABS_HEART : HEART;
            return formatHealth(total) + " " + heart;
        }

        // %codihi_old_health_decimal%
        // Decimal portion only of health + absorption: total 24.58 -> ".58"
        // Whole numbers return an empty string
        if (params.equalsIgnoreCase("old_health_decimal")) {
            double total = rawHealth + rawAbsorption;
            return formatDecimalOnly(total);
        }

        // %codihi_heart_icon%
        // Dynamic heart icon with no number, color reflects current state
        // Yellow when absorption is active, red otherwise
        if (params.equalsIgnoreCase("heart_icon")) {
            return rawAbsorption > 0 ? ABS_HEART : HEART;
        }

        // %codihi_normal_health%
        // Static plain red heart icon, independent of absorption state
        if (params.equalsIgnoreCase("normal_health")) {
            return HEART;
        }

        return null;
    }

    /**
     * Returns only the decimal portion of a value, including the leading dot.
     * Whole numbers (or infinite values) return an empty string.
     * Example: 24.58 -> ".58"  |  24.0 -> ""
     */
    private String formatDecimalOnly(double value) {
        if (value == Math.floor(value) && !Double.isInfinite(value)) {
            return "";
        }
        String formatted = String.format("%.2f", value);
        int dotIndex = formatted.indexOf('.');
        return dotIndex >= 0 ? formatted.substring(dotIndex) : "";
    }

    /**
     * Whole numbers → no decimal ("20", "16", "4")
     * Fractions     → 2 decimals ("16.23", "9.50")
     */
    private String formatHealth(double value) {
        if (value == Math.floor(value) && !Double.isInfinite(value)) {
            return String.valueOf((int) value);
        }
        return String.format("%.2f", value);
    }

    private double getHealthSafely(Player player) {
        return player.getHealth();
    }

    private double getMaxHealthSafely(Player player) {
        var attr = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        return attr != null ? attr.getValue() : 20.0;
    }

    private double getAbsorptionSafely(Player player) {
        return player.getAbsorptionAmount();
    }
}
