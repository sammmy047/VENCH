package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                    mAuth.signInWithEmailAndPassword(email_usr, encpass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if(user.isEmailVerified()) {
                                            Toast.makeText(LoginActivity.this, "Successfully Logged In ", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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
                    /*databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if username exists
                            if(snapshot.hasChild(user)){
                                // user exists in firebase we authenticate the password
                                final String getpass = snapshot.child(user).child("Password").getValue(String.class);
                                if(getpass.equals(encpass)){
                                    Toast.makeText(LoginActivity.this,"Successfully Logged In ",Toast.LENGTH_SHORT).show();
                                    Intent intent  = new Intent(getApplicationContext(), OTP.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(LoginActivity.this,"Invalid Password ",Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });*/

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