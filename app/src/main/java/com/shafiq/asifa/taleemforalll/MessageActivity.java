package com.shafiq.asifa.taleemforalll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shafiq.asifa.taleemforalll.Adapters.MessageAdapter;
import com.shafiq.asifa.taleemforalll.Model.Chat;
import com.shafiq.asifa.taleemforalll.Model.User;
import com.shafiq.asifa.taleemforalll.Notification.APIService;
import com.shafiq.asifa.taleemforalll.Notification.Client;
import com.shafiq.asifa.taleemforalll.Notification.Data;
import com.shafiq.asifa.taleemforalll.Notification.MyResponse;
import com.shafiq.asifa.taleemforalll.Notification.Sender;
import com.shafiq.asifa.taleemforalll.Notification.Token;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {

    String adminId = "5U3uVHLGR8TUIu6EszZcPwGu4l72";
    EditText text_send;
    ImageButton send_btn;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    private ArrayList<Chat> mChat;
    private RecyclerView recyclerView;
    private static final String TAG = "MessageActivity";
    private String sender, reciever;
    String uniqueId;

    Intent intent;
    String currentUserIdToChat;
    String currentUserName;
    Toolbar toolbar;
    TextView toolbarTv;

    FirebaseUser fuser;
    String userId;
    APIService apiService;
    private boolean notify = false;

    ValueEventListener seenListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        toolbar = findViewById(R.id.toolbar);

        text_send = findViewById(R.id.text_send);
        send_btn = findViewById(R.id.btn_send);
        recyclerView = findViewById(R.id.recyler_view);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        toolbarTv = findViewById(R.id.username);
        intent = getIntent();


        currentUserIdToChat = intent.getStringExtra("currentUserIdToChat");
        currentUserName = intent.getStringExtra("currentUserName");
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        uniqueId = intent.getStringExtra("uniqueId");
        Log.d(TAG, "onCreate: uniqueId : " + uniqueId);

        if (uniqueId.equals("UserAdapter")) {
            sender = adminId;
            reciever = currentUserIdToChat;
            userId = currentUserIdToChat;


            Log.d(TAG, "onCreate: Sender : " + sender);
            Log.d(TAG, "onCreate: Reciever : " + reciever);

            ReadMessage(sender, reciever);


        }

        if (uniqueId.equals("UniActivity")) {
            sender = firebaseUser.getUid();
            reciever = adminId;
            userId = adminId;

            Log.d(TAG, "onCreate: Sender : " + sender);
            Log.d(TAG, "onCreate: Reciever : " + reciever);

            ReadMessage(sender, reciever);
        }


        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify = true;
                String msg = text_send.getText().toString().trim();

                if (!msg.equals("")) {
                    SendMessage(sender, reciever, msg);
                } else {
                    Toast.makeText(getApplicationContext(), "Message can't be empty"
                            , Toast.LENGTH_SHORT).show();
                }

                text_send.setText("");
            }
        });

        // SeenMessage(reciever);
    }

    private void SeenMessage(final String userid) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    //firebaseUser is the current user
                    if (chat.getReciever().equals(firebaseUser.getUid()) && chat.getSender().equals(userid)) {

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isSeen", true);
                        snapshot.getRef().updateChildren(hashMap);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void SendMessage(String sender, final String reciever, final String msg) {

        databaseReference = firebaseDatabase.getReference();
        Log.d("ref", String.valueOf(databaseReference));


        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("reciever", reciever);
        hashMap.put("message", msg);
        hashMap.put("isSeen", false);

        databaseReference.child("Chats").push().setValue(hashMap);


        ReadMessage(sender, reciever);

        final DatabaseReference chatlistRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(firebaseUser.getUid())
                .child(currentUserIdToChat);

        chatlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()) {
                    chatlistRef.child("id").setValue(currentUserIdToChat);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final String msges = msg;
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(notify){
                    sendNotification(reciever,user.getUsername(),msg);
                }

                notify = false;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void ReadMessage(final String sender, final String reciever) {

        // Log.d(TAG, "ReadMessage: called");

        Log.d(TAG, "ReadMessage: Sender = " + sender);
        Log.d(TAG, "ReadMessage: Reciever" + reciever);

        mChat = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mChat.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);

                    assert chat != null;
                    if ((chat.getSender().equals(sender) && chat.getReciever().equals(reciever))
                            || (chat.getSender().equals(reciever) && chat.getReciever().equals(sender))) {
                        mChat.add(chat);
                    }


                }

                Log.d(TAG, "onDataChange: " + mChat.size());
                MessageAdapter messageAdapter = new MessageAdapter(MessageActivity.this, mChat);

                recyclerView.setAdapter(messageAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void status(String status) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);
        databaseReference.updateChildren(hashMap);

    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // databaseReference.removeEventListener(seenListener);
        status("offline");
    }

    private void sendNotification(String receiever,final String username,final String message){

        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiever);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(fuser.getUid(),R.mipmap.ic_launcher,username+": "+message,"New Message",userId);

                    Sender sender = new Sender(data,token.getToken());
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if(response.code() ==200){
                                        if(response.body().success != 1){
                                            Toast.makeText(MessageActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}