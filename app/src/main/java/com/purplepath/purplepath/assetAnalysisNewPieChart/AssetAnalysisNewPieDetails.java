package com.purplepath.purplepath.assetAnalysisNewPieChart;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assets.AssetsDetailsFragment;
import com.purplepath.purplepath.assetsanalysis.model.AssestAnalysisModel;
import com.purplepath.purplepath.assetsanalysis.model.Cur_alloc;
import com.purplepath.purplepath.customview.DefaultCurrencyTextView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class AssetAnalysisNewPieDetails extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    AssestAnalysisModel mAssetsAnalysisModel;
    private long mmasset_total_value =0;
    private Context mContext;

    private TextView errorTextview;
    private DefaultCurrencyTextView mCommoditiesgold_value,mEmploymentBenefit_value,mEquity_value,
            mFixedIncomeDebt_value,mHouseholdAsset_value,mLiquid_value,mRealEstateProperty_value,
            mOtherasset_value;
    private TextView mtotal_currencyTextView;
    private TextView msource_expanse;

    String mequityshares_value,mfixincome_value,mliquidity_value;

    private LinearLayout mlayout_commodities,mlayout_employementbenefit,mlayouy_equity,
            mlayout_fixedincomedebt,mlayout_householdasset,mlayout_liquid,mlayout_realestateproperty,
            mlayout_otherasset;
    Cur_alloc cur_alloc_obj;
    private FloatingActionButton mEditFloatingBtn;

    public AssetAnalysisNewPieDetails() {
        // Required empty public constructor
    }
    public static AssetAnalysisNewPieDetails newInstance(AssestAnalysisModel mAssetsAnalysisModel) {
        AssetAnalysisNewPieDetails assertanaysisMainPageFragment = new AssetAnalysisNewPieDetails();
        Bundle args = new Bundle();
        if (mAssetsAnalysisModel != null) {
            args.putSerializable("AssetsAnalysisModel", mAssetsAnalysisModel);
        }
        assertanaysisMainPageFragment.setArguments(args);
        return assertanaysisMainPageFragment;
    }


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backPressedListener= (OnActivityBackPressedListener) getContext();
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        mContext=getContext();
        View view=inflater.inflate(R.layout.fragment_assetanaysisdetail, container, false);
        backPressedListener.setActionBarTitle("Asset Analysis");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

        mCommoditiesgold_value = view.findViewById(R.id.Commoditiesgold_value);
        mEmploymentBenefit_value = view.findViewById(R.id.EmploymentBenefit_value);
        mEquity_value = view.findViewById(R.id.Equity_value);
        mFixedIncomeDebt_value = view.findViewById(R.id.FixedIncomeDebt_value);
        mHouseholdAsset_value = view.findViewById(R.id.HouseholdAsset_value);
        mLiquid_value = view.findViewById(R.id.Liquid_value);
        mRealEstateProperty_value = view.findViewById(R.id.RealEstateProperty_value);
        mOtherasset_value = view.findViewById(R.id.Otherasset_value);
        mtotal_currencyTextView = view.findViewById(R.id.total_currencyTextView);

        msource_expanse= view.findViewById(R.id.source_expanse);



        mlayout_commodities= view.findViewById(R.id.layout_commodities);
        mlayout_employementbenefit= view.findViewById(R.id.layout_employementbenefit);
        mlayouy_equity= view.findViewById(R.id.layouy_equity);
        mlayout_fixedincomedebt= view.findViewById(R.id.layout_fixedincomedebt);
        mlayout_householdasset= view.findViewById(R.id.layout_householdasset);
        mlayout_liquid= view.findViewById(R.id.layout_liquid);
        mlayout_realestateproperty= view.findViewById(R.id.layout_realestateproperty);
        mlayout_otherasset= view.findViewById(R.id.layout_otherasset);
        mEditFloatingBtn= view.findViewById(R.id.assets_fab_id);
        mEditFloatingBtn.setOnClickListener(this);


