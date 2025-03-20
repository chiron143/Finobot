package com.purplepath.purplepath.customview;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pravinr on 4/17/18.
 */

public class CurrencyGhostViewTaxFiling extends RelativeLayout {

    @BindView(R.id.curencyGhostEditTxt)
    CurrencyDefaultEdt editText;

    @BindView(R.id.amountInWords)
    TextView amountInWords;

    @BindView(R.id.currencyhintId)
    CustomTextInputLayout currencyHintTxt;

    @BindView(R.id.hintViewTxtId)
    TextView hintViewTxt;

    public CurrencyGhostViewTaxFiling(Context context) {
        super(context);
        init();
    }

    public CurrencyGhostViewTaxFiling(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CurrencyGhostViewTaxFiling(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public CurrencyDefaultEdt getEditText() {
        return editText;
    }

    public TextView getAmountInWords() {
        return amountInWords;
    }

    protected void init() {
        View inflate = inflate(getContext(), R.layout.custom_ghost_view, this);
        ButterKnife.bind(this, inflate);




        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    hintViewTxt.setVisibility(View.GONE);
                    try {
                        amountInWords.setVisibility(View.VISIBLE);
                        amountInWords.setText(UtileKit.currToCharConversion(String.valueOf(charSequence)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    amountInWords.setVisibility(View.GONE);
                    hintViewTxt.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        amountInWords.setTypeface(null, Typeface.BOLD | Typeface.ITALIC);
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

    public void setText(String text) {
        if (text.length() != 0) {

            try {
                editText.setText(text);
                amountInWords.setVisibility(View.VISIBLE);
                amountInWords.setText(UtileKit.currToCharConversion(text));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            amountInWords.setVisibility(View.GONE);
        }

    }

    public final void setTextHint(String text) {
        if (text != null) {
            currencyHintTxt.setHint(text);

        }
    }

    public void setEditTextId(int id) {
        editText.setId(id);
    }

    public void setfullHintTxt(String hintTxt) {
        //  hintViewTxt.setVisibility(View.VISIBLE);
        hintViewTxt.setText(hintTxt);
    }
    public void setHintTxtVisiblity(Boolean visiblity) {
        if(visiblity)
            hintViewTxt.setVisibility(View.VISIBLE);
        else
            hintViewTxt.setVisibility(View.GONE);

    }

}
