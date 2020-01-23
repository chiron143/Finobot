package com.purplepath.purplepath.AppManagement.Knowledge.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.purplepath.purplepath.AppManagement.Knowledge.model.Knowledge;
import com.purplepath.purplepath.AppManagement.Knowledge.model.Knowledgemodel;
import com.finobot.finobot.R;

import java.util.ArrayList;

import static com.purplepath.purplepath.retrofitservice.ServiceGenerator.service_base_url;

/**
 * Created by pravinr on 8/5/17.
 */

public class KnowledgeAdapter extends RecyclerView.Adapter<KnowledgeAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<Knowledge> knowledge;
    Knowledgemodel knowledgemodels;


    public KnowledgeAdapter(Context mContext, ArrayList<Knowledge> knowledge, Knowledgemodel knowledgemodel) {
        this.mContext = mContext;
        this.knowledge=knowledge;
        this.knowledgemodels=knowledgemodel;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public KnowledgeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_knowledge_item, parent, false);
        return new KnowledgeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final KnowledgeAdapter.ViewHolder holder, int position) {


      //  holder.mtitle.setText(knowledge.get(position).getTopic());

        holder.webView.setVerticalScrollBarEnabled(true);
        holder.webView.setHorizontalScrollBarEnabled(true);

        holder.webView_title.loadUrl(knowledge.get(position).getTopic());

        if(null!=service_base_url+knowledge.get(position).getWeb_url()) {
            holder.webView.loadUrl(service_base_url.concat(knowledge.get(position).getWeb_url()));
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return knowledge.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
       // public final TextView mtitle;
        public final WebView webView,webView_title;

        public ViewHolder(View view) {
            super(view);
            mView = view;

           // mtitle = (TextView) view.findViewById(R.id.title);

            webView_title= view.findViewById(R.id.webView_knowledge);
            webView = view.findViewById(R.id.webView_knowledge);
        }
    }
}
