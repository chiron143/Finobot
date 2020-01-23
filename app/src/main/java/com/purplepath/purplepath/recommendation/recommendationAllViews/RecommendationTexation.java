package com.purplepath.purplepath.recommendation.recommendationAllViews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.purplepath.purplepath.recommendation.adapter.RecomendationTableAdapter;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.Ded_by_prod;
import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.TaxCashFlowModel;
import com.purplepath.purplepath.taxproduct.model.Cell;
import com.purplepath.purplepath.taxproduct.model.ColumnHeader;
import com.purplepath.purplepath.taxproduct.model.RowHeader;

import java.math.BigInteger;
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

public class RecommendationTexation extends BaseFragment implements View.OnClickListener  {

    private Context mContext;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private OnActivityBackPressedListener mCallBackListener;

    private RecomendationTableAdapter mTableViewAdapter;

    RelativeLayout tableParentView;

    private TableView mTableView;

    private TextView mamount_pay,maverage_taxs,mmar_tax;

    TaxCashFlowModel taxCashFlowModel;

    String total_tax_payable="",avg_tax_rate="",marg_tax_rate="";

    HashSet<String> taxproduct_hashset = new HashSet<String>();

    private BigInteger availed=BigInteger.ZERO;

    BigInteger entitle=BigInteger.ZERO;

    private BigInteger total_sub_all=BigInteger.ZERO;

    private ArrayList<String> mColoumNameListTaxation=new ArrayList<String>( Arrays.asList("Section", "Entitled", "Cover Availed","Pending"));

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
        return inflater.inflate(R.layout.view_popup_taxation, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCallBackListener.setActionBarTitle("Taxation");


        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);


        mTableView = createTableView();
        tableParentView= view.findViewById(R.id.tableParentView_taxation);
        tableParentView.addView(mTableView);

        mamount_pay= view.findViewById(R.id.amount_pay);
        maverage_taxs= view.findViewById(R.id.average_tax);
        mmar_tax= view.findViewById(R.id.mar_tax);

