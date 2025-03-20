//package com.purplepath.purplepath.recommendation;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//import com.finobot.finobot.R;
//import com.finobot.finobot.activity.HomePageActivity;
//import com.purplepath.purplepath.apputiles.UtileKit;
//import com.purplepath.purplepath.fragments.BaseFragment;
//import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
//import com.purplepath.purplepath.recommendation.getcashmodel.Cashmodel;
//import com.purplepath.purplepath.recommendation.getgoalmodel.Goalmodel;
//import com.purplepath.purplepath.recommendation.getnetworthmodel.Networthmodel;
//import com.purplepath.purplepath.recommendation.getsaveinvestmodel.Saveinvestmodel;
//import com.purplepath.purplepath.recommendation.model.RecommendData;
//import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
//import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
//import com.purplepath.purplepath.riskAssesment.RiskProfile;
//import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.TaxCashFlowModel;
//
//import java.io.IOException;
//
//import retrofit2.Call;
//import retrofit2.Response;
//import rx.Observable;
//import rx.Subscriber;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.functions.Func0;
//import rx.schedulers.Schedulers;
//
//import static com.purplepath.purplepath.recommendation.model.RecommendationInfo.createRecommendationInfo;
//
///**
// * @author Praveen Kumar on 02/02/17.
// */
//
//public class RecommendationFragment extends BaseFragment implements View.OnClickListener  {
//
//    public static Fragment newInstance() {
//        return new RecommendationFragment();
//    }
//    private Context mContext;
//    private View viewContainer, progressView;
//    public static RecommendationItemView insuranceItemView, emergencyItemView,
//            mnetworth_plan_id,mcashmanagement_plan_id,mgoals_plan_id,mretirement_plan_id,
//            mtaxation_plan_id,msaving_and_invesment_plan_id,health_insurance_plan_id,
//            auto_insurance_plan_id,property_insurance_plan_id;
//    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
//    private OnActivityBackPressedListener mCallBackListener;
//    public static ScrollView scrollview;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mContext = getContext();
//        try {
//            setHasOptionsMenu(true);
//            mCallBackListener = (OnActivityBackPressedListener) (mContext);
//        }catch(ClassCastException e)
//        {
//            e.printStackTrace();
//        }
//        catch(Exception e)
//        {}
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_recommendation_screen, container, false);
//    }
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        viewContainer = view.findViewById(R.id.view_container);
//        progressView = view.findViewById(R.id.progressBar);
//        mCallBackListener.setActionBarTitle("My Action Plan");
//
//
//        insuranceItemView = (RecommendationItemView) view.findViewById(R.id.insurance_plan_id);
//        insuranceItemView.setViewType(RecommendationItemView.ViewType.INSURANCE_PLAN);
//
//        emergencyItemView = (RecommendationItemView) view.findViewById(R.id.emerrgency_plan_id);
//        emergencyItemView.setViewType(RecommendationItemView.ViewType.EMERGENCY_FUND_PLAN);
//
//        mnetworth_plan_id= (RecommendationItemView) view.findViewById(R.id.networth_plan_id);
//        mnetworth_plan_id.setViewType(RecommendationItemView.ViewType.NETWORTH_PLAN);
//
//        mcashmanagement_plan_id= (RecommendationItemView) view.findViewById(R.id.cashmanagement_plan_id);
//        mcashmanagement_plan_id.setViewType(RecommendationItemView.ViewType.CASH_MANAGEMENT);
//
//        mgoals_plan_id= (RecommendationItemView) view.findViewById(R.id.goals_plan_id);
//        mgoals_plan_id.setViewType(RecommendationItemView.ViewType.GOALS);
//
////        mretirement_plan_id= (RecommendationItemView) view.findViewById(R.id.retirement_plan_id);
//        mtaxation_plan_id= (RecommendationItemView) view.findViewById(R.id.taxation_plan_id);
//        mtaxation_plan_id.setViewType(RecommendationItemView.ViewType.TAXATION_PLAN);
//
//        msaving_and_invesment_plan_id=(RecommendationItemView)view.findViewById(R.id.saving_and_invesment_plan_id);
//        msaving_and_invesment_plan_id.setViewType(RecommendationItemView.ViewType.SAVING_AND_INVESTMENT);
//
//
//        health_insurance_plan_id= (RecommendationItemView) view.findViewById(R.id.health_insurance_plan_id);
//        health_insurance_plan_id.setViewType(RecommendationItemView.ViewType.HEALTH_INSURANCE);
//
//        auto_insurance_plan_id= (RecommendationItemView) view.findViewById(R.id.auto_insurance_plan_id);
//        auto_insurance_plan_id.setViewType(RecommendationItemView.ViewType.AUTO_INSURANCE);
//
//        property_insurance_plan_id= (RecommendationItemView) view.findViewById(R.id.property_insurance_plan_id);
//        property_insurance_plan_id.setViewType(RecommendationItemView.ViewType.PROPERTY_INSURANCE);
//
//        scrollview=(ScrollView)view.findViewById(R.id.scrollview);
//
//        mleftRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_left_arrow);
//        mcenterRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_center_home);
//        mRightRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_right_arrow);
//        mleftRelativeLayout.setOnClickListener(this);
//        mcenterRelativeLayout.setOnClickListener(this);
//        mRightRelativeLayout.setOnClickListener(this);
//    }
//
//    private void showProgress() {
//        viewContainer.setVisibility(View.GONE);
//        progressView.setVisibility(View.VISIBLE);
//    }
//
//    private void hideProgress() {
//        viewContainer.setVisibility(View.VISIBLE);
//        progressView.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        showProgress();
//
//        fetchRecommendationInfo()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<RecommendData>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        hideProgress();
//                    }
//                    @Override
//                    public void onNext(RecommendData recommendData) {
//                        if (!isUnsubscribed()) unsubscribe();
//                        hideProgress();
//                        if(UtileKit.getPersistedPurplePathBoolPref("Life Insurance Plan")) {
//                            insuranceItemView.updateRecommendationView(createRecommendationInfo(R.drawable.ic_life_lnsurances,
//                                    R.string.insurance_plan,
//                                    R.drawable.ic_add_icon));
//                        }
//                        else {
//                            insuranceItemView.updateRecommendationView(createRecommendationInfo(R.drawable.ic_life_lnsurances_tint,
//                                    R.string.insurance_plan,
//                                    R.drawable.ic_add_icon_tint));
//                           // UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
//                        }
//                        if(UtileKit.getPersistedPurplePathBoolPref("Emergency Fund Plan")) {
//                            emergencyItemView.updateRecommendationView(createRecommendationInfo(R.drawable.ic_emergency_fund_plan,
//                                    R.string.emergency_plan,
//                                    R.drawable.ic_add_icon));
//                        }else {
//                            emergencyItemView.updateRecommendationView(createRecommendationInfo(R.drawable.ic_emergency_fund_plan_tint,
//                                    R.string.emergency_plan,
//                                    R.drawable.ic_add_icon_tint));
//                        }
//                        if(UtileKit.getPersistedPurplePathBoolPref("Health Insurance Plan")) {
//                            health_insurance_plan_id.updateRecommendationView(createRecommendationInfo(R.drawable.ic_health_lnsurance_recommend,
//                                    R.string.Healthinsurance_plan,
//                                    R.drawable.ic_add_icon));
//                        }else {
//                            health_insurance_plan_id.updateRecommendationView(createRecommendationInfo(R.drawable.ic_health_lnsurance_recommend_tint                                          ,
//                                    R.string.Healthinsurance_plan,
//                                    R.drawable.ic_add_icon_tint));
//                        }
//                        if(UtileKit.getPersistedPurplePathBoolPref("Motor Insurance Plan")) {
//                            auto_insurance_plan_id.updateRecommendationView(createRecommendationInfo(R.drawable.ic_automobile_lnsurance,
//                                    R.string.Autoinsurance_plan,
//                                    R.drawable.ic_add_icon));
//                        }else {
//                            auto_insurance_plan_id.updateRecommendationView(createRecommendationInfo(R.drawable.ic_automobile_lnsurance_tint,
//                                    R.string.Autoinsurance_plan,
//                                    R.drawable.ic_add_icon_tint));
//                        }
//                        if(UtileKit.getPersistedPurplePathBoolPref("Property Insurance Plan")) {
//                        property_insurance_plan_id.updateRecommendationView(createRecommendationInfo(R.drawable.ic_property_lnsurance,
//                                R.string.Propertyinsurance_plan,
//                                R.drawable.ic_add_icon));
//                        }else {
//                            property_insurance_plan_id.updateRecommendationView(createRecommendationInfo(R.drawable.ic_property_lnsurance_tint,
//                                    R.string.Propertyinsurance_plan,
//                                    R.drawable.ic_add_icon_tint));
//                        }
//                    }
//                });
//
//        fetchNetworthRecommendationInfo()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Networthmodel>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        hideProgress();
//                    }
//                    @Override
//                    public void onNext(Networthmodel networthmodel) {
//                        if (!isUnsubscribed()) unsubscribe();
//                        hideProgress();
//                        if(UtileKit.getPersistedPurplePathBoolPref("Networth")) {
//                            mnetworth_plan_id.updateRecommendationView(createRecommendationInfo(R.drawable.ic_networth,
//                                    R.string.Networth_plan,
//                                    R.drawable.ic_add_icon));
//                        }else {
//                            mnetworth_plan_id.updateRecommendationView(createRecommendationInfo(R.drawable.ic_networth_tint,
//                                    R.string.Networth_plan,
//                                    R.drawable.ic_add_icon_tint));
//                        }
//
//                    }
//                });
//
//
//        fetchGoalRecommendationInfo()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Goalmodel>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        hideProgress();
//                    }
//                    @Override
//                    public void onNext(Goalmodel goalmodel) {
//                        if (!isUnsubscribed())
//                            unsubscribe();
//                        hideProgress();
//                        if(UtileKit.getPersistedPurplePathBoolPref("Goals")) {
//                            mgoals_plan_id.updateRecommendationView(createRecommendationInfo(R.drawable.ic_goals_recommend,
//                                    R.string.Goals_plan,
//                                    R.drawable.ic_add_icon));
//                        }else {
//                            mgoals_plan_id.updateRecommendationView(createRecommendationInfo(R.drawable.ic_goal_tint,
//                                    R.string.Goals_plan,
//                                    R.drawable.ic_add_icon_tint));
//                        }
//                    }
//                });
//
//
//        fetchCashManagementRecommendationInfo()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Cashmodel>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        hideProgress();
//                    }
//                    @Override
//                    public void onNext(Cashmodel cashmodel) {
//                        if (!isUnsubscribed())
//                            unsubscribe();
//                        hideProgress();
//                        if(UtileKit.getPersistedPurplePathBoolPref("Cash Management")) {
//                            mcashmanagement_plan_id.updateRecommendationView(createRecommendationInfo(R.drawable.ic_cash_management_recommend,
//                                    R.string.Cashmanagement_plan,
//                                    R.drawable.ic_add_icon));
//                        }else {
//                            mcashmanagement_plan_id.updateRecommendationView(createRecommendationInfo(R.drawable.ic_cash_management_recommend_tint,
//                                    R.string.Cashmanagement_plan,
//                                    R.drawable.ic_add_icon_tint));
//                        }
//                    }
//                });
//
//        fetchSavingInvestRecommendationInfo()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Saveinvestmodel>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        hideProgress();
//                    }
//                    @Override
//                    public void onNext(Saveinvestmodel saveinvestmodel) {
//                        if (!isUnsubscribed())
//                            unsubscribe();
//                        hideProgress();
//                        if(UtileKit.getPersistedPurplePathBoolPref("Savings and Investments")) {
//                            msaving_and_invesment_plan_id.updateRecommendationView(createRecommendationInfo(R.drawable.ic_save_investment,
//                                    R.string.savingandinvesment_plan,
//                                    R.drawable.ic_add_icon));
//                        }
//                        else {
//                            msaving_and_invesment_plan_id.updateRecommendationView(createRecommendationInfo(R.drawable.ic_save_investment_tint,
//                                    R.string.savingandinvesment_plan,
//                                    R.drawable.ic_add_icon_tint));
//                          //  UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
//                        }
//                    }
//                });
//
//
//
//
//        fetchTaxationRecommendationInfo()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<TaxCashFlowModel>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        hideProgress();
//                    }
//                    @Override
//                    public void onNext(TaxCashFlowModel taxCashFlowModel) {
//                        if (!isUnsubscribed())
//                            unsubscribe();
//                        hideProgress();
//
//                        mtaxation_plan_id.updateRecommendationView(createRecommendationInfo(R.drawable.ic_tax_recommend,
//                                    R.string.Taxation_plan,
//                                    R.drawable.ic_add_icon));
//                    }
//                });
//
//
//
//
//
//
//
//    }
//
//    /*
//     * fetching All Insurance screen information from service.
//     */
//    private Observable<RecommendData> fetchRecommendationInfo() {
//
//        return Observable.defer(new Func0<Observable<RecommendData>>() {
//            @Override
//            public Observable<RecommendData> call() {
//                RecommendData recommendData = null;
//                WebServiceCalls callObj = (ServiceGenerator.createService(WebServiceCalls.class));
//                Call<RecommendData> call = callObj.triggerRecommendationService(UtileKit.getPersistedPurplePathPref("user_id"));
//                try {
//                    Response<RecommendData> response = call.execute();
//                    recommendData = response.body();
//                  //  Log.d("hi","hhhhhhh"+recommendData);
////                    if (!isResumed() || isRemoving());
//                    insuranceItemView.setRecommendedData(recommendData);
//                    emergencyItemView.setRecommendedData(recommendData);
//                    health_insurance_plan_id.setRecommendedData(recommendData);
//                    auto_insurance_plan_id.setRecommendedData(recommendData);
//                    property_insurance_plan_id.setRecommendedData(recommendData);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                catch (Exception e1)
//                {
//                    e1.printStackTrace();
//                }
//                return Observable.just(recommendData);
//            }
//        });
//    }
//
//    /*
//     * fetching networth recommended screen information from service.
//     */
//    private Observable<Networthmodel> fetchNetworthRecommendationInfo() {
//        return Observable.defer(new Func0<Observable<Networthmodel>>() {
//            @Override
//            public Observable<Networthmodel> call() {
//
//                Networthmodel networthmodel=null;
//                WebServiceCalls callObj = (ServiceGenerator.createService(WebServiceCalls.class));
//                Call<Networthmodel> call = callObj.getNetworthRecommendation(UtileKit.getPersistedPurplePathPref("user_id"));
//                try {
//                    Response<Networthmodel> response = call.execute();
//                    networthmodel = response.body();
//                    Log.d("hi","networhlog"+networthmodel);
//                    mnetworth_plan_id.setNetworthmodel(networthmodel);
//                } catch (IOException e) {
//                    //Log.e("@@@@", e.toString());
//                }
//                return Observable.just(networthmodel);
//            }
//        });
//    }
//    /*
//     * fetching Goal recommended screen information from service.
//     */
//    private Observable<Goalmodel> fetchGoalRecommendationInfo() {
//        return Observable.defer(new Func0<Observable<Goalmodel>>() {
//            @Override
//            public Observable<Goalmodel> call() {
//
//                Goalmodel goalmodel=null;
//                WebServiceCalls callObj = (ServiceGenerator.createService(WebServiceCalls.class));
//                Call<Goalmodel> call = callObj.getGoalRecommendation(UtileKit.getPersistedPurplePathPref("user_id"));
//                try {
//                    Response<Goalmodel> response = call.execute();
//                    goalmodel = response.body();
//                    Log.d("hi","saveinvestmentlog"+goalmodel);
//                    mgoals_plan_id.setGoal(goalmodel);
//                } catch (IOException e) {
//                    //Log.e("@@@@", e.toString());
//                }
//                return Observable.just(goalmodel);
//            }
//        });
//    }
//
//
//    /*
//     * fetching CashManagement recommended screen information from service.
//     */
//    private Observable<Cashmodel> fetchCashManagementRecommendationInfo() {
//        return Observable.defer(new Func0<Observable<Cashmodel>>() {
//            @Override
//            public Observable<Cashmodel> call() {
//
//                Cashmodel cashmodel=null;
//                WebServiceCalls callObj = (ServiceGenerator.createService(WebServiceCalls.class));
//                Call<Cashmodel> call = callObj.getCashManagementRecommendation(UtileKit.getPersistedPurplePathPref("user_id"));
//                try {
//                    Response<Cashmodel> response = call.execute();
//                    cashmodel = response.body();
//                    Log.d("hi","saveinvestmentlog"+cashmodel);
//                    mcashmanagement_plan_id.setCashManagement(cashmodel);
//                } catch (IOException e) {
//                    //Log.e("@@@@", e.toString());
//                }
//                return Observable.just(cashmodel);
//            }
//        });
//    }
//
//
//
//
//
//
//
//    /*
//    * fetching SavingandInvestment recommended screen information from service.
//    */
//    private Observable<Saveinvestmodel> fetchSavingInvestRecommendationInfo() {
//        return Observable.defer(new Func0<Observable<Saveinvestmodel>>() {
//            @Override
//            public Observable<Saveinvestmodel> call() {
//
//                Saveinvestmodel saveinvestmodel = null;
//                WebServiceCalls callObj = (ServiceGenerator.createService(WebServiceCalls.class));
//                Call<Saveinvestmodel> call = callObj.getSaveInvestmentRecommendation(UtileKit.getPersistedPurplePathPref("user_id"));
//                try {
//                    Response<Saveinvestmodel> response = call.execute();
//                    saveinvestmodel = response.body();
//                    Log.d("hi","saveinvestmentlog"+saveinvestmodel);
//                    msaving_and_invesment_plan_id.setSavingInvest(saveinvestmodel);
//                } catch (IOException e) {
//                    //Log.e("@@@@", e.toString());
//                }
//                return Observable.just(saveinvestmodel);
//            }
//        });
//    }
//
//
//    /**
//     * Fetching Taxation recommended screen information from service.
//     */
//    private Observable<TaxCashFlowModel> fetchTaxationRecommendationInfo() {
//        return Observable.defer(new Func0<Observable<TaxCashFlowModel>>() {
//            @Override
//            public Observable<TaxCashFlowModel> call() {
//
//                TaxCashFlowModel taxCashFlowModel=null;
//                WebServiceCalls callObj = (ServiceGenerator.createService(WebServiceCalls.class));
//                Call<TaxCashFlowModel> call = callObj.callinsurance_tax_Cash_Flow_Service(UtileKit.getPersistedPurplePathPref("user_id"));
//                try {
//                    Response<TaxCashFlowModel> response = call.execute();
//                    taxCashFlowModel = response.body();
//                    Log.d("taxCashFlowModel","taxCashFlowModel"+taxCashFlowModel);
//                    mtaxation_plan_id.setTaxation(taxCashFlowModel);
//                } catch (IOException e) {
//                    //Log.e("@@@@", e.toString());
//                }
//                return Observable.just(taxCashFlowModel);
//            }
//        });
//    }
//
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.relative_left_arrow:
//            {
//                mCallBackListener.onActivityBackPressed();
//            }
//            break;
//            case R.id.relative_center_home:
//            {
//                Intent i = new Intent(getActivity(), HomePageActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//            break;
//            case R.id.relative_right_arrow:
//            {
//                addFragmenttoStack(new RiskProfile());
//            }
//            break;
//
//        }
//    }
//}
