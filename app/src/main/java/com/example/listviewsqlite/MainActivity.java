package com.example.listviewsqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * sqlite的增删改查
 * Created by：赖上罗小贱 on 2016/11/8
 * 邮箱：ljq@luojunquan.top
 * 个人博客：www.luojunquan.top
 * CSDN:http://blog.csdn.net/u012990171
 */
public class MainActivity extends AppCompatActivity {
    private List<Map<String, Object>> list;
    private MyListViewAdapter adapter;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
    }
    private void initView() {
        mListView = (ListView) findViewById(R.id.main_listview);
        List<Map<String, Object>> data = getData();
        adapter = new MyListViewAdapter(this, data);
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void setListener() {
        //点击跳转
        findViewById(R.id.insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        //ListView的监听事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            private int id;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                //删除是要拿到当前行的id值才能删除当前行,下面的操作都是点击某个item拿到对应item的id字段
//                //拿到当前position的 item的所有数据
//                //返回的数据格式为{name=段炼, age=25, sex=男, id=12, hobby=吃饭。睡觉}
//                Object itemAtPosition = mListView.getItemAtPosition(position);
//                Log.e("duanlian", itemAtPosition+"");
//                //转换成String
//                String s = itemAtPosition.toString();
//                //如果字符串包含"id"字段
//                if (s.contains("id")) {
//                    //拿到id字段是处于字符串第几个位置
//                    int a = s.indexOf("id");
//                    //"id"字段后面是"hobby"字段,拿到hobby字段的位置
//                    int b = s.indexOf("hobby");
//                    //切割字符串{name=段炼, age=25, sex=男, id=12, hobby=吃饭。睡觉}
//                    //从i开始+3个正好是id开始的地方,hobby-2正好是id结束的位置减去了一个h和一个逗号
//                    String substring = s.substring(a + 3, b-2);
//                    //得到id后转换成int类型
//                    id = Integer.parseInt(substring);
//                }
//                //将得到id传入到需要的方法中
//                showMyDialog(id, position);
                //得到对应item里面的id
                Object id = list.get(position).get("id");
                int i = Integer.parseInt(id.toString());
                String name = list.get(position).get("name").toString();
                String sex = list.get(position).get("sex").toString();
                String age = list.get(position).get("age").toString();
                String hobby = list.get(position).get("hobby").toString();
                //将得到id传入到需要的方法中
                showMyDialog(i, name, sex, age, hobby);
            }
        });
    }

    /**
     * 点击显示对话框选择修改或者是删除
     */
    private void showMyDialog(final int id, final String name, final String sex, final String age, final String hobby) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置一个标题
        builder.setTitle("请选择");
        //给dialog设置item
        builder.setItems(new String[]{"修改", "删除"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                switch (position) {
                    case 0://修改
                        Intent intent = new Intent(MainActivity.this, CorrectActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("name", name);
                        intent.putExtra("sex", sex);
                        intent.putExtra("age", age);
                        intent.putExtra("hobby", hobby);
                        startActivityForResult(intent,0);
                        break;
                    case 1://删除
                        MyDataBase.getInstances(MainActivity.this).delete(id);
                        //重新查询,然后显示
                        List<Map<String, Object>> data = getData();
                        adapter.refreshList(data);
                        Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
        builder.show();
    }

    /**
     * 通过查找数据库,拿到里面的数据
     */
    private List<Map<String, Object>> getData() {
        list = new ArrayList<>();
        Cursor query = MyDataBase.getInstances(MainActivity.this).query();
        /*
        游标cursor默认是在-1的位置,query.moveToFirst()将游标移动到第一行,如果不写这个就会报
         Caused by: android.database.CursorIndexOutOfBoundsException: Index -1 requested, with a size of 12
         这个问题坑爹,以后一定要注意
         */
        if (query.moveToFirst()) {
            do {
                String name = query.getString(query.getColumnIndex("name"));
                String sex = query.getString(query.getColumnIndex("sex"));
                String age = query.getString(query.getColumnIndex("age"));
                String hobby = query.getString(query.getColumnIndex("hobby"));
                int id = query.getInt(query.getColumnIndex("id"));
                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("name", name);
                map.put("sex", sex);
                map.put("age", age);
                map.put("hobby", hobby);
                list.add(map);
            } while (query.moveToNext());
        }
        //关闭查询游标
        query.close();
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            List<Map<String, Object>> data1 = getData();
//            adapter = new MyListViewAdapter(this, data1);
//            mListView.setAdapter(adapter);
            adapter.refreshList(data1);

        }
    }
}
