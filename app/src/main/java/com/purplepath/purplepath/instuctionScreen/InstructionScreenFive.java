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
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pravinr on 9/26/17.
 */

public class InstructionScreenFive extends BaseFragment {



    @Bind(R.id. text_first)
    TextView text_first;

    @Bind(R.id. layout_one)
    LinearLayout layout_one;

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
        text_first.setText("Your privacy is utmost important to us. We make use of your data to serve you better. We take all efforts to protect your data, and we never use your data for any purpose other than serving to your needs.");



        skib_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle args = new Bundle();
                args.putString("userid", UtileKit.getPersistedPurplePathPref("user_id"));
                args.putBoolean("IsSignUp",true);
                UtileKit.persistingPurplePathPref("isSignUp_demoScreen",true);

                //PersonalDetailsFragment fragment = new PersonalDetailsFragment();
                //PromptChatFragment fragment= new PromptChatFragment();
                PromptChatFragment1 fragment= new PromptChatFragment1();
                fragment.setArguments(args);
                addFragmenttoStack(fragment);
            }
        });
    }
}