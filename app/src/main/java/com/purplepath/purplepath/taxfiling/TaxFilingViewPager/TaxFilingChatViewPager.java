package com.purplepath.purplepath.taxfiling.TaxFilingViewPager;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxfiling.TaxFilingViewPager.model.QuestionData;
import com.purplepath.purplepath.taxfiling.TaxFilingViewPager.model.TaxFilingModel;
import com.purplepath.purplepath.taxfiling.TaxFilingViewPager.pagerAdapters.TaxFilingPagerAdapter;
import com.purplepath.purplepath.taxfiling.TaxFilingViewPager.recycleradapters.HorizontalAdapters;
import com.purplepath.purplepath.taxfiling.dialogtaxfilefragments.TaxFilingSectionDialog;
import com.purplepath.purplepath.taxprompt.model.DeleteTaxStatemetModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by pravinr on 8/7/18.
 * Modified by RAJASEKAR B 04/2/2019
 * <p>
 * Chart Dynamic view
 */

public class TaxFilingChatViewPager extends BaseFragment implements View.OnClickListener {

    private String TAG = "TaxFilingChatViewPager";
    private OnActivityBackPressedListener mCallBackListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    public static Context mContext;

    View taxView;

    public static int page_numbers = 0;

    static int not_refreshs = 1;

    public static ViewPager viewPagers;

    public static ArrayList<Fragment> fragmentsLists = new ArrayList<Fragment>();

    public static TaxFilingPagerAdapter taxFilingPagerAdapter;

    public static RecyclerView horizontal_recycler_views;

    public static HorizontalAdapters horizontalAdapters;

    private ArrayList<String> horizontalLists;

    public static ArrayList<Integer> tabPageStates = new ArrayList<Integer>();

    public static ArrayList<Integer> tapPageOriginalStates = new ArrayList<Integer>();

    Boolean dialogShownTaxFilingSection = false;


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

        refreshView(taxView);
        callGetTaxPromptService();

