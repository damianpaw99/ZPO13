package edu.ib.zpo13;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddCountryActivity extends AppCompatActivity {

    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_country);

        database=openOrCreateDatabase("COVID",MODE_PRIVATE,null);
    }

    public void addCountry(View view) {
        TextView textView=(TextView) findViewById(R.id.etxtCountry);
        String urlString = "https://coronavirus-19-api.herokuapp.com/countries/"+textView.getText().toString();
        StringBuilder response = new StringBuilder();

        Thread thread=new Thread(() -> {
            try {
                URL url  =new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                System.out.println("Response "+ responseCode);
                BufferedReader in =new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine())!=null) response.append(inputLine);
                in.close();

            } catch (IOException e) {
                Log.e(MainActivity.TAG,e.toString());
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e(MainActivity.TAG,e.toString());;
        }

        try {
            Gson gson = new Gson();
            COVIDData covidData = gson.fromJson(response.toString(), COVIDData.class);

            String sqlStudent = "INSERT INTO COUNTRIES VALUES (?,?,?,?,?)";
            SQLiteStatement statement = database.compileStatement(sqlStudent);
            statement.bindString(1, covidData.getCountry());
            statement.bindLong(2, covidData.getCases());
            statement.bindLong(3, covidData.getActive());
            statement.bindLong(4, covidData.getCasesPerOneMillion());
            statement.bindLong(5, covidData.getTestsPerOneMillion());
            statement.executeInsert();
            Toast.makeText(this, "Added new country", Toast.LENGTH_LONG).show();
        }catch(SQLiteConstraintException e){
            Log.e(MainActivity.TAG,e.toString());
            Toast.makeText(this,"Country already exists in database",Toast.LENGTH_LONG).show();
        } catch (Exception e){
            Log.e(MainActivity.TAG,e.toString());
            Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show();
        }
    }
}