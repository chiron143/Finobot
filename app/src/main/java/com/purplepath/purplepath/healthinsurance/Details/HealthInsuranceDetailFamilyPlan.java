package com.purplepath.purplepath.healthinsurance.Details;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.propertyinsurance.model.Motor_Proper_Health_Model;
import java.math.BigInteger;

/**
 * Created by pravinr on 2/20/18.
 */

public class HealthInsuranceDetailFamilyPlan extends BaseFragment implements View.OnClickListener {

    Context mContext;
    TextView errorTextview;
    Motor_Proper_Health_Model motor_Proper_Health_Model;

    private BigInteger family_suggessted;

    private ScrollView layout_Scroll;
    private BigInteger total_coverage = BigInteger.ZERO;
    private BigInteger total_rider=BigInteger.ZERO;
    private BigInteger total_topup=BigInteger.ZERO;
    private BigInteger total_super_topup=BigInteger.ZERO;
    private BigInteger total_basic_plan=BigInteger.ZERO;

    private BigInteger total_add_all;
    private BigInteger total_sub_all;
    private BigInteger total_coverage_subract;

    private BigInteger employer_total_coverage=BigInteger.ZERO;
    private BigInteger employer_total_rider=BigInteger.ZERO;
    private BigInteger employer_total_topup=BigInteger.ZERO;
    private BigInteger employer_total_super_topup=BigInteger.ZERO;
    private BigInteger employer_total_basic_plan=BigInteger.ZERO;

    private BigInteger employer_total_add_all;
    private BigInteger employer_total_sub_all;

    private BigInteger personally_total_coverage=BigInteger.ZERO;
    private BigInteger personally_total_rider=BigInteger.ZERO;
    private BigInteger personally_total_topup=BigInteger.ZERO;
    private BigInteger personally_total_super_topup=BigInteger.ZERO;
    private BigInteger personally_total_basic_plan=BigInteger.ZERO;

    private BigInteger personally_total_add_all;
    private BigInteger personally_total_sub_all;

    private TextView mSuggessted;

    private TextView mbasic_plan_value,mtop_up_value,msuper_top_up_value,mrider_value,mrecommended_value;

    private TextView memp_basic_plan_value,memp_top_up_value,memp_super_top_up_value,memp_rider_value,memp_recommended_value;

    private TextView mper_basic_plan_value,mper_top_up_value,mper_super_top_up_value,mper_rider_value,mper_recommended_value;


    public static HealthInsuranceDetailFamilyPlan newInstance(Motor_Proper_Health_Model motor_proper_health_model) {
        Bundle args = new Bundle();
        args.putSerializable("motor_Proper_Health_Model",motor_proper_health_model);
        HealthInsuranceDetailFamilyPlan fragment = new HealthInsuranceDetailFamilyPlan();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View familyview=inflater.inflate(R.layout.fragment_detail_family_healthplan, container, false);

        mSuggessted = familyview.findViewById(R.id.suggessted_value);


        mbasic_plan_value = familyview.findViewById(R.id.basic_plan_value);
        mtop_up_value = familyview.findViewById(R.id.top_up_value);
        msuper_top_up_value = familyview.findViewById(R.id.super_top_up_value);
        mrider_value = familyview.findViewById(R.id.rider_value);
        mrecommended_value = familyview.findViewById(R.id.recommended_value);

        memp_basic_plan_value = familyview.findViewById(R.id.emp_basic_plan_value);
        memp_top_up_value = familyview.findViewById(R.id.emp_top_up_value);
        memp_super_top_up_value = familyview.findViewById(R.id.emp_super_top_up_value);
        memp_rider_value = familyview.findViewById(R.id.emp_rider_value);
        memp_recommended_value = familyview.findViewById(R.id.emp_recommended_value);

        mper_basic_plan_value = familyview.findViewById(R.id.per_basic_plan_value);
        mper_top_up_value = familyview.findViewById(R.id.per_top_up_value);
        mper_super_top_up_value = familyview.findViewById(R.id.per_super_top_up_value);
        mper_rider_value = familyview.findViewById(R.id.per_rider_value);
        mper_recommended_value = familyview.findViewById(R.id.per_recommended_value);



        if(null!=getArguments())
        {
            if(getArguments().containsKey("motor_Proper_Health_Model"))
                motor_Proper_Health_Model = (Motor_Proper_Health_Model) getArguments().getSerializable("motor_Proper_Health_Model");

            if (motor_Proper_Health_Model.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                try{
                    if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan()!=null){

                        family_suggessted= (new BigInteger(motor_Proper_Health_Model.
                                getData().getHealth_ins_plan().getFamily_floater_plan().getSuggested()));

                        //Total coverage
                        int length = motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                getAvailable().size();
                        for (int i = 0; i < length; i++) {
                            if(motor_Proper_Health_Model.getData().getHealth_ins_plan().
                                    getFamily_floater_plan().getAvailable().get(i).getCoverage()!=null)
                            total_coverage=total_coverage.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().
                                    getFamily_floater_plan().getAvailable().get(i).getCoverage()));
                            Log.d("","total_coverage"+total_coverage);

                            //Sub Insurance find ins_product_categories
                            int sub_length=motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                    getAvailable().get(i).getSub_insur().size();
                            for (int j=0;j<sub_length;j++){

                                if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                        getAvailable().get(i).getSub_insur().get(j).getIns_prod_cat().isEmpty()){
                                    if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                            getAvailable().get(i).getSub_insur().get(j).getIns_prod_cat().equals("Rider")){
                                        total_rider=total_rider.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(i).getSub_insur().get(j).getSum_assured()));
                                        Log.d("","total_rider"+total_rider);
                                    }
                                }

