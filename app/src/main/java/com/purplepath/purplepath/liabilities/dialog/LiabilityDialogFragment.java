package com.purplepath.purplepath.liabilities.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.calculator.CalculatorAct;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.calenderNumberPicker.calenderUi.CalenderTabs;
import com.purplepath.purplepath.customview.CalendarEditText;
import com.purplepath.purplepath.customview.CharacterEditText;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.customview.CustomCalenderImageView;
import com.purplepath.purplepath.customview.CustomSpinerAdapter;
import com.purplepath.purplepath.customview.CustomTextInputLayout;
import com.purplepath.purplepath.customview.NumberEditText;
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.goal.GoalFamilyDetails;
import com.purplepath.purplepath.goal.GoalFamilyDetailsModel;
import com.purplepath.purplepath.goal.InterfaceDone.FirstTimeDoneInterface;
import com.purplepath.purplepath.insurance.insuranceinterface.OnCheckListIsEmpty;
import com.purplepath.purplepath.liabilities.model.AddLiabilityModel;
import com.purplepath.purplepath.liabilities.model.LiabCategoryModel;
import com.purplepath.purplepath.liabilities.model.Liab_cat_lev1;
import com.purplepath.purplepath.liabilities.model.Liab_cat_lev2;
import com.purplepath.purplepath.liabilities.model.Liab_cat_lev3;
import com.purplepath.purplepath.liabilities.model.UserLiabilityList;
import com.purplepath.purplepath.myinterface.DatePickerCallBackInterface;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

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

import static com.purplepath.purplepath.incomedetails.IncomeDynamicDetail.PARENT_CLASS_SOURCE;


public class LiabilityDialogFragment extends DialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener , RadioGroup.OnCheckedChangeListener,DatePickerCallBackInterface {

    private Spinner mliabiCategoriesOneSpinner,
            mliabiCategoriesTwoSpinner, mliabiCategoriesThreeSpinner,mAssetCatLinkSpinner,mlib_loan_due_date;
    TextInputLayout liabi_name_edt_inputlayout;
    private EditText  mTypeEdit,   mOtherEdt,  mCalanderEdt;
    private CalendarEditText mStartYearEdit,mEndYearEdit,mnextPremiumDuedate,mLastPremiumDueDate;
    private PercentageEditText mIntrestRateEdt;
    private CharacterEditText mLibNameEdit,mLenderNameEdit,mWholeNameEdit;
    //private  CharacterOnlyEdittext mLibNameEdit;
    //private CurrencyDefaultEdt mLoanAmoutEdt,mOutStandingBalaceEdt,mCurrentEmiEdt;
    private CurrencyGhostView mLoanAmoutEdt,mOutStandingBalaceEdt,mCurrentEmiEdt;
    //private NumberEditText mTotalTenureEdt;
    //private EditText ;
    private NumberEditText mTotalTenureEdt,mBalaceTenureEdit;
    private TextView liabi_objective_spinner, mliabiObjectiveTxt;
    private Activity activity;
    private LiabCategoryModel liabCategoriesModel;
//    private Addliabiodel addliabiodel;

    private AddLiabilityModel addLiabilityModel;
    private int currentdateFirsttime,currentmonthFirsttime,currentyearFirsttime;
    private ArrayList<Liab_cat_lev1> liabCategoriesLevelOneList = new ArrayList<Liab_cat_lev1>();
    private ArrayList<Liab_cat_lev2> liabCategoriesLevelTwoList = new ArrayList<Liab_cat_lev2>();
    private ArrayList<Liab_cat_lev3> liabCategoriesLevelThreeList = new ArrayList<Liab_cat_lev3>();

    private ArrayList<String> liabCategoriesLevelOneNameList = new ArrayList<String>();
    private ArrayList<String> liabCategoriesLevelTwoNameList = new ArrayList<String>();
    private ArrayList<String> liabCategoriesLevelThreenameList = new ArrayList<String>();

    private ArrayList<Liab_cat_lev2> liabCategoriesLevelTwoListonSelect = new ArrayList<Liab_cat_lev2>();
    private ArrayList<Liab_cat_lev3> liabCategoriesLevelThreeListonSelect = new ArrayList<Liab_cat_lev3>();

    private LinearLayout liabiategoriesTwoLayout, liabiategoriesThreeLayout;

    String user_id, mType, liab_name, cat_lev1_id, cat_lev2_id, cat_lev3_id, lenderName, loan_amt, outst_bal, current_emi,
            interest_rate, total_tenure, balance_tenure, start_year, end_year, whose_name, insured,mAssetLink,lastpaiddate,
            nextduedate,loanfreq;
//            user_id, family_id, type, liabiOtherEdt, cat_lev1_id, cat_lev2_id, cat_lev3_id,
//            other_cat,objective, current_value,
//            annual_contr, freq_of_contr, years_of_contr, years_to_maturity, alloc_to_goal, purchase_date, purchase_val, notes,lenderName;
private ArrayList<String> assetListCataLink = new ArrayList(Arrays.asList("Commodities/Gold","Employment Benefit","Equity" ,"Fixed Income/Debt","Household Asset","Liquid","Real Estate/Property"));
    private   ArrayList<String> monthInArray  = new ArrayList(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                                                                             "11", "12", "13", "14", "15", "16", "17", "18", "19","20",
                                                                              "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
                                                                                "31","32","33","34","35","36","37","38","39","40",
                                                                                "41","42","43","44","45","46","47","48","49","50",
                                                                                   "51","52","53","54","55","56","57","58","59","60"));

    private DatePickerDialog datePickerDialog;
    private Calendar calendar;


    private GoalFamilyDetailsModel addFamilyDetailModel;
    private ArrayList<GoalFamilyDetails> familyDetails = new ArrayList<GoalFamilyDetails>();
    private ArrayList<String> mgoalBelongsToArrayList = new ArrayList<String>();
    private ArrayList<String> mgoalBelongsToArrayListId = new ArrayList<String>();

    private LinearLayout otherLinearLayout;


    private boolean IsOnFirstSpinLev2Load=false,IsOnFirstLev3Load=false;
    private Context mContext;

    private RadioGroup mgenderRadioGroupe = null;
    private RadioButton mgenderMaleRadioBtn, mgenderFemaleRadioBtn;
    private  OnCheckListIsEmpty onCheckListIsEmptyOrNot;
    private ImageView backBtn;
    private TextView titleNameTxt;
    private UserLiabilityList mUserLiabilityList;
    private String startYearTitle="Select Start Year",endYearTitle="Select End Year";
    private String next_premium_due_date="Next Premium Due Date",last_premium_due_date="Last Premium Due Date";
    private String loan_frequency_due_date="Loan Frequency Due Date";
    private FloatingActionButton mSubmitbtn;
    Bundle args=new Bundle();
    View nameEditview;
    public static final String TITLE = "";
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private CustomCalenderImageView mliabi_loan_amt_calculaterImgView,mliabi_outstanding_loan_amt_calculaterImgView,
                      mliabi_current_emi_calculaterImgView;

    private  boolean isFirstTimeColor = false;
    private boolean isClicked;
    public HashMap<String,View> errorMapView=new HashMap<>();



    ArrayList<String> formArray = new ArrayList<String>();
    private boolean isMandatory;
    Boolean isSignUp = false;
    public HashMap<String,View> mandatoryMapView=new HashMap<>();
    HashMap<String, String> mandatoryPromptMapView = new HashMap<String, String>() {{
        put("Name","Enter your liability");
        put("Objective","Enter your objective");
        put("Categories","Choose your categories");
        put("Lender Name","Enter Lender name");
        put("Loan Amount","Enter loan amount");
        put("Oustanding balance","Enter outstanding balance");
        put("Current EMI","Enter your current EMI");
        put("Interest Rate","Enter your Interest rate");
        put("Total Tenure(in Months)","Enter the total tenure");
        put("Balance Tenure","Enter the balance tenure");
    }};
    public HashMap<String,View> mandatoryPromptPut=new HashMap<>();
    private LinearLayout bottom_bar_layout,bottom_bar_donelayout;
    private RelativeLayout relative_finish_later,relative_done_arrow;
    private LinearLayout name_layout,objective_layout,categories_layout,asset_links_layout,lender_name_layout,
            loan_amount_layout,outstanding_balance_layout,current_emi_layout,interest_layout,
            total_tenure_in_months_layout,balance_tenure_layout,last_premium_due_date_layout,
            next_premium_due_date_layout,loan_frequency_layout,start_year_layout,end_year_layout,
            whose_name_layout,insured_yes_no_layout;

    Bundle mArgs;
    FirstTimeDoneInterface firstTimeDoneInterface;


//    public static LiabilityDialogFragment newInstance() {
//        LiabilityDialogFragment liabilityDialogFragment = new LiabilityDialogFragment();
//        return liabilityDialogFragment;
//    }

