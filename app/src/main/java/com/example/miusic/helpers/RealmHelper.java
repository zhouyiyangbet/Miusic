package com.example.miusic.helpers;

import android.content.Context;

import com.example.miusic.migration.Migration;
import com.example.miusic.models.AlbumModel;
import com.example.miusic.models.MusicModel;
import com.example.miusic.models.MusicSourceModel;
import com.example.miusic.models.UserModel;
import com.example.miusic.utils.DataUtils;

import java.io.FileNotFoundException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmHelper {

    private Realm mRealm;

    public RealmHelper() {
        mRealm = Realm.getDefaultInstance();
    }

    /**
     * migration of the Realm db when the structure of Realm changes
     */
    private static RealmConfiguration getRealmConf() {
        return new RealmConfiguration.Builder()
                   .schemaVersion(1)
                   .migration(new Migration())
                   .build();
    }

    /**
     * notify realm to migrate and set latest config for realm
     */
    public static void migration() {
        //get latest config of realm
        RealmConfiguration conf = getRealmConf();
        Realm.setDefaultConfiguration(conf);
        //notify realm to migrate
        try {
            Realm.migrateRealm(conf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * close realm
     */

    public void close() {
        if (mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
        }
    }

    /**
     * save user info
     */

    public void saveUser(UserModel userModel) {
        mRealm.beginTransaction();
        mRealm.insert(userModel);
        mRealm.commitTransaction();
    }

    /**
     * get all users
     */
    public List<UserModel> getAllUsers() {
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        RealmResults<UserModel> models = query.findAll();
        return models;
    }

    public boolean validateUser(String phone, String password) {
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        query = query.equalTo("phone", phone).equalTo("password", password);
        UserModel userModel = query.findFirst();
        if (userModel != null) {
            return true;
        }
        return false;
    }

    /**
     * get current model
     */
    public UserModel getUser() {
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        UserModel userModel= query.equalTo("phone", UserHelper.getInstance().getPhone()).findFirst();
        return userModel;
    }

    /**
     * change password
     */
    public void changePassword(String password) {
        UserModel userModel = getUser();
        mRealm.beginTransaction();
        userModel.setPassword(password);
        mRealm.commitTransaction();
    }

    /**
     * save metadata of music
     */
    public void setMusicSource(Context context) {
        String musicSourceJson = DataUtils.getJsonFromAssets(context, "DataSource.json");
        mRealm.beginTransaction();
        mRealm.createObjectFromJson(MusicSourceModel.class, musicSourceJson);
        mRealm.commitTransaction();
    }

    public void removeMusicSource() {
        mRealm.beginTransaction();
        mRealm.delete(MusicModel.class);
        mRealm.delete(AlbumModel.class);
        mRealm.delete(MusicSourceModel.class);
        mRealm.commitTransaction();
    }

    /**
     * return music source data
     */
    public MusicSourceModel getMusicSource() {
        return mRealm.where(MusicSourceModel.class).findFirst();
    }

    /**
     * return album list
     */
    public AlbumModel getAlbum(String albumId) {
        return mRealm.where(AlbumModel.class).equalTo("albumId", albumId).findFirst();
    }

    /**
     * return music
     */
    public MusicModel getMusic(String musicId) {
        return mRealm.where(MusicModel.class).equalTo("musicId", musicId).findFirst();
    }
}
