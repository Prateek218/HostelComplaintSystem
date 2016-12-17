package com.project.prateek.hostelcomplaintsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CmpDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmp_display);

        TextView Lout = (TextView)findViewById(R.id.Logout);

        Intent intent = getIntent();
        final int cmp_id = intent.getIntExtra("Complaint_No",-1);
        final String c_frm_r = intent.getStringExtra("C_From_Reg");
        final String c_frm_n = intent.getStringExtra("C_From_Name");
        final String h_bl = intent.getStringExtra("Hostel_Block");
        final int r_no = intent.getIntExtra("Room_No",-1);
        final String to_warden_id = intent.getStringExtra("To_warden_id");
        final String subject = intent.getStringExtra("Subject");
        final String msg = intent.getStringExtra("Message");


        final TextView cmpid = (TextView) findViewById(R.id.id);
        final TextView cname = (TextView) findViewById(R.id.fname);
        final TextView usrid = (TextView) findViewById(R.id.usrid);
        final TextView rname = (TextView) findViewById(R.id.rname);
        final TextView hb = (TextView) findViewById(R.id.h_blk);
        final TextView rno = (TextView) findViewById(R.id.r_no);
        final TextView sub = (TextView) findViewById(R.id.subject);
        final TextView messa = (TextView) findViewById(R.id.msg);

        cmpid.setText(cmp_id+"");
        cname.setText(c_frm_n);
        usrid.setText(c_frm_r);
        rname.setText(to_warden_id);
        hb.setText(h_bl);
        rno.setText(r_no+ "");
        sub.setText(subject);
        messa.setText(msg);


    }
}
