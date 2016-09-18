package com.example.andreas.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import java.math.BigInteger;

public class EditActivity extends AppCompatActivity {

    int messageID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Button btn_save = (Button)findViewById(R.id.btn_save);
        Button btn_cancel = (Button)findViewById(R.id.btn_cancel);
        final EditText nameInput = (EditText)findViewById(R.id.name_input);
        final EditText idInput = (EditText)findViewById(R.id.id_input);
        final RadioButton devInputYes = (RadioButton)findViewById(R.id.radioBtn_yes);
        final RadioButton devInputNo = (RadioButton)findViewById(R.id.radioBtn_no);

        nameInput.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(imm.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        //if intent is received, get the info
        String messageName = getIntent().getStringExtra("nameInfoPopulate");

        String messageIdReceived = getIntent().getStringExtra("idInfoPopulate");

        Boolean messageDevInfo = getIntent().getBooleanExtra("devInfoPopulate",false);
        idInput.setText("");

        if(messageName != null) {
            nameInput.setText(messageName);

            if(!messageIdReceived.isEmpty()) {
                messageID = Integer.parseInt(messageIdReceived);
                idInput.setText(String.valueOf(messageID));
            }

            if(messageDevInfo)
            {
                devInputYes.setChecked(true);
            } else
            {
                devInputNo.setChecked(true);
            }
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnInfo = new Intent(getApplicationContext(), ViewAcivity.class);
                //get info
                String nameInfo = nameInput.getText().toString();
                String idInfo = idInput.getText().toString();
                Boolean devInfo = null;

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