                                if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                        getAvailable().get(i).getSub_insur().get(j).getIns_prod_cat().isEmpty()){
                                    if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                            getAvailable().get(i).getSub_insur().get(j).getIns_prod_cat().equals("Top Up")){
                                        total_topup=total_topup.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(i).getSub_insur().get(j).getSum_assured()));
                                        Log.d("","total_topup"+total_topup);
                                    }
                                }

                                if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                        getAvailable().get(i).getSub_insur().get(j).getIns_prod_cat().isEmpty()) {
                                    if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                            getAvailable().get(i).getSub_insur().get(j).getIns_prod_cat().equals("Super Top Up")) {
                                        total_super_topup = total_super_topup.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(i).getSub_insur().get(j).getSum_assured()));
                                        Log.d("", "total_super_topup" + total_super_topup);
                                    }
                                }

                                if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                        getAvailable().get(i).getSub_insur().get(j).getIns_prod_cat().isEmpty()){
                                    if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                            getAvailable().get(i).getSub_insur().get(j).getIns_prod_cat().equals("Basic Plan")){
                                        total_basic_plan=total_basic_plan.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(i).getSub_insur().get(j).getSum_assured()));
                                        Log.d("","total_basic_plan"+total_basic_plan);
                                    }
                                }

                                total_add_all=total_rider.add(total_topup).add(total_super_topup).add(total_basic_plan);
                                total_sub_all=family_suggessted.subtract(total_add_all);

                            }

                        }
                        if(total_coverage!=null&&total_add_all!=null)
                        total_coverage_subract=total_coverage.subtract(total_add_all);

                        /**
                         * employer provided
                         */
                        int emp_length = motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                getAvailable().size();

                        for (int k = 0; k < emp_length; k++) {

                            if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().getAvailable().
                                    get(k).getPlan_type()!=null) {

                                if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().getAvailable().
                                        get(k).getPlan_type().equals("Employer Provided")) {
                                    employer_total_coverage = employer_total_coverage.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().
                                            getFamily_floater_plan().getAvailable().get(k).getCoverage()));
                                    Log.d("", "employer_total_coverage" + employer_total_coverage);

                                    //Sub Insurance find ins_product_categories employer provided
                                    int emp_provided_sub_length = motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                            getAvailable().get(k).getSub_insur().size();
                                    for (int m = 0; m < emp_provided_sub_length; m++) {

                                        if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(k).getSub_insur().get(m).getIns_prod_cat().isEmpty()) {
                                            if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                    getAvailable().get(k).getSub_insur().get(m).getIns_prod_cat().equals("Rider")) {
                                                employer_total_rider = employer_total_rider.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                        getAvailable().get(k).getSub_insur().get(m).getSum_assured()));
                                                Log.d("", "employer_total_rider" + employer_total_rider);
                                            }
                                        }

                                        if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(k).getSub_insur().get(m).getIns_prod_cat().isEmpty()) {
                                            if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                    getAvailable().get(k).getSub_insur().get(m).getIns_prod_cat().equals("Top Up")) {
                                                employer_total_topup = employer_total_topup.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                        getAvailable().get(k).getSub_insur().get(m).getSum_assured()));
                                                Log.d("", "employer_total_topup" + employer_total_topup);
                                            }
                                        }

                                        if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(k).getSub_insur().get(m).getIns_prod_cat().isEmpty()) {
                                            if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                    getAvailable().get(k).getSub_insur().get(m).getIns_prod_cat().equals("Super Top Up")) {
                                                employer_total_super_topup = employer_total_super_topup.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                        getAvailable().get(k).getSub_insur().get(m).getSum_assured()));
                                                Log.d("", "employer_total_super_topup" + employer_total_super_topup);
                                            }
                                        }

                                        if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(k).getSub_insur().get(m).getIns_prod_cat().isEmpty()) {
                                            if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                    getAvailable().get(k).getSub_insur().get(m).getIns_prod_cat().equals("Basic Plan")) {
                                                employer_total_basic_plan = employer_total_basic_plan.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                        getAvailable().get(k).getSub_insur().get(m).getSum_assured()));
                                                Log.d("", "employer_total_basic_plan" + employer_total_basic_plan);
                                            }
                                        }


                                        employer_total_add_all = employer_total_rider.add(employer_total_topup).add(employer_total_super_topup).add(employer_total_basic_plan);
                                        employer_total_sub_all = family_suggessted.subtract(employer_total_add_all);

                                    }
                                }
                            }
                        }

                        /**
                         * personally subscribed
                         */
                        int per_length = motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                getAvailable().size();
                        for(int n=0;n<per_length;n++){
                            if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().getAvailable().get(n).
                                    getPlan_type() != null){

                            if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().getAvailable().get(n).
                                    getPlan_type().equals("Personally Subscribed")){
                                personally_total_coverage=personally_total_coverage.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().
                                        getFamily_floater_plan().getAvailable().get(n).getCoverage()));
                                Log.d("","personally_total_coverage"+personally_total_coverage);
                                //Sub Insurance find ins_product_categories personally provided
                                int emp_provided_sub_length=motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                        getAvailable().get(n).getSub_insur().size();
                                for(int o=0;o<emp_provided_sub_length;o++){

                                    if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                            getAvailable().get(n).getSub_insur().get(o).getIns_prod_cat().isEmpty()){
                                        if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(n).getSub_insur().get(o).getIns_prod_cat().equals("Rider")){
                                            personally_total_rider=personally_total_rider.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                    getAvailable().get(n).getSub_insur().get(o).getSum_assured()));
                                            Log.d("","personally_total_rider"+personally_total_rider);
                                        }
                                    }

                                    if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                            getAvailable().get(n).getSub_insur().get(o).getIns_prod_cat().isEmpty()){
                                        if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(n).getSub_insur().get(o).getIns_prod_cat().equals("Top Up")){
                                            personally_total_topup=employer_total_topup.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                    getAvailable().get(n).getSub_insur().get(o).getSum_assured()));
                                            Log.d("","personally_total_topup"+personally_total_topup);
                                        }
                                    }

                                    if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                            getAvailable().get(n).getSub_insur().get(o).getIns_prod_cat().isEmpty()) {
                                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(n).getSub_insur().get(o).getIns_prod_cat().equals("Super Top Up")) {
                                            personally_total_super_topup = personally_total_super_topup.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                    getAvailable().get(n).getSub_insur().get(o).getSum_assured()));
                                            Log.d("", "personally_total_super_topup" + personally_total_super_topup);
                                        }
                                    }

                                    if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                            getAvailable().get(n).getSub_insur().get(o).getIns_prod_cat().isEmpty()){
                                        if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(n).getSub_insur().get(o).getIns_prod_cat().equals("Basic Plan")){
                                            personally_total_basic_plan=personally_total_basic_plan.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                    getAvailable().get(n).getSub_insur().get(o).getSum_assured()));
                                            Log.d("","personally_total_basic_plan"+personally_total_basic_plan);
                                        }
                                    }

                                    personally_total_add_all=personally_total_rider.add(personally_total_topup).add(personally_total_super_topup).add(personally_total_basic_plan);
                                    personally_total_sub_all=family_suggessted.subtract(personally_total_add_all);
                                }

                            }
                            }
                        }


                        if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().getSuggested().
                                equalsIgnoreCase("0")){
                            errorTextview.setVisibility(View.VISIBLE);
                            layout_Scroll.setVisibility(View.GONE);
                        }else {



                            /**
                             * 0th suggested bar
                             */

                            mSuggessted.setText("₹ "+String.valueOf(family_suggessted));

                            /**
                             * 1st Barchart topup,supertopup,basic,rider
                             */

                            mbasic_plan_value.setText("₹ "+String.valueOf(total_basic_plan));

                            mtop_up_value.setText("₹ "+String.valueOf(total_topup));

                            msuper_top_up_value.setText("₹ "+String.valueOf(total_super_topup));

                            mrider_value.setText("₹ "+String.valueOf(total_rider));

                            mrecommended_value.setText("₹ "+String.valueOf(total_sub_all));

                            /**
                             * 2nd bar employer bar
                             */

                            memp_basic_plan_value.setText("₹ "+String.valueOf(employer_total_basic_plan));

                            memp_top_up_value.setText("₹ "+String.valueOf(employer_total_topup));

                            memp_super_top_up_value.setText("₹ "+String.valueOf(employer_total_super_topup));

                            memp_rider_value.setText("₹ "+String.valueOf(employer_total_rider));

                            memp_recommended_value.setText("₹ "+String.valueOf(employer_total_sub_all));


                            /**
                             * 3rd bar Personally provider
                             */

                            mper_basic_plan_value.setText("₹ "+String.valueOf(personally_total_basic_plan));

                            mper_top_up_value.setText("₹ "+String.valueOf(personally_total_topup));

                            mper_super_top_up_value.setText("₹ "+String.valueOf(personally_total_super_topup));

                            mper_rider_value.setText("₹ "+String.valueOf(personally_total_rider));

                            if(personally_total_sub_all!=null) {
                                mper_recommended_value.setText("₹ " + String.valueOf(personally_total_sub_all));
                            }else {
                                mper_recommended_value.setText("₹ " +"0");
                            }

                        }




                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else {

            }
        }
        return familyview;
    }




    @Override
    public void onClick(View v) {

    }
}
