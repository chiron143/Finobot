package com.purplepath.purplepath.taxprompt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxprompt.gettaxpromptmodel.TaxPromptNewModel;

import java.math.BigInteger;
import java.util.Calendar;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by pravinr on 2/8/18.
 */

public class TaxClasificationDetail extends BaseFragment implements View.OnClickListener {

    private TaxPromptNewModel taxPromptNewModel;

    private OnActivityBackPressedListener mCallBackListener;

    RelativeLayout relative_left_arrow,relative_center_home;

    private Context mContext;

    private  LinearLayout layout_dynamic_section;

    private String rs="₹ ";

    private String percentage="% ";

    private TextView empty_text;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBackListener=(OnActivityBackPressedListener)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view =inflater.inflate(R.layout.fragment_tax_clasification1, container, false);
        mContext=getContext();
        ButterKnife.bind(this,view);

        mCallBackListener.setActionBarTitle("Tax Analysis");
        relative_center_home= view.findViewById(R.id.relative_center_home);
        relative_left_arrow= view.findViewById(R.id.relative_left_arrow);

        relative_center_home.setOnClickListener(this);
        relative_left_arrow.setOnClickListener(this);

        layout_dynamic_section= view.findViewById(R.id.layout_dynamic_section);

        empty_text= view.findViewById(R.id.empty_text);


        callTaxAnalysisService();


        return view;
    }


    private void callTaxAnalysisService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String finYr = String.valueOf(year-1);
        Call<TaxPromptNewModel> call = webServiceObj.callTaxPromptService_new(UtileKit.getPersistedPurplePathPref("user_id"),"FY"+finYr);
        call.enqueue(new Callback<TaxPromptNewModel>() {
            @Override
            public void onResponse(Call<TaxPromptNewModel> call, Response<TaxPromptNewModel> response) {
                try {
                    UtileKit.dismisssSpinnerDialog();
                    taxPromptNewModel = response.body();

                    if (taxPromptNewModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        cardViewTable(taxPromptNewModel);

                    }else {
                        empty_text.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaxPromptNewModel> call, Throwable t) {

                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void cardViewTable(TaxPromptNewModel taxPromptNewModel) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        int length=taxPromptNewModel.getData().getTax_calc().getTax_classification().size();

        for (int i = 0; i < length; i++) {

            final View clasification_view = inflater.inflate(R.layout.tax_clasification_single_item, null);

            TextView benefit_value= clasification_view.findViewById(R.id.benefit_value);
            TextView income_value= clasification_view.findViewById(R.id.income_value);
            TextView tax_payable_value= clasification_view.findViewById(R.id.tax_payable_value);
            TextView avg_tax_value= clasification_view.findViewById(R.id.avg_tax_value);
            TextView mar_tax_value= clasification_view.findViewById(R.id.mar_tax_value);

            TextView title= clasification_view.findViewById(R.id.title);


            try{

                if(taxPromptNewModel.getData().getTax_calc().getTax_classification().get(i).getLabel()!=null){
                    title.setText(taxPromptNewModel.getData().getTax_calc().getTax_classification().get(i).getLabel());
                }
                if(taxPromptNewModel.getData().getTax_calc().getTax_classification().get(i).getBenefit()!=null){
                    benefit_value.setText(rs.concat(UtileKit.formatedNumbers(new BigInteger(taxPromptNewModel.getData().getTax_calc().
                            getTax_classification().get(i).getBenefit()))));
                }
                if(taxPromptNewModel.getData().getTax_calc().getTax_classification().get(i).getIncome()!=null){
                    income_value.setText(rs.concat(UtileKit.formatedNumbers(new BigInteger(taxPromptNewModel.getData().getTax_calc().
                            getTax_classification().get(i).getIncome()))));
                }
                if(taxPromptNewModel.getData().getTax_calc().getTax_classification().get(i).getTotal_tax_payable()!=null){
                    tax_payable_value.setText(rs.concat(UtileKit.formatedNumbersFloat(Float.valueOf((taxPromptNewModel.getData().
                            getTax_calc().getTax_classification().get(i).getTotal_tax_payable())))));
                }
                if(taxPromptNewModel.getData().getTax_calc().getTax_classification().get(i).getAvg_tax_rate()!=null){
                    avg_tax_value.setText(taxPromptNewModel.getData().getTax_calc().getTax_classification().get(i).
                            getAvg_tax_rate().concat(percentage));
                }
                if(taxPromptNewModel.getData().getTax_calc().getTax_classification().get(i).getMar_tax_rate()!=null){
                    mar_tax_value.setText(taxPromptNewModel.getData().getTax_calc().getTax_classification().get(i).
                            getMar_tax_rate().concat(percentage));
                }

            }catch (Exception e){
                e.printStackTrace();
            }


            layout_dynamic_section.addView(clasification_view);
        }

        }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_summary_and_chart,menu);
        MenuItem item=menu.findItem(R.id.menu_summary);
        MenuItem items=menu.findItem(R.id.menu_chart);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_chart:
                try{
                    addFragmenttoStack(new TaxPromptFunnel_Chart());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_summary:
                try{
                    addFragmenttoStack(new TaxPromptSummary());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relative_left_arrow:
                mCallBackListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

        }
    }

}
