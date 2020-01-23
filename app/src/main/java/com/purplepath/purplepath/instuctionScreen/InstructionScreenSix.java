package com.purplepath.purplepath.instuctionScreen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.chatprompt.PromptChatFragment1;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.fragments.PersonalDetailsFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pravinr on 9/26/17.
 */

public class InstructionScreenSix extends BaseFragment {

    @Bind(R.id. text_first)
    TextView text_first;
    @Bind(R.id. text_second)
    TextView text_second;
    @Bind(R.id. text_third)
    TextView text_third;


    @Bind(R.id. layout_one)
    LinearLayout layout_one;
    @Bind(R.id. layout_second)
    LinearLayout layout_second;
    @Bind(R.id. layout_third)
    LinearLayout layout_third;
    @Bind(R.id. skib_fab)
    FloatingActionButton skib_fab;



    private Context mContext;

    private OnActivityBackPressedListener mCallBackListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try {
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
            setHasOptionsMenu(true);

        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {}
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewfour =  inflater.inflate(R.layout.instruction_screen_three, container, false);
        ButterKnife.bind(this,viewfour);


        return viewfour;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        skib_fab.setVisibility(View.VISIBLE);

        layout_one.setVisibility(View.VISIBLE);
        layout_second.setVisibility(View.VISIBLE);
        layout_third.setVisibility(View.VISIBLE);

        text_first.setText("Your security comes first in everything we do. And it matters when it comes to manage your data. Security is built into all the layers of the system.");
        text_second.setText("This system is designed to operate in a high secure environment. That is, at your end, it is self- authenticated, and while at the transit, your information is transported in a SSL (Secure Sockets Layer) bandwidth, and at the system’s end, the data is stored in a 256-bit encrypted form.");
        text_third.setText("With this, you can rest assure that your information is safe and secure and that there is no intention of using it elsewhere and it is not shared to any third parties as well. ");

        skib_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle args = new Bundle();
                args.putString("userid", UtileKit.getPersistedPurplePathPref("user_id"));
                args.putBoolean("IsSignUp",true);
                UtileKit.persistingPurplePathPref("isSignUp_demoScreen",true);

                //addFragmenttoStack(new PersonalDetailsFragment());

                addFragmenttoStack(new PromptChatFragment1());

            }
        });

    }
}