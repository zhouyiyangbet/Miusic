package com.example.miusic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.miusic.constants.SPConstants;
import com.example.miusic.helpers.UserHelper;

/**
 * SharedPreference Util
 */
public class SPUtils {


    public static boolean saveUser(Context context, String phone) {
        SharedPreferences sp = context.getSharedPreferences(SPConstants.SP_NAME_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SPConstants.SP_KEY_PHONE, phone);
        return editor.commit();
    }

    public static boolean isLoginUser(Context context) {
        boolean result = false;
        SharedPreferences sp = context.getSharedPreferences(SPConstants.SP_NAME_USER, Context.MODE_PRIVATE);
        String phone = sp.getString(SPConstants.SP_KEY_PHONE, "");
        if (!TextUtils.isEmpty(phone)) {
            result = true;
            UserHelper.getInstance().setPhone(phone);
        }
        return result;
    }

    /**
     * delete user tag
     */
    public static boolean removeUser(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SPConstants.SP_NAME_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(SPConstants.SP_KEY_PHONE);
        return editor.commit();
    }
}
