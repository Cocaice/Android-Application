package com.example.yzh.lab8;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private MusicService musicService;
    private ServiceConnection serviceConnection;
    MusicService.MyBinder myBinder;
    private int code;
    private Parcel data;
    private Parcel reply;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar);
        final Button play = (Button)findViewById(R.id.playbutton);
        Button stop = (Button)findViewById(R.id.stopbutton);
        Button quit = (Button)findViewById(R.id.quitbutton);
        final TextView playtime = (TextView) findViewById(R.id.begin);
        final TextView alltime = (TextView) findViewById(R.id.end);
        final ImageView imageView = (ImageView) findViewById(R.id.cover);
        final TextView state = (TextView) findViewById(R.id.state);
        //添加动画
        final ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotation", 0,360);
        animator.setDuration(8000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        animator.setInterpolator(linearInterpolator);


        verifyStoragePermissions(this);
        //更新UI
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                super.handleMessage(message);
                try {
                    code = 104;
                    data = Parcel.obtain();
                    reply = Parcel.obtain();
                    myBinder.transact(code, data, reply, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                playtime.setText(ShowTime(musicService.mediaPlayer.getCurrentPosition()));
                alltime.setText(ShowTime(musicService.mediaPlayer.getDuration()));
                seekBar.setMax(musicService.mediaPlayer.getDuration());
                seekBar.setProgress(musicService.mediaPlayer.getCurrentPosition());

            }
        };

        final Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.obtainMessage().sendToTarget();
                }
            }
        };

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder = (MusicService.MyBinder) service;
                musicService = ((MusicService.MyBinder) service).getService();
                thread.start();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

                serviceConnection = null;
            }
        };

        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thread.isAlive() == false) {
                    thread.start();
                    animator.start();
                }
                play.setText(musicService.isPlaying() ? "Play" : "Pause");
                state.setText(musicService.isPlaying() ? "Pause" : "Playing");
                if (musicService.isPlaying()) {
                    animator.pause();
                } else {
                    if (animator.isRunning()) {
                        animator.resume();
                    } else {
                        animator.start();
                    }
                }
                try {
                    code = 101;
                    data = Parcel.obtain();
                    reply = Parcel.obtain();
                    myBinder.transact(code, data, reply, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thread.isAlive() == false) {
                    thread.start();
                }
                try {
                    code = 102;
                    myBinder.transact(code, data, reply, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                play.setText("Play");
                state.setText("Stop");
                animator.end();


            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    code = 103;
                    myBinder.transact(code, data, reply, 0);
                } catch (Exception e) {
                    e.printStackTrace();

                }

                unbindService(serviceConnection);
                System.exit(0);
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser == true) {
                    musicService.mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (thread.isAlive() == false) {
                    thread.start();
                }
                try {
                    code = 105;
                    Parcel tran_data = Parcel.obtain();
                    tran_data.writeInt(seekBar.getProgress());
                    myBinder.transact(code, tran_data, reply, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });





    }

    public String ShowTime(int time) {
        time /= 1000;
        int minute = time / 60;
        int second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }
}
