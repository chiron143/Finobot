package com.purplepath.purplepath.taxfiling;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.google.firebase.analytics.FirebaseAnalytics;
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

public class TaxFilingAgreeFragment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;

    private LinearLayout layout_continue;

    private CheckBox terms_checkbox;

    private TextView txt_terms, name;

    private String planamountSession = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        } catch (Exception e) {
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
        inflater.inflate(R.menu.menu_taxfile_reset, menu);
        MenuItem item = menu.findItem(R.id.menu_taxfile_reset);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_taxfile_reset:
                try {

                    resetTaxFilingDialog(mContext);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_taxfiling_agree, container, false);
        backPressedListener.setActionBarTitle("Income Tax Returns");
        mleftRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        layout_continue = (LinearLayout) view.findViewById(R.id.layout_continue);
        layout_continue.setOnClickListener(this);

        terms_checkbox = (CheckBox) view.findViewById(R.id.terms_checkbox);
        terms_checkbox.setOnClickListener(this);

        txt_terms = view.findViewById(R.id.txt_terms);

        ClickableSpan termsOfServicesClick = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                addFragmenttoStack(new TaxFilingTermsAndCondition());
            }
        };
        makeLinks(txt_terms, new String[]{"Terms of Service"}, new ClickableSpan[]{
                termsOfServicesClick
        });

        name = view.findViewById(R.id.name);

        String getprefName = UtileKit.getPersistedPurplePathPref("name_services", null);
        if (getprefName != null) {
            name.setText(getprefName);

        }

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.setCurrentScreen(getActivity(), getString(R.string.analtics_agreement_screen), getActivity().getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "11");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Tax Module");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Tax");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
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
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                startSettingHomeActivity();
                break;

            case R.id.layout_continue:

                String corresponding_table = "users_tax_file_page_visited_status", field_name = "agreement",
                        field_value = "Y";

                AddTaxfileSessionService(corresponding_table, field_name, field_value);

                break;
        }

    }


    private void AddTaxfileSessionService(String corresponding_table, String field_name, String field_value) {

        UtileKit.showSpinnerDialog(mContext, false);
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

                        if (terms_checkbox.isChecked() == true) {
                            addFragmenttoStack(new TaxFilingInitialConversation());
                        } else {
                            UtileKit.intitializeAlertDialog(getString(R.string.terms_checkboxtaxfile), mContext);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AddSessionModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });

    }

    private void resetTaxFilingDialog(Context mContext) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialog;

        inflater = LayoutInflater.from(mContext);
        dialogView = inflater.inflate(R.layout.yes_no_reset, null);
        alertDialog = new android.support.v7.app.AlertDialog.Builder(mContext).create();
        alertDialog.setView(dialogView);
        final TextView txt_heading = dialogView.findViewById(R.id.txt_heading);
        final TextView textView = dialogView.findViewById(R.id.additional_yes);
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

        UtileKit.showSpinnerDialog(mContext, false);
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
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });
    }
}
