package com.purplepath.purplepath.assets.model;

import java.io.Serializable;

/**
 * Created by bertrandrussellsakthees on 25/10/16.
 */

public class DeleteAssetData implements Serializable{

        private String message;

        private DeleteAssetDetails input;

        public String getMessage ()
        {
            return message;
        }

        public void setMessage (String message)
        {
            this.message = message;
        }

        public DeleteAssetDetails getInput ()
        {
            return input;
        }

        public void setInput (DeleteAssetDetails input)
        {
            this.input = input;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [message = "+message+", input = "+input+"]";
        }
    }
