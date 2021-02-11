package com.example.language;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import yuku.ambilwarna.AmbilWarnaDialog;

public class AddActivity extends AppCompatActivity {
    EditText name_input, preparation_input, work_input, rest_input, cycle_input, calm_input;
    Button add_button;
    ConstraintLayout mLayout;
    int mDefaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mLayout = (ConstraintLayout) findViewById(R.id.color_layout);
        mDefaultColor = ContextCompat.getColor(AddActivity.this, R.color.black);

        name_input = findViewById(R.id.name_input);
        preparation_input = findViewById(R.id.preparation_input);
        work_input = findViewById(R.id.work_input);
        rest_input = findViewById(R.id.rest_input);
        cycle_input = findViewById(R.id.cycle_input);
        calm_input = findViewById(R.id.calm_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDataBaseHelper myDB = new MyDataBaseHelper(AddActivity.this);
                myDB.addTimer(name_input.getText().toString().trim(),
                        Integer.valueOf(preparation_input.getText().toString().trim()),
                        Integer.valueOf(work_input.getText().toString().trim()),
                        Integer.valueOf(rest_input.getText().toString().trim()),
                        Integer.valueOf(cycle_input.getText().toString().trim()),
                        Integer.valueOf(calm_input.getText().toString().trim()),
                        Integer.valueOf(mDefaultColor));
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_color, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.color_icon){
            openColorPicker();
        }
        return super.onOptionsItemSelected(item);
    }
    public void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor = color;
                mLayout.setBackgroundColor(mDefaultColor);
            }
        });
        colorPicker.show();
    }
}