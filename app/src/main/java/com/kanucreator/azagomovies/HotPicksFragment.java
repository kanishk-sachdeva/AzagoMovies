package com.kanucreator.azagomovies;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HotPicksFragment extends Fragment {

    ViewPager2 viewPager2;
    HotPicksAdapter hotPicksAdapter;

    private Handler sliderhandler = new Handler();
    FirebaseFirestore db;
    Context context;
    private RequestQueue mRequestQueue;

    LottieAnimationView squareprogress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_picks, container, false);

        viewPager2 = view.findViewById(R.id.hotpicsflipper);
        context = container.getContext();
        squareprogress = view.findViewById(R.id.squareprogress);

        mRequestQueue = Volley.newRequestQueue(context);
        setAdapterviewpager();

        return view;
    }

    private void setAdapterviewpager() {

        final ArrayList<HotPicksModel> movies = new ArrayList<>();
        hotPicksAdapter = new HotPicksAdapter(movies,viewPager2);
        viewPager2.setAdapter(hotPicksAdapter);

        db = FirebaseFirestore.getInstance();
        db.collection("HOT").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        addimages(movies,
                                document.get("imdbid").toString(),
                                hotPicksAdapter,
                                document.get("movielink").toString());
                        hotPicksAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        hotPicksAdapter.notifyDataSetChanged();


        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);


        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);

            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);

        final Runnable sliderrunnable = new Runnable() {
            @Override
            public void run() {

                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        };

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                sliderhandler.removeCallbacks(sliderrunnable);
                sliderhandler.postDelayed(sliderrunnable,3000);

            }
        });



    }

    private void addimages(final ArrayList<HotPicksModel> movies, final String imdbid, final RecyclerView.Adapter adapter, final String movielink) {
        String url = "https://api.themoviedb.org/3/movie/" + imdbid + "?api_key=f70767cc10291d2bf829e069b0246b53";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    squareprogress.setVisibility(View.GONE);

                    String name = response.getString("title");
                    String image = response.getString("poster_path");
                    String original_image = "https://image.tmdb.org/t/p/w500"+image;
                    String desc = response.getString("overview");
                    float rating = Float.parseFloat(response.getString("vote_average"));
                    float rating1 = rating/2;

                    movies.add(new HotPicksModel(original_image, name, desc, rating1,imdbid,movielink));
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

        mRequestQueue.add(jsonObjectRequest);

    }

}