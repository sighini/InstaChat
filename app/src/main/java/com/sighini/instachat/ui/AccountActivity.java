package com.sighini.instachat.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sighini.instachat.R;
import com.sighini.instachat.util.Constants;

public class AccountActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private String mToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mToken = getIntent().getExtras().getString(Constants.TOKEN);
        ListView list = (ListView) findViewById(R.id.account_list);
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                        getResources().getStringArray(R.array.account_list) );
        list.setAdapter(itemsAdapter);
        list.setOnItemClickListener(this);

//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setDisplayShowTitleEnabled(false);
//        View actionBarView = getLayoutInflater().inflate(R.layout.title_edit_profile, null);
//        actionBar.setCustomView(actionBarView);
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);


        //setListenerForActionBarCustomView(actionBarView);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch(position) {
            case 0:
                intent = new Intent(this, EditAccountActivity.class);
                intent.putExtra(Constants.TOKEN, mToken);
                startActivity(intent);
                break;
            case 1:
                //Logout
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        //getMenuInflater().inflate(R.menu.menu_account, menu);
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
