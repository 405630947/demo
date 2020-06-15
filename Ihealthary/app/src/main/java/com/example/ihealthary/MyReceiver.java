package com.example.ihealthary;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import java.text.DecimalFormat;

import android.os.Bundle;
public class MyReceiver extends BroadcastReceiver {
    public ImportDB importDB;
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String td=sharedPreferences.getString("todaytotal","0");
        String weight=sharedPreferences.getString("weight","0");
        DecimalFormat df = new DecimalFormat("##.0");
        double changeweight= Double.parseDouble(df.format((Double.parseDouble(weight)-(Double.parseDouble(td)/7700))));
        sharedPreferences.edit().putString("weight" , String.valueOf(changeweight)).apply();
        importDB = new ImportDB(context);
        importDB.openDatabase();
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(ImportDB.DB_PATH + "/" + ImportDB.DB_NAME, null);
        ContentValues cv = new ContentValues();
        cv.put("每日卡路里",(int)Double.parseDouble(td));
        db.insert("使用者資料",null, cv);
        db.close();
        importDB.closeDatabase();
        Editor editor = sharedPreferences.edit();
        editor.remove("sportstotal");
        editor.remove("sportslist");
        editor.remove("foodtotal");
        editor.remove("foodlist");
        editor.commit();
    }
}