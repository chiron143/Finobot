package com.purplepath.purplepath.taxfiling.uploadtaxfiles;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxfiling.TaxFilingConformationPdf;
import com.purplepath.purplepath.taxfiling.adapter.TaxFileCheckListAdapter;
import com.purplepath.purplepath.taxfiling.getchecklist.ChecklistModel;
import com.purplepath.purplepath.taxfiling.getchecklist.Result;
import com.purplepath.purplepath.taxfiling.getconformationmodel.ConformationModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finobot.finobot.MyApplication.mFirebaseAnalytics;


/**
 * Created by pravinr on 3/28/18.
 */

public class TaxFileCheckListFragment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;

    private ListView listview;

    private TaxFileCheckListAdapter taxFileCheckListAdapter;

    private ChecklistModel checklistModel;

    private ArrayList<Result> arrayListResult = new ArrayList<>();

    private FloatingActionButton fab_declaration;

    private ConformationModel conformationModel;

    private String approvalFlag = "";

    private CardView card_one;

    TextView empty_values, heading;

    String file_uploaded = "";

    private FrameLayout blink_frame;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            backPressedListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        arrayListResult.clear();

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_taxfiling_checklist, container, false);
        backPressedListener.setActionBarTitle("Income Tax Checklist");

        mleftRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        listview = (ListView) view.findViewById(R.id.listview);

        fab_declaration = (FloatingActionButton) view.findViewById(R.id.fab_declaration);
        fab_declaration.setOnClickListener(this);

        card_one = (CardView) view.findViewById(R.id.card_one);

        blink_frame = (FrameLayout) view.findViewById(R.id.blink_frame);

        empty_values = view.findViewById(R.id.empty_values);
        heading = view.findViewById(R.id.heading);


        callCheckListService();

        callConformationService();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.setCurrentScreen(getActivity(), getString(R.string.analtics_taxchecklist_screen), getActivity().getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "4");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Tax Module");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Tax");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }


    public void callCheckListService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(getActivity(), false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<ChecklistModel> call = webServiceObj.getCheckListService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<ChecklistModel>() {
            @Override
            public void onResponse(Call<ChecklistModel> call, Response<ChecklistModel> response) {
                UtileKit.dismisssSpinnerDialog();
                checklistModel = response.body();
                try {
                    if (checklistModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        if (null != checklistModel.getData().getResult()) {

                            arrayListResult.addAll(checklistModel.getData().getResult());

                            file_uploaded = checklistModel.getData().getFile_uploaded();

                            callConformationService();

                            if (arrayListResult.size() > 0) {
                                taxFileCheckListAdapter = new TaxFileCheckListAdapter(mContext, arrayListResult);
                                listview.setAdapter(taxFileCheckListAdapter);
                                heading.setVisibility(View.VISIBLE);
                            } else {
                                empty_values.setVisibility(View.VISIBLE);
                                heading.setVisibility(View.GONE);
                            }


                            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                                    Result section = arrayListResult.get(position);

                                    onclickPostionStartFragment(section);

                                }
                            });
                        }
                    } else {
                        UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ChecklistModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(getActivity(), t);
            }
        });
    }

    private void onclickPostionStartFragment(Result section) {
        addFragmenttoStack(TaxFileCheckListDocuments.newInstance(section));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }


    public void callConformationService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(getActivity(), false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<ConformationModel> call = webServiceObj.callGetTaxfileConformationService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<ConformationModel>() {
            @Override
            public void onResponse(Call<ConformationModel> call, Response<ConformationModel> response) {
                UtileKit.dismisssSpinnerDialog();
                conformationModel = response.body();
                try {
                    if (conformationModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        approvalFlag = conformationModel.getData().getApprove_flag();


                        if (approvalFlag.equalsIgnoreCase("false") && file_uploaded.equalsIgnoreCase("true")) {
                            card_one.setVisibility(View.VISIBLE);
                        }

                    } else {
                        UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ConformationModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(getActivity(), t);
            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                startSettingHomeActivity();
                break;
            case R.id.fab_declaration:

                callConformationService();

                if (file_uploaded.equalsIgnoreCase("false")) {
                    uploadMoreFileAlertDialog(mContext);
                } else if (approvalFlag.equalsIgnoreCase("false")) {

                    //No need here this screen as per client change
                    //  addFragmenttoStack(new TaxFileProcessScreen());
                    //Animation startAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_blinking);
                    // blink_frame.startAnimation(startAnimation);

                } else if (approvalFlag.equalsIgnoreCase("true")) {

                    addFragmenttoStack(new TaxFilingConformationPdf());
                }


                break;


        }

    }


    private void uploadMoreFileAlertDialog(Context context) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialog;

        inflater = LayoutInflater.from(context);
        dialogView = inflater.inflate(R.layout.alert_message_layout, null);
        alertDialog = new android.support.v7.app.AlertDialog.Builder(context).create();
        alertDialog.setView(dialogView);
        TextView stringErrorMessage = dialogView.findViewById(R.id.textViewDilog);
        stringErrorMessage.setText("Please upload the files to proceed");
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();

            }
        });

        alertDialog.show();
    }

}
