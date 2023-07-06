package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.project.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth auth;
    RecyclerView mainUserRecyclerView;
    UserAdapter adapter;
    FirebaseDatabase database;
    ArrayList<UserDetails> usersArrayList;
    ArrayList<msgModelclass> messages;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        database=FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        DatabaseReference reference = database.getReference().child("Registered Users");

        usersArrayList = new ArrayList<>();
        messages= new ArrayList<>();
        mainUserRecyclerView = findViewById(R.id.recycler);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    UserDetails users = dataSnapshot.getValue(UserDetails.class);
                    usersArrayList.add(users);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reference2=database.getReference().child("chats");
        reference2.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    msgModelclass mess = dataSnapshot.getValue(msgModelclass.class);
                    String curr=currentUser.getEmail();
                    if(mess.toid.equals(curr))
                    {
                        Toast.makeText(HomeActivity.this, "hello", Toast.LENGTH_SHORT).show();
                    }
                        messages.add(mess);
                }
                int i=0;
                while (i<usersArrayList.size()){
                    boolean f=false;
                    for (msgModelclass m:
                            messages)
                    {
                        if(usersArrayList.get(i).email.equals(m.senderid))
                        {
                            f=true;
                            break;
                        }
                    }
                    if(!f) {
                        usersArrayList.remove(i);
                        i-=1;
                    }
                    i+=1;
                }

                mainUserRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new UserAdapter(HomeActivity.this,usersArrayList,messages);
                mainUserRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        if (auth.getCurrentUser() == null){
            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
        }

    }
}