//    public static LiabilityDialogFragment newInstance(UserLiabilityList userLiabilityList,
//                                                      LiabCategoryModel mliabiCategoryModel, String type,
//                                                      OnCheckListIsEmpty onCheckListIsEmptyOrNot,
//                                                      Boolean isSignUp, ArrayList<String> formArray) {
//        LiabilityDialogFragment fragment = new LiabilityDialogFragment();
//        Bundle args = new Bundle();
//        args.putSerializable("param", mliabiCategoryModel);
//        args.putString("param1",type);
//        args.putSerializable("param3",userLiabilityList);
//        fragment.onCheckListIsEmptyOrNot=onCheckListIsEmptyOrNot;
//
//
//
//        if (isSignUp != null) {
//            args.putSerializable("isSignUp", isSignUp);
//        }
//        if(formArray!=null){
//            args.putSerializable("formArray",formArray);
//        }
//
//        fragment.setArguments(args);
//        return fragment;
//    }
//



    public static LiabilityDialogFragment newInstance(UserLiabilityList userLiabilityList,
                                                      LiabCategoryModel mliabiCategoryModel, String type,
                                                      OnCheckListIsEmpty onCheckListIsEmptyOrNot,
                                                      Boolean isSignUp, ArrayList<String> formArray,
                                                      FirstTimeDoneInterface firstTimeDoneInterface) {
        LiabilityDialogFragment fragment = new LiabilityDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("param", mliabiCategoryModel);
        args.putString("param1",type);
        args.putSerializable("param3",userLiabilityList);

        args.putBoolean("isSignUp",isSignUp);
        args.putSerializable("formArray",formArray);

        fragment.onCheckListIsEmptyOrNot=onCheckListIsEmptyOrNot;
        fragment.firstTimeDoneInterface=firstTimeDoneInterface;

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public LiabilityDialogFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.MY_DIALOG);

//        mArgs = getArguments();
//        if(mArgs!=null) {
//            isSignUp=mArgs.getBoolean("isSignUp");
//
//            Log.d("isSignUp","isSignUp"+isSignUp);
//        }

        if (getArguments() != null) {

            if (getArguments().containsKey("param"))
                liabCategoriesModel = (LiabCategoryModel) getArguments().getSerializable("param");

            Log.d("liabCategoriesModel","liabCategoriesModel"+liabCategoriesModel);

            if(getArguments().containsKey("param1"))
            {
                mType= (String)getArguments().getSerializable("param1");
                Log.d("mType","mType"+mType);
            }
            if(getArguments().containsKey("param3"))
            {
                mUserLiabilityList= (UserLiabilityList) getArguments().getSerializable("param3");
                Log.d("mUserLiabilityList","mUserLiabilityList"+mUserLiabilityList);
            }

            if (getArguments().containsKey("isSignUp")) {
                isSignUp = getArguments().getBoolean("isSignUp");
                Log.d("isSignUp","isSignUp"+isSignUp);
            }

            if (getArguments().containsKey("formArray")) {
                formArray = (ArrayList<String>) getArguments().getSerializable("formArray");
                Log.d("formArray","formArray"+formArray);



            }

            if (liabCategoriesModel != null) {

                liabCategoriesLevelTwoList = liabCategoriesModel.getData().getLiab_cat_lev2();
                liabCategoriesLevelThreeList = liabCategoriesModel.getData().getLiab_cat_lev3();

                if (liabCategoriesModel.getData().getLiab_cat_lev1() != null) {
                    for (int i = 0; i < liabCategoriesModel.getData().getLiab_cat_lev1().size(); i++) {
                        if(mType.equalsIgnoreCase(liabCategoriesModel.getData().getLiab_cat_lev1().get(i).getType())) {
                            liabCategoriesLevelOneList.add(liabCategoriesModel.getData().getLiab_cat_lev1().get(i));
                            liabCategoriesLevelOneNameList.add(liabCategoriesModel.getData().getLiab_cat_lev1().get(i).getLev1_name());
                        }
                    }


                }
            }

            calendar = new GregorianCalendar();
            datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), isVibrate());
        }

        try {
            addliabi();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View liabiiew = inflater.inflate(R.layout.dialog_fragment_liability, container, false);
        backBtn= liabiiew.findViewById(R.id.backButtonId);
        titleNameTxt= liabiiew.findViewById(R.id.dialogTitleId);
        titleNameTxt.setText(""+mType);
        liabi_name_edt_inputlayout = liabiiew.findViewById(R.id.liabi_name_edt_inputlayout);
        liabiategoriesTwoLayout = liabiiew.findViewById(R.id.liabi_categoriestwo_layout);
        liabiategoriesThreeLayout = liabiiew.findViewById(R.id.liabi_categoriesthree_layout);
        mOtherEdt = liabiiew.findViewById(R.id.liabi_other_edt);
        mSubmitbtn = liabiiew.findViewById(R.id.liabiButton);
        mSubmitbtn.setOnClickListener(this);

        name_layout=liabiiew.findViewById(R.id.name_layout);
        mandatoryMapView.put("Name",name_layout);

        objective_layout=liabiiew.findViewById(R.id.objective_layout);
        mandatoryMapView.put("Objective",objective_layout);

        categories_layout=liabiiew.findViewById(R.id.categories_layout);
        mandatoryMapView.put("Categories",categories_layout);


        mandatoryMapView.put("liabiategoriesTwoLayout",liabiategoriesTwoLayout);
        mandatoryMapView.put("liabiategoriesThreeLayout",liabiategoriesThreeLayout);


        asset_links_layout=liabiiew.findViewById(R.id.asset_links_layout);
        mandatoryMapView.put("asset_links_layout",asset_links_layout);

        lender_name_layout=liabiiew.findViewById(R.id.lender_name_layout);
        mandatoryMapView.put("Lender Name",lender_name_layout);

        loan_amount_layout=liabiiew.findViewById(R.id.loan_amount_layout);
        mandatoryMapView.put("Loan Amount",loan_amount_layout);

        outstanding_balance_layout=liabiiew.findViewById(R.id.outstanding_balance_layout);
        mandatoryMapView.put("Oustanding balance",outstanding_balance_layout);

        current_emi_layout=liabiiew.findViewById(R.id.current_emi_layout);
        mandatoryMapView.put("Current EMI",current_emi_layout);

        interest_layout=liabiiew.findViewById(R.id.interest_layout);
        mandatoryMapView.put("Interest Rate",interest_layout);

        total_tenure_in_months_layout=liabiiew.findViewById(R.id.total_tenure_in_months_layout);
        mandatoryMapView.put("Total Tenure(in Months)",total_tenure_in_months_layout);

        balance_tenure_layout=liabiiew.findViewById(R.id.balance_tenure_layout);
        mandatoryMapView.put("Balance Tenure",balance_tenure_layout);

        last_premium_due_date_layout=liabiiew.findViewById(R.id.last_premium_due_date_layout);
        mandatoryMapView.put("last_premium_due_date_layout",last_premium_due_date_layout);

        next_premium_due_date_layout=liabiiew.findViewById(R.id.next_premium_due_date_layout);
        mandatoryMapView.put("next_premium_due_date_layout",next_premium_due_date_layout);

        loan_frequency_layout=liabiiew.findViewById(R.id.loan_frequency_layout);
        mandatoryMapView.put("loan_frequency_layout",loan_frequency_layout);

        start_year_layout=liabiiew.findViewById(R.id.start_year_layout);
        mandatoryMapView.put("start_year_layout",start_year_layout);

        end_year_layout=liabiiew.findViewById(R.id.end_year_layout);
        mandatoryMapView.put("end_year_layout",end_year_layout);

        whose_name_layout=liabiiew.findViewById(R.id.whose_name_layout);
        mandatoryMapView.put("whose_name_layout",whose_name_layout);

        insured_yes_no_layout=liabiiew.findViewById(R.id.insured_yes_no_layout);
        mandatoryMapView.put("insured_yes_no_layout",insured_yes_no_layout);



        bottom_bar_layout= liabiiew.findViewById(R.id.bottom_bar_layout);
        UtileKit.mandatoryFieldLinearLayout(isSignUp,bottom_bar_layout);

        bottom_bar_donelayout= liabiiew.findViewById(R.id.bottom_bar_donelayout);
        UtileKit.mandatoryFieldDoneLayout(isSignUp,bottom_bar_donelayout);


        mliabiObjectiveTxt = liabiiew.findViewById(R.id.liabi_objective_spinner);
        mandatoryPromptPut.put("Objective",mliabiObjectiveTxt);
        errorMapView.put("type",mliabiObjectiveTxt);
//        mliabiObjectiveTxt.setOnItemSelectedListener(this);

        mliabiCategoriesOneSpinner = liabiiew.findViewById(R.id.liabi_categoriesone_spinner);
        errorMapView.put("cat_lev1_id",mliabiCategoriesOneSpinner);
        mandatoryPromptPut.put("Objective",mliabiCategoriesOneSpinner);
        mliabiCategoriesOneSpinner.setOnItemSelectedListener(this);

        mliabiCategoriesTwoSpinner = liabiiew.findViewById(R.id.liabi_categoriestwo_spinner);
        errorMapView.put("cat_lev2_id",mliabiCategoriesTwoSpinner);

        mliabiCategoriesTwoSpinner.setOnItemSelectedListener(this);

        otherLinearLayout = liabiiew.findViewById(R.id.liabi_others_layout);

        mliabiCategoriesThreeSpinner = liabiiew.findViewById(R.id.liabi_categoriesthree_spinner);
        errorMapView.put("cat_lev3_id",mliabiCategoriesThreeSpinner);

        mliabiCategoriesThreeSpinner.setOnItemSelectedListener(this);

        mAssetCatLinkSpinner = liabiiew.findViewById(R.id.liabi_goal_cateLink_spinner);
        errorMapView.put("linked_asset",mAssetCatLinkSpinner);
        mAssetCatLinkSpinner.setOnItemSelectedListener(this);
        mAssetCatLinkSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });

        setSpinnerAdapter(mAssetCatLinkSpinner, assetListCataLink);

        mLibNameEdit = liabiiew.findViewById(R.id.liabi_name_edt);
        errorMapView.put("liab_name",mLibNameEdit);
        mandatoryPromptPut.put("Name",mLibNameEdit);

        mlib_loan_due_date= liabiiew.findViewById(R.id.liabi_loan_frequency_date_spinner);
        errorMapView.put("loan_freq",mlib_loan_due_date);
        mlib_loan_due_date.setOnItemSelectedListener(this);
        mlib_loan_due_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });


        setSpinnerAdapter(mlib_loan_due_date, monthInArray);

