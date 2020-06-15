package com.example.ihealthary;
import android.content.Context;
import android.widget.ArrayAdapter;
import java.util.List;
public class simpleArrayAdapter<T> extends ArrayAdapter {
    //構造方法
    public simpleArrayAdapter(Context context, int resource, List<T>  objects) {
        super(context, resource, objects);
    }
    //複寫這個方法，使返回的資料沒有最後一項
    @Override
    public int getCount() {
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }

}