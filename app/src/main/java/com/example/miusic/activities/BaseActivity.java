package com.example.miusic.activities;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miusic.R;

public class BaseActivity extends Activity {

    private ImageView mBack, mMe;
    private TextView mTextView;

    /**
     * find view by id
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T fd(@IdRes int id) {
        return findViewById(id);
    }

    /**
     * init nav bar
     * @param isShowBack
     * @param title
     * @param isShowMe
     */
    protected void initNavBar(boolean isShowBack, String title, boolean isShowMe) {
        mBack = fd(R.id.iv_back);
        mTextView = fd(R.id.tv_title);
        mMe = fd(R.id.iv_me);

        mBack.setVisibility(isShowBack ? View.VISIBLE : View.GONE);
        mMe.setVisibility(isShowMe ? View.VISIBLE : View.GONE);
        mTextView.setText(title);

        mBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mMe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, MeActivity.class));
            }
        });
    }
}

