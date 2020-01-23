package com.purplepath.purplepath.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by pravinr on 9/20/17.
 */

public class DefaultCurrencyTextView extends android.support.v7.widget.AppCompatTextView {
    String rawText;

    public DefaultCurrencyTextView(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "MyriadWebPro.ttf");
        this.setTypeface(face);
        this.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_currency_symbol, 0, 0, 0);

    }

    public DefaultCurrencyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "MyriadWebPro.ttf");
        this.setTypeface(face);
        // this.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_currency_symbol, 0, 0, 0);
    }

    public DefaultCurrencyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "MyriadWebPro.ttf");
        this.setTypeface(face);
        // this.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_currency_symbol, 0, 0, 0);
    }

    /**
     * Remove Curency Symbol for other Countries
     * @param text
     * @param type
     */
    @Override
    public void setText(CharSequence text, BufferType type) {
        String textWithComma=null;
        try {
            rawText = text.toString();
            textWithComma = text.toString();
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            DecimalFormat decimalFormat = new DecimalFormat("#,##,###", symbols);
            //  textWithComma =("₹ ").concat( decimalFormat.format(Integer.parseInt(text.toString())));
            UtileKit.logTest("Recomendation", text.toString());
            if(text.toString().replace("Rs.","").replace("Rs .","").replaceAll("[^0-9 .]","").length()!=0)
            textWithComma =("₹ ").concat( decimalFormat.format(new BigDecimal(text.toString().replace("Rs.","").replace("Rs .","").replaceAll("[^0-9 .]","").replace(" ",""))));
            else {

            }

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