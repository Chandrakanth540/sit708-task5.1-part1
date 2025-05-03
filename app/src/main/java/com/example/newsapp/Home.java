package com.example.newsapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // RecyclerView setup
        RecyclerView recyclerView = view.findViewById(R.id.main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            // Fetch news data
            activity.LoadNews("Trending News", new MainActivity.NewsDataCallback() {
                @Override
                public void onDataLoaded(List<com.example.news.models.NewsItem> relatedList) {
                    // Set adapter
                    ItemAdapter adapter = new ItemAdapter(relatedList, Home.this);

                    recyclerView.setAdapter(adapter);

                    // Grid layout
                    GridLayout newsGridLayout = view.findViewById(R.id.newsContainer);
                    newsGridLayout.removeAllViews();

                    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                    int screenWidth = displayMetrics.widthPixels;

                    for (com.example.news.models.NewsItem item : relatedList) {
                        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                        params.width = screenWidth / 2 - 30;
//                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        params.height=800;
                        params.setMargins(5, 15, 5, 15);

                        CardView cardView = new CardView(requireContext());
                        cardView.setRadius(16);
                        cardView.setCardElevation(8);
                        cardView.setLayoutParams(params);

                        LinearLayout itemLayout = new LinearLayout(requireContext());
                        itemLayout.setOrientation(LinearLayout.VERTICAL);
                        itemLayout.setPadding(16, 16, 16, 16);
                        itemLayout.setOnClickListener(v -> {
                            Detailed detailFragment = new Detailed();
                            Bundle bundle = new Bundle();
                            bundle.putString("title", item.getTitle());
                            bundle.putString("imageUrl", item.getImageUrl());  // Assuming URL
                            bundle.putString("desc", item.getDescription());
                            ArrayList<com.example.news.models.NewsItem> relatedNews = new ArrayList<>();
                            for (com.example.news.models.NewsItem otherItem : relatedList) {
                                if (!otherItem.getTitle().equals(item.getTitle()) && relatedNews.size() < 3) {
                                    relatedNews.add(otherItem);
                                }
                            }

                            bundle.putSerializable("relatedNews", relatedNews);
                            detailFragment.setArguments(bundle);
                            requireActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.main_container, detailFragment)
                                    .addToBackStack(null)
                                    .commit();
                        });

                        ImageView imageView = new ImageView(requireContext());

                        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT, 400));
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                        Glide.with(requireContext())
                                .load(item.getImageUrl()) // assuming item has a URL string
                                .placeholder(R.drawable.image1)
                                .error(R.drawable.image1)
                                .into(imageView);



                        TextView titleView = new TextView(requireContext());
                        titleView.setText(item.getTitle());
                        titleView.setGravity(Gravity.CENTER);
                        titleView.setTextColor(Color.BLACK);
                        titleView.setTextSize(18);
                        titleView.setPadding(0, 10, 0, 0);

                        TextView descView = new TextView(requireContext());
                        String descText = item.getDescription();
                        if (descText != null && descText.length() > 50) {
                            descText = descText.substring(0, 50) + "...";
                        }
                        descView.setText(descText);
                        descView.setGravity(Gravity.CENTER);
                        descView.setTextColor(Color.DKGRAY);
                        descView.setTextSize(14);
                        descView.setPadding(0, 6, 0, 10);

                        itemLayout.addView(imageView);
                        itemLayout.addView(titleView);
                        itemLayout.addView(descView);

                        cardView.addView(itemLayout);
                        newsGridLayout.addView(cardView);
                    }
                }
            });
        }

        return view; // This was missing
    }
}