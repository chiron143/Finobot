package com.purplepath.purplepath.customview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.editable;

/**
 * Created by pravinr on 4/13/18.
 */

public class PanCardEditText extends AppCompatEditText {
    private int errorUnderlineColor;
    private boolean isErrorStateEnabled;
    private boolean mHasReconstructedEditTextBackground;
    private Context mContext;
    CharacterEditText charactereditext;
    TextInputLayout mTextInputLayout;
    String mHintText;
    public PanCardEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        Typeface face=Typeface.createFromAsset(context.getAssets(), "MyriadWebPro.ttf");
        this.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        this.setTypeface(face);
       /* this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (charSequence.length() == 0) {
                        if(mTextInputLayout!=null&&mHintText!=null) {
                            mTextInputLayout.setErrorTextAppearance(R.style.errorHintEdit);
                            mTextInputLayout.setError(mHintText);
                        }
                    }
                    else {
                        if(mTextInputLayout!=null) {
                            mTextInputLayout.setErrorEnabled(false);
                            mTextInputLayout.setError(null);
                            mTextInputLayout.setErrorTextAppearance(R.style.errorRedHintEdit);
                        }
                    }
                }catch (Exception e){e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });*/
        initColors();



        this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(getText().length()!=0){
                        setHintTextFillError();


                        if(mTextInputLayout!=null) {
                            mTextInputLayout.setErrorEnabled(false);
                            mTextInputLayout.setError(null);
                            mTextInputLayout.setErrorTextAppearance(R.style.errorRedHintEdit);
                        }



                    }else {
                        setHintSelectedBgGrayFocus();
                    }
                }
                else {
                    setHintSelectedBgFocus();

                    if(hasFocus){
                        if(getText().length()==0){
                            if(mTextInputLayout!=null&&mHintText!=null) {
                                mTextInputLayout.setErrorTextAppearance(R.style.errorHintEdit);
                                mTextInputLayout.setError(mHintText);
                            }
                        }
                    }
                }
            }




        });




    }

    public void setHintText(String mHintText, TextInputLayout mTextInputLayout) {
        this.mHintText = mHintText;
        this.mTextInputLayout=mTextInputLayout;
    }

    public void setmTextInputLayout(TextInputLayout mTextInputLayout) {
        this.mTextInputLayout = mTextInputLayout;
    }

    public  void validate(CharacterEditText editText, String text) {
        if ((text.trim()).length() == 0) {
            mTextInputLayout.setErrorTextAppearance(R.style.errorHintEdit);
            setWarningErrorSugestion(editText);
        } else {
            setNutralColor(editText);
        }
    }
    public PanCardEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext=context;
        Typeface face=Typeface.createFromAsset(context.getAssets(), "MyriadWebPro.ttf");
        this.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        this.setTypeface(face);
        this.addTextChangedListener(new EditTextValidator(this));
        initColors();


        this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(getText().length()!=0){
                        // setHintTextFillError();


                        if(mTextInputLayout!=null) {
                            mTextInputLayout.setErrorEnabled(false);
                            mTextInputLayout.setError(null);
                            mTextInputLayout.setErrorTextAppearance(R.style.errorRedHintEdit);
                        }



                    }else {
                        // setHintSelectedBgGrayFocus();
                    }
                }
                else {
                    // setHintSelectedBgFocus();

                    if(hasFocus){
                        if(getText().length()==0){
                            if(mTextInputLayout!=null&&mHintText!=null) {
                                mTextInputLayout.setErrorTextAppearance(R.style.errorHintEdit);
                                mTextInputLayout.setError(mHintText);
                            }
                        }
                    }
                }
            }




        });



    }

    private void initColors() {
        errorUnderlineColor = R.color.DarkGreen;

    }

    public void setErrorColor() {

        getBackground().setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(
                ContextCompat.getColor(getContext(), errorUnderlineColor), PorterDuff.Mode.SRC_IN));
        ensureBackgroundDrawableStateWorkaround();
    }

    private void ensureBackgroundDrawableStateWorkaround() {
        final Drawable bg = getBackground();
        if (bg == null) {
            return;
        }
        if (!mHasReconstructedEditTextBackground) {
            // This is gross. There is an issue in the platform which affects container Drawables
            // where the first drawable retrieved from resources will propogate any changes
            // (like color filter) to all instances from the cache. We'll try to workaround it...
            final Drawable newBg = bg.getConstantState().newDrawable();
            //if (bg instanceof DrawableContainer) {
            //  // If we have a Drawable container, we can try and set it's constant state via
            //  // reflection from the new Drawable
            //  mHasReconstructedEditTextBackground =
            //      DrawableUtils.setContainerConstantState(
            //          (DrawableContainer) bg, newBg.getConstantState());
            //}
            if (!mHasReconstructedEditTextBackground) {
                // If we reach here then we just need to set a brand new instance of the Drawable
                // as the background. This has the unfortunate side-effect of wiping out any
                // user set padding, but I'd hope that use of custom padding on an EditText
                // is limited.
                setBackgroundDrawable(newBg);
                mHasReconstructedEditTextBackground = true;
            }
        }
    }

    public boolean isErrorStateEnabled() {
        return isErrorStateEnabled;
    }

    public void setErrorState(boolean isErrorStateEnabled) {
        this.isErrorStateEnabled = isErrorStateEnabled;
        if (isErrorStateEnabled) {
            setErrorColor();
            invalidate();
        } else {
            getBackground().mutate().clearColorFilter();
            invalidate();
        }
    }

    public void setHintTextEmptyError() {
       /* this.setBackgroundColor(UtileKit.getColor(mContext, R.color.Blue));*/
        /**
         * dinesh Edited for Edit Text Line Color
         */
//        setErrorState(true);
        setBackgroundResource(R.drawable.edittextyellowerrorbg);
       /* Drawable drawable = this.getBackground(); // get current EditText drawable
        drawable.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP); // change the drawable color

        if(Build.VERSION.SDK_INT > 16) {
            this.setBackground(drawable); // set the new drawable to EditText
        }else{
            this.setBackgroundDrawable(drawable); // use setBackgroundDrawable because setBackground required API 16
        }*/
    }
    public void setHintTextFillError(){
        setBackgroundResource(R.drawable.edittextbackgrounggreen);
    }
    public void setHintSelectedBgFocus(){
        setBackgroundResource(R.drawable.edittextbackgroundpurple);
    }
    public void setHintTextRequestFillError() {
        setBackgroundResource(R.drawable.edittextyellowerrorbg);
    }
    public void setHintSelectedBgGrayFocus(){
        setBackgroundResource(R.drawable.edittextbackgroundgray);
    }

    public void setWarningErrorSugestion(EditText textView) {
        //textView.setBackgroundResource(R.color.Yellow);
//        Drawable drawable = textView.getBackground(); // get current EditText drawable
//        drawable.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP); // change the drawable color
//        setEditTextDrawable(drawable);
        if(mTextInputLayout!=null)
        {
            //    mTextInputLayout.setError("please enter value");
            mTextInputLayout.setBackgroundColor(UtileKit.getColor(mTextInputLayout.getContext(),R.color.Yellow));
            mTextInputLayout.setHintTextAppearance(R.style.GreenEdit);
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
            this.setBackground(drawable); // set the new drawable to EditText
        }else{
            this.setBackgroundDrawable(drawable); // use setBackgroundDrawable because setBackground required API 16
        }
    }

}