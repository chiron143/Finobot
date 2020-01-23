package com.purplepath.purplepath.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.finobot.finobot.activity.LoginandSignUpActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.purplepath.purplepath.AppManagement.Quiz.nextpageinterpage.NextInterface;
import com.purplepath.purplepath.Notification.Models.PromptsModel;
import com.purplepath.purplepath.ScoreChartAnalysis.ScoreChartActivity;
import com.purplepath.purplepath.adapter.HomeViewPagerAdapter;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assets.model.GetAssetModel;
import com.purplepath.purplepath.assets.model.GetAssetUserData;
import com.purplepath.purplepath.goal.GetGoalsListModel;
import com.purplepath.purplepath.goal.GetGoalsUserData;
import com.purplepath.purplepath.guideView.GuideViewHomepage;
import com.purplepath.purplepath.insurance.model.GetInsuranceInputData;
import com.purplepath.purplepath.insurance.model.GetInsuranceModel;
import com.purplepath.purplepath.marqueeModels.GetAllMarketData;
import com.purplepath.purplepath.model.CardResponse;
import com.purplepath.purplepath.model.homeCardModel.HomeCardsModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Looper.getMainLooper;
import static com.finobot.finobot.activity.HomePageActivity.homeViewPagerPosition;


/**
 * Created by dinesh on 10/05/16.
 */
public class HomePageFragment extends BaseFragment {

    public static int checkGoalsId;
    public static int physicalAssetsize;
    public static int FinancialAssetsize;
    public static int goallistsize;
    public ArrayList<Fragment> mFragments = new ArrayList<Fragment>(),
            promptsFragments = new ArrayList<>(),
            homePageCard0Fragments = new ArrayList<>(),
            homePageCard1Fragments = new ArrayList<>(),
            homePageCard2Fragments = new ArrayList<>(),
            homePageCard4Fragments = new ArrayList<>();
    public ArrayList<GetGoalsUserData> getGoalsUserData = new ArrayList<GetGoalsUserData>();
    //    public static int lifeInsurancesize;
//    public static int generalInsurancesize;
    public NextInterface dismissDialog;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Boolean isSignUpactivity;
    int height;
    int width;
    private ImageView demoHomePageImgView;
    private TextView marque;
    private Context mContext;
    private GetInsuranceModel getInsuranceModel;
    private ArrayList<GetInsuranceInputData> getInsuranceUserData;
    private LinearLayout incomeplanLayout, include_announcement, include_learning;
    private RelativeLayout promptsLayout;
    private ViewPager mViewPager, homePromptsViewPager, homecard0ViewPager, homecard1ViewPager, homecard2ViewPager, homecard4ViewPager;
    private HomeViewPagerAdapter mHomeViewPagerAdapter, homePromptsViewPagerAdapter, homecard0ViewPagerAdapter, homecard1ViewPagerAdapter,
            homecard2ViewPagerAdapter, homecard4ViewPagerAdapter;
    private PageIndicator mIndicator = null, indicatorPrompts = null, indicatorcard0 = null, indicatorcard1 = null,
            indicatorcard2 = null, indicatorcard4 = null;
    private DisplayMetrics displayMetrics;
    private ArrayList<GetAssetUserData> getAssetUserData;
    private GetAssetModel getAssetModel;
    private GetGoalsListModel getGoalsListModel;
    // private AdView mAdView;
    private GetAllMarketData getAllMarketDataModel;
    private OnActivityBackPressedListener mCallBackListener;
    private PromptsModel promptsModel;
    private HomeCardsModel homeCardsModel;
    private CardResponse cardPermission;
    String plan, file;