//commit
//        mLibNameEdit = (CharacterOnlyEdittext) liabiiew.findViewById(R.id.liabi_name_edt);
        mTypeEdit = liabiiew.findViewById(R.id.liabi_type_edt);
        mLenderNameEdit = liabiiew.findViewById(R.id.liabi_liab_LenderNameEditId);
        errorMapView.put("lender",mLenderNameEdit);
        mandatoryPromptPut.put("Lender Name",mLenderNameEdit);


        mCurrentEmiEdt = liabiiew.findViewById(R.id.liabi_current_emi_edt);
        errorMapView.put("current_emi",mCurrentEmiEdt);
        mCurrentEmiEdt.setTextHint("Current EMI*");
        mandatoryPromptPut.put("Current EMI",mCurrentEmiEdt);



        mLoanAmoutEdt = liabiiew.findViewById(R.id.liabi_loan_amount_edt);
        errorMapView.put("loan_amt",mLoanAmoutEdt);
        mLoanAmoutEdt.setTextHint("Loan Amount*");
        mandatoryPromptPut.put("Loan Amount",mLoanAmoutEdt);


        mOutStandingBalaceEdt = liabiiew.findViewById(R.id.liabi_outstanding_loan_edit);
        errorMapView.put("outst_bal",mOutStandingBalaceEdt);
        mOutStandingBalaceEdt.setTextHint("Outstanding balance*");
        mandatoryPromptPut.put("Oustanding balance",mOutStandingBalaceEdt);


        mIntrestRateEdt = liabiiew.findViewById(R.id.liabi_interest_rate_edt);
        errorMapView.put("interest_rate",mIntrestRateEdt);
        mandatoryPromptPut.put("Interest Rate",mIntrestRateEdt);


        mTotalTenureEdt = liabiiew.findViewById(R.id.liabi_total_tenure_edt);
        errorMapView.put("total_tenure",mTotalTenureEdt);
        mandatoryPromptPut.put("Total Tenure(in Months)",mTotalTenureEdt);


        mBalaceTenureEdit = liabiiew.findViewById(R.id.liabi_balance_tenure_edt);
        errorMapView.put("balance_tenure",mBalaceTenureEdit);
        mandatoryPromptPut.put("Balance Tenure",mBalaceTenureEdit);



        mnextPremiumDuedate = liabiiew.findViewById(R.id.liabi_next_premium_due_date_edt);
        errorMapView.put("next_due_date",mnextPremiumDuedate);


        mLastPremiumDueDate= liabiiew.findViewById(R.id.liabi_last_premium_due_date_edt);
        errorMapView.put("last_paid_date",mLastPremiumDueDate);



        mStartYearEdit = liabiiew.findViewById(R.id.liabi_start_year_edt);
        errorMapView.put("start_year",mStartYearEdit);



        mEndYearEdit = liabiiew.findViewById(R.id.liabi_end_year_edt);
        errorMapView.put("end_year",mEndYearEdit);


        mWholeNameEdit = liabiiew.findViewById(R.id.liabi_whose_name_edt);
        errorMapView.put("whose_name",mWholeNameEdit);


        mOtherEdt = liabiiew.findViewById(R.id.liabi_other_edt);
        mgenderRadioGroupe = liabiiew.findViewById(R.id.liabi_Insured_RadioRg);
        mgenderMaleRadioBtn = liabiiew.findViewById(R.id.liabi_Insured_yes_RadioBtn);
        mgenderFemaleRadioBtn = liabiiew.findViewById(R.id.liabi_Insured_no_RadioBtn);
        mleftRelativeLayout = liabiiew.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = liabiiew.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = liabiiew.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        mgenderRadioGroupe.setOnCheckedChangeListener(this);
//        mysetChecked(mgenderMaleRadioBtn, mgenderFemaleRadioBtn);

        mliabi_loan_amt_calculaterImgView= liabiiew.findViewById(R.id.liabi_loan_amt_calculaterImgView);
        mliabi_outstanding_loan_amt_calculaterImgView= liabiiew.findViewById(R.id.liabi_outstanding_loan_amt_calculaterImgView);
        mliabi_current_emi_calculaterImgView= liabiiew.findViewById(R.id.liabi_current_emi_calculaterImgView);

        mliabi_loan_amt_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(mLoanAmoutEdt);
            }
        });
        mliabi_outstanding_loan_amt_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(mOutStandingBalaceEdt);
            }
        });
        mliabi_current_emi_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(mCurrentEmiEdt);
            }
        });
        editTextDrawableClick(mStartYearEdit,startYearTitle);
        editTextDrawableClick(mEndYearEdit,endYearTitle);
        editTextDrawableClick(mnextPremiumDuedate,next_premium_due_date);
        editTextDrawableClick(mLastPremiumDueDate,last_premium_due_date);
//        editTextDrawableClick(mlib_loan_due_date,loan_frequency_due_date);

        mliabiObjectiveTxt.setText(""+mType);
