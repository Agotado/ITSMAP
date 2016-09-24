package app.illbebackground;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import app.illbebackground.Services.BackgroundService;
import app.illbebackground.Services.CounterBindingService;
import app.illbebackground.Services.FooBazIntentService;

public class MainActivity extends AppCompatActivity {

    //BoundService variables
    private CounterBindingService countService;
    private ServiceConnection countingSC;
    private boolean bound = false;
    private int count;

    //IntentService variables
    private int fooCount = 0;
    private int bazCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BG Service buttons
        Button btn_start = (Button)findViewById(R.id.btn_start);
        Button btn_stop = (Button)findViewById(R.id.btn_stop);

        //Bound Service buttons
        Button btn_bind = (Button)findViewById(R.id.btn_bind);
        Button btn_unBind = (Button)findViewById(R.id.btn_unbind);
        Button btn_bindStatus = (Button)findViewById(R.id.btn_bindStatus);

        //Intent Service buttons
        Button btn_foo = (Button)findViewById(R.id.btn_foo);
        Button btn_baz = (Button)findViewById(R.id.btn_baz);

        //Start BgService
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent startCounterService = new Intent(getApplicationContext(),BackgroundService.class);
                startService(startCounterService);

                Log.d("Testing","Start Pressed!");
            }
        });

        //Stop bgService
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopCounter = new Intent(getApplicationContext(), BackgroundService.class);
                stopService(stopCounter);
                Log.d("Testing","Stop Pressed!");
            }
        });

        //bind to BoundService
        btn_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Testing", "Bind!..");

                if(!bound) {

                    Intent bind = new Intent(getApplicationContext(), CounterBindingService.class);
                    bindService(bind, countingSC, Context.BIND_AUTO_CREATE);
                    bound = true;
                    Toast.makeText(getApplicationContext(),"You are now Bound!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //unBind from BoundService
        btn_unBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Testing", "unBind!..");

                if(bound) {
                    unbindService(countingSC);
                    bound = false;
                    Toast.makeText(getApplicationContext(),"You are now unBound!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        //BoundService status
        btn_bindStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Testing", "BindStatus");

                if(bound && countService != null) {
                    count = countService.getCount();

                    Toast.makeText(getApplicationContext(),"Count is " + count, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"You are not bound to any service", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Create connection to BoundService
        countingSC = new ServiceConnection() {
            //Connect
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

                countService = ((CounterBindingService.ServiceBinder)service).getService();
                Log.d("Testing", "Counting service connected");
            }

            //Disconnect
            @Override
            public void onServiceDisconnected(ComponentName name) {
                countService = null;
                Log.d("Testing", "Counting service disconnected");
            }
        };

        //IntentService Foo call
        btn_foo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fooCount++;
                FooBazIntentService.startActionFoo(getApplicationContext(), "FOO" + fooCount, "" + (fooCount+bazCount));
            }
        });

        //IntentService Baz call
        btn_baz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bazCount++;
                FooBazIntentService.startActionBaz(getApplicationContext(), "BAZ" + bazCount, "" + (fooCount+bazCount));
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
