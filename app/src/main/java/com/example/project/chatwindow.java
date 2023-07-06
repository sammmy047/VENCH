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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;


        public class chatwindow extends AppCompatActivity {
            String  reciverEmail,reciverName,SenderUID;

            TextView reciverNName;
            FirebaseDatabase database;
            FirebaseAuth firebaseAuth;

            CardView sendbtn;
            Button profile,back;
            EditText textmsg;

            String senderRoom,reciverRoom;
            RecyclerView messageAdpter;
            ArrayList<msgModelclass> messagesArrayList;
            messageAdapter mmessagesAdpter;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_chatwindow);
                database = FirebaseDatabase.getInstance();
                firebaseAuth = FirebaseAuth.getInstance();

                reciverName = getIntent().getStringExtra("name");
                reciverEmail = getIntent().getStringExtra("email");

                messagesArrayList = new ArrayList<>();
                sendbtn = findViewById(R.id.sendbtnn);
                textmsg = findViewById(R.id.textmsg);
                reciverNName = findViewById(R.id.recivername);
                profile = findViewById(R.id.profile);
                messageAdpter = findViewById(R.id.msgadpter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setStackFromEnd(true);
                messageAdpter.setLayoutManager(linearLayoutManager);
                mmessagesAdpter = new messageAdapter(chatwindow.this,messagesArrayList);
                messageAdpter.setAdapter(mmessagesAdpter);

                back=findViewById(R.id.back);

                reciverNName.setText(""+reciverName);

                SenderUID =  firebaseAuth.getUid();

                senderRoom = SenderUID+reciverEmail;
                reciverRoom = reciverEmail+SenderUID;



                //DatabaseReference reference = database.getReference().child("Registered Users").child(firebaseAuth.getUid());
                DatabaseReference  chatreference = database.getReference().child("chats").child(senderRoom).child("messages");

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_BACK));
                        dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,KeyEvent.KEYCODE_BACK));
                    }
                });
                chatreference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messagesArrayList.clear();
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            msgModelclass messages = dataSnapshot.getValue(msgModelclass.class);
                            messagesArrayList.add(messages);
                        }
                        mmessagesAdpter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

               profile.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent intent = new Intent(chatwindow.this,view_profile.class);
                       startActivity(intent);
                   }
               });
                sendbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String message = textmsg.getText().toString();
                        if (message.isEmpty()){
                            Toast.makeText(chatwindow.this, "Enter The Message First", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        textmsg.setText("");
                        Date date = new Date();
                        msgModelclass messages = new msgModelclass(message,SenderUID,"shashanks_cs20.rvitm+ven@rvei.edu.in",date.getTime());

                        database=FirebaseDatabase.getInstance();
                        database.getReference().child("chats")
                                .child(senderRoom)
                                .child("messages")
                                .push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                    }
                });

            }
        }

