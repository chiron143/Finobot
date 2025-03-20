package com.purplepath.purplepath.schedule;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.calenderNumberPicker.calenderUi.CalenderTabs;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.DatePickerCallBackInterface;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.schedule.Interface.UpdateDetailsFromAdapter;
import com.purplepath.purplepath.schedule.adapter.UpcomingScheduleRecyclerViewAdapter;
import com.purplepath.purplepath.schedule.models.ScheduleModel;
import com.purplepath.purplepath.schedule.models.UpdateScheduleModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pratheep
 */

public class UpcomingSchedule extends BaseFragment implements RadioGroup.OnCheckedChangeListener, UpdateDetailsFromAdapter, DatePickerCallBackInterface {

    private static Context mcontext;

    private RecyclerView insurance_recycler_view, liablilty_recycler_view;

    private static UpcomingScheduleRecyclerViewAdapter insuranceAdapter, liabilityAdapter;

    private Bundle args;

    private ScheduleModel scheduleModel;

    private final int TYPE_CODE_INSURANCE = 0, TYPE_CODE_LIABILITY = 1;
    private static String TAG = "spcheck";

    private RadioGroup category_rg;
    private RadioButton insurance_rb, liability_rb;

    private String id = null, flag = null;
    private static int adapter_position;
    CalenderTabs dialog;

    private TextView noItemInsurance, noItemLiability;
    private boolean noItemInsuranceFlag = false, noItemLiabilityFlag = false;

    public static UpcomingSchedule newInstance(ScheduleModel scheduleModel) {

        Bundle args = new Bundle();
        args.putSerializable("scheduleModel", scheduleModel);
        UpcomingSchedule fragment = new UpcomingSchedule();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_schedule, container, false);
        insurance_recycler_view = view.findViewById(R.id.insurance_recycler_view);
        liablilty_recycler_view = view.findViewById(R.id.liablilty_recycler_view);

        noItemLiability = view.findViewById(R.id.noItemLiability);
        noItemInsurance = view.findViewById(R.id.noItemInsurance);

        category_rg = view.findViewById(R.id.category_rg);
        liability_rb = view.findViewById(R.id.liability_rb);
        insurance_rb = view.findViewById(R.id.insurance_rb);
        category_rg.setOnCheckedChangeListener(this);

        mcontext = getContext();

        args = getArguments();
        if (args != null) {
            if (args.containsKey("scheduleModel")) {
                scheduleModel = (ScheduleModel) args.get("scheduleModel");
                setValuesInAdapter(scheduleModel);

            } else {
                UtileKit.intitializeAlertDialog("No Data to show", mcontext);
            }

        }

