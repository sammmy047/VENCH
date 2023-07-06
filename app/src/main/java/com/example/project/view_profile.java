package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class view_profile extends AppCompatActivity {
TextView username,phone,email_id;

Button back;

FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        username = (EditText) findViewById(R.id.username);
        phone = (EditText) findViewById(R.id.phone_num);
        email_id = (EditText) findViewById(R.id.email_id);
        back = (Button) findViewById(R.id.backbutton);

        auth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = auth.getCurrentUser();

        assert firebaseUser != null;
        getdata(firebaseUser);
       email_id.setText(firebaseUser.getEmail());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view_profile.this,"Going Back To Home Page",Toast.LENGTH_SHORT).show();
                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
            }
        });
    }



public void getdata(@NonNull FirebaseUser firebaseUser) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");
        String uid = firebaseUser.getUid();
      databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              UserDetails readUser = snapshot.getValue(UserDetails.class);
              if (readUser != null) {
                  String uname = readUser.username;
                  username.setText(uname);
                  String phno = readUser.phone;
                  phone.setText(phno);


              } else {
                  Toast.makeText(view_profile.this,"Something Wrong !!",Toast.LENGTH_LONG).show();
              }
          }
          @Override
          public void onCancelled(@NonNull DatabaseError error) {
              Toast.makeText(view_profile.this,"Something Wrong !!",Toast.LENGTH_LONG).show();
          }
      });


}



}

