package com.fourmob.datetimepicker.date;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.finobot.finobot.R;

public class TextViewWithCircularIndicator extends AppCompatTextView {
    
	private final int mCircleColor;
    private Paint mCirclePaint = new Paint();
	private boolean mDrawCircle;
	private final String mItemIsSelectedText;
	private final int mRadius;

	public TextViewWithCircularIndicator(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
        
		Resources res = context.getResources();
		mCircleColor = res.getColor(R.color.purple_path_app_orange);
		mRadius = res.getDimensionPixelOffset(R.dimen.month_select_circle_radius);
		mItemIsSelectedText = context.getResources().getString(R.string.item_is_selected);
        
		init();
	}

	private void init() {
		mCirclePaint.setFakeBoldText(true);
		mCirclePaint.setAntiAlias(true);
		mCirclePaint.setColor(mCircleColor);
		mCirclePaint.setTextAlign(Paint.Align.CENTER);
		mCirclePaint.setStyle(Paint.Style.FILL);
		mCirclePaint.setAlpha(60);
	}

	public void drawIndicator(boolean drawIndicator) {
		mDrawCircle = drawIndicator;
	}

	public CharSequence getContentDescription() {
		CharSequence text = getText();
		if (mDrawCircle) {
			text = String.format(mItemIsSelectedText, text);
        }
		return text;
	}

	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mDrawCircle) {
			int width = getWidth();
			int heigth = getHeight();
			int radius = Math.min(width, heigth) / 2;
			canvas.drawCircle(width / 2, heigth / 2, radius, mCirclePaint);
		}
	}
}