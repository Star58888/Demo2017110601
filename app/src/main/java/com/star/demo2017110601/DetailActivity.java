package com.star.demo2017110601;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    TextView tvId,tvName,tvTel;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvId = (TextView) findViewById(R.id.textView);
        tvName = (TextView) findViewById(R.id.textView2);
        tvTel = (TextView) findViewById(R.id.textView3);
        Intent it = getIntent();
        id = it.getIntExtra("id", -1);
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DBInfo.DB_FILE, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor c = db.query("phone", new String[] {"id", "username", "tel"}, "id=?", new String[] {String.valueOf(id)}, null, null, null);
        if (c.moveToFirst())
        {
            tvId.setText(String.valueOf(c.getInt(0)));
            tvName.setText(c.getString(1));
            tvTel.setText(c.getString(2));
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DBInfo.DB_FILE, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor c = db.query("phone", new String[] {"id", "username", "tel"}, "id=?", new String[] {String.valueOf(id)}, null, null, null);
        if (c.moveToFirst())
        {
            tvId.setText(String.valueOf(c.getInt(0)));
            tvName.setText(c.getString(1));
            tvTel.setText(c.getString(2));
        }
    }

    public void clickBack(View v)
    {
        finish();
    }
    public void clickDelete(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setTitle("刪除確認");
        builder.setMessage("請確認刪除");
        builder.setPositiveButton("刪除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = SQLiteDatabase.openDatabase(DBInfo.DB_FILE, null, SQLiteDatabase.OPEN_READWRITE);
                db.delete("phone", "id=?", new String[] {String.valueOf(id)});
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }
    public void clickEdit(View v) {
        Intent intent = new Intent(this , EditActivity.class);
        Log.d("DATA", "send out id:" + id);
        intent.putExtra("id" , id);
        startActivity(intent);
    }

}
