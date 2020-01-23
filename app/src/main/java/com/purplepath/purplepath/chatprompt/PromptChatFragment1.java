package com.purplepath.purplepath.chatprompt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assets.AssetsDetailsFragment;
import com.purplepath.purplepath.chatprompt.insertmodel.InsertModel;
import com.purplepath.purplepath.chatprompt.model.GetPromptModel;
import com.purplepath.purplepath.chatprompt.model.Prompt_statements;
import com.purplepath.purplepath.famlydetail.fragmentview.FamilyDetailFragment;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.fragments.PersonalDetailsFragment;
import com.purplepath.purplepath.goal.GoalsListFragment;
import com.purplepath.purplepath.insurance.fragment.InsuranceDetailsFragment;
import com.purplepath.purplepath.liabilities.LiabilitiesTabViewFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retirementbenefits.RetirementBenefitsFragment;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxprepaid.TaxPrepaidFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by pravinr on 4/30/18.
 */

public class PromptChatFragment1 extends BaseFragment implements View.OnClickListener, View.OnFocusChangeListener{

    private static final String ARG_PARAM1 = "param1";

    private static final String ARG_PARAM2 = "param2";

    private String mParam1;

    private String mParam2;

    private OnActivityBackPressedListener mListener;

    private Context mContext;

    @Bind(R.id.parentViewId)
    LinearLayout parentView;

    @Bind(R.id.layout_yes_no_bottom_bar)
    LinearLayout layout_yes_no_bottom_bar;

    GetPromptModel getPromptModel;

    private TextView txt_left,dialog_no,dialog_yes;

    InsertModel insertModel;

    String ques_id ="";

    int positon;

    String corres_table="";

    private ScrollView scrollview;

    private LinearLayout redirect_page;

    OnActivityBackPressedListener backPressedListener;


