package com.purplepath.purplepath.customview;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import com.finobot.finobot.R;

/**
 * Created by Bert on 12-Aug-16.
 */
public class NumberEditText extends AppCompatEditText {
    private int errorUnderlineColor;
    private boolean isErrorStateEnabled;
    private boolean mHasReconstructedEditTextBackground;

    TextInputLayout mTextInputLayout;
    String mHintText;

    public NumberEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "MyriadWebPro.ttf");
        this.setTypeface(face);
        this.addTextChangedListener(new EditTextValidator(this));

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
                else  {
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

        //new text watcher added
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()!=0){
//                    setHintTextFillError();
                    if(mTextInputLayout!=null) {
                        mTextInputLayout.setErrorEnabled(false);
                        mTextInputLayout.setError(null);
                    }

                }else {
                    //   setHintSelectedBgGrayFocus();
//                    if(charSequence.length()==0){
//                        if(mTextInputLayout!=null&&mHintText!=null) {
//                            mTextInputLayout.setErrorTextAppearance(R.style.errorHintEdit);
//                            mTextInputLayout.setError(mHintText);
//                        }
//                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        /*this.addTextChangedListener(new TextWatcher() {
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



    }

    public void setHintText(String mHintText, TextInputLayout mTextInputLayout) {
        this.mHintText = mHintText;
        this.mTextInputLayout=mTextInputLayout;
    }
    
    public NumberEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "MyriadWebPro.ttf");
        this.setTypeface(face);
        this.addTextChangedListener(new EditTextValidator(this));
    }
    public void setErrorColor() {

        getBackground().setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(
                ContextCompat.getColor(getContext(), errorUnderlineColor), PorterDuff.Mode.SRC_IN));
        ensureBackgroundDrawableStateWorkaround();
    }
    public void setHintTextFillError(){
        setBackgroundResource(R.drawable.edittextbackgrounggreen);
    }
    public void setHintSelectedBgFocus(){
        setBackgroundResource(R.drawable.edittextbackgroundpurple);
    }

    public void setHintSelectedBgGrayFocus(){
        setBackgroundResource(R.drawable.edittextbackgroundgray);
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
        setBackgroundResource(R.drawable.edittextbackgrounggreen);
       /* this.setBackgroundColor(UtileKit.getColor(mContext, R.color.Blue));*/
//        setErrorState(true);
       /* Drawable drawable = this.getBackground(); // get current EditText drawable
        drawable.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP); // change the drawable color

        if(Build.VERSION.SDK_INT > 16) {
            this.setBackground(drawable); // set the new drawable to EditText
        }else{
            this.setBackgroundDrawable(drawable); // use setBackgroundDrawable because setBackground required API 16
        }*/
    }
    public void setHintTextRequestFillError() {
        setBackgroundResource(R.drawable.edittextyellowerrorbg);
    }
}