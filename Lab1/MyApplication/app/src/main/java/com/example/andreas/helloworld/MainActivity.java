package com.example.andreas.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView newText = (TextView)findViewById(R.id.textHello);
        newText.setText("newText waat");

        Button bait = (Button)findViewById(R.id.btnClick);

        bait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You've been baited sir..",Toast.LENGTH_SHORT).show();
            }
        });

        Button btnChange = (Button)findViewById(R.id.btnChange);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newText.setText("Textz changed");
            }
        });

        Button btnExit = (Button)findViewById(R.id.btnExit);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);

            }
        });
    }
}
