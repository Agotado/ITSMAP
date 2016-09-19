package com.example.andreas.mementowhome;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    //Declare SharedPreferences
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;

    //This mode means it can only be read from the app that created it
    private static final int PREFERENCE_MODE_PRIVATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Create Stuff
        EditText firstName = (EditText)findViewById(R.id.firstName_input);
        EditText lastName = (EditText)findViewById(R.id.lastName_input);
        EditText age = (EditText)findViewById(R.id.age_input);
        EditText phoneNumber = (EditText)findViewById(R.id.phoneNumber_input);

        //Get preferenceSettings
        preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);

        //Get saved data if available
        String savedFName = preferenceSettings.getString("firstName", null);
        String savedLName = preferenceSettings.getString("lastName", null);
        String savedAge = preferenceSettings.getString("age", null);
        String savedPhoneNumber = preferenceSettings.getString("phoneNumber", null);

        //Apply data
        firstName.setText(savedFName);
        lastName.setText(savedLName);
        age.setText(savedAge);
        phoneNumber.setText(savedPhoneNumber);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Create handles
        EditText firstName = (EditText)findViewById(R.id.firstName_input);
        EditText lastName = (EditText)findViewById(R.id.lastName_input);
        EditText age = (EditText)findViewById(R.id.age_input);
        EditText phoneNumber = (EditText)findViewById(R.id.phoneNumber_input);

        //Get preferences
        preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();

        //Insert data into preferenceSettings
        preferenceEditor.putString("firstName", firstName.getText().toString());
        preferenceEditor.putString("lastName", lastName.getText().toString());
        preferenceEditor.putString("age", age.getText().toString());
        preferenceEditor.putString("phoneNumber", phoneNumber.getText().toString());

        //Save
        preferenceEditor.commit();
    }
}
