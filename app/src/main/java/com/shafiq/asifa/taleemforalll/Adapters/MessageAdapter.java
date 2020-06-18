package com.shafiq.asifa.taleemforalll.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.shafiq.asifa.taleemforalll.Model.Chat;
import com.shafiq.asifa.taleemforalll.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context mContext;
    private List<Chat> mChat;

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private static final String TAG = "MessageAdapter";

    FirebaseUser firebaseUser;


    public MessageAdapter(Context mContext, List<Chat> mChat) {
        this.mContext = mContext;
        this.mChat = mChat;
        Log.d(TAG, "Constructor : "+mChat.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if(i == MSG_TYPE_LEFT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left,viewGroup,false);
            return  new MessageAdapter.ViewHolder(view);
        }

        else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right,viewGroup,false);
            return  new MessageAdapter.ViewHolder(view);

        }


        /*View view = LayoutInflater.from(mContext).inflate(R.layout.chat_ite,viewGroup,false);
        return  new MessageAdapter.ViewHolder(view);*/


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Chat currentChat = mChat.get(i);
        viewHolder.msgTv.setText(currentChat.getMessage());

     /*   //check for the last message
        if(i == mChat.size()-1){
            if(currentChat.isSeen()){
                viewHolder.textSeen.setText("Seen");
            }
            else {
                viewHolder.textSeen.setText("Delievered");
            }
        }
        else{
            viewHolder.textSeen.setVisibility(View.GONE);
        }*/

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        TextView msgTv;
        TextView textSeen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            msgTv = itemView.findViewById(R.id.msg_item);
            textSeen = itemView.findViewById(R.id.txt_seen);

        }


    }

    @Override
    public int getItemViewType(int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(mChat.get(position).getSender().equals(firebaseUser.getUid())){
            return  MSG_TYPE_RIGHT;
        }
        else {
            return MSG_TYPE_LEFT;
        }

    }
}
