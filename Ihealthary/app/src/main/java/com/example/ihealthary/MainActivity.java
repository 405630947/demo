package com.example.ihealthary;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    EditText h;
    EditText w;
    EditText a;
    List<String> data_list = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences msharedPreferences = this.getSharedPreferences("checkfirstin", MODE_PRIVATE);
        boolean isFirstRun = msharedPreferences.getBoolean("isFirstRun", true);//設定第一次進入預設為true
        final Editor editor = msharedPreferences.edit();//註冊editor
        a = (EditText)findViewById(R.id.editText2);//取得年齡
        h = (EditText)findViewById(R.id.editText3);//取得身高
        w = (EditText)findViewById(R.id.editText5);//取得體重
        data_list.add("男");
        data_list.add("女");
        data_list.add("請選擇性別");
        final Spinner notify = (Spinner)findViewById(R.id.spinner);
        simpleArrayAdapter arrAdapter = new simpleArrayAdapter<String>(this,android.R.layout.simple_spinner_item, data_list);
        arrAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        notify.setAdapter(arrAdapter);//指定spinner使用arradapter
        notify.setSelection(data_list.size() - 1, true);
        notify.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s=notify.getSelectedItem().toString();//監聽使用者所選取項目
            }
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Button btn_button = findViewById(R.id.btn_button);
        btn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (! ("".equals(h.getText().toString())
                        || "".equals(w.getText().toString())
                        ||"請選擇性別".equals(notify.getSelectedItem().toString())
                        || "".equals(a.getText().toString()))) {                                     //判別是否有未填的資料
                    SharedPreferences sharedPreferences = getSharedPreferences("data" , MODE_PRIVATE);
                    sharedPreferences.edit().putString("age", a.getText().toString()).apply();
                    sharedPreferences.edit().putString("height", h.getText().toString()).apply();
                    sharedPreferences.edit().putString("weight" , w.getText().toString()).apply();
                    sharedPreferences.edit().putString("unchangeweight",w.getText().toString()).apply();
                    sharedPreferences.edit().putString("sex",notify.getSelectedItem().toString()).apply();//使用sharepreference存入使用者輸入的 年齡體重身高性別
                    editor.putBoolean("isFirstRun", false);//將isFirstRun更改為false
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, ButtonActivity.class);
                    startActivity(intent);//跳轉頁面
                    finish();
                }
                else{
                    Toast toast = Toast.makeText(MainActivity.this,
                            "請輸入完整資訊!", Toast.LENGTH_SHORT);
                    //顯示提示訊息
                    toast.show();
                }

            }
        });
        if(isFirstRun){                                 //判斷是否第一次進入
            Log.e("debug", "第一次運行");
        }
        else{
            Intent intent = new Intent(MainActivity.this, ButtonActivity.class);
            startActivity(intent);
            finish();
        }
    }

}