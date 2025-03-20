package com.purplepath.purplepath.assets.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.calculator.CalculatorAct;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.finobot.finobot.activity.LoginandSignUpActivity;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.google.gson.Gson;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assets.FinancialAssetsListFragment;
import com.purplepath.purplepath.assets.PhysicalAssetsListFragment;
import com.purplepath.purplepath.assets.model.AddAssetModel;
import com.purplepath.purplepath.assets.model.AssetCategoriesLevelOne;
import com.purplepath.purplepath.assets.model.AssetCategoriesLevelThree;
import com.purplepath.purplepath.assets.model.AssetCategoriesLevelTwo;
import com.purplepath.purplepath.assets.model.AssetCategoriesModel;
import com.purplepath.purplepath.assets.model.GetAssetUserData;
import com.purplepath.purplepath.calenderNumberPicker.calenderUi.CalenderTabs;
import com.purplepath.purplepath.chatprompt.PromptChatFragment1;
import com.purplepath.purplepath.customview.CalendarEditText;
import com.purplepath.purplepath.customview.CharacterEditText;
import com.purplepath.purplepath.customview.CurrencyDefaultEdt;
import com.purplepath.purplepath.customview.CurrencyEditText;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.customview.CustomCalenderImageView;
import com.purplepath.purplepath.customview.CustomSpinerAdapter;
import com.purplepath.purplepath.customview.CustomTextInputLayout;
import com.purplepath.purplepath.customview.NumberEditText;
import com.purplepath.purplepath.fragments.EmailLoginFragment;
import com.purplepath.purplepath.goal.GetGoalsListModel;
import com.purplepath.purplepath.goal.GetGoalsUserData;
import com.purplepath.purplepath.goal.GoalFamilyDetails;
import com.purplepath.purplepath.goal.GoalFamilyDetailsModel;
import com.purplepath.purplepath.goal.InterfaceDone.FirstTimeDoneInterface;
import com.purplepath.purplepath.insurance.insuranceinterface.OnCheckListIsEmpty;
import com.purplepath.purplepath.liabilities.LiabilitiesTabViewFragment;
import com.purplepath.purplepath.myinterface.DatePickerCallBackInterface;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AssetDialogFragment extends DialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener,
        DatePickerDialog.OnDateSetListener, RadioGroup.OnCheckedChangeListener, DatePickerCallBackInterface, View.OnTouchListener {

    private String purchaseDateTitle = "Select Purchase Date", yearsOfContributionTitle = "Years of Contribution", yearsToMaturityTitle = "Years to Maturity";
    private RadioGroup mAllocateToGoal;
    private RadioButton mAllocateOn, mAllocateOff;
    private RadioGroup own_house_rg;
    private RadioButton own_house_on_RadioBtn, own_house_off_RadioBtn;
    private Spinner mAssetCategoriesOneSpinner,
            mAssetCategoriesTwoSpinner, mAssetCategoriesThreeSpinner, mAssetBelongToSpinner, mAllocateToGoalSpinner, mClassSpinner;
    private TextInputLayout asset_name_edt_textInputLayout;
    private Spinner mAssetObjectiveSpinner, mYeartoMutSpinner;
    private OnActivityBackPressedListener mCallBackListener;
    private CalendarEditText mAssetPurchaseDateEdt, mAssetYearsMaturityEdt;
    private NumberEditText mAssetContributionyearsEdt;
    private CharacterEditText mAssetNameEdt, mAssetNotesEdt, assetOtherEdt;
    private CurrencyDefaultEdt mAssetAllocateToGoalEdt;
    private CurrencyGhostView mAssetCurrentValueEdt, mAssetAnnualContributionEdt, mAssetFrequencyContributionEdt, mAssetPurchaseValueEdt;
    private Activity activity;
    private AssetCategoriesModel assetCategoriesModel;
    private AddAssetModel addAssetModel;
    private GetAssetUserData getAssetUserData;
    private ArrayList<AssetCategoriesLevelOne> assetCategoriesLevelOneList = new ArrayList<AssetCategoriesLevelOne>();
    private ArrayList<AssetCategoriesLevelOne> assetCategoriesLevelOneListData = new ArrayList<AssetCategoriesLevelOne>();
    private ArrayList<AssetCategoriesLevelTwo> assetCategoriesLevelTwoList = new ArrayList<AssetCategoriesLevelTwo>();
    private ArrayList<AssetCategoriesLevelThree> assetCategoriesLevelThreeList = new ArrayList<AssetCategoriesLevelThree>();
    private ArrayList<String> assetCategoriesLevelOneNameList = new ArrayList<String>();
    private ArrayList<String> assetCategoriesClassList = new ArrayList<String>();
    private ArrayList<String> assetCategoriesLevelTwoNameList = new ArrayList<String>();
    private ArrayList<String> assetCategoriesLevelThreenameList = new ArrayList<String>();
    private ArrayList<String> assetCategoriesLevelTwoIdsList = new ArrayList<String>();
    private ArrayList<String> assetCategoriesLevelThreeIdsList = new ArrayList<String>();
    private ArrayList<AssetCategoriesLevelTwo> assetCategorieLevelTwoListonSelect = new ArrayList<AssetCategoriesLevelTwo>();
    private ArrayList<AssetCategoriesLevelThree> assetCategorieLevelThreeListonSelect = new ArrayList<AssetCategoriesLevelThree>();
    private LinearLayout assetCategoriesTwoLayout, assetCategoriesThreeLayout, classOneLayout;

    String user_id, family_id, type, asset_name, asset_cat_lev1_id, asset_cat_lev2_id, asset_cat_lev3_id,
            other_cat, objective, current_value,
            annual_contr, freq_of_contr, years_of_contr, years_to_maturity, alloc_flag = "", alloc_to_goal, purchase_date, purchase_val, notes;

    private String is_own_house = "";
    private Button assetButton;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private GoalFamilyDetailsModel addFamilyDetailModel;
    private ArrayList<GoalFamilyDetails> familyDetails = new ArrayList<GoalFamilyDetails>();
    private ArrayList<String> mgoalBelongsToArrayList = new ArrayList<String>();
    private ArrayList<String> mgoalBelongsToArrayListId = new ArrayList<String>();
    private final String SUCCESSCODE = "200";
    private LinearLayout otherLinearLayout, masset_family_layout;
    private String assetID, UserID, assetType;
    private Context mContext;
    private ImageView backBtn;
    private TextView titleNameTxt;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private String checkAssetID;
    private OnCheckListIsEmpty onCheckListIsEmpty;
    ArrayList<String> objectiveArrayList;
    private int currentdateFirsttime, currentmonthFirsttime, currentyearFirsttime;
    private boolean isFirstTimeForSpinner = true, isFirstTimeForThirdSpinner = true;
    private GetGoalsListModel getGoalsListModel;
    ArrayList<GetGoalsUserData> getGoalsUserData = new ArrayList<GetGoalsUserData>();
    ArrayList<String> goalName = new ArrayList<String>(), goalId = new ArrayList<String>();
    String assetCat;
    Gson gson = new Gson();

    Bundle mArgs;
    public static final String TITLE = "";
    public static final String PARENT_CLASS_SOURCE = "com.gp89developers.example.MainActivity";
    View nameEditview;
    private CustomCalenderImageView current_calculaterImgView, annualcontribution_calculaterImgView,
            frequency_contribution_calculaterImgView, purchasevalue_calculaterImgView;

    private boolean isFirstTimeColor = false;
    private boolean isClicked;
    public HashMap<String, View> errorMapView = new HashMap<>();

    private ArrayList<String> monthInArray = new ArrayList(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
            "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
            "51", "52", "53", "54", "55", "56", "57", "58", "59", "60"));
    private RelativeLayout own_house_layout;


    ArrayList<String> formArray = new ArrayList<String>();
    private boolean isMandatory;
    Boolean isSignUp = false;
    public HashMap<String, View> mandatoryMapView = new HashMap<>();
    HashMap<String, String> mandatoryPromptMapView = new HashMap<String, String>() {{
        put("Name", "Enter your Goal name in detail");
        put("Objective", "Select the Goal category from the list");
        put("Categories", "Select Self / Family member to whom the Goal belogs to");
        put("Current Value", "Enter actual or approximate money you need to complete your goal");
        put("Annual Contribution", "Enter the number of years you have to reach the goal");
        put("Purchase Value", "Enter the number of years which the goal is active");
    }};
    public HashMap<String, View> mandatoryPromptPut = new HashMap<>();
    private LinearLayout bottom_bar_layout, bottom_bar_donelayout;
    private RelativeLayout relative_finish_later, relative_done_arrow;
    private LinearLayout name_layout, objective_layout, category_one_layout, asset_categoriestwo_layout,
            asset_categoriesthree_layout, current_value_layout,
            annual_contribution_layout, frequency_contribution_layout, no_of_year_contribution_layout,
            years_to_maturity_layout, allocate_to_goal_layout, purchase_date_layout, purchase_value_layout,
            note_layout;
    FloatingActionButton fab;

    FirstTimeDoneInterface firstTimeDoneInterface;

    public static AssetDialogFragment newInstance() {
        AssetDialogFragment assetDialogFragment = new AssetDialogFragment();
        return assetDialogFragment;
    }

    public static AssetDialogFragment newInstance(OnCheckListIsEmpty onCheckListIsEmpty,
                                                  FirstTimeDoneInterface firstTimeDoneInterface) {
        AssetDialogFragment fragment = new AssetDialogFragment();
        fragment.onCheckListIsEmpty = onCheckListIsEmpty;
        fragment.firstTimeDoneInterface = firstTimeDoneInterface;
        return fragment;
    }
