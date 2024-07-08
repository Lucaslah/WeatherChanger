package me.lucaslah.weatherchanger.timerlogic;

import me.lucaslah.weatherchanger.WeatherChanger;
import me.lucaslah.weatherchanger.config.WcMode;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Timer {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Random random = new Random();

    public Timer() {
        scheduleWeatherChange();
        scheduleWeatherIntensityAdjustment();
    }

    private void scheduleWeatherChange() {
        int initialDelay = 0; // Start immediately
        int delay = 20 + random.nextInt(11); // 20-30 minutes

        scheduler.scheduleWithFixedDelay(this::decideWeather, initialDelay, delay, TimeUnit.MINUTES);
    }

    private void decideWeather() {
        if (!WeatherChanger.isTimerEnabled()) return;
        if (random.nextDouble() < 0.75) { // 75% chance to change the weather
            if (random.nextDouble() < 0.66) { // 66% chance for rain
                WeatherChanger.setMode(WcMode.RAIN);
            } else { // 33% chance for thunderstorm
                WeatherChanger.setMode(WcMode.THUNDER);
            }
        }
    }

    private void scheduleWeatherIntensityAdjustment() {
        int initialDelay = 1; // Start after 1 minute
        int delay = 1 + random.nextInt(2); // 1-2 minutes

        scheduler.scheduleWithFixedDelay(this::adjustWeatherIntensity, initialDelay, delay, TimeUnit.MINUTES);
    }

    private void adjustWeatherIntensity() {
        if (!WeatherChanger.isTimerEnabled()) return;
        WcMode currentMode = WeatherChanger.getMode();
        double chance = random.nextDouble();

        if (currentMode == WcMode.RAIN) {
            if (chance < 0.05) { // 5% chance to worsen from rain to thunderstorm
                WeatherChanger.setMode(WcMode.THUNDER);
            } else if (chance < 0.20) { // 15% chance to clear up from rain
                WeatherChanger.setMode(WcMode.CLEAR);
            }
        } else if (currentMode == WcMode.THUNDER) {
            if (chance < 0.05) { // 5% chance to worsen, but already at worst so no change
                // No change
            } else if (chance < 0.20) { // 15% chance to improve from thunderstorm to rain
                WeatherChanger.setMode(WcMode.RAIN);
            }
        } else if (currentMode == WcMode.CLEAR) {
            // No change, clear weather cannot improve or worsen in this context
        }
    }
}