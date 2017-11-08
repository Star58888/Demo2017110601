package com.star.demo2017110601;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    EditText ed3 , ed4;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ed3 = findViewById(R.id.editText3);
        ed4 = findViewById(R.id.editText4);
        Intent intent = getIntent();
        id = intent.getIntExtra("id" , -1);
        Log.d("DATA", "id:" + id);

        SQLiteDatabase db = SQLiteDatabase.openDatabase(DBInfo.DB_FILE, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor c = db.query("phone", new String[] {"id", "username", "tel"}, "id=?", new String[] {String.valueOf(id)}, null, null, null);
        if (c.moveToFirst())
        {
            ed3.setText(c.getString(1));
            ed4.setText(c.getString(2));
        }
    }
}
