package com.example.andreas.oneapptorulethemall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class PickerActivity extends AppCompatActivity {

    String savedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

        final NumberPicker numberPicker = (NumberPicker)findViewById(R.id.numberPicker_);
        Button btn_ok = (Button)findViewById(R.id.btn_ok);
        Button btn_cancel = (Button)findViewById(R.id.btn_cancel);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(1000);

        if(savedInstanceState != null) {
            savedNumber = savedInstanceState.getString("savedNumber");

            numberPicker.setValue(Integer.parseInt(savedNumber));
        }

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendResult = new Intent(getApplicationContext(), MainActivity.class);

                sendResult.putExtra("pickerResult", String.valueOf(numberPicker.getValue()));

                startActivity(sendResult);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnWithoutResult = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(returnWithoutResult);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        int pickerInfo = ((NumberPicker)findViewById(R.id.numberPicker_)).getValue();
        savedNumber = String.valueOf(pickerInfo);

        savedInstanceState.putString("savedNumber", savedNumber);
        super.onSaveInstanceState(savedInstanceState);
    }
}