    public static PromptChatFragment1 newInstance(String param1, String param2) {
        PromptChatFragment1 fragment = new PromptChatFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        try {
            backPressedListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_prompt_chat, container, false);
        ButterKnife.bind(this,view);

        backPressedListener.setActionBarTitle("Signup Prompt");
        scrollview= view.findViewById(R.id.scrollview);

        dialog_no= view.findViewById(R.id.no);
        dialog_yes= view.findViewById(R.id.yes);
        dialog_no.setOnClickListener(this);
        dialog_yes.setOnClickListener(this);

        callGetPromptService();


        dialog_no.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollview.smoothScrollTo(0, parentView.getBottom());
                scrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
                return false;
            }
        });
        dialog_yes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollview.smoothScrollTo(0, parentView.getBottom());
                scrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
                return false;
            }
        });
        scrollview.post(new Runnable() {
            public void run() {
                scrollview.fullScroll(View.FOCUS_DOWN);
            }
        });

        return  view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActivityBackPressedListener) {
            mListener = (OnActivityBackPressedListener) context;
        }
        else {
            throw new RuntimeException(context.toString()+ " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void callGetPromptService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetPromptModel> call = webServiceObj.callGetPromptService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetPromptModel>() {
            @Override
            public void onResponse(Call<GetPromptModel> call, Response<GetPromptModel> response) {
                UtileKit.dismisssSpinnerDialog();
                getPromptModel = response.body();
                if(getPromptModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    if(null!=getPromptModel.getData().getPrompt_statements()){
                        LayoutInflater inflater = LayoutInflater.from(mContext);
                        parentView.removeAllViews();
                        startDynamicChartView(getPromptModel,0);
                    }
                }
            }
            @Override
            public void onFailure(Call<GetPromptModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }


    void startDynamicChartView(GetPromptModel getPromptModel, int startPosition){
        int length=getPromptModel.getData().getPrompt_statements().size();
        for(int i=startPosition;i<length;i++) {
            positon = i;
            String type = getPromptModel.getData().getPrompt_statements().get(i).getType();
            ques_id = getPromptModel.getData().getPrompt_statements().get(i).getId();
            String updated_flag = getPromptModel.getData().getPrompt_statements().get(i).getUpdated_flag();
            String boolean_flag = getPromptModel.getData().getPrompt_statements().get(i).getBoolean_flag();
            String user_visited_flag = getPromptModel.getData().getPrompt_statements().get(i).getUser_visited_flag();

            if (type.equalsIgnoreCase("message")) {
                addMessagetoLeft(i, ques_id);
            } else if (type.equalsIgnoreCase("form")) {

                if (boolean_flag.equalsIgnoreCase("N")) {

                    addMessagetoLeft(i, ques_id);
                    addMessageWithCard(i, ques_id);
                } else {

                    corres_table = getPromptModel.getData().getPrompt_statements().get(i).getCorresponding_table();
                    addMessagetoLeft(i, ques_id);

                    if (updated_flag.equalsIgnoreCase("N") && user_visited_flag.equalsIgnoreCase("Y")) {
                        addMessagetoRight("No");

                    } else if (updated_flag.equalsIgnoreCase("Y") && user_visited_flag.equalsIgnoreCase("Y")) {
                        addMessageWithCard(i, ques_id);
                    }else {
                        bottomLinearlayoutAnimation(i, layout_yes_no_bottom_bar);
                    }
                }
            } else if (type.equalsIgnoreCase("final")) {

                addSubmitMessage(i, ques_id);
                positon = positon + 1;

            }
            if (user_visited_flag.equalsIgnoreCase("N")) {
                break;
            }

            scrollview.post(new Runnable() {
                @Override
                public void run() {
                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }

//        if((length-1)==positon){
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable()
//            {
//                @Override
//                public void run()
//                {
//                    Intent i = new Intent(getActivity(), HomePageActivity.class);
//                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(i);
//                }
//            }, 5000);
//        }
    }

    void addMessageWithCard(int i,String ques_id){
        int form_lengh = getPromptModel.getData().getPrompt_statements().get(i).getForm_array().size();
        parentView.addView(addMessageCardView(form_lengh, i, ques_id));
    }

    void addMessagetoLeft(int i,String ques_id){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.left_chat_msg_view, null);
        txt_left= view.findViewById(R.id.txt_left);
        LinearLayout layout_anim= view.findViewById(R.id.layout_anim);
        leftLinearlayoutAnimation(i,layout_anim);

        txt_left.setText(getPromptModel.getData().getPrompt_statements().get(i).getStatement());
        parentView.addView(view);
    }

    void addMessagetoRight(String mRight){
        View view = LayoutInflater.from(mContext).inflate(R.layout.right_chat_msg_view, null);
        TextView txt_layout= view.findViewById(R.id.txt_right);
        txt_layout.setText(mRight);
        parentView.addView(view);

    }


    void leftLinearlayoutAnimation(int i, LinearLayout linearLayout){
        TranslateAnimation anim = new TranslateAnimation(-100f, 0f, 0f, 0f);
        anim.setDuration(500*(i+1));
        linearLayout.setAnimation(anim);
        linearLayout.setVisibility(View.VISIBLE);
    }


    void bottomLinearlayoutAnimation(int i, LinearLayout linearLayout){
        TranslateAnimation anim = new TranslateAnimation(0, 0, 100, 0);
        anim.setDuration(1000);
        linearLayout.setAnimation(anim);
        linearLayout.setVisibility(View.VISIBLE);
    }


    private void addSubmitMessage(int i,String ques_id) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.prompt_submit_card_view, null);
        final TextView txt_layout = (TextView) view.findViewById(R.id.title);
        TextView submitTxt = (TextView) view.findViewById(R.id.questionId);
        submitTxt.setText(R.string.submit);

        txt_layout.setText("" + getPromptModel.getData().getPrompt_statements().get(i).getStatement());

        LinearLayout redirect_page = (LinearLayout) view.findViewById(R.id.redirect_page);
        LinearLayout redoBtn = (LinearLayout) view.findViewById(R.id.redo_pageId);
        TextView or=(TextView)view.findViewById(R.id.or);
        or.setVisibility(View.GONE);
        redoBtn.setVisibility(View.GONE);


        redirect_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });
        parentView.addView(view);
    }



    View addMessageCardView(int form_lengh, int i, String ques_id) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = null;
        view = inflater.inflate(R.layout.card_chat_msg_view, null);
        TextView txt_one = view.findViewById(R.id.txt_one);
        TextView txt_two = view.findViewById(R.id.txt_two);
        TextView txt_three = view.findViewById(R.id.txt_three);
        TextView txt_four = view.findViewById(R.id.txt_four);
        TextView title= view.findViewById(R.id.title);
        LinearLayout one_layout= view.findViewById(R.id.one_layout);
        LinearLayout two_layout= view.findViewById(R.id.two_layout);
        LinearLayout three_layout= view.findViewById(R.id.three_layout);
        LinearLayout four_layout= view.findViewById(R.id.four_layout);
        redirect_page= view.findViewById(R.id.redirect_page);


        ImageView img_one= view.findViewById(R.id.img_one);
        ImageView img_two= view.findViewById(R.id.img_two);
        ImageView img_three= view.findViewById(R.id.img_three);
        ImageView img_four= view.findViewById(R.id.img_four);

        final String user_visited_flag=getPromptModel.getData().getPrompt_statements().get(i).getUser_visited_flag();
        if(user_visited_flag.equalsIgnoreCase("Y")){
            redirect_page.setVisibility(View.GONE);

            UtileKit.setSvgImageviewDrawable(img_one,mContext,R.drawable.ic_prompt_select_tick);
            UtileKit.setSvgImageviewDrawable(img_two,mContext,R.drawable.ic_prompt_select_tick);
            UtileKit.setSvgImageviewDrawable(img_three,mContext,R.drawable.ic_prompt_select_tick);
            UtileKit.setSvgImageviewDrawable(img_four,mContext,R.drawable.ic_prompt_select_tick);
        }


        String str_title=getPromptModel.getData().getPrompt_statements().get(i).getPage_name();
        title.setText(str_title);
        corres_table=getPromptModel.getData().getPrompt_statements().get(i).getCorresponding_table();
        // redirect_page.setTag(corres_table);
        redirect_page.setTag(getPromptModel.getData().getPrompt_statements().get(i));
        redirect_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prompt_statements prompt_obj= (Prompt_statements) view.getTag();
                //switch (view.getTag().toString())
                Bundle args = new Bundle();
                switch (prompt_obj.getCorresponding_table()){
                    case "users_personal_details":

                        args.putBoolean("IsSignUp", true);
                        if (prompt_obj.getForm_array() != null) {
                            args.putSerializable("form_array", prompt_obj.getForm_array());
                        }
                        PersonalDetailsFragment fragment;
                        fragment=PersonalDetailsFragment.newInstance();
                        fragment.setArguments(args);
                        addFragmenttoStack(fragment);
                        break;
                    case "users_family_details":
                        args.putBoolean("IsSignUp", true);
                        if (prompt_obj.getForm_array() != null) {
                            args.putSerializable("form_array", prompt_obj.getForm_array());
                        }
                        FamilyDetailFragment familyDetailFragment;
                        familyDetailFragment=FamilyDetailFragment.newInstance();
                        familyDetailFragment.setArguments(args);
                        addFragmenttoStack(familyDetailFragment);
                        break;

//                    case "users_income_details":
//                        addFragmenttoStack(new IncomeDetail());
//                        break;
//
//                    case "users_expense_details":
//                        addFragmenttoStack(new ExpenseTabMainFragment());
//                        break;

                    case "users_general_goals":

                        args.putBoolean("IsSignUp", true);
                        args.putSerializable("user_visited_flag",user_visited_flag);
                        if (prompt_obj.getForm_array() != null) {
                            args.putSerializable("form_array", prompt_obj.getForm_array());
                        }

                        GoalsListFragment goalsListFragment;
                        goalsListFragment=GoalsListFragment.newInstance();
                        goalsListFragment.setArguments(args);
                        addFragmenttoStack(goalsListFragment);

                        break;
                    case "users_assets":

                        args.putBoolean("IsSignUp", true);
                        if (prompt_obj.getForm_array() != null) {
                            args.putSerializable("form_array", prompt_obj.getForm_array());
                        }


                        AssetsDetailsFragment assetsDetailsFragment;
                        assetsDetailsFragment=AssetsDetailsFragment.newInstance();
                        assetsDetailsFragment.setArguments(args);
                        addFragmenttoStack(assetsDetailsFragment);



//                        AssetDialogFragment assetDialogFragment;
//                        assetDialogFragment=AssetDialogFragment.newInstance();
//                        assetDialogFragment.setArguments(args);
//                        assetDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);

                        break;
                    case "users_liabilities":
                        args.putBoolean("IsSignUp", true);
                        if (prompt_obj.getForm_array() != null) {
                            args.putSerializable("form_array", prompt_obj.getForm_array());
                        }

                        LiabilitiesTabViewFragment liabilitiesTabViewFragment;
                        liabilitiesTabViewFragment=LiabilitiesTabViewFragment.newInstance();
                        liabilitiesTabViewFragment.setArguments(args);
                        addFragmenttoStack(liabilitiesTabViewFragment);
//                        LiabilityDialogFragment liabilityDialogFragment;
//                        liabilityDialogFragment=LiabilityDialogFragment.newInstance();
//                        liabilityDialogFragment.setArguments(args);
//                        liabilityDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);

                        break;
                    case "users_insur_details":

                        args.putBoolean("IsSignUp", true);
                        if (prompt_obj.getForm_array() != null) {
                            args.putSerializable("form_array", prompt_obj.getForm_array());
                        }
                        InsuranceDetailsFragment insuranceDetailsFragment;
                        insuranceDetailsFragment=InsuranceDetailsFragment.newInstance();
                        insuranceDetailsFragment.setArguments(args);
                        addFragmenttoStack(insuranceDetailsFragment);

                        break;
                    case "users_ret_ben":

                        args.putBoolean("IsSignUp", true);
                        if (prompt_obj.getForm_array() != null) {
                            args.putSerializable("form_array", prompt_obj.getForm_array());
                        }
                        RetirementBenefitsFragment retirementBenefitsFragment;
                        retirementBenefitsFragment=RetirementBenefitsFragment.newInstance();
                        retirementBenefitsFragment.setArguments(args);
                        addFragmenttoStack(retirementBenefitsFragment);

                        break;
                    case "users_prepaid_tax":

                        args.putBoolean("IsSignUp", true);
                        if (prompt_obj.getForm_array() != null) {
                            args.putSerializable("form_array", prompt_obj.getForm_array());
                        }
                        TaxPrepaidFragment taxPrepaidFragment;
                        taxPrepaidFragment=TaxPrepaidFragment.newInstance();
                        taxPrepaidFragment.setArguments(args);
                        addFragmenttoStack(taxPrepaidFragment);

                        break;
                }
            }
        });
        if(getPromptModel.getData().getPrompt_statements().get(i).getForm_array()!=null&&
                !getPromptModel.getData().getPrompt_statements().get(i).getForm_array().isEmpty()){

            if(getPromptModel.getData().getPrompt_statements().get(i).getForm_array().size()>0){
                one_layout.setVisibility(View.VISIBLE);
                txt_one.setText(getPromptModel.getData().getPrompt_statements().get(i).getForm_array().get(0));
            }
            if(getPromptModel.getData().getPrompt_statements().get(i).getForm_array().size()>1){
                two_layout.setVisibility(View.VISIBLE);
                txt_two.setText(getPromptModel.getData().getPrompt_statements().get(i).getForm_array().get(1));
            }
            if(getPromptModel.getData().getPrompt_statements().get(i).getForm_array().size()>2){
                three_layout.setVisibility(View.VISIBLE);
                txt_three.setText(getPromptModel.getData().getPrompt_statements().get(i).getForm_array().get(2));
            }
            if(getPromptModel.getData().getPrompt_statements().get(i).getForm_array().size()>3){
                four_layout.setVisibility(View.VISIBLE);
                txt_four.setText(getPromptModel.getData().getPrompt_statements().get(i).getForm_array().get(3));
            }
        }
        return view;
    }


    public void callUpdateInsertFlagService(final String table_name_id) {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<InsertModel> call = webServiceObj.callUpdateInsertFlagService(UtileKit.getPersistedPurplePathPref("user_id"),
                table_name_id,"Y","N");
        call.enqueue(new Callback<InsertModel>() {
            @Override
            public void onResponse(Call<InsertModel> call, Response<InsertModel> response) {
                UtileKit.dismisssSpinnerDialog();
                insertModel = response.body();
                if(insertModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    if(null!=insertModel.getData().getMessage()){
                        startDynamicChartView(getPromptModel,positon+1);
                    }
                }
            }
            @Override
            public void onFailure(Call<InsertModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
                callUpdateInsertFlagService(corres_table);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.no:
                addMessagetoRight("No");
                callUpdateInsertFlagService(corres_table);
                layout_yes_no_bottom_bar.setVisibility(View.GONE);
                scrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollview.fullScroll(ScrollView.FOCUS_DOWN);

                    }
                });

                break;
            case R.id.yes:

                addMessageWithCard(positon,ques_id);
                layout_yes_no_bottom_bar.setVisibility(View.GONE);

                scrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });

                break;

        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            try {
                v.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            scrollview.post(new Runnable() {
                @Override
                public void run() {
                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }

}
