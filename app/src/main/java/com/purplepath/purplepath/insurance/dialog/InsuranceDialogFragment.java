package com.purplepath.purplepath.insurance.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.DialogFragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.calculator.CalculatorAct;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.fourmob.datetimepicker.Utils;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.google.gson.Gson;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assets.dialog.AssetDatePickerDialogFragment;
import com.purplepath.purplepath.assets.model.AssetCategoriesModel;
import com.purplepath.purplepath.calenderNumberPicker.calenderUi.CalenderTabs;
import com.purplepath.purplepath.customview.CalendarEditText;
import com.purplepath.purplepath.customview.CharacterEditText;
import com.purplepath.purplepath.customview.CurrencyEditText;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.customview.CustomCalenderImageView;
import com.purplepath.purplepath.customview.CustomSpinerAdapter;
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.goal.GoalFamilyDetails;
import com.purplepath.purplepath.goal.GoalFamilyDetailsModel;
import com.purplepath.purplepath.goal.InterfaceDone.FirstTimeDoneInterface;
import com.purplepath.purplepath.insurance.DialogFragmentInsurancesCallbackInterface;
import com.purplepath.purplepath.insurance.insuranceinterface.OnCheckListIsEmpty;
import com.purplepath.purplepath.insurance.model.AddInsuranceModel;
import com.purplepath.purplepath.insurance.model.GetInsuranceInputData;
import com.purplepath.purplepath.insurance.model.Ins_prod_cat;
import com.purplepath.purplepath.insurance.model.Ins_prod_type;
import com.purplepath.purplepath.insurance.model.Ins_sub_type;
import com.purplepath.purplepath.insurance.model.InsuranceCatogoryModel;
import com.purplepath.purplepath.insurance.model.Sub_insur;
import com.purplepath.purplepath.myinterface.DatePickerCallBackInterface;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.purplepath.purplepath.apputiles.UtileKit.INSURANCE_CATAGORY_PREF;
import static com.purplepath.purplepath.incomedetails.IncomeDynamicDetail.PARENT_CLASS_SOURCE;


public class InsuranceDialogFragment extends DialogFragment implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener,
        DatePickerCallBackInterface, CompoundButton.OnCheckedChangeListener {
    public static final String mpolicy_issue_date = "Policy Issue Date";
    public static final String mlast_prem_date = "Last Premium Paid Date";
    public static final String mnext_prem_date = "Next Premium Due Date";
    public static final String mprem_due_date = "Premium Due Till Date";
    public static final String mpolicy_end_date = "Policy End Date";
    public static final String mmaturity_date = "Maturity Date";
    public static final String myour_Year = "Your Year";
    public static final String mterm_years = "Terms In Year";
    public static final String minsurancePaidDueDate = "Insurance Paid Due Date";
    public static final String TITLE = "";
    public String str_insurancebelong = "";
    public HashMap<String, View> errorMapView = new HashMap<>();
    View nameEditview;
    ArrayList<String> lifeInsuranceSpinnerList = new ArrayList<>();
    ArrayList<Ins_prod_type> lifeInsuranceList = new ArrayList<>();
    ArrayList<String> generalInsuProSpinnerList = new ArrayList<>();
    ArrayList<Ins_prod_type> generalInsurProList = new ArrayList<>();
    ArrayList<Ins_prod_type> generalCatProList = new ArrayList<>();
    CheckBox mRiderCheckBtn, mTopUpCheckBtn, mSuperTopCheckBtn, mBasicPlanCheckBtn;
    ArrayList<Ins_sub_type> mIns_sub_type;
    ArrayList<Ins_prod_type> mIns_prod_type;
    ArrayList<Ins_prod_cat> mIns_prod_cat;

    Bundle mArgs, args = new Bundle();
    ArrayList<String> mRiderSpinnerList = new ArrayList<>(),
            mTopUpSpinnerList = new ArrayList<>(),
            mSuperTopSpinnerList = new ArrayList<>(),
            mBasicPlanSpinnerList = new ArrayList<>();
    ArrayList<Ins_prod_type> mRiderObjList = new ArrayList<>(),
            mTopUpObjList = new ArrayList<>(),
            mSuperTopObjList = new ArrayList<>(),
            mBasicPlanObjList = new ArrayList<>();

    private ArrayList<String> monthInArray = new ArrayList(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
            "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
            "51", "52", "53", "54", "55", "56", "57", "58", "59", "60"));

    private ArrayList<String>general_insuran_plan_type=new ArrayList(Arrays.asList("Personally Subscribed",
            "Employer Provided"));

    private ArrayList<String>general_insuran_motor_sub_plan_type=new ArrayList(Arrays.asList("Bike",
            "Car"));

    private ArrayList<String> termsInYear = new ArrayList<>(Arrays.asList("Quarterly", "Half yearly", "Annualy", "Monthly"));
    private int currentdateFirsttime, currentmonthFirsttime, currentyearFirsttime;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    //private EditText   ;
    private CharacterEditText mInsurNameEdt, mInsurNotesEdt;
    //private CharacterOnlyEdittext mInsurNameEdt;
    private CalendarEditText mInsurpolicyIssuedateEdt, mInsurlastpremiumpaidEdt, mInsurnextpreminumpaidEdt,
            mInsurpremiumduetilldateEdt, mInsurpolicyenddateEdt, mInsmaturitydateEdt, insur_paidduedate_edt;
    private PercentageEditText minsurExpincEdt;
    private DialogFragmentInsurancesCallbackInterface callbackInterfaceInsurances;
    // private CurrencyDefaultEdt mInsurSumAssuredEdt, mInsurAnnualperminumEdt;
    private CurrencyGhostView mInsurSumAssuredEdt, mInsurAnnualperminumEdt;
    private Activity activity;
    private AssetCategoriesModel assetCategoriesModel;
    private GetInsuranceInputData getInsuranceUserData;
    private Button assetButton;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private OnActivityBackPressedListener mCallBackListener;
    private LinearLayout otherLinearLayout, minsurance_family_layout, mGeneralInsurance_lay, mLifeInsuraceCatType_lay;
    private String InsID, UserID, insuranceType;
    private int size;
    private String policy_name, family_id, ins_type,
            coverage, annual_prem, exp_incr, policy_issue_date, last_prem_date, next_prem_date, prem_due_date,
            policy_end_date, maturity_date, term_years, notes, lastpaiddueDate, loan_freq_month;
    private ArrayList<GoalFamilyDetails> familyDetails = new ArrayList<GoalFamilyDetails>();
    private ArrayList<String> mgoalBelongsToArrayList = new ArrayList<String>();
    private ArrayList<String> mgoalBelongsToArrayListId = new ArrayList<String>();
    private Spinner mInsurBelongToSpinner, minsurTerminyearspinner, insu_loanFrequency_spinner;

    private AddInsuranceModel addInsuranceModel;
    private OnCheckListIsEmpty onCheckListIsEmpty;
    private ImageView backBtn;
    private TextView titleNameTxt;
    private EditText mCalanderEdt;
    private RelativeLayout policydateRelativeLayout, lastpremiumpaidRelativeLayout, nextpremiumpaidRelativeLayout, premiumduetilldateRelativeLayout, policyenddateRelativeLayout, maturitydateRelativeLayout;
    private GoalFamilyDetailsModel addFamilyDetailModel;
    private Date mPolicyIssueDate, mPolicyUpdateIssueDate;
    private ScrollView mScrollView;
    private TextInputLayout mInsurname_edtparent;
    //    private EditText mInsmaturitydateEdt1;
    private android.app.FragmentManager mFragmentManager;
    private AssetDatePickerDialogFragment assetDatePickerDialogFragment;
    private CustomCalenderImageView minsur_sumassured_calculaterImgView, minsur_annualpermium_calculaterImgView;
    private boolean isFirstTimeColor = false, isCheckSubType = false;
    private boolean isClicked;
    private Context mContext;
    private InsuranceCatogoryModel mInsuranceCatModel;
    private String lifeInsuranceCatSelected = "";
    private String insuraceProductType = "", insuraceSubType = "", mRiderCostType = "", mTopUpType = "", mSuperTopType = "", mBasicType = "";
    private Spinner mLifeInsuraceProTypeSpinner;
    private Spinner mGeneralSubTypeSpinner;
    private Spinner mgeneral_sub_insu_motor_type_spinner;
    private Spinner mGeneralProType_spinner;
    private CurrencyGhostView mRiderCostEdit, mTopUpEdit, mSuperTopEdit, mBasicPlanEdit;
    private Spinner mRiderCostSpinner, mTopUpSpinner, mSuperTopSpinner, mBasicPlanSpinner;
    private String mRiderCostId = "empty", mTopUpId = "empty", mSuperTopId = "empty", mBasicPlanId = "empty";
    private ArrayList<String> insSubTypeArray = new ArrayList<>();
    private String ins_detJson = "";

    private String[] mgeneral_ins_plan_Array;
    private Spinner general_insurance_plan_type_spinner;
    private String plan_type = "";
    private String motor_type = "";
    private LinearLayout sub_insu_motor_type_layout;

    ArrayList<String> formArray = new ArrayList<String>();
    Boolean isSignUp = false;
    private LinearLayout bottom_bar_layout,bottom_bar_donelayout;
    private RelativeLayout relative_finish_later,relative_done_arrow;

    FirstTimeDoneInterface firstTimeDoneInterface;

    FloatingActionButton fab;

    public InsuranceDialogFragment() {

    }

    public static InsuranceDialogFragment newInstance(OnCheckListIsEmpty onCheckListEmpty,
                                                      GoalFamilyDetailsModel addFamilyDetailModel,
                                                      FirstTimeDoneInterface firstTimeDoneInterface) {
        InsuranceDialogFragment fragment = new InsuranceDialogFragment();
        fragment.onCheckListIsEmpty = onCheckListEmpty;
        fragment.addFamilyDetailModel = addFamilyDetailModel;
        fragment.firstTimeDoneInterface=firstTimeDoneInterface;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.MY_DIALOG);
        activity = getActivity();
        mContext = getContext();
        calendar = new GregorianCalendar();
