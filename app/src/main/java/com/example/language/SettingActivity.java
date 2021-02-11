package com.example.language;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Context;
import static com.example.language.constants.switchFontSizeKey;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.example.language.SizeSettings;

import java.util.Arrays;
import java.util.Locale;



public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_setting);
        SizeSettings.restoreSettings(PreferenceManager.getDefaultSharedPreferences(this), this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));


        Button changeLang = findViewById(R.id.changeMyLang);
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showChangeLanguageDialog();
            }
        });


        Switch switch1 = findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }

        });

    }
    private void showChangeLanguageDialog() {
        final String[] listItems = {"English", "Русский"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingActivity.this);
        mBuilder.setTitle("Choose Language. . .");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    setLocale("eng");
                    recreate();
                } else if (i == 1) {
                    setLocale("ru");
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }

}