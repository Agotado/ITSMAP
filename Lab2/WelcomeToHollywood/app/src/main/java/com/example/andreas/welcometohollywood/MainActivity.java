package com.example.andreas.welcometohollywood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("LifeCycle", "onCreate() called");

    };

    @Override
    protected void onStop(){
        Log.d("LifeCycle", "onStop() called");
        super.onStop();
    };

    @Override
    protected void onPause(){
        Log.d("LifeCycle", "onPause() called");
        super.onPause();
    };

    @Override
    protected void onStart(){
        Log.d("LifeCycle", "onStart() called");
        super.onStart();
    };

    @Override
    protected void onResume(){
        Log.d("LifeCycle", "onResume() called");
        super.onResume();
    };

    @Override
    protected void onRestart(){
        Log.d("LifeCycle", "onRestart() called");
        super.onRestart();
    };

    @Override
    protected void onDestroy(){
        Log.d("LifeCycle", "onDestroy() called");
        super.onDestroy();
    };
}

// When you tilt the device this sequence gets called:
// onPause(),onStop(),onDestroy(), then "getSlotFromBufferLocked", then onCreate(), onStart(), onResume()