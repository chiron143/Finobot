package com.purplepath.purplepath.customview;

import android.text.Editable;
import android.text.TextWatcher;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by pravinr on 1/19/18.
 */

public class CurrencyDefaultEditTextWatcher implements TextWatcher {

    private CurrencyDefaultEdittext editText;
    private Locale defaultLocale;

    private boolean ignoreIteration;
    private String lastGoodInput;
    Locale indiaLocale = new Locale("hi", "IN");
    double CURRENCY_DECIMAL_DIVISOR;
    final int CURSOR_SPACING_COMPENSATION = 1;

    //Setting a max length because after this length, java represents doubles in scientific notation which breaks the formatter
    final int MAX_RAW_INPUT_LENGTH = 20;
    String current;
    /**
     * A specialized TextWatcher designed specifically for converting EditText values to a pretty-print string currency value.
     * @param textBox The EditText box to which this TextWatcher is being applied.
     *                Used for replacing user-entered text with formatted text as well as handling cursor position for inputting monetary values
     */
    public CurrencyDefaultEditTextWatcher(CurrencyDefaultEdittext textBox){
        this(textBox, new Locale("hi", "IN"));
    }

    /**
     * A specialized TextWatcher designed specifically for converting EditText values to a pretty-print string currency value.
     * @param textBox The EditText box to which this TextWatcher is being applied.
     *                Used for replacing user-entered text with formatted text as well as handling cursor position for inputting monetary values
     * @param defaultLocale optional locale to default to in the event that the provided CurrencyEditText locale fails due to being unsupported
     */
    public CurrencyDefaultEditTextWatcher(CurrencyDefaultEdittext textBox, Locale defaultLocale){
        editText = textBox;
        lastGoodInput = "";
        ignoreIteration = false;
        this.defaultLocale = defaultLocale;
        current = "";
        //Different countries use different fractional values for denominations (0.999 <x> vs. 0.99 cents), therefore this must be defined at runtime
        try{
            CURRENCY_DECIMAL_DIVISOR = (int) Math.pow(10, Currency.getInstance(editText.getLocale()).getDefaultFractionDigits());
        }
        catch(IllegalArgumentException e){
//            //Log.e("CurrencyDefaultTextWatcher", "Unsupported locale provided, defaulting to Locale.US. Error: " + e.getMessage());
            CURRENCY_DECIMAL_DIVISOR = (int) Math.pow(10, Currency.getInstance(defaultLocale).getDefaultFractionDigits());
        }
    }


    /**
     * After each letter is typed, this method will take in the current text, process it, and take the resulting
     * formatted string and place it back in the EditText box the TextWatcher is applied to
     * @param editable text to be transformed
     */
    @Override
    public void afterTextChanged(Editable editable) {
        try {
            if (!editable.toString().equals(current)) {
                editText.removeTextChangedListener(this);
                Locale indiaLocale = new Locale("hi", "IN");
                String replaceable = String.format("[%s,.\\s]", NumberFormat.getInstance().getCurrency().getSymbol());

                String cleanString = editable.toString().replaceAll(replaceable, "");

                double parsed;
                try {
                    parsed = Double.parseDouble(cleanString);
                } catch (NumberFormatException e) {
                    parsed = 0.00;
                }
//            NumberFormat formatter = NumberFormat.getInstance().getCurrencyInstance();
                NumberFormat formatter = NumberFormat.getCurrencyInstance(indiaLocale);
                formatter.setMaximumFractionDigits(0);
                String formatted = formatter.format((parsed));
                current = formatted.replaceAll("[^0-9 ,]", "");
                editText.setText(current);
                editText.setSelection(current.length());
                editText.addTextChangedListener(this);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}




}
