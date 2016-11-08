package com.example.listviewsqlite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.view.View;
/**
 * Created by：赖上罗小贱 on 2016/11/8
 * 邮箱：ljq@luojunquan.top
 * 个人博客：www.luojunquan.top
 * CSDN:http://blog.csdn.net/u012990171
 */
public class AddActivity extends AppCompatActivity{
    private EditText edt_name;
    private EditText edt_sex;
    private EditText edt_age;
    private EditText edt_hobby;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
    }

    private void initView() {
        edt_name = (EditText) findViewById(R.id.add_name);
        edt_sex = (EditText) findViewById(R.id.add_sex);
        edt_age = (EditText) findViewById(R.id.add_age);
        edt_hobby = (EditText) findViewById(R.id.add_hobby);
    }
    public void add(View view){
        String name = edt_name.getText().toString().trim();
        String sex = edt_sex.getText().toString().trim();
        String age = edt_age.getText().toString().trim();
        String hobby = edt_hobby.getText().toString().trim();
        //调用插入方法
        MyDataBase.getInstances(AddActivity.this).insert(name,sex,age,hobby);
        finish();
    }
}
