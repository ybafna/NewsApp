package com.example.nrbafna.mynewsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by N.R. BAFNA on 28/03/2017.
 */
public class DisplayNewsDetails extends AppCompatActivity{
    SwipeGestureListener gestureListener;
    TextView description,url,title1;
    ImageView news_image;
    GestureDetector gDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_in_detail);

        description = (TextView) findViewById(R.id.tv_description);
        news_image = (ImageView) findViewById(R.id.iv_news_image);
        title1 = (TextView) findViewById(R.id.tv_title1);
        //url = (TextView) findViewById(R.id.tv_url);
        Intent intent = getIntent();

        description.setText(intent.getStringExtra("description"));
        title1.setText(intent.getStringExtra("title"));
        Picasso.with(this).load(intent.getStringExtra("news_image")).resize(700,500).into(news_image);
        gestureListener = new SwipeGestureListener(DisplayNewsDetails.this,gDetector);
        //url.setMovementMethod(LinkMovementMethod.getInstance());
       // url.setText(intent.getStringExtra("url"));


    }
}