    public static HomePageFragment newInstance(Boolean isSignUp) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        fragment.isSignUpactivity = isSignUp;
        Log.i("HomePageFragment", "HomePageFragment isSignUpactivity" + fragment.isSignUpactivity);
        fragment.setArguments(args);
        return fragment;

    }

    //live id:Ad unit ID: ca-app-pub-2076188111537488/1179048751
    //dummy id for testing
    //ads:adUnitId="ca-app-pub-3940256099942544/6300978111"
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mCallBackListener = (OnActivityBackPressedListener) (mContext);
        mCallBackListener.setActionBarTitle("Welcome");
        Handler mainHandler = new Handler(getMainLooper());

        //Hiding tax filing 2017-18



        String enable_flag = UtileKit.getPersistedPurplePathPref("enable_flag");
        System.out.println("section :->" + enable_flag);
        //String enable_flag="N";
        Log.d("Planing&&Filing", LoginandSignUpActivity.plan +"::"+LoginandSignUpActivity.file);

        String planes = LoginandSignUpActivity.plan;
        String files = LoginandSignUpActivity.file;
        if (UtileKit.validateObjectValues(enable_flag)) {
            System.out.println("section :-> 1");

            if (enable_flag.equalsIgnoreCase("Y")) {
                System.out.println("section :-> 2");

                if (planes.equals("N")&&files.equals("N")){
                    mFragments.add(new FinancialSituationHomePageFragment());
                }else {
                    mFragments.add(new TaxPlanningHomePageFragment());
                }
                //
            }
        } else {
            System.out.println("section :-> 3");

            if (planes.equals("N")&&files.equals("N")){
                mFragments.add(new FinancialSituationHomePageFragment());
            }else {
                mFragments.add(new TaxPlanningHomePageFragment());
            }
        }
        if (planes.equals("Y")&&files.equals("Y")){
            mFragments.add(new FinancialSituationHomePageFragment());
        }
        mFragments.add(new FinancialPlaningHomePageFragment());
        mFragments.add(new FinancialRatiosHomePageFragment());
        try {
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
//            setNextInterface(this);
            callGetAllMarketDataService();// in oncreate not marque is not displaying every time if i launch any other activity
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        mainHandler.post(myRunnable);
        isSignUpactivity = UtileKit.getPersistedPurplePathBoolPref("isSignUp_demoScreen");
        if (isSignUpactivity) {
            GuideViewHomepage mguideview = new GuideViewHomepage();
            mguideview.show(getFragmentManager(), "Marriage Date");
            UtileKit.persistingPurplePathPref("isSignUp_demoScreen", false);
        }
    }

    public void setNextInterface(NextInterface nextInterface) {
        this.dismissDialog = nextInterface;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_home_page_view, container, false);

        promptsFragments = new ArrayList<>();
        homePageCard1Fragments = new ArrayList<>();
        homePageCard2Fragments = new ArrayList<>();
        homePageCard4Fragments = new ArrayList<>();

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        mViewPager = inflatedView.findViewById(R.id.homeViewPager);//pratheepChanges
        homePromptsViewPager = inflatedView.findViewById(R.id.homePromptsViewPager);
        homecard0ViewPager = inflatedView.findViewById(R.id.homecard0ViewPager);
        homecard1ViewPager = inflatedView.findViewById(R.id.homecard1ViewPager);
        homecard2ViewPager = inflatedView.findViewById(R.id.homecard2ViewPager);
        homecard4ViewPager = inflatedView.findViewById(R.id.homecard4ViewPager);


        indicatorcard0 = inflatedView.findViewById(R.id.indicatorcard0);
        indicatorcard1 = inflatedView.findViewById(R.id.indicatorcard1);
        indicatorcard2 = inflatedView.findViewById(R.id.indicatorcard2);
        indicatorcard4 = inflatedView.findViewById(R.id.indicatorcard4);

        mHomeViewPagerAdapter = new HomeViewPagerAdapter(getChildFragmentManager(), mFragments);
        mViewPager.setAdapter(mHomeViewPagerAdapter);
        mIndicator = inflatedView.findViewById(R.id.indicator);
        mIndicator.setViewPager(mViewPager);

        indicatorPrompts = inflatedView.findViewById(R.id.indicatorPrompts);
        FrameLayout pageIndicaterView = inflatedView.findViewById(R.id.pageIndicaterId);
        displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        pageIndicaterView.getLayoutParams().width = width * 10;
        pageIndicaterView.requestLayout();

//        RelativeLayout.LayoutParams indicatorParams = new RelativeLayout.LayoutParams(width * 10, (int) (width * 10));
//        pageIndicaterView.setLayoutParams(indicatorParams);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width * 1, (int) (height * .50));
        mViewPager.setLayoutParams(layoutParams);
        mViewPager.setCurrentItem(homeViewPagerPosition);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                homeViewPagerPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                homeViewPagerPosition = position;
