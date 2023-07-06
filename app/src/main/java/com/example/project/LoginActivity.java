package com.example.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
EditText email,password;

Button login;
TextView forgotlink;
FirebaseAuth mAuth;
DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://myotp-b736f-default-rtdb.firebaseio.com/");
Md5 md5;
    @SuppressLint("MissingInflatedId")



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.pass);
        login = (Button)findViewById(R.id.login);
        forgotlink = (TextView) findViewById(R.id.forgotpass);
        mAuth = FirebaseAuth.getInstance();
        md5 = new Md5(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_usr = email.getText().toString();
                String pass = password.getText().toString();
                String encpass = md5.getMd5(pass);
                if(email_usr.equals("")||pass.equals(""))
                {
                    Toast.makeText(LoginActivity.this,"Enter the empty Fields",Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.signInWithEmailAndPassword(email_usr, encpass )
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if(user.isEmailVerified()) {
                                            Toast.makeText(LoginActivity.this, "Successfully Logged In ", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(getApplicationContext(), email_usr.contains("+ven") ? HomeActivity.class : MapsActivity.class);
                                            startActivity(intent);
                                        }
                                        else {
                                            Toast.makeText(LoginActivity.this, "Email is Not Verified ", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(intent);
                                        }

                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
            }
        });
    forgotlink.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(),forgotPassOTP.class);
            startActivity(intent);
        }

    });

    }
}