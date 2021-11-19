package com.kanucreator.azagomovies;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

public class CastingAdapter extends RecyclerView.Adapter<CastingAdapter.CastingViewHolder> {

    public ArrayList<com.kanucreator.azagomovies.CastingModel> castingModelArrayList;

    public CastingAdapter(ArrayList<CastingModel> castingModelArrayList) {
        this.castingModelArrayList = castingModelArrayList;
    }

    @NonNull
    @Override
    public CastingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.casting_items_layout,parent,false);
        CastingViewHolder castingViewHolder = new CastingViewHolder(view);
        return castingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CastingViewHolder holder, int position) {

        CastingModel castingModel = castingModelArrayList.get(position);

        holder.actorname.setText(castingModel.getName());
        holder.setImage(castingModel.getImageurl());


    }

    @Override
    public int getItemCount() {
        return castingModelArrayList.size();
    }

    public class CastingViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout imagelayout;
        TextView actorname;

        public CastingViewHolder(@NonNull View itemView) {
            super(itemView);

            imagelayout = itemView.findViewById(R.id.actorimage);
            actorname = itemView.findViewById(R.id.actorname);


        }

        public void setImage(String imageurl) {
            Glide.with(itemView.getContext()).load(imageurl).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    imagelayout.setBackground(resource);
                }
            });
        }
    }
}