//
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                if (state == ViewPager.SCROLL_STATE_IDLE) {
//                    int pageCount = mFragments.size();
//
//                    if (homeViewPagerPosition == 0){
//                        mViewPager.setCurrentItem(pageCount-4,false);
//                    } else if (homeViewPagerPosition == pageCount-1){
//                        mViewPager.setCurrentItem(0,false);
//                    }
//                }
//            }
//        });
        mCallBackListener.setActionBarTitle("Welcome");

        marque = inflatedView.findViewById(R.id.marque_scrolling_text);
        promptsLayout = inflatedView.findViewById(R.id.promptsLayout);
        marque.setSelected(true);
        marque.setEnabled(true);
        marque.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        marque.setSingleLine(true);
        demoHomePageImgView = inflatedView.findViewById(R.id.home_page_imageView);
        demoHomePageImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScoreChartActivity fragment = new ScoreChartActivity();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commitAllowingStateLoss();
            }
        });

        callHomeAllCardsService();
        callPromptsWebService();
        // welcome();
        //prompts();
            /*announcements();
            learning();*/
        MobileAds.initialize(getContext(), "ca-app-pub-6375703219723081~4541496738");//OLD id
        // mAdView = (AdView) inflatedView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("9C8370B0C8DB48C8FE85A1C357B4A994").build();

        //  mAdView.loadAd(adRequest);

        String get_paid_type = UtileKit.getPersistedPurplePathPref("paid_type", null);

