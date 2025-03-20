package com.purplepath.purplepath.document.documentGridview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.document.documentGridview.adapter.CustomDocumentAdapter;
import com.purplepath.purplepath.document.getfilemodels.GetDocumentModels;
import com.purplepath.purplepath.document.ui.DocumentFragmentActivity;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.quickMenu.models.GirdviewText;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by bertrandrussellsakthees on 20/06/17.
 */

public class Documentgridfragment extends BaseFragment implements View.OnClickListener{

    private GridView mdocument_grid;
    private ArrayList<String> childnamePlandocument;
    private ArrayList<Integer> childnameDataImagedocument;
    private ArrayList<GirdviewText> gridListinnerMenu;
    private Context mcontext;
    private OnActivityBackPressedListener mCallBackListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    GetDocumentModels getDocumentModels;
    HashMap<String,String> digital_version =new HashMap<String,String>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext= getActivity();
        mCallBackListener = (OnActivityBackPressedListener) (mcontext);
            }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.document_gridview, container, false);
        mdocument_grid = view.findViewById(R.id.document_grid);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
       // mRightRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_right_arrow);
        try {
            mCallBackListener.setActionBarTitle("DigiVault");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
       // mRightRelativeLayout.setOnClickListener(this);


        childnamePlandocument = new ArrayList<>();
        childnamePlandocument.add("Government");
        childnamePlandocument.add("Academic");
        childnamePlandocument.add("Court Records");
        childnamePlandocument.add("Employment");
        childnamePlandocument.add("Insurance");
        childnamePlandocument.add("Investment");
        childnamePlandocument.add("Medical records");
        childnamePlandocument.add("Mediclaim");
        childnamePlandocument.add("Personal");
        childnamePlandocument.add("Post office");
        childnamePlandocument.add("Property");
        childnamePlandocument.add("Tax");
        childnamePlandocument.add("Others");
        childnamePlandocument.add("Email");
        childnamePlandocument.add("Form 16");
        childnamePlandocument.add("Form 16-A");
        childnamePlandocument.add("Form 16-B");



        childnameDataImagedocument = new ArrayList<>();

        childnameDataImagedocument.add( R.drawable.ic_government_icon);
        childnameDataImagedocument.add(R.drawable.ic_academic_icon);
        childnameDataImagedocument.add( R.drawable.ic_court_record_icon);
        childnameDataImagedocument.add(R.drawable.ic_employment_icon);
        childnameDataImagedocument.add(R.drawable.ic_insurance_icon);
        childnameDataImagedocument.add(R.drawable.ic_investment_icon);
        childnameDataImagedocument.add(R.drawable.ic_medical_record_icon);
        childnameDataImagedocument.add(R.drawable.ic_medical_record_icon);
        childnameDataImagedocument.add(R.drawable.ic_personal_icon);
        childnameDataImagedocument.add(R.drawable.ic_post_office_icon);
        childnameDataImagedocument.add(R.drawable.ic_property_icon);
        childnameDataImagedocument.add(R.drawable.ic_tax_icon);
        childnameDataImagedocument.add(R.drawable.ic_other_icon);
        childnameDataImagedocument.add(R.drawable.ic_email_svg_icon);
        childnameDataImagedocument.add(R.drawable.ic_form_16tax);
        childnameDataImagedocument.add(R.drawable.ic_taxform_a);
        childnameDataImagedocument.add(R.drawable.ic_taxform_b);
        gridListinnerMenu = generateQuickMenuListInner(childnamePlandocument,childnameDataImagedocument);
        Log.i("Document grid fragment"," gridListinnerMenu  " + gridListinnerMenu.size());


        mdocument_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onclickPostionStartFragment(position,gridListinnerMenu);
            }
        });

        callAddFileWebServiceupdates();
        return view;
    }

    /**
     *
     * @param position
     * @param gridListinnerMenu
     *
     * position will get the postion from the grid view
     * gridListinnerMenu is used add image and text manually
     * modify when services is given according to it
     *
     */
    private void onclickPostionStartFragment(int position, ArrayList<GirdviewText> gridListinnerMenu) {
        String validatestring = gridListinnerMenu.get(position).getMyDataText();
        Log.i("Document grid fragment"," validatestring  " + validatestring);
            // Strat next Fragment
           addFragmenttoStack(DocumentFragmentActivity.newInstance(validatestring));

    }

    private ArrayList<GirdviewText> generateQuickMenuListInner(ArrayList<String> childname, ArrayList<Integer> mChilddrawable) {
        try {
            ArrayList<GirdviewText> gridviewStringtextList = new ArrayList<>();

            for (int i = 0; i < childname.size(); i++) {
                GirdviewText gridTextInfo = new GirdviewText();

                    gridTextInfo.setMyDataText(childname.get(i));
                    gridTextInfo.setMyDataImage(mChilddrawable.get(i));

                gridviewStringtextList.add(gridTextInfo);
            }

            return gridviewStringtextList;
        }catch (Exception e){
            e.printStackTrace();

        }

        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relative_left_arrow:
            {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home:
            {
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
//                getActivity().finish();
            }
            break;
        }
    }

    public void callAddFileWebServiceupdates() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mcontext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);

            Call<GetDocumentModels> call = webServiceObj.callget_all_documents_by_user(UtileKit.getPersistedPurplePathPref("user_id"));
            call.enqueue(new retrofit2.Callback<GetDocumentModels>() {
                @Override
                public void onResponse(Call<GetDocumentModels> call, Response<GetDocumentModels> response) {
                    UtileKit.dismisssSpinnerDialog();
                    //Log.e("success", "" + response.body());
                     getDocumentModels = response.body();
                    Log.d("hi", "addfile" + response.body());
                    conditionToBeCheck(getDocumentModels);
                    if(gridListinnerMenu!= null){
                        CustomDocumentAdapter mcustomdocumentadapter = new CustomDocumentAdapter(mcontext,gridListinnerMenu, digital_version);
                        mdocument_grid.setAdapter(mcustomdocumentadapter);
                    }
                }

                @Override
                public void onFailure(Call<GetDocumentModels> call, Throwable t) {
                    //Log.e("CallBack", " failure is " + t);
                    UtileKit.dismisssSpinnerDialog();
                    UtileKit.alertRetrofitExceptionDialog(mcontext, t);
                }
            });
        }

    private void conditionToBeCheck(GetDocumentModels getDocumentModels) {

        if (getDocumentModels.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
            digital_version.clear();
            if (getDocumentModels.getData().getDocument_count() != null) {
                int size = getDocumentModels.getData().getDocument_count().size();
                for (int i = 0; i < size; i++) {
                    digital_version.put(getDocumentModels.getData().getDocument_count().get(i).getDigital_version(),
                            getDocumentModels.getData().getDocument_count().get(i).getCount());
                }
            }
        }else{
            digital_version.clear();
        }
    }
    }


