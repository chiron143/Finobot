package com.purplepath.purplepath.taxfiling;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.AppManagement.ContactUs.ContactusFragment;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxfiling.addsessionmodel.AddSessionModel;
import com.purplepath.purplepath.taxfiling.resettaxfiling.TaxFileResetModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.finobot.finobot.MyApplication.mFirebaseAnalytics;


/**
 * Created by pravinr on 3/15/18.
 */

public class TaxFilingPayment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;

    private LinearLayout itr1_click_first,itr2_click_first;

    private LinearLayout itr2_contactus_page,layout_free_click;

    private TextView incometax_link;

    private LinearLayout layout_viewmore_click_free,layout_viewmore_click_basicplan,
            layout_viewmore_click_valueplan,layout_viewmore_click_comprehensiveplan;

    private LinearLayout layout_freecard_information,layout_basicplan_information,
            layout_valueplan_information,layout_comprehensiveplan_information;

    private FrameLayout free_view_less,basic_view_less,value_view_less,comprehensive_view_less;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_taxfiling_payment, container, false);
        backPressedListener.setActionBarTitle("Income Tax Returns");

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout =  view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout =  view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        itr1_click_first = view.findViewById(R.id.itr1_click_first);
        itr1_click_first.setOnClickListener(this);

        itr2_click_first =  view.findViewById(R.id.itr2_click_first);
        itr2_click_first.setOnClickListener(this);

        itr2_contactus_page= view.findViewById(R.id.itr2_contactus_page);
        itr2_contactus_page.setOnClickListener(this);

        layout_free_click= view.findViewById(R.id.layout_free_click);
        layout_free_click.setOnClickListener(this);

        incometax_link =  view.findViewById(R.id.incometax_link);

        layout_viewmore_click_free= view.findViewById(R.id.layout_viewmore_click_free);
        layout_viewmore_click_basicplan= view.findViewById(R.id.layout_viewmore_click_basicplan);
        layout_viewmore_click_valueplan= view.findViewById(R.id.layout_viewmore_click_valueplan);
        layout_viewmore_click_comprehensiveplan= view.findViewById(R.id.layout_viewmore_click_comprehensiveplan);
        layout_viewmore_click_free.setOnClickListener(this);
        layout_viewmore_click_basicplan.setOnClickListener(this);
        layout_viewmore_click_valueplan.setOnClickListener(this);
        layout_viewmore_click_comprehensiveplan.setOnClickListener(this);
        layout_freecard_information= view.findViewById(R.id.layout_freecard_information);
        layout_basicplan_information= view.findViewById(R.id.layout_basicplan_information);
        layout_valueplan_information= view.findViewById(R.id.layout_valueplan_information);
        layout_comprehensiveplan_information= view.findViewById(R.id.layout_comprehensiveplan_information);

        free_view_less=view.findViewById(R.id.free_view_less);
        basic_view_less=view.findViewById(R.id.basic_view_less);
        value_view_less=view.findViewById(R.id.value_view_less);
        comprehensive_view_less=view.findViewById(R.id.comprehensive_view_less);
        free_view_less.setOnClickListener(this);
        basic_view_less.setOnClickListener(this);
        value_view_less.setOnClickListener(this);
        comprehensive_view_less.setOnClickListener(this);

        ClickableSpan termsOfServicesClick = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.incometaxindiaefiling.gov.in"));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "No application can handle this request."+ " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        };

        makeLinks(incometax_link, new String[] { "https://incometaxindiaefiling.gov.in/" }, new ClickableSpan[] {
                termsOfServicesClick});

        return view;
    }


    public void makeLinks(TextView textView, String[] links, ClickableSpan[] clickableSpans) {
        SpannableString spannableString = new SpannableString(textView.getText());
        for (int i = 0; i < links.length; i++) {
            ClickableSpan clickableSpan = clickableSpans[i];
            String link = links[i];

            int startIndexOfLink = textView.getText().toString().indexOf(link);
            spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spannableString, TextView.BufferType.SPANNABLE);
    }


    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.setCurrentScreen(getActivity(),getString(R.string.analtics_planamount_screen),getActivity().getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "18");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Tax Module");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Tax");
        MyApplication.mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.itr2_contactus_page:

                addFragmenttoStack(new ContactusFragment());

                break;

            case R.id.relative_left_arrow:

                backPressedListener.onActivityBackPressed();

                break;
            case R.id.relative_center_home:

                startSettingHomeActivity();

                break;

            case R.id.itr1_click_first:

                   String corresponding_table="users_tax_file_page_visited_status",
                    field_name="plan_amount",
                    field_value="199";

                AddTaxfileSessionService(corresponding_table,field_name,field_value);


                break;
            case R.id.itr2_click_first:

                corresponding_table="users_tax_file_page_visited_status";
                field_name="plan_amount";
                field_value="299";

                AddTaxfileSessionService(corresponding_table, field_name, field_value);
                Log.d("viswa_value", field_value);

                break;
            case R.id.layout_free_click:
                corresponding_table="users_tax_file_page_visited_status";
                field_name="plan_amount";
                field_value="1";

                AddTaxfileSessionService(corresponding_table, field_name, field_value);

                break;
            case R.id.layout_viewmore_click_free:

                layout_freecard_information.setVisibility(View.VISIBLE);
                layout_viewmore_click_free.setVisibility(View.GONE);
                free_view_less.setVisibility(View.VISIBLE);

                break;
            case R.id.layout_viewmore_click_basicplan:

                layout_basicplan_information.setVisibility(View.VISIBLE);
                layout_viewmore_click_basicplan.setVisibility(View.GONE);
                basic_view_less.setVisibility(View.VISIBLE);
                break;
            case R.id.layout_viewmore_click_valueplan:

                layout_valueplan_information.setVisibility(View.VISIBLE);
                layout_viewmore_click_valueplan.setVisibility(View.GONE);
                value_view_less.setVisibility(View.VISIBLE);

                break;
            case R.id.layout_viewmore_click_comprehensiveplan:

                layout_comprehensiveplan_information.setVisibility(View.VISIBLE);
                layout_viewmore_click_comprehensiveplan.setVisibility(View.GONE);
                comprehensive_view_less.setVisibility(View.VISIBLE);

                break;
            case R.id.free_view_less:

                layout_freecard_information.setVisibility(View.GONE);
                layout_viewmore_click_free.setVisibility(View.VISIBLE);
                free_view_less.setVisibility(View.GONE);

                break;
            case R.id.basic_view_less:
                layout_basicplan_information.setVisibility(View.GONE);
                layout_viewmore_click_basicplan.setVisibility(View.VISIBLE);
                basic_view_less.setVisibility(View.GONE);

                break;
            case R.id.value_view_less:
                layout_valueplan_information.setVisibility(View.GONE);
                layout_viewmore_click_valueplan.setVisibility(View.VISIBLE);
                value_view_less.setVisibility(View.GONE);

                break;
            case R.id.comprehensive_view_less:
                layout_comprehensiveplan_information.setVisibility(View.GONE);
                layout_viewmore_click_comprehensiveplan.setVisibility(View.VISIBLE);
                comprehensive_view_less.setVisibility(View.GONE);
                break;

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

                        addFragmenttoStack(new TaxFilingAgreeFragment());

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
