package com.example.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

    public class messageAdapter extends RecyclerView.Adapter {
        Context context;
        ArrayList<msgModelclass> messagesAdpterArrayList;
        int ITEM_SEND=1;
        int ITEM_RECIVE=2;

        public messageAdapter(Context context, ArrayList<msgModelclass> messagesAdpterArrayList) {
            this.context = context;
            this.messagesAdpterArrayList = messagesAdpterArrayList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == ITEM_SEND){
                View view = LayoutInflater.from(context).inflate(R.layout.sender_layout, parent, false);
                return new senderVierwHolder(view);
            }else {
                View view = LayoutInflater.from(context).inflate(R.layout.reciever_layout, parent, false);
                return new reciverViewHolder(view);
            }

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            msgModelclass messages = messagesAdpterArrayList.get(position);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(context).setTitle("Delete")
                            .setMessage("Are you sure you want to delete this message?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();

                    return false;
                }
            });
            if (holder.getClass()==senderVierwHolder.class){
                senderVierwHolder viewHolder = (senderVierwHolder) holder;
                viewHolder.msgtxt.setText(messages.getMessage());

            }else { reciverViewHolder viewHolder = (reciverViewHolder) holder;
                viewHolder.msgtxt.setText(messages.getMessage());



            }
        }

        @Override
        public int getItemCount() {
            return messagesAdpterArrayList.size();
        }

        @Override
        public int getItemViewType(int position) {
            msgModelclass messages = messagesAdpterArrayList.get(position);
            //FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(messages.getSenderid())
            if (messages.sender==true) {
                return ITEM_SEND;
            } else {
                return ITEM_RECIVE;
            }
        }

        class  senderVierwHolder extends RecyclerView.ViewHolder {

            TextView msgtxt;
            public senderVierwHolder(@NonNull View itemView) {
                super(itemView);

                msgtxt = itemView.findViewById(R.id.recivertextset);

            }
        }
        class reciverViewHolder extends RecyclerView.ViewHolder {

            TextView msgtxt;
            public reciverViewHolder(@NonNull View itemView) {
                super(itemView);

                msgtxt = itemView.findViewById(R.id.recivertextset);
            }
        }
    }


