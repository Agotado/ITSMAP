package app.illbebackground.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

/**
 * An {@link FooBazIntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class FooBazIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // FooBazIntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "app.illbebackground.Services.action.FOO";
    private static final String ACTION_BAZ = "app.illbebackground.Services.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "app.illbebackground.Services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "app.illbebackground.Services.extra.PARAM2";

    public FooBazIntentService() {
        super("FooBazIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see FooBazIntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, FooBazIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see FooBazIntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, FooBazIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        if(param1 == null) {
            param1 = "undefined";
        }
        if(param2 == null) {
            param2 = "underfined";
        }
        try {
            Log.d("IntentService","Foo started: " + param1 + " : " + param2);
            Thread.sleep(500);
            Log.d("IntentService", "Foo done son!");
        }catch (Exception e) {
            Log.d("IntentService", "Foo exception!");
        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        if(param1 == null) {
            param1 = "undefined";
        }
        if(param2 == null) {
            param2 = "underfined";
        }
        try {
            Log.d("IntentService","Baz started: " + param1 + " : " + param2);
            Thread.sleep(500);
            Log.d("IntentService", "Baz done son!");
        }catch (Exception e) {
            Log.d("IntentService", "Baz exception!");
        }
    }

    @Override
    public void onDestroy() {
        Log.d("IntentService", "Destroying");
        super.onDestroy();
    }
}
