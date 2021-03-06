package com.example.andreas.assignment1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class ViewAcivity extends AppCompatActivity {
    static Uri savedUri = null;
    static String savedName;
    static String savedId;
    static Boolean savedDevInfo = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_acivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ImageButton profilePic = (ImageButton) findViewById(R.id.profilePic);
        final TextView nameText = (TextView) findViewById(R.id.name_view);
        final TextView idText = (TextView) findViewById(R.id.id_view);
        final CheckBox devInfo = (CheckBox) findViewById(R.id.checkBox_view);
        Log.d("Testing", "Created Elements ");

        //if cancel clicked, get persisted data:
        nameText.setText(savedName);
        idText.setText(savedId);
        devInfo.setChecked(savedDevInfo);
        if (savedUri != null) {
            profilePic.setImageURI(savedUri);
        }

        //if intent is recieved, get the info
        String messageName = getIntent().getStringExtra("nameInfo");
        String messageID = getIntent().getStringExtra("idInfo");
        Boolean messageDevInfo = getIntent().getBooleanExtra("devInfo", false);
        Log.d("Testing", "Got the intent " + messageName + " " + messageID + " " + messageDevInfo + " " + savedUri);

        //if saved info, get it!
        if (savedInstanceState != null) {
            savedName = savedInstanceState.getString("savedName");
            savedId = savedInstanceState.getString("savedId");
            savedDevInfo = savedInstanceState.getBoolean("savedDevInfo");
            if (savedUri != null) {
                savedUri = Uri.parse(savedInstanceState.getString("savedUri"));
            }

            nameText.setText(savedName);
            idText.setText(savedId);
            devInfo.setChecked(savedDevInfo);
            profilePic.setImageURI(savedUri);
            Log.d("Testing", "Saved info " + nameText.getText() + " " + idText.getText() + " " + devInfo.isChecked());
        }

        // if intent received overwrite the saved info!
        // The check if a messageName exists will suffice, since the Intent by this design
        // always gives a value for each parameter, empty string being != null
        if (messageName != null) {
            nameText.setText(messageName);
            idText.setText(messageID);
            devInfo.setChecked(messageDevInfo);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                savedName = nameText.getText().toString();
                savedId = idText.getText().toString();
                savedDevInfo = devInfo.isChecked();

                Intent fillOut = new Intent(getApplicationContext(), EditActivity.class);

                Log.d("Testing", " filling " + savedName + " " + savedId + " " + savedDevInfo);

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

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Both Camera & Storage permissions needed", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                Intent takePicture = new Intent("android.media.action.IMAGE_CAPTURE");

                //Trouble overwriting a picturefile with same name, hence this flagging system..
                int pictureFlag = 0;

                if (savedUri != null) {

                    // should now contain "PicX.jpg"
                    String temp = (savedUri.toString().substring(savedUri.toString().length() - 8));

                    //take the X from temp
                    int lastUriFlag = Integer.parseInt(temp.substring(3, 4));

                    if (lastUriFlag == pictureFlag) {
                        pictureFlag = 1;
                    }
                }

                File photo = new File(Environment.getExternalStorageDirectory(), "Pic" + pictureFlag + ".jpg");
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                savedUri = Uri.fromFile(photo);

                Log.d("Testing", "Take pic " + savedUri);

                startActivityForResult(takePicture, 0);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 0) {

            ImageButton profilePic = (ImageButton) findViewById(R.id.profilePic);
            profilePic.setImageURI(savedUri);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString("savedName", savedName);
        savedInstanceState.putString("savedId", savedId);
        savedInstanceState.putBoolean("savedDevInfo", savedDevInfo);

        if (savedUri != null) {

            savedInstanceState.putString("savedUri", savedUri.getEncodedPath());
        }

        Log.d("Testing", " saving! " + savedName + " " + savedId + " " + savedDevInfo + " " + savedUri);
        super.onSaveInstanceState(savedInstanceState);
    }
}