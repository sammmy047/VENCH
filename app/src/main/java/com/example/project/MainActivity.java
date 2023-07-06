package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class MainActivity extends AppCompatActivity {
    EditText username, password, repassword, Email, phone;
    Button signin, signup;
    FirebaseAuth mAuth;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://myotp-b736f-default-rtdb.firebaseio.com/");

    Md5 md5;

    boolean signupDisplayed=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.uname);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        Email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        signin = (Button) findViewById(R.id.siginbtn);
        signup = (Button) findViewById(R.id.signupbtn);
        mAuth = FirebaseAuth.getInstance();
        EnableDisableButtons(View.INVISIBLE);
        md5 = new Md5(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String usr_email = Email.getText().toString();
                String phno = phone.getText().toString();
                String encpass = md5.getMd5(pass);
                String repass = repassword.getText().toString();
                String re_encpass = md5.getMd5(repass);
                if(!signupDisplayed) {
                    TextView t = (TextView) findViewById(R.id.mainText);
                    t.setText("SIGN UP");
                    signupDisplayed = true;
                    EnableDisableButtons(View.VISIBLE);
                    return;
                }
                if (user.equals("") || encpass.equals("") || re_encpass.equals("") || usr_email.equals("") || phno.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                } else if (!encpass.equals(re_encpass)) {
                    Toast.makeText(getApplicationContext(), "Entered Passwords Don't Match", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(user, usr_email, phno, encpass);
                }
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    void EnableDisableButtons(int s)
    {
        username.setVisibility(s);
        password.setVisibility(s);
        repassword.setVisibility(s);
        Email.setVisibility(s);
        phone.setVisibility(s);
        findViewById(R.id.textInputLayout).setVisibility(s);
        findViewById(R.id.textViewforRepass).setVisibility(s);
    }

    private void registerUser(String user, String usr_email, String phno, String encpass) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(usr_email, encpass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    UserDetails writeDetails = new UserDetails(user,usr_email,phno,encpass);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");
                    assert firebaseUser != null;
                    databaseReference.child(firebaseUser.getUid()).setValue(writeDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Verification Email Has Been Sent.", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error while sending Verification Email.", Toast.LENGTH_LONG).show();
                                }
                            });
                            Toast.makeText(getApplicationContext(), "SignUp Successful.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                }


                else{
                    try{
                        throw task.getException();
                    }catch(FirebaseAuthWeakPasswordException e){
                        password.setError("Password is too weak !!");
                        password.requestFocus();
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        Email.setError("Email is Invalid or in use ");
                        Email.requestFocus();
                    }catch (FirebaseAuthUserCollisionException e){
                        username.setError("User is already registered with this email ");
                        username.requestFocus();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}

