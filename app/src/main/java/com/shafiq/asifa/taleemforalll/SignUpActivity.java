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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText usenameEditText;
    EditText passwordEditText;
    EditText UserEmailEditText;
    Button signUpBtn;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    TextView signInTv;
    String adminId = "5U3uVHLGR8TUIu6EszZcPwGu4l72";
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();

        getSupportActionBar().hide();

        usenameEditText = (EditText)findViewById(R.id.userNameSignUp);
        passwordEditText = (EditText)findViewById(R.id.passwordSingUp);
        UserEmailEditText = (EditText)findViewById(R.id.userEmailSignUp);
        signUpBtn = (Button)findViewById(R.id.signUpBtn);
        signInTv = (TextView)findViewById(R.id.signInTextView);
        progressDialog = new ProgressDialog(this);
        signInTv.setOnClickListener(this);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUser();
            }
        });
    }


    public void signUpUser() {
        final String username = usenameEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();
        final String useremail = UserEmailEditText.getText().toString().trim();

        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected){

            if (TextUtils.isEmpty(username)) {

                Toast.makeText(this, "Username cant be empty", Toast.LENGTH_SHORT).show();
            }

            else  if (TextUtils.isEmpty(password)) {

                Toast.makeText(this, "password cant be empty", Toast.LENGTH_SHORT).show();

            }
            else if(TextUtils.isEmpty(useremail)){
                Toast.makeText(this,"User Email cant be empty",Toast.LENGTH_SHORT).show();
            }

            else {

                progressDialog.setMessage("Registering user.....");
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(useremail, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){

                                    progressDialog.dismiss();

                                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                    final String userId = firebaseUser.getUid();

                                    reference = FirebaseDatabase.getInstance().getReference("Users")
                                            .child(userId);
                                    HashMap<String,String> hashMap = new HashMap<>();
                                    hashMap.put("id",userId);
                                    hashMap.put("username",username);
                                    hashMap.put("userEmail",useremail);
                                    hashMap.put("userPassword",password);
                                    hashMap.put("status","offline");
                                    hashMap.put("search",username.toLowerCase());
                                    reference.setValue(hashMap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){

                                                        if(userId == adminId){
                                                            Intent intent = new Intent(SignUpActivity.this,AdminActivity.class);
                                                            startActivity(intent);
                                                        }

                                                        else {

                                                            Intent intent = new Intent(SignUpActivity.this,Activity_Universities.class);
                                                            startActivity(intent);
                                                            Toast.makeText(getApplicationContext(), "User registered successfully", Toast.LENGTH_SHORT).show();

                                                        }


                                                    }

                                                }
                                            });


                                }

                                else {

                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"Failed to register",Toast.LENGTH_SHORT).show();
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

       Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
       startActivity(intent);

    }
}
