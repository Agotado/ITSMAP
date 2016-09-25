package app.jsonbourne;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;

public class VolleyActivity extends AppCompatActivity {

    private boolean isConnected;
    private static final String WEATHER_API_KEY = "dea2451f405bfeae7aa4cf8bf4f1800c";
    private static final long CITY_ID_AARHUS = 2624652;
    private static String WEATHER_API_CALL = "http://api.openweathermap.org/data/2.5/forecast/city?id=" + CITY_ID_AARHUS + "&APPID=" + WEATHER_API_KEY;
    private TextView responseText;

    //Queue for volley
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //Create handlers
        Button btn_connection = (Button) findViewById(R.id.btn_connection);
        Button btn_weather = (Button) findViewById(R.id.btn_Weather);
        Button btn_switch = (Button) findViewById(R.id.btn_switch);
        Button btn_parse = (Button) findViewById(R.id.btn_parseJSON);
        responseText = (TextView) findViewById(R.id.txt_weather);
        responseText.setMovementMethod(new ScrollingMovementMethod());


        //Check connection
        btn_connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                if (isConnected) {
                    Toast.makeText(getApplicationContext(), "App is connected!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "App is not connected!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Get weatherData
        btn_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Testing", "go get data!");
                WeatherDataTask();
            }
        });

        //Parse JSON
        btn_parse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Testing", "Parse!");

                if (responseText.getText() != null) {
                    parseJSON(responseText.getText().toString());
                }
            }
        });

        //Switch between HTTP & Volley
        btn_switch.setText("Switch to HttpURLConnection");
        btn_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Testing", "HTTP!");
                finish();
            }
        });
    }

    //Parsing function
    private void parseJSON(String json) {
        Log.d("Testing", "Parse!");

        try {
            //Create json object with the given json string
            JSONObject cityWeather = new JSONObject(json);

            //get data from "city" field
            JSONObject city = cityWeather.getJSONObject("city");
            String name = city.getString("name");

            //get list of data from city
            JSONArray measurements = cityWeather.getJSONArray("list");

            //get description of weather
            String weatherDescription = measurements.getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main");

            //get temp and convert from kelvin to celsius
            Double temperature = measurements.getJSONObject(0).getJSONObject("main").getDouble("temp");
            Double temperatureInCelsius = temperature - 273.15;

            //format string describing wanted info
            String weatherString = weatherDescription + " : " + String.format("%.2f", temperatureInCelsius) + " degrees C";

            //Make toast with parse info
            Toast.makeText(getApplicationContext(), name + "\n" + weatherString, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Something went wrong! :o", Toast.LENGTH_SHORT).show();

        }
    }

    //Getting data
    private void WeatherDataTask() {

        Log.d("Testing","go get data!");

        //if no queue, make new
        if(queue==null)
        {
            queue = Volley.newRequestQueue(getApplicationContext());
        }

        //wanted api url
        String url = WEATHER_API_CALL;

        //format request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseText.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseText.setText("That didnt work!");
            }
        });

        //add request to queue
        queue.add(stringRequest);
    }
}
