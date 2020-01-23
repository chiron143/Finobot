package com.purplepath.purplepath.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.AppManagement.Payment.taxfilepayment.TaxFilePayment;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxfiling.TaxFileAdditionalChartConversation;
import com.purplepath.purplepath.taxfiling.TaxFileWouldYouConversation;
import com.purplepath.purplepath.taxfiling.TaxFilingAgreeFragment;
import com.purplepath.purplepath.taxfiling.TaxFilingConformationPdf;
import com.purplepath.purplepath.taxfiling.TaxFilingDoneFragment;
import com.purplepath.purplepath.taxfiling.TaxFilingHousePropertyConversation;
import com.purplepath.purplepath.taxfiling.TaxFilingInitialConversation;
import com.purplepath.purplepath.taxfiling.TaxFilingMultipleYesNoConversation;
import com.purplepath.purplepath.taxfiling.TaxFilingPayment;
import com.purplepath.purplepath.taxfiling.TaxFilingSummary;
import com.purplepath.purplepath.taxfiling.TaxFilingUploadFileNew;
import com.purplepath.purplepath.taxfiling.TaxFilingUploadFileNewMultiple;
import com.purplepath.purplepath.taxfiling.TaxFilingUploadNonForm;
import com.purplepath.purplepath.taxfiling.TaxPlanningViewPager.TaxPlanningChatViewPager;
import com.purplepath.purplepath.taxfiling.getUserStatusModel.UserStatusModel;
import com.purplepath.purplepath.taxfiling.uploadtaxfiles.TaxFileCheckListFragment;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pravinr on 2/7/18.
 */

public class TaxFilingHomePageFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout relativeLayout;

    private Context mContext;

    private String userStatus = "", paymentScreenSession = "", form26ScreenSession = "";

    private String filerStatus = "";

    private FrameLayout frame_layout;

    private String sessionAgreementScreen = "", form16Session = "", nonForm16Session = "",
            planamountSession = "", form16_uploadSession = "", add_invest = "", property_status = "",
            form16Multiple_uploadSession = "", form16MultipleSession = "", tax_summarySession = "",
            freeusercompleteSession = "", form26as_SkipSession = "", prepaid_tax_status = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tax_filing_homepage, container, false);

        mContext = getContext();
        relativeLayout = (RelativeLayout) view.findViewById(R.id.parent_RelativeLayout);