//        UtileKit.setStringArraySpinnerAdapter(mliabiObjectiveTxt, AppConstants.objective, activity);
//        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(
//                mContext,
//                android.R.layout.simple_spinner_dropdown_item, liabCategoriesLevelOneNameList);
//        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mliabiCategoriesOneSpinner.setAdapter(adapter_state);
        setSpinnerAdapter(mliabiCategoriesOneSpinner,liabCategoriesLevelOneNameList);
        if(mUserLiabilityList!=null)
        {
            setUserDateInput();
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mLibNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                liabi_name_edt_inputlayout.setErrorEnabled(false);
                liabi_name_edt_inputlayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        if(isSignUp==false) {
            if (mType.equalsIgnoreCase("Institutions")) {
        /*  bank*/
                mLibNameEdit.setHintText(getString(R.string.hint_bank_laibility_name), ((TextInputLayout) (mLibNameEdit.getParent()).getParent()));
                mLenderNameEdit.setHintText(getString(R.string.hint_bank_laibility_lender_name), ((TextInputLayout) (mLenderNameEdit.getParent()).getParent()));
                mLoanAmoutEdt.setfullHintTxt(getString(R.string.hint_bank_laibility_loan_amount));
                mOutStandingBalaceEdt.setfullHintTxt(getString(R.string.hint_bank_laibility_outstand_balan));
                mCurrentEmiEdt.setfullHintTxt(getString(R.string.hint_bank_laibility_current_emi));
                mIntrestRateEdt.setHintText(getString(R.string.hint_bank_laibility_interest_rate), ((TextInputLayout) (mIntrestRateEdt.getParent()).getParent()));
                mTotalTenureEdt.setHintText(getString(R.string.hint_bank_laibility_total_tenure), ((TextInputLayout) (mTotalTenureEdt.getParent()).getParent()));
                mBalaceTenureEdit.setHintText(getString(R.string.hint_bank_laibility_balance_tenure), ((TextInputLayout) (mBalaceTenureEdit.getParent()).getParent()));
                mLastPremiumDueDate.setHintText(getString(R.string.hint_bank_laibility_last_premium_due_date), ((TextInputLayout) (mLastPremiumDueDate.getParent()).getParent()));
                mnextPremiumDuedate.setHintText(getString(R.string.hint_bank_laibility_next_premium_due_date), ((TextInputLayout) (mnextPremiumDuedate.getParent()).getParent()));
                mStartYearEdit.setHintText(getString(R.string.hint_bank_laibility_start_year), ((TextInputLayout) (mStartYearEdit.getParent()).getParent()));
                mEndYearEdit.setHintText(getString(R.string.hint_bank_laibility_end_year), ((TextInputLayout) (mEndYearEdit.getParent()).getParent()));
                mWholeNameEdit.setHintText(getString(R.string.hint_bank_laibility_whose_name), ((TextInputLayout) (mWholeNameEdit.getParent()).getParent()));
            } else {
        /* friends*/
                mLibNameEdit.setHintText(getString(R.string.hint_friend_laibility_name), ((TextInputLayout) (mLibNameEdit.getParent()).getParent()));
                mLenderNameEdit.setHintText(getString(R.string.hint_friend_laibility_lender_name), ((TextInputLayout) (mLenderNameEdit.getParent()).getParent()));
                mLoanAmoutEdt.setfullHintTxt(getString(R.string.hint_friend_laibility_loan_amount));
                mOutStandingBalaceEdt.setfullHintTxt(getString(R.string.hint_friend_laibility_outstand_balan));
                mCurrentEmiEdt.setfullHintTxt(getString(R.string.hint_friend_laibility_current_emi));
                mIntrestRateEdt.setHintText(getString(R.string.hint_friend_laibility_interest_rate), ((TextInputLayout) (mIntrestRateEdt.getParent()).getParent()));
                mTotalTenureEdt.setHintText(getString(R.string.hint_friend_laibility_total_tenure), ((TextInputLayout) (mTotalTenureEdt.getParent()).getParent()));
                mBalaceTenureEdit.setHintText(getString(R.string.hint_friend_laibility_balance_tenure), ((TextInputLayout) (mBalaceTenureEdit.getParent()).getParent()));
                mLastPremiumDueDate.setHintText(getString(R.string.hint_friend_laibility_last_premium_due_date), ((TextInputLayout) (mLastPremiumDueDate.getParent()).getParent()));
                mnextPremiumDuedate.setHintText(getString(R.string.hint_friend_laibility_next_premium_due_date), ((TextInputLayout) (mnextPremiumDuedate.getParent()).getParent()));
                mStartYearEdit.setHintText(getString(R.string.hint_friend_laibility_start_year), ((TextInputLayout) (mStartYearEdit.getParent()).getParent()));
                mEndYearEdit.setHintText(getString(R.string.hint_friend_laibility_end_year), ((TextInputLayout) (mEndYearEdit.getParent()).getParent()));
                mWholeNameEdit.setHintText(getString(R.string.hint_friend_laibility_whose_name), ((TextInputLayout) (mWholeNameEdit.getParent()).getParent()));
            }
        }
        relative_finish_later= liabiiew.findViewById(R.id.relative_finish_later);
        relative_done_arrow= liabiiew.findViewById(R.id.relative_done_arrow);
        relative_finish_later.setOnClickListener(this);
        relative_done_arrow.setOnClickListener(this);

        //signup propmt
        if(isSignUp==true) {
            mSubmitbtn.setVisibility(View.GONE);
            for (String key : mandatoryMapView.keySet()) {
                mandatoryMapView.get(key).setVisibility(View.GONE);
            }
        }

        emptyErrorValidationMandatory(formArray);




        return liabiiew;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void emptyErrorValidationMandatory(ArrayList<String> emptyArrayList) {
        if(isSignUp==true) {
            for (String obj : emptyArrayList) {
                View view = mandatoryMapView.get(obj);
                view.setVisibility(View.VISIBLE);
            }
        }
    }
    private Boolean emptyErrorPromptValidation(ArrayList<String> emptyArrayList){
        if(isSignUp==true) {
            isMandatory=true;
            for (String key : emptyArrayList) {
                View view = mandatoryPromptPut.get(key);
                emptyErrorViewPrompt(view,key);
            }
        }return isMandatory;
    }

    void emptyErrorViewPrompt(View view, String key) {

        if (view instanceof CharacterEditText) {
            if(((CharacterEditText) view).length()==0) {
                ((TextInputLayout) (view.getParent()).getParent()).setError(mandatoryPromptMapView.get(key));
                isMandatory=false;
            }else {
                ((TextInputLayout) (view.getParent()).getParent()).setError(null);
            }
        }else if(view instanceof CalendarEditText){
            if(((CalendarEditText) view).length()==0) {
                ((TextInputLayout) (view.getParent()).getParent()).setError(mandatoryPromptMapView.get(key));
                isMandatory=false;
            }else {
                ((TextInputLayout) (view.getParent()).getParent()).setError(null);
            }
        }else if(view instanceof NumberEditText){
            if(((NumberEditText) view).length()==0) {
                ((TextInputLayout) (view.getParent()).getParent()).setError(mandatoryPromptMapView.get(key));
                isMandatory=false;
            }else {
                ((TextInputLayout) (view.getParent()).getParent()).setError(null);
            }
        }else if(view instanceof PercentageEditText){
            if(((PercentageEditText) view).length()==0) {
                ((TextInputLayout) (view.getParent()).getParent()).setError(mandatoryPromptMapView.get(key));
                isMandatory=false;
            }else {
                ((TextInputLayout) (view.getParent()).getParent()).setError(null);
            }
        }

        else if(view instanceof CurrencyGhostView){
            if(((CurrencyGhostView) view).getText().length()==0) {
                ((CustomTextInputLayout) (((CurrencyGhostView) view).getEditText().getParent()).getParent()).setError(mandatoryPromptMapView.get(key));
                isMandatory=false;
            }else {
                ((CustomTextInputLayout) (((CurrencyGhostView) view).getEditText().getParent()).getParent()).setError(null);
            }
        }
        else if(view instanceof Spinner){

            switch(view.getId()){
                case R.id.liabi_categoriesone_spinner:
                    String goal_category=mliabiCategoriesOneSpinner.getSelectedItem().toString();
                    if(goal_category.equalsIgnoreCase("")) {
                        mliabiCategoriesOneSpinner.setSelection(0);
                        spinnerError(mliabiCategoriesOneSpinner);
                        isMandatory = false;
                    }
                    break;

            }
        }
    }
    void spinnerError(Spinner spinner) {
        TextView errorText = (TextView) spinner.getSelectedView();
        errorText.setError("");
        errorText.setTextColor(Color.RED);
        errorText.setText("");
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
            ((CurrencyGhostView)nameEditview).getEditText().setBackgroundResource(R.drawable.edittextbackgrounggreen);
        }
    }
    private void  setUserDateInput() {
        try{
        if (UtileKit.validateObjectValues(mUserLiabilityList.getEmpty_flds()))
            emptyErrorValidation(mUserLiabilityList.getEmpty_flds());

        //Muruga Validation start//
        if (UtileKit.validateObjectValues(mUserLiabilityList.getLiab_name())) {
            mLibNameEdit.setText(mUserLiabilityList.getLiab_name());
            mLibNameEdit.setSelection(mUserLiabilityList.getLiab_name().length());
        } else {
            //  mLibNameEdit.setHintTextEmptyError();
        }
        if (UtileKit.validateObjectValues(mUserLiabilityList.getLender())) {
            mLenderNameEdit.setText(mUserLiabilityList.getLender());
            mLenderNameEdit.setSelection(mUserLiabilityList.getLender().length());
        } else {
            //  mLenderNameEdit.setHintTextEmptyError();
        }
        if (UtileKit.validateObjectValues(mUserLiabilityList.getOutst_bal())) {
            mOutStandingBalaceEdt.setText(mUserLiabilityList.getOutst_bal());
            mOutStandingBalaceEdt.getEditText().setSelection(mUserLiabilityList.getOutst_bal().length());
        } else {
            // mOutStandingBalaceEdt.setHintTextEmptyError();
        }
        if (UtileKit.validateObjectValues(mUserLiabilityList.getCurrent_emi())) {
            mCurrentEmiEdt.setText(mUserLiabilityList.getCurrent_emi());
            mCurrentEmiEdt.getEditText().setSelection(mUserLiabilityList.getCurrent_emi().length());
        } else {
            // mCurrentEmiEdt.setHintTextEmptyError();
        }
        if (UtileKit.validateObjectValues(mUserLiabilityList.getInterest_rate())) {
            mIntrestRateEdt.setText(mUserLiabilityList.getInterest_rate());
            mIntrestRateEdt.setSelection(mUserLiabilityList.getInterest_rate().length());
        } else {
            //  mIntrestRateEdt.setHintTextEmptyError();
        }
        if (UtileKit.validateObjectValues(mUserLiabilityList.getTotal_tenure())) {
            if (!mUserLiabilityList.getTotal_tenure().equalsIgnoreCase("0"))
                mTotalTenureEdt.setText(mUserLiabilityList.getTotal_tenure());
        } else {
            // mTotalTenureEdt.setHintTextEmptyError();
        }
        if (UtileKit.validateObjectValues(mUserLiabilityList.getBalance_tenure())) {
            if (!mUserLiabilityList.getBalance_tenure().equalsIgnoreCase("0"))
                mBalaceTenureEdit.setText(mUserLiabilityList.getBalance_tenure());
        } else {
            // mBalaceTenureEdit.setHintTextEmptyError();
        }
        if (UtileKit.validateObjectValues(mUserLiabilityList.getLast_paid_date())) {
            if (!mUserLiabilityList.getLast_paid_date().equalsIgnoreCase("0000-00-00"))
                mLastPremiumDueDate.setText(setDateFormat(mUserLiabilityList.getLast_paid_date()));
        } else {
            mLastPremiumDueDate.setText("");
        }
        if (UtileKit.validateObjectValues(mUserLiabilityList.getNext_due_date())) {
            if (!mUserLiabilityList.getNext_due_date().equalsIgnoreCase("0000-00-00")) {
                mnextPremiumDuedate.setText(setDateFormat(mUserLiabilityList.getNext_due_date()));
            } else {
                mnextPremiumDuedate.setText("");
            }
        } else {
            // mStartYearEdit.setHintTextEmptyError();
        }
        if (UtileKit.validateObjectValues(mUserLiabilityList.getStart_year())) {
            if (!mUserLiabilityList.getStart_year().equalsIgnoreCase("0"))
                mStartYearEdit.setText(mUserLiabilityList.getStart_year());
        } else {
            // mStartYearEdit.setHintTextEmptyError();
        }
        if (UtileKit.validateObjectValues(mUserLiabilityList.getEnd_year())) {
            if (!mUserLiabilityList.getEnd_year().equalsIgnoreCase("0"))
                mEndYearEdit.setText(mUserLiabilityList.getEnd_year());
        } else {
            // mEndYearEdit.setHintTextEmptyError();
        }
        if (UtileKit.validateObjectValues(mUserLiabilityList.getWhose_name())) {
            mWholeNameEdit.setText(mUserLiabilityList.getWhose_name());
            mWholeNameEdit.setSelection(mUserLiabilityList.getWhose_name().length());
        } else {
            //  mWholeNameEdit.setHintTextEmptyError();
        }
        if (UtileKit.validateObjectValues(mUserLiabilityList.getLoan_amt())) {
            mLoanAmoutEdt.setText(mUserLiabilityList.getLoan_amt());
            mLoanAmoutEdt.getEditText().setSelection(mUserLiabilityList.getLoan_amt().length());
        } else {
            // mLoanAmoutEdt.setHintTextEmptyError();
        }
        if (mUserLiabilityList.getCat_lev1_id() != null) {
            IsOnFirstSpinLev2Load = true;
            IsOnFirstLev3Load = true;
            mliabiCategoriesOneSpinner.setSelection(getSpinnerposition(mUserLiabilityList.getCat_lev1_id(),
                    liabCategoriesLevelOneNameList));


        }
        if (mUserLiabilityList.getLinked_asset() != null) {
            mAssetCatLinkSpinner.setSelection(getSpinnerAssetposition(mUserLiabilityList.getLinked_asset(), assetListCataLink));
        }
        if (mUserLiabilityList.getLoan_freq() != null) {
            mlib_loan_due_date.setSelection(getSpinnerAssetposition(mUserLiabilityList.getLoan_freq(), monthInArray));
        }
//        if(mUserLiabilityList.getCat_lev2_id()!=null)
//        {
//
//            if (!liabCategoriesLevelTwoNameList.isEmpty()) {
//                liabCategoriesLevelTwoNameList.clear();
//                liabCategoriesLevelTwoListonSelect.clear();
//            }
//            for (int i = 0; i < liabCategoriesLevelTwoList.size(); i++) {
//                String categories_id = liabCategoriesLevelTwoList.get(i).getLev1_id();
//                if (categories_id.equalsIgnoreCase(mUserLiabilityList.getCat_lev1_id())) {
//                    liabCategoriesLevelTwoListonSelect.add(liabCategoriesLevelTwoList.get(i));
//                    liabCategoriesLevelTwoNameList.add(liabCategoriesLevelTwoList.get(i).getLev2_name());
//                }
//            }
//            if (UtileKit.validateObjectValues(liabCategoriesLevelTwoNameList)) {
//                if (!liabCategoriesLevelTwoNameList.isEmpty()) {
//                    liabiategoriesTwoLayout.setVisibility(View.VISIBLE);
////                    setSpinnerAdapter(mliabiCategoriesTwoSpinner, liabCategoriesLevelTwoNameList);
//                } else {
//                    liabiategoriesTwoLayout.setVisibility(View.GONE);
//                    liabiategoriesThreeLayout.setVisibility(View.GONE);
//                    // mGoalOtherCategoriesLayout.setVisibility(View.GONE);
//                }
//            }
//            mliabiCategoriesTwoSpinner.setSelection(getSpinnerLevel2position(mUserLiabilityList.getCat_lev2_id(),liabCategoriesLevelTwoNameList));
//        }
//        if(mUserLiabilityList.getCat_lev3_id()!=null)
//        {
//            IsOnFirstLev3Load=true;
//            if (liabCategoriesLevelThreeList != null) {
//                if (!liabCategoriesLevelThreenameList.isEmpty()) {
//                    liabCategoriesLevelThreeListonSelect.clear();
//                    liabCategoriesLevelThreenameList.clear();
//                }
//                for (int i = 0; i < liabCategoriesLevelThreeList.size(); i++) {
//                    //Log.e("level3id", "" + liabCategoriesLevelThreeList.get(i).getLev2_id());
//
//                    String level2_categories_id = liabCategoriesLevelThreeList.get(i).getLev2_id();
//                    if (level2_categories_id.equalsIgnoreCase(mUserLiabilityList.getCat_lev2_id())) {
//                        liabCategoriesLevelThreeListonSelect.add(liabCategoriesLevelThreeList.get(i));
//                        liabCategoriesLevelThreenameList.add(liabCategoriesLevelThreeList.get(i).getLev3_name());
//                        //Log.e("level3list", "" + liabCategoriesLevelThreeList.get(i).getLev3_name());
//
//                    }
//                }
//                if (UtileKit.validateObjectValues(liabCategoriesLevelThreenameList)) {
//                    if (!liabCategoriesLevelThreenameList.isEmpty()) {
//                        liabiategoriesThreeLayout.setVisibility(View.VISIBLE);
////                        setSpinnerAdapter(mliabiCategoriesThreeSpinner, liabCategoriesLevelThreenameList);
//                    } else {
//                        cat_lev3_id = "0";
//                        liabiategoriesThreeLayout.setVisibility(View.GONE);
//                        otherLinearLayout.setVisibility(View.GONE);
//                    }
//                }
//                mliabiCategoriesThreeSpinner.setSelection(getSpinnerLevel3position(mUserLiabilityList.getCat_lev3_id(), liabCategoriesLevelThreenameList));
//            }
//        }
        if (mUserLiabilityList.getInsured() != null) {
            if (mUserLiabilityList.getInsured().equalsIgnoreCase("Y"))
                mgenderMaleRadioBtn.setChecked(true);
            else if (mUserLiabilityList.getInsured().equalsIgnoreCase("N"))
                mgenderFemaleRadioBtn.setChecked(true);
        }

//        mOtherEdt.setText(mUserLiabilityList.get);
    }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setSpinnerAdapter(Spinner mMyMartialSpinner, ArrayList<String> myStringArray) {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("");
        for (String s : myStringArray) {
            stringList.add(s);
        }
        CustomSpinerAdapter adapter_state = new CustomSpinerAdapter(mContext, stringList);
        mMyMartialSpinner.setAdapter(adapter_state);
        mMyMartialSpinner.setOnItemSelectedListener(this);
    }
    public void editTextDrawableClick(final EditText EdtText,final String title) {

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
                        setEdtText(EdtText);
                        showMonthCalanderonClick(EdtText,title);
                        return true;
//                    }
                }
                return false;
            }
        });
    }

    private void showMonthCalanderonClick(EditText EdtText,String title) {

//        String date=EdtText.getText().toString();
//        AssetDatePickerDialogFragment assetDatePickerDialogFragment=AssetDatePickerDialogFragment.newInstance(this,
//                title,Boolean.FALSE,Boolean.TRUE,Boolean.FALSE,date);
//
//        if(UtileKit.validateObjectValues(date)){
//            args.putString(AssetDatePickerDialogFragment.YEARONLY,date);
//            assetDatePickerDialogFragment.setArguments(args);
//        }

//        assetDatePickerDialogFragment.show(getActivity().getFragmentManager(),"show");

//        if(!date.isEmpty() && date!= null) {
//            String dateofYears= UtileKit.validation_to_years(date);
//            if (dateofYears != null) {
//                CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
//                        title, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, "1", dateofYears);
//
//                mcalenderTabs.show(getFragmentManager(), title);
//            }
//        }else{
//            CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
//                    title, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, "1", date);
//
//            mcalenderTabs.show(getFragmentManager(), title);
//        }

        if(title.equalsIgnoreCase(startYearTitle) ) {
            String years = EdtText.getText().toString();

            String concactDDMMYY = null;
            if (years != null && !years.isEmpty()) {
                if (years.length() <= 3) {
                    String date = getcurrentYear(years);
                    concactDDMMYY = currentdateFirsttime + "-" + currentmonthFirsttime + "-" + date;
                } else if (years.length() > 3 && years.length() <= 10 || years.length() <= 8) {

                    concactDDMMYY = years;
                } else {
                    concactDDMMYY = currentdateFirsttime + "-" + currentmonthFirsttime + "-" + years;
                }
                if (concactDDMMYY != null) {
                    CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                            startYearTitle, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, "1", concactDDMMYY);

                    mcalenderTabs.show(getFragmentManager(), startYearTitle);
                }
            } else {
                CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                        title, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, "1", "");
                mcalenderTabs.show(getFragmentManager(), title);
            }
        }else if( title.equalsIgnoreCase(endYearTitle)){
            try {
                String years = EdtText.getText().toString();
                String concactDDMMYY = null;
                if (years != null && !years.isEmpty()) {
                    if (years.length() <= 3) {
                        String date = getcurrentYear(years);
                        concactDDMMYY = currentdateFirsttime + "-" + currentmonthFirsttime + "-" + date;
                    } else if (years.length() > 3 && years.length() <= 10 || years.length() <= 8) {

                        concactDDMMYY = years;
                    } else {
                        concactDDMMYY = currentdateFirsttime + "-" + currentmonthFirsttime + "-" + years;
                    }
                    if (concactDDMMYY != null) {
                        CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                                endYearTitle, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "1", concactDDMMYY);
                        mcalenderTabs.show(getFragmentManager(), endYearTitle);
                    }
                }else{
                    CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                            endYearTitle, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "1", "");
                    mcalenderTabs.show(getFragmentManager(), endYearTitle);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(title.equalsIgnoreCase(last_premium_due_date)) {
            String years = EdtText.getText().toString();
            String concactDDMMYY = null;
            if (years != null && !years.isEmpty()) {
                if (years.length() <= 3) {
                    String date = getcurrentYear(years);
                    concactDDMMYY = currentdateFirsttime + "-" + currentmonthFirsttime + "-" + date;
                } else if (years.length() > 3 && years.length() <= 10 || years.length() <= 8) {

                    concactDDMMYY = years;
                } else {
                    concactDDMMYY = currentdateFirsttime + "-" + currentmonthFirsttime + "-" + years;
                }
                if (concactDDMMYY != null) {
                    CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                            last_premium_due_date, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "1", concactDDMMYY);
                    mcalenderTabs.show(getFragmentManager(), last_premium_due_date);
                }
            }else {
                CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                        title, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "1", "");
                mcalenderTabs.show(getFragmentManager(), title);
            }
        } else if (title.equalsIgnoreCase(next_premium_due_date)) {
                String years = EdtText.getText().toString();
                String concactDDMMYY = null;
                if (years != null && !years.isEmpty()) {
                    if (years.length() <= 3) {
                        String date = getcurrentYear(years);
                        concactDDMMYY = currentdateFirsttime + "-" + currentmonthFirsttime + "-" + date;
                    } else if (years.length() > 3 && years.length() <= 10 || years.length() <= 8) {

                        concactDDMMYY = years;
                    } else {
                        concactDDMMYY = currentdateFirsttime + "-" + currentmonthFirsttime + "-" + years;
                    }
                    if (concactDDMMYY != null) {
                        CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                                next_premium_due_date, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "1", concactDDMMYY);
                        mcalenderTabs.show(getFragmentManager(), next_premium_due_date);
                    }
            }else {
                    CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                            title, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "1", "");
                    mcalenderTabs.show(getFragmentManager(), title);
                }
        }
