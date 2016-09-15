package com.example.andreas.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

public class ViewAcivity extends AppCompatActivity {
    static String savedName;
    static String savedId;
    static Boolean savedDevInfo = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_acivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView nameText = (TextView)findViewById(R.id.name_view);
        final TextView idText = (TextView)findViewById(R.id.id_view);
        final CheckBox devInfo = (CheckBox)findViewById(R.id.checkBox_view);
        Log.d("Testing", "created stuffz ");

        //if persisted data:
        nameText.setText(savedName);
        idText.setText(savedId);
        devInfo.setChecked(savedDevInfo);

        //if intent is recieved, get the info
        String messageName = getIntent().getStringExtra("nameInfo");
        String messageID = getIntent().getStringExtra("idInfo");
        Boolean messageDevInfo = getIntent().getBooleanExtra("devInfo",false);
        Log.d("Testing", " got then intent " + messageName + " " + messageID + " " + messageDevInfo);

        //if saved info, get it!
        if(savedInstanceState != null) {
            savedName = savedInstanceState.getString("savedName");
            savedId = savedInstanceState.getString("savedId");
            savedDevInfo = savedInstanceState.getBoolean("savedDevInfo");

            nameText.setText(savedName);
            idText.setText(savedId);
            devInfo.setChecked(savedDevInfo);
            Log.d("Testing", " saved info " + nameText.getText() + " " + idText.getText() + " " + devInfo.isChecked());
        }

        if(messageName != null) {
            nameText.setText(messageName);
            Log.d("Testing", " name ");

            idText.setText(messageID);
            Log.d("Testing", " id ");

            devInfo.setChecked(messageDevInfo);
            Log.d("Testing", " devInfo ");
        }

        Log.d("Testing", " morar created stuffz ");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                savedName = nameText.getText().toString();
                savedId = idText.getText().toString();
                savedDevInfo = devInfo.isChecked();

                Intent fillOut = new Intent(getApplicationContext(), EditActivity.class);

                //insert info into intent
                fillOut.putExtra("nameInfoPopulate", savedName);
                fillOut.putExtra("idInfoPopulate", savedId);
                fillOut.putExtra("devInfoPopulate", savedDevInfo);

                startActivity(fillOut);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString("savedName", savedName);
        savedInstanceState.putString("savedId", savedId);
        savedInstanceState.putBoolean("savedDevInfo",savedDevInfo);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_acivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
