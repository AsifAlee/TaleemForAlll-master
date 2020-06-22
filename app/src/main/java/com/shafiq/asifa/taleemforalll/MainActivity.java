package com.shafiq.asifa.taleemforalll;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {



    EditText userEmailEditText;
    EditText passwordEditText;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    Button loginButton;
    FirebaseAuth.AuthStateListener mAuthListener;
    TextView textView;
    String adminId = "5U3uVHLGR8TUIu6EszZcPwGu4l72";
    FirebaseUser firebaseUser;



    @Override
    protected void onStart() {
        super.onStart();
//        firebaseAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    protected void onStop() {
        super.onStop();

    /*    if (mAuthListener != null) {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        userEmailEditText = (EditText)findViewById(R.id.userEmail);
        passwordEditText = (EditText)findViewById(R.id.passwordSignIn);
        loginButton = (Button)findViewById(R.id.loginButton);
        textView = (TextView)findViewById(R.id.signUpTextView);


        textView.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInUser();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            if(firebaseAuth.getCurrentUser().getUid().equals(adminId)){

                Intent intent = new Intent(MainActivity.this,AdminActivity.class);
                startActivity(intent);

            }


        }
        if(firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser().getUid() != adminId ){
            startActivity(new Intent(MainActivity.this,Activity_Universities.class));
        }



/*
        mAuthListener =  new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =  firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }

            }
        };*/


    }


    public void signInUser() {


        String userEmail = userEmailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected){

            if(TextUtils.isEmpty(userEmail)){
                Toast.makeText(getApplicationContext(),"Useremail can't be empty",Toast.LENGTH_SHORT).show();

            }
            else if(TextUtils.isEmpty(password)){
                Toast.makeText(getApplicationContext(),"Passwrod can't be empty",Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(userEmail)){
                Toast.makeText(getApplicationContext(),"Username cant be empty",Toast.LENGTH_SHORT).show();
            }

            else{

                progressDialog.setMessage("Signing in user....");
                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword(userEmail,password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    progressDialog.dismiss();
                                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                    if(firebaseUser.getUid().equals(adminId)){
                                        startActivity(new Intent(MainActivity.this,AdminActivity.class));
                                    }
                                    else{

                                        Toast.makeText(getApplicationContext(),"SignIn successful",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this,Activity_Universities.class);
                                        startActivity(intent);

                                    }

                                }
                                else{
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"sign in failed",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        }

        else {

            Toast.makeText(this,"Please check your internet connection",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
        startActivity(intent);
    }

}
