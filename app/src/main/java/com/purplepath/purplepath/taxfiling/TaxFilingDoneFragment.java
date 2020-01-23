package com.purplepath.purplepath.taxfiling;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assets.dialog.AssetDatePickerDialogFragment;
import com.purplepath.purplepath.calenderNumberPicker.calenderUi.CalenderTabs;
import com.purplepath.purplepath.customview.CalendarEditText;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.DatePickerCallBackInterface;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxfiling.getadharpanmodel.AdharModel;
import com.purplepath.purplepath.taxfiling.gettaxconformationcheckbox.TaxConfirmationModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.finobot.finobot.MyApplication.mFirebaseAnalytics;


/**
 * Created by pravinr on 5/4/18.
 */

public class TaxFilingDoneFragment extends BaseFragment implements View.OnClickListener,DatePickerCallBackInterface {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;

    private FloatingActionButton rating_floating_button;

    private TextView incometax_link;

    private LinearLayout conform_layout;

    RadioGroup radioGroup;

    RadioButton optionA,optionB,optionC,optionD,
            one,two,three,four,five,six,seven,eight;

    String selectedValue="";

    String itr_processing_date="",refund_received_date="",intimation_received_date="";

    private String timeStamp;

    private EditText mCalanderEdt;

    private Bundle args=new Bundle();

    private LinearLayout date_layout_OptionA,date_layout_OptionB,date_layout_OptionC;

    private CalendarEditText date_Edt_OptionA,date_Edt_OptionB,date_Edt_OptionC;

    String get_Itr_processing_date,get_Refund_received_date,get_Intimation_received_date;

    CardView card_view,card_view1,card_view2;

    String filerStatus="",prepaid_tax_status="",planamountSession="";

    private TextView status_heading,status_sub_heading;


