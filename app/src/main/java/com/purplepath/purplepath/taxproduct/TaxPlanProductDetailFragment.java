package com.purplepath.purplepath.taxproduct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxprompt.TaxAnaylsisBarChart;
import com.purplepath.purplepath.taxprompt.TaxPromptProductDetail;
import com.purplepath.purplepath.taxprompt.TaxPromptSummary;
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
 * Created by pravinr on 2/14/18.
 */

public class TaxPlanProductDetailFragment extends BaseFragment implements View.OnClickListener {


    Context mContext;

    TaxPromptNewModel taxPromptNewModel;

    HashSet<String> taxproduct_hashset = new HashSet<String>();

    private BigInteger availed = BigInteger.ZERO;

    private BigInteger total_sub_all = BigInteger.ZERO;

    BigInteger entitle = BigInteger.ZERO;
    private String bullet = "\u25AA";
    private String bullets = "&#8226";
    private String rs = "&#x20B9";
    private TextView empty_text;
    private ScrollView scrollview;
    // ArrayList< ArrayList<Ded_by_prods>> mfilterarray = new ArrayList<>();
    HashMap<String, ArrayList<Ded_by_prods>> mfilterarrayAvailed = new HashMap<>();
    ArrayList<String> arrayListavail = new ArrayList<String>();
    BigInteger entitle_greater = BigInteger.ZERO;
    LinearLayout layout_dynamic_section;
    private OnActivityBackPressedListener mCallBackListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static TaxPromptProductDetail newInstance(TaxPromptNewModel taxPromptNewModel) {
        Bundle args = new Bundle();
        args.putSerializable("taxPromptNewModel", taxPromptNewModel);
        TaxPromptProductDetail fragment = new TaxPromptProductDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_product_wises, container, false);
        mCallBackListener.setActionBarTitle("Tax Plan Details");

