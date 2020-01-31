package com.example.miusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.miusic.R;
import com.example.miusic.adapters.MiusicGridAdapter;
import com.example.miusic.adapters.MusicListAdapter;
import com.example.miusic.helpers.RealmHelper;
import com.example.miusic.models.MusicSourceModel;
import com.example.miusic.views.GridSpaceItemDecoration;

import io.realm.Realm;

public class MainActivity extends BaseActivity {

    private RecyclerView mRvGrid, mRvList;
    private MiusicGridAdapter mAdapter;
    private MusicListAdapter mListAdapter;
    private RealmHelper mRealmHelper;
    private MusicSourceModel mMusicSourceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        mRealmHelper = new RealmHelper();
        mMusicSourceModel = mRealmHelper.getMusicSource();
    }

    private void initView() {
        initNavBar(false, "Miusic", true);

        mRvGrid = fd(R.id.rv_grid);
        mRvGrid.setLayoutManager(new GridLayoutManager(this, 3));
        mRvGrid.addItemDecoration(new GridSpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.albumMarginSize), mRvGrid));
        mRvGrid.setNestedScrollingEnabled(false);
        mAdapter = new MiusicGridAdapter(this, mMusicSourceModel.getAlbum());
        mRvGrid.setAdapter(mAdapter);

        mRvList = fd(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setNestedScrollingEnabled(false);
        mRvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mListAdapter = new MusicListAdapter(this, mRvList, mMusicSourceModel.getHot());
        mRvList.setAdapter(mListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealmHelper.close();
    }
}
