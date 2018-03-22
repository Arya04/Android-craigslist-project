package com.example.listview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class CustomAdapter extends BaseAdapter {
    private static final String TAG = "custom adapter";
    LayoutInflater myInflater;


    private Activity_ListView myActivity;
    private List<BikeData> list;
    private String tempUrl;

    public CustomAdapter(Activity_ListView activity, List<BikeData> bikes){


        myActivity = activity;

        this.myInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        list = bikes;

        //tempUrl = "http://tetonsoftware.com/bikes/";
        tempUrl = myActivity.url;
        tempUrl = tempUrl.substring(0,tempUrl.lastIndexOf("/"))+"/";

    }



    public static class ViewHolder{
        ImageView img;
        TextView tv1;
        TextView tv2;
        TextView tv3;
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHolder holder;
        if(convertView == null){
            convertView = myInflater.inflate(R.layout.listview_row_layout,null);

            holder = new ViewHolder();
            holder.tv1 = (TextView)convertView.findViewById(R.id.Model);
            holder.tv2 = (TextView)convertView.findViewById(R.id.Price);
            holder.tv3 = (TextView)convertView.findViewById(R.id.Description);
            holder.img = (ImageView)convertView.findViewById(R.id.imageView1);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //set the text
        //TODO FIX BIKE DATA
        holder.tv1.setText(list.get(position).MODEL);
        holder.tv2.setText("$"+list.get(position).PRICE);
        holder.tv3.setText(list.get(position).DESCRIPTION);
        //holder.img.setImageResource(R.drawable.generic);


        //asyncTask for image

        new DownloadImageTask(myActivity,holder).execute(tempUrl+list.get(position).PICTURE);

        return convertView;
    }
}