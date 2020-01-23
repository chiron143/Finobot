package com.calculator;

/**
 * Created by bertrandrussellsakthees on 06/04/17.
 */

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.CrashExceptionHandler;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class CalculatorAct extends AppCompatActivity  implements OnClickListener, View.OnKeyListener {
    public static final int NUMBER_EDIT_TEXT_MAX_LENGTH = 30;
    public static final int REQUEST_RESULT_SUCCESSFUL = 2;
    public static final String TITLE_ACTIVITY = "title_activity";
    public static final String PARENT_ACTIVITY = "parent_activity";
    public static final String VALUE = "value_calculator";
    public static final String RESULT = "result_calculator";
    public static final String NEGATIVEFLAG = "negativeFlag";
    public static final String DECIMALFLAG="decimalFlag";

    public static final String ZERO = "0";
    public static final String ZERO_ZERO = "00";
    public static final String POINT = ".";
    public static final String CLICK_ARITHMETIC_OPERATOR = "clickArithmeticOperator";
    public static final String CLICK_EQUAL_OPERATOR = "clickEqualOperator";
    public static final String CLEAR_INPUT = "clearInput";
    public static final String FIRST_VALUE = "firstValue";
    public static final String SECONDS_VALUE = "secondsValue";
    public static final String OPERATOR_EXECUTE = "operatorExecute";
    public static final String ZERO_ZERO_ZERO = "000";
    public int edit = 0;

    //new
    private int count=0;
    private String expression="";
    private String text="";
    private Double result=0.0;

    private int[] numericButtons = {R.id.zero_button, R.id.one_button, R.id.tow_button, R.id.three_button, R.id.four_button, R.id.five_button, R.id.six_button, R.id.seven_button, R.id.eight_button, R.id.nine_button,R.id.three_zero_button};
    // IDs of all the operator buttons
    private int[] operatorButtons = {R.id.sum_button, R.id.subtraction_button, R.id.multiplication_button, R.id.divider_button};
    // TextView used to display the output
    private TextView txtScreen;
    // Represent whether the lastly pressed key is numeric or not
    private boolean lastNumeric;
    // Represent that current state is in error or not
    private boolean stateError;
    // If true, do not allow to add another DOT
    private boolean lastDot;

    private DecimalFormat decimalFormat;
    Toolbar toolbar;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout,mRightRelativeLayout;
    OnActivityBackPressedListener backPressedListener;
    //input
    private CurrencyEditText inputNumberText;
    private EditText developmentOperationInputText;

    //button operation
    private Button clearBtn;
    private Button dividerBtn;
    private Button multiplicationBtn;
    private Button deleteBtn;
    private Button subtractionBtn;
    private Button sumBtn;
    private Button equalBtn;
    private Button submitBtn;

    //button numeric
    private Button pointBtn;
    private Button zeroBtn;
    private Button threeZeroBtn;
    private Button oneBtn;
    private Button towBtn;
    private Button threeBtn;
    private Button fourBtn;
    private Button fiveBtn;
    private Button sixBtn;
    private Button sevenBtn;
    private Button eightBtn;
    private Button nineBtn;
    private Button del;

    //operations values
    private boolean clickArithmeticOperator;
    private boolean clickEqualOperator;
    private boolean clearInput;
    private Double firstValue;
    private Double secondsValue;

    Locale indianlocal = new Locale("en", "IN");
    Boolean negatieFlag,decimalFlag;
    final String currencyReplaceString=String.format("[%s,.\\s]",NumberFormat.getInstance().getCurrency().getSymbol());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc);
        Thread.setDefaultUncaughtExceptionHandler(new CrashExceptionHandler(this,CalculatorAct.class));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mleftRelativeLayout = (RelativeLayout) findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = (RelativeLayout) findViewById(R.id.relative_center_home);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        //toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.bottom_back_icon));
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        developmentOperationInputText = (EditText) findViewById(R.id.developing_operation_inputText);
        developmentOperationInputText.setOnKeyListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            developmentOperationInputText.setShowSoftInputOnFocus(false);
        }


        inputNumberText = (CurrencyEditText) findViewById(R.id.number_inputText);
        inputNumberText.setOnKeyListener(this);


        setupActionBar();
        initComponents();


