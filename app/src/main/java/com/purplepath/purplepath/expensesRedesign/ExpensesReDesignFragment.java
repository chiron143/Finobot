//package com.purplepath.purplepath.expensesRedesign;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentTransaction;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.finobot.finobot.R;
//import com.finobot.finobot.activity.HomePageActivity;
//import com.purplepath.purplepath.expenseEDCOC.ExpenseTabMainFragment;
//import com.purplepath.purplepath.expensesRedesign.expensesselection.ExpensesSelectionDetailsFragment;
//import com.purplepath.purplepath.fragments.BaseFragment;
//import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
//
//
//public class ExpensesReDesignFragment extends BaseFragment implements View.OnClickListener {
//
//    ImageView imageView;
//    TextView goal_headear_bg_TxtView;
//    private Activity activity;
//    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
//    private OnActivityBackPressedListener mCallBackListener;
//
//   // public static GetExpensesDetailsModel  getExpensesDetailsModel;
//   // public static int expensesDetailsSize;
//
//    public ExpensesReDesignFragment() {
//        // Required empty public constructor
//    }
//
//
////    public static ExpensesReDesignFragment newInstance(String param1, String param2) {
////        ExpensesReDesignFragment fragment = new ExpensesReDesignFragment();
////        Bundle args = new Bundle();
////           fragment.setArguments(args);
////        return fragment;
////    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        activity = getActivity();
//
//        try {
//            setHasOptionsMenu(true);
//            mCallBackListener = (OnActivityBackPressedListener) (getContext());
//        }catch(ClassCastException e)
//        {
//            e.printStackTrace();
//        }
//        catch(Exception e)
//        {}
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        //callGetExpensesService();
//        View view = inflater.inflate(R.layout.fragment_expenses_re_design, container, false);
//        mleftRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_left_arrow);
//                mcenterRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_center_home);
//                mRightRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_right_arrow);
//        mleftRelativeLayout.setOnClickListener(this);
//        mcenterRelativeLayout.setOnClickListener(this);
//        mRightRelativeLayout.setOnClickListener(this);
//        mCallBackListener.setActionBarTitle("Expense Details");
//        goal_headear_bg_TxtView= (TextView)view.findViewById(R.id.goal_headear_bg_TxtView);
//        goal_headear_bg_TxtView.setText("Please add Expenses by clicking the + button");
//
//        //toolbar.setTitle("Expenses Details");
////        imageView = (ImageView) view.findViewById(R.id.header_plus_icon);
//        imageView = (ImageView) view.findViewById(R.id.goal_click_image);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentManager.popBackStack();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                ExpensesSelectionDetailsFragment fragment = new ExpensesSelectionDetailsFragment();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commitAllowingStateLoss();
//            }
//        });
//        return view;
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.clear();
//        inflater.inflate(R.menu.menu_empty_items, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//
//
//    }
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.relative_left_arrow:
//            {
//            mCallBackListener.onActivityBackPressed();
//            }
//            break;
//            case R.id.relative_center_home:
//            {
//              Intent i = new Intent(activity, HomePageActivity.class);
//                startActivity(i);
//            }
//            break;
//            case R.id.relative_right_arrow:
//            {
//                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentManager.popBackStack();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                ExpenseTabMainFragment fragment = new ExpenseTabMainFragment();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
////                fragmentTransaction.remove(this);
//                fragmentTransaction.addToBackStack(null);
//
//                fragmentTransaction.commitAllowingStateLoss();
//
//            }
//        }
//    }
//
//
////    public void callGetExpensesService(){
////        WebServiceCalls webServiceObj;
////        getExpensesDetailsModel = new GetExpensesDetailsModel();
////        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
////        Call<GetExpensesDetailsModel> call = webServiceObj.callGetExpensesDetailsService(UtileKit.getPersistedPurplePathPref("user_id"));
////        call.enqueue(new Callback<GetExpensesDetailsModel>() {
////            @Override
////            public void onResponse(Call<GetExpensesDetailsModel> call, Response<GetExpensesDetailsModel> response) {
////                getExpensesDetailsModel = response.body();
////                if(getExpensesDetailsModel.getData().getUser_expense() !=null) {
////                    if (!getExpensesDetailsModel.getData().getUser_expense().isEmpty()) {
////                        expensesDetailsSize = getExpensesDetailsModel.getData().getUser_expense().size();
////                    }
////                }
////
////            }
////            @Override
////            public void onFailure(Call<GetExpensesDetailsModel> call, Throwable t) {
////
////                UtileKit.dismisssSpinnerDialog();
////            }
////        });
////        UtileKit.dismisssSpinnerDialog();
////    }
//
//
//
//}
