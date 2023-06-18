package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class forgotPassOTP extends AppCompatActivity {
    EditText username, otp, phone,Email;
    FirebaseUser user;
    Button btnsendemail, btnverify,btngetotp;
    String verificationId;
    FirebaseAuth mAuth;

     DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://myotp-b736f-default-rtdb.firebaseio.com/");
     @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_forgot_pass_otp);
          username = (EditText) findViewById(R.id.uname1);
          //phone = (EditText) findViewById(R.id.phone1);
          otp = (EditText) findViewById(R.id.otp);
          btngetotp = (Button) findViewById(R.id.BtnGetOtp);
          btnverify = (Button) findViewById(R.id.BtnVerify);
          mAuth = FirebaseAuth.getInstance();
          btngetotp.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  String user = username.getText().toString();
                  FirebaseUser firebaseUser = mAuth.getCurrentUser();

                  /*String phno = phone.getText().toString();*/
                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(user))
                        {
                            // user exists in firebase we authenticate the password
                            final String number = snapshot.child(user).child("Phone_Number").getValue(String.class);
                            //if (phno.equals(number)) {
                                sendverificationcode(number);
                                Toast.makeText(forgotPassOTP.this, "OTP is sent to registered Phone Number", Toast.LENGTH_SHORT).show();
                            //} else {
                                //Toast.makeText(forgotPassOTP.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                            //}
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifycode(otp.getText().toString());
            }
        });


    }
    private void verifycode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
        signinbyCredentials(credential);
    }

    private void signinbyCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(forgotPassOTP.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(forgotPassOTP.this,ForgotPassword.class));
                }
            }
        });
    }

    private void sendverificationcode(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted( @NotNull PhoneAuthCredential credential) {
            final String code = credential.getSmsCode();
            if(code != null)
            {
                verifycode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(forgotPassOTP.this,"Verification Failed",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {

            super.onCodeSent(s,token);
            verificationId =s;
            Toast.makeText(forgotPassOTP.this,"OTP sent successfully",Toast.LENGTH_SHORT).show();
            btnverify.setEnabled(true);
        }
    };
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass_otp);
        Email = (EditText) findViewById(R.id.uname1);
        btnsendemail = (Button) findViewById(R.id.BtnGetOtp);
        btnsendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString();
                if(TextUtils.isEmpty(email))
                {
                     Toast.makeText(forgotPassOTP.this,"Please Enter Registered Email Id",Toast.LENGTH_LONG).show();
                     Email.setError(" Valid Email is Required");
                     Email.requestFocus();
                }
                else{
                    resetpassword(email);
                }
            }
        });
    }

    private void resetpassword(String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(forgotPassOTP.this,"Please Check your inbox to reset password",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(forgotPassOTP.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}*/
              }