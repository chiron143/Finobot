package com.purplepath.purplepath.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Pratheep.S on 19-04-2017.
 */

public class OpenSansLightTextView extends AppCompatTextView {


    public OpenSansLightTextView(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(),"OpenSans-Light.ttf");
        this.setTypeface(face);

    }

    public OpenSansLightTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(),"OpenSans-Light.ttf");
        this.setTypeface(face);
    }

    public OpenSansLightTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface face=Typeface.createFromAsset(context.getAssets(),"OpenSans-Light.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }
    public void setBold(){

        this.setTypeface(getTypeface(), Typeface.BOLD);
    }
}
