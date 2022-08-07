package com.example.myapplication.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Other.SignInActivity;
import com.example.myapplication.R;
import com.example.myapplication.Seller.SellerActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    String Name1;
    String Phone;
    View v;
    EditText etName;
    private DatabaseReference aFirebaseDatabase;
    NameInterface nameInterface;
    SharedPreferences sharedPreferences;
    public static final String filename = "login";
    public static final String name = "Abid";
    public static final String userName = "username";
    public static final String password = "password";
    BottomSheetDialog(NameInterface nameInterface)
    {
        this.nameInterface =nameInterface;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.bootm_sheet,
                container, false);
        sharedPreferences = v.getContext().getSharedPreferences(filename, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(userName)){
            Name1 =sharedPreferences.getString(name,"");
            Phone = sharedPreferences.getString(userName,"");
        }
        aFirebaseDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-b93ab-default-rtdb.firebaseio.com/");
        Button btnsave = v.findViewById(R.id.Save);
        ImageButton btncancel = v.findViewById(R.id.cancelsheet);
        etName = v.findViewById(R.id.Name_change);
        etName.setText(Name1);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String n = etName.getText().toString();
                //Toast.makeText(v.getContext(), "="+n, Toast.LENGTH_SHORT).show();
                update(n);
                dismiss();
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return v;
    }

    private void update(String Name) {

        aFirebaseDatabase.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(Phone)) {
                    dataSnapshot.child(Phone).child("username").getRef().setValue(Name);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(name,Name);
                    editor.commit();
                    Name1 = Name;
                    nameInterface.onclick(Name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
