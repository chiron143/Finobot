package com.purplepath.purplepath.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.finobot.finobot.R;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dinesh on 26/05/16.
 */
public class TimeLineFragment extends BaseFragment {
    private boolean isClicked = true;
    ImageView  clickMeId;
    Button submitBtn;
    EditText submitText;
    LinearLayout rLayout;
    private Context mContext;
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View timeLineViewView;
        mContext = container.getContext();
        Toolbar    toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Goals TimeLine");
        timeLineViewView = inflater.inflate(R.layout.fragment_time_line,
                container, false);
        clickMeId = timeLineViewView.findViewById(R.id.clickmeId);
        submitBtn = timeLineViewView.findViewById(R.id.submitButtonId);
        rLayout = timeLineViewView.findViewById(R.id.addViewId);
        submitText = timeLineViewView.findViewById(R.id.getValueId);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rLayout.removeAllViews();
                if (submitText.getText().toString().trim().length() != 0) {
                    int count = Integer.parseInt(submitText.getText().toString());
                    setView(count);
                }
            }
        });


//        RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 1; i <= 10; i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_time_line_view, null);
            LinearLayout button1 = view.findViewById(R.id.left);
            LinearLayout button2 = view.findViewById(R.id.right);
//            ImageView addNodeCircleView = (ImageView) view.findViewById(R.id.LineGapSub1);
//            LinearLayout addChildNodeLeft1 = (LinearLayout) view.findViewById(R.id.leftSub1);
//            LinearLayout addChildNodeLeft2 = (LinearLayout) view.findViewById(R.id.leftSub2);
//            LinearLayout addChildNodeRight1 = (LinearLayout) view.findViewById(R.id.rightSub1);
//            LinearLayout addChildNodeRight2 = (LinearLayout) view.findViewById(R.id.rightSub2);
//            ImageView addNodeCircleView2 = (ImageView) view.findViewById(R.id.LineGapSub2);
//            ImageView nodeRightClick = (ImageView) view.findViewById(R.id.line_id11);
//            ImageView nodeLeftClick = (ImageView) view.findViewById(R.id.line_id1);


            if (i % 2 == 0) {
                button1.setVisibility(View.VISIBLE);
                button2.setVisibility(View.GONE);
//                addNodeCircleView.setVisibility(View.VISIBLE);
//                addChildNodeLeft1.setVisibility(View.VISIBLE);
//                addChildNodeLeft2.setVisibility(View.VISIBLE);
//                addNodeCircleView2.setVisibility(View.VISIBLE);
//                addChildNodeRight1.setVisibility(View.GONE);
//                addChildNodeRight2.setVisibility(View.GONE);
//                setViewId(nodeRightClick);
//                nodeRightClick.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(mContext, "Click on Goal" + v.getId(),
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });

            } else {
                button1.setVisibility(View.GONE);
                button2.setVisibility(View.VISIBLE);
//                addNodeCircleView.setVisibility(View.VISIBLE);
//                addChildNodeRight1.setVisibility(View.VISIBLE);
//                addChildNodeRight2.setVisibility(View.VISIBLE);
//                addNodeCircleView2.setVisibility(View.VISIBLE);
//                addChildNodeLeft1.setVisibility(View.GONE);
//                addChildNodeLeft2.setVisibility(View.GONE);
//                setViewId(nodeLeftClick);
//                nodeLeftClick.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(mContext, "Click on Goal " + v.getId(),
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });

            }

//            TextView button3 = (TextView) view.findViewById(R.id.button3);
//
//            button1.setText("HELLO " + i);
//            button2.setText("HELLO "+i);
//            button3.setText("HELLO "+i);
            rLayout.addView(view);
        }
