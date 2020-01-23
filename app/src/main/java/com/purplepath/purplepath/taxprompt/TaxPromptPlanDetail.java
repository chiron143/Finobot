package com.purplepath.purplepath.taxprompt;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxprompt.gettaxpromptmodel.Ded_by_prods;
import com.purplepath.purplepath.taxprompt.gettaxpromptmodel.TaxPromptNewModel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pravinr on 2/6/18.
 */

public class TaxPromptPlanDetail extends BaseFragment implements View.OnClickListener {

    private TaxPromptNewModel taxAnalysistModel;

    private OnActivityBackPressedListener mCallBackListener;

    private Bundle args;

    private Context mContext;

    HashSet<String> taxproducts_hashset = new HashSet<String>();

    BigInteger entitle=BigInteger.ZERO;

    LinearLayout layout_product_section;

    HashSet<String> taxavailed_hashset = new HashSet<String>();

    BigInteger availed=BigInteger.ZERO;

    LinearLayout layout_availed_section;

    HashMap<String ,ArrayList<Ded_by_prods>> mfilterarrayEntitlement = new HashMap<>();

    HashMap<String ,BigInteger> mEntitlementTotalValue = new HashMap<>();


    HashMap<String ,ArrayList<Ded_by_prods>> mfilterarrayAvailed = new HashMap<>();

    HashMap<String ,BigInteger> mAvailedTotalValue = new HashMap<>();

    BigInteger sub_entitlement_availed=BigInteger.ZERO;

    LinearLayout layout_pending_section;

    BigInteger entitle_greater=BigInteger.ZERO;

    ArrayList<String> arrayListavail = new ArrayList<String>();

    ArrayList<String> arrayListentil = new ArrayList<String>();

    private ScrollView scrollview;

    private TextView empty_text;





