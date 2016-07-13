package br.com.pedrocunial.maptest.utils;

import android.app.Activity;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by summerjob on 12/07/16.
 */
public class FooterOnTouchListener implements View.OnTouchListener {

    static final String logTag = "ActivitySwipeDetector";
    static final int MIN_DISTANCE = 100;// Based on screen resolution. Change in higher resolutions.
    private float downX, downY, upX, upY;

    private boolean      isLarge;
    private ImageView    icon;
    private ImageView    iconLarge;
    private LinearLayout footer;
    private LinearLayout largeFooter;

    public FooterOnTouchListener(boolean isLarge,
                                 LinearLayout footer, LinearLayout largeFooter,
                                 ImageView icon, ImageView iconLarge) {
        this.isLarge     = isLarge;
        this.footer      = footer;
        this.largeFooter = largeFooter;
        this.icon        = icon;
        this.iconLarge   = iconLarge;
    }

    public void onTap(){

        if (isLarge) {
            largeFooter.setVisibility(View.INVISIBLE);
            footer.setVisibility(View.VISIBLE);
            icon.setVisibility(View.VISIBLE);
            iconLarge.setVisibility(View.INVISIBLE);
        } else {
            largeFooter.setVisibility(View.VISIBLE);
            footer.setVisibility(View.INVISIBLE);
            icon.setVisibility(View.INVISIBLE);
            iconLarge.setVisibility(View.VISIBLE);
        }
        isLarge = !isLarge;
    }

    public void onTopToBottomSwipe() {

        if (isLarge){
            largeFooter.setVisibility(View.INVISIBLE);
            footer.setVisibility(View.VISIBLE);
            icon.setVisibility(View.VISIBLE);
            iconLarge.setVisibility(View.INVISIBLE);
            isLarge = false;
        }

    }

    public void onBottomToTopSwipe() {

        if (!isLarge){
            largeFooter.setVisibility(View.VISIBLE);
            footer.setVisibility(View.INVISIBLE);
            icon.setVisibility(View.INVISIBLE);
            iconLarge.setVisibility(View.VISIBLE);
            isLarge = true;
        }

    }

    public void onRightToLeftSwipe() {
        //TODO implement swipe
    }

    public void onLeftToRightSwipe() {
        //TODO implement swipe
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                // swipe horizontal?
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // left or right
                    if (deltaX < 0) {
                        this.onLeftToRightSwipe();
                        return true;
                    }
                    if (deltaX > 0) {
                        this.onRightToLeftSwipe();
                        return true;
                    }
                } else {
                    Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long horizontally, need at least " + MIN_DISTANCE);
                    // return false; // We don't consume the event
                }

                // swipe vertical?
                if (Math.abs(deltaY) > MIN_DISTANCE) {
                    // top or down
                    if (deltaY < 0) {
                        this.onTopToBottomSwipe();
                        return true;
                    }
                    if (deltaY > 0) {
                        this.onBottomToTopSwipe();
                        return true;
                    }
                } else {
                    Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long vertically, need at least " + MIN_DISTANCE);
                    // return false; // We don't consume the event
                }

                // tap?
                if (isAClick(downX, upX, downY, upY)) {
                    onTap();
                    return true;
                } else {
                    // return false; // We don't consume the event
                }

                return false; // no swipe horizontally and no swipe vertically

            }
        }
        return false;
    }


    private boolean isAClick(float startX, float endX, float startY, float endY) {
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);
        if (differenceX >= 5 || differenceY >= 5) {
            return false;
        }
        return true;
    }


}