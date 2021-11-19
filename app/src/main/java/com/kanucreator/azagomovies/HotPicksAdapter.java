package com.kanucreator.azagomovies;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.spark.submitbutton.SubmitButton;

import java.util.ArrayList;

public class HotPicksAdapter extends RecyclerView.Adapter<HotPicksAdapter.HotPicksViewHolder> {

    ArrayList<HotPicksModel> hotPicksModelArrayList;
    ViewPager2 viewPager2;

    public HotPicksAdapter(ArrayList<HotPicksModel> hotPicksModelArrayList, ViewPager2 viewPager2) {
        this.hotPicksModelArrayList = hotPicksModelArrayList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public HotPicksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotpicks_layoutitem,parent,false);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        HotPicksViewHolder hotPicksViewHolder = new HotPicksViewHolder(view);
        return hotPicksViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HotPicksViewHolder holder, int position) {
        HotPicksModel hotPicksModel = hotPicksModelArrayList.get(position);

        holder.setimage(hotPicksModel.getImage());
        holder.name.setText(hotPicksModel.getNameofmovie());
        holder.ratingBar.setRating(hotPicksModel.getRatingnum());
        holder.setrating(hotPicksModel.getRatingnum());
        holder.description.setText(hotPicksModel.getDescription());


        holder.setonclick(hotPicksModel.getImdbid(),hotPicksModel.getMovielink());
        if (position == hotPicksModelArrayList.size() - 2){
            viewPager2.post(runnable);

        }


    }

    @Override
    public int getItemCount() {
        return hotPicksModelArrayList.size();
    }

    public class HotPicksViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout image1;
        TextView name,rating,description;
        RatingBar ratingBar;

        Button watchnowbtn;

        public HotPicksViewHolder(@NonNull View itemView) {
            super(itemView);

            watchnowbtn = itemView.findViewById(R.id.watchnowbtn);

            image1 = itemView.findViewById(R.id.imageposter);
            name = itemView.findViewById(R.id.nameofmovie);
            rating = itemView.findViewById(R.id.ratingtext);
            description = itemView.findViewById(R.id.aboutmovie);

            ratingBar = itemView.findViewById(R.id.rtbProductRating);
        }

        public void setimage(final String image) {
            Glide.with(itemView.getContext())
                    .load(image)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            image1.setBackground(resource);
                        }
                    });
        }

        public void setrating(float ratingnum) {
            String rating6 = String.valueOf(ratingnum);

            if (ratingnum != 0){
                rating.setText(rating6);
            }else{
                rating.setVisibility(View.GONE);
                ratingBar.setVisibility(View.GONE);
            }
        }

        public void setonclick(final String imdbid,final String movielink) {
            watchnowbtn.setOnClickListener(new View.OnClickListener() {
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

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            hotPicksModelArrayList.addAll(hotPicksModelArrayList);
            notifyDataSetChanged();
        }
    };
}
