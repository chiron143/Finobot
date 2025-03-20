package com.purplepath.purplepath.customview;

import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by dinesh on 08/03/17.
 */

public class PercentageTextWatcher implements TextWatcher {

    final int CURSOR_SPACING_COMPENSATION = 1;
    //Setting a max length because after this length, java represents doubles in scientific notation which breaks the formatter
    final int MAX_RAW_INPUT_LENGTH = 20;
    Locale indiaLocale = new Locale("hi", "IN");
    double CURRENCY_DECIMAL_DIVISOR;
    String current = "";
    private PercentageEditText editText;
    private Locale defaultLocale;
    private boolean ignoreIteration;
    private String lastGoodInput;

    /**
     * A specialized TextWatcher designed specifically for converting EditText values to a pretty-print string currency value.
     *
     * @param textBox The EditText box to which this TextWatcher is being applied.
     *                Used for replacing user-entered text with formatted text as well as handling cursor position for inputting monetary values
     */
    public PercentageTextWatcher(PercentageEditText textBox) {
        this(textBox, new Locale("hi", "IN"));
    }

    /**
     * A specialized TextWatcher designed specifically for converting EditText values to a pretty-print string currency value.
     *
     * @param textBox       The EditText box to which this TextWatcher is being applied.
     *                      Used for replacing user-entered text with formatted text as well as handling cursor position for inputting monetary values
     * @param defaultLocale optional locale to default to in the event that the provided CurrencyEditText locale fails due to being unsupported
     */
    public PercentageTextWatcher(PercentageEditText textBox, Locale defaultLocale) {
        editText = textBox;
        lastGoodInput = "";
        ignoreIteration = false;
        this.defaultLocale = defaultLocale;
        current = "";
        //Different countries use different fractional values for denominations (0.999 <x> vs. 0.99 cents), therefore this must be defined at runtime
        try {
            CURRENCY_DECIMAL_DIVISOR = (int) Math.pow(10, Currency.getInstance(editText.getLocale()).getDefaultFractionDigits());
        } catch (IllegalArgumentException e) {
//            //Log.e("CurrencyDefaultTextWatcher", "Unsupported locale provided, defaulting to Locale.US. Error: " + e.getMessage());
            CURRENCY_DECIMAL_DIVISOR = (int) Math.pow(10, Currency.getInstance(defaultLocale).getDefaultFractionDigits());
        }
    }

    /**
     * After each letter is typed, this method will take in the current text, process it, and take the resulting
     * formatted string and place it back in the EditText box the TextWatcher is applied to
     *
     * @param editable text to be transformed
     */
    @Override
    public void afterTextChanged(Editable editable) {
        try {
            if( editText.getParent().getParent() instanceof TextInputLayout )
                ((TextInputLayout) editText.getParent().getParent()).setErrorEnabled(false);
            ((TextInputLayout) editText.getParent().getParent()).setError(null);
//            else
//            editText.setError(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!editable.toString().isEmpty())
                if (!editable.toString().equals(current)) {
                    if ((Double.parseDouble(editable.toString()) <= 100) && (Double.parseDouble(editable.toString()) >= 0)) {
//
//                        double parsed;
//                        String formated = "";
//                        try {
//                            parsed = Double.parseDouble(editable.toString().replace(".",""));
////                            parsed = (parsed / 100);
//                            formated = (new DecimalFormat("#0.00").format(parsed));
//                        } catch (NumberFormatException e) {
//                            parsed = 0.00;
//                        }
//
//
                        current = editable.toString();
//                        editText.setText(formated);
//                        editText.removeTextChangedListener(this);
////                        current = editable.toString();
////                        editText.setText(current);
//                        editText.setSelection(formated.length());
//                        editText.addTextChangedListener(this);

                    } else {
                        try {
                            editText.removeTextChangedListener(this);
                            current = editable.toString();
//                        editText.setText(current);
//                        editText.setSelection(current.length());

                            ((TextInputLayout) (editText.getParent().getParent())).setError("Invalid, % values should be between 0 to 100");
//                        Log.i("spcheck", "afterTextChanged: check test");
                        } catch (Exception e) {
                            e.printStackTrace();
//                            ((TextInputLayout) (editText.getParent())).setError("Invalid, % values should be between 0 to 100");
                        }
                        editText.addTextChangedListener(this);

                    }
                } else if ((Double.parseDouble(editable.toString()) > 100)) {
                       if(editText.getParent().getParent() instanceof TextInputLayout)
                           ((TextInputLayout) (editText.getParent().getParent())).setError("Invalid, % values should be between 0 to 100");
                       else if( editText.getParent() instanceof TextInputLayout )
                           ((TextInputLayout) (editText.getParent())).setError("Invalid, % values should be between 0 to 100");
                       else
                        editText.setError("Invalid, % values should be between 0 to 100");
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
    }


}