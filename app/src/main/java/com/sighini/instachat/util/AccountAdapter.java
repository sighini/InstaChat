package com.sighini.instachat.util;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AccountAdapter extends ArrayAdapter<String> {

	private String[] items;
	private Context _context = null;
	private int position = 0;
	//Wrapper wrapper = null;

    public AccountAdapter(Context context, int resource) {
        super(context, resource);
    }

//	public AccountAdapter(Context context, int textViewResourceId,
//			String[] objects, int pos) {
//		super(context, textViewResourceId, objects);
//
//		items = objects;
//		_context = context;
//		position = pos;
//	}

//	@Override
//	public View getDropDownView(int position, View convertView, ViewGroup parent) {
//
//		if (convertView == null) {
//			LayoutInflater vi = (LayoutInflater) _context
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			convertView = vi.inflate(R.layout.spinner_item, null);
//			wrapper = new Wrapper(convertView);
//			convertView.setTag(wrapper);
//		} else {
//			wrapper = (Wrapper) convertView.getTag();
//		}
//		final View touchView = convertView;
//		wrapper.getMethod().setText(items[position]);
//
//		if (this.position == position) {
//
//			int color = Color.parseColor("#BABEBF");
//			wrapper.getMethod().setBackgroundColor(color);
//		} else {
//			wrapper.getMethod().setBackgroundColor(Color.WHITE);
//		}
//
//		touchView.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//
//		    	RelativeLayout methodLayout = (RelativeLayout) ((ContactUsActivity)_context).findViewById(R.id.layoutmethod);
//		    	methodLayout.requestFocusFromTouch();
//
//				switch (event.getAction()) {
//
//				case MotionEvent.ACTION_DOWN:
//					int color = Color.parseColor("#808285");
//					touchView.setBackgroundColor(color);
//					break;
//				case MotionEvent.ACTION_UP:
//					touchView.setBackgroundColor(Color.WHITE);
//					break;
//				}
//
//				return false;
//			}
//
//		});
//		return wrapper.getMethod();
//
//	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		this.position = position;
		return super.getView(position, convertView, parent);
	}

//	public class Wrapper {
//		View _row;
//
//		TextView method;
//
//		public TextView getMethod() {
//			if (null == method)
//				method = (TextView) _row.findViewById(R.id.text_topic);
//
//			return method;
//
//		}
//
//		public Wrapper(View row) {
//			_row = row;
//		}
//	}
}