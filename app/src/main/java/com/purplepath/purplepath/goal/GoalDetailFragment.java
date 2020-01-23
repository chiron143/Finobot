package com.purplepath.purplepath.goal;

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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.calculator.CalculatorAct;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CalendarEditText;
import com.purplepath.purplepath.customview.CharacterEditText;
import com.purplepath.purplepath.customview.CurrencyEditText;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.customview.CustomCalenderImageView;
import com.purplepath.purplepath.customview.CustomSpinerAdapter;
import com.purplepath.purplepath.customview.CustomTextInputLayout;
import com.purplepath.purplepath.customview.NumberEditText;
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.goal.InterfaceDone.FirstTimeDoneInterface;
import com.purplepath.purplepath.model.AddAndUpdateGoalModel;
import com.purplepath.purplepath.myinterface.OnGoalDoneSelectedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.incomedetails.IncomeDynamicDetail.PARENT_CLASS_SOURCE;


public class GoalDetailFragment extends DialogFragment implements View.OnClickListener,
        AdapterView.OnItemSelectedListener , RadioGroup.OnCheckedChangeListener{

    private ImageButton mGoalPlus,mGoalMinus;
    private ImageView backBtn;
    private TextView titleNameTxt;
    private CharacterEditText mgoalNameEdt,mgoalNotesEdt;
    private  EditText  mOtherCategoriesEdt,
    mYearsToGoalEdt, mGoalDurationEdt, mGoalFrequencyEdt;
    private PercentageEditText mgoalExpectedIncr;
    private SeekBar mgoalCostToGoalSeekBar, mYearsToGoalSeekbar, mGoalDurationSeekbar, mGoalFrequencySeekbar ;
    private EditText mgoalCostToGoalEdt,mgoalNoRecuranceEdt;
    private EditText mgoal_intervelEdt,mgoal_frequency_edts;
    private Spinner  mgoal_frequency_spinner;

    private Spinner  mgoalBelongsToSpinner, mgoalDetailImportanceSpinner,
            mgoalDetailPrioritySpinner,  mGoalRecurrenceMonths,
            mGoalRecurrenceyear, mGoalCategoriesLevelOneSpinner, mGoalCategoriesLevelTwoSpinner,
            mGoalCategoriesLevelThreeSpinner, mGoalCategorieFlexibilitySpinner;
    private CustomSpinerAdapter adapter_state;
    private String[] mgoalTypeStrArray, mgoalBelongsToStrArray,
            mgoalDetailImportanceStrArray, mgoalDetailPriorityStrArray,
            mgoalYearsToGoalArray, mgoalRecurrenceYearStrArray, mgoalRecurrenceMonthArray;

    private String[] mGoalFlexibilityStrArray = new String[]{"Flexible", "Non-Flexible" };
    private String user_id, goal_id, goal_name= "", goal_years="", goal_frequency, goal_recurrence, cost_of_goal, goal_imp,
            goal_priority, recur_months, recur_years,
            goal_flexibility, goal_duration, expected_increment, notes, belongs_to_id, goal_cat_lev1_id, goal_cat_lev2_id,
            goal_cat_lev3_id,
            other_category,goal_no_of_recurense;
    private String str_goal_intervel;
    private RadioGroup  mgoalRecurrenceRadioGroup = null;
    private RadioButton mgoalRecurrenceYesRadioBtn, mgoalRecurrenceNoRadioBtn;
    private CurrencyGhostView costToGoalStandaloneEdit;

    private Button mgoalDetailSave;
    private Activity activity;
    private OnGoalDoneSelectedListener mOnGoalDoneSelectedListener;

    private ArrayList<String> mgoalTypesStrArray = new ArrayList<String>();
    private ArrayList<String> mgoalBelongsToArrayList = new ArrayList<String>();
    private ArrayList<String> mgoalBelongsToArrayListId = new ArrayList<String>();
    private ArrayList<String> mgoalrelationShipStrArray = new ArrayList<String>();




    private String mcostToGoalMaxRupees,myearToGoalMaxValue,mGoalDurationMaxValue,mGoalFrequencyMaxValue;
    private String  goalID, UserID;
    private GetGoalsUserData getGoalsUserData;
    private ArrayList<GoalFamilyDetails> familyDetails = new ArrayList<GoalFamilyDetails>();
    private GoalFamilyDetailsModel addFamilyDetailModel,mGoalFamilyDetailsModel;
    private GoalCategoriesModel goalCategoriesModel;
    private ArrayList<GoalCategoriesLevelOne> goalCategoriesLevelOneList = new ArrayList<GoalCategoriesLevelOne>();
    private ArrayList<GoalCategoriesLevelTwo> goalCategoriesLevelTwoList = new ArrayList<GoalCategoriesLevelTwo>();
    private ArrayList<GoalCategoriesLevelThree> goalCategoriesLevelThreeList = new ArrayList<GoalCategoriesLevelThree>();
    private ArrayList<String> goalCategoriesLevelThreeIdsList = new ArrayList<String>();
    private ArrayList<String> goalCategoriesLevelOneNameList = new ArrayList<String>();
    private ArrayList<String> goalCategoriesLevelTwoNameList = new ArrayList<String>();
    private ArrayList<String> goalCategoriesLevelThreenameList = new ArrayList<String>();
    private ArrayList<String> goalCategoriesLevelTwoIdsList = new ArrayList<String>();
    private ArrayList<GoalCategoriesLevelTwo> goalCategorieLevelTwoListonSelect = new ArrayList<GoalCategoriesLevelTwo>();
    private ArrayList<GoalCategoriesLevelThree> goalCategorieLevelThreeListonSelect = new ArrayList<GoalCategoriesLevelThree>();
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private LinearLayout mGoalCategoriesLevelOneLayout,
            mGoalCategoriesLevelTwoLayout,mGoalCategoriesLevelThreeLayout
            ,mGoalOtherCategoriesLayout, mGoalMonthYearLayout, mgoal_family_layout,mGoalFreqLayout,mgoalFrequencyTextLayout,mgoalNoRecuranceTextLayout;

    private LinearLayout mgoalintervel_linearLayout,mgoal_frequency_layout;
    private TextInputLayout mgoal_intervel_textinput,mgoal_frequency_textinput;
    Bundle mArgs;
    FragmentTransaction fragmentTransaction;
    private int level_one, level_two, level_three;
    private Boolean level_one_flag = true, level_two_flag = true, level_three_flag = true;
    private boolean isFirstTimeForSpinner = true, isFirstTimeForThirdSpinner= true;

    LayoutInflater inflater;
    View dialogView;
    AlertDialog alertDialog;
    private CustomCalenderImageView mcalculaterImgView;
    public static final String TITLE = "";
    private  boolean isFirstTimeColor = false;
    private boolean isClicked;
    public HashMap<String,View> errorMapView=new HashMap<>();

    private LinearLayout mgoalreccurence_layout,mgoal_duration_layout,mgoal_duration_txt_layout,goal_detail_importance_layout,
            goal_detail_priority_layout,goal_detail_flexibility_layout,goal_expected_inc_edt_layout,goal_notes_edt_layout;
    private  String cat_lev1_spinner_value="";

    FloatingActionButton fab;


    ArrayList<String> formArray = new ArrayList<String>();
    private boolean isMandatory;
    Boolean isSignUp = false;
    public HashMap<String,View> mandatoryMapView=new HashMap<>();
    HashMap<String, String> mandatoryPromptMapView = new HashMap<String, String>() {{
        put("Goal Name","Enter your Goal name in detail");
        put("Goal Category","Select the Goal category from the list");
        put("Goal Belongs To","Select Self / Family member to whom the Goal belogs to");
        put("Cost to Goal","Enter actual or approximate money you need to complete your goal");
        put("Years to Goal","Enter the number of years you have to reach the goal");
        put("Goal Duration","Enter the number of years which the goal is active");
    }};
    public HashMap<String,View> mandatoryPromptPut=new HashMap<>();
    private LinearLayout goalname_layout,goalcategory_layout,
                         goal_cost_to_goal_layout,Years_to_Goal_layout;
    private LinearLayout bottom_bar_layout,bottom_bar_donelayout;
    private RelativeLayout relative_finish_later,relative_done_arrow;

    FirstTimeDoneInterface firstTimeDoneInterface;




    public static GoalDetailFragment newInstance(OnGoalDoneSelectedListener mOnGoalDoneSelectedListener,
                                                 FirstTimeDoneInterface firstTimeDoneInterface) {
        GoalDetailFragment goalDetailFragment = new GoalDetailFragment(mOnGoalDoneSelectedListener,firstTimeDoneInterface);
        goalDetailFragment.mOnGoalDoneSelectedListener = mOnGoalDoneSelectedListener;
        goalDetailFragment.firstTimeDoneInterface=firstTimeDoneInterface;
        return goalDetailFragment;
    }

    public static GoalDetailFragment newInstance() {
        GoalDetailFragment goalDetailFragment = new GoalDetailFragment();
        return goalDetailFragment;
    }


    public GoalDetailFragment(){
    }

    public GoalDetailFragment(OnGoalDoneSelectedListener mOnGoalDoneSelectedListener,FirstTimeDoneInterface firstTimeDoneInterface){
        this.mOnGoalDoneSelectedListener=mOnGoalDoneSelectedListener;
        this.firstTimeDoneInterface=firstTimeDoneInterface;

    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        transaction.commitAllowingStateLoss();
        return super.show(transaction, tag);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.MY_DIALOG);
        try {


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_goal_detail, container, false);
        activity = getActivity();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();




        fab = view.findViewById(R.id.goal_tick_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                getValuesFromInput();
                String year=UtileKit.getTextFromObjects(mYearsToGoalEdt);
                if(goal_name.isEmpty() && goal_name.equalsIgnoreCase("") ) {
                    UtileKit.intitializeAlertDialog( HomePageActivity.errorMessageInGoals , activity);
                }
                else if(goal_years.isEmpty() && goal_years.equalsIgnoreCase("")) {
                    UtileKit.intitializeAlertDialog("Please enter Years to Goal", activity);
                }
                else if(goal_recurrence=="Y") {
                    if(str_goal_intervel.equalsIgnoreCase("")){
                        validationfreq(str_goal_intervel,mgoal_intervel_textinput,year);
                    }
                    else if(!goal_duration.equalsIgnoreCase("")){
                        validationGreaterOrlesser(goal_duration,str_goal_intervel,mgoal_intervel_textinput,year);
                    }else {

                        callServicestoaddAndUpdate(goalID,year);
                    }
                }
                else {
                    callServicestoaddAndUpdate(goalID,year);
                }
            }






        });

        backBtn= view.findViewById(R.id.backButtonId);
        titleNameTxt= view.findViewById(R.id.dialogTitleId);
        mGoalMinus= view.findViewById(R.id.ib_minus);
        mGoalPlus= view.findViewById(R.id.ib_plus);
        mGoalPlus.setOnClickListener(this);
        mGoalMinus.setOnClickListener(this);

        mgoalNameEdt = view.findViewById(R.id.goal_detail_name_edt);
        errorMapView.put("goal_name",mgoalNameEdt);
    //    mgoalNameEdt.setHintText(getString(R.string.hint_goal_name), ((TextInputLayout)(mgoalNameEdt.getParent()).getParent()));


        goalname_layout= view.findViewById(R.id.goalname_layout);
        mandatoryMapView.put("Goal Name",goalname_layout);

        goal_cost_to_goal_layout= view.findViewById(R.id.goal_cost_to_goal_layout);
        mandatoryMapView.put("Cost to Goal",goal_cost_to_goal_layout);

        Years_to_Goal_layout= view.findViewById(R.id.Years_to_Goal_layout);
        mandatoryMapView.put("Years to Goal",Years_to_Goal_layout);

        mgoal_duration_layout= view.findViewById(R.id.goal_duration_layout);


        goalcategory_layout= view.findViewById(R.id.goalcategory_layout);
        mandatoryMapView.put("Goal Category",goalcategory_layout);


        mgoal_family_layout = view.findViewById(R.id.goal_family_layout);
        mandatoryMapView.put("Goal Belongs To",mgoal_family_layout);


        mGoalCategoriesLevelTwoLayout = view.findViewById(R.id.goal_category_leveltwo_layout);
        mandatoryMapView.put("categorieslevel_two",mGoalCategoriesLevelTwoLayout);


        mGoalCategoriesLevelThreeLayout = view.findViewById(R.id.goal_category_levelthree_layout);
        mandatoryMapView.put("categorieslevel_three",mGoalCategoriesLevelThreeLayout);

        mGoalOtherCategoriesLayout = view.findViewById(R.id.other_category_layout);
        mandatoryMapView.put("other_categories",mGoalOtherCategoriesLayout);


        mGoalMonthYearLayout = view.findViewById(R.id.fragment_goal_monthandyear);
        mGoalFreqLayout = view.findViewById(R.id.goal_frequency_view);
        mgoalFrequencyTextLayout= view.findViewById(R.id.ll_goalFrequency);
        //  mgoalNoRecuranceTextLayout=(LinearLayout)view.findViewById(R.id.ll_goalRequrenceFrequency);
        //  mgoalNoRecuranceEdt=(EditText)view.findViewById(R.id.RequrenceFrequencyId);
        mgoalintervel_linearLayout= view.findViewById(R.id.ll_goalintervel);
        mgoal_intervelEdt= view.findViewById(R.id.goal_intervel);
        mgoal_intervel_textinput= view.findViewById(R.id.goal_intervel_textinput);

        mgoal_frequency_layout= view.findViewById(R.id.goal_frequency_layout);
        mgoal_frequency_edts= view.findViewById(R.id.goal_frequency_edts);
        mgoal_frequency_textinput= view.findViewById(R.id.goal_intervel_textinput);

        mgoalreccurence_layout= view.findViewById(R.id.goalreccurence_layout);
        mandatoryMapView.put("goal_reccurence",mgoalreccurence_layout);

        goal_detail_importance_layout= view.findViewById(R.id.goal_detail_importance_layout);
        mandatoryMapView.put("goal_detail_importance_layout",goal_detail_importance_layout);

        goal_detail_priority_layout= view.findViewById(R.id.goal_detail_priority_layout);
        mandatoryMapView.put("goal_detail_priority_layout",goal_detail_priority_layout);

        goal_detail_flexibility_layout= view.findViewById(R.id.goal_detail_flexibility_layout);
        mandatoryMapView.put("goal_detail_flexibility_layout",goal_detail_flexibility_layout);

        goal_expected_inc_edt_layout= view.findViewById(R.id.goal_expected_inc_edt_layout);
        mandatoryMapView.put("goal_expected_inc_edt_layout",goal_expected_inc_edt_layout);

        goal_notes_edt_layout= view.findViewById(R.id.goal_notes_edt_layout);
        mandatoryMapView.put("goal_notes_edt_layout",goal_notes_edt_layout);


        mgoal_duration_txt_layout= view.findViewById(R.id.goal_duration_txt_layout);
        mandatoryMapView.put("Goal Duration",mgoal_duration_layout);



        mGoalCategoriesLevelOneSpinner = view.findViewById(R.id.goal_category_levelone_spinner);
        errorMapView.put("goal_cat_lev1_id",mGoalCategoriesLevelOneSpinner);
        mGoalCategoriesLevelOneSpinner.setOnItemSelectedListener(this);
        mGoalCategoriesLevelOneSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });
        mGoalCategoriesLevelTwoSpinner = view.findViewById(R.id.goal_category_leveltwo_spinner);
        errorMapView.put("goal_cat_lev2_id",mGoalCategoriesLevelTwoSpinner);
        mGoalCategoriesLevelTwoSpinner.setOnItemSelectedListener(this);
        mGoalCategoriesLevelTwoSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });
        mGoalCategoriesLevelThreeSpinner = view.findViewById(R.id.goal_category_levelthree_spinner);
        errorMapView.put("goal_cat_lev3_id",mGoalCategoriesLevelThreeSpinner);
        mGoalCategoriesLevelThreeSpinner.setOnItemSelectedListener(this);
        mGoalCategoriesLevelThreeSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });

        mgoalNotesEdt = view.findViewById(R.id.goal_notes_edt);
        errorMapView.put("notes",mgoalNotesEdt);
        mgoalNotesEdt.setHintText(getString(R.string.hint_goal_notes), ((TextInputLayout)(mgoalNotesEdt.getParent()).getParent()));

        mOtherCategoriesEdt = view.findViewById(R.id.goal_othercategory_edt);
        errorMapView.put("OtherCategories",mOtherCategoriesEdt);

        mgoalExpectedIncr = view.findViewById(R.id.goal_expected_inc_edt);
        errorMapView.put("expected_increment",mgoalExpectedIncr);
        mgoalExpectedIncr.setHintText(getString(R.string.hint_goal_expectedincrement), ((TextInputLayout)(mgoalExpectedIncr.getParent()).getParent()));

        mgoalCostToGoalEdt = view.findViewById(R.id.goal_costtogoal_edt);

        costToGoalStandaloneEdit= view.findViewById(R.id.et_costToGoal);
        errorMapView.put("cost_of_goal",costToGoalStandaloneEdit);
        costToGoalStandaloneEdit.setTextHint("Cost to Goal* (in todays's cost)");




        mcalculaterImgView= view.findViewById(R.id.calculaterImgView);
        mcalculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  String calculaterValue = ((CurrencyEditText) costToGoalStandaloneEdit).getText().toString();
                Intent calculatorIntent = new Intent(getActivity(), CalculatorAct.class);
                calculatorIntent.putExtra(CalculatorAct.TITLE_ACTIVITY, TITLE);
                calculatorIntent.putExtra(CalculatorAct.PARENT_ACTIVITY, PARENT_CLASS_SOURCE);
                calculatorIntent.putExtra(CalculatorAct.VALUE, costToGoalStandaloneEdit.getText().toString());
                startActivityForResult(calculatorIntent, CalculatorAct.REQUEST_RESULT_SUCCESSFUL);
            }
        });

        //  editTextDrawableClick(mgoalCostToGoalEdt);
        //editTextCurrencyDrawableClick(mgoalCostToGoalEdt);

        mYearsToGoalEdt = view.findViewById(R.id.goal_yearstogoal_edt);
       /* mYearsToGoalEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mYearsToGoalEdt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                mYearsToGoalEdt.setError(null);
            }
        });*/
        mGoalDurationEdt = view.findViewById(R.id.goal_duration_edt);
       // mGoalFrequencyEdt = (EditText) view.findViewById(R.id.goal_frequency_edt);





        editTextYearDrawableClick(mYearsToGoalEdt);
       // editTextYearDrawableClick(mGoalFrequencyEdt);
        editTextYearDrawableClick(mGoalDurationEdt);

        mgoalCostToGoalSeekBar = view.findViewById(R.id.costtogoal_seekbar);
        seekBarChange(mgoalCostToGoalSeekBar);

        mYearsToGoalSeekbar  = view.findViewById(R.id.yearstogoal_seekbar);
        mGoalDurationSeekbar  = view.findViewById(R.id.goal_duration_seekbar);
        mGoalFrequencySeekbar  = view.findViewById(R.id.goal_frequency_seekbar);

        seekBarChange(mYearsToGoalSeekbar);
        seekBarChange(mGoalDurationSeekbar);
        seekBarChange(mGoalFrequencySeekbar);


        mcostToGoalMaxRupees = String.valueOf(mgoalCostToGoalSeekBar.getMax());
        myearToGoalMaxValue = String.valueOf(mYearsToGoalSeekbar.getMax());
        mGoalDurationMaxValue = String.valueOf(mGoalDurationSeekbar.getMax());
        mGoalFrequencyMaxValue= String.valueOf(mGoalFrequencySeekbar.getMax());

