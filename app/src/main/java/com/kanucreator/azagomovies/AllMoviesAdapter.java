package com.kanucreator.azagomovies;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

public class AllMoviesAdapter extends RecyclerView.Adapter<AllMoviesAdapter.AllMoviesViewHolder> {

    ArrayList<AllMoviesModel> allMoviesModelArrayList;

    public AllMoviesAdapter(ArrayList<AllMoviesModel> allMoviesModelArrayList) {
        this.allMoviesModelArrayList = allMoviesModelArrayList;
    }

    @NonNull
    @Override
    public AllMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_movies,parent,false);
        AllMoviesViewHolder allMoviesViewHolder = new AllMoviesViewHolder(view);
        return allMoviesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllMoviesViewHolder holder, int position) {

        AllMoviesModel allMoviesModel = allMoviesModelArrayList.get(position);

        holder.setImage(allMoviesModel.getImage());

        holder.setonclick(allMoviesModel.getImdbid(),allMoviesModel.getMovielink());



    }

    @Override
    public int getItemCount() {
        return allMoviesModelArrayList.size();
    }


    public class AllMoviesViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout imageposter;
        ImageButton infobtnmovie;

        public AllMoviesViewHolder(@NonNull View itemView) {
            super(itemView);

            imageposter = itemView.findViewById(R.id.posterimgrelative);
            infobtnmovie = itemView.findViewById(R.id.infobtnmovie);
        }

        public void setImage(String image) {
            Glide.with(itemView.getContext())
                    .load(image)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            imageposter.setBackground(resource);
                        }
                    });
        }

        public void setonclick(final String imdbid, final String movielink) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(),AboutMovieActivity.class);
                    intent.putExtra("imdbid",imdbid);
                    intent.putExtra("movielink",movielink);

                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
