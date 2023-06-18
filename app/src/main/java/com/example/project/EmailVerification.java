package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerification extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser users;
    TextView success,failure;
    Button retry,next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        success = (TextView) findViewById(R.id.success);
        success.setVisibility(View.INVISIBLE);
        failure = (TextView) findViewById(R.id.failure);
        failure.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();



        if(!(mAuth.getCurrentUser().isEmailVerified())){
            failure.setVisibility(View.VISIBLE);
           // retry.setVisibility(View.VISIBLE);
            //retry.setEnabled(true);
            retry.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   FirebaseUser user = mAuth.getCurrentUser();
                   user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {
                           Toast.makeText(EmailVerification.this, "Verification Email Has Been Sent.", Toast.LENGTH_LONG).show();
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(EmailVerification.this, "Error while sending Verification Email.", Toast.LENGTH_LONG).show();
                       }
                   });
               }
           });

        }
       else{
           success.setVisibility(View.VISIBLE);
           ///next.setEnabled(true);
          // next.setVisibility(View.VISIBLE);
           next.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                   startActivity(intent);

               }
           });
       }


        }



}