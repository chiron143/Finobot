package com.purplepath.purplepath.recommendation.recommendationAllViews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.evrencoskun.tableview.TableView;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.recommendation.adapter.RecomendationSavingTableAdapter;
import com.purplepath.purplepath.recommendation.getsaveinvestmodel.Saveinvestmodel;
import com.purplepath.purplepath.recommendation.getsaveinvestmodel.User_invst_goals;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxproduct.model.Cell;
import com.purplepath.purplepath.taxproduct.model.ColumnHeader;
import com.purplepath.purplepath.taxproduct.model.RowHeader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by pravinr on 3/7/18.
 */

public class RecommendationSavingsInvestment extends BaseFragment implements View.OnClickListener  {

    private Context mContext;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private OnActivityBackPressedListener mCallBackListener;

    private Saveinvestmodel saveinvestmodel;

    private RecomendationSavingTableAdapter recomendationSavingTableAdapter;

    RelativeLayout tableParentView;

    private TableView mTableView;

    HashSet<String> asset_hash = new HashSet<String>();

    private ArrayList<String> mColoumNameListSavingInvest=new ArrayList<String>( Arrays.asList("Assets", "Current Values", "Target future cost","Time to active","Resource used","Investment needed"));

    TextView empty_values,title;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {}
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_popup_savinginvestment, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCallBackListener.setActionBarTitle("Savings and Investments");


        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);

        empty_values= view.findViewById(R.id.empty_values);
        title= view.findViewById(R.id.title);

        mTableView = createTableViewSavings();
        tableParentView= view.findViewById(R.id.tableParentView_saving_investment);
        tableParentView.addView(mTableView);


        callAllInsuranceService();

    }
    private TableView createTableViewSavings() {
        TableView tableView = new TableView(getContext());
        // Set adapter
        recomendationSavingTableAdapter = new RecomendationSavingTableAdapter(mContext);
        tableView.setAdapter(recomendationSavingTableAdapter);
        // Set layout params
        FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams
                .MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        tableView.setLayoutParams(tlp);
        return tableView;
    }
    private void callAllInsuranceService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<Saveinvestmodel> call = webServiceObj.getSaveInvestmentRecommendation(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<Saveinvestmodel>() {
            @Override
            public void onResponse(Call<Saveinvestmodel> call, Response<Saveinvestmodel> response) {
                try {
                    UtileKit.dismisssSpinnerDialog();
                    saveinvestmodel = response.body();

                    if (saveinvestmodel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        if (null != saveinvestmodel.getData().getUser_invst_goals()&&
                                saveinvestmodel.getData().getUser_invst_goals().size()>0) {

                            title.setText("Your Saving and Investment");

                            applySavingInvestment(saveinvestmodel);
                        }else {
                            tableParentView.setVisibility(View.GONE);
                            empty_values.setVisibility(View.VISIBLE);
                        }
                    }
                    else{
                        empty_values.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Saveinvestmodel> call, Throwable t) {

                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void applySavingInvestment(Saveinvestmodel saveinvestmodel) {
        if (saveinvestmodel.getData().getUser_invst_goals()!= null) {


            int length=saveinvestmodel.getData().getUser_invst_goals().size();

            for(int i=0;i<length;i++) {
                asset_hash.add(saveinvestmodel.getData().getUser_invst_goals().get(i).getAsset_class());
            }
            ArrayList<String> arrayList = new ArrayList<String>(asset_hash);


            ArrayList< ArrayList<User_invst_goals>> mfilterarray = new ArrayList<>();

            for(int j=0;j<arrayList.size();j++){

                String str_obj=arrayList.get(j);
                {
                    if (str_obj!= null) {
                        ArrayList<User_invst_goals>asset_heading=new ArrayList<User_invst_goals>();
                        for (int k = 0; k < length; k++) {
                            if (str_obj.equalsIgnoreCase(saveinvestmodel.getData().getUser_invst_goals().get(k).getAsset_class())) {

                                asset_heading.add(saveinvestmodel.getData().getUser_invst_goals().get(k));

                            }
                        }
                        mfilterarray.add(asset_heading);
                    }
                }
            }
            showWebViewSavingInvest(mfilterarray);

        }
    }

    private void showWebViewSavingInvest(ArrayList<ArrayList<User_invst_goals>> saveinvestmodel) {
        loadSaving_InvestmentData(saveinvestmodel);
    }

    private void loadSaving_InvestmentData(ArrayList<ArrayList<User_invst_goals>> saveinvestmodel) {
        List<RowHeader> rowHeaders = getRowHeaderListSavingInvest(saveinvestmodel);
        List<List<Cell>> cellList = getCellListForSortingTestSavingInvest(saveinvestmodel);
        List<ColumnHeader> columnHeaders = getColumnHeaderListCash(mColoumNameListSavingInvest);
        recomendationSavingTableAdapter.setAllItems(columnHeaders, rowHeaders, cellList);
    }

    private List<List<Cell>> getCellListForSortingTestSavingInvest(ArrayList<ArrayList<User_invst_goals>> saveinvestmodel) {

        List<List<Cell>> list = new ArrayList<>();

        int length = saveinvestmodel.size();
        for (int i=0;i<length;i++) {


            int inner_length = saveinvestmodel.get(i).size();
            if (null != saveinvestmodel) {

                for (int j = 0; j < inner_length; j++) {
                    List<Cell> cellList = new ArrayList<>();
                    String asset_class = "", lev1_name = "", current_value = "",
                            target_fv = "", goal_years = "", salary = "",
                            property = "", business = "", pmt_1_year = "", pmt_1_mon = "", pval = "";
                    if(i ==0){
                    if(saveinvestmodel.get(i).get(0).getAsset_class()!= null
                            &&!saveinvestmodel.get(i).get(0).getAsset_class().isEmpty()){
                        asset_class=saveinvestmodel.get(i).get(0).getAsset_class();
                    }
                       }
                    if(saveinvestmodel.get(i).get(j).getLev1_name()!= null){
                        lev1_name=saveinvestmodel.get(i).get(j).getLev1_name();
                    }

                    if (saveinvestmodel.get(i).get(j).getCurrent_value() != null) {
                        current_value = (UtileKit.currToCharConversion(saveinvestmodel.get(i).get(j).
                                getCurrent_value()));
                    } else {
                        current_value = "0";
                    }

                    if (saveinvestmodel.get(i).get(j).getTarget_fv() != null) {
                        target_fv = (UtileKit.currToCharConversion(saveinvestmodel.get(i).get(j).getTarget_fv()));
                    } else {
                        target_fv = "0";
                    }

                    if (saveinvestmodel.get(i).get(j).getGoal_years() != null) {
                        goal_years = saveinvestmodel.get(i).get(j).getGoal_years();
                    } else {
                        goal_years = "0";
                    }

                    if (saveinvestmodel.get(i).get(j).getResources_used().getSalary() != null) {
                        salary = String.valueOf((Float.parseFloat(saveinvestmodel.get(i).get(j).
                                getResources_used().getSalary()) * 100));
                    } else {
                        salary = "0";
                    }

                    if (saveinvestmodel.get(i).get(j).getResources_used().getProperty() != null) {
                        property = String.valueOf((Float.parseFloat(saveinvestmodel.get(i).get(j).
                                getResources_used().getProperty()) * 100));
                    } else {
                        property = "0";
                    }

                    if (saveinvestmodel.get(i).get(j).getResources_used().getBusiness() != null) {
                        business = String.valueOf((Float.parseFloat(saveinvestmodel.get(i).get(j).
                                getResources_used().getBusiness()) * 100));
                    } else {
                        business = "0";
                    }

                    if (saveinvestmodel.get(i).get(j).getPmt_1_year() != null) {
                        pmt_1_year = (UtileKit.currToCharConversion(saveinvestmodel.get(i).get(j).getPmt_1_year()));
                    } else {
                        pmt_1_year = "0";
                    }

                    if (saveinvestmodel.get(i).get(j).getPmt_1_mon() != null) {
                        pmt_1_mon = (UtileKit.currToCharConversion(saveinvestmodel.get(i).get(j).getPmt_1_mon()));
                    } else {
                        pmt_1_mon = "0";
                    }


                    if (saveinvestmodel.get(i).get(j).getPval() != null) {
                        pval = (UtileKit.currToCharConversion(saveinvestmodel.get(i).get(j).getPval()));
                    } else {
                        pval = "0";
                    }


                    cellList.add(new Cell(i+"04", asset_class+"\n"+lev1_name));
                    cellList.add(new Cell(i+"00", current_value));
                    cellList.add(new Cell(i+"01", target_fv));
                    cellList.add(new Cell(i+"02", goal_years));
                    cellList.add(new Cell(i+"03", "Salary - "+salary+" % "+"\n"+"Property - "+property+" % "+"\n"+"Business - "+business+" % " ));
                    cellList.add(new Cell(i+"04",  ""+pmt_1_year+" p.a."+"\n"+"₹ "+pmt_1_mon+" p.m."+"\n"+ "₹ "+pval+" lumpsum"));





                    list.add(cellList);
                }

            }
        }
        return list;
    }

    private List<ColumnHeader> getColumnHeaderListCash(ArrayList<String> mColoumNameList) {
        List<ColumnHeader> list = new ArrayList<>();
        for (int i = 0; i < mColoumNameList.size(); i++) {
            ColumnHeader header = new ColumnHeader(""+i, mColoumNameList.get(i));
            list.add(header);
        }
        return list;
    }

    private List<RowHeader> getRowHeaderListSavingInvest(ArrayList<ArrayList<User_invst_goals>> saveinvestmodel) {
        List<RowHeader> list = new ArrayList<>();

        int k=0;
        int n=saveinvestmodel.size();
        for (int i = 0; i <n ; i++) {

            int inner_length=saveinvestmodel.get(i).size();

            for (int j = 0; j <inner_length ; j++) {
                RowHeader header = new RowHeader("row " + k, "" + (k ++));
                list.add(header);
            }
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_left_arrow:
            {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home:
            {
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
            break;


        }
    }

}
