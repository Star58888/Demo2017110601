package com.star.demo2017110601;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    TextView tvId,tvName,tvTel;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvId = findViewById(R.id.textView);
        tvName = findViewById(R.id.textView2);
        tvTel = findViewById(R.id.textView3);
        Intent intent =  getIntent();
        id = intent.getIntExtra("id" , -1);
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DBInfo.DB_FILE, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor c = db.query("phone" , new String[]{"id" , "username" , "tel"},"id=?",new String[] {String.valueOf(id)} ,null , null , null);
        if (c.moveToFirst())
        {
            tvId.setText(String.valueOf(c.getInt(0)));
            tvName.setText(c.getString(1));
            tvTel.setText(c.getString(2));
        }
    }
}
