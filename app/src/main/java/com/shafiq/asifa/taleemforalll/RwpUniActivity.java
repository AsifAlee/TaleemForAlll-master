package com.shafiq.asifa.taleemforalll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class RwpUniActivity extends AppCompatActivity {


    AdView mAdview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rwp_uni);
       mAdview = (AdView)findViewById(R.id.rwpAdview);

        MobileAds.initialize(this,getString(R.string.app_id));
        AdRequest adRequest =  new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);



        ListView listView = (ListView)findViewById(R.id.rwp_uni_list_view);
        ArrayList<University> rwpUniArraylist = new ArrayList<>();

        final ArrayList<String> rwpUniLinkArrayList = new ArrayList<>();

        rwpUniArraylist.add(new University("Pir Mehr Ali Shah Arid Agriculture University Rawalpindi",R.drawable.arid_2));
        rwpUniArraylist.add(new University("Govt Postgraduate College for Women Satellite Town Rawalpindi",R.drawable.govt_post));
        rwpUniArraylist.add(new University("Fatima Jinnah Women University Rawalpinid",R.drawable.fatima_jinnah));
        rwpUniArraylist.add(new University("National College of Arts,Rawalpindi campus",R.drawable.nca));

        rwpUniLinkArrayList.add(0,"https://www.uaar.edu.pk/");
        rwpUniLinkArrayList.add(1,"http://gpgcw.edu.pk/");
        rwpUniLinkArrayList.add(2,"https://www.fjwu.edu.pk/");
        rwpUniLinkArrayList.add(3,"http://nca.edu.pk/rawalpindi-campus/index");



        UniAdapter uniAdapter = new UniAdapter(this,R.layout.list_item_view,rwpUniArraylist);
        listView.setAdapter(uniAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RwpUniActivity.this,WebViewActivity.class);
                intent.putExtra("University Name",rwpUniLinkArrayList.get(position));
                startActivity(intent);
            }
        });


    }
}

