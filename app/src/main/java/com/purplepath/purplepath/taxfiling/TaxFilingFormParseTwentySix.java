package com.purplepath.purplepath.taxfiling;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
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
import com.purplepath.purplepath.taxfiling.dialogistructioncsreenadapter.TaxFilingFormABadapter;
import com.purplepath.purplepath.taxfiling.freeuserconfirmation.TaxFreeUserPdfModel;
import com.purplepath.purplepath.taxfiling.getUserStatusModel.UserStatusModel;
import com.purplepath.purplepath.taxfiling.getdeletevalidationform.DeleteValidationFormModel;
import com.purplepath.purplepath.taxfiling.getdialogforminstruction.TaxFileFormInstructionModel;
import com.purplepath.purplepath.taxfiling.getplanamtmodel.PlanAmountModel;
import com.purplepath.purplepath.taxfiling.getvalidateparsetwentysix.TwentySixParseValidateModel;
import com.purplepath.purplepath.taxfiling.uploadtaxfiles.TaxFileCheckListFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finobot.finobot.MyApplication.mFirebaseAnalytics;
import static com.finobot.finobot.R.id.Character_InputLayout;
import static com.finobot.finobot.R.id.layout_currencydefault_edittext;
import static com.finobot.finobot.R.id.layout_number_edittext;
import static com.finobot.finobot.R.id.layout_percentage_edittext;
import static com.finobot.finobot.R.id.listview_form16ab;
import static com.finobot.finobot.R.id.number_InputLayout;

/**
 * Created by pravinr on 6/12/18.
 */

public class TaxFilingFormParseTwentySix extends BaseFragment implements View.OnClickListener,View.OnFocusChangeListener {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;

    private String card_amount="";

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

    private String planAmount="",dataPlanAmount="",nonForm16Session="";

    private String add_invest="";



    public static TaxFilingFormParseTwentySix newInstance(String dcument_id) {
        TaxFilingFormParseTwentySix taxFilingFormParse = new TaxFilingFormParseTwentySix();
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

       // Log.d("dcument_idss","dcument_idss"+dcument_id);

        View view=inflater.inflate(R.layout.fragment_taxfiling_parseform_twentysix, container, false);
        backPressedListener.setActionBarTitle("Income Tax Form26AS");

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
        callGetTaxfileUserStatus();
        return view;
    }
    private void callGetTaxfileUserStatus() {

        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<UserStatusModel> call = webServiceObj.callGetTaxfileUserStatus(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<UserStatusModel>() {
            @Override
            public void onResponse(Call<UserStatusModel> call, Response<UserStatusModel> response) {
                UtileKit.dismisssSpinnerDialog();
                UserStatusModel userStatusModel = response.body();
                try {
                    if (userStatusModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        add_invest=userStatusModel.getData().getPage_visited_array().get(0).getAdd_invest();
                        nonForm16Session = userStatusModel.getData().getPage_visited_array().get(0).getNon_form16();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<UserStatusModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.setCurrentScreen(getActivity(),getString(R.string.analtics_taxnonform26uploadcompleted_screen),getActivity().getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "16");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Tax Module");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Tax");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }



    void leftLinearlayoutAnimation(LinearLayout linearLayout) {
        TranslateAnimation anim = new TranslateAnimation(-100f, 0f, 0f, 0f);
        anim.setDuration(500);
        linearLayout.setAnimation(anim);
        linearLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callGetPlanAmountService();
    }
    private void callGetPlanAmountService() {

        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<PlanAmountModel> call = webServiceObj.callGetPlanAmountService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new retrofit2.Callback<PlanAmountModel>() {
            @Override
            public void onResponse(Call<PlanAmountModel> call, Response<PlanAmountModel> response) {
                UtileKit.dismisssSpinnerDialog();
                PlanAmountModel userStatusModel = response.body();
                try {
                    if (userStatusModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        if(userStatusModel.getData().getResponse_array().get(0).getPlan_amount()!=null){
                            planAmount=userStatusModel.getData().getResponse_array().get(0).getPlan_amount();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PlanAmountModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
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
        Call<TwentySixParseValidateModel> call = webServiceObj.callgetTaxFilingParse26AS(UtileKit.getPersistedPurplePathPref("user_id"),
                dcument_id,password_protect,password);
        call.enqueue(new retrofit2.Callback<TwentySixParseValidateModel>() {
            @Override
            public void onResponse(Call<TwentySixParseValidateModel> call, Response<TwentySixParseValidateModel> response) {
                UtileKit.dismisssSpinnerDialog();
                TwentySixParseValidateModel taxValidationModel= response.body();

                try {
                    if (taxValidationModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        uploadValidFile(taxValidationModel,dcument_id);
                        AddTaxfileSessionService("users_tax_file_page_visited_status","form26as","Y");

                    }
                    else if(taxValidationModel.getStatus_code().equals(UtileKit.SUCCESS_OVERRIDE_CODE)){
//                        callgetTaxFilingParseDelete(dcument_id);
//                        Toast.makeText(getActivity(), "Please Upload Valid Document",Toast.LENGTH_LONG).show();
                        uploadValidFile(taxValidationModel,dcument_id);
                        AddTaxfileSessionService("users_tax_file_page_visited_status","form26as","Y");

                    }

                }catch (Exception e){
                    e.printStackTrace();
                    //callgetTaxFilingParseDelete(dcument_id);
                    Toast.makeText(getActivity(), "Please Upload Valid Document",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<TwentySixParseValidateModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void uploadValidFile(TwentySixParseValidateModel taxValidationModel, String dcument_id) {

       String check_Form26=taxValidationModel.getData().getIs_form_26as();

         if(check_Form26.equalsIgnoreCase("true")){

             if(add_invest.equalsIgnoreCase("Y")||nonForm16Session.equalsIgnoreCase("Y")){
                 addFragmenttoStack(new TaxFileCheckListFragment());
             }
             else if(add_invest.equalsIgnoreCase("N")&&planAmount.equalsIgnoreCase("199")){
                 addFragmenttoStack(new TaxFileCheckListFragment());
             }
             else if(planAmount.equalsIgnoreCase("1")){
                 callGetFreeUserPdfService();
             }


         }else {
//no need to 26AS must
             if(add_invest.equalsIgnoreCase("Y")||nonForm16Session.equalsIgnoreCase("Y")){
                 addFragmenttoStack(new TaxFileCheckListFragment());
             }
             else if(add_invest.equalsIgnoreCase("N")&&planAmount.equalsIgnoreCase("199")){
                 addFragmenttoStack(new TaxFileCheckListFragment());
             }
             else if(planAmount.equalsIgnoreCase("1")){
                 callGetFreeUserPdfService();
             }
         }

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


    //Free user PDF generate ITR1 files
    public void callGetFreeUserPdfService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);

        Call<TaxFreeUserPdfModel> call = webServiceObj.callGetFreeUserPdfService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new retrofit2.Callback<TaxFreeUserPdfModel>() {
            @Override
            public void onResponse(Call<TaxFreeUserPdfModel> call, Response<TaxFreeUserPdfModel> response) {
                UtileKit.dismisssSpinnerDialog();
                TaxFreeUserPdfModel getChecklistModel = response.body();
                try {
                    if (getChecklistModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        addFragmenttoStack(new TaxFilingConformationPdf());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaxFreeUserPdfModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
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

                        addFragmenttoStack(new TaxFileTwentySixForm());

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
