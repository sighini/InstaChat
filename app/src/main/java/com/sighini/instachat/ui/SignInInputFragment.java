package com.sighini.instachat.ui;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sighini.instachat.R;
import com.sighini.instachat.util.Constants;
import com.sighini.instachat.util.LoginInfo;

public class SignInInputFragment extends Fragment {

    EditText mEmail = null;
    EditText mPasswd = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signin_input, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEmail = (EditText) getActivity().findViewById(R.id.id_signin_email);
        mPasswd = (EditText) getActivity().findViewById(R.id.id_signin_passwd);
    }

    public LoginInfo getLoginInfo() {
        LoginInfo info = new LoginInfo();
        if (Constants.isTestMode) {
            mEmail.setText("andre@orainteractive.com");
            mPasswd.setText("Test123");
        }
        info.setEmail(mEmail.getText().toString());
        info.setPassword(mPasswd.getText().toString());
        return info;
    }

    public void resetInfo() {
        mEmail.setText("");
        mPasswd.setText("");
    }
}
