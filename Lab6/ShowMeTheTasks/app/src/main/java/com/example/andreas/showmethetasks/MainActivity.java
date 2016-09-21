package com.example.andreas.showmethetasks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

        final DatabaseHelper databaseHelper = DatabaseHelper.getsInstance(getApplicationContext());
        final Task task = new Task();

        final EditText taskName = (EditText) findViewById(R.id.task_input);
        final EditText placeName = (EditText) findViewById(R.id.place_input);

        Button btn_add = (Button) findViewById(R.id.btn_add);

        final ArrayList<Task> taskList = new ArrayList<Task>();

        for (Task receivedTask :databaseHelper.getAllTasks()) {
            taskList.add(receivedTask);
        }

        taskAdaptor = new TaskAdaptor(this,taskList);
        taskListView = (ListView)findViewById(R.id.listView);
        taskListView.setAdapter(taskAdaptor);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                task.setTaskName(taskName.getText().toString());
                task.setPlace(placeName.getText().toString());
                task.setTaskId(databaseHelper.addTask(task));

                Log.d("Testing", "Task: " + task.getTaskId() + " " + task.getTaskName() + " " + task.getPlace());

                taskList.add(task);
                taskAdaptor.notifyDataSetChanged();
                if (databaseHelper.WARNING != null) {
                    Toast.makeText(getApplicationContext(), databaseHelper.WARNING, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
