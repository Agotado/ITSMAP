package com.example.andreas.selfieswaparnieedition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    int pic1 = R.drawable.arnie;
    int pic2 = R.drawable.conan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView iv1 = (ImageView)findViewById(R.id.imageView);
        final ImageView iv2 = (ImageView)findViewById(R.id.imageView2);

        Button btn_swap = (Button)findViewById(R.id.btn_swap);

        if(savedInstanceState != null) {
            pic1 = savedInstanceState.getInt("savedPic1");
            pic2 = savedInstanceState.getInt("savedPic2");
        }

        iv1.setTag(pic1);
        iv2.setTag(pic2);

        iv1.setImageResource((int)iv1.getTag());
        iv2.setImageResource((int)iv2.getTag());

        btn_swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save tags
                int tempTag1 = (int)iv1.getTag();
                int tempTag2 = (int)iv2.getTag();

                //Switch Tags
                iv1.setTag(tempTag2);
                iv2.setTag(tempTag1);

                //Apply new Tag as Image
                iv1.setImageResource((int)iv1.getTag());
                iv2.setImageResource((int)iv2.getTag());

                //Save to global variable
                pic1 = (int)iv1.getTag();
                pic2 = (int)iv2.getTag();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt("savedPic1", pic1);
        savedInstanceState.putInt("savedPic2", pic2);

        super.onSaveInstanceState(savedInstanceState);
    }
}
