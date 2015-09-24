package com.sighini.instachat.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.sighini.instachat.R;
import com.sighini.instachat.dto.UserData;
import com.sighini.instachat.dto.UserResponsePojo;
import com.sighini.instachat.util.ConnectionManager;
import com.sighini.instachat.util.Constants;


public class EditAccountActivity extends AppCompatActivity {

    private static final String TAG = EditAccountActivity.class.getSimpleName();
    private String mToken;
    private EditText mEmail;
    private EditText mName;
    private EditText mPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        mToken = getIntent().getExtras().getString(Constants.TOKEN);

        mEmail = (EditText) findViewById(R.id.email);
        mName = (EditText) findViewById(R.id.name);
        mPasswd = (EditText) findViewById(R.id.password);
        ConnectionManager.sendRequest(this, sHandler,
                ConnectionManager.RequestType.VIEW_PROFILE, mToken);
//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setDisplayShowTitleEnabled(false);
//        View actionBarView = getLayoutInflater().inflate(R.layout.title_edit_profile, null);
//        actionBar.setCustomView(actionBarView);
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);


//        setListenerForActionBarCustomView(actionBarView);

    }

    private void setListenerForActionBarCustomView(View actionBarView) {
        ActionBarCustomViewOnClickListener actionBarCustomViewOnClickListener = new ActionBarCustomViewOnClickListener();
        actionBarView.findViewById(R.id.btn_cancel).setOnClickListener(actionBarCustomViewOnClickListener);
    }

    private class ActionBarCustomViewOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_cancel:
                    finish();
                    break;
                case R.id.btn_enter:
                    //Network
            }
        }
    }

    private Handler sHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle b = msg.getData();
            String response = b.getString(Constants.RESPONSE);
            if (response != null) {
                UserResponsePojo resp = new Gson().fromJson(response, UserResponsePojo.class);
                Log.e(TAG, "UserResponsePojo:" + resp.toString());
                UserData userData = resp.getData();
                if (userData != null) {
                    mEmail.setText(userData.getEmail());
                    mName.setText(userData.getName());
                    mPasswd.setText("********");

                } else {
                    Log.e(TAG, "Could not start home activity");
                }
            } else {
                Log.e(TAG, "Null response");
            }
            return true;
        }
    });
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_edit_account, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