        callAllInsuranceService();

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
    private void callAllInsuranceService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TaxCashFlowModel> call = webServiceObj.callinsurance_tax_Cash_Flow_Service(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<TaxCashFlowModel>() {
            @Override
            public void onResponse(Call<TaxCashFlowModel> call, Response<TaxCashFlowModel> response) {
                try {
                    UtileKit.dismisssSpinnerDialog();
                    taxCashFlowModel = response.body();

                    if (taxCashFlowModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        applyTaxation(taxCashFlowModel);
                    }
                    else{

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaxCashFlowModel> call, Throwable t) {

                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void applyTaxation(TaxCashFlowModel taxCashFlowModel) {
        if(taxCashFlowModel.getData().getTax_calc()!=null){
            if(taxCashFlowModel.getData().getTax_calc().getTotal_tax_payable()!=null) {
                total_tax_payable = taxCashFlowModel.getData().getTax_calc().getTotal_tax_payable();
            }
            if(taxCashFlowModel.getData().getTax_calc().getAvg_tax_rate()!=null) {
                avg_tax_rate = taxCashFlowModel.getData().getTax_calc().getAvg_tax_rate();
            }
            if(taxCashFlowModel.getData().getTax_calc().getMarg_tax_rate()!=null) {
                marg_tax_rate = taxCashFlowModel.getData().getTax_calc().getMarg_tax_rate();
            }
        }

        if(taxCashFlowModel.getData().getDed_by_prod()!=null){
            int length=taxCashFlowModel.getData().getDed_by_prod().size();
            for(int i=0;i<length;i++){
                taxproduct_hashset.add(taxCashFlowModel.getData().getDed_by_prod().get(i).getTax_section());
            }
            ArrayList<String> arrayList = new ArrayList<String>(taxproduct_hashset);
            ArrayList< ArrayList<Ded_by_prod>> mfilterarray = new ArrayList<>();
            for(int j=0;j<arrayList.size();j++){
                String str_obj=arrayList.get(j);
                if (str_obj!= null) {
                    ArrayList<Ded_by_prod>taxsection_heading=new ArrayList<Ded_by_prod>();

                    for (int k = 0; k < length; k++) {
                        if(str_obj.equals(taxCashFlowModel.getData().getDed_by_prod().get(k).getTax_section())){
                            taxsection_heading.add(taxCashFlowModel.getData().getDed_by_prod().get(k));
                        }
                    }
                    mfilterarray.add(taxsection_heading);
                }
            }
            showWebViewTaxation(mfilterarray);
        }
    }

    private void showWebViewTaxation(ArrayList<ArrayList<Ded_by_prod>> mfilterarray) {

        mamount_pay.setText(UtileKit.fromHtml("Based on the input provided, "+"₹ "+"<b>"+"<u>"+UtileKit.currToCharConversion(total_tax_payable)+"</u>"+"</b>"
                +"is the amount pay."));
        maverage_taxs.setText(UtileKit.fromHtml("Average Tax Rate : "+"<b>"+"<u>"+avg_tax_rate+"</u>"+"</b>"+"%"));
        mmar_tax.setText(UtileKit.fromHtml("Marginal Tax Rate : "+"<b>"+"<u>"+marg_tax_rate+"</u>"+"</b>"+"%"));

        loadTaxationInsuranceData(mfilterarray);
    }
    private void loadTaxationInsuranceData(ArrayList<ArrayList<Ded_by_prod>> mfilterarray) {

        List<RowHeader> rowHeaders = getRowHeaderListTaxation(mfilterarray);
        List<List<Cell>> cellList = getCellListForSortingTestTaxation(mfilterarray);
        List<ColumnHeader> columnHeaders = getColumnHeaderListCash(mColoumNameListTaxation);
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
    private List<RowHeader> getRowHeaderListTaxation(ArrayList<ArrayList<Ded_by_prod>> mfilterarray) {
        List<RowHeader> list = new ArrayList<>();
        int k=0;
        int n=mfilterarray.size();
        for (int i = 0; i <n ; i++) {

            int inner_length=mfilterarray.get(i).size();

            for (int j = 0; j <inner_length ; j++) {
                RowHeader header = new RowHeader("row " + k, "" + (k ++));
                list.add(header);
            }
        }
        return list;
    }

    private List<List<Cell>> getCellListForSortingTestTaxation(ArrayList<ArrayList<Ded_by_prod>> mfilterarray) {

        List<List<Cell>> list = new ArrayList<>();

        int length = mfilterarray.size();

        for (int i=0;i<length;i++) {
            int inner_length=mfilterarray.get(i).size();

            String  section = "",str_entitle = "",str_availed="",
                    str_pending = "";
            availed= BigInteger.ZERO;
            entitle=BigInteger.ZERO;
            total_sub_all=BigInteger.ZERO;
            for (int j = 0; j < inner_length; j++) {
                List<Cell> cellList = new ArrayList<>();

                if(i==0) {
                    section = mfilterarray.get(i).get(j).getTax_section();
                }
                //entitle
                if (mfilterarray.get(i).get(j).getEntitled() != null) {
                    entitle=new BigInteger(mfilterarray.get(i).get(j).getEntitled());
                    str_entitle="₹ "+String.valueOf(UtileKit.formatedNumbers(entitle));
                }
                //availed
                if (mfilterarray.get(i).get(j).getContr_val() != null) {
                    availed = availed.add(new BigInteger(mfilterarray.get(i).get(j).getContr_val()));
                    str_availed="₹ "+String.valueOf(UtileKit.formatedNumbers(availed));
                }
                //pending
                total_sub_all=entitle.subtract(availed);
                str_pending="₹ "+String.valueOf(UtileKit.formatedNumbers(total_sub_all));

                cellList.add(new Cell(i+"00", section));
                cellList.add(new Cell(i+"01", str_entitle));
                cellList.add(new Cell(i+"02", str_availed));
                cellList.add(new Cell(i+"03", str_pending));
                list.add(cellList);
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
