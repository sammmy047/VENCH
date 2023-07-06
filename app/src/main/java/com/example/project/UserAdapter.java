package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewholder>{
    Context homeActivity;//change this if giving error

    ArrayList<UserDetails> usersArrayList;
    ArrayList<msgModelclass> messages;
    public UserAdapter(HomeActivity homeActivity,ArrayList<UserDetails> usersArrayList,ArrayList<msgModelclass> messages)
    {
        this.homeActivity=homeActivity;
        this.usersArrayList=usersArrayList;
        this.messages=messages;
    }

    @NonNull
    @Override
    public UserAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(homeActivity).inflate(R.layout.user_row,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.viewholder holder, int position) {
       UserDetails userDetails = usersArrayList.get(position);
       holder.name.setText(userDetails.getUsername());
       holder.email.setText(userDetails.getEmail());
       holder.img.setImageResource(R.drawable.man);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homeActivity, chatwindow.class);
                Bundle b=new Bundle();
                b.putString("username",userDetails.getUsername().concat(",").concat(userDetails.getEmail()));
                intent.putExtras(b);
                homeActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        private TextView name,email;
        ImageView img;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userName);
            email = itemView.findViewById(R.id.userEmail);
            img=itemView.findViewById(R.id.img);
        }
    }
}
