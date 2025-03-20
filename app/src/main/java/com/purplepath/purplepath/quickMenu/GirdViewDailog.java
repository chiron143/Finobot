//package com.purplepath.purplepath.quickMenu;
//
//import android.content.Context;
//import android.os.Bundle;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.DialogFragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.GridViewInvers;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.finobot.finobot.R;
//import com.purplepath.purplepath.quickMenu.models.GirdviewText;
//
//import java.util.ArrayList;
//
///**
// * Created by bertrandrussellsakthees on 20/03/17.
// */
//
//public class GirdViewDailog  extends DialogFragment {
//    private GridViewInvers gridview;
//    private GridViewAdapterMenu adapter;
//    private ArrayList<GirdviewText> gridQuickmenuList;
//    private Context mContext;
//    private int height;
//    private int width;
//    private RelativeLayout mRelativeLayout;
//    int case_period ;
//    private TextView errorMessage;
//    private QuickMenuInterface callbackInterface;
//    private String childname[] = {"Personal Details",
//            "Family Details",
//            "Goal Details",
//            "Income Details",
//            "Expense Details",
//            "Asset Details",
//            "Liabilities",
//            "Insurance Details",
//            "Retirement Benefits"
//    };
//    private String child2name[] = {"Income Analysis",
//            "Expense Analysis", "Networth ", "Asset Allocation Chart", "Cash Management ", "Emergency fund", " Timeline", "Cash Flow Management", " Insurance Chart"
//            , "Tax Cash Flow Chart", "Risk Assesment", "Risk Assesment Result"};
//
//    private int mChild2drawable[] = {R.drawable.ic_income_chart_selat, R.drawable.ic_expense_chart_selat,
//            R.drawable.ic_networth_chart_selat, R.drawable.ic_asset_chart_selat,
//            R.drawable.ic_cash_management_chart_selat, R.drawable.ic_emergency_fund_chart_selat,
//            R.drawable.ic_goal_timeline_chart_selat, R.drawable.ic_cash_flow_management_chart_selat,
//            R.drawable.ic_insurance_chart, R.drawable.ic_tax_cash_flow_chart_selat
//            , R.drawable.ic_risk_chart, R.drawable.ic_risk_assenment_result_selat
//
//    };
//    private int mChilddrawable[] = {R.drawable.ic_personal_details_icon_selat, R.drawable.ic_family_details_icon_selat,
//            R.drawable.ic_goals_icon_selat,
//            R.drawable.ic_income_details_icon,
//            R.drawable.ic_expense_details_icon_selat,
//            R.drawable.ic_asst_icon_selat,
//            R.drawable.ic_liability_icon_selat,
//            R.drawable.ic_insurance_selat,
//            R.drawable.ic_retirement_icon_selat,
//            R.drawable.ic_logout_nav
//
//    };
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.categorydialog, container, false);
//        gridview = (GridViewInvers)v.findViewById(R.id.gridview);
//        errorMessage = ( TextView) v.findViewById(R.id.errorTextview);
//        mRelativeLayout=(RelativeLayout) v.findViewById(R.id.layout_root);
//        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(width, height);
//        params.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
//        mRelativeLayout.setLayoutParams(params);
//        case_period = (Integer) getArguments().get("peroid");
//        Log.i("GirdViewDailog","GirdViewDailog"+ case_period);
//        try {
//            if (gridQuickmenuList != null) {
//                adapter = new GridViewAdapterMenu(mContext, gridQuickmenuList, case_period);
//                gridview.setAdapter(adapter);
//            }else{
//                errorMessage.setVisibility(View.VISIBLE);
//                gridview.setVisibility(View.VISIBLE);
//                errorMessage.setText("Error in retriving data ");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                try {
//                    if (case_period == 0) {
//                        callbackInterface.upadatepostion(0, position);
//                        getDialog().dismiss();
//                    } else if (case_period == 1) {
//                        callbackInterface.upadatepostion(1, position);
//                        getDialog().dismiss();
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });
//        return  v;
//    }
//
//    private ArrayList<GirdviewText> generateQuickMenuList() {
//        try {
//            ArrayList<GirdviewText> gridviewStringtextList = new ArrayList<>();
//
//                for (int i = 0; i < childname.length; i++) {
//                    GirdviewText gridTextInfo = new GirdviewText();
////            gridTextInfo.setQuickmenuText(childname[i]);
////            gridTextInfo.setQuickmenuImage(mdrawable[i]);
//                    gridTextInfo.setMyDataText(childname[i]);
//                    gridTextInfo.setMyDataImage(mChilddrawable[i]);
//                    gridTextInfo.setMyPlanText(child2name[i]);
//                    gridTextInfo.setMyPlanImage(mChild2drawable[i]);
//                    gridviewStringtextList.add(gridTextInfo);
//                }
//
//            return gridviewStringtextList;
//        }catch (Exception e){
//            e.printStackTrace();
//
//        }
//
//        return null;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mContext = getContext();
//        gridQuickmenuList = generateQuickMenuList();
//
////        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.APNA_DIALOG);
////        DisplayMetrics metrics = new DisplayMetrics();
////        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
////        height = metrics.heightPixels-(int) Math.abs(metrics.heightPixels*.30);
////        width = metrics.widthPixels-(int) Math.abs(metrics.widthPixels*.08);
//
//    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//    }
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//    }
//
//
//
//    public static GirdViewDailog newInstance(int i, QuickMenuInterface callbackInterface) {
//
//        GirdViewDailog fragment = new GirdViewDailog();
//        Bundle args = new Bundle();
//        args.putInt("peroid", i);
//        fragment.callbackInterface = callbackInterface;
//        fragment.setArguments(args);
//        return fragment;
//    }
//}
