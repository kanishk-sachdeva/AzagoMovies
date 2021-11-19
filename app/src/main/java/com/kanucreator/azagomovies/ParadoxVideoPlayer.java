package com.kanucreator.azagomovies;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static android.view.View.VISIBLE;

public class ParadoxVideoPlayer extends AppCompatActivity {

    SimpleExoPlayer player;
    PlayerView playerView;
    ImageView azagologo;

    LottieAnimationView loading;
    RelativeLayout topbarlayout;
    LinearLayout topfeatureslayout;

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    private boolean iserror = false;

    ImageButton backbtn,settingsbtn,pip_icon;
    String name,imdbid;

    Context context;
    TextView videonametext,videodurationtext;

    int HI_BITRATE = 2097152;
    int MI_BITRATE = 1048576;
    int LO_BITRATE = 524288;

    PlayerNotificationManager playerNotificationManager;

    private PlayerNotificationManager.MediaDescriptionAdapter mediaDescriptionAdapter = new PlayerNotificationManager.MediaDescriptionAdapter() {
        @Override
        public String getCurrentSubText(Player player) {
            return "Sub text";
        }

        @Override
        public String getCurrentContentTitle(Player player) {
            return "Title";
        }

        @Override
        public PendingIntent createCurrentContentIntent(Player player) {
            return null;
        }

        @Override
        public String getCurrentContentText(Player player) {
            return "ContentText";
        }

        @Override
        public Bitmap getCurrentLargeIcon(Player player, PlayerNotificationManager.BitmapCallback callback) {
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paradox_video_player);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        playerView = findViewById(R.id.video_view);
        context = this;
        backbtn = findViewById(R.id.videoplayerbackbtn);
        topfeatureslayout = findViewById(R.id.topfeatureslayout);

        loading = findViewById(R.id.loading);
        settingsbtn = findViewById(R.id.settingsbtn);
        pip_icon = findViewById(R.id.pip_icon);

        topbarlayout = findViewById(R.id.topbarlayout);

        name = getIntent().getStringExtra("name");
        imdbid = getIntent().getStringExtra("imdbid");
        azagologo = findViewById(R.id.azagologo);


        videonametext = findViewById(R.id.videonametext);
        videodurationtext = findViewById(R.id.videodurationtext);

        videonametext.setText(name);

        pip_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterpipmode();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        notificationmanager();

    }

    private void notificationmanager() {

        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(this,"3" , R.string.app_name, createID(), mediaDescriptionAdapter, new PlayerNotificationManager.NotificationListener() {
            @Override
            public void onNotificationPosted(int notificationId, Notification notification, boolean ongoing) {
            }

            @Override
            public void onNotificationCancelled(int notificationId, boolean dismissedByUser) {
            }
        });
        playerNotificationManager.setPlayer(player);

    }

