package com.example.miusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.miusic.R;
import com.example.miusic.helpers.UserHelper;
import com.example.miusic.utils.UserUtils;

public class MeActivity extends BaseActivity {
    private TextView mTvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        initView();
    }

    private void initView() {
        initNavBar(true, "My Info", false);
        mTvUser = fd(R.id.tv_user);
        mTvUser.setText("Username: " + UserHelper.getInstance().getPhone());
    }

    /**
     * change password
     */

    public void onChangeClick(View v) {
        startActivity(new Intent(this, ChangePasswordActivity.class));
    }

    /**
     * log out
     * @param v
     */
    public void onLogoutClick(View v) {
        UserUtils.logout(this);
    }
}
