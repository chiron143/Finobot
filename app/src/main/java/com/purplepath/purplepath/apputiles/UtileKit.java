package com.purplepath.purplepath.apputiles;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatDrawableManager;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.R;
import com.fourmob.datetimepicker.Utils;
import com.purplepath.purplepath.customview.CalendarEditText;
import com.purplepath.purplepath.customview.CharacterEditText;
import com.purplepath.purplepath.customview.CurrencyDefaultEdt;
import com.purplepath.purplepath.customview.CurrencyEditText;
import com.purplepath.purplepath.customview.CurrencyTextView;
import com.purplepath.purplepath.customview.CustomTextView;
import com.purplepath.purplepath.customview.NumberEditText;
import com.purplepath.purplepath.customview.PercentageEditText;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.finobot.finobot.MyApplication.getContext;


/**
 * Created by dinesh on 09/05/16.
 */
public class UtileKit implements AppConstants {

    public static final String EMPTY_STRING = "";
    public static final String FALSE_STRING = "false";
    public static final String TRUE_STRING = "true";
    public static final String NO_NETWORK_CONNECTION = "No Network Connection";
    public static final String SERVER_DOWN = "SERVER DOWN";
    public static final String NO_DATA = "NO DATA";
    public static final String NO_DATA_FOUND = "No data found";
    public static final String FAILURE = "failure";
    public static final String ERROR = "error";
    public static final String SUCCESS = "Success";
    public static final String ERRORCODE = "300";
    public static final String SUCCESSCODE = "200";
    public static final String SUCCESS_OVERRIDE_CODE = "100";
    public static final String EXPANCE_CATAGORY_PREF = "expense_pref";
    public static final String INSURANCE_CATAGORY_PREF = "insurace_cat_pref";
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 101;
    public static final String is_version_update = "Yes";

    public static final String BLANK = "\u0020"; // space character

    //Payment
    public static final String lite_paid_type = "1";
    public static final String pro_paid_type = "2";
    public static final String prime_paid_type = "3";

    public static final String validity_one_year = "12";
    public static final String validity_two_year = "24";
    public static final String validity_three_year = "36";
    public static int avoidRefreshFlag = 1;


