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

    //database helper instance
    private static DatabaseHelper databaseHelper;

    //warning string for error handling
    public static String WARNING = null;

    //database info
    private static final String DATABASE_NAME = "MyDatabase";
    private static final String DATABASE_TABLE_NAME = "MyTable";
    private static final int DATABASE_VERSION = 1;

    //database columns
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASK_NAME = "taskName";
    public static final String COLUMN_PLACE_NAME = "placeName";

    //sql row creation string
    private static final String DATABASE_CREATE_TABLE = "create table "
            + DATABASE_TABLE_NAME + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TASK_NAME + " string, "
            + COLUMN_PLACE_NAME + " string);";


    //databaseHelper get method, makes sure that only one instance of the database will exist
    public static synchronized DatabaseHelper getsInstance(Context context) {

        if(databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context.getApplicationContext());
        }

        return databaseHelper;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //creating database table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TABLE);
        Log.d("Testing", "Creating Database");
    }

    //open database method
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.d("Testing", "Opening Database");

    }

    //override needed for implementation, but in this case, the functionality is unnecessary
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Add row to table
    public Long addTask(Task t) {

        //create content object and insert the values from the task object
        ContentValues values = new ContentValues();
        values.put(this.COLUMN_TASK_NAME, t.getTaskName());
        values.put(this.COLUMN_PLACE_NAME, t.getPlace());

        //get the database object from the databasehelper instance
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        //insert the row data into the table
        return db.insert(databaseHelper.DATABASE_TABLE_NAME,null,values);
    }


    //get specific row from table
    public Task getTask(Long task_id) {

        //get database instance
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        //create task object
        Task task = new Task();

        //create cursor
        Cursor c = null;

        //wanted columns
        String[] columns = new String[] {
                databaseHelper.COLUMN_ID,
                databaseHelper.COLUMN_TASK_NAME,
                databaseHelper.COLUMN_PLACE_NAME};

        try {

            //get columns from database from row where id column has the data = task_id
            c = db.query(databaseHelper.DATABASE_TABLE_NAME, columns, databaseHelper.COLUMN_ID + "=?", new String[] {String.valueOf(task_id)},null,null,null,null);
            c.moveToFirst();

            Log.d("Testing", "Row: " + "ID: " + String.valueOf(c.getLong(0)) + " Task: " + c.getString(1) + " Place: " + c.getString(2));
        }
        catch (CursorIndexOutOfBoundsException e) {

            //catch out of bounds errors and return to mainActivity
            WARNING = "Database out of bounds!";
            return null;
        }

        //insert the fetched data into the task object
        task.setTaskId(c.getLong(0));
        task.setTaskName(c.getString(1));
        task.setPlace(c.getString(2));

        //return the task
        return task;
    }

    //delete row from database
    public void deleteTask(Long task_id) {

        //get database instance
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        //id of the row which is to be deleted
        String[] task_to_delete = {String.valueOf(task_id)};

        Log.d("Testing", "Deleting Task: " + task_id);

        //delete row from database
        db.delete(databaseHelper.DATABASE_TABLE_NAME, databaseHelper.COLUMN_ID + "=?", task_to_delete);
    }

    //get all rows from database
    public List<Task> getAllTasks() {

        //get database instance
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        //list to hold tasks
        List<Task> taskList = new ArrayList<Task>();

        //create cursor
        Cursor c = null;

        //wanted columns
        String[] columns = new String[] {
                databaseHelper.COLUMN_ID,
                databaseHelper.COLUMN_TASK_NAME,
                databaseHelper.COLUMN_PLACE_NAME};

        try {
            //get rows from database
            c = db.query(databaseHelper.DATABASE_TABLE_NAME, columns,null,null,null,null,null);

            //foreach row
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                //Add task to list of tasks
                taskList.add(databaseHelper.getTask(c.getLong(0)));
            }
        }
        catch (CursorIndexOutOfBoundsException e) {

            //catch possible out of bounds errors and return to mainActivity
            WARNING = "Database out of bounds!";
            return null;
        }

        //return the list of tasks
        return taskList;
    }
}