    private void enterpipmode() {

        if (!iserror){
            if (player!= null){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)){
                    long curpos = player.getCurrentPosition();
                    playerView.setUseController(false);
                    topbarlayout.setVisibility(View.GONE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        PictureInPictureParams params = new PictureInPictureParams.Builder().build();
                        this.enterPictureInPictureMode(params);
                    }else{
                        this.enterPictureInPictureMode();
                    }


                }else{
                    Toast.makeText(context, "Your device doesn't support Picture-in-Picture Mode !!", Toast.LENGTH_SHORT).show();
                }
            }else{
        }



        }
    }

    @Override
    public void onBackPressed() {
        if (!iserror){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)){
                enterpipmode();
            }else{
                super.onBackPressed();
            }
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        hideSystemUi();
    }



    private void initializePlayer() {
        String uri1 = getIntent().getStringExtra("link");

        final DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);
        trackSelector.setParameters(
                trackSelector.buildUponParameters().build());

        player = new SimpleExoPlayer.Builder(this)
                .setTrackSelector(trackSelector)
                .build();
        playerView.setPlayer(player);

        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(uri1));
        player.setMediaItem(mediaItem);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare();

        if (player.getPlaybackState() == player.STATE_BUFFERING) {
            loading.setVisibility(VISIBLE);
            playerView.hideController();
        }

        if (playerView.isControllerVisible()){

            videodurationtext.setVisibility(VISIBLE);
            videonametext.setVisibility(VISIBLE);

            topfeatureslayout.setVisibility(VISIBLE);
            azagologo.setVisibility(View.GONE);
        }else{
            videodurationtext.setVisibility(View.GONE);
            videonametext.setVisibility(View.GONE);

            topfeatureslayout.setVisibility(View.GONE);
            azagologo.setVisibility(VISIBLE);
        }

        playerView.setControllerVisibilityListener(new PlayerControlView.VisibilityListener() {
            @Override
            public void onVisibilityChange(int visibility) {
                if (visibility == VISIBLE){
                    videodurationtext.setVisibility(VISIBLE);
                    videonametext.setVisibility(VISIBLE);
                    azagologo.setVisibility(View.GONE);


                    topfeatureslayout.setVisibility(VISIBLE);
                }else{
                    videodurationtext.setVisibility(View.GONE);
                    videonametext.setVisibility(View.GONE);

                    topfeatureslayout.setVisibility(View.GONE);
                    azagologo.setVisibility(VISIBLE);
                }
            }
        });

        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case ExoPlayer.STATE_READY:
                        loading.setVisibility(View.GONE);

                        long millis = player.getDuration();

                        long hours = TimeUnit.MILLISECONDS.toHours(millis);
                        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);

                        String hour = String.valueOf(hours);
                        String min = String.valueOf(minutes);

                        String dur = hour +" hr "+min+" min";
                        videodurationtext.setText("Duration : "+ dur);




                        break;
                    case ExoPlayer.STATE_BUFFERING:
                        loading.setVisibility(VISIBLE);
                        playerView.hideController();
                        break;
                    case ExoPlayer.STATE_ENDED:
                        loading.setVisibility(View.GONE);
                        finish();
                        break;
                    case ExoPlayer.STATE_IDLE:
                        loading.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                iserror = true;
                Toast.makeText(context, "Something went wrong while playing : "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Testing Quality

        findViewById(R.id.settingsbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,settingsbtn);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());

                popupMenu.getMenu().findItem(R.id.autoquality).setEnabled(true);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int menuid = menuItem.getItemId();

                        if (menuid == R.id.highquality){
                            Toast.makeText(context, "High Quality", Toast.LENGTH_SHORT).show();

                            DefaultTrackSelector.Parameters parameters = trackSelector.buildUponParameters()
                                    .setMaxVideoBitrate(HI_BITRATE)
                                    .setForceHighestSupportedBitrate(true)
                                    .build();
                            trackSelector.setParameters(parameters);
                            return true;
                        }else if (menuid == R.id.mediumquality){
                            Toast.makeText(context, "Medium Quality", Toast.LENGTH_SHORT).show();

                            DefaultTrackSelector.Parameters parameters = trackSelector.buildUponParameters()
                                    .setMaxVideoBitrate(MI_BITRATE)
                                    .setForceHighestSupportedBitrate(true)
                                    .build();
                            trackSelector.setParameters(parameters);
                            return true;
                        }else if (menuid == R.id.lowquality){
                            Toast.makeText(context, "Low Quality", Toast.LENGTH_SHORT).show();

                            DefaultTrackSelector.Parameters parameters = trackSelector.buildUponParameters()
                                    .setMaxVideoBitrate(LO_BITRATE)
                                    .setForceHighestSupportedBitrate(true)
                                    .build();
                            trackSelector.setParameters(parameters);
                            return true;
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        hideSystemUi();

        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playerNotificationManager != null) {
            playerNotificationManager.setPlayer(null);
        }
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

        if (!iserror){
            enterpipmode();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }

    public int createID(){
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(now));
        return id;
    }



}