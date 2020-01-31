package com.example.miusic.helpers;


/**
 * SharedPreferences to store the tag of users
 */
public class UserHelper {

    private static UserHelper instance;

    private UserHelper() {

    }

    public static UserHelper getInstance() {
        if (instance == null) {
            synchronized (UserHelper.class) {
                if (instance == null) {
                    instance = new UserHelper();
                }
            }
        }
        return instance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;

}