//        else if (title.equalsIgnoreCase(loan_frequency_due_date)) {
//            String years = EdtText.getText().toString();
//            String concactDDMMYY = null;
//            if (years != null && !years.isEmpty()) {
//                if (years.length() <= 3) {
//                    String date = getcurrentYear(years);
//                    concactDDMMYY = currentdateFirsttime + "-" + currentmonthFirsttime + "-" + date;
//                } else if (years.length() > 3 && years.length() <= 10 || years.length() <= 8) {
//
//                    concactDDMMYY = years;
//                } else {
//                    concactDDMMYY = currentdateFirsttime + "-" + currentmonthFirsttime + "-" + years;
//                }
//                if (concactDDMMYY != null) {
//                    CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
//                            loan_frequency_due_date, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "1", concactDDMMYY);
//                    mcalenderTabs.show(getFragmentManager(), loan_frequency_due_date);
//                }
//            }
//        }
    }

    private String getcurrentYear(String getyear) {
        String years = "";
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            Date date = new Date();
            Log.i("", "yearGeneratorpastYear date" + dateFormat.format(date));
            String[] items1 = dateFormat.format(date).split("/");
            Log.i("", "yearGeneratorpastYear year" + items1[0]);
            int currentdate= Integer.parseInt(items1[2]);
            int currentmonth= Integer.parseInt(items1[1]);
            int currentyear= Integer.parseInt(items1[0]);
            int updateyears =1;
            if(Integer.parseInt(getyear) < currentyear){
                updateyears = currentyear - Integer.parseInt(getyear);

            }else{
                updateyears = Integer.parseInt(getyear) - currentyear;
            }

            Log.i("", "yearGeneratorpastYear updateyears" + updateyears);
            years = String.valueOf(updateyears);
            return years;
        }catch (Exception e){
            e.printStackTrace();
        }
        return years;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                //  getActivity().onBackPressed();
        }
        return true;
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


