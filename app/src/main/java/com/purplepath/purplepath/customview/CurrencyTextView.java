package com.purplepath.purplepath.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.finobot.finobot.R;

/**
 * Created by Bert on 24-Aug-16.
 */
public class CurrencyTextView extends androidx.appcompat.widget.AppCompatTextView {
    String rawText;

    public CurrencyTextView(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "MyriadWebPro.ttf");
        this.setTypeface(face);
        this.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_currency_symbol, 0, 0, 0);

    }

    public CurrencyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "MyriadWebPro.ttf");
        this.setTypeface(face);
       // this.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_currency_symbol, 0, 0, 0);
    }

    public CurrencyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "MyriadWebPro.ttf");
        this.setTypeface(face);
       // this.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_currency_symbol, 0, 0, 0);
    }
    @Override
    public void setText(CharSequence text, BufferType type) {
        String textWithComma=null;
        try {
            rawText = text.toString();
            textWithComma = text.toString();
//            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
//            symbols.setDecimalSeparator(',');
//            DecimalFormat decimalFormat = new DecimalFormat("#,##,###", symbols);
          //  textWithComma =("₹ ").concat( decimalFormat.format(Integer.parseInt(text.toString())));
            textWithComma = text.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        super.setText(textWithComma, type);
    }

    @Override
    public CharSequence getText() {

        return rawText;
    }
    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }


}