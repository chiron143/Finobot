package com.purplepath.purplepath.fragments;


import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.model.homeCardModel.HomeCardsModel;
import butterknife.Bind;
import butterknife.ButterKnife;



/**
 * @author Pratheep.S
 */
public class HomePageCard1Fragment extends BaseFragment {

    @Bind(R.id.title_tv)
     TextView title_tv;

    @Bind(R.id.date)
    TextView date;

    @Bind(R.id.textMessage)
    TextView textMessage;


    private Bundle args;
    private HomeCardsModel homeCardsModel;
    private CardView card_root;

    int height;
    int width;
    private DisplayMetrics displayMetrics;

    FrameLayout blink_frame;

    LinearLayout layout_welcome,tax_outerContainer;

    TextView tax_title1,tax_title2,tax_message1,tax_message2;




    public static HomePageCard1Fragment newInstance(HomeCardsModel homeCardsModel, String userName) {

        Bundle args = new Bundle();
        args.putSerializable("homeCardsModel",homeCardsModel);
        args.putString("userName",userName);
        HomePageCard1Fragment fragment = new HomePageCard1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home_page_card1, container, false);
        ButterKnife.bind(this,view);


        displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        card_root= view.findViewById(R.id.card_root);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width * 1, (int) (height * .6 * .4));
        card_root.setLayoutParams(layoutParams);


//        tax_title1=(TextView)view.findViewById(R.id.tax_title1);
//        tax_title2=(TextView)view.findViewById(R.id.tax_title2);


//        tax_message1=(TextView)view.findViewById(R.id.tax_message1);
//        tax_message2=(TextView)view.findViewById(R.id.tax_message2);
//
//        layout_welcome=(LinearLayout)view.findViewById(R.id.layout_welcome);
//
//        tax_outerContainer=(LinearLayout)view.findViewById(R.id.tax_outerContainer);
//        blink_frame=(FrameLayout) view.findViewById(R.id.blink_frame);


     /*   Animation startAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_blinking);
        blink_frame.startAnimation(startAnimation);
        blink_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    addFragmenttoStack(new TaxPromptChartFragment());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        EasyGifView easyGifView = (EasyGifView)view.findViewById(R.id.easyGifView);
        easyGifView.setGifFromResource(R.drawable.tax);*/

        args=getArguments();
        if(args!=null){
            if(args.containsKey("homeCardsModel")){
                homeCardsModel= (HomeCardsModel) args.getSerializable("homeCardsModel");
                if(null!=homeCardsModel){
                    date.setText(homeCardsModel.getData().getCard1().getDate());
                    textMessage.setText(homeCardsModel.getData().getCard1().getText());


                  /*  String enableTaxCard=homeCardsModel.getData().getCard0().getEnable_flag();

                    if(enableTaxCard.equalsIgnoreCase("Y")){
                        tax_outerContainer.setVisibility(View.VISIBLE);
                        blink_frame.setVisibility(View.VISIBLE);

                        layout_welcome.setVisibility(View.GONE);
                    }else {
                        layout_welcome.setVisibility(View.VISIBLE);

                        tax_outerContainer.setVisibility(View.GONE);
                        blink_frame.setVisibility(View.GONE);
                    }
                    String taxtitle=homeCardsModel.getData().getCard0().getTitle();
                    String taxmessage=homeCardsModel.getData().getCard0().getText();

                    tax_title1.setText(taxtitle);
                    tax_message1.setText(taxmessage);*/

                }
            }


            if(args.containsKey("userName")){
                title_tv.setText("Welcome "+(args.getString("userName")));
            }

        }

        return view;
    }

}
