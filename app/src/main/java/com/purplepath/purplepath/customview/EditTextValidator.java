package com.purplepath.purplepath.customview;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by dinesh on 25/10/16.
 */
public  class EditTextValidator implements TextWatcher {
    private final EditText textView;
    TextInputLayout mTextInputLayout;


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    public EditTextValidator(EditText textView, TextInputLayout mTextInputLayout) {
        this.textView = textView;
        this.mTextInputLayout=mTextInputLayout;
    }
    public EditTextValidator(EditText textView) {
        this.textView = textView;
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        setError(null);

    }

    @Override
    public void afterTextChanged(Editable s) {
//        String text = textView.getText().toString();
//        validate(textView, text);
    }
    public  void validate(EditText editText, String text) {
        if ((text.trim()).length() == 0) {
            setWarningErrorSugestion(editText);
        } else {
            setNutralColor(editText);
        }
    }
    /**
     *   set Warning Yellow color
     */
    public void setWarningErrorSugestion(EditText textView) {
       //textView.setBackgroundResource(R.color.Yellow);
//        Drawable drawable = textView.getBackground(); // get current EditText drawable
//        drawable.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP); // change the drawable color
//        setEditTextDrawable(drawable);
        if(mTextInputLayout!=null)
        {
        //    mTextInputLayout.setError("please enter value");
//            mTextInputLayout.setBackgroundColor(UtileKit.getColor(mTextInputLayout.getContext(),R.color.Yellow));
//            mTextInputLayout.setHintTextAppearance(R.style.GreenEdit);
        }


    }

    public void setError(EditText textView) {
        Drawable drawable = textView.getBackground(); // get current EditText drawable
        drawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP); // change the drawable color
        setEditTextDrawable(drawable);
        if(mTextInputLayout!=null)
        {
            mTextInputLayout.setError("");
        }
    }

    /**
     * set Edit Text To Normal white colour
     */
    public void setNutralColor(EditText textView) {
        Drawable drawable = textView.getBackground(); // get current EditText drawable
        drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP); // change the drawable color
        setEditTextDrawable(drawable);
    }
    public void setEditTextDrawable(Drawable drawable )
    {

        if(Build.VERSION.SDK_INT > 16) {
            textView.setBackground(drawable); // set the new drawable to EditText
        }else{
            textView.setBackgroundDrawable(drawable); // use setBackgroundDrawable because setBackground required API 16
        }
    }
}
