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
 * Created by Suresh on 18/09/17.
 */

public class InstructionScreenFour extends BaseFragment {



    @Bind(R.id. text_first)
    TextView text_first;
    @Bind(R.id. text_second)
    TextView text_second;


    @Bind(R.id. layout_one)
    LinearLayout layout_one;
    @Bind(R.id. layout_second)
    LinearLayout layout_second;

    @Bind(R.id. skib_fab)
    FloatingActionButton skib_fab;




    private Context mContext;

    private OnActivityBackPressedListener mCallBackListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
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

        text_first.setText("We are cognizant of your time and effort and we will not test your patience. We have made our best effort to make the data entry process as convenient as possible and we never force you to enter a particular data in a particular screen to move forward. You can provide your data as and when you login into the system and see the plan reflects the newly entered data.");
        text_second.setText("However, for any particular functionality to be executed and the system lacks any data, it will prompt you to provide the required data. Only then the outcome generated would be of great value to you.");


        skib_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle args = new Bundle();
                args.putString("userid", UtileKit.getPersistedPurplePathPref("user_id"));
                args.putBoolean("IsSignUp",true);
                UtileKit.persistingPurplePathPref("isSignUp_demoScreen",true);

                PromptChatFragment1 fragment= new PromptChatFragment1();
                //PersonalDetailsFragment   fragment = new PersonalDetailsFragment();
                fragment.setArguments(args);
                addFragmenttoStack(fragment);
            }
        });
    }
}