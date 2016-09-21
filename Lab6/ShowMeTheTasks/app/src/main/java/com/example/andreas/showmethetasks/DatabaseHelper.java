package com.example.andreas.showmethetasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    public static String WARNING = null;

    private static final String DATABASE_NAME = "MyDatabase";
    private static final String DATABASE_TABLE_NAME = "MyTable";
    private static final int DATABASE_VERSION = 1;
    public static final String COLUMN_ID = "_id";

    //Table
    public  static final String COLUMN_TASK_NAME = "taskName";
    public  static final String COLUMN_PLACE_NAME = "placeName";

    private static final String DATABASE_CREATE_TABLE = "create table "
            + DATABASE_TABLE_NAME + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TASK_NAME + " string, "
            + COLUMN_PLACE_NAME + " string);";

    public static synchronized DatabaseHelper getsInstance(Context context) {

        if(sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
            Log.d("Testing", "Creating Database3");

        }
        Log.d("Testing", "Creating Database4");


        return sInstance;

    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TABLE);
        Log.d("Testing", "Creating Database");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.d("Testing", "Creating Database2");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public Long addTask(Task t) {

        ContentValues values = new ContentValues();
        values.put(this.COLUMN_TASK_NAME, t.getTaskName());
        values.put(this.COLUMN_PLACE_NAME, t.getPlace());

        SQLiteDatabase db = sInstance.getWritableDatabase();

        return db.insert(sInstance.DATABASE_TABLE_NAME,null,values);
    }

    public Task getTask(Long task_id) {

        SQLiteDatabase db = sInstance.getReadableDatabase();
        Task task = new Task();
        Cursor c = null;

        String[] columns = new String[] {
                sInstance.COLUMN_ID,
                sInstance.COLUMN_TASK_NAME,
                sInstance.COLUMN_PLACE_NAME};

        try {
            c = db.query(sInstance.DATABASE_TABLE_NAME, columns, sInstance.COLUMN_ID + "=?", new String[] {String.valueOf(task_id)},null,null,null,null);
            c.moveToFirst();

            Log.d("Testing", "Row: " + "ID: " + String.valueOf(c.getLong(0)) + " Task: " + c.getString(1) + " Place: " + c.getString(2));
        }
        catch (CursorIndexOutOfBoundsException e) {
            WARNING = "Database out of bounds!";
            return null;
        }

        task.setTaskId(c.getLong(0));
        task.setTaskName(c.getString(1));
        task.setPlace(c.getString(2));

        return task;
    }

    public void deleteTask(Long task_id) {

        SQLiteDatabase db = sInstance.getWritableDatabase();

        String[] task_to_delete = {String.valueOf(task_id)};

        db.delete(sInstance.DATABASE_TABLE_NAME, sInstance.COLUMN_ID, task_to_delete);
    }

    public List<Task> getAllTasks() {


        SQLiteDatabase db = sInstance.getReadableDatabase();

        List<Task> taskList = new ArrayList<Task>();
        Cursor c = null;

        String[] columns = new String[] {
                sInstance.COLUMN_ID,
                sInstance.COLUMN_TASK_NAME,
                sInstance.COLUMN_PLACE_NAME};

        try {
            c = db.query(sInstance.DATABASE_TABLE_NAME, columns,null,null,null,null,null);

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                //Add info to task
                Task task = new Task();
                task.setTaskId(c.getLong(0));
                task.setTaskName(c.getString(1));
                task.setPlace(c.getString(2));

                Log.d("Testing", "Row: " + "ID: " + String.valueOf(c.getLong(0)) + " Task: " + c.getString(1) + " Place: " + c.getString(2));

                //Add task to list of tasks
                taskList.add(task);
            }
        }
        catch (CursorIndexOutOfBoundsException e) {
            WARNING = "Database out of bounds!";
            return null;
        }
        return taskList;
    }
}
