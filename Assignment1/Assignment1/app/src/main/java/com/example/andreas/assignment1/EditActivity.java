package com.example.andreas.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        Button btn_ok = (Button)findViewById(R.id.btn_ok);
        Button btn_cancel = (Button)findViewById(R.id.btn_cancel);
        final EditText nameInput = (EditText)findViewById(R.id.name_input);
        final EditText idInput = (EditText)findViewById(R.id.id_input);
        final RadioButton devInputYes = (RadioButton)findViewById(R.id.radioBtn_yes);
        final RadioButton devInputNo = (RadioButton)findViewById(R.id.radioBtn_no);


        //if intent is recieved, get the info
        String messageName = getIntent().getStringExtra("nameInfoPopulate");
        String messageID = getIntent().getStringExtra("idInfoPopulate");
        Boolean messageDevInfo = getIntent().getBooleanExtra("devInfoPopulate",false);
        Log.d("Testing", " got the intent for populating " + messageName + " " + messageID + " " + messageDevInfo);


        if(messageName != null) {
            nameInput.setText(messageName);
            Log.d("Testing", " name ");

            idInput.setText(messageID);
            Log.d("Testing", " id ");


            if(messageDevInfo == true)
            {
                devInputYes.setChecked(true);
            } else
            {
                devInputNo.setChecked(true);
            }
            Log.d("Testing", " devInfo ");
        }

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnInfo = new Intent(getApplicationContext(), ViewAcivity.class);
                //get info
                String nameInfo = nameInput.getText().toString();
                String idInfo = idInput.getText().toString();
                Boolean devInfo = null;

                Log.d("Testing", " b4 radio ");

                if(devInputYes.isChecked()) {
                    devInfo = true;
                }
                else if(devInputNo.isChecked())
                {
                    devInfo = false;
                }

                //insert info into intent
                returnInfo.putExtra("nameInfo", nameInfo);
                returnInfo.putExtra("idInfo", idInfo);
                returnInfo.putExtra("devInfo", devInfo);

                //return info
                startActivity(returnInfo);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnToView = new Intent(getApplicationContext(), ViewAcivity.class);
                startActivity(returnToView);
            }
        });

    }
}
