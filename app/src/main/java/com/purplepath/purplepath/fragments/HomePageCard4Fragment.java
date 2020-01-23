package com.purplepath.purplepath.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.model.homeCardModel.HomeCardsModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Pratheep.S
 */


public class HomePageCard4Fragment extends Fragment {

    private Bundle args;
    private HomeCardsModel homeCardsModel;
    private int fragmentID=0;

    @Bind(R.id.quote)
    TextView quote;

    @Bind(R.id.person)
    TextView person;

    @Bind(R.id.title)
    TextView title;

    private CardView card_root;

    int height;
    int width;
    private DisplayMetrics displayMetrics;

    public HomePageCard4Fragment() {
        // Required empty public constructor
    }

    public static HomePageCard4Fragment newInstance(int fragmentID, HomeCardsModel homeCardsModel) {
        Bundle args = new Bundle();
        args.putInt("fragmentID",fragmentID);
        args.putSerializable("homeCardsModel",homeCardsModel);
        HomePageCard4Fragment fragment = new HomePageCard4Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home_page_card4, container, false);
        ButterKnife.bind(this, view);

        //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width * 1, (int) (height * .6 * .4));
        //layout_quote.setLayoutParams(layoutParams);
        displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        card_root= view.findViewById(R.id.card_root);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width * 1, (int) (height * .6 * .4));
        card_root.setLayoutParams(layoutParams);


        args = getArguments();
        if (args != null) {
            if (args.containsKey("fragmentID")) {
                fragmentID = args.getInt("fragmentID");
            }
            if (args.containsKey("homeCardsModel")) {
                homeCardsModel = (HomeCardsModel) args.getSerializable("homeCardsModel");
                if (null != homeCardsModel && null != homeCardsModel.getData() &&
                        null != homeCardsModel.getData().getCard4()) {
                    String quoteValue, personValue,category;
                    if (null != homeCardsModel.getData().getCard4().get(fragmentID)) {
                        quoteValue = homeCardsModel.getData().getCard4().get(fragmentID).getQuote();
                        personValue = homeCardsModel.getData().getCard4().get(fragmentID).getQuote_person();
                        category=homeCardsModel.getData().getCard4().get(fragmentID).getQuote_topic();

                        quote.setText(quoteValue);
                        person.setText("- "+personValue);
                        title.setText("Quotes -"+category);
                    }

                }
            }

        }


        return view;
    }

}
