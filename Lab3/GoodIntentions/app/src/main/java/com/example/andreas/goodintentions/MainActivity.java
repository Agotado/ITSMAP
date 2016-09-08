package com.example.andreas.goodintentions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.AlarmClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_email = (Button) findViewById(R.id.btn_Email);
        Button btn_sms = (Button) findViewById(R.id.btn_sms);
        Button btn_call = (Button) findViewById(R.id.btn_phonecall);
        Button btn_alarm = (Button)findViewById(R.id.btn_alarm);

        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendMail = new Intent(Intent.ACTION_SEND);
                sendMail.setType("text/html");
                sendMail.putExtra(Intent.EXTRA_EMAIL, new String[]{"myEmail@mail.com"});
                sendMail.putExtra(Intent.EXTRA_SUBJECT, "What, this is amazing!");
                sendMail.putExtra(Intent.EXTRA_TEXT, "This is how we party! :b");

                startActivity(Intent.createChooser(sendMail, "Send Email"));
            }
        });

        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendSMS = new Intent(Intent.ACTION_VIEW);
                String smsBody = "Damn this is cool!";

                sendSMS.putExtra("address", "someNumber");
                sendSMS.putExtra("sms_body", smsBody);
                sendSMS.setType("vnd.android-dir/mms-sms");
                sendSMS.setData(Uri.parse("sms:"));

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(),"You need permission!",Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                startActivity(sendSMS);
            }
        });

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent makeCall = new Intent(Intent.ACTION_CALL);
                String Contact = "Bette";

                makeCall.setData(Uri.parse("tel:" + Contact));

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(),"You need permission!",Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                startActivity(makeCall);
            }
        });

        btn_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
                Calendar time = Calendar.getInstance();
                int hour = time.get(Calendar.HOUR_OF_DAY);
                int minute = time.get(Calendar.MINUTE);

                setAlarm.putExtra(AlarmClock.EXTRA_HOUR, hour );
                setAlarm.putExtra(AlarmClock.EXTRA_MINUTES, minute + 1);


                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SET_ALARM) != PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(),"You need permission!",Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                startActivity(setAlarm);
            }
        });
    }
}
