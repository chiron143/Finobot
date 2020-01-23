package com.purplepath.purplepath.AppManagement.Quiz.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.AppManagement.Quiz.topicModels.GetQuizTopics;

/**
 * Created by bertrandrussellsakthees on 05/09/17.
 */

public class CustomQuizListTopicAdapter  extends BaseAdapter {

    private Context mContext;

    private GetQuizTopics arrayGetQuizTopics;

    public CustomQuizListTopicAdapter(GetQuizTopics mGetQuizTopics, Context mContext) {
        this.arrayGetQuizTopics = mGetQuizTopics;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arrayGetQuizTopics.getData().getQuiz_topics().size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.quiztopicadapter, null);
            TextView txt_topic = grid.findViewById(R.id.txt_topic);
            if (arrayGetQuizTopics.getData().getQuiz_topics().get(position).getQuiz_topic()!= null){
                txt_topic.setText(arrayGetQuizTopics.getData().getQuiz_topics().get(position).getQuiz_topic());
            }




        } else {
            grid = convertView;
        }

        return grid;
    }
}
