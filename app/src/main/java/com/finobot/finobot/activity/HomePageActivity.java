package com.finobot.finobot.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.numetriclabz.numandroidcharts.ChartData;
import com.purplepath.purplepath.AppManagement.AboutUs.AboutusFragment;
import com.purplepath.purplepath.AppManagement.FAQ.FaqFragment;
import com.purplepath.purplepath.AppManagement.Feedback.FeedbackFragment;
import com.purplepath.purplepath.AppManagement.Glossaries.GlossariesFragment;
import com.purplepath.purplepath.AppManagement.Knowledge.KnowledgeFragment;
import com.purplepath.purplepath.AppManagement.Links.LinksFragment;
import com.purplepath.purplepath.AppManagement.Payment.PaymentUpgradeSummary;
import com.purplepath.purplepath.AppManagement.Quiz.Ui.QuizFragmentListview;
import com.purplepath.purplepath.AppManagement.Survey.SurveyFragment;
import com.purplepath.purplepath.AppManagement.Survey.SurveyFragmentView;
import com.purplepath.purplepath.AppManagement.TermsAndConditions.TermsAndConditionFragment;
import com.purplepath.purplepath.AppManagement.privacy.PrivacyPolicyFragment;
import com.purplepath.purplepath.adapter.NavigationDrawerAdapter;
import com.purplepath.purplepath.alertprompt.personalprompt.model.PersonalPromptModel;
import com.purplepath.purplepath.alertprompt.personalprompt.model.dialog.PromptSugestionDialog;
import com.purplepath.purplepath.apputiles.CrashExceptionHandler;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assets.AssetsDetailsFragment;
import com.purplepath.purplepath.assets.model.AssetCategoriesLevelOne;
import com.purplepath.purplepath.assets.model.AssetCategoriesModel;
import com.purplepath.purplepath.assets.model.GetAssetModel;
import com.purplepath.purplepath.assets.model.GetAssetUserData;
import com.purplepath.purplepath.assetsanalysis.AssertanaysisMainPageFragment;
import com.purplepath.purplepath.assetsanalysis.fragment.AssetsAnalysisFragment;
import com.purplepath.purplepath.cashflowmanagmentchart.CashflowChartFragment;
import com.purplepath.purplepath.desiproAllModules.amortizationSchedule.AmortizationMainFragment;
import com.purplepath.purplepath.desiproAllModules.carBuyVsLease.ui.CarBuyVsLeaseFragment;
import com.purplepath.purplepath.desiproAllModules.depositcomparison.DepositFragment;
import com.purplepath.purplepath.desiproAllModules.goalaffordability.ui.GoalAffordabilityFragment;
import com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.ui.DesiproActivity;
import com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.HouseBuyVsRentActivity;
import com.purplepath.purplepath.desiproAllModules.loanComparison.LoanComparisonActivity;
import com.purplepath.purplepath.desiproAllModules.loanEligibility.view.LoanEligibility;
import com.purplepath.purplepath.desiproAllModules.timeValueOfMoney.TimeValueOfMoneyFragment;
import com.purplepath.purplepath.document.documentGridview.Documentgridfragment;
import com.purplepath.purplepath.emergencyfundAnalysis.model.EmergencyFundModel;
import com.purplepath.purplepath.expenseEDCOC.ExpenseTabMainFragment;
import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.GetExpensesDetailsModel;
import com.purplepath.purplepath.expensesanalysis.fragment.ExpensesAnalysisFragment;
import com.purplepath.purplepath.famlydetail.fragmentview.FamilyDetailFragment;
import com.purplepath.purplepath.financialratio.model.FinanceRatioModel;
import com.purplepath.purplepath.firebaseFcm.Config;
import com.purplepath.purplepath.firebaseFcm.NotificationUtils;
import com.purplepath.purplepath.fragments.EmailLoginFragment;
import com.purplepath.purplepath.fragments.HomePageFragment;
import com.purplepath.purplepath.fragments.PersonalDetailsFragment;
import com.purplepath.purplepath.goal.GetGoalsListModel;
import com.purplepath.purplepath.goal.GetGoalsUserData;
import com.purplepath.purplepath.goal.GoalsListFragment;
import com.purplepath.purplepath.goaltimeline.GoalTimeLineFragment;
import com.purplepath.purplepath.guideView.QuickAccessGuideView;
import com.purplepath.purplepath.incomechartdetail.IncomePieChartFragment;
import com.purplepath.purplepath.incomedetails.fragment.IncomeDetail;
import com.purplepath.purplepath.insurance.fragment.InsuranceDetailsFragment;
import com.purplepath.purplepath.insurance.model.GetInsuranceInputData;
import com.purplepath.purplepath.insurance.model.GetInsuranceModel;
import com.purplepath.purplepath.insuranceAnalysis.InsuranceAnalysis;
import com.purplepath.purplepath.liabilities.LiabilitiesTabViewFragment;
import com.purplepath.purplepath.model.AddDeviceIdModel;
import com.purplepath.purplepath.model.CardResponse;
import com.purplepath.purplepath.model.riskmodel.RiskDailyUpdateModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.quickMenu.GridViewAdapterMenu;
import com.purplepath.purplepath.quickMenu.QuickMenuInterface;
import com.purplepath.purplepath.quickMenu.models.GirdviewText;
import com.purplepath.purplepath.recommendation.recommendationTables.RecommendationTableViews;
import com.purplepath.purplepath.retirementbenefits.RetirementBenefitsFragment;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.riskAssesment.RiskAssesmentResultFragment;
import com.purplepath.purplepath.riskAssesment.RiskProfile;
import com.purplepath.purplepath.schedule.ScheduleFragment;
import com.purplepath.purplepath.taxfiling.TaxFileTwentySixForm;
import com.purplepath.purplepath.taxfiling.TaxFilingConformationPdf;
import com.purplepath.purplepath.taxfiling.TaxFilingUploadFileNew;
import com.purplepath.purplepath.taxfiling.TaxFilingUploadFileNewMultiple;
import com.purplepath.purplepath.taxfiling.uploadtaxfiles.TaxFileCheckListFragment;
import com.purplepath.purplepath.taxprepaid.TaxPrepaidFragment;
import com.purplepath.purplepath.user.MyAccount.MyAccount;
import com.purplepath.purplepath.user.editProfile.EditProfiles;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.apputiles.UtileKit.getPersistedPurplePathPref;
import static com.purplepath.purplepath.apputiles.UtileKit.validateObjectValues;


/**
 * Home Page View Created by dinesh on 10/05/16.
 */
