package com.example.brave.workshop;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by BRAVE on 4/11/2559.
 */

public class CustomAdapter extends BaseAdapter {

    Context context;

    public CustomAdapter (Context applicationContext){
        this.context = applicationContext;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater Inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        ViewHolder myHolder = null;
        if (convertView == null){

            convertView = Inflater.inflate(R.layout.activity_row_list,null);
            myHolder = new ViewHolder();
            myHolder.imageApp = (ImageView) convertView.findViewById(R.id.imageApp);
            myHolder.tvNew = (TextView) convertView.findViewById(R.id.tvNews);
            myHolder.tvDtail = (TextView) convertView.findViewById(R.id.tvDtail);

            convertView.setTag (myHolder);
        }else {
            myHolder = (ViewHolder) convertView.getTag();
        }return  convertView ;
    }


    private class ViewHolder {
        ImageView imageApp;
        TextView tvNew,tvDtail;
    }
}
