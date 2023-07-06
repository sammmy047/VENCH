package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgotPassword extends AppCompatActivity {
EditText password,repassword,username;
Md5 md5;
Button reset;
UserDetails userDetails;
FirebaseAuth mAuth;

DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://myotp-b736f-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        password = (EditText) findViewById(R.id.editpassword);
        repassword = (EditText) findViewById(R.id.editrepassword);
        username = (EditText)findViewById(R.id.uname);
        reset = (Button)findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = password.getText().toString();
                String user = username.getText().toString();
                String repass = repassword.getText().toString();
                String encpass = md5.getMd5(pass);
                String re_encpass = md5.getMd5(repass);
                if (user.equals("") || repass.equals("") || pass.equals("")) {
                    Toast.makeText(ForgotPassword.this, "Enter the empty Fields", Toast.LENGTH_SHORT).show();
                } else if (!encpass.equals(re_encpass)) {
                    Toast.makeText(ForgotPassword.this, "Passwords Don't Match", Toast.LENGTH_SHORT).show();
                } else  {

                        String name = userDetails.getUsername();
                        userDetails.getPass();
                        Toast.makeText(ForgotPassword.this, name, Toast.LENGTH_LONG).show();
                        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                databaseReference.child("users").child(user).child("Password").setValue(encpass);
                               //userDetails.setPass(encpass);
                                Toast.makeText(ForgotPassword.this, "Successfully Reset Password ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }

        });


  }

}