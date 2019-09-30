package com.company.arunas.passwordmanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.company.arunas.passwordmanager.R;
import com.company.arunas.passwordmanager.adapters.PasswordAdapter;
import com.company.arunas.passwordmanager.model.Password;
import com.company.arunas.passwordmanager.sql.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class PasswordActivity extends AppCompatActivity {

    private ArrayList<Password> passwordList;

    private RecyclerView mRecyclerView;
    private PasswordAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AppCompatButton appCompatButtonAddPass;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        //Add password button
        appCompatButtonAddPass = findViewById(R.id.appCompatButtonAddPass);

        //Add password on click listener
        appCompatButtonAddPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddPass = new Intent(PasswordActivity.this, PasswordAddActivity.class);
                startActivity(intentAddPass);
            }
        });


        //Methods for search functionality
        TextInputEditText searchPassword = findViewById(R.id.search_password);
        searchPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }

        });


        databaseHelper = new DatabaseHelper(PasswordActivity.this);

        //Retrieve all passwords from the database and add member variables to the list
        passwordList = databaseHelper.getAllPasswords();

        /*
        Hardcoded passwords
        Password password1 = new Password("Gmail Account nr1", "password1", "username1");
        Password password2 = new Password("Gmail Account nr2", "password2", "username2");
        Password password3 = new Password("Gmail Account nr3", "password3", "username3");
         passwordList.add(password1);
        passwordList.add(password2);
        passwordList.add(password3);
        */

        mRecyclerView = findViewById(R.id.passwordView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        //Pass in a newly created array list into adapter object
        mAdapter = new PasswordAdapter(passwordList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);



    }

    //Check if array list contains item, if it does add it to a new array list
    private void filter(String text) {
        ArrayList<Password> filteredPasswords = new ArrayList<>();

        for(Password passwordItem : passwordList) {
            if(passwordItem.getLabelFor().toLowerCase().contains(text.toLowerCase())) {
                filteredPasswords.add(passwordItem);
            }
        }

        //Pass the list to adapter
        mAdapter.filterList(filteredPasswords);
    }

}
