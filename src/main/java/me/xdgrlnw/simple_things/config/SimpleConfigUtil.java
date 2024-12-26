package me.xdgrlnw.simple_things.config;


import me.xdgrlnw.simple_things.util.SimpleLogger;

public class SimpleConfigUtil {

    public static int getColorFromHex(String colorString) {
        try {
            return (colorString != null && colorString.startsWith("#") && colorString.length() == 7)
                    ? (int) Long.parseLong(colorString.substring(1), 16)
                    : 0xFFFFFF;
        } catch (NumberFormatException e) {
            SimpleLogger.logError("Invalid HEX color: " + colorString);
            return 0xFFFFFF;
        }
    }

    public static void updateLoggingStatus() {
        SimpleLogger.setLoggingEnabled(SimpleConfig.common.loggingEnabled);
    }
}
