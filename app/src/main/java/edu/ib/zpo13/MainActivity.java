package edu.ib.zpo13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends AppCompatActivity {
    public static final String TAG="EDUIB";
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        database = openOrCreateDatabase("COVID",MODE_PRIVATE,null);
        //String drop = "DROP TABLE IF EXISTS COUNTRIES";
        //database.execSQL(drop);
        String sqlDB="CREATE TABLE IF NOT EXISTS COUNTRIES (Name VARCHAR primary key, Cases INTEGER, Active INTEGER, CasesPerOneMillion INTEGER, TestsPerOneMillion INTEGER)";
        database.execSQL(sqlDB);
    }

    public void showStats(View view) {
        Intent intent=new Intent(this, StatsActivity.class);
        startActivity(intent);
    }

    public void showData(View view) {
        Intent intent=new Intent(this, DataActivity.class);
        startActivity(intent);
    }

    public void addCountry(View view) {
        Intent intent=new Intent(this, AddCountryActivity.class);
        startActivity(intent);

    }
}