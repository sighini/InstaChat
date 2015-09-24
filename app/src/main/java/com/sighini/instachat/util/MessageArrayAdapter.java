package com.sighini.instachat.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sighini.instachat.R;
import com.sighini.instachat.dto.MsgUserData;

public class MessageArrayAdapter extends ArrayAdapter<MsgUserData> {

    private static final String TAG = MessageArrayAdapter.class.getSimpleName() ;
	private ArrayList<MsgUserData> mMessages = new ArrayList<>();
    private int mUserId;


	public MessageArrayAdapter(Context context, int textViewResourceId, List<MsgUserData> items,
                               int userId) {
		super(context, textViewResourceId);
        mMessages = (ArrayList<MsgUserData>) items;
        for (MsgUserData message: mMessages) {
            Log.e(TAG, "Each message user id:" + message.getUser_id());
            Log.e(TAG, "Each message user id:" + message.getId());

        }
        Log.e(TAG, "user Id" + userId);
        mUserId = userId;
	}

	public int getCount() {
		return mMessages.size();
	}

    @Override
    public MsgUserData getItem(int position) {
        return mMessages.get(position);
    }

    public static class ViewHolder{


        public TextView leftMsg;
        public TextView rightMsg;
        public ImageView leftImg;
        public ImageView rightImg;
        public LinearLayout leftView;
        public LinearLayout rightView;

    }

    @Override
    public void add(MsgUserData object) {
        mMessages.add(object);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
        ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.message_list_item, parent, false);
            holder = new ViewHolder();
           // holder.leftImg = (ImageView) row.findViewById(R.id.text);
            holder.leftMsg=(TextView) row.findViewById(R.id.left_message);
            holder.rightMsg=(TextView) row.findViewById(R.id.right_message);
           // holder.rightImg=(ImageView) row.findViewById(R.id.image);
            holder.leftView=(LinearLayout) row.findViewById(R.id.id_left_view);
            holder.rightView=(LinearLayout) row.findViewById(R.id.id_right_view);
            row.setTag(holder);
		} else {
            holder=(ViewHolder)row.getTag();
        }

        MsgUserData msg = getItem(position);
        if (mUserId == msg.getUser_id()) {
            holder.leftView.setVisibility(View.GONE);
            holder.rightView.setVisibility(View.VISIBLE);
            holder.rightMsg.setText(msg.getMessage());
            holder.rightMsg.setBackgroundResource(R.drawable.bubble_green);
        } else {
            holder.rightView.setVisibility(View.GONE);
            holder.leftView.setVisibility(View.VISIBLE);
            holder.leftMsg.setText(msg.getMessage());
            holder.rightMsg.setBackgroundResource(R.drawable.bubble_yellow);
        }

		return row;
	}

}