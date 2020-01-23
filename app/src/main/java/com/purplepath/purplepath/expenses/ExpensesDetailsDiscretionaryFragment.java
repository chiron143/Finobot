//package com.purplepath.purplepath.expenses;
//
//import android.app.DialogFragment;
//import android.app.FragmentManager;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTransaction;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//
//import com.finobot.finobot.R;
//import com.purplepath.purplepath.apputiles.UtileKit;
//import com.purplepath.purplepath.assets.AssetsDetailsFragment;
//import com.purplepath.purplepath.customview.CurrencyDefaultEdt;
//import com.purplepath.purplepath.customview.CurrencyEditText;
//import com.purplepath.purplepath.dia//Log.expectedIncrementDialogFragment;
//import com.purplepath.purplepath.expenses.expensesmodel.AddExpensesDetailModel;
//import com.purplepath.purplepath.expenses.expensesmodel.ExpensesDetailModel;
//import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelOneData;
//import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelThreeData;
//import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelTwoData;
//import com.purplepath.purplepath.expenses.expensesmodel.UpdateExpensesDetailModel;
//import com.purplepath.purplepath.expenses.expensesmodel.UpdateUserExpensesDetail;
//import com.purplepath.purplepath.fragments.BaseFragment;
//import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
//import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
///**
// * Created by Bert on 27-Jun-16.
// */
//public class ExpensesDetailsDiscretionaryFragment extends BaseFragment implements  View.OnClickListener, ExpectedIncrementDialogFragment.OnExpectedValueListener, ExpensesDetailsFragment.OnCustomDiscretionaryTabChange {
//
//    private CurrencyEditText mfoodEdt,
//            mgiftsEdt, mEditingOutEdt, mhealthcarExpensesEdt, mhealthcareListEdt,
//            mPurchasesListEdt, mShelterListEdt, mholidayVacationEdt,
//            mparentSupportEdt, mparentPersonalCareEdt, mPetCareEdt, mPurchasesEdt, mShelterEdt,
//            mskillDevelopmentEdt, mskillDevelopmentListEdt, mtaxEdt, mtaxListEdt, mtransportationEdt, mcommuteEdt,
//            mcommuteListEdt, mPakingEdt, mpetrolEdt,
//            mutilityEdt, mutilityListEdt, charityEdt, booksEdt, mtravelEdt, mtravelListEdt, mVechileEdt;
//
//    private EditText mOtherEdt;
//
//    private CheckBox mExpensesFoodCheckBox, mExpensesHealthcareCheckBox, mExpensesPurchaseCheckBox,
//            mExpensesShelterCheckBox, mExpensesSkillDevelopCheckBox,
//            mExpensesTaxCheckBox, mExpensesCommuteCheckBox, mExpensesTravelCheckBox, mExpennsesUtilityCheckBox;
//
//    private LinearLayout mExpensesEatingOutLayout, mExpensesHealthCareLayout, mExpensesPurchasesLayout,
//            mExpensesShelterLayout, mExpensesSkillDevelopmmentLayout, mExpensesTaxLayout,
//            mExpensesCommuteLayout, mExpensesTravelLayout, mExpensesUtilityLayout;
//
//    private RelativeLayout basisaleryView;
//
//    private ExpensesDetailModel mExpensesDetailModel;
//    public ArrayList<ExpensesLevelOneData> expensesLevelOneData = new ArrayList<ExpensesLevelOneData>();
//    public ArrayList<ExpensesLevelTwoData> expensesLevelTwoData = new ArrayList<ExpensesLevelTwoData>();
//    public ArrayList<ExpensesLevelThreeData> expensesLevelThreeData = new ArrayList<ExpensesLevelThreeData>();
//
//
//    public ArrayList<String> healthcareExpenses = new ArrayList<String>();
//    public ArrayList<String> purchasesExpenses = new ArrayList<String>();
//    public ArrayList<String> shelterExpenses = new ArrayList<String>();
//
//    public ArrayList<String> taxList = new ArrayList<String>();
//    public ArrayList<String> skilldevelopmentList = new ArrayList<String>();
//    public ArrayList<String> travelList = new ArrayList<String>();
//    public ArrayList<String> commuteList = new ArrayList<String>();
//    public ArrayList<String> utilityList = new ArrayList<String>();
//
//    public ArrayList<ExpensesLevelOneData> foodDataField = new ArrayList<ExpensesLevelOneData>();
//    public ArrayList<ExpensesLevelTwoData> foodListDataField = new ArrayList<ExpensesLevelTwoData>();
//
//    public ArrayList<ExpensesLevelOneData> giftsDataField = new ArrayList<ExpensesLevelOneData>();
//
//    public ArrayList<ExpensesLevelOneData> healthcareDataField = new ArrayList<ExpensesLevelOneData>();
//    public ArrayList<ExpensesLevelTwoData> healthcareListDataField = new ArrayList<ExpensesLevelTwoData>();
//
//    public ArrayList<ExpensesLevelOneData> holidayAndVacationDataField = new ArrayList<ExpensesLevelOneData>();
//    public ArrayList<ExpensesLevelOneData> parentSupportDataField = new ArrayList<ExpensesLevelOneData>();
//    public ArrayList<ExpensesLevelOneData> personalCareDataField = new ArrayList<ExpensesLevelOneData>();
//    public ArrayList<ExpensesLevelOneData> petCareDataField = new ArrayList<ExpensesLevelOneData>();
//
//    public ArrayList<ExpensesLevelOneData> purchasesDataField = new ArrayList<ExpensesLevelOneData>();
//    public ArrayList<ExpensesLevelTwoData> purchasesListDataField = new ArrayList<ExpensesLevelTwoData>();
//
//    public ArrayList<ExpensesLevelOneData> shelterDataField = new ArrayList<ExpensesLevelOneData>();
//    public ArrayList<ExpensesLevelTwoData> shelterListDataField = new ArrayList<ExpensesLevelTwoData>();
//
//    public ArrayList<ExpensesLevelOneData> skillDevelopmentDataField = new ArrayList<ExpensesLevelOneData>();
//    public ArrayList<ExpensesLevelTwoData> skillDevelopmentListDataField = new ArrayList<ExpensesLevelTwoData>();
//
//    public ArrayList<ExpensesLevelOneData> taxDataField = new ArrayList<ExpensesLevelOneData>();
//    public ArrayList<ExpensesLevelTwoData> taxListDataField = new ArrayList<ExpensesLevelTwoData>();
//
//    public ArrayList<ExpensesLevelOneData> transportationDataField = new ArrayList<ExpensesLevelOneData>();
//    public ArrayList<ExpensesLevelTwoData> travelDataField = new ArrayList<ExpensesLevelTwoData>();
//    public ArrayList<ExpensesLevelThreeData> travelListDataField = new ArrayList<ExpensesLevelThreeData>();
//    public ArrayList<ExpensesLevelTwoData> commuteDataField = new ArrayList<ExpensesLevelTwoData>();
//
//    public ArrayList<ExpensesLevelTwoData> parkingDataField = new ArrayList<ExpensesLevelTwoData>();
//    public ArrayList<ExpensesLevelTwoData> petrolDataField = new ArrayList<ExpensesLevelTwoData>();
//    public ArrayList<ExpensesLevelTwoData> vechicleDataField = new ArrayList<ExpensesLevelTwoData>();
//
//    public ArrayList<ExpensesLevelThreeData> commuteListDataField = new ArrayList<ExpensesLevelThreeData>();
//
//
//    public ArrayList<ExpensesLevelOneData> utilityDataField = new ArrayList<ExpensesLevelOneData>();
//    public ArrayList<ExpensesLevelOneData> otherDataField = new ArrayList<ExpensesLevelOneData>();
//
//    public ArrayList<ExpensesLevelTwoData> utilityListDataField = new ArrayList<ExpensesLevelTwoData>();
//
//
//    public ArrayList<View> healthcareListView = new ArrayList<View>();
//    public ArrayList<View> purchasesListView = new ArrayList<View>();
//    public ArrayList<View> shelterListView = new ArrayList<View>();
//    public ArrayList<View> skilldevelopmentListView = new ArrayList<View>();
//    public ArrayList<View> taxListView = new ArrayList<View>();
//    public ArrayList<View> commuteListView = new ArrayList<View>();
//    public ArrayList<View> travelListView = new ArrayList<View>();
//    public ArrayList<View> utilityListView = new ArrayList<View>();
//
//
////    private AutoCompleteTextView mhealtherCareAutoComplete, mpurchasesAutoComplete,
////            mshelterCareAutoComplete, mskillDevelopmentAutoComplete,
////            mTaxAutoComplete, mCommuteAutoComplete, mTravelAutoComplete,
////            mUtilityAutoComplete;
//
//    private UpdateExpensesDetailModel mupdateExpensesDetailModel;
//    private ArrayList<UpdateUserExpensesDetail> mupdateUserExpensesDetail;
//
//
//    private ArrayAdapter<String> healthecareAdapter, purchasesAdapter,
//            shelterAdapter, skillDevelopmentAdapter,
//            taxAdapter, commuteAdapter, travalAdapter, utilityAdapter;
//
//    private Context mContext;
//
//    ImageView mfoodImgView,
//            mgiftsImgView, mEditingOutImgView, mhealthcarExpensesImgView, mhealthcareListImgView,
//            mPurchasesListImgView, mShelterListImgView, mholidayVacationImgView,
//            mparentSupportImgView, mPetCareImgView, mPurchasesImgView, mShelterImgView,
//            mskillDevelopmentImgView, mskillDevelopmentListImgView, mtaxImgView, mtaxListImgView,
//            mtransportationImgView, mcommuteImgView, mcommuteListImgView, mPakingImgView, mpetrolImgView,
//            mutilityImgView, mutilityListImgView, booksImgView, mtravelImgView,
//            mtravelListImgView, mVechileImgView, mOtherImgView;
//
//
////    private ArrayList<AutoCompleteTextView> healthcareArray=new ArrayList<>();
////    private ArrayList<AutoCompleteTextView> purchaseArray=new ArrayList<>();
////    private ArrayList<AutoCompleteTextView> shelterArray=new ArrayList<>();
////    private ArrayList<AutoCompleteTextView> skilldevelopmentArray=new ArrayList<>();
////    private ArrayList<AutoCompleteTextView> taxArray=new ArrayList<>();
////    private ArrayList<AutoCompleteTextView> transportationArray=new ArrayList<>();
////    private ArrayList<AutoCompleteTextView> utilityArray=new ArrayList<>();
////    private ArrayList<AutoCompleteTextView> commuteArray=new ArrayList<>();
////    private ArrayList<AutoCompleteTextView> travelArray=new ArrayList<>();
//
//    private ImageView healthcareAddImgView,
//            purchasesImgView, shelterImgView, skilldevelopmentImgView,
//            taxImgView, commuteImgView, travelImgView, utilityImgView;
//
//    JSONObject jsonEssentialObject = new JSONObject();
//    JSONArray jsonArray;
//
//    private AddExpensesDetailModel mAddExpensesDetailModel;
//    private LinearLayout healthcarelistheaderId, purchaseslistheaderId,
//            shelterlistheaderId, skilldevelopmentheaderId, taxheaderId,
//            commuteheaderId, travelheaderId, utilityheaderId;
//
//    private String checkIsEntertainId;
//    private int setPosition;
//
//
//    ImageView healthcareaddimagView, healthcarepopupView, healthcaredeleteimagView;
//    ImageView skilldevelopmentaddimagView, skilldevelopmentpopupView, skilldevelopmentdeleteimagView ;
//    ImageView shelteraddimagView, shelterpopupView, shelterdeleteimagView ;
//    ImageView taxaddimagView, taxpopupView, taxdeleteimagView ;
//    ImageView purchcaseaddimagView, purchasepopupView ,purchasedeleteimagView;
//    ImageView commuteaddimagView, commutepopupView, commutedeleteimagView;
//    ImageView traveladdimagView, travelpopupView, traveldeleteimagView;
//    ImageView utilityaddimagView, utilitypopupView, utilitydeleteimagView;
//
//    String  getOverallExpenses, overallValue, OverallExpenses;
//    int moverallExpenses;
//    private  CurrencyDefaultEdt essentialTotal;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        callExpensesCategoriesService();
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View expensesdetailView = inflater.inflate(R.layout.fragment_expenses_details_discretionary, container, false);
//
////        healthcareAddImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_healthcare_list_add_imgView);;
////        purchasesImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_purchases_list_add_imgView);
////        shelterImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_shelter_list_add_imgView);
////        skilldevelopmentImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_skilldevelopment_list_add_imgView);
////        taxImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_tax_list_add_imgView);
////        commuteImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_commute_list_add_imgView);
////        travelImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_travel_list_add_imgView);
////        utilityImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_utility_list_add_imgView);
//
//        ExpensesDetailsFragment.setCustomOnDiscretionaryTabChangeListener(this);
//
//        mExpensesFoodCheckBox = (CheckBox) expensesdetailView.findViewById(R.id.exp_food_disc_checkbox);
//        mExpensesHealthcareCheckBox = (CheckBox) expensesdetailView.findViewById(R.id.exp_healthcare_checkbox);
//        mExpensesPurchaseCheckBox = (CheckBox) expensesdetailView.findViewById(R.id.exp_purchases_checkbox);
//        mExpensesShelterCheckBox = (CheckBox) expensesdetailView.findViewById(R.id.exp_shelter_checkbox);
//        mExpensesSkillDevelopCheckBox = (CheckBox) expensesdetailView.findViewById(R.id.exp_skilldevelopment_checkbox);
//        mExpensesTaxCheckBox = (CheckBox) expensesdetailView.findViewById(R.id.exp_tax_checkbox);
//        mExpensesCommuteCheckBox = (CheckBox) expensesdetailView.findViewById(R.id.exp_commute_checkbox);
//        mExpensesTravelCheckBox = (CheckBox) expensesdetailView.findViewById(R.id.exp_travel_checkbox);
//        mExpennsesUtilityCheckBox = (CheckBox) expensesdetailView.findViewById(R.id.exp_utility_checkbox);
//
//
//        mExpensesEatingOutLayout = (LinearLayout) expensesdetailView.findViewById(R.id.exp_eatingout_layout);
//        mExpensesHealthCareLayout = (LinearLayout) expensesdetailView.findViewById(R.id.exp_healthcare_list_layout);
//        mExpensesPurchasesLayout = (LinearLayout) expensesdetailView.findViewById(R.id.exp_purchases_list_layout);
//        mExpensesShelterLayout = (LinearLayout) expensesdetailView.findViewById(R.id.exp_shelter_list_layout);
//        mExpensesSkillDevelopmmentLayout = (LinearLayout) expensesdetailView.findViewById(R.id.exp_skilldevelopment_list_layout);
//        mExpensesTaxLayout = (LinearLayout) expensesdetailView.findViewById(R.id.exp_tax_list_layout);
//        mExpensesCommuteLayout = (LinearLayout) expensesdetailView.findViewById(R.id.exp_commute_list_layout);
//        mExpensesTravelLayout = (LinearLayout) expensesdetailView.findViewById(R.id.exp_travel_list_layout);
//        mExpensesUtilityLayout = (LinearLayout) expensesdetailView.findViewById(R.id.exp_utility_list_layout);
//        essentialTotal = (CurrencyDefaultEdt)expensesdetailView.findViewById(R.id.currencyEditTxtTotal);
////        healthcareArray.add((AutoCompleteTextView) expensesdetailView.findViewById(R.id.exp_healthcare_list_autocomplete));
////        purchaseArray.add((AutoCompleteTextView) expensesdetailView.findViewById(R.id.exp_purchases_list_autocomplete));
////        shelterArray.add((AutoCompleteTextView) expensesdetailView.findViewById(R.id.exp_shelter_list_autocomplete));
////        skilldevelopmentArray.add((AutoCompleteTextView) expensesdetailView.findViewById(R.id.exp_skilldevelopment_list_autocomplete));
////        taxArray.add((AutoCompleteTextView) expensesdetailView.findViewById(R.id.exp_tax_list_autocomplete));
////        commuteArray.add((AutoCompleteTextView) expensesdetailView.findViewById(R.id.exp_commute_list_autocomplete));
////        travelArray.add((AutoCompleteTextView) expensesdetailView.findViewById(R.id.exp_travel_list_autocomplete));
////        utilityArray.add((AutoCompleteTextView) expensesdetailView.findViewById(R.id.exp_utility_list_autocomplete));
//
//
//        healthcarelistheaderId = (LinearLayout) expensesdetailView.findViewById(R.id.healthcare_header_list);
//        healthcareListView.add(healthcarelistheaderId);
//
//        healthcareaddimagView = (ImageView) healthcareListView.get(0).findViewById(R.id.exp_education_list_add_imgView);
//        healthcarepopupView = (ImageView) healthcareListView.get(0).findViewById(R.id.exp_education_list_edt_popup);
//         healthcaredeleteimagView = (ImageView) healthcareListView.get(0).findViewById(R.id.exp_education_list_delete_imgView);
//
//        healthcareaddimagView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.setVisibility(View.GONE);
//                addListView(mExpensesHealthCareLayout, "healthcare", healthcareListView.size(), healthcareListView, healthcareExpenses);
//            }
//        });
//
//        healthcarepopupView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                showPopupDialog(R.id.exp_education_list_edt_popup);
//                setCheckIsEntertainId("healthcare");
//                setSetPosition(0);
//            }
//        });
//
//        purchaseslistheaderId = (LinearLayout) expensesdetailView.findViewById(R.id.purchases_header_list);
//        purchasesListView.add(purchaseslistheaderId);
//
//         purchcaseaddimagView = (ImageView) purchasesListView.get(0).findViewById(R.id.exp_education_list_add_imgView);
//         purchasepopupView = (ImageView) purchasesListView.get(0).findViewById(R.id.exp_education_list_edt_popup);
//         purchasedeleteimagView = (ImageView) purchasesListView.get(0).findViewById(R.id.exp_education_list_delete_imgView);
//
//        purchcaseaddimagView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.setVisibility(View.GONE);
//                addListView(mExpensesPurchasesLayout, "purchases", purchasesListView.size(), purchasesListView, purchasesExpenses);
//            }
//        });
//
//        purchasepopupView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopupDialog(R.id.exp_education_list_edt_popup);
//                setCheckIsEntertainId("purchases");
//                setSetPosition(0);
//            }
//        });
//
//        shelterlistheaderId = (LinearLayout) expensesdetailView.findViewById(R.id.shelter_header_list);
//        shelterListView.add(shelterlistheaderId);
//
//         shelteraddimagView = (ImageView) shelterListView.get(0).findViewById(R.id.exp_education_list_add_imgView);
//         shelterpopupView = (ImageView) shelterListView.get(0).findViewById(R.id.exp_education_list_edt_popup);
//         shelterdeleteimagView = (ImageView) shelterListView.get(0).findViewById(R.id.exp_education_list_delete_imgView);
//
//        shelteraddimagView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.setVisibility(View.GONE);
//                addListView(mExpensesShelterLayout, "shelter", shelterListView.size(), shelterListView, shelterExpenses);
//            }
//        });
//
//        shelterpopupView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopupDialog(R.id.exp_education_list_edt_popup);
//                setCheckIsEntertainId("shelter");
//                setSetPosition(0);
//            }
//        });
//
//
//        skilldevelopmentheaderId = (LinearLayout) expensesdetailView.findViewById(R.id.skilldevelopment_header_list);
//        skilldevelopmentListView.add(skilldevelopmentheaderId);
//
//
//        skilldevelopmentaddimagView = (ImageView) skilldevelopmentListView.get(0).findViewById(R.id.exp_education_list_add_imgView);
//        skilldevelopmentpopupView = (ImageView) skilldevelopmentListView.get(0).findViewById(R.id.exp_education_list_edt_popup);
//        skilldevelopmentdeleteimagView = (ImageView) skilldevelopmentListView.get(0).findViewById(R.id.exp_education_list_delete_imgView);
//
//        skilldevelopmentaddimagView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.setVisibility(View.GONE);
//                addListView(mExpensesSkillDevelopmmentLayout, "skilldevelopment", skilldevelopmentListView.size(), skilldevelopmentListView, skilldevelopmentList);
//            }
//        });
//
//        skilldevelopmentpopupView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopupDialog(R.id.exp_education_list_edt_popup);
//                setCheckIsEntertainId("skilldevelopment");
//                setSetPosition(0);
//            }
//        });
//
//        taxheaderId = (LinearLayout) expensesdetailView.findViewById(R.id.tax_header_list);
//        taxListView.add(taxheaderId);
//
//        taxaddimagView = (ImageView) taxListView.get(0).findViewById(R.id.exp_education_list_add_imgView);
//        taxpopupView = (ImageView) taxListView.get(0).findViewById(R.id.exp_education_list_edt_popup);
//        taxdeleteimagView = (ImageView) taxListView.get(0).findViewById(R.id.exp_education_list_delete_imgView);
//
//        taxaddimagView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.setVisibility(View.GONE);
//                addListView(mExpensesTaxLayout, "tax", taxListView.size(), taxListView, taxList);
//            }
//        });
//
//        taxpopupView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopupDialog(R.id.exp_education_list_edt_popup);
//                setCheckIsEntertainId("tax");
//                setSetPosition(0);
//            }
//        });
//
//        commuteheaderId = (LinearLayout) expensesdetailView.findViewById(R.id.commute_header_list);
//        commuteListView.add(commuteheaderId);
//
//        commuteaddimagView = (ImageView) commuteListView.get(0).findViewById(R.id.exp_education_list_add_imgView);
//        commutepopupView = (ImageView) commuteListView.get(0).findViewById(R.id.exp_education_list_edt_popup);
//        commutedeleteimagView = (ImageView) commuteListView.get(0).findViewById(R.id.exp_education_list_delete_imgView);
//
//        commuteaddimagView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.setVisibility(View.GONE);
//                addListView(mExpensesCommuteLayout, "commute", commuteListView.size(), commuteListView, commuteList);
//            }
//        });
//
//        commutepopupView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopupDialog(R.id.exp_education_list_edt_popup);
//                setCheckIsEntertainId("commute");
//                setSetPosition(0);
//            }
//        });
//
//        travelheaderId = (LinearLayout) expensesdetailView.findViewById(R.id.travel_header_list);
//        travelListView.add(travelheaderId);
//
//        traveladdimagView = (ImageView) travelListView.get(0).findViewById(R.id.exp_education_list_add_imgView);
//        travelpopupView = (ImageView) travelListView.get(0).findViewById(R.id.exp_education_list_edt_popup);
//        traveldeleteimagView = (ImageView) travelListView.get(0).findViewById(R.id.exp_education_list_delete_imgView);
//
//        traveladdimagView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.setVisibility(View.GONE);
//                addListView(mExpensesTravelLayout, "travel", travelListView.size(), travelListView, travelList);
//            }
//        });
//
//        travelpopupView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopupDialog(R.id.exp_education_list_edt_popup);
//                setCheckIsEntertainId("travel");
//                setSetPosition(0);
//            }
//        });
//
//        utilityheaderId = (LinearLayout) expensesdetailView.findViewById(R.id.utility_header_list);
//        utilityListView.add(utilityheaderId);
//
//        utilityaddimagView = (ImageView) utilityListView.get(0).findViewById(R.id.exp_education_list_add_imgView);
//        utilitypopupView = (ImageView) utilityListView.get(0).findViewById(R.id.exp_education_list_edt_popup);
//        utilitydeleteimagView = (ImageView) utilityListView.get(0).findViewById(R.id.exp_education_list_delete_imgView);
//
//        utilityaddimagView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.setVisibility(View.GONE);
//                addListView(mExpensesUtilityLayout, "utility", utilityListView.size(), utilityListView, utilityList);
//            }
//        });
//
//        utilitypopupView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopupDialog(R.id.exp_education_list_edt_popup);
//                setCheckIsEntertainId("utility");
//                setSetPosition(0);
//            }
//        });
//
//        mfoodImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_food_disc_edt_popup);
//        mEditingOutImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_eatingout_edt_popup);
//        mgiftsImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_gifts_edt_popup);
//        mhealthcarExpensesImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_healthcare_edt_popup);
//        //  mhealthcareListImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_healthcare_list_edt_popup);
//
//        mholidayVacationImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_holidayvacation_edt_popup);
//        mparentSupportImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_parentssupport_edt_popup);
//        mPetCareImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_petcare_edt_popup);
//        mPurchasesImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_purchases_edt_popup);
//        //mPurchasesListImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_purchases_list_edt_popup);
//        mShelterImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_shelter_edt_popup);
//        //mShelterListImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_shelter_list_edt_popup);
//        mskillDevelopmentImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_skilldevelopment_edt_popup);
//        //mskillDevelopmentListImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_skilldevelopment_list_edt_popup);
//
//
//        mtaxImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_tax_edt_popup);
//        //mtaxListImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_tax_list_edt_popup);
//
//        mtransportationImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_transportation_edt_popup);
//        mcommuteImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_commute_edt_popup);
//        //mcommuteListImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_commute_list_edt_popup);
//        mPakingImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_parking_edt_popup);
//        mpetrolImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_petrol_edt_popup);
//
//        mtravelImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_travel_edt_popup);
//        //mtravelListImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_travel_list_edt_popup);
//        mVechileImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_vechile_edt_popup);
//        mutilityImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_utility_edt_popup);
//        //mutilityListImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_utility_list_edt_popup);
//        mOtherImgView = (ImageView) expensesdetailView.findViewById(R.id.exp_other_edt_popup);
//
//
//        mfoodEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_food_disc_edt);
//        mEditingOutEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_eatingout);
//        mgiftsEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_gifts_edt);
//        mhealthcarExpensesEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_healthcare_edt);
////        mhealthcareListEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_healthcare_list_edt);
//        mholidayVacationEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_holidayvacation_edt);
//
//
//        mparentSupportEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_parentssupport_edt);
//        mPetCareEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_petcare_edt);
//        mPurchasesEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_purchases_edt);
//        //mPurchasesListEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_purchases_list_edt);
//        mShelterEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_shelter_edt);
//        //mShelterListEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_shelter_list_edt);
//        mskillDevelopmentEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_skilldevelopment_edt);
//        //mskillDevelopmentListEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_skilldevelopment_list_edt);
//        mtaxEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_tax_edt);
//        //mtaxListEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_tax_list_edt);
//
//        mtransportationEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_transportation_edt);
//        mcommuteEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_commute_edt);
//        //mcommuteListEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_commute_list_edt);
//        mPakingEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_parking_edt);
//        mpetrolEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_petrol_edt);
//        mtravelEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_travel_edt);
//        //mtravelListEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_travel_list_edt);
//        mVechileEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_vechile_edt);
//        mutilityEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_utility_edt);
//        //mutilityListEdt = (CurrencyEditText) expensesdetailView.findViewById(R.id.exp_utility_list_edt);
//        mOtherEdt = (EditText) expensesdetailView.findViewById(R.id.exp_other_edt);
//
//
////                healthcareAddImgView.setOnClickListener(this);
////                purchasesImgView.setOnClickListener(this);
////                shelterImgView.setOnClickListener(this);
////                skilldevelopmentImgView.setOnClickListener(this);
////                taxImgView.setOnClickListener(this);
////                commuteImgView.setOnClickListener(this);
////                travelImgView.setOnClickListener(this);
////                utilityImgView.setOnClickListener(this);
//
//
//        mfoodImgView.setOnClickListener(this);
//        mgiftsImgView.setOnClickListener(this);
//        mEditingOutImgView.setOnClickListener(this);
//        mhealthcarExpensesImgView.setOnClickListener(this);
////        mhealthcareListImgView.setOnClickListener(this);
////        mPurchasesListImgView.setOnClickListener(this);
////        mShelterListImgView.setOnClickListener(this);
//        mholidayVacationImgView.setOnClickListener(this);
//        mparentSupportImgView.setOnClickListener(this);
//        mPetCareImgView.setOnClickListener(this);
//        mPurchasesImgView.setOnClickListener(this);
//        mShelterImgView.setOnClickListener(this);
//        mskillDevelopmentImgView.setOnClickListener(this);
////        mskillDevelopmentListImgView.setOnClickListener(this);
//        mtaxImgView.setOnClickListener(this);
////        mtaxListImgView.setOnClickListener(this);
//        mtransportationImgView.setOnClickListener(this);
//        mcommuteImgView.setOnClickListener(this);
////        mcommuteListImgView.setOnClickListener(this);
//        mPakingImgView.setOnClickListener(this);
//        mpetrolImgView.setOnClickListener(this);
//        mutilityImgView.setOnClickListener(this);
////        mutilityListImgView.setOnClickListener(this);
//        mtravelImgView.setOnClickListener(this);
////        mtravelListImgView.setOnClickListener(this);
//        mVechileImgView.setOnClickListener(this);
//        mOtherImgView.setOnClickListener(this);
//
//
//        mExpensesFoodCheckBox.setOnClickListener(this);
//        mExpensesHealthcareCheckBox.setOnClickListener(this);
//        mExpensesPurchaseCheckBox.setOnClickListener(this);
//        mExpensesShelterCheckBox.setOnClickListener(this);
//        mExpensesSkillDevelopCheckBox.setOnClickListener(this);
//        mExpensesTaxCheckBox.setOnClickListener(this);
//        mExpensesCommuteCheckBox.setOnClickListener(this);
//        mExpensesTravelCheckBox.setOnClickListener(this);
//        mExpennsesUtilityCheckBox.setOnClickListener(this);
//
//        FloatingActionButton fab = (FloatingActionButton) expensesdetailView.findViewById(R.id.discretionary_fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    getInputValues();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                String jsonStr = ExpensesDetailsEssentialFragment.getPersistedAnonymePref("essential");
//                String essentialTotalamount = ExpensesDetailsEssentialFragment.getPersistedAnonymePref("essentialoverallexp");
//                int value = Integer.valueOf(essentialTotalamount);
//                moverallExpenses =+value;
//                OverallExpenses = String.valueOf(moverallExpenses);
//
//                if (UtileKit.validateObjectValues(jsonStr) && jsonStr.length() > 0) {
//                    JSONArray myjsonArray = null;
//                    try {
//                        myjsonArray = new JSONArray(jsonStr);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    if (myjsonArray.length() > 0) {
//                        for (int i = 0; i < myjsonArray.length(); i++) {
//                            try {
//                                jsonArray.put(myjsonArray.get(i));
//
////                                JSONObject json = (JSONObject) jsonArray.get(i);
////                                if(json.has("overallexpenses")) {
////                                    getOverallExpenses = json.get("overallexpenses").toString();
////                                    int value = Integer.valueOf(getOverallExpenses);
////                                    moverallExpenses =+value;
////                                    OverallExpenses = String.valueOf(moverallExpenses);
//////                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        try {
//                        jsonEssentialObject.put("exp_det", jsonArray);
//                        String str = jsonEssentialObject.toString();
//                        if (UtileKit.validateObjectValues(ExpensesDetailsEssentialFragment.updateexpenseId)) {
//                            updateExpensesService(str);
//                        } else {
//                            AddExpensesService(str);
//                        }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                } else {
//                    if ((UtileKit.validateObjectValues(jsonArray) && jsonArray.length() > 0)) {
//                        try {
//                            jsonEssentialObject.put("exp_det", jsonArray);
//                            String str = jsonEssentialObject.toString();
//                            if (UtileKit.validateObjectValues(ExpensesDetailsEssentialFragment.updateexpenseId)) {
//                                updateExpensesService(str);
//                            } else {
//                                AddExpensesService(str);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                AssetsDetailsFragment fragment = new AssetsDetailsFragment();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commitAllowingStateLoss();
//            }
//        });
//        callGetEpensesService();
//        return expensesdetailView;
//    }
//
//
//    private void callGetEpensesService(){
//        //UtileKit.showSpinnerDialog(mContext, false);
//        WebServiceCalls webServiceObj;
//        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
//        mupdateUserExpensesDetail = new ArrayList<UpdateUserExpensesDetail>();
//        Call<UpdateExpensesDetailModel> call = webServiceObj.callGetExpensesService(UtileKit.getPersistedPurplePathPref("user_id"));
//        call.enqueue(new Callback<UpdateExpensesDetailModel>() {
//            @Override
//            public void onResponse(Call<UpdateExpensesDetailModel> call, Response<UpdateExpensesDetailModel> response) {
//                //Log.e("CallBack", " response is " + call.toString());
//                mupdateExpensesDetailModel = response.body();
//                if(mupdateExpensesDetailModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
//                    mupdateUserExpensesDetail = mupdateExpensesDetailModel.getData().getuser_expense();
//                    if (mupdateUserExpensesDetail.size() > 0) {
//                        getExpensesData(mupdateUserExpensesDetail);
//                    }
//                }
//                UtileKit.dismisssSpinnerDialog();
//            }
//            @Override
//            public void onFailure(Call<UpdateExpensesDetailModel> call, Throwable t) {
//                //Log.e("CallBack", " failure is " + t);
//                UtileKit.dismisssSpinnerDialog();
//            }
//        });
//        UtileKit.dismisssSpinnerDialog();
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        mContext = context;
//        //Log.e("attach", "aatach");
//    }
//
////    public String addToJson(){
////        String str = null;
////        try {
////            str = getInputValues();
////            //Log.e("stringstring",""+str);
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
////        return str;
////    }
//
//
//    public void updateExpensesService(String str)  {
//
//        WebServiceCalls webServiceObj;
//        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
////        Call<AddExpensesDetailModel> call = webServiceObj.callUpdateExpensesService(UtileKit.getPersistedPurplePathPref("user_id"),OverallExpenses,"1",ExpensesDetailsEssentialFragment.updateexpenseId, "Y", str);
//        Call<AddExpensesDetailModel> call = webServiceObj.callUpdateExpensesService(UtileKit.getPersistedPurplePathPref("user_id"),OverallExpenses,"1", "Y", str);
//        call.enqueue(new Callback<AddExpensesDetailModel>() {
//            @Override
//            public void onResponse(Call<AddExpensesDetailModel> call, Response<AddExpensesDetailModel> response) {
//                ////Log.e("AddExpensesDetailModel", " AddExpensesDetailModel is " + call.toString());
//                mAddExpensesDetailModel = response.body();
//                ////Log.e("AddExpensesDetailModel", " AddExpensesDetailModel is " +mAddExpensesDetailModel.getStatusCode());
//                if(mAddExpensesDetailModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
//                    ExpensesDetailsEssentialFragment.removePersistedAnonymePref("essential");
//                    ExpensesDetailsEssentialFragment.removePersistedAnonymePref("discretionary");
//                }
//                UtileKit.dismisssSpinnerDialog();
//                //getAsArrayList(mExpensesDetailModel);
//            }
//            @Override
//            public void onFailure(Call<AddExpensesDetailModel> call, Throwable t) {
//               // //Log.e("AddExpensesfailure", " AddExpensefailure is "+ t);
//                UtileKit.dismisssSpinnerDialog();
//            }
//        });
//        UtileKit.dismisssSpinnerDialog();
//    }
//
//
//
//    public void AddExpensesService(String str) {
//
////        JSONObject jsonObject = new JSONObject();
////        JSONArray jsonArray = new JSONArray();
////        JSONObject jsonObject2 = new JSONObject();
////        String str = null;
////        try {
////            jsonObject2.put("field", "char_contri");
////        jsonObject2.put("value", "6000");
////        JSONObject jsonObject3 = new JSONObject();
////        jsonObject3.put("field", "cc_char_don");
////        jsonObject3.put("value", "5000");
////        jsonArray.put(jsonObject2);
////        jsonArray.put(jsonObject3);
////        jsonObject.put("exp_details",jsonArray);
////        str = jsonObject.toString();
////        //Log.e("JsonString", ""+str);
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
////        String str = null;
////        try {
////            str = getInputValues();
////            //Log.e("stringstring", "" + str);
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
//        UtileKit.showSpinnerDialog(mContext, false);
//        WebServiceCalls webServiceObj;
//        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
//        Call<AddExpensesDetailModel> call = webServiceObj.callAddExpensesService(UtileKit.getPersistedPurplePathPref("user_id"),OverallExpenses, "Y", str,"0");
//        call.enqueue(new Callback<AddExpensesDetailModel>() {
//            @Override
//            public void onResponse(Call<AddExpensesDetailModel> call, Response<AddExpensesDetailModel> response) {
//                //Log.e("AddExpensesDetailModel", " AddExpensesDetailModel is " + call.toString());
//                mAddExpensesDetailModel = response.body();
////                //Log.e("AddExpensesDetailModel", " AddExpensesDetailModel is " + mAddExpensesDetailModel.getStatusCode());
////
//                UtileKit.dismisssSpinnerDialog();
////                getAsArrayList(mExpensesDetailModel);
//               if(mAddExpensesDetailModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
//                   ExpensesDetailsEssentialFragment.removePersistedAnonymePref("essential");
//                   ExpensesDetailsEssentialFragment.removePersistedAnonymePref("discretionary");
//               }
//            }
//
//            @Override
//            public void onFailure(Call<AddExpensesDetailModel> call, Throwable t) {
//                //Log.e("AddExpensesfailure", " AddExpensefailure is " + t);
//                UtileKit.dismisssSpinnerDialog();
//            }
//        });
//        UtileKit.dismisssSpinnerDialog();
//    }
//
//
//    private void callExpensesCategoriesService() {
//        UtileKit.showSpinnerDialog(mContext, false);
//        WebServiceCalls webServiceObj;
//        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
//        Call<ExpensesDetailModel> call = webServiceObj.callExpensesCategoriesService();
//        call.enqueue(new Callback<ExpensesDetailModel>() {
//            @Override
//            public void onResponse(Call<ExpensesDetailModel> call, Response<ExpensesDetailModel> response) {
//                //Log.e("CallBack", " response is " + call.toString());
//                mExpensesDetailModel = response.body();
//                UtileKit.dismisssSpinnerDialog();
//                getAsArrayList(mExpensesDetailModel);
//            }
//
//            @Override
//            public void onFailure(Call<ExpensesDetailModel> call, Throwable t) {
//                //Log.e("CallBack", " failure is " + t);
//                UtileKit.dismisssSpinnerDialog();
//            }
//        });
//        UtileKit.dismisssSpinnerDialog();
//    }
//
//
//    private void getAsArrayList(ExpensesDetailModel expensesDetailModel) {
//        if (expensesDetailModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
//            expensesLevelOneData = expensesDetailModel.getData().getExpense_cat_lev1();
//            expensesLevelTwoData = expensesDetailModel.getData().getExpense_cat_lev2();
//            expensesLevelThreeData = expensesDetailModel.getData().getExpense_cat_lev3();
//            if (expensesLevelOneData != null) {
//                for (int i = 0; i < expensesLevelOneData.size(); i++) {
//                    String checkString = expensesLevelOneData.get(i).getLev1_name();
//                    if (checkString.equalsIgnoreCase("Healthcare Expenses")) {
//                        String level1Id = expensesLevelOneData.get(i).getId();
//                        healthcareDataField.add(expensesLevelOneData.get(i));
//                        for (int j = 0; j < expensesLevelTwoData.size(); j++) {
//                            String level2Id = expensesLevelTwoData.get(j).getLev1_id();
//                            if (level1Id.equalsIgnoreCase(level2Id)) {
//                                healthcareExpenses.add(expensesLevelTwoData.get(j).getLev2_name());
//                                //   healthcareArray.get(0).setAdapter(healthecareAdapter);
//                                healthcareListDataField.add(expensesLevelTwoData.get(j));
//                                //Log.e("CallBack", " Healthcare is " + expensesLevelTwoData.get(j).getLev2_name());
//                            }
//                        }
//                        healthecareAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, healthcareExpenses);
//                        AutoCompleteTextView autocomp = (AutoCompleteTextView) healthcareListView.get(0).findViewById(R.id.exp_education_list_autocomplete);
//                        autocomp.setAdapter(healthecareAdapter);
//
//                    }
//
//                    if (checkString.equalsIgnoreCase("Food")) {
//                        String level1Id = expensesLevelOneData.get(i).getId();
//                        foodDataField.add(expensesLevelOneData.get(i));
//                        for (int j = 0; j < expensesLevelTwoData.size(); j++) {
//                            String level2Id = expensesLevelTwoData.get(j).getLev1_id();
//                            if (level1Id.equalsIgnoreCase(level2Id)) {
//                                foodListDataField.add(expensesLevelTwoData.get(j));
//
//                            }
//                        }
//                    }
//
//                    if (checkString.equalsIgnoreCase("Holiday / Vacation")) {
//                        String level1Id = expensesLevelOneData.get(i).getId();
//                        holidayAndVacationDataField.add(expensesLevelOneData.get(i));
//                        for (int j = 0; j < expensesLevelTwoData.size(); j++) {
//                            String level2Id = expensesLevelTwoData.get(j).getLev1_id();
//                            if (level1Id.equalsIgnoreCase(level2Id)) {
//
//                            }
//                        }
//                    }
//
//                    if (checkString.equalsIgnoreCase("Parents Support")) {
//                        String level1Id = expensesLevelOneData.get(i).getId();
//                        parentSupportDataField.add(expensesLevelOneData.get(i));
//                        for (int j = 0; j < expensesLevelTwoData.size(); j++) {
//                            String level2Id = expensesLevelTwoData.get(j).getLev1_id();
//                            if (level1Id.equalsIgnoreCase(level2Id)) {
//
//                            }
//                        }
//                    }
//
//
//                    if (checkString.equalsIgnoreCase("Personal Care")) {
//                        String level1Id = expensesLevelOneData.get(i).getId();
//                        personalCareDataField.add(expensesLevelOneData.get(i));
//                        for (int j = 0; j < expensesLevelTwoData.size(); j++) {
//                            String level2Id = expensesLevelTwoData.get(j).getLev1_id();
//                            if (level1Id.equalsIgnoreCase(level2Id)) {
//
//                            }
//                        }
//                    }
//
//
//                    if (checkString.equalsIgnoreCase("Pet Care")) {
//                        String level1Id = expensesLevelOneData.get(i).getId();
//                        petCareDataField.add(expensesLevelOneData.get(i));
//                        for (int j = 0; j < expensesLevelTwoData.size(); j++) {
//                            String level2Id = expensesLevelTwoData.get(j).getLev1_id();
//                            if (level1Id.equalsIgnoreCase(level2Id)) {
//
//                            }
//                        }
//                    }
//
//
//                    if (checkString.equalsIgnoreCase("Purchases")) {
//                        String level1Id = expensesLevelOneData.get(i).getId();
//                        purchasesDataField.add(expensesLevelOneData.get(i));
//                        for (int j = 0; j < expensesLevelTwoData.size(); j++) {
//                            String level2Id = expensesLevelTwoData.get(j).getLev1_id();
//                            if (level1Id.equalsIgnoreCase(level2Id)) {
//                                purchasesExpenses.add(expensesLevelTwoData.get(j).getLev2_name());
//                                // purchasesAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, purchasesExpenses);
////                                purchaseArray.get(0).setAdapter(purchasesAdapter);
//                                purchasesListDataField.add(expensesLevelTwoData.get(j));
//
//                                //Log.e("CallBack", " Purchases is " + expensesLevelTwoData.get(j).getLev2_name());
//                            }
//                        }
//                        purchasesAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, purchasesExpenses);
//                        AutoCompleteTextView autocomp = (AutoCompleteTextView) purchasesListView.get(0).findViewById(R.id.exp_education_list_autocomplete);
//                        autocomp.setAdapter(purchasesAdapter);
//
//                    }
//
//
//                    if (checkString.equalsIgnoreCase("Shelter")) {
//                        String level1Id = expensesLevelOneData.get(i).getId();
//                        shelterDataField.add(expensesLevelOneData.get(i));
//                        for (int j = 0; j < expensesLevelTwoData.size(); j++) {
//                            String level2Id = expensesLevelTwoData.get(j).getLev1_id();
//                            if (level1Id.equalsIgnoreCase(level2Id)) {
//                                shelterExpenses.add(expensesLevelTwoData.get(j).getLev2_name());
////                                shelterAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, shelterExpenses);
////                                shelterArray.get(0).setAdapter(shelterAdapter);
//                                shelterListDataField.add(expensesLevelTwoData.get(j));
//                                //Log.e("CallBack", " Shelter is " + expensesLevelTwoData.get(j).getLev2_name());
//                            }
//                        }
//                        shelterAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, shelterExpenses);
//                        AutoCompleteTextView autocomp = (AutoCompleteTextView) shelterListView.get(0).findViewById(R.id.exp_education_list_autocomplete);
//                        autocomp.setAdapter(shelterAdapter);
//                    }
//
//                    if (checkString.equalsIgnoreCase("Skill Development")) {
//                        String level1Id = expensesLevelOneData.get(i).getId();
//                        skillDevelopmentDataField.add(expensesLevelOneData.get(i));
//                        for (int j = 0; j < expensesLevelTwoData.size(); j++) {
//                            String level2Id = expensesLevelTwoData.get(j).getLev1_id();
//                            if (level1Id.equalsIgnoreCase(level2Id)) {
//                                skilldevelopmentList.add(expensesLevelTwoData.get(j).getLev2_name());
////                                skillDevelopmentAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, skilldevelopmentList);
////                                skilldevelopmentArray.get(0).setAdapter(skillDevelopmentAdapter);
//                                skillDevelopmentListDataField.add(expensesLevelTwoData.get(j));
//
//                            }
//                        }
//
//                        skillDevelopmentAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, skilldevelopmentList);
//                        AutoCompleteTextView autocomp = (AutoCompleteTextView) skilldevelopmentListView.get(0).findViewById(R.id.exp_education_list_autocomplete);
//                        autocomp.setAdapter(skillDevelopmentAdapter);
//                    }
//
//
//                    if (checkString.equalsIgnoreCase("Tax")) {
//                        String level1Id = expensesLevelOneData.get(i).getId();
//                        taxDataField.add(expensesLevelOneData.get(i));
//                        for (int j = 0; j < expensesLevelTwoData.size(); j++) {
//                            String level2Id = expensesLevelTwoData.get(j).getLev1_id();
//                            if (level1Id.equalsIgnoreCase(level2Id)) {
//                                taxList.add(expensesLevelTwoData.get(j).getLev2_name());
////                                taxAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, taxList);
////                                taxArray.get(0).setAdapter(taxAdapter);
//                                taxListDataField.add(expensesLevelTwoData.get(j));
//                            }
//                        }
//                        taxAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, taxList);
//                        AutoCompleteTextView autocomp = (AutoCompleteTextView) taxListView.get(0).findViewById(R.id.exp_education_list_autocomplete);
//                        autocomp.setAdapter(taxAdapter);
//                    }
//
//
//                    if (checkString.equalsIgnoreCase("Transportation")) {
//                        String level1Id = expensesLevelOneData.get(i).getId();
//                        transportationDataField.add(expensesLevelOneData.get(i));
//                        for (int j = 0; j < expensesLevelTwoData.size(); j++) {
//                            String level2Id = expensesLevelTwoData.get(j).getLev1_id();
//                            if (level1Id.equalsIgnoreCase(level2Id)) {
//                                String leveltwoname = expensesLevelTwoData.get(j).getLev2_name();
//                                if (leveltwoname.equalsIgnoreCase("Travel")) {
//                                    String travelId = expensesLevelTwoData.get(j).getId();
//                                    travelDataField.add(expensesLevelTwoData.get(j));
//                                    for (int k = 0; k < expensesLevelThreeData.size(); k++) {
//                                        String level3Id = expensesLevelThreeData.get(k).getLev2_id();
//                                        if (travelId.equalsIgnoreCase(level3Id)) {
//                                            travelList.add(expensesLevelThreeData.get(k).getLev3_name());
////                                            travalAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, travelList);
////                                            travelArray.get(0).setAdapter(travalAdapter);
//                                            travelListDataField.add(expensesLevelThreeData.get(k));
//                                        }
//                                    }
//                                    travalAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, travelList);
//                                    AutoCompleteTextView autocomp = (AutoCompleteTextView) travelListView.get(0).findViewById(R.id.exp_education_list_autocomplete);
//                                    autocomp.setAdapter(travalAdapter);
//                                }
//
//
//                                if (leveltwoname.equalsIgnoreCase("Commute")) {
//                                    String commuteId = expensesLevelTwoData.get(j).getId();
//                                    commuteDataField.add(expensesLevelTwoData.get(j));
//                                    for (int k = 0; k < expensesLevelThreeData.size(); k++) {
//                                        String level3Id = expensesLevelThreeData.get(k).getLev2_id();
//                                        if (commuteId.equalsIgnoreCase(level3Id)) {
//                                            commuteList.add(expensesLevelThreeData.get(k).getLev3_name());
////                                            commuteAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, commuteList);
////                                            commuteArray.get(0).setAdapter(commuteAdapter);
//                                            commuteListDataField.add(expensesLevelThreeData.get(k));
//                                        }
//                                    }
//                                    commuteAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, commuteList);
//                                    AutoCompleteTextView autocomp = (AutoCompleteTextView) travelListView.get(0).findViewById(R.id.exp_education_list_autocomplete);
//                                    autocomp.setAdapter(commuteAdapter);
//                                }
//
//
//                                if (leveltwoname.equalsIgnoreCase("Parking / Miscellaneous")) {
//                                    String parkingId = expensesLevelTwoData.get(j).getId();
//                                    parkingDataField.add(expensesLevelTwoData.get(j));
//                                    for (int k = 0; k < expensesLevelThreeData.size(); k++) {
//                                        String level3Id = expensesLevelThreeData.get(k).getLev2_id();
//                                        if (parkingId.equalsIgnoreCase(level3Id)) {
//
//                                        }
//                                    }
//                                }
//
//                                if (leveltwoname.equalsIgnoreCase("Petrol / Diesel / Gas")) {
//                                    String petrolId = expensesLevelTwoData.get(j).getId();
//                                    petrolDataField.add(expensesLevelTwoData.get(j));
//                                    for (int k = 0; k < expensesLevelThreeData.size(); k++) {
//                                        String level3Id = expensesLevelThreeData.get(k).getLev2_id();
//                                        if (petrolId.equalsIgnoreCase(level3Id)) {
//
//                                        }
//                                    }
//                                }
//
//
//                                if (leveltwoname.equalsIgnoreCase("Vehicle Maintenance")) {
//                                    String vechicleId = expensesLevelTwoData.get(j).getId();
//                                    vechicleDataField.add(expensesLevelTwoData.get(j));
//                                    for (int k = 0; k < expensesLevelThreeData.size(); k++) {
//                                        String level3Id = expensesLevelThreeData.get(k).getLev2_id();
//                                        if (vechicleId.equalsIgnoreCase(level3Id)) {
//
//                                        }
//                                    }
//                                }
//
//                            }
//                        }
//                    }
//
//
//                    if (checkString.equalsIgnoreCase("Utility")) {
//                        String level1Id = expensesLevelOneData.get(i).getId();
//                        utilityDataField.add(expensesLevelOneData.get(i));
//                        for (int j = 0; j < expensesLevelTwoData.size(); j++) {
//                            String level2Id = expensesLevelTwoData.get(j).getLev1_id();
//                            if (level1Id.equalsIgnoreCase(level2Id)) {
//                                utilityList.add(expensesLevelTwoData.get(j).getLev2_name());
////                                utilityAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, utilityList);
////                                utilityArray.get(0).setAdapter(utilityAdapter);
//                                utilityListDataField.add(expensesLevelTwoData.get(j));
//                            }
//                        }
//                        utilityAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, utilityList);
//                        AutoCompleteTextView autocomp = (AutoCompleteTextView) utilityListView.get(0).findViewById(R.id.exp_education_list_autocomplete);
//                        autocomp.setAdapter(commuteAdapter);
//                    }
//
//
//                    if (checkString.equalsIgnoreCase("Gifts")) {
//                        String level1Id = expensesLevelOneData.get(i).getId();
//                        giftsDataField.add(expensesLevelOneData.get(i));
//                        for (int j = 0; j < expensesLevelTwoData.size(); j++) {
//                            String level2Id = expensesLevelTwoData.get(j).getLev1_id();
//                            if (level1Id.equalsIgnoreCase(level2Id)) {
//                            }
//                        }
//                    }
//
//
//                    if (checkString.equalsIgnoreCase("Other(s)")) {
//                        String level1Id = expensesLevelOneData.get(i).getId();
//                        otherDataField.add(expensesLevelOneData.get(i));
//                        for (int j = 0; j < expensesLevelTwoData.size(); j++) {
//                            String level2Id = expensesLevelTwoData.get(j).getLev1_id();
//                            if (level1Id.equalsIgnoreCase(level2Id)) {
//                            }
//                        }
//                    }
//
//                }
//
//            }
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.exp_food_disc_checkbox:
//                if (mExpensesFoodCheckBox.isChecked()) {
//                    mExpensesEatingOutLayout.setVisibility(View.VISIBLE);
//                } else {
//                    mExpensesEatingOutLayout.setVisibility(View.GONE);
//                }
//                break;
//            case R.id.exp_healthcare_checkbox:
//                if (mExpensesHealthcareCheckBox.isChecked()) {
//                    mExpensesHealthCareLayout.setVisibility(View.VISIBLE);
//                } else {
//                    mExpensesHealthCareLayout.setVisibility(View.GONE);
//                }
//                break;
//            case R.id.exp_purchases_checkbox:
//                if (mExpensesPurchaseCheckBox.isChecked()) {
//                    mExpensesPurchasesLayout.setVisibility(View.VISIBLE);
//
//                } else {
//                    mExpensesPurchasesLayout.setVisibility(View.GONE);
//                }
//                break;
//            case R.id.exp_shelter_checkbox:
//                if (mExpensesShelterCheckBox.isChecked()) {
//                    mExpensesShelterLayout.setVisibility(View.VISIBLE);
//
//                } else {
//                    mExpensesShelterLayout.setVisibility(View.GONE);
//                }
//                break;
//            case R.id.exp_skilldevelopment_checkbox:
//                if (mExpensesSkillDevelopCheckBox.isChecked()) {
//                    mExpensesSkillDevelopmmentLayout.setVisibility(View.VISIBLE);
//
//                } else {
//                    mExpensesSkillDevelopmmentLayout.setVisibility(View.GONE);
//                }
//                break;
//            case R.id.exp_tax_checkbox:
//                if (mExpensesTaxCheckBox.isChecked()) {
//                    mExpensesTaxLayout.setVisibility(View.VISIBLE);
//
//                } else {
//                    mExpensesTaxLayout.setVisibility(View.GONE);
//                }
//                break;
//            case R.id.exp_commute_checkbox:
//                if (mExpensesCommuteCheckBox.isChecked()) {
//                    mExpensesCommuteLayout.setVisibility(View.VISIBLE);
//
//                } else {
//                    mExpensesCommuteLayout.setVisibility(View.GONE);
//                }
//                break;
//
//            case R.id.exp_travel_checkbox:
//                if (mExpensesTravelCheckBox.isChecked()) {
//                    mExpensesTravelLayout.setVisibility(View.VISIBLE);
//
//                } else {
//                    mExpensesTravelLayout.setVisibility(View.GONE);
//                }
//                break;
//            case R.id.exp_utility_checkbox:
//                if (mExpennsesUtilityCheckBox.isChecked()) {
//                    mExpensesUtilityLayout.setVisibility(View.VISIBLE);
//
//                } else {
//                    mExpensesUtilityLayout.setVisibility(View.GONE);
//                }
//                break;
//            case R.id.exp_food_disc_edt_popup:
//                showPopupDialog(R.id.exp_food_disc_edt_popup);
//                break;
//            case R.id.exp_eatingout_edt_popup:
//                showPopupDialog(R.id.exp_eatingout_edt_popup);
//                break;
//            case R.id.exp_gifts_edt_popup:
//                showPopupDialog(R.id.exp_gifts_edt_popup);
//                break;
//            case R.id.exp_healthcare_edt_popup:
//                showPopupDialog(R.id.exp_healthcare_edt_popup);
//                break;
////            case R.id.exp_healthcare_list_edt_popup:
////                showPopupDialog(R.id.exp_healthcare_list_edt_popup);
////                break;
//            case R.id.exp_holidayvacation_edt_popup:
//                showPopupDialog(R.id.exp_holidayvacation_edt_popup);
//                break;
//            case R.id.exp_parentssupport_edt_popup:
//                showPopupDialog(R.id.exp_parentssupport_edt_popup);
//                break;
//            case R.id.exp_petcare_edt_popup:
//                showPopupDialog(R.id.exp_petcare_edt_popup);
//                break;
//            case R.id.exp_purchases_edt_popup:
//                showPopupDialog(R.id.exp_purchases_edt_popup);
//                break;
////            case R.id.exp_purchases_list_edt_popup:
////                showPopupDialog(R.id.exp_purchases_list_edt_popup);
////                break;
//            case R.id.exp_shelter_edt_popup:
//                showPopupDialog(R.id.exp_shelter_edt_popup);
//                break;
////            case R.id.exp_shelter_list_edt_popup:
////                showPopupDialog(R.id.exp_shelter_list_edt_popup);
////                break;
//            case R.id.exp_skilldevelopment_edt_popup:
//                showPopupDialog(R.id.exp_skilldevelopment_edt_popup);
//                break;
////            case R.id.exp_skilldevelopment_list_edt_popup:
////                showPopupDialog(R.id.exp_skilldevelopment_list_edt_popup);
////                break;
//            case R.id.exp_tax_edt_popup:
//                showPopupDialog(R.id.exp_tax_edt_popup);
//                break;
////            case R.id.exp_tax_list_edt_popup:
////                showPopupDialog(R.id.exp_tax_list_edt_popup);
////                break;
//            case R.id.exp_transportation_edt_popup:
//                showPopupDialog(R.id.exp_transportation_edt_popup);
//                break;
//            case R.id.exp_commute_edt_popup:
//                showPopupDialog(R.id.exp_commute_edt_popup);
//                break;
////            case R.id.exp_commute_list_edt_popup:
////                showPopupDialog(R.id.exp_commute_list_edt_popup);
////                break;
//            case R.id.exp_parking_edt_popup:
//                showPopupDialog(R.id.exp_parking_edt_popup);
//                break;
//            case R.id.exp_petrol_edt_popup:
//                showPopupDialog(R.id.exp_petrol_edt_popup);
//                break;
//            case R.id.exp_travel_edt_popup:
//                showPopupDialog(R.id.exp_travel_edt_popup);
//                break;
////            case R.id.exp_travel_list_edt_popup:
////                showPopupDialog(R.id.exp_travel_list_edt_popup);
////                break;
//            case R.id.exp_vechile_edt_popup:
//                showPopupDialog(R.id.exp_vechile_edt_popup);
//                break;
//            case R.id.exp_utility_edt_popup:
//                showPopupDialog(R.id.exp_utility_edt_popup);
//                break;
////            case R.id.exp_utility_list_edt_popup:
////                showPopupDialog(R.id.exp_utility_list_edt_popup);
////                break;
//            case R.id.exp_other_edt_popup:
//                showPopupDialog(R.id.exp_other_edt_popup);
//                break;
////            case R.id.exp_healthcare_list_add_imgView:
////                addView(mExpensesHealthCareLayout, "healther", healthcareArray.size());
////                break;
////            case R.id.exp_purchases_list_add_imgView:
////                addView(mExpensesPurchasesLayout, "purchases", purchaseArray.size());
////                break;
////            case R.id.exp_shelter_list_add_imgView:
////                addView(mExpensesShelterLayout, "shelter", shelterArray.size());
////                break;
////            case R.id.exp_skilldevelopment_list_add_imgView:
////                addView(mExpensesSkillDevelopmmentLayout, "skilldevelopment", skilldevelopmentArray.size());
////                break;
////            case R.id.exp_tax_list_add_imgView:
////                addView(mExpensesTaxLayout, "tax", taxArray.size());
////                break;
////            case R.id.exp_commute_list_add_imgView:
////                addView(mExpensesCommuteLayout, "commute", commuteArray.size());
////                break;
////            case R.id.exp_travel_list_add_imgView:
////                addView(mExpensesTravelLayout, "travel", travelArray.size());
////                break;
////            case R.id.exp_utility_list_add_imgView:
////                addView(mExpensesUtilityLayout, "utility", utilityArray.size());
////                break;
//        }
//    }
//
//    private void showPopupDialog(int clickedId) {
//        FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
//        DialogFragment newFragment = ExpectedIncrementDialogFragment.newInstance(this, clickedId);
//        newFragment.show(fm, "dialog");
//    }
//
//    private void addListView(final LinearLayout parentView, final String idNAme, final int position, final ArrayList<View> viewList, final ArrayList<String> arrayList) {
//        final View educationview = LayoutInflater.from(mContext).inflate(R.layout.add_row_view, null);
//        educationview.setId(position);
//        viewList.add(educationview);
//
//        ImageView addButton = (ImageView) viewList.get(position).findViewById(R.id.exp_education_list_add_imgView);
//        ImageView popupButton = (ImageView) viewList.get(position).findViewById(R.id.exp_education_list_edt_popup);
//        ImageView deleteButton = (ImageView) viewList.get(position).findViewById(R.id.exp_education_list_delete_imgView);
//        deleteButton.setId(position);
//
//        AutoCompleteTextView autocomplete = (AutoCompleteTextView) viewList.get(position).findViewById(R.id.exp_education_list_autocomplete);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, arrayList);
//        autocomplete.setAdapter(adapter);
//
//
//        popupButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupDialog(view.getId());
//                setCheckIsEntertainId(idNAme);
//                setSetPosition(position);
//            }
//        });
//
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.setVisibility(View.GONE);
//                addListView(parentView, "healthcare", viewList.size(), viewList, arrayList);
//            }
//        });
//
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                healthcaredeletePos(view, parentView, viewList);
//            }
//        });
//        parentView.addView(educationview);
//    }
//
//
//    private void showListView(final LinearLayout parentView, final String idNAme, final int position,
//                              final ArrayList<View> viewList, final ArrayList<String> arrayList, String str, String amount) {
//        final View educationview = LayoutInflater.from(mContext).inflate(R.layout.add_row_view, null);
//        educationview.setId(position);
//        viewList.add(educationview);
//
//        ImageView addButton = (ImageView) viewList.get(position).findViewById(R.id.exp_education_list_add_imgView);
//        ImageView popupButton = (ImageView) viewList.get(position).findViewById(R.id.exp_education_list_edt_popup);
//        ImageView deleteButton = (ImageView) viewList.get(position).findViewById(R.id.exp_education_list_delete_imgView);
//        deleteButton.setId(position);
//        EditText editText = (EditText) viewList.get(position).findViewById(R.id.exp_education_list_edt);
//        editText.setText(amount);
//
//        AutoCompleteTextView autocomplete = (AutoCompleteTextView) viewList.get(position).findViewById(R.id.exp_education_list_autocomplete);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, arrayList);
//        autocomplete.setAdapter(adapter);
//
//
//        autocomplete.setSelection(0);
//        popupButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupDialog(view.getId());
//                setCheckIsEntertainId(idNAme);
//                setSetPosition(position);
//            }
//        });
//
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.setVisibility(View.GONE);
//                addListView(parentView, "healthcare", viewList.size(), viewList, arrayList);
//            }
//        });
//
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                healthcaredeletePos(view, parentView, viewList);
//            }
//        });
//        parentView.addView(educationview);
//    }
//
//
//    private void healthcaredeletePos(final View view, final LinearLayout layout, final ArrayList<View> viewList) {
//        if (view.getId() > 0) {
//            layout.post(new Runnable() {
//                public void run() {
//                    layout.removeView(viewList.get(view.getId() - 1));
//                }
//            });
//            viewList.remove(view.getId() - 1);
//        }
//    }
//
//
//    private JSONArray getInputValues() throws JSONException {
//        jsonArray = new JSONArray();
//        String mfood, editingOut, gifts, healthcare,
//                healthcarelist, holidayvacation, parentsupport,
//                petcare, purchases, purchaseslist, shelter,
//                shelterlist, skilldevelopment, skilldevelopmentlist,
//                tax, taxlist, transportation, commute, commutelist,
//                parking, petrol, travel, travellist, vechile,
//                utility, utilitylist, other, str = null;
//
//        int moverallExpenses =0;
//        int  mfoodValue, editingOutValue, giftsValue, healthcareValue,
//                healthcarelistValue, holidayvacationValue, parentsupportValue,
//                petcareValue, purchasesValue, purchaseslistValue, shelterValue,
//                shelterlistValue, skilldevelopmentValue, skilldevelopmentlistValue,
//                taxValue, taxlistValue, transportationValue, commuteValue, commutelistValue,
//                parkingValue, petrolValue, travelValue, travellistValue, vechileValue,
//                utilityValue, utilitylistValue, otherValue;
//
//
//        mfood = getStringwithoutCurreny(mfoodEdt);
//        editingOut = getStringwithoutCurreny(mEditingOutEdt);
//        gifts = getStringwithoutCurreny(mgiftsEdt);
//        healthcare = getStringwithoutCurreny(mhealthcarExpensesEdt);
//        //healthcarelist = mhealthcareListEdt.getText().toString();
//        holidayvacation = getStringwithoutCurreny(mholidayVacationEdt);
//        parentsupport = getStringwithoutCurreny(mparentSupportEdt);
//        petcare = getStringwithoutCurreny(mPetCareEdt);
//        purchases = getStringwithoutCurreny(mPurchasesEdt);
//        //purchaseslist = mPurchasesListEdt.getText().toString();
//        shelter = getStringwithoutCurreny(mShelterEdt);
//        //shelterlist = mShelterListEdt.getText().toString();
//        skilldevelopment = getStringwithoutCurreny(mskillDevelopmentEdt);
//        ///skilldevelopmentlist = mskillDevelopmentListEdt.getText().toString();
//        tax = getStringwithoutCurreny(mtaxEdt);
//        //taxlist = mtaxListEdt.getText().toString();
//        transportation = getStringwithoutCurreny(mtransportationEdt);
//        commute = getStringwithoutCurreny(mcommuteEdt);
//        ///commutelist = mcommuteListEdt.getText().toString();
//        parking = getStringwithoutCurreny(mPakingEdt);
//        petrol = getStringwithoutCurreny(mpetrolEdt);
//        travel = getStringwithoutCurreny(mtravelEdt);
//        //travellist = mtravelListEdt.getText().toString();
//        vechile = getStringwithoutCurreny(mVechileEdt);
//        utility = getStringwithoutCurreny(mutilityEdt);
//        //utilitylist =mutilityListEdt.getText().toString();
//        other = mOtherEdt.getText().toString();
//
//
//        if (UtileKit.validateObjectValues(mfood)) {
//            mfoodValue = Integer.valueOf(mfood);
//            moverallExpenses =+ mfoodValue;
//            addToJsonObject(foodDataField.get(0).getTb_field_name(), mfood);
//        }
//
//        if (UtileKit.validateObjectValues(editingOut)) {
//            editingOutValue = Integer.valueOf(editingOut);
//            moverallExpenses =+ editingOutValue;
//            addToJsonObject(foodListDataField.get(0).getTb_field_name(), editingOut);
//        }
//
//        if (UtileKit.validateObjectValues(gifts)) {
//            giftsValue = Integer.valueOf(gifts);
//            moverallExpenses =+ giftsValue;
//            addToJsonObject(giftsDataField.get(0).getTb_field_name(), gifts);
//        }
//
//        if (UtileKit.validateObjectValues(healthcare)) {
//            healthcareValue = Integer.valueOf(healthcare);
//            moverallExpenses =+ healthcareValue;
//            addToJsonObject(healthcareDataField.get(0).getTb_field_name().toString(), healthcare);
//        }
//
////        if(UtileKit.validateObjectValues(healthcarelist)){
////            addToJsonObject(healthcareListDataField.get(0).getTb_field_name().toString(), healthcarelist);
////        }
//
//
//        int size = healthcareListView.size();
//        for (int i = 0; i < size; i++) {
//            String getfromautocomplete, getfieldname;
//            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) healthcareListView.get(i).findViewById(R.id.exp_education_list_autocomplete);
//            CurrencyEditText eduvalue = (CurrencyEditText) healthcareListView.get(i).findViewById(R.id.exp_education_list_edt);
//
//            healthcarelist = eduvalue.getText().toString();
//            getfromautocomplete = autoCompleteTextView.getText().toString();
//
//            if(UtileKit.validateObjectValues(healthcarelist)) {
//              int  healthcareListValue = Integer.valueOf(healthcarelist);
//                moverallExpenses = +healthcareListValue;
//            }
//
//            for (int j = 0; j < healthcareListDataField.size(); j++) {
//                if (getfromautocomplete.equalsIgnoreCase(healthcareListDataField.get(j).getLev2_name())) {
//                    getfieldname = healthcareListDataField.get(j).getTb_field_name();
//                   // //Log.e("getfieldname", "" + getfieldname);
//                   // //Log.e("healthcarelist", "" + healthcarelist);
//                    addToJsonObject(getfieldname, healthcarelist);
//                }
//            }
//
//        }
//
//
//        if (UtileKit.validateObjectValues(holidayvacation)) {
//             holidayvacationValue = Integer.valueOf(holidayvacation);
//            moverallExpenses =+ holidayvacationValue;
//            addToJsonObject(holidayAndVacationDataField.get(0).getTb_field_name().toString(), holidayvacation);
//        }
//
//        if (UtileKit.validateObjectValues(parentsupport)) {
//            parentsupportValue = Integer.valueOf(parentsupport);
//            moverallExpenses =+ parentsupportValue;
//            addToJsonObject(parentSupportDataField.get(0).getTb_field_name().toString(), parentsupport);
//        }
//
//
////        if(UtileKit.validateObjectValues(parentsupport)){
////            addToJsonObject(personalCareDataField.get(0).toString(), mfineAndPenalities);
////        }
//
//        if (UtileKit.validateObjectValues(petcare)) {
//            petcareValue = Integer.valueOf(petcare);
//            moverallExpenses =+ petcareValue;
//            addToJsonObject(petCareDataField.get(0).getTb_field_name().toString(), petcare);
//        }
//
//        if (UtileKit.validateObjectValues(purchases)) {
//            purchasesValue = Integer.valueOf(purchases);
//            moverallExpenses =+ purchasesValue;
//            addToJsonObject(purchasesDataField.get(0).getTb_field_name().toString(), purchases);
//        }
//
//
////        if(UtileKit.validateObjectValues(purchaseslist)){
////            addToJsonObject(purchasesListDataField.get(0).getTb_field_name().toString(), purchaseslist);
////        }
//
//        if (UtileKit.validateObjectValues(shelter)) {
//            shelterValue = Integer.valueOf(shelter);
//            moverallExpenses =+ shelterValue;
//            addToJsonObject(shelterDataField.get(0).getTb_field_name().toString(), shelter);
//        }
//
////        if(UtileKit.validateObjectValues(shelterlist)){
////            addToJsonObject(shelterListDataField.get(0).getTb_field_name().toString(), shelterlist);
////        }
//
//
//        if (UtileKit.validateObjectValues(skilldevelopment)) {
//            skilldevelopmentValue = Integer.valueOf(skilldevelopment);
//            moverallExpenses =+ skilldevelopmentValue;
//            addToJsonObject(skillDevelopmentDataField.get(0).getTb_field_name().toString(), skilldevelopment);
//        }
//
////        if(UtileKit.validateObjectValues(skilldevelopmentlist)){
////            addToJsonObject(skillDevelopmentListDataField.get(0).getTb_field_name().toString(), skilldevelopmentlist);
////        }
//
//        if (UtileKit.validateObjectValues(tax)) {
//            taxValue = Integer.valueOf(tax);
//            moverallExpenses =+ taxValue;
//            addToJsonObject(taxDataField.get(0).getTb_field_name().toString(), tax);
//        }
//
////        if(UtileKit.validateObjectValues(taxlist)){
////            addToJsonObject(taxListDataField.get(0).getTb_field_name().toString(), taxlist);
////        }
//
//
//        if (UtileKit.validateObjectValues(transportation)) {
//            transportationValue = Integer.valueOf(transportation);
//            moverallExpenses =+ transportationValue;
//            addToJsonObject(transportationDataField.get(0).getTb_field_name().toString(), transportation);
//        }
//
//
//        if (UtileKit.validateObjectValues(travel)) {
//            travelValue = Integer.valueOf(travel);
//            moverallExpenses =+ travelValue;
//            addToJsonObject(travelDataField.get(0).getTb_field_name().toString(), travel);
//        }
//
////        if(UtileKit.validateObjectValues(travellist)){
////            addToJsonObject(travelListDataField.get(0).getTb_field_name().toString(), travellist);
////        }
//
//        if (UtileKit.validateObjectValues(commute)) {
//            commuteValue = Integer.valueOf(commute);
//            moverallExpenses =+ commuteValue;
//            addToJsonObject(commuteDataField.get(0).getTb_field_name().toString(), commute);
//        }
//
//
////        if(UtileKit.validateObjectValues(commutelist)){
////            addToJsonObject(commuteListDataField.get(0).getTb_field_name().toString(), commutelist);
////        }
//
//        if (UtileKit.validateObjectValues(utility)) {
//            utilityValue = Integer.valueOf(utility);
//            moverallExpenses =+ utilityValue;
//            addToJsonObject(utilityDataField.get(0).getTb_field_name().toString(), utility);
//        }
//
//         overallValue =  String.valueOf(moverallExpenses);
//       // addToJsonObject("overallexpenses", overallValue);
//       // OverallExpenses = overallValue;
////        , , , , ,
////        parking, petrol, , , vechile,
////        , , other,
////
//
////        if(UtileKit.validateObjectValues(utilitylist)){
////            addToJsonObject(utilityListDataField.get(0).getTb_field_name().toString(), utilitylist);
////        }
//
////        jsonEssentialObject.put("exp_det", jsonArray);
////        str = jsonEssentialObject.toString();
////        return str;
//       return   jsonArray;
//    }
//
//    private void addToJsonObject(String field, String value) throws JSONException {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("field", field);
//        jsonObject.put("value", value);
//        jsonArray.put(jsonObject);
//    }
//
//
//    @Override
//    public void onValueSet(ExpectedIncrementDialogFragment dialog, String age, String percentage, int id) throws JSONException {
//        String contribution_info_value = null;
//        if (UtileKit.validateObjectValues(age) && UtileKit.validateObjectValues(percentage)) {
//            contribution_info_value = percentage.concat(",").concat(age);
//        } else if (UtileKit.validateObjectValues(percentage)) {
//            contribution_info_value = percentage;
//        } else if (UtileKit.validateObjectValues(age)) {
//            contribution_info_value = age;
//        }
//        switch (id) {
//            case R.id.exp_food_disc_edt_popup:
//                if (UtileKit.validateObjectValues(contribution_info_value)) {
//                    String contribution_info_field = foodDataField.get(0).getTb_field_name().toString().concat("_info");
//                    addToJsonObject(contribution_info_field, contribution_info_value);
//                    // //Log.e("afdsasdf",""+contribution_info_field+"value"+contribution_info_value);
//                }
//                break;
//            case R.id.exp_eatingout_edt_popup:
//                if (UtileKit.validateObjectValues(contribution_info_value)) {
//                    String contribution_info_field = foodListDataField.get(0).getTb_field_name().toString().concat("_info");
//                    addToJsonObject(contribution_info_field, contribution_info_value);
//                    // //Log.e("afdsasdf",""+contribution_info_field+"value"+contribution_info_value);
//                }
//                break;
//            case R.id.exp_gifts_edt_popup:
//                if (UtileKit.validateObjectValues(contribution_info_value)) {
//                    String contribution_info_field = giftsDataField.get(0).getTb_field_name().toString().concat("_info");
//                    addToJsonObject(contribution_info_field, contribution_info_value);
//                    // //Log.e("afdsasdf",""+contribution_info_field+"value"+contribution_info_value);
//                }
//                break;
//            case R.id.exp_healthcare_edt_popup:
//                if (UtileKit.validateObjectValues(contribution_info_value)) {
//                    String contribution_info_field = healthcareDataField.get(0).getTb_field_name().toString().concat("_info");
//                    addToJsonObject(contribution_info_field, contribution_info_value);
//                    // //Log.e("afdsasdf",""+contribution_info_field+"value"+contribution_info_value);
//                }
//                break;
//            case R.id.exp_holidayvacation_edt_popup:
//                if (UtileKit.validateObjectValues(contribution_info_value)) {
//                    String contribution_info_field = holidayAndVacationDataField.get(0).getTb_field_name().toString().concat("_info");
//                    addToJsonObject(contribution_info_field, contribution_info_value);
//                    // //Log.e("afdsasdf",""+contribution_info_field+"value"+contribution_info_value);
//                }
//                break;
//            case R.id.exp_parentssupport_edt_popup:
//                if (UtileKit.validateObjectValues(contribution_info_value)) {
//                    String contribution_info_field = parentSupportDataField.get(0).getTb_field_name().toString().concat("_info");
//                    addToJsonObject(contribution_info_field, contribution_info_value);
//                    // //Log.e("afdsasdf",""+contribution_info_field+"value"+contribution_info_value);
//                }
//                break;
//            case R.id.exp_petcare_edt_popup:
//                if (UtileKit.validateObjectValues(contribution_info_value)) {
//                    String contribution_info_field = petCareDataField.get(0).getTb_field_name().toString().concat("_info");
//                    addToJsonObject(contribution_info_field, contribution_info_value);
//                    // //Log.e("afdsasdf",""+contribution_info_field+"value"+contribution_info_value);
//                }
//                break;
//            case R.id.exp_purchases_edt_popup:
//                if (UtileKit.validateObjectValues(contribution_info_value)) {
//                    String contribution_info_field = purchasesDataField.get(0).getTb_field_name().toString().concat("_info");
//                    addToJsonObject(contribution_info_field, contribution_info_value);
//                    // //Log.e("afdsasdf",""+contribution_info_field+"value"+contribution_info_value);
//                }
//                break;
//            case R.id.exp_shelter_edt_popup:
//                if (UtileKit.validateObjectValues(contribution_info_value)) {
//                    String contribution_info_field = shelterDataField.get(0).getTb_field_name().toString().concat("_info");
//                    addToJsonObject(contribution_info_field, contribution_info_value);
//                    // //Log.e("afdsasdf",""+contribution_info_field+"value"+contribution_info_value);
//                }
//                break;
//            case R.id.exp_skilldevelopment_edt_popup:
//                if (UtileKit.validateObjectValues(contribution_info_value)) {
//                    String contribution_info_field = skillDevelopmentDataField.get(0).getTb_field_name().toString().concat("_info");
//                    addToJsonObject(contribution_info_field, contribution_info_value);
//                    // //Log.e("afdsasdf",""+contribution_info_field+"value"+contribution_info_value);
//                }
//                break;
//            case R.id.exp_tax_edt_popup:
//                if (UtileKit.validateObjectValues(contribution_info_value)) {
//                    String contribution_info_field = taxDataField.get(0).getTb_field_name().toString().concat("_info");
//                    addToJsonObject(contribution_info_field, contribution_info_value);
//                    // //Log.e("afdsasdf",""+contribution_info_field+"value"+contribution_info_value);
//                }
//                break;
//            case R.id.exp_transportation_edt_popup:
//                if (UtileKit.validateObjectValues(contribution_info_value)) {
//                    String contribution_info_field = transportationDataField.get(0).getTb_field_name().toString().concat("_info");
//                    addToJsonObject(contribution_info_field, contribution_info_value);
//                    // //Log.e("afdsasdf",""+contribution_info_field+"value"+contribution_info_value);
//                }
//                break;
//            case R.id.exp_commute_edt_popup:
//                if (UtileKit.validateObjectValues(contribution_info_value)) {
//                    String contribution_info_field = commuteDataField.get(0).getTb_field_name().toString().concat("_info");
//                    addToJsonObject(contribution_info_field, contribution_info_value);
//                    // //Log.e("afdsasdf",""+contribution_info_field+"value"+contribution_info_value);
//                }
//                break;
//            case R.id.exp_parking_edt_popup:
//                if (UtileKit.validateObjectValues(contribution_info_value)) {
//                    String contribution_info_field = parkingDataField.get(0).getTb_field_name().toString().concat("_info");
//                    addToJsonObject(contribution_info_field, contribution_info_value);
//                    // //Log.e("afdsasdf",""+contribution_info_field+"value"+contribution_info_value);
//                }
//                break;
//            case R.id.exp_petrol_edt_popup:
//                if (UtileKit.validateObjectValues(contribution_info_value)) {
//                    String contribution_info_field = petrolDataField.get(0).getTb_field_name().toString().concat("_info");
//                    addToJsonObject(contribution_info_field, contribution_info_value);
//                    // //Log.e("afdsasdf",""+contribution_info_field+"value"+contribution_info_value);
//                }
//                break;
//            case R.id.exp_travel_edt_popup:
//                if (UtileKit.validateObjectValues(contribution_info_value)) {
//                    String contribution_info_field = travelDataField.get(0).getTb_field_name().toString().concat("_info");
//                    addToJsonObject(contribution_info_field, contribution_info_value);
//                    // //Log.e("afdsasdf",""+contribution_info_field+"value"+contribution_info_value);
//                }
//                break;
//            case R.id.exp_vechile_edt_popup:
//                if (UtileKit.validateObjectValues(contribution_info_value)) {
//                    String contribution_info_field = vechicleDataField.get(0).getTb_field_name().toString().concat("_info");
//                    addToJsonObject(contribution_info_field, contribution_info_value);
//                    // //Log.e("afdsasdf",""+contribution_info_field+"value"+contribution_info_value);
//                }
//                break;
//            case R.id.exp_utility_edt_popup:
//                if (UtileKit.validateObjectValues(contribution_info_value)) {
//                    String contribution_info_field = utilityDataField.get(0).getTb_field_name().toString().concat("_info");
//                    addToJsonObject(contribution_info_field, contribution_info_value);
//                    // //Log.e("afdsasdf",""+contribution_info_field+"value"+contribution_info_value);
//                }
//                break;
//            case R.id.exp_other_edt_popup:
//                if (UtileKit.validateObjectValues(contribution_info_value)) {
//                    String contribution_info_field = otherDataField.get(0).getTb_field_name().toString().concat("_info");
//                    addToJsonObject(contribution_info_field, contribution_info_value);
//                    // //Log.e("afdsasdf",""+contribution_info_field+"value"+contribution_info_value);
//                }
//                break;
//        }
//    }
//
//    public String getCheckIsEntertainId() {
//        return checkIsEntertainId;
//    }
//
//    public void setCheckIsEntertainId(String checkIsEntertainId) {
//        this.checkIsEntertainId = checkIsEntertainId;
//    }
//
//    public int getSetPosition() {
//        return setPosition;
//    }
//
//    public void setSetPosition(int setPosition) {
//        this.setPosition = setPosition;
//    }
//
//
//
//    public String getStringwithoutCurreny(CurrencyEditText edt){
//        String str =  edt.getText().toString().replace("₹","");
//        String str1 = str.replace("\u00A0","").replace(",","");;
//        return  str1;
//    }
//
//    private void getExpensesData(ArrayList<UpdateUserExpensesDetail> updateExpenses){
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getFood())) {
//            mfoodEdt.setText(updateExpenses.get(0).getFood());
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getFd_eat_out())) {
//            mEditingOutEdt.setText(updateExpenses.get(0).getFd_eat_out());
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getGifts())) {
//            mgiftsEdt.setText(updateExpenses.get(0).getGifts());
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getHealth_exp())) {
//            mhealthcarExpensesEdt.setText(updateExpenses.get(0).getHealth_exp());
//        }
//
//        ArrayList<String> healthcaretempList = new ArrayList<String>();
//        ArrayList<String> healthcareexpectedList = new ArrayList<String>();
//        ArrayList<String> healthcareName = new ArrayList<String>();
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getHe_dental())) {
//            healthcaretempList.add(updateExpenses.get(0).getHe_dental());
//            healthcareexpectedList.add(updateExpenses.get(0).getHe_dental_info());
//            //healthcareName.add(healthcareExpenses.get(0));
//            healthcareName.add("Dental");
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getHe_med())) {
//            healthcaretempList.add(updateExpenses.get(0).getHe_med());
//            healthcareexpectedList.add(updateExpenses.get(0).getHe_med_info());
//           // healthcareName.add(healthcareExpenses.get(1));
//            healthcareName.add("Medical");
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getHe_vision())) {
//            healthcaretempList.add(updateExpenses.get(0).getHe_vision());
//            healthcareexpectedList.add(updateExpenses.get(0).getHe_vision_info());
//            healthcareName.add("Vision");
//
//        }
//
//        showRowData(mExpensesHealthCareLayout, "Healthcare", healthcaretempList.size(),
//                healthcareListView, healthcaretempList, healthcareexpectedList, healthcareName);
//
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getHol_vac())) {
//            mholidayVacationEdt.setText(updateExpenses.get(0).getHol_vac());
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getParent_support())) {
//            mparentSupportEdt.setText(updateExpenses.get(0).getParent_support());
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getPet_care())) {
//            mPetCareEdt.setText(updateExpenses.get(0).getPet_care());
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getPurchases())) {
//            mPurchasesEdt.setText(updateExpenses.get(0).getPurchases());
//        }
//
//        ArrayList<String> purchasestempList = new ArrayList<String>();
//        ArrayList<String> purchasesexpectedList = new ArrayList<String>();
//        ArrayList<String> purchasesName = new ArrayList<String>();
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getPurch_appar_cloth())) {
//            purchasestempList.add(updateExpenses.get(0).getPurch_appar_cloth());
//            purchasesexpectedList.add(updateExpenses.get(0).getPurch_appar_cloth_info());
//          //  purchasesName.add(purchasesExpenses.get(0));
//            purchasesName.add("Apparels / Clothing");
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getPurch_houhold_items())) {
//            purchasestempList.add(updateExpenses.get(0).getPurch_houhold_items());
//            purchasesexpectedList.add(updateExpenses.get(0).getPurch_houhold_items_info());
//          //  purchasesName.add(purchasesExpenses.get(0));
//            purchasesName.add("Household Items");
//
//        }
//
//        showRowData(mExpensesPurchasesLayout, "Purchases", purchasestempList.size(),
//                purchasesListView, purchasestempList, purchasesexpectedList, purchasesName);
//
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getShelter())) {
//            mShelterEdt.setText(updateExpenses.get(0).getShelter());
//        }
//
//        ArrayList<String> sheltertempList = new ArrayList<String>();
//        ArrayList<String> shelterexpectedList = new ArrayList<String>();
//        ArrayList<String> shelterName = new ArrayList<String>();
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getSh_hm_maint())) {
//            sheltertempList.add(updateExpenses.get(0).getSh_hm_maint());
//            shelterexpectedList.add(updateExpenses.get(0).getSh_hm_maint_info());
//           // shelterName.add(shelterExpenses.get(0));
//            shelterName.add("Home Maintenance");
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getSh_rent())) {
//            sheltertempList.add(updateExpenses.get(0).getSh_rent());
//            shelterexpectedList.add(updateExpenses.get(0).getSh_rent_info());
//            //shelterName.add(shelterExpenses.get(1));
//            shelterName.add("Rent");
//        }
//
//        showRowData(mExpensesShelterLayout, "Shelter", sheltertempList.size(),
//                shelterListView, sheltertempList, shelterexpectedList, shelterName);
//
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getSkill_dev())) {
//            mskillDevelopmentEdt.setText(updateExpenses.get(0).getSkill_dev());
//           // showRowData(skilldevelopmenttempList.size(), skilldevelopmentListView, skilldevelopmentList ,mExpensesSkillDevelopmmentLayout);
//        }
//
//        ArrayList<String> skilldevelopmenttempList = new ArrayList<String>();
//        ArrayList<String> skilldevelopmentexpectedList = new ArrayList<String>();
//        ArrayList<String> skilldevelopmentName = new ArrayList<String>();
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getSk_books())) {
//            skilldevelopmenttempList.add(updateExpenses.get(0).getSk_books());
//            skilldevelopmentexpectedList.add(updateExpenses.get(0).getSk_books_info());
//            //skilldevelopmentName.add(skilldevelopmentList.get(0));
//            skilldevelopmentName.add("Books");
//
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getSk_class())) {
//            skilldevelopmenttempList.add(updateExpenses.get(0).getSk_class());
//            skilldevelopmentexpectedList.add(updateExpenses.get(0).getSk_class_info());
//            skilldevelopmentName.add("Class");
//
////            skilldevelopmentName.add(skilldevelopmentList.get(1));
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getSk_course())) {
//            skilldevelopmenttempList.add(updateExpenses.get(0).getSk_course());
//            skilldevelopmentexpectedList.add(updateExpenses.get(0).getSk_course_info());
//            //skilldevelopmentName.add(skilldevelopmentList.get(2));
//            skilldevelopmentName.add("Course");
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getSk_equip_mat())) {
//            skilldevelopmenttempList.add(updateExpenses.get(0).getSk_equip_mat());
//            skilldevelopmentexpectedList.add(updateExpenses.get(0).getSk_equip_mat_info());
//           // skilldevelopmentName.add(skilldevelopmentList.get(3));
//            skilldevelopmentName.add("Equipments / Materials");
//        }
//
//
//        showRowData(mExpensesSkillDevelopmmentLayout, "Skilldevelopment", skilldevelopmenttempList.size(),
//                skilldevelopmentListView, skilldevelopmenttempList, skilldevelopmentexpectedList, skilldevelopmentName);
//
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getTax())) {
//            mtaxEdt.setText(updateExpenses.get(0).getTax());
//           // showRowData(taxtempList.size(), taxListView, taxList , mExpensesTaxLayout);
//        }
//
//        ArrayList<String> taxtempList = new ArrayList<String>();
//        ArrayList<String> taxexpectedList = new ArrayList<String>();
//        ArrayList<String> taxName = new ArrayList<String>();
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getTax_in_tax())) {
//            taxtempList.add(updateExpenses.get(0).getTax_in_tax());
//            taxexpectedList.add(updateExpenses.get(0).getTax_in_tax_info());
//            taxName.add(taxList.get(0));
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getTax_prop_tax())) {
//            taxtempList.add(updateExpenses.get(0).getTax_prop_tax());
//            taxexpectedList.add(updateExpenses.get(0).getTax_prop_tax_info());
//            taxName.add("Income Tax");
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getTc_taxi_cab())) {
//            taxtempList.add(updateExpenses.get(0).getTc_taxi_cab());
//            taxexpectedList.add(updateExpenses.get(0).getTc_taxi_cab_info());
//            taxName.add("Property Tax");
//        }
//
//        showRowData(mExpensesTaxLayout, "tax", taxtempList.size(),  taxListView,
//                taxtempList, taxexpectedList, taxName);
//
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getTransport())) {
//            mtransportationEdt.setText(updateExpenses.get(0).getTransport());
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getTrans_commute())) {
//            mcommuteEdt.setText(updateExpenses.get(0).getTrans_commute());
//
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getTrans_park_misce())) {
//            mPakingEdt.setText(updateExpenses.get(0).getTrans_park_misce());
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getTrans_pet_diesel())) {
//            mpetrolEdt.setText(updateExpenses.get(0).getTrans_pet_diesel());
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getTrans_trav())) {
//            mtravelEdt.setText(updateExpenses.get(0).getTrans_trav());
//
////            ArrayList<String> traveltempList = new ArrayList<String>();
////            if(UtileKit.validateObjectValues(updateExpenses.get(0).gettrans_)) {
//////                AutoCompleteTextView healthcareautoview = (AutoCompleteTextView) healthcareListView.get(0).findViewById(R.id.exp_education_list_autocomplete);
//////                healthcareautoview
//////                showListView(mExpensesHealthCareLayout, "healthcare", healthcareListView.size(), healthcareListView, healthcareExpenses, "Dental");
////                traveltempList.add(updateExpenses.get(0).getSk_books());
////            }
////
////            if(UtileKit.validateObjectValues(updateExpenses.get(0).getSk_class())) {
////                traveltempList.add(updateExpenses.get(0).getSk_class());
////            }
////
////            if(UtileKit.validateObjectValues(updateExpenses.get(0).getSk_course())) {
////                traveltempList.add(updateExpenses.get(0).getSk_course());
////            }
////
////            if(UtileKit.validateObjectValues(updateExpenses.get(0).getSk_equip_mat())) {
////                traveltempList.add(updateExpenses.get(0).getSk_equip_mat());
////            }
////
////            showRowData(traveltempList.size(), skilldevelopmentListView, skilldevelopmentList ,
////                    mExpensesSkillDevelopmmentLayout);
//
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getTrans_veh_maint())) {
//            mVechileEdt.setText(updateExpenses.get(0).getTrans_veh_maint());
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getUtility())) {
//            mutilityEdt.setText(updateExpenses.get(0).getUtility());
//        }
//
//        ArrayList<String> utilitytempList = new ArrayList<String>();
//        ArrayList<String> utilityexpectedList = new ArrayList<String>();
//        ArrayList<String> utilityName = new ArrayList<String>();
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getUt_cab_sat())) {
////                AutoCompleteTextView healthcareautoview = (AutoCompleteTextView) healthcareListView.get(0).findViewById(R.id.exp_education_list_autocomplete);
////                healthcareautoview
////                showListView(mExpensesHealthCareLayout, "healthcare", healthcareListView.size(), healthcareListView, healthcareExpenses, "Dental");
//            utilitytempList.add(updateExpenses.get(0).getUt_cab_sat());
//            utilityexpectedList.add(updateExpenses.get(0).getUt_cab_sat_info());
//            //utilityName.add(utilityList.get(0));
//            utilityName.add("Cable / Satellite TV");
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getUt_elec())) {
//            utilitytempList.add(updateExpenses.get(0).getUt_elec());
//            utilityexpectedList.add(updateExpenses.get(0).getUt_elec_info());
//            //utilityName.add(utilityList.get(1));
//            utilityName.add("Electricity");
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getUt_gas())) {
//            utilitytempList.add(updateExpenses.get(0).getUt_gas());
//            utilityexpectedList.add(updateExpenses.get(0).getUt_gas_info());
//            //utilityName.add(utilityList.get(2));
//            utilityName.add("Gas");
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getUt_mb_ph())) {
//            utilitytempList.add(updateExpenses.get(0).getUt_mb_ph());
//            utilityexpectedList.add(updateExpenses.get(0).getUt_mb_ph_info());
//           // utilityName.add(utilityList.get(3));
//            utilityName.add("Mobile Phone");
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getUt_water())) {
//            utilitytempList.add(updateExpenses.get(0).getUt_water());
//            utilityexpectedList.add(updateExpenses.get(0).getUt_water_info());
//            //utilityName.add(utilityList.get(4));
//            utilityName.add("Water");
//        }
//
//        if(UtileKit.validateObjectValues(updateExpenses.get(0).getUt_int_board())) {
//            utilitytempList.add(updateExpenses.get(0).getUt_int_board());
//            utilityexpectedList.add(updateExpenses.get(0).getUt_int_board_info());
//            //utilityName.add(utilityList.get(4));
//            utilityName.add("Internet / Broadband");
//        }
//
//        showRowData(mExpensesUtilityLayout, "utility", utilitytempList.size(),  utilityListView,
//                utilitytempList, utilityexpectedList, utilityName);
//
//    }
//
//
//    private void showRowData(LinearLayout parentLayout, String str, int listSize,
//                             ArrayList<View> listView, ArrayList<String> info, ArrayList<String> expected, ArrayList<String> name){
//        if(listSize>0) {
//            for (int i = 0; i <listSize; i++) {
//                AutoCompleteTextView healthcareautoview = (AutoCompleteTextView) listView.get(i).findViewById(R.id.exp_education_list_autocomplete);
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, name);
//                healthcareautoview.setAdapter(adapter);
//                healthcareautoview.setText(name.get(i));
//
//                EditText editText = (EditText) listView.get(i).findViewById(R.id.exp_education_list_edt);
//                editText.setText(info.get(i));
//            }
//        }
//    }
//
//
//    private String getJsonArry()  {
//        String str = null;
//        try {
//            str = getInputValues().toString();
//            //Log.e("stringstring",""+str);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return str;
//    }
//
//    @Override
//    public void onDiscretionaryTabChangeCallService() {
//        //AddExpensesService();
////       ExpensesDetailsEssentialFragment.persistingAnonymePref("discretionary",getJsonArry());
////        ExpensesDetailsEssentialFragment.persistingAnonymePref("discreoverallexp", overallValue);
//    }
//
////    private void showRowData(int listSize, ArrayList<View> listView, ArrayList<String> autoCompleteList, LinearLayout parentLayout){
////        if(listSize>0){
////            if(listSize==1) {
////                for (int i = 0; i <listSize; i++) {
////                    AutoCompleteTextView healthcareautoview = (AutoCompleteTextView) listView.get(0).findViewById(R.id.exp_education_list_autocomplete);
////                    healthcareautoview.setSelection(i);
////                    EditText editText = (EditText) listView.get(0).findViewById(R.id.exp_education_list_edt);
////                    editText.setText("100");
////                }
////            }else{
////                for (int i = 0; i<listSize; i++) {
////                    AutoCompleteTextView healthcareautoview = (AutoCompleteTextView) listView.get(0).findViewById(R.id.exp_education_list_autocomplete);
////                    healthcareautoview.setSelection(i);
////                    EditText editText = (EditText) listView.get(0).findViewById(R.id.exp_education_list_edt);
////                    editText.setText("100");
////                    if(i>0) {
////                        showListView(parentLayout, "healthcare", listView.size()
////                                , listView, autoCompleteList, "Dental", "2000");
////                    }
////                }
////            }
////        }
////    }
//
//}