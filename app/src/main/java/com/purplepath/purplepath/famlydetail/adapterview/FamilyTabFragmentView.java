package com.purplepath.purplepath.famlydetail.adapterview;

/**
 * Created by dinesh on 14/03/16.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.fourmob.datetimepicker.date.YearPickerDialog;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.calenderNumberPicker.calenderUi.CalenderTabs;
import com.purplepath.purplepath.customview.CalendarEditText;
import com.purplepath.purplepath.customview.CharacterEditText;
import com.purplepath.purplepath.customview.CustomSpinerAdapter;
import com.purplepath.purplepath.customview.CustomTextInputLayout;
import com.purplepath.purplepath.customview.NumberEditText;
import com.purplepath.purplepath.famlydetail.addTabinterface.OnAddTabChange;
import com.purplepath.purplepath.famlydetail.dailog.DailogYearOfservices;
import com.purplepath.purplepath.famlydetail.dailog.DialogNoyearInterface;
import com.purplepath.purplepath.famlydetail.fragmentview.FamilyDetailFragment;
import com.purplepath.purplepath.famlydetail.model.AddFamilyDetailModel;
import com.purplepath.purplepath.famlydetail.model.Family_details;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.goal.GoalsListFragment;
import com.purplepath.purplepath.myinterface.DatePickerCallBackInterface;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.purplepath.purplepath.famlydetail.fragmentview.FamilyDetailFragment.mfamilyId;

public class FamilyTabFragmentView extends BaseFragment implements RadioGroup.OnCheckedChangeListener,
        AdapterView.OnItemSelectedListener, YearPickerDialog.OnYearSetListener, View.OnClickListener,
        DialogNoyearInterface ,DatePickerCallBackInterface{

    private Context mContext;
    String id;
    private DialogNoyearInterface callbackInterface;
    TextInputLayout mFamilyNameParentview,mAgeParentEdt;
    private Spinner mRelationShipSpinner, mMartialStatusSpinner, mOccupationSpinner, mCurrenDesignation,mEducationLevel1,mEducationLevel2,mEducationLevel3;
    private RadioGroup otherQualifiRadioGroupe = null, mgenderRadioGroupe = null;
    private RadioButton mgenderMaleRadioBtn, mgenderFemaleRadioBtn;
    private String[] mMaritialStatusArray, mRelationShipArray, mOccupationArray,mPersonalCourseArray, mPersonalYearCourseArray,mPersonalQualificationArray;
    private String mMartialStatus, mOccupationStatus,mEducation , noOfYearsOfService;
    private Calendar calendar;
    private YearPickerDialog yearPickerDialog;
    public static final String DATEPICKER_TAG = "datepicker";
    private TextInputLayout mDesignationView, mOrganizationView, mNoOfYearsServiceView, retirementAgeInputLayout1;
    private View view ;
    private  Family_details mFamilyDetailModel=null;
    private  ArrayList<String> getEmpty_flds;
    private int mPosition;
    public static int mPreviusPageNumber=0;
    private String mPreFamilyId ,nnoOfworkservices;
    private TextInputLayout mQualificationRelativeLayout;
    private String course,courseYear;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout,relative_finish_later,relative_done_arrow;
    private OnActivityBackPressedListener mCallBackListener;
    private OnAddTabChange onAddTabChange;
    private String mGenderFamily = "";
    private EditText mCalanderEdt;
    private CalendarEditText mNoOfYearsOfService;
    private CharacterEditText mFamilyName,mOtherQualificationEdt,mCurrentDesignation,mFamilyCurentOrganization;
    private int year=Calendar.getInstance().get(Calendar.YEAR);
    private Bundle argsDatePicker=new Bundle();
    private String personnalID,str_fdmss="",str_fdos="",str_fdrs="";
    private CalendarEditText mAgeEdt;
    private NumberEditText mexpectedLifeEdt, mretirementAgeEdt;
    private CustomTextInputLayout expectedAgeInputLayout1;
    public String str_mexpectedLifeEdt="",str_mretirementAgeEdt="";
    public HashMap<String,View> errorMapView=new HashMap<>();
    int i=0;
    private int currentdateFirsttime,currentmonthFirsttime,currentyearFirsttime;
    private boolean isFirstTime=false;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String mName;
    private  boolean isFirstTimeColor = false;
    private boolean isClicked;
    private LinearLayout bottom_barId;
    Boolean isSignUp = false;
    public HashMap<String,View> mandatoryMapView=new HashMap<>();
    private LinearLayout name_layout,family_details_age_layout,relationship_layout,martial_status_layout,
            layout_occupation,qualification_layout,current_designation_layout,current_organization_layout,
            noOfYearsOfService_layout,retirementAge_layout,expectedAge_layout;
    private RelativeLayout layout_gender;
    private LinearLayout bottom_bar_layout,bottom_bar_donelayout;

    ArrayList<String> formArray = new ArrayList<String>();
    private boolean isMandatory;


    HashMap<String, String> mandatoryPromptMapView = new HashMap<String, String>() {{
        put("Name","Enter your Family member's full name");
        put("Born Year","Enter the Family member's age");
        put("Relationship","Select your relationship with the Family member");
        put("occupation","Enter the Family member Occupation");
        put("Qualification","Enter the Family member qualification");
        put("current_designation","Enter Family member Current designation");
        put("current_org","Enter the Current organization");
        put("no_of_work_years","Enter the years of service with Current organization");
        put("Planned Retirement Age","Enter the reiterment age");
        put("Life Expectancy Age","Enter the Life expectacy age");
    }};

    public HashMap<String,View> mandatoryPromptPut=new HashMap<>();
    private TextView selectedTextView;

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    public static FamilyTabFragmentView newInstance(int pageNumber,
                                                    Family_details family_details,
                                                    OnAddTabChange onAddTabChange,
                                                    Boolean isSignUp,
                                                    ArrayList<String> formArray) {
        FamilyTabFragmentView fragment = new FamilyTabFragmentView();
        Bundle args = new Bundle();

        if (isSignUp != null) {
            args.putSerializable("isSignUp", isSignUp);
        }
        if(formArray!=null){
            args.putSerializable("formArray",formArray);
        }
        args.putInt("Count", pageNumber);
        args.putSerializable("family_details",family_details);
        fragment.onAddTabChange=onAddTabChange;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        callbackInterface =  this;
        try {

            if (getArguments() != null) {
                if (getArguments().containsKey("isSignUp"))
                    isSignUp = getArguments().getBoolean("isSignUp");
            }

            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }

        catch(Exception e)
        {}
        setRetainInstance(true);
        if (getArguments() != null) {
            if(getArguments().containsKey("family_details")) {
                mFamilyDetailModel = (Family_details) getArguments().getSerializable("family_details");
            }
            if(getArguments().containsKey("Count"))
                mPosition =  getArguments().getInt("Count");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            view = inflater.inflate(R.layout.fragment_family_detail_tabview, container, false);
            mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
            mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
            mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
            mleftRelativeLayout.setOnClickListener(this);
            mcenterRelativeLayout.setOnClickListener(this);
            mRightRelativeLayout.setOnClickListener(this);

            relative_finish_later= view.findViewById(R.id.relative_finish_later);
            relative_done_arrow= view.findViewById(R.id.relative_done_arrow);
            relative_finish_later.setOnClickListener(this);
            relative_done_arrow.setOnClickListener(this);

            name_layout= view.findViewById(R.id.name_layout);
            mandatoryMapView.put("Name",name_layout);
            layout_gender = view.findViewById(R.id.layout_gender);
            mandatoryMapView.put("Gender",layout_gender);

            family_details_age_layout= view.findViewById(R.id.family_details_age_layout);
            mandatoryMapView.put("Born Year",family_details_age_layout);

            relationship_layout= view.findViewById(R.id.relationship_layout);
            mandatoryMapView.put("Relationship",relationship_layout);
            martial_status_layout = view.findViewById(R.id.martial_status_layout);
            mandatoryMapView.put("marital_status",martial_status_layout);
            layout_occupation= view.findViewById(R.id.layout_occupation);
            mandatoryMapView.put("occupation",layout_occupation);
            qualification_layout= view.findViewById(R.id.qualification_layout);
            mandatoryMapView.put("Qualification",qualification_layout);
            current_designation_layout= view.findViewById(R.id.current_designation_layout);
            mandatoryMapView.put("current_designation",current_designation_layout);
            current_organization_layout= view.findViewById(R.id.current_organization_layout);
            mandatoryMapView.put("current_org",current_organization_layout);
            noOfYearsOfService_layout= view.findViewById(R.id.noOfYearsOfService_layout);
            mandatoryMapView.put("no_of_work_years",noOfYearsOfService_layout);

            retirementAge_layout= view.findViewById(R.id.retirementAge_layout);
            mandatoryMapView.put("Dependant Planned Retirement Age",retirementAge_layout);

            expectedAge_layout= view.findViewById(R.id.expectedAge_layout);
            mandatoryMapView.put("Dependant Life Expectancy Age",expectedAge_layout);

            bottom_barId= view.findViewById(R.id.bottom_barId);
            UtileKit.mandatoryFieldLinearLayout(isSignUp,bottom_barId);

            bottom_bar_donelayout= view.findViewById(R.id.bottom_bar_donelayout);
            UtileKit.mandatoryFieldDoneLayout(isSignUp,bottom_bar_donelayout);

            mFamilyNameParentview = view.findViewById(R.id.nameditparentView);
            mFamilyName = view.findViewById(R.id.family_details_name_Edt);
            errorMapView.put("Name",mFamilyName);
            mandatoryPromptPut.put("Name",mFamilyName);
            UtileKit.nameCharacterOnly(mFamilyName);
            mFamilyName.setHintText(getString(R.string.hint_family_first_name), ((TextInputLayout) (mFamilyName.getParent()).getParent()));


            mAgeParentEdt = view.findViewById(R.id.ageditparentView);
            mAgeEdt = view.findViewById(R.id.family_details_ageViewId);
            errorMapView.put("Born Year",mAgeEdt);
            mandatoryPromptPut.put("Born Year",mAgeEdt);

            mCurrentDesignation = view.findViewById(R.id.family_current_designation_Spinner);
            errorMapView.put("current_designation",mCurrentDesignation);
            mandatoryPromptPut.put("current_designation",mCurrentDesignation);
            mCurrentDesignation.setHintText(getString(R.string.hint_family_current_decignation), ((TextInputLayout) (mCurrentDesignation.getParent()).getParent()));

            mFamilyCurentOrganization = view.findViewById(R.id.family_details_current_organization_Edt);
            errorMapView.put("current_org",mFamilyCurentOrganization);
            mandatoryPromptPut.put("current_org",mFamilyCurentOrganization);
            mFamilyCurentOrganization.setHintText(getString(R.string.hint_family_current_organization), ((TextInputLayout) (mFamilyCurentOrganization.getParent()).getParent()));

            mNoOfYearsOfService = view.findViewById(R.id.family_details_no_of_year_service_Txt);
            errorMapView.put("no_of_work_years",mNoOfYearsOfService);
            mandatoryPromptPut.put("no_of_work_years",mNoOfYearsOfService);

            editTextDrawableClick(mAgeEdt);
            editTextDrawableClicknoofYear(mNoOfYearsOfService);
            mQualificationRelativeLayout= view.findViewById(R.id.otherQualificattionInputLayout);


            mexpectedLifeEdt = view.findViewById(R.id.personal_life_expect_age_Spinner1);
            errorMapView.put("Dependant Life Expectancy Age",mexpectedLifeEdt);
            mandatoryPromptPut.put("Dependant Life Expectancy Age",mexpectedLifeEdt);
            mexpectedLifeEdt.setHintText(getString(R.string.hint_family_life_expec), ((TextInputLayout) (mexpectedLifeEdt.getParent()).getParent()));

            mretirementAgeEdt = view.findViewById(R.id.personal_planned_retirement_age_Spinner1);
            errorMapView.put("Dependant Planned Retirement Age",mretirementAgeEdt);
            mandatoryPromptPut.put("Dependant Planned Retirement Age",mretirementAgeEdt);
            mretirementAgeEdt.setHintText(getString(R.string.hint_family_retirement), ((TextInputLayout) (mretirementAgeEdt.getParent()).getParent()));

            expectedAgeInputLayout1 = view.findViewById(R.id.expectedAgeInputLayout1);
            /**
             *   Spinner Text view Initialization
             * */
            mRelationShipSpinner = view.findViewById(R.id.family_details_relationship_Spinner);
            errorMapView.put("Relationship",mRelationShipSpinner);
            mandatoryPromptPut.put("Relationship",mRelationShipSpinner);
            mRelationShipSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });
            mMartialStatusSpinner = view.findViewById(R.id.family_details_marital_status_Spinner);
            errorMapView.put("marital_status",mMartialStatusSpinner);
            mandatoryPromptPut.put("marital_status",mMartialStatusSpinner);

            mMartialStatusSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });
            mOccupationSpinner = view.findViewById(R.id.family_details_occupation_Spinner);
            errorMapView.put("occupation",mOccupationSpinner);
            mandatoryPromptPut.put("occupation",mOccupationSpinner);
            mOccupationSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });

            mEducationLevel1= view.findViewById(R.id.family_details_other_qualificaspiner);
            errorMapView.put("Qualification",mEducationLevel1);
            mandatoryPromptPut.put("Qualification",mEducationLevel1);
            mEducationLevel1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });

            mEducationLevel2= view.findViewById(R.id.family_details_other_qualificaspiner2);
            mEducationLevel2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });


            mEducationLevel3= view.findViewById(R.id.family_details_other_qualificaspiner3);
            mEducationLevel3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClicked=true;
                return false;
            }
        });
            mOtherQualificationEdt = view.findViewById(R.id.personal_details_other_qualifica_Edt);
            mPersonalCourseArray = getResources().getStringArray(R.array.course_stage);
            mPersonalQualificationArray = getResources().getStringArray(R.array.education);
            setSpinnerAdapter(mEducationLevel1, mPersonalQualificationArray);

            setSpinnerAdapter(mEducationLevel2, mPersonalCourseArray);
            mPersonalYearCourseArray = getResources().getStringArray(R.array.noofyear_course_stage);
            setSpinnerAdapter(mEducationLevel3, mPersonalYearCourseArray);


            FloatingActionButton fab = view.findViewById(R.id.fab);
            mMaritialStatusArray = getResources().getStringArray(R.array.maritalstatus);
            setSpinnerAdapter(mMartialStatusSpinner, mMaritialStatusArray);
            setSpinnerAdapter(mMartialStatusSpinner, mMaritialStatusArray);
            mRelationShipArray = getResources().getStringArray(R.array.relationship);

            setSpinnerAdapter(mRelationShipSpinner, mRelationShipArray);


            mOccupationArray = getResources().getStringArray(R.array.occupation);
            setSpinnerAdapter(mOccupationSpinner, mOccupationArray);
            /**
             *    RadioButton  view Initialization
             * */
            mgenderRadioGroupe = view.findViewById(R.id.family_details_gender_RadioRg);
            mgenderMaleRadioBtn = view.findViewById(R.id.family_details_gender_yes_RadioBtn);
            mgenderFemaleRadioBtn = view.findViewById(R.id.family_details_gender_no_RadioBtn);
            /**
             *     Hide View on Student And Home
             *
             */
            mDesignationView = view.findViewById(R.id.family_current_designation_view);
            mOrganizationView = view.findViewById(R.id.family_current_organization_view);
            mNoOfYearsServiceView = view.findViewById(R.id.family_noOfYearsOfService_view);
            retirementAgeInputLayout1 = view.findViewById(R.id.retirementAgeInputLayout1);
        //    mgenderMaleRadioBtn.setChecked(true);
         //   mgenderFemaleRadioBtn.setChecked(false);
            mgenderRadioGroupe.setOnCheckedChangeListener(this);


            mexpectedLifeEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                expectedAgeInputLayout1.setErrorEnabled(false);
                expectedAgeInputLayout1.setError(null);
                try {
                    if (mretirementAgeEdt.getText().toString().length() > 0) {
                        int age = Integer.parseInt(mretirementAgeEdt.getText().toString());
                        int lifeExpAge = Integer.parseInt(mexpectedLifeEdt.getText().toString());
                        if (lifeExpAge < age) {
                            expectedAgeInputLayout1.setError("Life expectancy should be greater than Retirement age");
                        }
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if(mretirementAgeEdt.getText().toString().length()>0) {
                        int age = Integer.parseInt(mretirementAgeEdt.getText().toString());
                        int lifeExpAge = Integer.parseInt(mexpectedLifeEdt.getText().toString());
                        if (lifeExpAge < age) {
                            expectedAgeInputLayout1.setError("Life expectancy should be greater than Retirement age");
                        }
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });


        mretirementAgeEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                expectedAgeInputLayout1.setErrorEnabled(false);
                expectedAgeInputLayout1.setError(null);
                try {
                    if (mretirementAgeEdt.getText().toString().length() > 0) {
                        int age = Integer.parseInt(mretirementAgeEdt.getText().toString());
                        int lifeExpAge = Integer.parseInt(mexpectedLifeEdt.getText().toString());
                        if (lifeExpAge < age) {
                            expectedAgeInputLayout1.setError("Life expectancy should be greater than Retirement age");
                        }
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if(mretirementAgeEdt.getText().toString().length()>0) {
                        int age = Integer.parseInt(mretirementAgeEdt.getText().toString());
                        int lifeExpAge = Integer.parseInt(mexpectedLifeEdt.getText().toString());
                        if (lifeExpAge < age) {
                            expectedAgeInputLayout1.setError("Life expectancy should be greater than Retirement age");
                        }
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        mFamilyName.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mFamilyNameParentview.setErrorEnabled(false);
                    mFamilyNameParentview.setError(null);
                }
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                    // TODO Auto-generated method stub
                }
                @Override
                public void afterTextChanged(Editable s) {
                    mFamilyNameParentview.setErrorEnabled(false);
                    mFamilyNameParentview.setError(null);

                   // validateEditText(s);
                }
            });
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveInputValue1();


                }
            });
            FamilyDetailFragment.mAddTabView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(FamilyDetailFragment.mFamilyDetailModel!=null) {
                        if (FamilyDetailFragment.mFamilyDetailModel.getData().getFamily_details().size() != mPosition) {
                            //Log.e("selcted Tab", "tab" + FamilyDetailFragment.mFamilyDetailModel.getData().getFamily_details().size() + "posi" + "" + (mPosition) + "Id" + FamilyDetailFragment.mFamilyDetailModel.getData().getFamily_details().get(mPosition).getName()
//                                    + FamilyDetailFragment.mFamilyDetailModel.getData().getFamily_details().get(mPosition).getId());

                            mfamilyId = FamilyDetailFragment.mFamilyDetailModel.getData().getFamily_details().get(mPosition).getId();
                        } else {
                            mfamilyId = null;
                        }
                    }else
                    {
                        mfamilyId = null;
                    }
                    mPreFamilyId=mfamilyId;
                    saveInputValue(mPosition,true);

                }
            });
            FamilyDetailFragment.mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                        if (tab.getPosition() == (FamilyDetailFragment.mTabLayout.getTabCount()-1)) {

                            FamilyDetailFragment. mAddTabView.setVisibility(View.VISIBLE);
                        } else {
                            FamilyDetailFragment.mAddTabView.setVisibility(View.VISIBLE);

                        }

                    try {
                        if (FamilyDetailFragment.mFamilyDetailModel != null) {
                            if (FamilyDetailFragment.mFamilyDetailModel.getData().getFamily_details().size() > tab.getPosition()) {
                                //Log.e("selcted Tab", "tab" + FamilyDetailFragment.mFamilyDetailModel.getData().getFamily_details().size() + "posi" + "" + (tab.getPosition()) + "Id" + FamilyDetailFragment.mFamilyDetailModel.getData().getFamily_details().get(tab.getPosition()).getName()
//                                        + FamilyDetailFragment.mFamilyDetailModel.getData().getFamily_details().get(tab.getPosition()).getId());

                                mfamilyId = FamilyDetailFragment.mFamilyDetailModel.getData().getFamily_details().get(tab.getPosition()).getId();
                            } else {
                                mfamilyId = null;
                            }
                        }

                    } catch (Exception e) {
                        mfamilyId = null;
                        e.printStackTrace();
                    }


                    if(mPreviusPageNumber!=tab.getPosition()) {
                        if (mPosition == mPreviusPageNumber) {
                            try {
                                if(FamilyDetailFragment.mFamilyDetailModel.getData().getFamily_details().size()>mPreviusPageNumber)
                                mPreFamilyId = FamilyDetailFragment.mFamilyDetailModel.getData().getFamily_details().get(mPreviusPageNumber).getId();
                                else
                                    mPreFamilyId=null;
                            } catch (Exception e) {
                                mPreFamilyId = null;
                                e.printStackTrace();
                            }
                            saveInputValue(mPreviusPageNumber, false);
                        }
                    }
                }
                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    if(tab!=null)
                    mPreviusPageNumber= tab.getPosition();
                }
                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });
            if(mFamilyDetailModel!=null)
            {
                loadValue(mFamilyDetailModel);
            }


        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(isSignUp==true) {
            for (String key : mandatoryMapView.keySet()) {
                mandatoryMapView.get(key).setVisibility(View.GONE);
            }
        }

        if (getArguments() != null) {
            if (getArguments().containsKey("formArray")) {
                formArray = (ArrayList<String>) getArguments().getSerializable("formArray");
                emptyErrorValidationMandatory(formArray);



            }
        }

    }

    private void loadValue(Family_details mFamilyDetailModel) {
        if (UtileKit.validateObjectValues(mFamilyDetailModel.getName())) {
            mFamilyName.setText(mFamilyDetailModel.getName());
        } else {
        }
        if (UtileKit.validateObjectValues(mFamilyDetailModel.getEducation())) {
            mOtherQualificationEdt.setText(mFamilyDetailModel.getEducation());
        } else {
        }
        if (UtileKit.validateObjectValues(mFamilyDetailModel.getCurrent_designation())) {
            mCurrentDesignation.setText(mFamilyDetailModel.getCurrent_designation());
        } else {
        }
        if (UtileKit.validateObjectValues(mFamilyDetailModel.getCurrent_org())) {
            mFamilyCurentOrganization.setText(mFamilyDetailModel.getCurrent_org());
        } else {
        }


        if (UtileKit.validateObjectValues(mFamilyDetailModel.getDependant_life_expectancy_age())) {
            mexpectedLifeEdt.setText(mFamilyDetailModel.getCurrent_org());
        } else {
        }
        if (UtileKit.validateObjectValues(mFamilyDetailModel.getDependant_retirement_age())) {
            mretirementAgeEdt.setText(mFamilyDetailModel.getCurrent_org());
        } else {
        }


        if(mFamilyDetailModel.getName()!=null ){
            mFamilyName.setText(mFamilyDetailModel.getName());
            mFamilyName.setSelection(mFamilyDetailModel.getName().length());
        }

        if(mFamilyDetailModel.getAge()!=null) {

            int onGoingYear = Calendar.getInstance().get(Calendar.YEAR);
            String dob = UtileKit.getTextFromObjects((mFamilyDetailModel.getAge()));
            //String DateOfbirt = "";
            if (UtileKit.validateObjectValues(dob)) {
                try {

                    mAgeEdt.setText(setDateFormat(dob));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }else{
            }
        }
        if(mFamilyDetailModel.getCurrent_designation()!=null)
        mCurrentDesignation.setText(mFamilyDetailModel.getCurrent_designation());
        if(mFamilyDetailModel.getCurrent_org()!=null)
        mFamilyCurentOrganization.setText(mFamilyDetailModel.getCurrent_org());
        if(mFamilyDetailModel.getNo_of_work_years()!=null) {
            nnoOfworkservices =mFamilyDetailModel.getNo_of_work_years();
            mNoOfYearsOfService.setText(mFamilyDetailModel.getNo_of_work_years());
        }

        if(mFamilyDetailModel.getDependant_life_expectancy_age()!=null){
            mexpectedLifeEdt.setText(mFamilyDetailModel.getDependant_life_expectancy_age());
        }
        if(mFamilyDetailModel.getDependant_retirement_age()!=null){
            mretirementAgeEdt.setText(mFamilyDetailModel.getDependant_retirement_age());
        }
        if(mFamilyDetailModel.getRelationship()!=null) {
            mRelationShipSpinner.setSelection(getSpinnerposition(mFamilyDetailModel.getRelationship(), mRelationShipArray));
        }
        if(mFamilyDetailModel.getMarital_status()!=null)
        mMartialStatusSpinner.setSelection(getSpinnerposition(mFamilyDetailModel.getMarital_status(),mMaritialStatusArray));


        if(mFamilyDetailModel.getOccupation()!=null) {
            mOccupationSpinner.setSelection(getSpinnerposition(mFamilyDetailModel.getOccupation(), mOccupationArray));

            if(mFamilyDetailModel.getRelationship()!=null){
                if(mFamilyDetailModel.getRelationship().equalsIgnoreCase("Spouse")){
                    if(mFamilyDetailModel.getOccupation().equalsIgnoreCase("HomeMaker")||mFamilyDetailModel.getOccupation().equalsIgnoreCase("Student")){
                        UtileKit.persistingPurplePathPref(getString(R.string.IsSpouseWorking),"No");
                    }else {
                        UtileKit.persistingPurplePathPref(getString(R.string.IsSpouseWorking),"Yes");
                    }
                    }
                }
            }
        if(mFamilyDetailModel.getGender()!=null)
        {
            if(mFamilyDetailModel.getGender().equalsIgnoreCase("M"))
        {
            mGenderFamily="M";
            mgenderMaleRadioBtn.setChecked(true);
        } else
            if(mFamilyDetailModel.getGender().equalsIgnoreCase("F"))
            {
                mgenderFemaleRadioBtn.setChecked(true);
                mGenderFamily="F";
            }
        }
        if(UtileKit.validateObjectValues(mFamilyDetailModel.getEducation())) {

            String qualification;
            String checkanyother = mFamilyDetailModel.getEducation();
            try {
                if (checkanyother.contains(",")) {
                    List<String> qualificationList = Arrays.asList(checkanyother.split(","));
                    qualification = qualificationList.get(0);
                    if (qualification.equalsIgnoreCase("Any Other")) {
                        mQualificationRelativeLayout.setVisibility(View.VISIBLE);
                        mEducationLevel2.setVisibility(View.GONE);
                        mEducationLevel3.setVisibility(View.GONE);
                        if(qualificationList.size()>1)
                        mOtherQualificationEdt.setText(""+qualificationList.get(1));

                    } else if (qualification.equalsIgnoreCase("UG")) {

                        if (UtileKit.validateObjectValues(qualificationList.get(1))) {
                            int coursestatus = getStringArraySpinnerposition(qualificationList.get(1), mPersonalCourseArray);
                            mEducationLevel2.setSelection(coursestatus);
                        }
                        if (UtileKit.validateObjectValues(qualificationList.get(2))) {
                            int courseYearStatus = getStringArraySpinnerposition(qualificationList.get(2), mPersonalYearCourseArray);
                            mEducationLevel3.setSelection(courseYearStatus);
                        }
                    } else if (qualification.equalsIgnoreCase("PG")) {
                        if (UtileKit.validateObjectValues(qualificationList.get(1))) {
                            int coursestatus = getStringArraySpinnerposition(qualificationList.get(1), mPersonalCourseArray);
                            mEducationLevel2.setSelection(coursestatus);

                        }
                        if (UtileKit.validateObjectValues(qualificationList.get(2))) {
                            int courseYearStatus = getStringArraySpinnerposition(qualificationList.get(2), mPersonalYearCourseArray);
                            mEducationLevel3.setSelection(courseYearStatus);
                        }
                    }
                } else {
                    qualification = mFamilyDetailModel.getEducation();
                }
                int status = getStringArraySpinnerposition(qualification,  mPersonalQualificationArray);
                mEducationLevel1.setSelection(status);
            }
            catch (Exception e){e.printStackTrace();}

        }

        // added emptyfields
        if(UtileKit.validateObjectValues(mFamilyDetailModel.getEmpty_flds()))
            emptyErrorValidation(mFamilyDetailModel.getEmpty_flds());
        // added emptyfields
    }

    private int getSpinnerposition(String value, String[] spinerlist){
        int pos=0;
        for(int i=0; i<spinerlist.length; i++){

            if(value.equalsIgnoreCase(spinerlist[i])) {
                pos=i+1;
            }
        }
        return pos;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        calendar = new GregorianCalendar();
        yearPickerDialog = YearPickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
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




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.family_details_relationship_Spinner:

                str_fdrs = mRelationShipSpinner.getSelectedItem().toString();
                if(position!=0){
                    if(!isFirstTimeColor&&isClicked) {
                        mRelationShipSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }

                if(str_fdrs.equalsIgnoreCase("Wife")||str_fdrs.equalsIgnoreCase("Husband")||str_fdrs.equalsIgnoreCase("Spouse")){
                    String statusMaritial = "Married";
                    if(statusMaritial!=null) {
                        mMartialStatusSpinner.setSelection(getSpinnerposition(statusMaritial, mMaritialStatusArray));
                        martial_status_layout.setVisibility(View.VISIBLE);
                        mMartialStatusSpinner.setVisibility(View.VISIBLE);
                    }
                }else{
                    martial_status_layout.setVisibility(View.GONE);
                    mMartialStatusSpinner.setVisibility(View.GONE);
                }

                String  relationShip="";
                if(position==0)
                    relationShip="";
                else
                    relationShip = mRelationShipArray[position-1];
                if(relationShip.equalsIgnoreCase("Spouse"))

                break;
            case R.id.family_details_marital_status_Spinner:

                if(position!=0){
                    if(!isFirstTimeColor&&isClicked) {
                        mMartialStatusSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }

                break;
            case R.id.family_details_occupation_Spinner:
                str_fdos = mOccupationSpinner.getSelectedItem().toString();



                if(position!=0){
                    if(!isFirstTimeColor&&isClicked) {
                        mOccupationSpinner.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }

                if(position==0)
                    mOccupationStatus="";
                else
                mOccupationStatus = mOccupationArray[position-1];
                boolean notWorking=mOccupationStatus.equalsIgnoreCase("Homemaker") || mOccupationStatus.equalsIgnoreCase("Student");
                String mRelationShip = mRelationShipSpinner.getSelectedItem().toString();

                if (notWorking) {
                    mDesignationView.setVisibility(View.GONE);
                    mOrganizationView.setVisibility(View.GONE);
                    mNoOfYearsServiceView.setVisibility(View.GONE);
                } else {
                    mDesignationView.setVisibility(View.VISIBLE);
                    mOrganizationView.setVisibility(View.VISIBLE);
                    mNoOfYearsServiceView.setVisibility(View.VISIBLE);
                }

                if(mRelationShip.equalsIgnoreCase("Spouse")){
                    if(notWorking){
                        UtileKit.persistingPurplePathPref(getString(R.string.IsSpouseWorking),"No");
                    }else if(!(mOccupationStatus.isEmpty())){
                        UtileKit.persistingPurplePathPref(getString(R.string.IsSpouseWorking),"Yes");
                    }
                }
                if(str_fdos.equalsIgnoreCase("Homemaker")){
                    retirementAgeInputLayout1.setVisibility(View.GONE);
                }

                break;
            case R.id.personal_life_expect_age_Spinner:
                break;
            case R.id.personal_planned_retirement_age_Spinner:
                break;

            case R.id.family_details_other_qualificaspiner2:

                 mEducationLevel2.getSelectedItem().toString();


                if(position!=0){
                    if(!isFirstTimeColor&&isClicked) {
                        mEducationLevel2.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }


                break;
            case R.id.family_details_other_qualificaspiner3:

                mEducationLevel3.getSelectedItem().toString();


                if(position!=0){
                    if(!isFirstTimeColor&&isClicked) {
                        mEducationLevel3.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }


                break;

            case R.id.family_details_other_qualificaspiner:
                mEducation = mEducationLevel1.getSelectedItem().toString();


                if(position!=0){
                    if(!isFirstTimeColor&&isClicked) {
                        mEducationLevel1.setBackgroundResource(R.drawable.spinner_background_green_arrow);
                        isClicked = false;
                    }
                }

                if (mEducation.equalsIgnoreCase("Any Other")) {
                    mQualificationRelativeLayout.setVisibility(View.VISIBLE);
                    mEducationLevel2.setVisibility(View.GONE);
                    mEducationLevel3.setVisibility(View.GONE);
                } else if (mEducation.equalsIgnoreCase("UG")) {
                    mEducationLevel2.setVisibility(View.VISIBLE);
                    mEducationLevel3.setVisibility(View.VISIBLE);
                    mQualificationRelativeLayout.setVisibility(View.GONE);
                } else if (mEducation.equalsIgnoreCase("PG")) {
                    mEducationLevel2.setVisibility(View.VISIBLE);
                    mEducationLevel3.setVisibility(View.VISIBLE);
                    mQualificationRelativeLayout.setVisibility(View.GONE);
                } else {
                    mQualificationRelativeLayout.setVisibility(View.GONE);
                    mEducationLevel2.setVisibility(View.GONE);
                    mEducationLevel3.setVisibility(View.GONE);
                }

        }
    }

    public void mysetChecked(RadioButton myYesRadioBtn, RadioButton myNoRadioBtn) {
        if (myYesRadioBtn.isChecked()) {
            UtileKit.getSwitchYesBtnView(myYesRadioBtn, myNoRadioBtn, mContext);
        } else {
            UtileKit.getSwitchNoBtnView(myYesRadioBtn, myNoRadioBtn, mContext);
        }
    }
    public void setListener(DialogNoyearInterface callbackInterface){
        this.callbackInterface=callbackInterface;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == mgenderMaleRadioBtn.getId()) {
            UtileKit.getSwitchYesBtnView(mgenderMaleRadioBtn, mgenderFemaleRadioBtn, mContext);
            mGenderFamily="M";

        } else if (checkedId == mgenderFemaleRadioBtn.getId()) {
            UtileKit.getSwitchNoBtnView(mgenderMaleRadioBtn, mgenderFemaleRadioBtn, mContext);
            mGenderFamily="F";
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                        showYearCalanderonClick(EdtText);
                        return true;
                }
                return false;
            }
        });
    }

    public void editTextDrawableClicknoofYear(final CalendarEditText EdtText) {

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
    public void edtTextonClick(CalendarEditText EdtText) {
        try {
             if (EdtText == mAgeEdt) {
                 showYearCalanderonClick(EdtText);


            }
            if (EdtText == mNoOfYearsOfService) {
                setEdtText(mNoOfYearsOfService);
                String Dob = "Years of Service";
                String age = " Years of Service";
                String years=EdtText.getText().toString();
                String concactDDMMYY= null;
                if(years!= null && !years.isEmpty()) {
                    if (years.length() <= 3) {
                        String date = getcurrentYear(years);
                        concactDDMMYY = currentdateFirsttime + "-" + currentmonthFirsttime + "-" + date;
                    } else if(years.length() > 3 && years.length() <= 10 || years.length() <=8){

                        concactDDMMYY =  years;
                    }else{
                        concactDDMMYY = currentdateFirsttime + "-" + currentmonthFirsttime + "-" + years;
                    }
                    if (concactDDMMYY != null) {
                        CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                                "Years of Service", Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, "1", concactDDMMYY);
                        mcalenderTabs.show(getFragmentManager(), "Years of Service");
                    }
                }else{
                    CalenderTabs mcalenderTabs = CalenderTabs.newInstance(this,
                            "Years of Service", Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, "1", "");
                    mcalenderTabs.show(getFragmentManager(), "Years of Service");
                }






            }
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
        }
    }
    private void shownoOFYearsDailog(String dob, String age, String servicesText) {

        Log.i("PersonalDetailFragment", "servicesText  " +dob + age + servicesText);
        DailogYearOfservices dialogFragment = DailogYearOfservices.newInstance(callbackInterface,dob,age, servicesText) ;

        dialogFragment.show(getActivity().getFragmentManager(),"date dialog");
    }
    private void setEdtText(EditText edttext) {
        this.mCalanderEdt = edttext;
    }

    private void showYearCalanderonClick(EditText EdtText) {
        String yearToSet;

        String age=EdtText.getText().toString();

        CalenderTabs mcalenderTabs =  CalenderTabs.newInstance(this,
                "Select Date of Birth",true,false,false,"1", age);
        mcalenderTabs.show(getFragmentManager(),"Select Date of Birth");
    }

    @Override
    public void onYearSet(YearPickerDialog yearPickerDialog, int year) {
        String yearValue = String.valueOf(year);

        int onGoingYear = Calendar.getInstance().get(Calendar.YEAR);
        int Age = onGoingYear - year;
        mAgeEdt.setText(Integer.toString(Age));
       // UtileKit.edittextbordercolorchange(mAgeEdt);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void saveInputValue(int position, boolean addTabFlag) {
        try {
              {
                mName = UtileKit.getTextFromObjects(mFamilyName);

                int onGoingYear = Calendar.getInstance().get(Calendar.YEAR);
                String mAge = UtileKit.getTextFromObjects((mAgeEdt));
                String DateOfbirt ="";
                if (UtileKit.validateObjectValues(mAge)) {
                    try {
                       // DateOfbirt = String.valueOf(onGoingYear - Integer.parseInt(mAge));
                        DateOfbirt = setDateFormat(mAge);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                if (mOtherQualificationEdt.isShown()) {
                    String anyother =  "Any Other,";
                    mEducation = anyother.concat(mOtherQualificationEdt.getText().toString());
                }
                  else {
                    course = mEducationLevel2.getSelectedItem().toString();
                    courseYear = mEducationLevel3.getSelectedItem().toString();
                    if (UtileKit.validateObjectValues(course) && UtileKit.validateObjectValues(courseYear)) {
                        String education = course.concat(",").concat(courseYear);
                        mEducation = mEducation.concat(",").concat(education);
                    } else if (UtileKit.validateObjectValues(course)) {
                        String education = course.concat(",");
                        mEducation = mEducation.concat(",").concat(education);
                    }
                }

                String mRelationShip = mRelationShipSpinner.getSelectedItem().toString();
                String mMaterialSpiner = mMartialStatusSpinner.getSelectedItem().toString();
                String mOccupationText = mOccupationSpinner.getSelectedItem().toString();

                String mCurrentOrganationText = UtileKit.getTextFromObjects(mFamilyCurentOrganization);
                String mCurrentDesignationText = UtileKit.getTextFromObjects(mCurrentDesignation);
                String mNoofYearOfServiceText = UtileKit.getTextFromObjects(mNoOfYearsOfService);


                  String str_mexpectedLifeEdt = UtileKit.getTextFromObjects(mexpectedLifeEdt);
                  String str_mretirementAgeEdt = UtileKit.getTextFromObjects(mretirementAgeEdt);

                if (UtileKit.validateObjectValues(mName.trim())) {

                    try {

                        if (UtileKit.validateObjectValues(DateOfbirt)) {
                            if (mPreFamilyId == null) {
                                callFamilyDetailsService(UtileKit.getPersistedPurplePathPref("user_id"),
                                        mName,
                                        mRelationShip,
                                        DateOfbirt,
                                        mGenderFamily,
                                        mMaterialSpiner,
                                        mOccupationText,
                                        mEducation,
                                        mCurrentDesignationText,
                                        mCurrentOrganationText,
                                        mNoofYearOfServiceText,
                                        str_mexpectedLifeEdt,
                                        str_mretirementAgeEdt,addTabFlag);
                            } else {
                                callUpdateFamilyDetailsService(UtileKit.getPersistedPurplePathPref("user_id"),
                                        mName,
                                        mRelationShip,
                                        DateOfbirt,
                                        mGenderFamily,
                                        mMaterialSpiner,
                                        mOccupationText,
                                        mEducation,
                                        mCurrentDesignationText,
                                        mCurrentOrganationText,
                                        mNoofYearOfServiceText,
                                        str_mexpectedLifeEdt,
                                        str_mretirementAgeEdt,
                                        mPreFamilyId,
                                        addTabFlag);
                            }
                        } else {
                            mAgeParentEdt.setError("Please enter valid age");
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                   mFamilyNameParentview.setError("Please enter Name");
                }
            }
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }



    private void callUpdateFamilyDetailsService(String userId, String mName, String mRelationShip, String dateOfbirt, String mGender, String mMaterialSpiner, String mOccupationText, String mEducationText, String mCurrentDesignationText, String mCurrentOrganationText, String mNoofYearOfServiceText, String str_mexpectedLifeEdt, String str_mretirementAgeEdt,String fid,  final Boolean addTabFlag) {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<AddFamilyDetailModel> call = webServiceObj.addUpdateFamilyService(userId, mName, mRelationShip,dateOfbirt,dateOfbirt, mGender, mMaterialSpiner, mOccupationText, mEducationText, mCurrentDesignationText, mCurrentOrganationText, mNoofYearOfServiceText,str_mexpectedLifeEdt,str_mretirementAgeEdt,fid);
        call.enqueue(new Callback<AddFamilyDetailModel>() {
            @Override
            public void onResponse(Call<AddFamilyDetailModel> call, Response<AddFamilyDetailModel> response) {
                AddFamilyDetailModel verificationModel = response.body();
                if (verificationModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    if(addTabFlag)
                    {
                        callGetFamilyDetail(UtileKit.getPersistedPurplePathPref("user_id"));
                    }

                } else {
//                    UtileKit.alertDialog(verificationModel.getData().getMessage(),mContext);
                }
                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<AddFamilyDetailModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
            }
        });
    }
    private void callUpdateFamilyDetailsService1(String userId, String mName, String mRelationShip, String dateOfbirt, String mGender, String mMaterialSpiner, String mOccupationText, String mEducationText, String mCurrentDesignationText, String mCurrentOrganationText, String mNoofYearOfServiceText, String str_mexpectedLifeEdt,String str_mretirementAgeEdt,String fid, final boolean addTabFlag) {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);

        Log.d("ji","rightexpe"+str_mexpectedLifeEdt);
        Log.d("ji","rightretiii"+str_mretirementAgeEdt);
        Call<AddFamilyDetailModel> call = webServiceObj.addUpdateFamilyService(userId, mName, mRelationShip,dateOfbirt, dateOfbirt, mGender, mMaterialSpiner, mOccupationText, mEducationText, mCurrentDesignationText, mCurrentOrganationText, mNoofYearOfServiceText,str_mexpectedLifeEdt,str_mretirementAgeEdt,fid);
        call.enqueue(new Callback<AddFamilyDetailModel>() {
            @Override
            public void onResponse(Call<AddFamilyDetailModel> call, Response<AddFamilyDetailModel> response) {
                AddFamilyDetailModel verificationModel = response.body();
                if (verificationModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    addFragmenttoStack(new GoalsListFragment());

                } else {
//                    UtileKit.alertDialog(verificationModel.getData().getMessage(),mContext);
                    UtileKit.intitializeAlertDialog(
                            verificationModel.getData().getMessage(),
                            mContext);
                }
                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<AddFamilyDetailModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
            }
        });
    }
    private void callGetFamilyDetail(String userId) {


        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        Call<AddFamilyDetailModel> call = webServiceObj.callFamilyDetailsService(userId);
        call.enqueue(new Callback<AddFamilyDetailModel>() {
            @Override
            public void onResponse(Call<AddFamilyDetailModel> call, Response<AddFamilyDetailModel> response) {
                //Log.e("CallBack", " response is " + call.toString());
                UtileKit.dismisssSpinnerDialog();
                AddFamilyDetailModel categoryModel = response.body();
               //Family_details family_details=response.body();
                FamilyDetailFragment.mFamilyDetailModel=categoryModel;

                if (categoryModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    onAddTabChange.onTabAdd(categoryModel);
                }
                else {
//                    UtileKit.alertDialog(categoryModel.getData().getMessage(),mContext);
                    onAddTabChange.onTabAdd(null);
                }
            }
            @Override
            public void onFailure(Call<AddFamilyDetailModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
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
        }else if(view instanceof Spinner){
            switch(view.getId()){
                case R.id.family_details_relationship_Spinner:
                    String relation=mRelationShipSpinner.getSelectedItem().toString();
                    if(relation.equalsIgnoreCase("")){
                        mRelationShipSpinner.setSelection(0);
                        spinnerError(mRelationShipSpinner);
                        isMandatory=false;
                    }
                    break;
                case R.id.family_details_marital_status_Spinner:
                    String martial_status=mMartialStatusSpinner.getSelectedItem().toString();
                    if(martial_status.equalsIgnoreCase("")) {
                        mMartialStatusSpinner.setSelection(0);
                        spinnerError(mMartialStatusSpinner);
                        isMandatory=false;
                    }
                    break;
                case R.id.family_details_occupation_Spinner:
                    String occupation=mMartialStatusSpinner.getSelectedItem().toString();
                    if(occupation.equalsIgnoreCase("")) {
                        mOccupationSpinner.setSelection(0);
                        spinnerError(mOccupationSpinner);
                        isMandatory=false;
                    }
                    break;
                case R.id.family_details_other_qualificaspiner:
                    String other_qualification=mMartialStatusSpinner.getSelectedItem().toString();
                    if(other_qualification.equalsIgnoreCase("")) {
                        mEducationLevel1.setSelection(0);
                        spinnerError(mEducationLevel1);
                        isMandatory=false;
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


    private void callFamilyDetailsService(String userId, String mName, String mRelationShip, String dateOfbirt, String mGender, String mMaterialSpiner, String mOccupationText, String mEducationText, String mCurrentDesignationText, String mCurrentOrganationText, String mNoofYearOfServiceText, String str_mexpectedLifeEdt, String str_mretirementAgeEdt, final boolean addTabFlag) {
        //Log.e("Tag", "callFamilyDetailsService" + mName);
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        Call<AddFamilyDetailModel> call = webServiceObj.addFamilyService(UtileKit.getPersistedPurplePathPref("user_id"), mName, mRelationShip, dateOfbirt,dateOfbirt, mGender, mMaterialSpiner, mOccupationText, mEducationText, mCurrentDesignationText, mCurrentOrganationText, mNoofYearOfServiceText,str_mexpectedLifeEdt,str_mretirementAgeEdt);

        Log.d("hi","boss"+str_mexpectedLifeEdt);
        Log.d("hi","doss"+str_mexpectedLifeEdt);
        call.enqueue(new Callback<AddFamilyDetailModel>() {
            @Override
            public void onResponse(Call<AddFamilyDetailModel> call, Response<AddFamilyDetailModel> response) {
                AddFamilyDetailModel verificationModel = response.body();
                Log.d("hi","ssss"+ response.body());
                if (verificationModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
//                    if(addTabFlag)
                    {
                        if(null!=verificationModel.getData().getFamily_details()) {
                            if(!verificationModel.getData().getFamily_details().isEmpty())
                                mFamilyDetailModel = verificationModel.getData().getFamily_details().get(0);
                        }
                        callGetFamilyDetail(UtileKit.getPersistedPurplePathPref("user_id"));
                    }

                } else {
                    UtileKit.intitializeAlertDialog(verificationModel.getData().getMessage(),mContext);

//                    UtileKit.alertDialog(verificationModel.getData().getMessage(),mContext);
                }
                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<AddFamilyDetailModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
            }
        });

    }
    private void callFamilyDetailsService1(String userId, String mName, String mRelationShip, String dateOfbirt, String mGender, String mMaterialSpiner, String mOccupationText, String mEducationText, String mCurrentDesignationText, String mCurrentOrganationText, String mNoofYearOfServiceText, String str_mexpectedLifeEdt, String str_mretirementAgeEdt) {
//        //Log.e("Tag", "" + familyJson.toString());
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        Call<AddFamilyDetailModel> call = webServiceObj.addFamilyService(UtileKit.getPersistedPurplePathPref("user_id"), mName, mRelationShip,dateOfbirt, dateOfbirt, mGender, mMaterialSpiner, mOccupationText, mEducationText, mCurrentDesignationText, mCurrentOrganationText, mNoofYearOfServiceText,str_mexpectedLifeEdt,str_mretirementAgeEdt);
        call.enqueue(new Callback<AddFamilyDetailModel>() {
            @Override
            public void onResponse(Call<AddFamilyDetailModel> call, Response<AddFamilyDetailModel> response) {
                AddFamilyDetailModel verificationModel = response.body();
                if (verificationModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        addFragmenttoStack(new GoalsListFragment());


                } else {
                    UtileKit.intitializeAlertDialog(verificationModel.getData().getMessage(),mContext);
//                    UtileKit.alertDialog(verificationModel.getData().getMessage(),mContext);
                }
                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<AddFamilyDetailModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
            }
        });

    }
    private int getStringArraySpinnerposition(String value, String[] spinerlist){
        int pos=0;
        for(int i=0; i<spinerlist.length; i++){
            if(value.equalsIgnoreCase(spinerlist[i])) {
                pos=i+1;
            }
        }
        return pos;
    }

    public void saveInputValue1() {
        String mName = UtileKit.getTextFromObjects(mFamilyName);

        int onGoingYear = Calendar.getInstance().get(Calendar.YEAR);
        String mAge = UtileKit.getTextFromObjects((mAgeEdt));
        String DateOfbirt="";
        if(UtileKit.validateObjectValues(mAge))DateOfbirt =setDateFormat(mAge);
        String mRelationShip = mRelationShipSpinner.getSelectedItem().toString();
        String mMaterialSpiner = mMartialStatusSpinner.getSelectedItem().toString();
        String mOccupationText = mOccupationSpinner.getSelectedItem().toString();

        if(mRelationShip.equalsIgnoreCase("Spouse")){
            if(mOccupationText.equalsIgnoreCase("HomeMaker")||mOccupationText.equalsIgnoreCase("Student")){
                UtileKit.persistingPurplePathPref(getString(R.string.IsSpouseWorking),"No");
            }else if(!(mOccupationText.isEmpty())){
                UtileKit.persistingPurplePathPref(getString(R.string.IsSpouseWorking),"Yes");
            }
        }
        if (mOtherQualificationEdt.isShown()) {
            String anyother =  "Any Other,";
            mEducation = anyother.concat(mOtherQualificationEdt.getText().toString());
        }
        else {
           // mOtherQualificationEdt.setHintTextEmptyError();
        }
        course = mEducationLevel2.getSelectedItem().toString();
        courseYear = mEducationLevel3.getSelectedItem().toString();


        if(UtileKit.validateObjectValues(course) &&  UtileKit.validateObjectValues(courseYear)){
            String education = course.concat(",").concat(courseYear);
            mEducation =  mEducation.concat(",").concat(education);
        }else if(UtileKit.validateObjectValues(course)){
            String education = course.concat(",");
            mEducation = mEducation.concat(",").concat(education);
        }

        String mCurrentOrganationText = UtileKit.getTextFromObjects(mFamilyCurentOrganization);
        String mCurrentDesignationText = UtileKit.getTextFromObjects(mCurrentDesignation);
        String mNoofYearOfServiceText = UtileKit.getTextFromObjects(mNoOfYearsOfService);

        String str_mexpectedLifeEdt = UtileKit.getTextFromObjects(mexpectedLifeEdt);
        String str_mretirementAgeEdt = UtileKit.getTextFromObjects(mretirementAgeEdt);


        if (UtileKit.validateObjectValues(mName)) {
            try {

                if (UtileKit.validateObjectValues(DateOfbirt)) {
                    if (mfamilyId == null)
                    {
                        callFamilyDetailsService1(UtileKit.getPersistedPurplePathPref("user_id"),
                                mName,
                                mRelationShip,
                                DateOfbirt,
                                mGenderFamily,
                                mMaterialSpiner,
                                mOccupationText,
                                mEducation,
                                mCurrentDesignationText,
                                mCurrentOrganationText,
                                mNoofYearOfServiceText,
                                str_mexpectedLifeEdt,
                                str_mretirementAgeEdt);
                } else {
                    callUpdateFamilyDetailsService1(UtileKit.getPersistedPurplePathPref("user_id"),
                            mName,
                            mRelationShip,
                            DateOfbirt,
                            mGenderFamily,
                            mMaterialSpiner,
                            mOccupationText,
                            mEducation,
                            mCurrentDesignationText,
                            mCurrentOrganationText,
                            mNoofYearOfServiceText,
                            str_mexpectedLifeEdt,
                            str_mretirementAgeEdt,
                            mfamilyId, false);
                }
            } else {
                    mAgeParentEdt.setError("please enter age");
//                    addFragmenttoStack(new GoalsListFragment());
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }

        } else {
                addFragmenttoStack(new GoalsListFragment());
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.relative_finish_later:{
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
            break;
            case R.id.relative_done_arrow:{

                //checkPromptMandatory();

                if(emptyErrorPromptValidation(formArray)){
                    mPreFamilyId=mfamilyId;
                    saveInputValue(mPosition,false);
                    mCallBackListener.onActivityBackPressed();
                }
               // emptyErrorPromptValidation(formArray);
            }
            break;
            case R.id.relative_left_arrow:
            {
                mPreFamilyId=mfamilyId;
                saveInputValue(mPosition,false);
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home:
            {
                mPreFamilyId=mfamilyId;
                saveInputValue(mPosition,false);
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
//                getActivity().finish();
            }
            break;
            case R.id.relative_right_arrow:
            {
                saveInputValue1();
            }
            break;
        }
    }

    private void checkPromptMandatory(){
        String DateOfbirt="";
        String mAge = UtileKit.getTextFromObjects((mAgeEdt));
        if(UtileKit.validateObjectValues(mAge))DateOfbirt =setDateFormat(mAge);
        mName = UtileKit.getTextFromObjects(mFamilyName);
        String mRelationShip = mRelationShipSpinner.getSelectedItem().toString();

        if(isSignUp==true){
            if(!mName.equalsIgnoreCase("")){
                if(UtileKit.validateObjectValues(DateOfbirt)){
                    if(!mRelationShip.equalsIgnoreCase("")){
                        mPreFamilyId=mfamilyId;
                        saveInputValue(mPosition,false);
                    }else {
                        spinnerError(mRelationShipSpinner);
                    }
                }else{
                    mAgeEdt.requestFocus();
                    ((TextInputLayout) (mAgeEdt.getParent()).getParent()).setError(getString(R.string.family_prompt_age));
                }
            }else {
                mFamilyName.requestFocus();
                ((TextInputLayout) (mFamilyName.getParent()).getParent()).setError(getString(R.string.family_prompt_name));
            }
        }
    }

    @Override
    public void upadateYearofServices(String yos) {
        Log.i("FamilyTabFragment", "upadateYearofServices " + yos);
        noOfYearsOfService= yos;
        mNoOfYearsOfService.setText(noOfYearsOfService);
        UtileKit.edittextbordercolorchange(mNoOfYearsOfService);
        UtileKit.edittextbordercolorchange(mAgeEdt);
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
    public void updateEditTextValue(String value, String title) {
        try {
            Log.i("FamilyTabFragment","TextValue "+ value + "title"+ title);
            if(title.equalsIgnoreCase("Years of Service")){
                mNoOfYearsOfService.setText(value);
                UtileKit.edittextbordercolorchange(mNoOfYearsOfService);

            }else {
                if (value.trim().length() != 0) {
                    mAgeParentEdt.setErrorEnabled(false);
                    mAgeParentEdt.setError(null);
                    mAgeEdt.setText(value);
                    UtileKit.edittextbordercolorchange(mAgeEdt);
                } else if (value.equals("")) {
                    mAgeEdt.setText("");
                    UtileKit.edittextbordercolorchange(mAgeEdt);
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
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


}