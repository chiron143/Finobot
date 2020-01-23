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
import com.purplepath.purplepath.model.homeCardModel.HomeCardsModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Pratheep.S
 */
public class HomePageCard2Fragment extends BaseFragment {

    private Bundle args;
    private HomeCardsModel homeCardsModel;
    private int fragmentID = 0;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.text)
    TextView text;

    @Bind(R.id.announcement_parent)
    LinearLayout announcement_parent;

    private CardView card_root;
    int height;
    int width;
    private DisplayMetrics displayMetrics;
    public HomePageCard2Fragment() {
        // Required empty public constructor
    }

    public static HomePageCard2Fragment newInstance(int fragmentID, HomeCardsModel homeCardsModel) {

        Bundle args = new Bundle();
        args.putInt("fragmentID", fragmentID);
        args.putSerializable("homeCardsModel", homeCardsModel);
        HomePageCard2Fragment fragment = new HomePageCard2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page_card2, container, false);
        ButterKnife.bind(this, view);



        displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        card_root= view.findViewById(R.id.card_root);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width * 1, (int) (height * .6 * .4));
        card_root.setLayoutParams(layoutParams);

        announcement_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    addFragmenttoStack(NotificationActivity.newInstance(3));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


        args = getArguments();
        if (args != null) {
            if (args.containsKey("fragmentID")) {
                fragmentID = args.getInt("fragmentID");
            }
            if (args.containsKey("homeCardsModel")) {
                homeCardsModel = (HomeCardsModel) args.getSerializable("homeCardsModel");
                if (null != homeCardsModel && null != homeCardsModel.getData() &&
                        null != homeCardsModel.getData().getCard2()) {
                    String titleValue, textValue;
                    if (null != homeCardsModel.getData().getCard2().get(fragmentID)) {
                        textValue = homeCardsModel.getData().getCard2().get(fragmentID).getText();
                        titleValue = homeCardsModel.getData().getCard2().get(fragmentID).getTitle();
                        title.setText(textValue);
                        text.setText(titleValue);
                    }

                }
            }

        }

        return view;
    }

}
