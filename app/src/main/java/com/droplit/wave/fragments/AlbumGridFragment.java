package com.droplit.wave.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.GridView;
import android.widget.Toast;

import com.droplit.wave.R;
import com.droplit.wave.adapters.AlbumAdapter;
import com.droplit.wave.adapters.AlbumGridAdapter;
import com.droplit.wave.models.Album;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import xyz.danoz.recyclerviewfastscroller.sectionindicator.title.SectionTitleIndicator;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class AlbumGridFragment extends Fragment {

    private GridView albumView;

    private ArrayList<Album> mAlbumItems = new ArrayList<>();
    private AlbumGridAdapter mAdapter;

    public static AlbumFragment newInstance() {
        return new AlbumFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grid_albums, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAlbumItems = new ArrayList<Album>();
        albumView = (GridView) view.findViewById(R.id.album_grid_list);

        getAlbumList();



        mAdapter = new AlbumGridAdapter(getActivity().getApplicationContext(), mAlbumItems);
        albumView.setAdapter(mAdapter);


    }

    public void getAlbumList() {
        //retrieve song info
        ContentResolver musicResolver = getActivity().getContentResolver();
        final String[] cursor_cols = {
                MediaStore.Audio.AlbumColumns.ARTIST, MediaStore.Audio.AlbumColumns.ALBUM,
                MediaStore.Audio.AlbumColumns.ALBUM_ART, MediaStore.Audio.AlbumColumns.ALBUM_KEY,
                MediaStore.Audio.AlbumColumns.FIRST_YEAR, MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS};
        //final String where = MediaStore.Audio.Media.IS_MUSIC + "=1";

        Uri musicUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri,cursor_cols,null,null,MediaStore.Audio.AlbumColumns.ALBUM);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int trackNumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.ALBUM_KEY);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.ARTIST);
            int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.ALBUM);
            int albumYear = musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.FIRST_YEAR);
            int artColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM_ART);

            //add songs to list
            do {
                String thisArtPath = musicCursor.getString(artColumn);


                long thisId = musicCursor.getLong(idColumn);
                int thisTrack = musicCursor.getInt(trackNumColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisAlbum = musicCursor.getString(albumColumn);
                String thisYear = musicCursor.getString(albumYear);
                //Log.d("ART", "Album art: " + thisArtPath);
                mAlbumItems.add(new Album(thisId, thisAlbum, thisArtist, thisTrack, thisYear, thisArtPath));
            }

            while (musicCursor.moveToNext());
        }

    }

}