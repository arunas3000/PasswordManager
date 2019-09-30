package com.company.arunas.passwordmanager.activities;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.company.arunas.passwordmanager.helpers.InputValidation;
import com.company.arunas.passwordmanager.model.Password;

import com.company.arunas.passwordmanager.sql.DatabaseHelper;
import com.company.arunas.passwordmanager.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class PasswordAddActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = PasswordAddActivity.this;

    private NestedScrollView nestedScrollView2;

    private TextInputLayout textInputLayoutFor;
    private TextInputLayout textInputLayoutUser;
    private TextInputLayout textInputLayoutPass;


    private TextInputEditText textInputEditTextFor;
    private TextInputEditText textInputEditTextUser;
    private TextInputEditText textInputEditTextPass;


    private AppCompatButton appCompatButtonAdd;
    private AppCompatButton appCompatButtonBack;


    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private Password pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_add);
        getSupportActionBar().hide();

        initViews();

        initListeners();


        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView2 = findViewById(R.id.nestedScrollView2);

        textInputLayoutFor = findViewById(R.id.textInputLayoutFor);
        textInputLayoutUser = findViewById(R.id.textInputLayoutUser);
        textInputLayoutPass = findViewById(R.id.textInputLayoutPass);

        textInputEditTextFor =  findViewById(R.id.textInputEditTextFor);
        textInputEditTextUser =  findViewById(R.id.textInputEditTextUser);
        textInputEditTextPass =  findViewById(R.id.textInputEditTextPass);

        appCompatButtonAdd = findViewById(R.id.appCompatButtonAdd);
        appCompatButtonBack = findViewById(R.id.appCompatButtonBack);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonAdd.setOnClickListener(this);
        appCompatButtonBack.setOnClickListener(this);

    }


    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.appCompatButtonAdd:
                postDataToSQLite();
                break;

            case R.id.appCompatButtonBack:
                Intent backIntent = new Intent(PasswordAddActivity.this, PasswordActivity.class);
                startActivity(backIntent);
                break;
        }
    }
    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextFor, textInputLayoutFor, getString(R.string.error_message_for))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextUser, textInputLayoutUser, getString(R.string.error_message_username))) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(textInputEditTextPass, textInputLayoutPass, getString(R.string.error_message_pass))) {
            return;
        }


        if (!databaseHelper.checkAccount(textInputEditTextFor.getText().toString().trim())) {

            String addPasswordFor = textInputEditTextFor.getText().toString().trim();
            String addUsername = textInputEditTextUser.getText().toString().trim();
            String addPassword = textInputEditTextPass.getText().toString().trim();

            pass = new Password(addPasswordFor, addUsername, addPassword);

            databaseHelper.addPassword(pass);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView2, getString(R.string.success_message_add), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView2, getString(R.string.error_account_exists), Snackbar.LENGTH_LONG).show();
        }


    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextFor.setText(null);
        textInputEditTextUser.setText(null);
        textInputEditTextPass.setText(null);

    }

}