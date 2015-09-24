package com.sighini.instachat.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.sighini.instachat.R;
import com.sighini.instachat.dto.ChatResponsePojo;
import com.sighini.instachat.dto.ChatUserData;
import com.sighini.instachat.util.ChatArrayAdapter;
import com.sighini.instachat.util.ConnectionManager;
import com.sighini.instachat.util.Constants;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private static final String TAG = HomeActivity.class.getSimpleName();
    private ListView mChatListView = null;
    private ChatArrayAdapter mAdapter = null;
    private String mToken = null;
    private int mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mChatListView = (ListView) findViewById(R.id.chat_room_list);
        mToken = getIntent().getExtras().getString(Constants.TOKEN);
        mUserId = getIntent().getExtras().getInt(Constants.CURRENT_USER_ID);
        getChatList(mToken);

    }

    private void getChatList(String token) {
        ConnectionManager.sendRequest(this, sHandler,
                ConnectionManager.RequestType.GET_CHAT_LIST, token);
    }


    private Handler sHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle b = msg.getData();
            String response = b.getString(Constants.RESPONSE);
            if (response != null) {
                ChatResponsePojo resp = new Gson().fromJson(response, ChatResponsePojo.class);
                Log.e(TAG, "ChatResponsePojo:" + resp.toString());
//                mchatListData = resp.getData();
//                ArrayList<String> chatList = new ArrayList<>();
//                for(ChatUserData chatRoom : mchatListData) {
//                    chatList.add(chatRoom.getName());
//                }

                if (resp.getSuccess()) {
                    mAdapter = new ChatArrayAdapter(HomeActivity.this,
                            android.R.layout.simple_list_item_1, resp.getData());
                    mChatListView.setAdapter(mAdapter);
                    mChatListView.setOnItemClickListener(HomeActivity.this);
                } else {
                    //TODO: Display error
                }

            } else {
                Log.e(TAG, "Null response");
            }
            return true;
        }
    });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_account:
                Intent intent = new Intent(this, AccountActivity.class);
                intent.putExtra(Constants.TOKEN, mToken);
                startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ChatUserData chat = mAdapter.getItem(position);
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra(Constants.TOKEN, mToken);
        intent.putExtra(Constants.CHAT_ID, chat.getId());
        intent.putExtra(Constants.CHAT_NAME, chat.getName());
        intent.putExtra(Constants.CURRENT_USER_ID, mUserId);
        Log.e(TAG, "user Id" + mUserId);
        startActivity(intent);
    }
}
