package com.star.demo2017110601;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    String DB_FILE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB_FILE = getFilesDir() + File.separator + "mydata.sqlite";
        try {
            InputStream is = getResources().openRawResource(R.raw.mydata);
//            OutputStream os = new FileOutputStream(getFilesDir() + File.separator + "mydata.sqlite");
                OutputStream os = new FileOutputStream(DB_FILE);
                int read;
                while ((read = is.read()) != -1)
                {
                    os.write(read);
                }
                os.close();
                is.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    public void click1(View v)
    {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_FILE, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor c = db.rawQuery("Select * from phone", null);
        if (c.moveToFirst())
        {
            do {
                Log.d("DATA", c.getString(1) + "," + c.getString(2));
            } while (c.moveToNext());
        }
    }
}
