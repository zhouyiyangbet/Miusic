package com.example.miusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.miusic.R;
import com.example.miusic.utils.UserUtils;
import com.example.miusic.views.InputView;

public class LoginActivity extends BaseActivity {

    private InputView mInputPhone, mInputPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        initNavBar(false, "Login", false);
        mInputPhone = fd(R.id.input_phone);
        mInputPwd = fd(R.id.input_password);
    }

    /**
     * redirect to register page
     */
    public void onRegisterClick(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * log in
     * @param v
     */
    public void onCommitClick(View v) {
        String phone = mInputPhone.getInputString();
        String pwd = mInputPwd.getInputString();
        if (!UserUtils.validateLogin(this, phone, pwd)) {
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
