package com.purplepath.purplepath.taxfiling;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.calenderNumberPicker.calenderUi.CalenderTabs;
import com.purplepath.purplepath.customview.CharacterEditText;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.customview.CurrencyGhostViewTaxFiling;
import com.purplepath.purplepath.customview.NumberEditText;
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.DatePickerCallBackInterface;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxfiling.adapter.CustomSpinnerAdapters;
import com.purplepath.purplepath.taxfiling.addsessionmodel.AddSessionModel;
import com.purplepath.purplepath.taxfiling.credencialmodel.CredencialModel;
import com.purplepath.purplepath.taxprompt.model.DeleteTaxStatemetModel;
import com.purplepath.purplepath.taxprompt.model.Overall_questions;
import com.purplepath.purplepath.taxprompt.model.TaxPromptAnserModel;
import com.purplepath.purplepath.taxprompt.model.TaxPromptModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finobot.finobot.MyApplication.mFirebaseAnalytics;


/**
 * Created by pravinr on 5/22/18.
 */

public class TaxFilingCredential extends BaseFragment implements View.OnClickListener,
        DatePickerCallBackInterface, View.OnFocusChangeListener {

    @Bind(R.id.parentViewId)
    LinearLayout parentView;

    @Bind(R.id.layout_yes_no_bottom_bar)
    LinearLayout layout_yes_no_bottom_bar;

    //character edittext
    @Bind(R.id.layout_character_edittext)
    LinearLayout layout_character_edittext;

    @Bind(R.id.character_edittext_img)
    FloatingActionButton character_edittext_img;
    //percentage edittext
    @Bind(R.id.layout_percentage_edittext)
    LinearLayout layout_percentage_edittext;

    @Bind(R.id.percentage_edittext_img)
    FloatingActionButton percentage_edittext_img;
    //number edittext
    @Bind(R.id.layout_number_edittext)
    LinearLayout layout_number_edittext;


    @Bind(R.id.number_edittext_img)
    FloatingActionButton number_edittext_img;
    //currencyDef edittext
    @Bind(R.id.layout_currencydefault_edittext)
    LinearLayout layout_currencydefault_edittext;

    @Bind(R.id.percentage_edittext)
    PercentageEditText percentage_edittext;

    @Bind(R.id.number_edittext)
    NumberEditText number_edittext;

    @Bind(R.id.currencydefault_edittext)
    CurrencyGhostView currencydefault_edittext;

    @Bind(R.id.character_edittext)
    CharacterEditText character_edittext;

    @Bind(R.id.currencydefault_edittext_img)
    FloatingActionButton currencydefault_edittext_img;

    CalenderTabs mcalenderTabDialog;

    private Context mContext;

    private TextView txt_left, dialog_no, dialog_yes;

    private ScrollView scrollview;

    private String selected_item;

    private OnActivityBackPressedListener mCallBackListener;

    private Spinner spinner;

    private Overall_questions overall_questions;

    private Overall_questions date_individual_questions;

    private TextView empty_text;

    int positon;

    String ques_id = "";

    String corres_table = "";

    TaxPromptModel taxPromptModel;

    View mUpdateView;

    boolean individual_date_boolean = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try{
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){
            e.printStackTrace();
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        try {
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_taxfile,menu);
        MenuItem item=menu.findItem(R.id.menu_taxfile);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_taxfile:
                try{
                    callDeleteTaxPromptStatement("","");
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mCallBackListener.setActionBarTitle("Credential");
        View view = inflater.inflate(R.layout.fragment_taxfiling_credential, container, false);
        ButterKnife.bind(this, view);

        scrollview = (ScrollView) view.findViewById(R.id.scrollview);
        empty_text=(TextView)view.findViewById(R.id.empty_text);

        dialog_no = (TextView) view.findViewById(R.id.no);
        dialog_yes = (TextView) view.findViewById(R.id.yes);
        dialog_no.setOnClickListener(this);
        dialog_yes.setOnClickListener(this);
        character_edittext_img.setOnClickListener(this);
        percentage_edittext_img.setOnClickListener(this);
        number_edittext_img.setOnClickListener(this);
        currencydefault_edittext_img.setOnClickListener(this);

        currencydefault_edittext.setOnClickListener(this);
        character_edittext.setOnClickListener(this);
        number_edittext.setOnClickListener(this);
        percentage_edittext.setOnClickListener(this);


        callGetTaxPromptService();

        currencydefault_edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollview.smoothScrollTo(0, parentView.getBottom());
                scrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        currencydefault_edittext.getEditText().requestFocus();
                    }
                });
                return false;
            }
        });

        number_edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollview.smoothScrollTo(0, parentView.getBottom());
                scrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        number_edittext.requestFocus();
                    }
                });
                return false;

            }
        });

        character_edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollview.smoothScrollTo(0, parentView.getBottom());
                scrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        character_edittext.requestFocus();
                    }
                });
                return false;
            }
        });
        percentage_edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollview.smoothScrollTo(0, parentView.getBottom());
                scrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        percentage_edittext.requestFocus();
                    }
                });
                return false;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.setCurrentScreen(getActivity(),getString(R.string.analtics_taxcredential_screen),getActivity().getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "13");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Tax Module");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Tax");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }

    void spinnerError(Spinner spinner) {
        TextView errorText = (TextView) spinner.getSelectedView();
        errorText.setError("");
        errorText.setTextColor(Color.RED);
        errorText.setText("");
    }

    public void setSpinnerAdapter(Spinner mMyMartialSpinner, ArrayList<String> mystringList, Context mycontext) {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("");
        for (String s : mystringList) {
            stringList.add(s);
        }
        CustomSpinnerAdapters adapter_state = new CustomSpinnerAdapters(mycontext, stringList);
        mMyMartialSpinner.setAdapter(adapter_state);

    }


    public void callGetTaxPromptService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TaxPromptModel> call = webServiceObj.callGetCredentialTaxFileService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<TaxPromptModel>() {
            @Override
            public void onResponse(Call<TaxPromptModel> call, Response<TaxPromptModel> response) {
                UtileKit.dismisssSpinnerDialog();
                taxPromptModel = response.body();
                if (taxPromptModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    parentView.removeAllViews();
                    startDynamicChartView(taxPromptModel, 0);

                }else {
                    empty_text.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<TaxPromptModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void startDynamicChartView(TaxPromptModel taxPromptModel, int startPosition) {
        int length = taxPromptModel.getData().getOverall_questions().size();
        for (int i = startPosition; i < length; i++) {
            positon = i;
            String type = taxPromptModel.getData().getOverall_questions().get(i).getType();
            ques_id = taxPromptModel.getData().getOverall_questions().get(i).getQues_id();
            String updated_flag = taxPromptModel.getData().getOverall_questions().get(i).getUpdated_flag();
            String boolean_flag = taxPromptModel.getData().getOverall_questions().get(i).getBoolean_flag();
            String user_visited_flag = taxPromptModel.getData().getOverall_questions().get(i).getUser_visited_flag();
            String input_type_flag = taxPromptModel.getData().getOverall_questions().get(i).getInput_type();
            if (type.equalsIgnoreCase("input_text")) {
                if (input_type_flag == null) {

                    addMessagetoLeft(i, ques_id);

                } else if (input_type_flag.equalsIgnoreCase("date")) {
                    overall_questions = taxPromptModel.getData().getOverall_questions().get(i);
                    addMessagetoLeft(i, ques_id);
                    if (user_visited_flag.equalsIgnoreCase("N")) {
                        addMessagetoRight(i, ques_id, input_type_flag, taxPromptModel.getData().getOverall_questions().get(i));

                    } else {
                        addMessagetoRight(taxPromptModel.getData().getOverall_questions().get(i).getField_answer());
                    }

                } else if (input_type_flag.equalsIgnoreCase("character")) {
                    addMessagetoLeft(i, ques_id);
                    overall_questions = taxPromptModel.getData().getOverall_questions().get(i);

                    if (user_visited_flag.equalsIgnoreCase("N")) {
                        layout_character_edittext.setVisibility(View.VISIBLE);
                        if(overall_questions.getField_answer()!=null)
                            character_edittext.setText(""+overall_questions.getField_answer());
                        else
                            character_edittext.setText("");
                    } else {
                        addMessagetoRight(overall_questions.getField_answer());
                    }

                } else if (input_type_flag.equalsIgnoreCase("percentage")) {
                    addMessagetoLeft(i, ques_id);
                    overall_questions = taxPromptModel.getData().getOverall_questions().get(i);

                    if (user_visited_flag.equalsIgnoreCase("N")) {
                        layout_percentage_edittext.setVisibility(View.VISIBLE);
                        if(overall_questions.getField_answer()!=null)
                            percentage_edittext.setText(""+overall_questions.getField_answer());
                        else
                            percentage_edittext.setText("");
                    } else {
                        addMessagetoRight(overall_questions.getField_answer());
                    }
                } else if (input_type_flag.equalsIgnoreCase("number")) {
                    addMessagetoLeft(i, ques_id);
                    overall_questions = taxPromptModel.getData().getOverall_questions().get(i);

                    if (user_visited_flag.equalsIgnoreCase("N")) {
                        layout_number_edittext.setVisibility(View.VISIBLE);
                        if(overall_questions.getField_answer()!=null)
                            number_edittext.setText(""+overall_questions.getField_answer());
                        else
                            number_edittext.setText("");
                    } else {
                        addMessagetoRight(overall_questions.getField_answer());
                    }
                } else if (input_type_flag.equalsIgnoreCase("currency")) {
                    addMessagetoLeft(i, ques_id);
                    overall_questions = taxPromptModel.getData().getOverall_questions().get(i);
                    if (user_visited_flag.equalsIgnoreCase("N")) {
                        layout_currencydefault_edittext.setVisibility(View.VISIBLE);
                        if(overall_questions.getField_answer()!=null)
                            currencydefault_edittext.getEditText().setText(""+overall_questions.getField_answer());
                        else
                            currencydefault_edittext.getEditText().setText("");

                    } else {
                        if (overall_questions.getField_answer() != null)
                            addMessagetoRight(UtileKit.currencyConvert(overall_questions.getField_answer()));
                        else
                            addMessagetoRight("Empty");
                    }

                } else if (input_type_flag.equalsIgnoreCase("spinner")) {
                    overall_questions = taxPromptModel.getData().getOverall_questions().get(i);
                    if (user_visited_flag.equalsIgnoreCase("N")) {

                        //addMessagetoLeft(i, ques_id);
                        addMessagetoRight(i, ques_id, input_type_flag, taxPromptModel.getData().getOverall_questions().get(i));

                    } else {
                        addMessagetoLeft(i, ques_id);
                        addMessagetoRight(overall_questions.getField_answer());
                    }


                }

            } else if (type.equalsIgnoreCase("message")) {
                overall_questions = taxPromptModel.getData().getOverall_questions().get(i);
                corres_table = taxPromptModel.getData().getOverall_questions().get(i).getCorresponding_table();
                addMessagetoLeft(i, ques_id);
                if (input_type_flag.equalsIgnoreCase("yn_dialog")) {
                    if (user_visited_flag.equalsIgnoreCase("Y")) {
                        if (updated_flag.equalsIgnoreCase("N")) {

                            addMessagetoRight("No");
                            jumptoQuestion(overall_questions.getNo_ques_id());
                            break;
                        } else {
                            addMessagetoRight("Yes");
                            jumptoQuestion(overall_questions.getYes_ques_id());
                            break;
                        }

                    } else {
                        bottomLinearlayoutAnimation(i, layout_yes_no_bottom_bar);
                    }
                }
                else if(input_type_flag.equalsIgnoreCase("yn_dialog_ans")){
                    if (user_visited_flag.equalsIgnoreCase("Y")) {
                        if (updated_flag.equalsIgnoreCase("N")) {

                            addMessagetoRight("No");
                            jumptoQuestion(overall_questions.getNo_ques_id());
                            break;
                        } else {
                            addMessagetoRight("Yes");
                            jumptoQuestion(overall_questions.getYes_ques_id());
                            break;
                        }

                    } else {
                        bottomLinearlayoutAnimation(i, layout_yes_no_bottom_bar);
                    }
                }
//do it here dialog
                else if(input_type_flag.equalsIgnoreCase("dialog")){
                    startDynamicChartView(taxPromptModel, positon + 1);
                }

            }
            else if(type.equalsIgnoreCase("final")){
                //   if ((length - 1) == positon) {
                overall_questions = taxPromptModel.getData().getOverall_questions().get(i);
                addSubmitMessage(overall_questions.getStatement());
                positon = positon + 1;
                //   }
            }

            scrollview.post(new Runnable() {
                @Override
                public void run() {
                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });


            if (user_visited_flag.equalsIgnoreCase("N")) {
                break;
            }
        }


    }


    void spinnerCardView(String title_heading, String question, Context mContext, ArrayList<String> spinnerArray,
                         final Overall_questions overall_questions, final View view, final String field_answer) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialogs;

        try {
            inflater = LayoutInflater.from(mContext);
            dialogView = inflater.inflate(R.layout.card_dialog_spinner_view, null);
            alertDialogs = new AlertDialog.Builder(mContext).create();
            alertDialogs.setView(dialogView);
            TextView heading = (TextView) dialogView.findViewById(R.id.txt_heading);
            TextView txt_question = (TextView) dialogView.findViewById(R.id.txt_question);
            spinner = (Spinner) dialogView.findViewById(R.id.spinner);


            heading.setText(title_heading);
            txt_question.setText("" + overall_questions.getStatement());
            setSpinnerAdapter(spinner, spinnerArray, mContext);

            if (UtileKit.validateObjectValues(field_answer)) {
                int pos = getSpinnerAssetposition(field_answer, spinnerArray);
                spinner.setSelection(pos);
            }


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    selected_item = parent.getItemAtPosition(position).toString();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selected_item.equalsIgnoreCase("")) {
                        spinnerError(spinner);
                    } else {
                        view.setVisibility(View.GONE);
                        addMessagetoRight("" + selected_item);
                        callUpdateFiledService(overall_questions.getQues_id(),
                                overall_questions.getCorresponding_table(), overall_questions.getField_name(),
                                selected_item, false, overall_questions.getEncrypt_flag(), "N", "");
                        alertDialogs.dismiss();
                    }
                }
            });
            alertDialogs.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private int getSpinnerAssetposition(String value, ArrayList<String> spinerlist) {
        int pos = 0;
        for (int j = 0; j < spinerlist.size(); j++) {
            if (value.equalsIgnoreCase(spinerlist.get(j))) {
                return j + 1;
            }
        }
        return pos;
    }

    private void addSubmitMessage(String msg) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.prompt_submit_card_view, null);
        final TextView txt_layout = (TextView) view.findViewById(R.id.title);
        TextView submitTxt = (TextView) view.findViewById(R.id.questionId);
        submitTxt.setText(R.string.submit);
        txt_layout.setText("" + overall_questions.getStatement());
        txt_layout.setId(getViewId());
        LinearLayout redirect_page = (LinearLayout) view.findViewById(R.id.redirect_page);
        LinearLayout redoBtn = (LinearLayout) view.findViewById(R.id.redo_pageId);
        redirect_page.setTag(overall_questions);

        redoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDeleteTaxPromptStatement("","");
            }
        });
        redirect_page.setOnClickListener(                                                                                                     new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callCredentialsService();

            }
        });
        parentView.addView(view);
    }

    //Create XML IRT file
    private void  callCredentialsService() {

        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<CredencialModel> call = webServiceObj.callCreateXmlfileService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<CredencialModel>() {
            @Override
            public void onResponse(Call<CredencialModel> call, Response<CredencialModel> response) {
                UtileKit.dismisssSpinnerDialog();
                CredencialModel declarationmodel = response.body();
                try{
                if (declarationmodel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    //addFragmenttoStack(new TaxFilingThankyouFragment());

                    AddTaxfileSessionService("users_tax_file_page_visited_status","itr_xml_generated","Y");
                }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CredencialModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }


    private void AddTaxfileSessionService(String corresponding_table, String field_name, String field_value) {

        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<AddSessionModel> call = webServiceObj.AddTaxfileSessionService(UtileKit.getPersistedPurplePathPref("user_id")
                ,corresponding_table,field_name, field_value);
        call.enqueue(new Callback<AddSessionModel>() {
            @Override
            public void onResponse(Call<AddSessionModel> call, Response<AddSessionModel> response) {
                UtileKit.dismisssSpinnerDialog();
                AddSessionModel userStatusModel = response.body();
                try {
                    if (userStatusModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        addFragmenttoStack(new TaxFilingThankyouFragment());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AddSessionModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }



    private void addMessagetoRight(int i, String quesId, final String ques_id, final Overall_questions overall_questions) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.prompt_get_data_view, null);
        final TextView txt_layout = (TextView) view.findViewById(R.id.title);
        txt_layout.setText("" + overall_questions.getStatement());
        txt_layout.setId(getViewId());
        LinearLayout redirect_page = (LinearLayout) view.findViewById(R.id.redirect_page);
        redirect_page.setTag(overall_questions);
        redirect_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (overall_questions.getType().equalsIgnoreCase("input_text")) {
                    if (overall_questions.getInput_type().equalsIgnoreCase("date")) {
                        statDateDialog(overall_questions, overall_questions.getStatement(),overall_questions.getField_answer());
                        v.setVisibility(View.GONE);

                    } else if (overall_questions.getInput_type().equalsIgnoreCase("spinner")) {

                        spinnerCardView("Select", overall_questions.getStatement(), mContext,
                                new ArrayList<String>(Arrays.asList(overall_questions.getSpinner_array().split(","))),
                                overall_questions, v,overall_questions.getField_answer());

                    }
                }
            }
        });
        parentView.addView(view);
    }

    private int getViewId() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return UtileKit.generateViewId();
        } else {
            return View.generateViewId();

        }
    }

    private void statDateDialog(Overall_questions overall_question, String mDateTitle, String date) {
        overall_questions = overall_question;

        String concactDDMMYY = "";
        String datess = date;
        if(datess!=null) {
            String[] parts = datess.split("-");
            concactDDMMYY = parts[2] + "-" + parts[1] + "-" + parts[0];
        }

        if (concactDDMMYY != null) {
            individual_date_boolean=false;
            mcalenderTabDialog = CalenderTabs.newInstance(this,
                    mDateTitle, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, "1", concactDDMMYY);
            mcalenderTabDialog.setCancelable(false);
            mcalenderTabDialog.show(getFragmentManager(), "Date");
        }
    }


    void addMessagetoLeft(int i, String ques_id) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.left_chat_msg_view, null);
        txt_left = (TextView) view.findViewById(R.id.txt_left);
        LinearLayout layout_anim = (LinearLayout) view.findViewById(R.id.layout_anim);
        leftLinearlayoutAnimation(i, layout_anim);

        txt_left.setText(taxPromptModel.getData().getOverall_questions().get(i).getStatement());
        parentView.addView(view);
    }

    void addMessagetoRight() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.right_chat_msg_view, null);
        TextView txt_layout = (TextView) view.findViewById(R.id.txt_right);
        parentView.addView(view);
    }

    void addMessagetoRight(String answer) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.right_chat_msg_view, null);
        TextView txt_layout = (TextView) view.findViewById(R.id.txt_right);
        UtileKit.setSvgEdittextDrawableRight(txt_layout,mContext,R.drawable.ic_edit_profile);
        txt_layout.setText("" + answer);
        txt_layout.setTag(positon);
        txt_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Test","*********"+view.getTag());
                // ((TextView) view).setText("Done");
                UpdateFieldByPosition(view);

            }
        });
        parentView.addView(view);
    }

    /**
     * Call Dialog With View Position
     * @param view
     */
    private void UpdateFieldByPosition(View view) {
        mUpdateView=view;
        int mPositon=Integer.parseInt(view.getTag().toString());
        Overall_questions mOverallQuestion=taxPromptModel.getData().getOverall_questions().get(mPositon);
        String answer =mOverallQuestion.getField_answer();
        String  QuestionType=mOverallQuestion.getType();
        String inputType=mOverallQuestion.getInput_type();
        String ques_id=mOverallQuestion.getQues_id();


        if(inputType.equalsIgnoreCase("character")||inputType.equalsIgnoreCase("percentage")||
                inputType.equalsIgnoreCase("number")||inputType.equalsIgnoreCase("currency")){

            individualDialogEdit(mPositon,mOverallQuestion,answer,QuestionType,inputType);



        }else if(inputType.equalsIgnoreCase("date")){
            date_individual_questions=mOverallQuestion;
            positon=mPositon;
            individualDialogDate(mPositon,mOverallQuestion, mOverallQuestion.getStatement(),mOverallQuestion.getField_answer());

        }else if(inputType.equalsIgnoreCase("spinner")){

            individualDialogSpinner(mPositon,"Select", mOverallQuestion.getStatement(), mContext,
                    new ArrayList<String>(Arrays.asList(mOverallQuestion.getSpinner_array().split(","))),
                    mOverallQuestion, mUpdateView,mOverallQuestion.getField_answer());
        }else if(inputType.equalsIgnoreCase("yn_dialog")){

            individualYesNoDialogRetain(mContext,"Unable to edit Yes/No question. Do you want to redo from this question?",
                    ques_id,"mid");
        }


    }
    private void individualYesNoDialogRetain(Context context, String message,final String ques_id,final String mid) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialog;

        inflater= LayoutInflater.from(context);
        dialogView=inflater.inflate(R.layout.yes_no_dialogs,null);
        alertDialog=new android.support.v7.app.AlertDialog.Builder(context).create();
        alertDialog.setView(dialogView);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        TextView stringErrorMessage = dialogView.findViewById(R.id.textViewDilog);
        stringErrorMessage.setText(message);
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDeleteTaxPromptStatement(ques_id,mid);
                alertDialog.dismiss();

            }
        });
        dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void individualDialogDate(int mPositon, Overall_questions mOverallQuestion, String mDateTitle, String date) {
        String concactDDMMYY = "";
        String datess = date;
        if(datess!=null) {
            String[] parts = datess.split("-");
            concactDDMMYY = parts[2] + "-" + parts[1] + "-" + parts[0];
        }

        if (concactDDMMYY != null) {
            individual_date_boolean=true;
            mcalenderTabDialog = CalenderTabs.newInstance(this,
                    mDateTitle, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, "1", concactDDMMYY);
            mcalenderTabDialog.setCancelable(false);
            mcalenderTabDialog.show(getFragmentManager(), "Date");
        }



    }

    private void individualDialogSpinner(final int postion,String select, String statement, Context mContext,
                                         ArrayList<String> spinnerArray, final Overall_questions mOverallQuestion,
                                         View view, String field_answer) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialogs;

        try {
            inflater = LayoutInflater.from(mContext);
            dialogView = inflater.inflate(R.layout.individual_card_dialog_spinner_view, null);
            alertDialogs = new AlertDialog.Builder(mContext).create();
            alertDialogs.setView(dialogView);
            TextView heading = (TextView) dialogView.findViewById(R.id.txt_heading);
            TextView txt_question = (TextView) dialogView.findViewById(R.id.txt_question);
            spinner = (Spinner) dialogView.findViewById(R.id.spinner);


            heading.setText("Update");
            txt_question.setText("" + statement);
            setSpinnerAdapter(spinner, spinnerArray, mContext);

            if (UtileKit.validateObjectValues(field_answer)) {
                int pos = getSpinnerAssetposition(field_answer, spinnerArray);
                spinner.setSelection(pos);
            }


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    selected_item = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if( mUpdateView instanceof TextView ) {
                        TextView textView = (TextView) mUpdateView;
                        textView.setText(selected_item);
                    }

                    callUpdateIndividualFiledService(mOverallQuestion.getQues_id(),
                            mOverallQuestion.getCorresponding_table(), mOverallQuestion.getField_name(),
                            selected_item, false, mOverallQuestion.getEncrypt_flag(), "N", "", postion);
                    alertDialogs.dismiss();

                }
            });


            dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogs.dismiss();
                }
            });


            alertDialogs.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void leftLinearlayoutAnimation(int i, LinearLayout linearLayout) {
        TranslateAnimation anim = new TranslateAnimation(-100f, 0f, 0f, 0f);
        anim.setDuration(500);
        linearLayout.setAnimation(anim);
        linearLayout.setVisibility(View.VISIBLE);
    }

    void bottomLinearlayoutAnimation(int i, LinearLayout linearLayout) {
        TranslateAnimation anim = new TranslateAnimation(0, 0, 100, 0);
        anim.setDuration(500);
        linearLayout.setAnimation(anim);
        linearLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.character_edittext_img:

                String value = character_edittext.getText().toString();
                addMessagetoRight("" + value);
                callUpdateFiledService(overall_questions.getQues_id(), overall_questions.getCorresponding_table(), overall_questions.getField_name(), value, false, overall_questions.getEncrypt_flag(), "N", "");
                layout_character_edittext.setVisibility(View.GONE);
                break;
            case R.id.percentage_edittext_img:

                value = percentage_edittext.getText().toString();
                addMessagetoRight("" + value);
                callUpdateFiledService(overall_questions.getQues_id(), overall_questions.getCorresponding_table(), overall_questions.getField_name(), value, false, overall_questions.getEncrypt_flag(), "N", "");
                layout_percentage_edittext.setVisibility(View.GONE);
                break;
            case R.id.number_edittext_img:

                value = number_edittext.getText().toString();
                addMessagetoRight("" + value);
                callUpdateFiledService(overall_questions.getQues_id(), overall_questions.getCorresponding_table(), overall_questions.getField_name(), value, false, overall_questions.getEncrypt_flag(), "N", "");
                layout_number_edittext.setVisibility(View.GONE);
                break;
            case R.id.currencydefault_edittext_img:

                value = UtileKit.getStringwithoutCurreny(currencydefault_edittext.getText().toString());
                addMessagetoRight("" + UtileKit.currencyConvert(value));
                callUpdateFiledService(overall_questions.getQues_id(), overall_questions.getCorresponding_table(), overall_questions.getField_name(), value, false, overall_questions.getEncrypt_flag(), "N", "");
                layout_currencydefault_edittext.setVisibility(View.GONE);
                break;
            case R.id.no:
                String output1 = jumpQuestionLable("N", overall_questions);
                addMessagetoRight("No");
                if(overall_questions.getInput_type().equalsIgnoreCase("yn_dialog_ans")) {
                    if (output1 != null) {
                        callUpdateFiledService(overall_questions.getQues_id(), overall_questions.getCorresponding_table(), overall_questions.getField_name(), ""+overall_questions.getNo_ques_ans(), true, overall_questions.getEncrypt_flag(), "Y", output1);
                    } else {
                        callUpdateFiledService(overall_questions.getQues_id(), overall_questions.getCorresponding_table(), overall_questions.getField_name(), ""+overall_questions.getNo_ques_ans(), true, overall_questions.getEncrypt_flag(), "N", "");
                    }
                }else {
                    if (output1 != null) {
                        callUpdateFiledService(overall_questions.getQues_id(), overall_questions.getCorresponding_table(), overall_questions.getField_name(), "N", true, overall_questions.getEncrypt_flag(), "Y", output1);
                    } else {
                        callUpdateFiledService(overall_questions.getQues_id(), overall_questions.getCorresponding_table(), overall_questions.getField_name(), "N", true, overall_questions.getEncrypt_flag(), "N", "");
                    }
                }

                layout_yes_no_bottom_bar.setVisibility(View.GONE);
                scrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
                break;
            case R.id.yes:
                String output2 = jumpQuestionLable("Y", overall_questions);
                addMessagetoRight("yes");
                if(overall_questions.getInput_type().equalsIgnoreCase("yn_dialog_ans")) {
                    if (output2 != null) {
                        callUpdateFiledService(overall_questions.getQues_id(), overall_questions.getCorresponding_table(), overall_questions.getField_name(), ""+overall_questions.getYes_ques_ans(), true, overall_questions.getEncrypt_flag(), "Y", output2);
                    } else {
                        callUpdateFiledService(overall_questions.getQues_id(), overall_questions.getCorresponding_table(), overall_questions.getField_name(), ""+overall_questions.getYes_ques_ans(), true, overall_questions.getEncrypt_flag(), "N", "");
                    }
                }else {
                    if (output2 != null) {
                        callUpdateFiledService(overall_questions.getQues_id(), overall_questions.getCorresponding_table(), overall_questions.getField_name(), "Y", true, overall_questions.getEncrypt_flag(), "Y", output2);
                    } else {
                        callUpdateFiledService(overall_questions.getQues_id(), overall_questions.getCorresponding_table(), overall_questions.getField_name(), "Y", true, overall_questions.getEncrypt_flag(), "N", "");
                    }
                }
                layout_yes_no_bottom_bar.setVisibility(View.GONE);

                scrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });

                break;
            case R.id.percentage_edittext:

            case R.id.character_edittext:

            case R.id.number_edittext:

            case R.id.currencydefault_edittext:

                break;

        }
    }

    private String jumpQuestionLable(String answer, Overall_questions questions) {
        Log.e("Sucesss", taxPromptModel.getData().getOverall_questions().toString());
        if (answer.equalsIgnoreCase("Y")) {
            return getJumptoposition(questions.getQues_id(), questions.getYes_ques_id());
        } else {
            return getJumptoposition(questions.getQues_id(), questions.getNo_ques_id());
        }

    }

    private String getJumptoposition(String id, String no_ques_id) {
        int size = taxPromptModel.getData().getOverall_questions().size();
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            for (int i = 0; i < size; i++) {
                if (taxPromptModel.getData().getOverall_questions().get(i).getQues_id().equalsIgnoreCase(id)) {
                    for (int k = i+1 ; k < taxPromptModel.getData().getOverall_questions().size(); k++) {
                        if (!taxPromptModel.getData().getOverall_questions().get(k).getQues_id().equalsIgnoreCase(no_ques_id)) {


                            if (!taxPromptModel.getData().getOverall_questions().get(k).getCorresponding_table() .equalsIgnoreCase("empty") ) {
                                JSONObject item = new JSONObject();
                                item.put("qid", "" + taxPromptModel.getData().getOverall_questions().get(k).getQues_id());
                                item.put("field_name", "" + taxPromptModel.getData().getOverall_questions().get(k).getField_name());
                                item.put("table_name", "" + taxPromptModel.getData().getOverall_questions().get(k).getCorresponding_table());
                                item.put("user_id", "" + UtileKit.getPersistedPurplePathPref("user_id"));
                                array.put(item);
                                Log.e("Sucesss", taxPromptModel.getData().getOverall_questions().get(k).getStatement());
                            }

                        } else {
                            if(array.length()!=0) {
                                json.put("clr_det", array);
                                Log.e("Sucesss", json.toString());
                                return json.toString();
                            }else
                            {
                                return null;
                            }
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void callDeleteTaxPromptStatement(String question_id,String flag_id) {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<DeleteTaxStatemetModel> call = webServiceObj.deleteCredentialTaxFileStatement(UtileKit.getPersistedPurplePathPref("user_id"),
                question_id,flag_id);
        call.enqueue(new Callback<DeleteTaxStatemetModel>() {
            @Override
            public void onResponse(Call<DeleteTaxStatemetModel> call, Response<DeleteTaxStatemetModel> response) {
                UtileKit.dismisssSpinnerDialog();
                DeleteTaxStatemetModel deleteModel = response.body();
                try {
                    if (!deleteModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        UtileKit.alertDialog("message",deleteModel.getData().getMessage(),mContext);
                    }
                    else {
                        parentView.removeAllViews();
                        callGetTaxPromptService();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DeleteTaxStatemetModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();

            }
        });
    }

    public void callUpdateFiledService(String quesId, String corresponding_table, String field_name,
                                       final String answer, final Boolean isYesNoDialog, String encrypt_flag,
                                       String clearflag, String ClearJson) {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TaxPromptAnserModel> call = webServiceObj.callTaxFileCredentialUpdateFieldService(UtileKit.getPersistedPurplePathPref("user_id"),
                corresponding_table, field_name, answer, quesId, encrypt_flag, clearflag, ClearJson);
        call.enqueue(new Callback<TaxPromptAnserModel>() {
            @Override
            public void onResponse(Call<TaxPromptAnserModel> call, Response<TaxPromptAnserModel> response) {
                UtileKit.dismisssSpinnerDialog();
                TaxPromptAnserModel insertModel = response.body();
                try {
                    if (insertModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        taxPromptModel.getData().getOverall_questions().get(positon).setField_answer(insertModel.getData().getInput().getField_value());
                        if (isYesNoDialog) {
                            if (answer.equalsIgnoreCase("N")) {
                                jumptoQuestion(overall_questions.getNo_ques_id());
                            } else {
                                jumptoQuestion(overall_questions.getYes_ques_id());
                            }

                        } else {
                            startDynamicChartView(taxPromptModel, positon + 1);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaxPromptAnserModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();

            }
        });
    }


    public void callUpdateIndividualFiledService(String quesId, String corresponding_table, String field_name,
                                                 final String answer, final Boolean isYesNoDialog,
                                                 String encrypt_flag, String clearflag, String ClearJson,
                                                 final int individial_position) {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TaxPromptAnserModel> call = webServiceObj.callTaxFileCredentialUpdateFieldService(UtileKit.getPersistedPurplePathPref("user_id"),
                corresponding_table, field_name, answer, quesId, encrypt_flag, clearflag, ClearJson);
        call.enqueue(new Callback<TaxPromptAnserModel>() {
            @Override
            public void onResponse(Call<TaxPromptAnserModel> call, Response<TaxPromptAnserModel> response) {
                UtileKit.dismisssSpinnerDialog();
                TaxPromptAnserModel insertModel = response.body();
                try {
                    if (insertModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        taxPromptModel.getData().getOverall_questions().get(individial_position).setField_answer(insertModel.getData().getInput().getField_value());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaxPromptAnserModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();

            }
        });
    }


    private void jumptoQuestion(String ques_id) {
        int size = taxPromptModel.getData().getOverall_questions().size();
        for (int i = 0; i < size; i++) {
            if (taxPromptModel.getData().getOverall_questions().get(i).getQues_id().equalsIgnoreCase(ques_id)) {
                startDynamicChartView(taxPromptModel, i);
                break;
            }
        }
    }

    @Override
    public void updateEditTextValue(String value, String title) {
        try {
            String dateFoemated = "";

            if(!individual_date_boolean){

                if (!mcalenderTabDialog.isVisible()) {
                    dateFoemated = setDateFormat(value);
                    addMessagetoRight("" + value);
                    callUpdateFiledService(overall_questions.getQues_id(), overall_questions.getCorresponding_table(),
                            overall_questions.getField_name(), dateFoemated, false, overall_questions.getEncrypt_flag(), "N", "");
                }}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateIndividualEditTextValue(String value, String title) {
        try {

            String dateFoemated="";
            if (!mcalenderTabDialog.isVisible()) {

                dateFoemated = setDateFormat(value);
                if( mUpdateView instanceof TextView ) {
                    TextView textView = (TextView) mUpdateView;
                    textView.setText(dateFoemated);
                }
            }


            callUpdateIndividualFiledService(date_individual_questions.getQues_id(),
                    date_individual_questions.getCorresponding_table(),
                    date_individual_questions.getField_name(), dateFoemated, false,
                    date_individual_questions.getEncrypt_flag(), "N", "", positon);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String setDateFormat(String date) {
        String output = "";
        try {
            if (date.trim().length() != 0) {
                String dateAray[] = date.split("-");
                output = dateAray[2].concat("-").concat(dateAray[1].concat("-").concat(dateAray[0]));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
        return output;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            try {
                v.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            scrollview.post(new Runnable() {
                @Override
                public void run() {
                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }





    //Need to work for spinner
    private void individualDialogEdit(final int mPositon, final Overall_questions mOverallQuestion,
                                      String answer, String questionType, final String inputType) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialog;

        final LinearLayout dialog_layout_character_edittext,dialog_layout_percentage_edittext,
                dialog_layout_number_edittext,dialog_layout_currencydefault_edittext;

        final TextView heading,txt_question;
        final CharacterEditText dialog_character_edittext;
        final PercentageEditText dialog_percentage_edittext;
        final NumberEditText dialog_number_edittext;
        final CurrencyGhostViewTaxFiling dialog_currencydefault_edittext;
        inflater = LayoutInflater.from(mContext);
        dialogView = inflater.inflate(R.layout.card_dialog_spinner_edit_view, null);


        heading = (TextView) dialogView.findViewById(R.id.txt_heading);
        txt_question = (TextView) dialogView.findViewById(R.id.txt_question);

        heading.setText("Update");
        txt_question.setText(mOverallQuestion.getStatement());

        dialog_layout_character_edittext=(LinearLayout)dialogView.findViewById(R.id.dialog_layout_character_edittext);
        dialog_layout_percentage_edittext=(LinearLayout)dialogView.findViewById(R.id.dialog_layout_percentage_edittext);
        dialog_layout_number_edittext=(LinearLayout)dialogView.findViewById(R.id.dialog_layout_number_edittext);
        dialog_layout_currencydefault_edittext=(LinearLayout)dialogView.findViewById(R.id.dialog_layout_currencydefault_edittext);

        dialog_character_edittext=(CharacterEditText)dialogView.findViewById(R.id.dialog_character_edittext);
        dialog_percentage_edittext=(PercentageEditText)dialogView.findViewById(R.id.dialog_percentage_edittext);
        dialog_number_edittext=(NumberEditText)dialogView.findViewById(R.id.dialog_number_edittext);
        dialog_currencydefault_edittext=(CurrencyGhostViewTaxFiling)dialogView.findViewById(R.id.dialog_currencydefault_edittext);

        dialog_character_edittext.setOnClickListener(this);
        dialog_percentage_edittext.setOnClickListener(this);
        dialog_number_edittext.setOnClickListener(this);
        dialog_currencydefault_edittext.setOnClickListener(this);

        alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setView(dialogView);


        if(inputType.equalsIgnoreCase("character")){
            dialog_layout_character_edittext.setVisibility(View.VISIBLE);
            dialog_character_edittext.setText(""+answer);

        }else if(inputType.equalsIgnoreCase("percentage")){
            dialog_layout_percentage_edittext.setVisibility(View.VISIBLE);
            dialog_percentage_edittext.setText(""+answer);


        }else if(inputType.equalsIgnoreCase("number")){
            dialog_layout_number_edittext.setVisibility(View.VISIBLE);
            dialog_number_edittext.setText(""+answer);


        }else if(inputType.equalsIgnoreCase("currency")){
            dialog_layout_currencydefault_edittext.setVisibility(View.VISIBLE);
            dialog_currencydefault_edittext.setText(""+answer);

        }

        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mUpdateAns="";

                switch (inputType){

                    case "character":
                        mUpdateAns= dialog_character_edittext.getText().toString();
                        break;
                    case "percentage":
                        mUpdateAns = dialog_percentage_edittext.getText().toString();
                        break;
                    case "number":
                        mUpdateAns= dialog_number_edittext.getText().toString();
                        break;
                    case "currency":
                        mUpdateAns= UtileKit.getStringwithoutCurreny(dialog_currencydefault_edittext.getText().toString());
                        break;
                }

                if( mUpdateView instanceof TextView) {
                    TextView textView = (TextView) mUpdateView;
                    textView.setText("" + UtileKit.currencyConvert(mUpdateAns));
                }
                callUpdateIndividualFiledService(mOverallQuestion.getQues_id(), mOverallQuestion.getCorresponding_table(),
                        mOverallQuestion.getField_name(), mUpdateAns, false, mOverallQuestion.getEncrypt_flag(), "N", "",mPositon);


                alertDialog.dismiss();



            }
        });
        dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

}
