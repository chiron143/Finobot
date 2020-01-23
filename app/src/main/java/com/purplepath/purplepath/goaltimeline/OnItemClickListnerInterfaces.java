package com.purplepath.purplepath.goaltimeline;

import android.view.View;

/**
 * Created by pravinr on 11/30/17.
 */

public interface OnItemClickListnerInterfaces {
    void onClick(View view, int position);
    void onClick(String id, String name, String mvalue, String expectedincrement);
}
