package com.example.miusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.miusic.R;
import com.example.miusic.utils.UserUtils;
import com.example.miusic.views.InputView;

public class RegisterActivity extends BaseActivity {

    private InputView mInputPhone, mInputPassword, mInputConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        initNavBar(true, " Sign Up", false);

        mInputPhone = fd(R.id.input_phone);
        mInputPassword = fd(R.id.input_password);
        mInputConfirm = fd(R.id.input_password_confirm);

    }

    /**
     * 1.verify the input(phone number, password)
     * 2.save phone number and password(MD5 encrypted) to realm
     *
     * @param v
     */
    public void onRegisterClick(View v) {
        String phone = mInputPhone.getInputString();
        String password = mInputPassword.getInputString();
        String confirm = mInputConfirm.getInputString();

        boolean res = UserUtils.registerUser(this, phone, password, confirm);

        if (!res) {
            System.out.println("the password is invalid");
            return;
        }

        onBackPressed();
    }

}
