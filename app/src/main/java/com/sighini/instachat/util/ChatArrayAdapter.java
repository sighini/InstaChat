package com.sighini.instachat.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sighini.instachat.R;
import com.sighini.instachat.dto.ChatUserData;

import java.util.ArrayList;
import java.util.List;


public class ChatArrayAdapter extends ArrayAdapter<ChatUserData>{

    private ArrayList<ChatUserData> mItems;
    private Context mContext;

    public ChatArrayAdapter(Context context, int resource, List<ChatUserData> items) {
        super(context, resource, items);
        mContext = context;
        mItems = (ArrayList<ChatUserData>) items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public ChatUserData getItem(int position) {
        return mItems.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_list_item, parent, false);
        }

        TextView chatName = (TextView)convertView.findViewById(R.id.id_chat_name);

        ChatUserData item = mItems.get(position);

        chatName.setText(item.getName());

        return convertView;
    }


}