//        del = (Button)findViewById(R.id.delete_button);
//        del.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                removeLastNumber();
//            }
//        });

    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.relative_left_arrow:
                onBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(CalculatorAct.this, HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

            case R.id.zero_button:
                if(inputNumberText.length() <= 23)
                    inputNumberText.setText(inputNumberText.getText()+"0");

                break;

            case R.id.one_button:
                if(inputNumberText.length() <= 23)
                    inputNumberText.setText(inputNumberText.getText()+"1");
                break;

            case R.id.tow_button:
                if(inputNumberText.length() <= 23)
                    inputNumberText.setText(inputNumberText.getText()+"2");
                break;

            case R.id.three_button:
                if(inputNumberText.length() <= 23)
                    inputNumberText.setText(inputNumberText.getText()+"3");
                break;


            case R.id.four_button:
                if(inputNumberText.length() <= 23)
                    inputNumberText.setText(inputNumberText.getText()+"4");
                break;

            case R.id.five_button:
                if(inputNumberText.length() <= 23)
                    inputNumberText.setText(inputNumberText.getText()+"5");
                break;


            case R.id.six_button:
                if(inputNumberText.length() <= 23)
                    inputNumberText.setText(inputNumberText.getText()+"6");
                break;

            case R.id.seven_button:
                if(inputNumberText.length() <= 23)
                    inputNumberText.setText(inputNumberText.getText()+"7");
                break;

            case R.id.eight_button:
                if(inputNumberText.length() <= 23)
                    inputNumberText.setText(inputNumberText.getText()+"8");
                break;

            case R.id.nine_button:

                if(inputNumberText.length() <= 21)
                    inputNumberText.setText(inputNumberText.getText()+"9");
                Log.i("hfi","los"+inputNumberText.length());
                break;

            case R.id.three_zero_button:

                if(inputNumberText.length() <= 23)
                    inputNumberText.setText(inputNumberText.getText()+"000");
                break;

            case R.id.point_button:
                if(count==0 && inputNumberText.length()!=0)
                {
                    inputNumberText.setText(inputNumberText.getText()+".");
                    count++;
                }
                break;

            case R.id.clear_button:
                clear();
                count=0;
                expression="";
                break;

            case R.id.delete_button:

                text=inputNumberText.getText().toString();

                if(text.length()>0)
                {
                    if(text.endsWith("."))
                    {
                        count=0;
                    }
                    String newText=text.substring(0,text.length()-1);
                    //to delete the data contained in the brackets at once
                    if(text.endsWith(")"))
                    {
                        char []a=text.toCharArray();
                        int pos=a.length-2;
                        int counter=1;
                        //to find the opening bracket position
                        for(int ii=a.length-2;ii>=0;ii--)
                        {
                            if(a[ii]==')')
                            {
                                counter++;
                            }
                            else if(a[ii]=='(')
                            {
                                counter--;
                            }
                            //if decimal is deleted b/w brackets then count should be zero
                            else if(a[ii]=='.')
                            {
                                count=0;
                            }
                            //if opening bracket pair for the last bracket is found
                            if(counter==0)
                            {
                                pos=ii;
                                break;
                            }
                        }
                        newText=text.substring(0,pos);
                    }
                    //if e2 edit text contains only - sign or sqrt at last then clear the edit text e2
                    if(newText.equals("-")||newText.endsWith("sqrt"))
                    {
                        newText="";
                    }
                    //if pow sign is left at the last
                    else if(newText.endsWith("^"))
                        newText=newText.substring(0,newText.length()-1);


                    inputNumberText.setText(newText);

                }
                break;

//            case R.id.backSpace:
//
//                int cursorPosition = developmentOperationInputText.getSelectionStart();
//                    text=developmentOperationInputText.getText().toString();
//                if(text.length()>0)
//                {
//                    if(text.endsWith("."))
//                    {
//                        count=0;
//                    }
//                    String newText=text.substring(0,cursorPosition);
//                    //to delete the data contained in the brackets at once
//                    if(text.endsWith(")"))
//                    {
//                        char []a=text.toCharArray();
//                        int pos=a.length-2;
//                        int counter=1;
//                        //to find the opening bracket position
//                        for(int ii=a.length-2;ii>=0;ii--)
//                        {
//                            if(a[ii]==')')
//                            {
//                                counter++;
//                            }
//                            else if(a[ii]=='(')
//                            {
//                                counter--;
//                            }
//                            //if decimal is deleted b/w brackets then count should be zero
//                            else if(a[ii]=='.')
//                            {
//                                count=0;
//                            }
//                            //if opening bracket pair for the last bracket is found
//                            if(counter==0)
//                            {
//                                pos=ii;
//                                break;
//                            }
//                        }
//                        newText=text.substring(0,pos);
//                    }
//                    //if e2 edit text contains only - sign or sqrt at last then clear the edit text e2
//                    if(newText.equals("-")||newText.endsWith("sqrt"))
//                    {
//                        newText="";
//                    }
//                    //if pow sign is left at the last
//                    else if(newText.endsWith("^"))
//                        newText=newText.substring(0,newText.length()-1);
//
//
//                        developmentOperationInputText.setText(newText);
//                }
//                break;

            case R.id.sum_button:
                operationClicked("+");
                break;

            case R.id.subtraction_button:
                operationClicked("-");
                break;

            case R.id.divider_button:
                operationClicked("/");
                break;

            case R.id.multiplication_button:
                operationClicked("*");
                break;


            case R.id.submit_button:
                returnResultOperation();
                break;



            case R.id.equal_button:
                String result1 = inputNumberText.getText().toString().replaceAll(currencyReplaceString,"").replaceAll("[^0-9.]","");


                if(result1.length()!=0)
                {

                    text=result1;
                    expression=developmentOperationInputText.getText().toString().replaceAll(currencyReplaceString,"").replaceAll("[^0-9.+-/*]","")+text;
                }
                developmentOperationInputText.setText("");
                if(expression.length()==0)
                    expression="0.0";
                DoubleEvaluator evaluator = new DoubleEvaluator();
                try
                {
                    //evaluate the expression
                    result=new ExtendedDoubleEvaluator().evaluate(expression);



                    BigDecimal bd1 = new BigDecimal(result); //convert to BigDecimal
                    BigInteger bi = bd1.unscaledValue(); //convert to BigInteger

                    BigDecimal bd11 = new BigDecimal(9999999999999999L);
                    BigInteger bi2 = bd11.unscaledValue();
                    //insert expression and result in sqlite database if expression is valid and not 0.0
                    int res;

                    // compare bi1 with bi2
                    res = bi.compareTo(bi2);

                    if((!expression.equals("0.0")) ) {
                        if (( res == 1 )) {

                            new Handler().postDelayed(new Runnable() {



                                @Override
                                public void run() {
                                    developmentOperationInputText.setText("");

                                }
                            }, 2500);

                            developmentOperationInputText.setText("Excessive Value..");
                            inputNumberText.setText("");

                        } else

                            inputNumberText.setText(bi + "");
                        //inputNumberText.setText("");
                    }
                }
                catch (Exception e)
                {
                    inputNumberText.setText("Invalid Expression");
                    developmentOperationInputText.setText("");
                    expression="";
                    e.printStackTrace();
                }
                break;


        }
    }

    private void operationClicked(String op)
    {

        if(inputNumberText.length()!=0)
        {
            String text=inputNumberText.getText().toString();
            developmentOperationInputText.setText(developmentOperationInputText.getText() + text+op);
            inputNumberText.setText("");
            count=0;
        }
        else
        {
            String text=developmentOperationInputText.getText().toString();
            if(text.length()>0)
            {
                String newText=text.substring(0,text.length()-1)+op;
                developmentOperationInputText.setText(newText);
            }
        }
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        if (outState != null) {
//            outState.putBoolean(CLICK_ARITHMETIC_OPERATOR, clickArithmeticOperator);
//            outState.putBoolean(CLICK_EQUAL_OPERATOR, clickEqualOperator);
//            outState.putBoolean(CLEAR_INPUT, clearInput);
//            outState.putDouble(FIRST_VALUE, firstValue);
//            outState.putDouble(SECONDS_VALUE, secondsValue);
//            outState.putString(OPERATOR_EXECUTE, operatorExecute);
//        }
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        if (savedInstanceState != null) {
//            clickArithmeticOperator = savedInstanceState.getBoolean(CLICK_ARITHMETIC_OPERATOR);
//            clickEqualOperator = savedInstanceState.getBoolean(CLICK_EQUAL_OPERATOR);
//            clearInput = savedInstanceState.getBoolean(CLEAR_INPUT);
//            firstValue = savedInstanceState.getDouble(FIRST_VALUE);
//            secondsValue = savedInstanceState.getDouble(SECONDS_VALUE);
//            operatorExecute = savedInstanceState.getString(OPERATOR_EXECUTE);
//        }
//    }

    @Override
    public Intent getParentActivityIntent() {
        String className = getIntent().getStringExtra(PARENT_ACTIVITY);

        if (className == null)
            return null;

        Intent newIntent = null;

        try {
            newIntent = new Intent(this, Class.forName(className));
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private void initComponents() {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator(',');
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormat = new DecimalFormat("#,###,##0.00", decimalFormatSymbols);

        setTitle(getIntent().getStringExtra(TITLE_ACTIVITY));

        String value = TextUtils.isEmpty(getIntent().getStringExtra(VALUE)) ? ZERO : getIntent().getStringExtra(VALUE);

        negatieFlag = getIntent().getBooleanExtra(NEGATIVEFLAG, false);
        decimalFlag = getIntent().getBooleanExtra(DECIMALFLAG, false);

        inputNumberText.setAllowNegativeValues(true);
//        if(decimalFlag)
        inputNumberText.setDecimalDigits(0);

//        inputNumberText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(NUMBER_EDIT_TEXT_MAX_LENGTH)});
        inputNumberText.setText(value);
        inputNumberText.setLocale(indianlocal);
    }






    private void removeLastNumber() {
        String value = inputNumberText.getText().toString().replaceAll(currencyReplaceString,"").replaceAll("[^0-9.+-/*]","");

        if (TextUtils.isEmpty(value) || value.length() == 1) {
            inputNumberText.setText(ZERO);
            return;
        }

        inputNumberText.setText(value.substring(0, value.length() - 1));
    }




    private void returnResultOperation() {

        String result = inputNumberText.getText().toString().replaceAll(currencyReplaceString,"").replaceAll("[^0-9.]","");
        Intent resultIntent = new Intent();
        resultIntent.putExtra(RESULT, result);

        setResult(REQUEST_RESULT_SUCCESSFUL, resultIntent);
        finish();
    }



    private void clear() {
        firstValue = null;
        secondsValue = null;


        developmentOperationInputText.setText("");
        inputNumberText.setText(ZERO);
    }


    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if (i == EditorInfo.IME_ACTION_SEARCH ||
                i == EditorInfo.IME_ACTION_DONE ||
                keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                        keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

            if (!keyEvent.isShiftPressed()) {
                Log.v("AndroidEnterKeyActivity","Enter Key Pressed!");
                switch (view.getId()) {
                    case R.id.developing_operation_inputText:

                        edit = 1;
                        break;
                    case R.id.number_inputText:

                        edit = 0;
                        break;
                }
                return true;
            }

        }

        return false;
    }
}