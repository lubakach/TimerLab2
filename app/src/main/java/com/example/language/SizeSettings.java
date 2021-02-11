package com.example.language;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import static com.example.language.constants.switchFontSizeKey;

public class SizeSettings {
    public static void restoreSettings(SharedPreferences preferences, Context context) {
        Configuration configuration = new Configuration();
        configuration.fontScale = Float.parseFloat(preferences.getString(switchFontSizeKey, "0.75"));
        context.getResources().updateConfiguration(configuration, null);
    }

    public static void restoreFontSize(SharedPreferences preferences, Context context) {
        Configuration configuration = new Configuration();
        configuration.fontScale = Float.parseFloat(preferences.getString(switchFontSizeKey, "1.2"));
        context.getResources().updateConfiguration(configuration, null);
    }
}
