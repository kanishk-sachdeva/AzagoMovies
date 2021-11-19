package com.kanucreator.azagomovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

public class ParadoxDashboard extends AppCompatActivity {

    BubbleNavigationLinearView bottom_navbar;
    FrameLayout paradoxframelayout;

    RelativeLayout nointernetlayout;
    Button retryone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paradox_dashboard);


        bottom_navbar = findViewById(R.id.bottom_navbar);
        paradoxframelayout = findViewById(R.id.paradoxframelayout);
        nointernetlayout = findViewById(R.id.nointernetlayout);

        retryone = findViewById(R.id.buttonforcancel);

        runagain();

        if (bottom_navbar.getCurrentActiveItemPosition() == 0){
            FragmentTransaction fragmentTransaction = ParadoxDashboard.this
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.paradoxframelayout, new HotPicksFragment());
            fragmentTransaction.commit();
        }else if (bottom_navbar.getCurrentActiveItemPosition() == 1){
            FragmentTransaction fragmentTransaction = ParadoxDashboard.this
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.paradoxframelayout, new AllFragment());
            fragmentTransaction.commit();
        }else if (bottom_navbar.getCurrentActiveItemPosition() == 2){
            FragmentTransaction fragmentTransaction = ParadoxDashboard.this
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.paradoxframelayout, new SavedFragment());
            fragmentTransaction.commit();
        }

        retryone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retryone.setText("Retrying .. (WAIT)");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checknetwork();
                    }
                },3000);
            }
        });


        bottom_navbar.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                if (position == 0){
                    FragmentTransaction fragmentTransaction = ParadoxDashboard.this
                            .getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.paradoxframelayout, new HotPicksFragment());
                    fragmentTransaction.commit();
                }else if (position == 1){
                    FragmentTransaction fragmentTransaction = ParadoxDashboard.this
                            .getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.paradoxframelayout, new AllFragment());
                    fragmentTransaction.commit();
                }else if (position == 2){
                    FragmentTransaction fragmentTransaction = ParadoxDashboard.this
                            .getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.paradoxframelayout, new SavedFragment());
                    fragmentTransaction.commit();
                }

            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int displayheight = displayMetrics.heightPixels;

        final LinearLayout layout = (LinearLayout)findViewById(R.id.bottom_navbar);
        final RelativeLayout toplayout = findViewById(R.id.toplayout);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                toplayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                int width  = layout.getMeasuredWidth();
                int height = layout.getMeasuredHeight();
                int height2 = toplayout.getMeasuredHeight();
//
//
//                ViewGroup.LayoutParams params = paradoxframelayout.getLayoutParams();
//// Changes the height and width to the specified *pixels*
//                params.height = displayheight - height - height2 - 100 - 100;
//                paradoxframelayout.setLayoutParams(params);

            }
        });
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void checknetwork(){
        if (haveNetworkConnection()){
            nointernetlayout.setVisibility(View.GONE);
            paradoxframelayout.setVisibility(View.VISIBLE);

            retryone.setText("Retry ..");
        }else{
            nointernetlayout.setVisibility(View.VISIBLE);
            paradoxframelayout.setVisibility(View.GONE);

            retryone.setText("No Connection .. RETRY");
        }
    }

    private void runagain(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checknetwork();
                runagain();
            }
        },1000);
    }
}