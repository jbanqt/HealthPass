package com.lorenzana.hackafest;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class header extends AppCompatActivity {

    private FirebaseDatabase rootNode;
    private FirebaseUser firebaseUserr;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header);

        FirebaseUser firebaseUserr = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = firebaseUserr.getUid();

        final TextView usernameGreeting = (TextView) findViewById(R.id.current_user);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User2 userProfile = snapshot.getValue(User2.class);

                if(userProfile != null){
                    String username = userProfile.username;

                    usernameGreeting.setText(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(header.this, "Something wrong happened!", Toast.LENGTH_LONG).show();

            }
        });


    }}
