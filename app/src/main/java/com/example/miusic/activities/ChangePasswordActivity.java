package com.example.miusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.miusic.R;
import com.example.miusic.utils.UserUtils;
import com.example.miusic.views.InputView;

public class ChangePasswordActivity extends BaseActivity {

    private InputView mOldPassword, mPassword, mConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initView();
    }

    private void initView() {
        initNavBar(true, "Change Password", false);

        mOldPassword = fd(R.id.old_password);
        mPassword = fd(R.id.input_password);
        mConfirm = fd(R.id.input_password_confirm);


    }

    public void onChangeClick(View v) {
        String old = mOldPassword.getInputString();
        String newPassword = mPassword.getInputString();
        String confirm = mConfirm.getInputString();

        boolean res = UserUtils.changePassword(this, old, newPassword, confirm);
        if (!res) {
            return;
        }
        UserUtils.logout(this);
    }
}
