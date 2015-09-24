package com.sighini.instachat.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;
import com.sighini.instachat.R;
import com.sighini.instachat.dto.MessageResponsePojo;
import com.sighini.instachat.dto.MsgUserData;
import com.sighini.instachat.dto.NewMsgResponsePojo;
import com.sighini.instachat.util.ConnectionManager;
import com.sighini.instachat.util.Constants;
import com.sighini.instachat.util.MessageArrayAdapter;


public class MessageActivity extends AppCompatActivity {
    private static final String TAG = MessageActivity.class.getSimpleName();
    private ListView mListView;
    private EditText mNewChatText;
    private MessageArrayAdapter mAdapter;
    private int mChatId;
    private String mChatName;
    private int mUserId;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

        mChatId = getIntent().getExtras().getInt(Constants.CHAT_ID);
        mChatName = getIntent().getExtras().getString(Constants.CHAT_NAME);
        mUserId = getIntent().getExtras().getInt(Constants.CURRENT_USER_ID);
        Log.e(TAG, "user Id" + mUserId);
        setTitle(mChatName);

		mListView = (ListView) findViewById(R.id.id_message_list);

		mNewChatText = (EditText) findViewById(R.id.id_new_msg);
		mNewChatText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    createNewMessage();

                    return true;
                }
                return false;
            }
        });

        ImageButton sendBtn = (ImageButton) findViewById(R.id.id_send);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewMessage();
            }
        });
		addItems();
	}

    private void createNewMessage() {
        String[] request = new String[] {
                mChatId+"",
                mNewChatText.getText().toString()
        };
        ConnectionManager.sendRequest(MessageActivity.this, sNewMsgHandler,
                ConnectionManager.RequestType.CREATE_MSG, request);

    }

    private void addItems() {
        ConnectionManager.sendRequest(this, sMessageListHandler,
                ConnectionManager.RequestType.GET_MESSAGE_LIST, mChatId+"");
	}

    private Handler sMessageListHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(android.os.Message msg) {
            Bundle b = msg.getData();
            String response = b.getString(Constants.RESPONSE);
            if (response != null) {
                MessageResponsePojo resp = new Gson().fromJson(response, MessageResponsePojo.class);

                if (resp.getSuccess()) {
                    mAdapter = new MessageArrayAdapter(MessageActivity.this,
                            R.layout.message_list_item, resp.getData(), mUserId);
                    mListView.setAdapter(mAdapter);
                } else {
                    //new AlertDialog()
                }

            } else {
                Log.e(TAG, "Null response");
            }
            return true;
        }
    });

    private Handler sNewMsgHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(android.os.Message msg) {
            Bundle b = msg.getData();
            String response = b.getString(Constants.RESPONSE);
            if (response != null) {
                NewMsgResponsePojo resp = new Gson().fromJson(response, NewMsgResponsePojo.class);
                Log.e(TAG, "NewMsgResponsePojo:" + resp.toString());
                if (resp.getSuccess()) {
                    //TODO:
                    MsgUserData data = resp.getData();
                    data.setMessage(mNewChatText.getText().toString());
                    mNewChatText.setText("");
                    ////
                    mAdapter.add(data);
                } else {
                    //new AlertDialog()
                    Log.e(TAG, "NewMsgResponsePojo: Error");
                }

            } else {
                Log.e(TAG, "Null response");
            }
            return true;
        }
    });
}