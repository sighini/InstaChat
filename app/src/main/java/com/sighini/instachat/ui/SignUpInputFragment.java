package com.sighini.instachat.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.sighini.instachat.R;
import com.sighini.instachat.dto.UserData;
import com.sighini.instachat.dto.UserResponsePojo;
import com.sighini.instachat.util.ConnectionManager;
import com.sighini.instachat.util.Constants;
import com.sighini.instachat.util.User;

public class SignUpInputFragment extends Fragment {

    private static final String TAG = SignUpInputFragment.class.getSimpleName();

    private EditText mEditText = null;
    private User mUser = null;

    private enum State {
        ENTER_EMAIL,
        ENTER_NAME,
        CREATE_PASSWD,
        CONFIRM_PASSWD
    }

    State mState = State.ENTER_EMAIL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup_input, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mEditText = (EditText)getActivity().findViewById(R.id.id_signup_info);
        mEditText.setText(R.string.email_hint);
        mEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ;
                    changeState();
                    return true;
                }
                return false;
            }
        });
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setHint("");
            }
        });

        mUser = new User();
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    public void resetState(){
        mState = State.ENTER_EMAIL;
        if (mEditText != null) {
            mEditText.setText(R.string.email_hint);
        }
    }

    private void changeState() {
        switch(mState) {
            case ENTER_EMAIL:
                mState = State.ENTER_NAME;
                mUser.setEmail(mEditText.getText().toString());
                mEditText.setText(R.string.name_hint);
                break;

            case ENTER_NAME:
                mState = State.CREATE_PASSWD;
                mUser.setName(mEditText.getText().toString());
                mEditText.setText(R.string.create_passwd_hint);
                break;

            case CREATE_PASSWD:
                mState = State.CONFIRM_PASSWD;
                mUser.setPassword(mEditText.getText().toString());
                mEditText.setText(R.string.confirm_passwd_hint);
                break;

            case CONFIRM_PASSWD:
                //Show spinner

                //Network request
                ConnectionManager.sendRequest(getActivity(), sHandler,
                        ConnectionManager.RequestType.REGISTER_USER, mUser);
                break;
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
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.putExtra(Constants.TOKEN, userData.getToken());
                    intent.putExtra(Constants.CURRENT_USER_ID, userData.getId());
                    startActivity(intent);

                } else {
                    Log.e(TAG, "Could not start home activity");
                }
            } else {
                Log.e(TAG, "Null response");
            }
            return true;
        }
    });
}
