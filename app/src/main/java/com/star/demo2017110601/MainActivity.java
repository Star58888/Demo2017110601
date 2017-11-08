package com.star.demo2017110601;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<Phone> mylist;
    ArrayList<String> showList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listView);
        mylist = new ArrayList<>();
        showList = new ArrayList<>();
        DBInfo.DB_FILE = getFilesDir() + File.separator + "mydata.sqlite";
        copyDBFile();


        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, showList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(MainActivity.this, DetailActivity.class);
                it.putExtra("id", mylist.get(position).id);
                startActivity(it);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mylist.clear();
        showList.clear();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DBInfo.DB_FILE, null, SQLiteDatabase.OPEN_READWRITE);
//        Cursor c = db.rawQuery("Select * from phone", null);       // studio的寫法
        //SQLite 的寫法
        Cursor c = db.query("phone", new String[] {"id", "username", "tel"}, null,null,null,null,null);
        if (c.moveToFirst())
        {
            do {

                mylist.add(new Phone(c.getInt(0), c.getString(1), c.getString(2)));
                showList.add(c.getString(1));
            } while (c.moveToNext());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add)
        {
            Intent it = new Intent(MainActivity.this, AddActivity.class);
            startActivity(it);
        }
        return super.onOptionsItemSelected(item);
    }

    public void copyDBFile()
    {
        try {
            File f = new File(DBInfo.DB_FILE);
            if (! f.exists())
            {
                InputStream is = getResources().openRawResource(R.raw.mydata);
                OutputStream os = new FileOutputStream(DBInfo.DB_FILE);
                int read;
                while ((read = is.read()) != -1)
                {
                    os.write(read);
                }
                os.close();
                is.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
