package com.example.nguyensang.anime_online_official.Customclass;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

/**
 * Created by NguyenSang on 04/23/2018.
 */

public class ShimmerText {
    private Typeface typeface;
    public void setSimmerTextView(ShimmerTextView txt , Context context , int time , boolean setTypeface) {
        Shimmer shimmer = new Shimmer();
        shimmer.setDuration(time);
        shimmer.start(txt);
        if(setTypeface){
            typeface= Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Light.ttf");
            txt.setTypeface(typeface);
        }
    }
}
