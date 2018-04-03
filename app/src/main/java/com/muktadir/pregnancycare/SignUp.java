package com.muktadir.pregnancycare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {

    MaterialEditText edtNewEmail,edtNewPassword;
    Button btnCreateAccount,btnSignIn;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() !=null){
            startActivity(new Intent(SignUp.this,Home.class));
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtNewEmail = findViewById(R.id.edtNewUserEmail);
        edtNewPassword = findViewById(R.id.edtNewUserPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnSignIn = findViewById(R.id.btnSignInOld);

        //Firebase Initilization

        mAuth = FirebaseAuth.getInstance();

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = edtNewEmail.getText().toString().trim();
                String newPassword = edtNewPassword.getText().toString().trim();


                if (newEmail.isEmpty()){
                    edtNewEmail.setError("Email Required");
                    edtNewEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()){
                    edtNewEmail.setError("Valid Email Required");
                    edtNewEmail.requestFocus();
                    return;
                }

                if (newPassword.isEmpty()){
                    edtNewPassword.setError("Password Required");
                    edtNewPassword.requestFocus();
                    return;
                }

                if (newPassword.length()<6){
                    edtNewPassword.setError("At least 6 character");
                    edtNewPassword.requestFocus();
                    return;
                }



                mAuth.createUserWithEmailAndPassword(newEmail,newPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            finish();
                            Toast.makeText(SignUp.this,"User Registered Successful",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignUp.this,Home.class);
                            startActivity(i);
                        }
                        else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(SignUp.this,"User Already Registered",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }
}