//public static AssetDialogFragment newInstance(Boolean isSignUp, ArrayList<String> formArray) {
//    AssetDialogFragment fragment = new AssetDialogFragment();
//
//    Bundle args = new Bundle();
//
//    if (isSignUp != null) {
//        args.putSerializable("isSignUp", isSignUp);
//
//        Log.d("beforeisSignUp","beforeisSignUp"+isSignUp);
//    }
//    if(formArray!=null){
//        args.putSerializable("formArray",formArray);
//
//        Log.d("beforeisformArray","beforeisformArray"+formArray);
//    }
//    fragment.setArguments(args);
//
//
//    return fragment;
//}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.MY_DIALOG);

        activity = getActivity();

//        if (getArguments() != null) {
//            if (getArguments().containsKey("isSignUp"))
//                isSignUp = getArguments().getBoolean("isSignUp");
//            Log.d("afterisSignUp","afterisSignUp"+isSignUp);
//        }
//        if (getArguments() != null) {
//            if (getArguments().containsKey("formArray")) {
//                formArray = (ArrayList<String>) getArguments().getSerializable("formArray");
//
//                Log.d("afterformArray","afterformArray"+formArray);
//
//                emptyErrorValidationMandatory(formArray);
//
//            }
//        }

        calendar = new GregorianCalendar();
        mCallBackListener = (OnActivityBackPressedListener) (mContext);
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), isVibrate());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        try {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        View assetview = inflater.inflate(R.layout.dialog_fragment_asset, container, false);
        mContext = getContext();


        return assetview;
    }

    @Override
    public void onViewCreated(View assetview, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(assetview, savedInstanceState);

        mArgs = getArguments();
        assetID = mArgs.getString("assetid");
        checkAssetID = mArgs.getString("assetid");
        assetType = mArgs.getString("type");

        //isSignUp=mArgs.getBoolean("isSignUp");
        //formArray=mArgs.getStringArrayList("formArray");

        if (mArgs != null) {
            isSignUp = mArgs.getBoolean("isSignUp");

        }


        name_layout = assetview.findViewById(R.id.name_layout);
        mandatoryMapView.put("Name", name_layout);

        objective_layout = assetview.findViewById(R.id.objective_layout);
        mandatoryMapView.put("Objective", objective_layout);

        category_one_layout = assetview.findViewById(R.id.category_one_layout);
        mandatoryMapView.put("Categories", category_one_layout);


        assetCategoriesTwoLayout = assetview.findViewById(R.id.asset_categoriestwo_layout);
        mandatoryMapView.put("assetCategoriesTwoLayout", assetCategoriesTwoLayout);

        assetCategoriesThreeLayout = assetview.findViewById(R.id.asset_categoriesthree_layout);
        mandatoryMapView.put("assetCategoriesThreeLayout", assetCategoriesThreeLayout);

        masset_family_layout = assetview.findViewById(R.id.asset_family_layout);
        mandatoryMapView.put("Asset Belongs to", masset_family_layout);

        current_value_layout = assetview.findViewById(R.id.current_value_layout);
        mandatoryMapView.put("Current Value", current_value_layout);

        annual_contribution_layout = assetview.findViewById(R.id.annual_contribution_layout);
        mandatoryMapView.put("Annual Contribution", annual_contribution_layout);

        frequency_contribution_layout = assetview.findViewById(R.id.frequency_contribution_layout);
        mandatoryMapView.put("frequency_contribution_layout", frequency_contribution_layout);

        no_of_year_contribution_layout = assetview.findViewById(R.id.no_of_year_contribution_layout);
        mandatoryMapView.put("no_of_year_contribution_layout", no_of_year_contribution_layout);

        years_to_maturity_layout = assetview.findViewById(R.id.years_to_maturity_layout);
        mandatoryMapView.put("years_to_maturity_layout", years_to_maturity_layout);

        allocate_to_goal_layout = assetview.findViewById(R.id.allocate_to_goal_layout);
        mandatoryMapView.put("allocate_to_goal_layout", allocate_to_goal_layout);

        purchase_date_layout = assetview.findViewById(R.id.purchase_date_layout);
        mandatoryMapView.put("purchase_date_layout", purchase_date_layout);

        purchase_value_layout = assetview.findViewById(R.id.purchase_value_layout);
        mandatoryMapView.put("Purchase Value", purchase_value_layout);

        note_layout = assetview.findViewById(R.id.note_layout);
        mandatoryMapView.put("note_layout", note_layout);

        classOneLayout = assetview.findViewById(R.id.class_one_layout);

        bottom_bar_layout = assetview.findViewById(R.id.bottom_bar_layout);
        UtileKit.mandatoryFieldLinearLayout(isSignUp, bottom_bar_layout);

        bottom_bar_donelayout = assetview.findViewById(R.id.bottom_bar_donelayout);
        UtileKit.mandatoryFieldDoneLayout(isSignUp, bottom_bar_donelayout);


        backBtn = assetview.findViewById(R.id.backButtonId);
        titleNameTxt = assetview.findViewById(R.id.dialogTitleId);
        fab = assetview.findViewById(R.id.asset_tick_button);
        // masset_family_layout = assetview.findViewById(R.id.asset_family_layout);
//        assetCategoriesTwoLayout = assetview.findViewById(R.id.asset_categoriestwo_layout);
//        assetCategoriesThreeLayout = assetview.findViewById(R.id.asset_categoriesthree_layout);
        assetOtherEdt = assetview.findViewById(R.id.asset_other_edt);
        asset_name_edt_textInputLayout = assetview.findViewById(R.id.asset_name_edt_textInputLayout);
        assetButton = assetview.findViewById(R.id.assetButton);
        mAssetObjectiveSpinner = assetview.findViewById(R.id.asset_objective_spinner);
        mandatoryPromptPut.put("Objective", mAssetObjectiveSpinner);
        mAssetCategoriesOneSpinner = assetview.findViewById(R.id.asset_categoriesone_spinner);
        mClassSpinner = assetview.findViewById(R.id.asset_class_spinner);
        mandatoryPromptPut.put("Categories", mAssetCategoriesOneSpinner);
        mandatoryPromptPut.put("Class", mClassSpinner);
        mAssetCategoriesTwoSpinner = assetview.findViewById(R.id.asset_categoriestwo_spinner);
        own_house_layout = assetview.findViewById(R.id.own_house_layout);
        mYeartoMutSpinner = assetview.findViewById(R.id.liabi_loan_frequency_date_spinner);
        otherLinearLayout = assetview.findViewById(R.id.asset_others_layout);
        mAssetCategoriesThreeSpinner = assetview.findViewById(R.id.asset_categoriesthree_spinner);
        mAssetBelongToSpinner = assetview.findViewById(R.id.asset_belongsto_spinner);
        mAllocateToGoalSpinner = assetview.findViewById(R.id.allocateToGoal__spinner);
        mAllocateToGoal = assetview.findViewById(R.id.allocateToGoal_rg);
        mAllocateOn = assetview.findViewById(R.id.alocate_on_RadioBtn);
        mAllocateOff = assetview.findViewById(R.id.alocate_off_RadioBtn);
        own_house_rg = assetview.findViewById(R.id.own_house_rg);
        own_house_on_RadioBtn = assetview.findViewById(R.id.own_house_on_RadioBtn);
        own_house_off_RadioBtn = assetview.findViewById(R.id.own_house_off_RadioBtn);
        mleftRelativeLayout = assetview.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = assetview.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = assetview.findViewById(R.id.relative_right_arrow);
        mAssetNameEdt = assetview.findViewById(R.id.asset_name_edt);
        mandatoryPromptPut.put("Name", mAssetNameEdt);
        mAssetCurrentValueEdt = assetview.findViewById(R.id.asset_currentvalue_edt);
        mandatoryPromptPut.put("Current Value", mAssetCurrentValueEdt);
        mAssetAnnualContributionEdt = assetview.findViewById(R.id.asset_annualcontribution_edt);
        mandatoryPromptPut.put("Annual Contribution", mAssetAnnualContributionEdt);
        mAssetContributionyearsEdt = assetview.findViewById(R.id.asset_contributionyears_edt);
        mAssetFrequencyContributionEdt = assetview.findViewById(R.id.asset_frequencycontribution_edt);
        mAssetYearsMaturityEdt = assetview.findViewById(R.id.asset_yearstomaturity_edt);
        mAssetPurchaseDateEdt = assetview.findViewById(R.id.asset_purchasedate_edt);
        current_calculaterImgView = assetview.findViewById(R.id.current_calculaterImgView);
        annualcontribution_calculaterImgView = assetview.findViewById(R.id.annualcontribution_calculaterImgView);
        frequency_contribution_calculaterImgView = assetview.findViewById(R.id.frequency_contribution_calculaterImgView);
        purchasevalue_calculaterImgView = assetview.findViewById(R.id.purchasevalue_calculaterImgView);
        mAssetPurchaseValueEdt = assetview.findViewById(R.id.asset_purchasevalue_edt);
        mandatoryPromptPut.put("Purchase Value", mAssetPurchaseValueEdt);
        mAssetNotesEdt = assetview.findViewById(R.id.asset_notes_edt);

        objectiveArrayList = new ArrayList<String>();
        objectiveArrayList.add("Investments");
        objectiveArrayList.add("Cash");
        objectiveArrayList.add("Savings");
        objectiveArrayList.add("Lifestyle");


        errorMapView.put("asset_name", mAssetNameEdt);


        errorMapView.put("current_value", mAssetCurrentValueEdt);
        mAssetCurrentValueEdt.setTextHint("Current Value*");


        errorMapView.put("annual_contr", mAssetAnnualContributionEdt);
        mAssetAnnualContributionEdt.setTextHint("Annual Contribution*");


        errorMapView.put("years_of_contr", mAssetContributionyearsEdt);


        errorMapView.put("freq_of_contr", mAssetFrequencyContributionEdt);
        mAssetFrequencyContributionEdt.setTextHint("Frequency Contribution");


        errorMapView.put("years_to_maturity", mAssetYearsMaturityEdt);


        errorMapView.put("purchase_date", mAssetPurchaseDateEdt);

        editTextDrawableClick(mAssetPurchaseDateEdt, purchaseDateTitle);
        editTextDrawableClick(mAssetYearsMaturityEdt, yearsToMaturityTitle);


        current_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(mAssetCurrentValueEdt);
            }
        });
        annualcontribution_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(mAssetAnnualContributionEdt);
            }
        });
        frequency_contribution_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(mAssetFrequencyContributionEdt);
            }
        });
        purchasevalue_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(mAssetPurchaseValueEdt);
            }
        });


        errorMapView.put("purchase_val", mAssetPurchaseValueEdt);
        mAssetPurchaseValueEdt.setTextHint("Purchase Value*");


        errorMapView.put("notes", mAssetNotesEdt);
        setSpinnerAdapter(mAssetObjectiveSpinner, objectiveArrayList, activity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // try{
                getInputData();
                if (asset_name != null && asset_name != "") {
                    if (UtileKit.validateObjectValues(assetID)) {
                        updateAsset();
                        dismiss();
                    } else {
                        if (UtileKit.validateObjectValues(asset_name)) {
                            addAsset();
                            dismiss();
                        } else {
                            asset_name_edt_textInputLayout.setError(HomePageActivity.errorMessageInAssets);
                            UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInAssets, mContext);
                        }
                    }
                }
            }
        });
        assetButton.setOnClickListener(this);

        errorMapView.put("objective", mAssetObjectiveSpinner);

        mAssetObjectiveSpinner.setOnItemSelectedListener(this);
        mAssetObjectiveSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked = true;
                return false;
            }
        });

        errorMapView.put("cat_lev1_id", mAssetCategoriesOneSpinner);

        mAssetCategoriesOneSpinner.setOnItemSelectedListener(this);
        mAssetCategoriesOneSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked = true;
                return false;
            }
        });

        errorMapView.put("cat_cls_id", mClassSpinner);

        mClassSpinner.setOnItemSelectedListener(this);
        mClassSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked = true;
                return false;
            }
        });

        errorMapView.put("cat_lev2_id", mAssetCategoriesTwoSpinner);

        mAssetCategoriesTwoSpinner.setOnItemSelectedListener(this);
        mAssetCategoriesTwoSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked = true;
                return false;
            }
        });

        own_house_layout.setOnClickListener(this);

        mYeartoMutSpinner.setOnItemSelectedListener(this);
        setSpinnerAdapter(mYeartoMutSpinner, monthInArray, mContext);
        otherLinearLayout.setVisibility(View.GONE);

        errorMapView.put("cat_lev3_id", mAssetCategoriesThreeSpinner);

        mAssetCategoriesThreeSpinner.setOnItemSelectedListener(this);
        mAssetCategoriesThreeSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked = true;
                return false;
            }
        });

        errorMapView.put("family_id", mAssetBelongToSpinner);

        mAssetBelongToSpinner.setOnItemSelectedListener(this);
        mAssetBelongToSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked = true;
                return false;
            }
        });

        mAllocateToGoalSpinner.setOnItemSelectedListener(this);
        mAllocateToGoalSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked = true;
                return false;
            }
        });


        mAllocateToGoal.setOnCheckedChangeListener(this);

        own_house_rg.setOnCheckedChangeListener(this);

        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);

        if (UtileKit.validateObjectValues(assetID)) {
            UserID = mArgs.getString("userid");
            getAssetUserData = (GetAssetUserData) mArgs.getSerializable("assetobject");
            setGoalDetailsForUpdate();
        }


        callAssetCategoriesService();
        callFamilyDetailsService();
        callGetGoalListService();
        titleNameTxt.setText("" + assetType);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        mAssetNameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                asset_name_edt_textInputLayout.setErrorEnabled(false);
                asset_name_edt_textInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        if (isSignUp == false) {

            if (assetType.equalsIgnoreCase("Physical")) {
                /*physical asset hint*/
                mAssetNameEdt.setHintText(getString(R.string.hint_phsical_asset_name), ((TextInputLayout) (mAssetNameEdt.getParent()).getParent()));
                mAssetCurrentValueEdt.setfullHintTxt(getString(R.string.hint_phsical_asset_currentvalue));
                mAssetAnnualContributionEdt.setfullHintTxt(getString(R.string.hint_phsical_asset_annual_contribution));
                mAssetFrequencyContributionEdt.setfullHintTxt(getString(R.string.hint_phsical_asset_frequency_contribution));
                mAssetContributionyearsEdt.setHintText(getString(R.string.hint_phsical_asset_no_of_year_contri), ((TextInputLayout) (mAssetContributionyearsEdt.getParent()).getParent()));
                mAssetPurchaseValueEdt.setfullHintTxt(getString(R.string.hint_phsical_asset_purchase_value));
                mAssetNotesEdt.setHintText(getString(R.string.hint_phsical_asset_notes), ((TextInputLayout) (mAssetNotesEdt.getParent()).getParent()));
            } else {
                /*financial asset*/
                mAssetNameEdt.setHintText(getString(R.string.hint_financial_asset_name), ((TextInputLayout) (mAssetNameEdt.getParent()).getParent()));
                mAssetCurrentValueEdt.setfullHintTxt(getString(R.string.hint_financial_asset_currentvalue));
                mAssetAnnualContributionEdt.setfullHintTxt(getString(R.string.hint_financial_asset_annual_contribution));
                mAssetFrequencyContributionEdt.setfullHintTxt(getString(R.string.hint_financial_asset_frequency_contribution));
                mAssetContributionyearsEdt.setHintText(getString(R.string.hint_financial_asset_no_of_year_contri), ((TextInputLayout) (mAssetContributionyearsEdt.getParent()).getParent()));
                mAssetPurchaseValueEdt.setfullHintTxt(getString(R.string.hint_financial_asset_purchase_value));
                mAssetNotesEdt.setHintText(getString(R.string.hint_financial_asset_notes), ((TextInputLayout) (mAssetNotesEdt.getParent()).getParent()));
            }

        }


        relative_finish_later = assetview.findViewById(R.id.relative_finish_later);
        relative_done_arrow = assetview.findViewById(R.id.relative_done_arrow);
        relative_finish_later.setOnClickListener(this);
        relative_done_arrow.setOnClickListener(this);


