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

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {
 EditText phone,otp;
 Button btngetotp,btnverify;
 String verificationId;
FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        phone = (EditText)findViewById(R.id.phno);
        otp = (EditText)findViewById(R.id.otp);
        btngetotp = (Button) findViewById(R.id.BtnGetOtp);
        btnverify = (Button) findViewById(R.id.BtnVerify);
        mAuth = FirebaseAuth.getInstance();

        btngetotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = null;
               if (TextUtils.isEmpty(phone.getText().toString())) {
                    Toast.makeText(OTP.this, "Enter valid phone number", Toast.LENGTH_SHORT).show();
                } else {
                    number = phone.getText().toString();
                }

                sendverificationcode(number);
            }
        });
        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(phone.getText().toString())) {
                    Toast.makeText(OTP.this, "Incorrect Otp", Toast.LENGTH_SHORT).show();

                } else {
                    verifycode(otp.getText().toString());
                }
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
                    Toast.makeText(OTP.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    //Toast.makeText(OTP.this,"user is"+u,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(OTP.this,HomeActivity.class));
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

            Toast.makeText(OTP.this,"Verification Failed",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {

            super.onCodeSent(s,token);
           verificationId =s;
           Toast.makeText(OTP.this,"OTP sent successfully",Toast.LENGTH_SHORT).show();
           btnverify.setEnabled(true);
        }
    };
}