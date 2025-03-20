package com.purplepath.purplepath.networthanalysis;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
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
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assets.AssetsDetailsFragment;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.liabilities.LiabilitiesTabViewFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.networthanalysis.model.NetworkAnalysisModel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetworthSummaryFragment extends BaseFragment implements View.OnClickListener {

     @BindView(R.id.netWorthDateText)
    TextView netWorthDateText;

     @BindView(R.id.netWorthValueText)
    TextView netWorthValueText;

     @BindView(R.id.assetText)
    TextView assetText ;

     @BindView(R.id.asssetValueText)
    TextView asssetValueText;

     @BindView(R.id.liabilityText)
    TextView liabilityText;

     @BindView(R.id.liabilityValueText)
    TextView liabilityValueText;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton  mfab_assert, mfab_liability;



    Context mContext;
    OnActivityBackPressedListener backPressedListener;
    NetworkAnalysisModel networkAnalysisModel;
    public NetworthSummaryFragment() {
        // Required empty public constructor
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
        View view=inflater.inflate(R.layout.fragment_networth_summary, container, false);
        ButterKnife.bind(this,view);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        setHasOptionsMenu(true);

        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);

        backPressedListener.setActionBarTitle("Networth");

        materialDesignFAM = view.findViewById(R.id.material_design_android_floating_action_menu);
        mfab_assert = view.findViewById(R.id.fab_assert);
        mfab_liability = view.findViewById(R.id.fab_liability);

        UtileKit.setSvgButtonDrawableFloatingButton(mfab_assert,mContext,R.drawable.ic_assetsfloat_icon);
        UtileKit.setSvgButtonDrawableFloatingButton(mfab_liability,mContext,R.drawable.ic_liabilityfloat_icon);


        UtileKit.showSpinnerDialog(mContext,false);
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
        callNetworthService();

        return view;
    }

    private void callNetworthService() {
        WebServiceCalls obj= ServiceGenerator.createService(WebServiceCalls.class);
        Call<NetworkAnalysisModel> call= obj.callNetworkAnalysisService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<NetworkAnalysisModel>() {
            @Override
            public void onResponse(Call<NetworkAnalysisModel> call, Response<NetworkAnalysisModel> response) {
                UtileKit.dismisssSpinnerDialog();
                networkAnalysisModel=response.body();
                try{
                if(networkAnalysisModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    setValuesInCardView(networkAnalysisModel);
                }else {
                    setOnFailureText();
                }
            }catch (Exception e) {
                    e.printStackTrace();
                }}

            @Override
            public void onFailure(Call<NetworkAnalysisModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void setOnFailureText() {
    }

    private void setValuesInCardView(NetworkAnalysisModel networkAnalysisModel) {
        try {
           // Date date = new Date();
           // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
           // String[] value = dateFormat.format(date).split("/");

           // String startString = "As on today";
          //  SpannableString str = new SpannableString(startString + "th " +
            //        UtileKit.getMonthString(Integer.parseInt(value[1]) - 1) + " " + value[0] + " My Networth is");

            //SpannableString str = new SpannableString(startString +"My Networth is");
           // str.setSpan(new SuperscriptSpan(), startString.length(), startString.length() + 2, 0);


            netWorthDateText.setText("As on today My Networth");

            String assetTotal = networkAnalysisModel.getData().getNet_worth().getAsst_det().getOver_annu_contri();
            String liablilityTotal = networkAnalysisModel.getData().getNet_worth().getLiab_det().getOver_loan_amt();
            float netWorthValue = Float.parseFloat(assetTotal) - Float.parseFloat(liablilityTotal);


            netWorthValueText.setText("₹ "+UtileKit.longvalueabsolute(netWorthValue));
            assetText.setText("My Assets are valued at ");
            asssetValueText.setText("₹ "+UtileKit.longvalueabsolute(Float.parseFloat(assetTotal)));
            liabilityText.setText("My Liablities are valued at ");
            liabilityValueText.setText("₹ "+UtileKit.longvalueabsolute(Float.parseFloat(liablilityTotal)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void displayRoundedValues(TextView textView, int
            value) {
        long rounded= 0L;
        SpannableStringBuilder str;
        if(value>0&&value<100000){
            if(value!=0) {
                rounded =(long) Math.floor  (value/1000.0);
                str = new SpannableStringBuilder("₹ " + rounded + " k");
                //  str.setSpan(new StyleSpan(Typeface.BOLD),0,(rounded+"").length()+2, 0);
                str.setSpan(new RelativeSizeSpan(1.75f), 0, (rounded + "").length() + 2, 0);
                textView.setText(str);
            }
        }   else if(value>=100000&&value<10000000){
            rounded= (long) Math.floor (value/100000.0);

            str=new SpannableStringBuilder("₹ "+rounded+" Lac");
            //str.setSpan(new StyleSpan(Typeface.BOLD),0,(rounded+"").length()+2,0);
            str.setSpan(new RelativeSizeSpan(1.75f),0,(rounded+"").length()+2,0);
            textView.setText(str);
        }   else if(value>=10000000){
            rounded=(long) Math.floor (value/10000000.0);
            str=new SpannableStringBuilder("₹ "+rounded+" Cr");
           // str.setSpan(new StyleSpan(Typeface.BOLD),0,(rounded+"").length()+2,0);
            str.setSpan(new RelativeSizeSpan(1.75f),0,(rounded+"").length()+2,0);
            textView.setText(str);
        } else if(value<0&&value>-100000){
                rounded =(long) Math.floor (value / 1000.0);
                str = new SpannableStringBuilder("₹ " + rounded + " k");
                //  str.setSpan(new StyleSpan(Typeface.BOLD),0,(rounded+"").length()+2, 0);
                str.setSpan(new RelativeSizeSpan(1.75f), 0, (rounded + "").length() + 2, 0);
                textView.setText(str);
        } else if(value<-100000&&value>-10000000){
            rounded=(long) Math.floor (value/100000.0);
            str=new SpannableStringBuilder("₹ "+rounded+" Lac");
            //str.setSpan(new StyleSpan(Typeface.BOLD),0,(rounded+"").length()+2,0);
            str.setSpan(new RelativeSizeSpan(1.75f),0,(rounded+"").length()+2,0);
            textView.setText(str);
        } else if(value<-10000000) {
            rounded = (long) Math.floor (value / 10000000.0);
            str = new SpannableStringBuilder("₹ " + rounded + " Cr");
            // str.setSpan(new StyleSpan(Typeface.BOLD),0,(rounded+"").length()+2,0);
            str.setSpan(new RelativeSizeSpan(1.75f), 0, (rounded + "").length() + 2, 0);
            textView.setText(str);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.networth_summary_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()){

            case R.id.menu_details:
                fragment=NetworthDetailsFragment.newInstance(networkAnalysisModel);

                addToActivity(fragment);
                break;
            case R.id.menu_graph:
                fragment=new BarChartActivitySinus();
                addToActivity(fragment);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void addToActivity(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commitAllowingStateLoss();

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
