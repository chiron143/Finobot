package com.purplepath.purplepath.fragments;


import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.NotificationActivity;
import com.purplepath.purplepath.Notification.Models.PromptsModel;
import com.purplepath.purplepath.alertprompt.personalprompt.model.dialog.PromptSugestionDialog;
import com.purplepath.purplepath.apputiles.UtileKit;

import java.util.ArrayList;

import static com.purplepath.purplepath.Notification.adapters.PromptsRecyclerViewAdapter.context;

/**
 * @author Pratheep.S
 */
public class GenericPromptsHomePageFragment extends BaseFragment implements View.OnClickListener {

    private Bundle args;
    private String promptCategory;
    private TextView desc;
    private PromptsModel promptsModel;
    private int fragmentID;
    private LinearLayout outerContainer;
    private CardView card_root;

    int height;
    int width;
    private DisplayMetrics displayMetrics;

    // public static FragmentManager fragmentManager;

    public GenericPromptsHomePageFragment() {
        // Required empty public constructor
    }

    public static GenericPromptsHomePageFragment newInstance(int fragmentID, PromptsModel promptsModel) {
        Bundle args = new Bundle();
        args.putInt("fragmentID",fragmentID);
        args.putSerializable("promptsModel",promptsModel);
        GenericPromptsHomePageFragment fragment = new GenericPromptsHomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_generic_prompts_home_page, container, false);

        //layout_prompt=(RelativeLayout)view.findViewById(R.id.layout_prompt);
        //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width * 1, (int) (height * .6 * .4));
        //layout_prompt.setLayoutParams(layoutParams);
        displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        card_root= view.findViewById(R.id.card_root);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width * 1, (int) (height * .6 * .4));
        card_root.setLayoutParams(layoutParams);

        desc= view.findViewById(R.id.desc);
        outerContainer= view.findViewById(R.id.outerContainer);
        outerContainer.setOnClickListener(this);

        args=getArguments();
        if(args!=null){
            if(args.containsKey("fragmentID")){
                fragmentID=args.getInt("fragmentID");
                if(args.containsKey("promptsModel")){
                    this.promptsModel= (PromptsModel) args.getSerializable("promptsModel");
                    setValues();
                }

            }

        }

        return view;
    }

    private void setValues() {
       if(fragmentID==1){
           desc.setText("You have missing details to be entered under Personal details");
       }else if(fragmentID==2){
           desc.setText("You have missing details to be entered under Family details");
       }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.outerContainer:
                String pageTitle,pageId;
                ArrayList<String> emptylist;
                PromptSugestionDialog newFragment;

                if(fragmentID==1){
                    if(promptsModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                        if (promptsModel.getData().getPersonal_fields()!=null) {
                           /* emptylist = promptsModel.getData().getPersonal_fields().getEmpty_fields();
                            pageTitle = "Personal Details";
                            pageId = promptsModel.getData().getPersonal_fields().getPage_id();
                            //Intent i = new Intent(view.getContext(), HomePageActivity.class);
                            //startActivityForResult(view.getParent().this,i,0,null)
                            newFragment = PromptSugestionDialog.newInstance(emptylist, pageTitle, Integer.parseInt(pageId));
                            newFragment.show(getChildFragmentManager(), "dialog");*/
                            addFragmenttoStack(NotificationActivity.newInstance(1));

                        }else {
                            UtileKit.showToastShort(context, context.getString(R.string.noInfoMsg));
                        }
                    }else {
                        UtileKit.showToastShort(context,context.getString(R.string.noInfoTempMsg));
                    }

                }else if(fragmentID==2){
                    if(promptsModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                        if (promptsModel.getData().getFamily_fields() != null) {
                            if (promptsModel.getData().getFamily_fields().getEmpty_fields() != null) {
                                /*emptylist = promptsModel.getData().getFamily_fields().getEmpty_fields();
                                pageTitle = "Family Details";
                                pageId = promptsModel.getData().getFamily_fields().getPage_id();
                                newFragment = PromptSugestionDialog.newInstance(emptylist, pageTitle, Integer.parseInt(pageId));
                                newFragment.show(getChildFragmentManager(), "dialog");*/
                                addFragmenttoStack(NotificationActivity.newInstance(1));

                            }
                        }else{
                            UtileKit.showToastShort(context,context.getString(R.string.noInfoMsg));
                        }
                    }else {
                        UtileKit.showToastShort(context,context.getString(R.string.noInfoTempMsg));
                    }

                }


                break;
        }
    }
}
