package com.example.nrbafna.mynewsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by N.R. BAFNA on 28/03/2017.
 */
public class NewsActivity extends AppCompatActivity {
    public static final String URL = "https://newsapi.org/v1/";

    public static final String apiKey="d98bbacb46d94dde9d40ce02e5d7cf7a";
    private List<Article> articlesList = new ArrayList<>();
    private NewsSources myArticle = new NewsSources();;
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private String source_id,source_url;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView source_name;
    private ImageView source_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_recycler_view);
        // listView = (ListView) findViewById(R.id.lv_sources);

        Intent intent = getIntent();
        source_id = intent.getStringExtra("source_id");
        source_name = (TextView) findViewById(R.id.tv_source_name1);
        source_image = (ImageView) findViewById(R.id.iv_source_image);
        source_name.setText(intent.getStringExtra("source_name"));
        source_url = intent.getStringExtra("source_url");

        Picasso.with(this).load(source_url).resize(100,100).into(source_image);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(this);


        getArticles();

        recyclerView.addOnItemTouchListener(new NewsDetails(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Article article = articlesList.get(position);

                Intent intent = new Intent(getBaseContext(),DisplayNewsDetails.class);

                intent.putExtra("description",article.getDescription());
                intent.putExtra("url",article.getUrl());
                intent.putExtra("news_image",article.getUrlToImage());
                intent.putExtra("title",article.getTitle());
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), article.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void getArticles() {
        final ProgressDialog loading = ProgressDialog.show(this, "Fetching Data", "Please wait...", false, false);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiInterface apiinterface = retrofit.create(ApiInterface.class);

        Call<NewsSources> call = apiinterface.getNews(source_id,apiKey);
        call.enqueue(new Callback<NewsSources>() {

            @Override
            public void onResponse(Call<NewsSources> call, Response<NewsSources> response) {
                if(response.isSuccessful()){
                    loading.dismiss();
                    myArticle = response.body();

                    articlesList = myArticle.getArticles();
                    String description = articlesList.get(0).getDescription();
                    Log.d("Data aaya : ", description);
                   // mAdapter.notifyDataSetChanged();
                    mAdapter = new MyAdapter(getApplicationContext(),articlesList);
                    recyclerView.setLayoutManager(mLayoutManager);
                   // recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),LinearLayoutManager.VERTICAL));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), LinearLayoutManager.VERTICAL));
                    recyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<NewsSources> call, Throwable t) {
                Log.d("NAHI AAYA","NAHI AAYA");
                Toast.makeText(getBaseContext(),"Couldn't fetch data",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
