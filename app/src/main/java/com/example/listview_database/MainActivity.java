package com.example.listview_database;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listview_database.dao.ContactInfoDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private EditText et_name;
    private EditText et_phone;
    private ContactInfoDao dao;
    private List<Person> lists;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.id_lv);

        et_name = (EditText) findViewById(R.id.id_name);
        et_phone = (EditText) findViewById(R.id.id_number);
        dao = new ContactInfoDao(this, "mydb", "contactinfo", null, 1);
        lists = new ArrayList<Person>();
        // lv.setAdapter(new MyAdapter());// 放在这里添加数据再查询会挂掉Exception dispatching input event.
    }

    public void add(View view) {
        String name = et_name.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            dao.add(name, phone);
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(View view) {
        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            dao.delete(name);
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        }
    }

    public void modify(View view) {
        String name = et_name.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            dao.update(name, phone);
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        }
    }

    public void find(View view) {
        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Cursor cursor = dao.query(name);
            String phone = null;
            if (cursor.moveToFirst()) { // 将光标移动到第一行，如果游标为空，此方法将返回false。
                String str1 = null;
                do {
                    phone = cursor.getString(cursor.getColumnIndex("phone"));
                    str1 = "name:" + name + "phone:" + phone;
                    // 把javabean对象装到集合
                    lists.add(new Person(name, phone));

                    Log.d(TAG, str1);
                } while (cursor.moveToNext()); // 将光标移动到下一行，如果游标已经超过结果集中的最后一个条目，此方法将返回false。

                lv.setAdapter(new MyAdapter());
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Person person = lists.get(position);
                        Toast.makeText(MainActivity.this, person.getName() + ":" + person.getPhone(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
            cursor.close();
            if (phone == null) {
                Toast.makeText(this, "无此联系人信息", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 定义listview的数据适配器
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_name);
                viewHolder.tv_phone = (TextView) view.findViewById(R.id.tv_phone);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            Person person = lists.get(position);
            viewHolder.tv_name.setText(person.getName());
            viewHolder.tv_phone.setText(person.getPhone());
            return view;
        }

        class ViewHolder {
            TextView tv_name, tv_phone;
        }
    }
}
