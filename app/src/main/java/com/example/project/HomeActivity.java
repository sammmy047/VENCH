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
   /* ActivityHomeBinding binding;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    UserAdapter  userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        userAdapter =new UserAdapter(this);
        binding.recycler.setAdapter(userAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userAdapter.clear();
               for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                   String uid = dataSnapshot.getKey();
                   if(!uid.equals(FirebaseAuth.getInstance().getUid())){
                     UserDetails userDetails=  dataSnapshot.child(uid).getValue(UserDetails.class);
                     userAdapter.add(userDetails);
                   }
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (user != null) {
            // Name, email address, and profile photo Url

            String email = user.getUid();
            Toast.makeText(HomeActivity.this,""+email,Toast.LENGTH_LONG).show();
        }

            
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if(item.getItemId()==R.id.logout){
           FirebaseAuth.getInstance().signOut();
           startActivity(new Intent(HomeActivity.this,LoginActivity.class));
           return true;
       }
       return false;
    }*/

    FirebaseAuth auth;
    RecyclerView mainUserRecyclerView;
    UserAdapter adapter;
    FirebaseDatabase database;
    ArrayList<UserDetails> usersArrayList;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        database=FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        DatabaseReference reference = database.getReference().child("Registered Users");

        usersArrayList = new ArrayList<>();

        mainUserRecyclerView = findViewById(R.id.recycler);
        mainUserRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(HomeActivity.this,usersArrayList);
        mainUserRecyclerView.setAdapter(adapter);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    UserDetails users = dataSnapshot.getValue(UserDetails.class);
                    usersArrayList.add(users);
                }
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