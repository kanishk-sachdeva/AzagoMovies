package com.kanucreator.azagomovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllFragment extends Fragment {

    RecyclerView allproductsrecycler;
    RecyclerView.Adapter adapter;
    FirebaseFirestore db;

    Context context;
    private RequestQueue mRequestQueue;

    LottieAnimationView progressingall;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_all, container, false);
        context = container.getContext();
        progressingall = view.findViewById(R.id.progressingall);

        mRequestQueue = Volley.newRequestQueue(context);

        allproductsrecycler = view.findViewById(R.id.allproductsrecycler);

        final ArrayList<AllMoviesModel> moviesall = new ArrayList<>();
        adapter = new AllMoviesAdapter(moviesall);
        allproductsrecycler.setHasFixedSize(true);
        allproductsrecycler.setLayoutManager(new GridLayoutManager(context,3));
        allproductsrecycler.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        db.collection("ALL").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        addimages(moviesall,document.get("imdbid").toString(),document.get("movielink").toString(),adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });


        adapter.notifyDataSetChanged();





        return view;
    }

    private void addimages(final ArrayList<AllMoviesModel> movies, final String imdbid,final String movielink, final RecyclerView.Adapter adapter) {
        String url = "https://api.themoviedb.org/3/movie/" + imdbid + "?api_key=f70767cc10291d2bf829e069b0246b53";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    progressingall.setVisibility(View.GONE);

                    String name = response.getString("title");
                    String image = response.getString("poster_path");
                    String original_image = "https://image.tmdb.org/t/p/w500"+image;
                    String desc = response.getString("overview");
                    float rating = Float.parseFloat(response.getString("vote_average"));
                    float rating1 = rating/2;

                    movies.add(new AllMoviesModel(original_image,imdbid,name,movielink));

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