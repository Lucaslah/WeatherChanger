package me.lucaslah.weatherchanger.config;

public class WcConfig {
    private WcMode mode;
    private String version;

    public WcConfig() {
    }

    public WcConfig(WcMode mode, String version) {
        this.mode = mode;
        this.version = version;
    }

    public WcMode getMode() {
        return mode;
    }

    public void setMode(WcMode mode) {
        this.mode = mode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
