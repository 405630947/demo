package com.example.ihealthary;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.SharedPreferences;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SeasonActivity extends AppCompatActivity {
    public ImportDB importDB;
    String dbtotal = " ";
    static String[] arraytotal;
    static double changeweight0, changeweight1, changeweight2, changeweight3, changeweight4;
    double doubletotal0 = 0, doubletotal1 = 0, doubletotal2 = 0, doubletotal3 = 0, doubletotal4 = 0;
    float fresult, fresult2, fresult3, fresult4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String w = sharedPreferences.getString("unchangeweight", "0");
        DecimalFormat df = new DecimalFormat("##.0");
        String h = sharedPreferences.getString("height", "0");
        String s = sharedPreferences.getString("sex", "0");
        float fh = Float.parseFloat(h);
        fh = fh / 100;
        fh = fh * fh;
        TextView month = (TextView) findViewById(R.id.textView33);
        TextView month1 = (TextView) findViewById(R.id.textView34);
        TextView month2 = (TextView) findViewById(R.id.textView35);
        TextView month3 = (TextView) findViewById(R.id.textView36);
        float fw = Float.parseFloat(w);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        fresult = fw / fh;
        String first = "最初體重:" + w + "公斤" + "\n" + "BMI:" + nf.format(fw/ fh);
        month.setText(first);
        importDB = new ImportDB(this);
        importDB.openDatabase();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(ImportDB.DB_PATH + "/" + ImportDB.DB_NAME, null);
        Cursor cursor = db.rawQuery("select * from 使用者資料", null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            dbtotal += cursor.getString(cursor.getColumnIndex("每日卡路里")) + ",";
        }
        arraytotal = dbtotal.split(",");
        if (cursor.getCount() < 120) {
            if (cursor.getCount() >= 30) {
                for (int i = 0; i <= 29; i++) {
                    doubletotal1 += Double.parseDouble(arraytotal[i]);
                }
                changeweight1 = Double.parseDouble(df.format((Double.parseDouble(w) - (doubletotal1 / 7700))));
                float fw2 = (float) changeweight1;
                fresult2 = fw2 / fh;
                String month1text = "原本體重:" + w + "公斤" + "\n" + "最終體重:" + changeweight1 + "公斤" + "\n" + "本月卡路里變化量:"+ "\n" + (0-(int)doubletotal1)+ "大卡" + "\n" + "最終BMI:" + nf.format(fresult2);
                month1.setText(month1text);
            }
            if (cursor.getCount() >= 60) {
                for (int i = 30; i <= 59; i++) {
                    doubletotal2 += Double.parseDouble(arraytotal[i]);
                }
                changeweight2 = Double.parseDouble(df.format((Double.parseDouble(w) - ((doubletotal1 + doubletotal2) / 7700))));
                float fw3 = (float) changeweight1;
                fresult3 = fw3 / fh;
                String month2text = "上月體重:" + changeweight1 + "公斤" + "\n" + "最終體重:" + changeweight2 + "公斤" + "\n" + "本月卡路里變化量:"+ "\n" + (0-(int)doubletotal2) + "大卡" + "\n" + "最終BMI:" + nf.format(fresult3);
                month2.setText(month2text);
            }
            if (cursor.getCount() >= 90) {
                for (int i = 60; i <= 89; i++) {
                    doubletotal3 += Double.parseDouble(arraytotal[i]);
                }
                changeweight3 = Double.parseDouble(df.format((Double.parseDouble(w) - ((doubletotal1 + doubletotal2 + doubletotal3) / 7700))));
                float fw4 = (float) changeweight1;
                fresult4= fw4 / fh;
                String month3text = "上月體重" + changeweight2 + "公斤" + "\n" + "最終體重:" + changeweight2 + "公斤" + "\n" + "本月卡路里變化量:"+ "\n" + (0-(int)doubletotal3)+ "大卡" + "\n" + "最終BMI:" + nf.format(fresult4);
                month3.setText(month3text);
            }
        }
        if (cursor.getCount() >= 120) {
            int cnt = cursor.getCount();
            int latest = cnt - cnt % 30;
            int oldest = latest - 90;
            double[] arr = new double[3];
            for (int i = oldest; i < latest; i++) {
                int index = (i - oldest) / 30;
                arr[index] += Double.parseDouble(arraytotal[i]);
            }
            for (int a = 0; a<oldest; a++){
                doubletotal0 += Double.parseDouble(arraytotal[a]);
            }
            changeweight0 = Double.parseDouble(df.format((Double.parseDouble(w) - (doubletotal0 / 7700))));
            changeweight1 = Double.parseDouble(df.format((Double.parseDouble(w) - ((doubletotal0 + arr[0]) / 7700))));
            changeweight2 = Double.parseDouble(df.format((Double.parseDouble(w) - ((doubletotal0 + arr[0] + arr[1])/ 7700))));
            changeweight3 = Double.parseDouble(df.format((Double.parseDouble(w) - ((doubletotal0 + arr[0] + arr[1] + arr[2]) / 7700))));
            float fw2 = (float) changeweight1,fw3 = (float) changeweight2,fw4 = (float) changeweight3;
            fresult2 = fw2 / fh;
            fresult3 = fw3 / fh;
            fresult4 = fw4 / fh;
            String month1text="上月體重:" + changeweight0 + "公斤" + "\n" + "最終體重:" + changeweight1 + "公斤" + "\n" + "本月卡路里變化量:"+ "\n" + (0-(int)arr[0]) + "大卡"+ "\n" + "最終BMI:" + nf.format(fresult2);
            String month2text="上月體重:" + changeweight1 + "公斤" + "\n" + "最終體重:" + changeweight2 + "公斤" + "\n" + "本月卡路里變化量:"+ "\n" + (0-(int)arr[1]) + "大卡"+ "\n" + "最終BMI:" + nf.format(fresult3);
            String month3text="上月體重:" + changeweight2 + "公斤" + "\n" + "最終體重:" + changeweight3 + "公斤" + "\n" + "本月卡路里變化量:"+ "\n" + (0-(int)arr[2]) + "大卡"+ "\n" + "最終BMI:" + nf.format(fresult4);
            month1.setText(month1text);
            month2.setText(month2text);
            month3.setText(month3text);
        }
        ImageView iv = (ImageView)findViewById(R.id.imageView12);
        if("男".equals(s)) {
            if (fresult <= 15) {
                iv.setImageResource(R.drawable.bmi15);
            } else if (15 < fresult && fresult <= 15.5) {
                iv.setImageResource(R.drawable.bmi15a);
            } else if (15.5 < fresult && fresult <= 16) {
                iv.setImageResource(R.drawable.bmi16);
            } else if (16 < fresult && fresult <= 16.5) {
                iv.setImageResource(R.drawable.bmi16a);
            }  else if (16.5 < fresult && fresult <= 17) {
                iv.setImageResource(R.drawable.bmi17);
            } else if (17 < fresult && fresult <= 17.5) {
                iv.setImageResource(R.drawable.bmi17a);
            } else if (17.5 < fresult && fresult <= 18) {
                iv.setImageResource(R.drawable.bmi18);
            } else if (18 < fresult && fresult <= 19) {
                iv.setImageResource(R.drawable.bmi19);
            } else if (19 < fresult && fresult <= 20) {
                iv.setImageResource(R.drawable.bmi20);
            } else if (20 < fresult && fresult <= 21) {
                iv.setImageResource(R.drawable.bmi21);
            } else if (21 < fresult && fresult <= 22) {
                iv.setImageResource(R.drawable.bmi22);
            } else if (22 < fresult && fresult <= 23) {
                iv.setImageResource(R.drawable.bmi23);
            } else if (23 < fresult && fresult <= 24) {
                iv.setImageResource(R.drawable.bmi24);
            } else if (24 < fresult && fresult <= 24.5) {
                iv.setImageResource(R.drawable.bmi24a);
            } else if (24.5 < fresult && fresult <= 26) {
                iv.setImageResource(R.drawable.bmi26);
            } else if (26 < fresult && fresult <= 27.5) {
                iv.setImageResource(R.drawable.bmi27a);
            } else if (27.5 < fresult && fresult <= 29) {
                iv.setImageResource(R.drawable.bmi29);
            } else if (29 < fresult && fresult <= 30.5) {
                iv.setImageResource(R.drawable.bmi30a);
            } else if (30.5 < fresult && fresult <= 32) {
                iv.setImageResource(R.drawable.bmi32);
            } else if (32 < fresult && fresult <= 33.5) {
                iv.setImageResource(R.drawable.bmi33a);
            } else if (33.5 < fresult && fresult <= 35) {
                iv.setImageResource(R.drawable.bmi35);
            } else if (fresult > 35) {
                iv.setImageResource(R.drawable.bmi35);
            }
        }
        else{
            if (fresult <= 17) {
                iv.setImageResource(R.drawable.bmi17f);
            } else if (17 < fresult && fresult <= 18) {
                iv.setImageResource(R.drawable.bmi18f);
            } else if (18 < fresult && fresult <= 19) {
                iv.setImageResource(R.drawable.bmi19f);
            } else if (19 < fresult && fresult <= 20) {
                iv.setImageResource(R.drawable.bmi20f);
            } else if (20< fresult && fresult <= 21) {
                iv.setImageResource(R.drawable.bmi21f);
            } else if (21 < fresult && fresult <= 22) {
                iv.setImageResource(R.drawable.bmi22f);
            } else if (22 < fresult && fresult <= 23) {
                iv.setImageResource(R.drawable.bmi23f);
            } else if (23 < fresult && fresult <= 24) {
                iv.setImageResource(R.drawable.bmi24f);
            } else if (24 < fresult && fresult <= 25) {
                iv.setImageResource(R.drawable.bmi25f);
            } else if (25 < fresult && fresult <= 26) {
                iv.setImageResource(R.drawable.bmi26f);
            } else if (26 < fresult && fresult <= 27) {
                iv.setImageResource(R.drawable.bmi27f);
            } else if (27 < fresult && fresult <= 28) {
                iv.setImageResource(R.drawable.bmi28f);
            } else if (28 < fresult && fresult <= 29) {
                iv.setImageResource(R.drawable.bmi29f);
            } else if (29 < fresult && fresult <= 30) {
                iv.setImageResource(R.drawable.bmi30f);
            } else if (30 < fresult && fresult <= 31) {
                iv.setImageResource(R.drawable.bmi31f);
            } else if (31< fresult && fresult <= 32) {
                iv.setImageResource(R.drawable.bmi32f);
            } else if (32 < fresult && fresult <= 33) {
                iv.setImageResource(R.drawable.bmi33f);
            } else if (33 < fresult && fresult <= 34) {
                iv.setImageResource(R.drawable.bmi34f);
            } else if (34 < fresult && fresult <= 35) {
                iv.setImageResource(R.drawable.bmi35f);
            } else if (35 < fresult && fresult <= 36) {
                iv.setImageResource(R.drawable.bmi36f);
            } else if (36 < fresult && fresult <= 37) {
                iv.setImageResource(R.drawable.bmi37f);
            } else if (fresult>37) {
                iv.setImageResource(R.drawable.bmi37f);
            }
        }
        if(cursor.getCount()>=30) {
            ImageView iv2 = (ImageView) findViewById(R.id.imageView13);
            if ("男".equals(s)) {
                if (fresult2 <= 15) {
                    iv2.setImageResource(R.drawable.bmi15);
                } else if (15 < fresult2 && fresult2 <= 15.5) {
                    iv2.setImageResource(R.drawable.bmi15a);
                } else if (15.5 < fresult2 && fresult2 <= 16) {
                    iv2.setImageResource(R.drawable.bmi16);
                } else if (16 < fresult2 && fresult2 <= 16.5) {
                    iv2.setImageResource(R.drawable.bmi16a);
                } else if (16.5 < fresult2 && fresult2 <= 17) {
                    iv2.setImageResource(R.drawable.bmi17);
                } else if (17 < fresult2 && fresult2 <= 17.5) {
                    iv2.setImageResource(R.drawable.bmi17a);
                } else if (17.5 < fresult2 && fresult2 <= 18) {
                    iv2.setImageResource(R.drawable.bmi18);
                } else if (18 < fresult2 && fresult2 <= 19) {
                    iv2.setImageResource(R.drawable.bmi19);
                } else if (19 < fresult2 && fresult2 <= 20) {
                    iv2.setImageResource(R.drawable.bmi20);
                } else if (20 < fresult2 && fresult2 <= 21) {
                    iv2.setImageResource(R.drawable.bmi21);
                } else if (21 < fresult2 && fresult2 <= 22) {
                    iv2.setImageResource(R.drawable.bmi22);
                } else if (22 < fresult2 && fresult2 <= 23) {
                    iv2.setImageResource(R.drawable.bmi23);
                } else if (23 < fresult2 && fresult2 <= 24) {
                    iv2.setImageResource(R.drawable.bmi24);
                } else if (24 < fresult2 && fresult2 <= 24.5) {
                    iv2.setImageResource(R.drawable.bmi24a);
                } else if (24.5 < fresult2 && fresult2 <= 26) {
                    iv2.setImageResource(R.drawable.bmi26);
                } else if (26 < fresult2 && fresult2 <= 27.5) {
                    iv2.setImageResource(R.drawable.bmi27a);
                } else if (27.5 < fresult2 && fresult2 <= 29) {
                    iv2.setImageResource(R.drawable.bmi29);
                } else if (29 < fresult2 && fresult2 <= 30.5) {
                    iv2.setImageResource(R.drawable.bmi30a);
                } else if (30.5 < fresult2 && fresult2 <= 32) {
                    iv2.setImageResource(R.drawable.bmi32);
                } else if (32 < fresult2 && fresult2 <= 33.5) {
                    iv2.setImageResource(R.drawable.bmi33a);
                } else if (33.5 < fresult2 && fresult2 <= 35) {
                    iv2.setImageResource(R.drawable.bmi35);
                } else if (fresult2 > 35) {
                    iv2.setImageResource(R.drawable.bmi35);
                }
            } else {
                if (fresult2 <= 17) {
                    iv2.setImageResource(R.drawable.bmi17f);
                } else if (17 < fresult2 && fresult2 <= 18) {
                    iv2.setImageResource(R.drawable.bmi18f);
                } else if (18 < fresult2 && fresult2 <= 19) {
                    iv2.setImageResource(R.drawable.bmi19f);
                } else if (19 < fresult2 && fresult2 <= 20) {
                    iv2.setImageResource(R.drawable.bmi20f);
                } else if (20 < fresult2 && fresult2 <= 21) {
                    iv2.setImageResource(R.drawable.bmi21f);
                } else if (21 < fresult2 && fresult2 <= 22) {
                    iv2.setImageResource(R.drawable.bmi22f);
                } else if (22 < fresult2 && fresult2 <= 23) {
                    iv2.setImageResource(R.drawable.bmi23f);
                } else if (23 < fresult2 && fresult2 <= 24) {
                    iv2.setImageResource(R.drawable.bmi24f);
                } else if (24 < fresult2 && fresult2 <= 25) {
                    iv2.setImageResource(R.drawable.bmi25f);
                } else if (25 < fresult2 && fresult2 <= 26) {
                    iv2.setImageResource(R.drawable.bmi26f);
                } else if (26 < fresult2 && fresult2 <= 27) {
                    iv2.setImageResource(R.drawable.bmi27f);
                } else if (27 < fresult2 && fresult2 <= 28) {
                    iv2.setImageResource(R.drawable.bmi28f);
                } else if (28 < fresult2 && fresult2 <= 29) {
                    iv2.setImageResource(R.drawable.bmi29f);
                } else if (29 < fresult2 && fresult2 <= 30) {
                    iv2.setImageResource(R.drawable.bmi30f);
                } else if (30 < fresult2 && fresult2 <= 31) {
                    iv2.setImageResource(R.drawable.bmi31f);
                } else if (31 < fresult2 && fresult2 <= 32) {
                    iv2.setImageResource(R.drawable.bmi32f);
                } else if (32 < fresult2 && fresult2 <= 33) {
                    iv2.setImageResource(R.drawable.bmi33f);
                } else if (33 < fresult2 && fresult2 <= 34) {
                    iv2.setImageResource(R.drawable.bmi34f);
                } else if (34 < fresult2 && fresult2 <= 35) {
                    iv2.setImageResource(R.drawable.bmi35f);
                } else if (35 < fresult2 && fresult2 <= 36) {
                    iv2.setImageResource(R.drawable.bmi36f);
                } else if (36 < fresult2 && fresult2 <= 37) {
                    iv2.setImageResource(R.drawable.bmi37f);
                } else if (fresult2 > 37) {
                    iv2.setImageResource(R.drawable.bmi37f);
                }
            }
        }
        if(cursor.getCount()>=60) {
            ImageView iv3 = (ImageView) findViewById(R.id.imageView14);
            if ("男".equals(s)) {
                if (fresult3 <= 15) {
                    iv3.setImageResource(R.drawable.bmi15);
                } else if (15 < fresult3 && fresult3 <= 15.5) {
                    iv3.setImageResource(R.drawable.bmi15a);
                } else if (15.5 < fresult3 && fresult3 <= 16) {
                    iv3.setImageResource(R.drawable.bmi16);
                } else if (16 < fresult3 && fresult3 <= 16.5) {
                    iv3.setImageResource(R.drawable.bmi16a);
                } else if (16.5 < fresult3 && fresult3 <= 17) {
                    iv3.setImageResource(R.drawable.bmi17);
                } else if (17 < fresult3 && fresult3 <= 17.5) {
                    iv3.setImageResource(R.drawable.bmi17a);
                } else if (17.5 < fresult3 && fresult3 <= 18) {
                    iv3.setImageResource(R.drawable.bmi18);
                } else if (18 < fresult3 && fresult3 <= 19) {
                    iv3.setImageResource(R.drawable.bmi19);
                } else if (19 < fresult3 && fresult3 <= 20) {
                    iv3.setImageResource(R.drawable.bmi20);
                } else if (20 < fresult3 && fresult3 <= 21) {
                    iv3.setImageResource(R.drawable.bmi21);
                } else if (21 < fresult3 && fresult3 <= 22) {
                    iv3.setImageResource(R.drawable.bmi22);
                } else if (22 < fresult3 && fresult3 <= 23) {
                    iv3.setImageResource(R.drawable.bmi23);
                } else if (23 < fresult3 && fresult3 <= 24) {
                    iv3.setImageResource(R.drawable.bmi24);
                } else if (24 < fresult3 && fresult3 <= 24.5) {
                    iv3.setImageResource(R.drawable.bmi24a);
                } else if (24.5 < fresult3 && fresult3 <= 26) {
                    iv3.setImageResource(R.drawable.bmi26);
                } else if (26 < fresult3 && fresult3 <= 27.5) {
                    iv3.setImageResource(R.drawable.bmi27a);
                } else if (27.5 < fresult3 && fresult3 <= 29) {
                    iv3.setImageResource(R.drawable.bmi29);
                } else if (29 < fresult3 && fresult3 <= 30.5) {
                    iv3.setImageResource(R.drawable.bmi30a);
                } else if (30.5 < fresult3 && fresult3 <= 32) {
                    iv3.setImageResource(R.drawable.bmi32);
                } else if (32 < fresult3 && fresult3 <= 33.5) {
                    iv3.setImageResource(R.drawable.bmi33a);
                } else if (33.5 < fresult3 && fresult3 <= 35) {
                    iv3.setImageResource(R.drawable.bmi35);
                } else if (fresult3 > 35) {
                    iv3.setImageResource(R.drawable.bmi35);
                }
            } else {
                if (fresult3 <= 17) {
                    iv3.setImageResource(R.drawable.bmi17f);
                } else if (17 < fresult3 && fresult3 <= 18) {
                    iv3.setImageResource(R.drawable.bmi18f);
                } else if (18 < fresult3 && fresult3 <= 19) {
                    iv3.setImageResource(R.drawable.bmi19f);
                } else if (19 < fresult3 && fresult3 <= 20) {
                    iv3.setImageResource(R.drawable.bmi20f);
                } else if (20 < fresult3 && fresult3 <= 21) {
                    iv3.setImageResource(R.drawable.bmi21f);
                } else if (21 < fresult3 && fresult3 <= 22) {
                    iv3.setImageResource(R.drawable.bmi22f);
                } else if (22 < fresult3 && fresult3 <= 23) {
                    iv3.setImageResource(R.drawable.bmi23f);
                } else if (23 < fresult3 && fresult3 <= 24) {
                    iv3.setImageResource(R.drawable.bmi24f);
                } else if (24 < fresult3 && fresult3 <= 25) {
                    iv3.setImageResource(R.drawable.bmi25f);
                } else if (25 < fresult3 && fresult3 <= 26) {
                    iv3.setImageResource(R.drawable.bmi26f);
                } else if (26 < fresult3 && fresult3 <= 27) {
                    iv3.setImageResource(R.drawable.bmi27f);
                } else if (27 < fresult3 && fresult3 <= 28) {
                    iv3.setImageResource(R.drawable.bmi28f);
                } else if (28 < fresult3 && fresult3 <= 29) {
                    iv3.setImageResource(R.drawable.bmi29f);
                } else if (29 < fresult3 && fresult3 <= 30) {
                    iv3.setImageResource(R.drawable.bmi30f);
                } else if (30 < fresult3 && fresult3 <= 31) {
                    iv3.setImageResource(R.drawable.bmi31f);
                } else if (31 < fresult3 && fresult3 <= 32) {
                    iv3.setImageResource(R.drawable.bmi32f);
                } else if (32 < fresult3 && fresult3 <= 33) {
                    iv3.setImageResource(R.drawable.bmi33f);
                } else if (33 < fresult3 && fresult3 <= 34) {
                    iv3.setImageResource(R.drawable.bmi34f);
                } else if (34 < fresult3 && fresult3 <= 35) {
                    iv3.setImageResource(R.drawable.bmi35f);
                } else if (35 < fresult3 && fresult3 <= 36) {
                    iv3.setImageResource(R.drawable.bmi36f);
                } else if (36 < fresult3 && fresult3 <= 37) {
                    iv3.setImageResource(R.drawable.bmi37f);
                } else if (fresult3 > 37) {
                    iv3.setImageResource(R.drawable.bmi37f);
                }
            }
        }
        if(cursor.getCount()>=90) {
            ImageView iv4 = (ImageView) findViewById(R.id.imageView15);
            if ("男".equals(s)) {
                if (fresult4 <= 15) {
                    iv4.setImageResource(R.drawable.bmi15);
                } else if (15 < fresult4 && fresult4 <= 15.5) {
                    iv4.setImageResource(R.drawable.bmi15a);
                } else if (15.5 < fresult4 && fresult4 <= 16) {
                    iv4.setImageResource(R.drawable.bmi16);
                } else if (16 < fresult4 && fresult4 <= 16.5) {
                    iv4.setImageResource(R.drawable.bmi16a);
                } else if (16.5 < fresult4 && fresult4 <= 17) {
                    iv4.setImageResource(R.drawable.bmi17);
                } else if (17 < fresult4 && fresult4 <= 17.5) {
                    iv4.setImageResource(R.drawable.bmi17a);
                } else if (17.5 < fresult4 && fresult4 <= 18) {
                    iv4.setImageResource(R.drawable.bmi18);
                } else if (18 < fresult4 && fresult4 <= 19) {
                    iv4.setImageResource(R.drawable.bmi19);
                } else if (19 < fresult4 && fresult4 <= 20) {
                    iv4.setImageResource(R.drawable.bmi20);
                } else if (20 < fresult4 && fresult4 <= 21) {
                    iv4.setImageResource(R.drawable.bmi21);
                } else if (21 < fresult4 && fresult4 <= 22) {
                    iv4.setImageResource(R.drawable.bmi22);
                } else if (22 < fresult4 && fresult4 <= 23) {
                    iv4.setImageResource(R.drawable.bmi23);
                } else if (23 < fresult4 && fresult4 <= 24) {
                    iv4.setImageResource(R.drawable.bmi24);
                } else if (24 < fresult4 && fresult4 <= 24.5) {
                    iv4.setImageResource(R.drawable.bmi24a);
                } else if (24.5 < fresult4 && fresult4 <= 26) {
                    iv4.setImageResource(R.drawable.bmi26);
                } else if (26 < fresult4 && fresult4 <= 27.5) {
                    iv4.setImageResource(R.drawable.bmi27a);
                } else if (27.5 < fresult4 && fresult4 <= 29) {
                    iv4.setImageResource(R.drawable.bmi29);
                } else if (29 < fresult4 && fresult4 <= 30.5) {
                    iv4.setImageResource(R.drawable.bmi30a);
                } else if (30.5 < fresult4 && fresult4 <= 32) {
                    iv4.setImageResource(R.drawable.bmi32);
                } else if (32 < fresult4 && fresult4 <= 33.5) {
                    iv4.setImageResource(R.drawable.bmi33a);
                } else if (33.5 < fresult4 && fresult4 <= 35) {
                    iv4.setImageResource(R.drawable.bmi35);
                } else if (fresult4 > 35) {
                    iv4.setImageResource(R.drawable.bmi35);
                }
            } else {
                if (fresult4 <= 17) {
                    iv4.setImageResource(R.drawable.bmi17f);
                } else if (17 < fresult4 && fresult4 <= 18) {
                    iv4.setImageResource(R.drawable.bmi18f);
                } else if (18 < fresult4 && fresult4 <= 19) {
                    iv4.setImageResource(R.drawable.bmi19f);
                } else if (19 < fresult4 && fresult4 <= 20) {
                    iv4.setImageResource(R.drawable.bmi20f);
                } else if (20 < fresult4 && fresult4 <= 21) {
                    iv4.setImageResource(R.drawable.bmi21f);
                } else if (21 < fresult4 && fresult4 <= 22) {
                    iv4.setImageResource(R.drawable.bmi22f);
                } else if (22 < fresult4 && fresult4 <= 23) {
                    iv4.setImageResource(R.drawable.bmi23f);
                } else if (23 < fresult4 && fresult4 <= 24) {
                    iv4.setImageResource(R.drawable.bmi24f);
                } else if (24 < fresult4 && fresult4 <= 25) {
                    iv4.setImageResource(R.drawable.bmi25f);
                } else if (25 < fresult4 && fresult4 <= 26) {
                    iv4.setImageResource(R.drawable.bmi26f);
                } else if (26 < fresult4 && fresult4 <= 27) {
                    iv4.setImageResource(R.drawable.bmi27f);
                } else if (27 < fresult4 && fresult4 <= 28) {
                    iv4.setImageResource(R.drawable.bmi28f);
                } else if (28 < fresult4 && fresult4 <= 29) {
                    iv4.setImageResource(R.drawable.bmi29f);
                } else if (29 < fresult4 && fresult4 <= 30) {
                    iv4.setImageResource(R.drawable.bmi30f);
                } else if (30 < fresult4 && fresult4 <= 31) {
                    iv4.setImageResource(R.drawable.bmi31f);
                } else if (31 < fresult4 && fresult4 <= 32) {
                    iv4.setImageResource(R.drawable.bmi32f);
                } else if (32 < fresult4 && fresult4 <= 33) {
                    iv4.setImageResource(R.drawable.bmi33f);
                } else if (33 < fresult4 && fresult4 <= 34) {
                    iv4.setImageResource(R.drawable.bmi34f);
                } else if (34 < fresult4 && fresult4 <= 35) {
                    iv4.setImageResource(R.drawable.bmi35f);
                } else if (35 < fresult4 && fresult4 <= 36) {
                    iv4.setImageResource(R.drawable.bmi36f);
                } else if (36 < fresult4 && fresult4 <= 37) {
                    iv4.setImageResource(R.drawable.bmi37f);
                } else if (fresult4 > 37) {
                    iv4.setImageResource(R.drawable.bmi37f);
                }
            }
        }
    }
}