        return taxView;

    }

    private void refreshView(View taxView) {
        tabPageStates.clear();
        tapPageOriginalStates.clear();
        horizontal_recycler_views = (RecyclerView) taxView.findViewById(R.id.horizontal_recycler_view);
        viewPagers = taxView.findViewById(R.id.pager);
        mleftRelativeLayout = taxView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = taxView. findViewById(R.id.relative_center_home);

        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        taxFilingPagerAdapter = TaxFilingPagerAdapter.newInstance(getChildFragmentManager());

        if (UtileKit.validateObjectValues(dialogShownTaxFilingSection)) {
            dialogShownTaxFilingSection = UtileKit.getPersistedPurplePathBoolPref("dialogShownTaxFilingSection");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!dialogShownTaxFilingSection) {
            UtileKit.persistingPurplePathPref("dialogShownTaxFilingSection", true);
            TaxFilingSectionDialog mguideview = new TaxFilingSectionDialog();
            mguideview.show(getFragmentManager(), "GuideView");
        }

    }


    public void callGetTaxPromptService() {
        Log.d("callGetTaxPrompt:", "callGetTaxPromptService");
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TaxFilingModel> call = webServiceObj.callGetTaxFileServiceNew(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<TaxFilingModel>() {
            @Override
            public void onResponse(Call<TaxFilingModel> call, Response<TaxFilingModel> response) {
                UtileKit.dismisssSpinnerDialog();
                TaxFilingModel taxPromptModels = response.body();
                try {
                    if (taxPromptModels.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        Iterator myVeryOwnIterator = taxPromptModels.getData().getKeyQuestion().keySet().iterator();
                        HashMap<String, ArrayList<QuestionData>> overall_questions = taxPromptModels.getData().getKeyQuestion();
                        horizontalLists = new ArrayList<>();
                        int ii = 0;
                        ArrayList<String> list_tab_name = taxPromptModels.getData().getAva_tab();
                        String complete = list_tab_name.get((list_tab_name.size() - 1))/*"Address"*/;
                        Log.d("complete_name", complete);
                        for (int i = 0; i < list_tab_name.size(); i++) {

                            String key = list_tab_name.get(i);
                            Log.d("key_name", key);
                            horizontalLists.add(key);
                            ii++;
                            Log.e(TAG, "i:" + ii + ":");
                            Log.e(TAG, "horizontalLists:" + horizontalLists.size() + ":");
                            Log.w("Viswa_testing_value", key+":"+complete);
                            taxFilingPagerAdapter.addFragment(TaxFilingDynamic.newInstance(taxPromptModels, key, complete), key);
                        }

                        tabPageStates.add(0);
                        horizontalAdapters = new HorizontalAdapters(horizontalLists);
                        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                        horizontal_recycler_views.setLayoutManager(horizontalLayoutManagaer);
                        horizontal_recycler_views.setAdapter(horizontalAdapters);


/*

                        taxFilingPagerAdapter.addFragment(TaxFilingDynamic.newInstance(taxPromptModels), "Personal Info");
                        taxFilingPagerAdapter.addFragment(TaxFilingAddress.newInstance(taxPromptModels), "Address");
                        taxFilingPagerAdapter.addFragment(TaxFilingStatus.newInstance(taxPromptModels), "Filing Status");
                        taxFilingPagerAdapter.addFragment(TaxFilingBankDetails.newInstance(taxPromptModels), "Bank Details");
*/

                        viewPagers.setAdapter(taxFilingPagerAdapter);
                        viewPagers.setOffscreenPageLimit(fragmentsLists.size());


                        viewPagers.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                page_numbers = position;
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }

                        });
                        if (not_refreshs == 1) {
                            if (taxPromptModels.getData().getTab_visited_status().size() == 0) {
                                tabPageStates.clear();
                                tabPageStates.add(0);
                                tapPageOriginalStates.addAll(tabPageStates);
                                // tapPageOriginalState.remove(tapPageOriginalState.size() - 1);
                                viewPagers.setCurrentItem(0);
                                horizontal_recycler_views.scrollToPosition(0);
                                horizontalAdapters.notifyDataSetChanged();
                            }
                            for (int i = 0; i < taxPromptModels.getData().getTab_visited_status().size(); i++) {
                                Log.d("horizontalList:>>--", taxPromptModels.getData().getTab_visited_status().get(i).getTab_name() + ":" + horizontalLists.get(i));
                                if (taxPromptModels.getData().getTab_visited_status().get(i).getTab_name().equalsIgnoreCase(horizontalLists.get(i))) {
                                    tabPageStates.add(i);
                                    Log.d("horizontalList:<<--", taxPromptModels.getData().getTab_visited_status().get(i).getTab_name() + ":" + horizontalLists.get(i));
                                    tapPageOriginalStates.addAll(tabPageStates);
                                    tapPageOriginalStates.remove(tapPageOriginalStates.size() - 1);
                                    viewPagers.setCurrentItem(i);
                                    horizontal_recycler_views.scrollToPosition(i);
                                    horizontalAdapters.notifyDataSetChanged();

                                }
                            }
                        } else {
                            not_refreshs = 1;
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaxFilingModel> call, Throwable t) {
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
                   // callDeleteTaxPromptStatement("", "");

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
        alertDialog = new androidx.appcompat.app.AlertDialog.Builder(mContext).create();
        alertDialog.setView(dialogView);
        final TextView txt_heading = dialogView.findViewById(R.id.txt_heading);
        txt_heading.setText("Redo All");
        final TextView textView = dialogView.findViewById(R.id.additional_yes);
        textView.setText("Are you sure you want to Redo all tabs and do the details again?");
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callDeleteTaxPromptStatement("", "");
                callGetTaxPromptService();

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
        Call<DeleteTaxStatemetModel> call = webServiceObj.deleteTaxFileStatementNew(UtileKit.getPersistedPurplePathPref("user_id")
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
                        not_refreshs = 0;
                        tabPageStates.clear();
                        tapPageOriginalStates.clear();
                        viewPagers.setCurrentItem(0);
                        horizontalAdapters.notifyDataSetChanged();
                        refreshView(taxView);
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
                startHomeActivity();
//                Intent i = new Intent(mContext, HomePageActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(i);
//                getActivity().finish();
            }
            break;
            case R.id.relative_right_arrow: {

            }
            break;

        }
    }
}
