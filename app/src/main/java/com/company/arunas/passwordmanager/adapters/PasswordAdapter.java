package com.company.arunas.passwordmanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.company.arunas.passwordmanager.R;
import com.company.arunas.passwordmanager.model.Password;

import java.util.ArrayList;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder> {
    private ArrayList<Password> mPasswordList;

    //Pass array list of type Password into adapter and assign member variable to this array list
    public PasswordAdapter(ArrayList<Password> passwordsList) {
        mPasswordList = passwordsList;
    }
    @NonNull
    @Override
    public PasswordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       //Pass in the cardView layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.password_item, parent, false);

        //Now create instance PasswordViewHolder class and pass in newly created view
        PasswordViewHolder pvh = new PasswordViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordViewHolder holder, int position) {

        //Retrieve the member variables from the password class with getter methods
        Password currentItem = mPasswordList.get(position);
        holder.mLabelFor.setText(currentItem.getLabelFor());
        holder.mPassword.setText(currentItem.getLabelPassword());
        holder.mUsername.setText(currentItem.getLabelUsername());
    }



    @Override
    public int getItemCount() {
        //Define how many items there will be in our list
        //For that we count the items in our array list
        return mPasswordList.size();
    }

    //For search functionality
    public void filterList(ArrayList<Password> filteredList) {
        mPasswordList = filteredList;
        notifyDataSetChanged();
    }

    public class PasswordViewHolder extends RecyclerView.ViewHolder {

        //Create member variables that would hold our items
        public AppCompatTextView mLabelFor;
        public AppCompatTextView mPassword;
        public AppCompatTextView mUsername;

        //Constructor that passes in the passwordItem object and assigns member variables
        //Views are from password item XML
        public PasswordViewHolder(View passwordItem){
            super(passwordItem);
            mLabelFor = passwordItem.findViewById(R.id.passwordFor);
            mPassword = passwordItem.findViewById(R.id.password);
            mUsername = passwordItem.findViewById(R.id.username);
        }
    }
}
