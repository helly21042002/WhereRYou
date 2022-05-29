package com.example.whereryou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import static android.content.ContentValues.TAG;

public class Login extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mLoginBtn;
    ProgressBar progressBar;
    FirebaseAuth mAuth;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.signinButton);


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                // authenticate the user
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();


                            Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            if (task.getException().getMessage() == "There is no user record corresponding to this identifier. The user may have been deleted.")
                            {
                                signup(email,password);

                            }
                            else{
                                Toast.makeText(Login.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });




            }

            private void signup(String email, String password) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();


                                    ParseUser User = new ParseUser();
                                    User.setUsername(email);
                                    User.setPassword(password);
                                    User.signUpInBackground(new SignUpCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e==null){
                                                Toast.makeText(getApplicationContext(),"Sign Up Successful, User Updated",Toast.LENGTH_LONG).show();
                                            }else {
                                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                    Log.d(TAG, "createUserWithEmail:success");
                                    updateUI();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

            }

            private void updateUI() {
                Toast.makeText(this, "Signed up Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        }







