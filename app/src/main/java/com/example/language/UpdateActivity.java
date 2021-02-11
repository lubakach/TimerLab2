package com.example.language;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;

public class UpdateActivity extends AppCompatActivity {
    EditText name_input, preparation_input, work_input, rest_input, cycle_input, calm_input;
    Button update_button, delete_button;
    String id, name, prep, work, rest, cycle, calm, color;
    ConstraintLayout mLayout;
    int mDefaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        mLayout = (ConstraintLayout) findViewById(R.id.color_layout);
        mDefaultColor = ContextCompat.getColor(UpdateActivity.this, R.color.black);

        name_input = findViewById(R.id.name_input2);
        preparation_input = findViewById(R.id.preparation_input2);
        work_input = findViewById(R.id.work_input2);
        rest_input = findViewById(R.id.rest_input2);
        cycle_input = findViewById(R.id.cycle_input2);
        calm_input = findViewById(R.id.calm_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        getAndSetIntentData();
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyDataBaseHelper myDB = new MyDataBaseHelper(UpdateActivity.this);
                myDB.updateData(id, name_input.getText().toString().trim(),
                        Integer.valueOf(preparation_input.getText().toString().trim()),
                        Integer.valueOf(work_input.getText().toString().trim()),
                        Integer.valueOf(rest_input.getText().toString().trim()),
                        Integer.valueOf(cycle_input.getText().toString().trim()),
                        Integer.valueOf(calm_input.getText().toString().trim()),
                        Integer.valueOf(mDefaultColor));

            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id")&& getIntent().hasExtra("Name") && getIntent().hasExtra("Preparation") && getIntent().hasExtra("Work")&& getIntent().hasExtra("Rest")&& getIntent().hasExtra("Cycle")&& getIntent().hasExtra("Calm")){
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("Name");
            prep = getIntent().getStringExtra("Preparation");
            work = getIntent().getStringExtra("Work");
            rest = getIntent().getStringExtra("Rest");
            cycle = getIntent().getStringExtra("Cycle");
            calm = getIntent().getStringExtra("Calm");
            name_input.setText(name);
            preparation_input.setText(prep);
            work_input.setText(work);
            rest_input.setText(rest);
            cycle_input.setText(cycle);
            calm_input.setText(calm);


        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
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

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDataBaseHelper myDB = new MyDataBaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
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