//        Animation startAnimationa = AnimationUtils.loadAnimation(getContext(), R.anim.anim_blinking);
//        star_mage.startAnimation(startAnimationa);
        frame_layout = (FrameLayout) view.findViewById(R.id.frame_layout);
        frame_layout.setOnClickListener(this);

        setListenerForAllImageViews(relativeLayout);

        // callGetTaxfileUserStatus();

        return view;
    }


    private void setListenerForAllImageViews(ViewGroup viewGroup) {
        View view;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                setListenerForAllImageViews((ViewGroup) view);
            } else if (view instanceof ImageView) {
                view.setOnClickListener(this);
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.frame_layout:
                // addFragmenttoStack(new TaxPlanningChatViewPager());
                // Hidden by rajasekar b
                callGetTaxfileUserStatus();
                break;
        }

    }

    private void callGetTaxfileUserStatus() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<UserStatusModel> call = webServiceObj.callGetTaxfileUserStatus(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<UserStatusModel>() {
            @Override
            public void onResponse(Call<UserStatusModel> call, Response<UserStatusModel> response) {
                UtileKit.dismisssSpinnerDialog();
                UserStatusModel userStatusModel = response.body();
                try {
                    if (userStatusModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        sessionMaintanance(userStatusModel);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UserStatusModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });
    }

    private void sessionMaintanance(UserStatusModel userStatusModel) {
        userStatus = userStatusModel.getData().getTax_file_user_status();
        paymentScreenSession = userStatusModel.getData().getPayment_made();
        form26ScreenSession = userStatusModel.getData().getForm_26as();
        filerStatus = userStatusModel.getData().getFiler_status();
        property_status = userStatusModel.getData().getProperty_status();
        prepaid_tax_status = userStatusModel.getData().getPrepaid_tax_status();

        if (!userStatusModel.getData().getPage_visited_array().isEmpty()) {
            sessionAgreementScreen = userStatusModel.getData().getPage_visited_array().get(0).getAgreement();

            form16Session = userStatusModel.getData().getPage_visited_array().get(0).getForm16();
            form16MultipleSession = userStatusModel.getData().getPage_visited_array().get(0).getMulti_form16();

            nonForm16Session = userStatusModel.getData().getPage_visited_array().get(0).getNon_form16();
            planamountSession = userStatusModel.getData().getPage_visited_array().get(0).getPlan_amount();

            form16_uploadSession = userStatusModel.getData().getPage_visited_array().get(0).getForm16_upload();
            form16Multiple_uploadSession = userStatusModel.getData().getPage_visited_array().get(0).getMulti_form16_upload();

            add_invest = userStatusModel.getData().getPage_visited_array().get(0).getAdd_invest();
            tax_summarySession = userStatusModel.getData().getPage_visited_array().get(0).getTax_summary();
            freeusercompleteSession = userStatusModel.getData().getPage_visited_array().get(0).getFree_user_flow_completed();
            form26as_SkipSession = userStatusModel.getData().getPage_visited_array().get(0).getForm26as();

        }


        HashMap<Integer, String> session_hashmap_switch = new HashMap<Integer, String>();
        session_hashmap_switch.put(1, userStatus);
        session_hashmap_switch.put(2, form26as_SkipSession);
        session_hashmap_switch.put(3, paymentScreenSession);
        session_hashmap_switch.put(4, nonForm16Session);
        session_hashmap_switch.put(5, planamountSession);
        session_hashmap_switch.put(6, form16Multiple_uploadSession);
        session_hashmap_switch.put(7, form16MultipleSession);
        session_hashmap_switch.put(8, form16MultipleSession);
        session_hashmap_switch.put(9, form16_uploadSession);
        session_hashmap_switch.put(10, form16Session);
        session_hashmap_switch.put(11, sessionAgreementScreen);
        session_hashmap_switch.put(12, planamountSession);


        switch (1) {
            case 1:
                Log.d("11", "11" + 1);
                if (userStatus.equalsIgnoreCase("true") || freeusercompleteSession.equalsIgnoreCase("Y")) {

                    addFragmenttoStack(TaxFilingDoneFragment.newInstance(filerStatus, prepaid_tax_status, planamountSession));

                    break;
                }
            case 2:
                Log.d("checklist", "checklist" + 2);
                if (!userStatusModel.getData().getPage_visited_array().isEmpty()) {

                    if (form26as_SkipSession.equalsIgnoreCase("Y")) {

                        if (add_invest.equalsIgnoreCase("Y")) {
                            addFragmenttoStack(new TaxFileCheckListFragment());
                        } else if (nonForm16Session.equalsIgnoreCase("Y")) {
                            addFragmenttoStack(new TaxFileCheckListFragment());
                        } else if (add_invest.equalsIgnoreCase("N")
                                && planamountSession.equalsIgnoreCase("199")) {
                            addFragmenttoStack(new TaxFileCheckListFragment());
                        } else if (planamountSession.equalsIgnoreCase("1")) {
                            addFragmenttoStack(new TaxFilingConformationPdf());
                        }

                        break;
                    }
                }
            case 3:
                Log.d("payment", "payment" + 3);
                if (paymentScreenSession.equalsIgnoreCase("true")) {

                    if (nonForm16Session.equalsIgnoreCase("Y")) {
                        // addFragmenttoStack(new TaxFilePlanningChartConversation());
                        addFragmenttoStack(new TaxPlanningChatViewPager());
                        break;
                    }
                    if (nonForm16Session.equalsIgnoreCase("N")) {

                        if (tax_summarySession.equalsIgnoreCase("Y")) {
                            addFragmenttoStack(new TaxFilingSummary());
                            break;
                        } else if (planamountSession.equalsIgnoreCase("1") && add_invest.equalsIgnoreCase("Y")) {
                            addFragmenttoStack(new TaxFileAdditionalChartConversation());
                            break;
                        } else if (planamountSession.equalsIgnoreCase("299") && add_invest.equalsIgnoreCase("Y")) {
                            addFragmenttoStack(new TaxFileAdditionalChartConversation());
                            break;
                        } else if (planamountSession.equalsIgnoreCase("199") && add_invest.equalsIgnoreCase("N")) {
                            if (property_status.equalsIgnoreCase("Y")) {
                                addFragmenttoStack(new TaxFilingHousePropertyConversation());
                            } else {
                                addFragmenttoStack(new TaxFilingSummary());
                            }
                            break;
                        } else if (planamountSession.equalsIgnoreCase("199") && add_invest.equalsIgnoreCase("Y")) {
                            addFragmenttoStack(new TaxFileAdditionalChartConversation());
                            break;
                        }
                    }

                }


            case 4:
                Log.d("nonform", "nonform" + 4);
                if (nonForm16Session.equalsIgnoreCase("Y")) {
                    String card_amount = "299";
                    addFragmenttoStack(TaxFilingUploadNonForm.newInstance(card_amount));
                    break;
                }

            case 5:
                Log.d("freeform", "freeform" + 5);
                if (planamountSession.equalsIgnoreCase("1")) {

                    if (tax_summarySession.equalsIgnoreCase("Y")) {
                        addFragmenttoStack(new TaxFilingSummary());
                        break;
                    } else if (form16Multiple_uploadSession.equalsIgnoreCase("Y")) {
                        addFragmenttoStack(TaxFileWouldYouConversation.newInstance("", add_invest));
                        break;
                    } else if (form16Multiple_uploadSession.equalsIgnoreCase("Y")) {
                        if (add_invest.equalsIgnoreCase("Y")) {
//                                    String dataPlanAmountYes = "299";
//                                    addFragmenttoStack(TaxFilePayment.newInstance(dataPlanAmountYes));
                            additionalPaymentDialog(mContext);
                        } else if (add_invest.equalsIgnoreCase("N")) {
                            if (property_status.equalsIgnoreCase("Y")) {
                                addFragmenttoStack(new TaxFilingHousePropertyConversation());
                            } else {
                                addFragmenttoStack(new TaxFilingSummary());
                            }
                        }
                        break;
                    } else if (form16Multiple_uploadSession.equalsIgnoreCase("Y")) {
                        addFragmenttoStack(TaxFileWouldYouConversation.newInstance("", add_invest));
                        break;
                    } else if (form16MultipleSession.equalsIgnoreCase("Y")) {

                        addFragmenttoStack(new TaxFilingUploadFileNewMultiple());
                        break;
                    } else if (form16MultipleSession.equalsIgnoreCase("N") && form16_uploadSession.equalsIgnoreCase("Y")) {

                        addFragmenttoStack(TaxFilingMultipleYesNoConversation.newInstance("", ""));
                        break;
                    } else if (form16_uploadSession.equalsIgnoreCase("Y")) {
                        if (add_invest.equalsIgnoreCase("Y")) {
//                                    String dataPlanAmountYes = "299";
//                                    addFragmenttoStack(TaxFilePayment.newInstance(dataPlanAmountYes));
                            additionalPaymentDialog(mContext);
                        } else if (add_invest.equalsIgnoreCase("N")) {
                            if (property_status.equalsIgnoreCase("Y")) {
                                addFragmenttoStack(new TaxFilingHousePropertyConversation());
                            } else {
                                addFragmenttoStack(new TaxFilingSummary());
                            }
                        }
                        break;
                    }
                }
            case 6:
                if (form16Multiple_uploadSession.equalsIgnoreCase("Y")) {
                    addFragmenttoStack(TaxFileWouldYouConversation.newInstance("", add_invest));
                    break;
                }

            case 7:
                if (form16MultipleSession.equalsIgnoreCase("Y")) {
                    addFragmenttoStack(new TaxFilingUploadFileNewMultiple());
                    break;
                }

            case 8:
                if (form16MultipleSession.equalsIgnoreCase("N") && form16_uploadSession.equalsIgnoreCase("Y")) {
//
                    addFragmenttoStack(TaxFilingMultipleYesNoConversation.newInstance("", ""));
                    break;
                }
            case 9:
                if (form16_uploadSession.equalsIgnoreCase("Y")) {
                    addFragmenttoStack(TaxFilingMultipleYesNoConversation.newInstance("", ""));
                    break;
                }

            case 10:
                if (form16Session.equalsIgnoreCase("Y")) {
                    addFragmenttoStack(new TaxFilingUploadFileNew());
                    break;
                }
            case 11:
                if (sessionAgreementScreen.equalsIgnoreCase("Y")) {
                    addFragmenttoStack(new TaxFilingInitialConversation());
                    break;
                }
            case 12:
                if (planamountSession.equalsIgnoreCase("1") ||
                        planamountSession.equalsIgnoreCase("199") ||
                        planamountSession.equalsIgnoreCase("299")) {

                    addFragmenttoStack(new TaxFilingAgreeFragment());
                    break;
                }

            default:
                Log.d("default", "default");
                if (userStatusModel.getData().getPage_visited_array().isEmpty()) {

                    addFragmenttoStack(new TaxFilingPayment());
                }


        }

    }


    private void additionalPaymentDialog(Context context) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialog;

        inflater = LayoutInflater.from(context);
        dialogView = inflater.inflate(R.layout.yes_no_settext, null);
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setView(dialogView);
        final TextView textView = dialogView.findViewById(R.id.additional_yes);
        textView.setText("You have said that you have additional income / investment(s) / claim(s) to be considered in your income tax return calculation, in addition to what is reflected in your Form 16. This would come under the 299 plan. If you agree, please click “Yes” to proceed.");
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dataPlanAmountYes = "299";
                addFragmenttoStack(TaxFilePayment.newInstance(dataPlanAmountYes));

                alertDialog.dismiss();

            }
        });
        dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

}