//        if (get_paid_type != null) {
//
//            if (get_paid_type.equalsIgnoreCase("1")) {
//                mAdView.setVisibility(View.VISIBLE);
//            } else {
//                mAdView.setVisibility(View.GONE);
//            }
//        }


        return inflatedView;
    }

    private void cardViewPermission() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls callObj= ServiceGenerator.createService(WebServiceCalls.class);
        Call<CardResponse> call =callObj.getCardPermission("6");
        call.enqueue(new Callback<CardResponse>() {
            @Override
            public void onResponse(Call<CardResponse> call, Response<CardResponse> response) {
                UtileKit.dismisssSpinnerDialog();

                cardPermission=response.body();
                Log.d("tax_plan_display", cardPermission.getData().getResult().get(0).getTaxPlanDisplay());
                Log.d("tax_file_display", cardPermission.getData().getResult().get(0).getTaxFileDisplay());
                plan = cardPermission.getData().getResult().get(0).getTaxPlanDisplay();
                file = cardPermission.getData().getResult().get(0).getTaxFileDisplay();



            }

            @Override
            public void onFailure(Call<CardResponse> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( getActivity(),t);
            }
        });
    }


    private void callHomeAllCardsService() {
        final String userName = UtileKit.getPersistedPurplePathPref("name_services", null);
        Call<HomeCardsModel> call = ServiceGenerator.createService(WebServiceCalls.class)
                .getHomeAllCardsService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<HomeCardsModel>() {
            @Override
            public void onResponse(Call<HomeCardsModel> call, Response<HomeCardsModel> response) {

                homeCardsModel = response.body();
                if (homeCardsModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {


                    UtileKit.persistingPurplePathPref("enable_flag", homeCardsModel.getData().getCard0().getEnable_flag());


                    if (null != homeCardsModel.getData()) {//welcome card 1st card
                        //Hiding tax filing 2017-18

//                        if (null != homeCardsModel.getData().getCard0()) {//welcome
//                            if (homePageCard0Fragments.size() > 0) homePageCard0Fragments.clear();
//                            homePageCard0Fragments.add(HomePageCard0Fragment.newInstance(homeCardsModel, userName));
//                            homecard0ViewPagerAdapter = new HomeViewPagerAdapter(getChildFragmentManager(),
//                                    homePageCard0Fragments);
//                            homecard0ViewPager.setAdapter(homecard0ViewPagerAdapter);
//                            indicatorcard0.setViewPager(homecard0ViewPager);
//                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width * 1, (int) (height * .6 * .4));
//                            homecard0ViewPager.setLayoutParams(layoutParams);
//                        }


                        if (null != homeCardsModel.getData().getCard1()) {//welcome
                            if (homePageCard1Fragments.size() > 0) homePageCard1Fragments.clear();
                            homePageCard1Fragments.add(HomePageCard1Fragment.newInstance(homeCardsModel, userName));
                            homecard1ViewPagerAdapter = new HomeViewPagerAdapter(getChildFragmentManager(),
                                    homePageCard1Fragments);
                            homecard1ViewPager.setAdapter(homecard1ViewPagerAdapter);
                            indicatorcard1.setViewPager(homecard1ViewPager);
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width * 1, (int) (height * .6 * .4));
                            homecard1ViewPager.setLayoutParams(layoutParams);
                        }
                        if (null != homeCardsModel.getData().getCard2()) {//Announcements
                            if (homePageCard2Fragments.size() > 0) homePageCard2Fragments.clear();
                            homePageCard2Fragments.add(HomePageCard2Fragment.newInstance(0, homeCardsModel));
                            homePageCard2Fragments.add(HomePageCard2Fragment.newInstance(1, homeCardsModel));
                            homePageCard2Fragments.add(HomePageCard2Fragment.newInstance(2, homeCardsModel));
                            homecard2ViewPagerAdapter = new HomeViewPagerAdapter(getChildFragmentManager(),
                                    homePageCard2Fragments);

                            homecard2ViewPager.setAdapter(homecard2ViewPagerAdapter);
                            indicatorcard2.setViewPager(homecard2ViewPager);
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width * 1, (int) (height * .6 * .4));
                            homecard2ViewPager.setLayoutParams(layoutParams);

                        }
                        if (null != homeCardsModel.getData().getCard4()) {//Quotes
                            if (homePageCard4Fragments.size() > 0) homePageCard4Fragments.clear();
                            homePageCard4Fragments.add(HomePageCard4Fragment.newInstance(0, homeCardsModel));
                            homePageCard4Fragments.add(HomePageCard4Fragment.newInstance(1, homeCardsModel));
                            homePageCard4Fragments.add(HomePageCard4Fragment.newInstance(2, homeCardsModel));

                            homecard4ViewPagerAdapter = new HomeViewPagerAdapter(getChildFragmentManager(),
                                    homePageCard4Fragments);

                            homecard4ViewPager.setAdapter(homecard4ViewPagerAdapter);
                            indicatorcard4.setViewPager(homecard4ViewPager);
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width * 1, (int) (height * .6 * .4));
                            homecard4ViewPager.setLayoutParams(layoutParams);
                        }


                     /*   if(null!=homeCardsModel.getData().getCard0()){
                            String enable_flag="";

                            if(homeCardsModel.getData().getCard0().getEnable_flag()!=null) {
                                enable_flag = homeCardsModel.getData().getCard0().getEnable_flag();
                            }

                            if(enable_flag.equalsIgnoreCase("Y")) {
                                mFragments.add(TaxPlanningHomePageFragment.newInstance(homeCardsModel));
                            }
                        }*/


                    }
                }
            }

            @Override
            public void onFailure(Call<HomeCardsModel> call, Throwable t) {

            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public void callGetAllMarketDataService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetAllMarketData> call = webServiceObj.callGetAllMarketDataService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetAllMarketData>() {
            @Override
            public void onResponse(Call<GetAllMarketData> call, Response<GetAllMarketData> response) {
                try {

                    getAllMarketDataModel = response.body();
                    if (getAllMarketDataModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        ArrayList<String> marketStatus = new ArrayList<String>();

                        for (int i = 0; i < getAllMarketDataModel.getData().getMk_data().size(); i++) {
                            marketStatus.add(getAllMarketDataModel.getData().getMk_data().get(i).getName().concat("  ")
                                    .concat(getAllMarketDataModel.getData().getMk_data().get(i).getCurr_value()));

                            Log.i("GetAllMarketDataService", " marketStatus is " + marketStatus.get(i));
                        }

                        StringBuilder builder = new StringBuilder();
                        for (String details : marketStatus) {
                            builder.append("* ".concat(details).concat(" *"));
                        }
                        marque.setText(builder.toString());
                        UtileKit.dismisssSpinnerDialog();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<GetAllMarketData> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });

    }

    private void callPromptsWebService() {

        Call<PromptsModel> call = ServiceGenerator.createService(WebServiceCalls.class).callPromptsService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<PromptsModel>() {
            @Override
            public void onResponse(Call<PromptsModel> call, Response<PromptsModel> response) {
                promptsModel = response.body();
                if (promptsModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    if (promptsFragments.size() > 0) promptsFragments.clear();
                    if (promptsModel.getData().getPersonal_fields() != null
                            && response.body().getData().getFamily_fields() != null) {
                        promptsFragments.add(GenericPromptsHomePageFragment.newInstance(1, promptsModel));
                        promptsFragments.add(GenericPromptsHomePageFragment.newInstance(2, promptsModel));
                        homePromptsViewPagerAdapter = new HomeViewPagerAdapter(getChildFragmentManager(), promptsFragments);
                        homePromptsViewPager.setAdapter(homePromptsViewPagerAdapter);
                        indicatorPrompts.setViewPager(homePromptsViewPager);

                       /* mPromptsRecyclerViewAdapter = new PromptsRecyclerViewAdapter(mContext, promptsModel, alldetails, mFragmentManager);
                        mRecyclerView.setAdapter(mPromptsRecyclerViewAdapter);
                        Log.i("spcheck", "Personal & family details not empty");*/
                    } else if (promptsModel.getData().getPersonal_fields() != null) {
                       /* mPromptsRecyclerViewAdapter = new PromptsRecyclerViewAdapter(mContext, promptsModel,details, mFragmentManager);
                        mRecyclerView.setAdapter(mPromptsRecyclerViewAdapter);*/
                        promptsFragments.add(GenericPromptsHomePageFragment.newInstance(1, promptsModel));
                        //  promptsFragments.add(GenericPromptsHomePageFragment.newInstance(2,promptsModel));
                        homePromptsViewPagerAdapter = new HomeViewPagerAdapter(getChildFragmentManager(), promptsFragments);
                        homePromptsViewPager.setAdapter(homePromptsViewPagerAdapter);
                        indicatorPrompts.setViewPager(homePromptsViewPager);
                        Log.i("spcheck", "Personal details alone is not empty");
                    } else if (promptsModel.getData().getFamily_fields() != null) {
                        promptsFragments.add(GenericPromptsHomePageFragment.newInstance(2, promptsModel));
                        //  promptsFragments.add(GenericPromptsHomePageFragment.newInstance(2,promptsModel));
                        homePromptsViewPagerAdapter = new HomeViewPagerAdapter(getChildFragmentManager(), promptsFragments);
                        homePromptsViewPager.setAdapter(homePromptsViewPagerAdapter);
                        indicatorPrompts.setViewPager(homePromptsViewPager);

                    }
                } else if (response.body().getStatus_code().equals("100")) {
                    //mNoAlertsTextView.setText("You have entered the required details");
                    promptsLayout.setVisibility(View.GONE);
                    /*mNoAlertsTextView.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);*/
//                    UtileKit.showToastShort(mContext, mContext.getString(R.string.noInfoMsg));
                }

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width * 1, (int) (height * .6 * .4));
                homePromptsViewPager.setLayoutParams(layoutParams);
            }

            @Override
            public void onFailure(Call<PromptsModel> call, Throwable t) {
                //UtileKit.alertRetrofitExceptionDialog( mContext,t);
                //prompts();
            }
        });

    }