    public static TaxFilingDoneFragment newInstance(String filerStatus,String prepaid_tax_status,String planamountSession) {
        TaxFilingDoneFragment taxFilingDoneFragment = new TaxFilingDoneFragment();
        Bundle args = new Bundle();

        if (filerStatus != null) {
            args.putSerializable("filerStatus", filerStatus);
        }
        if (prepaid_tax_status != null) {
            args.putSerializable("prepaid_tax_status", prepaid_tax_status);
        }
        if (planamountSession != null) {
            args.putSerializable("planamountSession", planamountSession);
        }
        taxFilingDoneFragment.setArguments(args);
        return taxFilingDoneFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try{
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            backPressedListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        if(getArguments().containsKey("filerStatus")) {
            filerStatus = getArguments().getString("filerStatus");
        }
        if(getArguments().containsKey("prepaid_tax_status")) {
            prepaid_tax_status = getArguments().getString("prepaid_tax_status");
        }
        if(getArguments().containsKey("planamountSession")) {
            planamountSession = getArguments().getString("planamountSession");
        }


        View view=inflater.inflate(R.layout.fragment_taxfiling_done, container, false);
        backPressedListener.setActionBarTitle("Tax Filing Done");

        mleftRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        rating_floating_button = view.findViewById(R.id.rating_floating_button);
        rating_floating_button.setOnClickListener(this);
        incometax_link= view.findViewById(R.id.incometax_link);
        incometax_link.setOnClickListener(this);
        conform_layout=(LinearLayout)view.findViewById(R.id.conform_layout);
        conform_layout.setOnClickListener(this);

        card_view=view.findViewById(R.id.card_view);
        card_view1=view.findViewById(R.id.card_view1);
        card_view2=view.findViewById(R.id.card_view2);

        status_heading=(TextView)view.findViewById(R.id.status_heading);
        status_sub_heading=(TextView)view.findViewById(R.id.status_sub_heading);



        if(filerStatus.equalsIgnoreCase("false")){
            card_view.setVisibility(View.VISIBLE);
            card_view1.setVisibility(View.GONE);
            card_view2.setVisibility(View.GONE);

            if(planamountSession.equalsIgnoreCase("1")){
                if(prepaid_tax_status.equalsIgnoreCase("N")){
                    status_heading.setText("Failed to generate ITR-1 XML");
                    status_sub_heading.setText("We found you have not paid any prepaid tax. Please contact our support for more assistance.");
                }else {
                    status_heading.setText("Filing In-Progress");
                    status_sub_heading.setText("Thank you for using Finobot. Please check you email and download the ITR in XML format to file your Income Tax Return(ITR)");
                }
            }else {
                status_heading.setText("Filing In-Progress");
                status_sub_heading.setText("Thank you for enabling us to do your ITR eFiling. We are preparing the final artifacts to do the filing. We will call you sooner.");
            }


        }
        else
            {
            card_view.setVisibility(View.VISIBLE);
            card_view1.setVisibility(View.VISIBLE);
            card_view2.setVisibility(View.VISIBLE);
            status_heading.setText("Already Done");
            status_sub_heading.setText("You have already submitted all the documents. We will get back to you after tax filing");
        }


        date_layout_OptionA=(LinearLayout)view.findViewById(R.id.date_layout_OptionA);
        date_layout_OptionB=(LinearLayout)view.findViewById(R.id.date_layout_OptionB);
        date_layout_OptionC=(LinearLayout)view.findViewById(R.id.date_layout_OptionC);


        date_Edt_OptionA=(CalendarEditText) view.findViewById(R.id.date_Edt_OptionA);
        editTextDrawableClick(date_Edt_OptionA);
        date_Edt_OptionB=(CalendarEditText) view.findViewById(R.id.date_Edt_OptionB);
        editTextDrawableClick(date_Edt_OptionB);
        date_Edt_OptionC=(CalendarEditText) view.findViewById(R.id.date_Edt_OptionC);
        editTextDrawableClick(date_Edt_OptionC);



        radioGroup= view.findViewById(R.id.radioGroup1);
        optionA= view.findViewById(R.id.optionA);
        optionB= view.findViewById(R.id.optionB);
        optionC = view.findViewById(R.id.optionC);
        optionD= view.findViewById(R.id.optionD);
        one= view.findViewById(R.id.one);
        two= view.findViewById(R.id.two);
        three = view.findViewById(R.id.three);
        four= view.findViewById(R.id.four);
        five= view.findViewById(R.id.five);
        six= view.findViewById(R.id.six);
        seven = view.findViewById(R.id.seven);
        eight= view.findViewById(R.id.eight);



        try {
            timeStamp = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        }catch (Exception e){
            e.printStackTrace();
        }
        String dateStr = parseDate(timeStamp);

        date_Edt_OptionA.setText(dateStr);
        date_Edt_OptionB.setText(dateStr);
        date_Edt_OptionC.setText(dateStr);

        optionA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //Radio INvisible
                    setInVisibileRadioButton(optionD);
                    setInVisibileRadioButton(one);
                    setInVisibileRadioButton(two);
                    setInVisibileRadioButton(three);
                    setInVisibileRadioButton(four);
                    setInVisibileRadioButton(five);
                    setInVisibileRadioButton(six);
                    setInVisibileRadioButton(seven);
                    setInVisibileRadioButton(eight);


                    setVisibleLinearlayout(date_layout_OptionA);
                    setInVisibleLinearlayout(date_layout_OptionB);



                }
            }
        });

        optionB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {

                    //Radio INvisible
                    setInVisibileRadioButton(optionD);
                    setInVisibileRadioButton(one);
                    setInVisibileRadioButton(two);
                    setInVisibileRadioButton(three);
                    setInVisibileRadioButton(four);
                    setInVisibileRadioButton(five);
                    setInVisibileRadioButton(six);
                    setInVisibileRadioButton(seven);
                    setInVisibileRadioButton(eight);

                    setVisibleLinearlayout(date_layout_OptionB);
                    setInVisibleLinearlayout(date_layout_OptionA);
                }
            }
        });


        optionC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    setVisibileRadioButton(optionD);
                    setVisibileRadioButton(one);
                    setVisibileRadioButton(two);
                    setVisibileRadioButton(three);
                    setVisibileRadioButton(four);
                    setVisibileRadioButton(five);
                    setVisibileRadioButton(six);
                    setVisibileRadioButton(seven);
                    setVisibileRadioButton(eight);

                    setVisibleLinearlayout(date_layout_OptionC);
                    setInVisibleLinearlayout(date_layout_OptionA);
                    setInVisibleLinearlayout(date_layout_OptionB);

                }
            }
        });


        callgetTaxConfirmationService();

        return view;
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        mFirebaseAnalytics.setCurrentScreen(getActivity(), getString(R.string.analtics_taxconfirmationdone_screen), null /* class override */);
//    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.setCurrentScreen(getActivity(),getString(R.string.analtics_taxconfirmationdone_screen),getActivity().getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "14");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Tax Module");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Tax");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }

    private void callgetTaxConfirmationService() {

        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TaxConfirmationModel> call = webServiceObj.callgetTaxConfirmationStatus(UtileKit.
                getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<TaxConfirmationModel>() {
            @Override
            public void onResponse(Call<TaxConfirmationModel> call, Response<TaxConfirmationModel> response) {
                UtileKit.dismisssSpinnerDialog();
                TaxConfirmationModel declarationmodel = response.body();
                if (declarationmodel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                  selectedCheckBoxValue(declarationmodel);

                }
            }

            @Override
            public void onFailure(Call<TaxConfirmationModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }

    private void selectedCheckBoxValue(TaxConfirmationModel declarationmodel) {

        int length=declarationmodel.getData().getTax_conf_details().size();

        for(int i=0;i<length;i++) {

            String selectedCheckbox = declarationmodel.getData().getTax_conf_details().get(i).getConfirmation_text();


             get_Itr_processing_date=declarationmodel.getData().getTax_conf_details().get(i).getItr_processing_date();
             get_Refund_received_date=declarationmodel.getData().getTax_conf_details().get(i).getRefund_received_date();
             get_Intimation_received_date=declarationmodel.getData().getTax_conf_details().get(i).getIntimation_received_date();


            if(get_Itr_processing_date.equalsIgnoreCase("0000-00-00")){
                optionA.setText("ITR Processing Completed");
            }else {
                optionA.setText("ITR Processing Completed"+"  "+setDateFormat(get_Itr_processing_date));
            }

            if(get_Refund_received_date.equalsIgnoreCase("0000-00-00")) {
                optionB.setText("Refund Received Date");
            }else {
                optionB.setText("Refund Received " +"  "+ setDateFormat(get_Refund_received_date));
            }

            date_Edt_OptionC.setText(setDateFormat(get_Intimation_received_date));
            date_Edt_OptionA.setText(setDateFormat(get_Itr_processing_date));
            date_Edt_OptionB.setText(setDateFormat(get_Refund_received_date));

            if(selectedCheckbox.equalsIgnoreCase("ITR Processing Completed Date")) {
                optionA.setChecked(true);
               // if (get_Itr_processing_date.equalsIgnoreCase("")){
                  //  setVisibleLinearlayout(date_layout_OptionA);
                //  }
            }
            if(selectedCheckbox.equalsIgnoreCase("Refund Received Date")){
                optionB.setChecked(true);
            //    if(get_Refund_received_date.equalsIgnoreCase("")) {
                  //  setVisibleLinearlayout(date_layout_OptionB);
              //  }
            }
            if(selectedCheckbox.equalsIgnoreCase("Intimation Notice Received Date")){
                optionC.setChecked(true);
                //setVisibleLinearlayout(date_layout_OptionC);
            }
            if(selectedCheckbox.equalsIgnoreCase("Notice u/s 139(9)-Defective")){
                setVisibileRadioButton(optionD);
                optionD.setChecked(true);
                setVisibleLinearlayout(date_layout_OptionC);

            }
            if(selectedCheckbox.equalsIgnoreCase("Notice u/s 142(1)-Inquire Notice")){
                setVisibileRadioButton(one);
                one.setChecked(true);
                setVisibleLinearlayout(date_layout_OptionC);

            }
            if(selectedCheckbox.equalsIgnoreCase("Notice u/s 143(1)-Intimation")){
                setVisibileRadioButton(two);
                two.setChecked(true);
                setVisibleLinearlayout(date_layout_OptionC);


            }
            if(selectedCheckbox.equalsIgnoreCase("Notice u/s 143(1)(a)-Seeking Response During Assessment")){
                setVisibileRadioButton(three);
                three.setChecked(true);
                setVisibleLinearlayout(date_layout_OptionC);

            }
            if(selectedCheckbox.equalsIgnoreCase("Notice u/s 143(2)-Scrutiny Assessment Notice")){
                setVisibileRadioButton(four);
                four.setChecked(true);
                setVisibleLinearlayout(date_layout_OptionC);

            }
            if(selectedCheckbox.equalsIgnoreCase("Notice u/s 148-Commence Proceedings")){
                setVisibileRadioButton(five);
                five.setChecked(true);
                setVisibleLinearlayout(date_layout_OptionC);

            }
            if(selectedCheckbox.equalsIgnoreCase("Notice u/s 154-Rectification Notice")){
                setVisibileRadioButton(six);
                six.setChecked(true);
                setVisibleLinearlayout(date_layout_OptionC);

            }
            if(selectedCheckbox.equalsIgnoreCase("Notice u/s 154-Demand Notice")){
                setVisibileRadioButton(seven);
                seven.setChecked(true);
                setVisibleLinearlayout(date_layout_OptionC);

            }
            if(selectedCheckbox.equalsIgnoreCase("Notice u/s 245-Intimation to set off demand and Refund")){
                setVisibileRadioButton(eight);
                eight.setChecked(true);
                setVisibleLinearlayout(date_layout_OptionC);

            }
        }

        //for radio button purpose
        optionA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //Radio INvisible
                    setInVisibileRadioButton(optionD);
                    setInVisibileRadioButton(one);
                    setInVisibileRadioButton(two);
                    setInVisibileRadioButton(three);
                    setInVisibileRadioButton(four);
                    setInVisibileRadioButton(five);
                    setInVisibileRadioButton(six);
                    setInVisibileRadioButton(seven);
                    setInVisibileRadioButton(eight);

                  if(get_Itr_processing_date.equalsIgnoreCase("0000-00-00")) {
                        setVisibleLinearlayout(date_layout_OptionA);
                   }
                    setInVisibleLinearlayout(date_layout_OptionB);



                }
            }
        });

        optionB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {

                    //Radio INvisible
                    setInVisibileRadioButton(optionD);
                    setInVisibileRadioButton(one);
                    setInVisibileRadioButton(two);
                    setInVisibileRadioButton(three);
                    setInVisibileRadioButton(four);
                    setInVisibileRadioButton(five);
                    setInVisibileRadioButton(six);
                    setInVisibileRadioButton(seven);
                    setInVisibileRadioButton(eight);


                    if(get_Refund_received_date.equalsIgnoreCase("0000-00-00")) {
                        setVisibleLinearlayout(date_layout_OptionB);
                    }
                    setInVisibleLinearlayout(date_layout_OptionA);
                }
            }
        });


        optionC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    setVisibileRadioButton(optionD);
                    setVisibileRadioButton(one);
                    setVisibileRadioButton(two);
                    setVisibileRadioButton(three);
                    setVisibileRadioButton(four);
                    setVisibileRadioButton(five);
                    setVisibileRadioButton(six);
                    setVisibileRadioButton(seven);
                    setVisibileRadioButton(eight);

                    setVisibleLinearlayout(date_layout_OptionC);
                    setInVisibleLinearlayout(date_layout_OptionA);
                    setInVisibleLinearlayout(date_layout_OptionB);

                }
            }
        });



    }

    private String setDateFormat(String date) {
        String output = "";
        try {
            if (date.trim().length() != 0) {
                String dateAray[] = date.split("-");
                output = dateAray[2].concat("-").concat(dateAray[1].concat("-").concat(dateAray[0]));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
        return output;
    }


    public String parseDate(String time) {
        String str = null;
        DateFormat oldFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy ");
        Date oldDate = null;
        try {
            oldDate = (Date)oldFormatter .parse(time);
            str =formatter.format(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(formatter.format(oldDate));

        return str;
    }
    public String parseDateReverse(String time) {
        String str = null;
        DateFormat oldFormatter = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date oldDate = null;
        try {
            oldDate = (Date)oldFormatter .parse(time);
            str =formatter.format(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(formatter.format(oldDate));

        return str;
    }
    public void editTextDrawableClick(final CalendarEditText EdtText) {

        EdtText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    edtTextonClick(EdtText);
                    return true;
                }
                return false;
            }
        });
    }
    private void setEdtText(EditText edttext) {
        this.mCalanderEdt = edttext;
    }
    private EditText getEdtText() {
        return mCalanderEdt;
    }
    public void edtTextonClick(CalendarEditText EdtText) {
        try {
            if (EdtText == date_Edt_OptionA) {
                setEdtText(date_Edt_OptionA);
                String date=EdtText.getText().toString();
                if (UtileKit.validateObjectValues(date)) {
                    if (UtileKit.removeDefaultValue(date)) {
                        if (date.trim().length() != 0) {
                            CalenderTabs mcalenderTabs =  CalenderTabs.newInstance(this,
                                    "",Boolean.TRUE,Boolean.FALSE,Boolean.FALSE,"1", date);
                            if(UtileKit.validateObjectValues(date)) {
                                args.putString(AssetDatePickerDialogFragment.FULLDATE, date);
                                mcalenderTabs.setArguments(args);
                            }
                            mcalenderTabs.show(getFragmentManager(),"");
                        }
                    }
                }
            }
            else if(EdtText==date_Edt_OptionB){
                setEdtText(date_Edt_OptionB);
                String date=EdtText.getText().toString();
                if (UtileKit.validateObjectValues(date)) {
                    if (UtileKit.removeDefaultValue(date)) {
                        if (date.trim().length() != 0) {
                            CalenderTabs mcalenderTabs =  CalenderTabs.newInstance(this,
                                    "",Boolean.TRUE,Boolean.FALSE,Boolean.FALSE,"1", date);
                            if(UtileKit.validateObjectValues(date)) {
                                args.putString(AssetDatePickerDialogFragment.FULLDATE, date);
                                mcalenderTabs.setArguments(args);
                            }
                            mcalenderTabs.show(getFragmentManager(),"");
                        }
                    }
                }
            }
            else if(EdtText==date_Edt_OptionC){
                setEdtText(date_Edt_OptionC);
                String date=EdtText.getText().toString();
                if (UtileKit.validateObjectValues(date)) {
                    if (UtileKit.removeDefaultValue(date)) {
                        if (date.trim().length() != 0) {
                            CalenderTabs mcalenderTabs =  CalenderTabs.newInstance(this,
                                    "",Boolean.TRUE,Boolean.FALSE,Boolean.FALSE,"1", date);
                            if(UtileKit.validateObjectValues(date)) {
                                args.putString(AssetDatePickerDialogFragment.FULLDATE, date);
                                mcalenderTabs.setArguments(args);
                            }
                            mcalenderTabs.show(getFragmentManager(),"");
                        }
                    }
                }
            }




        } catch (IllegalArgumentException e) {

            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public void updateEditTextValue(String value, String title) {

        try {
            if (UtileKit.validateObjectValues(value)) {



                if(title.equalsIgnoreCase("ITR Processing Completed")){
                    date_Edt_OptionA.setText(value);

                    itr_processing_date = parseDateReverse(value);

                    Log.d("itr_processing_date","itr_processing_date"+itr_processing_date);

                }else if(title.equalsIgnoreCase("Refund Received")){
                    date_Edt_OptionB.setText(value);

                    refund_received_date = parseDateReverse(value);

                    Log.d("refund_received_date","refund_received_date"+refund_received_date);

                }
                else if(title.equalsIgnoreCase("Indimation Notice Received")){
                    date_Edt_OptionC.setText(value);

                    intimation_received_date = parseDateReverse(value);
                    Log.d("intimation_received_date","intimation_received_date"+intimation_received_date);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void updateIndividualEditTextValue(String value, String title) {

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){



            case R.id.conform_layout: {

                itr_processing_date=setDateFormat(date_Edt_OptionA.getText().toString());
                refund_received_date=setDateFormat(date_Edt_OptionB.getText().toString());
                intimation_received_date=setDateFormat(date_Edt_OptionC.getText().toString());

                if (optionA.isChecked()) {
                    selectedValue="ITR Processing Completed Date";
                    setVisibleLinearlayout(date_layout_OptionA);

                }
                if (optionB.isChecked()) {
                    selectedValue="Refund Received Date";
                    setVisibleLinearlayout(date_layout_OptionB);

                }
                if (optionC.isChecked()) {
                    selectedValue="Intimation Notice Received Date";
                    setVisibleLinearlayout(date_layout_OptionC);
                }
                if (optionD.isChecked()) {
                    selectedValue="Notice u/s 139(9)-Defective";

                }
                //
                if(one.isChecked()){
                    selectedValue="Notice u/s 142(1)-Inquire Notice";

                }
                if(two.isChecked()){
                    selectedValue="Notice u/s 143(1)-Intimation";

                }
                if(three.isChecked()){
                    selectedValue="Notice u/s 143(1)(a)-Seeking Response During Assessment";

                }
                if(four.isChecked()){
                    selectedValue="Notice u/s 143(2)-Scrutiny Assessment Notice";

                }
                if(five.isChecked()){
                    selectedValue="Notice u/s 148-Commence Proceedings";

                }
                if(six.isChecked()){
                    selectedValue="Notice u/s 154-Rectification Notice";

                }
                if(seven.isChecked()){
                    selectedValue="Notice u/s 154-Demand Notice";

                }
                if(eight.isChecked()){
                    selectedValue="Notice u/s 245-Intimation to set off demand and Refund";

                }
                callCredentialsService(selectedValue,itr_processing_date,refund_received_date,intimation_received_date);
            }
                break;

            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                startSettingHomeActivity();
                break;
            case R.id.rating_floating_button:

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.finobot.finobot&hl=en"));
                startActivity(intent);
                break;
            case R.id.incometax_link:
                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://portal.incometaxindiaefiling.gov.in/e-Filing/UserLogin/LoginHome.html?lang=eng"));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "No application can handle this request."+ " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;

        }

    }

//Radio button Visible
    void setVisibileRadioButton(RadioButton radioButton){
        radioButton.setVisibility(View.VISIBLE);
    }

//Radio button Gone
    void setInVisibileRadioButton(RadioButton radioButton){
        radioButton.setVisibility(View.GONE);
    }

//Linearlayout Visible
    void setVisibleLinearlayout(LinearLayout linearlayout){
        linearlayout.setVisibility(View.VISIBLE);
    }
    //Linearlayout Gone
    void setInVisibleLinearlayout(LinearLayout linearlayout){
        linearlayout.setVisibility(View.GONE);
    }


    private void callCredentialsService(String selectedValue,
                                        String itr_processing_date,
                                        String refund_received_date,
                                        String intimation_received_date) {

        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<AdharModel> call = webServiceObj.calladdTaxConfirmationStatus(UtileKit.
                getPersistedPurplePathPref("user_id"), selectedValue,itr_processing_date,refund_received_date,
                                                       intimation_received_date);
        call.enqueue(new Callback<AdharModel>() {
            @Override
            public void onResponse(Call<AdharModel> call, Response<AdharModel> response) {
                UtileKit.dismisssSpinnerDialog();
                AdharModel declarationmodel = response.body();
                if (declarationmodel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    addFragmenttoStack(new TaxFilingThankyouFragment());

                }
            }

            @Override
            public void onFailure(Call<AdharModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }

}
