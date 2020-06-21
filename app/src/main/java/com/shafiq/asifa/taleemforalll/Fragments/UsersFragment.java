package com.shafiq.asifa.taleemforalll.Fragments;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shafiq.asifa.taleemforalll.Adapters.UserAdapter;
import com.shafiq.asifa.taleemforalll.Model.User;
import com.shafiq.asifa.taleemforalll.R;

import java.util.ArrayList;
import java.util.List;


public class UsersFragment extends Fragment {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    private List<User> mUsers;
    private static final String TAG = "UsersFragment";
    UserAdapter userAdapter;
    EditText searchUserEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mUsers = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_users,container,false);

        searchUserEditText = view.findViewById(R.id.search_users);
        recyclerView = view.findViewById(R.id.recyler_view);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(searchUserEditText.getText().toString().equals("")) {
                    mUsers.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        User user = snapshot.getValue(User.class);
                        mUsers.add(user);

                        userAdapter = new UserAdapter(getContext(), mUsers, false);
                        recyclerView.setAdapter(userAdapter);
                    }


                    Log.d(TAG, "onDataChange2: " + mUsers.size());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        


        searchUserEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                SearchUser(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        
        
        return view;


    }

    private void SearchUser(String s) {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("search")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);

                    assert  user != null;
                    assert  firebaseUser != null;

                    if(!user.getId().equals(firebaseUser.getUid())){
                        mUsers.add(user);
                        userAdapter = new UserAdapter(getContext(),mUsers,false);
                        recyclerView.setAdapter(userAdapter);
                    }


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
