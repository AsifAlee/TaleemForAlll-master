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

public class IslamabadUniversitiesAct extends AppCompatActivity {

    ArrayList<String> isbLinkArrayList;
    AdView mAdview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_islamabad_universities);
      mAdview = (AdView)findViewById(R.id.adViewIsb);
        MobileAds.initialize(this,getString(R.string.app_id));

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);



        isbLinkArrayList = new ArrayList<>();
        isbLinkArrayList.add(0,"http://qau.edu.pk/message-from-the-admissions-office__trashed/");
        isbLinkArrayList.add(1,"https://www.iiu.edu.pk/?page_id=1007");
        isbLinkArrayList.add(2,"http://www.nust.edu.pk/Admissions/Pages/Why-NUST.aspx");
        isbLinkArrayList.add(3,"http://onlineadmission.numl.edu.pk/addmax/app/index.aspx");
        isbLinkArrayList.add(4,"http://nu.edu.pk/#");
        isbLinkArrayList.add(5,"https://ndu.edu.pk/fcs/fcs_admission.php");
        isbLinkArrayList.add(6,"http://islamabad.comsats.edu.pk/");
        isbLinkArrayList.add(7,"https://www.ist.edu.pk/admission");
        isbLinkArrayList.add(8,"https://cust.edu.pk/admissions/");
        isbLinkArrayList.add(9,"https://bahria.edu.pk/");
        isbLinkArrayList.add(10,"https://www.riphah.edu.pk/admissions");
        isbLinkArrayList.add(11,"https://fuuast.edu.pk/");
        isbLinkArrayList.add(12,"http://www.szabist.edu.pk/");
        isbLinkArrayList.add(13,"http://pide.org.pk/");
        isbLinkArrayList.add(14,"https://www.aiou.edu.pk/");
        isbLinkArrayList.add(15,"https://iqra.edu.pk/isl/");
        isbLinkArrayList.add(16,"http://www.pieas.edu.pk/");
        isbLinkArrayList.add(17,"https://www.au.edu.pk/");



        ListView listView = (ListView)findViewById(R.id.isbUniList);
        ArrayList<University> islambadUniArrayList = new ArrayList<>();

        islambadUniArrayList.add(new University("Quaid e Azam University,Islamabad",R.drawable.qau_logo));
        islambadUniArrayList.add(new University("International Islamic University,Islamabad",R.drawable.iuii_logo));
        islambadUniArrayList.add(new University("NUST,Islamabad",R.drawable.nust));
        islambadUniArrayList.add(new University("NUML,Islamabad",R.drawable.numl_logo));
        islambadUniArrayList.add(new University("FAST University,Islamabad",R.drawable.fast_logo));
        islambadUniArrayList.add(new University("National Defence University,Islamabad",R.drawable.nationaldef));
        islambadUniArrayList.add(new University("Comsats,University,Islamabad",R.drawable.comsats));
        islambadUniArrayList.add(new University("Institute of Space Technology,Islamabad",R.drawable.ist));
        islambadUniArrayList.add(new University("Capital Univsersity of Science and Technology,Islamabad",R.drawable.cust));
        islambadUniArrayList.add(new University("Bahria University,Islamabad",R.drawable.bahria));
        islambadUniArrayList.add(new University("Riphah University,Islamabad",R.drawable.riphah));
        islambadUniArrayList.add(new University("Federal Urdu University,Islamabad",R.drawable.federal_urdu));
        islambadUniArrayList.add(new University("SZABIST,Islamabad",R.drawable.szabist_logo));
        islambadUniArrayList.add(new University("PIDE,Islamabad",R.drawable.pide_logo));
        islambadUniArrayList.add(new University("Allama Iqbal Open University,Islamabad",R.drawable.allama_iqbal));
        islambadUniArrayList.add(new University("Iqra University,Islamabad",R.drawable.iqra_uni));
        islambadUniArrayList.add(new University("PIEAS,Islamabad",R.drawable.pieas));
        islambadUniArrayList.add(new University("Air University,Islamabad",R.drawable.air_uni_isb_));

        UniAdapter uniAdapter = new UniAdapter(this,R.layout.list_item_view,islambadUniArrayList);
        listView.setAdapter(uniAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),WebViewActivity.class);
                intent.putExtra("University Name",isbLinkArrayList.get(position));
                startActivity(intent);


            }
        });
    }
}