//    private void callFamilyDetailsService() {
//        UtileKit.showSpinnerDialog(activity, false);
//        WebServiceCalls webServiceObj;
//        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
//        Call<GoalFamilyDetailsModel> call = webServiceObj.callFamilyDetailsListService(LoginandSignUpActivity.UserId);
//        call.enqueue(new Callback<GoalFamilyDetailsModel>() {
//            @Override
//            public void onResponse(Call<GoalFamilyDetailsModel> call, Response<GoalFamilyDetailsModel> response) {
//                //Log.e("CallBack", " family is " + call.toString());
//                addFamilyDetailModel = response.body();
//                UtileKit.dismisssSpinnerDialog();
//                if (addFamilyDetailModel.getStatusCode().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
//                    familyDetails = addFamilyDetailModel.getData().getFamily_details();
//                    if (UtileKit.validateObjectValues(familyDetails)) {
//                        if(!familyDetails.isEmpty()) {
//                            for (int i = 0; i < familyDetails.size(); i++) {
//                                mgoalBelongsToArrayList.add(familyDetails.get(i).getName());
//                                mgoalBelongsToArrayListId.add(familyDetails.get(i).getId());
//                                //Log.e("CallBack", " family is " + familyDetails.get(i).getName());
//                            }
//                        }
//                        UtileKit.setArrayListSpinnerAdapter(mliabielongToSpinner, mgoalBelongsToArrayList, activity);
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<GoalFamilyDetailsModel> call, Throwable t) {
//                //Log.e("CallBack", " failure is " + t);
//                UtileKit.dismisssSpinnerDialog();
//            }
//        });
//        UtileKit.dismisssSpinnerDialog();
//    }


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

                if(emptyErrorPromptValidation(formArray)){
                    getInputData();
                    if(liab_name!=null && !liab_name.isEmpty() ) {
                        if (UtileKit.validateObjectValues(mLibNameEdit)) {
                     //       mSubmitbtn.setVisibility(View.GONE);
                            try {
                                addliabi();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        } else {
                            liabi_name_edt_inputlayout.setError(HomePageActivity.errorMessageInLiability);
                        }
                    }else{
                        liabi_name_edt_inputlayout.setError(HomePageActivity.errorMessageInLiability);
                        UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInLiability,activity);
                    }
                }

                firstTimeDoneInterface.firstTimeDone("Y");

            }
            break;




            case R.id.liabiButton:
                getInputData();
                if(liab_name!=null && !liab_name.isEmpty() ) {
                    if (UtileKit.validateObjectValues(mLibNameEdit)) {
                //        mSubmitbtn.setVisibility(View.GONE);
                        try {
                            addliabi();
                        }catch (Exception e){
                        e.printStackTrace();
                    }
                    } else {
                        liabi_name_edt_inputlayout.setError(HomePageActivity.errorMessageInLiability);
                    }

                }else{
                    liabi_name_edt_inputlayout.setError(HomePageActivity.errorMessageInLiability);
                    UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInLiability,activity);
                }

                break;
            case R.id.relative_left_arrow:
            {
                        try {
                            saveLiabilityData();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                dismiss();



            }
            break;
            case R.id.relative_center_home:
            {
                try {
                    saveLiabilityData();
                }catch (Exception e){
                    e.printStackTrace();
                }
                onCheckListIsEmptyOrNot.onHomeBackPresedLisaner();
                dismiss();
//				getActivity().finish();
            }
            break;
            case R.id.relative_right_arrow:
            {
                onCheckListIsEmptyOrNot.onNextBAckpressed();
                dismiss();

            }

        }

    }
