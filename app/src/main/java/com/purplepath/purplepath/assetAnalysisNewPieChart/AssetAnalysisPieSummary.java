package com.purplepath.purplepath.assetAnalysisNewPieChart;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assets.AssetsDetailsFragment;
import com.purplepath.purplepath.assetsanalysis.model.AssestAnalysisModel;
import com.purplepath.purplepath.customview.CurrencyTextView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class AssetAnalysisPieSummary extends BaseFragment implements View.OnClickListener {

        OnActivityBackPressedListener backPressedListener;
        private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
        private AssestAnalysisModel mAssetsAnalysisModel;
        Context mContext;
        private long mmasset_total_value =0;
        private TextView errorTextview;
        private CurrencyTextView masset_total_value,mcurrent_allocation_value;
        private FloatingActionButton mEditFloatingBtn;

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
                mContext=getContext();
                setHasOptionsMenu(true);
                View view=inflater.inflate(R.layout.fragment_asset_anaysis_main_page, container, false);
                backPressedListener.setActionBarTitle("Asset Analysis");
                mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
                mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
                mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
                masset_total_value = view.findViewById(R.id.asset_total_value);
                mEditFloatingBtn= view.findViewById(R.id.assets_fab_id);
                mEditFloatingBtn.setOnClickListener(this);
                // mcurrent_allocation_value = (CurrencyTextView) view.findViewById(R.id.current_allocation_value);
                // errorTextview = (TextView) view.findViewById(R.id.empty_chart_display);
                //mRightRelativeLayout.setVisibility(View.GONE);
                mleftRelativeLayout.setOnClickListener(this);
                mcenterRelativeLayout.setOnClickListener(this);
                callAssetsAllocationAnalysisService();
                return view;
        }
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
                menu.clear();
                inflater.inflate(R.menu.menu_detail_and_chart,menu);
                MenuItem item=menu.findItem(R.id.menu_detail);
                MenuItem items=menu.findItem(R.id.menu_chart);
                // item.setVisible(false);
                super.onCreateOptionsMenu(menu, inflater);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                case R.id.menu_detail:
                try{
                 addFragmenttoStack(AssetAnalysisNewPieDetails.newInstance(mAssetsAnalysisModel));

                }
                catch (Exception e)
                {
                e.printStackTrace();
                }
                return true;

        case R.id.menu_chart:

        try{
                addFragmenttoStack(new AssetAnalysisNewPieChart());

//                    mCallBackListener.onActivityBackPressed();
        }catch (Exception e){
        e.printStackTrace();
        }
        return true;
        }
        return super.onOptionsItemSelected(menuItem);
        }


        public void callAssetsAllocationAnalysisService() {
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
                try{
                if(mAssetsAnalysisModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

        String comm_gold=mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getComm_gold();
        String emp_ben=mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getEmp_ben();
        String equ=mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getEqu();
        String fix_in=mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getFix_inc();
        String house_asset=mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getHou_asst();
        String liq=mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getLiq();
        String real=mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getReal_prop();
        String otert=mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getOth_asst();


        if(null != mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det()){

                mmasset_total_value=(Long.parseLong(comm_gold)+
                        Long.parseLong(emp_ben)+
                        Long.parseLong(equ)+
                        Long.parseLong(fix_in)+
                        Long.parseLong(house_asset)+
                        Long.parseLong(liq)+
                        Long.parseLong(real)+
                        Long.parseLong(otert));
                Log.d("hi","mmasset_total_value" + mmasset_total_value);

                Log.d("hi","mmasset_total_value comm_gold" + comm_gold);
                Log.d("hi","mmasset_total_emp_ben" + emp_ben);
                Log.d("hi","mmasset_total_equ" + equ);
                Log.d("hi","mmasset_total_valuefix_in" + fix_in);
                Log.d("hi","mmasset_total_valuehouse_asset" + house_asset);
                Log.d("hi","mmasset_total_liq" + liq);
                Log.d("hi","mmasset_total_real" + real);
                Log.d("hi","mmasset_total_otert" + otert);

                masset_total_value.setText("₹ "+String.valueOf((UtileKit.longvalueabsolute(mmasset_total_value))));
        }

        } else{
                UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
        }
              }  catch (Exception e) {
                e.printStackTrace();
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
//        public void addFragmenttoStack(Fragment mfagment) {
//                if(!mfagment.isVisible()) {
//                        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.fragment_container, mfagment);
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commitAllowingStateLoss();
//                }
//        }
        }
