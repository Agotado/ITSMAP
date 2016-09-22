package app.illbebackground;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import app.illbebackground.Services.BackgroundService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_start = (Button)findViewById(R.id.btn_start);
        Button btn_stop = (Button)findViewById(R.id.btn_stop);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent startCounterService = new Intent(getApplicationContext(),BackgroundService.class);
                startService(startCounterService);

                Log.d("Testing","Start Pressed!");
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopCounter = new Intent(getApplicationContext(), BackgroundService.class);
                stopService(stopCounter);
                Log.d("Testing","Stop Pressed!");
            }
        });
    }

    @Override
    protected void onResume() {

        //Filtering the intents which the receiver should react to
        IntentFilter filter = new IntentFilter();
        filter.addAction(BackgroundService.BROADCAST_BG_SERVICE_RESULT);

        //register receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(onBackgroundServiceResult, filter);
        super.onResume();
    }

    private BroadcastReceiver onBackgroundServiceResult = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //get result from intent
            String result = intent.getStringExtra("taskResult");
            if(result == null) {
                result = "Error receiving broadcast";
            }
            //write result as toast
            Toast.makeText(getApplicationContext(),"Got result from BG service:\n" + result, Toast.LENGTH_SHORT).show();
        }
    };
}
