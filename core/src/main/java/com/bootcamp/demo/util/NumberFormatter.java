package com.bootcamp.demo.util;

import com.bootcamp.demo.data.save.stats.Stat;

public class NumberFormatter {
    public static String statToString(Stat stat){
        return stat.toString();
    }
    public static String formatToShortForm(long number) {
        if (number >= 1_000_000_000_000L) {
            return String.format("%.1ft", number / 1_000_000_000_000.0);
        } else if (number >= 1_000_000_000) {
            return String.format("%.1fb", number / 1_000_000_000.0);
        } else if (number >= 1_000_000) {
            return String.format("%.1fm", number / 1_000_000.0);
        } else if (number >= 1_000) {
            return String.format("%.1fk", number / 1_000.0);
        } else {
            return String.valueOf(number);
        }
    }

}
