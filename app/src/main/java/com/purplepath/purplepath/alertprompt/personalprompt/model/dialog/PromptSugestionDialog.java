package com.purplepath.purplepath.alertprompt.personalprompt.model.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.customview.CustomTextView;
import com.purplepath.purplepath.famlydetail.fragmentview.FamilyDetailFragment;
import com.purplepath.purplepath.fragments.PersonalDetailsFragment;

import java.util.ArrayList;

/**
 * Created by dinesh on 19/01/17.
 */
public class PromptSugestionDialog extends DialogFragment {


    String title;
    ArrayList<String> promptListArray;
    Context context;
    int height;
    int width;
    int mPageId;
    String FramentIdToPass;
    private RelativeLayout mRelativeLayout;
    private ImageView doneBtn,closebtn;

    public static PromptSugestionDialog newInstance(ArrayList<String> listView, String titelString,int pageid) {

        Bundle args = new Bundle();

        PromptSugestionDialog fragment = new PromptSugestionDialog();
        args.putStringArrayList("list", listView);
        args.putString("title", titelString);
        args.putInt("pageId",pageid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.APNA_DIALOG);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
         height = metrics.heightPixels-(int) Math.abs(metrics.heightPixels*.30);
         width = metrics.widthPixels-(int) Math.abs(metrics.widthPixels*.08);


        if (getArguments().containsKey("title"))
            title = getArguments().getString("title");
        if (getArguments().containsKey("list"))
            promptListArray = getArguments().getStringArrayList("list");
        if (getArguments().containsKey("pageId"))
        mPageId=getArguments().getInt("pageId");

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.prompt_dialog_view, container, false);

        mRelativeLayout= v.findViewById(R.id.outerContainer);
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(width, height);
        params.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
        mRelativeLayout.setLayoutParams(params);

       // mRelativeLayout.getGravity(c);
        CustomTextView descriptionText= v.findViewById(R.id.tv_missingfields);
        descriptionText.setBold();
        TextView titleView = v.findViewById(R.id.captiontitleId);
        titleView.setText(""+title);
        ListView listView= v.findViewById(R.id.listviewId);


        //getDialog().getWindow().setBackgroundDrawableResource(R.drawable.rounded_drawable);

        doneBtn= v.findViewById(R.id.done);
         closebtn= v.findViewById(R.id.closebtnId);
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Fragment fragmentView=null;
                switch (mPageId) {
                    case 1:
                        fragmentView = new PersonalDetailsFragment();

                        Bundle value = new Bundle();
                        value.putBoolean("IsSignUp", false);
                        fragmentView.setArguments(value);
//                        addFragmentToActivity(fragmentView);
                        FramentIdToPass="PersonalDetails";
                        break;
                    case 2:
                        fragmentView = new FamilyDetailFragment();
                        FramentIdToPass="FamilyDetails";
                        break;
                    default:
//                        FramentIdToPass="PersonalDetails";
                        fragmentView = new PersonalDetailsFragment();
                        break;
                }
                addFragmentToActivity(fragmentView);
//                callFragmentInHomePage(FramentIdToPass);
                dismiss();
            }
        });
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                context,
                R.layout.prompt_list_view_dialog,
                promptListArray);

        listView.setAdapter(arrayAdapter);
        return v;
    }

    private void callFragmentInHomePage(String FramentID) {

       // Intent intent=new Intent(getActivity(), HomePageActivity.class);
        Intent intent=getActivity().getIntent();
        intent.putExtra("FragmentID",FramentID);
        /*startActivity(intent);
        getActivity().finish();*/
        getActivity().setResult(11,intent);
       // getActivity().finish();
    }

    public void addFragmentToActivity(Fragment fragment) {
        try {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commitAllowingStateLoss();
        }catch (Exception e){e.printStackTrace();}
    }
    @Override
    public void onResume() {
        super.onResume();
       /* Window window = getDialog().getWindow();
        //window.setLayout(width, height);

        window.setGravity(Gravity.CENTER);*/
    }
}