public class HomePageActivity extends AppCompatActivity implements OnActivityBackPressedListener, QuickMenuInterface, Runnable,
        NavigationView.OnNavigationItemSelectedListener {

    //    int mdrawable[] = {
//            R.drawable.ic_menu_time_view,
//    };
    public static final String errorMessageInName = "Please enter your name ";
    public static final String errorMessageInChart = "No details to retrieve ";
    public static final String errorMessageInLiability = "Please enter the Liability name ";
    public static final String errorMessageInAssets = "Please enter the Asset name ";
    public static final String errorMessageInInsurance = "Please enter the Insurance name ";
    public static final String errorMessageInGoals = "Please enter Goal name";
    public static final String stringMessageError = "Are you sure want to delete ?";
    private static final int PERIOD = 20000;
    public static int checkGoalsId;
    public static int goallistsize;
    public static GetExpensesDetailsModel getExpensesDetailsModel;
    public static int expensesDetailsSize;
    public static int homeViewPagerPosition;
    public static boolean isClicked = true;
    private static ImageView belowView, clickMeId;
    final Handler handler = new Handler();
    private final String TAG = HomePageActivity.class.getSimpleName();
    public ArrayList<GetGoalsUserData> getGoalsUserData = new ArrayList<GetGoalsUserData>();
    public HorizontalScrollView mDropdownLayout;
    List<ChartData> value1 = new ArrayList<>(), value2 = new ArrayList<>();
    HomePageFragment homefragment;
    Boolean isSignUp = false;
    LayoutInflater inflater;
    View dialogView, dialogViewLogout;
    AlertDialog alertDialog, alertDialogLogout;
    String FragmentIdFromIntent, quickmenu = null;
    Context context;
    Fragment fragment;
    FinanceRatioModel getFinanceRatioModel;
    RiskDailyUpdateModel mRiskDailyUpdateModel;
    Dialog dialog;
    RatingBar mratingBar;
    Button mlater, mdone;
    boolean stopThread = false;
    Timer timer;
    TimerTask timerTask;
    Boolean isFirstTime;
    private QuickMenuInterface callbackInterface;
    private NavigationDrawerAdapter navigationAdapter;
    private DrawerLayout mNavigartiondrawer;
    private ActionBarDrawerToggle mActionBarToggle;
    private GetGoalsListModel getGoalsListModel;
    private EmergencyFundModel emergencyFundModel;
    private HashMap<String, List<String>> _listDataChild;
    private Context mContext;
    private CoordinatorLayout coordinatorLayout;
    private long lastPressedTime;
    private TextView mTitle;
    private Toolbar mToolbar;
    private ArrayList<GetAssetUserData> getAssetUserData;
    private GetAssetModel getAssetModel;
    private GetInsuranceModel getInsuranceModel;
    private ArrayList<GetInsuranceInputData> getInsuranceUserData;
    private int height, width;
    private AssetCategoriesModel assetCategoriesModel;
    private ArrayList<AssetCategoriesLevelOne> assetCategoriesLevelOneListData = new ArrayList<AssetCategoriesLevelOne>();
    private String assetType;
    private ArrayList<AssetCategoriesLevelOne> assetCategoriesLevelOneList = new ArrayList<AssetCategoriesLevelOne>();
    private ArrayList<GirdviewText> gridQuickmenuList;
    private ArrayList<GirdviewText> gridQuickmenuListinnerMenu;
    private ArrayList<String> childnameData;
    private ArrayList<String> childnamePlan;
    private ArrayList<String> childnamePlandecipro;
    private ArrayList<Integer> childnameDataImagedecipro;
    private ArrayList<Integer> childnameDataImage;
    private ArrayList<Integer> childnamePlanImage;
    private Stack<Fragment> fragmentStack;
    private FragmentManager fragmentManager;
    private FaqFragment faqfrag;
    private GlossariesFragment glo;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private int count = 0;

    private TextView nav_name;
    NavigationView navigationView;

    private ArrayList<Integer> mdrawable;
    public static Bus bus;

    private int STORAGE_PERMISSION_CODE = 1;
    private int STORAGE_PERMISSION_CODE_MULTIPLlE = 2;
    private int STORAGE_PERMISSION_CODE_26AS = 3;
    private CardResponse cardPermission;
    public static String plan, file;


    private String childnamemenu[] = {"My Data ", "My Action\nPlan", "My Timeline", "My Asset \n Allocation", "My Cash\nFlow", "My Risk \nProfile", "DeciPro  ",
            "DigiVault", " PayTracker"};

    //    *//**//**
//     * Animation pull up View to Top
//     * Older versions of android (pre API 21) cancel animations for views with a height of 0.
//     *
//     * @param v
//     *//**//*
    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
        isClicked = true;
        clickMeId.setImageResource(R.drawable.ic_menu_drop_dwon);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // code to check if the home page is launched from notification - added by Pratheep
        if (intent.hasExtra("showSchedule")) {
            String show_shcedule = intent.getStringExtra("showSchedule");
            if (show_shcedule.equalsIgnoreCase("showSchedulePage")) {
                addFragmentToActivity(new ScheduleFragment());
                //addFragmentToActivity( ScheduleFragment.newSingleton_Instance());
            }
        } else if (intent.hasExtra("flag")) {
            String flags = intent.getStringExtra("flag");
            Log.d("flagsss", "flagsss" + flags);
            if (flags.equalsIgnoreCase("Tax Filing")) {
                addFragmentToActivity(new TaxFileCheckListFragment());
            } else if (flags.equalsIgnoreCase("ITR-1")) {
                addFragmentToActivity(new TaxFilingConformationPdf());
            }
        }


        //prompt page redirect
//        else if (intent.hasExtra("flag")&&intent.hasExtra("page_name")) {
//
//            String prompt = intent.getStringExtra("flag");
//            String page_name = intent.getStringExtra("page_name");
//
//            Log.d("promptss", "promptss"+prompt);
//            Log.d("page_namess", "page_namess"+page_name);
//
//            //PROMPT
//            if (prompt.equalsIgnoreCase("prompt")) {
//                if(page_name.equalsIgnoreCase("Personal Details")) {
//                    addFragmentToActivity(new PersonalDetailsFragment());
//                }
//            }
//            //APNA
//            else if(prompt.equalsIgnoreCase("apna")){
//
//            }
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_home_page, menu);
        if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
            try {
                Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                m.setAccessible(true);
                m.invoke(menu, true);
                SharedPreferences bb123 = getSharedPreferences("my_prefscheck", 0);
                String m2 = bb123.getString("MIDonoff", "");

                //SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
                //String silu = pref.getString("off", "empty");
                MenuItem item = menu.findItem(R.id.menu_Survey);
                String sur = UtileKit.getPersistedPurplePathPref("survey_flag");
                Log.i("go", "ge" + sur);
                if (SurveyFragmentView.silakidum == 1) {
                    if (item != null)
                        item.setVisible(false);
                } else if (EmailLoginFragment.surch.equals("N")) {

                    if (item != null)
                        item.setVisible(true);
                } else if (EmailLoginFragment.surch.equals("Y")) {

                    if (item != null)
                        item.setVisible(false);
                }

                //callSurveyCheck(menu);
            } catch (NoSuchMethodException e) {
//                //Log.e("spcheck", "onMenuOpened", e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        Fragment fragment;
        fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        switch (item.getItemId()) {
            case R.id.menu_Settings:
              /*  intent = new Intent(HomePageActivity.this, SettingsActivity.class);
                startActivity(intent);*/
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                addFragmentToActivity(new SettingsActivity());

                break;

           /* case R.id.menu_AboutUs:
                fragment=new AboutusFragment();
                addFragmentToActivity(fragment);
                break;*/
            case R.id.menu_FAQ:

                fragment = new FaqFragment();
                //  fragment = new TaxFileChartConversation();
                if (!(currentFragment.getClass().equals(fragment.getClass())))
                    addFragmentToActivity(fragment);

                break;

            /*case R.id.menu_Vendor:
                fragment= new VendorFragment();
                if(!(currentFragment.getClass().equals(fragment.getClass())))
                addFragmentToActivity(fragment);
                break;*/

            case R.id.menu_Knowledge:
                fragment = new KnowledgeFragment();
                if (!(currentFragment.getClass().equals(fragment.getClass())))
                    addFragmentToActivity(fragment);
                break;
            case R.id.menu_editProfile:
                fragment = new EditProfiles();
                if (!(currentFragment.getClass().equals(fragment.getClass())))
                    addFragmentToActivity(fragment);
                break;
            case R.id.menu_Links:
                fragment = new LinksFragment();
                if (!(currentFragment.getClass().equals(fragment.getClass())))
                    addFragmentToActivity(fragment);
                break;
            case R.id.menu_FinoBotWebsite:
                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.finobot.in"));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "No application can handle this request." + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                //fragment=new FinobotFragment();
                // addFragmentToActivity(fragment);
                break;
            /*case R.id.menu_RateTheApp:
                fragment=new RatetheappFragment();
                addFragmentToActivity(fragment);
                break;*/
            case R.id.menu_RateTheApp:
                Intent ik = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.finobot.finobot&hl=en"));
                startActivity(ik);
                break;
            case R.id.menu_Survey:
                fragment = new SurveyFragment();
                if (!(currentFragment.getClass().equals(fragment.getClass())))
                    addFragmentToActivity(fragment);
                break;
            case R.id.menu_Quiz:
//                fragment=new QuizFragment();
                fragment = new QuizFragmentListview();
                if (!(currentFragment.getClass().equals(fragment.getClass())))
                    //do fragment transaction and add frag
                    addFragmentToActivity(fragment);

                break;
            case R.id.menu_Glossaries:
                fragment = new GlossariesFragment();
                if (!(currentFragment.getClass().equals(fragment.getClass())))
                    addFragmentToActivity(fragment);
                break;
            case R.id.menu_Notifications:
                fragment = new NotificationActivity();
                addFragmentToActivity(fragment);
                //startActivity(intent);
//                startActivityForResult(intent,11);
                break;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String taxfiling_flags = null;

        Thread.setDefaultUncaughtExceptionHandler(new CrashExceptionHandler(this, HomePageActivity.class));
        UtileKit.cretePrefAtHome(getBaseContext());
        UtileKit.cretefinobotPrefForFirsttime(getBaseContext());
        setContentView(R.layout.activity_mains);
        bus = new Bus(ThreadEnforcer.MAIN);
        bus.register(this);
        cardViewPermission();
        //live app id OLD
//         MobileAds.initialize(this,"ca-app-pub-6375703219723081~4541496738");//OLD id

        //live app id
//        MobileAds.initialize(this,"ca-app-pub-2076188111537488~4690439427");//live app id use for release
//        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");//dummy app id for test ads

        Intent intent = getIntent();

        Bundle bd = intent.getExtras();
        if (bd != null) {
            taxfiling_flags = bd.getString("taxfiling_flags");
            Log.d("taxfiling_flagrr", "taxfiling_flagrr" + taxfiling_flags);
        }


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mContext = HomePageActivity.this;
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
        mTitle = mToolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.app_name);
        context = this;
        setListenerQuickMenu(this);
        //nav_name=(TextView)findViewById(R.id.nav_name);
        mdrawable = new ArrayList<>();
        mdrawable.add(R.drawable.ic_my_data_icon);
        mdrawable.add(R.drawable.ic_my_actionplan);
        mdrawable.add(R.drawable.ic_my_timeline_icon);
        mdrawable.add(R.drawable.ic_asset_allocation_icon);
        if (UtileKit.getPersistedPurplePathBoolPref("Tax Cash Flow Chart")) {
            mdrawable.add(R.drawable.ic_my_cashflow_icon);
        } else {
            mdrawable.add(R.drawable.ic_my_cash_flow_tint);
        }
        mdrawable.add(R.drawable.ic_my_risk_profile_icon);
        mdrawable.add(R.drawable.ic_decipro_menu);

        if (UtileKit.getPersistedPurplePathBoolPref("DigiVault")) {
            mdrawable.add(R.drawable.ic_digit_icon);
        } else {
            mdrawable.add(R.drawable.ic_document_icon_tint);
        }

        mdrawable.add(R.drawable.ic_schedule_icon);
        gridQuickmenuList = generateQuickMenuList();
        childnameData = new ArrayList<>();
        childnameData.add("Personal Details");
        childnameData.add("Family Details");
        childnameData.add("Goal Details");
        childnameData.add("Income Details");
        childnameData.add("Expense Details");
        childnameData.add("Asset Details");
        childnameData.add("Liability Details");
        childnameData.add("Insurance Details");
        childnameData.add("Retirement Benefits");
        childnameData.add("Tax Prepaid");

        childnamePlan = new ArrayList<>();
        childnamePlan.add("Income Analysis");
        childnamePlan.add("Expense Analysis");
        childnamePlan.add("Assets Allocation Chart");
        childnamePlan.add("Emergency fund");
        childnamePlan.add("Cash Flow Management");
        childnamePlan.add(" Insurance Chart");
        childnamePlan.add("Tax Cash Flow Chart");
        childnamePlan.add(" Recommendation ");
        childnamePlan.add("Liabilities");

        childnamePlanImage = new ArrayList<>();

        childnamePlanImage.add(R.drawable.ic_income_chart_selat);
        childnamePlanImage.add(R.drawable.ic_expense_chart_selat);
        childnamePlanImage.add(R.drawable.ic_asset_chart_selat);
        childnamePlanImage.add(R.drawable.ic_emergency_fund_chart_selat);
        childnamePlanImage.add(R.drawable.ic_cash_flow_management_chart_selat);
        childnamePlanImage.add(R.drawable.ic_insurance_chart);
        childnamePlanImage.add(R.drawable.ic_tax_cash_flow_chart_selat);
        childnamePlanImage.add(R.drawable.ic_recommendation_menu);
        childnamePlanImage.add(R.drawable.ic_liability);

        childnameDataImage = new ArrayList<>();
        childnameDataImage.add(R.drawable.ic_personal_details_icon_selat);
        childnameDataImage.add(R.drawable.ic_family_details_icon_selat);
        childnameDataImage.add(R.drawable.ic_goals_icon_selat);
        childnameDataImage.add(R.drawable.ic_income_details_icon);
        childnameDataImage.add(R.drawable.ic_expense_details_icon_selat);
        childnameDataImage.add(R.drawable.ic_asst_icon_selat);
        childnameDataImage.add(R.drawable.ic_liability_icon_selat);
        childnameDataImage.add(R.drawable.ic_insurance_selat);
        childnameDataImage.add(R.drawable.ic_retirement_icon_selat);
        childnameDataImage.add(R.drawable.ic_tax_prepaid);

        childnameDataImagedecipro = new ArrayList<>();

        setTintimage("Home Loan Switch", childnameDataImagedecipro, R.drawable.ic_loan_calculation, R.drawable.ic_loan_calculation_tint);
        setTintimage("Amortization Schedule", childnameDataImagedecipro, R.drawable.ic_amortization_schedule, R.drawable.ic_amortization_schedule_tint);
        setTintimage("Loan Comparsion", childnameDataImagedecipro, R.drawable.ic_loan_comparison, R.drawable.ic_loan_comparison_tint);
        setTintimage("Deposit Comparison", childnameDataImagedecipro, R.drawable.ic_deposit_comparison, R.drawable.ic_deposit_comparison_tint);
        setTintimage("Time Value of Money", childnameDataImagedecipro, R.drawable.ic_time_value_money, R.drawable.ic_time_value_money_tint);
        setTintimage("Loan Eligibility", childnameDataImagedecipro, R.drawable.ic_loan_eligibility, R.drawable.ic_loan_eligibility_tint);
        setTintimage("Goal Affordability", childnameDataImagedecipro, R.drawable.ic_goal_affordability, R.drawable.ic_goal_affordability_tint);
        setTintimage("Home - Buy vs Rent", childnameDataImagedecipro, R.drawable.ic_buyvs_rent, R.drawable.ic_buyvs_rent_tint);

        childnameDataImagedecipro.add(R.drawable.ic_company_car);
        // childnameDataImagedecipro.add(R.drawable.ic_forreclosure_calculator);

        childnamePlandecipro = new ArrayList<>();
        childnamePlandecipro.add("Home Loan Switch");
        childnamePlandecipro.add("Amortization Schedule");
        childnamePlandecipro.add("Loan Comparison");
        childnamePlandecipro.add("Deposit Comparison");
        childnamePlandecipro.add("Time Value of Money");
        childnamePlandecipro.add("Loan Eligibility");
        childnamePlandecipro.add("Goal Affordability");
        childnamePlandecipro.add("Home - Buy vs Rent");
        childnamePlandecipro.add("Car - Buy vs Lease");
        // childnamePlandecipro.add("Foreclosure Calculator");
        calculateDisplayMetrics();

        try {
            Bundle extras = intent.getExtras();
            if (extras != null)
                isSignUp = extras.getBoolean("IsSignUp");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            homefragment = HomePageFragment.newInstance(isSignUp);
            fragmentTransaction.add(R.id.fragment_container, homefragment);
            if (!isFinishing())
                fragmentTransaction.commitAllowingStateLoss();
        }

        // code to check if the home page is launched from notification - added by Pratheep
        if (intent.hasExtra("showSchedule")) {
            String show_shcedule = intent.getStringExtra("showSchedule");
            if (show_shcedule.equalsIgnoreCase("showSchedulePage")) {
                addFragmentToActivity(new ScheduleFragment());
                // addFragmentToActivity( ScheduleFragment.newSingleton_Instance());
            }
        }


//        callGetGoalListService();
//        callGetExpensesService();
//        callEmergFundAnalyService();
//        callAddPersonalDetailsService();
        try {
//            String Token = FirebaseInstanceId.getInstance().getToken();
            final String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            Log.i("HomePageActivity", " token is " + android_id);
            final String user_id = getPersistedPurplePathPref("user_id");


            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    // checking for type intent filter
                    if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                        // gcm successfully registered
                        // now subscribe to `global` topic to receive app wide notifications
                        FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                        displayFirebaseRegId(android_id, user_id);
                    } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                        // new push notification is received
//                        String message = intent.getStringExtra("message");
//                        Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
//                        txtMessage.setText(message);
                    }
                }
            };

            displayFirebaseRegId(android_id, user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mNavigartiondrawer = (DrawerLayout) findViewById(R.id.drawer_layouts);
        mActionBarToggle = new ActionBarDrawerToggle(
                this, mNavigartiondrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mActionBarToggle.setDrawerIndicatorEnabled(true);
                collapse(mDropdownLayout);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mNavigartiondrawer.addDrawerListener(mActionBarToggle);
        mActionBarToggle.syncState();
        //ExpandableListView navigationView = (ExpandableListView) findViewById(R.id.nav_view);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView) hView.findViewById(R.id.nav_name);
        if (ServiceGenerator.IDENTIFY.equalsIgnoreCase("D")) {
            nav_user.setPaintFlags(nav_user.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        }
        TextView nav_customer_id = (TextView) hView.findViewById(R.id.customer_id);

        String getprefName = UtileKit.getPersistedPurplePathPref("name_services", null);
        String getCusName = UtileKit.getPersistedPurplePathPref("cust_id", null);
        //   String getCusName1 = UtileKit.getPersistedPurplePathPref("cust_id1",null);
        if (getprefName != null || getCusName != null) {
            try {
                nav_user.setText(getprefName);
                nav_customer_id.setText(getCusName);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        clickMeId = (ImageView) findViewById(R.id.clickmeId);
        mDropdownLayout = (HorizontalScrollView) findViewById(R.id.horizontalScroll);


//        navigationAdapter = new NavigationDrawerAdapter(this);
//        navigationView.setAdapter(navigationAdapter);
//        //  navigationView.setOnItemSelectedListener(new ExpandableListView.OnFocusChangeListener());
//        navigationView.setOnChildClickListener(
//                new ExpandableListView.OnChildClickListener() {
//                    @Override
//                    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
//                        mNavigartiondrawer.closeDrawers();
//
//                        if (i == 1) {
//                            mydatapan(i1);
//                        } else if (i == 2) {
//                            // myplanAnalysis(i1);
//                            mNavigartiondrawer.closeDrawers();
//
//                        } else if (i == 5) {
//                           /*Intent intent = new Intent(getParent(), SettingsActivity.class);
//                            startActivity(intent);*/
//                            addFragmentToActivity(new SettingsActivity());
//                        }
//
//                        return true;
//                    }
//                });
//        navigationView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView expandableListView, View view, int position, long l) {
//                boolean isGroupClicked = true;
//                Fragment fragment;
//                fragmentManager = getSupportFragmentManager();
//                Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
//                if (position == 0) {
//                    fragment = new MyAccount();
//                    if (!(currentFragment.getClass().equals(fragment.getClass())))
//                        addFragmentToActivity(fragment);
//                    isGroupClicked = false;
//                }
//   /*           else if (position == 1) {
////                    fragment=new PaymentViewPager();
//                    fragment=new PaymentWebView();
//                    addFragmentToActivity(fragment);
//                    isGroupClicked = false;
//                }*/
//
//                else if (position == 1) {
////                    fragment=new MyReport();
////                    addFragmentToActivity(fragment);
////                    isGroupClicked = false;
////                } else if(position == 2 ) {
////
////                    fragment=new UpgradeFragment();
////                    addFragmentToActivity(fragment);
////                    isGroupClicked = false;
////                }else if (position == 3) {
////                    fragment=new PaymentFragment();
////                    addFragmentToActivity(fragment);
////                    isGroupClicked = false;
////                }
////                else if (position == 4) {
//                    fragment = new AboutusFragment();
//                    if (!(currentFragment.getClass().equals(fragment.getClass())))
//                        addFragmentToActivity(fragment);
//                    isGroupClicked = false;
//                }
//
//                else if (position == 2) {
//                    fragment = new FeedbackFragment();
//                    if (!(currentFragment.getClass().equals(fragment.getClass())))
//                        addFragmentToActivity(fragment);
//                    isGroupClicked = false;
//                }  else if (position == 3) {
//                    fragment= TermsAndConditionFragment.newInstance(true);
//                    if(!(currentFragment.getClass().equals(fragment.getClass())))
//                        addFragmentToActivity(fragment);
//                    isGroupClicked = false;
//                }
//
//                else if (position == 4) {
//                    fragment= PrivacyPolicyFragment.newInstance(true);
//                    if(!(currentFragment.getClass().equals(fragment.getClass())))
//                        addFragmentToActivity(fragment);
//                    isGroupClicked = false;
//                } else if (position == 5) {
//                    fragment = new com.purplepath.purplepath.AppManagement.ContactUs.ContactusFragment();
//                    if (!(currentFragment.getClass().equals(fragment.getClass())))
//                        addFragmentToActivity(fragment);
//                    isGroupClicked = false;
//                } else if (position == 6) {
//                    //flag
//                    isGroupClicked = false;
//                    alertDialogLogout.show();
//                   /* isGroupClicked = true;
//                    HomePageActivity.getExpensesDetailsModel = null;
//                    HomePageActivity.expensesDetailsSize = 0;
//                    finish();
//                    Intent loginActivity = new Intent(view.getContext(), LoginandSignUpActivity.class);
//                    loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(loginActivity);*/
//
//                } else {
//                    isGroupClicked = false;
//                }
//                mNavigartiondrawer.closeDrawers();
//                return isGroupClicked;
//            }
//        });


        setDropDownView();


        String todayDate = UtileKit.getPersistedPurplePathPref("UserPrefDate");
        Calendar dt = Calendar.getInstance();
        String curentDate = Integer.toString(dt.get(Calendar.DATE));
        if (todayDate == null) {
            dailyupdateRisk();
//            callAlertPromptService();
            UtileKit.persistingPurplePathPref("UserPrefDate", curentDate);
        } else if (!todayDate.equalsIgnoreCase(Integer.toString(dt.get(Calendar.DATE)))) {
//            callAlertPromptService();
            dailyupdateRisk();
            UtileKit.persistingPurplePathPref("UserPrefDate", curentDate);
        }

        Handler mainHandler = new Handler(getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {

//                callGetGoalListService();
                callEmergFundAnalyService();
                // callGetAssetService();
//                callGetInsuranceService();
                if (StringUtils.isEmpty(UtileKit.getPersistedPurplePathPref("AssetCat"))) {
                    callAssetCategoriesService();
                }


            }
        };
        mainHandler.post(myRunnable);
        intitializeAlertDialog();

        try {
            quickmenu = UtileKit.getPersistedPurplePathPref("firsttimeLoginquickmenu");
            if (quickmenu == null) {
                expand(mDropdownLayout);
                UtileKit.persistingPurplePathPref("firsttimeLoginquickmenu", "quickmenuClosed");

            } else {
                collapse(mDropdownLayout);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (taxfiling_flags != null && taxfiling_flags.equalsIgnoreCase("Tax Filing")) {
            startFragment(new TaxFileCheckListFragment());
        }

        if (taxfiling_flags != null && taxfiling_flags.equalsIgnoreCase("ITR-1")) {
            startFragment(new TaxFilingConformationPdf());
        }


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment;
        fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);


        if (id == R.id.nav_profile) {

            fragment = new MyAccount();
            //fragment = new TaxFilingInitialConversation();
            if (!(currentFragment.getClass().equals(fragment.getClass())))
                addFragmentToActivity(fragment);


        } else if (id == R.id.nav_payment) {

            //fragment = new PaymentViewPager();
            fragment = new PaymentUpgradeSummary();
            if (!(currentFragment.getClass().equals(fragment.getClass())))
                addFragmentToActivity(fragment);

        } else if (id == R.id.nav_aboutus) {

            fragment = new AboutusFragment();
            if (!(currentFragment.getClass().equals(fragment.getClass())))
                addFragmentToActivity(fragment);


        } else if (id == R.id.nav_feedback) {

            fragment = new FeedbackFragment();
            if (!(currentFragment.getClass().equals(fragment.getClass())))
                addFragmentToActivity(fragment);


        } else if (id == R.id.nav_termsandcondition) {

            fragment = TermsAndConditionFragment.newInstance(true);
            if (!(currentFragment.getClass().equals(fragment.getClass())))
                addFragmentToActivity(fragment);


        } else if (id == R.id.nav_privacypolicy) {

            fragment = PrivacyPolicyFragment.newInstance(true);
            if (!(currentFragment.getClass().equals(fragment.getClass())))
                addFragmentToActivity(fragment);

        } else if (id == R.id.nav_contactus) {

            fragment = new com.purplepath.purplepath.AppManagement.ContactUs.ContactusFragment();
            //fragment= ContactusFragment.newInstance("");
            if (!(currentFragment.getClass().equals(fragment.getClass())))
                addFragmentToActivity(fragment);

        } else if (id == R.id.nav_logout) {
            //flag

            alertDialogLogout.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layouts);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    void setTintimage(String key, ArrayList<Integer> image_obj, int without_tint_image, int tint_image) {

        if (UtileKit.getPersistedPurplePathBoolPref(key)) {
            image_obj.add(without_tint_image);
        } else {
            image_obj.add(tint_image);
        }

    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId(String android_id, String user_id) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String Token = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + Token);

        if (validateObjectValues(android_id) && validateObjectValues(user_id) && validateObjectValues(Token)) {
            callRegisterNotification(user_id, Token, android_id);
        }

//        if (!TextUtils.isEmpty(regId))
//            txtRegId.setText("Firebase Reg Id: " + regId);
//        else
//            txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    private void decipro(int i1) {
        Fragment fragment;
        switch (i1) {
            case 0:
//                if(UtileKit.getPersistedPurplePathBoolPref("Home Loan Switch")) {
//                    Intent i = new Intent(HomePageActivity.this, DesiproActivity.class);
//                    startActivity(i);
//                }
//                else {
//                    UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
//                }
                if (UtileKit.getPersistedPurplePathBoolPref("Home Loan Switch")) {
                    fragment = new DesiproActivity();
                    addFragmentToActivity(fragment);
                } else {
                    UtileKit.intitializeAlertDialog("please upgrade your pack", mContext);
                }

                break;
            case 1:
//                Intent amort = new Intent(this, AmortizationScheduleActivity.class);
//                startActivity(amort);
                if (UtileKit.getPersistedPurplePathBoolPref("Amortization Schedule")) {
                    fragment = new AmortizationMainFragment();
                    addFragmentToActivity(fragment);
                } else {
                    UtileKit.intitializeAlertDialog("please upgrade your pack", mContext);
                }
                break;
            case 2:
//                Intent loanComparison = new Intent(this, LoanComparisonActivity.class);
//                startActivity(loanComparison);
                if (UtileKit.getPersistedPurplePathBoolPref("Loan Comparsion")) {
                    fragment = new LoanComparisonActivity();
                    addFragmentToActivity(fragment);
                } else {
                    UtileKit.intitializeAlertDialog("please upgrade your pack", mContext);
                }
                break;
            case 3:
                if (UtileKit.getPersistedPurplePathBoolPref("Deposit Comparison")) {
                    //Intent depositIntent = new Intent(this, DepositComparisonActivity.class);
                    //startActivity(depositIntent);

                    fragment = new DepositFragment();
                    addFragmentToActivity(fragment);
                } else {
                    UtileKit.intitializeAlertDialog("please upgrade your pack", mContext);
                }

                //fragment = new DepositFragment();
                //addFragmentToActivity(fragment);

                break;
            case 4:
//                Intent timeValOfMoney = new Intent(this, TimeValueOfMoneyActivity.class);
//                startActivity(timeValOfMoney);

                if (UtileKit.getPersistedPurplePathBoolPref("Time Value of Money")) {
                    fragment = new TimeValueOfMoneyFragment();
                    addFragmentToActivity(fragment);
                } else {
                    UtileKit.intitializeAlertDialog("please upgrade your pack", mContext);
                }
                break;
            case 5:

                if (UtileKit.getPersistedPurplePathBoolPref("Loan Eligibility")) {
                    fragment = new LoanEligibility();
                    addFragmentToActivity(fragment);
                } else {
                    UtileKit.intitializeAlertDialog("please upgrade your pack", mContext);
                }
                break;
            case 6:
                if (UtileKit.getPersistedPurplePathBoolPref("Goal Affordability")) {
                    fragment = new GoalAffordabilityFragment();
                    addFragmentToActivity(fragment);
                } else {
                    UtileKit.intitializeAlertDialog("please upgrade your pack", mContext);
                }

                break;

            case 7:
                if (UtileKit.getPersistedPurplePathBoolPref("Home - Buy vs Rent")) {
                    startActivity(new Intent(this, HouseBuyVsRentActivity.class));
                } else {
                    UtileKit.intitializeAlertDialog("please upgrade your pack", mContext);
                }

                /*fragment=new TaxPromptSummary();
                addFragmentToActivity(fragment);
*/
                break;

            case 8:

                fragment = new CarBuyVsLeaseFragment();
                addFragmentToActivity(fragment);

                break;

        }

    }

    private void myplanAnalysis(int i1) {
        Fragment fragment;


       /* switch (i1) {
            case 0:
                fragment = new IncomeanaysisMainPageFragment();
                addFragmentToActivity(fragment);
                break;
            case 1:
                fragment = new ExpanseanaysisMainPageFragment();
                addFragmentToActivity(fragment);
                break;
*//*
            case 2:
                fragment = new AssetsAnalysisFragment();
                addFragmentToActivity(fragment);
                break;*//*
            case 2:
                fragment = new AssetAnalysisPieSummary();
                addFragmentToActivity(fragment);
                break;


            case 3:
               // fragment =new EmergencyFundParent();
              //  fragment =new EmergencyFundParent();
                fragment =new EmergencyFundSummaryFrag();

               *//* Bundle value = new Bundle();

                if(value1.isEmpty()&&value2.isEmpty())
                    value.putBoolean("IsEmpty",true);
                else
                    value.putBoolean("IsEmpty",false);
                if (value1.isEmpty())
                    value1.add(new ChartData(0f, ""));
                if (value2.isEmpty())
                    value2.add(new ChartData(0f, ""));
//                                    value.putParcelableArrayList("chart1",  value1);
//                                    value.putParcelableArrayList("chart2", value2);
                EmergencyFundChart.value1=value1;
                EmergencyFundChart.value2=value2;
                fragment.setArguments(value);*//*
                addFragmentToActivity(fragment);
                break;
           case 4:
                fragment = new CashflowChartFragment();
                addFragmentToActivity(fragment);
                break;
            case 5:
                fragment = new InsuranceAnalysis();
                addFragmentToActivity(fragment);
                break;
            case 6:
                //fragment = new TaxCashFlowChart();
                fragment=new TaxPlanSummary();
                addFragmentToActivity(fragment);
                break;

            case 7:
                fragment = new RecommendationFragment();
                addFragmentToActivity(fragment);
                break;
            case 8:
                fragment = new LiabilitiesMainPageFragment();
                addFragmentToActivity(fragment);
                break;



            case 9:
                fragment = new RiskProfile();
                addFragmentToActivity(fragment);
                break;
            case 10:
                fragment = new RiskAssesmentResultFragment();
                addFragmentToActivity(fragment);
                break;

           *//* case 11:
                fragment = new AssetAnalysisPieSummary();
                addFragmentToActivity(fragment);
                break;*//*

        }*/
    }

    private void mydatapan(int postion) {
        Fragment fragment;
        switch (postion) {
            case 0:
                fragment = new PersonalDetailsFragment();
                Bundle value = new Bundle();
                value.putBoolean("IsSignUp", false);
                fragment.setArguments(value);
                addFragmentToActivity(fragment);
                break;
            case 1:
                fragment = new FamilyDetailFragment();
                addFragmentToActivity(fragment);
                break;
            case 2:
                fragment = new GoalsListFragment();
                //fragment=new AddGoalFragment();
                addFragmentToActivity(fragment);
                break;
            case 3:

                fragment = new IncomeDetail();
                addFragmentToActivity(fragment);
                break;
            case 4:
                /**
                 * ExpenseNew design
                 */
//                fragment = new ExpensesReDesignDetailsFragment();
                fragment = ExpenseTabMainFragment.newInstance();
                addFragmentToActivity(fragment);
                break;
            case 5:
                fragment = new AssetsDetailsFragment();
                addFragmentToActivity(fragment);
                break;
            case 6:
                fragment = new LiabilitiesTabViewFragment();
                addFragmentToActivity(fragment);
                break;
            case 7:
                fragment = new InsuranceDetailsFragment();
                addFragmentToActivity(fragment);
                break;
            case 8:
                fragment = new RetirementBenefitsFragment();
                addFragmentToActivity(fragment);
                break;
            case 9:
                fragment = new TaxPrepaidFragment();
                addFragmentToActivity(fragment);
                break;

        }
    }

    private void myplanAnalysisdisplayInPopup(int i1) {
        Fragment fragment;
        switch (i1) {
            case 0:
                fragment = new IncomePieChartFragment();
                addFragmentToActivity(fragment);
                break;
            case 1:
                fragment = new ExpensesAnalysisFragment();
                addFragmentToActivity(fragment);
                break;
            case 2:
                fragment = new AssetsAnalysisFragment();
                addFragmentToActivity(fragment);
                break;
//            case 4:
//                fragment = new CashManagemntAnalysis();
//                addFragmentToActivity(fragment);
//                break;
//            case 5:
//
////                fragment = new EmergencyFundChart();
//                fragment=new EmergencyFundParent();
//               /* Bundle value = new Bundle();
//
//                if(value1.isEmpty()&&value2.isEmpty())
//                    value.putBoolean("IsEmpty",true);
//                else
//                    value.putBoolean("IsEmpty",false);
//                if (value1.isEmpty())
//                    value1.add(new ChartData(0f, ""));
//                if (value2.isEmpty())
//                    value2.add(new ChartData(0f, ""));
////                                    value.putParcelableArrayList("chart1",  value1);
////                                    value.putParcelableArrayList("chart2", value2);
//                EmergencyFundChart.value1=value1;
//                EmergencyFundChart.value2=value2;
//                fragment.setArguments(value);*/
//                addFragmentToActivity(fragment);
//                break;
//            case 6:
//
//                fragment = new GoalTimeLineFragment();
//                addFragmentToActivity(fragment);
//                break;
            case 7:
                fragment = new CashflowChartFragment();
                addFragmentToActivity(fragment);
//                            Intent intent=new Intent(getApplicationContext(),SettingsActivity.class);
//                            startActivity(intent);

                break;
            case 8:
                fragment = new InsuranceAnalysis();
                addFragmentToActivity(fragment);
                break;

//                        case 9:
//                            fragment = new TaxAnalysis();
//                            addFragmentToActivity(fragment);
//                            break;
//            case 9:
//                fragment = new TaxCashFlowChart();
//                addFragmentToActivity(fragment);
//                break;

            case 10:
                fragment = new RiskProfile();
                addFragmentToActivity(fragment);
                break;
            case 11:
                fragment = new RiskAssesmentResultFragment();
                addFragmentToActivity(fragment);
                break;
        }
    }

    private void showFragmentFromIntent(String fragmentID) {
        Fragment fragment;
        if (UtileKit.validateObjectValues(fragmentID)) {
            if (fragmentID.equals("FamilyDetails")) {
                fragment = new FamilyDetailFragment();
                showFragment(fragment);
            } else if (fragmentID.equals("PersonalDetails")) {
                fragment = new PersonalDetailsFragment();
                showFragment(fragment);
            }
        }

    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commitAllowingStateLoss();
    }

    private void intitializeAlertDialog() {
        inflater = LayoutInflater.from(this);
        dialogView = inflater.inflate(R.layout.yes_or_no_system_back, null);
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setView(dialogView);
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();
//

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityCompat.finishAffinity(HomePageActivity.this);
                } else
                    finish();


            }
        });
        dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        dialogViewLogout = inflater.inflate(R.layout.yes_no_dialog, null);
        alertDialogLogout = new AlertDialog.Builder(this).create();
        alertDialogLogout.setView(dialogViewLogout);
        dialogViewLogout.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPreferences();
                LogotTheApp();

            }
        });
        dialogViewLogout.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialogLogout.dismiss();
            }
        });


    }

    private void LogotTheApp() {
        HomePageActivity.getExpensesDetailsModel = null;
        HomePageActivity.expensesDetailsSize = 0;
        alertDialogLogout.dismiss();
        finish();
        Intent loginActivity = new Intent(context, LoginandSignUpActivity.class);
        loginActivity.putExtra("Logout", true);
        loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginActivity);
    }



    private void clearPreferences() {
        SharedPreferences settings = context.getSharedPreferences("useridPref", Context.MODE_PRIVATE);
        settings.edit().remove("name_services").commit();
        settings.edit().remove("email_service").commit();
        settings.edit().remove("user_id").commit();
        settings.edit().remove("scheduleModel").commit();
        settings.edit().remove("UserPrefDate").commit();
        settings.edit().remove("AssetCat").commit();
        settings.edit().remove("mobilenumbergot").commit();
        //  settings.edit().remove("oneTimeShowAgreement").commit();
        //  settings.edit().remove("terms_checkbox_select").commit();
        // settings.edit().remove("continueAgreement").commit();
        settings.edit().remove("dialogShown").commit();
        settings.edit().remove("cust_id").commit();

        settings.edit().remove("dialogShownInitial").commit();
        settings.edit().remove("dialogShownTaxfile").commit();
        settings.edit().remove("dialogShownTaxPlanningSection").commit();
        settings.edit().remove("dialogShownTaxFilingSection").commit();


    }

    private void dailyupdateRisk() {
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        String mVersionName = null;
        try {
            mVersionName = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
            //VersionCode = Integer.toString(mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Call<RiskDailyUpdateModel> call = webServiceObj.getRiskSoreByUser(getPersistedPurplePathPref("user_id"), mVersionName);
        call.enqueue(new Callback<RiskDailyUpdateModel>() {
            @Override
            public void onResponse(Call<RiskDailyUpdateModel> call, Response<RiskDailyUpdateModel> response) {
                mRiskDailyUpdateModel = response.body();

                checkYourverson(mRiskDailyUpdateModel);


            }

            @Override
            public void onFailure(Call<RiskDailyUpdateModel> call, Throwable t) {

            }
        });

    }

    private void checkYourverson(RiskDailyUpdateModel mRiskDailyUpdateModel) {
        if (mRiskDailyUpdateModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
            if (mRiskDailyUpdateModel.getData().getIs_version_update().equalsIgnoreCase(UtileKit.is_version_update)) {

                passalertButtonDialogYesNo("New update available, Install the lastest version for new features", mContext);

            }
        }
    }

    private void passalertButtonDialogYesNo(String message, final Context context) {

        inflater = LayoutInflater.from(context);
        dialogView = inflater.inflate(R.layout.alert_message_layout, null);
        alertDialog = new android.support.v7.app.AlertDialog.Builder(context).create();
        alertDialog.setView(dialogView);
        // dialogView.findViewById(R.id.no).setVisibility(View.GONE);
        TextView stringErrorMessage = dialogView.findViewById(R.id.textViewDilog);
        stringErrorMessage.setText(message);
        alertDialog.setCancelable(false);
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity  object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                }
//                clearPreferences();
//                LogotTheApp();
                alertDialog.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityCompat.finishAffinity(HomePageActivity.this);
                } else
                    finish();
            }
        });
        /*dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });*/
        alertDialog.show();
    }

    private void callAlertPromptService() {
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<PersonalPromptModel> call = webServiceObj.callAlertPromptService(getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<PersonalPromptModel>() {
            @Override
            public void onResponse(Call<PersonalPromptModel> call, Response<PersonalPromptModel> response) {
                UtileKit.dismisssSpinnerDialog();
                PersonalPromptModel getPersonalPromptModel = response.body();
                if (getPersonalPromptModel.getStatusCode().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    ArrayList<String> list = new ArrayList<String>();
                    list.addAll(getPersonalPromptModel.getData().getEmptyFields());
                    int pageId = getPersonalPromptModel.getData().getPageId();
                    String pageTitle = getPersonalPromptModel.getData().getPageName();
                    Fragment fragmentView = null;
                    if (UtileKit.validateObjectValues(list)) {
                        if (!list.isEmpty()) {
                          /*  switch (pageId) {
                                case 1:
                                    fragmentView = new PersonalDetailsFragment();
                                    break;
                                case 2:
                                    fragmentView = new FamilyDetailFragment();
                                    break;
                                case 3:
                                    break;
                            }*/
                            String listbuild = "\n";
                            for (String name : list) {
                                listbuild = listbuild.concat(name).concat("\n");
                            }
                            //final Fragment finalFragmentView = fragmentView;
                            PromptSugestionDialog newFragment = PromptSugestionDialog.newInstance(list, pageTitle, pageId);
                            newFragment.show(getSupportFragmentManager(), "dialog");
                           /* new AlertDialog.Builder(HomePageActivity.this)
                                    .setTitle(""+pageTitle)
                                    .setMessage("Seems you have missed the following fields. Fill the following fields to serve you better."+listbuild)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // TODO Auto-generated method stub

                                            addFragmentToActivity(finalFragmentView);

                                            dialog.dismiss();
                                        }
                                    })
//                                    .setNeutralButton("No", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            // TODO Auto-generated method stub
//                                            dialog.dismiss();
//                                        }
//                                    })
                                    .create()
                                    .show();*/
                        } else {

                        }
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<PersonalPromptModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void callRegisterNotification(String user_id, String token, String android_id) {
        try {

            Log.i("CallBack", " callRegisterNotification is user id " + user_id);

            WebServiceCalls webServiceObj;
            webServiceObj = ServiceGenerator
                    .createService(WebServiceCalls.class);
            Call<AddDeviceIdModel> call = webServiceObj.AddDeviceIDService(user_id, token, android_id);
            call.enqueue(new Callback<AddDeviceIdModel>() {
                @Override
                public void onResponse(Call<AddDeviceIdModel> call, Response<AddDeviceIdModel> response) {
                    Log.i("CallBack", "ç response is " + response.body());

                    try {
                        AddDeviceIdModel addDeviceModels = response.body();

                        if (addDeviceModels.getData() == null) {
                            if (addDeviceModels.getData().get(0).getMessage().equalsIgnoreCase("Device added successfully")) {
                                UtileKit.persistingPurplePathPref("addDeviceIdModel", "ON");
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<AddDeviceIdModel> call, Throwable t) {
//                    //Log.e("CallBack", " failure is " + t);
                    UtileKit.alertRetrofitExceptionDialog(mContext, t);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
//            if (event.getDownTime() - lastPressedTime < PERIOD) {
//                alertDialog.show();
//                //finish();
//                return true;
//            } else {
//                Toast.makeText(getApplicationContext(), "Press again to exit.",
//                        Toast.LENGTH_SHORT).show();
//                lastPressedTime = event.getEventTime();
//                Snackbar.make(coordinatorLayout, "press one moret time to Exit", Snackbar.LENGTH_SHORT);
//                return false;
//            }
            try {
                alertDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
///*
//    *//**//**
//     * Animation drops down View from Top
//     * Older versions of android (pre API 21) cancel animations for views with a height of 0.
//     *
//     * @param  v
//     *//**//*

    private int getPixelsToDP(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (dp * scale + 0.5f);
        return pixels;
    }

    public void expand(final View v) {
        v.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 1;

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? WindowManager.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
        isClicked = false;
        clickMeId.setImageResource(R.drawable.ic_menu_drop_up);
    }

//    *//*
//
//    *//**
//     * Swipe View On Top of activity
//     *//**//*

    private void setDropDownView() {
        try {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll);
            for (int v = 0; v < mdrawable.size(); v++) {
//            *//**//*---------------Creating frame layout----------------------*//**//*

                FrameLayout frameLayout = new FrameLayout(HomePageActivity.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getPixelsToDP(70));
                // layoutParams.rightMargin = getPixelsToDP(10);
                layoutParams.setMargins(10, 10, 10, 10);
                frameLayout.setLayoutParams(layoutParams);
//            *//**//*--------------end of frame layout----------------------------*//**//*
//
//            *//**//*---------------Creating image view----------------------*//**//*
                final ImageView imgView = new ImageView(HomePageActivity.this); //create imageview dynamically
                LinearLayout.LayoutParams lpImage = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getPixelsToDP(40), Gravity.CENTER_HORIZONTAL);
                imgView.setImageResource(gridQuickmenuList.get(v).getQuickmenuImage());
//                imgView.setImageResource(mdrawable[v]);
                imgView.setLayoutParams(lpImage);
                // setting ID to retrieve at later time (same as its position)
                imgView.setId(v);

//            *//**//*--------------end of image view----------------------------*//**//*
//            *//**//*---------------Creating Text view----------------------*//**//*
                FrameLayout frameLayoutInner = new FrameLayout(HomePageActivity.this);
                FrameLayout.LayoutParams frameLP = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getPixelsToDP(35), Gravity.BOTTOM);
                frameLayoutInner.setLayoutParams(frameLP);
                TextView textView = new TextView(HomePageActivity.this);//create textview dynamically
                textView.setText(gridQuickmenuList.get(v).getQuickmenuText());
                textView.setMaxLines(2);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.month_day_label_text_size));
//                textView.setText(childname[v]);
                FrameLayout.LayoutParams lpText = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP);
                // Note: LinearLayout.LayoutParams 's gravity was not working so I putted Framelayout as 3 paramater is gravity itself
                textView.setTextColor(Color.parseColor("#000000"));
                lpText.setMargins(10, 10, 10, 0);
                textView.setLayoutParams(lpText);
//            *//**//*--------------end of Text view----------------------------*//**//*
                frameLayoutInner.addView(textView);
                frameLayout.addView(imgView);
                frameLayout.addView(frameLayoutInner);

                //frameLayout.addView(textView);
                final int finalV = v;

                frameLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment;
                        fragmentManager = getSupportFragmentManager();
                        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
                        if (!UtileKit.isNetworkAvailable(MyApplication.getInstance())) {
                            Toast.makeText(HomePageActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        } else {
//                            collapse(mDropdownLayout);
                            if (finalV == 0) {
                                gridQuickmenuListinnerMenu = generateQuickMenuListInner(childnameData, childnameDataImage, 0);
                                Log.i("HomePage Activity", " gridQuickmenuListinnerMenu 1 " + gridQuickmenuListinnerMenu.size());
                                showPopUpWindow(view, finalV);
//                                GirdViewDailog newFragment =  GirdViewDailog.newInstance(1, callbackInterface);
//                                newFragment.show(getSupportFragmentManager(), "dialog");
                                clickMeId.setImageResource(R.drawable.ic_menu_drop_dwon);

                                // clickMeId.setImageResource(R.drawable.ic_menu_drop_up);

                            }
//                            else if (finalV == 1) {
//                                gridQuickmenuListinnerMenu = generateQuickMenuListInner(childnamePlan,childnamePlanImage,1);
//                                Log.i("HomePage Activity"," gridQuickmenuListinnerMenu 0 " + gridQuickmenuListinnerMenu.size());
//                                showPopUpWindow(view, finalV);
//                                //                                GirdViewDailog newFragment =  GirdViewDailog.newInstance(0, callbackInterface);
////                                newFragment.show(getSupportFragmentManager(), "dialog");
//                            }
                            else if (finalV == 1) {

                                if (UtileKit.getPersistedPurplePathBoolPref("My Action Plan")) {
                                    //fragment = new RecommendationFragment();
                                    fragment = new RecommendationTableViews();


                                    if (!(currentFragment.getClass().equals(fragment.getClass())))
                                        addFragmentToActivity(fragment);
//                                collapse(mDropdownLayout);
                                    clickMeId.setImageResource(R.drawable.ic_menu_drop_dwon);
                                } else {
                                    UtileKit.intitializeAlertDialog("please upgrade your pack", mContext);
                                }

                            } else if (finalV == 2) {
                                //Toast.makeText(HomePageActivity.this, "No Internet Connection 2", Toast.LENGTH_SHORT).show();
                                fragment = new GoalTimeLineFragment();
                                addFragmentToActivity(fragment);
//                                collapse(mDropdownLayout);
                                clickMeId.setImageResource(R.drawable.ic_menu_drop_dwon);


                            } else if (finalV == 3) {
//                                Toast.makeText(HomePageActivity.this, "No Internet Connection 3", Toast.LENGTH_SHORT).show();
                                fragment = new AssertanaysisMainPageFragment();
                                //fragment=new AssertanaysisMainPageFragment();
                                if (!(currentFragment.getClass().equals(fragment.getClass())))
                                    addFragmentToActivity(fragment);
//                                collapse(mDropdownLayout);
                                clickMeId.setImageResource(R.drawable.ic_menu_drop_dwon);
                            } else if (finalV == 4) {

                                if (UtileKit.getPersistedPurplePathBoolPref("Tax Cash Flow Chart")) {
                                    fragment = new CashflowChartFragment();
                                    if (!(currentFragment.getClass().equals(fragment.getClass())))
                                        addFragmentToActivity(fragment);
                                    // Toast.makeText(HomePageActivity.this, "Under Development", Toast.LENGTH_SHORT).show();
//                                    collapse(mDropdownLayout);
                                    clickMeId.setImageResource(R.drawable.ic_menu_drop_dwon);
                                } else {
                                    UtileKit.intitializeAlertDialog("please upgrade your pack", mContext);
                                }
                            } else if (finalV == 5) {
                                fragment = new RiskProfile();
                                if (!(currentFragment.getClass().equals(fragment.getClass())))
                                    addFragmentToActivity(fragment);
                                // Toast.makeText(HomePageActivity.this, "Under Development", Toast.LENGTH_SHORT).show();
//                                collapse(mDropdownLayout);
                                clickMeId.setImageResource(R.drawable.ic_menu_drop_dwon);
                            } else if (finalV == 6) {

                                if (UtileKit.getPersistedPurplePathBoolPref("DeciPro")) {
                                    gridQuickmenuListinnerMenu = generateQuickMenuListInner(childnamePlandecipro, childnameDataImagedecipro, 1);
                                    Log.i("HomePage Activity", " gridQuickmenuListinnerMenu 0 " + gridQuickmenuListinnerMenu.size());
                                    showPopUpWindow(view, finalV);
                                    // collapse(mDropdownLayout);
                                    clickMeId.setImageResource(R.drawable.ic_menu_drop_dwon);
                                    // clickMeId.setImageResource(R.drawable.ic_menu_drop_up);
//                                Intent i=new Intent(HomePageActivity.this,DesiproActivity.class);
//                                startActivity(i);
                                    //Toast.makeText(HomePageActivity.this, "Under Development", Toast.LENGTH_SHORT).show();
                                } else {
                                    UtileKit.intitializeAlertDialog("please upgrade your pack", mContext);
                                }
                            } else if (finalV == 7) {

                                if (UtileKit.getPersistedPurplePathBoolPref("DigiVault")) {
                                    fragment = new Documentgridfragment();
                                    if (!(currentFragment.getClass().equals(fragment.getClass())))
                                        addFragmentToActivity(fragment);
//                                    collapse(mDropdownLayout);
                                    clickMeId.setImageResource(R.drawable.ic_menu_drop_dwon);
                                } else {
                                    UtileKit.intitializeAlertDialog("please upgrade your pack", mContext);
                                }

                            } else if (finalV == 8) {
                                fragment = new ScheduleFragment();
                                // fragment=ScheduleFragment.newSingleton_Instance();
                                if (!(currentFragment.getClass().equals(fragment.getClass())))
                                    addFragmentToActivity(fragment);
//                                collapse(mDropdownLayout);
                                clickMeId.setImageResource(R.drawable.ic_menu_drop_dwon);
                            }

                        }
                    }
                });
//                collapse(mDropdownLayout);
                linearLayout.addView(frameLayout);

            }
            clickMeId.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (!UtileKit.isNetworkAvailable(MyApplication.getInstance())) {
                        Toast.makeText(HomePageActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    } else {
                        if (isClicked) {
                            mDropdownLayout.setVisibility(View.VISIBLE);
                            expand(mDropdownLayout);
                            clickMeId.setImageResource(R.drawable.ic_menu_drop_up);
                            //true && false is the required condition to show guide view
                            if (UtileKit.getPersistedPurplePathBoolPref("isSignUp_demoScreen") && !UtileKit.getPersistedPurplePathBoolPref("qucikAccessGuide")) {
                                QuickAccessGuideView dialog = new QuickAccessGuideView();
                                dialog.show(getSupportFragmentManager(), "guideScreen");
                                UtileKit.persistingPurplePathPref("qucikAccessGuide", true);
                            }

                        } else {

                            clickMeId.setImageResource(R.drawable.ic_menu_drop_dwon);
                            collapse(mDropdownLayout);
                        }
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPopUpWindow(View view, final int i) {

        {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View popupView = layoutInflater.inflate(R.layout.categorydialog, null);
            final PopupWindow popupWindow = new PopupWindow(popupView);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            // popupWindow.setWidth((int) (width * .6));
            popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.BLUE));
            GridView gridview = popupView.findViewById(R.id.gridview);
            TextView errorMessage = popupView.findViewById(R.id.errorTextview);
            RelativeLayout mRelativeLayout = popupView.findViewById(R.id.layout_root);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mRelativeLayout.setLayoutParams(params);
//            int case_period = (Integer) getArguments().get("peroid");
//            Log.i("GirdViewDailog","GirdViewDailog"+ case_period);
            try {
                if (gridQuickmenuList != null) {
                    GridViewAdapterMenu adapter = new GridViewAdapterMenu(mContext, gridQuickmenuListinnerMenu, i);
                    gridview.setAdapter(adapter);
                } else {
                    errorMessage.setVisibility(View.VISIBLE);
                    gridview.setVisibility(View.VISIBLE);
                    errorMessage.setText("Error in retriving data ");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        if (i == 0) {
                            callbackInterface.upadatepostion(0, position);
                            popupWindow.dismiss();
                        } else if (i == 1) {
                            callbackInterface.upadatepostion(1, position);
                            popupWindow.dismiss();
                        } else if (i == 6) {
                            callbackInterface.upadatepostion(6, position);
                            popupWindow.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    collapse(mDropdownLayout);
                }
            });

            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            popupWindow.showAsDropDown(view, 0, -0);
        }
    }

    //    */
    @Override
    public void onActivityBackPressed() {
        //alertDialog.show();
        try {
            onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // UtileKit.alertDialog("Are you Sure you want to exit the Application",mContext);
    }


    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
    private ArrayList<GirdviewText> generateQuickMenuList() {
        ArrayList<GirdviewText> gridviewStringtextList = new ArrayList<>();
        for (int i = 0; i < mdrawable.size(); i++) {
            GirdviewText gridTextInfo = new GirdviewText();
            gridTextInfo.setQuickmenuText(childnamemenu[i]);
            gridTextInfo.setQuickmenuImage(mdrawable.get(i));

            gridviewStringtextList.add(gridTextInfo);
        }
        return gridviewStringtextList;
    }

    private ArrayList<GirdviewText> generateQuickMenuListInner(ArrayList<String> childname, ArrayList<Integer> mChilddrawable, int j) {
        try {
            ArrayList<GirdviewText> gridviewStringtextList = new ArrayList<>();

            for (int i = 0; i < childname.size(); i++) {
                GirdviewText gridTextInfo = new GirdviewText();
                if (j == 1) {
                    gridTextInfo.setMyPlanText(childname.get(i));
                    gridTextInfo.setMyPlanImage(mChilddrawable.get(i));
                } else if (j == 0) {

//            gridTextInfo.setQuickmenuText(childname[i]);
//            gridTextInfo.setQuickmenuImage(mdrawable[i]);
                    gridTextInfo.setMyDataText(childname.get(i));
                    gridTextInfo.setMyDataImage(mChilddrawable.get(i));
                }

                gridviewStringtextList.add(gridTextInfo);
            }

            return gridviewStringtextList;
        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }

    public void callGetGoalListService() {
        //UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetGoalsListModel> call = webServiceObj.callGetGoalsListService(/*EmailLoginFragment.oAuth_key,*/getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetGoalsListModel>() {
            @Override
            public void onResponse(Call<GetGoalsListModel> call, Response<GetGoalsListModel> response) {
                UtileKit.dismisssSpinnerDialog();
                getGoalsListModel = response.body();
                if (getGoalsListModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    getGoalsUserData = getGoalsListModel.getData().getUser_goals();
                    if (UtileKit.validateObjectValues(getGoalsUserData)) {
                        if (!getGoalsUserData.isEmpty()) {
                            checkGoalsId = 0;
                            goallistsize = getGoalsUserData.size();
                        } else {
                            checkGoalsId = 0;
                            goallistsize = 0;
                        }
                    }
                } else {
                    checkGoalsId = 0;
                    goallistsize = 0;
                }
            }

            @Override
            public void onFailure(Call<GetGoalsListModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                goallistsize = 0;
                UtileKit.dismisssSpinnerDialog();
            }
        });


    }


    public void callGetExpensesService() {
        WebServiceCalls webServiceObj;
        HomePageActivity.getExpensesDetailsModel = null;
        HomePageActivity.expensesDetailsSize = 0;
        getExpensesDetailsModel = null;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetExpensesDetailsModel> call = webServiceObj.callGetExpensesDetailsService(getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetExpensesDetailsModel>() {
            @Override
            public void onResponse(Call<GetExpensesDetailsModel> call, Response<GetExpensesDetailsModel> response) {
                getExpensesDetailsModel = response.body();
                if (getExpensesDetailsModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    if (getExpensesDetailsModel.getData().getUser_expense() != null) {
                        if (!getExpensesDetailsModel.getData().getUser_expense().isEmpty()) {
                            expensesDetailsSize = getExpensesDetailsModel.getData().getUser_expense().size();
                        }
                    }
                } else {
                    getExpensesDetailsModel = null;
                }
                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<GetExpensesDetailsModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }

    public void callEmergFundAnalyService() {
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<EmergencyFundModel> call = webServiceObj.callEmergencyFundChartService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<EmergencyFundModel>() {
            @Override
            public void onResponse(Call<EmergencyFundModel> call, Response<EmergencyFundModel> response) {
                UtileKit.dismisssSpinnerDialog();
//                //Log.e("success", "overall_per" + response.body());
                emergencyFundModel = response.body();
                ArrayList<Float> chartdatalist = new ArrayList<Float>();
                ArrayList<String> chartitledatalist = new ArrayList<String>();
                if (emergencyFundModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    value1 = new ArrayList();
                    value1.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_cash_per()), "cash_per"));
                    value1.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_savings_acc_per()), "savings_acc"));
                    value1.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_curr_acc_per()), "curr_acc"));
                    value1.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_fix_recc_dep_per()), "fix_recc_dep"));
                    value1.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_term_dep_per()), "term_dep"));
                    value1.add(new ChartData(ChartData.issum, "overall_per"));
//                    value.add(new ChartData(15f, "plan_cash_per"));
//                    value.add(new ChartData(16f, "plan_savings_acc_per"));
//                    value.add(new ChartData(12f,"plan_curr_acc_per"));
//                    value.add(new ChartData(24f, "plan_fix_recc_dep_per"));
//                    value.add(new ChartData(ChartData.issum, "plan_term_dep_per"));
//                        //Log.e("value1", "ChartData ArrayList" +emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_cash_per());

                    value2 = new ArrayList();
//                    value.add(new ChartData((float)100, "act_overall_per"));
                    value2.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_cash_per()), "cash_per"));
                    value2.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_savings_acc_per()), "savings_acc"));
                    value2.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_curr_acc_per()), "curr_acc"));
                    value2.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_fix_recc_dep_per()), "fix_recc_dep"));
                    value2.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_term_dep_per()), "term_dep"));
                    value2.add(new ChartData(ChartData.issum, "overall_per"));


                } else {

                    Log.i("success", "value1 value2" + response.body());
//                    UtileKit.intitializeAlertDialog("No Emergency Fund details to retrieve. Please try again!", mContext);
                }

            }

            @Override
            public void onFailure(Call<EmergencyFundModel> call, Throwable t) {
//                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog(mContext, t);

                UtileKit.dismisssSpinnerDialog();
            }
        });

    }
