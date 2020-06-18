package com.shafiq.asifa.taleemforalll;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.shafiq.asifa.taleemforalll.R;

public class AboutActivity extends AppCompatActivity {

    AdView mAdview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        MobileAds.initialize(this,getString(R.string.app_id));
        mAdview = (AdView)findViewById(R.id.aboutAdView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);

        ImageView faceBookImageView = (ImageView)findViewById(R.id.facebookView);








        faceBookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Chitraltogether/"));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Chitraltogether/")));
                }

            }
        });
    }
}
