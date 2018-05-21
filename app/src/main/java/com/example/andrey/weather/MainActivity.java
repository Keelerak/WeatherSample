package com.example.andrey.weather;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private Button btnT;
    String x =null;

    Intent intent;


   // String city = ( ",RU&appid=17d96931d2482c0f29b544ef190e58f9");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        tv7 = (TextView) findViewById(R.id.tv7);
       // tv7.setVisibility(View.INVISIBLE);
        btnT = (Button) findViewById(R.id.btnSaveTemperature);
        btnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick();
            }
        });


        Log.i(TAG, getCityName() + "    ПОЛУЧИЛ ГОРОД   " + getCityCountry() + "        " );

        String city = ( getCityName()+"," + getCityCountry());
        String string = city;

        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(new String[]{string });

    }
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == (KeyEvent.KEYCODE_BACK)){
            onButtonClick();
        }
        return true;
    }
    private void onButtonClick(){
        Intent intentI = new Intent();
        intentI.putExtra(Constants.FIRST_NAME, getCityName());
        intentI.putExtra(Constants.COUNTRY_NAME, getCityCountry());
        intentI.putExtra(Constants.TEMPERATURE,  x);//tv7.getText().toString());
        Log.i(TAG, "+ " );
        setResult(RESULT_OK, intentI);
        finish();
    }
    private String getCityName() {
        intent = getIntent();
        String cityName = intent.getStringExtra("cityName");
        return cityName;

    }
    private String getCityCountry(){
        intent = getIntent();
        String cityCountry = intent.getStringExtra("countryName");
        return cityCountry;
    }

    protected void onResume(){
        super.onResume();
       // invalidateOptionsMenu();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_main, menu);

        menu.findItem(R.id.action_settings).setVisible(true);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = ( (new WeatherHttpClient()).getWeatherData(params[0]));

            try {
                weather = WeatherParserJSON.getWeather(data);



            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;

        }
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            tv1.setText("Город "+weather.location.getCity() + " , " + weather.location.getCountry());
            tv3.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
            tv2.setText("Температура        " + Math.round((weather.temperature.getTemp() - 273.15)) + " °C");
            tv4.setText("Влажность        " + weather.currentCondition.getHumidity() + "%");
            tv5.setText("Давление "+"       " + weather.currentCondition.getPressure() + " hPa");
            tv6.setText("Скорость ветра       " + weather.wind.getSpeed() + " m/s");
            //tv7.setText(String.valueOf( Math.round((weather.temperature.getTemp() - 273.15))));
            long timeInMillis = weather.location.getSunrise();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeInMillis);
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
            tv7.setText("Закат " /*+ dateFormat.format(weather.location.getSunrise())*/);
            x = String.valueOf(( Math.round((weather.temperature.getTemp() - 273.15))));


        }
    }
}
