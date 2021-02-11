package com.example.language;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TimersActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    MyDataBaseHelper myDB;
    ImageView empty_imageview;
    TextView no_data;
    ArrayList<String> timer_id, timer_name, timer_prep, timer_work, timer_rest, timer_cycle, timer_calm, timer_color;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timers);
        //SizeSettings.restoreSettings(PreferenceManager.getDefaultSharedPreferences(this), this);
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (TimersActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        myDB = new MyDataBaseHelper(TimersActivity.this);
        timer_id = new ArrayList<>();
        timer_name = new ArrayList<>();
        timer_prep = new ArrayList<>();
        timer_work = new ArrayList<>();
        timer_rest = new ArrayList<>();
        timer_cycle = new ArrayList<>();
        timer_calm = new ArrayList<>();
        timer_color = new ArrayList<>();

        storeDataInArrays();
        customAdapter = new CustomAdapter(TimersActivity.this, TimersActivity.this, timer_id, timer_name, timer_prep, timer_work, timer_rest, timer_cycle, timer_calm, timer_color);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TimersActivity.this));



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }


    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() ==0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while(cursor.moveToNext()){
                timer_id.add(cursor.getString(0));
                timer_name.add(cursor.getString(1));
                timer_prep.add(cursor.getString(2));
                timer_work.add(cursor.getString(3));
                timer_rest.add(cursor.getString(4));
                timer_cycle.add(cursor.getString(5));
                timer_calm.add(cursor.getString(6));
                timer_color.add(cursor.getString(7));

            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDataBaseHelper myDB = new MyDataBaseHelper(TimersActivity.this);
                myDB.deleteAllData();
                //Refresh Activity
                Intent intent = new Intent(TimersActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}