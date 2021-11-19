package com.kanucreator.azagomovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class AboutMovieActivity extends AppCompatActivity {
    String imdbid,url,name,moviename;
    ImageView backicononclick;
    RelativeLayout image1;
    Context context;
    private RequestQueue mRequestQueue;
    private RequestQueue mRequestQueue1;
    Toolbar toolbar;
    TextView nameofmovie,ratingtextforabout,releasedatetext,overviewtextover,genres;
    ImageButton playicon;
    Button playmoviebtn;
    Button savevideo;
    FirebaseFirestore db;
    RatingBar ratingbar1;
    RecyclerView castrecycler;
    RecyclerView.Adapter adapter;
    LinearLayout overviewlayout1,morelikethislayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_movie);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        backicononclick = findViewById(R.id.backicononclick);
        context = AboutMovieActivity.this;
        playicon = findViewById(R.id.playicon);
        overviewlayout1 = findViewById(R.id.overviewlayout1);
        mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue1 = Volley.newRequestQueue(context);
        morelikethislayout = findViewById(R.id.morelikethislayout);
        savevideo = findViewById(R.id.savevideo);
        savevideo();
        toolbar = findViewById(R.id.toolbar);
        toolbar();
        imdbid = getIntent().getStringExtra("imdbid");
        url = "https://api.themoviedb.org/3/movie/" + imdbid + "?api_key=f70767cc10291d2bf829e069b0246b53";
        image1 = findViewById(R.id.posterimagerelative);
        backicononclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        settablayout();
        db = FirebaseFirestore.getInstance();
        playmoviebtn = findViewById(R.id.playmoviebtn);
        nameofmovie = findViewById(R.id.nameofmovie);
        ratingbar1 = findViewById(R.id.ratingbar1);
        ratingtextforabout = findViewById(R.id.ratingtextforabout);
        releasedatetext = findViewById(R.id.releasedatetext);
        genres = findViewById(R.id.genres);
        overviewtextover = findViewById(R.id.overviewtextover);
        addimages(imdbid);

        final String link = getIntent().getStringExtra("movielink");

        playicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutMovieActivity.this,ParadoxVideoPlayer.class);
                intent.putExtra("name",moviename);
                intent.putExtra("link",link);
                intent.putExtra("imdbid",imdbid);
                startActivity(intent);
                finish();
            }
        });

        playmoviebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutMovieActivity.this,ParadoxVideoPlayer.class);
                intent.putExtra("name",moviename);
                intent.putExtra("link",link);
                intent.putExtra("imdbid",imdbid);
                startActivity(intent);
                finish();
            }
        });


        castrecycler = findViewById(R.id.castrecycler);
        addcastingimage(imdbid);
    }

    private void savevideo() {
        savevideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(AboutMovieActivity.this.getContentResolver(),
                        Settings.Secure.ANDROID_ID);

                final Map<String,Object> user = new HashMap<>();
                user.put("imdbid",imdbid);
                user.put("userid",android_id);
                user.put("combinedid",imdbid+android_id);

                db.collection("SAVEDPREF")
                        .whereEqualTo("combinedid",imdbid+android_id)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    for (QueryDocumentSnapshot document : task.getResult()){
                                        if (!document.exists()){
                                            db.collection("SAVEDPREF").add(user)
                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }else{
                                            Toast.makeText(context, "Already Exists", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });

            }
        });

    }

    private void addcastingimage(String imdbid) {

        final ArrayList<CastingModel> casters = new ArrayList<>();
        adapter = new CastingAdapter(casters);
        castrecycler.setHasFixedSize(true);
        castrecycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        castrecycler.setAdapter(adapter);


        String url = "https://api.themoviedb.org/3/movie/" + imdbid + "/credits?api_key=f70767cc10291d2bf829e069b0246b53";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray json1 = response.getJSONArray("cast");
                    for (int i = 0; i < json1.length(); i++) {
                        JSONObject jsonObject1 = json1.getJSONObject(i);
                        name = jsonObject1.getString("name");
                        String image = jsonObject1.getString("profile_path");
                        String original_image = "https://image.tmdb.org/t/p/w500"+image;

                        casters.add(new CastingModel(original_image,name));

                        adapter.notifyDataSetChanged();
                    }

                    adapter.notifyDataSetChanged();


                } catch (JSONException e) {

                    Toast.makeText(context, "Front Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Server Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        mRequestQueue1.add(jsonObjectRequest);

    }


    private void addimages(final String imdbid) {
        String url = "https://api.themoviedb.org/3/movie/" + imdbid + "?api_key=f70767cc10291d2bf829e069b0246b53";

        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    moviename = response.getString("title");
                    String image = response.getString("poster_path");
                    String original_image = "https://image.tmdb.org/t/p/w500"+image;
                    String desc = response.getString("overview");
                    float rating = Float.parseFloat(response.getString("vote_average"));
                    String releasedate = response.getString("release_date");
                    List<String> ids = new ArrayList<String>();

                    JSONArray jsonArray = response.getJSONArray("genres");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String genres2 = jsonObject.getString("name");
                        String genres1 = "#"+genres2;
                        ids.add(genres1);

                    }

                    String joinedgenre = TextUtils.join(", ",ids);

                    genres.setText(joinedgenre);

                    nameofmovie.setText(moviename);
                    String rating6 = String.valueOf(rating);
                    releasedatetext.setText(releasedate);

                    if (rating != 0){
                        ratingtextforabout.setText(rating6);
                        ratingbar1.setRating(rating);
                    }else{
                        ratingbar1.setVisibility(View.GONE);
                        ratingtextforabout.setVisibility(View.GONE);
                    }

                    overviewtextover.setText(desc);

                    Glide.with(AboutMovieActivity.this).load(original_image).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            image1.setBackground(resource);
                        }
                    });

                } catch (JSONException e) {

                    Toast.makeText(context, "Front Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Server Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        mRequestQueue.add(jsonObjectRequest1);

    }

    private void settablayout() {
        final View overviewline = findViewById(R.id.redcolorlineforoverview);
        final View morelikeline = findViewById(R.id.redcolorlineformorelikethis);

        final TextView overviewtext = findViewById(R.id.overviewtext);
        final TextView moreliketext = findViewById(R.id.morelikethistext);

        RelativeLayout overviewlayout = findViewById(R.id.overviewtab);
        RelativeLayout morelikelayout = findViewById(R.id.morelikelayout);

        overviewlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overviewline.setBackgroundColor(getResources().getColor(R.color.red));
                overviewtext.setTextColor(getResources().getColor(R.color.white));


                morelikeline.setBackgroundColor(getResources().getColor(R.color.white));
                moreliketext.setTextColor(getResources().getColor(R.color.grey_active));

                overviewlayout1.setVisibility(View.VISIBLE);
                morelikethislayout.setVisibility(View.GONE);
            }
        });


        morelikelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morelikeline.setBackgroundColor(getResources().getColor(R.color.red));
                moreliketext.setTextColor(getResources().getColor(R.color.white));


                overviewline.setBackgroundColor(getResources().getColor(R.color.white));
                overviewtext.setTextColor(getResources().getColor(R.color.grey_active));

                overviewlayout1.setVisibility(View.GONE);
                morelikethislayout.setVisibility(View.VISIBLE);
            }
        });


    }

    private void toolbar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.back_icon3);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getdata() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String name = response.getString("title");
                    String image = response.getString("poster_path");
                    String original_image = "https://image.tmdb.org/t/p/w500"+image;
                    String desc = response.getString("overview");
                    float rating = Float.parseFloat(response.getString("vote_average"));
                    float rating1 = rating/2;

                    Glide.with(context).load(original_image).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            image1.setBackground(resource);
                        }
                    });

                } catch (JSONException e) {

                    Toast.makeText(context, "Front Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Server Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        mRequestQueue.add(jsonObjectRequest);
    }
}