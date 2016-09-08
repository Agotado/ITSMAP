package com.example.andreas.goodintentions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_email = (Button)findViewById(R.id.btn_Email);
        Button btn_sms = (Button)findViewById(R.id.btn_sms);

        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendMail = new Intent(Intent.ACTION_SEND);
                sendMail.setType("text/html");
                sendMail.putExtra(Intent.EXTRA_EMAIL, new String[] {"myEmail@mail.com"});
                sendMail.putExtra(Intent.EXTRA_SUBJECT, "What, this is amazing!");
                sendMail.putExtra(Intent.EXTRA_TEXT, "This is how we party! :b");

                startActivity(Intent.createChooser(sendMail, "Send Email"));
            }
        });

        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Doesnt work try another approach
//                String smsBody = "Damn this is cool!";
//                Intent sendSMS = new Intent(Intent.ACTION_VIEW);
//                sendSMS.putExtra("sms_body", smsBody);
//                sendSMS.setType("vnd.android-dir/mms-sms");
//                startActivity(sendSMS);
            }
        });




    }
}
