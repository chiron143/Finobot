package com.purplepath.purplepath.riskAssesment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.fourmob.datetimepicker.Utils;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.riskAssesment.models.RiskProfileModel;
import com.purplepath.purplepath.riskAssesment.models.Risk_Profile_Questionnaries;
import com.purplepath.purplepath.riskAssesment.scoreModels.RiskProfileByScoreModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Suresh on 12/01/17.
 */

public class RiskProfile  extends BaseFragment implements View.OnClickListener{
    private final static String TAG = RiskProfile.class.getCanonicalName();
    private static final int question1 = 1;
    private Context mContext;
    private Activity activity;
    private RiskProfileModel mriskProfilemodel;
    private LinearLayout parentView;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private OnActivityBackPressedListener mCallBackListener;
    ArrayList<Risk_Profile_Questionnaries> jsonAddArrayList = new ArrayList<>();
    final ArrayList<String> jsonAddArrayListId = new ArrayList<>();
    final ArrayList<String> getselectedOptionArrayList = new ArrayList<>();
    final ArrayList<String> jsonAddArrayListOption = new ArrayList<>();
    final ArrayList<String> jsonAddArrayListPoints = new ArrayList<>();
    ArrayList<RadioGroup> radioGroup;
    ArrayList<RadioButton> optionA,optionB,optionC, optionD;
    private FloatingActionButton fab;
    private HashMap<Integer,String> map=new HashMap<>();
    int pos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        activity = getActivity();
        setHasOptionsMenu(true);
        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {}
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View riskProfileView =  inflater.inflate(R.layout.frag_risk_profile, container, false);
        parentView= riskProfileView.findViewById(R.id.parentViewId);

        mCallBackListener.setActionBarTitle("My Risk Assessment");
        mleftRelativeLayout = riskProfileView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = riskProfileView.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = riskProfileView.findViewById(R.id.relative_right_arrow);
        fab = riskProfileView.findViewById(R.id.risk_tick_button);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        fab.setOnClickListener(this);
        try {
            jsonAddArrayListOption.clear();
        }catch (Exception e){
            e.printStackTrace();
        }

