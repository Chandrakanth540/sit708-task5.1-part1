package com.example.newsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Relatednews extends RecyclerView.Adapter<Relatednews.ItemViewHolder> {

    private List<com.example.news.models.NewsItem> itemList;

    private Fragment parentFragment;

    public Relatednews(List<com.example.news.models.NewsItem> itemList, Fragment parentFragment) {
        this.itemList = itemList;
        this.parentFragment = parentFragment;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        com.example.news.models.NewsItem item = itemList.get(position);
        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getSourceName());

        // Load image using Glide
        Glide.with(holder.imageView.getContext())
                .load(item.getImageUrl())  // URL of the image
                .into(holder.imageView);  // Target ImageView

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView;
        ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.itemText);
            descriptionTextView = itemView.findViewById(R.id.itemDesc);
            imageView = itemView.findViewById(R.id.itemImage);
        }
    }
}