    public static TaxPromptPlanDetail newInstance(TaxPromptNewModel taxAnalysistModel) {
        TaxPromptPlanDetail fragment = new TaxPromptPlanDetail();
        Bundle args = new Bundle();
        if (taxAnalysistModel != null) {
            args.putSerializable("taxAnalysistModel", taxAnalysistModel);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        mCallBackListener = (OnActivityBackPressedListener) context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_taxprompt_plan_details, container, false);
        ButterKnife.bind(this, view);
        mCallBackListener.setActionBarTitle("Tax Plan Details");
        setHasOptionsMenu(true);

        layout_product_section= view.findViewById(R.id.layout_product);
        layout_availed_section= view.findViewById(R.id.layout_availed);
        layout_pending_section= view.findViewById(R.id.layout_pending);


        scrollview = view.findViewById(R.id.scrollview);
        empty_text= view.findViewById(R.id.empty_text);

        args = getArguments();
        if (args != null) {
            if (args.containsKey("taxAnalysistModel")) {
                taxAnalysistModel = (TaxPromptNewModel) args.getSerializable("taxAnalysistModel");
                try {
                    if (taxAnalysistModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        groupingEntitlementProducts(taxAnalysistModel);

                        groupingAvailedDedbyProd(taxAnalysistModel);

                        pending();
                    } else {
                       scrollview.setVisibility(View.GONE);
                        empty_text.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else {
            callTaxAnalysisService();
        }
        return view;
    }

    //Entitlement card
    private void groupingEntitlementProducts(TaxPromptNewModel taxAnalysistModel) {

        if(taxAnalysistModel.getData().getDed_by_prod()!=null){
            int length=taxAnalysistModel.getData().getDed_by_prod().size();

            String diability_flag=taxAnalysistModel.getData().getDisability_flag();

            for(int i=0;i<length;i++){
                taxavailed_hashset.add(taxAnalysistModel.getData().getDed_by_prod().get(i).getTax_section());
            }

            arrayListentil = new ArrayList<String>(taxavailed_hashset);

            Collections.sort(arrayListentil);

            for(int j=0;j<arrayListentil.size();j++){
                String str_obj=arrayListentil.get(j);
                if (str_obj!= null) {

                    ArrayList<Ded_by_prods>taxsection_heading=new ArrayList<Ded_by_prods>();

                    //Disablity

                    for (int k = 0; k < length; k++) {
                        if(str_obj.equals(taxAnalysistModel.getData().getDed_by_prod().get(k).getTax_section())){

                            String disability_inner_array=taxAnalysistModel.getData().getDed_by_prod().get(k).
                                    getDisability_flag();

                            try {
                                if ((disability_inner_array.equalsIgnoreCase("A") && diability_flag.equalsIgnoreCase("Y"))) {

                                    taxsection_heading.add(taxAnalysistModel.getData().getDed_by_prod().get(k));

                                } else if ((disability_inner_array.equalsIgnoreCase("Y") && diability_flag.equalsIgnoreCase("Y"))) {

                                    taxsection_heading.add(taxAnalysistModel.getData().getDed_by_prod().get(k));

                                } else if ((disability_inner_array.equalsIgnoreCase("A") && diability_flag.equalsIgnoreCase("N"))) {

                                    taxsection_heading.add(taxAnalysistModel.getData().getDed_by_prod().get(k));

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }



                        }
                    }
                  //  if(!taxsection_heading.isEmpty()) {
                        mfilterarrayEntitlement.put(str_obj, taxsection_heading);
                   // }
                }
            }


            filterSection(mfilterarrayEntitlement);

        }
    }


    private void filterSection(HashMap<String, ArrayList<Ded_by_prods>> mfilterarray) {

        LayoutInflater inflater = LayoutInflater.from(mContext);


        for(String key:arrayListentil){

//            for(String key:mfilterarray.keySet()){
            int innerlength=mfilterarray.get(key).size();

                if(innerlength!=0) {
                    final View tax_section_view = inflater.inflate(R.layout.tax_product_section_single_item, null);

                    TextView txt_product_value = tax_section_view.findViewById(R.id.txt_product_value);
                    TextView product_value = tax_section_view.findViewById(R.id.product_value);

                    LinearLayout color_layout= tax_section_view.findViewById(R.id.color_layout);

                    entitle = BigInteger.ZERO;
                    String section = "";
                    for (int j = 0; j < innerlength; j++) {

                        if (mfilterarray.get(key).get(j).getTax_section() != null) {
                            section = mfilterarray.get(key).get(j).getTax_section();

                            txt_product_value.setText(section);
                        }
                        if (mfilterarray.get(key).get(j).getEntitled() != null) {
                            entitle = new BigInteger(mfilterarray.get(key).get(j).getEntitled());

                        }

                        /*if (j % 2 == 1) {
                            color_layout.setBackgroundColor(ColorTemplate.rgb("#e3d2e7"));
                        }else {
                            color_layout.setBackgroundColor(ColorTemplate.rgb("#f4f4f4"));
                        }*/

                    }
                    product_value.setText("₹ " + String.valueOf(UtileKit.formatedNumbers(entitle)));

                    if(mEntitlementTotalValue!=null) {

                        mEntitlementTotalValue.put(key, entitle);
                    }



                    layout_product_section.addView(tax_section_view);
                }
        }
    }
//Entitlement card


    //Availed card
    private void groupingAvailedDedbyProd(TaxPromptNewModel taxAnalysistModel) {

        if(taxAnalysistModel.getData().getDed_by_prod()!=null){

            int length=taxAnalysistModel.getData().getDed_by_prod().size();

            String diability_flag=taxAnalysistModel.getData().getDisability_flag();

            for(int i=0;i<length;i++){
                taxavailed_hashset.add(taxAnalysistModel.getData().getDed_by_prod().get(i).getTax_section());
            }

            arrayListavail = new ArrayList<String>(taxavailed_hashset);

            Collections.sort(arrayListavail);


            for(int j=0;j<arrayListavail.size();j++){

                String str_obj=arrayListavail.get(j);
                if (str_obj!= null) {
                    ArrayList<Ded_by_prods>taxsection_heading=new ArrayList<Ded_by_prods>();

                    for (int k = 0; k < length; k++) {
                        if(str_obj.equals(taxAnalysistModel.getData().getDed_by_prod().get(k).getTax_section())){

                            String disability_inner_array=taxAnalysistModel.getData().getDed_by_prod().get(k).
                                    getDisability_flag();

                            try {
                                if ((disability_inner_array.equalsIgnoreCase("A") && diability_flag.equalsIgnoreCase("Y"))) {
                                    taxsection_heading.add(taxAnalysistModel.getData().getDed_by_prod().get(k));

                                } else if ((disability_inner_array.equalsIgnoreCase("Y") && diability_flag.equalsIgnoreCase("Y"))) {
                                    taxsection_heading.add(taxAnalysistModel.getData().getDed_by_prod().get(k));

                                } else if ((disability_inner_array.equalsIgnoreCase("A") && diability_flag.equalsIgnoreCase("N"))) {
                                    taxsection_heading.add(taxAnalysistModel.getData().getDed_by_prod().get(k));

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }




                        }
                    }
                    //if(!taxsection_heading.isEmpty()) {
                        mfilterarrayAvailed.put(str_obj, taxsection_heading);
                   // }
                }
            }
            filterAvailedSection(mfilterarrayAvailed);
        }
    }

    private void filterAvailedSection(HashMap<String, ArrayList<Ded_by_prods>> mfilterarray) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        for(String key:arrayListavail){

      //  for(String key:mfilterarray.keySet()){

            int innerlength=mfilterarray.get(key).size();

            if(innerlength!=0) {

                final View tax_section_view = inflater.inflate(R.layout.tax_availed_section_single_item, null);
                TextView txt_availed_value= tax_section_view.findViewById(R.id.txt_availed_value);
                TextView availed_value= tax_section_view.findViewById(R.id.availed_value);

                availed = BigInteger.ZERO;
                entitle_greater = BigInteger.ZERO;
                String section = "";

                int checkLess;
                for (int j = 0; j < innerlength; j++) {

                    if (mfilterarray.get(key).get(j).getTax_section() != null) {
                        section = mfilterarray.get(key).get(j).getTax_section();
                        txt_availed_value.setText(section);
                    }
                    if (mfilterarray.get(key).get(j).getAllowed_value() != null) {
                        availed = availed.add(new BigInteger(mfilterarray.get(key).get(j).getAllowed_value()));
                    }

                    if (mfilterarray.get(key).get(j).getEntitled() != null) {
                        entitle_greater = new BigInteger(mfilterarray.get(key).get(j).getEntitled());
                    }


                }

                //---------if in this array availed value greater means take entitle value
                checkLess = availed.compareTo(entitle_greater);
                if (checkLess == 1) {
                    availed_value.setText("₹ " + String.valueOf(UtileKit.formatedNumbers(entitle_greater)));

                    if(mAvailedTotalValue!=null) {
                        mAvailedTotalValue.put(key, entitle_greater);
                    }

                } else if (checkLess == 0) {
                    availed_value.setText("₹ " + String.valueOf(UtileKit.formatedNumbers(entitle_greater)));
                    if(mAvailedTotalValue!=null) {
                        mAvailedTotalValue.put(key, entitle_greater);
                    }

                } else {
                    availed_value.setText("₹ " + String.valueOf(UtileKit.formatedNumbers(availed)));
                    if(mAvailedTotalValue!=null) {
                        mAvailedTotalValue.put(key, availed);
                    }
                }
                //--------


                layout_availed_section.addView(tax_section_view);
            }
        }
    }
    //Availed card



    //pending
    private void pending(){

        LayoutInflater inflater = LayoutInflater.from(mContext);



        // for(String key:taxavailed_hashset){
        sub_entitlement_availed = BigInteger.ZERO;

        for(String key:arrayListentil){

            if(mAvailedTotalValue.containsKey(key)) {


                final View tax_section_view = inflater.inflate(R.layout.tax_pending_section_single_item, null);

                Log.d("entitle", "entitless" + mEntitlementTotalValue.get(key));
                Log.d("availed", "availedss" + mAvailedTotalValue.get(key));

                sub_entitlement_availed = mEntitlementTotalValue.get(key).subtract(mAvailedTotalValue.get(key));


                TextView txt_pending_value = tax_section_view.findViewById(R.id.txt_pending_value);
                TextView pending_value = tax_section_view.findViewById(R.id.pending_value);

                txt_pending_value.setText(key);

                pending_value.setText("₹ " + String.valueOf(UtileKit.formatedNumbers(sub_entitlement_availed)));

                layout_pending_section.addView(tax_section_view);
            }
        }
    }
//pending



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
                    taxAnalysistModel = response.body();
                    if (taxAnalysistModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        groupingEntitlementProducts(taxAnalysistModel);

                        groupingAvailedDedbyProd(taxAnalysistModel);

                        pending();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<TaxPromptNewModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }


    @Override
    public void onClick(View v) {

    }


}
