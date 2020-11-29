package com.lorenzana.hackafest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
private TextView textView;
private FirebaseAuth mAuth;
private Button CreateAccount;
private EditText editTextUsername, editTextEmail, editTextPwd;
private ProgressBar progressBar;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //Firebase

        mAuth = FirebaseAuth.getInstance();

        CreateAccount = (Button) findViewById(R.id.button_SignUp);
        CreateAccount.setOnClickListener(this);

        //Initialize variables
        editTextUsername = (EditText) findViewById(R.id.etUsername);
        editTextEmail = (EditText) findViewById(R.id.etEmail);
        editTextPwd = (EditText) findViewById(R.id.etPassword);


        //Redirect to Login page
        textView = (TextView) findViewById(R.id.Sign_In);
        textView.setOnClickListener(this);
        }


@Override
public void onClick(View v) {
        switch (v.getId()){
        case R.id.Sign_In:
        startActivity(new Intent(this, LoginActivity.class));
        finish();
        break;
        case R.id.button_SignUp:
        createAccount();
        break;


        }

        }

private void createAccount() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String pwd = editTextPwd.getText().toString().trim();

        if(username.isEmpty()){
        editTextUsername.setError("Username is required!");
        editTextUsername.requestFocus();
        return;
        }
        if(email.isEmpty()){
        editTextEmail.setError("Email is required!");
        editTextEmail.requestFocus();
        return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        editTextEmail.setError("Please provide a valid email.");
        editTextEmail.requestFocus();
        return;
        }

        if(pwd.isEmpty()){
        editTextPwd.setError("Password is required!");
        editTextPwd.requestFocus();
        return;
        }

        if(pwd.length() < 6){
        editTextPwd.setError("Password should be more than 6 characters.");
        editTextPwd.requestFocus();
        return;
        }


        mAuth.createUserWithEmailAndPassword(email, pwd)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
@Override
public void onComplete(@NonNull Task<AuthResult> task) {

        if(task.isSuccessful()){
        User2 users = new User2(username);


        FirebaseDatabase.getInstance().getReference("Users")
        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
        .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
@Override
public void onComplete(@NonNull Task<Void> task) {

        if(task.isSuccessful()){
        Toast.makeText(CreateAccountActivity.this, "Account created successfully!", Toast.LENGTH_LONG).show();

        //redirect to LoginActivity
        startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
        finish();

        }else {
        Toast.makeText(CreateAccountActivity.this, "Failed to create an account.", Toast.LENGTH_LONG).show();
        }
        }
        });

        }
        }
        });


        }
        }