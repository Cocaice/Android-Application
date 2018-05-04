package com.example.yzh.lab8;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public class MusicService extends Service {
    public MediaPlayer mediaPlayer = new MediaPlayer();
    public final IBinder binder = new MyBinder();
    public MusicService() {
        try {
            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory() + "/Music/melt.mp3");
            mediaPlayer.prepare();
            //mediaPlayer.setLooping(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }
    public class MyBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 101:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    } else {
                        mediaPlayer.start();
                    }
                    break;

                case 102:
                    mediaPlayer.stop();
                    try {
                        mediaPlayer.prepare();
                        mediaPlayer.seekTo(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 103:
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    break;

                case 104:
                    break;

                case 105:
                    int i= data.readInt();
                    mediaPlayer.seekTo(i);
                    break;
            }
            return super.onTransact(code, data, reply, flags);
        }
    }

    @Override
    public boolean onUnbind(Intent intent){
        mediaPlayer.stop();
        mediaPlayer.release();
        return false;
    }
    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

}

