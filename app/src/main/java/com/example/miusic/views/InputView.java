package com.example.miusic.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.miusic.R;

public class InputView extends FrameLayout {
    private int inputIcon;
    private String inputHint;
    private boolean isPwd;

    private View mView;
    private ImageView mIvIcon;
    private EditText mEtText;

    public InputView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public InputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public InputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public InputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * initialize
     */
    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        //get custom attrs
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.inputView);
        inputIcon = typedArray.getResourceId(R.styleable.inputView_input_icon, R.mipmap.logo);
        inputHint = typedArray.getString(R.styleable.inputView_input_hint);
        isPwd = typedArray.getBoolean(R.styleable.inputView_is_password, false);
        typedArray.recycle();

        mView = LayoutInflater.from(context).inflate(R.layout.input_view, this, false);
        mIvIcon = mView.findViewById(R.id.iv_icon);
        mEtText = mView.findViewById(R.id.et_input);

        mIvIcon.setImageResource(inputIcon);
        mEtText.setHint(inputHint);
        mEtText.setInputType(isPwd ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_PHONE);

        addView(mView);
    }

    public String getInputString() {
        return mEtText.getText().toString().trim();
    }
}
