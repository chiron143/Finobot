package com.purplepath.purplepath.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.finobot.finobot.R;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by pravinr on 1/19/18.
 */

public class CurrencyDefaultEdittext extends AppCompatEditText {

    Locale indianlocal =  new Locale("en", "IN");
    private Locale locale = indianlocal;
    private Currency currency;
    private Locale defaultLocale = indianlocal;

    private boolean defaultHintEnabled = true;
    private boolean allowNegativeValues = false;
    private long valueInLowestDenom = 0L;

    private CurrencyDefaultEditTextWatcher textWatcher;

    private String hintCache = null;
    private String mytotal;
    private int errorUnderlineColor;
    private boolean isErrorStateEnabled;
    private boolean mHasReconstructedEditTextBackground;

    TextInputLayout mTextInputLayout;
    String mHintText;
    /*
    PUBLIC METHODS
     */

    public CurrencyDefaultEdittext(Context context) {
        super(context);
        init();
    }

    public CurrencyDefaultEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        init();
        this.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_currency_symbol, 0, 0, 0);
        this.setGravity(Gravity.LEFT);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "MyriadWebPro.ttf");
        this.setTypeface(face);
        processAttributes(context, attrs);
        initColors();


    }

    public CurrencyDefaultEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        initCurrency();
        initCurrencyTextWatcher();
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
    }

    public void setHintText(String mHintText, TextInputLayout mTextInputLayout) {
        this.mHintText = mHintText;
        this.mTextInputLayout=mTextInputLayout;
    }

    private void initCurrencyTextWatcher(){
        if(textWatcher != null){
            this.removeTextChangedListener(textWatcher);
        }
        textWatcher = new CurrencyDefaultEditTextWatcher(this, defaultLocale);
        this.addTextChangedListener(textWatcher);
//        CurrencyTextWatcher.setOnTextChangeWatcherListerner(this);
    }



    private void initCurrency(){
        try {
            currency = Currency.getInstance(locale);
        } catch (IllegalArgumentException e) {
            currency = Currency.getInstance(defaultLocale);
        }
    }

    /**
     * Sets whether or or not the Default Hint (users local currency symbol) will be shown in the textbox when no value has yet been entered.
     * @param useDefaultHint - true to enable default hint, false to disable
     */
    public void setDefaultHintEnabled(boolean useDefaultHint) {
        this.defaultHintEnabled = useDefaultHint;
    }

    /**
     * Determine whether or not the default hint is currently enabled for this view.
     * @return true if the default hint is enabled, false if it is not.
     */
    public boolean getDefaultHintEnabled(){
        return this.defaultHintEnabled;
    }

    /**
     * Enable the user to input negative values
     */
    public void setAllowNegativeValues(boolean negativeValuesAllowed){
        allowNegativeValues = negativeValuesAllowed;
    }

    /**
     * Returns whether or not negative values have been allowed for this CurrencyEditText field
     */
    public boolean areNegativeValuesAllowed(){
        return allowNegativeValues;
    }

    /**
     * Retrieve the raw value that was input by the user in their currencies lowest denomination (e.g. pennies).
     *
     * IMPORTANT: Remember that the location of the decimal varies by currency/Locale. This method
     *  returns the raw given value, and does not account for locality of the user. It is up to the
     *  calling application to handle that level of conversion.
     *  For example, if the text of the field is $13.37, this method will return a long with a
     *  value of 1337, as penny is the lowest denomination for USD. It will be up to the calling
     *  application to know that it needs to handle this value as pennies and not some other denomination.
     *
     * @return The raw value that was input by the user, in the lowest denomination of that users
     *  locale.
     */
    public long getRawValue() {
        return valueInLowestDenom;
    }

    /**
     * Convenience method to retrieve the users Locale object. The same as calling
     * getResources().getConfiguration().locale
     *
     * @return the Locale object for the given users configuration
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Override the locale used by CurrencyEditText (which is the users device locale by default).
     * WARNING: If this method is used to set the locale to one not supported by ISO 3166,
     * formatting the text will throw an exception. Also keep in mind that calling this method
     * will set the hint based on the specified locale, which will override any previous hint value.
     * @param locale The locale to set the CurrencyEditText box to adhere to.
     */
    public void setLocale(Locale locale){
        this.locale = locale;
        init();
        updateHint();
    }

    public void setCurrency(Currency currency, Locale locale) {
        this.currency = currency;
        this.locale = locale;

        init();
        updateHint();
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;

        init();
        updateHint();
    }

    public Currency getCurrency() {
        return currency;
    }


    private void updateHint() {
        if(hintCache != null){
            setHint(hintCache);
        }
        else{
            if(defaultHintEnabled){
                setHint(getDefaultHintValue());
            }
        }
    }

    /**
     * Override the locale used by CurrencyEditText (which is the users device locale by default).
     * Defaults to Locale.US.
     * NOTE: Be absolutely sure that this value is supported by ISO 3166. See
     * Java.util.Locale.getISOCountries() for a list of currently supported ISO 3166 locales (note that this list
     * may not be identical on all devices)
     * @param locale The fallback locale to recover gracefully in the event of the CurrencyEditText's locale value failing.
     */
    public void setDefaultLocale(Locale locale){
        this.defaultLocale = locale;
        init();
    }

    public Locale getDefaultLocale(){
        return defaultLocale;
    }

    /**
     * Pass in a value to have it formatted using the same rules used during data entry.
     * @param val A string which represents the value you'd like formatted. It is expected that this string will be in the same format returned by the getRawValue() method (i.e. a series of digits, such as
     *            "1000" to represent "$10.00"). Note that formatCuurrency will take in ANY string, and will first strip any non-digit characters before working on that string. If the result of that processing
     *            reveals an empty string, or a string whose number of digits is greater than the max number of digits, an exception will be thrown.
     * @return A locale-formatted string of the passed in value, represented as currency.
     */

    /**
     * Pass in a value to have it formatted using the same rules used during data entry.
     *   A long which represents the value you'd like formatted. It is expected that this value will be in the same format returned by the getRawValue() method (i.e. a series of digits, such as
     *            "1000" to represent "$10.00").
     * @return A locale-formatted string of the passed in value, represented as currency.
     */

    protected void setValueInLowestDenom(Long mValueInLowestDenom) {
        this.valueInLowestDenom = mValueInLowestDenom;
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

    private void initColors() {
        errorUnderlineColor = R.color.Green;

    }
    public void setErrorColor() {
        try {

            getBackground().setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(
                    ContextCompat.getColor(getContext(), errorUnderlineColor), PorterDuff.Mode.SRC_IN));
            ensureBackgroundDrawableStateWorkaround();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
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

    /*
    PRIVATE HELPER METHODS
     */

    private void processAttributes(Context context, AttributeSet attrs){
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CurrencyEditText);

        boolean defaultHintAttrVal = array.getBoolean(R.styleable.CurrencyEditText_enable_default_hint, false);
        configureHint(defaultHintAttrVal);

        this.setAllowNegativeValues(array.getBoolean(R.styleable.CurrencyEditText_allow_negative_values, false));

        array.recycle();
    }

    private void configureHint(boolean defaultHintAttrVal){

        if(hintAlreadySet()){
            this.setDefaultHintEnabled(false);
            this.hintCache = getHint().toString();
            return;
        }
        else{
            this.setDefaultHintEnabled(defaultHintAttrVal);
        }

        if(getDefaultHintEnabled()) {
            this.setHint(getDefaultHintValue());
        }
        else{
            Log.i(this.getClass().getSimpleName(), "configureHint: Default Hint disabled; ignoring request.");
        }
    }

    private boolean hintAlreadySet(){
        return (this.getHint() != null && !this.getHint().equals(""));
    }

    private String getDefaultHintValue() {
        return currency.getSymbol();
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
