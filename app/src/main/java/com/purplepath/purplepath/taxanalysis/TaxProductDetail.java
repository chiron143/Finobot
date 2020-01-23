package com.purplepath.purplepath.taxanalysis;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.DefaultCurrencyTextView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.Ded_by_prod;
import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.TaxCashFlowModel;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finobot.finobot.R.id.errorTextview;

/**
 * Created by Murugesan on 1/5/18.
 */

public class TaxProductDetail extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;

    Context mContext;

    TaxCashFlowModel taxCashFlowModel;

    HashSet<String> taxproduct_hashset = new HashSet<String>();

    private BigInteger availed=BigInteger.ZERO;

    private BigInteger total_sub_all=BigInteger.ZERO;

    BigInteger entitle=BigInteger.ZERO;

    private String bullet="\u25BA";

    private String bullets="&#8226";

    WebView webView_Taxproduct;

    private String rs="&#x20B9";

    private TaxCashFlowModel taxAnalysistModel;

    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
    }


    public static TaxProductDetail newInstance(TaxCashFlowModel taxCashFlowModel) {
        Bundle args = new Bundle();
        args.putSerializable("taxCashFlowModel",taxCashFlowModel);
        TaxProductDetail fragment = new TaxProductDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        View view=inflater.inflate(R.layout.fragment_product_wise, container, false);
        backPressedListener.setActionBarTitle("Tax Product");

        webView_Taxproduct= view.findViewById(R.id.webView_Taxproduct);

        if(null!=getArguments())
        {
            if(getArguments().containsKey("taxCashFlowModel"))
                taxCashFlowModel = (TaxCashFlowModel) getArguments().getSerializable("taxCashFlowModel");
            groupingSection(taxCashFlowModel);
        }else {
            callTaxProductService();
        }
        return view;
    }


    private void groupingSection(TaxCashFlowModel taxCashFlowModel) {

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
            filterSection(mfilterarray);

        }
    }



    private void filterSection(ArrayList<ArrayList<Ded_by_prod>> mfilterarray) {
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head> <meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" >" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<table border=\"1\" width=\"200%\" height = \"150px\" cellpadding=\"5px\" cellspacing=\"0\" " +
                "style=\"border-collapse:collapse;\" >\n" +
                "  <tr id=\"header-row\" align = \"center\">\n" +
                "    <th width = \"150px\">Section</th>\n" +
                "    <th width = \"150px\">Instrument</th>\n" +
                "    <th width = \"150px\">Entitled</th>\n" +
                "    <th width = \"150px\">Availed</th>\n" +
                "    <th width = \"150px\">Pending</th>\n" +
                "  </tr>\n" +
                addTableRows(mfilterarray)
                +
                "</table>\n" +
                "</body>\n" +
                "</html>\n";
        webView_Taxproduct.loadData(htmlContent, "text/html", "UTF-8");
    }

    private String addTableRows(ArrayList<ArrayList<Ded_by_prod>> mfilterarray) {
        String rowsadd = "";
        for (ArrayList<Ded_by_prod> ded_by_prod:mfilterarray) {
            int length=ded_by_prod.size();
            String  section = "",instrument="",str_entitle = "",str_availed="",
                    str_pending = "";
            availed=BigInteger.ZERO;
            entitle=BigInteger.ZERO;
            total_sub_all=BigInteger.ZERO;
            for (int i = 0; i < length; i++) {

                    if(ded_by_prod.get(i).getTax_section()!=null) {
                        if(i==0) {
                        section = ded_by_prod.get(i).getTax_section();
                    }
                }
                //instrument
               if(ded_by_prod.get(i).getProd_name()!=null){
                   instrument=instrument+bullets+"  "+ded_by_prod.get(i).getProd_name()+" <br/> ";
               }

                //entitle
                if (ded_by_prod.get(i).getEntitled() != null) {
                    entitle=new BigInteger(ded_by_prod.get(i).getEntitled());
                    str_entitle=String.valueOf(entitle);
                }else {
                    str_entitle="0";
                }
                //availed
                if (ded_by_prod.get(i).getContr_val() != null&&!ded_by_prod.get(i).getContr_val().isEmpty()) {
                    availed = availed.add(new BigInteger(ded_by_prod.get(i).getContr_val()));
                    str_availed=String.valueOf(availed);
                }else {
                    str_availed="0";
                }
                //pending
                total_sub_all=entitle.subtract(availed);
                str_pending=String.valueOf(total_sub_all);
            }
            rowsadd = rowsadd + "  <tr align = \"left\">\n" +
                    "    <td>" + section + "</td>\n" +
                    "    <td>" +"    "+ instrument + "   "+"</td>\n" +
                    "    <td>" + rs + " " + UtileKit.longvalueabsolute(Float.parseFloat(str_entitle))+ "</td>\n" +
                    "    <td>" + rs + " " + UtileKit.longvalueabsolute(Float.parseFloat(str_availed)) + "</td>\n" +
                    "    <td>" + rs + " " + UtileKit.longvalueabsolute(Float.parseFloat(str_pending)) + "</td>\n" +
                    "  </tr>\n";

        }
        return rowsadd;
    }

    private void callTaxProductService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TaxCashFlowModel> call = webServiceObj.callinsurance_tax_Cash_Flow_Service(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<TaxCashFlowModel>() {
            @Override
            public void onResponse(Call<TaxCashFlowModel> call, Response<TaxCashFlowModel> response) {
                UtileKit.dismisssSpinnerDialog();
                taxAnalysistModel= response.body();
                try {
                    if (taxAnalysistModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                    }
                }catch (Exception e){
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



    @Override
    public void onClick(View v) {

    }




}
