package com.abcx.fbev;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activitySignup extends AppCompatActivity {
    TextView textView;
    Button btnSignup;
    EditText suEmai, suPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        textView = findViewById(R.id.textView);
        btnSignup = findViewById(R.id.btnSignUp);
        suEmai = findViewById(R.id.suEmail);
        suPassword = findViewById(R.id.suPassword);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activitySignup.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = suEmai.getText().toString();
                String Password = suPassword.getText().toString();

                createUsers(Email,Password);
            }
        });
    }

    private void createUsers(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("activitySignUp", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(activitySignup.this, "Email Verification sent", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(activitySignup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("activitySignUp", "createUserWithEmail:failure", task.getException());
                            //Toast.makeText(this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            Log.d("activitySignUp", "onComplete: " + task.getException().getMessage());
                            // Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user==null){
            Toast.makeText(this, "SignUp Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,user.getEmail(),Toast.LENGTH_SHORT).show();

        }
        }


}
