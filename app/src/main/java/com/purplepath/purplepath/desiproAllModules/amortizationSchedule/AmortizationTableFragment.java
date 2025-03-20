package com.purplepath.purplepath.desiproAllModules.amortizationSchedule;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.amortizationSchedule.models.AmortizationScheduleModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pratheep.S on 31-05-2017.
 */
public class AmortizationTableFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.webViewDeciPro)
    WebView webViewDeciPro;

    private Bundle args;
    private AmortizationScheduleModel amort_model;

    private Context mContext;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private OnActivityBackPressedListener mCallBackListener;

    public static AmortizationTableFragment newInstance(AmortizationScheduleModel amort_model) {

        Bundle args = new Bundle();
        args.putSerializable("amort_model", amort_model);
        AmortizationTableFragment fragment = new AmortizationTableFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        try {
            setHasOptionsMenu(true);
            mContext = getContext();
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amortization_table, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        mCallBackListener.setActionBarTitle("DeciPro - Amortization Schedule");

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        mContext = getContext();
        args = getArguments();
        if (args != null) {
            if (args.containsKey("amort_model")) {
                amort_model = (AmortizationScheduleModel) args.getSerializable("amort_model");
            }

        }
        try {
            if (amort_model.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                if (amort_model.getData().getAmtz_sch().size() > 0) {
                    showWebView(amort_model);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    private void showWebView(AmortizationScheduleModel amort_model) {
        String htmlContent = "<!DOCTYPE html>\n" +

                "<html>\n" +
                "<head> <meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" >" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<table border=\"1\" width=\"device-width\" height = \"150px\" cellpadding=\"10px\" cellspacing=\"0\" style=\"border-collapse:collapse;\" >\n" +
                "  <tr id=\"header-row\" align = \"center\">\n" +
                "    <th width = \"150px\"> Month</th>\n" +
                "    <th width = \"150px\">Outstanding Balance start</th> \n" +
                "    <th width = \"150px\">EMI</th>\n" +
                "    <th width = \"150px\">Interest </th>\n" +
                "    <th width = \"150px\"> Principal</th>\n" +
                "    <th width = \"150px\">Service Tax </th>\n" +
                "    <th width = \"150px\">Outstanding Balance end</th> \n" +
                "    <th width = \"150px\">Total Payment</th> \n" +
                "    <th width = \"150px\">Cummulative Payment </th>\n" +
                "    <th width = \"150px\">Cummulative Principal </th>\n" +
                "    <th width = \"150px\">Cummulative Interest </th>\n" +
                "    <th width = \"150px\">Cummulative Tax </th>\n" +
                "  </tr>\n" +
                addTableRows(amort_model)
                +
                "</table>\n" +
                "</body>\n" +
                "</html>\n";

        //webViewDeciPro.setBackgroundColor(Color.parseColor("#e5e5e5"));
        webViewDeciPro.setBackgroundColor(Color.parseColor("#fafafa"));
        webViewDeciPro.loadData(htmlContent, "text/html", "UTF-8");
        addTableRows(amort_model);

    }

    private String addTableRows(AmortizationScheduleModel amort_model) {
        String row = "";
        int rowSize = amort_model.getData().getAmtz_sch().size();
        for (int i = 0; i < rowSize; i++) {
            row = row + "  <tr align = \"center\">\n" +
                    "    <td>" + amort_model.getData().getAmtz_sch().get(i).getTenure() + "</td>\n" +
                    "    <td>" + UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getOut_bal_start())) + "</td>\n" +
                    "    <td>" + UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getEmi())) + "</td>\n" +
                    "    <td>" + UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getLoan_interest())) + "</td>\n" +
                    "    <td>" + UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getLoan_principal())) + "</td>\n" +
                    "    <td>" + UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getService_tax())) + "</td>\n" +
                    "    <td>" + UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getOut_bal_end())) + "</td>\n" +
                    "    <td>" + UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getTot_payment())) + "</td>\n" +
                    "    <td>" + UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getCum_payment())) + "</td>\n" +
                    "    <td>" + UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getCum_principal())) + "</td>\n" +
                    "    <td>" + UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getCum_interest())) + "</td>\n" +
                    "    <td>" + UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getCum_tax())) + "</td>\n" +
                    "  </tr>\n";
        }


        return row;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.networth_summary_menu, menu);
        menu.findItem(R.id.menu_details).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_graph:
                AmortizationGraph fragment = AmortizationGraph.newInstance(amort_model);
                addFragment(fragment);
                break;

            case android.R.id.home:
                // ((OnActivityBackPressedListener)mContext).onActivityBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    private void addFragment(AmortizationGraph fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_left_arrow:
                mCallBackListener.onActivityBackPressed();
                break;

            case R.id.relative_center_home:
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                startActivity(i);
                break;
        }
    }
}
