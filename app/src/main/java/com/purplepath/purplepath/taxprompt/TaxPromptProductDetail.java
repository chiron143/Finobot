package com.purplepath.purplepath.taxprompt;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Murugesan on 1/22/18.
 */

public class TaxPromptProductDetail extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;

    Context mContext;

    TaxPromptNewModel taxPromptNewModel;

    HashSet<String> taxproduct_hashset = new HashSet<String>();

    private BigInteger availed=BigInteger.ZERO;

    private BigInteger total_sub_all=BigInteger.ZERO;

    BigInteger entitle=BigInteger.ZERO;

    private String bullet="\u25BA";

    private String bullets="&#8226";

    WebView webView_Taxproduct;

    private String rs="&#x20B9";

    private TaxPromptNewModel taxAnalysistModel;

    private TextView empty_text;

    private ScrollView scrollview;

    // ArrayList< ArrayList<Ded_by_prods>> mfilterarray = new ArrayList<>();
    HashMap<String ,ArrayList<Ded_by_prods>> mfilterarrayAvailed = new HashMap<>();

    ArrayList<String> arrayListavail = new ArrayList<String>();

    BigInteger entitle_greater=BigInteger.ZERO;



    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
    }


    public static TaxPromptProductDetail newInstance(TaxPromptNewModel taxPromptNewModel) {
        Bundle args = new Bundle();
        args.putSerializable("taxPromptNewModel",taxPromptNewModel);
        TaxPromptProductDetail fragment = new TaxPromptProductDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        View view=inflater.inflate(R.layout.fragment_product_wise, container, false);
        backPressedListener.setActionBarTitle("Tax Plan Details");

        webView_Taxproduct= view.findViewById(R.id.webView_Taxproduct);

        scrollview = view.findViewById(R.id.scrollview);
        empty_text= view.findViewById(R.id.empty_text);

        if(null!=getArguments())
        {
            if(getArguments().containsKey("taxPromptNewModel")){

                taxPromptNewModel = (TaxPromptNewModel) getArguments().getSerializable("taxPromptNewModel");
                groupingSection(taxPromptNewModel);

            }else {
                scrollview.setVisibility(View.GONE);
                empty_text.setVisibility(View.VISIBLE);
            }
        }else {
            callTaxProductService();
        }
        return view;
    }


    private void groupingSection(TaxPromptNewModel taxPromptNewModel) {

        //Disablity
        String diability_flag=taxPromptNewModel.getData().getDisability_flag();

        if(taxPromptNewModel.getData().getDed_by_prod()!=null){
            int length=taxPromptNewModel.getData().getDed_by_prod().size();



            for(int i=0;i<length;i++){
                taxproduct_hashset.add(taxPromptNewModel.getData().getDed_by_prod().get(i).getTax_section());
            }

         //   ArrayList<String> arrayList = new ArrayList<String>(taxproduct_hashset);

            arrayListavail= new ArrayList<String>(taxproduct_hashset);

            Collections.sort(arrayListavail);

            // ArrayList< ArrayList<Ded_by_prods>> mfilterarray = new ArrayList<>();

            for(int j=0;j<arrayListavail.size();j++){
                String str_obj=arrayListavail.get(j);
                if (str_obj!= null) {
                    ArrayList<Ded_by_prods>taxsection_heading=new ArrayList<Ded_by_prods>();

                    for (int k = 0; k < length; k++) {
                        if(str_obj.equals(taxPromptNewModel.getData().getDed_by_prod().get(k).getTax_section())){

                            String disability_inner_array=taxPromptNewModel.getData().getDed_by_prod().get(k).getDisability_flag();

                            try {
                                if ((disability_inner_array.equalsIgnoreCase("A") && diability_flag.equalsIgnoreCase("Y"))) {
                                    taxsection_heading.add(taxPromptNewModel.getData().getDed_by_prod().get(k));
                                } else if ((disability_inner_array.equalsIgnoreCase("Y") && diability_flag.equalsIgnoreCase("Y"))) {
                                    taxsection_heading.add(taxPromptNewModel.getData().getDed_by_prod().get(k));
                                } else if ((disability_inner_array.equalsIgnoreCase("A") && diability_flag.equalsIgnoreCase("N"))) {
                                    taxsection_heading.add(taxPromptNewModel.getData().getDed_by_prod().get(k));
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                  //  mfilterarray.add(taxsection_heading);
                    mfilterarrayAvailed.put(str_obj,taxsection_heading);
                }
            }
            filterSection(mfilterarrayAvailed);

        }
    }



    private void filterSection(HashMap<String, ArrayList<Ded_by_prods>> mfilterarray) {
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

    private String addTableRows(HashMap<String, ArrayList<Ded_by_prods>> mfilterarray) {
        String rowsadd = "";

        for (String key:arrayListavail) {

            int innerlength=mfilterarray.get(key).size();
            //for (ArrayList<Ded_by_prods> ded_by_prod:mfilterarray) {
           //int length=ded_by_prod.size();
            String  section = "",instrument="",str_entitle = "",str_availed="",
                    str_pending = "";
            String str_availed_greater="";
            availed=BigInteger.ZERO;
            entitle=BigInteger.ZERO;
            total_sub_all=BigInteger.ZERO;

            entitle_greater=BigInteger.ZERO;
            int checkLess;
            if (innerlength != 0) {

                for (int j = 0; j < innerlength; j++) {

                    try {

                        if (mfilterarray.get(key).get(j).getTax_section() != null) {
                            if (j == 0) {
                                section = mfilterarray.get(key).get(j).getTax_section();
                            }
                        }
                        //instrument
                        if (mfilterarray.get(key).get(j).getProd_name() != null) {
                            instrument = instrument + bullets + "  " + mfilterarray.get(key).get(j).getProd_name() + " <br/> ";
                        }

                        //entitle
                        if (mfilterarray.get(key).get(j).getEntitled() != null) {
                            entitle = new BigInteger(mfilterarray.get(key).get(j).getEntitled());
                            str_entitle = String.valueOf(UtileKit.formatedNumbers(entitle));
                        } else {
                            str_entitle = "0";
                        }
                        //availed
                        if (mfilterarray.get(key).get(j).getAllowed_value() != null &&
                                mfilterarray.get(key).get(j).getEntitled() != null) {
                            availed = availed.add(new BigInteger(mfilterarray.get(key).get(j).getAllowed_value()));

                            str_availed = String.valueOf(UtileKit.formatedNumbers(availed));

                            entitle_greater = new BigInteger(mfilterarray.get(key).get(j).getEntitled());

                            str_availed_greater = String.valueOf(UtileKit.formatedNumbers(entitle_greater));

                        } else {
                            str_availed = "0";
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                checkLess = availed.compareTo(entitle_greater);

                if (checkLess == 1) {

                    //pending
                    total_sub_all = entitle.subtract(entitle_greater);
                    str_pending = String.valueOf(UtileKit.formatedNumbers(total_sub_all));

                    rowsadd = rowsadd + "  <tr align = \"left\">\n" +
                            "    <td>" + section + "</td>\n" +
                            "    <td>" + "    " + instrument + "   " + "</td>\n" +
                            "    <td>" + rs + " " + str_entitle + "</td>\n" +
                            "    <td>" + rs + " " + str_availed_greater + "</td>\n" +
                            "    <td>" + rs + " " + str_pending + "</td>\n" +
                            "  </tr>\n";

                } else if (checkLess == 0) {
                    //pending
                    total_sub_all = entitle.subtract(entitle_greater);
                    str_pending = String.valueOf(UtileKit.formatedNumbers(total_sub_all));


                    rowsadd = rowsadd + "  <tr align = \"left\">\n" +
                            "    <td>" + section + "</td>\n" +
                            "    <td>" + "    " + instrument + "   " + "</td>\n" +
                            "    <td>" + rs + " " + str_entitle + "</td>\n" +
                            "    <td>" + rs + " " + str_availed_greater + "</td>\n" +
                            "    <td>" + rs + " " + str_pending + "</td>\n" +
                            "  </tr>\n";
                } else {
                    //pending
                    total_sub_all = entitle.subtract(availed);
                    str_pending = String.valueOf(UtileKit.formatedNumbers(total_sub_all));

                    rowsadd = rowsadd + "  <tr align = \"left\">\n" +
                            "    <td>" + section + "</td>\n" +
                            "    <td>" + "    " + instrument + "   " + "</td>\n" +
                            "    <td>" + rs + " " + str_entitle + "</td>\n" +
                            "    <td>" + rs + " " + str_availed + "</td>\n" +
                            "    <td>" + rs + " " + str_pending + "</td>\n" +
                            "  </tr>\n";
                }
            }
        }
        return rowsadd;
    }

    private void callTaxProductService() {
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
                UtileKit.dismisssSpinnerDialog();
                taxAnalysistModel= response.body();
                try {
                    if (taxAnalysistModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        groupingSection(taxPromptNewModel);

                    }else {
                        empty_text.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
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



    @Override
    public void onClick(View v) {

    }




}
