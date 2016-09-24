package app.tankineedanoperator;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        Button btn_connection = (Button)findViewById(R.id.btn_connection);

        btn_connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();


                if(isConnected) {
                    Toast.makeText(getApplicationContext(),"App is connected!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"App is not connected!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
