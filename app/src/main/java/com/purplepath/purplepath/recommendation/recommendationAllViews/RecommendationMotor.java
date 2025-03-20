package com.purplepath.purplepath.recommendation.recommendationAllViews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.recommendation.adapter.RecomendationTableAdapter;
import com.purplepath.purplepath.recommendation.model.RecommendData;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxproduct.model.Cell;
import com.purplepath.purplepath.taxproduct.model.ColumnHeader;
import com.purplepath.purplepath.taxproduct.model.RowHeader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by pravinr on 3/7/18.
 */

public class RecommendationMotor extends BaseFragment implements View.OnClickListener  {

    private Context mContext;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private OnActivityBackPressedListener mCallBackListener;

    RecommendData recommendData;

    private AbstractTableAdapter mTableViewAdapter;

    private TableView mTableView;

    private ArrayList<String> mColoumNameListHealth=new ArrayList<String>( Arrays.asList("Insurance Product Type", "Cover Required", "Cover Availed","Cover Recommendation","Anual Premium"));

    RelativeLayout auto_insurance_container;

    TextView empty_values,title;


    public static RecommendationMotor newInstance(RecommendData recommendData) {
        RecommendationMotor recommend = new RecommendationMotor();
        Bundle args = new Bundle();
        if (recommendData != null) {
            args.putSerializable("recommendData", recommendData);
        }
        recommend.setArguments(args);
        return recommend;
    }

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
        return inflater.inflate(R.layout.fragment_recommendation_motor, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCallBackListener.setActionBarTitle("Motor Insurance");

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);

        empty_values= view.findViewById(R.id.empty_values);

        title= view.findViewById(R.id.title);
     //   title.setText("Your Motor Insurance");

        auto_insurance_container = view.findViewById(R.id.auto_insurance_container);

        mTableView = createTableView();
        auto_insurance_container.addView(mTableView);


        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey("recommendData")) {
                recommendData = (RecommendData) args.getSerializable("recommendData");
                try {
                    if (recommendData.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        if (null != recommendData.getData().getMotor_ins_plan()&&
                                recommendData.getData().getMotor_ins_plan().size()>0) {
                            title.setText("Your Motor Insurance");
                            loadAutoInsuranceData(recommendData);
                        }else {
                            auto_insurance_container.setVisibility(View.GONE);
                            empty_values.setVisibility(View.VISIBLE);
                        }
                    }else {
                        UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }


       // callAllInsuranceService();
    }


    private TableView createTableView() {
        TableView tableView = new TableView(getContext());
        // Set adapter
        mTableViewAdapter = new RecomendationTableAdapter(mContext);
        tableView.setAdapter(mTableViewAdapter);
        // Set layout params
        FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams
                .MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        tableView.setLayoutParams(tlp);
        return tableView;
    }
    private void loadAutoInsuranceData(RecommendData recommendData) {
        List<RowHeader> rowHeaders = getRowHeaderListAuto(recommendData);
        List<List<Cell>> cellList = getCellListForSortingTestAutoInsurance(recommendData);
        List<ColumnHeader> columnHeaders = getColumnHeaderListCash(mColoumNameListHealth);
        mTableViewAdapter.setAllItems(columnHeaders, rowHeaders, cellList);
    }
    private List<ColumnHeader> getColumnHeaderListCash(ArrayList<String> mColoumNameList) {
        List<ColumnHeader> list = new ArrayList<>();
        for (int i = 0; i < mColoumNameList.size(); i++) {
            ColumnHeader header = new ColumnHeader(""+i, mColoumNameList.get(i));
            list.add(header);
        }
        return list;
    }
    private List<RowHeader> getRowHeaderListAuto(RecommendData recommendData) {
        List<RowHeader> list = new ArrayList<>();
        int n=recommendData.getData().getMotor_ins_plan().size();
        for (int i = 0; i <n ; i++) {
            RowHeader header = new RowHeader("row " + i,""+(i+1));
            list.add(header);
        }
        return list;
    }
    private List<List<Cell>> getCellListForSortingTestAutoInsurance(RecommendData recommendData) {

        List<List<Cell>> list = new ArrayList<>();
        String rowsadd ="";
        int length = recommendData.getData().getMotor_ins_plan().size();

        if (null!=recommendData.getData().getMotor_ins_plan()) {
            for (int i = 0; i < length; i++) {

                List<Cell> cellList = new ArrayList<>();

                String ins_prod_type="",annual_prem="",cover_availed="",cover_required="",cover_recommended="";
                if(recommendData.getData().getMotor_ins_plan().get(i).getIns_prod_type()!= null){
                    ins_prod_type=recommendData.getData().getMotor_ins_plan().get(i).getIns_prod_type();
                }else{
                    ins_prod_type= "0";
                }
                if(recommendData.getData().getMotor_ins_plan().get(i).getCover_required()!= null){
                    cover_required="₹ "+(UtileKit.currToCharConversion(recommendData.getData().
                            getMotor_ins_plan().get(i).getCover_required()));
                }else{
                    cover_required="₹ "+ "0";
                }
                if(recommendData.getData().getMotor_ins_plan().get(i).getCover_availed()!= null){
                    cover_availed=(UtileKit.currToCharConversion(recommendData.getData().
                            getMotor_ins_plan().get(i).getCover_availed()));
                }else{
                    cover_availed= "₹ "+"0";
                }

                if(recommendData.getData().getMotor_ins_plan().get(i).getCover_recommended()!= null){
                    cover_recommended="₹ "+(UtileKit.currToCharConversion(recommendData.getData().
                            getMotor_ins_plan().get(i).getCover_recommended()));
                }else {
                    cover_recommended = "₹ "+"0";
                }
                if(recommendData.getData().getMotor_ins_plan().get(i).getAnnual_prem()!= null){
                    annual_prem="₹ "+(UtileKit.currToCharConversion(recommendData.getData().
                            getMotor_ins_plan().get(i).getAnnual_prem()));
                }else{
                    annual_prem="₹ "+ "0";
                }
                cellList.add(new Cell(i+"00", ins_prod_type));
                cellList.add(new Cell(i+"01", cover_required));
                cellList.add(new Cell(i+"02", cover_availed));
                cellList.add(new Cell(i+"03", cover_recommended));
                cellList.add(new Cell(i+"03", annual_prem));


                list.add(cellList);
            }
        }
        return list;
    }


    private void callAllInsuranceService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<RecommendData> call = webServiceObj.triggerRecommendationService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<RecommendData>() {
            @Override
            public void onResponse(Call<RecommendData> call, Response<RecommendData> response) {
                try {
                    UtileKit.dismisssSpinnerDialog();
                    recommendData = response.body();

                    if (recommendData.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        loadAutoInsuranceData(recommendData);
                    }
                    else{

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RecommendData> call, Throwable t) {

                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
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
