package com.example.listviewsqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by：赖上罗小贱 on 2016/11/8
 * 邮箱：ljq@luojunquan.top
 * 个人博客：www.luojunquan.top
 * CSDN:http://blog.csdn.net/u012990171
 */
public class MyListViewAdapter extends BaseAdapter{
    private Context context;
    private List<Map<String,Object>> list;

    public MyListViewAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview,null);
            holder.id = (TextView) convertView.findViewById(R.id.item_id);
            holder.position = (TextView) convertView.findViewById(R.id.item_position);
            holder.name = (TextView) convertView.findViewById(R.id.item_name);
            holder.sex = (TextView) convertView.findViewById(R.id.item_sex);
            holder.age = (TextView) convertView.findViewById(R.id.item_age);
            holder.hobby = (TextView) convertView.findViewById(R.id.item_hobby);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.position.setText(position + 1 +"");
        holder.id.setText(list.get(position).get("id").toString());
        holder.name.setText(list.get(position).get("name").toString());
        holder.sex.setText(list.get(position).get("sex").toString());
        holder.age.setText(list.get(position).get("age").toString());
        holder.hobby.setText(list.get(position).get("hobby").toString());
        return convertView;
    }
    static class ViewHolder{
        TextView position;
        TextView id;
        TextView name;
        TextView sex;
        TextView age;
        TextView hobby;
    }
    public void refreshList(List<Map<String,Object>> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
