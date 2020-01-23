package com.purplepath.purplepath.settings.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.settings.Interfaces.UpdateOnNavigation;

/**
 * Created by pravinr on 3/16/18.
 */

public class CalenderFragment extends BaseFragment implements View.OnClickListener,RadioGroup.OnCheckedChangeListener,UpdateOnNavigation {


    public View view;

    private Context mContext;

    RadioGroup radioGroup;

    RadioButton both_calender,default_calender,span_calender;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
       }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calender, container, false);
        initiallizeAllViews(view);

        return view;
    }





    private void initiallizeAllViews(View view) {
        radioGroup=((RadioGroup) view.findViewById(R.id.radioGroup));
        both_calender=((RadioButton)view.findViewById(R.id.both_calender));
        default_calender=((RadioButton)view.findViewById(R.id.default_calender));
        span_calender =((RadioButton)view.findViewById(R.id.span_calender));

        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
        switch (radioGroup.getId()) {

            case R.id.radioGroup:

                if (checkedId == R.id.both_calender) {
                    UtileKit.persistingPurplePathPref("Setting_Calender","Both");
                }
                if (checkedId == R.id.default_calender) {
                    UtileKit.persistingPurplePathPref("Setting_Calender","Default");
                }
                if (checkedId == R.id.span_calender) {
                    UtileKit.persistingPurplePathPref("Setting_Calender","Span");
                }

                break;
        }

    }

    @Override
    public void updateAllFields() {

    }
}

