package com.example.ihealthary;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.os.Bundle;
import java.text.NumberFormat;
import android.app.AlarmManager;
import android.app.PendingIntent;
import java.util.Calendar;

public class ButtonActivity extends AppCompatActivity {
    public ImportDB importDB;
    int minheat,maxheat,advice;
    TextView horse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        importDB = new ImportDB(this);
        importDB.openDatabase();
        long systemTime = System.currentTimeMillis();
        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE,0);
        long selectTime = c.getTimeInMillis();
        if(systemTime > selectTime) {
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        Intent intent= new Intent("ELITOR_CLOCK");
        intent.setClass(this, MyReceiver.class);
        PendingIntent pi=PendingIntent.getBroadcast(this, 0, intent,0);
        AlarmManager am=(AlarmManager)getSystemService(ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);//設定計時器

        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ButtonActivity.this,SettingActivity.class);
                startActivity(intent);

            }
        });
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ButtonActivity.this, ReportActivity.class);
                startActivity(intent);

            }
        });
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ButtonActivity.this, ExerciseActivity.class);
                startActivity(intent);

            }
        });
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ButtonActivity.this, FoodActivity.class);
                startActivity(intent);

            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();
        horse =findViewById(R.id.horse);
        horse.setSelected(true);
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String weight = sharedPreferences.getString("weight","0");
        minheat = 25*(int)(Float.parseFloat(weight));
        maxheat = 30*(int)(Float.parseFloat(weight));
        advice = maxheat-Integer.parseInt(sharedPreferences.getString("foodtotal","0"));
        if(advice>0) {
            horse.setText("今日建議攝取熱量為" + minheat + "大卡" + "~" + maxheat + "大卡" + "，" + "離建議最大攝取量還差" + advice + "大卡");
        }
        else {
            horse.setText("今日建議攝取熱量為" + minheat + "大卡" + "~" + maxheat + "大卡" + "，" + "已達到建議最大攝取熱量");
        }
        String st = sharedPreferences.getString("sportstotal","0");//取得運動消耗卡路里
        String ft =sharedPreferences.getString("foodtotal","0");//取得食物攝取卡路里
        double today = Double.parseDouble(st)-Double.parseDouble(ft);//運算得出總消耗
        TextView heat =(TextView)findViewById(R.id.textView2);
        if(today<0){
            heat.setText("增加"+(int)Math.abs(today)+"大卡");
            sharedPreferences.edit().putString("todaytotal", String.valueOf(today)).apply();
        }
        else if (today>0){
            heat.setText("減少"+(int)Math.abs(today)+"大卡");
            sharedPreferences.edit().putString("todaytotal", String.valueOf(today)).apply();
        }
        else if(today==0){
            heat.setText(String.valueOf(0));
            sharedPreferences.edit().putString("todaytotal", String.valueOf(today)).apply();
        }

        String h = sharedPreferences.getString("height", "0");
        String w = sharedPreferences.getString("weight", "0");
        String s = sharedPreferences.getString("sex","0");
        float fh = Float.parseFloat(h);      //取得身高
        float fw = Float.parseFloat(w);     //取得體重
        float fresult;
        TextView result = (TextView) findViewById(R.id.textView9);
        fh = fh / 100; // 計算BMI
        fh = fh * fh;  // 計算BMI

        NumberFormat nf = NumberFormat.getInstance();   // 數字格式
        nf.setMaximumFractionDigits(1);                 // 限制小數第一位
        fresult = fw / fh;                                // 計算BMI
        result.setText("BMI:" + nf.format(fw / fh));
        TextView status = (TextView) findViewById(R.id.textView);
        ImageView iv = (ImageView)findViewById(R.id.imageView2);
        if("男".equals(s)) {
            if (fresult <= 15) {
                status.setText("體重過輕");
                iv.setImageResource(R.drawable.bmi15);
            } else if (15 < fresult && fresult <= 15.5) {
                status.setText("體重過輕");
                iv.setImageResource(R.drawable.bmi15a);
            } else if (15.5 < fresult && fresult <= 16) {
                status.setText("體重過輕");
                iv.setImageResource(R.drawable.bmi16);
            } else if (16 < fresult && fresult <= 16.5) {
                status.setText("體重過輕");
                iv.setImageResource(R.drawable.bmi16a);
            }  else if (16.5 < fresult && fresult <= 17) {
                status.setText("體重過輕");
                iv.setImageResource(R.drawable.bmi17);
            } else if (17 < fresult && fresult <= 17.5) {
                status.setText("體重過輕");
                iv.setImageResource(R.drawable.bmi17a);
            } else if (17.5 < fresult && fresult <= 18) {
                status.setText("體重過輕");
                iv.setImageResource(R.drawable.bmi18);
            } else if (18 < fresult && fresult <= 19) {
                status.setText("體重正常");
                iv.setImageResource(R.drawable.bmi19);
            } else if (19 < fresult && fresult <= 20) {
                status.setText("體重正常");
                iv.setImageResource(R.drawable.bmi20);
            } else if (20 < fresult && fresult <= 21) {
                status.setText("體重正常");
                iv.setImageResource(R.drawable.bmi21);
            } else if (21 < fresult && fresult <= 22) {
                status.setText("體重正常");
                iv.setImageResource(R.drawable.bmi22);
            } else if (22 < fresult && fresult <= 23) {
                status.setText("體重正常");
                iv.setImageResource(R.drawable.bmi23);
            } else if (23 < fresult && fresult <= 24) {
                status.setText("體重正常");
                iv.setImageResource(R.drawable.bmi24);
            } else if (24 < fresult && fresult <= 24.5) {
                status.setText("體重過重");
                iv.setImageResource(R.drawable.bmi24a);
            } else if (24.5 < fresult && fresult <= 26) {
                status.setText("體重過重");
                iv.setImageResource(R.drawable.bmi26);
            } else if (26 < fresult && fresult <= 27.5) {
                status.setText("輕度肥胖");
                iv.setImageResource(R.drawable.bmi27a);
            } else if (27.5 < fresult && fresult <= 29) {
                status.setText("輕度肥胖");
                iv.setImageResource(R.drawable.bmi29);
            } else if (29 < fresult && fresult <= 30.5) {
                status.setText("輕度肥胖");
                iv.setImageResource(R.drawable.bmi30a);
            } else if (30.5 < fresult && fresult <= 32) {
                status.setText("中度肥胖");
                iv.setImageResource(R.drawable.bmi32);
            } else if (32 < fresult && fresult <= 33.5) {
                status.setText("中度肥胖");
                iv.setImageResource(R.drawable.bmi33a);
            } else if (33.5 < fresult && fresult <= 35) {
                status.setText("中度肥胖");
                iv.setImageResource(R.drawable.bmi35);
            } else if (fresult > 35) {
                status.setText("重度肥胖 ");
                iv.setImageResource(R.drawable.bmi35);
            }
        }
        else{
            if (fresult <= 17) {
                status.setText("體重過輕");
                iv.setImageResource(R.drawable.bmi17f);
            } else if (17 < fresult && fresult <= 18) {
                status.setText("體重正常");
                iv.setImageResource(R.drawable.bmi18f);
            } else if (18 < fresult && fresult <= 19) {
                status.setText("體重正常");
                iv.setImageResource(R.drawable.bmi19f);
            } else if (19 < fresult && fresult <= 20) {
                status.setText("體重正常");
                iv.setImageResource(R.drawable.bmi20f);
            } else if (20< fresult && fresult <= 21) {
                status.setText("體重正常");
                iv.setImageResource(R.drawable.bmi21f);
            } else if (21 < fresult && fresult <= 22) {
                status.setText("體重正常");
                iv.setImageResource(R.drawable.bmi22f);
            } else if (22 < fresult && fresult <= 23) {
                status.setText("體重正常");
                iv.setImageResource(R.drawable.bmi23f);
            } else if (23 < fresult && fresult <= 24) {
                status.setText("體重正常");
                iv.setImageResource(R.drawable.bmi24f);
            } else if (24 < fresult && fresult <= 25) {
                status.setText("體重過重");
                iv.setImageResource(R.drawable.bmi25f);
            } else if (25 < fresult && fresult <= 26) {
                status.setText("體重過重");
                iv.setImageResource(R.drawable.bmi26f);
            } else if (26 < fresult && fresult <= 27) {
                status.setText("體重過重");
                iv.setImageResource(R.drawable.bmi27f);
            } else if (27 < fresult && fresult <= 28) {
                status.setText("輕度肥胖");
                iv.setImageResource(R.drawable.bmi28f);
            } else if (28 < fresult && fresult <= 29) {
                status.setText("輕度肥胖");
                iv.setImageResource(R.drawable.bmi29f);
            } else if (29 < fresult && fresult <= 30) {
                status.setText("中度肥胖");
                iv.setImageResource(R.drawable.bmi30f);
            } else if (30 < fresult && fresult <= 31) {
                status.setText("中度肥胖");
                iv.setImageResource(R.drawable.bmi31f);
            } else if (31< fresult && fresult <= 32) {
                status.setText("中度肥胖");
                iv.setImageResource(R.drawable.bmi32f);
            } else if (32 < fresult && fresult <= 33) {
                status.setText("中度肥胖");
                iv.setImageResource(R.drawable.bmi33f);
            } else if (33 < fresult && fresult <= 34) {
                status.setText("中度肥胖");
                iv.setImageResource(R.drawable.bmi34f);
            } else if (34 < fresult && fresult <= 35) {
                status.setText("中度肥胖");
                iv.setImageResource(R.drawable.bmi35f);
            } else if (35 < fresult && fresult <= 36) {
                status.setText("重度肥胖");
                iv.setImageResource(R.drawable.bmi36f);
            } else if (36 < fresult && fresult <= 37) {
                status.setText("重度肥胖");
                iv.setImageResource(R.drawable.bmi37f);
            } else if (fresult>37) {
                status.setText("重度肥胖");
                iv.setImageResource(R.drawable.bmi37f);
            }
        }

    }
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            System.exit(0);
        }
        return false;
    }

}