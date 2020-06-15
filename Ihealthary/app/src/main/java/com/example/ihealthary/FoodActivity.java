package com.example.ihealthary;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.widget.Toast;


public class FoodActivity extends AppCompatActivity {
    String[]foodtype={"小七三明治","小七便當","小七沙拉","小七涼麵","小七飯糰","全家麵包","全家甜點","全家飯糰","全家便當","SUBWAY","三商巧福","五十嵐","四海遊龍","壽司與飯糰類",
            "小吃類","必勝客","手搖飲","摩斯","星巴克","水果類","永和豆漿","湯類","炸物類","甜點類","肉魚蛋類","肯德基","零食類","頂呱呱","飯類","飲料類","鬍鬚張","鮮芋仙","麥當勞"
            ,"麵包類","麵類","義大利麵"};
    public ImportDB importDB;
    static String[] arrayheat1,arrayfood1;
    static TextView information,total;
    Spinner secondarray ,firstarray;
    AutoCompleteTextView search;
    ArrayAdapter<String> adapter,MyArrayAdapter;
    MyAdapter<String> searchadapter;
    String text;
    String usecalc = "use";
    SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(ImportDB.DB_PATH + "/" + ImportDB.DB_NAME, null);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food2);
        importDB = new ImportDB(this);
        importDB.openDatabase();
        firstarray = (Spinner) findViewById(R.id.spinner3);
        secondarray = (Spinner) findViewById(R.id.spinner4);
        information = (TextView) findViewById(R.id.textView17);
        total = findViewById(R.id.textView19);
        search = (AutoCompleteTextView) findViewById(R.id.foodsearch);
        ListView listview2 = (ListView) findViewById(R.id.listview2);
        MyArrayAdapter = new ArrayAdapter<String>(FoodActivity.this, android.R.layout.simple_list_item_1);
        listview2.setAdapter(MyArrayAdapter);
        listview2.setOnItemClickListener(listViewOnItemClickListener);

        firstarray.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String food1 = "";
                String heat1 = "";
                String table = foodtype[i];
                Cursor cursor = db.rawQuery("select * from " + table, null);//使用所選取的foodtype當作table名查詢資料庫
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    food1 += cursor.getString(cursor.getColumnIndex("_id")) + ",";//抓出table中所有食物
                    heat1 += cursor.getString(cursor.getColumnIndex("sex")) + ",";//抓出table中所有熱量
                }
                arrayfood1 = food1.split(",");
                arrayheat1 = heat1.split(",");
                adapter = new ArrayAdapter<String>(FoodActivity.this, android.R.layout.simple_spinner_item, arrayfood1);//註冊adapter 使用arrayfood1陣列
                secondarray.setAdapter(adapter);//指定第二個spinner使用 adapter
                cursor.close();
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        secondarray.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int a, long b) {
                TextView information = (TextView) findViewById(R.id.textView17);
                information.setText(arrayheat1[a]);
                searchadapter = new MyAdapter<String>(FoodActivity.this, android.R.layout.simple_dropdown_item_1line, arrayfood1);
                search.setAdapter(searchadapter);
                search.setText("");
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String chooseheat = "";
                String kind = firstarray.getSelectedItem().toString();
                String choosefood = search.getEditableText().toString();
                String[] args = {choosefood};
                Cursor foodcursor = db.rawQuery(" select * from " + kind + " where _id=? ", args);
                for (foodcursor.moveToFirst(); !foodcursor.isAfterLast(); foodcursor.moveToNext()) {
                    chooseheat += foodcursor.getString(foodcursor.getColumnIndex("sex"));//抓出table中所有熱量
                }
                secondarray.setSelection(adapter.getPosition(choosefood));
                TextView information = (TextView) findViewById(R.id.textView17);
                information.setText(chooseheat);
                foodcursor.close();
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String findfood =search.getEditableText().toString();
                for (int f=0;f<arrayfood1.length;f++){
                    if(arrayfood1[f].equals(findfood)||findfood.equals("")){
                            usecalc = "use";
                            break;
                    }
                    else {
                            usecalc = "not use";
                    }
                }
            }
        });
    }
    public void calc1(View v) {
        if (usecalc.equals("use")) {
            TextView quntity = (TextView) findViewById(R.id.quantity);
            String q = quntity.getText().toString();
            if (q.isEmpty() || q.equals(".")) {
                Toast toast = Toast.makeText(FoodActivity.this,
                        "請輸入份數!", Toast.LENGTH_SHORT);
                //顯示提示訊息
                toast.show();
                return;
            }

            String f = secondarray.getSelectedItem().toString();
            int fkcal = (int) Math.round(Double.parseDouble(information.getText().toString()) * Double.parseDouble(q));
            text = (f + " " + q + "份" + " " + fkcal + "大卡");
            MyArrayAdapter.add(text);
            MyArrayAdapter.notifyDataSetChanged();
            int fTotalkcal = Integer.parseInt(total.getText().toString());
            fTotalkcal += fkcal;
            total.setText(String.valueOf(fTotalkcal));
            SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
            sharedPreferences.edit().putString("foodtotal", total.getText().toString()).apply();
        }
        else if(usecalc.equals("not use")){
            Toast error =Toast.makeText(FoodActivity.this,"查無此食物!",Toast.LENGTH_SHORT);
            error.show();
            return;
        }
    }

    private ListView.OnItemClickListener listViewOnItemClickListener = new ListView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // TODO Auto-generated method stub
            final String strSelectedItem = parent.getItemAtPosition(position).toString();

            AlertDialog.Builder delAlertDialog = new AlertDialog.Builder(FoodActivity.this);
            delAlertDialog.setTitle("刪除該資料?");
            delAlertDialog.setMessage(strSelectedItem);
            delAlertDialog.setPositiveButton("是", new DialogInterface.OnClickListener() {

                // do something when the button is clicked
                public void onClick(DialogInterface arg0, int arg1) {
                    MyArrayAdapter.remove(strSelectedItem);
                    MyArrayAdapter.notifyDataSetChanged();
                    int ReducefKcal =  Integer.parseInt(strSelectedItem.substring(strSelectedItem.indexOf("份")+2, strSelectedItem.indexOf("大卡")));
                    int TotalfKcal = Integer.parseInt(total.getText().toString());
                    total.setText(String.valueOf(TotalfKcal -= ReducefKcal));
                    SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                    sharedPreferences.edit().putString("foodtotal", total.getText().toString()).apply();
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
        for(int i=0;i<MyArrayAdapter.getCount();i++){
            str[i]=MyArrayAdapter.getItem(i);
        }

        String list= TextUtils.join(",",str);
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        sharedPreferences.edit().putString("foodtotal", total.getText().toString()).apply();
        sharedPreferences.edit().putString("foodlist", list).apply();
        MyArrayAdapter.clear();
        MyArrayAdapter.notifyDataSetChanged();

    }

    protected void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        total.setText((sharedPreferences.getString("foodtotal","0")));
        String retext = sharedPreferences.getString("foodlist","0").trim();
        if(retext.equals("0") || retext.equals("")){
            total.setText((sharedPreferences.getString("foodtotal","0")));
        }
        else{
            String[] split = retext.split(",");
            for (int i = 0; i < split.length; i++) {
                MyArrayAdapter.add(split[i]);
                MyArrayAdapter.notifyDataSetChanged();
            }
        }
    }

}