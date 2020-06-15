package com.example.ihealthary;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ListView;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class ExerciseActivity extends AppCompatActivity {
    double[] energyRate = {5.5, 8.2, 16.8, 3.6, 8.4, 4.2, 4.2, 3.6, 4.7, 5, 5.1, 5.1, 6.3, 6.3, 8.3, 7.7, 7, 8.4};
    EditText time;
    TextView information, total;
    Spinner sports;
    ListView listView;
    ArrayAdapter<String> MyArrayAdapter;
    String text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise2);
        time = (EditText) findViewById(R.id.time);
        information = (TextView) findViewById(R.id.textView11);
        total = (TextView) findViewById(R.id.textView14);
        sports = (Spinner) findViewById(R.id.spinner2);
        listView=(ListView)findViewById(R.id.listview);
        MyArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);//註冊MyArrayAdapter
        listView.setAdapter(MyArrayAdapter);//指定listView使用MyArrayAdapter
        listView.setOnItemClickListener(listViewOnItemClickListener);
        sports.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {                 //sports spinner監聽器
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                information.setText(String.valueOf(energyRate[i]));
                ImageView sportpic =(ImageView)findViewById(R.id.sportpic);
                if(i==0){
                    sportpic.setImageResource(R.drawable.walk);
                }
                if(i==1){
                    sportpic.setImageResource(R.drawable.runslow);
                }
                if(i==2){
                    sportpic.setImageResource(R.drawable.runfast);
                }
                if(i==3){
                    sportpic.setImageResource(R.drawable.vollyball);
                }
                if(i==4){
                    sportpic.setImageResource(R.drawable.bike);
                }
                if(i==5){
                    sportpic.setImageResource(R.drawable.taigi);
                }
                if(i==6){
                    sportpic.setImageResource(R.drawable.pingpong);
                }
                if(i==7){
                    sportpic.setImageResource(R.drawable.bowling);
                }
                if(i==8){
                    sportpic.setImageResource(R.drawable.baseball);
                }
                if(i==9){
                    sportpic.setImageResource(R.drawable.golf);
                }
                if(i==10){
                    sportpic.setImageResource(R.drawable.badminton);
                }
                if(i==11){
                    sportpic.setImageResource(R.drawable.skates);
                }
                if(i==12){
                    sportpic.setImageResource(R.drawable.swim);
                }
                if(i==13){
                    sportpic.setImageResource(R.drawable.basketball50);
                }
                if(i==14){
                    sportpic.setImageResource(R.drawable.basketball);
                }
                if(i==15){
                    sportpic.setImageResource(R.drawable.soccer);
                }
                if(i==16){
                    sportpic.setImageResource(R.drawable.climbing);
                }
                if(i==17){
                    sportpic.setImageResource(R.drawable.ropeskipping);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }

    public void calc(View v) {                  //calc button onclick事件
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String st = time.getText().toString();
        if (st.isEmpty() || st.equals(".")) {           //判斷時間有沒有輸入
            Toast toast2 = Toast.makeText(ExerciseActivity.this,
                    "請輸入時間!", Toast.LENGTH_SHORT);
            //顯示提示訊息
            toast2.show();
            return;
        }
        double w = Double.parseDouble(sharedPreferences.getString("weight","0"));//取得體重
        double t = Double.parseDouble(time.getText().toString());//取得時間
        int i = sports.getSelectedItemPosition();//取得運動熱量
        String a =sports.getSelectedItem().toString();
        int kcal = (int)Math.round(energyRate[i] * w * t);//計算後強制轉型int
        text=(a+" "+time.getText().toString()+"小時"+" "+kcal+"大卡");
        MyArrayAdapter.add(text);//text內容加入listview中
        MyArrayAdapter.notifyDataSetChanged();

        int dTotalKcal = Integer.parseInt(total.getText().toString());
        dTotalKcal += kcal;//每次觸發按鈕事件累加卡路里
        total.setText(String.valueOf(dTotalKcal));
        sharedPreferences.edit().putString("sportstotal", total.getText().toString()).apply();

    }
    private ListView.OnItemClickListener listViewOnItemClickListener = new ListView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // TODO Auto-generated method stub
            final String strSelectedItem = parent.getItemAtPosition(position).toString();

            AlertDialog.Builder delAlertDialog = new AlertDialog.Builder(ExerciseActivity.this);
            delAlertDialog.setTitle("刪除該資料?");
            delAlertDialog.setMessage(strSelectedItem);
            delAlertDialog.setPositiveButton("是", new DialogInterface.OnClickListener() {

                // do something when the button is clicked
                public void onClick(DialogInterface arg0, int arg1) {
                    MyArrayAdapter.remove(strSelectedItem);
                    MyArrayAdapter.notifyDataSetChanged();

                    int dReduceKcal =  Integer.parseInt(strSelectedItem.substring(strSelectedItem.indexOf("小時")+3, strSelectedItem.indexOf("大卡")));
                    int dTotalKcal =Integer.parseInt(total.getText().toString());
                    total.setText(String.valueOf(dTotalKcal -= dReduceKcal));//刪除資料時減掉卡路里
                    SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                    sharedPreferences.edit().putString("sportstotal", total.getText().toString()).apply();
                }
            });
            delAlertDialog.setNegativeButton("否", new DialogInterface.OnClickListener() {

                // do something when the button is clicked
                public void onClick(DialogInterface arg0, int arg1) {
                    //...
                }
            });
            delAlertDialog.show();
        }};
    protected void onPause(){
        super.onPause();
        String[] str =new String[MyArrayAdapter.getCount()];
        for(int i=0;i<MyArrayAdapter.getCount();i++){//迴圈取出listview內容寫成陣列
            str[i]=MyArrayAdapter.getItem(i);
        }
        String list= TextUtils.join(",",str);//陣列分割成字串
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        sharedPreferences.edit().putString("sportstotal", total.getText().toString()).apply();
        sharedPreferences.edit().putString("sportslist", list).apply();//listview字串內容儲存
        MyArrayAdapter.clear();
        MyArrayAdapter.notifyDataSetChanged();
    }
    protected void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        total.setText((sharedPreferences.getString("sportstotal","0")));
        String retext = sharedPreferences.getString("sportslist","0").trim();
        if(retext.equals("0")||retext.equals("")){
            total.setText((sharedPreferences.getString("sportstotal","0")));
        }
        else{
            String[] split = retext.split(",");
            for (int i = 0; i < split.length; i++) {
                MyArrayAdapter.add(split[i]);//字串分割成陣列後加入listview
                MyArrayAdapter.notifyDataSetChanged();
            }
        }
    }

}