        return view;
    }

    public void setValuesInAdapter(ScheduleModel scheduleModel) {
        Log.i(TAG, "setValuesInAdapter:called ");
        if (scheduleModel != null && scheduleModel.getData() != null) {
            if (null != scheduleModel.getData().getUpcom_sch()) {
                if (null != scheduleModel.getData().getUpcom_sch().getIns() &&
                        scheduleModel.getData().getUpcom_sch().getIns().size() > 0) {

                    if (category_rg.getCheckedRadioButtonId() == R.id.insurance_rb) {
                        insurance_recycler_view.setVisibility(View.VISIBLE);
                        noItemInsurance.setVisibility(View.GONE);
                        noItemLiability.setVisibility(View.GONE);
                        liablilty_recycler_view.setVisibility(View.GONE);
                    }
                    noItemInsuranceFlag = false;
                    insuranceAdapter = new UpcomingScheduleRecyclerViewAdapter(this, scheduleModel, mcontext, TYPE_CODE_INSURANCE);
                    insurance_recycler_view.setLayoutManager(new LinearLayoutManager(mcontext));
                    insurance_recycler_view.setAdapter(insuranceAdapter);
                } else {
                    noItemInsuranceFlag = true;
                    if (category_rg.getCheckedRadioButtonId() == R.id.insurance_rb) {
                        noItemInsurance.setVisibility(View.VISIBLE);
                        insurance_recycler_view.setVisibility(View.GONE);
                        noItemLiability.setVisibility(View.GONE);
                        liablilty_recycler_view.setVisibility(View.GONE);
                    }
                }
                if (null != scheduleModel.getData().getUpcom_sch().getLiab() &&
                        scheduleModel.getData().getUpcom_sch().getLiab().size() > 0) {
                    Log.i(TAG, "setValuesInAdapter: inner liablilties ");
                    if (category_rg.getCheckedRadioButtonId() == R.id.liability_rb) {
                        liablilty_recycler_view.setVisibility(View.VISIBLE);
                        noItemLiability.setVisibility(View.GONE);
                        insurance_recycler_view.setVisibility(View.GONE);
                        noItemInsurance.setVisibility(View.GONE);
                    }
                    noItemLiabilityFlag = false;
                    liabilityAdapter = new UpcomingScheduleRecyclerViewAdapter(this, scheduleModel, mcontext, TYPE_CODE_LIABILITY);
                    liablilty_recycler_view.setLayoutManager(new LinearLayoutManager(mcontext));
                    liablilty_recycler_view.setAdapter(liabilityAdapter);
                } else {
                    noItemLiabilityFlag = true;
                    if (category_rg.getCheckedRadioButtonId() == R.id.liability_rb) {
                        noItemLiability.setVisibility(View.VISIBLE);
                        liablilty_recycler_view.setVisibility(View.GONE);
                        insurance_recycler_view.setVisibility(View.GONE);
                        noItemInsurance.setVisibility(View.GONE);
                    }
                }
            } else {
                UtileKit.intitializeAlertDialog("No Data to show", mcontext);
            }
        } else {
            UtileKit.intitializeAlertDialog("No Data to show", mcontext);
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        switch (group.getId()) {
            case R.id.category_rg:
                if (checkedId == R.id.liability_rb) {
                    if (noItemLiabilityFlag) {
                        noItemLiability.setVisibility(View.VISIBLE);
                        liablilty_recycler_view.setVisibility(View.GONE);
                        insurance_recycler_view.setVisibility(View.GONE);
                        noItemInsurance.setVisibility(View.GONE);
                    }else{
                        liablilty_recycler_view.setVisibility(View.VISIBLE);
                        noItemLiability.setVisibility(View.GONE);
                        insurance_recycler_view.setVisibility(View.GONE);
                        noItemInsurance.setVisibility(View.GONE);
                    }

                } else if (checkedId == R.id.insurance_rb) {
                    if (noItemInsuranceFlag) {
                        noItemInsurance.setVisibility(View.VISIBLE);
                        insurance_recycler_view.setVisibility(View.GONE);
                        noItemLiability.setVisibility(View.GONE);
                        liablilty_recycler_view.setVisibility(View.GONE);
                    }else {
                        insurance_recycler_view.setVisibility(View.VISIBLE);
                        noItemInsurance.setVisibility(View.GONE);
                        noItemLiability.setVisibility(View.GONE);
                        liablilty_recycler_view.setVisibility(View.GONE);
                    }
                }
                break;
        }

    }

    @Override
    public void update_details(String id, String flag, int position) {
        this.id = id;
        this.flag = flag;
        adapter_position = position;
        dialog = CalenderTabs.newInstance(this, "Enter paid date", false, false, false, "1", "");
        dialog.show(getChildFragmentManager(), "date dialog");

    }

    public void update_schedule_details(String id, final String flag, String paid_date) {
        UtileKit.showSpinnerDialog(mcontext, false);
        WebServiceCalls obj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<UpdateScheduleModel> call = obj.call_update_schedule(id, flag, paid_date);
        call.enqueue(new Callback<UpdateScheduleModel>() {
            @Override
            public void onResponse(Call<UpdateScheduleModel> call, Response<UpdateScheduleModel> response) {
                UtileKit.dismisssSpinnerDialog();
                UpdateScheduleModel updateScheduleModel = response.body();
                if (updateScheduleModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    Toast.makeText(mcontext, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    refreshRecyclerView(flag);
                    callGetSchedulesByUserService();
                }
            }

            @Override
            public void onFailure(Call<UpdateScheduleModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private static void refreshRecyclerView(String flag) {
        if (flag.equals("ins")) {
            Log.i(TAG, "onClick:position refresh ins " + adapter_position);
            insuranceAdapter.scheduleModel.getData().getUpcom_sch().getIns().remove(adapter_position);
            //insuranceAdapter.notifyItemRemoved(adapter_position);
            insuranceAdapter.notifyDataSetChanged();
        } else if (flag.equals("liab")) {
            Log.i(TAG, "onClick:position refresh liab " + adapter_position);

            liabilityAdapter.scheduleModel.getData().getUpcom_sch().getLiab().remove(adapter_position);
            //liabilityAdapter.notifyItemRemoved(adapter_position);
            liabilityAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateEditTextValue(String value, String title) {
        //Log.i(TAG, "before format change "+value);
        dialog.dismiss();
        if (UtileKit.validateObjectValues(id) && UtileKit.validateObjectValues(flag) && UtileKit.validateObjectValues(value)) {
            //Log.i(TAG, "after format change "+changeDateFormat(value));
            update_schedule_details(id, flag, changeDateFormat(value));
            //  Log.i(TAG, "id,  "+id+" flag : "+flag+" value :"+value);
        }
    }

    @Override
    public void updateIndividualEditTextValue(String value, String title) {

    }

    private String changeDateFormat(String value) {
        String[] date = value.split("-");
        return (date[2] + "-" + date[1] + "-" + date[0]);

    }

    private void callGetSchedulesByUserService() {
        UtileKit.showSpinnerDialog(mcontext, false);
        WebServiceCalls obj = ServiceGenerator.createService(WebServiceCalls.class);
        Call call = obj.call_schedules_by_user(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                UtileKit.dismisssSpinnerDialog();
                scheduleModel = (ScheduleModel) response.body();
                setValuesInAdapter(scheduleModel);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();

            }
        });
    }

}
