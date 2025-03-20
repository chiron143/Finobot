package com.purplepath.purplepath.fragments;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.fourmob.datetimepicker.Utils;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.YearPickerDialog;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assets.dialog.AssetDatePickerDialogFragment;
import com.purplepath.purplepath.calenderNumberPicker.DialogFragmentCallbackInterface;
import com.purplepath.purplepath.calenderNumberPicker.calenderUi.CalenderTabs;
import com.purplepath.purplepath.customview.CalendarEditText;
import com.purplepath.purplepath.customview.CharacterEditText;
import com.purplepath.purplepath.customview.CharacterWrapTextView;
import com.purplepath.purplepath.customview.CustomSpinerAdapter;
import com.purplepath.purplepath.customview.CustomTextInputLayout;
import com.purplepath.purplepath.customview.EditTextValidator;
import com.purplepath.purplepath.customview.NumberEditText;
import com.purplepath.purplepath.dialog.HomeAddressDialogFragmentNew;
import com.purplepath.purplepath.famlydetail.fragmentview.FamilyDetailFragment;
import com.purplepath.purplepath.model.AddPersonalDetailsModel;
import com.purplepath.purplepath.model.personnalmodel.GetPersonnalDetailsModel;
import com.purplepath.purplepath.model.personnalmodel.User_per_det;
import com.purplepath.purplepath.myinterface.DatePickerCallBackInterface;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PersonalDetailsFragment extends BaseFragment implements
        RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener, View.OnClickListener,
        YearPickerDialog.OnYearSetListener, DatePickerDialog.OnDateSetListener, HomeAddressDialogFragmentNew.OnHomeAddressSetListener
        , DialogFragmentCallbackInterface ,DatePickerCallBackInterface{
    private DialogFragmentCallbackInterface callbackInterface;
    private Switch mySwitch;
    private RadioGroup otherQualifiRadioGroupe = null, mgenderRadioGroupe = null,
            mresidential_status_RadioGroup=null,mcitizenship_status_RadioGroup=null,mgovernment_sector_RadioGroup=null;
    private RadioButton mgenderMaleRadioBtn, mgenderFemaleRadioBtn,
            mresidential_status_yes_RadioBtn,mresidential_status_no_RadioBtn,
            mcitizenship_status_yes_RadioBtn,mcitizenship_status_no_RadioBtn,
            mgovernment_sector_yes_RadioBtn,mgovernment_sector_no_RadioBtn;
    private Context mContext;
    private AppCompatEditText mOtherQualificationEdt, mOtherOccupationEdt, mplannedRetirmentAgeEdt;
    private CharacterEditText mCurrentOrganizationEdt,mPersonalDesignationEdt,mHomeAddressEdt,
    mWorkAddressEdt;
    private CalendarEditText mBornYearEdt;
    private CheckBox copy_homeaddress;
    private TextInputLayout mHomeAddressInputLAyoutEdt,currentOrganizationInputLayout,yearstayedInputLayout,
            workAddressInputLayout,designationInputLayout,retirementAgeInputLayout,expectedAgeInputLayout;
    private Spinner mMartialStatusSpinner, mMarriedSinceSpinner, mMarriedDataSpinner,
            mQualificationSpinnner, mOccupationSpinner, mStayedinCurrentAddressSpinner,
            mStayedinCurrentOrganizationSpinner;
    private String[] mpersonalMaritialStatusArray, mPersonalQualificationArray,
            mPersonalOccupationArray, mPersonalAge, mPersonalCourseArray, mPersonalYearCourseArray;
    private OnActivityBackPressedListener mCallBackListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout
            ,relative_finish_later,relative_done_arrow;
    private Button mpersonalDetailSave;
    public  String mUserId, mPid, mName="", mDateOfBirth="", mAge="", mCountrycode="",mAlias_name="",
            mMobileno="", mEmailId="", mGender="", mMartialStatus="", mEducation="", mFinalEducation="", mOccupation="", mCurrentDesignation="",
            mCurrentOrg="", mAddressHome="", mAddressWork="", mNoofAverageYears="", mNoofWorkYears="", mLifeExpectancyAge="",
            mPlannedRetirementAge="", mMarriageDate="", mMarriedSince="", mCourseYear="",mMiddlename="",mLastname="",mNoofYearCourse,
            mResident_status="",mCitizenship_status="",mGovernment_sector="";

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private YearPickerDialog yearPickerDialog;
    private DatePicker mdatepicker;

    private CalendarEditText mMaritalSinceEdt, mMarriageDateEdt, mStayedCurrentResidenceEdt,
            mYearServiceCurrOrganizationEdt;
    private ImageView mPersonalImageViewCalendar, mPersonalMarriedSinceCalendar, mPersonalMarriageDateCalendar, mPersonalStayedCurrentResidenceCalendar, mPersonalYearServiceCurrOrganzationCalendar;

    private EditText mCalanderEdt;
    private CharacterWrapTextView mCalanderEdt1;
    private NumberEditText mExpectedLifeEdt, mRetirementAgeEdt;
    private RelativeLayout mMarriedDateRelativeLayout, mQualificationRelativeLayout, mOccupationRelativeLayout;
    private Spinner mPersonalCourseSelectedSpinner, mPersonalCourseNoofYearSelectedSpinner;
    private Boolean isfirstrun =false, isCheckEducaion = false;
    private Toolbar toolbar;

    private GetPersonnalDetailsModel getPersonnalDetailsModel;
    ArrayList<User_per_det> user_per_det;
    private String personnalID;
    String course, courseYear;
    private String getDobDate, getMarriageDate;
    private ImageView mbackButtonId;
    public  String ah_city="", ah_state="", ah_country="", ah_zipcode="", aw_city="", aw_state="", aw_country="", aw_zipcode="";

    public  String ah_resno="", ah_resname="", ah_street="", ah_area="", aw_resno="", aw_resname="", aw_street="", aw_area="";

    public  int instance_Month;
    public int instance_date;
    public int instance_year;
    public static  boolean popupIsShow = false;
    private DialogFragment newFragment;
    Boolean isSignUp = false,mIsFistRun=false;
    private List<String> qualificationList;
    boolean isFistRun=false;
    private Bundle args=new Bundle();
    public static final String date_of_birth = "Date Of Birth";
    public static final String your_age = "Your Age";
    public static final String life_of_expectancy_age = "Life Expectancy Age";
    public static final String expectancy_age = "Expectancy in Age";
    public static final String planned_retirement_age = "Planned Retirement Age";
    public static final String years_to_retirement = "Years to Retirement";
    public static final String current_organization = "Current Organization";
    private CharacterEditText mFirstname_Edt,mMiddlename_Edt,mLastname_Edt,maliasname_Edt;

    private int currentdateFirsttime,currentmonthFirsttime,currentyearFirsttime;
    public HashMap<String,View> errorMapView=new HashMap<>();
    private  boolean isFirstTimeColor = false;
    private boolean isClicked;
    private LinearLayout bottom_bar_layout,bottom_bar_donelayout;

    public HashMap<String,View> mandatoryMapView=new HashMap<>();
    private LinearLayout name_layout,alias_layout,bornyear_layout,mlayout_gender,layout_matitalstatus,
            layout_current_occupation,layout_currentOrganization,layout_designation,layout_homeAddress,
            layout_workAddress,layout_retirementAge,layout_expectedAge;
    private RelativeLayout personal_qualification_relativielayout,currentResidenceRelativeLayout,yearstayedRelativeLayout;

    ArrayList<String> formArray = new ArrayList<String>();
    private TextInputLayout bornyearInputLayout;
    private boolean isMandatory;


    HashMap<String, String> mandatoryPromptMapView = new HashMap<String, String>() {{
        put("Name","Enter your full name");
        put("Born Year","Enter your date of birth from Calendar");
        put("Gender","Select your Gender");
        put("Relationship","Select your Relationship");
        put("Qualification","Select your Qualification");
        put("Current Occupation","Enter your current occupation");
        put("Life Expectancy Age","Enter your Life expectacy age as you assumed");
        put("Planned Retirement Age","Enter the retirement age which you have planned");
    }};
    public HashMap<String,View> mandatoryPromptPut=new HashMap<>();

       public static PersonalDetailsFragment newInstance() {
        PersonalDetailsFragment personal = new PersonalDetailsFragment();

        return personal;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mContext = getContext();
        callbackInterface = this;


        try {
            if (getArguments() != null) {
                if (getArguments().containsKey("IsSignUp"))
                    isSignUp = getArguments().getBoolean("IsSignUp");
            }


            setHasOptionsMenu(true);
            if(getPersonnalDetailsModel!=null){
                mAddressHome= " ";ah_city= " "; ah_state =" "; ah_country= " "; ah_zipcode =" ";
                mAddressWork= " ";aw_city= " "; aw_state =" "; aw_country= " "; aw_zipcode =" ";

            }
            mCallBackListener = (OnActivityBackPressedListener) (mContext);

        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!isSignUp) {
            callGetPersonalDetailsService();
        }

        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View personalDetailsView = inflater.inflate(R.layout.fragment_personal_details, container, false);

        return personalDetailsView;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isSignUp) {
            toolbar = getActivity().findViewById(R.id.toolbar);
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        try {
            mCallBackListener.setActionBarTitle("Personal Details");
        } catch (Exception e) {
            e.printStackTrace();
        }
        name_layout= view.findViewById(R.id.name_layout);
        mandatoryMapView.put("Name",name_layout);
        alias_layout= view.findViewById(R.id.alias_layout);
        mandatoryMapView.put("alias_name",alias_layout);
        bornyear_layout= view.findViewById(R.id.bornyear_layout);
        mandatoryMapView.put("Born Year",bornyear_layout);
        mlayout_gender= view.findViewById(R.id.layout_gender);
        mandatoryMapView.put("Gender",mlayout_gender);
        layout_matitalstatus= view.findViewById(R.id.layout_matitalstatus);
        mandatoryMapView.put("martial_status",layout_matitalstatus);
        layout_current_occupation= view.findViewById(R.id.layout_current_occupation);
        mandatoryMapView.put("Current Occupation",layout_current_occupation);
        personal_qualification_relativielayout= view.findViewById(R.id.personal_qualification_relativielayout);
        mandatoryMapView.put("Qualification",personal_qualification_relativielayout);
        layout_currentOrganization= view.findViewById(R.id.layout_currentOrganization);
        mandatoryMapView.put("current_org",layout_currentOrganization);
        layout_designation= view.findViewById(R.id.layout_designation);
        mandatoryMapView.put("current_designation",layout_designation);
        layout_homeAddress= view.findViewById(R.id.layout_homeAddress);
        mandatoryMapView.put("address_home",layout_homeAddress);
        layout_workAddress= view.findViewById(R.id.layout_workAddress);
        mandatoryMapView.put("address_work",layout_workAddress);
        currentResidenceRelativeLayout = view.findViewById(R.id.currentResidenceRelativeLayout);
        mandatoryMapView.put("no_of_ava_years",currentResidenceRelativeLayout);
        yearstayedRelativeLayout = view.findViewById(R.id.yearstayedRelativeLayout);
        mandatoryMapView.put("no_of_work_years",yearstayedRelativeLayout);
        layout_retirementAge= view.findViewById(R.id.layout_retirementAge);
        mandatoryMapView.put("Planned Retirement Age",layout_retirementAge);
        layout_expectedAge= view.findViewById(R.id.layout_expectedAge);
        mandatoryMapView.put("Life Expectancy Age",layout_expectedAge);


        bottom_bar_layout= view.findViewById(R.id.bottom_bar_layout);
        UtileKit.mandatoryFieldLinearLayout(isSignUp,bottom_bar_layout);

        bottom_bar_donelayout= view.findViewById(R.id.bottom_bar_donelayout);
        UtileKit.mandatoryFieldDoneLayout(isSignUp,bottom_bar_donelayout);


        yearstayedInputLayout= view.findViewById(R.id.yearstayedInputLayout);
        workAddressInputLayout = view.findViewById(R.id.workAddressInputLayout);
        designationInputLayout = view.findViewById(R.id.designationInputLayout);
        currentOrganizationInputLayout = view.findViewById(R.id.currentOrganizationInputLayout);
        copy_homeaddress = view.findViewById(R.id.copy_homeaddress);
        expectedAgeInputLayout = view.findViewById(R.id.expectedAgeInputLayout);
        retirementAgeInputLayout  = view.findViewById(R.id.retirementAgeInputLayout);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        relative_finish_later= view.findViewById(R.id.relative_finish_later);
        relative_done_arrow= view.findViewById(R.id.relative_done_arrow);

        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        relative_finish_later.setOnClickListener(this);
        relative_done_arrow.setOnClickListener(this);
        mMarriedDateRelativeLayout = view.findViewById(R.id.personal_marrieddate_relativielayout);
        mQualificationRelativeLayout = view.findViewById(R.id.personal_other_qualifica_relativielayout);
        mOccupationRelativeLayout = view.findViewById(R.id.personal_other_occupation_relativielayout);

        maliasname_Edt= view.findViewById(R.id.aliasname_Edt);
        maliasname_Edt.setHintText(getString(R.string.hint_personal_alias_name), ((TextInputLayout) (maliasname_Edt.getParent()).getParent()));
        UtileKit.nameCharacterOnly(maliasname_Edt);
        mFirstname_Edt= view.findViewById(R.id.firstname_Edt);




        mFirstname_Edt.setHintText(getString(R.string.hint_personal_first_name), ((TextInputLayout) (mFirstname_Edt.getParent()).getParent()));
        errorMapView.put("Name",mFirstname_Edt);
        mandatoryPromptPut.put("Name",mFirstname_Edt);
        UtileKit.nameCharacterOnly(mFirstname_Edt);

        mMiddlename_Edt= view.findViewById(R.id.middlename_Edt);
        errorMapView.put("middle_name",mMiddlename_Edt);
        UtileKit.nameCharacterOnly(mMiddlename_Edt);

        mLastname_Edt= view.findViewById(R.id.lastname_Edt);
        errorMapView.put("last_name",mLastname_Edt);
        UtileKit.nameCharacterOnly(mLastname_Edt);

        mBornYearEdt = view.findViewById(R.id.personal_details_bornyear_Edt);
        bornyearInputLayout= view.findViewById(R.id.bornyearInputLayout);
        mBornYearEdt.setHintText(getString(R.string.hint_personal_born_year), ((TextInputLayout) (mBornYearEdt.getParent()).getParent()));
        errorMapView.put("Born Year",mBornYearEdt);
        mandatoryPromptPut.put("Born Year",mBornYearEdt);

        mBornYearEdt.addTextChangedListener(new EditTextValidator(mBornYearEdt));
        editTextDrawableClick(mBornYearEdt);
        mMarriageDateEdt = view.findViewById(R.id.personal_details_marital_date_Edt);
        errorMapView.put("marriage_date",mMarriageDateEdt);
        editTextDrawableClick(mMarriageDateEdt);
        mStayedCurrentResidenceEdt = view.findViewById(R.id.personal_year_stayed_in_currentResidence_Edt);
        mStayedCurrentResidenceEdt.setHintText("Select the years stayed at current residence from the calendar",
                ((TextInputLayout) (mStayedCurrentResidenceEdt.getParent()).getParent()));


        errorMapView.put("no_of_ava_years",mStayedCurrentResidenceEdt);
        editTextDrawableClick(mStayedCurrentResidenceEdt);
        mYearServiceCurrOrganizationEdt = view.findViewById(R.id.personal_year_stayed_in_currentOrganization_Edt);
        mYearServiceCurrOrganizationEdt.setHintText("Select the years stayed at current organization from the calendar",
                ((TextInputLayout) (mYearServiceCurrOrganizationEdt.getParent()).getParent()));

        errorMapView.put("no_of_work_years",mYearServiceCurrOrganizationEdt);
        editTextDrawableClick(mYearServiceCurrOrganizationEdt);

        mPersonalDesignationEdt = view.findViewById(R.id.personal_details_designation_Edt);
        mPersonalDesignationEdt.setHintText(getString(R.string.hint_personal_designation), ((TextInputLayout)
                (mPersonalDesignationEdt.getParent()).getParent()));

        mMartialStatusSpinner = view.findViewById(R.id.personal_details_marital_status_Spinner);
        errorMapView.put("martial_status",mMartialStatusSpinner);

        mMartialStatusSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });
        mQualificationSpinnner = view.findViewById(R.id.personal_details_qualification_Spinner);
        errorMapView.put("Qualification",mQualificationSpinnner);
        mandatoryPromptPut.put("Qualification",mQualificationSpinnner);

        mQualificationSpinnner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });

        mOccupationSpinner = view.findViewById(R.id.personal_details_occupation_Spinner);
        errorMapView.put("Current Occupation",mOccupationSpinner);
        mandatoryPromptPut.put("Current Occupation",mOccupationSpinner);
        mOccupationSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });
        mPersonalCourseSelectedSpinner = view.findViewById(R.id.personal_course_selected_spinner);
        isFistRun=true;

        mPersonalCourseSelectedSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });
        mPersonalCourseNoofYearSelectedSpinner = view.findViewById(R.id.personal_course_noofYear_selected_spinner);
        mPersonalCourseNoofYearSelectedSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });


        mpersonalMaritialStatusArray = getResources().getStringArray(R.array.maritalstatus);
        setSpinnerAdapter(mMartialStatusSpinner, mpersonalMaritialStatusArray);
        mPersonalQualificationArray = getResources().getStringArray(R.array.education);
        setSpinnerAdapter(mQualificationSpinnner, mPersonalQualificationArray);
        mPersonalYearCourseArray = getResources().getStringArray(R.array.noofyear_course_stage);
        setSpinnerAdapter(mPersonalCourseNoofYearSelectedSpinner, mPersonalYearCourseArray);
        mPersonalOccupationArray = getResources().getStringArray(R.array.occupation);
        setSpinnerAdapter(mOccupationSpinner, mPersonalOccupationArray);

        mPersonalAge = getResources().getStringArray(R.array.retirementage);

        mPersonalCourseArray = getResources().getStringArray(R.array.course_stage);
        setSpinnerAdapter(mPersonalCourseSelectedSpinner, mPersonalCourseArray);

        mExpectedLifeEdt = view.findViewById(R.id.personal_life_expect_age_Spinner);
        mExpectedLifeEdt.setHintText(getString(R.string.hint_personal_life_expec), ((TextInputLayout)
                (mExpectedLifeEdt.getParent()).getParent()));
        errorMapView.put("Life Expectancy Age",mExpectedLifeEdt);
        mandatoryPromptPut.put("Life Expectancy Age",mExpectedLifeEdt);

        mRetirementAgeEdt = view.findViewById(R.id.personal_planned_retirement_age_Spinner);
        mRetirementAgeEdt.setHintText(getString(R.string.hint_personal_plan_retire), ((TextInputLayout)
                (mRetirementAgeEdt.getParent()).getParent()));
        errorMapView.put("Planned Retirement Age",mRetirementAgeEdt);
        mandatoryPromptPut.put("Planned Retirement Age",mRetirementAgeEdt);

        mOtherQualificationEdt = view.findViewById(R.id.personal_details_other_qualifica_Edt);
        mOtherQualificationEdt.setSelection(mOtherQualificationEdt.getText().length());

        mOtherOccupationEdt = view.findViewById(R.id.personal_details_other_occupation_Edt);
        mOtherOccupationEdt.setSelection(mOtherOccupationEdt.getText().length());

        mCurrentOrganizationEdt = view.findViewById(R.id.personal_details_current_organization_Edt);
        mCurrentOrganizationEdt.setHintText(getString(R.string.hint_personal_organization), ((TextInputLayout) (mCurrentOrganizationEdt.getParent()).getParent()));

        errorMapView.put("current_org",mCurrentOrganizationEdt);

        mHomeAddressEdt = view.findViewById(R.id.personal_details_home_address_Edt);
        mHomeAddressEdt.setHintText(getString(R.string.hint_personal_home_address), ((TextInputLayout) (mHomeAddressEdt.getParent()).getParent()));

        mHomeAddressInputLAyoutEdt = view.findViewById(R.id.homeAddressInputLayout);
        errorMapView.put("address_home",mHomeAddressEdt);

        mHomeAddressEdt.setOnClickListener(this);

        mHomeAddressInputLAyoutEdt.setOnClickListener(this);

        callGetPersonalDetailsService();

        mWorkAddressEdt = view.findViewById(R.id.personal_details_work_address_Edt);
        mWorkAddressEdt.setHintText(getString(R.string.hint_personal_work_address), ((TextInputLayout) (mWorkAddressEdt.getParent()).getParent()));

        mWorkAddressEdt.setOnClickListener(this);
        errorMapView.put("address_work",mWorkAddressEdt);

        mgenderRadioGroupe = view.findViewById(R.id.personal_details_gender_RadioRg);
        mgenderMaleRadioBtn = view.findViewById(R.id.personal_details_gender_yes_RadioBtn);
        mgenderFemaleRadioBtn = view.findViewById(R.id.personal_details_gender_no_RadioBtn);
        mpersonalDetailSave = view.findViewById(R.id.personal_details_save);
        mpersonalDetailSave.setOnClickListener(this);
        mgenderRadioGroupe.setOnCheckedChangeListener(this);

        mresidential_status_RadioGroup= view.findViewById(R.id.personal_residential_status_RadioRg);
        mresidential_status_yes_RadioBtn = view.findViewById(R.id.personal_details_residential_status_yes_RadioBtn);
        mresidential_status_no_RadioBtn= view.findViewById(R.id.personal_details_residential_status_no_RadioBtn);
        mresidential_status_RadioGroup.setOnCheckedChangeListener(this);

        mcitizenship_status_RadioGroup= view.findViewById(R.id.personal_citizenship_status_RadioRg);
        mcitizenship_status_yes_RadioBtn = view.findViewById(R.id.personal_details_citizenship_status_yes_RadioBtn);
        mcitizenship_status_no_RadioBtn= view.findViewById(R.id.personal_details_citizenship_status_no_RadioBtn);
        mcitizenship_status_RadioGroup.setOnCheckedChangeListener(this);

        mgovernment_sector_RadioGroup= view.findViewById(R.id.personal_government_sector_RadioRg);
        mgovernment_sector_yes_RadioBtn = view.findViewById(R.id.personal_details_government_sector_yes_RadioBtn);
        mgovernment_sector_no_RadioBtn= view.findViewById(R.id.personal_details_government_sector_no_RadioBtn);
        mgovernment_sector_RadioGroup.setOnCheckedChangeListener(this);

        firstTimegetCurrentDate();
        FloatingActionButton fab = view.findViewById(R.id.personal_details_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromInput();
                callAddPersonalDetailsService( "backbtn");
            }
        });
        GregorianCalendar calendar = new GregorianCalendar();
        instance_date = calendar.get(Calendar.DATE);
        instance_Month = calendar.get(Calendar.MONTH);
        instance_year = calendar.get(Calendar.YEAR);

        mExpectedLifeEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                expectedAgeInputLayout.setErrorEnabled(false);
                expectedAgeInputLayout.setError(null);
                try {
                    if (mRetirementAgeEdt.getText().toString().length() > 0) {
                        int age = Integer.parseInt(mRetirementAgeEdt.getText().toString());
                        if(UtileKit.validateObjectValues(mExpectedLifeEdt.getText().toString())){
                        int lifeExpAge = Integer.parseInt(mExpectedLifeEdt.getText().toString());
                        if (lifeExpAge < age) {
                            expectedAgeInputLayout.setError("Life expectancy should be greater than Retirement age");
                        }}
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if(mRetirementAgeEdt.getText().toString().length()>0) {
                        int age = Integer.parseInt(mRetirementAgeEdt.getText().toString());
                        if(UtileKit.validateObjectValues(mExpectedLifeEdt.getText().toString())){
                        int lifeExpAge = Integer.parseInt(mExpectedLifeEdt.getText().toString());
                        if (lifeExpAge < age) {
                            expectedAgeInputLayout.setError("Life expectancy should be greater than Retirement age");
                        }
                        }
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        mRetirementAgeEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                expectedAgeInputLayout.setErrorEnabled(false);
                expectedAgeInputLayout.setError(null);
                try {
                    if (mRetirementAgeEdt.getText().toString().length() > 0) {
                        int age = Integer.parseInt(mRetirementAgeEdt.getText().toString());
                        if(UtileKit.validateObjectValues(mExpectedLifeEdt.getText().toString())){
                        int lifeExpAge = Integer.parseInt(mExpectedLifeEdt.getText().toString());

                        if (lifeExpAge < age) {
                            expectedAgeInputLayout.setError("Life expectancy should be greater than Retirement age");
                        }}
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if(mRetirementAgeEdt.getText().toString().length()>0) {
                        int age = Integer.parseInt(mRetirementAgeEdt.getText().toString());
                        if(UtileKit.validateObjectValues(mExpectedLifeEdt.getText().toString())){
                        int lifeExpAge = Integer.parseInt(mExpectedLifeEdt.getText().toString());
                        if (lifeExpAge < age) {
                            expectedAgeInputLayout.setError("Life expectancy should be greater than Retirement age");
                        }}
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        copy_homeaddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try{
                    if(copy_homeaddress.isChecked()){

                        aw_city = ah_city;
                        aw_state = ah_state;
                        aw_country = ah_country;
                        aw_zipcode = ah_zipcode;

                        aw_resno=ah_resno;
                        aw_resname=ah_resname;
                        aw_street=ah_street;
                        aw_area=ah_area;
                        if(UtileKit.validateObjectValues(aw_resno)||
                                UtileKit.validateObjectValues(aw_resname)||
                                UtileKit.validateObjectValues(aw_street)||
                                UtileKit.validateObjectValues(aw_area)||
                                UtileKit.validateObjectValues(aw_city)||
                                UtileKit.validateObjectValues(aw_state)||
                                UtileKit.validateObjectValues(aw_country)||
                                UtileKit.validateObjectValues(aw_zipcode)){
                            mAddressWork = mHomeAddressEdt.getText().toString();
                            mWorkAddressEdt.setText(mAddressWork);
                            popupIsShow = true;
                            mAddressWork = mAddressHome;


                        }else{
                            Toast.makeText(mContext, "Please enter your Home Address", Toast.LENGTH_LONG).show();
                        }
                    }else{
                       /* mAddressWork = "";
                        aw_city = "";
                        aw_state = "";
                        aw_country = "";
                        aw_zipcode = "";

                        aw_resno="";
                        aw_resname="";
                        aw_street="";
                        aw_area="";*/
                        mWorkAddressEdt.setText("");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        if(isSignUp==true) {
            for (String key : mandatoryMapView.keySet()) {
                mandatoryMapView.get(key).setVisibility(View.GONE);
            }
        }

        if (getArguments() != null) {
            if (getArguments().containsKey("form_array")) {
                formArray = (ArrayList<String>) getArguments().getSerializable("form_array");
                emptyErrorValidationMandatory(formArray);
            }
        }

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_empty_items, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.ic_done_btn:
                try{
                    mCallBackListener.onActivityBackPressed();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (isSignUp) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        calendar = new GregorianCalendar();
        yearPickerDialog = YearPickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), isVibrate());



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


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == mgenderMaleRadioBtn.getId()) {
            UtileKit.getSwitchYesBtnView(mgenderMaleRadioBtn, mgenderFemaleRadioBtn, mContext);
            mGender="M";
        } else if (checkedId == mgenderFemaleRadioBtn.getId()) {
            UtileKit.getSwitchNoBtnView(mgenderMaleRadioBtn, mgenderFemaleRadioBtn, mContext);
            mGender="F";
        }
        //resident status
        if(checkedId == mresidential_status_yes_RadioBtn.getId()){
            UtileKit.getSwitchYesBtnView(mresidential_status_yes_RadioBtn, mresidential_status_no_RadioBtn, mContext);
            mResident_status="Y";
        }else if(checkedId == mresidential_status_no_RadioBtn.getId()){
            UtileKit.getSwitchNoBtnView(mresidential_status_yes_RadioBtn, mresidential_status_no_RadioBtn, mContext);
            mResident_status="N";
        }
        //citizenship status
        if(checkedId == mcitizenship_status_yes_RadioBtn.getId()){
            UtileKit.getSwitchYesBtnView(mcitizenship_status_yes_RadioBtn, mcitizenship_status_no_RadioBtn, mContext);
            mCitizenship_status="Y";
        }else if(checkedId == mcitizenship_status_no_RadioBtn.getId()){
            UtileKit.getSwitchNoBtnView(mcitizenship_status_yes_RadioBtn, mcitizenship_status_no_RadioBtn, mContext);
            mCitizenship_status="N";
        }
        //government sector
        if(checkedId == mgovernment_sector_yes_RadioBtn.getId()){
            UtileKit.getSwitchYesBtnView(mgovernment_sector_yes_RadioBtn, mgovernment_sector_no_RadioBtn, mContext);
            mGovernment_sector="Y";
        }else if(checkedId == mgovernment_sector_no_RadioBtn.getId()){
            UtileKit.getSwitchNoBtnView(mgovernment_sector_yes_RadioBtn, mgovernment_sector_no_RadioBtn, mContext);
            mGovernment_sector="N";
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {

            case R.id.personal_details_marital_status_Spinner:
                mMartialStatus = mMartialStatusSpinner.getSelectedItem().toString();

                if(position!=0){
                    if(!isFirstTimeColor&&isClicked) {
                        mMartialStatusSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }


                if (mMartialStatus.equalsIgnoreCase("Married")) {
                    mMarriedDateRelativeLayout.setVisibility(View.VISIBLE);
                    UtileKit.persistingPurplePathPref(getString(R.string.MartialStatus),"Yes");
                } else {
                    mMarriedDateRelativeLayout.setVisibility(View.GONE);
                    UtileKit.persistingPurplePathPref(getString(R.string.MartialStatus),"No");
                }
                break;
            case R.id.personal_details_occupation_Spinner:
                mOccupation = mOccupationSpinner.getSelectedItem().toString();


                if(position!=0){
                    if(!isFirstTimeColor&&isClicked) {
                        mOccupationSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }

                if (mOccupation.equalsIgnoreCase("Homemaker") || mOccupation.equalsIgnoreCase("Student")) {
                    workAddressInputLayout.setVisibility(View.GONE);
                    yearstayedInputLayout.setVisibility(View.GONE);
                    currentOrganizationInputLayout.setVisibility(View.GONE);
                    mPersonalDesignationEdt.setVisibility(View.GONE);
                    designationInputLayout.setVisibility(View.GONE);
                    mOccupationRelativeLayout.setVisibility(View.GONE);
                    copy_homeaddress.setVisibility(View.GONE);
                } else {
                    copy_homeaddress.setVisibility(View.VISIBLE);
                    workAddressInputLayout.setVisibility(View.VISIBLE);
                    yearstayedInputLayout.setVisibility(View.VISIBLE);
                    currentOrganizationInputLayout.setVisibility(View.VISIBLE);
                    mPersonalDesignationEdt.setVisibility(View.VISIBLE);
                    designationInputLayout.setVisibility(View.VISIBLE);
                    mOccupationRelativeLayout.setVisibility(View.GONE);
                }
                if (mOccupation.equalsIgnoreCase("Any Others")) {
                    mOccupationRelativeLayout.setVisibility(View.VISIBLE);
                    mOtherOccupationEdt.setFocusableInTouchMode(true);
                   // mOtherOccupationEdt.requestFocus();
                }
                else if ( mOccupation.equalsIgnoreCase("Lawyer") || mOccupation.equalsIgnoreCase("Doctor") || mOccupation.equalsIgnoreCase("Salaried")) {
                    mPersonalQualificationArray = getResources().getStringArray(R.array.retriedarray);
                    mOccupationRelativeLayout.setVisibility(View.GONE);
                    setSpinnerAdapter(mQualificationSpinnner, mPersonalQualificationArray);
                } else {
                    mOccupationRelativeLayout.setVisibility(View.GONE);
                    mPersonalQualificationArray = getResources().getStringArray(R.array.education);
                    setSpinnerAdapter(mQualificationSpinnner, mPersonalQualificationArray);
                }
                if(isCheckEducaion) {
                    try {
                        //here found array index error throws
                        if(user_per_det.size()!= 0) {

                            if (UtileKit.validateObjectValues(user_per_det.get(0).getEducation())) {


                                if (UtileKit.validateObjectValues(user_per_det.get(0).getEducation())) {
                                    Log.i("PersonalDetailsFragment", " Education on get data :" + user_per_det.get(0).getEducation());
                                    String qualification;
                                    String checkanyother = user_per_det.get(0).getEducation();
                                    if (checkanyother.contains(",")) {

                                        qualificationList = Arrays.asList(checkanyother.split(","));
                                        qualification = qualificationList.get(0);
                                        int status = getStringArraySpinnerposition(qualification, mPersonalQualificationArray);
                                        mQualificationSpinnner.setSelection(status);

                                    } else {
                                        int status = getStringArraySpinnerposition(checkanyother, mPersonalQualificationArray);
                                        mQualificationSpinnner.setSelection(status);
                                    }
                                }
                            }
                            isCheckEducaion = false;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.personal_details_qualification_Spinner:
                try{
                mEducation = mQualificationSpinnner.getSelectedItem().toString();
                mFinalEducation=mQualificationSpinnner.getSelectedItem().toString();
                if(position!=0){
                    if(!isFirstTimeColor&&isClicked) {
                        mQualificationSpinnner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }



                if (mEducation.equalsIgnoreCase("Any Other")) {
                    mQualificationRelativeLayout.setVisibility(View.VISIBLE);
                    mPersonalCourseSelectedSpinner.setVisibility(View.GONE);
                    mPersonalCourseNoofYearSelectedSpinner.setVisibility(View.GONE);
                    mOtherQualificationEdt.setFocusableInTouchMode(true);
                }
                else if (mEducation.equalsIgnoreCase("UG")) {
                    mPersonalCourseSelectedSpinner.setVisibility(View.VISIBLE);
                    mPersonalCourseNoofYearSelectedSpinner.setVisibility(View.VISIBLE);
                    mQualificationRelativeLayout.setVisibility(View.GONE);
                    if(user_per_det!=null)
                    if (UtileKit.validateObjectValues(user_per_det.get(0).getEducation())) {
                        Log.i("PersonalDetailsFragment", " Education on get data :" + user_per_det.get(0).getEducation());
                        String qualification;
                        String checkanyother = user_per_det.get(0).getEducation();
                        if (checkanyother.contains(",")) {
                            qualificationList = Arrays.asList(checkanyother.split(","));
                            if (UtileKit.validateObjectValues(qualificationList.get(1))) {
                                int coursestatus = getStringArraySpinnerposition(qualificationList.get(1), mPersonalCourseArray);
                                Log.i("personal", "UG " + coursestatus);
                                mPersonalCourseSelectedSpinner.setSelection(coursestatus);

                            }
                        }
                    }
                } else if (mEducation.equalsIgnoreCase("PG")) {
                    mPersonalCourseSelectedSpinner.setVisibility(View.VISIBLE);
                    mPersonalCourseNoofYearSelectedSpinner.setVisibility(View.VISIBLE);
                    mQualificationRelativeLayout.setVisibility(View.GONE);
                    if(user_per_det!=null)
                        if (UtileKit.validateObjectValues(user_per_det.get(0).getEducation())) {
                            Log.i("PersonalDetailsFragment", " Education on get data :" + user_per_det.get(0).getEducation());
                            String qualification;
                            String checkanyother = user_per_det.get(0).getEducation();
                            if (checkanyother.contains(",")) {
                                qualificationList = Arrays.asList(checkanyother.split(","));
                                if (UtileKit.validateObjectValues(qualificationList.get(1))) {
                                    int coursestatus = getStringArraySpinnerposition(qualificationList.get(1), mPersonalCourseArray);
                                    Log.i("personal", "UG " + coursestatus);
                                    mPersonalCourseSelectedSpinner.setSelection(coursestatus);

                                }
                            }
                        }
                } else {
                    mQualificationRelativeLayout.setVisibility(View.GONE);
                    mPersonalCourseSelectedSpinner.setVisibility(View.GONE);
                    mPersonalCourseNoofYearSelectedSpinner.setVisibility(View.GONE);
                }


        }catch (Exception e){
            e.printStackTrace();
        }
                break;

            case R.id.personal_course_selected_spinner:
                try {
                    mCourseYear = mPersonalCourseSelectedSpinner.getSelectedItem().toString();
                    if (position != 0) {
                        if (!isFirstTimeColor && isClicked) {
                            mPersonalCourseSelectedSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                            isClicked = false;
                        }
                    }

                    if (mEducation.equalsIgnoreCase("Any Other")) {
                        mQualificationRelativeLayout.setVisibility(View.VISIBLE);
                        mPersonalCourseSelectedSpinner.setVisibility(View.GONE);
                        mPersonalCourseNoofYearSelectedSpinner.setVisibility(View.GONE);
                        mOtherQualificationEdt.setFocusableInTouchMode(true);
                    } else if (mEducation.equalsIgnoreCase("UG")) {
                        mPersonalCourseSelectedSpinner.setVisibility(View.VISIBLE);
                        mPersonalCourseNoofYearSelectedSpinner.setVisibility(View.VISIBLE);
                        mQualificationRelativeLayout.setVisibility(View.GONE);
                        if(user_per_det!=null) {
                            if (UtileKit.validateObjectValues(user_per_det.get(0).getEducation())) {
                                Log.i("PersonalDetailsFragment", " Education on get data :" + user_per_det.get(0).getEducation());
                                String qualification;
                                String checkanyother = user_per_det.get(0).getEducation();
                                if (checkanyother.contains(",")) {
                                    qualificationList = Arrays.asList(checkanyother.split(","));
                                    if (qualificationList.size() == 3)
                                        if (UtileKit.validateObjectValues(qualificationList.get(2))) {
                                            int courseYearStatus = getStringArraySpinnerposition(qualificationList.get(2), mPersonalYearCourseArray);
                                            mPersonalCourseNoofYearSelectedSpinner.setSelection(courseYearStatus);
                                        }
                                }
                            }
                        }
                    } else if (mEducation.equalsIgnoreCase("PG")) {
                        mPersonalCourseSelectedSpinner.setVisibility(View.VISIBLE);
                        mPersonalCourseNoofYearSelectedSpinner.setVisibility(View.VISIBLE);
                        mQualificationRelativeLayout.setVisibility(View.GONE);
                        if(user_per_det!=null) {
                            if (UtileKit.validateObjectValues(user_per_det.get(0).getEducation())) {
                                Log.i("PersonalDetailsFragment", " Education on get data :" + user_per_det.get(0).getEducation());
                                String qualification;
                                String checkanyother = user_per_det.get(0).getEducation();
                                if (checkanyother.contains(",")) {
                                    qualificationList = Arrays.asList(checkanyother.split(","));
                                    if (qualificationList.size() == 3)
                                        if (UtileKit.validateObjectValues(qualificationList.get(2))) {
                                            int courseYearStatus = getStringArraySpinnerposition(qualificationList.get(2), mPersonalYearCourseArray);
                                            mPersonalCourseNoofYearSelectedSpinner.setSelection(courseYearStatus);
                                        }
                                }
                            }
                        }
                    } else {
                        mQualificationRelativeLayout.setVisibility(View.GONE);
                        mPersonalCourseSelectedSpinner.setVisibility(View.GONE);
                        mPersonalCourseNoofYearSelectedSpinner.setVisibility(View.GONE);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;


            case R.id.personal_course_noofYear_selected_spinner:
                mNoofYearCourse = mPersonalCourseNoofYearSelectedSpinner.getSelectedItem().toString();
                if(position!=0){
                    if(!isFirstTimeColor&&isClicked) {
                        mPersonalCourseNoofYearSelectedSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }

                if(qualificationList!=null&&!isFistRun)
                    if (qualificationList.size() == 3)
                        if (UtileKit.validateObjectValues(qualificationList.get(2))) {
                            int courseYearStatus = getStringArraySpinnerposition(qualificationList.get(2),
                                    mPersonalYearCourseArray);
                            mPersonalCourseNoofYearSelectedSpinner.setSelection(courseYearStatus);
                            qualificationList=null;
                            isFistRun=true;
                        }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    @Override
    public void onStop() {
        super.onStop();
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
               // checkPromptMandatory();

                /*if(isMandatory==true) {
                    getValuesFromInput();
                    callAddPersonalDetailsService("backbtn");
                }
               emptyErrorPromptValidation(formArray);*/

                if(emptyErrorPromptValidation(formArray)){
                    getValuesFromInput();
                    callAddPersonalDetailsService("backbtn");
                }

            }
            break;
            case R.id.relative_left_arrow: {
                getValuesFromInput();
                callAddPersonalDetailsService("backbtn");
            }
            break;
            case R.id.relative_center_home: {
                getValuesFromInput();
                callAddPersonalDetailsService( "homeBtn");
            }
            break;
            case R.id.relative_right_arrow: {
                getValuesFromInput();
                callAddPersonalDetailsService( "next");
            }
            break;
            case R.id.homeAddressInputLayout:
            case R.id.personal_details_home_address_Edt: {
                try {
                    showPopupDialog(mAddressHome , ah_city, ah_state, ah_country, ah_zipcode,
                                    ah_resno,ah_resname,ah_street,ah_area);
                    setEdtText(mHomeAddressEdt);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            break;
            case R.id.personal_details_work_address_Edt: {
                try{
                       if(mWorkAddressEdt.equals("")) {
                        showPopupDialog("", "", "", "", "","","","","");
                        setEdtText(mWorkAddressEdt);
                       }
                       else {
                        showPopupDialog(mAddressWork, aw_city, aw_state, aw_country, aw_zipcode,
                                        aw_resno,aw_resname,aw_street,aw_area);
                       setEdtText(mWorkAddressEdt);
                      }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
    }

    private void showPopupDialog(String address, String city, String state, String country, String zipcode,
                                    String resno,String resname,String street,String area) {
        if(newFragment!=null && newFragment.getDialog()!=null ){
            if(newFragment.getDialog().isShowing()){
            }
        }
        if(newFragment!=null && newFragment.getDialog()!=null && newFragment.getDialog().isShowing()) {
        }
        else {
            FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
            newFragment = HomeAddressDialogFragmentNew.newInstance(this,
                    address, city, state, country, zipcode,resno,resname,street,area);
            newFragment.show(fm, "dialog");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        callGetPersonalDetailsService();
    }

    private void callAddPersonalDetailsService(final String state) {

        try {

            if (!(mName.equalsIgnoreCase("") && mMiddlename.equalsIgnoreCase("")
                    &&mLastname.equalsIgnoreCase("")&& mDateOfBirth.equalsIgnoreCase("")
                    && mAge.equalsIgnoreCase("") && mCountrycode.equalsIgnoreCase("")
                    && mMobileno.equalsIgnoreCase("") && mEmailId.equalsIgnoreCase("")
                    && mGender.equalsIgnoreCase("")   && mMartialStatus.equalsIgnoreCase("")
                    && mFinalEducation.equalsIgnoreCase("")
                    && mOccupation.equalsIgnoreCase("") && mCurrentDesignation.equalsIgnoreCase("")
                    && mCurrentOrg.equalsIgnoreCase("") && mAddressHome.equalsIgnoreCase("")
                    && mAddressWork.equalsIgnoreCase("") && mNoofAverageYears.equalsIgnoreCase("")
                    && mNoofWorkYears.equalsIgnoreCase("") && mLifeExpectancyAge.equalsIgnoreCase("") &&
                    mPlannedRetirementAge.equalsIgnoreCase("") && mMarriageDate.equalsIgnoreCase("") &&
                    mMarriedSince.equalsIgnoreCase("") && ah_city.equalsIgnoreCase("")
                    && ah_state.equalsIgnoreCase("") && ah_country.equalsIgnoreCase("")
                    && ah_zipcode.equalsIgnoreCase("") && aw_city.equalsIgnoreCase("")
                    && aw_state.equalsIgnoreCase("") && aw_country.equalsIgnoreCase("")
                    && aw_zipcode.equalsIgnoreCase("")&&  mAlias_name.equalsIgnoreCase("")
                    &&mResident_status.equalsIgnoreCase("")&&mCitizenship_status.equalsIgnoreCase("")
                    &&mGovernment_sector.equalsIgnoreCase("")

                    &&ah_resno.equalsIgnoreCase("")
                    &&ah_resname.equalsIgnoreCase("")
                    &&ah_street.equalsIgnoreCase("")
                    &&ah_area.equalsIgnoreCase("")

                    &&aw_resno.equalsIgnoreCase("")
                    &&aw_resname.equalsIgnoreCase("")
                    &&aw_street.equalsIgnoreCase("")
                    &&aw_area.equalsIgnoreCase(""))) {

                WebServiceCalls webServiceObj;
                webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
                Call<AddPersonalDetailsModel> call = webServiceObj.addPersonalDetailService(UtileKit.getPersistedPurplePathPref("user_id"),
                        mName,mMiddlename,mLastname,mDateOfBirth, mAge, mCountrycode,
                        mMobileno, mEmailId, mGender, mMartialStatus, mFinalEducation, mOccupation, mCurrentDesignation,
                        mCurrentOrg, mAddressHome, mAddressWork, mNoofAverageYears, mNoofWorkYears, mLifeExpectancyAge,
                        mPlannedRetirementAge, mMarriageDate, mMarriedSince, ah_city, ah_state, ah_country, ah_zipcode, aw_city,
                        aw_state, aw_country, aw_zipcode,mAlias_name,mResident_status,mCitizenship_status,mGovernment_sector,

                        ah_resno,ah_resname,ah_street,ah_area,aw_resno,aw_resname,aw_street,aw_area);
                call.enqueue(new Callback<AddPersonalDetailsModel>() {
                    @Override
                    public void onResponse(Call<AddPersonalDetailsModel> call, Response<AddPersonalDetailsModel> response) {

                        onResult(state);
                        UtileKit.dismisssSpinnerDialog();

                    }
                    @Override
                    public void onFailure(Call<AddPersonalDetailsModel> call, Throwable t) {
                        UtileKit.dismisssSpinnerDialog();
                        UtileKit.alertRetrofitExceptionDialog(mContext, t);
                        onResult(state);
                    }
                });
            }
            else {
                onResult(state);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        }

    private void onResult(String state) {
        if (state.equalsIgnoreCase("next")) {
            addFragmenttoStack(new FamilyDetailFragment());
        } else if (state.equalsIgnoreCase("homeBtn")) {
            Intent i = new Intent(getActivity(), HomePageActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (state.equalsIgnoreCase("backbtn"))
        {
            /*if (isSignUp) {
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("IsSignUp", isSignUp);
                startActivity(i);
            } */
             {
                mCallBackListener.onActivityBackPressed();
            }
        }
    }


    private void getValuesFromInput() {
        mDateOfBirth = setDateFormat(mBornYearEdt.getText().toString());
        mLifeExpectancyAge = mExpectedLifeEdt.getText().toString();
        mPlannedRetirementAge = mRetirementAgeEdt.getText().toString();

        mName=mFirstname_Edt.getText().toString();
        mMiddlename=mMiddlename_Edt.getText().toString();
        mLastname=mLastname_Edt.getText().toString();
        mAlias_name= maliasname_Edt.getText().toString();
        UtileKit.persistingPurplePathPref("name",mName);
        UtileKit.persistingPurplePathPref("LastnamePref",mLastname);
        mMarriageDate = setDateFormat(mMarriageDateEdt.getText().toString());

        if (mOtherQualificationEdt.isShown()) {
            String anyother = "Any Other, ";
            mFinalEducation = anyother.concat(mOtherQualificationEdt.getText().toString().trim());
        }
        if (mOtherOccupationEdt.isShown()) {
            String anyother = "Any Others, ";

            mOccupation = anyother.concat(mOtherOccupationEdt.getText().toString().trim());
            mOtherOccupationEdt.setSelection(mOtherOccupationEdt.getText().length());
        }


        course = mPersonalCourseSelectedSpinner.getSelectedItem().toString();

        if(mPersonalCourseNoofYearSelectedSpinner.isShown())
            courseYear = mPersonalCourseNoofYearSelectedSpinner.getSelectedItem().toString();
        else
            courseYear=null;
        if (UtileKit.validateObjectValues(course) && UtileKit.validateObjectValues(courseYear)) {
            String education = course.concat(",").concat(courseYear);
            mFinalEducation = mFinalEducation.concat(",").concat(education);
        } else if (UtileKit.validateObjectValues(course)) {
            String education = course.concat(",");
            mFinalEducation = mFinalEducation.concat(",").concat(education);
        }
        mCurrentOrg = mCurrentOrganizationEdt.getText().toString();
        mNoofAverageYears = mStayedCurrentResidenceEdt.getText().toString();
        mNoofWorkYears = mYearServiceCurrOrganizationEdt.getText().toString();
        mCurrentDesignation = mPersonalDesignationEdt.getText().toString();
    }




        void spinnerError(Spinner spinner) {
            TextView errorText = (TextView) spinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("");
        }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        int monthincr = month + 1;
        getEdtText().setText("" + day + "-" + monthincr + "-" + year);
        if (getEdtText() == mBornYearEdt) {
            getDobDate = setDateFormat(getEdtText().getText().toString());
        } else if (getEdtText() == mMarriageDateEdt) {
            getMarriageDate = setDateFormat(getEdtText().getText().toString());
        }
    }

    private void setEdtText(EditText edttext) {
        this.mCalanderEdt = edttext;
    }

    private EditText getEdtText() {
        return mCalanderEdt;
    }

    @Override
    public void onYearSet(YearPickerDialog yearPickerDialog, int year) {
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            final int currentYear = calendar.get(Calendar.YEAR);
            if (year < currentYear) {
                String yearValue = String.valueOf(Utils.getAgeDataSubFromCurrentYear(year));
                getEdtText().setText(yearValue);
            } else {
                String yearValue = String.valueOf(Utils.getAgeDataSubFromCurrentYearisGreater(year));
                getEdtText().setText(yearValue);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void editTextDrawableClick(final CalendarEditText EdtText) {

        EdtText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    edtTextonClick(EdtText);
                    return true;
                }
                return false;
            }
        });
    }

    public void edtTextonClick(CalendarEditText EdtText) {
        try {
            if (EdtText == mBornYearEdt) {
                setEdtText(mBornYearEdt);
                if (UtileKit.validateObjectValues(getDobDate)) {
                    if (UtileKit.removeDefaultValue(getDobDate)) {
                        if (getDobDate.trim().length() != 0) {
                            String dateAray[] = getDobDate.split("-");
                            String day = dateAray[2];
                            String month = dateAray[1];
                            String year = dateAray[0];
                            String Dob = date_of_birth;
                            String age = your_age;
                            String date=EdtText.getText().toString();
                            CalenderTabs mcalenderTabs =  CalenderTabs.newInstance(this,
                                    "Date Of Birth",Boolean.TRUE,Boolean.FALSE,Boolean.FALSE,"1", date);
                            if(UtileKit.validateObjectValues(date)) {
                                args.putString(AssetDatePickerDialogFragment.FULLDATE, date);
                                mcalenderTabs.setArguments(args);
                            }
                            mcalenderTabs.show(getFragmentManager(),"Wedding Date");

                        }
                    }
                } else {
                    String Dob = date_of_birth;
                    String age = your_age;

                    String date=EdtText.getText().toString();
                    CalenderTabs mcalenderTabs =  CalenderTabs.newInstance(this,
                            date_of_birth,Boolean.TRUE,Boolean.FALSE,Boolean.FALSE,"1", date);
                    if(UtileKit.validateObjectValues(date)) {
                        args.putString(AssetDatePickerDialogFragment.FULLDATE, date);
                        mcalenderTabs.setArguments(args);
                    }
                    mcalenderTabs.show(getFragmentManager(),"Wedding Date");
                }
            } else if (EdtText == mMarriageDateEdt) {
                String date=EdtText.getText().toString();
                CalenderTabs mcalenderTabs =  CalenderTabs.newInstance(this,
                        "Select Wedding Date",Boolean.TRUE,Boolean.FALSE,Boolean.FALSE,"1", date);
                if(UtileKit.validateObjectValues(date)) {
                    args.putString(AssetDatePickerDialogFragment.FULLDATE, date);
                    mcalenderTabs.setArguments(args);
                }
                mcalenderTabs.show(getFragmentManager(),"Wedding Date");

            }
            else if (EdtText == mStayedCurrentResidenceEdt) {
                String years=EdtText.getText().toString();
                String concactDDMMYY= null;
                if(!years.isEmpty()&& years!= null) {
                    if (years.length() <= 3) {
                        String date = getcurrentYear(years);
                        concactDDMMYY = currentdateFirsttime + "-" + currentmonthFirsttime + "-" + date;
                    } else {

                        concactDDMMYY = years;
                    }
                }else{
                    concactDDMMYY = currentdateFirsttime + "-" + currentmonthFirsttime + "-" + currentyearFirsttime;
                }
                if(concactDDMMYY!= null) {
                    CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                            "Current Residence", Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, "1", concactDDMMYY);
                    mcalenderTabs.show(getFragmentManager(), "Date");
                }

            } else if (EdtText == mYearServiceCurrOrganizationEdt) {
                //flag
                String years=EdtText.getText().toString();
                String concactDDMMYY= null;
                if(!years.isEmpty()&& years!= null) {
                if(years.length()<=3){
                    String date = getcurrentYear(years);
                    concactDDMMYY = currentdateFirsttime+"-"+currentmonthFirsttime+"-"+date;
                }else{

                    concactDDMMYY= years;
                }
                }else{
                    concactDDMMYY = currentdateFirsttime + "-" + currentmonthFirsttime + "-" + currentyearFirsttime;
                }
                if(concactDDMMYY!= null) {
                    CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                            "Current Organization", Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, "1", concactDDMMYY);
                    mcalenderTabs.show(getFragmentManager(), "Date");
                }

            }
        } catch (IllegalArgumentException e) {

            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    public void onStart() {
        super.onStart();

    }
    private void callGetPersonalDetailsService() {
        getPersonnalDetailsModel = new GetPersonnalDetailsModel();
        user_per_det = new ArrayList<User_per_det>();
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetPersonnalDetailsModel> call = webServiceObj.callGetPersonnalDetailService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetPersonnalDetailsModel>() {
            @Override
            public void onResponse(Call<GetPersonnalDetailsModel> call, Response<GetPersonnalDetailsModel> response) {
                UtileKit.dismisssSpinnerDialog();
                getPersonnalDetailsModel = response.body();
                if (getPersonnalDetailsModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    user_per_det = getPersonnalDetailsModel.getData().getUser_per_det();
                    getPersonnalData(user_per_det);
                    if(UtileKit.validateObjectValues(getPersonnalDetailsModel.getData().getEmpty_flds()))
                    emptyErrorValidation(getPersonnalDetailsModel.getData().getEmpty_flds());
                }
            }
            @Override
            public void onFailure(Call<GetPersonnalDetailsModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
    }


    public void setSpinnerAdapter(Spinner mMyMartialSpinner, String[] myStringArray) {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("");
        for (String s : myStringArray) {
            stringList.add(s);
        }
        CustomSpinerAdapter adapter_state = new CustomSpinerAdapter(mContext, stringList);
        mMyMartialSpinner.setAdapter(adapter_state);
        mMyMartialSpinner.setOnItemSelectedListener(this);
    }
    private void emptyErrorValidation(ArrayList<String> emptyArrayList) {
        for(String obj:emptyArrayList)
        {
            View view=errorMapView.get(obj);
            UtileKit.emptyErrorViewList(view);
        }
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
                ((CustomTextInputLayout) (view.getParent()).getParent()).setError(mandatoryPromptMapView.get(key));
                isMandatory=false;
            }else {
                ((CustomTextInputLayout) (view.getParent()).getParent()).setError(null);
            }
        }else if(view instanceof NumberEditText){
            if(((NumberEditText) view).length()==0) {
                ((CustomTextInputLayout) (view.getParent()).getParent()).setError(mandatoryPromptMapView.get(key));
                isMandatory=false;
            }else {
                ((CustomTextInputLayout) (view.getParent()).getParent()).setError(null);
            }
        }else if(view instanceof Spinner){

            switch(view.getId()){
                case R.id.personal_details_marital_status_Spinner:
                    String marital_status=mMartialStatusSpinner.getSelectedItem().toString();
                    if(marital_status.equalsIgnoreCase("")) {
                        mMartialStatusSpinner.setSelection(0);
                        spinnerError(mMartialStatusSpinner);
                        isMandatory = false;
                    }
                    break;
                case R.id.personal_details_occupation_Spinner:
                    String occupation=mOccupationSpinner.getSelectedItem().toString();
                    if(occupation.equalsIgnoreCase("")) {
                        mOccupationSpinner.setSelection(0);
                        spinnerError(mOccupationSpinner);
                        isMandatory = false;
                    }
                    break;
                case R.id.personal_details_qualification_Spinner:
                    String qualification=mQualificationSpinnner.getSelectedItem().toString();
                    if(qualification.equalsIgnoreCase("")) {
                        mQualificationSpinnner.setSelection(0);
                        spinnerError(mQualificationSpinnner);
                        isMandatory = false;
                    }
                    break;
            }
        }
    }

    private void getPersonnalData(ArrayList<User_per_det> userDetails) {
        isfirstrun=true;
        isCheckEducaion = true;
        try {
            personnalID = userDetails.get(0).getId();
            if (UtileKit.validateObjectValues(userDetails.get(0).getDob()) && (UtileKit.removeDefaultValue(userDetails.get(0).getDob()))) {

                Boolean isEmptyDate = UtileKit.checkEmptyDate(userDetails.get(0).getDob());
                if (isEmptyDate) {
                    getDobDate = userDetails.get(0).getDob();
                    mBornYearEdt.setText(setDateFormat(getDobDate));
                }
            } else {
            }

            if (UtileKit.validateObjectValues(userDetails.get(0).getGender())) {
                String gender = userDetails.get(0).getGender();
                if (gender.equalsIgnoreCase("M")) {
                    mGender = "M";
                    UtileKit.getSwitchYesBtnView(mgenderMaleRadioBtn, mgenderFemaleRadioBtn, mContext);
                } else {
                    mGender = "F";
                    UtileKit.getSwitchNoBtnView(mgenderMaleRadioBtn, mgenderFemaleRadioBtn, mContext);
                }
            }

            if(UtileKit.validateObjectValues(userDetails.get(0).getResidential_status())){
                String resident_status=userDetails.get(0).getResidential_status();
                if(resident_status.equalsIgnoreCase("Y")){
                    mResident_status="Y";
                    UtileKit.getSwitchYesBtnView(mresidential_status_yes_RadioBtn, mresidential_status_no_RadioBtn, mContext);
                }else {
                    mResident_status="N";
                    UtileKit.getSwitchNoBtnView(mresidential_status_yes_RadioBtn, mresidential_status_no_RadioBtn, mContext);
                }
            }

            if(UtileKit.validateObjectValues(userDetails.get(0).getCitizenship_status())){
                String resident_status=userDetails.get(0).getCitizenship_status();
                if(resident_status.equalsIgnoreCase("Y")){
                    mCitizenship_status="Y";
                    UtileKit.getSwitchYesBtnView(mcitizenship_status_yes_RadioBtn, mcitizenship_status_no_RadioBtn, mContext);
                }else {
                    mCitizenship_status="N";
                    UtileKit.getSwitchNoBtnView(mcitizenship_status_yes_RadioBtn, mcitizenship_status_no_RadioBtn, mContext);
                }
            }
            if(UtileKit.validateObjectValues(userDetails.get(0).getGovernment_sector())){
                String resident_status=userDetails.get(0).getGovernment_sector();
                if(resident_status.equalsIgnoreCase("Y")){
                    mGovernment_sector="Y";
                    UtileKit.getSwitchYesBtnView(mgovernment_sector_yes_RadioBtn, mgovernment_sector_no_RadioBtn, mContext);
                }else {
                    mGovernment_sector="N";
                    UtileKit.getSwitchNoBtnView(mgovernment_sector_yes_RadioBtn, mgovernment_sector_no_RadioBtn, mContext);
                }
            }

            if (UtileKit.validateObjectValues(userDetails.get(0).getMartial_status())) {
                int status = getStringArraySpinnerposition(userDetails.get(0).getMartial_status(), mpersonalMaritialStatusArray);
                mMartialStatusSpinner.setSelection(status);
                mMartialStatusSpinner.setBackgroundResource(R.drawable.spinner_background_gray_arrow);
                if (userDetails.get(0).getMartial_status().equalsIgnoreCase("Married")) {
                    mMarriedDateRelativeLayout.setVisibility(View.VISIBLE);
                    UtileKit.persistingPurplePathPref(getString(R.string.MartialStatus), "Yes");
                    if (UtileKit.validateObjectValues(userDetails.get(0).getMarriage_date())) {

                        Boolean isEmptyDate = UtileKit.checkEmptyDate(userDetails.get(0).getMarriage_date().trim());
                        if (isEmptyDate) {
                            getMarriageDate = userDetails.get(0).getMarriage_date();
                            mMarriageDateEdt.setText(setDateFormat(getMarriageDate));
                        }
                    }

                } else {
                    mMarriedDateRelativeLayout.setVisibility(View.GONE);
                    UtileKit.persistingPurplePathPref(getString(R.string.MartialStatus), "No");
                }
            }

            if (UtileKit.validateObjectValues(userDetails.get(0).getOccupation())) {
                String occupation;
                String checkanyother = userDetails.get(0).getOccupation();
                if (checkanyother.contains(",")) {
                    List<String> occupationList = Arrays.asList(checkanyother.split(","));
                    occupation = occupationList.get(0);
                    Log.i("occupationList.get", "checkanyother checkanyother" + checkanyother);
                    Log.i("occupationList.get", "occupationList.get" + occupation);
                    if (occupation.equalsIgnoreCase("Any Others")) {
                        mOccupationRelativeLayout.setVisibility(View.VISIBLE);
                        try{
                        if(occupationList.get(1)!= null){
                            mOtherOccupationEdt.setText(occupationList.get(1).trim());

                                mOtherOccupationEdt.setSelection(mOtherOccupationEdt.getText().length());

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    occupation = userDetails.get(0).getOccupation();
                    Log.i("occupationList.get", "occupationList.get in else" + occupation);
                }
                mOccupationSpinner.setBackgroundResource(R.drawable.spinner_background_gray_arrow);
                int status = getStringArraySpinnerposition(occupation, mPersonalOccupationArray);
                mOccupationSpinner.setSelection(status);

            }
            if (UtileKit.validateObjectValues(userDetails.get(0).getCurrent_org())) {
                mCurrentOrganizationEdt.setText(userDetails.get(0).getCurrent_org());
            } else {

            }

            if (UtileKit.validateObjectValues(userDetails.get(0).getCurrent_designation())) {
                mPersonalDesignationEdt.setText(userDetails.get(0).getCurrent_designation());
            } else {
            }

//            if (UtileKit.validateObjectValues(userDetails.get(0).getAddress_home())) {
//                Log.i("PersonalDetailsFragment", " home address" + userDetails.get(0).getAddress_home());
//                mAddressHome = userDetails.get(0).getAddress_home();
//
//                if (mAddressHome != null) {
//                    mHomeAddressEdt.setText(mAddressHome +"\n"+ ah_city +"\n"+ah_state +"\n"+ah_country+"\n"+ah_zipcode);
//
//                }
//            } else {
//                mHomeAddressEdt.setHighlightColor(UtileKit.getColor(getContext(), R.color.Green));
//
//            }

            if (UtileKit.validateObjectValues(userDetails.get(0).getAh_res_no())) {
                ah_resno = userDetails.get(0).getAh_res_no();
            }
            if (UtileKit.validateObjectValues(userDetails.get(0).getAh_res_name())) {
                ah_resname = userDetails.get(0).getAh_res_name();
            }
            if (UtileKit.validateObjectValues(userDetails.get(0).getAh_road_street())) {
                ah_street = userDetails.get(0).getAh_road_street();
            }
            if (UtileKit.validateObjectValues(userDetails.get(0).getAh_locality_area())) {
                ah_area = userDetails.get(0).getAh_locality_area();
            }

            if (UtileKit.validateObjectValues(userDetails.get(0).getAh_city())) {
                ah_city = userDetails.get(0).getAh_city();
                UtileKit.persistingPurplePathPref("City", ah_city);
            }


            if (UtileKit.validateObjectValues(userDetails.get(0).getAh_state())) {
                ah_state = userDetails.get(0).getAh_state();
                UtileKit.persistingPurplePathPref("State", ah_state);
            }

            if (UtileKit.validateObjectValues(userDetails.get(0).getAh_country())) {
                ah_country = userDetails.get(0).getAh_country();
                UtileKit.persistingPurplePathPref("Country", ah_country);
            }

            if (UtileKit.validateObjectValues(userDetails.get(0).getAh_zipcode())) {
                ah_zipcode = userDetails.get(0).getAh_zipcode();
                UtileKit.persistingPurplePathPref("Zipcode", ah_zipcode);
            }



            setHomeWorkAddress(ah_resno,ah_resname,ah_street,ah_area, ah_city, ah_state,ah_country,ah_zipcode,mHomeAddressEdt,"home");

          //  mHomeAddressEdt.setText(ah_resno+","+ah_resname+","+ah_street+","+ah_area+","+ ah_city +","+ah_state +","+ah_country+","+ah_zipcode);







//work address
            if (UtileKit.validateObjectValues(userDetails.get(0).getAw_res_no())) {
                aw_resno = userDetails.get(0).getAw_res_no();
            }
            if (UtileKit.validateObjectValues(userDetails.get(0).getAw_res_name())) {
                aw_resname = userDetails.get(0).getAw_res_name();
            }
            if (UtileKit.validateObjectValues(userDetails.get(0).getAw_road_street())) {
                aw_street = userDetails.get(0).getAw_road_street();
            }
            if (UtileKit.validateObjectValues(userDetails.get(0).getAw_locality_area())) {
                aw_area = userDetails.get(0).getAw_locality_area();
            }

            if (UtileKit.validateObjectValues(userDetails.get(0).getAddress_work())) {
                mAddressWork = userDetails.get(0).getAddress_work();
            }
            if (UtileKit.validateObjectValues(userDetails.get(0).getAw_city())) {
                aw_city = userDetails.get(0).getAw_city();
            }

            if (UtileKit.validateObjectValues(userDetails.get(0).getAw_state())) {
                aw_state = userDetails.get(0).getAw_state();
            }

            if (UtileKit.validateObjectValues(userDetails.get(0).getAw_country())) {
                aw_country = userDetails.get(0).getAw_country();
            }

            if (UtileKit.validateObjectValues(userDetails.get(0).getAw_zipcode())) {
                aw_zipcode = userDetails.get(0).getAw_zipcode();
            }
            if (UtileKit.validateObjectValues(userDetails.get(0).getAddress_work())) {
                try {
                    mAddressWork = userDetails.get(0).getAddress_work();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
            }



            setHomeWorkAddress(aw_resno,aw_resname,aw_street,aw_area, aw_city,aw_state ,aw_country,aw_zipcode,mWorkAddressEdt,"work");
         //   mWorkAddressEdt.setText(aw_resno+","+aw_resname+","+aw_street+","+aw_area+","+ aw_city + ", " +aw_state +", "+aw_country+","+aw_zipcode);

//            if (mAddressWork != null) {
//                mWorkAddressEdt.setText(mAddressWork +" "+ aw_city + " " +aw_state +" "+aw_country+" "+aw_zipcode);
//            }
            if (UtileKit.validateObjectValues(userDetails.get(0).getNo_of_ava_years()) || UtileKit.removeDefaultValue(userDetails.get(0).getNo_of_ava_years())) {
                mNoofAverageYears = userDetails.get(0).getNo_of_ava_years();
                mStayedCurrentResidenceEdt.setText(userDetails.get(0).getNo_of_ava_years());
            } else {
            }

            if (UtileKit.validateObjectValues(userDetails.get(0).getNo_of_work_years()) || UtileKit.removeDefaultValue(userDetails.get(0).getNo_of_work_years())) {
                mCurrentOrg = userDetails.get(0).getNo_of_work_years();
                mYearServiceCurrOrganizationEdt.setText(userDetails.get(0).getNo_of_work_years());
            } else {
            }

            if (UtileKit.validateObjectValues(userDetails.get(0).getLife_expectancy_age())) {
                mLifeExpectancyAge = userDetails.get(0).getLife_expectancy_age();
                mExpectedLifeEdt.setText(userDetails.get(0).getLife_expectancy_age());
            } else {
            }

            if (UtileKit.validateObjectValues(userDetails.get(0).getPlanned_retirement_age())) {
                mPlannedRetirementAge = userDetails.get(0).getPlanned_retirement_age();
                mRetirementAgeEdt.setText(userDetails.get(0).getPlanned_retirement_age());
            } else {
            }

            if (UtileKit.validateObjectValues(userDetails.get(0).getName())) {
                mName = userDetails.get(0).getName();
                mFirstname_Edt.setText(userDetails.get(0).getName());
            } else {
            }
            if (UtileKit.validateObjectValues(userDetails.get(0).getMiddle_name())) {
                mMiddlename = userDetails.get(0).getMiddle_name();
                mMiddlename_Edt.setText(userDetails.get(0).getMiddle_name());
            } else {
            }
            if (UtileKit.validateObjectValues(userDetails.get(0).getLast_name())) {
                mLastname = userDetails.get(0).getLast_name();
                mLastname_Edt.setText(userDetails.get(0).getLast_name());
            } else {
            }

            if (UtileKit.validateObjectValues(userDetails.get(0).getAlias_name())) {
                mAlias_name = userDetails.get(0).getAlias_name();
                maliasname_Edt.setText(userDetails.get(0).getAlias_name());
            } else {
            }

            if(!(StringUtils.isEmpty(ah_city)
                    &&StringUtils.isEmpty(aw_city)&&StringUtils.isEmpty(ah_country)&&StringUtils.isEmpty(aw_country)
                    &&StringUtils.isEmpty(ah_state)&&StringUtils.isEmpty(aw_state)&&StringUtils.isEmpty(ah_zipcode)
                    &&StringUtils.isEmpty(aw_zipcode) &&StringUtils.isEmpty(ah_resno)  &&StringUtils.isEmpty(ah_resname)
                    &&StringUtils.isEmpty(ah_street) &&StringUtils.isEmpty(ah_area) &&StringUtils.isEmpty(aw_resno)
                    &&StringUtils.isEmpty(aw_resname) &&StringUtils.isEmpty(aw_street) &&StringUtils.isEmpty(aw_area))){


                if (ah_city.equalsIgnoreCase(aw_city)
                        && ah_country.equalsIgnoreCase(aw_country) && ah_state.equalsIgnoreCase(aw_state)
                        && ah_zipcode.equalsIgnoreCase(aw_zipcode)&& ah_resno.equalsIgnoreCase(aw_resno)
                        && ah_resname.equalsIgnoreCase(aw_resname)&& ah_street.equalsIgnoreCase(aw_street)
                        && ah_area.equalsIgnoreCase(aw_area)) {

                    copy_homeaddress.setChecked(true);
                } else {
                    copy_homeaddress.setChecked(false);
                }
            }

            mOtherQualificationEdt.setSelection(mOtherQualificationEdt.getText().length());
        } catch (Exception e){
            e.printStackTrace();
        }
            isfirstrun=false;
    }


    void setHomeWorkAddress(String ah_resno, String ah_resname,
                            String ah_street, String ah_area,
                            String ah_city, String ah_state,
                            String ah_country, String ah_zipcode, CharacterEditText mHomeAddressEdt, String mType){
        ArrayList<String> array_string = new ArrayList<>();

        if(UtileKit.validateObjectValues(ah_resno)){
            array_string.add(ah_resno +",");
        } if(UtileKit.validateObjectValues(ah_resname)){
            array_string.add(ah_resname +",");
        } if(UtileKit.validateObjectValues(ah_street)){
            array_string.add(ah_street +",");
        } if(UtileKit.validateObjectValues(ah_area)){
            array_string.add(ah_area +",");
        } if(UtileKit.validateObjectValues(ah_city)){
            array_string.add(ah_city +",");
        } if(UtileKit.validateObjectValues(ah_state)){
            array_string.add(ah_state +",");
        } if(UtileKit.validateObjectValues(ah_country)){
            array_string.add(ah_country +",");
        } if(UtileKit.validateObjectValues(ah_zipcode)){
            array_string.add(ah_zipcode );
        }


        String listString = "";

        for (String s : array_string)
        {
            listString += s + "\t";
        }

        System.out.println(listString);
        mHomeAddressEdt.setText(listString);
        if(mType.equalsIgnoreCase("home")){
            mAddressHome = listString;
        }else {
            mAddressWork = listString;
        }

//        if(array_string!=null && array_string.size()!=0) {
//            for (int i = 0; i < array_string.size(); i++) {
//                mHomeAddressEdt.setText(listString);
//            }
//            mAddressHome = mHomeAddressEdt.getText().toString();
//        }
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


    private int getStringArraySpinnerposition(String value, String[] spinerlist) {
        int pos = 0;
        for (int i = 0; i < spinerlist.length; i++) {
            if (value.equalsIgnoreCase(spinerlist[i])) {
                pos = i + 1;
            }
        }
        return pos;
    }


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


    @Override
    public void onHomeAddressSet(String address, String city, String state, String country, String zipcode,
                                 String resno,String resname,String street,String area) {
        if (getEdtText() == mHomeAddressEdt) {
            mAddressHome = address;
            ah_city = city;
            ah_state = state;
            ah_country = country;
            ah_zipcode = zipcode;

            ah_resno = resno;
            ah_resname = resname;
            ah_street = street;
            ah_area = area;

//            getEdtText().setText(ah_resno +"\n "+ah_resname+"\n"+ah_street+"\n"
//                   + ah_area +"\n"+ ah_city+" \n" +ah_state +" \n"+ah_country+"\n"+ah_zipcode);
            setHomeWorkAddress(ah_resno,ah_resname,ah_street,ah_area, ah_city, ah_state,ah_country,ah_zipcode,mHomeAddressEdt,"home");
            getEdtText().setText(mAddressHome);
            //getEdtText().setText(mAddressHome +"\n "+ ah_city + " \n" +ah_state +" \n"+ah_country+"\n"+ah_zipcode);
        } else if (getEdtText() == mWorkAddressEdt) {
            mAddressWork = address;
            aw_city = city;
            aw_state = state;
            aw_country = country;
            aw_zipcode = zipcode;

            aw_resno = resno;
            aw_resname = resname;
            aw_street = street;
            aw_area = area;

            setHomeWorkAddress(aw_resno,aw_resname,aw_street,aw_area, aw_city, aw_state,aw_country,aw_zipcode,mWorkAddressEdt,"work");

           // setHomeWorkAddress(ah_resno,ah_resname,ah_street,ah_area, ah_city, ah_state,ah_country,ah_zipcode,mWorkAddressEdt,"work");
            getEdtText().setText(mAddressWork);
          //  getEdtText().setText(aw_resno +"\n "+aw_resname+"\n"+aw_street+"\n"+ aw_area +"\n"+ aw_city + "\n " +aw_state +"\n "+aw_country+"\n "+aw_zipcode);
           // getEdtText().setText(mAddressWork +" \n"+ aw_city + "\n " +aw_state +"\n "+aw_country+"\n "+aw_zipcode);
        }
    }

    public void setListener(DialogFragmentCallbackInterface callbackInterface){
        this.callbackInterface=callbackInterface;
    }

    @Override
    public void upadateDOB(String dob) {
        mBornYearEdt.setText(dob);
        getDobDate = setDateFormat(dob);
        UtileKit.edittextbordercolorchange(mBornYearEdt);
    }

    @Override
    public void upadateExpectedLife(String expectedLife) {
        mExpectedLifeEdt.setText(expectedLife);
        mLifeExpectancyAge = expectedLife;
        UtileKit.normaledittextbordercolorchange(mExpectedLifeEdt);
    }

    @Override
    public void upadateReterimentLife(String reterimentLife) {
        mRetirementAgeEdt.setText(reterimentLife);
        mPlannedRetirementAge = reterimentLife;
        UtileKit.normaledittextbordercolorchange(mRetirementAgeEdt);
    }
    @Override
    public void upadatecurrentResidency(String currentResidency) {
        mStayedCurrentResidenceEdt.setText(currentResidency);
        mNoofAverageYears = currentResidency;
        UtileKit.edittextbordercolorchange(mStayedCurrentResidenceEdt);
    }
    @Override
    public void upadatecurrentOrgnazation(String currentOrgnazation) {
        mCurrentOrg = " ";

        mCurrentOrg = currentOrgnazation;
        if(!currentOrgnazation.equalsIgnoreCase("0")){
            mYearServiceCurrOrganizationEdt.setText(currentOrgnazation);
            UtileKit.edittextbordercolorchange(mYearServiceCurrOrganizationEdt);
        }
        else{
            mYearServiceCurrOrganizationEdt.setText("");
            UtileKit.edittextbordercolorchange(mYearServiceCurrOrganizationEdt);
        }
    }
    @Override
    public void upadateMaraigeDate(String MaraigeDate) {
        getMarriageDate = " ";
        mMarriageDateEdt.setText(MaraigeDate);
        getMarriageDate = MaraigeDate;
        UtileKit.edittextbordercolorchange(mMarriageDateEdt);


    }

    /**
     * update interface value
     * @param value
     * @param title
     */
    @Override
    public void updateEditTextValue(String value, String title) {
        if(title.equalsIgnoreCase("Select Wedding Date")){
            mMarriageDateEdt.setText(value);
            UtileKit.edittextbordercolorchange(mMarriageDateEdt);
        }else if (title.equalsIgnoreCase("Current Residence")){
            try {
                if(!value.equalsIgnoreCase("")){
                    if(value.length()<=3){
                        mStayedCurrentResidenceEdt.setText(value);
                    }else{
                        mStayedCurrentResidenceEdt.setText(value);
                    }

                    UtileKit.edittextbordercolorchange(mStayedCurrentResidenceEdt);
                }
                else{
                    mStayedCurrentResidenceEdt.setText("");
                    UtileKit.edittextbordercolorchange(mStayedCurrentResidenceEdt);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(title.equalsIgnoreCase("Current Organization")){
            try {
                if(!value.equalsIgnoreCase("")){
                    if(value.length()<=3){
                        mYearServiceCurrOrganizationEdt.setText(value);
                    }else {
                        mYearServiceCurrOrganizationEdt.setText(value);
                        UtileKit.edittextbordercolorchange(mYearServiceCurrOrganizationEdt);
                    }
                }
                else{
                    mYearServiceCurrOrganizationEdt.setText("");
                    UtileKit.edittextbordercolorchange(mYearServiceCurrOrganizationEdt);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        } else if(title.equalsIgnoreCase("Date Of Birth")){
            mBornYearEdt.setText(value);
            getDobDate = setDateFormat(value);
            UtileKit.edittextbordercolorchange(mBornYearEdt);

            //prompt remove error
         /*   if(mBornYearEdt.getText().length()!=0)
            {
                bornyearInputLayout.setError(null);
                bornyearInputLayout.setEnabled(false);
            }*/

        }
    }

    @Override
    public void updateIndividualEditTextValue(String value, String title) {

    }


    String getcurrentYear (String getyear){
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

            if(getyear.isEmpty()){
                updateyears =1;
            }else {

                if (Integer.parseInt(getyear) < currentyear) {
                    updateyears = currentyear - Integer.parseInt(getyear);

                } else {
                    updateyears = Integer.parseInt(getyear) - currentyear;
                }
            }

            Log.i("", "yearGeneratorpastYear updateyears" + updateyears);
            years = String.valueOf(updateyears);
            return years;
        }catch (Exception e){
            e.printStackTrace();
        }
        return years;
    }

    private void firstTimegetCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Date date = new Date();
        Log.i("", "yearGeneratorpastYear date" + dateFormat.format(date));
        String[] items1 = dateFormat.format(date).split("/");
        Log.i("", "yearGeneratorpastYear year" + items1[0]);
        currentdateFirsttime= Integer.parseInt(items1[2]);
        currentmonthFirsttime= Integer.parseInt(items1[1]);
        currentyearFirsttime= Integer.parseInt(items1[0]);
    }


}