        return  riskProfileView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callRiskService();// Services call here ;
    }

    private void callRiskService() {
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<RiskProfileModel> call = webServiceObj.getRiskProfileService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<RiskProfileModel>() {
            @Override
            public void onResponse(Call<RiskProfileModel> call, Response<RiskProfileModel> response) {
                try {
                    Log.i("RiskProfile","RiskProfile user id"+UtileKit.getPersistedPurplePathPref("user_id"));
                    mriskProfilemodel = response.body();

                    if (mriskProfilemodel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                            layoutDesignquestionCheckBox(mriskProfilemodel);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RiskProfileModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
        UtileKit.dismisssSpinnerDialog();
    }

    private void layoutDesignquestionCheckBox(final RiskProfileModel mriskProfilemodel) {
        radioGroup=new ArrayList<>();
        optionA =new ArrayList<>();
        optionB =new ArrayList<>();
        optionC=new ArrayList<>();
        optionD=new ArrayList<>();
        if(mriskProfilemodel.getData()!=null){
                Log.i(TAG,"RiskProfile "+mriskProfilemodel.getData().getRisk_Profile_Questionnaries().get(0).getQuestion());
                LayoutInflater inflater = LayoutInflater.from(mContext);
                jsonAddArrayList =new ArrayList<>();

                jsonAddArrayList.addAll(mriskProfilemodel.getData().getRisk_Profile_Questionnaries());
                int size = mriskProfilemodel.getData().getRisk_Profile_Questionnaries().size();
                for (int i = 0; i < size; i++) {
                    final View view = inflater.inflate(R.layout.risk_profile_single_checkbox, null);
                    TextView setQuestions = view.findViewById(R.id.questions);
                    CardView card_root = view.findViewById(R.id.card_root);
                    radioGroup.add((RadioGroup) view.findViewById(R.id.radioGroup1));
                    radioGroup.get(radioGroup.size()-1).setId(i);
                    optionA.add((RadioButton)view.findViewById(R.id.optionA));
                    optionB.add((RadioButton)view.findViewById(R.id.optionB));
                    optionC .add((RadioButton)view.findViewById(R.id.optionC));
                    optionD .add( (RadioButton)view.findViewById(R.id.optionD));
                    radioGroup.get(radioGroup.size()-1).setTag(i);
                    optionA.get(optionA.size()-1).setId((100*i) +1);
                    optionB.get(optionB.size()-1).setId((100*i) +2);
                    optionC.get(optionC.size()-1).setId((100*i) +3);
                    optionD.get(optionD.size()-1).setId((100*i) +4);

                    if (mriskProfilemodel.getData().getRisk_Profile_Questionnaries().get(i).getQuestion() != null
                            && mriskProfilemodel.getData().getRisk_Profile_Questionnaries().get(i).getOption_1() != null
                            && mriskProfilemodel.getData().getRisk_Profile_Questionnaries().get(i).getOption_2() != null
                            && mriskProfilemodel.getData().getRisk_Profile_Questionnaries().get(i).getOption_3() != null
                            && mriskProfilemodel.getData().getRisk_Profile_Questionnaries().get(i).getOption_4() != null) {

                        setQuestions.setText(mriskProfilemodel.getData().getRisk_Profile_Questionnaries().get(i).getQuestion());
                        optionA.get(optionA.size() - 1).setText(mriskProfilemodel.getData().getRisk_Profile_Questionnaries().get(i).getOption_1());
                        optionB.get(optionA.size() - 1).setText(mriskProfilemodel.getData().getRisk_Profile_Questionnaries().get(i).getOption_2());
                        optionC.get(optionA.size() - 1).setText(mriskProfilemodel.getData().getRisk_Profile_Questionnaries().get(i).getOption_3());
                        optionD.get(optionA.size() - 1).setText(mriskProfilemodel.getData().getRisk_Profile_Questionnaries().get(i).getOption_4());
                        jsonAddArrayListId.add(mriskProfilemodel.getData().getRisk_Profile_Questionnaries().get(i).getRisk_ques_id());


                    } else {
                        card_root.setVisibility(View.GONE);
                        jsonAddArrayListId.add("");
                    }
                    jsonAddArrayListOption.add("");
                    jsonAddArrayListPoints.add("");
                    try{
                        radioGroup.get(radioGroup.size() - 1).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {


                                int getTAGgetmodels = (int) group.getTag();
                                pos = radioGroup.get(getTAGgetmodels).indexOfChild(view.findViewById(checkedId));
                                Log.i(TAG, "gettagValue" + getTAGgetmodels);
                                switch (pos) {
                                    case 0:
                                        jsonAddArrayListOption.set(getTAGgetmodels, "a");
                                        jsonAddArrayListPoints.set(getTAGgetmodels, jsonAddArrayList.get(getTAGgetmodels).getPoint_1());
                                        break;
                                    case 1:
                                        jsonAddArrayListOption.set(getTAGgetmodels, "b");
                                        jsonAddArrayListPoints.set(getTAGgetmodels, jsonAddArrayList.get(getTAGgetmodels).getPoint_2());
                                        break;
                                    case 2:
                                        jsonAddArrayListOption.set(getTAGgetmodels, "c");
                                        jsonAddArrayListPoints.set(getTAGgetmodels, jsonAddArrayList.get(getTAGgetmodels).getPoint_3());
                                        break;

                                    case 3:
                                        jsonAddArrayListOption.set(getTAGgetmodels, "d");
                                        jsonAddArrayListPoints.set(getTAGgetmodels, jsonAddArrayList.get(getTAGgetmodels).getPoint_4());
                                        break;
                                    default:
                                        break;
                                }
                            }

                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                    if(mriskProfilemodel.getData().getSelected_options()!=null){
                        try {
                            int size_Profile_Questionnaries = mriskProfilemodel.getData().getRisk_Profile_Questionnaries().size();
                            int size_Answers = mriskProfilemodel.getData().getSelected_options().size();

                            Log.i(TAG, "RiskProfile selected options " + mriskProfilemodel.getData().getSelected_options());
                            for (int k = 0; k < size_Profile_Questionnaries; k++) {
                                for(int j =0; j < size_Answers; j++ ) {
                                    String profile_Question = mriskProfilemodel.getData().getRisk_Profile_Questionnaries().get(k).getRisk_ques_id();
                                    String profile_Answer = mriskProfilemodel.getData().getSelected_options().get(j).getRisk_ques_id();
                                    if (profile_Question.equalsIgnoreCase(profile_Answer)) {
                                        String id_position = mriskProfilemodel.getData().getSelected_options().get(j).getRisk_ques_id();
                                        Log.i(TAG, "RiskProfile selected options " + id_position);
                                        int selected_postion = Utils.getpostion(mriskProfilemodel.getData().getSelected_options().get(j).getSelected_option());
                                        Log.i(TAG, "RiskProfile selected options postion" + selected_postion );
//                                        case_checkTrue(view,radioGroup,selected_postion);
//                                        radioGroup.get(radioGroup.size()-1).check(((RadioButton)radioGroup.get(radioGroup.size()-1).getChildAt(selected_postion)).getId());
//                                        radioGroup.get(radioGroup.size()-1).check(Integer.parseInt(id_position));
                                        if(mriskProfilemodel.getData().getRisk_Profile_Questionnaries().get(i).getRisk_ques_id().equalsIgnoreCase(id_position)){

                                            if(selected_postion == 0){

                                                optionA.get(i).setChecked(true);
                                            }
                                            else if(selected_postion == 1){
                                                optionB.get(i).setChecked(true);
                                            }
                                            else if(selected_postion == 2){
                                                optionC.get(i).setChecked(true);
                                            }
                                            else if(selected_postion == 3){
                                                optionD.get(i).setChecked(true);
                                            }
                                        }

                                    }
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }else {
                        Log.i(TAG, "RiskProfile selected options else ");

                    }


                    parentView.addView(view);
                }
        }


    }

    private void updateWebservice() {
        JSONObject finalObj= makeJsonObject();
        Log.i("WebServiceCalls", "finalObj: success" +makeJsonObject());
        WebServiceCalls callObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<RiskProfileByScoreModel> call=callObj.getRiskProfileByScore(UtileKit.getPersistedPurplePathPref("user_id")
                ,finalObj.toString());
        call.enqueue(new Callback<RiskProfileByScoreModel>() {
            @Override
            public void onResponse(Call<RiskProfileByScoreModel> call, Response<RiskProfileByScoreModel> response) {
                Log.i("WebServiceCalls", "onResponse: success");
            }
            @Override
            public void onFailure(Call<RiskProfileByScoreModel> call, Throwable t) {
                Log.i("WebServiceCalls", "onResponse: failure"); UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }

    private  JSONObject makeJsonObject() {
        JSONArray array = new JSONArray();
        String Points ="0", Option = null, id=null;
        int size=jsonAddArrayListOption.size();
        Log.i("spcheck", "makeJsonObject " + size);
        for(int i= 0; i< size ; i++ ){
            if(jsonAddArrayListOption!=null){
                 Option =    jsonAddArrayListOption.get(i);
                    Log.i(TAG, "makeJsonObject Option " + Option);
                if(jsonAddArrayListPoints.get(i)!=null && jsonAddArrayListPoints.get(i)!="") {
                    Points = jsonAddArrayListPoints.get(i);
                }else{
                    Points = "0";
                }
                id = jsonAddArrayListId.get(i);
                Log.i(TAG, "makeJsonObject Option id" + id);
                JSONObject obj = null;
                obj = new JSONObject();
                try {
                    obj.put("risk_ques_id", id);
                    obj.put("selected_option",Option );
                    obj.put("selected_option_score",Points);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                array.put(obj);
                Log.i(TAG, "makeJsonObject Points " + Points);

            }
        }
        JSONObject finalObj = new JSONObject();
        try {
            finalObj.put("risk_det", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return finalObj;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relative_left_arrow:
            {

                Log.i("spcheck", " relative_left_arrow is clicked"  );
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home:
            {
                Log.i(TAG, " relative_center_home is clicked"  );
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                startActivity(i);
            }
            break;


            case R.id.risk_tick_button:{
                updateWebservice();
                try{
                    addFragmenttoStack(new RiskAssesmentResultFragment());
                }catch (Exception e){
                    e.printStackTrace();
                }

               /* android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                RiskAssesmentResultFragment fragment = new RiskAssesmentResultFragment();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(fragment.getClass().getName());
                fragmentTransaction.commitAllowingStateLoss();*/
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_risk, menu);
        super.onCreateOptionsMenu(menu, inflater);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.ic_done_btn:
                Log.i(TAG, "ic_done_btn" );
                try{
                    updateWebservice();
                    addFragmenttoStack(new RiskAssesmentResultFragment());

                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();

    }

}