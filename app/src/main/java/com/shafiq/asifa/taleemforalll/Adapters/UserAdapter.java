package com.shafiq.asifa.taleemforalll.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shafiq.asifa.taleemforalll.MessageActivity;
import com.shafiq.asifa.taleemforalll.Model.Chat;
import com.shafiq.asifa.taleemforalll.Model.User;
import com.shafiq.asifa.taleemforalll.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUser;
    private static final String TAG = "UserAdapter";
    private boolean isChat;
    private  String theLastMessage;



    public UserAdapter(Context mContext, List<User> mUser,boolean isChat) {
        this.mContext = mContext;
        this.mUser = mUser;
        this.isChat = isChat;
        Log.d(TAG, "UserAdapter: This is UserAdapter ");


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item,viewGroup,false);
        Log.d(TAG, "onCreateViewHolder: This is onCreateViewHolder");
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder viewHolder, int i) {
        final User user =  mUser.get(i);
        Log.d(TAG, "onBindViewHolder:  "+user.getUserEmail());
        Log.d(TAG, "onBindViewHolder: this is onBindViewHolder!");
        
        viewHolder.userName.setText(user.getUsername());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("currentUserIdToChat",user.getId());
                intent.putExtra("currentUserName",user.getUsername());
                intent.putExtra("uniqueId","UserAdapter");
                mContext.startActivity(intent);
            }
        });

        if(isChat){
            LastMessage(user.getId(),viewHolder.last_msg);
        }
        else {
            viewHolder.last_msg.setVisibility(View.GONE);
        }

        if(isChat){
            if(user.getStatus().equals("online")){

                viewHolder.imgOn.setVisibility(View.VISIBLE);
                viewHolder.imgOff.setVisibility(View.GONE);
            }
            else{

                viewHolder.imgOn.setVisibility(View.GONE);
                viewHolder.imgOff.setVisibility(View.VISIBLE);

            }
        }



        else{
            viewHolder.imgOn.setVisibility(View.GONE);
            viewHolder.imgOff.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: this is getItemCount = "+mUser.size());
        return mUser.size();
        
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        
        TextView userName;
        TextView last_msg;

        CircleImageView imgOn,imgOff;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.username);

            imgOff = itemView.findViewById(R.id.img_off);
            imgOn = itemView.findViewById(R.id.img_on);
            last_msg = itemView.findViewById(R.id.last_msg);

            Log.d(TAG, "ViewHolder: Thi is ViewHolder Class");
        }
    }

    private void LastMessage(final String userid, final TextView last_msg){

        theLastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    Chat chat = snapshot.getValue(Chat.class);

                    if (chat.getReciever() != null && !firebaseUser.getUid().isEmpty()) {

                        if((chat.getReciever().equalsIgnoreCase(firebaseUser.getUid())
                                && chat.getSender().equalsIgnoreCase(userid))
                                ||
                                (chat.getReciever().equalsIgnoreCase(userid)
                                        && chat.getSender().equalsIgnoreCase(firebaseUser.getUid())))
                        {
                            theLastMessage = chat.getMessage();
                        }

                    }

                }

                switch (theLastMessage){
                    case "default":
                        last_msg.setText("No message");
                        break;

                        default:
                            last_msg.setText(theLastMessage);
                            break;
                }

                theLastMessage = "default";

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
