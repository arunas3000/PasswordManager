package com.company.arunas.passwordmanager.model;

public class Password {
    private String labelFor;
    private String labelUsername;
    private String labelPassword;

    public Password(String labelFor, String labelPassword, String labelUsername){
        this.labelFor = labelFor;
        this.labelUsername = labelUsername;
        this.labelPassword = labelPassword;
    }

    public String getLabelFor() {
        return labelFor;
    }


    public String getLabelUsername() {
        return labelUsername;
    }


    public String getLabelPassword() {
        return labelPassword;
    }

}
