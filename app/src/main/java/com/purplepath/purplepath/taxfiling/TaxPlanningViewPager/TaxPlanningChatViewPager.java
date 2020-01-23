package com.purplepath.purplepath.taxfiling.TaxPlanningViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxfiling.TaxPlanningViewPager.model.QuestionData;
import com.purplepath.purplepath.taxfiling.TaxPlanningViewPager.model.TaxPromptModels;
import com.purplepath.purplepath.taxfiling.TaxPlanningViewPager.pagerAdapter.TaxPlanningPagerAdapter;
import com.purplepath.purplepath.taxfiling.TaxPlanningViewPager.recycleradapter.HorizantalAdapter;
import com.purplepath.purplepath.taxfiling.dialogtaxfilefragments.TaxPlanningSectionConversationDialog;
import com.purplepath.purplepath.taxprompt.model.DeleteTaxStatemetModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finobot.finobot.MyApplication.mFirebaseAnalytics;


/**
 * Created by pravinr on 7/23/18.
 */

public class TaxPlanningChatViewPager extends BaseFragment implements View.OnClickListener {


    private OnActivityBackPressedListener mCallBackListener;

    public static Context mContext;
    String TAG = "TaxPlanningChatViewPager->";
    View taxView;
    RelativeLayout mleftRelativeLayout, mcenterRelativeLayout;

    public static int page_number = 0;

    static int not_refresh = 1;

    public static TabLayout tabLayout;

    public static ViewPager viewPager;

    public static ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();

    public static TaxPlanningPagerAdapter taxPlanningPagerAdapter;

    public static RecyclerView horizontal_recycler_view;

    public static HorizantalAdapter horizontalAdapter;

    private ArrayList<String> horizontalList;
    String complete_tab = "";
    public static ArrayList<Integer> tabPageState = new ArrayList<Integer>();

    public static ArrayList<Integer> tapPageOriginalState = new ArrayList<Integer>();

    Boolean dialogShownTaxPlanningSection = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try {
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mCallBackListener.setActionBarTitle("Tax Filing");
        taxView = inflater.inflate(R.layout.fragment_taxplanning_viewpager, container, false);
        horizontalList = new ArrayList<>();
        mleftRelativeLayout = taxView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = taxView.findViewById(R.id.relative_center_home);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        refreshView(taxView);
        callGetTaxPromptService();
        return taxView;
    }

    private void refreshView(View taxView) {
        tabPageState.clear();
        tapPageOriginalState.clear();
        // UtileKit.avoidRefreshFlag=1;
        // tabLayout = taxView.findViewById(R.id.tab_layout_id);
        horizontal_recycler_view = (RecyclerView) taxView.findViewById(R.id.horizontal_recycler_view);
        viewPager = taxView.findViewById(R.id.pager);


        tabPageState.add(0);

        horizontalAdapter = new HorizantalAdapter(horizontalList);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);
        horizontal_recycler_view.setAdapter(horizontalAdapter);

        taxPlanningPagerAdapter = TaxPlanningPagerAdapter.newInstance(getChildFragmentManager());

        // callGetTaxPromptService();

