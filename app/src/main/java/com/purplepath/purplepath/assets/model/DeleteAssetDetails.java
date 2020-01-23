package com.purplepath.purplepath.assets.model;

/**
 * Created by bertrandrussellsakthees on 25/10/16.
 */

public class DeleteAssetDetails {

        private String asst_id;

        private String user_id;

        public String getAsst_id ()
        {
            return asst_id;
        }

        public void setAsst_id (String asst_id)
        {
            this.asst_id = asst_id;
        }

        public String getUser_id ()
        {
            return user_id;
        }

        public void setUser_id (String user_id)
        {
            this.user_id = user_id;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [asst_id = "+asst_id+", user_id = "+user_id+"]";
        }
    }
