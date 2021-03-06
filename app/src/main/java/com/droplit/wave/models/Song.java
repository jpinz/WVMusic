package com.droplit.wave.models;

import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

/**
 * A class that represents a song.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public class Song {

    /**
     * The unique Id of the song
     */
    public long mSongId;

    /**
     * The song name
     */
    public String mSongName;

    /**
     * The song artist
     */
    public String mArtistName;

    /**
     * The song album
     */
    public String mAlbumName;

    /**
     * The song duration in seconds
     */
    public int mDuration;

    /**
     * Constructor of <code>Song</code>
     *
     * @param songId The Id of the song
     * @param songName The name of the song
     * @param artistName The song artist
     * @param albumName The song album
     * @param duration The duration of a song in seconds
     */
    public Song(final long songId, final String songName, final String artistName,
                final String albumName, final int duration) {
        mSongId = songId;
        mSongName = songName;
        mArtistName = artistName;
        mAlbumName = albumName;
        mDuration = duration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (mAlbumName == null ? 0 : mAlbumName.hashCode());
        result = prime * result + (mArtistName == null ? 0 : mArtistName.hashCode());
        result = prime * result + mDuration;
        result = prime * result + (int) mSongId;
        result = prime * result + (mSongName == null ? 0 : mSongName.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Song other = (Song) obj;
        if (mSongId != other.mSongId) {
            return false;
        }
        if (!TextUtils.equals(mAlbumName, other.mAlbumName)) {
            return false;
        }
        if (!TextUtils.equals(mArtistName, other.mArtistName)) {
            return false;
        }
        if (mDuration != other.mDuration) {
            return false;
        }
        if (!TextUtils.equals(mSongName, other.mSongName)) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return mSongName;
    }

    public long getID() {
        return mSongId;
    }

    public String getTitle() {
        return mSongName;
    }

    public String getArtist() {
        if (mArtistName.equals("<unknown>")) {
            return "unknown";
        }
        return mArtistName;
    }

    public String getDuration() {
        String thisDuration = String.format("%d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(mDuration),
                TimeUnit.MILLISECONDS.toSeconds(mDuration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mDuration))
        );
        return thisDuration;
    }

    public String getAlbumName() {
        return mAlbumName;
    }
}