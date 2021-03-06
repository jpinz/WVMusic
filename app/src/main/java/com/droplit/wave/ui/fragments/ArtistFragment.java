package com.droplit.wave.ui.fragments;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droplit.wave.R;
import com.droplit.wave.adapters.ArtistAdapter;
import com.droplit.wave.models.Artist;

import java.util.ArrayList;

import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;


public class ArtistFragment extends Fragment {

    private RecyclerView artistView;

    private ArrayList<Artist> mArtistItems = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_artists, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        mArtistItems = new ArrayList<Artist>();
        artistView = (RecyclerView) view.findViewById(R.id.artist_list);

        VerticalRecyclerViewFastScroller fastScroller = (VerticalRecyclerViewFastScroller) view.findViewById(R.id.fast_scroller);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        artistView.setLayoutManager(layoutManager);
        artistView.setHasFixedSize(true);

        getArtistList();

        mAdapter = new ArtistAdapter(getActivity().getApplicationContext(), mArtistItems);
        artistView.setAdapter(mAdapter);

        //artistView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.HORIZONTAL));

        fastScroller.setRecyclerView(artistView);

        // Connect the scroller to the recycler (to let the recycler scroll the scroller's handle)
        artistView.setOnScrollListener(fastScroller.getOnScrollListener());

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.menu_main,menu);
    }

    public void getArtistList() {
        //retrieve song info
        ContentResolver musicResolver = getActivity().getContentResolver();
        final String[] cursor_cols = {
                MediaStore.Audio.ArtistColumns.ARTIST, MediaStore.Audio.ArtistColumns.ARTIST_KEY,
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.ArtistColumns.NUMBER_OF_ALBUMS, MediaStore.Audio.ArtistColumns.NUMBER_OF_TRACKS};
        //final String where = MediaStore.Audio.Media.IS_MUSIC + "=1";

        Uri musicUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri,cursor_cols,null,null,null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int trackNumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.ArtistColumns.NUMBER_OF_TRACKS);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists._ID);
            int artistColumn =musicCursor.getColumnIndex
                    (MediaStore.Audio.ArtistColumns.ARTIST);

            int albumNumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.ArtistColumns.NUMBER_OF_ALBUMS);

            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                int thisTrack = musicCursor.getInt(trackNumColumn);
                int thisAlbum = musicCursor.getInt(albumNumColumn);
                String thisArtist = musicCursor.getString(artistColumn);

                mArtistItems.add(new Artist(thisId,thisArtist,thisTrack,thisAlbum));
            }

            while (musicCursor.moveToNext());
        }

    }
}