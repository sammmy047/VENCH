package com.example.project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*public class MainActivity extends AppCompatActivity {
  EditText username,password,repassword,Email,phone;
  Button signin,signup;
  DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://myotp-b736f-default-rtdb.firebaseio.com/");
FirebaseAuth mAuth;

  Md5 md5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.uname);
        password = (EditText)findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        Email = (EditText)findViewById(R.id.email);
        phone = (EditText)findViewById(R.id.phone);
        signin= (Button) findViewById(R.id.siginbtn);
        signup = (Button) findViewById(R.id.signupbtn);
        mAuth = FirebaseAuth.getInstance();
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
                if(user.equals("")||encpass.equals("")||re_encpass.equals("")||usr_email.equals("")||phno.equals("")){
                    Toast.makeText(MainActivity.this,"Enter all fields",Toast.LENGTH_SHORT).show();
                }
                else if(!encpass.equals(re_encpass))
                {
                    Toast.makeText(MainActivity.this,"Entered Passwords Don't Match",Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.createUserWithEmailAndPassword(usr_email, encpass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information


                                        FirebaseUser user = mAuth.getCurrentUser();
                                        //UserDetails writedata = new UserDetails(users,usr_email,phno);
                                       // DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Registered users");
                                        //databaseReference.child(user.getUid()).setValue(writedata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                          //  @Override
                                            //public void onComplete(@NonNull Task<Void> task) {
                                                assert user != null;
                                                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(MainActivity.this, "Verification Email Has Been Sent.", Toast.LENGTH_LONG).show();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(MainActivity.this, "Error while sending Verification Email.", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                                Toast.makeText(MainActivity.this, "SignUp Successful.", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                                startActivity(intent);
                                            }


                                    else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                  databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // we check if phone is registered before or not
                            if (snapshot.hasChild(user)) {
                                Toast.makeText(MainActivity.this, "User Name Exists Please Enter A Different User Name", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);

                            } else {


                                // here we are uniquely identifying users by username
                             databaseReference.child("users").child(user).child("Phone_Number").setValue(phno);
                             databaseReference.child("users").child(user).child("Password").setValue(encpass);
                             databaseReference.child("users").child(user).child("User Name").setValue(user);
                               databaseReference.child("users").child(user).child("Email").setValue(usr_email);
                                Toast.makeText(MainActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                //Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                              // startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                }


               /* else {
                    if(pass.equals(repass)){
                        Boolean checkuser = db.checkuser(user);
                        if(checkuser==false){
                            Boolean insert = db.insert(user,encpass,usr_email,phno);
                            if(insert==true){
                                Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                final String getMobileTxt = phone.getText().toString();
                                final String getEmailTxt = email.getText().toString();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.putExtra("mobile",getMobileTxt);
                                intent.putExtra("email",getEmailTxt);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                   }
                }*/
            // remove this comment    });


/*
signin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
    }
});

        }


}*/
public class MainActivity extends AppCompatActivity {
    EditText username, password, repassword, Email, phone;
    Button signin, signup;
    FirebaseAuth mAuth;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://myotp-b736f-default-rtdb.firebaseio.com/");

    Md5 md5;

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
                if (user.equals("") || encpass.equals("") || re_encpass.equals("") || usr_email.equals("") || phno.equals("")) {
                    Toast.makeText(MainActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                } else if (!encpass.equals(re_encpass)) {
                    Toast.makeText(MainActivity.this, "Entered Passwords Don't Match", Toast.LENGTH_SHORT).show();
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


    private void registerUser(String user, String usr_email, String phno, String encpass) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(usr_email, encpass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    UserDetails writeDetails = new UserDetails(user,usr_email,phno,encpass);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");
                    databaseReference.child(firebaseUser.getUid()).setValue(writeDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(MainActivity.this, "Verification Email Has Been Sent.", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, "Error while sending Verification Email.", Toast.LENGTH_LONG).show();
                                }
                            });
                            Toast.makeText(MainActivity.this, "SignUp Successful.", Toast.LENGTH_LONG).show();
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