/*
    public void callGetAssetService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetAssetModel> call = webServiceObj.callGetAssetService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetAssetModel>() {
            @Override
            public void onResponse(Call<GetAssetModel> call, Response<GetAssetModel> response) {
                UtileKit.dismisssSpinnerDialog();
                GetAssetModel getAssetModel = response.body();
                if (getAssetModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    getAssetUserData = getAssetModel.getData().getUser_assets();
                    ArrayList<GetAssetUserData> financialAssetUserData = new ArrayList<GetAssetUserData>();
                    ArrayList<GetAssetUserData> physicalAssetUserData = new ArrayList<GetAssetUserData>();
                    for (int i = 0; i < getAssetUserData.size(); i++) {
                        if (getAssetUserData.get(i).getType().equalsIgnoreCase("Financial")) {
                            financialAssetUserData.add(getAssetUserData.get(i));
                        } else if (getAssetUserData.get(i).getType().equalsIgnoreCase("Physical")) {
                            physicalAssetUserData.add(getAssetUserData.get(i));
                        }
                    }
                    if (UtileKit.validateObjectValues(financialAssetUserData)) {
                        if (!financialAssetUserData.isEmpty()) {
                            FinancialAssetsize = financialAssetUserData.size();
                        } else {
                            FinancialAssetsize = 0;
                        }

                    } else {
                        FinancialAssetsize = 0;
                    }

                    if (UtileKit.validateObjectValues(physicalAssetUserData)) {
                        if (!physicalAssetUserData.isEmpty()) {
                            physicalAssetsize = physicalAssetUserData.size();

                        } else {
                            physicalAssetsize = 0;
                        }
                    } else {
                        physicalAssetsize = 0;
                    }
                } else {
                    FinancialAssetsize = 0;
                    physicalAssetsize = 0;
                }
            }

            @Override
            public void onFailure(Call<GetAssetModel> call, Throwable t) {
//                //Log.e("CallBack", " failure is " + t);
                FinancialAssetsize = 0;
                physicalAssetsize = 0;
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }
*/

