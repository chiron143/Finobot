package com.purplepath.purplepath.customview;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.finobot.finobot.R;

/**
 * Created by pravinr on 7/5/17.
 */

public class CharacterOnlyEdittext extends AppCompatEditText {
    private int errorUnderlineColor;
    private boolean isErrorStateEnabled;
    private boolean mHasReconstructedEditTextBackground;
    private Context mContext;
    CharacterOnlyEdittext charactereditext;
    public CharacterOnlyEdittext(Context context, AttributeSet attrs, final CharacterOnlyEdittext characterOnlyEdittext) {
        super(context, attrs);
        mContext=context;

        Typeface face=Typeface.createFromAsset(context.getAssets(), "MyriadWebPro.ttf");
        this.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
      //  this.setKeyListener(DigitsKeyListener.getInstance("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"));


        this.setFilters(new InputFilter[] {
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if(src.equals("")){ // for backspace
                            return src;
                        }
                        if((characterOnlyEdittext.getText().toString() + src).matches("[a-zA-Z ]+")){
                            return src;
                        }
                        return "";
                    }
                }
        });


        this.setTypeface(face);
        this.addTextChangedListener(new EditTextValidator(this));
        initColors();

        this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(getText().length()!=0){
                        setHintTextFillError();
                    }else {
                        setHintSelectedBgGrayFocus();
                    }
                }
                else  {
                    setHintSelectedBgFocus();
                }

            }
        });
    }

    public CharacterOnlyEdittext(Context context, AttributeSet attrs, int defStyle, final CharacterOnlyEdittext characterOnlyEdittext) {
        super(context, attrs, defStyle);
        mContext=context;

        Typeface face=Typeface.createFromAsset(context.getAssets(), "MyriadWebPro.ttf");
      //  this.setKeyListener(DigitsKeyListener.getInstance("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"));



        this.setFilters(new InputFilter[] {
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if(src.equals("")){ // for backspace
                            return src;
                        }
                        if((characterOnlyEdittext.getText().toString() + src).matches("[a-zA-Z ]+")){
                            return src;
                        }
                        return "";
                    }
                }
        });


        this.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        this.setTypeface(face);
        this.addTextChangedListener(new EditTextValidator(this));
        initColors();
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
        setBackgroundResource(R.drawable.edittextyellowerrorbg);
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




}