//    @Override
//    public void onTabAddpostion(int count) {
//        String position = String.valueOf(count);
//        if(position.equalsIgnoreCase("1")){
//            isSignUpactivity= false;
//        }
//    }
   /* public void callGetGoalListService() {
        //UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetGoalsListModel> call = webServiceObj.callGetGoalsListService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetGoalsListModel>() {
            @Override
            public void onResponse(Call<GetGoalsListModel> call, Response<GetGoalsListModel> response) {
                UtileKit.dismisssSpinnerDialog();
                getGoalsListModel = response.body();
                if (getGoalsListModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    getGoalsUserData =     getGoalsListModel.getData().getUser_goals();
                    if (UtileKit.validateObjectValues(getGoalsUserData)) {
                        if (!getGoalsUserData.isEmpty()) {
                            checkGoalsId = 0;
                            goallistsize = getGoalsUserData.size();
                        } else {
                            checkGoalsId = 0;
                            goallistsize = 0;
                        }
                    }
                }else{
                    checkGoalsId = 0;
                    goallistsize =0;
                }
            }

            @Override
            public void onFailure(Call<GetGoalsListModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                goallistsize =0;
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }
    public void callGetAssetService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetAssetModel> call = webServiceObj.callGetAssetService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetAssetModel>() {
            @Override
            public void onResponse(Call<GetAssetModel> call, Response<GetAssetModel> response) {
                UtileKit.dismisssSpinnerDialog();
                getAssetModel = response.body();
                if (getAssetModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    getAssetUserData = getAssetModel.getData().getUser_assets();
                    ArrayList<GetAssetUserData> financialAssetUserData = new ArrayList<GetAssetUserData>();
                    ArrayList<GetAssetUserData> physicalAssetUserData = new ArrayList<GetAssetUserData>();
                    for (int i = 0; i<getAssetUserData.size(); i++) {
                        if (getAssetUserData.get(i).getType().equalsIgnoreCase("Financial")) {
                            financialAssetUserData.add(getAssetUserData.get(i));
                        }else if(getAssetUserData.get(i).getType().equalsIgnoreCase("Physical")){
                            physicalAssetUserData.add(getAssetUserData.get(i));
                        }
                    }
                    if (UtileKit.validateObjectValues(financialAssetUserData)) {
                        if(!financialAssetUserData.isEmpty()){
                            FinancialAssetsize = financialAssetUserData.size();
                        }else{
                            FinancialAssetsize = 0;
                        }

                    }else{
                        FinancialAssetsize = 0;
                    }

                    if (UtileKit.validateObjectValues(physicalAssetUserData)) {
                        if (!physicalAssetUserData.isEmpty()) {
                            physicalAssetsize = physicalAssetUserData.size();

                        } else {
                            physicalAssetsize = 0;
                        }
                    }else{
                        physicalAssetsize = 0;
                    }
                }else{
                    FinancialAssetsize = 0;
                    physicalAssetsize = 0;
                }
            }

            @Override
            public void onFailure(Call<GetAssetModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                FinancialAssetsize = 0;
                physicalAssetsize = 0;
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }

    public void callGetInsuranceService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetInsuranceModel> call = webServiceObj.callGetInsuranceService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetInsuranceModel>() {
            @Override
            public void onResponse(Call<GetInsuranceModel> call, Response<GetInsuranceModel> response) {
                UtileKit.dismisssSpinnerDialog();
                getInsuranceModel = response.body();
                if (getInsuranceModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    ArrayList<GetInsuranceInputData> getGeneralInsuranceUserData = new ArrayList<GetInsuranceInputData>();
                    ArrayList<GetInsuranceInputData> getLifeInsuranceUserData = new ArrayList<GetInsuranceInputData>();
                    getInsuranceUserData =     getInsuranceModel.getData().getUser_insurance();
                    for (int i = 0; i<getInsuranceUserData.size(); i++) {
                        if (getInsuranceUserData.get(i).getIns_type().equalsIgnoreCase("General")) {
                            getGeneralInsuranceUserData.add(getInsuranceUserData.get(i));
                        }else if(getInsuranceUserData.get(i).getIns_type().equalsIgnoreCase("Life")){
                            getLifeInsuranceUserData.add(getInsuranceUserData.get(i));
                        }
                    }
                    if(UtileKit.validateObjectValues(getGeneralInsuranceUserData)&& !getGeneralInsuranceUserData.isEmpty()){
                        generalInsurancesize =   getGeneralInsuranceUserData.size();
                    }else{
                        generalInsurancesize =0;
                    }
                    if(UtileKit.validateObjectValues(getLifeInsuranceUserData)&& !getLifeInsuranceUserData.isEmpty()){
                        lifeInsurancesize =   getLifeInsuranceUserData.size();
                    }else{
                        lifeInsurancesize =0;
                    }

                }else{
                    generalInsurancesize =0;
                    lifeInsurancesize=0;
                }

            }

            @Override
            public void onFailure(Call<GetInsuranceModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                generalInsurancesize =0;
                lifeInsurancesize=0;
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }*/

}