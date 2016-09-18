package com.example.andreas.oneapptorulethemall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_pickerDemo = (Button)findViewById(R.id.btn_pickerDemo);
        Button btn_editTextDemo = (Button)findViewById(R.id.btn_editTextDemo);

        //Possible received intents
        String pickerResult = getIntent().getStringExtra("pickerResult");

        if(pickerResult != null) {
            Toast toast = Toast.makeText(getApplicationContext(), "Picker value was: " + pickerResult, Toast.LENGTH_SHORT);
            toast.show();
        }

        btn_pickerDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startPickerDemo = new Intent(getApplicationContext(), PickerActivity.class);

                startActivity(startPickerDemo);
            }
        });

        btn_editTextDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startEditTextDemo = new Intent(getApplicationContext(), EditTextActivity.class);

                startActivity(startEditTextDemo);
            }
        });

    }
}
