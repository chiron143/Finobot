package com.purplepath.purplepath.networthanalysis;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.github.clans.fab.FloatingActionMenu;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assets.AssetsDetailsFragment;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.liabilities.LiabilitiesTabViewFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.networthanalysis.model.Asst_det;
import com.purplepath.purplepath.networthanalysis.model.Liab_det;
import com.purplepath.purplepath.networthanalysis.model.Net_worth;
import com.purplepath.purplepath.networthanalysis.model.NetworkAnalysisModel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetworthDetailsFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.gold_value)
    TextView gold_value;

    @BindView(R.id.employeeBenifits_value)
    TextView EmployeeBenifits_value;

    @BindView(R.id.equity_value)
    TextView Equity_value;

    @BindView(R.id.fixedIncome_value)
    TextView FixedIncome_value;

    @BindView(R.id.houseHoldAssets_value)
    TextView HouseHoldAssets_value;

    @BindView(R.id.liquidCash_value)
    TextView LiquidCash_value;

    @BindView(R.id.realEstate_value)
    TextView RealEstate_value;

    @BindView(R.id.otherAsset_value)
    TextView otherAsset_value;

    @BindView(R.id.creditCard_value)
    TextView CreditCard_value;

    @BindView(R.id.loanOffers_value)
    TextView LoanOffers_value;

    @BindView(R.id.loan_value)
    TextView Loan_value;

    @BindView(R.id.refundableDeposit_value)
    TextView RefundableDeposit_value;

    @BindView(R.id.unpaidBills_value)
    TextView UnpaidBills_value;

    @BindView(R.id.otherLiablities_value)
    TextView OtherLiablities_value;

    @BindView(R.id.dateText)
    TextView dateText;

    @BindView(R.id.statusText)
    TextView statusText;

    //Relative Layout

    @BindView(R.id.gold_layout)
    RelativeLayout gold_layout;

    @BindView(R.id.employeeBenifits_layout)
    RelativeLayout EmployeeBenifits_layout;

    @BindView(R.id.equity_layout)
    RelativeLayout Equity_layout;

    @BindView(R.id.fixedIncome_layout)
    RelativeLayout FixedIncome_layout;

    @BindView(R.id.houseHoldAssets_layout)
    RelativeLayout HouseHoldAssets_layout;

    @BindView(R.id.liquidCash_layout)
    RelativeLayout LiquidCash_layout;

    @BindView(R.id.realEstate_layout)
    RelativeLayout RealEstate_layout;

    @BindView(R.id.otherAsset_layout)
    RelativeLayout otherAsset_layout;

    @BindView(R.id.creditCard_layout)
    RelativeLayout CreditCard_layout;

    @BindView(R.id.loanOffers_layout)
    RelativeLayout LoanOffers_layout;

    @BindView(R.id.loan_layout)
    RelativeLayout Loan_layout;

    @BindView(R.id.refundableDeposit_layout)
    RelativeLayout RefundableDeposit_layout;

    @BindView(R.id.unpaidBills_layout)
    RelativeLayout UnpaidBills_layout;

    @BindView(R.id.otherLiablities_layout)
    RelativeLayout OtherLiablities_layout;

    NetworkAnalysisModel networkAnalysisModel;
    Net_worth net_worthModelObj;

    FloatingActionMenu materialDesignFAM;

    com.github.clans.fab.FloatingActionButton  mfab_assert, mfab_liability;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    Context mContext;
    OnActivityBackPressedListener backPressedListener;

    public NetworthDetailsFragment() {
        // Required empty public constructor
    }

    public static NetworthDetailsFragment newInstance(NetworkAnalysisModel networkAnalysisModel) {
        NetworthDetailsFragment networthDetailsFragment = new NetworthDetailsFragment();
        Bundle args = new Bundle();
        if (networkAnalysisModel != null) {
            args.putSerializable("networthModel", networkAnalysisModel);
        }
        networthDetailsFragment.setArguments(args);
        return networthDetailsFragment;
    }
    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        mContext=context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_networth_details, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

        materialDesignFAM = view.findViewById(R.id.material_design_android_floating_action_menu);
        mfab_assert = view.findViewById(R.id.fab_assert);
        mfab_liability = view.findViewById(R.id.fab_liability);

        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);


        UtileKit.setSvgButtonDrawableFloatingButton(mfab_assert,mContext,R.drawable.ic_assetsfloat_icon);
        UtileKit.setSvgButtonDrawableFloatingButton(mfab_liability,mContext,R.drawable.ic_liabilityfloat_icon);


        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey("networthModel")) {
                networkAnalysisModel = (NetworkAnalysisModel) args.getSerializable("networthModel");
                Log.i("spcheck", "onCreateView:args called ");
                if (networkAnalysisModel != null && networkAnalysisModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    net_worthModelObj = networkAnalysisModel.getData().getNet_worth();
                    Log.i("spcheck", "onCreateView: " + net_worthModelObj.getAsst_det().getOver_annu_contri());
                    showAssetDetails(networkAnalysisModel);
                }else {
                    statusText.setText(getString(R.string.noInfoTempMsg));
                }
            }

        }
        else {
            callNetworkAnalysisService();
        }

        mfab_assert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try{
                    addFragmenttoStack(new AssetsDetailsFragment());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


        mfab_liability.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    addFragmenttoStack(new LiabilitiesTabViewFragment());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    public void callNetworkAnalysisService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<NetworkAnalysisModel> call = webServiceObj.callNetworkAnalysisService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<NetworkAnalysisModel>() {
            @Override
            public void onResponse(Call<NetworkAnalysisModel> call, Response<NetworkAnalysisModel> response) {
                UtileKit.dismisssSpinnerDialog();
                //Log.e("success", "" + response.body());
                networkAnalysisModel = response.body();
                try {

                    if (networkAnalysisModel != null && networkAnalysisModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                        net_worthModelObj = networkAnalysisModel.getData().getNet_worth();
                        Log.i("spcheck", "onCreateView: " + net_worthModelObj.getAsst_det().getOver_annu_contri());
                        showAssetDetails(networkAnalysisModel);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<NetworkAnalysisModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }

    private void showAssetDetails(NetworkAnalysisModel networkAnalysisModel) {
        Asst_det asst_detObj = networkAnalysisModel.getData().getNet_worth().getAsst_det();
        Liab_det liab_detObj = networkAnalysisModel.getData().getNet_worth().getLiab_det();

        if (UtileKit.validateObjectValuesAndCheckZero(asst_detObj.getComm_gold())) {
            gold_layout.setVisibility(View.VISIBLE);
            gold_value.setText("₹ "+String.valueOf(UtileKit.formatedNumber(Double.parseDouble(asst_detObj.getComm_gold()))));
        } else {
            gold_layout.setVisibility(View.GONE);
        }
        if (UtileKit.validateObjectValuesAndCheckZero(asst_detObj.getEmp_ben())) {
            EmployeeBenifits_layout.setVisibility(View.VISIBLE);
            EmployeeBenifits_value.setText("₹ "+String.valueOf(UtileKit.formatedNumber(Double.parseDouble(networkAnalysisModel.getData().getNet_worth().getAsst_det().getEmp_ben()))));
        } else {
            EmployeeBenifits_layout.setVisibility(View.GONE);
        }
        if (UtileKit.validateObjectValuesAndCheckZero(networkAnalysisModel.getData().getNet_worth().getAsst_det().getEqu())) {
            Equity_layout.setVisibility(View.VISIBLE);
            Equity_value.setText("₹ "+String.valueOf(UtileKit.formatedNumber(Double.parseDouble(networkAnalysisModel.getData().getNet_worth().getAsst_det().getEqu()))));
        } else {
            Equity_layout.setVisibility(View.GONE);
        }
        if (UtileKit.validateObjectValuesAndCheckZero(networkAnalysisModel.getData().getNet_worth().getAsst_det().getFix_inc())) {
            FixedIncome_layout.setVisibility(View.VISIBLE);
            FixedIncome_value.setText("₹ "+String.valueOf(UtileKit.formatedNumber(Double.parseDouble(networkAnalysisModel.getData().getNet_worth().getAsst_det().getFix_inc()))));
        } else {
            FixedIncome_layout.setVisibility(View.GONE);
        }
        if (UtileKit.validateObjectValuesAndCheckZero(networkAnalysisModel.getData().getNet_worth().getAsst_det().getHou_asst())) {
            HouseHoldAssets_layout.setVisibility(View.VISIBLE);
            HouseHoldAssets_value.setText("₹ "+String.valueOf(UtileKit.formatedNumber(Double.parseDouble(networkAnalysisModel.getData().getNet_worth().getAsst_det().getHou_asst()))));
        } else {
            HouseHoldAssets_layout.setVisibility(View.GONE);
        }
        if (UtileKit.validateObjectValuesAndCheckZero(networkAnalysisModel.getData().getNet_worth().getAsst_det().getLiq())) {
            LiquidCash_layout.setVisibility(View.VISIBLE);
            LiquidCash_value.setText("₹ "+String.valueOf(UtileKit.formatedNumber(Double.parseDouble(networkAnalysisModel.getData().getNet_worth().getAsst_det().getLiq()))));
        } else {
            LiquidCash_layout.setVisibility(View.GONE);
        }
        if (UtileKit.validateObjectValuesAndCheckZero(networkAnalysisModel.getData().getNet_worth().getAsst_det().getReal_prop())) {
            RealEstate_layout.setVisibility(View.VISIBLE);
            RealEstate_value.setText("₹ "+String.valueOf(UtileKit.formatedNumber(Double.parseDouble(networkAnalysisModel.getData().getNet_worth().getAsst_det().getReal_prop()))));
        } else {
            RealEstate_layout.setVisibility(View.GONE);
        }
        if (UtileKit.validateObjectValuesAndCheckZero(networkAnalysisModel.getData().getNet_worth().getAsst_det().getOth_asst())) {
            otherAsset_layout.setVisibility(View.VISIBLE);
            otherAsset_value.setText(("₹ "+String.valueOf(UtileKit.formatedNumber(Double.parseDouble(networkAnalysisModel.getData().getNet_worth().getAsst_det().getOth_asst())))));
        } else {
            otherAsset_layout.setVisibility(View.GONE);
        }
        if (UtileKit.validateObjectValuesAndCheckZero(networkAnalysisModel.getData().getNet_worth().getLiab_det().getCcard().getVal())) {
            CreditCard_layout.setVisibility(View.VISIBLE);
            CreditCard_value.setText("₹ "+String.valueOf(UtileKit.formatedNumber(Double.parseDouble(networkAnalysisModel.getData().getNet_worth().getLiab_det().getCcard().getVal()))));
        } else {
            CreditCard_layout.setVisibility(View.GONE);
        }
        if (UtileKit.validateObjectValuesAndCheckZero(networkAnalysisModel.getData().getNet_worth().getLiab_det().getLn_off().getVal())) {
            LoanOffers_layout.setVisibility(View.VISIBLE);
            LoanOffers_value.setText("₹ "+String.valueOf(UtileKit.formatedNumber(Double.parseDouble(networkAnalysisModel.getData().getNet_worth().getLiab_det().getLn_off().getVal()))));
        } else {
            LoanOffers_layout.setVisibility(View.GONE);
        }
        if (UtileKit.validateObjectValuesAndCheckZero(networkAnalysisModel.getData().getNet_worth().getLiab_det().getLn().getVal())) {
            Loan_layout.setVisibility(View.VISIBLE);
            Loan_value.setText("₹ "+String.valueOf(UtileKit.formatedNumber(Double.parseDouble(networkAnalysisModel.getData().getNet_worth().getLiab_det().getLn().getVal()))));
        } else {
            Loan_layout.setVisibility(View.GONE);
        }
        if (UtileKit.validateObjectValuesAndCheckZero(networkAnalysisModel.getData().getNet_worth().getLiab_det().getRf_dep().getVal())) {
            RefundableDeposit_layout.setVisibility(View.VISIBLE);
            RefundableDeposit_value.setText("₹ "+String.valueOf(UtileKit.formatedNumber(Double.parseDouble(networkAnalysisModel.getData().getNet_worth().getLiab_det().getRf_dep().getVal()))));
        } else {
            RefundableDeposit_layout.setVisibility(View.GONE);
        }
        if (UtileKit.validateObjectValuesAndCheckZero(networkAnalysisModel.getData().getNet_worth().getLiab_det().getUnpd_bills().getVal())) {
            UnpaidBills_layout.setVisibility(View.VISIBLE);
            UnpaidBills_value.setText("₹ "+String.valueOf(UtileKit.formatedNumber(Double.parseDouble(networkAnalysisModel.getData().getNet_worth().getLiab_det().getUnpd_bills().getVal()))));
        } else {
            UnpaidBills_layout.setVisibility(View.GONE);
        }
        if (UtileKit.validateObjectValuesAndCheckZero(networkAnalysisModel.getData().getNet_worth().getLiab_det().getOth_liab().getVal())) {
            OtherLiablities_layout.setVisibility(View.VISIBLE);
            OtherLiablities_value.setText("₹ "+String.valueOf(UtileKit.formatedNumber(Double.parseDouble(networkAnalysisModel.getData().getNet_worth().getLiab_det().getOth_liab().getVal()))));
        } else {
            OtherLiablities_layout.setVisibility(View.GONE);
        }

        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Date date=new Date();
        String [] value=dateFormat.format(date).split("/");
        dateText.setText("As on "+value[2]+" "+UtileKit.getMonthString(Integer.parseInt(value[1])-1)+" "+value[0] +"(Asset - Liability)");
        //statusText.setText(networkAnalysisModel.getData().getMessage());


        String assetTotal = networkAnalysisModel.getData().getNet_worth().getAsst_det().getOver_annu_contri();
        String liablilityTotal = networkAnalysisModel.getData().getNet_worth().getLiab_det().getOver_loan_amt();
        int netWorthValue = Integer.parseInt(assetTotal) - Integer.parseInt(liablilityTotal);
        statusText.setText("₹ "+(UtileKit.longvalueabsolute(netWorthValue)));

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_summary_and_chart,menu);
        MenuItem item=menu.findItem(R.id.menu_summary);
        MenuItem items=menu.findItem(R.id.menu_chart);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.menu_chart:
                fragment = new BarChartActivitySinus();
                showFragment(fragment);
                break;
            case R.id.menu_summary:
                NetworthSummaryFragment networthSummaryFragment = new NetworthSummaryFragment();
                showFragment(networthSummaryFragment);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
        }
    }
}