        if (UtileKit.validateObjectValues(dialogShownTaxPlanningSection)) {
            dialogShownTaxPlanningSection = UtileKit.getPersistedPurplePathBoolPref("dialogShownTaxPlanningSection");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!dialogShownTaxPlanningSection) {
            UtileKit.persistingPurplePathPref("dialogShownTaxPlanningSection", true);
            TaxPlanningSectionConversationDialog mguideview = new TaxPlanningSectionConversationDialog();
            mguideview.show(getFragmentManager(), "GuideView");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.setCurrentScreen(getActivity(), getString(R.string.analtics_taxplanningconversation_screen), getActivity().getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "8");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Tax Module");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Tax");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }

    public void callGetTaxPromptService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TaxPromptModels> call = webServiceObj.callGetTaxPromptServiceNew(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<TaxPromptModels>() {
            @Override
            public void onResponse(Call<TaxPromptModels> call, Response<TaxPromptModels> response) {
                UtileKit.dismisssSpinnerDialog();
                TaxPromptModels taxPromptModels = response.body();
                try {
                    if (taxPromptModels.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        int length = taxPromptModels.getData().getTab_visited_status().size();
                        horizontalList = new ArrayList<>();
                        ArrayList<String> list_tab_name = taxPromptModels.getData().getAva_tab();
                        Iterator myVeryOwnIterator = taxPromptModels.getData().getKeyQuestion().keySet().iterator();
                        HashMap<String, ArrayList<QuestionData>> overall_questions = taxPromptModels.getData().getKeyQuestion();


                        Log.d("Arraysize:", overall_questions.size() + "");
                     /*   while (myVeryOwnIterator.hasNext()) {
                            String key = myVeryOwnIterator.next().toString();
                            Log.d("KEYCheck:", "Key: " + key);
                            horizontalList.add(key);
                            taxPlanningPagerAdapter.addFragment(TaxPlanningDynamic.newInstance(taxPromptModels, key), "Family details");
                        }*/

                        complete_tab = list_tab_name.get((list_tab_name.size() - 1));
                        for (int i = 0; i < list_tab_name.size(); i++) {
                            String key = list_tab_name.get(i);
                            horizontalList.add(key);
                            taxPlanningPagerAdapter.addFragment(TaxPlanningDynamic.newInstance(taxPromptModels, key, complete_tab), "Family details");
                            // taxPlanningPagerAdapter.addFragment(TaxPlanningTaxPaid.newInstance(taxPromptModels), "Tax Paid");
                        }

                        horizontalAdapter = new HorizantalAdapter(horizontalList);
                        LinearLayoutManager horizontalLayoutManagaer
                                = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                        horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);
                        horizontal_recycler_view.setAdapter(horizontalAdapter);

                        Log.e(TAG, "taxPlanningPagerAdapter:" + taxPlanningPagerAdapter.getCount());

                        viewPager.setAdapter(taxPlanningPagerAdapter);

                        //tabLayout.setupWithViewPager(viewPager);
                        // TabLayoutUtils.enableTabs(tabLayout, false);
                        viewPager.setOffscreenPageLimit(fragmentsList.size());


                        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                page_number = position;
                                Log.e(TAG, "page_number:" + page_number);
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }

                        });

                        if (not_refresh == 1) {
                            Log.e(TAG, "length:" + length + ":");
                     /*       tabPageState.clear();
                            tapPageOriginalState.clear();
                            for (int j = 0; j < horizontalList.size(); j++) {
                                tabPageState.add(j);
                            }
                            tapPageOriginalState.addAll(tabPageState);
                            //   tapPageOriginalState.remove(tapPageOriginalState.size());
                            viewPager.setCurrentItem(0);
                            horizontal_recycler_view.scrollToPosition(0);
                            horizontalAdapter.notifyDataSetChanged();*/

                            if (taxPromptModels.getData().getTab_visited_status().size() == 0) {
                                tabPageState.clear();
                                tabPageState.add(0);
                                tapPageOriginalState.addAll(tabPageState);
                                // tapPageOriginalState.remove(tapPageOriginalState.size() - 1);
                                viewPager.setCurrentItem(0);
                                horizontal_recycler_view.scrollToPosition(0);
                                horizontalAdapter.notifyDataSetChanged();
                            }

                            for (int i = 0; i < taxPromptModels.getData().getTab_visited_status().size(); i++) {
                                Log.d("horizontalList:>>--", taxPromptModels.getData().getTab_visited_status().get(i).getTab_name() + ":" + horizontalList.get(i));
                                if (taxPromptModels.getData().getTab_visited_status().get(i).getTab_name().equalsIgnoreCase(horizontalList.get(i))) {
                                    tabPageState.add(i);
                                    Log.d("horizontalList:<<--", taxPromptModels.getData().getTab_visited_status().get(i).getTab_name() + ":" + horizontalList.get(i));
                                    tapPageOriginalState.addAll(tabPageState);
                                    tapPageOriginalState.remove(tapPageOriginalState.size() - 1);
                                    viewPager.setCurrentItem(i);
                                    horizontal_recycler_view.scrollToPosition(i);
                                    horizontalAdapter.notifyDataSetChanged();

                                }
                            }
                            for (int i = 0; i < 0; i++) {
                                Log.e(TAG, "for loop:" + i + ":");
                                if (taxPromptModels.getData().getTab_visited_status().get(i).getTab_name().equalsIgnoreCase("Personaldetail")) {
                                    Log.e(TAG, "for loop:" + i + "");

                                    tabPageState.clear();
                                    tapPageOriginalState.clear();
                                    for (int j = 0; j < 2; j++) {
                                        tabPageState.add(j);
                                    }
                                    tapPageOriginalState.addAll(tabPageState);
                                    tapPageOriginalState.remove(tapPageOriginalState.size() - 1);
                                    viewPager.setCurrentItem(1);
                                    horizontal_recycler_view.scrollToPosition(1);
                                    horizontalAdapter.notifyDataSetChanged();
                                } else if (taxPromptModels.getData().getTab_visited_status().get(i).getTab_name().equalsIgnoreCase("Familydetail")) {

                                    Log.e(TAG, "for loop:" + i + ":Familydetail");
                                    tabPageState.clear();
                                    tapPageOriginalState.clear();
                                    for (int j = 0; j < 3; j++) {
                                        tabPageState.add(j);

                                    }
                                    tapPageOriginalState.addAll(tabPageState);
                                    tapPageOriginalState.remove(tapPageOriginalState.size() - 1);
                                    viewPager.setCurrentItem(2);
                                    horizontal_recycler_view.scrollToPosition(2);
                                    horizontalAdapter.notifyDataSetChanged();
                                } else if (taxPromptModels.getData().getTab_visited_status().get(i).getTab_name().equalsIgnoreCase("IncomefromSP")) {

                                    Log.e(TAG, "for loop:" + i + ":IncomefromSP");
                                    tabPageState.clear();
                                    tapPageOriginalState.clear();
                                    for (int j = 0; j < 4; j++) {
                                        tabPageState.add(j);

                                    }
                                    tapPageOriginalState.addAll(tabPageState);
                                    tapPageOriginalState.remove(tapPageOriginalState.size() - 1);
                                    viewPager.setCurrentItem(3);
                                    horizontal_recycler_view.scrollToPosition(3);
                                    horizontalAdapter.notifyDataSetChanged();
                                } else if (taxPromptModels.getData().getTab_visited_status().get(i).getTab_name().equalsIgnoreCase("IncomefromHP")) {
                                    Log.e(TAG, "for loop:" + i + ":IncomefromHP");

                                    tabPageState.clear();
                                    tapPageOriginalState.clear();
                                    for (int j = 0; j < 5; j++) {
                                        tabPageState.add(j);

                                    }
                                    tapPageOriginalState.addAll(tabPageState);
                                    tapPageOriginalState.remove(tapPageOriginalState.size() - 1);
                                    viewPager.setCurrentItem(4);
                                    horizontal_recycler_view.scrollToPosition(4);
                                    horizontalAdapter.notifyDataSetChanged();
                                } else if (taxPromptModels.getData().getTab_visited_status().get(i).getTab_name().equalsIgnoreCase("IncomefromBP")) {
                                    Log.e(TAG, "for loop:" + i + ":IncomefromBP");

                                    tabPageState.clear();
                                    tapPageOriginalState.clear();
                                    for (int j = 0; j < 6; j++) {
                                        tabPageState.add(j);

                                    }
                                    tapPageOriginalState.addAll(tabPageState);
                                    tapPageOriginalState.remove(tapPageOriginalState.size() - 1);
                                    viewPager.setCurrentItem(5);
                                    horizontal_recycler_view.scrollToPosition(5);
                                    horizontalAdapter.notifyDataSetChanged();
                                } else if (taxPromptModels.getData().getTab_visited_status().get(i).getTab_name().equalsIgnoreCase("IncomefromCG")) {
                                    Log.e(TAG, "for loop:" + i + ":IncomefromCG");

                                    tabPageState.clear();
                                    tapPageOriginalState.clear();
                                    for (int j = 0; j < 7; j++) {
                                        tabPageState.add(j);
                                    }
                                    tapPageOriginalState.addAll(tabPageState);
                                    tapPageOriginalState.remove(tapPageOriginalState.size() - 1);
                                    horizontalAdapter.notifyDataSetChanged();
                                    viewPager.setCurrentItem(6);
                                    horizontal_recycler_view.scrollToPosition(6);
                                } else if (taxPromptModels.getData().getTab_visited_status().get(i).getTab_name().equalsIgnoreCase("IncomefromOS")) {
                                    Log.e(TAG, "for loop:" + i + ":IncomefromOS");

                                    tabPageState.clear();
                                    tapPageOriginalState.clear();
                                    for (int j = 0; j < 8; j++) {
                                        tabPageState.add(j);
                                    }
                                    tapPageOriginalState.addAll(tabPageState);
                                    tapPageOriginalState.remove(tapPageOriginalState.size() - 1);
                                    horizontalAdapter.notifyDataSetChanged();
                                    viewPager.setCurrentItem(7);
                                    horizontal_recycler_view.scrollToPosition(7);
                                } else if (taxPromptModels.getData().getTab_visited_status().get(i).getTab_name().equalsIgnoreCase("TaxBenefits80C")) {
                                    Log.e(TAG, "for loop:" + i + ":TaxBenefits80C");

                                    tabPageState.clear();
                                    tapPageOriginalState.clear();
                                    for (int j = 0; j < 9; j++) {
                                        tabPageState.add(j);
                                    }
                                    tapPageOriginalState.addAll(tabPageState);
                                    tapPageOriginalState.remove(tapPageOriginalState.size() - 1);
                                    horizontalAdapter.notifyDataSetChanged();
                                    viewPager.setCurrentItem(8);
                                    horizontal_recycler_view.scrollToPosition(8);
                                } else if (taxPromptModels.getData().getTab_visited_status().get(i).getTab_name().equalsIgnoreCase("TaxBenefits80D")) {
                                    Log.e(TAG, "for loop:" + i + ":TaxBenefits80D");

                                    tabPageState.clear();
                                    tapPageOriginalState.clear();
                                    for (int j = 0; j < 10; j++) {
                                        tabPageState.add(j);
                                    }
                                    tapPageOriginalState.addAll(tabPageState);
                                    tapPageOriginalState.remove(tapPageOriginalState.size() - 1);
                                    horizontalAdapter.notifyDataSetChanged();
                                    viewPager.setCurrentItem(9);
                                    horizontal_recycler_view.scrollToPosition(9);
                                } else if (taxPromptModels.getData().getTab_visited_status().get(i).getTab_name().equalsIgnoreCase("TaxBenefits80E")) {
                                    Log.e(TAG, "for loop:" + i + ":TaxBenefits80E");

                                    tabPageState.clear();
                                    tapPageOriginalState.clear();
                                    for (int j = 0; j < 11; j++) {
                                        tabPageState.add(j);
                                    }
                                    tapPageOriginalState.addAll(tabPageState);
                                    tapPageOriginalState.remove(tapPageOriginalState.size() - 1);
                                    horizontalAdapter.notifyDataSetChanged();
                                    viewPager.setCurrentItem(10);
                                    horizontal_recycler_view.scrollToPosition(10);
                                } else if (taxPromptModels.getData().getTab_visited_status().get(i).getTab_name().equalsIgnoreCase("TaxBenefitsOthers")) {

                                    Log.e(TAG, "for loop:" + i + ":TaxBenefitsOthers");
                                    tabPageState.clear();
                                    tapPageOriginalState.clear();
                                    for (int j = 0; j < 12; j++) {
                                        tabPageState.add(j);
                                    }
                                    tapPageOriginalState.addAll(tabPageState);
                                    tapPageOriginalState.remove(tapPageOriginalState.size() - 1);
                                    horizontalAdapter.notifyDataSetChanged();
                                    viewPager.setCurrentItem(11);
                                    horizontal_recycler_view.scrollToPosition(11);
                                } else if (taxPromptModels.getData().getTab_visited_status().get(i).getTab_name().equalsIgnoreCase("TaxBenefits24")) {

                                    Log.e(TAG, "for loop:" + i + ":TaxBenefits24");
                                    tabPageState.clear();
                                    tapPageOriginalState.clear();
                                    for (int j = 0; j < 13; j++) {
                                        tabPageState.add(j);
                                    }
                                    tapPageOriginalState.addAll(tabPageState);
                                    tapPageOriginalState.remove(tapPageOriginalState.size() - 1);
                                    horizontalAdapter.notifyDataSetChanged();
                                    viewPager.setCurrentItem(12);
                                    horizontal_recycler_view.scrollToPosition(12);
                                }
                            }

                        } else {
                            not_refresh = 1;
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaxPromptModels> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_taxfile, menu);
        MenuItem item = menu.findItem(R.id.menu_taxfile);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_taxfile:
                try {
                    resetTaxFilingDialog(mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void resetTaxFilingDialog(Context mContext) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialog;

        inflater = LayoutInflater.from(mContext);
        dialogView = inflater.inflate(R.layout.yes_no_reset, null);
        alertDialog = new android.support.v7.app.AlertDialog.Builder(mContext).create();
        alertDialog.setView(dialogView);
        final TextView txt_heading = dialogView.findViewById(R.id.txt_heading);
        txt_heading.setText("Redo All");
        final TextView textView = dialogView.findViewById(R.id.additional_yes);
        textView.setText("Are you sure you want to Redo all tabs and do the details again?");
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callDeleteTaxPromptStatement("", "");

                alertDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    public void callDeleteTaxPromptStatement(String question_id, String flag_id) {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<DeleteTaxStatemetModel> call = webServiceObj.deleteTaxPromptStatementNew(UtileKit.getPersistedPurplePathPref("user_id")
                , question_id, flag_id);
        call.enqueue(new Callback<DeleteTaxStatemetModel>() {
            @Override
            public void onResponse(Call<DeleteTaxStatemetModel> call, Response<DeleteTaxStatemetModel> response) {
                UtileKit.dismisssSpinnerDialog();
                DeleteTaxStatemetModel deleteModel = response.body();
                try {
                    if (!deleteModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        UtileKit.alertDialog("message", deleteModel.getData().getMessage(), mContext);
                    } else {
                        not_refresh = 0;
                        tabPageState.clear();
                        tapPageOriginalState.clear();
                        viewPager.setCurrentItem(0);
                        horizontalAdapter.notifyDataSetChanged();

                        refreshView(taxView);

                        callGetTaxPromptService();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DeleteTaxStatemetModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();

            }
        });
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_left_arrow: {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home: {
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                //getActivity().finish();
            }
            break;
        }
    }
}


