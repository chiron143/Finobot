//package com.purplepath.purplepath.taxproduct;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import com.finobot.finobot.R;
//import com.finobot.finobot.activity.HomePageActivity;
//import com.purplepath.purplepath.AppManagement.Payment.PrimeFragment;
//import com.purplepath.purplepath.apputiles.UtileKit;
//import com.purplepath.purplepath.customview.DefaultCurrencyTextView;
//import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
//import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
//import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
//import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.Ded_by_prod;
//import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.TaxCashFlowModel;
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.HashSet;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
///**
// * Created by pravinr on 1/4/18.
// */
//
//public class TaxProductwiseFragment extends PrimeFragment implements View.OnClickListener {
//
//    OnActivityBackPressedListener backPressedListener;
//    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
//    Context mContext;
//
//    TaxCashFlowModel taxCashFlowModel;
//
//    LinearLayout layout_dynamic_section;
//
//    HashSet<String> taxproduct_hashset = new HashSet<String>();
//
//    private BigInteger availed=BigInteger.ZERO;
//    private BigInteger total_sub_all=BigInteger.ZERO;
//    BigInteger entitle=BigInteger.ZERO;
//
//    @Override
//    public void onAttach(Context context) {
//        backPressedListener= (OnActivityBackPressedListener) context;
//        super.onAttach(context);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        mContext = getContext();
//        View view=inflater.inflate(R.layout.fragment_product_wise, container, false);
//        backPressedListener.setActionBarTitle("Tax Product");
//        mleftRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_left_arrow);
//        mcenterRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_center_home);
//        mRightRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_right_arrow);
//
//      //  layout_dynamic_section=(LinearLayout)view.findViewById(R.id.layout_dynamic_section);
//
//
//        mRightRelativeLayout.setVisibility(View.GONE);
//        mleftRelativeLayout.setOnClickListener(this);
//        mcenterRelativeLayout.setOnClickListener(this);
//
//        callTaxProductService();
//        return view;
//    }
//    private void callTaxProductService() {
//        UtileKit.showSpinnerDialog(mContext,false);
//        WebServiceCalls webServiceObj;
//        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
//        Call<TaxCashFlowModel> call = webServiceObj.callinsurance_tax_Cash_Flow_Service(UtileKit.getPersistedPurplePathPref("user_id"));
//        call.enqueue(new Callback<TaxCashFlowModel>() {
//            @Override
//            public void onResponse(Call<TaxCashFlowModel> call, Response<TaxCashFlowModel> response) {
//                UtileKit.dismisssSpinnerDialog();
//                taxCashFlowModel= response.body();
//                if(taxCashFlowModel.getStatus_code().equals(UtileKit.SUCCESSCODE)){
//                    groupingSection(taxCashFlowModel);
//                }
//
//            }
//            @Override
//            public void onFailure(Call<TaxCashFlowModel> call, Throwable t) {
//                UtileKit.alertRetrofitExceptionDialog( mContext, t);
//                UtileKit.dismisssSpinnerDialog();
//            }
//        });
//    }
//
//    private void groupingSection(TaxCashFlowModel taxCashFlowModel) {
//
//        if(taxCashFlowModel.getData().getDed_by_prod()!=null){
//            int length=taxCashFlowModel.getData().getDed_by_prod().size();
//
//            for(int i=0;i<length;i++){
//                taxproduct_hashset.add(taxCashFlowModel.getData().getDed_by_prod().get(i).getTax_section());
//            }
//
//            ArrayList<String> arrayList = new ArrayList<String>(taxproduct_hashset);
//
//            ArrayList< ArrayList<Ded_by_prod>> mfilterarray = new ArrayList<>();
//
//            for(int j=0;j<arrayList.size();j++){
//                String str_obj=arrayList.get(j);
//                if (str_obj!= null) {
//                    ArrayList<Ded_by_prod>taxsection_heading=new ArrayList<Ded_by_prod>();
//
//                    for (int k = 0; k < length; k++) {
//                        if(str_obj.equals(taxCashFlowModel.getData().getDed_by_prod().get(k).getTax_section())){
//                            taxsection_heading.add(taxCashFlowModel.getData().getDed_by_prod().get(k));
//                        }
//                    }
//                    mfilterarray.add(taxsection_heading);
//                }
//            }
//           filterSection(mfilterarray);
//
//        }
//    }
//
//
//    private void filterSection(ArrayList<ArrayList<Ded_by_prod>> mfilterarray) {
//
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//
//        int length=mfilterarray.size();
//
//        for(int i=0;i<length;i++){
//            final View tax_section_view = inflater.inflate(R.layout.tax_section_single_item, null);
//            TextView tax_section=(TextView)tax_section_view.findViewById(R.id.tax_section);
//            DefaultCurrencyTextView tax_entitle=(DefaultCurrencyTextView)tax_section_view.findViewById(R.id.tax_entitle);
//            DefaultCurrencyTextView tax_availed=(DefaultCurrencyTextView)tax_section_view.findViewById(R.id.tax_availed);
//            DefaultCurrencyTextView tax_pending=(DefaultCurrencyTextView)tax_section_view.findViewById(R.id.tax_pending);
//
//
//            LinearLayout layout_dynamic_product_name=(LinearLayout)tax_section_view.findViewById(R.id.layout_dynamic_product_name);
//
//            int innerlength=mfilterarray.get(i).size();
//
//            availed=BigInteger.ZERO;
//            total_sub_all=BigInteger.ZERO;
//            entitle=BigInteger.ZERO;
//
//            for(int j=0;j<innerlength;j++) {
//
//                if (mfilterarray.get(i).get(j).getTax_section()!= null){
//
//                        //Section
//                                tax_section.setText("Section"+"   "+mfilterarray.get(i).get(j).getTax_section());
//
//                        //entitle
//                            if (mfilterarray.get(i).get(j).getEntitled() != null) {
//                                entitle=new BigInteger(mfilterarray.get(i).get(j).getEntitled());
//                                tax_entitle.setText(String.valueOf(entitle));
//                            }
//                        //availed
//                            if (mfilterarray.get(i).get(j).getContr_val() != null) {
//                                availed = availed.add(new BigInteger(mfilterarray.get(i).get(j).getContr_val()));
//                                tax_availed.setText(String.valueOf(availed));
//                            }
//                        //pending
//                                total_sub_all=entitle.subtract(availed);
//                                tax_pending.setText(String.valueOf(total_sub_all));
//
//
//                    filterSectioninnerArray(layout_dynamic_product_name,mfilterarray.get(i).get(j));
//
//                }
//            }
//           layout_dynamic_section.addView(tax_section_view);
//        }
//    }
//
//    private void filterSectioninnerArray(LinearLayout layout_dynamic_product_name, Ded_by_prod ded_by_prod) {
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        final View tax_section_view = inflater.inflate(R.layout.tax_product_name_single_item, null);
//        TextView tax_product_name=(TextView)tax_section_view.findViewById(R.id.tax_product_name);
//        tax_product_name.setText(ded_by_prod.getProd_name());
//        layout_dynamic_product_name.addView(tax_section_view);
//        }
//
//
//
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()){
//            case R.id.relative_left_arrow:
//                backPressedListener.onActivityBackPressed();
//                break;
//            case R.id.relative_center_home:
//                Intent i=new Intent(getActivity(), HomePageActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//                break;
//
//        }
//
//    }
//}
