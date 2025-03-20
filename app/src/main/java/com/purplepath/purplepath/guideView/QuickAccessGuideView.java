package com.purplepath.purplepath.guideView;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.finobot.finobot.R;

/**
 * Created by Pratheep.S on 23-06-2017.
 */

public class QuickAccessGuideView extends DialogFragment implements View.OnClickListener {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.WindowTitleBackground);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.quicka_ccess_guide_dialog,container,false);
        setCancelable(true);
        LinearLayout outerContainer= view.findViewById(R.id.outerContainer);
        outerContainer.setOnClickListener(this);
        view.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