/*        mgoalCostToGoalEdt.setText(mcostToGoalMaxRupees);
        mYearsToGoalEdt.setText(myearToGoalMaxValue);
        mGoalDurationEdt.setText(mGoalDurationMaxValue);*/
       // mGoalFrequencyEdt.setText(mGoalFrequencyMaxValue);

        mgoalBelongsToSpinner = view.findViewById(R.id.goal_detail_belongsto_spinner);
        errorMapView.put("belongs_to_id",mgoalBelongsToSpinner);

        mgoalBelongsToSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });
       //callFamilyDetailsService();
        mgoalBelongsToArrayList.add("Self");
        mgoalBelongsToArrayListId.add("0");
        setSpinnerAdapter(mgoalBelongsToSpinner, mgoalBelongsToArrayList, activity);

        mgoalDetailImportanceSpinner = view.findViewById(R.id.goal_detail_importance);
        errorMapView.put("goal_imp",mgoalDetailImportanceSpinner);

        mgoalDetailImportanceSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });

        mgoalDetailPrioritySpinner = view.findViewById(R.id.goal_detail_priority);
        errorMapView.put("goal_priority",mgoalDetailPrioritySpinner);

        mgoalDetailPrioritySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });

        mGoalCategorieFlexibilitySpinner = view.findViewById(R.id.goal_detail_flexibility);
        errorMapView.put("goal_flexibility",mGoalCategorieFlexibilitySpinner);

        mGoalCategorieFlexibilitySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });

       // UtileKit.setStringArraySpinnerAdapter(mGoalCategorieFlexibilitySpinner, goalFlexi, activity);
        setStringArraySpinnerAdapter(mGoalCategorieFlexibilitySpinner, mGoalFlexibilityStrArray, activity);

        mgoalRecurrenceMonthArray = getResources().getStringArray(R.array.goal_recurrence_months);
        mGoalRecurrenceMonths = view.findViewById(R.id.goal_recurrence_months);
       //UtileKit.setStringArraySpinnerAdapter(mGoalRecurrenceMonths, UtileKit.goalRecurrenceMonths(), activity);
        setStringArraySpinnerAdapter(mGoalRecurrenceMonths, mgoalRecurrenceMonthArray, activity);

        mgoalRecurrenceYearStrArray = getResources().getStringArray(R.array.goal_recurrence_years);
                mGoalRecurrenceyear = view.findViewById(R.id.goal_recurrence_year);

       // UtileKit.setStringArraySpinnerAdapter(mGoalRecurrenceyear, mgoalRecurrenceYearStrArray, activity);
        setStringArraySpinnerAdapter(mGoalRecurrenceyear, mgoalRecurrenceYearStrArray, activity);

        mgoalRecurrenceRadioGroup = view.findViewById(R.id.goal_recurrence_RadioRg);
        mgoalRecurrenceYesRadioBtn = view.findViewById(R.id.goal_yes_RadioBtn);
        mgoalRecurrenceNoRadioBtn = view.findViewById(R.id.goal_no_RadioBtn);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        mgoalRecurrenceRadioGroup.setOnCheckedChangeListener(this);