private void saveLiabilityData(){
    getInputData();
    if(liab_name!=null && !liab_name.isEmpty() ) {
        if (UtileKit.validateObjectValues(mLibNameEdit)) {
     //       mSubmitbtn.setVisibility(View.GONE);
            try {
                addliabi();
            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
        }

    }else{
    }
    dismiss();
}

    public void addliabi() {
        UtileKit.showSpinnerDialog(activity, false);
        if(mUserLiabilityList==null) {
            WebServiceCalls webServiceObj;
            webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
            Call<AddLiabilityModel> call = webServiceObj.calladdliabilityService(UtileKit.getPersistedPurplePathPref("user_id"),
                    mType, liab_name, cat_lev1_id, cat_lev2_id, cat_lev3_id, lenderName, loan_amt, outst_bal,
                    current_emi, interest_rate, total_tenure, balance_tenure, start_year, end_year, whose_name,
                    insured,mAssetLink,lastpaiddate, nextduedate,loanfreq);
            call.enqueue(new Callback<AddLiabilityModel>() {
                @Override
                public void onResponse(Call<AddLiabilityModel> call, Response<AddLiabilityModel> response) {
                    UtileKit.dismisssSpinnerDialog();
                    //Log.e("CallBack", "sucess liabi is " + call.toString());
                     addLiabilityModel = response.body();

                    if (addLiabilityModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        dismiss();
                        onCheckListIsEmptyOrNot.checkListSize(1);
                    }else {
                  //      mSubmitbtn.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<AddLiabilityModel> call, Throwable t) {
                    //Log.e("CallBack", " failure liabi is " + t);
                    UtileKit.alertRetrofitExceptionDialog( mContext,t);
                    UtileKit.dismisssSpinnerDialog();
                //    mSubmitbtn.setVisibility(View.VISIBLE);
                }
            });
            UtileKit.dismisssSpinnerDialog();
        }
        else
        {
            WebServiceCalls webServiceObj;
            webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
            Call<AddLiabilityModel> call = webServiceObj.callUpdateliabilityService(UtileKit.getPersistedPurplePathPref("user_id"),mUserLiabilityList.getId(),
                    mType, liab_name, cat_lev1_id, cat_lev2_id, cat_lev3_id, lenderName, loan_amt, outst_bal, current_emi, interest_rate,
                    total_tenure, balance_tenure, start_year, end_year, whose_name, insured,mAssetLink,lastpaiddate, nextduedate,loanfreq);
            call.enqueue(new Callback<AddLiabilityModel>() {
                @Override
                public void onResponse(Call<AddLiabilityModel> call, Response<AddLiabilityModel> response) {
                    //Log.e("CallBack", "sucess liabi is " + call.toString());
                    AddLiabilityModel addLiabilityModel = response.body();
                    UtileKit.dismisssSpinnerDialog();
                    if (addLiabilityModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        dismiss();
                        onCheckListIsEmptyOrNot.checkListSize(1);
                    }else{
                //        mSubmitbtn.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<AddLiabilityModel> call, Throwable t) {
                    //Log.e("CallBack", " failure liabi is " + t);

                    UtileKit.dismisssSpinnerDialog();
                 //   mSubmitbtn.setVisibility(View.VISIBLE);
                    UtileKit.alertRetrofitExceptionDialog( mContext,t);
                }
            });
            UtileKit.dismisssSpinnerDialog();
        }
    }

    private void emptyErrorValidation(ArrayList<String> emptyArrayList) {
        for(String obj:emptyArrayList)
        {
            View view=errorMapView.get(obj);
            UtileKit.emptyErrorViewList(view);
        }
    }


    private void getInputData() {
        liab_name = mLibNameEdit.getText().toString().trim();
        lenderName = mLenderNameEdit.getText().toString().trim();
        loan_amt = UtileKit.getStringwithoutDefaultCurreny(mLoanAmoutEdt.getEditText());
        outst_bal = UtileKit.getStringwithoutDefaultCurreny(mOutStandingBalaceEdt.getEditText());
        current_emi = UtileKit.getStringwithoutDefaultCurreny(mCurrentEmiEdt.getEditText());
        interest_rate = mIntrestRateEdt.getText().toString().trim();
        total_tenure = mTotalTenureEdt.getText().toString().trim();
        balance_tenure = mBalaceTenureEdit.getText().toString().trim();
        start_year = setDateFormat(mStartYearEdit.getText().toString().trim());
        end_year = setDateFormat(mEndYearEdit.getText().toString().trim());
        nextduedate=setDateFormat(mnextPremiumDuedate.getText().toString().trim());
        lastpaiddate = setDateFormat(mLastPremiumDueDate.getText().toString().trim());
        whose_name = mWholeNameEdit.getText().toString().trim();

//        addliabi();

    }

    private String setDateFormat(String date)
    {
        String output="";
        try {

            //Log.e("Date",date);
            if(date.trim().length()!=0) {
                String dateAray[] = date.split("-");
                //Log.e("Date",dateAray.toString());
                output=dateAray[2].concat("-").concat(dateAray[1].concat("-").concat(dateAray[0]));
                //Log.e("output",output);
            }
        }catch (ArrayIndexOutOfBoundsException e)
        {
            return date;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return date;
        }
        return output;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (position != 0) {
            position = position - 1;
            switch (parent.getId()) {
                case R.id.liabi_categoriesone_spinner:
                    cat_lev1_id = liabCategoriesLevelOneList.get(position).getId();


//                type = liabCategoriesLevelOneList.get(position).getType();
                if (!liabCategoriesLevelOneList.get(position).getLev1_name().equalsIgnoreCase("Others(s)")) {
                    if (liabCategoriesLevelTwoList != null) {
                        if (!liabCategoriesLevelTwoNameList.isEmpty()) {
                            liabCategoriesLevelTwoNameList.clear();
                            liabCategoriesLevelTwoListonSelect.clear();
                        }
                        for (int i = 0; i < liabCategoriesLevelTwoList.size(); i++) {
                            String categories_id = liabCategoriesLevelTwoList.get(i).getLev1_id();
                            if (categories_id.equalsIgnoreCase(cat_lev1_id)) {
                                liabCategoriesLevelTwoListonSelect.add(liabCategoriesLevelTwoList.get(i));
                                liabCategoriesLevelTwoNameList.add(liabCategoriesLevelTwoList.get(i).getLev2_name());
                            }
                        }
                        if (UtileKit.validateObjectValues(liabCategoriesLevelTwoNameList)) {
                            if (!liabCategoriesLevelTwoNameList.isEmpty()) {
                                liabiategoriesTwoLayout.setVisibility(View.VISIBLE);
                                setSpinnerAdapter(mliabiCategoriesTwoSpinner, liabCategoriesLevelTwoNameList);
                                if(IsOnFirstSpinLev2Load)
                                {
                                    mliabiCategoriesTwoSpinner.setSelection(getSpinnerLevel2position(mUserLiabilityList.getCat_lev2_id(),liabCategoriesLevelTwoNameList));
                                    IsOnFirstSpinLev2Load=false;
                                }
                            } else {
                                liabiategoriesTwoLayout.setVisibility(View.GONE);
                                liabiategoriesThreeLayout.setVisibility(View.GONE);
                                // mGoalOtherCategoriesLayout.setVisibility(View.GONE);
                            }
                        }
                    }
                } else {
                    cat_lev2_id = "0";
                    liabiategoriesTwoLayout.setVisibility(View.GONE);
                    liabiategoriesThreeLayout.setVisibility(View.GONE);
                    otherLinearLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.liabi_categoriestwo_spinner:
                cat_lev2_id = liabCategoriesLevelTwoListonSelect.get(position).getId();



//                //Log.e("level3", "" + cat_lev2_id);
                if (liabCategoriesLevelThreeList != null) {
                    if (!liabCategoriesLevelThreenameList.isEmpty()) {
                        liabCategoriesLevelThreeListonSelect.clear();
                        liabCategoriesLevelThreenameList.clear();
                    }
                    for (int i = 0; i < liabCategoriesLevelThreeList.size(); i++) {
//                        //Log.e("level3id", "" + liabCategoriesLevelThreeList.get(i).getLev2_id());

                        String level2_categories_id = liabCategoriesLevelThreeList.get(i).getLev2_id();
                        if (level2_categories_id.equalsIgnoreCase(cat_lev2_id)) {
                            liabCategoriesLevelThreeListonSelect.add(liabCategoriesLevelThreeList.get(i));
                            liabCategoriesLevelThreenameList.add(liabCategoriesLevelThreeList.get(i).getLev3_name());
                            //Log.e("level3list", "" + liabCategoriesLevelThreeList.get(i).getLev3_name());

                        }
                    }

                    if (UtileKit.validateObjectValues(liabCategoriesLevelThreenameList)) {
                        if (!liabCategoriesLevelThreenameList.isEmpty()) {
                            liabiategoriesThreeLayout.setVisibility(View.VISIBLE);
                            setSpinnerAdapter(mliabiCategoriesThreeSpinner, liabCategoriesLevelThreenameList);
                            if(IsOnFirstLev3Load)
                            {
                                mliabiCategoriesThreeSpinner.setSelection(getSpinnerLevel3position(mUserLiabilityList.getCat_lev3_id(),liabCategoriesLevelThreenameList));
                                IsOnFirstLev3Load=false;
                            }

                        } else {
                            cat_lev3_id = "0";
                            liabiategoriesThreeLayout.setVisibility(View.GONE);
                            otherLinearLayout.setVisibility(View.GONE);
                        }
                    }
                }
                break;
            case R.id.liabi_categoriesthree_spinner:
                cat_lev3_id = liabCategoriesLevelThreeListonSelect.get(position).getId();


                break;



                case R.id.liabi_goal_cateLink_spinner:

                    if(position!=0){
                        mAssetLink = mAssetCatLinkSpinner.getSelectedItem().toString();
                        if(!isFirstTimeColor&&isClicked) {
                            mAssetCatLinkSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                            isClicked = false;
                        }
                    }
                    break;
                case R.id.liabi_loan_frequency_date_spinner:
                    loanfreq = mlib_loan_due_date.getSelectedItem().toString();
                    //Log.e("", "loanfreq" + mlib_loan_due_date.getSelectedItem().toString());
                    if(position!=0){

                        if(!isFirstTimeColor&&isClicked) {
                            mlib_loan_due_date.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                            isClicked = false;
                        }
                    }


                    break;



            default:
        }

     }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

        //  getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void setEdtText(EditText edttext) {
        this.mCalanderEdt = edttext;
    }

    private EditText getEdtText() {
        return mCalanderEdt;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

        int monthincr = month + 1;
        getEdtText().setText("" +  day + "-" + monthincr + "-" +year);

    }


    public void mysetChecked(RadioButton myYesRadioBtn, RadioButton myNoRadioBtn) {
        if (myYesRadioBtn.isChecked()) {
            UtileKit.getSwitchYesBtnView(myYesRadioBtn, myNoRadioBtn, mContext);
        } else {
            UtileKit.getSwitchNoBtnView(myYesRadioBtn, myNoRadioBtn, mContext);
        }
    }


    private int getSpinnerposition(String value, ArrayList<String> spinerlist){
        int pos=0;String name="";
        for(int j=0;j<liabCategoriesLevelOneList.size();j++)
            if(value.equalsIgnoreCase(liabCategoriesLevelOneList.get(j).getId()))
            {
                name=liabCategoriesLevelOneList.get(j).getLev1_name();
                for(int i=0; i<spinerlist.size(); i++){

                    if(name.equalsIgnoreCase(spinerlist.get(i))) {
                        pos=i+1;
                        break;
                    }
                }
                break;
            }

        return pos;
    }
    private int getSpinnerLevel2position(String value, ArrayList<String> spinerlist){
        int pos=0;String name="";
        for(int j=0;j<liabCategoriesLevelTwoList.size();j++) {
            if (value.equalsIgnoreCase(liabCategoriesLevelTwoList.get(j).getId())) {
                name = liabCategoriesLevelTwoList.get(j).getLev2_name();
                //Log.e("Sucess",""+name);
                for (int i = 0; i < spinerlist.size(); i++) {
                    if (name.equalsIgnoreCase(spinerlist.get(i))) {
                        pos = i+1 ;
                        break;
                    }

                }
                break;
            }
        }
        return pos;
    }
    private int getSpinnerAssetposition(String value, ArrayList<String> spinerlist){
        int pos=0;
        for(int j=0;j<spinerlist.size();j++) {
            if (value.equalsIgnoreCase(spinerlist.get(j))) {
                return j+1;
            }
        }
        return pos;
    }
    private int getSpinnerLevel3position(String value, ArrayList<String> spinerlist){
        int pos=0;
        String name="";
        for(int j=0;j<liabCategoriesLevelThreeList.size();j++)
        {
            if(value.equalsIgnoreCase(liabCategoriesLevelThreeList.get(j).getId())) {

                name = liabCategoriesLevelThreeList.get(j).getLev3_name();
                for (int i = 0; i < spinerlist.size(); i++) {
                    if (name.equalsIgnoreCase(spinerlist.get(i))) {
                        pos = i+1 ;
                        //Log.e("Lev#Sucess",""+name+"position"+pos);
                        return pos;
                    }

                }
                break;
            }
        }
        return pos;
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == mgenderMaleRadioBtn.getId()) {
            UtileKit.getSwitchYesBtnView(mgenderMaleRadioBtn, mgenderFemaleRadioBtn, mContext);
            insured="Y";

        } else if (checkedId == mgenderFemaleRadioBtn.getId()) {
            UtileKit.getSwitchNoBtnView(mgenderMaleRadioBtn, mgenderFemaleRadioBtn, mContext);
            insured="N";

        }
    }

    @Override
    public void updateEditTextValue(String value, String title) {

        if (title.equalsIgnoreCase(startYearTitle)) {
            if (value.length() <= 3) {
                String ageOfcalulation = UtileKit.displayYearCalulation(value);
                Log.i("Persional Organisation", "Current Residence  ageOfcalulation" + ageOfcalulation);
                mStartYearEdit.setText(ageOfcalulation);

            } else {
                mStartYearEdit.setText(value);

            }
//            mStartYearEdit.setText(value);
            UtileKit.edittextbordercolorchange(mStartYearEdit);
        } else if (title.equalsIgnoreCase(endYearTitle)) {

            if (value.length() <= 3) {
                String ageOfcalulation = UtileKit.displayYearCalulation(value);
                Log.i("Persional Organisation", "Current Residence  ageOfcalulation" + ageOfcalulation);
                mEndYearEdit.setText(ageOfcalulation);

            } else {

                mEndYearEdit.setText(value);

            }
//            mEndYearEdit.setText(value);
            UtileKit.edittextbordercolorchange(mEndYearEdit);
        } else if (title.equalsIgnoreCase(next_premium_due_date)) {

            if (value.length() <= 3) {
                String ageOfcalulation = UtileKit.displayYearCalulation(value);
                Log.i("Persional Organisation", "Current Residence  ageOfcalulation" + ageOfcalulation);
                mnextPremiumDuedate.setText(ageOfcalulation);

            } else {
                mnextPremiumDuedate.setText(value);
            }
//            mEndYearEdit.setText(value);
            UtileKit.edittextbordercolorchange(mnextPremiumDuedate);
        } else if (title.equalsIgnoreCase(last_premium_due_date)) {
            if (value.length() <= 3) {
                String ageOfcalulation = UtileKit.displayYearCalulation(value);
                Log.i("Persional Organisation", "Current Residence  ageOfcalulation" + ageOfcalulation);
                mLastPremiumDueDate.setText(ageOfcalulation);

            } else {

                mLastPremiumDueDate.setText(value);
            }
            UtileKit.edittextbordercolorchange(mLastPremiumDueDate);
        }

//    String getcurrentYear (String getyear){
//        String years = "";
//        try {
//            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
//            Date date = new Date();
//            Log.i("", "yearGeneratorpastYear date" + dateFormat.format(date));
//            String[] items1 = dateFormat.format(date).split("/");
//            Log.i("", "yearGeneratorpastYear year" + items1[0]);
//            int currentdate= Integer.parseInt(items1[2]);
//            int currentmonth= Integer.parseInt(items1[1]);
//            int currentyear= Integer.parseInt(items1[0]);
//            int updateyears =1;
//            if(Integer.parseInt(getyear) < currentyear){
//                updateyears = currentyear - Integer.parseInt(getyear);
//
//            }else{
//                updateyears = Integer.parseInt(getyear) - currentyear;
//            }
//
//            Log.i("", "yearGeneratorpastYear updateyears" + updateyears);
//            years = String.valueOf(updateyears);
//            return years;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return years;
//    }
    }

    @Override
    public void updateIndividualEditTextValue(String value, String title) {

    }

    private String setDateFormatToDDMMYY(String value) {


        Date date = null;
        String dateString2 = null;
        try {
            date = new SimpleDateFormat("dd-MM-yyyy").parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
         dateString2 = new SimpleDateFormat("dd-MM-yyyy").format(date);
        return  dateString2;
    }
}