//        callbackInterfaceInsurances = this;
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), isVibrate());

        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        String catgoryJsonString = UtileKit.getPersistedPurplePathPref(INSURANCE_CATAGORY_PREF);
        if (catgoryJsonString != null) {
            Log.e("catgoryJsonString", "Tag->" + catgoryJsonString);
            try {
                Gson gson = new Gson();
                mInsuranceCatModel = gson.fromJson(catgoryJsonString, InsuranceCatogoryModel.class);
            } catch (Exception ex) {
//                callInsuranceCatagory();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.dialog_fragment_insurance, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentManager = getActivity().getFragmentManager();
        backBtn = view.findViewById(R.id.backButtonId);
        titleNameTxt = view.findViewById(R.id.dialogTitleId);
        fab = view.findViewById(R.id.insurance_tick_button);
        mScrollView = view.findViewById(R.id.scrollViewId);
        mInsurname_edtparent = view.findViewById(R.id.insur_name_edtparent);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputData();
                if (policy_name != null && !policy_name.isEmpty()) {
                    if (UtileKit.validateObjectValues(InsID)) {
                        callUpdateInsuranceService();
                        dismiss();
                    } else {
                        addInsurance();
                        dismiss();
                    }
                } else {
                    mInsurname_edtparent.setError(HomePageActivity.errorMessageInInsurance);
                    mInsurNameEdt.requestFocus();
                    mScrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            mScrollView.scrollTo(0, 0);
                        }
                    });

                    UtileKit.intitializeAlertDialog(
                            HomePageActivity.errorMessageInInsurance,
                            activity);

                }


            }
        });


        policydateRelativeLayout = view.findViewById(R.id.policydateRelativeLayout);
        lastpremiumpaidRelativeLayout = view.findViewById(R.id.lastpremiumpaidRelativeLayout);
        nextpremiumpaidRelativeLayout = view.findViewById(R.id.nextpremiumpaidRelativeLayout);
        premiumduetilldateRelativeLayout = view.findViewById(R.id.premiumduetilldateRelativeLayout);
        policyenddateRelativeLayout = view.findViewById(R.id.policyenddateRelativeLayout);
        maturitydateRelativeLayout = view.findViewById(R.id.maturitydateRelativeLayout);
        assetButton = view.findViewById(R.id.assetButton);
        assetButton.setOnClickListener(this);
        mInsurBelongToSpinner = view.findViewById(R.id.insur_belongsto_spinner);
        errorMapView.put("family_id", mInsurBelongToSpinner);
        mInsurBelongToSpinner.setOnItemSelectedListener(this);

        mInsurBelongToSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked = true;
                return false;
            }
        });

        minsurance_family_layout = view.findViewById(R.id.insurance_family_layout);

        mInsurNameEdt = view.findViewById(R.id.insur_name_edt);
        errorMapView.put("policy_name", mInsurNameEdt);
        UtileKit.nameCharacterOnly(mInsurNameEdt);

        mInsurSumAssuredEdt = view.findViewById(R.id.insur_sumassured_edt);
        errorMapView.put("coverage", mInsurSumAssuredEdt);
        mInsurSumAssuredEdt.setTextHint("Sum Assured / Cover*");


        mInsurAnnualperminumEdt = view.findViewById(R.id.insur_annualperminum_edt);
        errorMapView.put("annual_prem", mInsurAnnualperminumEdt);
        mInsurAnnualperminumEdt.setTextHint("Annual Premium*");


        minsurExpincEdt = view.findViewById(R.id.insur_expinc_edt);
        errorMapView.put("exp_incr", minsurExpincEdt);


        mInsurpolicyIssuedateEdt = view.findViewById(R.id.insur_policydate_edt);
        errorMapView.put("policy_issue_date", mInsurpolicyIssuedateEdt);
        editTextDrawableClick(mInsurpolicyIssuedateEdt);


        mInsurlastpremiumpaidEdt = view.findViewById(R.id.insur_lastpremiumpaid_edt);
        errorMapView.put("last_prem_date", mInsurlastpremiumpaidEdt);
        editTextDrawableClick(mInsurlastpremiumpaidEdt);


        mInsurnextpreminumpaidEdt = view.findViewById(R.id.insur_nextpremiumpaid_edt);
        errorMapView.put("next_prem_date", mInsurnextpreminumpaidEdt);
        editTextDrawableClick(mInsurnextpreminumpaidEdt);


        mInsurpremiumduetilldateEdt = view.findViewById(R.id.insur_premiumduetilldate_edt);
        errorMapView.put("prem_due_date", mInsurpremiumduetilldateEdt);
        editTextDrawableClick(mInsurpremiumduetilldateEdt);


        mInsurpolicyenddateEdt = view.findViewById(R.id.insur_policyenddate_edt);
        errorMapView.put("policy_end_date", mInsurpolicyenddateEdt);
        editTextDrawableClick(mInsurpolicyenddateEdt);


        mInsmaturitydateEdt = view.findViewById(R.id.ins_maturitydate_edt);
        errorMapView.put("maturity_date", mInsmaturitydateEdt);



        editTextDrawableClick(mInsmaturitydateEdt);
        minsurTerminyearspinner = view.findViewById(R.id.insur_terminyear_edt);
        errorMapView.put("term_years", minsurTerminyearspinner);

        minsurTerminyearspinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked = true;
                return false;
            }
        });
