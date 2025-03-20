package com.purplepath.purplepath.goalanalysis.dailogFragment;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.purplepath.purplepath.goalanalysis.adapter.MultipleGoalListadapter;
import com.purplepath.purplepath.goaltimeline.OnItemClickListnerInterfaces;
import com.purplepath.purplepath.goaltimeline.model.Goal_tmln;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import java.util.ArrayList;

/**
 * Created by Suresh on 18/07/17.
 */

public class MutipleGoalDialog extends DialogFragment {

    private Context mContext;

    private OnActivityBackPressedListener mCallBackListener;

    int height;

    int width;

    private GridView mgridview;

    private  ImageView closebtn;

    private FrameLayout mframelayout;

    private ArrayList<Integer> maddimageview = new ArrayList<>();

    private RelativeLayout mRelativeLayout;

    ArrayList<Goal_tmln> mGoalArrayFromInstance;

    OnItemClickListnerInterfaces getpositionInterface;

    public static MutipleGoalDialog newInstance(ArrayList<Goal_tmln> mGoalArray, Context mContext,
                                                OnItemClickListnerInterfaces callBackInterface) {

        Bundle args = new Bundle();
        args.putSerializable("gaolObj", mGoalArray);
        MutipleGoalDialog fragment = new MutipleGoalDialog();
        fragment.getpositionInterface=callBackInterface;
        fragment.mContext = mContext;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallBackListener = (OnActivityBackPressedListener) (mContext);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.APNA_DIALOG);

        try {
            if (getArguments().containsKey("gaolObj")) {
                mGoalArrayFromInstance = (ArrayList<Goal_tmln>) getArguments().getSerializable("gaolObj");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        height = metrics.widthPixels - (int) Math.abs(metrics.widthPixels * .20);
        width = metrics.widthPixels - (int) Math.abs(metrics.widthPixels * .40);


    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        this.getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.multiple_goal_item_list, container, false);
        mgridview = view.findViewById(R.id.multiple_goal_grid);

        mRelativeLayout = view.findViewById(R.id.outerContainer);


        closebtn= view.findViewById(R.id.closebtnId);

        mframelayout= view.findViewById(R.id.framelayout);



        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        mRelativeLayout.setLayoutParams(params);


        adapterFunctioncalls(mGoalArrayFromInstance);
        mgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Goal_tmln mGoalArray = (Goal_tmln) parent.getAdapter().getItem(position);
                    String goalId = mGoalArray.getId();
                    String mGoalname = mGoalArray.getGoal_name();
                    String mGoalyears = mGoalArray.getGoal_years();
                    String mExpectedincrement=mGoalArray.getExpected_increment();
                    getpositionInterface.onClick(goalId, mGoalname, mGoalyears,mExpectedincrement);
                    Log.i("Possition To Add L", "setOnItemSelectedListener" + position);
                    dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

            }
        });

//        mframelayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dismiss();
//            }
//        });
        return view;
    }

    private void adapterFunctioncalls(ArrayList<Goal_tmln> maddimageview) {
        if (maddimageview != null) {
            MultipleGoalListadapter mlistitemgoals = new MultipleGoalListadapter(mContext, maddimageview);
            mgridview.setAdapter(mlistitemgoals);


        }
    }



//    public void addFragmenttoStack(Fragment mfagment) {
//        if (!mfagment.isVisible()) {
//            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container, mfagment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commitAllowingStateLoss();
//        }
//    }
}
