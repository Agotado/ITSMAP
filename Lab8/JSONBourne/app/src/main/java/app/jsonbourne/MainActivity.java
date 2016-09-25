package app.jsonbourne;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private boolean isConnected;
    private static final String WEATHER_API_KEY = "dea2451f405bfeae7aa4cf8bf4f1800c";
    private static final long CITY_ID_AARHUS = 2624652;
    private static String WEATHER_API_CALL = "http://api.openweathermap.org/data/2.5/forecast/city?id=" + CITY_ID_AARHUS + "&APPID=" + WEATHER_API_KEY;

    HttpURLConnection con = null;
    InputStream is = null;

    private TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //Create handlers
        Button btn_connection = (Button) findViewById(R.id.btn_connection);
        Button btn_weather = (Button) findViewById(R.id.btn_Weather);
        Button btn_switch = (Button) findViewById(R.id.btn_switch);
        Button btn_parse = (Button)findViewById(R.id.btn_parseJSON);
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
                WeatherDataTask wd = new WeatherDataTask();
                wd.execute(WEATHER_API_CALL);
            }
        });

        //Parse JSON
        btn_parse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Testing", "Parse!");

                if(responseText.getText() != null) {
                    Log.d("Testing", "Parse!");

                    parseJSON(responseText.getText().toString());

                }
            }
        });

        //Switch between HTTP & Volley
        btn_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Testing", "Volley!");
                Intent goVolley = new Intent(getApplicationContext(), VolleyActivity.class);
                startActivity(goVolley);
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

    //Get JSON from API
    private class WeatherDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            Log.d("Testing", "go get data!");

            try {
                //Format HTTP request
                con = (HttpURLConnection) (new URL(urls[0])).openConnection();
                con.setRequestMethod("GET");
                con.setDoInput(true);

                //Connect
                con.connect();
                Log.d("Testing", "Connect!");

                //Read data
                StringBuffer buffer = new StringBuffer();
                is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ((line = br.readLine()) != null) {
                    buffer.append(line + "rn");
                    Log.d("Testing", "Reading!" + line);
                }

                //close connection
                is.close();
                con.disconnect();

                Log.d("Testing", "got the data! " + buffer.toString());

                //Return the response
                return buffer.toString();

            } catch (Exception e) {
                Log.d("Testing", "Something went wrong");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null) {
                //Set response in textView
                responseText.setText(s);
            }
        }
    }
}
