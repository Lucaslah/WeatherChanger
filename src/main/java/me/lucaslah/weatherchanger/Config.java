package me.lucaslah.weatherchanger;

public class Config {
    private String mode;

    public Config() {}

    public Config(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
