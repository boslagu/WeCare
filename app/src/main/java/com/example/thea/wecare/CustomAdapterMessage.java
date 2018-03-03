package com.example.thea.wecare;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Tottie on 2/28/2018.
 */

public class CustomAdapterMessage extends ArrayAdapter<ConsultMessage>{

    private Context context;

    public CustomAdapterMessage(Context context, int resourceid, List<ConsultMessage> Items){
        super (context, resourceid, Items);
        this.context = context;
    }
    private class ViewHolder{
        TextView textView, textView1;
    }
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        ViewHolder holder = null;
        ConsultMessage rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (context != null){
            convertView = mInflater.inflate(R.layout.activity_consult_message, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            holder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (rowItem != null){
            holder.textView.setText(rowItem.getTitle());
            holder.textView1.setText(rowItem.getDescription());
        }
        return convertView;
    }


}
