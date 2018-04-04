package com.muktadir.pregnancycare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {
    Button signInButton,signUpButton;
    MaterialEditText edtUserPassword,edtUserEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() !=null){
            startActivity(new Intent(SignIn.this,Home.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signInButton = findViewById(R.id.btnSignIn);
        signUpButton = findViewById(R.id.btnSignUpNew);
        edtUserEmail = findViewById(R.id.edtUserEmail);
        edtUserPassword = findViewById(R.id.edtUserPassword);

        mAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this,SignUp.class);
                startActivity(intent);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = edtUserEmail.getText().toString().trim();
                String newPassword = edtUserPassword.getText().toString().trim();


                if (newEmail.isEmpty()){
                    edtUserEmail.setError("Email Required");
                    edtUserEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()){
                    edtUserEmail.setError("Valid Email Required");
                    edtUserEmail.requestFocus();
                    return;
                }

                if (newPassword.isEmpty()){
                    edtUserPassword.setError("Password Required");
                    edtUserPassword.requestFocus();
                    return;
                }

                if (newPassword.length()<6){
                    edtUserPassword.setError("At least 6 character");
                    edtUserPassword.requestFocus();
                    return;
                }

                // progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(newEmail,newPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            Intent i = new Intent(SignIn.this,Home.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }
}
