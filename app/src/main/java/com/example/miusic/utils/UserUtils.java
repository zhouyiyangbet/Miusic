package com.example.miusic.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.miusic.R;
import com.example.miusic.activities.LoginActivity;
import com.example.miusic.helpers.RealmHelper;
import com.example.miusic.helpers.UserHelper;
import com.example.miusic.models.UserModel;

import java.util.List;

public class UserUtils {
    /**
     * verify the input of user
     */
    public static boolean validateLogin(Context context, String phone, String password) {
        if (!RegexUtils.isMobileSimple(phone)) {
            Toast.makeText(context, "invalid phone number", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "please type in password", Toast.LENGTH_SHORT).show();
            return false;
        }

        /**
         * 1.if exists
         * 2.if the number and password is the same
         */
        if (!UserUtils.userExistFromPhone(phone)) {
            Toast.makeText(context, "need to register first", Toast.LENGTH_SHORT).show();
            return false;
        }
        RealmHelper realmHelper = new RealmHelper();
        boolean result = realmHelper.validateUser(phone, EncryptUtils.encryptMD5ToString(password));
        if (!result) {
            Toast.makeText(context, "password or phone number does not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean isSaved = SPUtils.saveUser(context, phone);
        if (!isSaved) {
            Toast.makeText(context, "Inner Error, try again later", Toast.LENGTH_SHORT).show();
            return false;
        }

        //save tag of user to singleton
        UserHelper.getInstance().setPhone(phone);
        realmHelper.setMusicSource(context);
        realmHelper.close();
        return result;
    }

    public static void logout(Context context) {
        //delete user tag in the sp
        boolean isRemoved = SPUtils.removeUser(context);

        if (!isRemoved) {
            Toast.makeText(context, "Inner Error, try again later", Toast.LENGTH_SHORT).show();
            return;
        }
        //delete the data source
        RealmHelper realmHelper = new RealmHelper();
        realmHelper.removeMusicSource();
        realmHelper.close();

        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //define the jump animation
        ((Activity)context).overridePendingTransition(R.anim.open_enter, R.anim.open_exit);
    }

    public static boolean registerUser(Context context, String phone, String password, String confirm) {
        if (!RegexUtils.isMobileSimple(phone)) {
            Toast.makeText(context, "invalid phone number", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (StringUtils.isEmpty(password) || !password.equals(confirm)) {
            Toast.makeText(context, "please confirm the password", Toast.LENGTH_SHORT).show();
            return false;
        }
        /**
         * 1.get all users registered
         */
        if (userExistFromPhone(phone)) {
            Toast.makeText(context, "Phone Number Already Exists", Toast.LENGTH_SHORT).show();
            return false;
        }
        UserModel userModel = new UserModel();
        userModel.setPhone(phone);
        userModel.setPassword(EncryptUtils.encryptMD5ToString(password));

        saveUser(userModel);

        return true;
    }

    /**
     * save user to realm
     */
    public static void saveUser(UserModel userModel) {
        RealmHelper helper = new RealmHelper();
        helper.saveUser(userModel);
        helper.close();
    }

    /**
     * verify if the user has existed
     */
    public static boolean userExistFromPhone(String phone) {
        boolean res = false;
        RealmHelper realmHelper = new RealmHelper();
        List<UserModel> allUser = realmHelper.getAllUsers();

        for (UserModel userModel : allUser) {
            if (userModel.getPhone().equals(phone)) {
                res = true;
                break;
            }
        }
        realmHelper.close();
        return res;
    }

    public static boolean validateUserLogin(Context context) {
        return SPUtils.isLoginUser(context);
    }

    /**
     * change password
     * 1.validate the original password
     * 2.new password is typed
     * 3.if the new password and confirm match
     */
    public static boolean changePassword(Context context, String old, String newPWd, String confirm) {

        if (TextUtils.isEmpty(old)) {
            Toast.makeText(context, "Please type in old password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(newPWd) || !newPWd.equals(confirm)) {
            Toast.makeText(context, "New Password does not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        RealmHelper realmHelper = new RealmHelper();
        UserModel userModel = realmHelper.getUser();

        if (!EncryptUtils.encryptMD5ToString(old).equals(userModel.getPassword())) {
            Toast.makeText(context, "Original Password Is Wrong", Toast.LENGTH_SHORT).show();
            return false;
        }

        realmHelper.changePassword(EncryptUtils.encryptMD5ToString(newPWd));
        realmHelper.close();

        return true;
    }


}
