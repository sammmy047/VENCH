package com.example.project;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewholder>{
    Context homeActivity;//change this if giving error

    ArrayList<UserDetails> usersArrayList;
    public UserAdapter(HomeActivity homeActivity,ArrayList<UserDetails> usersArrayList)
    {
        this.homeActivity=homeActivity;
        this.usersArrayList=usersArrayList;
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
       holder.name.setText(userDetails.username);
       holder.email.setText(userDetails.email);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homeActivity, chatwindow.class);
                intent.putExtra("name",userDetails.getUsername());
                intent.putExtra("email","email");
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
        public viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userName);
            email = itemView.findViewById(R.id.userEmail);
        }
    }
}
