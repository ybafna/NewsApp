package com.example.nrbafna.mynewsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by N.R. BAFNA on 28/03/2017.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Article> articles;

    private Context context;

    public MyAdapter(Context context,List<Article> articles) {
        this.articles = articles;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = articles.get(position);
       // Log.d("Source Name : ",newsSources.getSource());
       // Log.d("Description : ",article.getDescription());
        holder.publishedAt.setText(article.getPublishedAt());
        holder.title.setText(article.getTitle());
        holder.author.setText(article.getAuthor());
        Picasso.with(context).load(article.getUrlToImage()).resize(200, 180).into(holder.newsImage);

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView publishedAt,title,author;
        ImageView newsImage;
        public ViewHolder(View itemView) {
            super(itemView);
            publishedAt = (TextView)itemView.findViewById(R.id.tv_publishedAt);
            title = (TextView)itemView.findViewById(R.id.tv_title);
            newsImage = (ImageView) itemView.findViewById(R.id.iv_news_image);
            author = (TextView) itemView.findViewById(R.id.tv_author);

        }
    }
}
