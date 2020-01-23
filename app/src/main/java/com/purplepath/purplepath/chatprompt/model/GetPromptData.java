package com.purplepath.purplepath.chatprompt.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 12/13/17.
 */

public class GetPromptData implements Serializable {
    private String message;

    private ArrayList<Prompt_statements> prompt_statements;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }
    public ArrayList<Prompt_statements> getPrompt_statements() {
        return prompt_statements;
    }

    public void setPrompt_statements(ArrayList<Prompt_statements> prompt_statements) {
        this.prompt_statements = prompt_statements;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", prompt_statements = "+prompt_statements+"]";
    }
}