/*public void callGetInsuranceService() {
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
                    getInsuranceUserData = getInsuranceModel.getData().getUser_insurance();
                    for (int i = 0; i < getInsuranceUserData.size(); i++) {
                        if (getInsuranceUserData.get(i).getIns_type().equalsIgnoreCase("General")) {
                            getGeneralInsuranceUserData.add(getInsuranceUserData.get(i));
                        } else if (getInsuranceUserData.get(i).getIns_type().equalsIgnoreCase("Life")) {
                            getLifeInsuranceUserData.add(getInsuranceUserData.get(i));
                        }
                    }
                    if (UtileKit.validateObjectValues(getGeneralInsuranceUserData) && !getGeneralInsuranceUserData.isEmpty()) {
                        generalInsurancesize = getGeneralInsuranceUserData.size();
                    } else {
                        generalInsurancesize = 0;
                    }
                    if (UtileKit.validateObjectValues(getLifeInsuranceUserData) && !getLifeInsuranceUserData.isEmpty()) {
                        lifeInsurancesize = getLifeInsuranceUserData.size();
                    } else {
                        lifeInsurancesize = 0;
                    }

                } else {
                    generalInsurancesize = 0;
                    lifeInsurancesize = 0;
                }

            }

            @Override
            public void onFailure(Call<GetInsuranceModel> call, Throwable t) {
//                //Log.e("CallBack", " failure is " + t);
                generalInsurancesize = 0;
                lifeInsurancesize = 0;
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }*/
    /*private void callAddPersonalDetailsService() {
        //UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<AddPersonalDetailsModel> call = webServiceObj.addPersonalDetailService(UtileKit.getPersistedPurplePathPref("user_id"),
                PersonalDetailsFragment.mName, PersonalDetailsFragment.mDateOfBirth, PersonalDetailsFragment.mAge, PersonalDetailsFragment.mCountrycode,
                PersonalDetailsFragment.mMobileno, PersonalDetailsFragment.mEmailId, PersonalDetailsFragment.mGender, PersonalDetailsFragment.mMartialStatus,
                PersonalDetailsFragment.mEducation, PersonalDetailsFragment.mOccupation, PersonalDetailsFragment.mCurrentDesignation,
                PersonalDetailsFragment.mCurrentOrg, PersonalDetailsFragment.mAddressHome, PersonalDetailsFragment.mAddressWork,
                PersonalDetailsFragment.mNoofAverageYears, PersonalDetailsFragment.mNoofWorkYears, PersonalDetailsFragment.mLifeExpectancyAge,
                PersonalDetailsFragment.mPlannedRetirementAge, PersonalDetailsFragment.mMarriageDate, PersonalDetailsFragment.mMarriedSince,
                PersonalDetailsFragment.ah_city, PersonalDetailsFragment.ah_state, PersonalDetailsFragment.ah_country,
                PersonalDetailsFragment.ah_zipcode, PersonalDetailsFragment.aw_city, PersonalDetailsFragment.aw_state, PersonalDetailsFragment.aw_country,
                PersonalDetailsFragment.aw_zipcode);
        call.enqueue(new Callback<AddPersonalDetailsModel>() {
            @Override
            public void onResponse(Call<AddPersonalDetailsModel> call, Response<AddPersonalDetailsModel> response) {
                //Log.e("CallBack", " response is " + call.toString());
                UtileKit.dismisssSpinnerDialog();
//                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                FamilyDetailFragment fragment = new FamilyDetailFragment();
//                fragmentTransaction.add(R.id.fragment_container, fragment);
//                fragmentTransaction.commit();

            }

            @Override
            public void onFailure(Call<AddPersonalDetailsModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //  Toast.makeText(HomePageActivity.this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
                TaxFilingUploadFileNew obj = new TaxFilingUploadFileNew();
                obj.fileGetFromStorage();

            }
//            else{
//
//                Toast.makeText(HomePageActivity.this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
//            }
        }
        if (requestCode == STORAGE_PERMISSION_CODE_MULTIPLlE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                TaxFilingUploadFileNewMultiple objmul = new TaxFilingUploadFileNewMultiple();
                objmul.fileGetMultipleFromStorage();
            }
        }

        if (requestCode == STORAGE_PERMISSION_CODE_26AS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                TaxFileTwentySixForm obj26as = new TaxFileTwentySixForm();
                obj26as.fileGetFrom26AS();

            }
        }


    }


    private void callAssetCategoriesService() {
        final ArrayList<String> assetCategoriesLevelOneIds = new ArrayList<String>();
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<AssetCategoriesModel> call = webServiceObj.callAssetsCategoriesService();
        call.enqueue(new Callback<AssetCategoriesModel>() {
            @Override
            public void onResponse(Call<AssetCategoriesModel> call, Response<AssetCategoriesModel> response) {
//                //Log.e("CallBack", " assets is " + call.toString());
                assetCategoriesModel = response.body();

                UtileKit.dismisssSpinnerDialog();
                if (assetCategoriesModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    Gson gson = new Gson();
                    String str = gson.toJson(assetCategoriesModel);

                    //UtileKit.persistingPurplePathPref("AssetCat",assetCategoriesModel.toString());
                    UtileKit.persistingPurplePathPref("AssetCat", str);
                   /* assetCategoriesLevelOneListData = assetCategoriesModel.getData().getAsset_cat_lev1();
                    if (assetType.equalsIgnoreCase("Financial")) {
                        for (int i = 0; i < assetCategoriesLevelOneListData.size(); i++) {
                            if (assetCategoriesLevelOneListData.get(i).getType().equalsIgnoreCase("Financial")) {
                                assetCategoriesLevelOneList.add(assetCategoriesLevelOneListData.get(i));
                            }
                        }
                    } else {
                        for (int i = 0; i < assetCategoriesLevelOneListData.size(); i++) {
                            if (assetCategoriesLevelOneListData.get(i).getType().equalsIgnoreCase("Physical")) {
                                assetCategoriesLevelOneList.add(assetCategoriesLevelOneListData.get(i));
                            }
                        }

                    }
*/

				/*	assetCategoriesLevelTwoList = assetCategoriesModel.getData().getAsset_cat_lev2();
                    assetCategoriesLevelThreeList = assetCategoriesModel.getData().getAsset_cat_lev3();
					if (assetCategoriesLevelOneList != null) {
						for (int i = 0; i < assetCategoriesLevelOneList.size(); i++) {
							assetCategoriesLevelOneNameList.add(assetCategoriesLevelOneList.get(i).getLev1_name());
							assetCategoriesLevelOneIds.add(assetCategoriesLevelOneList.get(i).getId());

						}
						//    UtileKit.setArrayListSpinnerAdapter(mAssetCategoriesOneSpinner, assetCategoriesLevelOneNameList, activity);
						setSpinnerAdapter(mAssetCategoriesOneSpinner, assetCategoriesLevelOneNameList, activity);
						if (UtileKit.validateObjectValues(checkAssetID)) {
							if (UtileKit.validateObjectValues(getAssetUserData.getCat_lev1_id())) {
								int cat1_pos = Integer.valueOf(getAssetUserData.getCat_lev1_id());
								mAssetCategoriesOneSpinner.setSelection(getSelectedSpinnerposition(getAssetUserData.getCat_lev1_id(), assetCategoriesLevelOneIds));
							}
//                            if(UtileKit.validateObjectValues(getAssetUserData.getCat_lev3_id())) {
//                                int cat3_pos = Integer.valueOf(getAssetUserData.getCat_lev3_id());
//                                mAssetCategoriesThreeSpinner.setSelection(getSpinnerposition(getAssetUserData.getCat_lev3_id(), assetCategoriesLevelOneNameList));
//                            }
						}
					}
					*/

