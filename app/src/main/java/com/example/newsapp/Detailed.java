package com.example.newsapp;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Detailed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Detailed extends Fragment {

    private String title;
    private String desc;
    private String imageResId;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Detailed() {
        // Required empty public constructor
    }


    public static Detailed newInstance(String param1, String param2) {
        Detailed fragment = new Detailed();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("title");
            desc = getArguments().getString("desc");
            imageResId = getArguments().getString("imageUrl"); // FIXED
        }
        View view = inflater.inflate(R.layout.fragment_detailed, container, false);

        TextView titleView = view.findViewById(R.id.titleTextView);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView descView = view.findViewById(R.id.descTextView);

        titleView.setText(title);
        Glide.with(this).load(imageResId).into(imageView); // Glide is better for loading images
        descView.setText(desc);

        return view;
    }

}