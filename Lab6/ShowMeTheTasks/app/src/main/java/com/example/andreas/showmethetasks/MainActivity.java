package com.example.andreas.showmethetasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TaskAdaptor taskAdaptor;
    private ListView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create handles
        final DatabaseHelper databaseHelper = DatabaseHelper.getsInstance(getApplicationContext());
        final Task task = new Task();
        final EditText taskInput = (EditText) findViewById(R.id.task_input);
        final EditText placeInput = (EditText) findViewById(R.id.place_input);
        Button btn_add = (Button) findViewById(R.id.btn_add);

        //list for tasks
        final ArrayList<Task> taskList = new ArrayList<Task>();

        //foreach task in the database, add to the taskList
        for (Task receivedTask :databaseHelper.getAllTasks()) {
            taskList.add(receivedTask);
        }

        //create taskAdaptor
        taskAdaptor = new TaskAdaptor(this,taskList);

        //create listView
        taskListView = (ListView)findViewById(R.id.listView);

        //link the listView & the taskAdaptor
        taskListView.setAdapter(taskAdaptor);

        //Add tasks to ListView + Database
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //populate task object with data from fields + add it to the database (which returns the taskId)
                task.setTaskName(taskInput.getText().toString());
                task.setPlace(placeInput.getText().toString());
                task.setTaskId(databaseHelper.addTask(task));

                Log.d("Testing", "Task: " + task.getTaskId() + " " + task.getTaskName() + " " + task.getPlace());

                //add task to list
                taskList.add(task);

                //notify changes
                taskAdaptor.notifyDataSetChanged();

                //error handling, if databaseindex somehow get out of bounds, warning is set in the databaseHelper class
                if (databaseHelper.WARNING != null) {
                    Toast.makeText(getApplicationContext(), databaseHelper.WARNING, Toast.LENGTH_SHORT).show();
                }

                //Remove text from input fields
                taskInput.setText("");
                placeInput.setText("");
            }
        });

        //Long click delete in ListView + Database
        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //get task in list position
                Task task = taskList.get(position);

                //delete said task
                databaseHelper.deleteTask(task.getTaskId());

                //remove task from list
                taskList.remove(position);

                //notify the adaptor that it should refresh its content
                taskAdaptor.notifyDataSetChanged();

                return true;
            }
        });

    }
}
