package com.minder.gotandroid.list;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.minder.gotandroid.R;
import com.minder.gotandroid.dto.Dream;


public class ListAdapter extends BaseAdapter {
    private Activity activity;
    private List<Dream> data;
    private List<Dream> originalList;

    int p;
    View v;

    public ListAdapter(Activity a, List<Dream> list) {
        activity = a;
        data = list;
        originalList = new ArrayList<Dream>();
        originalList.addAll(list);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;

        p = position;
        v = convertView;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.test_layout, null);
            holder.text = (TextView) convertView
                    .findViewById(R.id.mem_info_txt_id);
            holder.image = (ImageView) convertView
                    .findViewById(R.id.mem_photo_img_id);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.text.setText(data.get(position).getTodo());
        if ((data.get(position).getCategory()).equals("food"))
            holder.image.setImageResource(R.drawable.listicon1);
        else if ((data.get(position).getCategory()).equals("event"))
            holder.image.setImageResource(R.drawable.listicon2);
        else if ((data.get(position).getCategory()).equals("festival"))
            holder.image.setImageResource(R.drawable.listicon3);
        else if ((data.get(position).getCategory()).equals("tour"))
            holder.image.setImageResource(R.drawable.listicon4);
        else
            holder.image.setImageResource(R.drawable.listicon5);


        ImageButton button = (ImageButton) convertView.findViewById(R.id.ib_search_engine);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.google.com/#q=" + data.get(p).getTodo() + "&ie=utf8");

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent);
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        TextView text;
        ImageView image;

    }

    public void filterData(String query) {
        data.clear();

        if (TextUtils.isEmpty(query)) {
            data.addAll(originalList);
        } else {

            for (Dream dream : originalList) {

                if (dream.getTodo().contains(query)) {
                    data.add(dream);
                }
            }
        }
        notifyDataSetChanged();
    }
}