//                   /* if(goalCategoriesLevelTwoList !=null){
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
            public void onFailure(Call<AssetCategoriesModel> call, Throwable t) {
//                //Log.e("CallBack", " assets is " + t);
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }


    @Override
    public void setActionBarTitle(String mTitleName) {
        if (mToolbar != null) {
            mToolbar.setTitle("");
            // mToolbar.getMenu().clear();
        }
        if (mTitle != null)
            mTitle.setText(mTitleName);

    }

    @Override
    public void setActionBarExpTitle(String mTitleName) {
        if (mToolbar != null) {
            mToolbar.setTitle("");
        }
        if (mTitle != null)
            mTitle.setText(mTitleName);
    }

    @Override
    public void closeDropDownTab() {
        Log.e("Closetab", "Sucess");
        collapse(mDropdownLayout);
    }

    public void addFragmentToActivity(Fragment fragment) {

        fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (!(currentFragment.getClass().equals(fragment.getClass()))) {
            if (!fragment.isAdded()) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                if (!isFinishing())
                    fragmentTransaction.commitAllowingStateLoss();
                closeDropDownTab();
            }
        }
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
                Log.d("Planing&&Filing_home", plan +"::"+file);

            }

            @Override
            public void onFailure(Call<CardResponse> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(HomePageActivity.this,t);
            }
        });
    }


    public void startFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 11) {
            // try {
            if (data.getExtras() != null) {
                if (data.hasExtra("FragmentID")) {
                    FragmentIdFromIntent = data.getStringExtra("FragmentID");
                    showFragmentFromIntent(FragmentIdFromIntent);
                } else {
                    throw new IllegalArgumentException("Intent has no key FragmentID");
                }
            }
        /*}catch (Exception e){
            e.printStackTrace();
            }
        */

        }

    }

    public void setListenerQuickMenu(QuickMenuInterface callbackInterface) {
        this.callbackInterface = callbackInterface;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void upadatepostion(int case_type, int postion) {
        Log.i("Homepageactivity", "upadatepostion" + case_type + " int postion" + postion);

        if (case_type == 0) {
            mydatapan(postion);

        } else if (case_type == 1) {
            myplanAnalysis(postion);
        } else if (case_type == 6) {
            decipro(postion);
        }
    }

    private void calculateDisplayMetrics() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

    }

    @Override
    public void run() {
        while (!stopThread) {
            stopThread = true;
            handler.removeCallbacks(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
        try {
            if (UtileKit.validateObjectValues(mRiskDailyUpdateModel)) {
                checkYourverson(mRiskDailyUpdateModel);
            } else {
                dailyupdateRisk();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


}
