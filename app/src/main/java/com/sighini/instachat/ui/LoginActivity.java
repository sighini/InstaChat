package com.sighini.instachat.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.sighini.instachat.R;
import com.sighini.instachat.dto.UserData;
import com.sighini.instachat.dto.UserResponsePojo;
import com.sighini.instachat.util.ConnectionManager;
import com.sighini.instachat.util.Constants;
import com.sighini.instachat.util.LoginInfo;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private Button mBtnSignIn = null;
    private Button mBtnSignUp = null;
//    private SignInInputFragment signInFragment = null;
//    private SignUpInputFragment signUpFragment = null;

    private FrameLayout mSignInFrameLayout = null;
    private FrameLayout mSignUpFrameLayout= null;

    private final SignUpInputFragment mSignUpFragment = new SignUpInputFragment();
    private final SignInInputFragment mSignInFragment = new SignInInputFragment();

    FragmentManager mFragmentManager = getFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mBtnSignIn = (Button) findViewById(R.id.id_btn_signin);
        mBtnSignUp = (Button) findViewById(R.id.id_btn_signup);

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.login_input_container, mSignInFragment);
        fragmentTransaction.commit();

        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSignUpFragment.isAdded()) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.login_input_container, mSignInFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                mBtnSignIn.setTextColor(getResources().getColor(R.color.white));
                mBtnSignUp.setTextColor(getResources().getColor(R.color.gray));
                LoginInfo data = getLoginInfo();
                //Network request
                ConnectionManager.sendRequest(LoginActivity.this, sHandler,
                        ConnectionManager.RequestType.LOGIN, data);
            }
        });

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSignInFragment.isAdded()) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.login_input_container, mSignUpFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                mSignUpFragment.resetState();
                mBtnSignIn.setTextColor(getResources().getColor(R.color.gray));
                mBtnSignUp.setTextColor(getResources().getColor(R.color.white));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra(Constants.TOKEN, userData.getToken());
                    intent.putExtra(Constants.CURRENT_USER_ID, userData.getId());
                    Log.e(TAG, "user Id" + userData.getId());
                    startActivity(intent);
                    mSignInFragment.resetInfo();

                } else {
                    Log.e(TAG, "Could not start home activity");
                }
            } else {
                Log.e(TAG, "Null response");
            }
            return true;
        }
    });

    public LoginInfo getLoginInfo() {
        return mSignInFragment.getLoginInfo();
    }
}
