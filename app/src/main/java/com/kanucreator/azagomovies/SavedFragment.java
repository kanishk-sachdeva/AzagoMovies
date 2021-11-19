package com.kanucreator.azagomovies;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class SavedFragment extends Fragment {

    TextView savedpreftext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved, container, false);
//
//        savedpreftext = view.findViewById(R.id.savedpreftext);
//
//        SharedPreferences pref = container.getContext().getSharedPreferences("ParadoxMovies",MODE_PRIVATE);
//        String ids = pref.getString("id","");
//
//        savedpreftext.setText(ids);

        return view;
    }
}