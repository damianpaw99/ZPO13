package edu.ib.zpo13;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_stats);

        database=openOrCreateDatabase("COVID",MODE_PRIVATE,null);
        
        TextView textView=(TextView) findViewById(R.id.txtStats);
        try{
            StringBuilder stats = new StringBuilder();

            ArrayList<String> data = new ArrayList<>();
            String sqlCount = "SELECT sum(Cases) FROM COUNTRIES";
            Cursor cursor = database.rawQuery(sqlCount, null);
            cursor.moveToFirst();
            int cases = cursor.getInt(0);
            cursor.close();

            sqlCount = "SELECT Name FROM COUNTRIES ORDER BY (Cases-Active)/Cases DESC LIMIT 1";
            cursor = database.rawQuery(sqlCount, null);
            cursor.moveToFirst();
            String max = cursor.getString(0);
            cursor.close();

            sqlCount = "SELECT Name FROM COUNTRIES ORDER BY testsPerOneMillion DESC";
            cursor = database.rawQuery(sqlCount, null);
            cursor.moveToFirst();
            StringBuilder tests = new StringBuilder();
            while (!cursor.isAfterLast()) {
                data.add(cursor.getString(cursor.getColumnIndex("Name")));
                cursor.moveToNext();
            }
            cursor.close();
            for (int i = 0; i < data.size(); i++) {
                tests.append(data.get(i)).append(", ");
            }
            stats.append(getString(R.string.allCases));
            stats.append(cases);
            stats.append("\n");
            stats.append(getString(R.string.healPercent));
            stats.append(max);
            stats.append("\n");
            stats.append(getString(R.string.testPerMillion));
            stats.append(tests.toString());
            textView.setText(stats.toString().substring(0, stats.length() - 2));
        } catch (Exception e){
            Log.e(MainActivity.TAG,e.toString());
            textView.setText(getString(R.string.noRecords));
        }

    }
}