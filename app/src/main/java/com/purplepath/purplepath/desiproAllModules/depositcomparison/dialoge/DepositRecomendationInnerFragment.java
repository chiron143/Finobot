package com.purplepath.purplepath.desiproAllModules.depositcomparison.dialoge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.depositcomparison.model.Dep1;
import com.purplepath.purplepath.desiproAllModules.depositcomparison.model.Dep2;
import com.purplepath.purplepath.desiproAllModules.depositcomparison.model.Dep3;
import com.purplepath.purplepath.desiproAllModules.depositcomparison.model.DepositCompModel;
import com.purplepath.purplepath.fragments.BaseFragment;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dinesh on 28/06/17.
 */

public class DepositRecomendationInnerFragment  extends BaseFragment {

    @Bind(R.id.total_investment_return_edt)
    EditText total_investment_return_edt;

    @Bind(R.id.total_investment_edt)
    EditText total_investment_edt;

    @Bind(R.id.total_return_edt)
    EditText total_return_edt;

    @Bind(R.id.statedInterestRate_edt)
    EditText statedInterestRate_edt;

    @Bind(R.id.effectiveInterestRate_edt)
    EditText effectiveInterestRate_edt;

    @Bind(R.id.internalRateOfReturn_edt)
    EditText internalRateOfReturn_edt;

    @Bind(R.id.classification_txt)
    TextView classification_txt;

    @Bind(R.id.star1)
    ImageView star1;

    @Bind(R.id.star2)
    ImageView star2;

    @Bind(R.id.star3)
    ImageView star3;

    private DepositCompModel depositComparisonModel;
    private int fragmentID;
    private HashMap<Integer,String> classificationMap=new HashMap<Integer,String>(){{
        put(0,"Best Preffered Deal");
        put(1,"Next Preffered Deal");
        put(2,"Least Preffered Deal");
        put(3,"");
    }};

    public static DepositRecomendationInnerFragment newInstance(DepositCompModel depositCompModel, int fragmentID) {

        Bundle args = new Bundle();
        args.putSerializable("depositCompModel",depositCompModel);
        args.putInt("fragmentID",fragmentID);
        DepositRecomendationInnerFragment fragment = new DepositRecomendationInnerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_deposit_recomendation_inner, container, false);
        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            Bundle args = getArguments();
            if (args != null) {
                if (args.containsKey("depositCompModel")) {
                    depositComparisonModel = (DepositCompModel) args.getSerializable("depositCompModel");
                    if (depositComparisonModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                        if (args.containsKey("fragmentID")) {
                            fragmentID = args.getInt("fragmentID");

                        int size= depositComparisonModel.getData().getBest_dep_array().size();
                            int rank = 0;
                            switch (fragmentID) {
                                case 1:
                                    Dep1 dep1 = depositComparisonModel.getData().getDep1();
                                    for (int i = 0; i < size; i++) {
                                        if (depositComparisonModel.getData().getBest_dep_array().get(i).equals("dep1")) {
                                            rank = i;
                                        }
                                    }
                                    setValues(String.valueOf(Math.round(Float.parseFloat(dep1.getTot_inst_ret())))
                                            , String.valueOf(Math.round(Float.parseFloat(dep1.getTot_inst()))),
                                            String.valueOf(Math.round(Float.parseFloat(dep1.getTot_ret()))), dep1.getStat_int_rate(),
                                            dep1.getNom_int_rate(), "122", rank);


                                    break;
                                case 2:
                                    Dep2 dep2 = depositComparisonModel.getData().getDep2();
                                    for (int i = 0; i < size; i++) {
                                        if (depositComparisonModel.getData().getBest_dep_array().get(i).equals("dep2")) {
                                            rank = i;
                                        }
                                    }
                                    setValues(String.valueOf(Math.round(Float.parseFloat(dep2.getTot_inst_ret()))),
                                            String.valueOf(Math.round(Float.parseFloat(dep2.getTot_inst())))
                                            , String.valueOf(Math.round(Float.parseFloat(dep2.getTot_ret()))), dep2.getStat_int_rate(),
                                            dep2.getNom_int_rate(), "122", rank);

                                    break;
                                case 3:
                                    Dep3 dep3 = depositComparisonModel.getData().getDep3();
                                    for (int i = 0; i < size; i++) {
                                        if (depositComparisonModel.getData().getBest_dep_array().get(i).equals("dep3")) {
                                            rank = i;
                                        }
                                    }
                                    setValues(String.valueOf(Math.round(Float.parseFloat(dep3.getTot_inst_ret())))
                                            , String.valueOf(Math.round(Float.parseFloat(dep3.getTot_inst())))
                                            , String.valueOf(Math.round(Float.parseFloat(dep3.getTot_ret()))), dep3.getStat_int_rate(),
                                            dep3.getNom_int_rate(), "122", rank);

                                    break;
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "Network Error Please try again later..", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }catch (Exception e){e.printStackTrace();}

    }

    private void setValues(String total_pay, String total_principal_pay, String tot_interest_pay, String stated_rate,
                           String effective_rate, String irr_rate, int rank) {
        total_investment_return_edt.setText(total_pay);
        total_investment_edt.setText(total_principal_pay);
        total_return_edt.setText(tot_interest_pay);
        statedInterestRate_edt.setText(stated_rate);
        effectiveInterestRate_edt.setText(effective_rate);
        internalRateOfReturn_edt.setText(irr_rate);
        classification_txt.setText(classificationMap.get(rank));
        switch (rank){
            case 0:
                star1.setVisibility(View.VISIBLE);
                star2.setVisibility(View.VISIBLE);
                star3.setVisibility(View.VISIBLE);
                break;
            case 1:
                star1.setVisibility(View.VISIBLE);
                star2.setVisibility(View.VISIBLE);
                star3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                star1.setVisibility(View.VISIBLE);
                star2.setVisibility(View.INVISIBLE);
                star3.setVisibility(View.INVISIBLE);
                break;

        }
    }

}
