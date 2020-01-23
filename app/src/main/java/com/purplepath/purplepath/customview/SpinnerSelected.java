package com.purplepath.purplepath.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.finobot.finobot.R;

/**
 * @author Bert on 04-May-17.
 */

public class SpinnerSelected extends android.support.v7.widget.AppCompatSpinner {
    public SpinnerSelected(Context context) {
        super(context);
    }

    public SpinnerSelected(Context context, int mode) {
        super(context, mode);
    }

    public SpinnerSelected(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setBackgroundResource(R.drawable.spinner_background_gray_arrow);


    }

    @Override
    public void setOnItemSelectedListener(@Nullable OnItemSelectedListener listener) {
        super.setOnItemSelectedListener(listener);
       // setBackgroundResource(R.drawable.spinner_background_green_arrow);
    }


}
