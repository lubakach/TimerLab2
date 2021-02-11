package com.example.language;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper{

    private Context context;
    private static final String DATABASE_NAME ="Library_workout.db";
    private static final int DATABASE_VERSION =1;

    private static final String TABLE_NAME="my_workouts";
    private static final String COLUMN_ID="_id";
    private static final String NAME="Name";
    private static final String PREPARATION="Preparation";
    private static final String WORK="Work";
    private static final String REST="Rest";
    private static final String CYCLE="Cycle";
    private static final String CALM="Calm";
    private static final String COLOR="Color";


    public MyDataBaseHelper(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context= context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                PREPARATION + " INTEGER, " +
                WORK + " INTEGER, " +
                REST + " INTEGER, " +
                CYCLE + " INTEGER, " +
                CALM + " INTEGER, " +
                COLOR + " INTEGER);";
        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    void addTimer(String title, int prep, int work, int rest, int cycle, int calm, int color){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME, title);
        cv.put(PREPARATION, prep);
        cv.put(WORK, work);
        cv.put(REST, rest);
        cv.put(CYCLE, cycle);
        cv.put(CALM, calm);
        cv.put(COLOR, color);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result==-1)
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }

    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
         SQLiteDatabase db = this.getReadableDatabase();

         Cursor cursor = null;
         if(db!=null){
             cursor = db.rawQuery(query, null);
         }
         return cursor;
    }
    void updateData(String row_id, String title, int prep, int work, int rest, int cycle, int calm, int color){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME, title);
        cv.put(PREPARATION, prep);
        cv.put(WORK, work);
        cv.put(REST, rest);
        cv.put(CYCLE, cycle);
        cv.put(CALM, calm);
        cv.put(COLOR, color);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}

