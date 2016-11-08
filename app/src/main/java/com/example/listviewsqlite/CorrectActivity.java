package com.example.listviewsqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.view.View;
/**
 * 修改
 * Created by：赖上罗小贱 on 2016/11/8
 * 邮箱：ljq@luojunquan.top
 * 个人博客：www.luojunquan.top
 * CSDN:http://blog.csdn.net/u012990171
 */
public class CorrectActivity extends AppCompatActivity{
    private int id;
    private String name;
    private String sex;
    private String age;
    private String hobby;
    private EditText edt_name;
    private EditText edt_sex;
    private EditText edt_age;
    private EditText edt_hobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct);
        id = getIntent().getIntExtra("id",0);
        name = getIntent().getStringExtra("name");
        sex = getIntent().getStringExtra("sex");
        age = getIntent().getStringExtra("age");
        hobby = getIntent().getStringExtra("hobby");
        initView();
    }

    private void initView() {
        edt_name = (EditText) findViewById(R.id.correct_name);
        edt_sex = (EditText) findViewById(R.id.correct_sex);
        edt_age = (EditText) findViewById(R.id.correct_age);
        edt_hobby = (EditText) findViewById(R.id.correct_hobby);
        edt_name.setText(name);
        edt_sex.setText(sex);
        edt_age.setText(age);
        edt_hobby.setText(hobby);
    }
    public void choose(View view){
        switch (view.getId()){
            case R.id.correct_cancel:
                finish();
                break;
            case R.id.correct_sure:
                //得到输入的
                String name = edt_name.getText().toString().trim();
                String sex = edt_sex.getText().toString().trim();
                String age = edt_age.getText().toString().trim();
                String hobby = edt_hobby.getText().toString().trim();
                //调用修改方法
                MyDataBase.getInstances(CorrectActivity.this).updata(id,name,sex,age,hobby);
                finish();
                break;
        }
    }
}
