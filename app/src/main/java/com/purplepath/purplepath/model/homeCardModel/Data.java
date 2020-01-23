package com.purplepath.purplepath.model.homeCardModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pratheep.S on 19-09-2017.
 */

public class Data implements Serializable{

    private String message;

    private Card1 card1;

    private Card0 card0;

    private ArrayList<Card2> card2;

    private ArrayList<Card4>  card4;

    public ArrayList<Card2> getCard2() {
        return card2;
    }

    public void setCard2(ArrayList<Card2> card2) {
        this.card2 = card2;
    }

    public ArrayList<Card4> getCard4() {
        return card4;
    }

    public void setCard4(ArrayList<Card4> card4) {
        this.card4 = card4;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Card1 getCard1 ()
    {
        return card1;
    }

    public void setCard1 (Card1 card1)
    {
        this.card1 = card1;
    }

    public Card0 getCard0 ()
    {
        return card0;
    }

    public void setCard0 (Card0 card0)
    {
        this.card0 = card0;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", card1 = "+card1+", card0 = "+card0+", card2 = "+card2+", card4 = "+card4+"]";
    }
}

