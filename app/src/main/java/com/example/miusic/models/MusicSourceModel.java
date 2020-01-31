package com.example.miusic.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;

@Data
public class MusicSourceModel extends RealmObject {

    public RealmList<AlbumModel> getAlbum() {
        return album;
    }

    public void setAlbum(RealmList<AlbumModel> album) {
        this.album = album;
    }

    public RealmList<MusicModel> getHot() {
        return hot;
    }

    public void setHot(RealmList<MusicModel> hot) {
        this.hot = hot;
    }

    private RealmList<AlbumModel> album;
    private RealmList<MusicModel> hot;

}
