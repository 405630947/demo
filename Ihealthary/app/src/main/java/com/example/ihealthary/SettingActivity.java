package com.example.ihealthary;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {
    private Button mBtnButton;
    EditText h;
    EditText w;
    EditText a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        a = (EditText)findViewById(R.id.editText20);
        h = (EditText)findViewById(R.id.editText21);// 取得身高物件
        w = (EditText)findViewById(R.id.editText22);
        SharedPreferences setting = getSharedPreferences("data" , MODE_PRIVATE);
        a.setText(setting.getString("age","0"));
        h.setText(setting.getString("height","0"));
        w.setText(setting.getString("unchangeweight","0"));
        String age = setting.getString("sex","0");
        final Spinner notify = (Spinner)findViewById(R.id.spinner20);
        ArrayAdapter<CharSequence> manAdapter = ArrayAdapter.createFromResource(
                this, R.array.manlist, android.R.layout.simple_spinner_item );
        manAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> ladyAdapter = ArrayAdapter.createFromResource(
                this, R.array.ladylist, android.R.layout.simple_spinner_item );
        ladyAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        if(age.equals("男")){
            notify.setAdapter(manAdapter);
        }
        else {
            notify.setAdapter(ladyAdapter);
        }

        notify.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s=notify.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Button btn_button = findViewById(R.id.btn_button20);
        btn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (! ("".equals(h.getText().toString())
                        || "".equals(w.getText().toString())
                        ||"請選擇性別".equals(notify.getSelectedItem().toString())
                        || "".equals(a.getText().toString()))) {
                    SharedPreferences sharedPreferences = getSharedPreferences("data" , MODE_PRIVATE);
                    sharedPreferences.edit().putString("age", a.getText().toString()).apply();
                    sharedPreferences.edit().putString("height", h.getText().toString()).apply();
                    sharedPreferences.edit().putString("weight" , w.getText().toString()).apply();
                    sharedPreferences.edit().putString("unchangeweight",w.getText().toString()).apply();
                    sharedPreferences.edit().putString("sex",notify.getSelectedItem().toString()).apply();
                    finish();
                }
                else{
                    Toast toast = Toast.makeText(SettingActivity.this,
                            "請輸入完整資訊!", Toast.LENGTH_SHORT);
                    //顯示Toast
                    toast.show();
                }

            }
        });

    }

}