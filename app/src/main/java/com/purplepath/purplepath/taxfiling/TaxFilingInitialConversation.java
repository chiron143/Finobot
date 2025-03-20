package com.purplepath.purplepath.taxfiling;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.AppManagement.ContactUs.ContactusFragment;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CharacterEditText;
import com.purplepath.purplepath.customview.CurrencyGhostViewTaxFiling;
import com.purplepath.purplepath.customview.NumberEditText;
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxfiling.addsessionmodel.AddSessionModel;
import com.purplepath.purplepath.taxfiling.dialogtaxfilefragments.TaxFilingInitialConversationDialog;
import com.purplepath.purplepath.taxfiling.resettaxfiling.TaxFileResetModel;
import com.purplepath.purplepath.taxprompt.model.DeleteTaxStatemetModel;
import com.purplepath.purplepath.taxprompt.model.Overall_questions;
import com.purplepath.purplepath.taxprompt.model.TaxPromptAnserModel;
import com.purplepath.purplepath.taxprompt.model.TaxPromptModel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finobot.finobot.MyApplication.mFirebaseAnalytics;


/**
 * Created by pravinr on 6/18/18.
 */

public class TaxFilingInitialConversation extends BaseFragment implements View.OnClickListener,
                                     View.OnFocusChangeListener {

    TaxPromptModel taxPromptModel;

    @BindView(R.id.parentViewId)
    LinearLayout parentView;

    int positon;

    String ques_id = "";

    String corres_table = "";

    @BindView(R.id.layout_yes_no_bottom_bar)
    LinearLayout layout_yes_no_bottom_bar;


    //number edittext
    @BindView(R.id.layout_number_edittext)
    LinearLayout layout_number_edittext;

    @BindView(R.id.number_edittext_img)
    FloatingActionButton number_edittext_img;

    @BindView(R.id.number_edittext)
    NumberEditText number_edittext;

    private Context mContext;

    private TextView txt_left, dialog_no, dialog_yes;

    private ScrollView scrollview;

    private OnActivityBackPressedListener mCallBackListener;
    RelativeLayout mleftRelativeLayout, mcenterRelativeLayout;

    private Overall_questions overall_questions;

    private TextView empty_text;

    View mUpdateView;

    private String card_amount="";

    Boolean dialogShownInitial=false;
    SharedPreferences prefs;



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

//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
//                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|
//                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

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
        inflater.inflate(R.menu.menu_taxfile_undo_reset,menu);
        MenuItem item=menu.findItem(R.id.menu_taxfile);
        MenuItem items=menu.findItem(R.id.menu_taxfile_reset);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {


            case R.id.menu_taxfile:
                try{
                    callDeleteTaxPromptStatement("","");
                    layout_number_edittext.setVisibility(View.GONE);
                    layout_yes_no_bottom_bar.setVisibility(View.GONE);


                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
            case R.id.menu_taxfile_reset:
                try{
                    resetTaxFilingDialog(mContext);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCallBackListener.setActionBarTitle("Initial Conversation");
        View view = inflater.inflate(R.layout.fragment_taxfiling_initialconversation, container, false);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mleftRelativeLayout.setOnClickListener(this);
        mleftRelativeLayout.setOnClickListener(this);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);


        prefs = mContext.getSharedPreferences("useridPref", Context.MODE_PRIVATE);
        dialogShownInitial = prefs.getBoolean("dialogShownInitial", false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      //  mFirebaseAnalytics.setCurrentScreen(getActivity(), getString(R.string.analtics_initialconversation_screen), null /* class override */);

        scrollview = view.findViewById(R.id.scrollview);
        empty_text= view.findViewById(R.id.empty_text);
        dialog_no = view.findViewById(R.id.no);
        dialog_yes = view.findViewById(R.id.yes);
        dialog_no.setOnClickListener(this);
        dialog_yes.setOnClickListener(this);

        number_edittext_img.setOnClickListener(this);
        number_edittext.setOnClickListener(this);


        callGetTaxPromptService();


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

        if(!dialogShownInitial){
            TaxFilingInitialConversationDialog mguideview = new TaxFilingInitialConversationDialog();
            mguideview.show(getFragmentManager(), "GuideView");
            prefs.edit().putBoolean("dialogShownInitial",true).commit();
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.setCurrentScreen(getActivity(),getString(R.string.analtics_initialconversation_screen),getActivity().getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "17");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Tax Module");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Tax");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }




    public void callGetTaxPromptService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TaxPromptModel> call = webServiceObj.callInitialGetTaxFileService(UtileKit.getPersistedPurplePathPref("user_id"));
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

            String statemets="";

            if (type.equalsIgnoreCase("input_text")) {
                if (input_type_flag == null) {

                    addMessagetoLeft(i, ques_id);

                }
                else if (input_type_flag.equalsIgnoreCase("number")) {
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

            }

            else if(type.equalsIgnoreCase("redirect")){

                if(input_type_flag.equalsIgnoreCase("form16")){

                    addSubmitForm16();

                   // addFragmenttoStack(TaxFilingUploadFileNew.newInstance(card_amount));


                }else if(input_type_flag.equalsIgnoreCase("non-form16")){

                    addSubmitNonForm16();
                   // addFragmenttoStack(TaxFilingUploadNonForm.newInstance(card_amount));

                }
            }

            else if(type.equalsIgnoreCase("contact")){
                if(input_type_flag.equalsIgnoreCase("contact")) {

                    addRedirectContactPage(i, ques_id);
                }
            }


            scrollview.post(new Runnable() {
                @Override
                public void run() {
                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
            if ((length - 1) == positon) {

                overall_questions = taxPromptModel.getData().getOverall_questions().get(i);
                addSubmitMessage(overall_questions.getStatement());
                positon = positon + 1;

            }
            if (user_visited_flag.equalsIgnoreCase("N")) {
                break;
            }
        }


    }

    //Free user work flow start
    private void multiForm16(){
        String corresponding_table="users_tax_file_page_visited_status",
                field_name="multi_form16",field_value="Y";

        AddTaxfileNonForm16SessionService(corresponding_table,field_name,field_value);

    }

    //Free user work flow end


    //Non-Form 16 Start
    private void addSubmitNonForm16() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.prompt_submitformcard_view, null);
        final TextView txt_layout = view.findViewById(R.id.title);
        TextView submitTxt = view.findViewById(R.id.questionId);
        CardView card_view=view.findViewById(R.id.card_view);

        txt_layout.setText("Click here to proceed Non-Form16");

        LinearLayout redirect_page = view.findViewById(R.id.redirect_page);
        LinearLayout redoBtn = view.findViewById(R.id.redo_pageId);

        redoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDeleteTaxPromptStatement("","");
                layout_number_edittext.setVisibility(View.GONE);
            }
        });
        redirect_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String corresponding_table="users_tax_file_page_visited_status",
                        field_name="non_form16",field_value="Y";


                AddTaxfileNonForm16SessionService(corresponding_table,field_name,field_value);

            }
        });
        parentView.addView(view);
    }
    private void AddTaxfileNonForm16SessionService(String corresponding_table, String field_name, String field_value) {

        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<AddSessionModel> call = webServiceObj.AddTaxfileSessionService(UtileKit.getPersistedPurplePathPref("user_id")
                ,corresponding_table,field_name,field_value);
        call.enqueue(new Callback<AddSessionModel>() {
            @Override
            public void onResponse(Call<AddSessionModel> call, Response<AddSessionModel> response) {
                UtileKit.dismisssSpinnerDialog();
                AddSessionModel userStatusModel = response.body();
                try {
                    if (userStatusModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        String card_amount ="299";

                        addFragmenttoStack(TaxFilingUploadNonForm.newInstance(card_amount));

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
    //Non-Form 16 end

    //Form 16 Start
    private void addSubmitForm16() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.prompt_submitformcard_view, null);
        final TextView txt_layout = view.findViewById(R.id.title);
        TextView submitTxt = view.findViewById(R.id.questionId);
        CardView card_view=view.findViewById(R.id.card_view);

        LinearLayout redirect_page = view.findViewById(R.id.redirect_page);
        LinearLayout redoBtn = view.findViewById(R.id.redo_pageId);

        redoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDeleteTaxPromptStatement("","");
                layout_number_edittext.setVisibility(View.GONE);
            }
        });
        redirect_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String corresponding_table="users_tax_file_page_visited_status",
                        field_name="form16",field_value="Y";
                AddTaxfileForm16SessionService(corresponding_table,field_name,field_value);
            }
        });
        parentView.addView(view);
    }


    private void AddTaxfileForm16SessionService(String corresponding_table, String field_name, String field_value) {

        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<AddSessionModel> call = webServiceObj.AddTaxfileSessionService(UtileKit.getPersistedPurplePathPref("user_id")
                ,corresponding_table,field_name,field_value);
        call.enqueue(new Callback<AddSessionModel>() {
            @Override
            public void onResponse(Call<AddSessionModel> call, Response<AddSessionModel> response) {
                UtileKit.dismisssSpinnerDialog();
                AddSessionModel userStatusModel = response.body();
                try {
                    if (userStatusModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        addFragmenttoStack(new TaxFilingUploadFileNew());

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
//Form 16 end



    void addRedirectContactPage(int i, String ques_id) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.fragement_taxfile_yesno_contact, null);

        TextView title_contact_page = view.findViewById(R.id.title_contact_page);

        title_contact_page.setText(taxPromptModel.getData().getOverall_questions().get(i).getStatement());


        LinearLayout redirect_home_page=(LinearLayout)view.findViewById(R.id.redirect_home_page);
        LinearLayout redirect_contact_page=(LinearLayout)view.findViewById(R.id.redirect_contact_page);
        redirect_home_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSettingHomeActivity();
            }
        });

        redirect_contact_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragmenttoStack(new ContactusFragment());
            }
        });


        parentView.addView(view);
    }


    private void addSubmitMessage(String msg) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.prompt_submit_card_view, null);
        final TextView txt_layout = view.findViewById(R.id.title);
        TextView submitTxt = view.findViewById(R.id.questionId);
        CardView card_view=view.findViewById(R.id.card_view);
        card_view.setVisibility(View.GONE);
        submitTxt.setText(R.string.submit);
        txt_layout.setText("" + overall_questions.getStatement());
        txt_layout.setId(getViewId());
        LinearLayout redirect_page = view.findViewById(R.id.redirect_page);
        LinearLayout redoBtn = view.findViewById(R.id.redo_pageId);
        redirect_page.setTag(overall_questions);

        redoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDeleteTaxPromptStatement("","");
                layout_number_edittext.setVisibility(View.GONE);
            }
        });
        redirect_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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




    void addMessagetoLeft(int i, String ques_id) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.left_chat_msg_view, null);
        txt_left = view.findViewById(R.id.txt_left);
        LinearLayout layout_anim = view.findViewById(R.id.layout_anim);
        leftLinearlayoutAnimation(i, layout_anim);

        txt_left.setText(taxPromptModel.getData().getOverall_questions().get(i).getStatement());
        parentView.addView(view);
    }

    void addMessagetoRight() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.right_chat_msg_view, null);
        TextView txt_layout = view.findViewById(R.id.txt_right);
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
    private void UpdateFieldByPosition(View view) {
        mUpdateView=view;
        int mPositon=Integer.parseInt(view.getTag().toString());
        Overall_questions mOverallQuestion=taxPromptModel.getData().getOverall_questions().get(mPositon);
        String answer =mOverallQuestion.getField_answer();
        String  QuestionType=mOverallQuestion.getType();
        String inputType=mOverallQuestion.getInput_type();
        String ques_id=mOverallQuestion.getQues_id();


        if(inputType.equalsIgnoreCase("number")){

            individualDialogEdit(mPositon,mOverallQuestion,answer,QuestionType,inputType);

        }
        else if(inputType.equalsIgnoreCase("yn_dialog")){

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
        alertDialog=new androidx.appcompat.app.AlertDialog.Builder(context).create();
        alertDialog.setView(dialogView);
        TextView stringErrorMessage = dialogView.findViewById(R.id.textViewDilog);
        stringErrorMessage.setText(message);
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDeleteTaxPromptStatement(ques_id,mid);

                layout_number_edittext.setVisibility(View.GONE);

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

    private void individualDialogEdit(final int mPositon, final Overall_questions mOverallQuestion,
                                      String answer, String questionType, final String inputType) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialog;

        final LinearLayout dialog_layout_number_edittext;

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

        dialog_layout_number_edittext=(LinearLayout)dialogView.findViewById(R.id.dialog_layout_number_edittext);


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


         if(inputType.equalsIgnoreCase("number")){
            dialog_layout_number_edittext.setVisibility(View.VISIBLE);
            dialog_number_edittext.setText(""+answer);

        }

        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mUpdateAns="";

                switch (inputType){

                    case "number":
                        mUpdateAns= dialog_number_edittext.getText().toString();
                        break;

                }


                callUpdateIndividualFiledService(mOverallQuestion.getQues_id(), mOverallQuestion.getCorresponding_table(),
                        mOverallQuestion.getField_name(), mUpdateAns, false, mOverallQuestion.getEncrypt_flag(), "N", "",mPositon);

                if( mUpdateView instanceof TextView ) {
                    TextView textView = (TextView) mUpdateView;
                    textView.setText("" + mUpdateAns);
                }

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

            case R.id.number_edittext_img:

                String value = number_edittext.getText().toString();
                addMessagetoRight("" + value);
                callUpdateFiledService(overall_questions.getQues_id(), overall_questions.getCorresponding_table(), overall_questions.getField_name(), value, false, overall_questions.getEncrypt_flag(), "N", "");
                layout_number_edittext.setVisibility(View.GONE);

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

            case R.id.relative_left_arrow: {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home: {
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                //getActivity().finish();
            }
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
        Call<DeleteTaxStatemetModel> call = webServiceObj.deleteInitialTaxFileStatement(UtileKit.getPersistedPurplePathPref("user_id")
                ,question_id,flag_id);
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
    public void callUpdateFiledService(String quesId, String corresponding_table, String field_name, final String answer, final Boolean isYesNoDialog, String encrypt_flag, String clearflag, String ClearJson) {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TaxPromptAnserModel> call = webServiceObj.callTaxFileInitialUpdateFieldService(UtileKit.getPersistedPurplePathPref("user_id"),
                corresponding_table, field_name, answer, quesId, encrypt_flag, clearflag, ClearJson);
        call.enqueue(new Callback<TaxPromptAnserModel>() {
            @Override
            public void onResponse(Call<TaxPromptAnserModel> call, Response<TaxPromptAnserModel> response) {
                UtileKit.dismisssSpinnerDialog();
                TaxPromptAnserModel insertModel = response.body();
                try {
                    if (insertModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
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
        Call<TaxPromptAnserModel> call = webServiceObj.callTaxFileInitialUpdateFieldService(UtileKit.getPersistedPurplePathPref("user_id"),
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


    private void resetTaxFilingDialog(Context mContext) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialog;

        inflater= LayoutInflater.from(mContext);
        dialogView=inflater.inflate(R.layout.yes_no_reset,null);
        alertDialog=new androidx.appcompat.app.AlertDialog.Builder(mContext).create();
        alertDialog.setView(dialogView);
        final TextView txt_heading=dialogView.findViewById(R.id.txt_heading);
        final TextView textView=dialogView.findViewById(R.id.additional_yes);
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getResetTaxfilingService();

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

    private void getResetTaxfilingService() {

        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TaxFileResetModel> call = webServiceObj.callGetResetTaxfileService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<TaxFileResetModel>() {
            @Override
            public void onResponse(Call<TaxFileResetModel> call, Response<TaxFileResetModel> response) {
                UtileKit.dismisssSpinnerDialog();
                TaxFileResetModel userStatusModel = response.body();
                try {
                    if (userStatusModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        startSettingHomeActivity();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaxFileResetModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext,t);
            }
        });
    }




}
