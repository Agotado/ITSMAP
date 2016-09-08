package com.example.andreas.post_it;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {
    static String savedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        final TextView text = (TextView)findViewById(R.id.text_view);
        Log.d("LifeCycle", "create " + savedText);

        text.setText(savedText);

        Button btn_edit = (Button)findViewById(R.id.btn_edit);

        String message = getIntent().getStringExtra("ConfirmedMessage");

        if(savedInstanceState != null){
            savedText = savedInstanceState.getString("savedText");
            text.setText(savedText);
            Log.d("LifeCycle", "Saved " + message + " and " + savedInstanceState);
        }

        if(message != null) {
            text.setText(message);
            savedText = text.getText().toString();
            Log.d("LifeCycle", "else called " + message + " and " + savedInstanceState);
        }

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                savedText = text.getText().toString();
                Intent edit = new Intent(getApplicationContext(),EditActivity.class);
                startActivity(edit);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString("savedText", savedText);

        super.onSaveInstanceState(savedInstanceState);
    }
}
