package com.codi.healthindicator;

public class ServerTypeDetector {

    private final ServerType serverType;

    public ServerTypeDetector() {
        this.serverType = detectServerType();
    }

    private ServerType detectServerType() {
        try {
            // Try to detect Folia
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return ServerType.FOLIA;
        } catch (ClassNotFoundException e) {
            // Not Folia, check for Paper
            try {
                Class.forName("com.destroystokyo.paper.PaperConfig");
                return ServerType.PAPER;
            } catch (ClassNotFoundException ex) {
                // Not Paper either, must be Spigot or CraftBukkit
                return ServerType.SPIGOT;
            }
        }
    }

    public boolean isFolia() {
        return serverType == ServerType.FOLIA;
    }

    public boolean isPaper() {
        return serverType == ServerType.PAPER;
    }

    public boolean isSpigot() {
        return serverType == ServerType.SPIGOT;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public enum ServerType {
        FOLIA("Folia"),
        PAPER("Paper"),
        SPIGOT("Spigot");

        private final String name;

        ServerType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}