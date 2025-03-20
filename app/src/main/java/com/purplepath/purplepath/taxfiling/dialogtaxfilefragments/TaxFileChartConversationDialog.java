package com.purplepath.purplepath.taxfiling.dialogtaxfilefragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;

/**
 * Created by pravinr on 6/20/18.
 */

public class TaxFileChartConversationDialog extends DialogFragment implements View.OnClickListener{


    private FrameLayout frame_layout_clickall;

    private RelativeLayout gotlayout_click;
    private Context mContext;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();

        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.WindowTitleBackground);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View guideview = inflater.inflate(R.layout.fragment_taxfile_chatconversationdialog, container, false);


        frame_layout_clickall = guideview.findViewById(R.id.frame_layout_clickall);
        gotlayout_click=guideview.findViewById(R.id.gotlayout_click);


        gotlayout_click.setOnClickListener(this);
        frame_layout_clickall.setOnClickListener(this);

        frame_layout_clickall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

        return guideview;
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()){


        }
    }



}
