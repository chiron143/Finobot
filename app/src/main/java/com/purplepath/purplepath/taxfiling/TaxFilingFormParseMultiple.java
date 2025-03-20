package com.purplepath.purplepath.taxfiling;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CharacterEditText;
import com.purplepath.purplepath.customview.CustomTextView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxfiling.addsessionmodel.AddSessionModel;
import com.purplepath.purplepath.taxfiling.getdeletevalidationform.DeleteValidationFormModel;
import com.purplepath.purplepath.taxfiling.getsuccessparsemodel.ParseFormSuccessModel;
import com.purplepath.purplepath.taxfiling.getvalidateform.TaxValidationModel;
import com.purplepath.purplepath.taxfiling.getvalidateformmultiple.TaxValidationModelMultiple;
import com.purplepath.purplepath.taxfiling.resettaxfiling.TaxFileResetModel;
import com.purplepath.purplepath.taxprompt.gettaxpromptmodel.TaxPromptNewModel;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finobot.finobot.MyApplication.mFirebaseAnalytics;

/**
 * Created by pravinr on 7/5/18.
 */

public class TaxFilingFormParseMultiple extends BaseFragment implements View.OnClickListener,View.OnFocusChangeListener {

    OnActivityBackPressedListener backPressedListener;

    private Context mContext;

    private LinearLayout left_layout_first,left_layout_second;

    private TextView dialog_no, dialog_yes;

    private CustomTextView right_first_yes_no,right_second_yes_no;

    private LinearLayout layout_right_first_yes_no,layout_right_second_yes_no;

    private LinearLayout layout_submit,layout_character_edittext;

    private CardView card_view_submit_layout;

    private LinearLayout layout_yes_no_bottom_bar_tax_creden;

    private CharacterEditText character_edittext;

    private FloatingActionButton character_edittext_img;

    private String dcument_id,password_protect,password="";

    View view;



    public static TaxFilingFormParseMultiple newInstance(String dcument_id) {
        TaxFilingFormParseMultiple taxFilingFormParse = new TaxFilingFormParseMultiple();
        Bundle args = new Bundle();

        if (dcument_id != null) {
            args.putSerializable("dcument_id", dcument_id);
        }
        taxFilingFormParse.setArguments(args);
        return taxFilingFormParse;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try{
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){
            e.printStackTrace();

        }
        try {
            backPressedListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        if(getArguments().containsKey("dcument_id")) {
            dcument_id = getArguments().getString("dcument_id");
        }

        View view=inflater.inflate(R.layout.fragment_taxfiling_parseform, container, false);
        backPressedListener.setActionBarTitle("Income Tax Form16");

        refreshView(view);



        return view;
    }

    private void refreshView(View view) {
        left_layout_first=(LinearLayout)view.findViewById(R.id.left_layout_first);
        left_layout_second=(LinearLayout)view.findViewById(R.id.left_layout_second);

        layout_right_first_yes_no=(LinearLayout)view.findViewById(R.id.layout_right_first_yes_no);
        layout_right_second_yes_no=(LinearLayout)view.findViewById(R.id.layout_right_second_yes_no);
        layout_right_first_yes_no.setOnClickListener(this);
        layout_right_second_yes_no.setOnClickListener(this);


        layout_yes_no_bottom_bar_tax_creden=(LinearLayout)view.findViewById(R.id.layout_yes_no_bottom_bar_tax_creden);

        right_first_yes_no=(CustomTextView)view.findViewById(R.id.right_first_yes_no);
        right_second_yes_no=(CustomTextView)view.findViewById(R.id.right_second_yes_no);

        dialog_no = view.findViewById(R.id.no);
        dialog_yes = view.findViewById(R.id.yes);
        dialog_no.setOnClickListener(this);
        dialog_yes.setOnClickListener(this);

        layout_submit=(LinearLayout)view.findViewById(R.id.layout_submit);
        layout_submit.setOnClickListener(this);

        card_view_submit_layout=(CardView)view.findViewById(R.id.card_view_submit_layout);

        layout_character_edittext=(LinearLayout)view.findViewById(R.id.layout_character_edittext);

        character_edittext=(CharacterEditText)view.findViewById(R.id.character_edittext);
        character_edittext.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

        character_edittext_img=(FloatingActionButton)view.findViewById(R.id.character_edittext_img);
        character_edittext_img.setOnClickListener(this);

        leftLinearlayoutAnimation(left_layout_first);
        layout_yes_no_bottom_bar_tax_creden.setVisibility(View.VISIBLE);

    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.setCurrentScreen(getActivity(),getString(R.string.analtics_taxform16multipleuploadcompleted_screen),getActivity().getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "28");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Tax Module");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Tax");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }

