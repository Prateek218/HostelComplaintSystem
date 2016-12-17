package com.project.prateek.hostelcomplaintsystem;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class ComplainS extends AppCompatActivity {

    // URL to get contacts JSON
    private static String url = "http://192.168.43.64/webapp/fetchcmp.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView lv = (ListView)findViewById(R.id.pc_listview);
       // final Downloader d = new Downloader(this,url,lv);
       // d.execute();
    }
}
