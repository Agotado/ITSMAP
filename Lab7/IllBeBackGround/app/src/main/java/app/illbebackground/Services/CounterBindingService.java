package app.illbebackground.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class CounterBindingService extends Service {

    private static int counter;
    private static boolean running = false;

    //Add 1 to the count every second while running
    public class ServiceCounter implements Runnable {

        @Override
        public void run() {
            while(running) {
                counter++;
                Log.d("Testing", "Count: " + counter);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.d("Testing", "Something went terribly wrong!");
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //create new counter
        ServiceCounter sc = new ServiceCounter();

        //if this is the first time, set counter to 0, and spawn a thread for counting.
        if(!running) {

            counter = 0;
            running = true;

            Thread t = new Thread(sc);
            t.start();

            Log.d("Testing", "SPAWN!!");
        }

        Log.d("Testing", "Create CounterService");
    }

    //Binder class which basically returns the service
    public class ServiceBinder extends Binder {
        public CounterBindingService getService() {
            return CounterBindingService.this;
        }
    }

    public CounterBindingService() {}

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceBinder();
    }

    //Get the status on the count number
    public int getCount() {
        return counter;
    }
}