    void leftLinearlayoutAnimation(LinearLayout linearLayout) {
        TranslateAnimation anim = new TranslateAnimation(-100f, 0f, 0f, 0f);
        anim.setDuration(500);
        linearLayout.setAnimation(anim);
        linearLayout.setVisibility(View.VISIBLE);
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
                    left_layout_first.setVisibility(View.VISIBLE);
                    layout_yes_no_bottom_bar_tax_creden.setVisibility(View.VISIBLE);

                    layout_character_edittext.setVisibility(View.GONE);
                    layout_right_first_yes_no.setVisibility(View.GONE);
                    left_layout_second.setVisibility(View.GONE);
                    layout_right_second_yes_no.setVisibility(View.GONE);
                    card_view_submit_layout.setVisibility(View.GONE);
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
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.yes:
                password_protect="Yes";

                layout_right_first_yes_no.setVisibility(View.VISIBLE);
                right_first_yes_no.setText("Yes");

                layout_yes_no_bottom_bar_tax_creden.setVisibility(View.GONE);
                left_layout_second.setVisibility(View.VISIBLE);
                layout_character_edittext.setVisibility(View.VISIBLE);

                break;
            case R.id.no:

                password_protect="No";

                layout_right_first_yes_no.setVisibility(View.VISIBLE);
                right_first_yes_no.setText("No");

                layout_yes_no_bottom_bar_tax_creden.setVisibility(View.GONE);

                card_view_submit_layout.setVisibility(View.VISIBLE);

                break;

            case R.id.character_edittext_img:

                password=character_edittext.getText().toString();


                InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(character_edittext.getWindowToken(), 0);



                layout_character_edittext.setVisibility(View.GONE);

                layout_right_second_yes_no.setVisibility(View.VISIBLE);
                right_second_yes_no.setText(password);

                card_view_submit_layout.setVisibility(View.VISIBLE);

                break;

            case R.id.layout_submit:

                callgetTaxFilingParseSuccess();

                break;
            case R.id.layout_right_first_yes_no:

                individualYesNoFirstDialogRetain(mContext);

                break;
            case R.id.layout_right_second_yes_no:

                individualYesNoSecondDialogRetain(mContext,password);

                break;

        }

    }

    private void individualYesNoFirstDialogRetain(Context context) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialog;

        inflater= LayoutInflater.from(context);
        dialogView=inflater.inflate(R.layout.yes_no_dialogs,null);
        alertDialog=new androidx.appcompat.app.AlertDialog.Builder(context).create();
        alertDialog.setView(dialogView);
        TextView stringErrorMessage = dialogView.findViewById(R.id.textViewDilog);
        stringErrorMessage.setText("Unable to edit Yes/No question. Do you want to redo from this question?");
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // left_layout_second.setVisibility(View.VISIBLE);
                layout_yes_no_bottom_bar_tax_creden.setVisibility(View.VISIBLE);
                card_view_submit_layout.setVisibility(View.GONE);
                layout_character_edittext.setVisibility(View.GONE);
                right_first_yes_no.setText("Yes");

                alertDialog.dismiss();

            }
        });
        dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                left_layout_second.setVisibility(View.GONE);
                layout_character_edittext.setVisibility(View.GONE);
                card_view_submit_layout.setVisibility(View.VISIBLE);
                right_first_yes_no.setText("No");

                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    private void individualYesNoSecondDialogRetain(Context context, final String password_temp) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialog;

        inflater= LayoutInflater.from(context);
        dialogView=inflater.inflate(R.layout.yes_no_characteredit,null);
        alertDialog=new androidx.appcompat.app.AlertDialog.Builder(context).create();
        alertDialog.setView(dialogView);
        final CharacterEditText characterEditText=dialogView.findViewById(R.id.dialog_character_edittext);
        characterEditText.setText(password_temp);
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password=characterEditText.getText().toString();
                right_second_yes_no.setText(password);
                card_view_submit_layout.setVisibility(View.VISIBLE);

                alertDialog.dismiss();

            }
        });
        dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                card_view_submit_layout.setVisibility(View.VISIBLE);

                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void callgetTaxFilingParseSuccess() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TaxValidationModelMultiple> call = webServiceObj.callgetTaxFilingMultipleParseSuccess(UtileKit.getPersistedPurplePathPref("user_id"),
                dcument_id,password_protect,password);
        call.enqueue(new retrofit2.Callback<TaxValidationModelMultiple>() {
            @Override
            public void onResponse(Call<TaxValidationModelMultiple> call, Response<TaxValidationModelMultiple> response) {
                UtileKit.dismisssSpinnerDialog();
                TaxValidationModelMultiple taxValidationModel= response.body();

                try {
                    if (taxValidationModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        uploadValidFile(mContext,taxValidationModel,dcument_id);

                        UtileKit.intitializeAlertDialog(taxValidationModel.getData().getMessage(), mContext);

                    }
                    else if(taxValidationModel.getStatus_code().equals(UtileKit.SUCCESS_OVERRIDE_CODE)){
//                        callgetTaxFilingParseDelete(dcument_id);
//                        Toast.makeText(getActivity(), "Please Upload Valid Document",Toast.LENGTH_LONG).show();

                        Toast.makeText(getActivity(), taxValidationModel.getData().getMessage(),Toast.LENGTH_LONG).show();
                        errorCodeDialog(mContext,taxValidationModel,dcument_id);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Something went wrong Please try again",Toast.LENGTH_LONG).show();
                    addFragmenttoStack(new TaxFilingUploadFileNewMultiple());
                }
            }
            @Override
            public void onFailure(Call<TaxValidationModelMultiple> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }
        private void errorCodeDialog(Context context, TaxValidationModelMultiple taxValidationModel,
                                     final String dcument_id) {
            LayoutInflater inflater;
            final View dialogView;
            final AlertDialog alertDialog;

            inflater= LayoutInflater.from(context);
            dialogView=inflater.inflate(R.layout.error_alert_message,null);
            alertDialog=new androidx.appcompat.app.AlertDialog.Builder(context).create();
            alertDialog.setView(dialogView);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            final TextView errorTitle=dialogView.findViewById(R.id.errorTitle);
            final TextView errorDescription = dialogView.findViewById(R.id.errorDescription);

            final String getErrorCode=taxValidationModel.getData().getError_array().get(0).getError_code();
            final String getErrorTitle=taxValidationModel.getData().getError_array().get(0).getTitle();
            final String getErrorDescription=taxValidationModel.getData().getError_array().get(0).getDescription();

            if(getErrorCode.equalsIgnoreCase("004a")){
                errorTitle.setText(getErrorTitle);
                errorDescription.setText(getErrorDescription);
            }
            if(getErrorCode.equalsIgnoreCase("001x")){
                errorTitle.setText(getErrorTitle);
                errorDescription.setText(getErrorDescription);
            }
            if(getErrorCode.equalsIgnoreCase("002x")){
                errorTitle.setText(getErrorTitle);
                errorDescription.setText(getErrorDescription);
            }
            if(getErrorCode.equalsIgnoreCase("003x")){
                errorTitle.setText(getErrorTitle);
                errorDescription.setText(getErrorDescription);
            }
            if(getErrorCode.equalsIgnoreCase("004x")){
                errorTitle.setText(getErrorTitle);
                errorDescription.setText(getErrorDescription);
            }
            if(getErrorCode.equalsIgnoreCase("005x")){
                errorTitle.setText(getErrorTitle);
                errorDescription.setText(getErrorDescription);
            }
            if(getErrorCode.equalsIgnoreCase("006x")){
                errorTitle.setText(getErrorTitle);
                errorDescription.setText(getErrorDescription);
            }
            if(getErrorCode.equalsIgnoreCase("007x")){
                errorTitle.setText(getErrorTitle);
                errorDescription.setText(getErrorDescription);
            }
            if(getErrorCode.equalsIgnoreCase("008x")){
                errorTitle.setText(getErrorTitle);
                errorDescription.setText(getErrorDescription);
            }




            dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getErrorCode.equalsIgnoreCase("004a")){
                        callgetTaxFilingParseDelete(dcument_id);
                    }
                    if(getErrorCode.equalsIgnoreCase("001x")){
                        callgetTaxFilingParseDelete(dcument_id);
                    }
                    if(getErrorCode.equalsIgnoreCase("002x")){

                    }
                    if(getErrorCode.equalsIgnoreCase("003x")){
                        callgetTaxFilingParseDelete(dcument_id);
                    }
                    if(getErrorCode.equalsIgnoreCase("004x")){
                        callgetTaxFilingParseDelete(dcument_id);
                    }
                    if(getErrorCode.equalsIgnoreCase("005x")){
                        callgetTaxFilingParseDelete(dcument_id);
                    }
                    if(getErrorCode.equalsIgnoreCase("006x")){
                        callgetTaxFilingParseDelete(dcument_id);
                    }
                    if(getErrorCode.equalsIgnoreCase("007x")){
                        callgetTaxFilingParseDelete(dcument_id);
                    }
                    if(getErrorCode.equalsIgnoreCase("008x")){
                        callgetTaxFilingParseDelete(dcument_id);
                    }


                    alertDialog.dismiss();

                }
            });

            alertDialog.show();
        }


    private void uploadValidFile(final Context mContext, final TaxValidationModelMultiple taxValidationModel,
                                 final String dcument_id) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialog;
        final String partA, partB, AssessmentYear;
        final String uploadpartA, uploadpartB, form16_upload;

        inflater = LayoutInflater.from(mContext);
        dialogView = inflater.inflate(R.layout.alert_message_formparse, null);
        alertDialog = new androidx.appcompat.app.AlertDialog.Builder(mContext).create();
        alertDialog.setView(dialogView);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

        partA = taxValidationModel.getData().getResponse_array().getPartA();
        partB = taxValidationModel.getData().getResponse_array().getPartB();
        AssessmentYear = taxValidationModel.getData().getResponse_array().getAssessmentYear();

        uploadpartA = taxValidationModel.getData().getUpload_array().getForm16a_1_upload();
        uploadpartB = taxValidationModel.getData().getUpload_array().getForm16b_1_upload();
        form16_upload = taxValidationModel.getData().getUpload_array().getForm16_1_upload();


        final TextView heading = dialogView.findViewById(R.id.textViewAlert);
        final ImageView success_image=dialogView.findViewById(R.id.success_image);

        if (taxValidationModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

            if (partA.equalsIgnoreCase("true") && partB.equalsIgnoreCase("true")) {

                UtileKit.setSvgImageviewDrawable(success_image,mContext,R.drawable.ic_upload_success);
                heading.setText("We received all your details through Form16 A&B");


            } else if (partA.equalsIgnoreCase("true") && partB.equalsIgnoreCase("false")) {

                heading.setText("We have not receive all your details, Form16 partB is missing. Please upload the same!");

            } else if (partB.equalsIgnoreCase("true") && partA.equalsIgnoreCase("false")) {

                heading.setText("We have not receive all your details, Form16 partA is missing. Please upload the same!");
            }


            dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (taxValidationModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        if (partA.equalsIgnoreCase("true") && partB.equalsIgnoreCase("true")) {
//
                            callTaxFilingParseFormSucess();

                        } else if (partA.equalsIgnoreCase("true") && partB.equalsIgnoreCase("false")) {
//
                            addFragmenttoStack(new TaxFilingUploadFileNewMultiple());

                        } else if (partB.equalsIgnoreCase("true") && partA.equalsIgnoreCase("false")) {

                            addFragmenttoStack(new TaxFilingUploadFileNewMultiple());
                        }
                    }

                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }
    //Success Parse team Response
    private void callTaxFilingParseFormSucess() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<ParseFormSuccessModel> call = webServiceObj.callgetTaxFilingParseFormMultipleSucess(UtileKit.
                getPersistedPurplePathPref("user_id"));
        call.enqueue(new retrofit2.Callback<ParseFormSuccessModel>() {
            @Override
            public void onResponse(Call<ParseFormSuccessModel> call, Response<ParseFormSuccessModel> response) {
                UtileKit.dismisssSpinnerDialog();
                ParseFormSuccessModel parseFormSuccessModel= response.body();

                try {
                    if (parseFormSuccessModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        String corresponding_table="users_tax_file_page_visited_status",
                                field_name="multi_form16_upload",field_value="Y";

                        callTaxAnalysisService();

                        AddTaxfileNonForm16SessionService(corresponding_table,field_name,field_value);

                    }else {
                        UtileKit.intitializeAlertDialog(parseFormSuccessModel.getData().getMessage(), mContext);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ParseFormSuccessModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    //Once we got file upload success in this service has to use for prabhu calculation
    private void callTaxAnalysisService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String finYr = String.valueOf(year-1);
        Call<TaxPromptNewModel> call = webServiceObj.callTaxPromptService(UtileKit.getPersistedPurplePathPref("user_id"), "FY"+finYr);
        call.enqueue(new Callback<TaxPromptNewModel>() {
            @Override
            public void onResponse(Call<TaxPromptNewModel> call, Response<TaxPromptNewModel> response) {
                try {
                    UtileKit.dismisssSpinnerDialog();
                    TaxPromptNewModel taxPromptNewModel = response.body();

                    if (taxPromptNewModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaxPromptNewModel> call, Throwable t) {

                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
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

                        addFragmenttoStack(TaxFileWouldYouConversation.newInstance("",""));

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

    //Delete Parse service
    private void callgetTaxFilingParseDelete(String dcument_id) {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<DeleteValidationFormModel> call = webServiceObj.callgetTaxFilingParseDelete(UtileKit.getPersistedPurplePathPref("user_id"),
                dcument_id);
        call.enqueue(new retrofit2.Callback<DeleteValidationFormModel>() {
            @Override
            public void onResponse(Call<DeleteValidationFormModel> call, Response<DeleteValidationFormModel> response) {
                UtileKit.dismisssSpinnerDialog();
                DeleteValidationFormModel taxValidationModel= response.body();

                try {
                    if (taxValidationModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        addFragmenttoStack(new TaxFilingUploadFileNewMultiple());
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<DeleteValidationFormModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
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
        }
    }

}
