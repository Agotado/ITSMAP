package com.example.andreas.showmethetasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andreas on 21-Sep-16.
 */
public class TaskAdaptor extends BaseAdapter {
    Context context;
    ArrayList<Task> tasks;
    Task task;

    public TaskAdaptor(Context c, ArrayList<Task> taskList) {
        this.context = c;
        this.tasks = taskList;
    }

    @Override
    public int getCount() {
        if(tasks != null) {
            return tasks.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if(tasks != null) {
            return tasks.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater taskInflator = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = taskInflator.inflate(R.layout.task_adapter_model,null);
        }

        task = tasks.get(position);
        if(task != null) {
            TextView taskName = (TextView)convertView.findViewById(R.id.task_text);
            taskName.setText(task.getTaskName());

            TextView placeName = (TextView)convertView.findViewById(R.id.place_text);
            placeName.setText(task.getPlace());
        }
        return convertView;
    }
}
