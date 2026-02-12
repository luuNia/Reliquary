package org.octave.chestobserver.util;

public class TimeFormatter {

    public static String format(long timestamp) {
        long seconds = (System.currentTimeMillis() - timestamp) / 1000;

        if (seconds < 10) return "just now";
        if (seconds < 60) return seconds + "s ago";

        long minutes = seconds / 60;
        if (minutes < 60) return minutes + "m ago";

        long hours = minutes / 60;
        if (hours < 24) return hours + "h ago";

        long days = hours / 24;
        return days + "d ago";
    }
}
