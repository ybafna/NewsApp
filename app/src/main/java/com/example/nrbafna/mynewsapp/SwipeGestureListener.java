package com.example.nrbafna.mynewsapp;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by N.R. BAFNA on 28/03/2017.
 */
public class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener implements
        View.OnTouchListener {

    Context context;
    String url;
    GestureDetector gDetector;
    static final int SWIPE_MIN_DISTANCE = 120;
    static final int SWIPE_MAX_OFF_PATH = 250;
    static final int SWIPE_THRESHOLD_VELOCITY = 200;

    public SwipeGestureListener() {
        super();
    }

    public SwipeGestureListener(Context context,String url) {
       this.context = context;
        this.url = url;
    }

    public SwipeGestureListener(Context context, GestureDetector gDetector) {

        if (gDetector == null)
            gDetector = new GestureDetector(context, this);

        this.context = context;
        this.gDetector = gDetector;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE) {
            Toast.makeText(this.context,"bottomToTop",
                    Toast.LENGTH_SHORT).show();}
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
