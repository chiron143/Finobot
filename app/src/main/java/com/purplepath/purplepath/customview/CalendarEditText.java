package com.purplepath.purplepath.customview;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Bert on 10-Aug-16.
 */
public class CalendarEditText extends AppCompatEditText {

    private int errorUnderlineColor;
    private Context mContext;
    private Pattern pattern;
    private Matcher matcher;
    private boolean isErrorStateEnabled;
    private static final String DATE_PATTERN =
            "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
    private boolean mHasReconstructedEditTextBackground;
    TextInputLayout mTextInputLayout;
    String mHintText;

    public CalendarEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        pattern = Pattern.compile(DATE_PATTERN);
        this.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        this.setGravity(Gravity.LEFT);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "MyriadWebPro.ttf");
        this.setTypeface(face);
//        this.addTextChangedListener(new EditTextValidator(this));
        initColors();
//        this.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_calander_icon_color, 0);
        UtileKit.setSvgEdittextDrawableRight(this,context,R.drawable.ic_calander_icon_color);
    }

    public CalendarEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        pattern = Pattern.compile(DATE_PATTERN);
        this.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        this.setGravity(Gravity.LEFT);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "MyriadWebPro.ttf");
        this.setTypeface(face);
//        this.addTextChangedListener(new EditTextValidator(this));
//        this.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_calander_icon_color, 0);
        UtileKit.setSvgEdittextDrawableRight(this,context,R.drawable.ic_calander_icon_color);
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
                if(mTextInputLayout!=null) {
                    mTextInputLayout.setErrorEnabled(false);
                    mTextInputLayout.setError(null);
                }
            }
        });*/




    }


    public void setHintText(String mHintText, TextInputLayout mTextInputLayout) {
        this.mHintText = mHintText;
        this.mTextInputLayout=mTextInputLayout;
    }

    /**
     *   set Warning Yellow color
     */
    public void setWarningErrorSugestion() {
        this.setBackgroundColor(UtileKit.getColor(mContext, R.color.Yellow));
    }

    public void setError() {
        this.setBackgroundColor(UtileKit.getColor(mContext, R.color.Red));
    }

    /**
     * set Edit Text To Normal white colour
     */
    public void setNutralColor() {
        this.setBackgroundColor(UtileKit.getColor(mContext, R.color.white));
    }

    public void ValidateDate(String date) {
        if (!dateValidate(date)) {
            setError();
        } else {
            this.setBackgroundColor(UtileKit.getColor(mContext, R.color.white));
        }

    }
    public void setHintTextEmptyError() {
//        setErrorState(true);
        setBackgroundResource(R.drawable.edittextbackgrounggreen);

    }
    public void setHintTextRequestFillError() {
        setBackgroundResource(R.drawable.edittextyellowerrorbg);
    }

    /**
     * Validate date format with regular expression
     *
     * @param date date address for validation
     * @return true valid date fromat, false invalid date format
     */
    public boolean dateValidate(final String date) {

        matcher = pattern.matcher(date);

        if (matcher.matches()) {

            matcher.reset();

            if (matcher.find()) {

                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));

                if (day.equals("31") &&
                        (month.equals("4") || month.equals("6") || month.equals("9") ||
                                month.equals("11") || month.equals("04") || month.equals("06") ||
                                month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                } else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if (year % 4 == 0) {
                        return !(day.equals("30") || day.equals("31"));
                    } else {
                        return !(day.equals("29") || day.equals("30") || day.equals("31"));
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    private void initColors() {
        errorUnderlineColor = R.color.Green;

    }
    public void setErrorColor() {
        ensureBackgroundDrawableStateWorkaround();
        getBackground().setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(
                ContextCompat.getColor(getContext(), errorUnderlineColor), PorterDuff.Mode.SRC_IN));

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


}
