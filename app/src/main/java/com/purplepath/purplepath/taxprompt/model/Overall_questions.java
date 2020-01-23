package com.purplepath.purplepath.taxprompt.model;

import java.io.Serializable;

/**
 * Created by pravinr on 12/22/17.
 */

public class Overall_questions implements Serializable {
    private String field_name;

    private String input_type;

    private String user_visited_flag;

    private String type;

    private String updated_flag;

    private String ques_id;

    private String statement;

    private String encrypt_flag;

    private String yes_ques_id;

    private String created_datetime;

    private String validation;

    private String validation_length;

    private String validation_pattern;

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getValidation_length() {
        return validation_length;
    }

    public void setValidation_length(String validation_length) {
        this.validation_length = validation_length;
    }

    public String getValidation_pattern() {
        return validation_pattern;
    }

    public void setValidation_pattern(String validation_pattern) {
        this.validation_pattern = validation_pattern;
    }

    public String getQues_id() {
        return ques_id;
    }

    public void setQues_id(String ques_id) {
        this.ques_id = ques_id;
    }

    private String in_use;

    private String field_answer;

    private String spinner_array;

    private String modified_datetime;

    public String getYes_ques_ans() {
        return yes_ques_ans;
    }

    public void setYes_ques_ans(String yes_ques_ans) {
        this.yes_ques_ans = yes_ques_ans;
    }

    public String getNo_ques_ans() {
        return no_ques_ans;
    }

    public void setNo_ques_ans(String no_ques_ans) {
        this.no_ques_ans = no_ques_ans;
    }

    private String boolean_flag;

    private String corresponding_table;

    private String no_ques_id;

    private String yes_ques_ans;

    private String no_ques_ans;

    public String getField_name ()
    {
        return field_name;
    }

    public void setField_name (String field_name)
    {
        this.field_name = field_name;
    }

    public String getInput_type ()
    {
        return input_type;
    }

    public void setInput_type (String input_type)
    {
        this.input_type = input_type;
    }

    public String getUser_visited_flag ()
    {
        return user_visited_flag;
    }

    public void setUser_visited_flag (String user_visited_flag)
    {
        this.user_visited_flag = user_visited_flag;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getUpdated_flag ()
    {
        return updated_flag;
    }

    public void setUpdated_flag (String updated_flag)
    {
        this.updated_flag = updated_flag;
    }

//    public String getId ()
//    {
//        return id;
//    }
//
//    public void setId (String id)
//    {
//        this.id = id;
//    }

    public String getStatement ()
    {
        return statement;
    }

    public void setStatement (String statement)
    {
        this.statement = statement;
    }

    public String getEncrypt_flag ()
    {
        return encrypt_flag;
    }

    public void setEncrypt_flag (String encrypt_flag)
    {
        this.encrypt_flag = encrypt_flag;
    }

    public String getYes_ques_id ()
    {
        return yes_ques_id;
    }

    public void setYes_ques_id (String yes_ques_id)
    {
        this.yes_ques_id = yes_ques_id;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    public String getIn_use ()
    {
        return in_use;
    }

    public void setIn_use (String in_use)
    {
        this.in_use = in_use;
    }

    public String getField_answer ()
    {
        return field_answer;
    }

    public void setField_answer (String field_answer)
    {
        this.field_answer = field_answer;
    }

    public String getSpinner_array ()
    {
        return spinner_array;
    }

    public void setSpinner_array (String spinner_array)
    {
        this.spinner_array = spinner_array;
    }

    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
    }

    public String getBoolean_flag ()
    {
        return boolean_flag;
    }

    public void setBoolean_flag (String boolean_flag)
    {
        this.boolean_flag = boolean_flag;
    }

    public String getCorresponding_table ()
    {
        return corresponding_table;
    }

    public void setCorresponding_table (String corresponding_table)
    {
        this.corresponding_table = corresponding_table;
    }

    public String getNo_ques_id ()
    {
        return no_ques_id;
    }

    public void setNo_ques_id (String no_ques_id)
    {
        this.no_ques_id = no_ques_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [field_name = "+field_name+", input_type = "+input_type+", user_visited_flag = "+user_visited_flag+", type = "+type+", updated_flag = "+updated_flag+", id = "+ques_id+", statement = "+statement+", encrypt_flag = "+encrypt_flag+", yes_ques_id = "+yes_ques_id+", created_datetime = "+created_datetime+", in_use = "+in_use+", field_answer = "+field_answer+", spinner_array = "+spinner_array+", modified_datetime = "+modified_datetime+", boolean_flag = "+boolean_flag+", corresponding_table = "+corresponding_table+", no_ques_id = "+no_ques_id+"]";
    }
}

