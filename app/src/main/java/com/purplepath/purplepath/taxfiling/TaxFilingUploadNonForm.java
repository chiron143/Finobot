package com.purplepath.purplepath.taxfiling;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.AppManagement.Payment.taxfilepayment.TaxFilePayment;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxfiling.getUserStatusModel.UserStatusModel;
import com.purplepath.purplepath.taxfiling.resettaxfiling.TaxFileResetModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finobot.finobot.MyApplication.mFirebaseAnalytics;

/**
 * Created by pravinr on 6/19/18.
 */

public class TaxFilingUploadNonForm extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;


    private String card_amount = "";

    private ScrollView scrollview;

    private LinearLayout layout_non_form16;

    private String planamountSession = "";


    public static TaxFilingUploadNonForm newInstance(String card_amount) {
        TaxFilingUploadNonForm taxFileDeclaration = new TaxFilingUploadNonForm();
        Bundle args = new Bundle();

        if (card_amount != null) {
            args.putSerializable("card_amount", card_amount);
        }
        taxFileDeclaration.setArguments(args);
        return taxFileDeclaration;
    }


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        if (getArguments().containsKey("card_amount")) {
            card_amount = getArguments().getString("card_amount");
        }

        View view = inflater.inflate(R.layout.fragment_taxfiling_uploadnonform, container, false);
        backPressedListener.setActionBarTitle("Income Tax Non-Form");

        scrollview = view.findViewById(R.id.scrollview);
        mleftRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        layout_non_form16 = (LinearLayout) view.findViewById(R.id.layout_non_form16);
        layout_non_form16.setOnClickListener(this);

        callGetTaxfileUserStatus();
        return view;
    }

    private void callGetTaxfileUserStatus() {

        UtileKit.showSpinnerDialog(mContext, false);
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
                        planamountSession = userStatusModel.getData().getPage_visited_array().get(0).getPlan_amount();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UserStatusModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.setCurrentScreen(getActivity(), getString(R.string.analtics_nonform16_screen), getActivity().getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "23");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Tax Module");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Tax");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
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
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                startSettingHomeActivity();
                break;


            case R.id.layout_non_form16:

                if (planamountSession.equalsIgnoreCase("299")) {
                    addFragmenttoStack(TaxFilePayment.newInstance(card_amount));
                } else {
                    additionalPaymentDialog(mContext, planamountSession);
                }


                break;
        }
    }

    private void additionalPaymentDialog(Context context, final String dataPlanAmount) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialog;

        inflater = LayoutInflater.from(context);
        dialogView = inflater.inflate(R.layout.yes_no_settext, null);
        alertDialog = new android.support.v7.app.AlertDialog.Builder(context).create();
        alertDialog.setView(dialogView);
        final TextView textView = dialogView.findViewById(R.id.additional_yes);

        if (dataPlanAmount.equalsIgnoreCase("1")) {

            textView.setText("You have choosen the Zero Plan But This would come under the Value Plan(₹299). If you agree, please click “Yes” to proceed.");
        } else if (dataPlanAmount.equalsIgnoreCase("199")) {

            textView.setText("You have choosen the Basic Plan(₹199) But This would come under the Value Plan(₹299). If you agree, please click “Yes” to proceed.");

        }
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addFragmenttoStack(TaxFilePayment.newInstance(card_amount));

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