//        mgoalRecurrenceNoRadioBtn.setChecked(true);
//        mysetChecked(mgoalRecurrenceNoRadioBtn, mgoalRecurrenceYesRadioBtn, activity);


       // mgoalTypeStrArray = getResources().getStringArray(R.array.goal_type);
       // UtileKit.setStringArraySpinnerAdapter(mgoalTypeSpinner, mgoalTypeStrArray, activity);
        //mgoalTypeSpinner.setOnItemSelectedListener(this);
       // mgoalTypeSpinner.setOnItemSelectedListener(this);

       // mgoalBelongsToStrArray = getResources().getStringArray(R.array.relationship);
        // UtileKit.setStringArraySpinnerAdapter(mgoalBelongsToSpinner, mgoalBelongsToStrArray, activity);
        mgoalBelongsToSpinner.setOnItemSelectedListener(this);

        mgoalDetailImportanceStrArray = getResources().getStringArray(R.array.goal_importants);
        //UtileKit.setStringArraySpinnerAdapter(mgoalDetailImportanceSpinner, mgoalDetailImportanceStrArray, activity);
        setStringArraySpinnerAdapter(mgoalDetailImportanceSpinner,mgoalDetailImportanceStrArray, activity );
        mgoalDetailImportanceSpinner.setOnItemSelectedListener(this);
        mgoalDetailPriorityStrArray = getResources().getStringArray(R.array.goal_priority);
       // UtileKit.setStringArraySpinnerAdapter(mgoalDetailPrioritySpinner, mgoalDetailPriorityStrArray, activity);
        setStringArraySpinnerAdapter(mgoalDetailPrioritySpinner,mgoalDetailPriorityStrArray,activity);
        mgoalDetailPrioritySpinner.setOnItemSelectedListener(this);
        mgoalDetailSave = view.findViewById(R.id.goal_details_save);
        mgoalDetailSave.setOnClickListener(this);

        // mpersonalMaritialStatusArray = getResources().getStringArray(R.array.maritalstatus);
        ///setSpinnerAdapter(mMartialStatusSpinner, mpersonalMaritialStatusArray);
          mArgs = getArguments();
          goalID = mArgs.getString("goalid");
        mGoalFamilyDetailsModel=(GoalFamilyDetailsModel)mArgs.getSerializable("GoalFamilyDetails");

        if(mArgs!=null) {
            isSignUp=mArgs.getBoolean("isSignUp");
        }

        if(mArgs!=null) {
            formArray = (ArrayList<String>) mArgs.getSerializable("formArray");

        }

        useGoalFamilyDetailsModelInfo( mGoalFamilyDetailsModel);

        callGoalCategoriesService();

        if(UtileKit.validateObjectValues(goalID)) {
            UserID = mArgs.getString("userid");
            getGoalsUserData = (GetGoalsUserData) mArgs.getSerializable("object");
            Log.i("setGoalDetailsForUpdate","setGoalDetailsForUpdate" + getGoalsUserData.getCost_of_goal());
            setGoalDetailsForUpdate(getGoalsUserData);
        }
        intitializeAlertDialog();
        titleNameTxt.setText("Add Goal ");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        if(isSignUp==false) {
            mgoalNameEdt.setHintText(getString(R.string.hint_goal_name), ((TextInputLayout)(mgoalNameEdt.getParent()).getParent()));
            costToGoalStandaloneEdit.setfullHintTxt(getString(R.string.hint_goal_costtogoal));
        }

        bottom_bar_layout= view.findViewById(R.id.bottom_bar_layout);
        UtileKit.mandatoryFieldLinearLayout(isSignUp,bottom_bar_layout);

        bottom_bar_donelayout= view.findViewById(R.id.bottom_bar_donelayout);
        UtileKit.mandatoryFieldDoneLayout(isSignUp,bottom_bar_donelayout);


        relative_finish_later= view.findViewById(R.id.relative_finish_later);
        relative_done_arrow= view.findViewById(R.id.relative_done_arrow);
        relative_finish_later.setOnClickListener(this);
        relative_done_arrow.setOnClickListener(this);


        mandatoryPromptPut.put("Goal Name",mgoalNameEdt);
        mandatoryPromptPut.put("Goal Category",mGoalCategoriesLevelOneSpinner);
        mandatoryPromptPut.put("Goal Belongs To",mgoalBelongsToSpinner);
        mandatoryPromptPut.put("Cost to Goal",costToGoalStandaloneEdit);
        mandatoryPromptPut.put("Years to Goal",mYearsToGoalEdt);
        mandatoryPromptPut.put("Goal Duration",mGoalDurationEdt);





        return view;
    }

    private void validationGreaterOrlesser(String str_goal_intervel, String goal_duration, TextInputLayout textinput, String year) {
          if (Integer.parseInt(str_goal_intervel) < Integer.parseInt(goal_duration)) {
            callServicestoaddAndUpdate(goalID, year);
        }else {
            textinput.setError("Goal interval should be greater than goal duration");
        }
    }
    private void validationfreq(String str_goal_intervel, TextInputLayout textinput, String year) {
        if (!str_goal_intervel.equalsIgnoreCase("")) {
            callServicestoaddAndUpdate(goalID, year);
        }
        else if(goal_duration.equalsIgnoreCase("")&&!goal_frequency.isEmpty()){
            callServicestoaddAndUpdate(goalID, year);
        }else {
            textinput.setError("Please fill Goal interval");
        }
    }
    private void callServicestoaddAndUpdate(String goalID, String year) {
        if (UtileKit.validateObjectValues(goalID)) {
            if (UtileKit.validateObjectValues(year)) {
                callUpdateGoalDetailsService();
                dismiss();
            } else {
                UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInGoals, activity);
            }
        } else {
            if (UtileKit.validateObjectValues(year)) {
                callAddGoalDetailsService();
                dismiss();
            } else {
                UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInGoals, activity);
            }
        }
    }



