package com.example.miusic.migration;

import com.example.miusic.models.MusicSourceModel;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class Migration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        /**
         *     private String musicId;
         *     private String name;
         *     private String poster;
         *     private String path;
         *     private String author;
         */
        if (oldVersion == 0) {

            schema.create("MusicModel")
                    .addField("musicId", String.class)
                    .addField("name", String.class)
                    .addField("poster", String.class)
                    .addField("path", String.class)
                    .addField("author", String.class);
            /**
             *     private String albumId;
             *     private String name;
             *     private String poster;
             *     private String playNum;
             *     private String title;
             *     private String intro;
             *     private RealmList<MusicModel> list;
             */
            schema.create("AlbumModel")
                    .addField("albumId", String.class)
                    .addField("name", String.class)
                    .addField("poster", String.class)
                    .addField("playNum", String.class)
                    .addField("title", String.class)
                    .addField("intro", String.class)
                    .addRealmListField("list", schema.get("MusicModel"));

            schema.create("MusicSourceModel")
                    .addRealmListField("album", schema.get("AlbumModel"))
                    .addRealmListField("hot", schema.get("MusicModel"));
            oldVersion = newVersion;
        }
    }
}
