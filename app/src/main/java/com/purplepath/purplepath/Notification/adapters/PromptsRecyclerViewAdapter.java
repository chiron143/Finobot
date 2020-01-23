package com.purplepath.purplepath.Notification.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.purplepath.purplepath.Notification.Models.PromptsModel;
import com.finobot.finobot.R;
import com.purplepath.purplepath.alertprompt.personalprompt.model.dialog.PromptSugestionDialog;
import com.purplepath.purplepath.apputiles.UtileKit;

import java.util.ArrayList;

/**
 * Created by Pratheep.S on 21-01-2017.
 */

public class PromptsRecyclerViewAdapter extends RecyclerView.Adapter<PromptsRecyclerViewAdapter.ViewHolder> {
    public static PromptsModel promptsModel;
    public static FragmentManager fragmentManager;
    public static String missingDetails[];
    public static Context context;

    public PromptsRecyclerViewAdapter(Context context, PromptsModel promptsModel, String missingDetails[], FragmentManager mFragmentManager) {
        PromptsRecyclerViewAdapter.promptsModel = promptsModel;
        PromptsRecyclerViewAdapter.missingDetails = missingDetails;
        fragmentManager = mFragmentManager;
        PromptsRecyclerViewAdapter.context = context;


    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_prompts);
            imageView = itemView.findViewById(R.id.iv_prompts);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            String pageTitle, pageId;
            ArrayList<String> emptylist;
            PromptSugestionDialog newFragment;
            switch (getAdapterPosition()) {
                case 0:
                    if (promptsModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                        if (promptsModel.getData().getPersonal_fields() != null) {
                            emptylist = promptsModel.getData().getPersonal_fields().getEmpty_fields();
                            pageTitle = "Personal Details";
                            pageId = promptsModel.getData().getPersonal_fields().getPage_id();
                            //Intent i = new Intent(view.getContext(), HomePageActivity.class);
                            //startActivityForResult(view.getParent().this,i,0,null)
                            newFragment = PromptSugestionDialog.newInstance(emptylist, pageTitle, Integer.parseInt(pageId));
                            newFragment.show(fragmentManager, "dialog");
                        } else if (missingDetails[0].equalsIgnoreCase("Family Details") && promptsModel.getData().getFamily_fields() != null) {
                            if (promptsModel.getData().getFamily_fields().getEmpty_fields() != null) {
                                emptylist = promptsModel.getData().getFamily_fields().getEmpty_fields();
                                pageTitle = "Family Details";
                                pageId = promptsModel.getData().getFamily_fields().getPage_id();
                                newFragment = PromptSugestionDialog.newInstance(emptylist, pageTitle, Integer.parseInt(pageId));
                                newFragment.show(fragmentManager, "dialog");
                            } else {
                                UtileKit.showToastShort(context, context.getString(R.string.noInfoMsg));
                            }

                        } else {
                            UtileKit.showToastShort(context, context.getString(R.string.noInfoMsg));
                        }
                    } else {
                        UtileKit.showToastShort(context, context.getString(R.string.noInfoTempMsg));
                    }


                    break;
                case 1:
                    if (promptsModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                        if (promptsModel.getData().getFamily_fields() != null) {
                            if (promptsModel.getData().getFamily_fields().getEmpty_fields() != null) {
                                emptylist = promptsModel.getData().getFamily_fields().getEmpty_fields();
                                pageTitle = "Family Details";
                                pageId = promptsModel.getData().getFamily_fields().getPage_id();
                                newFragment = PromptSugestionDialog.newInstance(emptylist, pageTitle, Integer.parseInt(pageId));
                                newFragment.show(fragmentManager, "dialog");
                            }
                        } else {
                            UtileKit.showToastShort(context, context.getString(R.string.noInfoMsg));
                        }
                    } else {
                        UtileKit.showToastShort(context, context.getString(R.string.noInfoTempMsg));
                    }
                    break;
                case 2:
                    // makeText(view.getContext(),"position 2",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    // makeText(view.getContext(),"position 3",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public PromptsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prompts_single_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PromptsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.textView.setText(missingDetails[position]);


    }

    @Override
    public int getItemCount() {
        return missingDetails.length;
    }
}
