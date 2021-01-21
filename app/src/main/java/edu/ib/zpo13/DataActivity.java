package edu.ib.zpo13;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class DataActivity extends AppCompatActivity {
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_data);

        database=openOrCreateDatabase("COVID",MODE_PRIVATE,null);

        ListView listView = (ListView) findViewById(R.id.lvdata);
        ArrayList<String> list = new ArrayList<String>();

        String sqlData="SELECT * FROM COUNTRIES";
        Cursor cursor=database.rawQuery(sqlData,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            StringBuilder row=new StringBuilder();
            row.append("Country: ");
            row.append(cursor.getString(0)).append(", ");
            row.append("C/A: ");
            row.append(cursor.getInt(1)).append("/");
            row.append(cursor.getInt(2)).append(",\n");
            row.append("cPerOneM: ");
            row.append(cursor.getInt(3)).append(", ");
            row.append("tPerOneM: ");
            row.append(cursor.getInt(4));
            list.add(row.toString());
            cursor.moveToNext();
        }
        cursor.close();
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }
}