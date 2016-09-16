package com.example.andreas.assignment1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;

public class ViewAcivity extends AppCompatActivity {
    static Uri savedUri;
    static String savedName;
    static String savedId;
    static Boolean savedDevInfo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_acivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ImageButton profilePic = (ImageButton)findViewById(R.id.profilePic);
        final TextView nameText = (TextView)findViewById(R.id.name_view);
        final TextView idText = (TextView)findViewById(R.id.id_view);
        final CheckBox devInfo = (CheckBox)findViewById(R.id.checkBox_view);
        Log.d("Testing", "created stuffz ");

        //if persisted data:
        nameText.setText(savedName);
        idText.setText(savedId);
        devInfo.setChecked(savedDevInfo);
        if(savedUri != null) {
            profilePic.setImageURI(savedUri);
        }

        //if intent is recieved, get the info
        String messageName = getIntent().getStringExtra("nameInfo");
        String messageID = getIntent().getStringExtra("idInfo");
        Boolean messageDevInfo = getIntent().getBooleanExtra("devInfo",false);
        Log.d("Testing", " got the intent " + messageName + " " + messageID + " " + messageDevInfo + " " + savedUri);

        //if saved info, get it!
        if(savedInstanceState != null) {
            savedName = savedInstanceState.getString("savedName");
            savedId = savedInstanceState.getString("savedId");
            savedDevInfo = savedInstanceState.getBoolean("savedDevInfo");
            savedUri = Uri.parse(savedInstanceState.getString("savedUri"));

            nameText.setText(savedName);
            idText.setText(savedId);
            devInfo.setChecked(savedDevInfo);
            profilePic.setImageURI(savedUri);
            Log.d("Testing", " saved info " + nameText.getText() + " " + idText.getText() + " " + devInfo.isChecked());
        }

        // if intent received overwrite the saved info!
        // The check if a messageName exists will suffice, since the Intent by this design
        // always gives a value for each parameter, empty string being != null
        if(messageName != null) {
            nameText.setText(messageName);
            Log.d("Testing", " name " + messageName);

            idText.setText(messageID);
            Log.d("Testing", " id " + messageID);

            devInfo.setChecked(messageDevInfo);
            Log.d("Testing", " devInfo " + messageDevInfo);
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

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent("android.media.action.IMAGE_CAPTURE");
                File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");

                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                savedUri = Uri.fromFile(photo);

                startActivityForResult(takePicture, 0);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK && requestCode == 0) {

            ImageButton profilePic = (ImageButton) findViewById(R.id.profilePic);
            profilePic.setImageURI(savedUri);

            Log.d("Testing", " Pictur! " + savedUri);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString("savedName", savedName);
        savedInstanceState.putString("savedId", savedId);
        savedInstanceState.putBoolean("savedDevInfo",savedDevInfo);
        savedInstanceState.putString("savedUri",savedUri.getEncodedPath());

        super.onSaveInstanceState(savedInstanceState);
    }
}