    public static final String[] MONTHS_IN_ENGLISH = new String[]{"January", " February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    public static final String[] DATES = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    public static final ArrayList<String> monthInArray = new ArrayList(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
            "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
            "51", "52", "53", "54", "55", "56", "57", "58", "59", "60"));

    private final static String TAG = UtileKit.class.getCanonicalName();
    public static Dialog dialog;
    public static Dialog exploredialog;
    public static Handler mProgressHandler;
    public static Handler mProgressExploreHandler;
    public static String exploremessage;
    public static boolean dialogActive = false;
    public static String message;
    public static boolean isAppOverlay = false;
    public static SharedPreferences purplepathPref, finobotPrefForFirsttime;
    public static String year, month, day;
    public static AlertDialog alertDialog;

    public static int constantsTab = 0;
    /**
     *
     */
    public static String yearToSave, monthToSave, dayToSave, calenderHeading, CurrentBelowTextToBeUpdate, gotServiceText, dobyearToSave, dobmonthToSave, dobdayToSave;
    public static int Year1[];

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    // static String convertConcat;
    private static boolean explorecancelable = true;
    private static boolean cancelable = true;
    private static Context context;
    public static Runnable mShowCustomSpinnerDialog = new Runnable() {

        public void run() {
            try {
                if (message != null)
                    showSpinner(context, message);
                else
                    showSpinner(context);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        /**
         * @param ctx
         */
        private void showSpinner(Context ctx) {
            try {
                if (isDialogShown()) {
                    dismisssSpinnerDialog();
                }
                dialog = new Dialog(ctx, android.R.style.Theme_Translucent);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(UtileKit.cancelable);
                dialog.setContentView(R.layout.process_spinner);
                dialog.show();
            } catch (Exception e) {
                dialog = null;
            }
        }

        private void showSpinner(Context ctx, String msg) {
            try {
                if (isDialogShown()) {
                    dismisssSpinnerDialog();
                }
                dialog = new Dialog(ctx, android.R.style.Theme_Translucent);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(UtileKit.cancelable);
                dialog.setContentView(R.layout.process_spinner);
                TextView text = dialog.findViewById(R.id.textView1);
                text.setText(msg);
                dialog.show();
            } catch (Exception e) {
                dialog = null;
            }
        }
    };
    // srini
    public static Runnable mShowCustomSpinnerDialogForExplore = new Runnable() {

        public void run() {
            try {
                if (exploremessage != null)
                    showSpinner(context, exploremessage);
                else
                    showSpinner(context);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        /**
         * @param ctx
         */
        private void showSpinner(Context ctx) {
            try {
                if (isExploreDialogShown()) {
                    dismisssExploreSpinnerDialog();
                }
                exploredialog = new Dialog(ctx, android.R.style.Theme_Translucent);
                exploredialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                exploredialog.setCancelable(UtileKit.explorecancelable);
                exploredialog.setContentView(R.layout.process_spinner);
                exploredialog.show();
            } catch (Exception e) {
                exploredialog = null;
            }
        }

        private void showSpinner(Context ctx, String msg) {
            try {
                if (isExploreDialogShown()) {
                    dismisssExploreSpinnerDialog();
                }
                exploredialog = new Dialog(ctx, android.R.style.Theme_Translucent);
                exploredialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                exploredialog.setCancelable(UtileKit.explorecancelable);
                exploredialog.setContentView(R.layout.process_spinner);
                TextView text = exploredialog.findViewById(R.id.textView1);
                text.setText(msg);
                exploredialog.show();
            } catch (Exception e) {
                exploredialog = null;
            }
        }
    };
    private Context mContext;

    public static String getTextFromObjects(Object object) {
        if (validateObjectValues(object)) {
            String _textStr = null;
            if (object instanceof Button) {
                _textStr = ((Button) object).getText().toString().trim();
            } else if (object instanceof EditText) {
                _textStr = ((EditText) object).getText().toString().trim();
            } else if (object instanceof TextView) {
                _textStr = ((TextView) object).getText().toString().trim();
            } else if (object instanceof JSONObject) {
                _textStr = object.toString().trim();
            } else if (object instanceof Integer) {
                _textStr = String.valueOf(object).trim();
            } else if (object instanceof Long) {
                _textStr = String.valueOf(object).trim();
            } else if (object instanceof Double) {
                _textStr = String.valueOf(object).trim();
            } else
                _textStr = object.toString().trim();

            if (validateObjectValues(_textStr))
                return _textStr;
            else
                return EMPTY_STR;
        } else {
            return null;
        }

    }

    public static boolean validateObjectValues(Object _validateObj) {
        if (_validateObj == null)
            return false;
        else {
            return !(_validateObj instanceof String
                    && ((String) _validateObj).trim().equalsIgnoreCase(
                    EMPTY_STR));
        }
    }


    public static boolean validateObjectValuesAndCheckZero(Object _validateObj) {
        if (_validateObj == null)
            return false;
        else if ((_validateObj instanceof String
                && ((String) _validateObj).trim().equalsIgnoreCase(
                EMPTY_STR))) {
            return false;
        } else return !((String) _validateObj).trim().equals(WEBSERVICE_DEFAULT_VALUE);

    }

    public static String validateObjectValuesreturnZero(String _validateObj) {

        if (StringUtils.isEmpty(_validateObj)) {
            return "0.0";
        } else {
            return _validateObj;
        }
    }

    public static String logTest(String Topic, String string) {
        Log.i(Topic, string);
        return string;
    }

    public static EditText nameCharacterOnly(final EditText editText) {
        editText.setFilters(new InputFilter[]{
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if (src.equals("")) { // for backspace
                            return src;
                        }
                        if ((editText.getText().toString() + src).matches("[a-zA-Z ]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        });
        return editText;
    }

    public static CharacterEditText addFilterToUpperNumber(final CharacterEditText characterEditText) {

        characterEditText.setFilters(new InputFilter[]{
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if (src.equals("")) { // for backspace
                            return src;
                        }
                        if ((characterEditText.getText().toString() + src).matches("[A-Z 0-9]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        });
        return characterEditText;
    }


    public static void setHtml(TextView textView, String string) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
        } else {
            textView.setText(Html.fromHtml(string));

        }

    }

    public static Spanned setHtml(String string) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(string);

        }

    }

    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    public static boolean validateEmail(String email) {
        // TODO Auto-generated method stub
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //Added by Murali
//    public static void alertDialog( String message, Context context) {
//        try {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setMessage(message);
//        builder.setPositiveButton(context.getString(R.string.dialog_ok), null);
//        builder.setCancelable(true);
//        AlertDialog alert = builder.show();
//        TextView messageText = (TextView)alert.findViewById(android.R.id.message);
//        messageText.setGravity(Gravity.CENTER);
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//    }

    public static void alertRetrofitExceptionalert(String message, final Context context) {
        LayoutInflater inflater;
        View dialogView;
        inflater = LayoutInflater.from(UtileKit.context);
        dialogView = inflater.inflate(R.layout.alert_message_layout, null);
        alertDialog = new AlertDialog.Builder(UtileKit.context).create();
        alertDialog.setView(dialogView);
        TextView erroreMessage = dialogView.findViewById(R.id.textViewDilog);

        Log.i(TAG, "intitializeAlertDialog " + message);
        erroreMessage.setText(message);
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityCompat.finishAffinity((Activity) context);
                } else
                    ((Activity) context).finish();

            }
        });

        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }

    }

    //number formater in Indian format
    public static String formatedNumber(Float value) {
        DecimalFormat myFormatter = new DecimalFormat("#,##,###");
        String output = myFormatter.format(value);
        return output;
    }

    //number formater in Indian format
    public static String formatedNumber(Double value) {
        DecimalFormat myFormatter = new DecimalFormat("#,##,###");
        String output = myFormatter.format(value);
        return output;
    }

    public static String rounddecimalNumber(String value) {

        BigDecimal decimal = new BigDecimal(value);
        decimal = decimal.setScale(2, RoundingMode.HALF_UP);
        String result = decimal.toString();
        return result;

    }


    public static String concatdinateRupeeSymbol(String value) {
        String output = null;
        if (value != null) {
            try {
                output = "₹ ".concat(value);
            } catch (Exception e) {
                e.printStackTrace();

            }
            return output;
        }
        return output;
    }

    public static String formatedNumbers(BigInteger value) {
        DecimalFormat myFormatter = new DecimalFormat("#,##,###");
        String output = myFormatter.format(value);
        return output;
    }

    public static String formatedNumbers_decimal(BigDecimal value) {
        DecimalFormat myFormatter = new DecimalFormat("#,##,###");
        String output = myFormatter.format(value);
        return output;
    }


    public static String formatedNumbersFloat(Float value) {
        DecimalFormat myFormatter = new DecimalFormat("#,##,###");
        String output = myFormatter.format(value);
        return output;
    }


    public static String addIntegervalue(String valueone, String valuetwo) {

        BigInteger v1 = new BigInteger(UtileKit.getStringwithoutCurreny(valueone));

        BigInteger v2 = new BigInteger(UtileKit.getStringwithoutCurreny(valuetwo));

        return v1.add(v2).toString();

    }

    public static String currToCharConversion(String rounded) {
        String convertConcat = "0.0";
        try {
            BigDecimal value = new BigDecimal(new DecimalFormat("###########0.00").format(new BigDecimal(getStringwithoutCurreny(rounded).replace("\\s+", ""))));

            BigDecimal valuRound;
            if (value.compareTo(BigDecimal.valueOf(1000)) == -1) {
                return value + "";
            } else if (value.compareTo(BigDecimal.valueOf(999)) == 1 && value.compareTo(BigDecimal.valueOf(100000)) == -1) {
                if (value.compareTo(new BigDecimal(0)) != 0) {
                    valuRound = value.divide(new BigDecimal(1000));
                    convertConcat = roundoftwodecimalpoint(valuRound) + "K";
                    return convertConcat;
                }
            } else if (value.compareTo(BigDecimal.valueOf(100000)) >= 0 && value.compareTo(BigDecimal.valueOf(10000000)) == -1) {
                valuRound = value.divide(new BigDecimal(100000));
                return roundoftwodecimalpoint(valuRound) + "L";
            } else if (value.compareTo(BigDecimal.valueOf(10000000)) >= 0) {
                valuRound = value.divide(new BigDecimal(10000000));
                return roundoftwodecimalpoint(valuRound) + "CR";
            } else if (value.compareTo(BigDecimal.valueOf(0)) <= 0 && value.compareTo(BigDecimal.valueOf(-100000)) == 1) {
                valuRound = value.divide(new BigDecimal(1000));
                return "-" + roundoftwodecimalpoint(valuRound) + "K";
            } else if (value.compareTo(BigDecimal.valueOf(-1000000)) <= 0 && value.compareTo(BigDecimal.valueOf(-10000000)) == 1) {
                valuRound = value.divide(new BigDecimal(100000));
                return "-" + roundoftwodecimalpoint(valuRound) + "L";
            } else if (value.compareTo(BigDecimal.valueOf(-10000000)) <= 0) {
                valuRound = value.divide(new BigDecimal(10000000));
                return "-" + roundoftwodecimalpoint(valuRound) + "CR";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertConcat;
    }


    public static String currToCharConversionabsolute(float value) {

        float rounded = 0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String convertConcat = "0.00";
        if (value > 0 && value < 1000) {
            if (value != 0) {
                rounded = Math.round(value);
                convertConcat = decimalFormat.format(rounded) + "";
            }
        } else if (value >= 1000 && value < 10000) {
            rounded = Math.round(value / 100);
            convertConcat = decimalFormat.format(rounded) + "K";

        } else if (value > 1000 && value < 100000) {
            rounded = Math.round(value / 1000);
            convertConcat = decimalFormat.format(rounded) + "K";

        } else if (value >= 100000 && value < 10000000) {
            rounded = value / 100000;
            convertConcat = decimalFormat.format(rounded) + "L";
        } else if (value >= 10000000) {
            rounded = value / 10000000;
            convertConcat = decimalFormat.format(rounded) + "CR";
        } else if (value < 0 && value > -1000) {
            rounded = Math.round(value);
            convertConcat = decimalFormat.format(rounded) + "";
        } else if (value <= -1000 && value >= -10000) {
            rounded = Math.round(value / 100);
            convertConcat = decimalFormat.format(rounded) + "K";
        } else if (value < -1000 && value >= -100000) {
            rounded = Math.round(value / 1000);
            convertConcat = decimalFormat.format(rounded) + "K";
        } else if (value < -100000 && value > -10000000) {
            rounded = value / 100000;
            convertConcat = decimalFormat.format(rounded) + "L";
        } else if (value <= -10000000) {
            rounded = value / 10000000;
            convertConcat = decimalFormat.format(rounded) + "CR";
        }
        Log.i("", "currToCharConversionabsolute" + convertConcat);
        return convertConcat;
    }


    public static double roundoftwodecimalpoint(BigDecimal value) {
        try {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            return Double.parseDouble(decimalFormat.format(value));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static String longvalueabsolute(float value) {
        float rounded = 0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        String convertConcat = "0.00";
        if (value > 0 && value < 100000) {
            if (value != 0) {
                rounded = value / 1000;
                // decimalFormat.format(rounded);
                convertConcat = decimalFormat.format(rounded) + "K";
            }
        } else if (value >= 100000 && value < 10000000) {
            rounded = value / 100000;
            //decimalFormat.format(rounded);
            convertConcat = decimalFormat.format(rounded) + "L";
        } else if (value >= 10000000) {
            rounded = value / 10000000;
            //decimalFormat.format(rounded);
            convertConcat = decimalFormat.format(rounded) + "Cr";
        } else if (value < 0 && value > -100000) {
            rounded = value / 1000;
            //decimalFormat.format(rounded);
            convertConcat = decimalFormat.format(rounded) + "K";
        } else if (value < -100000 && value > -10000000) {
            rounded = value / 100000;
            //decimalFormat.format(rounded);
            convertConcat = decimalFormat.format(rounded) + "L";
        } else if (value < -10000000) {
            rounded = value / 10000000;
            //decimalFormat.format(rounded);
            convertConcat = decimalFormat.format(rounded) + "Cr";
        }
        return convertConcat;
    }


    public static String networthabsuluterounded(Double value) {
        Double rounded = 0.0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        String convertConcat = "0.00";
        if (value > 0 && value < 100000) {
            if (value != 0) {
                rounded = value / 1000;
                // decimalFormat.format(rounded);
                convertConcat = decimalFormat.format(rounded) + "K";
            }
        } else if (value >= 100000 && value < 10000000) {
            rounded = value / 100000;
            //decimalFormat.format(rounded);
            convertConcat = decimalFormat.format(rounded) + "L";
        } else if (value >= 10000000) {
            rounded = value / 10000000;
            //decimalFormat.format(rounded);
            convertConcat = decimalFormat.format(rounded) + "Cr";
        } else if (value < 0 && value > -100000) {
            rounded = value / 1000;
            //decimalFormat.format(rounded);
            convertConcat = decimalFormat.format(rounded) + "K";
        } else if (value < -100000 && value > -10000000) {
            rounded = value / 100000;
            //decimalFormat.format(rounded);
            convertConcat = decimalFormat.format(rounded) + "L";
        } else if (value < -10000000) {
            rounded = value / 10000000;
            //decimalFormat.format(rounded);
            convertConcat = decimalFormat.format(rounded) + "Cr";
        }
        return convertConcat;
    }


//    public static void showAlertDialog(Context context, String message) {
//        alertRetrofitExceptionalert(message, context);
//    }

    public static void alertRetrofitExceptionDialog(Context context, Throwable t) {
        try {
            if (t instanceof SocketTimeoutException) {
                alertRetrofitExceptionalert("Network Time out. Please try again.", context);
            } else if (t instanceof ConnectException) {
                alertRetrofitExceptionalert("Server Time out. Please try again.", context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String roundofftheEpowervalue(String cost) {
        try {
            Double d = Double.valueOf(cost);
            System.out.println("exponential = " + d);

            String formatMask = "0.################################################";
            DecimalFormat df = new DecimalFormat(formatMask);

            System.out.println("normal number=" + df.format(d));
            return df.format(d);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return cost;
    }

    //Added by Murali
//    public static void alertDialogYesNo( String message, Context context) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setMessage(message);
//        builder.setPositiveButton(context.getString(R.string.dialog_yes),
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        UtileKit.dialogActive = true;
//                    }
//                });
//        builder.setNegativeButton(context.getString(R.string.dialog_no),
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        UtileKit.dialogActive = false;
//                    }
//                });
//        builder.setCancelable(true);
//        AlertDialog alert = builder.show();
//        TextView messageText = (TextView)alert.findViewById(android.R.id.message);
//        messageText.setGravity(Gravity.CENTER);
//
//    }

    public static void alertDialog(String title, String message, Context context) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle(title);
        dlgAlert.setPositiveButton(context.getString(R.string.dialog_ok), null);
        dlgAlert.setCancelable(true);
        AlertDialog alert = dlgAlert.show();
        TextView messageText = (TextView) alert.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
    }

    public static void alertDialogview(String title, View imgview,
                                       Context context) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
        dlgAlert.setTitle(title);
        dlgAlert.setView(imgview);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    public static void alertDialogCredentials(String title, String message,
                                              final Context context) {
        try {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
            dlgAlert.setMessage(message);
            dlgAlert.setTitle(title);
            dlgAlert.setPositiveButton(context.getString(R.string.dialog_ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            UtileKit.dialogActive = false;
                        }
                    });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param cancelable
     */
    public static void showSpinnerDialog(Context context, boolean cancelable) {

        try {
            UtileKit.context = context;
            message = null;
            mProgressHandler = new Handler();
            mProgressHandler.post(mShowCustomSpinnerDialog);
            UtileKit.cancelable = cancelable;
        } catch (Exception e) {
            dialog = null;
        }
    }

    public static void showToastShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    // srini
    public static void showSpinnerDialogForExplore(Context context, boolean cancelable) {

        try {
            UtileKit.context = context;
            exploremessage = null;
            mProgressExploreHandler = new Handler();
            mProgressExploreHandler.post(mShowCustomSpinnerDialogForExplore);
            UtileKit.explorecancelable = cancelable;
        } catch (Exception e) {
            exploredialog = null;
        }
    }

    public static void showSpinnerDialog(Context context, String msg,
                                         boolean cancelable) {
        try {
            UtileKit.context = context;
            message = msg;
            mProgressHandler = new Handler();
            mProgressHandler.post(mShowCustomSpinnerDialog);
            UtileKit.cancelable = cancelable;
        } catch (Exception e) {
            dialog = null;
        }
    }

    public static boolean isExploreDialogShown() {
        return exploredialog != null && exploredialog.isShowing();
    }


    public static boolean isDialogShown() {
        return dialog != null && dialog.isShowing();
    }


    public static void dismisssExploreSpinnerDialog() {
        try {
            if (exploredialog != null && exploredialog.isShowing()) {
                exploredialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exploredialog = null;
        }
    }


    /**
     *
     */
    public static void dismisssSpinnerDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dialog = null;
        }
    }

    public static void showFailedToast(Context cnt) {
        Toast.makeText(
                cnt,
                ""
                        + cnt.getString(R.string.http_communication_failed_try_again),
                Toast.LENGTH_LONG).show();
    }

    /**
     * function md5 encryption for passwords
     *
     * @param password
     * @return passwordEncrypted
     */
    public static String encryptPwd(final String password) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(password.getBytes());
            final byte[] bytes = digest.digest();

            final StringBuilder sb = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02X", bytes[i]));
            }

            return sb.toString().toLowerCase(Locale.UK);
        } catch (Exception exc) {
            exc.printStackTrace();
            return EMPTY_STR;
        }
    }

    public static void getSwitchYesBtnView(RadioButton yesView, RadioButton noView, Context context) {
        yesView.setBackgroundResource(R.drawable.switch_left);
        yesView.setTextColor(ContextCompat.getColor(context, R.color.white));
        noView.setBackgroundResource(R.drawable.switch_deselected_yes);
        noView.setTextColor(ContextCompat.getColor(context, R.color.DarkGray));

    }

    public static void getSwitchEmptyBtnView(RadioButton yesView, RadioButton noView, Context context) {
        yesView.setBackgroundResource(R.drawable.swithc_yes);
        noView.setBackgroundResource(R.drawable.switch_no);

    }

    public static void getSwitchNoBtnView(RadioButton yesView, RadioButton noView, Context context) {
        yesView.setBackgroundResource(R.drawable.switch_deselected_no);
        yesView.setTextColor(ContextCompat.getColor(context, R.color.DarkGray));
        noView.setBackgroundResource(R.drawable.switch_right);
        noView.setTextColor(ContextCompat.getColor(context, R.color.white));
    }

    public static void getSwitchYesBtnViewSmall(RadioButton yesView, RadioButton noView, Context context) {
        yesView.setBackgroundResource(R.drawable.ic_left_selected_small);
        yesView.setTextColor(ContextCompat.getColor(context, R.color.white));
        noView.setBackgroundResource(R.drawable.ic_empty_right_small);
        noView.setTextColor(ContextCompat.getColor(context, R.color.DarkGray));

    }

    public static void getSwitchNoBtnViewSmall(RadioButton yesView, RadioButton noView, Context context) {
        yesView.setBackgroundResource(R.drawable.ic_empty_left_small);
        yesView.setTextColor(ContextCompat.getColor(context, R.color.DarkGray));
        noView.setBackgroundResource(R.drawable.ic_right_selected_small);
        noView.setTextColor(ContextCompat.getColor(context, R.color.white));
    }


    public static void setStringArraySpinnerAdapter(Spinner mMyMartialSpinner, String[] myStringArray, Context mContext) {
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(
                mContext,
                android.R.layout.simple_spinner_dropdown_item, myStringArray);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMyMartialSpinner.setAdapter(adapter_state);
    }

    public static void setArrayListSpinnerAdapter(Spinner mMyMartialSpinner, ArrayList<String> myStringArray, Context mContext) {
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(
                mContext,
                android.R.layout.simple_spinner_dropdown_item, myStringArray);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMyMartialSpinner.setAdapter(adapter_state);
    }

    public static String[] goalRecurrenceMonths() {
        String[] recurrenceMonth = {"0", "3", "6", "9"};
        return recurrenceMonth;
    }

    public static void editTextOnFocus(EditText edtText) {
        edtText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.focus_border_style);
                } else {
                    view.setBackgroundResource(R.drawable.lost_focus_style);
                }
            }
        });
    }

//    public static String getStringremovefirstvariable(CurrencyDefaultEdt edt) {
//        try {
//            if (!StringUtils.isEmpty(edt))
//                return edt.replaceAll("₹", "").replaceAll(",", "").replaceAll("\\s+", "").trim().replaceAll("[^0-9.+-/*]", "");
//            else
//                return "";
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//            return "";
//        } catch (Exception e) {
//            return "";
//        }
//    }

    public static String getStringwithoutDefaultCurreny(CurrencyDefaultEdt edt) {
        try {
            String str = edt.getText().toString().replace("₹", "").trim().replaceAll("[^0-9.]", "");
            String str1 = str.replace("\u00A0", "").replace("\u00a0", "").replace(",", "").replaceAll("[^0-9.]", "");
            return str1;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static String getStringwithoutCurreny(CurrencyEditText edt) {
        try {
            String str = edt.getText().toString().replace("₹", "").trim();
            String str1 = str.replace("\u00A0", "").replace("\u00a0", "").replace(",", "").replaceAll("[^0-9.]", "");
            return str1;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static String getStringwithoutCurreny(String edt) {
        try {
            if (!StringUtils.isEmpty(edt))
                return edt.replaceAll("₹", "").replaceAll(",", "").replaceAll("\\s+", "").trim().replaceAll("[^0-9.+-/*]", "");
            else
                return "0";
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "0";
        } catch (Exception e) {
            return "0";
        }
    }

    public static Boolean checkEdtIsEmpty(CurrencyEditText edt) {
        try {
            String str = edt.getText().toString().replace("₹", "").trim();
            String str1 = str.replace("\u00A0", "").replace("\u00a0", "").replace("[^0-9.]", "");
            return str1.length() > 0 && !str.equalsIgnoreCase("0");
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static int unitCalculation(ArrayList<Float> unitCount) {
        try {
            int largestString = 0;
            int index = 0;
            Log.i("checktheCountValue", " unitCount array list " + unitCount.size());
            largestString = unitCount.get(0).toString().length();
            index = 0;
            int output = 100;

            for (int i = 0; i < unitCount.size(); i++) {
                if (unitCount.get(i).toString().length() > largestString) {
//                    largestString = Math.abs(unitCount.get(i).toString().length());
                    largestString = (int) Math.abs(unitCount.get(index));
                    index = i;
                }
            }
            Log.i("checktheCountValue", "Index " + index + " " + unitCount.get(index) + " " + "is the largest and is size " + largestString);

            Log.i("checktheCountValue", "Index " + "is the largest and is Math.round " + Math.round(Float.parseFloat(unitCount.get(index).toString())));
            if (largestString > 1000) {
                output = 1000;

            } else if (largestString > 10000) {
                output = 10000;

            } else if (largestString > 100000) {
                output = 100000;
            } else if (largestString > 1000000) {
                output = 1000000;
            } else if (largestString > 10000000) {
                output = 10000000;
            } else if (largestString > 100000000) {
                output = 100000000;
            } else if (largestString > 1000000000) {
                output = 1000000000;
            } else if (largestString > 1000000000) {
                output = 1000000000;
            } else if (largestString > 1000000000) {
                output = 1000000000;
            } else if (largestString > 1000000000) {
                output = 1000000000;
            } else if (largestString < 1000) {
                output = 100;

            } else if (largestString < 10000) {
                output = 1000;

            } else if (largestString < 100000) {
                output = 10000;

            } else if (largestString < 1000000) {
                output = 100000;

            } else if (largestString < 10000000) {
                output = 1000000;

            } else if (largestString < 100000000) {
                output = 10000000;

            } else if (largestString < 1000000000) {
                output = 100000000;

            }

            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 100;
    }

    public static boolean removeDefaultValue(String mystr) {
        return !(mystr.equalsIgnoreCase("0") || mystr.equalsIgnoreCase("0000-00-00"));
    }

    public static void setTextAppearance(Context context, int resId, CheckBox text) {

        if (Build.VERSION.SDK_INT < 23) {
            text.setTextAppearance(context, resId);
        } else {
            text.setTextAppearance(resId);
        }
    }

    public static void createSharedPreference(Context context, String userID) {
        purplepathPref = context.getSharedPreferences("useridPref", Context.MODE_PRIVATE);
        persistingPurplePathPref("user_id", userID);
    }

    public static void cretePrefAtHome(Context baseContext) {
        try {
            purplepathPref = baseContext.getSharedPreferences("useridPref", Context.MODE_PRIVATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cretefinobotPrefForFirsttime(Context baseContext) {
        try {
            finobotPrefForFirsttime = baseContext.getSharedPreferences("userPrefFirsttime", Context.MODE_PRIVATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void persistingPurplePathPrefFirsttime(String key, String valString) {
        finobotPrefForFirsttime.edit().putString(key, valString).commit();
    }

    public static String getPersistedfinobotPrefForFirsttime(String key) {
        if (finobotPrefForFirsttime != null && finobotPrefForFirsttime.contains(key)) {
            return finobotPrefForFirsttime.getString(key, null);
        } else {
            return null;
        }
    }

    //    public static void cretePrefAtHome(){
//        purplepathPref = context.getSharedPreferences("useridPref", Context.MODE_PRIVATE);
//    }
//    public static void removePersistedPurplePathPref(String key) {
//        purplepathPref.edit().remove(key).commit();
//    }
    public static void persistingPurplePathPref(String key, String valString) {
        purplepathPref.edit().putString(key, valString).commit();
    }

    public static void persistingPurplePathPref(String key, boolean valBool) {
        purplepathPref.edit().putBoolean(key, valBool).commit();
    }

    public static String getPersistedPurplePathPref(String key) {
        if (purplepathPref != null && purplepathPref.contains(key)) {
            return purplepathPref.getString(key, null);
        } else {
            return null;
        }
    }

    public static Boolean getPersistedPurplePathBoolPref(String key) {
        if (purplepathPref != null && purplepathPref.contains(key)) {
            return purplepathPref.getBoolean(key, false);
        } else {
            return false;
        }
    }

    public static String getPersistedPurplePathPref(String key, String _defaultValue) {
        return purplepathPref.getString(key, _defaultValue);
    }

    public static boolean isNetworkAvailable(Context context) {

        boolean networkAvailability = false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo[] = manager.getAllNetworkInfo();

        for (int j = 0; j < netInfo.length; j++) {
            networkAvailability = (networkAvailability || netInfo[j].isConnected());
        }

        return networkAvailability;
    }


//    public static boolean isshowNetworkAvailable(Context context) {
//
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        if (activeNetwork != null) {
//            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
//                //Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
//            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
//                //Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
//        }
//    }

    public static final int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    /**
     * Method to extract the user's age from the entered Date of Birth.
     *
     * @param year,month,day String The user's date of birth.
     * @return ageS int The user's age in years based on the supplied DoB.
     */
    public static int getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
      /*  Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();
*/
        return age;
    }

    public static int getDurationAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = dob.get(Calendar.YEAR) - today.get(Calendar.YEAR);

        if (dob.get(Calendar.DAY_OF_YEAR) < today.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
      /*  Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();
*/
        return age;
    }

    public static void pickerviewpastYear(final NumberPicker mDayPicker, final NumberPicker mMonthPicker, final NumberPicker mYearPicker, String gotServiceText) {
        mMonthPicker.setMinValue(0);
        mMonthPicker.setMaxValue(MONTHS_IN_ENGLISH.length - 1);
        mMonthPicker.setDisplayedValues(MONTHS_IN_ENGLISH);
        mMonthPicker.setWrapSelectorWheel(true);
        mMonthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        Log.i(TAG, " pickerviewpastYear utile gotServiceText from services  " + " " + gotServiceText);
        yearGenerator();
        String[] yearString = Arrays.toString(Year1).split("[\\[\\]]")[1].split(", ");
        Log.i(TAG, yearString[0] + "=yearString[0] " + yearString[116] + "=yearString[116]");
        Log.i(TAG, "Current Year " + yearString.length);

        mYearPicker.setMinValue(0);
        try {
            mYearPicker.setMaxValue(yearString.length - 1);
//            mYearPicker.setMaxValue(Integer.parseInt(year) - 1900);

        } catch (Exception e) {
            e.printStackTrace();
        }
        mYearPicker.setDisplayedValues(yearString);
        mYearPicker.setWrapSelectorWheel(true);
        mYearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        mDayPicker.setMinValue(0);

        mDayPicker.setMaxValue(maxDaysInMonth(mYearPicker.getValue() + 1900, mMonthPicker.getValue()) - 1);
        mDayPicker.setDisplayedValues(DATES);
        mDayPicker.setWrapSelectorWheel(true);
        mDayPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        try {
            if (!gotServiceText.equalsIgnoreCase("null")) {
                if (!gotServiceText.equals(" ")) {
                    Log.i(TAG, " pickerviewpastYear utile gotServiceText from services  " + " " + gotServiceText);

                    String[] items = gotServiceText.split("-");
//                    Log.i(TAG, " gotServiceText from services  items 0" + items[0]);
//                    Log.i(TAG, " gotServiceText from services  items 1" + items[1]);
//                    Log.i(TAG, " gotServiceText from services  items 2" + items[2]);

                    int dd = Integer.parseInt(items[0]);
                    int mm = Integer.parseInt(items[1]);
                    int yy = Integer.parseInt(items[2]);
                    Log.i(TAG, "pickerviewpastYear utile gotServiceText updateyear yy " + yy);
                    mDayPicker.setValue(dd - 1);
                    mMonthPicker.setValue(mm - 1);
                    mYearPicker.setValue(yy - 1900);

                } else {
                    setCurrentDate(mDayPicker, mMonthPicker, mYearPicker);
                }
            }

        } catch (Exception e) {
            setCurrentDate(mDayPicker, mMonthPicker, mYearPicker);
            Log.i(TAG, " pickerviewpastYear utile gotServiceText from services  " + " " + gotServiceText);
            e.printStackTrace();
        }


        View edtView = mMonthPicker.getFocusedChild();

        if (null != edtView && edtView instanceof EditText) {
            edtView.setEnabled(false);
        }

        mMonthPicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        mMonthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                try {
                    Log.v(TAG, "oldVal: " + oldVal + "newVal: " + newVal);
                    int maxdays = maxDaysInMonth(mYearPicker.getValue() + 1900, newVal);
                    mDayPicker.setMaxValue(maxdays - 1);

                    if ((mYearPicker.getValue() + 1900) == Integer.parseInt(year)) {
                        mMonthPicker.setMaxValue(Integer.parseInt(month) - 1);
                        if (newVal == Integer.parseInt(month) - 1) {
                            mDayPicker.setMaxValue(Integer.parseInt(day) - 1);
                        }
                    }
                    monthToSave = " ";
                    monthToSave = getMonthString(newVal);
                    Log.i(TAG, "Current monthToSave " + monthToSave);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        mYearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                try {
                    if (year == null) {
                        year = "0";
                        if ((newVal + 1900) == Integer.parseInt(year)) {
                            mMonthPicker.setMaxValue(Integer.parseInt(month) - 1);
                            if (mMonthPicker.getValue() == Integer.parseInt(month) - 1) {
                                try {
                                    mDayPicker.setMaxValue(Integer.parseInt(day));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            mMonthPicker.setMaxValue(MONTHS_IN_ENGLISH.length - 1);

                        }
                    } else {
                        if ((newVal + 1900) == Integer.parseInt(year)) {
                            mMonthPicker.setMaxValue(Integer.parseInt(month) - 1);
                            if (mMonthPicker.getValue() == Integer.parseInt(month) - 1) {
                                try {
                                    mDayPicker.setMaxValue(Integer.parseInt(day));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            mMonthPicker.setMaxValue(MONTHS_IN_ENGLISH.length - 1);

                        }
                    }
                    if (mMonthPicker.getValue() == 1) {
                        int maxdays = maxDaysInMonth(newVal + 1900, 1);
                        mDayPicker.setMaxValue(maxdays - 1);
                        if ((newVal + 1900) == Integer.parseInt(year)) {
                            mDayPicker.setMaxValue(Integer.parseInt(day));
                        }
                    }
                    dobyearToSave = " ";
                    dobyearToSave = newVal + 1900 + "";
                    Log.i(TAG, "Current yearToSave " + dobyearToSave);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mDayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                try {
                    mDayPicker.setMaxValue(maxDaysInMonth(mYearPicker.getValue() + 1900, mMonthPicker.getValue()) - 1);
                    if ((mYearPicker.getValue() + 1900) == Integer.parseInt(year)) {
                        mMonthPicker.setMaxValue(Integer.parseInt(month) - 1);
                        if (mMonthPicker.getValue() == (Integer.parseInt(month) - 1)) {
                            mDayPicker.setMaxValue(Integer.parseInt(day) - 1);
                        }
                    }
                    dayToSave = " ";
                    dayToSave = newVal + 1 + "";
                    Log.i(TAG, "Current dayToSave " + dayToSave);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void yearGenerator() {
        try {
            Year1 = new int[118];
            for (int i = 0; i < 118; i++) {
                Year1[i] = 1900 + i;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getMonthIndex(String month) {
        Log.i("DatePickDialog", "getMonthIndex month " + month);
        int index = 11, length = MONTHS_IN_ENGLISH.length;
        for (int i = 0; i < length; i++) {
            if (month.equalsIgnoreCase(MONTHS_IN_ENGLISH[i]))
                index = i;
        }


        return index;
    }

    public static String getMonthString(int i) {

        return MONTHS_IN_ENGLISH[i];
    }

    public static int maxDaysInMonth(int year, int month) {

        Calendar mycal = new GregorianCalendar(year, month, 1);
        return mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

    }

    public static void pickerviewFuture(final NumberPicker mDayPicker, final NumberPicker mMonthPicker, final NumberPicker mYearPicker, String gotServiceText) {
        mMonthPicker.setMinValue(0);
        mMonthPicker.setMaxValue(MONTHS_IN_ENGLISH.length - 1);
        mMonthPicker.setDisplayedValues(MONTHS_IN_ENGLISH);
        mMonthPicker.setWrapSelectorWheel(true);
        mMonthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


        yearGeneratorFuture();
        String[] yearString = Arrays.toString(Year1).split("[\\[\\]]")[1].split(", ");
        Log.i(TAG, yearString[0] + "=yearString[0] " + yearString[116] + "=yearString[116]");
        Log.i(TAG, "Current Year " + yearString.length);

        mYearPicker.setMinValue(0);
        try {
//            mYearPicker.setMaxValue(yearString.length - 1);
            mYearPicker.setMaxValue(Integer.parseInt(year) - 1900);

        } catch (Exception e) {
            e.printStackTrace();
        }
        mYearPicker.setDisplayedValues(yearString);
        mYearPicker.setWrapSelectorWheel(true);
        mYearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


        mDayPicker.setMinValue(0);

        mDayPicker.setMaxValue(maxDaysInMonth(mYearPicker.getValue() + 1900, mMonthPicker.getValue()) - 1);
        mDayPicker.setDisplayedValues(DATES);
        mDayPicker.setWrapSelectorWheel(true);
        mDayPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


        try {
            if (!gotServiceText.equalsIgnoreCase("null")) {
                if (!gotServiceText.equals(" ")) {
                    Log.i(TAG, " gotServiceText from services  " + CurrentBelowTextToBeUpdate + " " + gotServiceText);

                    String[] items = gotServiceText.split("-");
                    Log.i(TAG, " gotServiceText from services  items 0" + items[0]);
                    Log.i(TAG, " gotServiceText from services  items 1" + items[1]);
                    Log.i(TAG, " gotServiceText from services  items 2" + items[2]);

                    int dd = Integer.parseInt(items[0]);
                    int mm = Integer.parseInt(items[1]);
                    int yy = Integer.parseInt(items[2]);
                    Log.i(TAG, " gotServiceText updateyear yy " + yy);
                    mDayPicker.setValue(dd - 1);
                    mMonthPicker.setValue(mm - 1);
                    mYearPicker.setValue(yy - 27);

                } else {
                    setCurrentDatefuture(mDayPicker, mMonthPicker, mYearPicker);
                }
            }
        } catch (Exception e) {
            setCurrentDatefuture(mDayPicker, mMonthPicker, mYearPicker);
            e.printStackTrace();
        }


        View edtView = mMonthPicker.getFocusedChild();

        if (null != edtView && edtView instanceof EditText) {
            edtView.setEnabled(false);
        }

        mMonthPicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        mMonthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                try {
                    Log.v(TAG, "oldVal: " + oldVal + "newVal: " + newVal);
                    int maxdays = maxDaysInMonth(mYearPicker.getValue() + 1900, newVal);
                    mDayPicker.setMaxValue(maxdays - 1);

                    if ((mYearPicker.getValue() + 1900) == Integer.parseInt(year)) {
                        mMonthPicker.setMaxValue(Integer.parseInt(month) - 1);
                        if (newVal == Integer.parseInt(month) - 1) {
                            mDayPicker.setMaxValue(Integer.parseInt(day) - 1);
                        }
                    }
                    monthToSave = " ";
                    monthToSave = getMonthString(newVal);
                    Log.i(TAG, "Current monthToSave " + monthToSave);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mYearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                try {
                    yearToSave = newVal + 2017 + "";
                    Log.i(TAG, "setOnValueChangedListener newVal " + newVal);
                    Log.i(TAG, "setOnValueChangedListener newValyearToSave " + yearToSave);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
//        mYearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                if ((newVal + 1900) == Integer.parseInt(year)) {
//                    mMonthPicker.setMaxValue(Integer.parseInt(month) - 1);
//                    if (mMonthPicker.getValue() == Integer.parseInt(month) - 1) {
//                        try {
//                            mDayPicker.setMaxValue(Integer.parseInt(day));
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                } else {
//                    mMonthPicker.setMaxValue(MONTHS_IN_TAMIL.length - 1);
//
//                }
//                if (mMonthPicker.getValue() == 1) {
//                    int maxdays = maxDaysInMonth(newVal + 1900, 1);
//                    mDayPicker.setMaxValue(maxdays - 1);
//                    if ((newVal + 1900) == Integer.parseInt(year)) {
//                        mDayPicker.setMaxValue(Integer.parseInt(day));
//                    }
//                }
//                dobyearToSave = " ";
//                dobyearToSave = newVal + 1900 + "";
//                Log.i(TAG, "Current yearToSave " + dobyearToSave);
//            }
//        });

        mDayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                try {

                    mDayPicker.setMaxValue(maxDaysInMonth(mYearPicker.getValue() + 1900, mMonthPicker.getValue()) - 1);
                    if ((mYearPicker.getValue() + 1900) == Integer.parseInt(year)) {
                        mMonthPicker.setMaxValue(Integer.parseInt(month) - 1);
                        if (mMonthPicker.getValue() == (Integer.parseInt(month) - 1)) {
                            mDayPicker.setMaxValue(Integer.parseInt(day) - 1);
                        }
                    }
                    dayToSave = " ";
                    dayToSave = newVal + 1 + "";
                    Log.i(TAG, "Current dayToSave " + dayToSave);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void setCurrentDatefuture(final NumberPicker mDayPicker, final NumberPicker mMonthPicker, final NumberPicker mYearPicker) {
        mYearPicker.setValue(Integer.parseInt(year) - 27);
        mMonthPicker.setValue(Integer.parseInt(month) - 1);
        mDayPicker.setValue(Integer.parseInt(day) - 1);
    }

    public static void yearGeneratorFuture() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            Date date = new Date();
            Log.i("yearGeneratorFuture", "yearGeneratorFuture date" + dateFormat.format(date));
            String[] items1 = dateFormat.format(date).split("/");
            year = items1[0];
            month = items1[1];
            day = items1[2];
            Log.i("yearGeneratorFuture", "yearGeneratorFuture year" + year);
            int currentYear = Integer.parseInt(year);
            Year1 = new int[118];
            for (int i = 0; i < 118; i++) {
                Year1[i] = i + currentYear;
                Log.i("yearGeneratorFuture", "yearGeneratorFuture Year1[i]" + Year1[i]);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean checkEmptyDate(String getMarriageDate) {
        return (!getMarriageDate.equalsIgnoreCase("0000-00-00"));

    }

    public static void setCurrentDate(final NumberPicker mDayPicker, final NumberPicker mMonthPicker, final NumberPicker mYearPicker) {
        try {
            mYearPicker.setValue(Integer.parseInt(year) - 1900);
            mMonthPicker.setValue(Integer.parseInt(month) - 1);
            mDayPicker.setValue(Integer.parseInt(day) - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayOnlyYearCalenderFuture(NumberPicker mDayPicker, NumberPicker mMonthPicker,
                                                     NumberPicker mYearPicker, ImageView uparrow1, ImageView uparrow2,
                                                     ImageView downarrow1, ImageView downarrow2, LinearLayout yearPickerlayout,
                                                     TextView textview_Display_year_Age, String gotServiceText, String serviceText) {
        textview_Display_year_Age.setText(serviceText);

        try {
            if (!gotServiceText.equalsIgnoreCase("null")) {


                Log.i(TAG, " serviceText  : " + serviceText + " gotServiceText : " + gotServiceText);
                textview_Display_year_Age.setText(serviceText + " " + gotServiceText);
                int year = Calendar.getInstance().get(Calendar.YEAR);
                int serviceYearvalue = Integer.parseInt(gotServiceText);
                int updateyear = year - serviceYearvalue;
                Log.i(TAG, "updateyear " + updateyear);

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mDayPicker.setVisibility(View.GONE);
        mMonthPicker.setVisibility(View.GONE);
        uparrow1.setVisibility(View.GONE);
        uparrow2.setVisibility(View.GONE);
        downarrow1.setVisibility(View.GONE);
        downarrow2.setVisibility(View.GONE);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 3.0f;
        params.gravity = Gravity.CENTER;
        yearPickerlayout.setLayoutParams(params);

        yearGeneratorFuture();
        String[] yearString = Arrays.toString(Year1).split("[\\[\\]]")[1].split(", ");
        Log.i(TAG, yearString[0] + "=yearString[0] " + yearString[116] + "=yearString[116]");
        mYearPicker.setMinValue(0);
        try {
            //mYearPicker.setMaxValue(yearString.length - 1);
            mYearPicker.setMaxValue(Integer.parseInt(year) - 1900);

        } catch (Exception e) {
            e.printStackTrace();
        }
        mYearPicker.setDisplayedValues(yearString);
        mYearPicker.setWrapSelectorWheel(true);
        mYearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        try {
            if (!gotServiceText.equalsIgnoreCase("null")) {
                Log.i(TAG, " gotServiceText from services  " + CurrentBelowTextToBeUpdate + " " + gotServiceText);

                String yearValue = String.valueOf(Utils.getAgeDataSubFromCurrentYear(Integer.parseInt(gotServiceText)));
//                   Log.i(TAG, " gotServiceText CurrentOrganization year " +yearValue );
                textview_Display_year_Age.setText(serviceText + " " + gotServiceText);

                int yy = Integer.parseInt(gotServiceText);
                Log.i(TAG, " gotServiceText updateyear yy " + yy);

                mYearPicker.setValue(yy);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mYearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                try {

                    yearToSave = newVal + 2017 + "";
                    Log.i(TAG, "setOnValueChangedListener newVal " + newVal);
                    Log.i(TAG, "setOnValueChangedListener newValyearToSave " + yearToSave);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static String currencyCunvertion(BigInteger obj) {
        try {
            Locale indianlocal = new Locale("en", "IN");
            NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance(indianlocal);

            usdCostFormat.setMaximumFractionDigits(0);
//            String replaceable = String.format("[%s,.\\s]", NumberFormat.getInstance().getCurrency().getSymbol());
            return getStringwithoutCurreny((usdCostFormat.format(obj.doubleValue())));
        } catch (Exception e) {
            return "";
        }


    }

    public static void intitializeAlertDialog(String string, Context mContext) {
        LayoutInflater inflater;
        View dialogView;
        final AlertDialog alertDialogs;
        try {
            inflater = LayoutInflater.from(mContext);
            dialogView = inflater.inflate(R.layout.alert_message_layout, null);
            alertDialogs = new AlertDialog.Builder(mContext).create();
            alertDialogs.setView(dialogView);
            TextView erroreMessage = dialogView.findViewById(R.id.textViewDilog);
            TextView title = dialogView.findViewById(R.id.textViewAlert);
            Log.i(TAG, "intitializeAlertDialog " + string);
            erroreMessage.setText(string);
            dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogs.dismiss();

//                onBackPressed();
//                finish();
                }
            });
//            dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    alertDialog.dismiss();
//                }
//            });
            alertDialogs.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void intitializeAlertDialogQuiz(String Title, String string, Context mContext) {
        LayoutInflater inflater;
        View dialogView;
        final AlertDialog alertDialogs;
        try {
            inflater = LayoutInflater.from(mContext);
            dialogView = inflater.inflate(R.layout.alert_message_layout, null);
            alertDialogs = new AlertDialog.Builder(mContext).create();
            alertDialogs.setView(dialogView);
            TextView erroreMessage = dialogView.findViewById(R.id.textViewDilog);
            TextView title = dialogView.findViewById(R.id.textViewAlert);
            Log.i(TAG, "intitializeAlertDialog " + string);
            erroreMessage.setText(string);
            dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogs.dismiss();

//                onBackPressed();
//                finish();
                }
            });
//            dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    alertDialog.dismiss();
//                }
//            });
            alertDialogs.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String currencyCunvertions(String data) {
        try {
            Log.i("loanAmount", "loanAmount obj" + data);
            data = data.trim();
            Locale indianlocal = new Locale("en", "IN");
            NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance(indianlocal);

            usdCostFormat.setMaximumFractionDigits(0);

//            Log.i("loanAmount","loanAmount usdCostFormat.format(obj)"+ usdCostFormat.format(obj));
//            String replaceable = String.format("[%s,.\\s]", NumberFormat.getInstance().getCurrency().getSymbol());
            return getStringwithoutCurreny((usdCostFormat.format(Double.valueOf(data.replaceAll(" ", "")))));
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("loanAmount", "loanAmount error" + e.getMessage());
            return "";
        }


    }


    public static String validateNegativeWithZero(String mstring) {

        if (mstring.compareTo(String.valueOf(BigDecimal.ZERO)) > 0) {
            return (mstring);
        } else {
            return ("0");
        }

    }

    public static void setDataForCheckbox(String[] xDataCheckbox, ArrayList colour, LinearLayout parentView, Context mContext) {
        try {
            Log.i("InsuranceAnalysis", " InsuranceAnalysis setDataForCheckbox is " + xDataCheckbox.length);

            for (int i = 0; i < xDataCheckbox.length; i++) {

                LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout parent_layout = new LinearLayout(mContext);
                parent_layout.setWeightSum(2);
                parent_layout.setOrientation(LinearLayout.HORIZONTAL);
                parent_param_layout.setMargins(10, 0, 0, 10);
                parent_layout.setLayoutParams(parent_param_layout);

                LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parms_left_layout.weight = 1F;
                LinearLayout left_layout = new LinearLayout(mContext);
                left_layout.setOrientation(LinearLayout.HORIZONTAL);
                left_layout.setGravity(Gravity.LEFT);
                left_layout.setLayoutParams(parms_left_layout);

                LinearLayout.LayoutParams parms_legen_layout = new LinearLayout.LayoutParams(45, 45);
                parms_legen_layout.setMargins(0, 0, 20, 0);
                LinearLayout legend_layout = new LinearLayout(mContext);
                legend_layout.setLayoutParams(parms_legen_layout);
                legend_layout.setOrientation(LinearLayout.HORIZONTAL);
                legend_layout.setBackgroundColor((Integer) colour.get(i));
                left_layout.addView(legend_layout);

                TextView txt_unit = new TextView(mContext);
                txt_unit.setText(xDataCheckbox[i]);
                left_layout.addView(txt_unit);
//                i++;
//                if ( (xDataCheckbox.length) == i) {
//                    parent_layout.addView(left_layout);
//                    parentView.addView(parent_layout);
//                    break;
//                }
//
//                LinearLayout.LayoutParams parms_right_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                parms_right_layout.weight = 1F;
//                LinearLayout right_layout = new LinearLayout(mContext);
//                right_layout.setOrientation(LinearLayout.HORIZONTAL);
//                right_layout.setGravity(Gravity.LEFT);
//                parent_param_layout.setMargins(10, 0, 0, 10);
//                right_layout.setLayoutParams(parms_right_layout);
//
//
//                LinearLayout.LayoutParams parms_rightlegend_layout = new LinearLayout.LayoutParams(45, 45);
//                parms_rightlegend_layout.setMargins(0, 0, 20, 0);
//                LinearLayout right_legend_layout = new LinearLayout(mContext);
//                right_legend_layout.setLayoutParams(parms_rightlegend_layout);
//                right_legend_layout.setOrientation(LinearLayout.HORIZONTAL);
//                right_legend_layout.setBackgroundColor(colour[i]);
//                right_layout.addView(right_legend_layout);
//
//                TextView right_txt_unit = new TextView(mContext);
//                right_txt_unit.setText( xDataCheckbox[i]);
//                right_layout.addView(right_txt_unit);
//
//                parent_layout.addView(right_layout);
                parent_layout.addView(left_layout);
                parentView.addView(parent_layout);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // writen by suresh
    public static String getIndianCurrencyFormat(String amount) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            char amountArray[] = amount.toCharArray();
            int a = 0, b = 0;
            for (int i = amountArray.length - 1; i >= 0; i--) {
                if (a < 3) {
                    stringBuilder.append(amountArray[i]);
                    a++;
                } else if (b < 2) {
                    if (b == 0) {
                        stringBuilder.append(",");
                        stringBuilder.append(amountArray[i]);
                        b++;
                    } else {
                        stringBuilder.append(amountArray[i]);
                        b = 0;
                    }
                }
            }
            return stringBuilder.reverse().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amount;
    }

    //FloatingButton
    public static void setSvgButtonDrawableFloatingButton(com.github.clans.fab.FloatingActionButton button, Context mContext, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            button.setImageResource(id);
        } else {
            button.setImageDrawable(vectorToBitmapDrawable(mContext, id));
        }
    }

    // Button
    public static void setSvgButtonDrawableLeft(Button button, Context mContext, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            button.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0);
        } else {
            button.setCompoundDrawablesWithIntrinsicBounds(vectorToBitmapDrawable(mContext, id), null, null, null);
        }
    }

    public static void setSvgImageviewDrawable(ImageView imageview, Context mContext, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageview.setImageResource(id);
        } else {
            imageview.setImageDrawable(vectorToBitmapDrawable(mContext, id));
        }
    }

    //Imageview


    public static void setTintImage(String key, ImageView image_obj, Context context, int tint_image, int without_tint_image) {

        if (UtileKit.getPersistedPurplePathBoolPref(key)) {
            UtileKit.setSvgImageviewDrawable(image_obj, context, without_tint_image);
            DrawableCompat.setTint(image_obj.getDrawable(), ContextCompat.getColor(getContext(), R.color.transparent_black));
        } else {
            UtileKit.setSvgImageviewDrawable(image_obj, context, tint_image);
        }

    }


    // TextView
    public static void setSvgEdittextDrawableLeft(TextView editText, Context mContext, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0);
        } else {
            editText.setCompoundDrawablesWithIntrinsicBounds(vectorToBitmapDrawable(mContext, id), null, null, null);
        }
    }

    // TextView
    public static void setSvgEdittextDrawableRight(TextView editText, Context mContext, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, id, 0);
        } else {
            editText.setCompoundDrawablesWithIntrinsicBounds(null, null, vectorToBitmapDrawable(mContext, id), null);
        }
    }

    public static void setSvgEdittextDrawableRight(EditText editText, Context mContext, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, id, 0);
        } else {
            editText.setCompoundDrawablesWithIntrinsicBounds(null, null, vectorToBitmapDrawable(mContext, id), null);
        }
    }

    // Customtextview
    public static void setSvgCustomTextviewDrawableLeft(CustomTextView customTextView, Context mContext, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            customTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, id, 0);
        } else {
            customTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, vectorToBitmapDrawable(mContext, id), null);
        }
    }


    //Muruga changes
    public static BitmapDrawable vectorToBitmapDrawable(Context mContext, @DrawableRes int resVector) {
        return new BitmapDrawable(mContext.getResources(), vectorToBitmap(mContext, resVector));
    }

    public static Bitmap vectorToBitmap(Context mContext, @DrawableRes int resVector) {
        Drawable drawable = AppCompatDrawableManager.get().getDrawable(mContext, resVector);
        Bitmap b = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        drawable.setBounds(0, 0, c.getWidth(), c.getHeight());
        drawable.draw(c);
        return b;
    }

    //edittextbordercolorchange for calender edittext only
    public static void edittextbordercolorchange(CalendarEditText mcalenderEdittext) {
        if (mcalenderEdittext.getText().length() != 0) {
            mcalenderEdittext.setBackgroundResource(R.drawable.edittextbackgrounggreen);
        } else {
            mcalenderEdittext.setBackgroundResource(R.drawable.edittextbackgroundgray);
        }
    }

    // normaledittextbordercolorchange for calender edittext only
    public static void normaledittextbordercolorchange(EditText mEdittext) {
        if (mEdittext.getText().length() != 0) {
            mEdittext.setBackgroundResource(R.drawable.edittextbackgrounggreen);
        } else {
            mEdittext.setBackgroundResource(R.drawable.edittextbackgroundgray);
        }
    }

    public static void emptyErrorViewList(View view) {

        if (view instanceof CurrencyDefaultEdt) {
            ((CurrencyDefaultEdt) view).setHintTextRequestFillError();
        } else if (view instanceof CharacterEditText) {

            ((CharacterEditText) view).setHintTextRequestFillError();
        } else if (view instanceof Spinner) {
            view.setBackgroundResource(R.drawable.spinner_background_yellow_arrow);
        } else if (view instanceof NumberEditText) {
            ((NumberEditText) view).setHintTextRequestFillError();
        } else if (view instanceof CurrencyTextView) {
//            ((CurrencyTextView)view).setHintTextRequestFillError();
        } else if (view instanceof PercentageEditText) {
            ((PercentageEditText) view).setHintTextRequestFillError();
        } else if (view instanceof CalendarEditText) {

            ((CalendarEditText) view).setHintTextRequestFillError();
        }
    }


    public static void mandatoryFieldLinearLayout(Boolean trues, LinearLayout relativeLayout) {
        if (trues == true) {
            relativeLayout.setVisibility(View.GONE);
        }
    }

    public static void mandatoryFieldDoneLayout(Boolean trues, LinearLayout linearLayout) {
        if (trues == true) {
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    public static String displayonlyears(String year) {
        Date date = null;
        String split_year = null;
        try {
            date = new SimpleDateFormat("dd-MM-yyyy").parse(String.valueOf(year));

            String dateString2 = new SimpleDateFormat("yyyy-MM-dd").format(date);
            Log.i("", "dateString2 " + dateString2);
            String[] splitdate = dateString2.split("\\-");
            split_year = splitdate[0];
            String month = splitdate[1];
            String days = splitdate[2];
            Log.i("", "year" + UtileKit.year + "month " + month + "days " + days);
            return split_year;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return split_year;
    }

    //Snack bar
    public static void showSnackBar(Context context, View view, String text) {
        Snackbar sb = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        sb.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        sb.show();
    }

    public static String displayYearCalulation(String monthtosetinEdittext) {
        Log.i(TAG, "displayYearCalulation parameter year" + monthtosetinEdittext);
        String yearValue = "";
        try {
            int selectedYear = Integer.parseInt(monthtosetinEdittext);

            GregorianCalendar calendar = new GregorianCalendar();
            final int currentYear = calendar.get(Calendar.YEAR);
            if (selectedYear < currentYear) {
                yearValue = String.valueOf(Utils.getAgeDataSubFromCurrentYear(selectedYear));
            } else {
                yearValue = String.valueOf(Utils.getAgeDataSubFromCurrentYearisGreater(selectedYear));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return yearValue;
    }

    public static String validation_to_years(String years) {
        String conversionyears = null;
        if (years.length() <= 3) {
            conversionyears = displayYearCalulation(years);

        } else {
            conversionyears = years;
        }
        return conversionyears;

    }

    public static boolean validate_character(String val) {
        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(val);
        boolean bs = ms.matches();
        return bs;

    }

    public static void saveScreenImageText(Context mContext, String text) {
        try {
            String noTag = text.replaceAll("<[^>]*>", "");
//            File path = Environment.getExternalStoragePublicDirectory(
//                    Environment.DIRECTORY_PICTURES);
//            File file = new File(path, "Shareimage" + System.currentTimeMillis()
//                    + ".jpeg");
//
//            if (!path.isDirectory())
//                path.mkdir();
//
//            FileOutputStream fOut = new FileOutputStream(file);
////            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
//            fOut.flush();
//            fOut.close();
//            file.setReadable(true, false);
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("vnd.android-dir/mms-sms");
            intent.putExtra("sms_body", noTag);
//            intent.putExtra(Intent.EXTRA_TEXT, noTag);
//            intent.setType("image/png");
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Check Version
    public static void saveandShareTextandImage(Context mContext, String text) {
        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission(mContext)) {
                requestForSpecificPermission(mContext);
            } else {
                saveScreenImageText(mContext, text);
            }
        } else {
            saveScreenImageText(mContext, text);

        }
    }

    //Permission Granted
    public static boolean checkIfAlreadyhavePermission(Context mContext) {
        int result = ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    //Declare in fragment or Activity
    public static void requestForSpecificPermission(Context mContext) {
        ActivityCompat.requestPermissions((Activity) mContext, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
    }

    public ArrayList<String> getAssetObjective() {
        ArrayList<String> objective = new ArrayList<String>();
        objective.add("Investments");
        objective.add("Cash");
        objective.add("Savings");
        objective.add("Lifestyle");
        return objective;
    }

    /**
     * @param mActivity Facebook sdk KeyHash for developer dash bord
     * @param mActivity
     * @auther dinesh
     */
    public static void createKeyHash(Activity mActivity) {
        try {
            PackageInfo info = mActivity.getPackageManager().getPackageInfo(
                    "com.finobot.finobot",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    /**
     * Generate a value suitable for use in .
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    /**
     * dinesh
     *
     * @param text
     * @return
     */
    public static String currencyConvert(String text) {
        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            DecimalFormat decimalFormat = new DecimalFormat("#,##,###", symbols);
            //  textWithComma =("₹ ").concat( decimalFormat.format(Integer.parseInt(text.toString())));


            return ("₹ ").concat(decimalFormat.format(new BigDecimal(text.toString().replace("Rs.", "").replace("Rs .", "").replaceAll("[^0-9 .]", ""))));
        } catch (Exception e) {
            return ("₹ 0");
        }
    }

    public static String get_financial_year() {
        String financial = "-:-";
        String fin_year_1 = "";
        String fin_year_2 = "";
        int year = Calendar.getInstance().get(Calendar.YEAR);

        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        System.out.println("Financial month : " + month);
        if (month < 4) {
            System.out.println("Financial Year : " + (year - 1) + "-" + year);

            fin_year_1 = ((year - 1)) + "";

            fin_year_2 = (year) + "";
            financial = (fin_year_1 + "-" + fin_year_2.substring(fin_year_2.length() - 2));
        } else {
            System.out.println("Financial Year : " + year + "-" + (year + 1));

            fin_year_1 = (year) + "";

            fin_year_2 = (year + 1) + "";
            financial = (fin_year_1 + "-" + fin_year_2.substring(fin_year_2.length() - 2));

        }

        return financial;
    }

    public static String get_tax_filing_year() {
        String financial = "-:-";
        String fin_year_1 = "";
        String fin_year_2 = "";
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        System.out.println("Financial month : " + month);
        if (month < 4) {
            fin_year_1 = (year - 2) + "";
            fin_year_2 = (year - 1) + "";
            financial = (fin_year_1 + "-" + fin_year_2.substring(fin_year_2.length() - 2));
            System.out.println("Financial Year : " + financial);
        } else {
            fin_year_1 = (year - 1) + "";
            fin_year_2 = (year) + "";
            financial = (fin_year_1) + "-" + (fin_year_2.substring(fin_year_2.length() - 2));
            System.out.println("Financial Year : " + financial);
        }
        return financial;
    }

    public static String get_start_date(ArrayList<Integer> list) {
        String val = "";
        try {
            Collections.sort(list, Collections.reverseOrder());
            System.out.println("s date:" + list);

            if (list.size() > 0) {
                val = list.get(list.size() - 1).toString();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return val;
    }

    public static String get_end_date(ArrayList<Integer> list) {
        String val = "";

        try {
            Collections.sort(list, Collections.reverseOrder());
            System.out.println("e date:" + list);
            if (list.size() > 0) {
                val = list.get(0).toString();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return val;
    }

}
