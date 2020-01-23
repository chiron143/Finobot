package com.purplepath.purplepath.AppManagement.FAQ.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 5/17/17.
 */

public class Faqdata implements Serializable {
    private String message;

    //private FAQ[] FAQ;

    public ArrayList<FAQ> FAQ;



    public ArrayList<FAQ> getFaq() {
        return FAQ;
    }

    public void setFaq(ArrayList<FAQ> faq) {
        this.FAQ = faq;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

   /* public FAQ[] getFAQ () {
        return FAQ;
    }
        public void setFAQ (FAQ[] FAQ)
    {
        this.FAQ = FAQ;
    }*/

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", FAQ = "+FAQ+"]";
    }
}