//signup propmt
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        if(isSignUp==true) {
            fab.setVisibility(View.GONE);

            for (String key : mandatoryMapView.keySet()) {
                mandatoryMapView.get(key).setVisibility(View.GONE);
            }
        }

        emptyErrorValidationMandatory(formArray);










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
                ((CustomTextInputLayout) (view.getParent()).getParent()).setError(mandatoryPromptMapView.get(key));
                isMandatory=false;
            }else {
                ((CustomTextInputLayout) (view.getParent()).getParent()).setError(null);
            }
        }else if(view instanceof CurrencyGhostView){
            if(((CurrencyGhostView) view).getText().length()==0) {
                ((CustomTextInputLayout) (((CurrencyGhostView) view).getEditText().getParent()).getParent()).setError(mandatoryPromptMapView.get(key));
                isMandatory=false;
            }else {
               ((CustomTextInputLayout) (((CurrencyGhostView) view).getEditText().getParent()).getParent()).setError(null);
            }
        }
        else if(view instanceof Spinner){

            switch(view.getId()){
                case R.id.goal_category_levelone_spinner:
                    String goal_category=mGoalCategoriesLevelOneSpinner.getSelectedItem().toString();
                    if(goal_category.equalsIgnoreCase("")) {
                        mGoalCategoriesLevelOneSpinner.setSelection(0);
                        spinnerError(mGoalCategoriesLevelOneSpinner);
                        isMandatory = false;
                    }
                    break;
                case R.id.goal_detail_belongsto_spinner:
                    String goal_detail_belongsto=mgoalBelongsToSpinner.getSelectedItem().toString();
                    if(goal_detail_belongsto.equalsIgnoreCase("")) {
                        mgoalBelongsToSpinner.setSelection(0);
                        spinnerError(mgoalBelongsToSpinner);
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





    private void useGoalFamilyDetailsModelInfo(GoalFamilyDetailsModel addFamilyDetailModel) {
        if(addFamilyDetailModel!=null) {
    if (addFamilyDetailModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
        familyDetails = addFamilyDetailModel.getData().getFamily_details();
        if (UtileKit.validateObjectValues(familyDetails)) {
            if (!familyDetails.isEmpty()) {
                mgoalBelongsToArrayList.clear();
                mgoalBelongsToArrayListId.clear();
                for (int i = 0; i < familyDetails.size(); i++) {
                    mgoalBelongsToArrayList.add(familyDetails.get(i).getName());
                    mgoalBelongsToArrayListId.add(familyDetails.get(i).getId());
                }
                mgoalBelongsToArrayList.add("Self");
                mgoalBelongsToArrayListId.add("0");
                mgoal_family_layout.setVisibility(View.VISIBLE);
            }
            //      UtileKit.setArrayListSpinnerAdapter(mgoalBelongsToSpinner, mgoalBelongsToArrayList, activity);
            setSpinnerAdapter(mgoalBelongsToSpinner, mgoalBelongsToArrayList, activity);
            for(String s : mgoalBelongsToArrayList){
                Log.i("spcheck", "useGoalFamilyDetailsModelInfo: in ws "+s);
            }
//                        if(UtileKit.validateObjectValues(getGoalsUserData)) {
//                            if (UtileKit.validateObjectValues(getGoalsUserData.getBelongs_to_id())) {
//                                int belongsto_pos = Integer.valueOf(getGoalsUserData.getBelongs_to_id().toString());
//                                //Log.e("belongsSpinnerPosition", belongsto_pos + "");
//                                mgoalBelongsToSpinner.setSelection(belongsto_pos);
//                            }
//                        }
        }
    }
}

    }

    @Override
    public void onClick(View v) {
        int value;
        switch (v.getId()) {


            //signup prompt
            case R.id.relative_finish_later:{
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
            break;
            case R.id.relative_done_arrow:





                if(emptyErrorPromptValidation(formArray)) {

                    getValuesFromInput();
                    String year=UtileKit.getTextFromObjects(mYearsToGoalEdt);
                    if(goal_name.isEmpty() && goal_name.equalsIgnoreCase("") ) {
                        UtileKit.intitializeAlertDialog( HomePageActivity.errorMessageInGoals , activity);
                    }
                    else if(goal_years.isEmpty() && goal_years.equalsIgnoreCase("")) {
                        UtileKit.intitializeAlertDialog("Please enter Years to Goal ", activity);
                    }
                    else if(goal_recurrence=="Y") {
                        if(str_goal_intervel.equalsIgnoreCase("")){
                            validationfreq(str_goal_intervel,mgoal_intervel_textinput,year);
                        }
                        else if(!goal_duration.equalsIgnoreCase("")){
                            validationGreaterOrlesser(goal_duration,str_goal_intervel,mgoal_intervel_textinput,year);
                        }else {

                            callServicestoaddAndUpdate(goalID,year);
                        }
                    }
                    else {
                        callServicestoaddAndUpdate(goalID,year);
                    }

                }

firstTimeDoneInterface.firstTimeDone("Y");

            break;
            case R.id.goal_details_save:
                getValuesFromInput();
                if(UtileKit.validateObjectValues(goalID)) {
                    if(UtileKit.validateObjectValues(goal_name)) {
                        callUpdateGoalDetailsService();
                        dismiss();
                    }else{
                        mgoalNameEdt.setError("Please Enter Goal Name");
                    }
                }else{
                    if(UtileKit.validateObjectValues(goal_name)) {
                        callAddGoalDetailsService();
                        dismiss();
                    }else{
                        mgoalNameEdt.setError("Please Enter Goal Name");
                    }
                }
                break;
            case R.id.ib_plus:
                String incrementValue = mgoalCostToGoalEdt.getText().toString().replace(",","").trim();
                        //getStringwithoutCurreny(EdtText);
                //Log.e("incrementValue",""+incrementValue);
                if(incrementValue.equalsIgnoreCase("")){
                    incrementValue ="0";
                    value = Integer.valueOf(incrementValue);
                    value = value + 100;
                    String increment = String.valueOf(value);
                    mgoalCostToGoalEdt.setText(increment);
                }else {
                    value = Integer.valueOf(incrementValue);
                    value = value + 100;
                    String increment = String.valueOf(value);
                    mgoalCostToGoalEdt.setText(increment);
                }
                break;

            case R.id.ib_minus:
                String decreaseValue = mgoalCostToGoalEdt.getText().toString().replace(",","").trim();
                //getStringwithoutCurreny(EdtText);
                //Log.e("decreaseValue",""+decreaseValue);

                if(decreaseValue .equalsIgnoreCase("")){
                    decreaseValue ="0";
                    value = Integer.valueOf(decreaseValue);
                    value = value + 100;
                    String deccrement = String.valueOf(value);
                    mgoalCostToGoalEdt.setText(deccrement);
                }else {
                    value = Integer.valueOf(decreaseValue);
                    value = value - 100;
                    String deccrement = String.valueOf(value);
                    mgoalCostToGoalEdt.setText(deccrement);
                }
                break;
            case R.id.relative_left_arrow:
            {
                try {
                    saveGoalData();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                dismiss();





            }
            break;
            case R.id.relative_center_home:
            {
                mOnGoalDoneSelectedListener.onHomeBackPresedLisaner();

                try {
                    saveGoalData();
                }catch (Exception e) {
                    e.printStackTrace();
                }

                dismiss();
//				getActivity().finish();
            }
            break;
            case R.id.relative_right_arrow:
            {
                mOnGoalDoneSelectedListener.onNextBAckpressed();
                dismiss();

            }
            break;

        }

    }






    private void saveGoalData(){
        getValuesFromInput();
        if(UtileKit.validateObjectValues(goalID)) {
            if(UtileKit.validateObjectValues(goal_name)) {
                callUpdateGoalDetailsService();
            }else{
                // mgoalNameEdt.setError("Please Enter Goal Name");
            }
        }else{
            if(UtileKit.validateObjectValues(goal_name)) {
                callAddGoalDetailsService();
            }else{
                // mgoalNameEdt.setError("Please Enter Goal Name");
            }
        }
    }




    private void getValuesFromInput() {
        goal_name = mgoalNameEdt.getText().toString().trim();
        //cost_of_goal =  mgoalCostToGoalEdt.getText().toString().trim();
        cost_of_goal=UtileKit.getStringwithoutDefaultCurreny(costToGoalStandaloneEdit.getEditText());
        //Log.e("spcheck","cost_of_goal "+cost_of_goal);
        goal_duration = mGoalDurationEdt.getText().toString().trim();
        other_category =  mOtherCategoriesEdt.getText().toString();
        goal_years = mYearsToGoalEdt.getText().toString();
         mGoalDurationEdt.getText().toString();

        //goal_frequency = mGoalFrequencyEdt.getText().toString();
        goal_frequency=mgoal_frequency_edts.getText().toString();

        Boolean checked = mgoalRecurrenceYesRadioBtn.isChecked();
        if (checked) {
            goal_recurrence = "Y";
        } else if(mgoalRecurrenceNoRadioBtn.isChecked()) {
            goal_recurrence = "N";
            recur_months="0";
            recur_years ="0";
        }
        expected_increment = mgoalExpectedIncr.getText().toString();
        notes =  mgoalNotesEdt.getText().toString();
      //  goal_no_of_recurense=mgoalNoRecuranceEdt.getText().toString();
        str_goal_intervel=mgoal_intervelEdt.getText().toString();



        ////Log.e("goal_years",""+goal_years);
    }


    private void callAddGoalDetailsService() {

        //constant value assigned for goal time purpose
        if(cat_lev1_spinner_value.equalsIgnoreCase("Retirement")){
          //  goal_duration="100";
            goal_recurrence="N";
        }
        UtileKit.showSpinnerDialog(activity, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<AddAndUpdateGoalModel> call = webServiceObj.callAddGoalService(UtileKit.getPersistedPurplePathPref("user_id"),
                goal_name, goal_years, goal_frequency,goal_recurrence,str_goal_intervel, recur_months, recur_years, cost_of_goal, goal_imp,
                goal_priority, goal_flexibility,goal_duration, expected_increment, notes, belongs_to_id,goal_cat_lev1_id, goal_cat_lev2_id, goal_cat_lev3_id,
                other_category);
        call.enqueue(new Callback<AddAndUpdateGoalModel>() {
            @Override
            public void onResponse(Call<AddAndUpdateGoalModel> call, Response<AddAndUpdateGoalModel> response) {
                //Log.e("CallBack", "response is "+ call.toString());
            //    activity.getFragmentManager().popBackStack();


                try {
                   // if(mOnGoalDoneSelectedListener!=null) {
                        mOnGoalDoneSelectedListener.OnGoalDoneSelected();
                   // }
                }catch(Exception e){
                    e.printStackTrace();
                }
                dismiss();
                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<AddAndUpdateGoalModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( activity,t);
            }
        });

    }

    private void callFamilyDetailsService() {
        UtileKit.showSpinnerDialog(activity, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GoalFamilyDetailsModel> call = webServiceObj.callFamilyDetailsListService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GoalFamilyDetailsModel>() {
            @Override
            public void onResponse(Call<GoalFamilyDetailsModel> call, Response<GoalFamilyDetailsModel> response) {
                //Log.e("spcheck", " callFamilyDetailsListService success");
                addFamilyDetailModel = response.body();
                UtileKit.dismisssSpinnerDialog();
                if (addFamilyDetailModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    familyDetails = addFamilyDetailModel.getData().getFamily_details();
                    if (UtileKit.validateObjectValues(familyDetails)) {
                        if(!familyDetails.isEmpty()) {
                            for (int i = 0; i < familyDetails.size(); i++) {
                                mgoalBelongsToArrayList.add(familyDetails.get(i).getName());
                                mgoalBelongsToArrayListId.add(familyDetails.get(i).getId());
                            }
                            mgoalBelongsToArrayList.add("Self");
                            mgoalBelongsToArrayListId.add("0");
                            mgoal_family_layout.setVisibility(View.VISIBLE);
                        }
                  //      UtileKit.setArrayListSpinnerAdapter(mgoalBelongsToSpinner, mgoalBelongsToArrayList, activity);
                        setSpinnerAdapter(mgoalBelongsToSpinner, mgoalBelongsToArrayList, activity);
//                        if(UtileKit.validateObjectValues(getGoalsUserData)) {
//                            if (UtileKit.validateObjectValues(getGoalsUserData.getBelongs_to_id())) {
//                                int belongsto_pos = Integer.valueOf(getGoalsUserData.getBelongs_to_id().toString());
//                                //Log.e("belongsSpinnerPosition", belongsto_pos + "");
//                                mgoalBelongsToSpinner.setSelection(belongsto_pos);
//                            }
//                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<GoalFamilyDetailsModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( activity,t);
            }
        });
    }


    private void callGoalCategoriesService(){
        final ArrayList<String> goalCategoriesIds = new ArrayList<String>();
        UtileKit.showSpinnerDialog(activity, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GoalCategoriesModel> call = webServiceObj.callGoalCategoriesService();
        call.enqueue(new Callback<GoalCategoriesModel>() {
            @Override
            public void onResponse(Call<GoalCategoriesModel> call, Response<GoalCategoriesModel> response) {
                //Log.e("CallBack", " goal is " + call.toString());
                goalCategoriesModel = response.body();
                UtileKit.dismisssSpinnerDialog();
                if (goalCategoriesModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    goalCategoriesLevelOneList = goalCategoriesModel.getData().getGoal_cat_lev1();
                    goalCategoriesLevelTwoList = goalCategoriesModel.getData().getGoal_cat_lev2();
                    goalCategoriesLevelThreeList  = goalCategoriesModel.getData().getGoal_cat_lev3();

                    if(goalCategoriesLevelOneList !=null){
                        for(int i=0; i<goalCategoriesLevelOneList.size(); i++){
                            goalCategoriesLevelOneNameList.add(goalCategoriesLevelOneList.get(i).getLev1_name());
                            goalCategoriesIds.add(goalCategoriesLevelOneList.get(i).getId());
                        }
                      //  UtileKit.setArrayListSpinnerAdapter(mGoalCategoriesLevelOneSpinner, goalCategoriesLevelOneNameList, activity);
                        System.out.println("CategoryoneArraylistvalues"+ goalCategoriesLevelOneNameList);
                        setSpinnerAdapter(mGoalCategoriesLevelOneSpinner, goalCategoriesLevelOneNameList, activity);
                        if(UtileKit.validateObjectValues(goalID)) {
                            if (UtileKit.validateObjectValues(getGoalsUserData.getGoal_cat_lev1_id())) {
                                int cat1_pos = Integer.valueOf(getGoalsUserData.getGoal_cat_lev1_id());
                                if(!(getGoalsUserData.getGoal_cat_lev1_id().equals("0"))) {
                                    mGoalCategoriesLevelOneSpinner.setSelection(getSelectedSpinnerposition(getGoalsUserData.getGoal_cat_lev1_id(), goalCategoriesIds));
                                }
                            }
//                            if(UtileKit.validateObjectValues(getAssetUserData.getCat_lev3_id())) {
//                                int cat3_pos = Integer.valueOf(getAssetUserData.getCat_lev3_id());
//                                mAssetCategoriesThreeSpinner.setSelection(getSpinnerposition(getAssetUserData.getCat_lev3_id(), assetCategoriesLevelOneNameList));
//                            }
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
            }
            @Override
            public void onFailure(Call<GoalCategoriesModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( activity,t);
            }
        });
    }




    private void emptyErrorValidation(ArrayList<String> emptyArrayList) {
        for(String obj:emptyArrayList)
        {
            View view=errorMapView.get(obj);
            UtileKit.emptyErrorViewList(view);
        }
    }



    private void callUpdateGoalDetailsService() {
  //constant value assigned for goal time purpose
        if(cat_lev1_spinner_value.equalsIgnoreCase("Retirement")){

          //  goal_duration="100";
            goal_recurrence="N";
        }

        UtileKit.showSpinnerDialog(activity, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<AddAndUpdateGoalModel> call = webServiceObj.callUpdateGoalService(UtileKit.getPersistedPurplePathPref("user_id"), goalID,
                goal_name, goal_years, goal_frequency, goal_recurrence, str_goal_intervel,recur_months, recur_years, cost_of_goal,
                goal_imp, goal_priority, goal_flexibility, goal_duration, expected_increment, notes, belongs_to_id,
                goal_cat_lev1_id,
                goal_cat_lev2_id, goal_cat_lev3_id, other_category);
        call.enqueue(new Callback<AddAndUpdateGoalModel>() {
            @Override
            public void onResponse(Call<AddAndUpdateGoalModel> call, Response<AddAndUpdateGoalModel> response) {
                //Log.e("CallBack", " update is " + call.toString());
                UtileKit.dismisssSpinnerDialog();
                try {
                    mOnGoalDoneSelectedListener.OnGoalDoneSelected();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<AddAndUpdateGoalModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( activity,t);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.goal_detail_belongsto_spinner:
                //belongs_to_id = mgoalBelongsToArrayListId.get(position);
                //String belongss_goal =  mgoalBelongsToSpinner.getSelectedItem().toString();
                //belongs_to_id = String.valueOf(getBelongToIndex(belongss_goal));

                if(position!=0){
                    if(!isFirstTimeColor&&isClicked) {
                        mgoalBelongsToSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }


                try{
                   // belongs_to_id = mgoalBelongsToArrayListId.get(position - 1);
                    belongs_to_id      =   String.valueOf(getBelongsSpinnerposition(mgoalBelongsToSpinner.getSelectedItem().toString(),mgoalBelongsToArrayListId, mgoalBelongsToArrayList));
                   // mgoalBelongsToSpinner.setBackgroundResource(R.drawable.edittextbackgrounggreen);
                }catch(ArrayIndexOutOfBoundsException e){

                }
                break;
            case R.id.goal_detail_importance:
                goal_imp = mgoalDetailImportanceSpinner.getSelectedItem().toString();

                if(position!=0){
                    if(!isFirstTimeColor&&isClicked) {
                        mgoalDetailImportanceSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }

                break;
            case R.id.goal_detail_priority:
                goal_priority = mgoalDetailPrioritySpinner.getSelectedItem().toString();

                if(position!=0){
                    if(!isFirstTimeColor&&isClicked) {
                        mgoalDetailPrioritySpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }


                break;
            case R.id.goal_category_levelone_spinner:
               // goal_cat_lev1_id = goalCategoriesLevelOneList.get(position).getId();
                 cat_lev1_spinner_value = mGoalCategoriesLevelOneSpinner.getSelectedItem().toString();

                if(cat_lev1_spinner_value.equalsIgnoreCase("Retirement")){
                    mgoalreccurence_layout.setVisibility(View.GONE);
                    mgoal_duration_layout.setVisibility(View.GONE);
                    mgoal_duration_txt_layout.setVisibility(View.GONE);
                }else {
                    mgoalreccurence_layout.setVisibility(View.VISIBLE);
                    mgoal_duration_layout.setVisibility(View.VISIBLE);
                    mgoal_duration_txt_layout.setVisibility(View.VISIBLE);
                }




                if(position!=0){
                    if(!isFirstTimeColor&&isClicked) {
                        mGoalCategoriesLevelOneSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }


                //Log.e("GoalCategoryLevel1Id",spinnerValue);
                for(int v=0; v<goalCategoriesLevelOneList.size(); v++){
                   String tempCheck = goalCategoriesLevelOneList.get(v).getLev1_name();
                    if(cat_lev1_spinner_value.equalsIgnoreCase(tempCheck)) {
                        goal_cat_lev1_id = goalCategoriesLevelOneList.get(v).getId();
                        //Log.e("GoalCategoryLevel1Id",goal_cat_lev1_id);
                        break;
                    }
                }
                if(!cat_lev1_spinner_value.equalsIgnoreCase("Other")) {
                    if (goalCategoriesLevelTwoList != null) {
                        if (!goalCategoriesLevelTwoNameList.isEmpty()) {
                            goalCategoriesLevelTwoNameList.clear();
                            goalCategorieLevelTwoListonSelect.clear();
                            goalCategoriesLevelTwoIdsList.clear();
                        }
                        for (int i = 0; i < goalCategoriesLevelTwoList.size(); i++) {
                            String categories_id = goalCategoriesLevelTwoList.get(i).getLev1_id();
                            if (categories_id.equalsIgnoreCase(goal_cat_lev1_id)) {
                                goalCategorieLevelTwoListonSelect.add(goalCategoriesLevelTwoList.get(i));
                                goalCategoriesLevelTwoNameList.add(goalCategoriesLevelTwoList.get(i).getLev2_name());
                                goalCategoriesLevelTwoIdsList.add(goalCategoriesLevelTwoList.get(i).getId());
                            }
                        }
                        if (UtileKit.validateObjectValues(goalCategoriesLevelTwoNameList)) {
                            if (!goalCategoriesLevelTwoNameList.isEmpty()) {
                                mGoalCategoriesLevelTwoLayout.setVisibility(View.VISIBLE);
                               // UtileKit.setArrayListSpinnerAdapter(mGoalCategoriesLevelTwoSpinner,
                                // goalCategoriesLevelTwoNameList, activity);
                                setSpinnerAdapter(mGoalCategoriesLevelTwoSpinner, goalCategoriesLevelTwoNameList, activity);
                                if(getGoalsUserData!=null){
                                    if(UtileKit.validateObjectValues(goalID)) {
                                        if(isFirstTimeForSpinner){
                                            isFirstTimeForSpinner = false;
                                            if(!(getGoalsUserData.getGoal_cat_lev2_id().equals("0"))) {
                                                mGoalCategoriesLevelTwoSpinner.setSelection(getSelectedSpinnerposition(getGoalsUserData.getGoal_cat_lev2_id(), goalCategoriesLevelTwoIdsList));
                                            }
                                        }
                                    }
                                }
                            } else {
                                mGoalCategoriesLevelTwoLayout.setVisibility(View.GONE);
                                mGoalCategoriesLevelThreeLayout.setVisibility(View.GONE);
                                mGoalOtherCategoriesLayout.setVisibility(View.GONE);
                            }
                        }
                    }
                }else{
                    goal_cat_lev2_id ="0";
                    mGoalCategoriesLevelTwoLayout.setVisibility(View.GONE);
                    mGoalCategoriesLevelThreeLayout.setVisibility(View.GONE);
                    mGoalOtherCategoriesLayout.setVisibility(View.VISIBLE);
                }
//                if(level_one_flag)
//                if(UtileKit.validateObjectValues(getGoalsUserData.getGoal_cat_lev1_id()) ) {
//                    level_one =  Integer.valueOf(getGoalsUserData.getGoal_cat_lev1_id().toString());
//                    if(level_one>0) {
//                        mGoalCategoriesLevelOneSpinner.setSelection(level_one);
//                        level_one=0;
//                        level_one_flag = false;
//                    }
//                }
                break;
            case R.id.goal_category_leveltwo_spinner:
               // goal_cat_lev2_id = goalCategorieLevelTwoListonSelect.get(position).getId();
                String spinnerValueLevel2 = mGoalCategoriesLevelTwoSpinner.getSelectedItem().toString();



                if(position!=0){
                    if(!isFirstTimeColor&&isClicked) {
                        mGoalCategoriesLevelTwoSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }


                for(int v=0; v<goalCategorieLevelTwoListonSelect.size(); v++){
                    String tempCheck = goalCategorieLevelTwoListonSelect.get(v).getLev2_name();
                    if(spinnerValueLevel2.equalsIgnoreCase(tempCheck)) {
                        goal_cat_lev2_id =goalCategorieLevelTwoListonSelect.get(v).getId();
                        break;
                    }
                }

                if(goalCategoriesLevelThreeList !=null){
                    if(!goalCategoriesLevelThreenameList.isEmpty()){
                        goalCategorieLevelThreeListonSelect.clear();
                        goalCategoriesLevelThreenameList.clear();
                    }
                    for(int i=0; i<goalCategoriesLevelThreeList.size(); i++) {
                        String level2_categories_id = goalCategoriesLevelThreeList.get(i).getLev2_id();
                        if (level2_categories_id.equalsIgnoreCase(goal_cat_lev2_id)) {
                            goalCategorieLevelThreeListonSelect.add(goalCategoriesLevelThreeList.get(i));
                            goalCategoriesLevelThreenameList.add(goalCategoriesLevelThreeList.get(i).getLev3_name());
                            goalCategoriesLevelThreeIdsList.add(goalCategoriesLevelThreeList.get(i).getId());
                        }
                    }
                    if(UtileKit.validateObjectValues(goalCategoriesLevelThreenameList)) {
                        if (!goalCategoriesLevelThreenameList.isEmpty()) {
                            mGoalCategoriesLevelThreeLayout.setVisibility(View.VISIBLE);
                            //  UtileKit.setArrayListSpinnerAdapter(mGoalCategoriesLevelThreeSpinner, goalCategoriesLevelThreenameList, activity);
                            // setSpinnerLevelAdapter(mGoalCategoriesLevelThreeSpinner, goalCategoriesLevelThreenameList, activity);
                            setSpinnerAdapter(mGoalCategoriesLevelThreeSpinner, goalCategoriesLevelThreenameList, activity);
                            try {
                                if (getGoalsUserData != null) {
                                    if (UtileKit.validateObjectValues(goalID)) {
                                    if(isFirstTimeForThirdSpinner){
                                        isFirstTimeForThirdSpinner = false;
                                        if(!(getGoalsUserData.getGoal_cat_lev3_id().equals("0"))) {
                                            mGoalCategoriesLevelThreeSpinner.setSelection(getSelectedSpinnerposition(getGoalsUserData.getGoal_cat_lev3_id(), goalCategoriesLevelThreeIdsList));
                                        }
                                        }
                                    }
                                }
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }else{
                            goal_cat_lev3_id = "0";
                            mGoalOtherCategoriesLayout.setVisibility(View.GONE);
                            mGoalCategoriesLevelThreeLayout.setVisibility(View.GONE);
                        }
                        }
                    }
//                    if(level_two_flag) {
//                        if (UtileKit.validateObjectValues(getGoalsUserData.getGoal_cat_lev2_id())) {
//                            level_two = Integer.valueOf(getGoalsUserData.getGoal_cat_lev2_id().toString());
//                            if (level_two > 0) {
//                                mGoalCategoriesLevelTwoSpinner.setSelection(level_two);
//                                level_two = 0;
//                                level_two_flag = false;
//                            }
//                        }
//                    }

                break;
            case R.id.goal_category_levelthree_spinner:
                //goal_cat_lev3_id = goalCategorieLevelThreeListonSelect.get(position).getId();
                //goal_cat_lev3_id
                try{
                String spinnerValueLevel3= mGoalCategoriesLevelThreeSpinner.getSelectedItem().toString();


                    if(position!=0){
                        if(!isFirstTimeColor&&isClicked) {
                            mGoalCategoriesLevelThreeSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                            isClicked = false;
                        }
                    }


                    for(int v=0; v<goalCategorieLevelThreeListonSelect.size(); v++){
                    String tempCheck = goalCategorieLevelThreeListonSelect.get(v).getLev3_name();
                    if(spinnerValueLevel3.equalsIgnoreCase(tempCheck)) {
                        goal_cat_lev3_id =goalCategorieLevelThreeListonSelect.get(v).getId();
                        break;
                    }
                }
                }catch (Exception e){
                    e.printStackTrace();
                }

//                if(level_three_flag) {
//                    if (UtileKit.validateObjectValues(getGoalsUserData.getGoal_cat_lev2_id())) {
//                        level_two = Integer.valueOf(getGoalsUserData.getGoal_cat_lev2_id().toString());
//                        if (level_two > 0) {
//                            mGoalCategoriesLevelTwoSpinner.setSelection(level_two);
//                            level_two = 0;
//                            level_three_flag = false;
//                        }
//                    }
//                }
                break;
            case R.id.goal_recurrence_months:
                recur_months = mGoalRecurrenceMonths.getSelectedItem().toString();

                //Log.e("recur_months",mGoalRecurrenceMonths.getSelectedItem().toString());
                break;
            case R.id.goal_recurrence_year:
                recur_years = mGoalRecurrenceyear.getSelectedItem().toString();


            break;
            case R.id.goal_detail_flexibility:
                goal_flexibility = mGoalCategorieFlexibilitySpinner.getSelectedItem().toString();


                if(position!=0){
                    if(!isFirstTimeColor&&isClicked) {
                        mGoalCategorieFlexibilitySpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }


                break;
            default:

        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void mysetChecked(RadioButton myYesRadioBtn, RadioButton myNoRadioBtn, Context mContext) {
        if (myYesRadioBtn.isChecked()) {
            UtileKit.getSwitchYesBtnView(myYesRadioBtn, myNoRadioBtn, mContext);
        } else {
            UtileKit.getSwitchNoBtnView(myYesRadioBtn, myNoRadioBtn, mContext);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == CalculatorAct.REQUEST_RESULT_SUCCESSFUL) {
            String result = data.getStringExtra(CalculatorAct.RESULT);
            costToGoalStandaloneEdit.setText(result);
            costToGoalStandaloneEdit.getEditText().setBackgroundResource(R.drawable.edittextbackgrounggreen);
        }
    }



    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == mgoalRecurrenceNoRadioBtn.getId()) {
            UtileKit.getSwitchYesBtnView(mgoalRecurrenceNoRadioBtn, mgoalRecurrenceYesRadioBtn, activity);
           // mGoalMonthYearLayout.setVisibility(View.GONE);
           // mGoalFreqLayout.setVisibility(View.GONE);
            //mgoalFrequencyTextLayout.setVisibility(View.GONE);
           // mgoalNoRecuranceTextLayout.setVisibility(View.GONE);
            mgoalintervel_linearLayout.setVisibility(View.GONE);
            mgoal_frequency_layout.setVisibility(View.GONE);
        } else if (checkedId == mgoalRecurrenceYesRadioBtn.getId()) {
            UtileKit.getSwitchNoBtnView(mgoalRecurrenceNoRadioBtn, mgoalRecurrenceYesRadioBtn, activity);
           // mGoalMonthYearLayout.setVisibility(View.GONE);
            //mGoalFreqLayout.setVisibility(View.VISIBLE);
           // mgoalFrequencyTextLayout.setVisibility(View.VISIBLE);
           // mGoalFreqLayout.setVisibility(View.VISIBLE);
          //  mgoalNoRecuranceTextLayout.setVisibility(View.VISIBLE);
            mgoalintervel_linearLayout.setVisibility(View.VISIBLE);
            mgoal_frequency_layout.setVisibility(View.VISIBLE);
        }
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

                    if (event.getRawX() >= (EdtText.getRight() - EdtText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        String incrementValue = EdtText.getText().toString();
                        int checkMaxValue = Integer.valueOf(incrementValue);
                        int checkMaxSeekValue = Integer.valueOf(mcostToGoalMaxRupees);
                        //if(checkMaxValue<=checkMaxSeekValue) {
                            int i = Integer.valueOf(incrementValue);
                            i = i + 1000;
                            String increment = String.valueOf(i);
                          //  //Log.e("increment", "" + increment);
                            EdtText.setText(increment);
                        //}
                        return true;
                    }

                    if(event.getX() <= (EdtText.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                        String decrementValue = EdtText.getText().toString();
                        int checkMinValue = Integer.valueOf(decrementValue);
                        // if(checkMinValue>0) {
                        int i = Integer.valueOf(decrementValue);
                        i = i - 1000;
                        String decrement = String.valueOf(i);
                        ////Log.e("decrement", "" + decrement);
                        EdtText.setText(decrement);
                        //}
                        return true;
                    }


//                    if(event.getRawX() <= (EdtText.getLeft() - EdtText.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
//                        // your action here
//                         String decrementValue = EdtText.getText().toString();
//                         int checkMinValue = Integer.valueOf(decrementValue);
//                       // if(checkMinValue>0) {
//                            int i = Integer.valueOf(decrementValue);
//                            i = i - 1000;
//                            String decrement = String.valueOf(i);
//                            ////Log.e("decrement", "" + decrement);
//                            EdtText.setText(decrement);
//                        //}
//                        return true;
//                    }

                }
                return false;
            }
        });
    }

    public void editTextCurrencyDrawableClick(final CurrencyEditText EdtText) {

        EdtText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (EdtText.getRight() - EdtText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        String incrementValue = getStringwithoutCurreny(EdtText);

                        int checkMaxValue = Integer.valueOf(incrementValue);
                        int checkMaxSeekValue = Integer.valueOf(mcostToGoalMaxRupees);

                       /// if(checkMaxValue<=checkMaxSeekValue) {
                            int i = Integer.valueOf(incrementValue);
                            i = i + 100;
                            String increment = String.valueOf(i);
                            //Log.e("increment", "" + increment);
                            EdtText.setText(increment);
                        //}
                        return true;
                    }else if(event.getRawX() <= (EdtText.getLeft() - EdtText.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                        // your action here
                        String decrementValue = getStringwithoutCurreny(EdtText);
                        int checkMinValue = Integer.valueOf(decrementValue);
                      ///  if(checkMinValue>0) {
                            int i = Integer.valueOf(decrementValue);
                            i = i - 100;
                            String decrement = String.valueOf(i);
                            //Log.e("decrement", "" + decrement);
                            EdtText.setText(decrement);
                        //}
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void editTextYearDrawableClick(final EditText EdtText) {

        EdtText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {

                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        int rightBorder=EdtText.getRight() - EdtText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                        int leftBorder=EdtText.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width();
                       // if (event.getRawX() >= (EdtText.getRight() - EdtText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if( ((int)event.getX() >= (rightBorder))||((int)event.getX() >= (rightBorder)-(int)(.20*(EdtText.getRight()))))
                        {
                            // your action here
                            String incrementValue = EdtText.getText().toString();
                            int checkMaxValue = Integer.valueOf(incrementValue);
                            int checkMaxSeekValue = Integer.valueOf(mcostToGoalMaxRupees);

                            //if(checkMaxValue<=checkMaxSeekValue) {
                            int i = Integer.valueOf(incrementValue);
                            i = i + 1;
                            String increment = String.valueOf(i);
                            ////Log.e("increment", "" + increment);
                            EdtText.setText(increment);
                            ///}
                            return true;
                        } else if((int)event.getX() <= (leftBorder+(int)(leftBorder*.50))){
                        //else if(event.getRawX() <= (EdtText.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())){
                        //else if (event.getX() >= (EdtText.getLeft() - EdtText.getRight() - EdtText.getCompoundDrawables()[0].getBounds().width())) {
                            // your action here
                            String decrementValue = EdtText.getText().toString();
                            int checkMinValue = Integer.valueOf(decrementValue);
                            //if(checkMinValue>0) {
                            int i = Integer.valueOf(decrementValue);
                            i = i - 1;
                            String decrement = String.valueOf(i);
                            ////Log.e("decrement", "" + decrement);
                            EdtText.setText(decrement);
                            //}
                            return true;
                        }
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    public void seekBarChange(SeekBar mgoalCostToGoalSeekBar){
        mgoalCostToGoalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                switch(seekBar.getId()) {
                    case R.id.yearstogoal_seekbar:
                        mYearsToGoalEdt.setText(""+progress);
                    break;
                    case R.id.costtogoal_seekbar:
                        int step=100;
                        progress= Math.round(progress/step) *100;
                        mgoalCostToGoalEdt.setText(""+progress);
                    break;
                    case R.id.goal_duration_seekbar:
                        mGoalDurationEdt.setText(""+progress);
                        break;
                   /* case R.id.goal_frequency_seekbar:
                        mGoalFrequencyEdt.setText(""+progress);
                        break;*/

                }
            }
        });
    }

    private void setGoalDetailsForUpdate(GetGoalsUserData getGoalsUserData){

        if(UtileKit.validateObjectValues(getGoalsUserData.getEmpty_flds()))
            emptyErrorValidation(getGoalsUserData.getEmpty_flds());

        if(UtileKit.validateObjectValues(getGoalsUserData.getGoal_interval())){
            mgoal_intervelEdt.setText(getGoalsUserData.getGoal_interval().toString());
        }

        if(UtileKit.validateObjectValues(getGoalsUserData.getGoal_name())) {
            mgoalNameEdt.append(getGoalsUserData.getGoal_name().toString());
        }
        else {
           // mgoalNameEdt.setHintTextEmptyError();
        }
        if(UtileKit.validateObjectValues(getGoalsUserData.getExpected_increment())) {
            mgoalExpectedIncr.setText(getGoalsUserData.getExpected_increment().toString());
        }
        else{
            //mgoalExpectedIncr.setHintTextRequestFillError();
        }

        //spinner
      if(UtileKit.validateObjectValues(this.getGoalsUserData.getBelongs_to_id())) {
            int belongsto_pos = getBelongToIndex(this.getGoalsUserData.getBelongs_to_id());
          mgoalBelongsToSpinner.setSelection(belongsto_pos+1);
        }
        //spinner

        //spinner
        if(UtileKit.validateObjectValues(this.getGoalsUserData.getGoal_imp())) {
            int goal_imp_pos = getImpToIndex(this.getGoalsUserData.getGoal_imp(),  mgoalDetailImportanceStrArray);
            mgoalDetailImportanceSpinner.setSelection(goal_imp_pos+1);
        }

        //spinner
        if(UtileKit.validateObjectValues(this.getGoalsUserData.getGoal_priority())) {
            int goal_imp_priority = getImpToIndex(this.getGoalsUserData.getGoal_priority(),  mgoalDetailPriorityStrArray);
            mgoalDetailPrioritySpinner.setSelection(goal_imp_priority+1);
        }

        //spinner
        if(UtileKit.validateObjectValues(this.getGoalsUserData.getGoal_flexibility())) {
            int goal_flexi = getImpToIndex(this.getGoalsUserData.getGoal_flexibility(), mGoalFlexibilityStrArray);
            mGoalCategorieFlexibilitySpinner.setSelection(goal_flexi+1);
        }

        if(UtileKit.validateObjectValues(this.getGoalsUserData.getOther_category())) {
            Log.i("spcheck", "setGoalDetailsForUpdate: called ");
            mOtherCategoriesEdt.setText(this.getGoalsUserData.getOther_category());
        }else{

            mGoalOtherCategoriesLayout.setVisibility(View.GONE);
        }


        if(UtileKit.validateObjectValues(this.getGoalsUserData.getNotes())) {
            mgoalNotesEdt.setText(this.getGoalsUserData.getNotes().toString());
        }
        else{
           // mgoalNotesEdt.setHintTextEmptyError();
        }


        if(UtileKit.validateObjectValues(getGoalsUserData.getCost_of_goal())) {
            costToGoalStandaloneEdit.setText(getGoalsUserData.getCost_of_goal().toString());
            //mgoalCostToGoalEdt.setText(getGoalsUserData.getCost_of_goal().toString());
            //Log.e("Seekbar GOAL DATA1", "" + getGoalsUserData.getCost_of_goal().toString());
            try {
                int getCost_of_goal = Integer.valueOf((getGoalsUserData.getCost_of_goal()).replace(",", ""));
                //Log.e("Seekbar GOAL DATA1 INT", "" + getCost_of_goal);
                mgoalCostToGoalSeekBar.setProgress(getCost_of_goal);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
           // costToGoalStandaloneEdit.setHintTextEmptyError();
        }

        if(UtileKit.validateObjectValues(this.getGoalsUserData.getGoal_years())) {
            mYearsToGoalEdt.setText(this.getGoalsUserData.getGoal_years().toString());
            int getGoal_years = Integer.valueOf(this.getGoalsUserData.getGoal_years());
            mYearsToGoalSeekbar.setProgress(getGoal_years);
        }


        if(UtileKit.validateObjectValues(this.getGoalsUserData.getGoal_duration())) {
            mGoalDurationEdt.setText(this.getGoalsUserData.getGoal_duration().toString());
            int getGoal_duration = Integer.valueOf(this.getGoalsUserData.getGoal_duration());
            mGoalDurationSeekbar.setProgress(getGoal_duration);

        }

        if(UtileKit.validateObjectValues(this.getGoalsUserData.getGoal_recurrence())) {
            String gender = this.getGoalsUserData.getGoal_recurrence();
            if (gender.equalsIgnoreCase("N")) {
                goal_recurrence = "N";
                UtileKit.getSwitchYesBtnView(mgoalRecurrenceNoRadioBtn, mgoalRecurrenceYesRadioBtn, activity);
                mgoalintervel_linearLayout.setVisibility(View.GONE);
                mgoal_frequency_layout.setVisibility(View.GONE);
              //  mGoalFreqLayout.setVisibility(View.GONE);
              //  mGoalMonthYearLayout.setVisibility(View.GONE);
            } else {
                goal_recurrence = "Y";
                UtileKit.getSwitchNoBtnView(mgoalRecurrenceNoRadioBtn, mgoalRecurrenceYesRadioBtn, activity);
                mgoalintervel_linearLayout.setVisibility(View.VISIBLE);
                mgoal_frequency_layout.setVisibility(View.VISIBLE);
               // mgoalFrequencyTextLayout.setVisibility(View.VISIBLE);
               // mGoalFreqLayout.setVisibility(View.VISIBLE);

                //mGoalMonthYearLayout.setVisibility(View.VISIBLE);
                /*if(UtileKit.validateObjectValues(getGoalsUserData.getRecur_years())) {
                    int goal_recurrence_year = getImpToIndex(getGoalsUserData.getRecur_years(), mgoalRecurrenceYearStrArray);
                    mGoalRecurrenceyear.setSelection(goal_recurrence_year+1);
                }
                if(UtileKit.validateObjectValues(getGoalsUserData.getRecur_months())) {
                    int goal_recurrence_month = getImpToIndex(getGoalsUserData.getRecur_months(), mgoalRecurrenceMonthArray);
                    mGoalRecurrenceMonths.setSelection(goal_recurrence_month+1);
                }*/
            }
        }
//        if(UtileKit.validateObjectValues(getGoalsUserData.get)) {
//            mGoalDurationEdt.setText(mGoalDurationMaxValue);
//
//        }

        if(UtileKit.validateObjectValues(this.getGoalsUserData.getGoal_frequency())) {
            if(this.getGoalsUserData.getGoal_recurrence().equalsIgnoreCase("Y")) {
              //  mGoalFrequencyEdt.setText(this.getGoalsUserData.getGoal_frequency().toString());
                mgoal_frequency_edts.setText(this.getGoalsUserData.getGoal_frequency().toString());
                int getGoal_frequency = Integer.valueOf(this.getGoalsUserData.getGoal_frequency());
                mGoalFrequencySeekbar.setProgress(getGoal_frequency);
            }
        }}

    private int getIndex(String myString) {
        int index = 0;
        for (int i = 0; i < mgoalYearsToGoalArray.length; i++) {
            String dialCodeValue = mgoalYearsToGoalArray[i];
            if (dialCodeValue.equals(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private int getSelectedSpinnerposition(String value, ArrayList<String> spinerlist){
        int pos=0;
        for(int i=0; i<spinerlist.size(); i++){
            if(value.equalsIgnoreCase(spinerlist.get(i))) {
                pos=i;
            }
        }
        return pos+1;
    }

    private int getBelongToIndex(String myString) {
        int index = 0;
        Log.i("spcheck","mgoalBelongsToArrayListId.size="+mgoalBelongsToArrayListId.size());
        for (int i = 0; i < mgoalBelongsToArrayListId.size(); i++) {
            Log.i("spcheck","mgoalBelongsToArrayList content="+mgoalBelongsToArrayListId.get(i));
            String dialCodeValue = mgoalBelongsToArrayListId.get(i);
            if (dialCodeValue.equals(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private int getImpToIndex(String myString, String[] mStrArray) {
        int index = 0;
        for (int i = 0; i < mStrArray.length; i++) {
            String dialCodeValue = mStrArray[i];
            if (dialCodeValue.equals(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }

      //  getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public String getStringwithoutCurreny(CurrencyEditText edt){
        String str =  edt.getText().toString().replace("₹","").trim();
        return  str;
    }

    public void setSpinnerAdapter(Spinner mMyMartialSpinner, ArrayList<String> mystringList, Context mycontext) {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("");
        for (String s : mystringList) {
            stringList.add(s);
        }
        adapter_state = new CustomSpinerAdapter(mycontext, stringList);
        mMyMartialSpinner.setAdapter(adapter_state);
        mMyMartialSpinner.setGravity(Gravity.BOTTOM);
    }

    public void setStringArraySpinnerAdapter(Spinner mMyMartialSpinner, String[] myStringArray, Context mycontext) {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("");
        for (String s : myStringArray) {
            stringList.add(s);
        }
        CustomSpinerAdapter adapter_state = new CustomSpinerAdapter(mycontext, stringList);
        mMyMartialSpinner.setAdapter(adapter_state);
        mMyMartialSpinner.setOnItemSelectedListener(this);
        mMyMartialSpinner.setGravity(Gravity.BOTTOM);
    }

    public void setSpinnerLevelAdapter(Spinner mMyMartialSpinner, ArrayList<String> mystringList, Context mycontext) {
        CustomSpinerAdapter adapter_state = new CustomSpinerAdapter(mycontext, mystringList);
        mMyMartialSpinner.setAdapter(adapter_state);
        mMyMartialSpinner.setGravity(Gravity.BOTTOM);
    }


    private int getBelongsSpinnerposition(String value, ArrayList<String> spinerlistId, ArrayList<String> spinerlistName){
        int pos=0;
        for(int i=0; i<spinerlistName.size(); i++){
            if(value.equalsIgnoreCase(spinerlistName.get(i))) {
                pos= Integer.valueOf(spinerlistId.get(i));
            }
        }
        return pos;
    }

    private void intitializeAlertDialog() {
        inflater= LayoutInflater.from(getContext());
        dialogView=inflater.inflate(R.layout.yes_no_dialog,null);
        TextView textView= dialogView.findViewById(R.id.textViewDilog);
        textView.setText("Please enter Years to Goal");
        ((Button)dialogView.findViewById(R.id.no)).setText("Cancel");
        ((Button)dialogView.findViewById(R.id.yes)).setText("Ok");
        alertDialog=new AlertDialog.Builder(getContext()).create();
        alertDialog.setView(dialogView);
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();
               // finish();
                mYearsToGoalEdt.requestFocus();
                mYearsToGoalEdt.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.showSoftInput(mYearsToGoalEdt, InputMethodManager.SHOW_IMPLICIT);
                    }
                }, 1000);
                alertDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                dismiss();
            }
        });


    }

}