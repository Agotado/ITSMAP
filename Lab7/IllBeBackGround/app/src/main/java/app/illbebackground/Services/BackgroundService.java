package app.illbebackground.Services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class BackgroundService extends Service {

    public static String BROADCAST_BG_SERVICE_RESULT = "app.illbebackground.backgroundResult";

    //time to wait before broadcasting
    private long wait = 4 * 1000; //4 seconds

    //flag to determine when the service is running
    private boolean started = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Testing", "Created Service!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(!started && intent != null) {
            started = true;
            backgroundWork(wait);
        }
        Log.d("Testing", "Starting BG Service");

//        return START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    private void backgroundWork(final long waitTime) {
        Log.d("Testing", "Start BackgroundWork ");

        //Heavily inspired by leafCastle's demo
        AsyncTask<Object, Object, String> task = new AsyncTask<Object, Object, String>() {
            @Override
            protected String doInBackground(Object[] params) {
                try {
                    Log.d("Testing", "Create Thread ");
                    Thread.sleep(waitTime);
                    Log.d("Testing", "Jobs Done!");
                } catch (InterruptedException e) {
                    Log.d("Testing", "Service failed");
                    return null;
                }

                return "Completed task in " + waitTime/1000 + " seconds";
            }

            @Override
            protected void onPostExecute(String stringResult) {
                super.onPostExecute(stringResult);
                broadcastResult(stringResult);

                //if Service is still running, keep doing this recursively
                if(started){
                    backgroundWork(waitTime);
                }
            }

        };
        task.execute();
    }

    private void broadcastResult(String result) {
        Intent broadCast = new Intent();
        broadCast.setAction(BROADCAST_BG_SERVICE_RESULT);
        broadCast.putExtra("taskResult", result);
        Log.d("Testing", "Broadcasting: " + result);

        LocalBroadcastManager.getInstance(this).sendBroadcast(broadCast);
    }

    //not implemented because binding isnt relevant
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        started = false;
        Log.d("Testing", "Stopping BG Service");
    }
}
