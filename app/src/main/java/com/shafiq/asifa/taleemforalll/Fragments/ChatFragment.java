package com.shafiq.asifa.taleemforalll.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shafiq.asifa.taleemforalll.Adapters.UserAdapter;
import com.shafiq.asifa.taleemforalll.Model.Chat;
import com.shafiq.asifa.taleemforalll.Model.ChatList;
import com.shafiq.asifa.taleemforalll.Model.User;
import com.shafiq.asifa.taleemforalll.R;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment {

    String adminId = "5U3uVHLGR8TUIu6EszZcPwGu4l72";
    FirebaseUser firebaseUser;
    private List<ChatList> usersInChat;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers;
    DatabaseReference reference;
    private static final String TAG = "ChatFragment";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_chat,container,false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        usersInChat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usersInChat.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    ChatList chatList = snapshot.getValue(ChatList.class);
                    usersInChat.add(chatList);

                }

                ReadChatList();
                Log.d(TAG, "onDataChange: usersInChat:"+usersInChat.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;



    }


    private void ReadChatList(){

        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    for(ChatList chatList:usersInChat) {
                        if (user.getId().equals(chatList.getId())) {
                            mUsers.add(user);
                            userAdapter = new UserAdapter(getContext(),mUsers,true);
                            recyclerView.setAdapter(userAdapter);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



}