//signup propmt
        if (isSignUp == true) {
            fab.setVisibility(View.GONE);
            for (String key : mandatoryMapView.keySet()) {
                mandatoryMapView.get(key).setVisibility(View.GONE);
            }
        }

        if (mArgs != null) {

            formArray = (ArrayList<String>) mArgs.getSerializable("formArray");
            emptyErrorValidationMandatory(formArray);
        }


    }


    private void emptyErrorValidationMandatory(ArrayList<String> emptyArrayList) {
        if (isSignUp == true) {
            for (String obj : emptyArrayList) {
                View view = mandatoryMapView.get(obj);
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    private Boolean emptyErrorPromptValidation(ArrayList<String> emptyArrayList) {
        if (isSignUp == true) {
            isMandatory = true;
            for (String key : emptyArrayList) {
                View view = mandatoryPromptPut.get(key);
                emptyErrorViewPrompt(view, key);
            }
        }
        return isMandatory;
    }

    void emptyErrorViewPrompt(View view, String key) {

        if (view instanceof CharacterEditText) {
            if (((CharacterEditText) view).length() == 0) {
                ((TextInputLayout) (view.getParent()).getParent()).setError(mandatoryPromptMapView.get(key));
                isMandatory = false;
            } else {
                ((TextInputLayout) (view.getParent()).getParent()).setError(null);
            }
        } else if (view instanceof CalendarEditText) {
            if (((CalendarEditText) view).length() == 0) {
                ((TextInputLayout) (view.getParent()).getParent()).setError(mandatoryPromptMapView.get(key));
                isMandatory = false;
            } else {
                ((TextInputLayout) (view.getParent()).getParent()).setError(null);
            }
        } else if (view instanceof NumberEditText) {
            if (((NumberEditText) view).length() == 0) {
                ((CustomTextInputLayout) (view.getParent()).getParent()).setError(mandatoryPromptMapView.get(key));
                isMandatory = false;
            } else {
                ((CustomTextInputLayout) (view.getParent()).getParent()).setError(null);
            }
        } else if (view instanceof CurrencyGhostView) {
            if (((CurrencyGhostView) view).getText().length() == 0) {
                ((CustomTextInputLayout) (((CurrencyGhostView) view).getEditText().getParent()).getParent()).setError(mandatoryPromptMapView.get(key));
                isMandatory = false;
            } else {
                ((CustomTextInputLayout) (((CurrencyGhostView) view).getEditText().getParent()).getParent()).setError(null);
            }
        } else if (view instanceof Spinner) {

            switch (view.getId()) {
                case R.id.asset_objective_spinner:
                    String goal_category = mAssetObjectiveSpinner.getSelectedItem().toString();
                    if (goal_category.equalsIgnoreCase("")) {
                        mAssetObjectiveSpinner.setSelection(0);
                        spinnerError(mAssetObjectiveSpinner);
                        isMandatory = false;
                    }
                    break;
                case R.id.asset_categoriesone_spinner:
                    String goal_detail_belongsto = mAssetCategoriesOneSpinner.getSelectedItem().toString();
                    if (goal_detail_belongsto.equalsIgnoreCase("")) {
                        mAssetCategoriesOneSpinner.setSelection(0);
                        spinnerError(mAssetCategoriesOneSpinner);
                        isMandatory = false;
                    }

                case R.id.asset_class_spinner:
                    String goal_detail_belongstos = mClassSpinner.getSelectedItem().toString();
                    if (goal_detail_belongstos.equalsIgnoreCase("")) {
                        mClassSpinner.setSelection(0);
                        spinnerError(mClassSpinner);
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
            ((CurrencyGhostView) nameEditview).getEditText().setBackgroundResource(R.drawable.edittextbackgrounggreen);
        }
    }


    public void callGetGoalListService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetGoalsListModel> call = webServiceObj.callGetGoalsListService(/*EmailLoginFragment.oAuth_key, */UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetGoalsListModel>() {
            @Override
            public void onResponse(Call<GetGoalsListModel> call, Response<GetGoalsListModel> response) {
                UtileKit.dismisssSpinnerDialog();
                getGoalsListModel = response.body();
                Log.d("Viswaaaa_errorrr", response.code()+"");
                if (getGoalsListModel.getStatus_code().equalsIgnoreCase("401")) {
                    Log.d("Error_responseeee", "error");
                }
                if (getGoalsListModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    getGoalsUserData = getGoalsListModel.getData().getUser_goals();
                    if (UtileKit.validateObjectValues(getGoalsUserData)) {
                        for (int i = 0; i < getGoalsUserData.size(); i++) {
                            goalName.add(getGoalsUserData.get(i).getGoal_name());
                            goalId.add(getGoalsUserData.get(i).getId());
                        }

                        setSpinnerAdapter(mAllocateToGoalSpinner, goalName, activity);
                        if (UtileKit.validateObjectValues(assetID)) {
                            if (UtileKit.validateObjectValues(getAssetUserData.getAlloc_to_goal()) &&
                                    (getAssetUserData.getAlloc_flag().equals("Y"))) {
                                UtileKit.getSwitchYesBtnView(mAllocateOn, mAllocateOff, mContext);
                                mAllocateToGoalSpinner.setVisibility(View.VISIBLE);
                                alloc_flag = "Y";
                                if (!(getAssetUserData.getAlloc_to_goal().equals("0"))) {
                                    mAllocateToGoalSpinner.setSelection(getSpinnerposition(getAssetUserData.getAlloc_to_goal(), goalId) + 1);
                                }
                            } else if (UtileKit.validateObjectValues(getAssetUserData.getAlloc_to_goal()) &&
                                    (getAssetUserData.getAlloc_flag().equals("N"))) {
                                alloc_flag = "N";
                                UtileKit.getSwitchNoBtnView(mAllocateOn, mAllocateOff, mContext);
                            }
                        }
                    } else {

                    }
                }

            }

            @Override
            public void onFailure(Call<GetGoalsListModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                Log.d("Error", t.getMessage());
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


    public void editTextDrawableClick(final EditText EdtText, final String title) {

        EdtText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;
                    if (event.getAction() == MotionEvent.ACTION_UP) {

//                        if (event.getRawX() >= (EdtText.getRight() - EdtText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        showMonthCalanderonClick(EdtText, title);
                        return true;
//                        }
                    }

                } catch (NullPointerException e) {
                } catch (Exception e) {
                }
                return false;
            }
        });
    }

    private void showMonthCalanderonClick(EditText editText, String title) {
        String dateToPass;

       /* datePickerDialog.setVibrate(isVibrate());
        datePickerDialog.setYearRange(1902, 2100);
        datePickerDialog.setCloseOnSingleTapDay(isCloseOnSingleTapDay());
        datePickerDialog.show(((FragmentActivity) activity).getSupportFragmentManager(), AppConstants.DATEPICKER_TAG);*/
        if (title.equalsIgnoreCase(purchaseDateTitle)) {
            try {
                dateToPass = editText.getText().toString();


                CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                        purchaseDateTitle, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, "1", dateToPass);

                mcalenderTabs.show(getFragmentManager(), purchaseDateTitle);
//        AssetDatePickerDialogFragment assetDatePickerDialogFragment= AssetDatePickerDialogFragment.newInstance(this,
//                purchaseDateTitle,Boolean.TRUE,Boolean.FALSE,Boolean.FALSE,dateToPass);
//        //    if (UtileKit.validateObjectValues(assetID)) {
//
//                    Bundle args=new Bundle();
//                    args.putString(AssetDatePickerDialogFragment.FULLDATE,dateToPass);
//                    assetDatePickerDialogFragment.setArguments(args);


            } catch (Exception e) {
                e.printStackTrace();
            }
            //  }
//        assetDatePickerDialogFragment.show(getActivity().getFragmentManager(),"date");
        } else if (title.equalsIgnoreCase(yearsOfContributionTitle)) {
            try {

                /*
                 * ONly Years to be displays
                 *
                 *
                 * */

//                dateToPass=editText.getText().toString();
//
//                CalenderTabs mcalenderTabs =  CalenderTabs.newInstance(this,
//                        yearsOfContributionTitle,Boolean.FALSE,Boolean.TRUE,Boolean.FALSE,"1", dateToPass);
//
//                mcalenderTabs.show(getFragmentManager(),yearsOfContributionTitle);


                /*
                 * It display all the date month and years
                 *
                 *
                 *
                 * */


                String years = editText.getText().toString();

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
                                yearsOfContributionTitle, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, "1", concactDDMMYY);
//                if(UtileKit.validateObjectValues(date)) {
//                    args.putString(AssetDatePickerDialogFragment.FULLDATE, date);
//                    mcalenderTabs.setArguments(args);
//                }
                        mcalenderTabs.show(getFragmentManager(), yearsOfContributionTitle);
                    }
                } else {
                    CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                            yearsOfContributionTitle, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, "1", "");
                    mcalenderTabs.show(getFragmentManager(), yearsOfContributionTitle);
                }


//            AssetDatePickerDialogFragment assetDatePickerDialogFragment= AssetDatePickerDialogFragment.newInstance(this
//                    ,yearsOfContributionTitle,Boolean.FALSE,Boolean.TRUE,Boolean.FALSE,dateToPass);
//
//                Bundle args=new Bundle();
//                args.putString(AssetDatePickerDialogFragment.ONLYNUMBER,dateToPass);
//                assetDatePickerDialogFragment.setArguments(args);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            assetDatePickerDialogFragment.show(getActivity().getFragmentManager(),"date");
        } else if (title.equalsIgnoreCase(yearsToMaturityTitle)) {
            try {

                /*
                 * It display all the date month and years
                 *
                 *
                 *
                 * */

                String years = editText.getText().toString();

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
                                yearsToMaturityTitle, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "1", concactDDMMYY);
//                if(UtileKit.validateObjectValues(date)) {
//                    args.putString(AssetDatePickerDialogFragment.FULLDATE, date);
//                    mcalenderTabs.setArguments(args);
//                }
                        mcalenderTabs.show(getFragmentManager(), yearsToMaturityTitle);
                    }
                } else {
                    CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                            yearsToMaturityTitle, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "1", "");
                    mcalenderTabs.show(getFragmentManager(), yearsToMaturityTitle);
                }

                /*
                 * ONly Years to be displays
                 *
                 *
                 * */

