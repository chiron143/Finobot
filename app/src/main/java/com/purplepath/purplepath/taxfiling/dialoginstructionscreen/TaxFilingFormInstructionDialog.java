package com.purplepath.purplepath.taxfiling.dialoginstructionscreen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxfiling.dialogistructioncsreenadapter.TaxFilingFormABadapter;
import com.purplepath.purplepath.taxfiling.getdialogforminstruction.Samples;
import com.purplepath.purplepath.taxfiling.getdialogforminstruction.TaxFileFormInstructionModel;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

import static com.purplepath.purplepath.retrofitservice.ServiceGenerator.download_file_url;


/**
 * Created by pravinr on 7/3/18.
 */

public class TaxFilingFormInstructionDialog extends DialogFragment implements View.OnClickListener{


    private Context mContext;

    private ListView listview_form16ab;

    public Uri uri;

    private FloatingActionButton fab_declaration;

    TaxFilingFormABadapter taxFilingFormABadapter;

    private ArrayList<Samples> arrayListResult=new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_taxfile_forminstructiondialog, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.white);

        listview_form16ab=(ListView)view.findViewById(R.id.listview_form16ab);

        fab_declaration=(FloatingActionButton)view.findViewById(R.id.fab_declaration);
        fab_declaration.setOnClickListener(this);


        callGetInstructionFormService();



        return view;
    }


    public void callGetInstructionFormService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);

            Call<TaxFileFormInstructionModel> call = webServiceObj.callGetInstructionFormService(UtileKit.getPersistedPurplePathPref("user_id"));
            call.enqueue(new retrofit2.Callback<TaxFileFormInstructionModel>() {
                @Override
                public void onResponse(Call<TaxFileFormInstructionModel> call, Response<TaxFileFormInstructionModel> response) {
                    UtileKit.dismisssSpinnerDialog();
                    TaxFileFormInstructionModel getChecklistModel = response.body();
                    try {
                        if (getChecklistModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                            if (null != getChecklistModel.getData().getSamples()) {

                                arrayListResult.addAll(getChecklistModel.getData().getSamples());
                                taxFilingFormABadapter = new TaxFilingFormABadapter(mContext, arrayListResult);
                                listview_form16ab.setAdapter(taxFilingFormABadapter);
                                taxFilingFormABadapter.notifyDataSetChanged();



                                listview_form16ab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        try {
                                            uri = Uri.parse(arrayListResult.get(i).getUrl().trim());
                                            File file = new File(String.valueOf(uri));

                                            Intent intent = new Intent(Intent.ACTION_VIEW);
                                            if(file.toString().contains(".jpg")|| file.toString().contains(".jpeg") || file.toString().contains(".png"))
                                            {
                                                intent.setDataAndType(uri, "image/jpeg");
                                            }
                                            else if (file.toString().contains(".pdf")) {
                                                Log.i("inside"," pdf section "+file.toString());

                                                intent.setDataAndType(uri, "application/pdf");
                                            }
                                            else if (file.toString().contains(".ppt") || file.toString().contains(".pptx")) {
                                                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
                                            }
                                            else if (file.toString().contains(".xls") || file.toString().contains(".xlsx")) {
                                                intent.setDataAndType(uri, "application/vnd.ms-excel");
                                            }
                                            else if (file.toString().contains(".gif")) {
                                                intent.setDataAndType(uri, "image/gif");
                                            }
                                            startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<TaxFileFormInstructionModel> call, Throwable t) {
                    UtileKit.dismisssSpinnerDialog();
                    UtileKit.alertRetrofitExceptionDialog(mContext, t);
                }
            });
        }


    @Override
    public void onResume() {
        super.onResume();
        int height=getResources().getDisplayMetrics().heightPixels;
        int width=getResources().getDisplayMetrics().widthPixels;
        getDialog().getWindow().setLayout((int)(width*.90),(int)(height*.95));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.fab_declaration:

                dismiss();

                break;
        }
    }

}
