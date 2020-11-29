package com.lorenzana.hackafest;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView;
    private EditText editTextEmail, editTextPwd;
    private Button Login;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login = (Button) findViewById(R.id.button_SignIn);
        Login.setOnClickListener(this);

        textView = (TextView) findViewById(R.id.Sign_Up);
        textView.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.etEmail);
        editTextPwd = (EditText) findViewById(R.id.etPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();

    }


    private void setupFloatingLabelError() {
        final TextInputLayout floatingUsernameLabel = (TextInputLayout) findViewById(R.id.username_text_input_layout);
        floatingUsernameLabel.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() > 0 && text.length() <= 4) {
                    floatingUsernameLabel.setError(getString(R.string.username_required));
                    floatingUsernameLabel.setErrorEnabled(true);
                } else {
                    floatingUsernameLabel.setErrorEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Sign_Up:
                startActivity(new Intent(this, CreateAccountActivity.class));
                break;
            case R.id.button_SignIn:
                Login();
                break;
        }

    }

    private void Login() {

        String email = editTextEmail.getText().toString().trim();
        String pwd = editTextPwd.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email.");
            editTextEmail.requestFocus();
            return;
        }
        if (pwd.isEmpty()) {
            editTextPwd.setError("Password is required!");
            editTextPwd.requestFocus();
            return;
        }
        if (pwd.length() < 6) {
            editTextPwd.setError("Password should be more than 6 characters.");
            editTextPwd.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()) {

                        //redirect to MainActivity

                        startActivity(new Intent(LoginActivity.this, MainActivityUser.class));
                        finish();

                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Check your email for verification.", Toast.LENGTH_LONG).show();
                    }


                } else {

                    Toast.makeText(LoginActivity.this, "Failed to login. Please check your credentials.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
