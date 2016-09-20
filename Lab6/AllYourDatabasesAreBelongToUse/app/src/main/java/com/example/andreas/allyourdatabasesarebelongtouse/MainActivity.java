package com.example.andreas.allyourdatabasesarebelongtouse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DatabaseHelper databaseHelper = DatabaseHelper.getsInstance(getApplicationContext());
        final Task task = new Task();

        final EditText taskName = (EditText)findViewById(R.id.task_input);
        final EditText placeName = (EditText)findViewById(R.id.place_input);

        Button btn_add = (Button)findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                task.setTaskName(taskName.getText().toString());
                task.setPlace(placeName.getText().toString());
                task.setTaskId(databaseHelper.addTask(task));

                Log.d("Testing", "Task: " + task.getTaskId() + " " + task.getTaskName() + " " + task.getPlace());

                if(databaseHelper.WARNING!= null) {
                    Toast.makeText( getApplicationContext() , databaseHelper.WARNING, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}