//        errorTextview = (TextView) view.findViewById(empty_chart_display);

        //mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);




        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey("AssetsAnalysisModel")) {
                mAssetsAnalysisModel = (AssestAnalysisModel) args.getSerializable("AssetsAnalysisModel");
                Log.i("spcheck", "onCreateView:args called ");
                try {


                if (mAssetsAnalysisModel != null && mAssetsAnalysisModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    cur_alloc_obj = mAssetsAnalysisModel.getData().getCur_alloc();

                    showAssetAnalysisDetails(mAssetsAnalysisModel);
                }else {
                    UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                }
            }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        else {
            callAssetsAllocationAnalysisService();

        }

        //
        return view;
    }

    private void callAssetsAllocationAnalysisService() {

        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<AssestAnalysisModel> call = webServiceObj.callAssetsAllocationAnalysisService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<AssestAnalysisModel>() {
            @Override
            public void onResponse(Call<AssestAnalysisModel> call, Response<AssestAnalysisModel> response) {
                UtileKit.dismisssSpinnerDialog();
//                //Log.e("success", "" + response.body());
                mAssetsAnalysisModel = response.body();
                if (mAssetsAnalysisModel != null && mAssetsAnalysisModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                   // try{
                    cur_alloc_obj = mAssetsAnalysisModel.getData().getCur_alloc();

                    showAssetAnalysisDetails(mAssetsAnalysisModel);

                //}
              //  catch (Exception e) {
                //        e.printStackTrace();
                   // }
                    }

            }
            @Override
            public void onFailure(Call<AssestAnalysisModel> call, Throwable t) {
//                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    public void showAssetAnalysisDetails(AssestAnalysisModel mAssetsAnalysisModel){
        if(null != mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det()) {

            String commgold=mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getComm_gold();
            String empben=mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getEmp_ben();
            String equ=mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getEqu();
            String fixin=mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getFix_inc();
            String household=mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getHou_asst();
            String liq=mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getLiq();
            String real=mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getReal_prop();
            String otherasst=mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getOth_asst();

            validate(commgold,mCommoditiesgold_value,mlayout_commodities);
            validate(empben,mEmploymentBenefit_value,mlayout_employementbenefit);
            validate(equ,mEquity_value,mlayouy_equity);
            validate(fixin,mFixedIncomeDebt_value,mlayout_fixedincomedebt);
            validate(household,mHouseholdAsset_value,mlayout_householdasset);
            validate(liq,mLiquid_value,mlayout_liquid);
            validate(real,mRealEstateProperty_value,mlayout_realestateproperty);
            validate(otherasst,mOtherasset_value,mlayout_otherasset);

            mmasset_total_value=(Long.parseLong(commgold)+
                    Long.parseLong(empben)+
                    Long.parseLong(equ)+
                    Long.parseLong(fixin)+
                    Long.parseLong(household)+
                    Long.parseLong(liq)+
                    Long.parseLong(real)+
                    Long.parseLong(otherasst));


//            mmasset_total_value=(Integer.parseInt(commgold)+
//                    Integer.parseInt(empben)+
//                    Integer.parseInt(equ)+
//                    Integer.parseInt(fixin)+
//                    Integer.parseInt(household)+
//                    Integer.parseInt(liq)+
//                    Integer.parseInt(real)+
//                    Integer.parseInt(otherasst));
            Log.d("hi","mmasset_total_value" + mmasset_total_value);
           // mtotal_currencyTextView.setText(Integer.toString(mmasset_total_value));

            mtotal_currencyTextView.setText("₹ "+String.valueOf((UtileKit.longvalueabsolute(mmasset_total_value))));

            DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            Date date=new Date();
            String [] value=dateFormat.format(date).split("/");
            msource_expanse.setText("As on "+value[2]+" "+UtileKit.getMonthString(Integer.parseInt(value[1])-1)+" "+value[0]);
        }

        else {
            UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
        }

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
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_chart:
                try{
                    addFragmenttoStack(new AssetAnalysisNewPieChart());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
            case R.id.menu_summary:
                try{
                    addFragmenttoStack(new AssetAnalysisPieSummary());

                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
//    public void addFragmenttoStack(Fragment mfagment) {
//        if(!mfagment.isVisible()) {
//            android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container, mfagment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commitAllowingStateLoss();
//        }
//    }

    void validate(String mstring,DefaultCurrencyTextView mcurrencyTextView,LinearLayout mlinearlayout){
        if (mstring==null|| mstring.equalsIgnoreCase("0")) {
            mcurrencyTextView.setText("0");
            mlinearlayout.setVisibility(View.GONE);
        }
        else {
            mcurrencyTextView.setText("₹ "+String.valueOf((UtileKit.formatedNumber(Float.valueOf((mstring))))));
           // mcurrencyTextView.setText(mstring);
            mlinearlayout.setVisibility(View.VISIBLE);
        }
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

            case R.id.assets_fab_id:
                addFragmenttoStack(new AssetsDetailsFragment());
                break;

        }

    }
}