//                dateToPass=editText.getText().toString();
//
//                CalenderTabs mcalenderTabs =  CalenderTabs.newInstance(this,
//                        yearsToMaturityTitle,Boolean.FALSE,Boolean.TRUE,Boolean.FALSE,"1", dateToPass);
//
//                mcalenderTabs.show(getFragmentManager(),purchaseDateTitle);


//            AssetDatePickerDialogFragment assetDatePickerDialogFragment= AssetDatePickerDialogFragment.newInstance(this,
//                    yearsToMaturityTitle,Boolean.FALSE,Boolean.TRUE,Boolean.FALSE,dateToPass);
//
//                Bundle args=new Bundle();
//                args.putString(AssetDatePickerDialogFragment.ONLYNUMBER,dateToPass);
//                assetDatePickerDialogFragment.setArguments(args);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            assetDatePickerDialogFragment.show(getActivity().getFragmentManager(),"date");
        }


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

    private void callAssetCategoriesService() {
        try {
            final ArrayList<String> assetCategoriesLevelOneIds = new ArrayList<String>();
//        final ArrayList<String> assetCategoriesLevelOneIds = new ArrayList<String>();
//        UtileKit.showSpinnerDialog(activity, false);
//        WebServiceCalls webServiceObj;
//        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
//        Call<AssetCategoriesModel> call = webServiceObj.callAssetsCategoriesService();
//        call.enqueue(new Callback<AssetCategoriesModel>() {
//            @Override
//            public void onResponse(Call<AssetCategoriesModel> call, Response<AssetCategoriesModel> response) {
//                //Log.e("CallBack", " assets is " + call.toString());
//                assetCategoriesModel = response.body();
//                UtileKit.dismisssSpinnerDialog();

            assetCategoriesModel = gson.fromJson(UtileKit.getPersistedPurplePathPref("AssetCat"), AssetCategoriesModel.class);
            if (assetCategoriesModel != null) {
                if (assetCategoriesModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    assetCategoriesLevelOneListData = assetCategoriesModel.getData().getAsset_cat_lev1();
                    if (assetType.equalsIgnoreCase("Financial")) {
                        for (int i = 0; i < assetCategoriesLevelOneListData.size(); i++) {
                            if (assetCategoriesLevelOneListData.get(i).getType().equalsIgnoreCase("Financial")) {
                                List<AssetCategoriesLevelOne> orderNumbers = assetCategoriesLevelOneList;
                                LinkedHashSet<AssetCategoriesLevelOne> listToSet = new LinkedHashSet<AssetCategoriesLevelOne>(orderNumbers);
                                System.out.println("ArrayList with duplicates: " + listToSet.size());
                                assetCategoriesLevelOneList.add(assetCategoriesLevelOneListData.get(i));
                                // System.out.println("ArrayList with duplicates: " + assetCategoriesLevelOneList.size());

                            }
                        }
                    } else {
                        for (int i = 0; i < assetCategoriesLevelOneListData.size(); i++) {
                            if (assetCategoriesLevelOneListData.get(i).getType().equalsIgnoreCase("Physical")) {
                                List<AssetCategoriesLevelOne> orderNumbers = assetCategoriesLevelOneList;
                                LinkedHashSet<AssetCategoriesLevelOne> listToSet = new LinkedHashSet<AssetCategoriesLevelOne>(orderNumbers);
                                System.out.println("ArrayList with duplicates: " + listToSet.size());
                                assetCategoriesLevelOneList.add(assetCategoriesLevelOneListData.get(i));
                                System.out.println("ArrayList with duplicates1: " + assetCategoriesLevelOneList.size());
                            }
                        }

                    }
                    assetCategoriesLevelTwoList = assetCategoriesModel.getData().getAsset_cat_lev2();
                    assetCategoriesLevelThreeList = assetCategoriesModel.getData().getAsset_cat_lev3();
                    if (assetCategoriesLevelOneList != null) {
                        for (int i = 0; i < assetCategoriesLevelOneList.size(); i++) {
                            if (!assetCategoriesLevelOneNameList.contains(assetCategoriesLevelOneList.get(i).getLev1_name()))
                                assetCategoriesLevelOneNameList.add(assetCategoriesLevelOneList.get(i).getLev1_name());
                            assetCategoriesClassList.add(assetCategoriesLevelOneList.get(i).getAsset_class());
                            assetCategoriesLevelOneIds.add(assetCategoriesLevelOneList.get(i).getId());

                        }
                        //    UtileKit.setArrayListSpinnerAdapter(mAssetCategoriesOneSpinner, assetCategoriesLevelOneNameList, activity);
                        setSpinnerAdapter(mAssetCategoriesOneSpinner, assetCategoriesLevelOneNameList, activity);
                        setSpinnerAdapter(mClassSpinner, assetCategoriesClassList, activity);
                        if (UtileKit.validateObjectValues(checkAssetID)) {
                            if (UtileKit.validateObjectValues(getAssetUserData.getCat_lev1_id())) {
                                int cat1_pos = Integer.valueOf(getAssetUserData.getCat_lev1_id());
                                if (cat1_pos != 0) {

                                    mAssetCategoriesOneSpinner.setSelection(getSelectedSpinnerposition(getAssetUserData.getCat_lev1_id(), assetCategoriesLevelOneIds));
                                    mClassSpinner.setSelection(getSelectedSpinnerposition(getAssetUserData.getCat_lev1_id(), assetCategoriesLevelOneIds));
                                }
                            }
                            if (UtileKit.validateObjectValues(getAssetUserData.getCat_lev3_id())) {
                                int cat3_pos = Integer.valueOf(getAssetUserData.getCat_lev3_id());
                                mAssetCategoriesThreeSpinner.setSelection(getSpinnerposition(getAssetUserData.getCat_lev3_id(), assetCategoriesLevelOneNameList));
                            }
                        }
                    }

//                    if(goalCategoriesLevelTwoList !=null){
//                        for(int i=0; i<goalCategoriesLevelTwoList.size(); i++){
//                            goalCategoriesLevelTwoNameList.add(goalCategoriesLevelTwoList.get(i).getLev2_name());
//                        }
//                    }
//
//                    if(goalCategoriesLevelThreeList !=null){
//                        for(int i=0; i<goalCategoriesLevelThreeList.size(); i++){
//                            goalCategoriesLevelThreenameList.add(goalCategoriesLevelThreeList.get(i).getLev3_name());
//                        }
//
//                    }

                }
            } else {
                callAssetCategories();
            }
//            }

//            @Override
//            public void onFailure(Call<AssetCategoriesModel> call, Throwable t) {
//                //Log.e("CallBack", " assets is " + t);
//                UtileKit.dismisssSpinnerDialog();
//            }
//        });
            // UtileKit.dismisssSpinnerDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callAssetCategories() {
        try {

//        final ArrayList<String> assetCategoriesLevelOneIds = new ArrayList<String>();
            final ArrayList<String> assetCategoriesLevelOneIds = new ArrayList<String>();
            UtileKit.showSpinnerDialog(activity, false);
            WebServiceCalls webServiceObj;
            webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
            Call<AssetCategoriesModel> call = webServiceObj.callAssetsCategoriesService();
            call.enqueue(new Callback<AssetCategoriesModel>() {
                @Override
                public void onResponse(Call<AssetCategoriesModel> call, Response<AssetCategoriesModel> response) {
//                    //Log.e("CallBack", " assets is " + call.toString());
                    assetCategoriesModel = response.body();
                    UtileKit.dismisssSpinnerDialog();
                    try {
                        String str = gson.toJson(assetCategoriesModel);
                        UtileKit.persistingPurplePathPref("AssetCat", str);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                       assetCategoriesModel=gson.fromJson(UtileKit.getPersistedPurplePathPref("AssetCat"),AssetCategoriesModel.class);
                    if (assetCategoriesModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        assetCategoriesLevelOneListData = assetCategoriesModel.getData().getAsset_cat_lev1();
                        if (assetType.equalsIgnoreCase("Financial")) {
                            for (int i = 0; i < assetCategoriesLevelOneListData.size(); i++) {
                                if (assetCategoriesLevelOneListData.get(i).getType().equalsIgnoreCase("Financial")) {
                                    if (!assetCategoriesLevelOneList.contains(assetCategoriesLevelOneListData.get(i)))
                                        assetCategoriesLevelOneList.add(assetCategoriesLevelOneListData.get(i));

                                    System.out.println("ArrayList with duplicatesFin: " + assetCategoriesLevelOneIds);
                                }
                            }
                        } else {
                            for (int i = 0; i < assetCategoriesLevelOneListData.size(); i++) {
                                if (assetCategoriesLevelOneListData.get(i).getType().equalsIgnoreCase("Physical")) {
                                    if (!assetCategoriesLevelOneList.contains(assetCategoriesLevelOneListData.get(i)))

                                        System.out.println("ArrayList with duplicatesdd: " + assetCategoriesLevelOneIds);

                                }
                            }

                        }
                        assetCategoriesLevelTwoList = assetCategoriesModel.getData().getAsset_cat_lev2();
                        assetCategoriesLevelThreeList = assetCategoriesModel.getData().getAsset_cat_lev3();
                        if (assetCategoriesLevelOneList != null) {
                            for (int i = 0; i < assetCategoriesLevelOneList.size(); i++) {
                                assetCategoriesLevelOneNameList.add(assetCategoriesLevelOneList.get(i).getLev1_name());
                                assetCategoriesClassList.add(assetCategoriesLevelOneList.get(i).getAsset_class());
                                assetCategoriesLevelOneIds.add(assetCategoriesLevelOneList.get(i).getId());


                            }
                            //    UtileKit.setArrayListSpinnerAdapter(mAssetCategoriesOneSpinner, assetCategoriesLevelOneNameList, activity);
                            setSpinnerAdapter(mAssetCategoriesOneSpinner, assetCategoriesLevelOneNameList, activity);
                            setSpinnerAdapter(mClassSpinner, assetCategoriesClassList, activity);
                            if (UtileKit.validateObjectValues(checkAssetID)) {
                                if (UtileKit.validateObjectValues(getAssetUserData.getCat_lev1_id())) {
                                    int cat1_pos = Integer.valueOf(getAssetUserData.getCat_lev1_id());
                                    if (cat1_pos != 0) {
                                        mAssetCategoriesOneSpinner.setSelection(getSelectedSpinnerposition(getAssetUserData.getCat_lev1_id(), assetCategoriesLevelOneIds));
                                        mClassSpinner.setSelection(getSelectedSpinnerposition(getAssetUserData.getCat_lev1_id(), assetCategoriesLevelOneIds));
                                    }
                                }
                                if (UtileKit.validateObjectValues(getAssetUserData.getCat_lev3_id())) {
                                    int cat3_pos = Integer.valueOf(getAssetUserData.getCat_lev3_id());
                                    mAssetCategoriesThreeSpinner.setSelection(getSpinnerposition(getAssetUserData.getCat_lev3_id(), assetCategoriesLevelOneNameList));
                                }
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<AssetCategoriesModel> call, Throwable t) {
//                    //Log.e("CallBack", " assets is " + t);
                    UtileKit.dismisssSpinnerDialog();
                }
            });
            // UtileKit.dismisssSpinnerDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setGoalDetailsForUpdate() {

        if (UtileKit.validateObjectValues(getAssetUserData.getEmpty_flds()))
            emptyErrorValidation(getAssetUserData.getEmpty_flds());

        if (UtileKit.validateObjectValues(getAssetUserData.getAsset_name())) {
            mAssetNameEdt.setText(getAssetUserData.getAsset_name());
            mAssetNameEdt.setSelection(mAssetNameEdt.getText().length());
        } else {

        }

        if (UtileKit.validateObjectValues(getAssetUserData.getCurrent_value())) {
            if (getAssetUserData.getCurrent_value().equalsIgnoreCase("0")) {
                mAssetCurrentValueEdt.setText("");
            } else {
                mAssetCurrentValueEdt.setText(getAssetUserData.getCurrent_value().toString());
            }
        } else {

        }

        if (UtileKit.validateObjectValues(getAssetUserData.getObjective())) {
            mAssetObjectiveSpinner.setSelection(getSpinnerposition(getAssetUserData.getObjective().toString(),
                    objectiveArrayList) + 1);

        }

        if (UtileKit.validateObjectValues(getAssetUserData.getAnnual_contr())) {
            if (getAssetUserData.getAnnual_contr().equalsIgnoreCase("0")) {
                mAssetAnnualContributionEdt.setText("");
            } else
                mAssetAnnualContributionEdt.setText(getAssetUserData.getAnnual_contr());
        } else {

        }

        if (UtileKit.validateObjectValues(getAssetUserData.getYears_of_contr())) {
            if (getAssetUserData.getYears_of_contr().equalsIgnoreCase("0")) {
                mAssetContributionyearsEdt.setText("");

            } else
                mAssetContributionyearsEdt.setText(getAssetUserData.getYears_of_contr());
        } else {

        }

        if (UtileKit.validateObjectValues(getAssetUserData.getNotes())) {
            mAssetNotesEdt.setText(getAssetUserData.getNotes().toString());
        } else {

        }

        if (UtileKit.validateObjectValues(getAssetUserData.getFreq_of_contr())) {
            if (getAssetUserData.getFreq_of_contr().equalsIgnoreCase("0")) {
                mAssetFrequencyContributionEdt.setText("");

            } else
                mAssetFrequencyContributionEdt.setText(getAssetUserData.getFreq_of_contr().toString());
        } else {

        }

        if (UtileKit.validateObjectValues(getAssetUserData.getPurchase_date())) {
            String parseSetDate = getAssetUserData.getPurchase_date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date myDate = null;
            try {
                myDate = dateFormat.parse(parseSetDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy");
            String finalDate = timeFormat.format(myDate);
            if ((finalDate.contains("0002"))) {
                mAssetPurchaseDateEdt.setText("");

            } else {
                mAssetPurchaseDateEdt.setText(finalDate);
            }
        } else {
        }

        if (UtileKit.validateObjectValues(getAssetUserData.getPurchase_val())) {
            mAssetPurchaseValueEdt.setText(getAssetUserData.getPurchase_val().toString());
        } else {

        }

        if (UtileKit.validateObjectValues(getAssetUserData.getOther_cat())) {
            assetOtherEdt.setText(getAssetUserData.getOther_cat().toString());
        } else {

        }
        if (UtileKit.validateObjectValues(getAssetUserData.getYears_to_maturity())) {

            mYeartoMutSpinner.setSelection(getSpinnerposition(getAssetUserData.getYears_to_maturity().toString(),
                    monthInArray) + 1);

        } else {

        }
        if (UtileKit.validateObjectValues(getAssetUserData.getAlloc_flag())) {
            if (getAssetUserData.getAlloc_flag().equals("N")) {
                UtileKit.getSwitchNoBtnView(mAllocateOn, mAllocateOff, mContext);
                alloc_flag = "N";
            } else if (getAssetUserData.getAlloc_flag().equals("Y")) {
                UtileKit.getSwitchYesBtnView(mAllocateOn, mAllocateOff, mContext);
                alloc_flag = "Y";
            }
        }

        if (UtileKit.validateObjectValues(getAssetUserData.getIs_own_house())) {
            if (getAssetUserData.getIs_own_house().equalsIgnoreCase("N")) {
                UtileKit.getSwitchNoBtnView(own_house_on_RadioBtn, own_house_off_RadioBtn, mContext);
                is_own_house = "N";
            } else if (getAssetUserData.getIs_own_house().equalsIgnoreCase("Y")) {
                UtileKit.getSwitchYesBtnView(own_house_on_RadioBtn, own_house_off_RadioBtn, mContext);
                is_own_house = "Y";
            }

        }


    }

    private void callFamilyDetailsService() {
        UtileKit.showSpinnerDialog(activity, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GoalFamilyDetailsModel> call = webServiceObj.callFamilyDetailsListService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GoalFamilyDetailsModel>() {
            @Override
            public void onResponse(Call<GoalFamilyDetailsModel> call, Response<GoalFamilyDetailsModel> response) {
                addFamilyDetailModel = response.body();
                UtileKit.dismisssSpinnerDialog();
                if (addFamilyDetailModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    familyDetails = addFamilyDetailModel.getData().getFamily_details();
                    if (UtileKit.validateObjectValues(familyDetails)) {
                        if (!familyDetails.isEmpty()) {
                            for (int i = 0; i < familyDetails.size(); i++) {
                                mgoalBelongsToArrayList.add(familyDetails.get(i).getName());
                                mgoalBelongsToArrayListId.add(familyDetails.get(i).getId());
                            }
                            mgoalBelongsToArrayList.add("self");
                            mgoalBelongsToArrayListId.add("0");
                            if (isSignUp == false) {
                                masset_family_layout.setVisibility(View.VISIBLE);
                            }
                        }
                        setSpinnerAdapter(mAssetBelongToSpinner, mgoalBelongsToArrayList, activity);
                        if (UtileKit.validateObjectValues(assetID)) {
                            if (UtileKit.validateObjectValues(getAssetUserData)) {
                                if (UtileKit.validateObjectValues(getAssetUserData.getFamily_id())) {

                                    try {
                                        mAssetBelongToSpinner.setSelection(getSpinnerposition(getAssetUserData.getFamily_id().toString(), mgoalBelongsToArrayListId) + 1);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GoalFamilyDetailsModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relative_finish_later: {
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
            break;
            case R.id.relative_done_arrow: {

                if (emptyErrorPromptValidation(formArray)) {
                    getInputData();
                    if (asset_name != null && asset_name != "") {
                        if (UtileKit.validateObjectValues(assetID)) {
                            updateAsset();
                            dismiss();
                        } else {
                            if (UtileKit.validateObjectValues(asset_name)) {
                                addAsset();
                                dismiss();
                            } else {
                                asset_name_edt_textInputLayout.setError(HomePageActivity.errorMessageInAssets);
                                UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInAssets, mContext);
                            }
                        }
                    }
                }
                firstTimeDoneInterface.firstTimeDone("Y");
                // financialDialog(activity,assetType,isSignUp,formArray);
                //  UtileKit.persistingPurplePathPref("firstTimeGoneAsset","firstTimeGoneAsset");
            }
            break;


            case R.id.assetButton: {
                getInputData();
                if (asset_name != null && asset_name != "") {
                    if (UtileKit.validateObjectValues(assetID)) {
                        updateAsset();
                        dismiss();
                    } else {
                        if (UtileKit.validateObjectValues(asset_name)) {
                            addAsset();
                            dismiss();
                        } else {
//                            mAssetNameEdt.setError("Please enter the Asset name");
                        }
                    }
                } else {
                    asset_name_edt_textInputLayout.setError(HomePageActivity.errorMessageInAssets);
                }
            }
            break;

            case R.id.relative_left_arrow: {
                try {
                    saveAssetData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dismiss();
            }
            break;
            case R.id.relative_center_home: {
                try {
                    saveAssetData();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                getActivity().finish();
            }
            break;
            case R.id.relative_right_arrow: {
                try {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    LiabilitiesTabViewFragment fragment = new LiabilitiesTabViewFragment();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commitAllowingStateLoss();

                    dismiss();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;
        }

    }


    private void financialDialog(Activity context, final String assetType,
                                 final Boolean isSignUp, final ArrayList<String> formArray) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialog;

        inflater = LayoutInflater.from(context);
        dialogView = inflater.inflate(R.layout.yes_no_dialogs, null);
        alertDialog = new androidx.appcompat.app.AlertDialog.Builder(context).create();
        alertDialog.setView(dialogView);
        final TextView stringErrorMessage = dialogView.findViewById(R.id.textViewDilog);

        if (assetType.equalsIgnoreCase("Physical")) {
            stringErrorMessage.setText("Would you like to add Financial assets");
        } else if (assetType.equalsIgnoreCase("Financial")) {
            stringErrorMessage.setText("Would you like to add Physical assets");
        }


        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (assetType.equalsIgnoreCase("Physical")) {
                    startFragment(PhysicalAssetsListFragment.newInstance(isSignUp, formArray, firstTimeDoneInterface));
                } else if (assetType.equalsIgnoreCase("Financial")) {
                    startFragment(FinancialAssetsListFragment.newInstance(isSignUp, formArray, firstTimeDoneInterface));
                }

                alertDialog.dismiss();

            }
        });
        dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new PromptChatFragment1());
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    public void startFragment(Fragment fragment) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void saveAssetData() {
        getInputData();
        if (asset_name != null && asset_name != "") {
            if (UtileKit.validateObjectValues(assetID)) {
                updateAsset();
            } else {
                if (UtileKit.validateObjectValues(asset_name)) {
                    addAsset();
                } else {
                }
            }
        }
    }


    public void addAsset() {
        UtileKit.showSpinnerDialog(activity, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<AddAssetModel> call = webServiceObj.calladdAssetService(UtileKit.getPersistedPurplePathPref("user_id"), family_id, assetType, asset_name,
                asset_cat_lev1_id, asset_cat_lev2_id, asset_cat_lev3_id, other_cat, objective, current_value,
                annual_contr, freq_of_contr, years_of_contr, years_to_maturity, alloc_flag, alloc_to_goal, purchase_date,
                purchase_val, notes, is_own_house);
        call.enqueue(new Callback<AddAssetModel>() {
            @Override
            public void onResponse(Call<AddAssetModel> call, Response<AddAssetModel> response) {
                addAssetModel = response.body();
                UtileKit.dismisssSpinnerDialog();
                if (addAssetModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    onCheckListIsEmpty.checkListSize(1);
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<AddAssetModel> call, Throwable t) {
                onCheckListIsEmpty.checkListSize(0);
                dismiss();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }

    public void updateAsset() {
        UtileKit.showSpinnerDialog(activity, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        if (!mAllocateToGoalSpinner.isShown())
            alloc_to_goal = "";
        Call<AddAssetModel> call = webServiceObj.callupdateAssetService(UtileKit.getPersistedPurplePathPref("user_id"), family_id, assetType, assetID, asset_name, asset_cat_lev1_id, asset_cat_lev2_id, asset_cat_lev3_id, other_cat, objective, current_value,
                annual_contr, freq_of_contr, years_of_contr, years_to_maturity, alloc_flag, alloc_to_goal, purchase_date,
                purchase_val, notes, is_own_house);
        call.enqueue(new Callback<AddAssetModel>() {
            @Override
            public void onResponse(Call<AddAssetModel> call, Response<AddAssetModel> response) {
                addAssetModel = response.body();
                UtileKit.dismisssSpinnerDialog();
                if (addAssetModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    onCheckListIsEmpty.checkListSize(1);
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<AddAssetModel> call, Throwable t) {
                try {
                    onCheckListIsEmpty.checkListSize(0);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                dismiss();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }

    private void getInputData() {

        asset_name = mAssetNameEdt.getText().toString().trim();
        current_value = UtileKit.getStringwithoutDefaultCurreny(mAssetCurrentValueEdt.getEditText());
        annual_contr = UtileKit.getStringwithoutDefaultCurreny(mAssetAnnualContributionEdt.getEditText());
        years_of_contr = mAssetContributionyearsEdt.getText().toString();
        if (years_of_contr.equalsIgnoreCase("0"))
            years_of_contr = "";

        freq_of_contr = UtileKit.getStringwithoutDefaultCurreny(mAssetFrequencyContributionEdt.getEditText());
        if (freq_of_contr.equalsIgnoreCase("0"))
            freq_of_contr = "";
        years_to_maturity = mYeartoMutSpinner.getSelectedItem().toString();
        purchase_date = setDateFormat(mAssetPurchaseDateEdt.getText().toString());
        Log.i("Assets", "purchase_date" + purchase_date);
        purchase_val = UtileKit.getStringwithoutDefaultCurreny(mAssetPurchaseValueEdt.getEditText());
        notes = mAssetNotesEdt.getText().toString();
        other_cat = assetOtherEdt.getText().toString();

        if (mAllocateToGoal.getCheckedRadioButtonId() == R.id.alocate_on_RadioBtn) {
            alloc_flag = "Y";
        } else if (mAllocateToGoal.getCheckedRadioButtonId() == R.id.alocate_off_RadioBtn) {
            alloc_flag = "N";
        }
        Log.i("spcheck", "getInputData: " + alloc_flag);


        if (own_house_rg.getCheckedRadioButtonId() == R.id.own_house_on_RadioBtn) {
            is_own_house = "Y";
        } else if (own_house_rg.getCheckedRadioButtonId() == R.id.own_house_off_RadioBtn) {
            is_own_house = "N";
        }


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.asset_categoriesone_spinner:

                String spinnerValue = mAssetCategoriesOneSpinner.getSelectedItem().toString();
//                Toast.makeText(activity, "id : "+ assetCategoriesLevelOneList.get(position).getId()+"Class Name : "+assetCategoriesLevelOneList.get(position).getAsset_class(), Toast.LENGTH_SHORT).show();
                if (position != 0) {
                    if (!isFirstTimeColor && isClicked) {
                        mAssetCategoriesOneSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }

                if (mAssetCategoriesOneSpinner.getSelectedItem().equals("Commodities")){
                    classOneLayout.setVisibility(View.GONE);
                }else {
                    classOneLayout.setVisibility(View.VISIBLE);
                }


                for (int v = 0; v < assetCategoriesLevelOneList.size(); v++) {
                    if (assetCategoriesLevelOneList.get(v).getLev1_name().equals("Commodities")) {

                        String tempCheck = assetCategoriesLevelOneList.get(v).getLev1_name();
                        if (spinnerValue.equalsIgnoreCase(tempCheck)) {
                            asset_cat_lev1_id = assetCategoriesLevelOneList.get(v).getId();
                            Log.d("CHECKIDS", asset_cat_lev1_id);
                            break;
                        }
                    }
                }
                if (!spinnerValue.equalsIgnoreCase("Others(s)")) {
                    otherLinearLayout.setVisibility(View.GONE);
                    if (assetCategoriesLevelTwoList != null) {
                        if (!assetCategoriesLevelTwoNameList.isEmpty()) {
                            assetCategoriesLevelTwoNameList.clear();
                            assetCategorieLevelTwoListonSelect.clear();
                            assetCategoriesLevelTwoIdsList.clear();
                        }
                        for (int i = 0; i < assetCategoriesLevelTwoList.size(); i++) {
                            String categories_id = assetCategoriesLevelTwoList.get(i).getLev1_id();
                            if (categories_id.equalsIgnoreCase(asset_cat_lev1_id)) {
                                assetCategorieLevelTwoListonSelect.add(assetCategoriesLevelTwoList.get(i));
                                assetCategoriesLevelTwoNameList.add(assetCategoriesLevelTwoList.get(i).getLev2_name());
                                assetCategoriesLevelTwoIdsList.add(assetCategoriesLevelTwoList.get(i).getId());
                            }
                        }
                        if (UtileKit.validateObjectValues(assetCategoriesLevelTwoNameList)) {
                            if (!assetCategoriesLevelTwoNameList.isEmpty()) {
                                assetCategoriesTwoLayout.setVisibility(View.VISIBLE);
                                setSpinnerAdapter(mAssetCategoriesTwoSpinner, assetCategoriesLevelTwoNameList, activity);
                                if (getAssetUserData != null) {
                                    if (UtileKit.validateObjectValues(checkAssetID)) {
                                        if (isFirstTimeForSpinner) {
                                            isFirstTimeForSpinner = false;
                                            if (!(getAssetUserData.getCat_lev2_id().equals("0"))) {
                                                mAssetCategoriesTwoSpinner.setSelection(getSelectedSpinnerposition(getAssetUserData.getCat_lev2_id(), assetCategoriesLevelTwoIdsList));
                                            }
                                        }
                                    }
                                }
                            } else {
                                assetCategoriesTwoLayout.setVisibility(View.GONE);
                                assetCategoriesThreeLayout.setVisibility(View.GONE);
                            }
                        }
                    }
                } else {
                    asset_cat_lev2_id = "0";
                    assetCategoriesTwoLayout.setVisibility(View.GONE);
                    assetCategoriesThreeLayout.setVisibility(View.GONE);
                    otherLinearLayout.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.asset_class_spinner:

                String spinnerValues = mClassSpinner.getSelectedItem().toString();

                if (position != 0) {
                    if (!isFirstTimeColor && isClicked) {
                        mClassSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }


                for (int v = 0; v < assetCategoriesLevelOneList.size(); v++) {
                    if (mAssetCategoriesOneSpinner.getSelectedItem().equals("Commodities")) {
                      //  classOneLayout.setVisibility(View.GONE);
                        String tempCheck = assetCategoriesLevelOneList.get(v).getLev1_name();
                        if (spinnerValues.equalsIgnoreCase(tempCheck)) {
                            asset_cat_lev1_id = assetCategoriesLevelOneList.get(v).getId();
                            Log.d("CHECKIDS1", asset_cat_lev1_id);
                            break;
                        }
                    }else {
                        String tempCheck = assetCategoriesLevelOneList.get(v).getAsset_class();
                      //  classOneLayout.setVisibility(View.VISIBLE);
                        if (spinnerValues.equalsIgnoreCase(tempCheck)) {
                            asset_cat_lev1_id = assetCategoriesLevelOneList.get(v).getId();
                            Log.d("CHECKIDS1", asset_cat_lev1_id);
                            break;
                        }
                    }
                }
                if (!spinnerValues.equalsIgnoreCase("Others(s)")) {
                    otherLinearLayout.setVisibility(View.GONE);
                    if (assetCategoriesLevelTwoList != null) {
                        if (!assetCategoriesLevelTwoNameList.isEmpty()) {
                            assetCategoriesLevelTwoNameList.clear();
                            assetCategorieLevelTwoListonSelect.clear();
                            assetCategoriesLevelTwoIdsList.clear();
                        }
                        for (int i = 0; i < assetCategoriesLevelTwoList.size(); i++) {
                            String categories_id = assetCategoriesLevelTwoList.get(i).getLev1_id();
                            if (categories_id.equalsIgnoreCase(asset_cat_lev1_id)) {
                                assetCategorieLevelTwoListonSelect.add(assetCategoriesLevelTwoList.get(i));
                                assetCategoriesLevelTwoNameList.add(assetCategoriesLevelTwoList.get(i).getLev2_name());
                                assetCategoriesLevelTwoIdsList.add(assetCategoriesLevelTwoList.get(i).getId());
                            }
                        }
                        if (UtileKit.validateObjectValues(assetCategoriesLevelTwoNameList)) {
                            if (!assetCategoriesLevelTwoNameList.isEmpty()) {
                                assetCategoriesTwoLayout.setVisibility(View.VISIBLE);
                                setSpinnerAdapter(mAssetCategoriesTwoSpinner, assetCategoriesLevelTwoNameList, activity);
                                if (getAssetUserData != null) {
                                    if (UtileKit.validateObjectValues(checkAssetID)) {
                                        if (isFirstTimeForSpinner) {
                                            isFirstTimeForSpinner = false;
                                            if (!(getAssetUserData.getCat_lev2_id().equals("0"))) {
                                                mAssetCategoriesTwoSpinner.setSelection(getSelectedSpinnerposition(getAssetUserData.getCat_lev2_id(), assetCategoriesLevelTwoIdsList));
                                            }
                                        }
                                    }
                                }
                            } else {
                                assetCategoriesTwoLayout.setVisibility(View.GONE);
                                assetCategoriesThreeLayout.setVisibility(View.GONE);
                            }
                        }
                    }
                } else {
                    asset_cat_lev2_id = "0";
                    assetCategoriesTwoLayout.setVisibility(View.GONE);
                    assetCategoriesThreeLayout.setVisibility(View.GONE);
                    otherLinearLayout.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.asset_categoriestwo_spinner:

                String spinnerLevelValue = mAssetCategoriesTwoSpinner.getSelectedItem().toString();

                if (spinnerLevelValue.equalsIgnoreCase("Apartment") || spinnerLevelValue.equalsIgnoreCase("House")) {
                    own_house_layout.setVisibility(View.VISIBLE);
                } else {
                    own_house_layout.setVisibility(View.GONE);
                }

                if (position != 0) {
                    if (!isFirstTimeColor && isClicked) {
                        mAssetCategoriesTwoSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }


                for (int v = 0; v < assetCategorieLevelTwoListonSelect.size(); v++) {
                    String tempCheck = assetCategorieLevelTwoListonSelect.get(v).getLev2_name();
                    if (spinnerLevelValue.equalsIgnoreCase(tempCheck)) {
                        asset_cat_lev2_id = assetCategorieLevelTwoListonSelect.get(v).getId();
                        break;
                    }
                }
                if (assetCategoriesLevelThreeList != null) {
                    if (!assetCategoriesLevelThreenameList.isEmpty()) {
                        assetCategorieLevelThreeListonSelect.clear();
                        assetCategoriesLevelThreenameList.clear();
                    }
                    for (int i = 0; i < assetCategoriesLevelThreeList.size(); i++) {
//
                        String level2_categories_id = assetCategoriesLevelThreeList.get(i).getLev2_id();
                        if (level2_categories_id.equalsIgnoreCase(asset_cat_lev2_id)) {
                            assetCategorieLevelThreeListonSelect.add(assetCategoriesLevelThreeList.get(i));
                            assetCategoriesLevelThreenameList.add(assetCategoriesLevelThreeList.get(i).getLev3_name());
                            assetCategoriesLevelThreeIdsList.add(assetCategoriesLevelThreeList.get(i).getId());
//
                        }
                    }
                    if (UtileKit.validateObjectValues(assetCategoriesLevelThreenameList)) {
                        if (!assetCategoriesLevelThreenameList.isEmpty()) {
                            assetCategoriesThreeLayout.setVisibility(View.VISIBLE);
                            setSpinnerAdapter(mAssetCategoriesThreeSpinner, assetCategoriesLevelThreenameList, activity);

                            if (getAssetUserData != null) {
                                if (UtileKit.validateObjectValues(checkAssetID)) {
                                    if (isFirstTimeForThirdSpinner) {
                                        isFirstTimeForThirdSpinner = false;
                                        if (!(getAssetUserData.getCat_lev3_id().equals("0"))) {
                                            mAssetCategoriesThreeSpinner.setSelection(getSelectedSpinnerposition(getAssetUserData.getCat_lev3_id(), assetCategoriesLevelThreeIdsList));
                                        }
                                    }
                                }
                            }
                        } else {
                            asset_cat_lev3_id = "0";
                            assetCategoriesThreeLayout.setVisibility(View.GONE);
                            otherLinearLayout.setVisibility(View.GONE);
                        }
                    }
                }
                break;


            case R.id.asset_categoriesthree_spinner:
                String spinnerLevelThreeValue = mAssetCategoriesThreeSpinner.getSelectedItem().toString();


                if (position != 0) {
                    if (!isFirstTimeColor && isClicked) {
                        mAssetCategoriesThreeSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }


                for (int v = 0; v < assetCategorieLevelThreeListonSelect.size(); v++) {
                    String tempCheck = assetCategorieLevelThreeListonSelect.get(v).getLev3_name();
                    if (spinnerLevelThreeValue.equalsIgnoreCase(tempCheck)) {
                        asset_cat_lev3_id = assetCategorieLevelThreeListonSelect.get(v).getId();
                        break;
                    }
                }
                break;
            case R.id.asset_objective_spinner:
                objective = mAssetObjectiveSpinner.getSelectedItem().toString();


                if (position != 0) {
                    if (!isFirstTimeColor && isClicked) {
                        mAssetObjectiveSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }

                break;
            case R.id.asset_belongsto_spinner:
                try {
                    family_id = String.valueOf(getBelongsSpinnerposition(mAssetBelongToSpinner.getSelectedItem().toString(), mgoalBelongsToArrayListId, mgoalBelongsToArrayList));

                    if (position != 0) {
                        if (!isFirstTimeColor && isClicked) {
                            mAssetBelongToSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                            isClicked = false;
                        }
                    }

                } catch (Exception e) {

                }
                break;
            case R.id.allocateToGoal__spinner:
                try {
                    alloc_to_goal = String.valueOf(getBelongsSpinnerposition(mAllocateToGoalSpinner.getSelectedItem().toString(), goalId, goalName));

                    if (position != 0) {
                        if (!isFirstTimeColor && isClicked) {
                            mAllocateToGoalSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                            isClicked = false;
                        }
                    }

                } catch (Exception e) {

                }

            case R.id.liabi_loan_frequency_date_spinner:
                years_to_maturity = mYeartoMutSpinner.getSelectedItem().toString();

                break;

            default:
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
            try {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setLayout(width, height);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

        int monthincr = month + 1;
        mAssetPurchaseDateEdt.setText("" + day + "-" + monthincr + "-" + year);

    }

    public String getStringwithoutCurreny(CurrencyEditText edt) {
        String str = edt.getText().toString().replace("₹", "");
        String str1 = str.replace("\u00A0", "");
        return str1;
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

    private int getSpinnerposition(String value, ArrayList<String> spinerlist) {
        int pos = 0;
        for (int i = 0; i < spinerlist.size(); i++) {
            if (value.equalsIgnoreCase(spinerlist.get(i))) {
                pos = i;
            }
        }
        return pos;
    }

    private int getBelongsSpinnerposition(String value, ArrayList<String> spinerlistId, ArrayList<String> spinerlistName) {
        int pos = 0;
        for (int i = 0; i < spinerlistName.size(); i++) {
            if (value.equalsIgnoreCase(spinerlistName.get(i))) {
                pos = Integer.valueOf(spinerlistId.get(i));
            }
        }
        return pos;
    }

    private int getSelectedSpinnerposition(String value, ArrayList<String> spinerlist) {
        int pos = 0;
        for (int i = 0; i < spinerlist.size(); i++) {
            if (value.equalsIgnoreCase(spinerlist.get(i))) {
                pos = i;
            }
        }
        return pos + 1;
    }


    private String setDateFormat(String date) {
        String output = "";
        Log.i("Assets", "date" + date);
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

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        switch (radioGroup.getId()) {
            case R.id.allocateToGoal_rg:
                if (id == mAllocateOn.getId()) {
                    UtileKit.getSwitchYesBtnView(mAllocateOn, mAllocateOff, mContext);
                    mAllocateToGoalSpinner.setVisibility(View.VISIBLE);
                    alloc_flag = "Y";

                } else if (id == mAllocateOff.getId()) {
                    UtileKit.getSwitchNoBtnView(mAllocateOn, mAllocateOff, mContext);
                    mAllocateToGoalSpinner.setVisibility(View.GONE);
                    alloc_flag = "N";
                }
                break;

            case R.id.own_house_rg:
                if (id == own_house_on_RadioBtn.getId()) {
                    UtileKit.getSwitchYesBtnView(own_house_on_RadioBtn, own_house_off_RadioBtn, mContext);

                    is_own_house = "Y";

                } else if (id == own_house_off_RadioBtn.getId()) {
                    UtileKit.getSwitchNoBtnView(own_house_on_RadioBtn, own_house_off_RadioBtn, mContext);

                    is_own_house = "N";
                }
                break;

        }
    }

    @Override
    public void updateEditTextValue(String value, String title) {
        if (value.equalsIgnoreCase("0"))
            value = "";
        if (title.equalsIgnoreCase(purchaseDateTitle)) {
            mAssetPurchaseDateEdt.setText(value);
            UtileKit.edittextbordercolorchange(mAssetPurchaseDateEdt);
        } else if (title.equalsIgnoreCase(yearsToMaturityTitle)) {
            mAssetYearsMaturityEdt.setText(value);
            UtileKit.edittextbordercolorchange(mAssetYearsMaturityEdt);
        }


    }

    @Override
    public void updateIndividualEditTextValue(String value, String title) {

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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

}