package com.purplepath.purplepath.document.Interface;

import android.view.View;

/**
 * Created by bertrandrussellsakthees on 30/06/17.
 */

public interface OnItemClickListenerInterface {
      void onClick(View view, int position);
      void onClick(View view, int position, String name, String mvalue);
}
