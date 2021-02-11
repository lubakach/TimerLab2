package com.example.language;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import org.intellij.lang.annotations.Language;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button_settings;
    public boolean big=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        //SizeSettings.restoreSettings(PreferenceManager.getDefaultSharedPreferences(this), this);
        actionBar.setTitle(getResources().getString(R.string.app_name));



        button= (Button) findViewById(R.id.infoActivity);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });
        button_settings= (Button) findViewById(R.id.settingsActivity);
        button_settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openSettings();
            }
        });


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        big=true;
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.size_menu){
            //SizeSettings.restoreFontSize(PreferenceManager.getDefaultSharedPreferences(this), this);
            SizeSettings.restoreFontSize(PreferenceManager.getDefaultSharedPreferences(this), this);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
    public void openActivity2(){
        Intent intent = new Intent(this, TimersActivity.class);
        startActivity(intent);
    }
    public void openSettings(){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

}