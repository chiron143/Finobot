package com.purplepath.purplepath.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.finobot.finobot.R;

import org.json.JSONException;

/**
 * Created by Bert on 08-Jun-16.
 */


public class ExpectedIncrementDialogFragment extends DialogFragment {
    private OnExpectedValueListener mCallBack;
    private EditText expectedAge, expectedPercentage, expectedNotes;
    private Button cancelbutton, savebutton;
    private String ageStr, percentageStr, notes;
    private int mId;
    private View mView;
    int heights = 0;
    int widths = 0;
    private LinearLayout mainLinearLayout;
    private String mExpIncrementValue;

    public static ExpectedIncrementDialogFragment newInstance(OnExpectedValueListener onDateSetListener, View id, String expIncrementValue) {
        ExpectedIncrementDialogFragment expectedIncrementDialogFragment = new ExpectedIncrementDialogFragment();
        expectedIncrementDialogFragment.initialize(onDateSetListener, id,expIncrementValue);
        return expectedIncrementDialogFragment;
    }
    public static ExpectedIncrementDialogFragment newInstance(OnExpectedValueListener onDateSetListener, int id) {
        ExpectedIncrementDialogFragment expectedIncrementDialogFragment = new ExpectedIncrementDialogFragment();
        expectedIncrementDialogFragment.initialize(onDateSetListener, id);
        return expectedIncrementDialogFragment;
    }

    public void initialize(OnExpectedValueListener ondatelistener,View  id,String expIncrementValue){
        mView = id;
        mCallBack = ondatelistener;
        mId=Integer.parseInt(id.getTag().toString());
        mExpIncrementValue=expIncrementValue;
    }
    public void initialize(OnExpectedValueListener ondatelistener,int  id){
        mId = id;
        mCallBack = ondatelistener;

    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    //    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        DisplayMetrics metrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        height = metrics.heightPixels-(int) Math.abs(metrics.heightPixels*.60);
//        width = metrics.widthPixels-(int) Math.abs(metrics.widthPixels*.08);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_expected_increment, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        int height = displaymetrics.widthPixels;
//        int width = displaymetrics.heightPixels;
//
//        mainLinearLayout = (LinearLayout)v.findViewById(R.id.mainLinearLayout);
//        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
//        mainLinearLayout.setLayoutParams(parms);

        expectedAge = v.findViewById(R.id.expected_inc_age);
        expectedPercentage = v.findViewById(R.id.expected_inc_percentage);
        expectedNotes = v.findViewById(R.id.expected_inc_notes);
        cancelbutton = v.findViewById(R.id.expected_inc_cancel);
        savebutton = v.findViewById(R.id.expected_inc_save);
        if(mExpIncrementValue.length()!=0) {
            try {
                String value[] = mExpIncrementValue.split(",");

                expectedAge.setText(value[0]);
                if (value.length > 1)
                    expectedPercentage.setText(value[1]);
                expectedAge.setSelection(expectedAge.length());
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (Exception e)
            {

            }
        }
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDoneButtonClick();
            }
        });

        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }

//    @Override
//    public void onResume() {
//        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
//        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.50);
//        getDialog().getWindow().setLayout(width, height);
//        super.onResume();
//    }
    public interface OnExpectedValueListener {
        void onValueSet(ExpectedIncrementDialogFragment dialog, String age, String percentage, int id) throws JSONException;
    }

    private void onDoneButtonClick() {
        ageStr = expectedAge.getText().toString();
        expectedAge.setSelection(expectedAge.length());
        percentageStr = expectedPercentage.getText().toString();
        notes = expectedNotes.getText().toString();
        if (mCallBack != null) {
            try {
                mCallBack.onValueSet(this, ageStr, percentageStr, mId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        dismiss();
    }

}