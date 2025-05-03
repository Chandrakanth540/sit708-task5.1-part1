package com.example.newsapp;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {

            loadFragment(new Home());

        }
    }
    public interface NewsDataCallback {
        void onDataLoaded(List<com.example.news.models.NewsItem> relatedList);
    }


    public void LoadNews(String query, NewsDataCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsApiService apiService = retrofit.create(NewsApiService.class);

        apiService.getRelatedNews(query, "543f5c977b1e47bf81b91d27bb725113", 1).enqueue(new Callback<NewsApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsApiResponse> call, @NonNull Response<NewsApiResponse> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().articles.isEmpty()) {
                    List<com.example.news.models.NewsItem> relatedList = new ArrayList<>();
                    for (Article article : response.body().articles) {
                        relatedList.add(new com.example.news.models.NewsItem(
                                article.title,
                                article.urlToImage,
                                article.description,
                                article.url,
                                article.source != null ? article.source.name : "Unknown"

                        ));
                    }
                    // Pass the data to the callback
                    callback.onDataLoaded(relatedList);
                } else {
                    Toast.makeText(MainActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsApiResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }}