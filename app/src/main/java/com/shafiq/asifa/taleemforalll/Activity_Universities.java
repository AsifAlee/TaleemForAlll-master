package com.shafiq.asifa.taleemforalll;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Activity_Universities extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    private static final String TAG = "Activity_Universities";


    Button isbBtn;
    Button rwpBtn;
    AdView mAdview;
    private String currentUserIdToChat;
    DatabaseReference reference;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(this,getString(R.string.app_id));
        setContentView(R.layout.activity__universities);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        rwpBtn = (Button)findViewById(R.id.rwpBtn);
        isbBtn = (Button)findViewById(R.id.IsbButton);
        currentUserIdToChat = firebaseUser.getUid();
        //firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mAdview = (AdView)findViewById(R.id.adView);
        AdRequest adRequest =  new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);








        isbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Universities.this,IslamabadUniversitiesAct.class);
                startActivity(intent);
            }
        });

        rwpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Universities.this,RwpUniActivity.class);
                startActivity(intent);
            }
        });



    }


    public void RegisterWithUs(View view){

        Intent intent = new Intent(Activity_Universities.this,RegistrationActivity.class);
        startActivity(intent);


    }

    public void SignOut(){

        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getApplicationContext(),"User Sign Out Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Activity_Universities.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);
        return  true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.aboutUs:
                Intent intent = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.logOut:
                status("offline");
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(Activity_Universities.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                return true;

            case R.id.policy:
                String url = "https://taleem-for-all.flycricket.io/privacy.html";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);



        }



    }

    public void chatWithUs(View view){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = new Intent(Activity_Universities.this,MessageActivity.class);
        intent.putExtra("currentUserIdToChat",firebaseUser.getUid());
        intent.putExtra("uniqueId","UniActivity");
        startActivity(intent);

    }

    public void status(String status){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        Log.d(TAG, "status : "+user.getUid());

        assert user != null;

        if (user != null) {

            reference = FirebaseDatabase.getInstance().getReference();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("status", status);
            reference.child("Users").child(user.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Activity_Universities.this, "Status Updated Successfully.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

   @Override
    protected void onPause() {
        super.onPause();


            status("offline");


    }




}