//        editTextDrawableClick(minsurTerminyearEdt);
        minsurTerminyearspinner.setOnItemSelectedListener(this);
        setSpinnerAdapter(minsurTerminyearspinner, termsInYear, activity);

        insur_paidduedate_edt = view.findViewById(R.id.insur_paidduedate_edt);
        errorMapView.put("last_paid_date", insur_paidduedate_edt);
        editTextDrawableClick(insur_paidduedate_edt);

        insu_loanFrequency_spinner = view.findViewById(R.id.insu_loanFrequency_spinner);
        errorMapView.put("ins_freq", insu_loanFrequency_spinner);
        insu_loanFrequency_spinner.setOnItemSelectedListener(this);


        insu_loanFrequency_spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked = true;
                return false;
            }
        });


        setSpinnerAdapter(insu_loanFrequency_spinner, monthInArray, activity);


        mInsurNotesEdt = view.findViewById(R.id.insur_notes_edt);
        errorMapView.put("notes", mInsurNotesEdt);



        mInsurpolicyIssuedateEdt.setOnClickListener(this);
        mInsurpolicyIssuedateEdt.setInputType(InputType.TYPE_NULL);

        minsur_sumassured_calculaterImgView = view.findViewById(R.id.insur_sumassured_calculaterImgView);
        minsur_annualpermium_calculaterImgView = view.findViewById(R.id.insur_annualpermium_calculaterImgView);
        minsur_sumassured_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(mInsurSumAssuredEdt);
            }
        });
        minsur_annualpermium_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(mInsurAnnualperminumEdt);
            }
        });
        /**
         * Life
         */
        mLifeInsuraceCatType_lay = view.findViewById(R.id.life_sub_insu_product_type_layout);
        mLifeInsuraceProTypeSpinner = view.findViewById(R.id.life_insu_product_type_spinner);
        /**
         * general
         */
        mGeneralInsurance_lay = view.findViewById(R.id.generalInsurancelayot);
        mGeneralSubTypeSpinner = view.findViewById(R.id.general_sub_insu_type_spinner);
        mGeneralProType_spinner = view.findViewById(R.id.general_insu_product_cat_spinner);

        mRiderCheckBtn = view.findViewById(R.id.riderId);
        mTopUpCheckBtn = view.findViewById(R.id.top_upId);
        mSuperTopCheckBtn = view.findViewById(R.id.supertop_upId);
        mBasicPlanCheckBtn = view.findViewById(R.id.basic_PlanId);
        mRiderCheckBtn.setOnCheckedChangeListener(this);
        mTopUpCheckBtn.setOnCheckedChangeListener(this);
        mSuperTopCheckBtn.setOnCheckedChangeListener(this);
        mBasicPlanCheckBtn.setOnCheckedChangeListener(this);
        mRiderCostEdit = view.findViewById(R.id.rider_amountId);
        mTopUpEdit = view.findViewById(R.id.top_up_amountId);
        mSuperTopEdit = view.findViewById(R.id.supertop_up_amountId);
        mBasicPlanEdit = view.findViewById(R.id.basic_plan_amountId);
        mRiderCostSpinner = view.findViewById(R.id.rider_spinnerId);
        mTopUpSpinner = view.findViewById(R.id.top_up_spinnerId);
        mSuperTopSpinner = view.findViewById(R.id.supertop_up_spinnerId);
        mBasicPlanSpinner = view.findViewById(R.id.basic_plan_spinnerId);
        mLifeInsuraceProTypeSpinner.setOnItemSelectedListener(this);
        mGeneralSubTypeSpinner.setOnItemSelectedListener(this);
        mGeneralProType_spinner.setOnItemSelectedListener(this);

        general_insurance_plan_type_spinner= view.findViewById(R.id.general_insurance_plan_type_spinner);
        general_insurance_plan_type_spinner.setOnItemSelectedListener(this);

        sub_insu_motor_type_layout= view.findViewById(R.id.sub_insu_motor_type_layout);
        mgeneral_sub_insu_motor_type_spinner= view.findViewById(R.id.general_sub_insu_motor_type_spinner);
        mgeneral_sub_insu_motor_type_spinner.setOnItemSelectedListener(this);

        setSpinnerAdapter(general_insurance_plan_type_spinner,general_insuran_plan_type,activity);
        setSpinnerAdapter(mgeneral_sub_insu_motor_type_spinner,general_insuran_motor_sub_plan_type,activity);


        mRiderCostSpinner.setOnItemSelectedListener(this);
        mTopUpSpinner.setOnItemSelectedListener(this);
        mSuperTopSpinner.setOnItemSelectedListener(this);
        mBasicPlanSpinner.setOnItemSelectedListener(this);
        getFirstTimeCurrentDateandMonth();
        mArgs = getArguments();
        InsID = mArgs.getString("ins_id");
        insuranceType = mArgs.getString("type");
        size = mArgs.getInt("size");

        isSignUp=mArgs.getBoolean("isSignUp");

        bottom_bar_layout= view.findViewById(R.id.bottom_bar_layout);
        UtileKit.mandatoryFieldLinearLayout(isSignUp,bottom_bar_layout);

        bottom_bar_donelayout= view.findViewById(R.id.bottom_bar_donelayout);
        UtileKit.mandatoryFieldDoneLayout(isSignUp,bottom_bar_donelayout);

        relative_finish_later= view.findViewById(R.id.relative_finish_later);
        relative_done_arrow= view.findViewById(R.id.relative_done_arrow);
        relative_finish_later.setOnClickListener(this);
        relative_done_arrow.setOnClickListener(this);


        if(isSignUp==true){
            fab.setVisibility(View.GONE);
        }


        Log.i("CallBack", "InsID" + InsID + "insuranceType " + insuranceType);
        if (UtileKit.validateObjectValues(InsID)) {
            UserID = mArgs.getString("userid");
            getInsuranceUserData = (GetInsuranceInputData) mArgs.getSerializable("Insuranceobject");
        }
        try {
            mIns_prod_type = mInsuranceCatModel.getData().getIns_prod_type();
            int ins_subSize = mInsuranceCatModel.getData().getIns_sub_type().size();
            mIns_sub_type = mInsuranceCatModel.getData().getIns_sub_type();
            mIns_prod_cat = mInsuranceCatModel.getData().getIns_prod_cat();
            getLifeInsuraceProductList("Life");
            String lifeInsId = "";
            for (Ins_sub_type ins_sub : mIns_sub_type) {
                if (ins_sub.getIns_type().equalsIgnoreCase("Life")) {
                    lifeInsId = ins_sub.getId();
                    break;
                }
            }

            int size = mIns_prod_type.size();
            lifeInsuranceSpinnerList.clear();
            lifeInsuranceList.clear();
            for (int i = 0; i < size; i++) {
                if (mIns_prod_type.get(i).getIns_sub_type_id() != null) {
                    if (mIns_prod_type.get(i).getIns_sub_type_id().equalsIgnoreCase(lifeInsId)) {
                        lifeInsuranceSpinnerList.add(mIns_prod_type.get(i).getProd_type());
                        lifeInsuranceList.add(mIns_prod_type.get(i));
                    } else {
                        generalInsuProSpinnerList.add(mIns_prod_type.get(i).getProd_type());
                        generalInsurProList.add(mIns_prod_type.get(i));
                    }
                }
            }
        } catch (Exception e) {
        }

        if (insuranceType.equalsIgnoreCase("Life")) {
            try {

                setSpinnerAdapter(mLifeInsuraceProTypeSpinner, lifeInsuranceSpinnerList, activity);
                mLifeInsuraceCatType_lay.setVisibility(View.VISIBLE);
                mGeneralInsurance_lay.setVisibility(View.GONE);
                insuraceSubType = "Life";
                if (UtileKit.validateObjectValues(getInsuranceUserData)) {
                    if (UtileKit.validateObjectValues(getInsuranceUserData.getIns_prod_type())) {
                        int pos = getSpinnerAssetposition(getInsuranceUserData.getIns_prod_type(), lifeInsuranceSpinnerList);
                        mLifeInsuraceProTypeSpinner.setSelection(pos);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            mRiderSpinnerList = new ArrayList<>();
            mTopUpSpinnerList = new ArrayList<>();
            mSuperTopSpinnerList = new ArrayList<>();
            mBasicPlanSpinnerList = new ArrayList<>();
            mRiderObjList = new ArrayList<>();
            mTopUpObjList = new ArrayList<>();
            mSuperTopObjList = new ArrayList<>();
            mBasicPlanObjList = new ArrayList<>();

            getSubProductList("Rider", mRiderObjList, mRiderSpinnerList);
            getSubProductList("Top Up", mTopUpObjList, mTopUpSpinnerList);
            getSubProductList("Super Top Up", mSuperTopObjList, mSuperTopSpinnerList);
            getSubProductList("Basic Plan", mBasicPlanObjList, mBasicPlanSpinnerList);
            insSubTypeArray = new ArrayList<>();
            for (Ins_sub_type obj : mIns_sub_type) {
                insSubTypeArray.add(obj.getIns_sub_type());
            }

            mGeneralInsurance_lay.setVisibility(View.VISIBLE);
            mLifeInsuraceCatType_lay.setVisibility(View.GONE);
//            mSub_insu_type_layout.setVisibility(View.VISIBLE);
            setSpinnerAdapter(mGeneralSubTypeSpinner, insSubTypeArray, activity);
            setSpinnerAdapter(mGeneralProType_spinner, generalInsuProSpinnerList, activity);
            setSpinnerAdapter(mBasicPlanSpinner, mBasicPlanSpinnerList, activity);
            setSpinnerAdapter(mSuperTopSpinner, mSuperTopSpinnerList, activity);
            setSpinnerAdapter(mRiderCostSpinner, mRiderSpinnerList, activity);
            setSpinnerAdapter(mTopUpSpinner, mTopUpSpinnerList, activity);
            if (UtileKit.validateObjectValues(getInsuranceUserData)) {
                try {
                    if (UtileKit.validateObjectValues(getInsuranceUserData.getIns_sub_type())) {
                        int pos = getSpinnerAssetposition(getInsuranceUserData.getIns_sub_type(), insSubTypeArray);
                        mGeneralSubTypeSpinner.setSelection(pos);
                        isCheckSubType = true;
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        titleNameTxt.setText("" + insuranceType + " Insurance");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        if (UtileKit.validateObjectValues(familyDetails)) {
            if (!familyDetails.isEmpty()) {
                mgoalBelongsToArrayListId = new ArrayList<>();
                mgoalBelongsToArrayList = new ArrayList<>();
                mgoalBelongsToArrayList.add("Self");
                mgoalBelongsToArrayListId.add("0");
                for (int i = 0; i < familyDetails.size(); i++) {
                    mgoalBelongsToArrayList.add(familyDetails.get(i).getName());
                    mgoalBelongsToArrayListId.add(familyDetails.get(i).getId());
                    //Log.e("CallBack", " family is " + familyDetails.get(i).getName());
                }
                minsurance_family_layout.setVisibility(View.VISIBLE);
            }
            //  UtileKit.setArrayListSpinnerAdapter(mInsurBelongToSpinner, mgoalBelongsToArrayList, activity);
            //Log.e("CallBack", "InsuranceSpinnerbelongtoarraylist" + mgoalBelongsToArrayList);
            setSpinnerAdapter(mInsurBelongToSpinner, mgoalBelongsToArrayList, activity);
            //Log.e("CallBack", "InsuranceSpinnerbelongtoarraylist2" + mgoalBelongsToArrayList);
        }
        policydateRelativeLayout.setOnClickListener(this);
        lastpremiumpaidRelativeLayout.setOnClickListener(this);
        nextpremiumpaidRelativeLayout.setOnClickListener(this);
        premiumduetilldateRelativeLayout.setOnClickListener(this);
        policyenddateRelativeLayout.setOnClickListener(this);
        maturitydateRelativeLayout.setOnClickListener(this);
//        terminyearRelativeLayout.setOnClickListener(this);
        callFamilyDetailsService();

        mInsurNameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mInsurname_edtparent.setErrorEnabled(false);
                mInsurname_edtparent.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        insu_loanFrequency_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loan_freq_month = insu_loanFrequency_spinner.getSelectedItem().toString();

                if (position != 0) {
                    if (!isFirstTimeColor && isClicked) {
                        insu_loanFrequency_spinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        general_insurance_plan_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                plan_type=general_insurance_plan_type_spinner.getSelectedItem().toString();

                if (position != 0) {
                    if (!isFirstTimeColor && isClicked) {
                        general_insurance_plan_type_spinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mgeneral_sub_insu_motor_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                motor_type=mgeneral_sub_insu_motor_type_spinner.getSelectedItem().toString();

                if (position != 0) {
                    if (!isFirstTimeColor && isClicked) {
                        mgeneral_sub_insu_motor_type_spinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        minsurTerminyearspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                term_years = minsurTerminyearspinner.getSelectedItem().toString();


                if (position != 0) {
                    if (!isFirstTimeColor && isClicked) {
                        minsurTerminyearspinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }


                if (term_years.equalsIgnoreCase("Quarterly")) {
                    term_years = "3";
                } else if (term_years.equalsIgnoreCase("Half yearly")) {
                    term_years = "6";
                } else if (term_years.equalsIgnoreCase("Annualy")) {
                    term_years = "9";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mRiderCostEdit.getEditText().setEnabled(false);
        mRiderCostSpinner.setEnabled(false);
        mTopUpSpinner.setEnabled(false);
        mTopUpEdit.getEditText().setEnabled(false);
        mSuperTopSpinner.setEnabled(false);
        mSuperTopEdit.getEditText().setEnabled(false);
        mBasicPlanEdit.getEditText().setEnabled(false);
        mBasicPlanSpinner.setEnabled(false);

        mBasicPlanCheckBtn.setChecked(false);
        if (getInsuranceUserData != null) {
            setInsuranceDetailsForUpdateonlyCalender(getInsuranceUserData);
        }
        if (UtileKit.validateObjectValues(InsID)) {
            setInsuranceDetailsForUpdate();
        }



        /*life insurance*/
        if (insuranceType.equalsIgnoreCase("Life")) {
            mInsurNameEdt.setHintText(getString(R.string.hint_life_insurance_name), ((TextInputLayout) (mInsurNameEdt.getParent()).getParent()));
            mInsurSumAssuredEdt.setfullHintTxt(getString(R.string.hint_life_insurance_sum_assured));
            mInsurAnnualperminumEdt.setfullHintTxt(getString(R.string.hint_life_insurance_annual_premiam));
            minsurExpincEdt.setHintText(getString(R.string.hint_life_insurance_expected_increment), ((TextInputLayout) (minsurExpincEdt.getParent()).getParent()));
            mInsurpolicyIssuedateEdt.setHintText(getString(R.string.hint_life_insurance_Policyissues_date_Start_year), ((TextInputLayout) (mInsurpolicyIssuedateEdt.getParent()).getParent()));
            mInsurlastpremiumpaidEdt.setHintText(getString(R.string.hint_life_insurance_last_premium_paid_date), ((TextInputLayout) (mInsurlastpremiumpaidEdt.getParent()).getParent()));
            mInsurnextpreminumpaidEdt.setHintText(getString(R.string.hint_life_insurance_next_premium_due_date), ((TextInputLayout) (mInsurnextpreminumpaidEdt.getParent()).getParent()));
            mInsurpremiumduetilldateEdt.setHintText(getString(R.string.hint_life_insurance_Final_last_premium_due_till_date_year),
                    ((TextInputLayout) (mInsurpremiumduetilldateEdt.getParent()).getParent()));
            mInsurNotesEdt.setHintText(getString(R.string.hint_life_insurance_note), ((TextInputLayout) (mInsurNotesEdt.getParent()).getParent()));
        }else {
        /*life insurance*/
            mInsurNameEdt.setHintText(getString(R.string.hint_general_insurance_name), ((TextInputLayout)(mInsurNameEdt.getParent()).getParent()));
            mInsurSumAssuredEdt.setfullHintTxt(getString(R.string.hint_lgeneral_insurance_sum_assured));
            mInsurAnnualperminumEdt.setfullHintTxt(getString(R.string.hint_general_insurance_annual_premiam));
            minsurExpincEdt.setHintText(getString(R.string.hint_general_insurance_expected_increment), ((TextInputLayout)(minsurExpincEdt.getParent()).getParent()));
            mInsurpolicyIssuedateEdt.setHintText(getString(R.string.hint_general_insurance_Policyissues_date_Start_year), ((TextInputLayout)(mInsurpolicyIssuedateEdt.getParent()).getParent()));
            mInsurlastpremiumpaidEdt.setHintText(getString(R.string.hint_life_insurance_last_premium_paid_date), ((TextInputLayout)(mInsurlastpremiumpaidEdt.getParent()).getParent()));
            mInsurnextpreminumpaidEdt.setHintText(getString(R.string.hint_general_insurance_last_premium_paid_date), ((TextInputLayout)(mInsurnextpreminumpaidEdt.getParent()).getParent()));
            mInsurpremiumduetilldateEdt.setHintText(getString(R.string.hint_general_insurance_Final_last_premium_due_till_date_year),
                    ((TextInputLayout)(mInsurpremiumduetilldateEdt.getParent()).getParent()));
            mInsurNotesEdt.setHintText(getString(R.string.hint_general_insurance_note),((TextInputLayout)(mInsurNotesEdt.getParent()).getParent()));
        }


    }

    private void getLifeInsuraceProductList(String life) {
    }

    private void setInsuranceDetailsForUpdateonlyCalender(GetInsuranceInputData getInsuranceUserData) {

        if (UtileKit.validateObjectValues(getInsuranceUserData.getPolicy_issue_date())) {
            setcalenderDateInEditText(mInsurpolicyIssuedateEdt, getInsuranceUserData.getPolicy_issue_date());
        } else {
            mInsurpolicyIssuedateEdt.setHintTextEmptyError();
        }

        if (UtileKit.validateObjectValues(getInsuranceUserData.getLast_paid_date())) {
            setcalenderDateInEditText(insur_paidduedate_edt, getInsuranceUserData.getLast_paid_date());
        } else {
            insur_paidduedate_edt.setHintTextEmptyError();
        }
        if (UtileKit.validateObjectValues(getInsuranceUserData.getLast_prem_date())) {
            setcalenderDateInEditText(mInsurlastpremiumpaidEdt, getInsuranceUserData.getLast_prem_date());
        } else {
            mInsurlastpremiumpaidEdt.setHintTextEmptyError();
        }
        if (UtileKit.validateObjectValues(getInsuranceUserData.getNext_prem_date())) {
            setcalenderDateInEditText(mInsurnextpreminumpaidEdt, getInsuranceUserData.getNext_prem_date());
        } else {
            mInsurnextpreminumpaidEdt.setHintTextEmptyError();
        }

        if (UtileKit.validateObjectValues(getInsuranceUserData.getPrem_due_date())) {
            setcalenderDateInEditText(mInsurpremiumduetilldateEdt, getInsuranceUserData.getPrem_due_date());
        } else {
            mInsurpremiumduetilldateEdt.setHintTextEmptyError();
        }
        if (UtileKit.validateObjectValues(getInsuranceUserData.getPolicy_end_date())) {
            setcalenderDateInEditText(mInsurpolicyenddateEdt, getInsuranceUserData.getPolicy_end_date());
        } else {
            mInsurpolicyenddateEdt.setHintTextEmptyError();
        }

        if (UtileKit.validateObjectValues(getInsuranceUserData.getMaturity_date())) {

            setcalenderDateInEditText(mInsmaturitydateEdt, getInsuranceUserData.getMaturity_date());
        } else {
            mInsmaturitydateEdt.setHintTextEmptyError();
        }

    }

    private void setcalenderDateInEditText(CalendarEditText mEditText, String policy_issue_date) {
        try {

            String parseSetDate = policy_issue_date;
            Log.i("term_years_postion", "getPolicy_issue_date" + parseSetDate);
            if (parseSetDate.equalsIgnoreCase("0000-00-00")) {
                mEditText.setText("");

            } else {
                Log.i("parseSetDate", "parseSetDate " + parseSetDate.toString());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date myDate = null;
                try {
                    myDate = dateFormat.parse(parseSetDate);
                    //Log.e("DateParse", myDate.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy");
                String finalDate = timeFormat.format(myDate);
                Log.i("DateParse", "getPolicy_issue_date getPolicy_issue_date " + finalDate.toString());
                mEditText.setText(finalDate.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showCalDialog(View view) {
        nameEditview = view;
//        nameEditview.setTag(view.getTag());
        String calculaterValue = ((CurrencyGhostView) nameEditview).getText().toString();
        Intent calculatorIntent = new Intent(getActivity(), CalculatorAct.class);
        calculatorIntent.putExtra(CalculatorAct.TITLE_ACTIVITY, TITLE);
        calculatorIntent.putExtra(CalculatorAct.PARENT_ACTIVITY, PARENT_CLASS_SOURCE);
        calculatorIntent.putExtra(CalculatorAct.VALUE, calculaterValue);
        startActivityForResult(calculatorIntent, CalculatorAct.REQUEST_RESULT_SUCCESSFUL);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == CalculatorAct.REQUEST_RESULT_SUCCESSFUL) {
            String result = data.getStringExtra(CalculatorAct.RESULT);
            ((CurrencyGhostView) nameEditview).setText(result);
            ((CurrencyGhostView) nameEditview).getEditText().setBackgroundResource(R.drawable.edittextbackgrounggreen);

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getActivity().getCurrentFocus();
        if (view == null) {
            view = new View(getActivity());
        }
        im.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    public void editTextDrawableClick(final EditText EdtText) {

        EdtText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (event.getRawX() >= (EdtText.getRight() - EdtText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    // your action here
                    edtTextonClick(EdtText);
                    return true;
//                    }
                }
                return false;
            }
        });
    }


    public void edtTextonClick(EditText EdtText) {
//        setInsuranceDetailsForUpdate();
        policy_issue_date = mInsurpolicyIssuedateEdt.getText().toString();
//        term_years = minsurTerminyearEdt.getText().toString();
        last_prem_date = mInsurlastpremiumpaidEdt.getText().toString();
        next_prem_date = mInsurnextpreminumpaidEdt.getText().toString();
        prem_due_date = mInsurpremiumduetilldateEdt.getText().toString();
        policy_end_date = mInsurpolicyenddateEdt.getText().toString();
        maturity_date = mInsmaturitydateEdt.getText().toString();
        if (EdtText == mInsurpolicyIssuedateEdt) {
            String date = EdtText.getText().toString();
            CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                    getString(R.string.policyIssueDateDialog), Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, "1", date);
            if (UtileKit.validateObjectValues(date)) {
                args.putString(AssetDatePickerDialogFragment.FULLDATE, date);
                mcalenderTabs.setArguments(args);
            }
            mcalenderTabs.show(getFragmentManager(), "policy date");

        } else if (EdtText == mInsurlastpremiumpaidEdt) {
            setEdtText(mInsurlastpremiumpaidEdt);
            String date = EdtText.getText().toString();


            CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                    mlast_prem_date, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, "1", date);
            if (UtileKit.validateObjectValues(date)) {
                args.putString(AssetDatePickerDialogFragment.FULLDATE, date);
                mcalenderTabs.setArguments(args);
            }
            mcalenderTabs.show(getFragmentManager(), "policy date");

        } else if (EdtText == mInsurnextpreminumpaidEdt) {
            setEdtText(mInsurnextpreminumpaidEdt);

            try {
                String edit_date = EdtText.getText().toString();

                CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                        mnext_prem_date, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "1", edit_date);
                if (UtileKit.validateObjectValues(edit_date)) {
                    args.putString(AssetDatePickerDialogFragment.FULLDATE, edit_date);
                    mcalenderTabs.setArguments(args);
                }
                mcalenderTabs.show(getFragmentManager(), mnext_prem_date);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (EdtText == mInsurpremiumduetilldateEdt) {
            setEdtText(mInsurpremiumduetilldateEdt);
            String date = EdtText.getText().toString();
            CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                    mprem_due_date, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "1", date);
            if (UtileKit.validateObjectValues(date)) {
                args.putString(AssetDatePickerDialogFragment.FULLDATE, date);
                mcalenderTabs.setArguments(args);
            }
            mcalenderTabs.show(getFragmentManager(), "terms");
        } else if (EdtText == mInsurpolicyenddateEdt) {
            setEdtText(mInsurpolicyenddateEdt);
            String date = EdtText.getText().toString();
            CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                    mpolicy_end_date, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "1", date);
            if (UtileKit.validateObjectValues(date)) {
                args.putString(AssetDatePickerDialogFragment.FULLDATE, date);
                mcalenderTabs.setArguments(args);
            }
            mcalenderTabs.show(getFragmentManager(), "display");


        } else if (EdtText == mInsmaturitydateEdt) {
            setEdtText(mInsmaturitydateEdt);
            String date = EdtText.getText().toString();
            if (date.length() <= 3) {

            }
            CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                    mmaturity_date, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "1", date);
            if (UtileKit.validateObjectValues(date)) {
                args.putString(AssetDatePickerDialogFragment.FULLDATE, date);
                mcalenderTabs.setArguments(args);
            }
            mcalenderTabs.show(getFragmentManager(), "date");

        } else if (EdtText == insur_paidduedate_edt) {
            setEdtText(insur_paidduedate_edt);
            String date = EdtText.getText().toString();
            if (date.length() <= 3) {

            }
            CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                    minsurancePaidDueDate, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "1", date);
            if (UtileKit.validateObjectValues(date)) {
                args.putString(AssetDatePickerDialogFragment.FULLDATE, date);
                mcalenderTabs.setArguments(args);
            }
            mcalenderTabs.show(getFragmentManager(), "date");
        }
    }

    private EditText getEdtText() {
        return mCalanderEdt;
    }

    private void setEdtText(EditText edttext) {
        this.mCalanderEdt = edttext;
    }

    private boolean isVibrate() {
        return false;
    }

    private boolean isCloseOnSingleTapDay() {
        return true;
    }

    private boolean isCloseOnSingleTapMinute() {
        return true;
    }

    private int getSpinnerAssetposition(String value, ArrayList<String> spinerlist) {
        int pos = 0;
        for (int j = 0; j < spinerlist.size(); j++) {
            if (value.equalsIgnoreCase(spinerlist.get(j))) {
                return j + 1;
            }
        }
        return pos;
    }

    private void setInsuranceDetailsForUpdate() {

        if (getInsuranceUserData != null) {
            try {

                if (UtileKit.validateObjectValues(getInsuranceUserData.getEmpty_flds())) {
                    emptyErrorValidation(getInsuranceUserData.getEmpty_flds());
                }

                if (UtileKit.validateObjectValues(getInsuranceUserData.getFamily_id())) {
                    mInsurBelongToSpinner.setSelection(getSpinnerposition(getInsuranceUserData.getFamily_id(), mgoalBelongsToArrayListId));

                }

                if (UtileKit.validateObjectValues(getInsuranceUserData.getPolicy_name())) {
                    mInsurNameEdt.setText(getInsuranceUserData.getPolicy_name());

                    mInsurNameEdt.setSelection(getInsuranceUserData.getPolicy_name().length());
                } else {
                    // mInsurNameEdt.setHintTextEmptyError();
                }



                if (UtileKit.validateObjectValues(getInsuranceUserData.getTerm_years())) {

                    String term_years_postion = getInsuranceUserData.getTerm_years();
                    Log.i("term_years_postion", "term_years_postion" + term_years_postion);
                    if (term_years_postion.equalsIgnoreCase("3")) {
                        minsurTerminyearspinner.setSelection(getSpinnerAssetposition("Quarterly", termsInYear));
                    } else if (term_years_postion.equalsIgnoreCase("6")) {
                        minsurTerminyearspinner.setSelection(getSpinnerAssetposition("Half yearly", termsInYear));
                    } else if (term_years_postion.equalsIgnoreCase("9")) {
                        minsurTerminyearspinner.setSelection(getSpinnerAssetposition("Annualy", termsInYear));
                    }

                }
                if (UtileKit.validateObjectValues(getInsuranceUserData.getIns_freq())) {
                    insu_loanFrequency_spinner.setSelection(getSpinnerAssetposition(getInsuranceUserData.getIns_freq(),
                            monthInArray));
                }


                if(UtileKit.validateObjectValues(getInsuranceUserData.getPlan_type())){
                    general_insurance_plan_type_spinner.setSelection(getSpinnerAssetposition(
                            getInsuranceUserData.getPlan_type(), general_insuran_plan_type));
                }

                if (UtileKit.validateObjectValues(getInsuranceUserData.getMotor_type())) {
                mgeneral_sub_insu_motor_type_spinner.setSelection(getSpinnerAssetposition(
                        getInsuranceUserData.getMotor_type(),general_insuran_motor_sub_plan_type));
                }

                if (UtileKit.validateObjectValues(getInsuranceUserData.getCoverage())) {
                    mInsurSumAssuredEdt.setText(getInsuranceUserData.getCoverage());
                    mInsurSumAssuredEdt.getEditText().setSelection(getInsuranceUserData.getCoverage().length());
                } else {
                    //  mInsurSumAssuredEdt.setHintTextEmptyError();
                }

                if (UtileKit.validateObjectValues(getInsuranceUserData.getAnnual_prem())) {
                    mInsurAnnualperminumEdt.setText(getInsuranceUserData.getAnnual_prem());
                    mInsurAnnualperminumEdt.getEditText().setSelection(getInsuranceUserData.getAnnual_prem().length());
                } else {
                    // mInsurAnnualperminumEdt.setHintTextEmptyError();
                }

                if (UtileKit.validateObjectValues(getInsuranceUserData.getNotes())) {
                    mInsurNotesEdt.setText(getInsuranceUserData.getNotes());
                    mInsurNotesEdt.setSelection(getInsuranceUserData.getNotes().length());
                } else {
                    //  mInsurNotesEdt.setHintTextEmptyError();
                }

                if (UtileKit.validateObjectValues(getInsuranceUserData.getExp_incr())) {
                    minsurExpincEdt.setText(getInsuranceUserData.getExp_incr());
                    minsurExpincEdt.setSelection(getInsuranceUserData.getExp_incr().length());
                } else {
                    //   minsurExpincEdt.setHintTextEmptyError();

                }
                if (UtileKit.validateObjectValues(getInsuranceUserData.getSub_insur())) {
                    ArrayList<Sub_insur> mSub_insur = getInsuranceUserData.getSub_insur();
                    for (Sub_insur obj : mSub_insur) {

                        setSubInsuraceId(obj);


                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void setSubInsuraceId(Sub_insur ins_prod_cat) {
        int selectedPosition = 0;
        try {
            switch (ins_prod_cat.getIns_prod_cat()) {
                case "Rider":
                    mRiderCheckBtn.setChecked(true);
                    mRiderCostEdit.getEditText().setEnabled(true);
                    mRiderCostSpinner.setEnabled(true);
                    mRiderCostId = ins_prod_cat.getId();
                    selectedPosition = getStringArraySpinnerposition(ins_prod_cat.getIns_prod_type(), mRiderSpinnerList);
                    mRiderCostSpinner.setSelection(selectedPosition);
                    mRiderCostEdit.getEditText().setText("" + ins_prod_cat.getSum_assured());

                    break;
                case "Top Up":
                    mTopUpCheckBtn.setChecked(true);
                    mTopUpEdit.getEditText().setEnabled(true);
                    mTopUpSpinner.setEnabled(true);
                    mTopUpId = ins_prod_cat.getId();
                    selectedPosition = getStringArraySpinnerposition(ins_prod_cat.getIns_prod_type(), mTopUpSpinnerList);
                    mTopUpSpinner.setSelection(selectedPosition);
                    mTopUpEdit.getEditText().setText("" + ins_prod_cat.getSum_assured());
                    break;
                case "Super Top Up":
                    mSuperTopCheckBtn.setChecked(true);
                    mSuperTopEdit.getEditText().setEnabled(true);
                    mSuperTopSpinner.setEnabled(true);
                    mSuperTopId = ins_prod_cat.getId();
                    selectedPosition = getStringArraySpinnerposition(ins_prod_cat.getIns_prod_type(), mSuperTopSpinnerList);
                    mSuperTopSpinner.setSelection(selectedPosition);
                    mSuperTopEdit.getEditText().setText("" + ins_prod_cat.getSum_assured());
                    break;
                case "Basic Plan":
                    mBasicPlanCheckBtn.setChecked(true);
                    mBasicPlanSpinner.setEnabled(true);
                    mBasicPlanEdit.getEditText().setEnabled(true);
                    mBasicPlanId = ins_prod_cat.getId();
                    selectedPosition = getStringArraySpinnerposition(ins_prod_cat.getIns_prod_type(), mBasicPlanSpinnerList);
                    mBasicPlanSpinner.setSelection(selectedPosition);
                    mBasicPlanEdit.getEditText().setText("" + ins_prod_cat.getSum_assured());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getStringArraySpinnerposition(String value, ArrayList<String> spinerlist) {
        int pos = 0;
        int size = spinerlist.size();
        for (int i = 0; i < size; i++) {
            if (value.equalsIgnoreCase(spinerlist.get(i))) {
                pos = i + 1;
            }
        }
        return pos;
    }

    private void callFamilyDetailsService() {
        UtileKit.showSpinnerDialog(activity, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GoalFamilyDetailsModel> call = webServiceObj.callFamilyDetailsListService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GoalFamilyDetailsModel>() {
            @Override
            public void onResponse(Call<GoalFamilyDetailsModel> call, Response<GoalFamilyDetailsModel> response) {
                //Log.e("CallBack", " family is " + call.toString());
                addFamilyDetailModel = response.body();
                UtileKit.dismisssSpinnerDialog();
                if (addFamilyDetailModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    familyDetails = addFamilyDetailModel.getData().getFamily_details();
                    mgoalBelongsToArrayList = new ArrayList<String>();
                    mgoalBelongsToArrayListId = new ArrayList<String>();
                    mgoalBelongsToArrayList.add("Self");
                    mgoalBelongsToArrayListId.add("0");
                    if (UtileKit.validateObjectValues(familyDetails)) {
                        if (!familyDetails.isEmpty()) {
                            for (int i = 0; i < familyDetails.size(); i++) {
                                mgoalBelongsToArrayList.add(familyDetails.get(i).getName());
                                mgoalBelongsToArrayListId.add(familyDetails.get(i).getId());
                                //Log.e("CallBack", " family is " + familyDetails.get(i).getName());
                            }
                            minsurance_family_layout.setVisibility(View.VISIBLE);
                        }
                        setSpinnerAdapter(mInsurBelongToSpinner, mgoalBelongsToArrayList, activity);
                        if (UtileKit.validateObjectValues(getInsuranceUserData)) {
                            if (UtileKit.validateObjectValues(getInsuranceUserData.getFamily_id())) {
                                mInsurBelongToSpinner.setSelection(getSpinnerposition(getInsuranceUserData.getFamily_id(), mgoalBelongsToArrayListId));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GoalFamilyDetailsModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog(activity, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }

    private void emptyErrorValidation(ArrayList<String> emptyArrayList) {
        for (String obj : emptyArrayList) {
            View view = errorMapView.get(obj);
            UtileKit.emptyErrorViewList(view);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.relative_finish_later:{
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
            break;
            case R.id.relative_done_arrow:{

            getInputData();
            if (policy_name != null && !policy_name.isEmpty()) {
                if (UtileKit.validateObjectValues(InsID)) {
                    callUpdateInsuranceService();
                    dismiss();
                } else {
                    addInsurance();
                    dismiss();
                }
            } else {
                mInsurname_edtparent.setError(HomePageActivity.errorMessageInInsurance);
                mInsurNameEdt.requestFocus();
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.scrollTo(0, 0);
                    }
                });

                UtileKit.intitializeAlertDialog(
                        HomePageActivity.errorMessageInInsurance,
                        activity);
            }
            //signup prompt done button gone value update here
                firstTimeDoneInterface.firstTimeDone("Y");
            }
            break;





            case R.id.assetButton:
                getInputData();
                if (UtileKit.validateObjectValues(InsID)) {
                    callUpdateInsuranceService();
                    dismiss();
                } else {
                    addInsurance();
                    dismiss();
                }
                break;
            case R.id.relative_left_arrow: {
                try {
                    saveInsuranceData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dismiss();

            }
            break;
            case R.id.relative_center_home: {
                try {
                    saveInsuranceData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                onCheckListIsEmpty.onHomeBackPresedLisaner();
//                getActivity().finish();
            }
            break;
            case R.id.relative_right_arrow: {
                onCheckListIsEmpty.onNextBAckpressed();
                dismiss();
            }
            break;
        }

    }

    private void saveInsuranceData() {
        try {
            getInputData();
            if (UtileKit.validateObjectValues(InsID)) {
                callUpdateInsuranceService();
            } else {
                addInsurance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addInsurance() {
        UtileKit.showSpinnerDialog(activity, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        String mpolicyissuedate = policy_issue_date.toString();

        Call<AddInsuranceModel> call = webServiceObj.calladdInsuranceService(UtileKit.getPersistedPurplePathPref("user_id"), family_id, ins_type,
                policy_name, coverage, annual_prem, exp_incr, mpolicyissuedate, last_prem_date, next_prem_date, prem_due_date,
                policy_end_date, maturity_date, term_years, notes, lastpaiddueDate, loan_freq_month, insuraceSubType,
                insuraceProductType, ins_detJson,plan_type,motor_type);
        call.enqueue(new Callback<AddInsuranceModel>() {
            @Override
            public void onResponse(Call<AddInsuranceModel> call, Response<AddInsuranceModel> response) {
                //Log.e("CallBack", "sucess insurance is " + call.toString());
                addInsuranceModel = response.body();
                UtileKit.dismisssSpinnerDialog();
                if (addInsuranceModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    onCheckListIsEmpty.checkListSize(1);
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<AddInsuranceModel> call, Throwable t) {
                //Log.e("CallBack", " failure assets is " + t);
                if (insuranceType.equalsIgnoreCase("General")) {
                    onCheckListIsEmpty.checkListSize(1);
                } else if (insuranceType.equalsIgnoreCase("Life")) {
                    onCheckListIsEmpty.checkListSize(1);
                }
                dismiss();
                UtileKit.alertRetrofitExceptionDialog(activity, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }


    public void callUpdateInsuranceService() {
        UtileKit.showSpinnerDialog(activity, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        String mpolicyissuedate = policy_issue_date.toString();
        Call<AddInsuranceModel> call = webServiceObj.callUpdateInsuranceService(UtileKit.getPersistedPurplePathPref("user_id"),
                InsID, family_id, ins_type, policy_name,
                coverage, annual_prem, exp_incr, mpolicyissuedate, last_prem_date, next_prem_date, prem_due_date,
                policy_end_date, maturity_date, term_years, notes, lastpaiddueDate, loan_freq_month, insuraceSubType,
                insuraceProductType, ins_detJson,plan_type,motor_type);
        call.enqueue(new Callback<AddInsuranceModel>() {
            @Override
            public void onResponse(Call<AddInsuranceModel> call, Response<AddInsuranceModel> response) {
                //Log.e("CallBack", "sucess insurance is " + call.toString());
                addInsuranceModel = response.body();
                UtileKit.dismisssSpinnerDialog();
                if (addInsuranceModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    try {
                        onCheckListIsEmpty.checkListSize(1);
                    } catch (NullPointerException e) {

                    }
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<AddInsuranceModel> call, Throwable t) {
                //Log.e("CallBack", " failure assets is " + t);
                if (insuranceType.equalsIgnoreCase("General")) {
                    onCheckListIsEmpty.checkListSize(1);
                } else if (insuranceType.equalsIgnoreCase("Life")) {
                    onCheckListIsEmpty.checkListSize(1);
                }
                dismiss();
                UtileKit.alertRetrofitExceptionDialog(activity, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }

    private void getInputData() {
        try {
            ins_type = insuranceType;
            policy_name = mInsurNameEdt.getText().toString().trim();
            coverage = UtileKit.getStringwithoutDefaultCurreny(mInsurSumAssuredEdt.getEditText());
            annual_prem = UtileKit.getStringwithoutDefaultCurreny(mInsurAnnualperminumEdt.getEditText());
            exp_incr = minsurExpincEdt.getText().toString();

            policy_issue_date = setDateFormat(mInsurpolicyIssuedateEdt.getText().toString());
            last_prem_date = setDateFormat(mInsurlastpremiumpaidEdt.getText().toString());
            next_prem_date = setDateFormat(mInsurnextpreminumpaidEdt.getText().toString());
            prem_due_date = setDateFormat(mInsurpremiumduetilldateEdt.getText().toString());
            policy_end_date = setDateFormat(mInsurpolicyenddateEdt.getText().toString());
            Log.i("Insurance Dialog", "annual_prem string " + annual_prem);

            maturity_date = setDateFormat(mInsmaturitydateEdt.getText().toString());
//            term_years = setDateFormat(minsurTerminyearEdt.getText().toString());
//            Log.i("Insurance Dialog", "edit text string " + term_years + " minsurTerminyearEdt " + minsurTerminyearEdt.getText().toString());
            notes = mInsurNotesEdt.getText().toString();
            lastpaiddueDate = setDateFormat(insur_paidduedate_edt.getText().toString());
            getInuranceTopCheck();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getInuranceTopCheck() {
        if (mRiderCheckBtn.isChecked()
                || mTopUpCheckBtn.isChecked()
                || mSuperTopCheckBtn.isChecked()
                || mBasicPlanCheckBtn.isChecked()) {
            JSONObject obj = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            if (mRiderCheckBtn.isChecked()) {
                try {
                    JSONObject mRiderCheckObj = new JSONObject();
                    mRiderCheckObj.put("ins_prod_cat", "Rider");
                    mRiderCheckObj.put("ins_prod_type", mRiderCostType);
                    mRiderCheckObj.put("sum_assured", UtileKit.getStringwithoutCurreny(mRiderCostEdit.getEditText().getText().toString()));
                    mRiderCheckObj.put("id", mRiderCostId);
                    jsonArray.put(mRiderCheckObj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (mTopUpCheckBtn.isChecked()) {
                try {
                    JSONObject mRiderCheckObj = new JSONObject();
                    mRiderCheckObj.put("ins_prod_cat", "Top Up");
                    mRiderCheckObj.put("ins_prod_type", mTopUpType);
                    mRiderCheckObj.put("sum_assured", UtileKit.getStringwithoutCurreny(mTopUpEdit.getEditText().getText().toString()));
                    mRiderCheckObj.put("id", mTopUpId);
                    jsonArray.put(mRiderCheckObj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (mSuperTopCheckBtn.isChecked()) {
                try {
                    JSONObject mRiderCheckObj = new JSONObject();
                    mRiderCheckObj.put("ins_prod_cat", "Super Top Up");
                    mRiderCheckObj.put("ins_prod_type", mSuperTopType);
                    mRiderCheckObj.put("sum_assured", UtileKit.getStringwithoutCurreny(mSuperTopEdit.getEditText().getText().toString()));
                    mRiderCheckObj.put("id", mSuperTopId);
                    jsonArray.put(mRiderCheckObj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (mBasicPlanCheckBtn.isChecked()) {
                try {
                    JSONObject mRiderCheckObj = new JSONObject();
                    mRiderCheckObj.put("ins_prod_cat", "Basic Plan");
                    mRiderCheckObj.put("ins_prod_type", mBasicType);
                    mRiderCheckObj.put("sum_assured", UtileKit.getStringwithoutCurreny(mBasicPlanEdit.getEditText().getText().toString()));
                    mRiderCheckObj.put("id", mBasicPlanId);
                    jsonArray.put(mRiderCheckObj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                obj.put("ins_det", jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ins_detJson = obj.toString();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

        int monthincr = month + 1;
//        mAssetPurchaseDateEdt.setText("" + year + "-" + monthincr + "-" + day);

        getEdtText().setText("" + day + "-" + monthincr + "-" + year);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        try {
            // family_id = mgoalBelongsToArrayListId.get(position);

            if (!mgoalBelongsToArrayListId.isEmpty()) {
                if (position != 0) {
                    family_id = mgoalBelongsToArrayListId.get(position - 1);
                } else {
                    family_id = mgoalBelongsToArrayListId.get(position);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        switch (parent.getId()) {
            case R.id.insur_belongsto_spinner:
                str_insurancebelong = mInsurBelongToSpinner.getSelectedItem().toString();


                if (position != 0) {
                    if (!isFirstTimeColor && isClicked) {
                        mInsurBelongToSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }
                break;


            /*case R.id.general_insurance_plan_type_spinner:
                plan_type = general_insurance_plan_type_spinner.getSelectedItem().toString();
                if (position != 0) {
                    if (!isFirstTimeColor && isClicked) {
                        general_insurance_plan_type_spinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }
                break;*/


            case R.id.life_insu_product_type_spinner:
                try {
                    if (position != 0)
                        insuraceProductType = lifeInsuranceList.get(position-1).getProd_type();
                } catch (Exception e) {
                }
                break;
            case R.id.general_sub_insu_type_spinner:
                try {
                    if (position != 0) {
                        insuraceSubType = mIns_sub_type.get(position-1).getIns_sub_type();
                        generalInsuProSpinnerList.clear();
                        getProductTypeList(insuraceSubType, generalInsuProSpinnerList);
                        setSpinnerAdapter(mGeneralProType_spinner, generalInsuProSpinnerList, mContext);

                        if(insuraceSubType.equalsIgnoreCase("Motor")){
                            sub_insu_motor_type_layout.setVisibility(View.VISIBLE);
                        }else {
                            sub_insu_motor_type_layout.setVisibility(View.GONE);

                        }



                    }
                    if (isCheckSubType) {
                        if (UtileKit.validateObjectValues(getInsuranceUserData.getIns_prod_type())) {
                            int pos = getSpinnerAssetposition(getInsuranceUserData.getIns_prod_type(), generalInsuProSpinnerList);
                            mGeneralProType_spinner.setSelection(pos);
                            isCheckSubType = false;
                        }
                    }
                } catch (Exception e) {
                }
                break;
            case R.id.general_insu_product_cat_spinner:
                try {
                    if (position != 0) {

                        insuraceProductType = generalInsuProSpinnerList.get(position-1);

                    }
                } catch (Exception e) {
                }
                break;
            case R.id.rider_spinnerId:
                try {
                    if (position != 0) {
                        mRiderCostType = mRiderObjList.get(position-1).getProd_type();
                    }
                } catch (Exception e) {
                }

                break;
            case R.id.supertop_up_spinnerId:
                try {
                    if (position != 0) {
                        mSuperTopType = mSuperTopObjList.get(position-1).getProd_type();
                    }
                } catch (Exception e) {
                }
                break;
            case R.id.basic_plan_spinnerId:
                try {
                    if (position != 0) {
                        mBasicType = mBasicPlanObjList.get(position-1).getProd_type();
                    }
                } catch (Exception e) {
                }
                break;
            case R.id.top_up_spinnerId:
                try {
                    if (position != 0) {
                        mTopUpType = mTopUpObjList.get(position-1).getProd_type();
                    }
                } catch (Exception e) {
                }

                break;
            default:
                break;
        }
    }

    public void getProductTypeList(String subInsuraceType, ArrayList<String> sortedList) {
        String lifeInsId = "";
//        sortedList.add("");
        for (Ins_sub_type ins_sub : mIns_sub_type) {
            if (ins_sub.getIns_sub_type().equalsIgnoreCase(subInsuraceType)) {
                lifeInsId = ins_sub.getId();
                break;
            }
        }

        int size = mIns_prod_type.size();
        for (int i = 0; i < size; i++) {
            if (mIns_prod_type.get(i).getIns_sub_type_id() != null) {
                if (mIns_prod_type.get(i).getIns_sub_type_id().equalsIgnoreCase(lifeInsId)) {
                    sortedList.add(mIns_prod_type.get(i).getProd_type());

                }
            }
        }
    }

    public void getSubProductList(String subInsuraceType, ArrayList<Ins_prod_type> sortedObjList, ArrayList<String> sortedList) {
        String lifeInsId = "";

        for (Ins_prod_cat ins_prod_cat : mIns_prod_cat) {
            if (ins_prod_cat.getProd_cat().equalsIgnoreCase(subInsuraceType)) {
                lifeInsId = ins_prod_cat.getId();
                break;
            }
        }

        int size = mIns_prod_type.size();
        for (int i = 0; i < size; i++) {
            if (mIns_prod_type.get(i).getIns_prod_cat_id() != null) {
                if (mIns_prod_type.get(i).getIns_prod_cat_id().equalsIgnoreCase(lifeInsId)) {
                    sortedList.add(mIns_prod_type.get(i).getProd_type());
                    sortedObjList.add(mIns_prod_type.get(i));

                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public String getStringwithoutCurreny(CurrencyEditText edt) {
        String str = edt.getText().toString().replace("₹", "").trim();
        return str;
    }

    public void setSpinnerAdapter(Spinner mMyMartialSpinner, ArrayList<String> mystringList, Context mycontext) {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("");
        for (String s : mystringList) {
            stringList.add(s);
        }
        CustomSpinerAdapter adapter_state = new CustomSpinerAdapter(mycontext, stringList);
        mMyMartialSpinner.setAdapter(adapter_state);

    }

   /* public void setSpinnerAdapter(Spinner mMyMartialSpinner, String[] myStringArray) {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("");
        for (String s : myStringArray) {
            stringList.add(s);
        }
        CustomSpinerAdapter adapter_state = new CustomSpinerAdapter(mContext, stringList);
        mMyMartialSpinner.setAdapter(adapter_state);
        mMyMartialSpinner.setOnItemSelectedListener(this);
    }*/


    private String setDateFormat(String date) {
        String output = "";
        try {
            if (date.trim().length() != 0) {
                String dateAray[] = date.split("-");
                output = dateAray[2].concat("-").concat(dateAray[1].concat("-").concat(dateAray[0]));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
        return output;
    }

    private int getSpinnerposition(String value, ArrayList<String> spinerlist) {
        int pos = 0;
        for (int i = 0; i < spinerlist.size(); i++) {
            if (value.equalsIgnoreCase(spinerlist.get(i))) {
                pos = i + 1;
                //Log.e("CallBack", " SpinnerPosition " + pos);
            }
        }
        return pos;
    }

    public void setListener(DialogFragmentInsurancesCallbackInterface callbackInterface) {
        this.callbackInterfaceInsurances = callbackInterface;
    }

//    @Override
//    public void upadateInsurancesYears(String dailog_tearms_years) {
//
//        Log.i("InsuranceDialogFrag", " Policy upadateInsurancesYears"+ dailog_tearms_years);
//        term_years = dailog_tearms_years;
////        minsurTerminyearEdt.setText(term_years);
////        UtileKit.edittextbordercolorchange(minsurTerminyearEdt);
//    }
//
//    @Override
//    public void upadatepolicy_issue_date(String policyissuedate) {
//
//        Log.i("InsuranceDialogFrag", "policy_issue_dates"+ policy_issue_date);
//        policy_issue_date = policyissuedate;
//
//        mInsurpolicyIssuedateEdt.setText(policy_issue_date);
//        UtileKit.edittextbordercolorchange(mInsurpolicyIssuedateEdt);
//
//
//    }
//
//    @Override
//    public void upadatelast_Premium_Paid_Date(String last_Premium_Paid_Date) {
//        Log.i("InsuranceDialogFrag", "last_Premium_Paid_Date"+ last_Premium_Paid_Date);
//        last_prem_date = last_Premium_Paid_Date;
//        mInsurlastpremiumpaidEdt.setText(last_prem_date);
//        UtileKit.edittextbordercolorchange(mInsurlastpremiumpaidEdt);
//    }
//
//    @Override
//    public void upadatenext_Premium_Due_Date(String next_Premium_Due_Date) {
//        Log.i("InsuranceDialogFrag", "next_Premium_Due_Date"+ next_Premium_Due_Date);
//        next_prem_date = next_Premium_Due_Date;
//        mInsurnextpreminumpaidEdt.setText(next_prem_date);
//        UtileKit.edittextbordercolorchange(mInsurnextpreminumpaidEdt);
//
//    }
//
//    @Override
//    public void upadatePremium_Due_Till_Date(String premium_Due_Till_Date) {
//        Log.i("InsuranceDialogFrag", "premium_Due_Till_Date"+ premium_Due_Till_Date);
//        prem_due_date = premium_Due_Till_Date;
//        mInsurpremiumduetilldateEdt.setText(prem_due_date);
//        UtileKit.edittextbordercolorchange(mInsurpremiumduetilldateEdt);
//    }
//
//    @Override
//    public void upadatepolicy_end_date(String policy_end_dates) {
//        Log.i("InsuranceDialogFrag", "policy_end_dates"+ policy_end_dates);
//
//        policy_end_date = policy_end_dates;
//        mInsurpolicyenddateEdt.setText(policy_end_date);
//        UtileKit.edittextbordercolorchange(mInsurpolicyenddateEdt);
//    }
//
//    @Override
//    public void upadatematurity_Date(String maturity_Dates) {
//        Log.i("InsuranceDialogFrag", "maturity_Date"+ maturity_Dates);
//
//        maturity_date = maturity_Dates;
//        mInsmaturitydateEdt.setText(maturity_date);
//        UtileKit.edittextbordercolorchange(mInsmaturitydateEdt);
//    }

    @Override
    public void updateEditTextValue(String value, String title) {
        Log.i("InsuranceDialogFrag", "title  :" + title + "value  :" + value);
        if (title.equalsIgnoreCase(getString(R.string.policyIssueDateDialog))) {
            mInsurpolicyIssuedateEdt.setText(value);
            UtileKit.edittextbordercolorchange(mInsurpolicyIssuedateEdt);
        } else if (title.equalsIgnoreCase(mterm_years)) {

//                minsurTerminyearEdt.setText(value);
//                UtileKit.edittextbordercolorchange(minsurTerminyearEdt);
//            }
        } else if (title.equalsIgnoreCase(mlast_prem_date)) {
            mInsurlastpremiumpaidEdt.setText(value);
            UtileKit.edittextbordercolorchange(mInsurlastpremiumpaidEdt);
        } else if (title.equalsIgnoreCase(mnext_prem_date)) {
            mInsurnextpreminumpaidEdt.setText(value);
            UtileKit.edittextbordercolorchange(mInsurnextpreminumpaidEdt);
        } else if (title.equalsIgnoreCase(mprem_due_date)) {
            mInsurpremiumduetilldateEdt.setText(value);
            UtileKit.edittextbordercolorchange(mInsurpremiumduetilldateEdt);
        } else if (title.equalsIgnoreCase(mmaturity_date)) {
            mInsmaturitydateEdt.setText(value);
            UtileKit.edittextbordercolorchange(mInsmaturitydateEdt);
        } else if (title.equalsIgnoreCase(mpolicy_end_date)) {
            mInsurpolicyenddateEdt.setText(value);
            UtileKit.edittextbordercolorchange(mInsurpolicyenddateEdt);
        } else if (title.equalsIgnoreCase(minsurancePaidDueDate)) {
            insur_paidduedate_edt.setText(value);
            UtileKit.edittextbordercolorchange(insur_paidduedate_edt);
        }


    }

    @Override
    public void updateIndividualEditTextValue(String value, String title) {

    }

    private String displayYearCalulation(String monthtosetinEdittext) {
        Log.i(TAG, "displayYearCalulation parameter year" + monthtosetinEdittext);
        int selectedYear = Integer.parseInt(monthtosetinEdittext);
        String yearValue = "";
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            final int currentYear = calendar.get(Calendar.YEAR);
            if (selectedYear < currentYear) {
                yearValue = String.valueOf(Utils.getAgeDataSubFromCurrentYear(selectedYear));
            } else {
                yearValue = String.valueOf(Utils.getAgeDataSubFromCurrentYearisGreater(selectedYear));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return yearValue;
    }

    String getcurrentYear(String getyear) {
        String years = "";
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            Date date = new Date();
            Log.i("", "yearGeneratorpastYear date" + dateFormat.format(date));
            String[] items1 = dateFormat.format(date).split("/");
            Log.i("", "yearGeneratorpastYear year" + items1[0]);
            int currentdate = Integer.parseInt(items1[2]);
            int currentmonth = Integer.parseInt(items1[1]);
            int currentyear = Integer.parseInt(items1[0]);
            int updateyears = 1;
            if (Integer.parseInt(getyear) < currentyear) {
                updateyears = currentyear - Integer.parseInt(getyear);

            } else {
                updateyears = Integer.parseInt(getyear) - currentyear;
            }

            Log.i("", "yearGeneratorpastYear updateyears" + updateyears);
            years = String.valueOf(updateyears);
            return years;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return years;
    }

    private void getFirstTimeCurrentDateandMonth() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            Date date = new Date();
            Log.i("", "yearGeneratorpastYear date" + dateFormat.format(date));
            String[] items1 = dateFormat.format(date).split("/");
            Log.i("", "yearGeneratorpastYear year" + items1[0]);
            currentdateFirsttime = Integer.parseInt(items1[2]);
            currentmonthFirsttime = Integer.parseInt(items1[1]);
            currentyearFirsttime = Integer.parseInt(items1[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.riderId:
                if (b) {
                    mRiderCostEdit.getEditText().setEnabled(true);
                    mRiderCostSpinner.setEnabled(true);

                } else {
                    mRiderCostEdit.getEditText().setEnabled(false);
                    mRiderCostSpinner.setEnabled(false);
                }
                break;
            case R.id.top_upId:
                if (b) {
                    mTopUpEdit.getEditText().setEnabled(true);
                    mTopUpSpinner.setEnabled(true);
                } else {
                    mTopUpEdit.getEditText().setEnabled(false);
                    mTopUpSpinner.setEnabled(false);
                }
                break;
            case R.id.supertop_upId:
                if (b) {
                    mSuperTopEdit.getEditText().setEnabled(true);
                    mSuperTopSpinner.setEnabled(true);
                } else {
                    mSuperTopEdit.getEditText().setEnabled(false);
                    mSuperTopSpinner.setEnabled(true);
                }
                break;
            case R.id.basic_PlanId:
                if (b) {
                    mBasicPlanEdit.getEditText().setEnabled(true);
                    mBasicPlanSpinner.setEnabled(true);
                } else {
                    mBasicPlanEdit.getEditText().setEnabled(false);
                    mBasicPlanSpinner.setEnabled(false);
                }
                break;
        }

    }
}