        layout_dynamic_section = view.findViewById(R.id.layout_dynamic_section);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);

        scrollview = view.findViewById(R.id.scrollview);
        empty_text = view.findViewById(R.id.empty_text);

        if (null != getArguments()) {
            if (getArguments().containsKey("taxPromptNewModel")) {
                taxPromptNewModel = (TaxPromptNewModel) getArguments().getSerializable("taxPromptNewModel");
                groupingSection(taxPromptNewModel);
            } else {
                scrollview.setVisibility(View.GONE);
                empty_text.setVisibility(View.VISIBLE);
            }
        } else {
            callTaxProductService();
        }
        return view;
    }


    private void groupingSection(TaxPromptNewModel taxPromptNewModel) {

        //Disablity
        String diability_flag = taxPromptNewModel.getData().getDisability_flag();

        if (taxPromptNewModel.getData().getDed_by_prod() != null) {
            int length = taxPromptNewModel.getData().getDed_by_prod().size();


            for (int i = 0; i < length; i++) {
                taxproduct_hashset.add(taxPromptNewModel.getData().getDed_by_prod().get(i).getTax_section());
            }

            //   ArrayList<String> arrayList = new ArrayList<String>(taxproduct_hashset);

            arrayListavail = new ArrayList<String>(taxproduct_hashset);

            Collections.sort(arrayListavail);

            // ArrayList< ArrayList<Ded_by_prods>> mfilterarray = new ArrayList<>();

            for (int j = 0; j < arrayListavail.size(); j++) {
                String str_obj = arrayListavail.get(j);
                if (str_obj != null) {
                    ArrayList<Ded_by_prods> taxsection_heading = new ArrayList<Ded_by_prods>();

                    for (int k = 0; k < length; k++) {
                        if (str_obj.equals(taxPromptNewModel.getData().getDed_by_prod().get(k).getTax_section())) {

                            String disability_inner_array = taxPromptNewModel.getData().getDed_by_prod().get(k).getDisability_flag();

                            try {
                                if ((disability_inner_array.equalsIgnoreCase("A") && diability_flag.equalsIgnoreCase("Y"))) {
                                    taxsection_heading.add(taxPromptNewModel.getData().getDed_by_prod().get(k));
                                } else if ((disability_inner_array.equalsIgnoreCase("Y") && diability_flag.equalsIgnoreCase("Y"))) {
                                    taxsection_heading.add(taxPromptNewModel.getData().getDed_by_prod().get(k));
                                } else if ((disability_inner_array.equalsIgnoreCase("A") && diability_flag.equalsIgnoreCase("N"))) {
                                    taxsection_heading.add(taxPromptNewModel.getData().getDed_by_prod().get(k));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    //  mfilterarray.add(taxsection_heading);
                    mfilterarrayAvailed.put(str_obj, taxsection_heading);
                }
            }
            filterSection(mfilterarrayAvailed);

        }
    }


    private void filterSection(HashMap<String, ArrayList<Ded_by_prods>> mfilterarray) {

        addTableRows(mfilterarray);
    }

    private String addTableRows(HashMap<String, ArrayList<Ded_by_prods>> mfilterarray) {
        String rowsadd = "";

        for (String key : arrayListavail) {

            LayoutInflater inflater = LayoutInflater.from(mContext);

            int innerlength = mfilterarray.get(key).size();
            //for (ArrayList<Ded_by_prods> ded_by_prod:mfilterarray) {
            //int length=ded_by_prod.size();
            String section = "", instrument = "", str_entitle = "", str_availed = "",
                    str_pending = "";
            String str_availed_greater = "";
            availed = BigInteger.ZERO;
            entitle = BigInteger.ZERO;
            total_sub_all = BigInteger.ZERO;

            entitle_greater = BigInteger.ZERO;
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
                            instrument = instrument + bullet + " " + mfilterarray.get(key).get(j).getProd_name() + " \n ";
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

//                    rowsadd = rowsadd + "  <tr align = \"left\">\n" +
//                            "    <td>" + section + "</td>\n" +
//                            "    <td>" + "    " + instrument + "   " + "</td>\n" +
//                            "    <td>" + rs + " " + str_entitle + "</td>\n" +
//                            "    <td>" + rs + " " + str_availed_greater + "</td>\n" +
//                            "    <td>" + rs + " " + str_pending + "</td>\n" +
//                            "  </tr>\n";
                    singleItemLayout(section, instrument, str_entitle, str_availed_greater, str_pending);


                } else if (checkLess == 0) {
                    //pending
                    total_sub_all = entitle.subtract(entitle_greater);
                    str_pending = String.valueOf(UtileKit.formatedNumbers(total_sub_all));


//                    rowsadd = rowsadd + "  <tr align = \"left\">\n" +
//                            "    <td>" + section + "</td>\n" +
//                            "    <td>" + "    " + instrument + "   " + "</td>\n" +
//                            "    <td>" + rs + " " + str_entitle + "</td>\n" +
//                            "    <td>" + rs + " " + str_availed_greater + "</td>\n" +
//                            "    <td>" + rs + " " + str_pending + "</td>\n" +
//                            "  </tr>\n";

                    singleItemLayout(section, instrument, str_entitle, str_availed_greater, str_pending);
                } else {
                    //pending
                    total_sub_all = entitle.subtract(availed);
                    str_pending = String.valueOf(UtileKit.formatedNumbers(total_sub_all));

//                    rowsadd = rowsadd + "  <tr align = \"left\">\n" +
//                            "    <td>" + section + "</td>\n" +
//                            "    <td>" + "    " + instrument + "   " + "</td>\n" +
//                            "    <td>" + rs + " " + str_entitle + "</td>\n" +
//                            "    <td>" + rs + " " + str_availed + "</td>\n" +
//                            "    <td>" + rs + " " + str_pending + "</td>\n" +
//                            "  </tr>\n";

                    singleItemLayout(section, instrument, str_entitle, str_availed, str_pending);
                }
            }
        }
        return rowsadd;
    }


    void singleItemLayout(String section, String instrument,
                          String str_entitle, String str_availed, String str_pending) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View clasification_view = inflater.inflate(R.layout.fragment_taxproductwise_singleitem, null);

        TextView tax_section = clasification_view.findViewById(R.id.section);
        TextView instrument_value = clasification_view.findViewById(R.id.instrument_value);
        TextView entitled_value = clasification_view.findViewById(R.id.entitled_value);
        TextView availed_value = clasification_view.findViewById(R.id.availed_value);
        TextView pending_value = clasification_view.findViewById(R.id.pending_value);
        tax_section.setText(section);
        instrument_value.setText(instrument);
        entitled_value.setText("₹ " + str_entitle);
        availed_value.setText("Availed : " + "₹ " + str_availed);
        pending_value.setText("Pending : " + "₹ " + str_pending);
        layout_dynamic_section.addView(clasification_view);
    }


    private void callTaxProductService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String finYr = String.valueOf(year-1);
        Call<TaxPromptNewModel> call = webServiceObj.callTaxPromptService_new(UtileKit.getPersistedPurplePathPref("user_id"), "FY"+finYr);
        call.enqueue(new Callback<TaxPromptNewModel>() {
            @Override
            public void onResponse(Call<TaxPromptNewModel> call, Response<TaxPromptNewModel> response) {
                UtileKit.dismisssSpinnerDialog();
                taxPromptNewModel = response.body();
                try {
                    if (taxPromptNewModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        groupingSection(taxPromptNewModel);

                    } else {
                        empty_text.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaxPromptNewModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_summary_and_chart, menu);
        MenuItem item = menu.findItem(R.id.menu_summary);
        MenuItem items = menu.findItem(R.id.menu_chart);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_chart:
                try {

                    //addFragmenttoStack(new TaxAnaylsisBarChart());
                    addFragmenttoStack(new TaxAnaylsisBarChart());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_summary:
                try {
                    addFragmenttoStack(new TaxPromptSummary());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_left_arrow: {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home: {
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
            break;


        }
    }


}
