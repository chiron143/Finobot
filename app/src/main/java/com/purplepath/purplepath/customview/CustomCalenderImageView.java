package com.purplepath.purplepath.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.finobot.finobot.R;

/**
 * Created by pravinr on 5/30/17.
 */

public class CustomCalenderImageView  extends android.support.v7.widget.AppCompatImageView
{
    public CustomCalenderImageView(Context context)
    {
        super(context);
        setBackgroundColor(Color.WHITE);
        setImageResource(R.drawable.ic_calcuator_icon_new);
    }
    public CustomCalenderImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setImageResource(R.drawable.ic_calcuator_icon_new);
    }
    public CustomCalenderImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        setImageResource(R.drawable.ic_calcuator_icon_new);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Paint paint  = new Paint(Paint.LINEAR_TEXT_FLAG);
        paint.setColor(Color.WHITE);
        //paint.setTextSize(12.0F);
        //canvas.drawText("Hello World in custom view", 100, 100, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // TODO Auto-generated method stub
      //  Log.d("Hello Android", "Got a touch event: " + event.getAction());
        return super.onTouchEvent(event);

    }
}