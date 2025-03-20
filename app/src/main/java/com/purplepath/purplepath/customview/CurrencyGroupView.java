package com.purplepath.purplepath.customview;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dinesh on 05/05/17.
 */

public class CurrencyGroupView extends RelativeLayout {

    @BindView(R.id.curencyEditTxt)
    CurrencyEditText editText;

    @BindView(R.id.amountInWords)
    TextView amountInWords;

    @BindView(R.id.currencyhintId)
    CustomTextInputLayout currencyHintTxt;

    public CurrencyGroupView(Context context) {
        super(context);
        init();
    }

    public CurrencyGroupView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CurrencyGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public CurrencyEditText getEditText() {
        return editText;
    }

    public TextView getAmountInWords() {
        return amountInWords;
    }

    protected void init() {
        View inflate = inflate(getContext(), R.layout.custem_currency_view, this);
        ButterKnife.bind(this, inflate);
        amountInWords.setTypeface(null, Typeface.BOLD|Typeface.ITALIC);
    }

    public String getAmountInWordsText() {
        return amountInWords.getText().toString();
    }

    public void setAmountInWordsText(String amountInWordsText) {
        this.amountInWords.setText(amountInWordsText);
    }

    public final String getText() {
        return editText.getText().toString();
    }

    public  void setText(String text) {
        if (text.length() != 0) {
            editText.setText(text);
            try{

                amountInWords.setVisibility(View.VISIBLE);
                amountInWords.setText(UtileKit.currToCharConversion(text));
            }catch (Exception e)
            {e.printStackTrace();}
        }
        else {
            amountInWords.setVisibility(View.GONE);
        }

    }

    public final  void setTextHint(String text)
    {
        if(text!=null)
        {
            currencyHintTxt.setHint(text);

        }
    }

}
