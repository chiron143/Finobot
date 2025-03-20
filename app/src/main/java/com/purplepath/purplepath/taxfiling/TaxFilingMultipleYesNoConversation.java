package com.purplepath.purplepath.taxfiling;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CustomTextView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxfiling.addsessionmodel.AddSessionModel;
import com.purplepath.purplepath.taxfiling.resettaxfiling.TaxFileResetModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pravinr on 7/8/18.
 */

public class TaxFilingMultipleYesNoConversation extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;

    private Context mContext;

    private LinearLayout left_layout_first;

    private LinearLayout layout_right_first_yes_no;

    private TextView dialog_no, dialog_yes;

    private CustomTextView right_first_yes_no;

    private LinearLayout layout_submit,layout_yes_no_bottom_bar;

    private CardView card_view_submit_layout;

    private String additionalInvestment="";

    String paymentScreenSession="",add_invest="";

    public static TaxFilingMultipleYesNoConversation newInstance( String paymentScreenSession, String add_invest) {
        TaxFilingMultipleYesNoConversation taxFileChartConversation = new TaxFilingMultipleYesNoConversation();
        Bundle args = new Bundle();

        if (paymentScreenSession != null) {
            args.putSerializable("paymentScreenSession", paymentScreenSession);
        }
        if (add_invest != null) {
            args.putSerializable("add_invest", add_invest);
        }
        taxFileChartConversation.setArguments(args);
        return taxFileChartConversation;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
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

        View view=inflater.inflate(R.layout.fragment_taxfiling_multipleforms, container, false);
        backPressedListener.setActionBarTitle("Multiple Form16");


        if(getArguments().containsKey("paymentScreenSession")) {
            paymentScreenSession = getArguments().getString("paymentScreenSession");
        }

        if(getArguments().containsKey("add_invest")) {
            add_invest = getArguments().getString("add_invest");
        }
        left_layout_first=(LinearLayout)view.findViewById(R.id.left_layout_first);
        layout_right_first_yes_no=(LinearLayout)view.findViewById(R.id.layout_right_first_yes_no);


        dialog_no = view.findViewById(R.id.no);
        dialog_yes = view.findViewById(R.id.yes);
        dialog_no.setOnClickListener(this);
        dialog_yes.setOnClickListener(this);


        layout_yes_no_bottom_bar=(LinearLayout)view.findViewById(R.id.layout_yes_no_bottom_bar);
        right_first_yes_no=(CustomTextView)view.findViewById(R.id.right_first_yes_no);

        card_view_submit_layout=(CardView)view.findViewById(R.id.card_view_submit_layout);
        layout_submit=(LinearLayout)view.findViewById(R.id.layout_submit);
        layout_submit.setOnClickListener(this);

        leftLinearlayoutAnimation(left_layout_first);
        layout_yes_no_bottom_bar.setVisibility(View.VISIBLE);


        return view;
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
        inflater.inflate(R.menu.menu_taxfile_reset,menu);
        MenuItem item=menu.findItem(R.id.menu_taxfile_reset);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
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

                additionalInvestment="Yes";
                layout_right_first_yes_no.setVisibility(View.VISIBLE);
                right_first_yes_no.setText("Yes");
                layout_yes_no_bottom_bar.setVisibility(View.GONE);

                AddTaxfileSessionService("users_tax_file_page_visited_status","multi_form16","Y");

                break;
            case R.id.no:

                additionalInvestment="No";
                layout_right_first_yes_no.setVisibility(View.VISIBLE);
                right_first_yes_no.setText("No");
                layout_yes_no_bottom_bar.setVisibility(View.GONE);

                addFragmenttoStack(TaxFileWouldYouConversation.newInstance(paymentScreenSession,add_invest));

                break;


            case R.id.layout_submit:


                break;

        }
    }

    private void AddTaxfileSessionService(String corresponding_table, String field_name,String field_value) {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<AddSessionModel> call = webServiceObj.AddTaxfileSessionService(UtileKit.getPersistedPurplePathPref("user_id")
                , corresponding_table, field_name, field_value);
        call.enqueue(new Callback<AddSessionModel>() {
            @Override
            public void onResponse(Call<AddSessionModel> call, Response<AddSessionModel> response) {
                UtileKit.dismisssSpinnerDialog();
                AddSessionModel userStatusModel = response.body();
                try {
                    if (userStatusModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        if(additionalInvestment.equalsIgnoreCase("Yes")){
                            addFragmenttoStack(new TaxFilingUploadFileNewMultiple());
                        }

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

