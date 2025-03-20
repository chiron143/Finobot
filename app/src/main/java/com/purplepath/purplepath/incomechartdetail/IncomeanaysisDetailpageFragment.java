package com.purplepath.purplepath.incomechartdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.DefaultCurrencyTextView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.incomechartdetail.model.IncomeAnalysisModel;
import com.purplepath.purplepath.incomedetails.fragment.IncomeDetail;
import com.purplepath.purplepath.incomedetails.fragment.model.GetIncomeModel;
import com.purplepath.purplepath.incomedetails.fragment.model.Income_cat_lev1;
import com.purplepath.purplepath.incomedetails.fragment.model.User_incomes;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IncomeanaysisDetailpageFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private GetIncomeModel incomeModel;
    private IncomeAnalysisModel incomeAnalysisModel;

    private TextView errorTextview, mincome_date;
    private TextView total_currencyTextView;
    private DefaultCurrencyTextView salary_income_value, income_from_property_value,
    income_from_business_property_value, income_from_other_sources_value, capital_gain_value;

    String mtotal_currencyTextView, msalary_income_value, mincome_from_property_value,
            mincome_from_business_property_value, mincome_from_other_sources_value, mcapital_gain_value;
    private FloatingActionButton mEditIncomeFloatBtn;

    private LinearLayout checkboxLayout;
    private ArrayList<CheckBox> userNameCheckBox = new ArrayList<>();
    private Float[] yData;
    private ArrayList<String> xData, familyMemberNames;
    // private ArrayList<Family_Member_Details> family_member_details;
    String overallincome;

    String sitotal, iptotal, ibtotal, ifstotal, cgtotal;
    BigDecimal total_value=new BigDecimal(0);

    ArrayList<User_incomes> user_incomes = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        backPressedListener = (OnActivityBackPressedListener) context;
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_incomeanaysis_detailpage, container, false);
        backPressedListener.setActionBarTitle("Income Analysis");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

        total_currencyTextView = view.findViewById(R.id.total_currencyTextView);
        salary_income_value = view.findViewById(R.id.salary_income_value);
        income_from_property_value = view.findViewById(R.id.income_from_property_value);
        income_from_business_property_value = view.findViewById(R.id.income_from_business_property_value);
        income_from_other_sources_value = view.findViewById(R.id.income_from_other_sources_value);
        capital_gain_value = view.findViewById(R.id.capital_gain_value);

        mincome_date = view.findViewById(R.id.income_date);

       // errorTextview = (TextView) view.findViewById(R.id.empty_chart_display);
        mEditIncomeFloatBtn = view.findViewById(R.id.incom_fab_id);
        mEditIncomeFloatBtn.setOnClickListener(this);

        //mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        checkboxLayout = view.findViewById(R.id.checkboxLayout);
        getIncomeDetail();
        callIncomeAnalysisService();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_summary_and_chart, menu);
        MenuItem item = menu.findItem(R.id.menu_summary);
        MenuItem items = menu.findItem(R.id.menu_chart);
        // item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_chart:

                try {
                    addFragmenttoStack(new IncomePieChartFragment());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_summary:

                try {
                    addFragmenttoStack(new IncomeanaysisMainPageFragment());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;


        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void callIncomeAnalysisService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<IncomeAnalysisModel> call = webServiceObj.callIncomeAnalysisService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<IncomeAnalysisModel>() {
            @Override
            public void onResponse(Call<IncomeAnalysisModel> call, Response<IncomeAnalysisModel> response) {
                UtileKit.dismisssSpinnerDialog();
                //Log.e("success",""+response.body());
                incomeAnalysisModel = response.body();
                try {
                    if (incomeAnalysisModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        if (null != incomeAnalysisModel.getData().getUser_inc_analysis()) {
                            if (!incomeAnalysisModel.getData().getUser_inc_analysis().isEmpty()) {
                                overallincome = incomeAnalysisModel.getData().getUser_inc_analysis().get(0).getOverall_income();
//                                sitotal = incomeAnalysisModel.getData().getUser_inc_analysis().get(0).getFam_det().getSi_total();
//                                iptotal = incomeAnalysisModel.getData().getUser_inc_analysis().get(0).getFam_det().getIp_total();
//                                ibtotal = incomeAnalysisModel.getData().getUser_inc_analysis().get(0).getFam_det().getIb_total();
//                                ifstotal = incomeAnalysisModel.getData().getUser_inc_analysis().get(0).getFam_det().getIfs_total();
//                                cgtotal = incomeAnalysisModel.getData().getUser_inc_analysis().get(0).getFam_det().getCg_total();

                                // validate(overallincome,total_currencyTextView);
//                                total_value = Float.parseFloat(overallincome);
//                                total_currencyTextView.setText("₹ " + (UtileKit.longvalueabsolute(Float.parseFloat(overallincome))));
//                                validate(sitotal, salary_income_value);
//                                validate(iptotal, income_from_property_value);
//                                validate(ibtotal, income_from_business_property_value);
//                                validate(ifstotal, income_from_other_sources_value);
//                                validate(cgtotal, capital_gain_value);

                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                                Date date = new Date();
                                String[] value = dateFormat.format(date).split("/");
                                mincome_date.setText("As on " + value[2] + " " + UtileKit.getMonthString
                                        (Integer.parseInt(value[1]) - 1) + " " + value[0]);
                            }
                        } else {
                         //   UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
                        }
                    }
                    UtileKit.dismisssSpinnerDialog();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<IncomeAnalysisModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });
    }


    void validate(String string, DefaultCurrencyTextView currencyTextView) {
        if (string == null || string.equals("") || string.equalsIgnoreCase("0")) {
            currencyTextView.setText("0", TextView.BufferType.EDITABLE);
        } else {

            currencyTextView.setText(string, TextView.BufferType.NORMAL);
            //murugatextview.setText(muruga);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.incom_fab_id:
                addFragmenttoStack(new IncomeDetail());
                break;
        }

    }

//    public void addFragmenttoStack(Fragment mfagment) {
//        if (!mfagment.isVisible()) {
//            android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container, mfagment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commitAllowingStateLoss();
//        }
//    }

 /*   public class Family_Member_Details {
        int index;
        String familyMemberName;
        HashMap<Integer, Level_1_details> detailsHashMap;
        int hashMapKeySize;

        public Family_Member_Details(int index, String familyMemberName, HashMap<Integer, Level_1_details> detailsHashMap, int hashMapKeySize) {
            this.index = index;
            this.familyMemberName = familyMemberName;
            this.detailsHashMap = detailsHashMap;
            this.hashMapKeySize = hashMapKeySize;
        }
    }


    public class Level_1_details {
        String category_name, category_values;

        public Level_1_details(String category_name, String category_values) {
            this.category_name = category_name;
            this.category_values = category_values;
        }
    }*/

    private void getIncomeDetail() {

        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        Call<GetIncomeModel> call = webServiceObj.GetIncomeDetailService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetIncomeModel>() {
            @Override
            public void onResponse(Call<GetIncomeModel> call, Response<GetIncomeModel> response) {

                UtileKit.dismisssSpinnerDialog();
                incomeModel = response.body();
                yData = null;
                if (incomeModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    // scrolllinearlayout.setVisibility(View.VISIBLE);
                    if (null != incomeModel.getData().getUser_incomes()) {
                        int size = incomeModel.getData().getUser_incomes().size();
                        familyMemberNames = new ArrayList<String>();
                        for (int i = 0; i < size; i++) {
                            if (incomeModel.getData().getUser_incomes().get(i) != null) {
                                if (incomeModel.getData().getUser_incomes().get(i).getFamily_name().equalsIgnoreCase("")) {
                                    familyMemberNames.add(UtileKit.getPersistedPurplePathPref("name_services", null));

                                } else {
                                    familyMemberNames.add(incomeModel.getData().getUser_incomes().get(i).getFamily_name());

                                }

                            }

                        }
                        familyMemberNames.add("Total");
                        AddCheckBoxView(familyMemberNames);
//                        yData[size]=Float.valueOf(0);


                    } else {
                        //scrolllinearlayout.setVisibility(View.GONE);
                       // errorTextview.setVisibility(View.VISIBLE);
                        //errorTextview.setText(HomePageActivity.errorMessageInChart);

                       // UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
                    }

                } else {
                    //scrolllinearlayout.setVisibility(View.GONE);
                    //errorTextview.setVisibility(View.VISIBLE);
                    //errorTextview.setText(HomePageActivity.errorMessageInChart);

                    //UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
                }
            }

            @Override
            public void onFailure(Call<GetIncomeModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void AddCheckBoxView(ArrayList<String> xData) {
        checkboxLayout.removeAllViews();
        userNameCheckBox.clear();
        for (int i = 0; i < xData.size(); i++) {
            LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout parent_layout = new LinearLayout(mContext);
            parent_layout.setWeightSum(2);
            parent_layout.setOrientation(LinearLayout.HORIZONTAL);
            parent_param_layout.setMargins(10, 0, 0, 10);
            parent_layout.setLayoutParams(parent_param_layout);

            LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            parms_left_layout.weight = 1F;
            LinearLayout left_layout = new LinearLayout(mContext);
            left_layout.setOrientation(LinearLayout.HORIZONTAL);
            left_layout.setGravity(Gravity.LEFT);
            left_layout.setLayoutParams(parms_left_layout);

            //Log.e("Possition To Add L", "" + i);
            userNameCheckBox.add(new CheckBox(mContext));
            userNameCheckBox.get(i).setId(i);
            Log.i("spcheck", "set id=" + i);
            UtileKit.setTextAppearance(mContext, android.R.style.TextAppearance_Small, userNameCheckBox.get(i));
            userNameCheckBox.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int position = compoundButton.getId();
                    checkBoxOnClick(position, b);
                    //Log.e("Possition To Add L", "" + position);

                }
            });
            userNameCheckBox.get(i).setText(xData.get(i));
            Log.i("spcheck", "name get(i) i=" + i + " name " + xData.get(i));
            userNameCheckBox.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.app_text_color_gray));
            left_layout.addView(userNameCheckBox.get(i));
            i++;
            if (xData.size() == i) {
                if (xData.remove("Total")) {
                    parent_layout.addView(left_layout);
                    checkboxLayout.addView(parent_layout);
                }
                break;
            }

            LinearLayout.LayoutParams parms_right_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            parms_right_layout.weight = 1F;
            LinearLayout right_layout = new LinearLayout(mContext);
            right_layout.setOrientation(LinearLayout.HORIZONTAL);
            right_layout.setGravity(Gravity.LEFT);
            parent_param_layout.setMargins(10, 0, 0, 10);
            right_layout.setLayoutParams(parms_right_layout);

            //Log.e("Possition To Add R", "" + i);
            userNameCheckBox.add(new CheckBox(mContext));
            userNameCheckBox.get(i).setId(i);
            UtileKit.setTextAppearance(mContext, android.R.style.TextAppearance_Small, userNameCheckBox.get(i));
            userNameCheckBox.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int position = compoundButton.getId();
                    checkBoxOnClick(position, b);
                }
            });
            userNameCheckBox.get(i).setText(xData.get(i));
            userNameCheckBox.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.app_text_color_gray));
            right_layout.addView(userNameCheckBox.get(i));

            parent_layout.addView(left_layout);
            parent_layout.addView(right_layout);
            checkboxLayout.addView(parent_layout);


        }
        if (!userNameCheckBox.isEmpty()) userNameCheckBox.get(0).setChecked(true);
        // userNameCheckBox.get(userNameCheckBox.size() - 1).setChecked(true);
    }

    private void checkBoxOnClick(int position, boolean b) {
        user_incomes = incomeModel.getData().getUser_incomes();
        if (position == userNameCheckBox.size() - 1 & !b) {
            total_value=new BigDecimal(0);
            total_currencyTextView.setText("₹ "+"0");
            //show default value on uncheck
            validate("0", salary_income_value);
            validate("0", income_from_property_value);
            validate("0", income_from_business_property_value);
            validate("0", income_from_other_sources_value);
            validate("0", capital_gain_value);
        }
        if (position == userNameCheckBox.size() - 1 & b) {
            for (int i = 0; i < userNameCheckBox.size() - 1; i++) {
                userNameCheckBox.get(i).setChecked(false);
            }
            total_value=new BigDecimal(calCulateOverAllTotalValues());
            total_currencyTextView.setText("₹ "+(UtileKit.currToCharConversion(""+total_value)));
            calCulateTotalValues();
        } else {

//            userNameCheckBox.get(position).setChecked(b);
            if (b) {
                if (userNameCheckBox.get(userNameCheckBox.size() - 1).isChecked()) {
                    userNameCheckBox.get(userNameCheckBox.size() - 1).setChecked(false);
                    total_value=new BigDecimal(0);
                    total_currencyTextView.setText("₹ "+"0");
                    validate("0", salary_income_value);
                    validate("0", income_from_property_value);
                    validate("0", income_from_business_property_value);
                    validate("0", income_from_other_sources_value);
                    validate("0", capital_gain_value);
                    int size = user_incomes.size();
                    //clear the value/replace with its value
                    if (size > 0 && user_incomes != null) {
                        if (position < size) {
                            try {
                                if(!StringUtils.isEmpty(user_incomes.get(position).getOver_all_total()))
                                total_value = new BigDecimal(user_incomes.get(position).getOver_all_total());
//                                else
//                                    total_value=new BigDecimal(0);
                            }catch (Exception e){e.printStackTrace();}
                            total_currencyTextView.setText("₹ "+UtileKit.currToCharConversion(""+total_value));
                            ArrayList<Income_cat_lev1> income_cat_lev1 = user_incomes.get(position).getIncome_cat_lev1();
                            if (income_cat_lev1 != null & income_cat_lev1.size() > 0) {
                                for (int i = 0; i < income_cat_lev1.size(); i++) {
                                    if (income_cat_lev1.get(i).getId().equals("1")) {
                                        validate(user_incomes.get(position).getIncome_cat_lev1().get(i).getValue(), salary_income_value);
                                    } else if (income_cat_lev1.get(i).getId().equals("2")) {
                                        validate(user_incomes.get(position).getIncome_cat_lev1().get(i).getValue(), income_from_property_value);
                                    } else if (income_cat_lev1.get(i).getId().equals("3")) {
                                        validate(user_incomes.get(position).getIncome_cat_lev1().get(i).getValue(), income_from_business_property_value);
                                    } else if (income_cat_lev1.get(i).getId().equals("5")) {
                                        validate(user_incomes.get(position).getIncome_cat_lev1().get(i).getValue(), income_from_other_sources_value);
                                    } else if (income_cat_lev1.get(i).getId().equals("4")) {
                                        validate(user_incomes.get(position).getIncome_cat_lev1().get(i).getValue(), capital_gain_value);
                                    }
                                }
                            }

                        }//else set null values

                    }

                }// /add vlaue to other value if it is total value is not checked

                else {
                    Log.i("spcehck", "checkBoxOnClick: salary_income_value.getText()" + salary_income_value.getText());
                    int size = user_incomes.size();
                    if (size > 0 && user_incomes != null) {
                        try {
                            if (position < size) {
                                BigDecimal temp = new BigDecimal(0);
                       /*     temp=Float.parseFloat((UtileKit.validateObjectValues(total_currencyTextView.getText().toString()))?
                                    total_currencyTextView.getText().toString():0+"");*/
//                            total_value=0;
                                temp = total_value;
                                 if(!StringUtils.isEmpty(user_incomes.get(position).getOver_all_total()))
                                temp = temp.add(new BigDecimal(user_incomes.get(position).getOver_all_total()));
                                else
                                    temp=new BigDecimal(0);
                                total_value = temp;
                                total_currencyTextView.setText("₹ " + UtileKit.currToCharConversion("" + total_value));
                                ArrayList<Income_cat_lev1> income_cat_lev1 = user_incomes.get(position).getIncome_cat_lev1();
                                if (income_cat_lev1 != null & income_cat_lev1.size() > 0) {
                                    BigDecimal incomeValue;
                                    String valueFromWS;
                                    for (int i = 0; i < income_cat_lev1.size(); i++) {
                                        if (income_cat_lev1.get(i).getId().equals("1")) {
                                            valueFromWS = user_incomes.get(position).getIncome_cat_lev1().get(i).getValue();
                                            incomeValue = new BigDecimal(salary_income_value.getText().toString()).add(
                                                    new BigDecimal(UtileKit.validateObjectValues(valueFromWS) ? valueFromWS : 0 + ""));
                                            validate(incomeValue + "", salary_income_value);
                                        } else if (income_cat_lev1.get(i).getId().equals("2")) {
                                            valueFromWS = user_incomes.get(position).getIncome_cat_lev1().get(i).getValue();
                                            incomeValue = new BigDecimal((income_from_property_value.getText().toString())).add(
                                                    new BigDecimal(UtileKit.validateObjectValues(valueFromWS) ? valueFromWS : 0 + ""));
                                            validate(incomeValue + "", income_from_property_value);
                                        } else if (income_cat_lev1.get(i).getId().equals("3")) {
                                            valueFromWS = user_incomes.get(position).getIncome_cat_lev1().get(i).getValue();
                                            incomeValue = new BigDecimal(income_from_business_property_value.getText().toString()).add(
                                                    new BigDecimal(UtileKit.validateObjectValues(valueFromWS) ? valueFromWS : 0 + ""));
                                            validate(incomeValue + "", income_from_business_property_value);
                                        } else if (income_cat_lev1.get(i).getId().equals("5")) {
                                            valueFromWS = user_incomes.get(position).getIncome_cat_lev1().get(i).getValue();
                                            incomeValue = new BigDecimal(income_from_other_sources_value.getText().toString()).add(
                                                    new BigDecimal(UtileKit.validateObjectValues(valueFromWS) ? valueFromWS : 0 + ""));
                                            validate(incomeValue + "", income_from_other_sources_value);
                                        } else if (income_cat_lev1.get(i).getId().equals("4")) {
                                            valueFromWS = user_incomes.get(position).getIncome_cat_lev1().get(i).getValue();
                                            incomeValue = new BigDecimal(capital_gain_value.getText().toString()).add(
                                                    new BigDecimal(UtileKit.validateObjectValues(valueFromWS) ? valueFromWS : 0 + ""));
                                            validate(incomeValue + "", capital_gain_value);
                                        }
                                    }
                                }

                            }//else set null values
                        }catch (Exception e){e.printStackTrace();}
                    }

                }
            } else  {
                //  subtract the values from other value
                int size = user_incomes.size();
                if (size > 0 && user_incomes != null) {
                    try{
                    if (position < size) {
                        BigDecimal temp=new BigDecimal(0);
                        temp=total_value;
                        BigDecimal total=new BigDecimal(0);
                        try {
                            if (!StringUtils.isEmpty(user_incomes.get(position).getOver_all_total()))
                                total = new BigDecimal(user_incomes.get(position).getOver_all_total());
                        }catch (Exception e){e.printStackTrace();}

                        temp=temp.subtract((total));
                        total_value=temp;
                        total_currencyTextView.setText("₹ "+UtileKit.currToCharConversion(""+total_value));
                        ArrayList<Income_cat_lev1> income_cat_lev1 = user_incomes.get(position).getIncome_cat_lev1();
                        if (income_cat_lev1 != null & income_cat_lev1.size() > 0) {
                            BigDecimal incomeValue;
                            String valueFromWS;
                            for (int i = 0; i < income_cat_lev1.size(); i++) {
                                if (income_cat_lev1.get(i).getId().equals("1")) {
                                    valueFromWS = user_incomes.get(position).getIncome_cat_lev1().get(i).getValue();
                                    incomeValue = new BigDecimal(salary_income_value.getText().toString()) .subtract(
                                            new BigDecimal(UtileKit.validateObjectValues(valueFromWS) ? valueFromWS : 0 + ""));
                                    validate(incomeValue + "", salary_income_value);
                                } else if (income_cat_lev1.get(i).getId().equals("2")) {
                                    valueFromWS = user_incomes.get(position).getIncome_cat_lev1().get(i).getValue();
                                    incomeValue = new BigDecimal((income_from_property_value.getText().toString())).subtract(
                                            new BigDecimal(UtileKit.validateObjectValues(valueFromWS) ? valueFromWS : 0 + ""));
                                    validate(incomeValue + "", income_from_property_value);
                                } else if (income_cat_lev1.get(i).getId().equals("3")) {
                                    valueFromWS = user_incomes.get(position).getIncome_cat_lev1().get(i).getValue();
                                    incomeValue = new BigDecimal((income_from_business_property_value.getText().toString())).subtract(
                                            new BigDecimal(UtileKit.validateObjectValues(valueFromWS) ? valueFromWS : 0 + ""));
                                    validate(incomeValue + "", income_from_business_property_value);
                                } else if (income_cat_lev1.get(i).getId().equals("5")) {
                                    valueFromWS = user_incomes.get(position).getIncome_cat_lev1().get(i).getValue();
                                    incomeValue =  new BigDecimal(income_from_other_sources_value.getText().toString()).subtract(
                                            new BigDecimal(UtileKit.validateObjectValues(valueFromWS) ? valueFromWS : 0 + ""));
                                    validate(incomeValue + "", income_from_other_sources_value);
                                } else if (income_cat_lev1.get(i).getId().equals("4")) {
                                    valueFromWS = user_incomes.get(position).getIncome_cat_lev1().get(i).getValue();
                                    incomeValue = new BigDecimal(capital_gain_value.getText().toString()) .subtract(
                                            new BigDecimal(UtileKit.validateObjectValues(valueFromWS) ? valueFromWS : 0 + ""));
                                    validate(incomeValue + "", capital_gain_value);
                                }
                            }
                        }

                    }//else set null values
                }catch (Exception e){e.printStackTrace();}
                }

            }


        }
    }

    private String calCulateOverAllTotalValues() {
        BigDecimal total = new BigDecimal(0);
        if (user_incomes != null) {
            int size = user_incomes.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    if (!StringUtils.isEmpty(user_incomes.get(i).getOver_all_total())) {
                        total=total.add(new BigDecimal(user_incomes.get(i).getOver_all_total()));

                    }
                }
            }
        }
        return total + "";
    }

    private void calCulateTotalValues() {
        ArrayList<Income_cat_lev1> income_cat_lev1;
        BigDecimal totalSalaryIncome = new BigDecimal(0), totalIncomeFromProperty = new BigDecimal(0), totalIncomeFromBusiness = new BigDecimal(0), totalIncomeFromOtherSource = new BigDecimal(0),
                totalCapitalGains = new BigDecimal(0);
        String t1, t2, t3, t4, t5;
        if (user_incomes != null) {
            int size = user_incomes.size();
            BigDecimal overallTotal;
//            float total = 0;
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    income_cat_lev1 = user_incomes.get(i).getIncome_cat_lev1();
                    if (income_cat_lev1 != null & income_cat_lev1.size() > 0) {

                        for (int j = 0; j < income_cat_lev1.size(); j++) {
                            if (income_cat_lev1.get(j).getId().equals("1")) {
                                t1 = user_incomes.get(i).getIncome_cat_lev1().get(j).getValue();
                                totalSalaryIncome = totalSalaryIncome.add(new BigDecimal((UtileKit.validateObjectValues(t1) ? t1 : 0 + "")));
                            } else if (income_cat_lev1.get(j).getId().equals("2")) {
                                t2 = user_incomes.get(i).getIncome_cat_lev1().get(j).getValue();
                                totalIncomeFromProperty =totalIncomeFromProperty.add(new BigDecimal(UtileKit.validateObjectValues(t2) ? t2 : 0 + ""));
                            } else if (income_cat_lev1.get(j).getId().equals("3")) {
                                t3 = user_incomes.get(i).getIncome_cat_lev1().get(j).getValue();
                                totalIncomeFromBusiness = totalIncomeFromBusiness.add(new BigDecimal(UtileKit.validateObjectValues(t3) ? t3 : 0 + ""));
                            } else if (income_cat_lev1.get(j).getId().equals("5")) {
                                t4 = user_incomes.get(i).getIncome_cat_lev1().get(j).getValue();
                                totalIncomeFromOtherSource =totalIncomeFromOtherSource.add(new BigDecimal(UtileKit.validateObjectValues(t4) ? t4 : 0 + ""));
                            } else if (income_cat_lev1.get(j).getId().equals("4")) {
                                t5 = user_incomes.get(i).getIncome_cat_lev1().get(j).getValue();
                                totalCapitalGains = totalCapitalGains.add(new BigDecimal(UtileKit.validateObjectValues(t5) ? t5 : 0 + ""));
                            }
                        }
                        validate(totalSalaryIncome + "", salary_income_value);
                        validate(totalIncomeFromProperty + "", income_from_property_value);
                        validate(totalIncomeFromBusiness + "", income_from_business_property_value);
                        validate(totalIncomeFromOtherSource + "", income_from_other_sources_value);
                        validate(totalCapitalGains + "", capital_gain_value);
                    }
                }

            }//else set null values
        }
    }
}