//        ImageView tv1 = new ImageView(this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            tv1.setImageDrawable(getResources().getDrawable(R.drawable.icon_1, getApplicationContext().getTheme()));
//        } else {
//            tv1.setImageDrawable(getResources().getDrawable(R.drawable.icon_1));
//        }
//        lprams.addRule(RelativeLayout.CENTER_IN_PARENT);
//        tv1.setLayoutParams(lprams);
//        tv1.setId(1);
//
//        rLayout.addView(tv1);
//        RelativeLayout.LayoutParams lprams2 = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//        ImageView tv2 = new ImageView(this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            tv2.setImageDrawable(getResources().getDrawable(R.drawable.icon_2, getApplicationContext().getTheme()));
//        } else {
//            tv2.setImageDrawable(getResources().getDrawable(R.drawable.icon_2));
//        }
////        lprams2.addRule(RelativeLayout.BELOW,tv1.getId());
//        lprams2.addRule(Gravity.CENTER);
//        tv2.setLayoutParams(lprams2);
//        tv2.setId(2);
//
//        rLayout.addView(tv2);

//        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fabViewId);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        return timeLineViewView;
    }

    void setView(int count)

    {
        for (int i = 1; i <= count; i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_time_line_view, null);
            LinearLayout button1 = view.findViewById(R.id.left);
            LinearLayout button2 = view.findViewById(R.id.right);
//            ImageView addNodeCircleView = (ImageView) view.findViewById(R.id.LineGapSub1);
//            LinearLayout addChildNodeLeft1 = (LinearLayout) view.findViewById(R.id.leftSub1);
//            LinearLayout addChildNodeLeft2 = (LinearLayout) view.findViewById(R.id.leftSub2);
//            LinearLayout addChildNodeRight1 = (LinearLayout) view.findViewById(R.id.rightSub1);
//            LinearLayout addChildNodeRight2 = (LinearLayout) view.findViewById(R.id.rightSub2);
//            ImageView addNodeCircleView2 = (ImageView) view.findViewById(R.id.LineGapSub2);
//            ImageView nodeRightClick = (ImageView) view.findViewById(R.id.line_id11);
//            ImageView nodeLeftClick = (ImageView) view.findViewById(R.id.line_id1);


            if (i % 2 == 0) {
                button1.setVisibility(View.VISIBLE);
                button2.setVisibility(View.GONE);
//                addNodeCircleView.setVisibility(View.VISIBLE);
//                addChildNodeLeft1.setVisibility(View.VISIBLE);
//                addChildNodeLeft2.setVisibility(View.VISIBLE);
//                addNodeCircleView2.setVisibility(View.VISIBLE);
//                addChildNodeRight1.setVisibility(View.GONE);
//                addChildNodeRight2.setVisibility(View.GONE);
//                setViewId(nodeRightClick);
//                nodeRightClick.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(mContext, "Click on Node left Node" + v.getId(),
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });

            } else {
                button1.setVisibility(View.GONE);
                button2.setVisibility(View.VISIBLE);
//                addNodeCircleView.setVisibility(View.VISIBLE);
//                addChildNodeRight1.setVisibility(View.VISIBLE);
//                addChildNodeRight2.setVisibility(View.VISIBLE);
//                addNodeCircleView2.setVisibility(View.VISIBLE);
//                addChildNodeLeft1.setVisibility(View.GONE);
//                addChildNodeLeft2.setVisibility(View.GONE);
//                setViewId(nodeLeftClick);
//                nodeLeftClick.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(mContext, "Click on Node right Node" + v.getId(),
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });

            }

//            TextView button3 = (TextView) view.findViewById(R.id.button3);
//
//            button1.setText("HELLO " + i);
//            button2.setText("HELLO "+i);
//            button3.setText("HELLO "+i);
            rLayout.addView(view);
        }

    }

    public void expand(final View v) {

        v.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? WindowManager.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
        isClicked = false;
    }

    public void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
        isClicked = true;
    }

    public void setViewId(View myView) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {

            myView.setId(generateViewId());

        } else {

            myView.setId(View.generateViewId());

        }
    }

    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}
