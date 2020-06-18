package com.shafiq.asifa.taleemforalll;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText fnameEditText;
    EditText cnicEditText;
    EditText phoneEditText;
    EditText emailEditText;
    EditText domicileEditText;
    EditText applyEditText;
    Button applyBtn;
    String name,fname,cnic,phone,email,domicile,applyText;
    DatabaseReference applicationRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        applicationRef = FirebaseDatabase.getInstance().getReference("Applications");

        nameEditText =(EditText)findViewById(R.id.nameEditText);
        fnameEditText = (EditText)findViewById(R.id.fnameEditText);
        cnicEditText  =(EditText)findViewById(R.id.cnicEditText);
        phoneEditText =(EditText)findViewById(R.id.phoneEditText);
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        domicileEditText = (EditText)findViewById(R.id.domicileEditText);
        applyEditText = (EditText)findViewById(R.id.applyEditText);
        applyBtn = (Button)findViewById(R.id.submitButton);


        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });






    }

    public  void  submitForm(){

        name = nameEditText.getText().toString().trim();
        fname = fnameEditText.getText().toString().trim();
        cnic =  cnicEditText.getText().toString().trim();
        phone = phoneEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
        domicile = domicileEditText.getText().toString().trim();
        applyText = applyEditText.getText().toString().trim();
        String id =applicationRef.push().getKey();

        if(TextUtils.isEmpty(name) | TextUtils.isEmpty(fname)|TextUtils.isEmpty(cnic)|TextUtils.isEmpty(phone)|TextUtils.isEmpty(email)|TextUtils.isEmpty(domicile)| TextUtils.isEmpty(applyText)){

            Toast.makeText(this,"Any text field can't be empty",Toast.LENGTH_SHORT).show();

        }

        else {

            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();


            if(isConnected){

                StudentApplication newApp = new StudentApplication(id,name,fname,cnic,phone,email,domicile,applyText);
                applicationRef.child(id).setValue(newApp);
                Intent intent = new Intent(RegistrationActivity.this,SubmitActivity.class);
                startActivity(intent);


            }

            else{


                Toast.makeText(getApplicationContext(),"Please check your internet connection", Toast.LENGTH_SHORT).show();

            }


        }

    }


}
