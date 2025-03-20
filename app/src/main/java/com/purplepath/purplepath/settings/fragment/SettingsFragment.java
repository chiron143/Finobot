package com.purplepath.purplepath.settings.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
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
 * Created by Pratheep.S on 04-01-2017.
 */

public class SettingsFragment extends BaseFragment implements UpdateOnNavigation, RadioGroup.OnCheckedChangeListener {

    private RadioGroup playAlarm_rg,vibration_rg,notification_rg,dateformat_rg,options_rg;
    private RadioButton playAlarm_on_rb,playAlarm_off_rb,vibration_on_rb,vibration_off_rb,notification_on_rb,notification_off_rb,dateformat_on_rb,dateformat_off_rb;
    private RadioButton defalultOption,suggestedOption,otherOption;
    private Context mContext;
    private int height,width;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_settings,container,false);
        playAlarm_rg= view.findViewById(R.id.alarm_rg);
        playAlarm_on_rb= view.findViewById(R.id.alarm_on_RadioBtn);
        playAlarm_off_rb= view.findViewById(R.id.alarm_off_RadioBtn);

        vibration_rg= view.findViewById(R.id.vibration_rg);
        vibration_on_rb= view.findViewById(R.id.vibration_on_RadioBtn);
        vibration_off_rb= view.findViewById(R.id.vibration_off_RadioBtn);

        notification_rg= view.findViewById(R.id.notification_rg);
        notification_on_rb= view.findViewById(R.id.notification_on_RadioBtn);
        notification_off_rb= view.findViewById(R.id.notification_off_RadioBtn);

        dateformat_rg= view.findViewById(R.id.dateformat_rg);
        dateformat_on_rb= view.findViewById(R.id.dateformat_on_RadioBtn);
        dateformat_off_rb= view.findViewById(R.id.dateformat_off_RadioBtn);

        options_rg= view.findViewById(R.id.optionsRadioGroup);
        defalultOption= view.findViewById(R.id.defaultOptionRadioButton);
        suggestedOption= view.findViewById(R.id.suggestedOptionRadioButton);
        otherOption= view.findViewById(R.id.otherOptionRadioButton);

        options_rg.setOnCheckedChangeListener(this);
        playAlarm_rg.setOnCheckedChangeListener(this);
        vibration_rg.setOnCheckedChangeListener(this);
        notification_rg.setOnCheckedChangeListener(this);
        dateformat_rg.setOnCheckedChangeListener(this);


        mContext=getContext();
        width=mContext.getResources().getDisplayMetrics().widthPixels;

//        playAlarm_rg.setLayoutParams(new RelativeLayout.LayoutParams((int)(width*.25),(int) (width*.125)));
        playAlarm_on_rb.setWidth(width/15);
        playAlarm_on_rb.setHeight(width/15);
       /* playAlarm_off_rb.setWidth((int) ((int) (width*.25)));
        playAlarm_off_rb.setHeight((int) ((int) (width*.25)));
*/

        loadOptionsRadioButtonsState();
        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        Log.i("spcheck", "onResume: Settings");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("spcheck", "onPause: Settings ");
    }

    @Override
    public void updateAllFields() {
        Log.i("spcheck", "updateAllFields: SettingsFragment");
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        switch (radioGroup.getId()) {
            case R.id.alarm_rg:
                if(id==playAlarm_on_rb.getId()){
                    UtileKit.getSwitchYesBtnView(playAlarm_on_rb, playAlarm_off_rb, mContext);
                }
                else if(id==playAlarm_off_rb.getId()){
                    UtileKit.getSwitchNoBtnView(playAlarm_on_rb,playAlarm_off_rb, mContext);

                }
                break;
            case R.id.vibration_rg:
                if(id==vibration_on_rb.getId()){
                    UtileKit.getSwitchYesBtnView(vibration_on_rb,vibration_off_rb, mContext);
                }
                else if (id==vibration_off_rb.getId()){
                    UtileKit.getSwitchNoBtnView(vibration_on_rb,vibration_off_rb,mContext);
                }
                break;

            case R.id.notification_rg:
                if(id==notification_on_rb.getId()){
                    UtileKit.getSwitchYesBtnView(notification_on_rb,notification_off_rb, mContext);
                }
                else if (id==notification_off_rb.getId()){
                    UtileKit.getSwitchNoBtnView(notification_on_rb,notification_off_rb,mContext);
                }
                break;

            case R.id.dateformat_rg:
                if(id==dateformat_on_rb.getId()){
                    UtileKit.getSwitchYesBtnView(dateformat_on_rb,dateformat_off_rb, mContext);
                }
                else if (id==dateformat_off_rb.getId()){
                    UtileKit.getSwitchNoBtnView(dateformat_on_rb,dateformat_off_rb,mContext);
                }
                break;
            case R.id.optionsRadioGroup:
                if(id==defalultOption.getId()){

                }
                else if(id==suggestedOption.getId()){}
                else if(id==otherOption.getId()){}

                options_rg.check(id);
                saveOptionsRadioButtonsState();
                break;


        }

    }

    private void saveOptionsRadioButtonsState() {
        UtileKit.persistingPurplePathPref("defaultOptionRB",defalultOption.isChecked());
        UtileKit.persistingPurplePathPref("suggestedOptionRB",suggestedOption.isChecked());
        UtileKit.persistingPurplePathPref("otherOptionRB",otherOption.isChecked());
    }

    private void loadOptionsRadioButtonsState() {
        defalultOption.setChecked(UtileKit.getPersistedPurplePathBoolPref("defaultOptionRB"));
        suggestedOption.setChecked(UtileKit.getPersistedPurplePathBoolPref("suggestedOptionRB"));
        otherOption.setChecked(UtileKit.getPersistedPurplePathBoolPref("otherOptionRB"));
    }


}
